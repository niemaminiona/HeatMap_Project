import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JFrame {
    public SettingsWindow(HeatMapGraphicPanel heatMapPanel){
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // vertical stacking for sections

        // --- Buttons row ---
        JButton randomizeButton = new JButton("Randomize");
        randomizeButton.addActionListener(_ ->
        {
            heatMapPanel.randomizeHeatMap();
        });
        randomizeButton.setFocusPainted(false);
        JButton countButton = new JButton("Count");
        countButton.addActionListener(_ -> heatMapPanel.countHeatMap());
        countButton.setFocusPainted(false);
        JButton countNeighboursButton = new JButton("Count by neighbours");
        countNeighboursButton.addActionListener(_ -> heatMapPanel.countHeatMapByNeighbors());
        countNeighboursButton.setFocusPainted(false);

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
            sliderValueLabel.setText("Threshold value: " + thresholdSlider.getValue());
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

        // --- window Buttons row ---
        JPanel windowButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton logWindowButton = new JButton("View Logs");
        logWindowButton.addActionListener(_ -> LogWindow.showWindow());
        logWindowButton.setPreferredSize(buttonSize);
        logWindowButton.setFocusPainted(false);

        JButton patternWindowButton = new JButton("Patterns");
        patternWindowButton.addActionListener(_ -> new PatternWindow(heatMapPanel));
        patternWindowButton.setPreferredSize(buttonSize);
        patternWindowButton.setFocusPainted(false);

        windowButtonPanel.add(logWindowButton);
        windowButtonPanel.add(patternWindowButton);

        mainPanel.add(buttonPanel);
        mainPanel.add(sliderPanel);
        mainPanel.add(checkPanel);
        mainPanel.add(windowButtonPanel);

        this.add(mainPanel);
        this.pack();
        this.setTitle("Settings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}