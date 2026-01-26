import javax.swing.*;
import java.awt.*;

public class HeatMapWindow extends JFrame {
    public HeatMapWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HeatMapGraphicPanel heatMapPanel = new HeatMapGraphicPanel(200, 5);

        this.add(heatMapPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Heat distribution");

        new SettingsWindow(heatMapPanel);
    }
}

class HeatMapGraphicPanel extends JPanel{

    /// variables ///
    public int mapSize;
    public int plotSize;
    public int diversity = 16;
    public int activeThreshold = 7;
    public boolean drawOutLine = false;
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
        mainHeatCellsMap = returnRandomHeatMap(mapSize);

        countHeatMapByNeighbours(2);
        countHeatMap(4);

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
                if (drawOutLine){
                    g2.setColor(Color.black);
                    g2.drawRect(y * plotSize, x * plotSize, plotSize, plotSize);
                }
            }
        }
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


    // method that returns averaged map
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
    public void countHeatMap(){
        countHeatMap(1);
    }
    public void countHeatMap(int amount) {
        for(int i = 0; i < amount; i++){
            mainHeatCellsMap = returnCountedValues(mainHeatCellsMap);
        }
        repaint();
    }

    // method that returns map with counted neighbours
    private HeatCell[][] returnCountedByNeighbours(HeatCell[][] heatMap) {
        int size = heatMap.length;
        HeatCell[][] updatedHeatMap = new HeatCell[size][size];

        final Point[] neighbours = {
                new Point(-1, -1), new Point(0, -1), new Point(1, -1),
                new Point(-1,  0),                         new Point(1,  0),
                new Point(-1,  1), new Point(0,  1), new Point(1,  1)
        };

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                float count = 0;
                float ratio = (float)diversity / neighbours.length;

                for (Point p : neighbours) {
                    int ny = y + p.y;
                    int nx = x + p.x;

                    if (ny >= 0 && ny < size && nx >= 0 && nx < size) {
                        if (heatMap[ny][nx].value >= activeThreshold) {
                            count += ratio;
                        }
                    }
                }
                updatedHeatMap[y][x] = new HeatCell(Math.round(count));
            }
        }
        return updatedHeatMap;
    }

    //function that randomizes mainHeatMap
    public void countHeatMapByNeighbours(){
        countHeatMapByNeighbours(1);
    }
    public void countHeatMapByNeighbours(int amount) {
        for(int i = 0; i < amount; i++){
            mainHeatCellsMap = returnCountedByNeighbours(mainHeatCellsMap);
        }
        repaint();
    }

    // method that out writes map
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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // vertical stacking for sections

        // --- Buttons row ---
        JButton randomizeButton = new JButton("Randomize");
        randomizeButton.addActionListener(_ -> heatMapPanel.randomizeHeatMap());
        JButton countButton = new JButton("Count");
        countButton.addActionListener(_ -> heatMapPanel.countHeatMap());
        JButton countNeighboursButton = new JButton("Count by neighbours");
        countNeighboursButton.addActionListener(_ -> heatMapPanel.countHeatMapByNeighbours());

        // fixed size
        Dimension buttonSize = new Dimension(180, 70);
        randomizeButton.setPreferredSize(buttonSize);
        countButton.setPreferredSize(buttonSize);
        countNeighboursButton.setPreferredSize(buttonSize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(randomizeButton);
        buttonPanel.add(countButton);
        buttonPanel.add(countNeighboursButton);

        // --- Slider row ---
        JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel sliderValueLabel = new JLabel("Threshold value: " + heatMapPanel.activeThreshold);
        JSlider thresholdSlider = new JSlider(1, heatMapPanel.diversity - 1, heatMapPanel.activeThreshold);
        thresholdSlider.addChangeListener(_ -> {
            heatMapPanel.activeThreshold = thresholdSlider.getValue();
            sliderValueLabel.setText("Value: " + thresholdSlider.getValue());
        });
        sliderPanel.add(sliderValueLabel);
        sliderPanel.add(thresholdSlider);

        // --- Check row ---
        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JCheckBox drawOutLineCheckBox = new JCheckBox();
        drawOutLineCheckBox.setSelected(heatMapPanel.drawOutLine);
        drawOutLineCheckBox.addActionListener(_ -> {
            heatMapPanel.drawOutLine = drawOutLineCheckBox.isSelected();
            heatMapPanel.repaint();
        });
        checkPanel.add(new JLabel("Draw grid outline: "));
        checkPanel.add(drawOutLineCheckBox);


        mainPanel.add(buttonPanel);
        mainPanel.add(sliderPanel);
        mainPanel.add(checkPanel);

        this.add(mainPanel);
        this.pack();
        this.setTitle("Settings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

