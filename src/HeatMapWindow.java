import javax.swing.*;
import java.awt.*;

public class HeatMapWindow extends JFrame {
    public HeatMapWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HeatMapGraphicPanel heatMapPanel = new HeatMapGraphicPanel(50, 15);

        this.add(heatMapPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        new SettingsWindow();
    }
}

class HeatMapGraphicPanel extends JPanel{

    /// variables ///
    public int mapSize;
    public int plotSize;
    public int diversity;
    private HeatCell[][] mainHeatCellsMap;

    /// Constructors ///
    public HeatMapGraphicPanel(){
        this(10,50);
    }

    public HeatMapGraphicPanel(int mapSize){
        this(mapSize,50);
    }

    /// Actual constructor ///
    public HeatMapGraphicPanel(int mapSize, int plotSize){
        this.mapSize = mapSize;
        this.plotSize = plotSize;
        this.diversity = 8;
        mainHeatCellsMap = returnRandomHeatMap(mapSize);

        this.setPreferredSize(new Dimension(mapSize * plotSize, mapSize * plotSize));
    }

    /// actual drawing happens here ///
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // casts g to g2 since graphics2D is refreshed and better version of graphics

        for (int y = 0; y < mainHeatCellsMap.length; y++) {
            for (int x = 0; x < mainHeatCellsMap.length; x++) {

                int value = mainHeatCellsMap[y][x].numberOfNeighbours;

                // clamp just in case
                value = Math.min(value, 8);

                float ratio = value / 8.0f;

                // higher neighbour count → more red
                int red   = 255;
                int green = (int)(255 * (1 - ratio));
                int blue  = (int)(255 * (1 - ratio));

                g2.setColor(new Color(red, green, blue));

                g2.fillRect(y * plotSize, x * plotSize, plotSize, plotSize);
                g2.setColor(Color.black);
                g2.drawRect(y * plotSize, x * plotSize, plotSize, plotSize);
            }
        }
    }

    /// Heat map generators ///
    // function that returns random map
    private HeatCell[][] returnRandomHeatMap(int size){
        HeatCell[][] heatMap = new HeatCell[size][size];

        for(int y = 0; y < heatMap.length; y++){
            for(int x = 0; x < heatMap.length; x++){
                heatMap[y][x] = new HeatCell((int)(Math.random() * diversity));
            }
        }
        
        return heatMap;
    }

    //function that randomizes mainHeatMap
    public void randomizeHeatMap() {
        mainHeatCellsMap = returnCountedNeighbours(
                returnRandomHeatMap(mapSize)
        );
        repaint();
    }


    // method that returns map with counted neighbours
    private HeatCell[][] returnCountedNeighbours(HeatCell[][] heatMap) {
        int size = heatMap.length;
        HeatCell[][] updatedHeatMap = new HeatCell[size][size];

        final Point[] neighbours = {
                new Point(-1, -1), new Point(0, -1), new Point(1, -1),
                new Point(-1,  0),                         new Point(1,  0),
                new Point(-1,  1), new Point(0,  1), new Point(1,  1)
        };

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                int neighbourCount = 0;

                for (Point p : neighbours) {
                    int ny = y + p.y;
                    int nx = x + p.x;

                    // bounds check
                    if (ny >= 0 && ny < size && nx >= 0 && nx < size && heatMap[ny][nx].value > 4) {
                        neighbourCount++;
                    }

                }

                updatedHeatMap[y][x] = new HeatCell(heatMap[y][x].value, neighbourCount);
            }
        }
        return updatedHeatMap;
    }
}
/// Additional classes ///
//class for data of single cell
class HeatCell{
    public int value;
    public int numberOfNeighbours;

    public HeatCell(int value){
        this.value = value;
        this.numberOfNeighbours = 0;
    }

    public HeatCell(int value, int numberOfNeighbours){
        this.value = value;
        this.numberOfNeighbours = numberOfNeighbours;
    }
}

// class of window for settings
class SettingsWindow extends JFrame {
    public SettingsWindow(HeatMapGraphicPanel heatMapPanel){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton randomizeButton = new JButton("Randomize");
        randomizeButton.setPreferredSize(new Dimension(200,100));
        randomizeButton.addActionListener(_ ->{

        });
        this.add(randomizeButton);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
