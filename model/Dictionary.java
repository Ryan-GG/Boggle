package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import utilities.Utilities;

/**
 * Dictionary - stores the words used in the game of Boggle.
 * 
 * Modification: PA02 - Added iterator()
 *               PA03 - Converted to Singleton
 *                    - Removed deprecated "remove" method
 * 
 * 
 * @author Michael Norton
 * @version PA03 (30 October 2019), PA02 (12 October 2019)
 */
public class Dictionary {

    private static Dictionary dict;
    private TreeSet< String > dictionary;

    /**
     * Default constructor.
     */
    private Dictionary() {

        dictionary = new TreeSet<>();

    } // default constructor

    /**
     * add - add a word to the dictionary.
     * 
     * @param word - the word to add to the dictionary
     * @return true if the word was added
     */
    public boolean add( String word ) {

        boolean isValid = false;

        if ( Utilities.isValidWord( word ) ) {
            isValid = dictionary.add( word.toLowerCase() );
            
        } // end if

        return isValid;

    } // method add

    /**
     * clear - empty the dictionary.
     */
    public void clear() {

        dictionary.clear();

    } // method clear

    /**
     * contains - does the dictionary contain the word given in the parameter.
     * 
     * @param word - the word to check
     * @return true if the word is found in the dictionary
     */
    public boolean contains( String word ) {

        return dictionary.contains( word.toLowerCase() );

    } // method contains

    /**
     * getDictionary - return the dictionary as an ArrayList.
     * 
     * @return an ArrayList containing the words in the dictionary
     */
    public ArrayList< String > getDictionary() {

        ArrayList< String > list = new ArrayList< String >();

        Iterator< String > iterator = iterator();

        while ( iterator.hasNext() ) {
            list.add( iterator.next() );
            
        } // end while

        return list;

    } // method getDictionary

    /**
     * Return an iterator for the Dictionary.
     * 
     * Modification: **MLN PA02 - added for PA2
     * 
     * @return an Iterator for the Dictionary
     */
    public Iterator< String > iterator() {

        return dictionary.iterator();

    } // method iterator()

    /**
     * size - return the size of the dictionary.
     * 
     * @return the size of the dictionary
     */
    public int size() {

        return dictionary.size();

    } // method size

    /******************************* Static Methods ***********************/

    /**
     * getDictionary - Singleton method to ensure that there is never more than
     * a single Dictionary object.
     * 
     * ** Added for PA3
     * 
     * @return the one and only Dictionary
     */
    public static Dictionary getBoggleDictionary() {

        if ( dict == null ) {
            dict = new Dictionary();
            
        } // end if

        return dict;

    }
} // class Dictionary
