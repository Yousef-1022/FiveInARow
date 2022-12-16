import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;


/**
 * Window to choose game type
 */
public class MainWindow extends BaseWindow {

    
    private List<Window> gameWindows = new ArrayList<>();

    
    public MainWindow() {

        
        JButton smallScale = new JButton();
        smallScale.setText("6 x 6");
        smallScale.addActionListener(getActionListener(6));
        

        JButton mediumScale = new JButton();
        mediumScale.setText("10 x 10");
        mediumScale.addActionListener(getActionListener(10));


        JButton largeScale = new JButton();
        largeScale.setText("14 x 14");
        largeScale.addActionListener(getActionListener(14));
        

        JPanel top = new JPanel();
        JLabel info = new JLabel();
        info.setText("Choose game type");
        top.add(info);
        
        
        JPanel buttons = new JPanel();
        buttons.add(smallScale);
        buttons.add(mediumScale);
        buttons.add(largeScale);
        buttons.setLayout(new GridLayout());

        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top,BorderLayout.NORTH);
        getContentPane().add(buttons,BorderLayout.CENTER);
    }
    

    private ActionListener getActionListener(final int size) {


        return new ActionListener() { 

            @Override
            public void actionPerformed(ActionEvent E) {

                Window window = new Window(size, MainWindow.this);
                window.setVisible(true);
                gameWindows.add(window);
            }
        };
    }
    

    public List<Window> getWindowList() { return gameWindows; }
    

    /**
     * Method to close the current game window
    */
    @Override
    protected void proceedAtExit() { System.exit(0); }
}