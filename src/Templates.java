import javax.swing.*;
import java.awt.*;

public enum Templates {;
    public static class ButtonT1 extends JButton {
        public ButtonT1(){
            this("");
        }
        public ButtonT1(String text){
            this.setText(text);
            this.setPreferredSize(new Dimension(180, 70));
            this.setFocusPainted(false);
            this.setFont(new Font("SansSerif", Font.BOLD, 22));
        }
    }
    public static class FlowStackPanel extends JPanel{
        public FlowStackPanel(){
            this.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
    }
    public static class HorizontalStackPanel extends JPanel{
        public HorizontalStackPanel(){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }
    }
}
