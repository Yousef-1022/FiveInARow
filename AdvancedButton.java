import javax.swing.JButton;


/**
 * Regular JButton but with an additional row,column elements
 */
public class AdvancedButton {

    public JButton button = new JButton();
    public int row;
    public int col;

    public AdvancedButton (JButton button,int row, int col) {
        
        this.button = button;
        this.row = row;
        this.col = col;
    }
}