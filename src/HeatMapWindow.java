import javax.swing.*;
import java.awt.*;

public class HeatMapWindow extends JFrame {
    public HeatMapWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new HeatMapGraphicPanel(20));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

class HeatMapGraphicPanel extends JPanel{

    public int mapSize;
    public int plotSize;
    public int diversity;
    private int[][] heatCellsList;

    public HeatMapGraphicPanel(){
        this(10,50);
    }

    public HeatMapGraphicPanel(int mapSize){
        this(mapSize,50);
    }

    public HeatMapGraphicPanel(int mapSize, int plotSize){
        this.mapSize = mapSize;
        this.plotSize = plotSize;
        this.diversity = 10;
        heatCellsList = new int[mapSize][mapSize];

        this.setPreferredSize(new Dimension(mapSize * plotSize, mapSize * plotSize));

        // filling list with random values
        for(int y = 0; y < mapSize; y++){
            for(int x = 0; x < mapSize; x++){
                heatCellsList[y][x] = (int)(Math.random() * diversity);
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // casts g to g2 since graphics2D is refreshed and better version of graphics

        for (int y = 0; y < heatCellsList.length; y++) {
            for (int x = 0; x < heatCellsList.length; x++) {

                int value = heatCellsList[y][x];
                int c = 255 - value * 255 / (diversity - 1);

                g2.setColor(new Color(255, c, c));
                g2.fillRect(y * plotSize, x * plotSize, plotSize, plotSize);

                g2.setColor(Color.black);
                g2.drawRect(y * plotSize, x * plotSize, plotSize, plotSize);
            }
        }
    }
}
