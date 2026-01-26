import javax.swing.*;
import java.awt.*;

public class HeatMapWindow extends JFrame {
    public HeatMapWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HeatMapGraphicPanel heatMapPanel = new HeatMapGraphicPanel(20, 50);

        this.add(heatMapPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);

        new SettingsWindow(heatMapPanel);
    }
}

class HeatMapGraphicPanel extends JPanel{

    /// variables ///
    public int mapSize;
    public int plotSize;
    public int diversity;
    public int activeThreshold = 3;
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
        this.diversity = 16;
        mainHeatCellsMap = returnRandomHeatMap(mapSize);

        this.setPreferredSize(new Dimension(mapSize * plotSize, mapSize * plotSize));
    }

    /// actual drawing happens here ///
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // casts g to g2 since graphics2D is refreshed and better version of graphics

        for (int y = 0; y < mainHeatCellsMap.length; y++) {
            for (int x = 0; x < mainHeatCellsMap[y].length; x++) {

                float ratio = mainHeatCellsMap[y][x].value / (float) diversity;

                // higher value, more red
                int red   = 255;
                int green = (int)(255 * (1 - ratio));
                int blue  = (int)(255 * (1 - ratio));
                g2.setColor(new Color(red, green, blue));

                g2.fillRect(y * plotSize, x * plotSize, plotSize, plotSize);
                g2.setColor(Color.black);
                g2.drawRect(y * plotSize, x * plotSize, plotSize, plotSize);
            }
        }

        writeTable(mainHeatCellsMap);
    }

    /// Heat map manipulation methods ///
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
        mainHeatCellsMap = returnRandomHeatMap(mapSize);

        repaint();
    }


    // method that returns map with counted neighbours
    private HeatCell[][] returnCountedValues(HeatCell[][] heatMap) {
        int size = heatMap.length;
        HeatCell[][] updatedHeatMap = new HeatCell[size][size];

        final Point[] neighbours = {
                new Point(-1, -1), new Point(0, -1), new Point(1, -1),
                new Point(-1,  0),                         new Point(1,  0),
                new Point(-1,  1), new Point(0,  1), new Point(1,  1)
        };

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                int count = 0;

                for (Point p : neighbours) {
                    int ny = y + p.y;
                    int nx = x + p.x;

                    if (ny >= 0 && ny < size && nx >= 0 && nx < size) {
                        if (heatMap[ny][nx].value >= activeThreshold) {
                            count += heatMap[ny][nx].value;
                        }
                    }
                }

                updatedHeatMap[y][x] = new HeatCell(count / neighbours.length);
            }
        }
        return updatedHeatMap;
    }


    //function that randomizes mainHeatMap
    public void countHeatMap() {
        mainHeatCellsMap = returnCountedValues(mainHeatCellsMap);

        repaint();
    }

    private void writeTable(HeatCell[][] map) {
        System.out.println("===========================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                System.out.print(map[x][y].value + " ");
            }
            System.out.println();
        }
    }

}
/// Additional classes ///
//class for data of single cell
class HeatCell {
    public int value;

    public HeatCell(int value) {
        this.value = value;
    }
}


// class of window for settings
class SettingsWindow extends JFrame {
    public SettingsWindow(HeatMapGraphicPanel heatMapPanel){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new GridLayout(2, 3));

        // part with buttons
        JButton randomizeButton = new JButton("Randomize");
        randomizeButton.setPreferredSize(new Dimension(200,100));
        randomizeButton.addActionListener(_ ->heatMapPanel.randomizeHeatMap());

        JButton countButton = new JButton("Count");
        countButton.setPreferredSize(new Dimension(200,100));
        countButton.addActionListener(_ -> heatMapPanel.countHeatMap());

        mainPanel.add(randomizeButton);
        mainPanel.add(countButton);

        //bottom part
        JLabel sliderValueLabel = new JLabel();

        JSlider thresholdSlider = new JSlider();
        thresholdSlider.setMaximum(heatMapPanel.diversity - 1);
        thresholdSlider.setMinimum(1);
        thresholdSlider.setValue(heatMapPanel.activeThreshold);
        thresholdSlider.addChangeListener(_ -> {
            heatMapPanel.activeThreshold = thresholdSlider.getValue();
            sliderValueLabel.setText("Value: " + thresholdSlider.getValue());
        });
        sliderValueLabel.setText("Value: " + thresholdSlider.getValue());

        mainPanel.add(sliderValueLabel);
        mainPanel.add(thresholdSlider);

        // finally adds main panel to window
        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
