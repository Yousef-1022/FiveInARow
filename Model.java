import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Backend class simulation of the game, represents the Matrix and the current player
 */
public class Model {


    private int size;
    private Player actualPlayer;
    private Pair<Boolean,Player> gameResult;
    public Player[][] table;


    public Model(int size) {

        this.size = size;
        actualPlayer = Player.X;
        this.gameResult = new Pair<Boolean,Player>(false,Player.NOBODY);

        table = new Player[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                table[i][j] = Player.NOBODY;
            }
        }
    }


    /**
     * Modifies the board accordingly after a player steps
     * @param row
     * @param column
     * @return Player who stepped last
     */
    public Player step(int row, int column) {

        if (table[row][column] != Player.NOBODY) {
            return table[row][column];
        }

        table[row][column] = actualPlayer;

        if (actualPlayer == Player.X) {
            actualPlayer = Player.O;
        } else {
            actualPlayer = Player.X;
        }


        //Code size can be significantly reduced for each size(3-4-5) checking. However, keeping as it is allows easier testing
        checkOnRow(table[row][column], row, column);
        checkOnColumn(table[row][column], row, column);
        checkOnDiagonalBack(table[row][column], row, column);
        checkOnDiagonalFront(table[row][column], row, column);
        return table[row][column];
    }


    /**
     * Indicates a random tile owned by the given players to be removed
     * @param thePlayer
     * @return a Pair of coordinates
     */
    public Pair<Integer,Integer> indicateToRemoveRandom (Player thePlayer) {


        List <Pair<Integer,Integer>> listOfOwnedTiles = new ArrayList<Pair<Integer,Integer>>();
        for (int i=0; i<table.length; i++) {
            for (int j=0; j<table[i].length; j++) {
                if (table[i][j] == thePlayer) {
                    listOfOwnedTiles.add(new Pair<Integer,Integer>(i,j));
                }
            }
        }   


        Random randomizer = new Random();
        int randomNum = randomizer.nextInt(((listOfOwnedTiles.size()-1) - 0) + 1) + 0;
        
        return (listOfOwnedTiles.get(randomNum));
    }



    /**
     * Checks if there are either 4/3 similar shapes on the row itself at the tile, also marks a Winner if 5 similar shapes are detected
     * @param p (is The player in Question -> used to modify gameResult)
     * @param row
     * @param col
     */
    public void checkOnRow (Player p, int row, int col) {

        List <Integer> tmp = new ArrayList<Integer>();
    

        // right counts the tile itself
        for (int right = col; right<table[0].length; right++) {
            if (table[row][right] == p) { tmp.add(1); }
            else break;
        }
        for (int left = col-1; left>=0; left--) {
            if (table[row][left] == p) { tmp.add(1); }
            else break;
        }

        
        if (tmp.size() == 3) {
            
            Pair <Integer,Integer> ij = indicateToRemoveRandom(p);
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
            //System.out.println("ROW-3 detected after placement, removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
        }
        else if (tmp.size() == 4) {
            
            Pair <Integer,Integer> ij = indicateToRemoveRandom(p);
            //System.out.println("ROW-4 detected after placement, (1) removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+") ");
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
            ij = indicateToRemoveRandom(p);
            //System.out.println("ROW-4 detected after placement, (2) removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+") ");
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
        } 
        else if (tmp.size() == 5) {

            this.gameResult.setFirst(true);
            this.gameResult.setSecond(p);
            //System.out.println("BRUH row won -> ("+row+","+col+") -> ("+this.gameResult.getFirst()+","+this.gameResult.getSecond()+")");
        }
        else {
            
            if(this.gameResult.getFirst()) {
                this.gameResult.setSecond(p);
            }
            else {
                this.gameResult.setFirst(false);
                this.gameResult.setSecond(p);
            }
            //System.out.println("\nRow At: "+tmp.size() + "-> ("+row+","+col+") -> ("+this.gameResult.getFirst()+","+this.gameResult.getSecond()+") ");
        }
    }
    
   
    /**
     * Checks if there are either 4/3 similar shapes on the column itself at the tile, also marks a Winner if 5 similar shapes are detected
     * @param p (is The player in Question -> used to modify gameResult)
     * @param row
     * @param col
     */
    public void checkOnColumn (Player p, int row, int col) {

        List <Integer> tmp = new ArrayList<Integer>();


        //down counts the tile itself
        for (int down = row; down<table.length; down++) {
            if (table[down][col] == p) { tmp.add(1); }
            else break;
        }
        for (int up = row-1; up >=0; up--) {
            if (table[up][col] == p) { tmp.add(1); }
            else break;
        }


        if (tmp.size() == 3) {
            
            Pair <Integer,Integer> ij = indicateToRemoveRandom(p);
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
            //System.out.println("COL-3 detected after placement, removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
        }
        else if (tmp.size() == 4) {
            
            Pair <Integer,Integer> ij = indicateToRemoveRandom(p);
            //System.out.println("COL-4 detected after placement, (1) removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
            ij = indicateToRemoveRandom(p);
            //System.out.println("COL-4 detected after placement, (2) removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
        } 
        else if (tmp.size() == 5) {
            
            this.gameResult.setFirst(true);
            this.gameResult.setSecond(p);
            //System.out.println("BRUH col won -> ("+row+","+col+") -> ("+this.gameResult.getFirst()+","+this.gameResult.getSecond()+") ");
        }
        else {

            if(this.gameResult.getFirst()) {
                this.gameResult.setSecond(p);
            }
            else {
                this.gameResult.setFirst(false);
                this.gameResult.setSecond(p);
            }
            //System.out.println("\nCol At: "+tmp.size()+" -> ("+row+","+col+") -> ("+this.gameResult.getFirst()+","+this.gameResult.getSecond()+") ");
        }
    }
  



    /**
     * Checks if there are either 4/3 similar shapes on the back " \ " diagonal itself of the tile, also marks a Winner if 5 similar shapes are detected
     * @param p (is The player in Question -> used to modify gameResult)
     * @param row
     * @param col
     */
    public void checkOnDiagonalBack (Player p, int row, int col) {

        List <Integer> tmp = new ArrayList<Integer>();

        //NW - counts the tile itself
        for (int i=row, j=col; (i>=0 && j>=0); i--,j--) {
            
            if (table[i][j] == p) { tmp.add(i); }
            else break;
        }
        //SE
        for (int i=row+1, j=col+1; (i<table.length && j<table[i].length); i++,j++) {
            
            if (table[i][j] == p) { tmp.add(i); }
            else break;
        }
        
        
        if (tmp.size() == 3) {
            
            Pair <Integer,Integer> ij = indicateToRemoveRandom(p);
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
            //System.out.println("DiagBack-3 detected after placement, removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
        }
        else if (tmp.size() == 4) {
            
            Pair <Integer,Integer> ij = indicateToRemoveRandom(p);
            //System.out.println("DiagBack-4 detected after placement, (1) removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
            ij = indicateToRemoveRandom(p);
            //System.out.println("DiagBack-4 detected after placement, (2) removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
        } 
        else if (tmp.size() == 5) {
            
            this.gameResult.setFirst(true);
            this.gameResult.setSecond(p);
            //System.out.println("BRUH DiagBack won -> ("+row+","+col+") -> ("+this.gameResult.getFirst()+","+this.gameResult.getSecond()+") ");
        }
        else {

            if(this.gameResult.getFirst()) {
                this.gameResult.setSecond(p);
            }
            else {
                this.gameResult.setFirst(false);
                this.gameResult.setSecond(p);
            }
            //System.out.println("\nDiagBack At: "+tmp.size()+" -> ("+row+","+col+") -> ("+this.gameResult.getFirst()+","+this.gameResult.getSecond()+") ");
        }

    }




    /**
     * Checks if there are either 4/3 similar shapes on the front " / " diagonal itself of the tile, also marks a Winner if 5 similar shapes are detected
     * @param p (is The player in Question -> used to modify gameResult)
     * @param row
     * @param col
     */
    public void checkOnDiagonalFront (Player p, int row, int col) {

        List <Integer> tmp = new ArrayList<Integer>();

        //SW - counts the tile itself
        for (int i=row, j=col; (i<table.length && j>=0); i++,j--) {
            
            if (table[i][j] == p) { tmp.add(i); }
            else break;
        }
        //NE
        for (int i=row-1, j=col+1; (i>=0 && j<table[i].length); i--,j++) {
            
            if (table[i][j] == p) { tmp.add(i); }
            else break;
        }



        if (tmp.size() == 3) {
            
            Pair <Integer,Integer> ij = indicateToRemoveRandom(p);
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
            //System.out.println("DiagFront-3 detected after placement, removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
        }
        else if (tmp.size() == 4) {
            
            Pair <Integer,Integer> ij = indicateToRemoveRandom(p);
            //System.out.println("DiagFront-4 detected after placement, (1) removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
            ij = indicateToRemoveRandom(p);
            //System.out.println("DiagFront-4 detected after placement, (2) removing ("+ij.getFirst()+","+ij.getSecond()+") -> ("+row+","+col+")");
            table[ij.getFirst()][ij.getSecond()] = Player.NOBODY;
        } 
        else if (tmp.size() == 5) {
            
            this.gameResult.setFirst(true);
            this.gameResult.setSecond(p);
            //System.out.println("BRUH DiagFront won -> ("+row+","+col+") -> ("+this.gameResult.getFirst()+","+this.gameResult.getSecond()+") ");
        }
        else {

            if(this.gameResult.getFirst()) {
                this.gameResult.setSecond(p);
            }
            else {
                this.gameResult.setFirst(false);
                this.gameResult.setSecond(p);
            }
            //System.out.println("\nDiagFront At: "+tmp.size()+" -> ("+row+","+col+") -> ("+this.gameResult.getFirst()+","+this.gameResult.getSecond()+") ");
        }
    }

    
    public Player findWinner() {

        //System.out.println("FindWinner: "+this.gameResult.getFirst()+" "+this.gameResult.getSecond());
        if (this.gameResult.getFirst()) {
            return this.gameResult.getSecond();
        }
        else {
            return Player.NOBODY;   
        }
    }

    public Player getActualPlayer() { return actualPlayer; }
}