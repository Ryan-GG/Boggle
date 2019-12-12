package model;

import java.util.Iterator;

import utilities.BoggleConstants;

/**
 * ComputerPlayer - child of BogglePlayer.
 * 
 * Modifications: New for PA3
 * 
 * @author Michael Norton
 * @version PA03 (27 October 2019)
 */
public class HumanPlayer extends BogglePlayer implements BoggleConstants {

    /**
     * Default constructor.
     */
    public HumanPlayer() {

        super();

    } // default constructor

    /**
     * Add a word to this player's word list.
     * 
     * Modification: PA02 - added difficulty to parameter and used value to 
     *                      determine when to add to dictionary 
     *               PA03 - moved from BogglePlayer removed difficulty
     * 
     * @param word The word to add
     * @return true if the word was successfully added to this player's word
     *         list
     */
    public boolean add( String word ) {

        return getSet().add( word.toLowerCase() );

    } // method add( String, int )

    /**
     * Build the appropriate Iterator and send it to the validateWords() method.
     */
    @Override
    public void validate() {

        Iterator< String > list = getSet().iterator();

        validateWords( list );

    } // method validate


} // child class HumanPlayer
