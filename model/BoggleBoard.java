package model;

import java.util.Random;
import utilities.Utilities;

/**
 * BoggleBoard - this class manages the Boggle board
 * 
 * Modifications: PA03 - Converted to Singleton to make PA03 easier
 *                     - Added hasWord() methods to check if word sent is on the
 *                       board or not.
 * 
 * @author Michael Norton
 * @version PA03 (20 October 2019)
 * . PA02 (4 October 2019), PA01 (21 September 2019)
 */
public class BoggleBoard {

    public static final String LETTERS = "JKQYZ" 
                    + "BBCCFFGGMMPPVV"
                    + "DDDUUUWWWXXX" 
                    + "HHHHHLLLLLRRRRR"
                    + "AAAAAAIIIIIINNNNNNSSSSSSOOOOOO" 
                    + "EEEEEEEEEETTTTTTTTTT";

    private static BoggleBoard brd;

    private char[][] board;

    /**
     * Default constructor.
     */
    private BoggleBoard() {

        board = new char[ 4 ][ 4 ];
        mix();

    } // default constructor

    /**
     * Explicit value constructor (for testing only).
     * 
     * @param staticBoard - 16 character String for the board
     */
    private BoggleBoard( String staticBoard ) {

        this(); // call default constructor--failsafe in case staticBoard is
                // too short

        if ( staticBoard.length() >= 16 ) {
            int counter = 0;

            for ( int row = 0; row < 4; row++ ) {
                for ( int col = 0; col < 4; col++ ) {
                    board[ row ][ col ] = staticBoard.charAt( counter );
                    counter++;

                } // end for
                
            } // end for

        } // end if

    } // explicit value constructor

    /**
     * getCell - return the char at the specified call. Return (char)0 if
     * invalid indexes
     * 
     * @param row The row to get
     * @param col The column to get
     * @return the char at the specified cell
     */
    public char getCell( int row, int col ) {

        char charToGet = (char) 0;

        if ( row >= 0 && row < 4 ) {
            if ( col >= 0 && col < 4 ) {
                charToGet = board[ row ][ col ];
                
            } // end if
            
        } // end if

        return charToGet;

    } // method getCell( int, int )

    /**
     * Is the word received on the board?
     * 
     * Modification: PA03 - new for PA03
     * 
     * @param word the word to check
     * @return true if present, false if not
     */
    public boolean hasWord( String word ) {

        boolean wordFound = false;

        if ( word != null ) {
            boolean[][] check = new boolean[ 4 ][ 4 ];
            int row = 0;
            int col = 0;

            // init check array to false
            for ( int i = 0; i < 4; i++ ) {
                for ( int j = 0; j < 4; j++ ) {
                    check[ i ][ j ] = false;
                    
                } // end for
                
            } // end for

            // loop through the board to see if the word sent is on the board
            while ( row < 4 && !wordFound ) {
                while ( col < 4 && !wordFound ) {
                    wordFound = hasWord( word, row, col, check );
                    col++;

                } // end while

                row++;
                col = 0; // reset column

            } // end while

        } // end if

        return wordFound;

    } // method has word

    /**
     * Randomly assigns chars to the board based on the specified probabilities.
     */
    public void mix() {

        Random random = new Random();

        for ( int row = 0; row < 4; row++ ) {
            for ( int col = 0; col < 4; col++ ) {
                board[ row ][ col ] = LETTERS.charAt( random.nextInt( 96 ) );

            } // end for
            
        } // end for
        
    } // method mix()

    /**
     * Return the Boggle board as a String.
     * 
     * @return the board as a String
     */
    public String toString() {

        String boardAsString = "";

        for ( int row = 0; row < 4; row++ ) {
            for ( int col = 0; col < 4; col++ ) {
                boardAsString += board[ row ][ col ] + " ";
                if ( col == 3 ) {
                    boardAsString = boardAsString.trim() + "\n";
                    
                } // end if

            } // end for
            
        } // end for

        return boardAsString;

    } // method toString()

    /**
     * Recursive version of hasWord (overloaded).
     *
     * Modification: PA03 - new for PA3
     *
     * @param word the word to find
     * @param row the row to start searching
     * @param col the column to start searching
     * @param check the shadow array for the board
     * 
     * @return true if the word is found, false otherwise
     */
    private boolean hasWord( String word, int row, int col,
                    boolean[][] check ) {

        boolean wordFound = false;
        String wordToCheck;

        if ( Utilities.isBetween( row, 0, 3 )
                        && Utilities.isBetween( col, 0, 3 ) ) {
            if ( word != null ) {
                wordToCheck = word.toUpperCase(); // force to upper case 
                                                  // for comparison

                // check to see if the first character matches the tile on the
                // board - Note: this is base case # 1
                if ( wordToCheck.charAt( 0 ) == getCell( row, col ) ) {
                    // make sure we have not used this tile already
                    // Note: this is base case #2
                    if ( !check[ row ][ col ] ) {
                        // make sure we don't use this tile again
                        check[ row ][ col ] = true;

                        // if this is the last letter, then we have found the
                        // word - Note: this is base case #3
                        if ( wordToCheck.length() == 1 ) {
                            wordFound = true;

                        } else {

                            // otherwise, we need to check all of the columns
                            // around
                            // us until we either find the word or determine
                            // it's
                            // not here - Note: this is the recursive case

                            int checkRow = 0;
                            int checkCol = 0;
                            int startRow = row - 1;
                            int startCol = col - 1;

                            // loop through the tiles around the one we're
                            // looking at - Note: we'll skip the current one
                            // with the check above.
                            while ( checkRow < 3 && !wordFound ) {
                                while ( checkCol < 3 && !wordFound ) {
                                    wordFound = 
                                           hasWord( wordToCheck.substring( 1 ),
                                                    startRow + checkRow,
                                                    startCol + checkCol,
                                                    check );

                                    checkCol++;

                                } // end while

                                checkRow++;
                                checkCol = 0; // reset column

                            } // end while

                            if ( !wordFound ) { // back out the shadow
                                check[ row ][ col ] = false; // variable if word
                                                             // not found from
                            } // end if // this cell

                        } // end else

                    } // end if

                } // end if

            } // end if

        } // end if

        return wordFound;

    } // method hasWord (overloaded, recursive version)

    /******************************* Static Methods ***********************/

    /**
     * clearBoard = set the Board to null (needed for tests).
     * 
     * Modification: PA03 - Added for PA3 (to enable better targeted tests)
     */
    public static void clearBoard() {

        brd = null;
    }

    /**
     * getBoggleBoard - Singleton method to ensure that there is never more than
     * a single BoggleBoard object.
     * 
     * Modification: PA02 - Added for PA2 (to make PA3 easier)
     * 
     * @return the one and only Dictionary
     */
    public static BoggleBoard getBoggleBoard() {

        if ( brd == null ) {
            brd = new BoggleBoard();
            
        } // end if

        return brd;

    } // static Singleton method

    /**
     * getStaticBoggleBoard - Singleton method to ensure that there is never
     * more than a single BoggleBoard object.
     * 
     * Modification: PA02 - Added for PA2 (to make PA3 easier)
     * 
     * @param letters The letters to set for the BoggleBoard
     * @return the one and only Dictionary
     */
    public static BoggleBoard getStaticBoggleBoard( String letters ) {

        if ( brd == null ) {
            brd = new BoggleBoard( letters );
            
        } // end if

        return brd;

    } // static Singleton method for overloaded constructor

} // class BoggleBoard
