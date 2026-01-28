import javax.swing.*;

public class LogWindow extends JFrame {

    private static LogWindow instance;   // single global instance
    private final JTextArea logArea = new JTextArea();

    private LogWindow() {   // private constructor
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Logs");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        this.add(new JScrollPane(logArea));
    }

    // Access point
    public static LogWindow getInstance() {
        if (instance == null) {
            instance = new LogWindow();
        }
        return instance;
    }

    // Show window
    public static void showWindow() {
        SwingUtilities.invokeLater(() -> {
            LogWindow w = getInstance();
            w.setVisible(true);
            w.toFront();
        });
    }

    // Log API
    public static void addLog(String message) {
        SwingUtilities.invokeLater(() ->
                getInstance().append(message)
        );
    }

    public static void clearLogs() {
        SwingUtilities.invokeLater(() ->
                getInstance().logArea.setText("")
        );
    }

    // internal helper
    private void append(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}
