
import java.awt.Dimension;
import javax.swing.UIManager;
import org.javahispano.javacup.gui.asistente.AsistenteFrame;

public class JavaCupCodeGenerator {

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(com.jtattoo.plaf.graphite.GraphiteLookAndFeel.class.getName());
        } catch (Exception e) {
        }
        AsistenteFrame a = new AsistenteFrame();
        a.setSize(new Dimension(710, 440));
        a.setLocationRelativeTo(null);
    }
}
