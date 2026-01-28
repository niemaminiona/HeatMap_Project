import javax.swing.*;

public class SettingsWindow extends JFrame {
    public SettingsWindow(HeatMapGraphicPanel heatMapPanel){
        JPanel mainPanel = new Templates.HorizontalStackPanel();

        // --- Buttons row ---
        JPanel buttonPanel = new Templates.FlowStackPanel();
        JButton randomizeButton, countButton, countNeighboursButton;

        buttonPanel.add(randomizeButton = new Templates.ButtonT1("Randomize"));
        randomizeButton.addActionListener(_ -> heatMapPanel.randomizeHeatMap());

        buttonPanel.add(countButton = new Templates.ButtonT1("Count by Avg."));
        countButton.addActionListener(_ -> heatMapPanel.countHeatMapByAverage());

        buttonPanel.add(countNeighboursButton = new Templates.ButtonT1("Count by Nbr."));
        countNeighboursButton.addActionListener(_ -> heatMapPanel.countHeatMapByNeighbors());

        // --- Slider row ---
        JPanel sliderPanel = new Templates.FlowStackPanel();
        JLabel sliderValueLabel = new JLabel("Threshold value: " + heatMapPanel.activeThreshold);
        JSlider thresholdSlider = new JSlider(1, heatMapPanel.diversity - 1, heatMapPanel.activeThreshold);
        thresholdSlider.addChangeListener(_ -> {
            heatMapPanel.activeThreshold = thresholdSlider.getValue();
            sliderValueLabel.setText("Threshold value: " + thresholdSlider.getValue());
        });
        sliderPanel.add(sliderValueLabel);
        sliderPanel.add(thresholdSlider);

        // --- Check row ---
        JPanel checkPanel = new Templates.FlowStackPanel();
        JCheckBox drawOutLineCheckBox = new JCheckBox();
        drawOutLineCheckBox.setSelected(heatMapPanel.drawOutLine);
        drawOutLineCheckBox.addActionListener(_ -> {
            heatMapPanel.drawOutLine = drawOutLineCheckBox.isSelected();
            heatMapPanel.repaint();
        });
        checkPanel.add(new JLabel("Draw grid outline: "));
        checkPanel.add(drawOutLineCheckBox);

        // --- window Buttons row ---
        JPanel windowButtonPanel = new Templates.FlowStackPanel();
        JButton logWindowButton, patternWindowButton, advancedSettingsButton;

        windowButtonPanel.add(advancedSettingsButton = new Templates.ButtonT1("Adv Settings"));
        advancedSettingsButton.addActionListener(_ -> new AdvancedSettingsWindow(heatMapPanel));

        windowButtonPanel.add(patternWindowButton = new Templates.ButtonT1("Patterns"));
        patternWindowButton.addActionListener(_ -> new PatternWindow(heatMapPanel));

        windowButtonPanel.add(logWindowButton = new Templates.ButtonT1("View Logs"));
        logWindowButton.addActionListener(_ -> LogWindow.showWindow());

        // adds up all of the above panels
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

        this.setAlwaysOnTop(true);
    }
}