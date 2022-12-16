import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;


/**
 * Actual gameplay window
 */
public class Window extends BaseWindow {


    private final int size;
    private final Model model;
    private final JLabel label;
    private final MainWindow mainWindow;
    private List <AdvancedButton> theTable; 
    

    public Window(int size, MainWindow mainWindow) {
        
        
        //Initialization
        this.size = size;

        model = new Model(size);

        label = new JLabel();
        updateLabelText();

        this.mainWindow = mainWindow;
        mainWindow.getWindowList().add(this);

        this.theTable = new ArrayList<AdvancedButton>();
        
    
        //top Panel
        JPanel top = new JPanel();
        

        //Button to reset or start new game
        JButton newGameButton = new JButton();
        newGameButton.setText("make a New Game");
        newGameButton.addActionListener(E -> newGame());
        
        
        //Adding info for top Panel
        top.add(label);
        top.add(newGameButton);
        

        //Main panel of the game
        JPanel mainPanel = new JPanel();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));


        //Add tiles with their event listeners
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
            }
        }


        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    

    /**
     * Method to create all the tiles in the game, add event listeners. Each listeners update the board accordingly, and showWinner if the game isWon
     * @param panel (Current Game Panel)
     * @param i (Rows)
     * @param j (Columns)
     */
    private void addButton(JPanel panel, final int i, final int j) {

        
        final JButton button = new JButton();
        

        //ActionListener function changes the values of the buttons accordingly
        button.addActionListener(E -> {

            model.step(i, j);    
            for (AdvancedButton btn : theTable) {

                if(model.table[btn.row][btn.col].name() != "NOBODY") {
                    btn.button.setText(model.table[btn.row][btn.col].name());
                }
                else if (model.table[btn.row][btn.col].name() == "NOBODY") {
                    btn.button.setText("");
                }
            }
    
    
            updateLabelText();
            
            Player winner = model.findWinner();
            if (winner != Player.NOBODY) {
                showGameOverMessage(winner);
            }
        });

        panel.add(button);
        theTable.add(new AdvancedButton(button,i,j));
    }

    

    /**
     * Method to display the winner dialog, triggered if a player wins the game after pressing a button - used in eventListener function
     * @param Winner (Player)
     */
    private void showGameOverMessage(Player Winner) {

        JOptionPane.showMessageDialog(this, "Game ended!  -  Winner: " + Winner.name());
        newGame();
    }
    

    /**
     * Method to create a newWindow based on the same metrics of the game, invoked upon pressing the button
    */
    private void newGame() {
        
        Window newWindow = new Window(size, mainWindow);
        newWindow.setVisible(true);
        
        this.dispose();
        mainWindow.getWindowList().remove(this);
    }
    

    /**
     * Method to update the top label which shows the current player, invoked after clicking a button
     */
    private void updateLabelText() { label.setText("Player: " + model.getActualPlayer().name()); }


    /**
     * Method to close the current game window
    */
    @Override
    protected void proceedAtExit() {

        super.proceedAtExit();
        mainWindow.getWindowList().remove(this);
    }
}