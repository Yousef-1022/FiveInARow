import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;


/**
 * Extension of JFrame, serves as the baseFrame of all windows. Crea=tes the Size of the window, gameIcon, and exit functionality
 */
public class BaseWindow extends JFrame {


    public BaseWindow() {


        setTitle("FiveInARow");
        setSize(800, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {


            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }

        });


        URL the_url = Window.class.getResource("icon.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(the_url));

    }
    

    /**
     * Shows the option to leave/continue game upon clicking the close window button
     */
    private void showExitConfirmation() {


        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?",
        "Proceed with choice", JOptionPane.YES_NO_OPTION);


        if (choice == JOptionPane.YES_OPTION) { proceedAtExit(); }
    }
    
    /**
     * Method to close the current game window
    */
    protected void proceedAtExit() { this.dispose(); }
}