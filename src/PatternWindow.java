import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PatternWindow extends JFrame {
    public PatternWindow(HeatMapGraphicPanel heatMapPanel){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JButton[] buttons = new JButton[]{new JButton(), new JButton(),  new JButton(),  new JButton()};

        // Define patterns here
        buttons[0].addActionListener(_ ->{
            int temp = heatMapPanel.activeThreshold;
            heatMapPanel.randomizeHeatMap();

            heatMapPanel.activeThreshold = 7;
            heatMapPanel.countHeatMapByNeighbors(2);
            heatMapPanel.countHeatMap(4);

            heatMapPanel.activeThreshold = temp;
        });

        buttons[1].addActionListener(_ ->{
            int temp = heatMapPanel.activeThreshold;
            heatMapPanel.randomizeHeatMap();

            heatMapPanel.activeThreshold = 7;
            heatMapPanel.countHeatMapByNeighbors(2);
            heatMapPanel.activeThreshold = 9;
            heatMapPanel.countHeatMap(3);

            heatMapPanel.activeThreshold = temp;
        });

        buttons[2].addActionListener(_ ->{
            int temp = heatMapPanel.activeThreshold;
            heatMapPanel.randomizeHeatMap();

            heatMapPanel.activeThreshold = 4;
            heatMapPanel.countHeatMap(2);
            heatMapPanel.activeThreshold = 6;
            heatMapPanel.countHeatMap();
            heatMapPanel.countHeatMapByNeighbors(3);

            heatMapPanel.activeThreshold = temp;
        });

        buttons[3].addActionListener(_ ->{
            int temp = heatMapPanel.activeThreshold;
            heatMapPanel.randomizeHeatMap();

            heatMapPanel.activeThreshold = 4;
            heatMapPanel.countHeatMap(2);
            heatMapPanel.activeThreshold = 6;
            heatMapPanel.countHeatMap(2);
            heatMapPanel.countHeatMapByNeighbors();
            heatMapPanel.countHeatMap(4);

            heatMapPanel.activeThreshold = temp;
        });

        Dimension buttonSize = new Dimension(220, 70);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 22);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("Pattern " + (i + 1));
            buttons[i].setPreferredSize(buttonSize);
            buttons[i].setMaximumSize(buttonSize);
            buttons[i].setFont(buttonFont);
            buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            buttons[i].setFocusPainted(false);
            mainPanel.add(buttons[i]);
        }

        this.add(mainPanel);
        this.pack();
        this.setTitle("Patterns");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addWindowFocusListener(new WindowAdapter() {
            public void windowLostFocus(WindowEvent e) {
                dispose();
            }
        });
    }
}