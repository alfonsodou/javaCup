
import javax.swing.UIManager;
import org.javahispano.javacup.gui.principal.PrincipalFrame;

public class JavaCup {

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(com.jtattoo.plaf.graphite.GraphiteLookAndFeel.class.getName());
        } catch (Exception e) {
        }
        PrincipalFrame principal = new PrincipalFrame();
    }
}
