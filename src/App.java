import javax.swing.SwingUtilities;
import com.formdev.flatlaf.FlatDarkLaf;

public class App {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FlatDarkLaf.setup();
                InterfazGrafica gui = new InterfazGrafica();
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            }
        });
    }
}
