import javax.swing.*;
import java.awt.*;

public class HeatMapWindow extends JFrame {
    public HeatMapWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new HeatMapGraphicPanel(50, 20));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

class HeatMapGraphicPanel extends JPanel{

    //variables
    public int mapSize;
    public int plotSize;
    public int diversity;
    private final HeatCell[][] heatCellsList;

    //Constructors
    public HeatMapGraphicPanel(){
        this(10,50);
    }

    public HeatMapGraphicPanel(int mapSize){
        this(mapSize,50);
    }

    //Actual constructor
    public HeatMapGraphicPanel(int mapSize, int plotSize){
        this.mapSize = mapSize;
        this.plotSize = plotSize;
        this.diversity = 8;
        heatCellsList = returnCountedNeighbours(returnRandomHeatMap(mapSize));

        this.setPreferredSize(new Dimension(mapSize * plotSize, mapSize * plotSize));
    }

    //actual drawing happens here
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // casts g to g2 since graphics2D is refreshed and better version of graphics

        for (int y = 0; y < heatCellsList.length; y++) {
            for (int x = 0; x < heatCellsList.length; x++) {

                int value = heatCellsList[y][x].numberOfNeighbours;

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

    //Heat map generators
    private HeatCell[][] returnRandomHeatMap(int size){
        HeatCell[][] heatMap = new HeatCell[size][size];

        for(int y = 0; y < heatMap.length; y++){
            for(int x = 0; x < heatMap.length; x++){
                heatMap[y][x] = new HeatCell((int)(Math.random() * diversity));
            }
        }
        
        return heatMap;
    }

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
