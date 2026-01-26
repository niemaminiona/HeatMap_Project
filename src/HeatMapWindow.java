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
        this.diversity = 10;
        heatCellsList = returnRandomHeatMap(mapSize);

        this.setPreferredSize(new Dimension(mapSize * plotSize, mapSize * plotSize));
    }

    //actual drawing happens here
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // casts g to g2 since graphics2D is refreshed and better version of graphics

        for (int y = 0; y < heatCellsList.length; y++) {
            for (int x = 0; x < heatCellsList.length; x++) {

                int value = heatCellsList[y][x].value;
                int c = 255 - value * 255 / (diversity - 1);

                g2.setColor(new Color(255, c, c));
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

    private HeatCell[][] returnCountedNeighbours(HeatCell[][] heatMap){
        HeatCell[][] updatedHeatMap = new HeatCell[heatMap.length][heatMap.length];


        final Point[] listOfNeighboursIndexes = {
                new Point(-1, -1), new Point(0, -1), new Point(1, -1),
                new Point(-1,  0), new Point(0,  0), new Point(1,  0),
                new Point(-1,  1), new Point(0,  1), new Point(1,  1)
        };




        for(int y = 0; y < heatMap.length; y++){
            for(int x = 0; x < heatMap.length; x++){
                for(int count = 0; count < listOfNeighboursIndexes.length; count++){
                    Point currentIndexPointed = listOfNeighboursIndexes[count];
                    int indexedY = y + currentIndexPointed.y;
                    int indexedX = x + currentIndexPointed.x;
                    HeatCell selectedHeaCell = heatMap[indexedY][indexedX];
                    if(currentIndexPointed.equals(new Point(0, 0)) && indexedY < 0 && x + indexedX < 0){
                        continue;
                    }else {
                        if()
                    }
                }
            }
        }

        return updatedHeatMap;
    }
}

//class for data of single cell
class HeatCell{
    public int value;
    public int numberOfNeighbours = 0;

    public HeatCell(int value){
        this.value = value;
    }
}
