package model;

import java.util.Iterator;

/**
 * ComputerPlayer - child of BogglePlayer.
 * 
 * Modifications: New for PA3
 * 
 * @author Michael Norton
 * @version PA03 (27 October 2019)
 */
public class ComputerPlayer extends BogglePlayer {

    /**
     * Default constructor.
     */
    public ComputerPlayer() {

        super();

    } // default constructor

    /**
     * Build the appropriate Iterator and send it to the validateWords() method.
     */
    @Override
    public void validate() {

        Dictionary dictionary = Dictionary.getBoggleDictionary();
        Iterator< String > list = dictionary.iterator();

        validateWords( list );

    } // method validate

} // child class ComputerPlayer
