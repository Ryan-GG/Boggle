package model;

import java.util.Iterator;

import utilities.BoggleConstants;

/**
 * BogglePlayer - manages the details for a single Boggle player.
 * 
 * Modifications: PA02 - Added parameter dictionary to constructor 
 *                     - Added methods computeScore(), getRoundScore(), 
 *                       getTotalScore(), remove(), reset() 
 *                     - Added method getRejectSet(): not in SRS 
 *                     - Changed method getList() to getSet()
 *                     - Modified add() to add to dictionary according to  
 *                       supplied difficulty level
 *                PA3 Prep: Used Singleton version of Dictionary
 *                PA03 - Refactored method comments to show modifications in a
 *                       consistent fashion
 *                     - Made class abstract
 *                     - Removed dictionary from constructor parameter - use 
 *                       Singleton instead.
 *                     - Added new sets as instance variables and instantiated 
 *                       in constructor.
 *                     - Added methods get...Set() for new WordSets
 *                     - Added resetGame method to set totalScore = 0.
 *                     - Refactored computeScore
 *                     - Modified reset() to clear all WordSets 
 * 
 * @author Michael Norton
 * @version PA03 (27 October 2019), PA02 (9 October 2019), 
 *          PA01 (20 September 2019)
 */
public abstract class BogglePlayer implements BoggleConstants {

    private int roundScore;
    private int totalScore;

    private BoggleBoard board;
    // private Dictionary dictionary;
    private WordSet invalidSet;
    private WordSet rejectSet;
    private WordSet set;
    private WordSet uniqueSet;
    private WordSet validSet;

    /**
     * Explicit value constructor.
     * 
     * Modification: PA02 - Added the dictionary to the parameter PA03 - Removed
     * dictionary from parameter - use Singleton - Added new sets for
     * instantiation
     */
    public BogglePlayer() {

        roundScore = 0;
        totalScore = 0;

        rejectSet = new WordSet(); // new for PA2
        set = new WordSet();
        uniqueSet = new WordSet(); // new for PA3
        validSet = new WordSet(); // new for PA3
        invalidSet = new WordSet(); // new for PA3 **MLN
//        dictionary = Dictionary.getBoggleDictionary(); // new for PA3
        board = BoggleBoard.getBoggleBoard(); // new for PA3

    } // explicit value constructor

    /**
     * Computes the round and total scores for the player.
     * 
     * Modification: PA02 - new for PA02 PA03 - added default case & removed if
     * statement for length > 7 - switched set to uniqueSet
     */
    public void computeScore() {

        int points = 0;

        for ( int i = 0; i < uniqueSet.size(); i++ ) {
            switch ( uniqueSet.get( i ).length() ) {
                case 0:
                case 1:
                case 2:
                    points = 0;
                    break;
                case 3:
                case 4:
                    points = 1;
                    break;
                case 5:
                    points = 2;
                    break;
                case 6:
                    points = 3;
                    break;
                case 7:
                    points = 5;
                    break;
                default:
                    points = 11;
            }

            roundScore += points;
            totalScore += points;

        } // end for

    } // method computeScore()

    /**
     * Return the set of invalid words.
     * 
     * Modification: PA03 - new for PA03
     * 
     * @return the set of invalid words (not on the board)
     */
    public WordSet getInvalidSet() {

        return invalidSet;

    } // method getInvalidSet()

    /**
     * Return the player's set of rejected words.
     * 
     * Modification: PA02 - New for PA2 (not in SRS but needed to allow
     * controller access to this)
     * 
     * @return a WordSet containing this player's rejected words
     */
    public WordSet getRejectSet() {

        return rejectSet;

    } // method getRejectSet

    /**
     * Return the round score.
     * 
     * Modification: PA02 - New for PA2
     * 
     * @return the score for the current round
     */
    public int getRoundScore() {

        return roundScore;

    } // method getRoundScore()

    /**
     * Return this player's word list.
     * 
     * Modification: PA2 - changed WordList to WordSet and changed name of
     * method to getSet
     * 
     * @return the word list for this player
     */
    public WordSet getSet() {

        return set;

    } // method getSet()

    /**
     * Return the total score.
     * 
     * Modification: PA02 - New for PA2
     * 
     * @return the total
     */
    public int getTotalScore() {

        return totalScore;

    } // method getTotalScore()

    /**
     * Return the set of unique words for this player.
     * 
     * Modification: PA03 - new for PA03
     * 
     * @return the set of unique words for the player
     */
    public WordSet getUniqueSet() {

        return uniqueSet;

    } // method getUniqueSet()

    /**
     * Return the set of valid words (words on the board) - should be a subset.
     * of set
     * 
     * Modification: PA03 - new for PA3
     * 
     * @return the set of valid words
     */
    public WordSet getValidSet() {

        return validSet;

    } // method validSet()

    /**
     * Return a word from this player's word list.
     * 
     * @param position the position in the list for the word to retrieve
     * @return a word from the word list
     */
    public String getWord( int position ) {

        return set.get( position );

    } // method getWord( int )

    /**
     * Clear the player's word sets and set the round score to 0.
     * 
     * Modification: PA02 - new for PA2 PA03 - added additional WordSets to
     * clear
     */
    public void reset() {

        set.clear();
        rejectSet.clear();
        validSet.clear();
        invalidSet.clear();
        uniqueSet.clear();
        roundScore = 0;

    } // method reset()

    /**
     * Reset Boggle for the next game.
     * 
     * Modification: PA03 - new for PA3
     */
    public void resetGame() {

        reset();
        totalScore = 0;

    } // method resetGame()

    /**
     * setUniqueSet - Set the unique StringSet to that sent via the parameter.
     *
     * Modification: PA03 - new for PA3
     * 
     * @param in The new unique StringSet
     */
    public void setUniqueSet( WordSet in ) {

        if ( in != null ) {
            uniqueSet = in;
            
        }

    } // method setUniqueSet

    /**
     * Validate the words in the iterator & remove any invalid words from the
     * dictionary.
     * 
     * Modification: PA03 - new for PA3
     * 
     * @param list the list of words
     */
    protected void validateWords( Iterator< String > list ) {

        String word = null;

        // make sure sets are empty
        validSet.clear();
        invalidSet.clear();

        while ( list.hasNext() ) {
            word = list.next();

            if ( board.hasWord( word ) ) {
                validSet.add( word );
                
            // no need for invalidSet for the computer player  
            } else if ( this instanceof HumanPlayer ) { 
                invalidSet.add( word );  
            }
            
        } // end while

    } // method validateWords

    /**
     * Defined in child classes to return the proper Iterator to the
     * validateWords() method.
     * 
     * Modification: PA03 - new for PA3
     */
    public abstract void validate();

} // abstract class BogglePlayer
