import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdvancedSettingsWindow extends JFrame {
    public AdvancedSettingsWindow(HeatMapGraphicPanel heatMapPanel){
        JPanel mainPanel = new Templates.HorizontalStackPanel();
        Dimension spacing = new Dimension(8, 0);

        // map size slider
        JLabel mapSizeLabel = new JLabel("(" + heatMapPanel.mapSize + ")");

        JSlider mapSizeSlider = new JSlider(1, 1000, heatMapPanel.mapSize);
        mapSizeSlider.addChangeListener(_ -> {
            mapSizeLabel.setText("(" + mapSizeSlider.getValue() + ")");
        });

        JButton mapSizeMinusButton = new JButton("-");
        mapSizeMinusButton.addActionListener(_ -> mapSizeSlider.setValue(mapSizeSlider.getValue() - 1));
        mapSizeMinusButton.setFocusable(false);

        JButton mapSizePlusButton = new JButton("+");
        mapSizePlusButton.addActionListener(_ -> mapSizeSlider.setValue(mapSizeSlider.getValue() + 1));
        mapSizePlusButton.setFocusable(false);

        JButton mapSizeSetButton = new JButton("Set");
        mapSizeSetButton.addActionListener(_ -> {
            heatMapPanel.mapSize = mapSizeSlider.getValue();
            heatMapPanel.randomizeHeatMap();
            heatMapPanel.adjustWindowSize();
        });
        mapSizeSetButton.setFocusable(false);

        JPanel mapSizePanel = new Templates.FlowStackPanel();
        mapSizePanel.setLayout(new BoxLayout(mapSizePanel, BoxLayout.X_AXIS));
        mapSizePanel.add(new JLabel("Map Size: "));
        mapSizePanel.add(mapSizeLabel);
        mapSizePanel.add(Box.createRigidArea(spacing)); // spacing
        mapSizePanel.add(mapSizeMinusButton);
        mapSizePanel.add(mapSizeSlider);
        mapSizePanel.add(mapSizePlusButton);
        mapSizePanel.add(Box.createRigidArea(spacing)); // spacing
        mapSizePanel.add(mapSizeSetButton);
        mainPanel.add(mapSizePanel);

        // plots size slider
        JLabel plotSizeLabel = new JLabel("(" + heatMapPanel.plotSize + ")");

        JSlider plotSizeSlider = new JSlider(1, 50, heatMapPanel.plotSize);
        plotSizeSlider.addChangeListener(_ -> {
            plotSizeLabel.setText("(" + plotSizeSlider.getValue() + ")");
        });

        JButton plotSizeMinusButton = new JButton("-");
        plotSizeMinusButton.addActionListener(_ -> plotSizeSlider.setValue(plotSizeSlider.getValue() - 1));
        plotSizeMinusButton.setFocusable(false);

        JButton plotSizePlusButton = new JButton("+");
        plotSizePlusButton.addActionListener(_ -> plotSizeSlider.setValue(plotSizeSlider.getValue() + 1));
        plotSizePlusButton.setFocusable(false);

        JButton plotSizeSetButton = new JButton("Set");
        plotSizeSetButton.addActionListener(_ -> {
            heatMapPanel.plotSize = plotSizeSlider.getValue();
            heatMapPanel.randomizeHeatMap();
            heatMapPanel.adjustWindowSize();
        });
        plotSizeSetButton.setFocusable(false);

        JPanel plotSizePanel = new Templates.FlowStackPanel();
        plotSizePanel.setLayout(new BoxLayout(plotSizePanel, BoxLayout.X_AXIS));
        plotSizePanel.add(new JLabel("Plot Size: "));
        plotSizePanel.add(plotSizeLabel);
        plotSizePanel.add(Box.createRigidArea(spacing)); // spacing
        plotSizePanel.add(plotSizeMinusButton);
        plotSizePanel.add(plotSizeSlider);
        plotSizePanel.add(plotSizePlusButton);
        plotSizePanel.add(Box.createRigidArea(spacing)); // spacing
        plotSizePanel.add(plotSizeSetButton);
        mainPanel.add(plotSizePanel);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        this.add(mainPanel);
        this.pack();
        this.setTitle("Advanced Settings");
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
