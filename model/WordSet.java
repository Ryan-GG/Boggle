package model;

import java.util.ArrayList;
import java.util.Iterator;

import utilities.Utilities;

/**
 * WordSet - manages the users lists of words.
 * 
 * Modifications: PA02 - based on PA01 WordList class but with ArrayList and
 *                       functioning as set 
 *                     - Added iterator(), difference(), intersection() and
 *                       union() methods
 *               PA04 - getArray method        
 * 
 * @author Michael L. Norton, Ryan Gross
 * @version  PA04( 06 December 2019), PA02 (11 October 2019)
 */
public class WordSet {

    private ArrayList< String > set;

    /**
     * Default constructor.
     */
    public WordSet() {

        set = new ArrayList<>();

    } // default constructor

    /**
     * Add a word to the word set
     * 
     * Modification: PA02 - from old WordList: refactored valid word condition
     * and enforce unique constraint, forced lower case.
     * 
     * @param word the word to add
     * @return true if added successfully
     */
    public boolean add( String word ) {

        boolean isValid = false;

        if ( Utilities.isValidWord( word ) ) {
            if ( !contains( word ) ) {
                set.add( word.toLowerCase() );
                isValid = true;

            } // end if
            
        } // end if

        return isValid;

    } // method add( String )

    /**
     * Clear the word set.
     */
    public void clear() {

        set.clear();

    } // method clear

    /**
     * Does the word set contain the word sent through the parameter.
     * 
     * Modification: PA02 - forced lower case
     * 
     * @param word The word to search
     * @return true if the word is found, false otherwise
     */
    public boolean contains( String word ) {

        return set.contains( word.toLowerCase() );

    } // method contains( String )

    /**
     * Return the unique items in the current set.
     * 
     * Modification - new for PA2
     * 
     * @param other - the other set
     * @return a WordSet containing the difference between the 2 sets
     */
    public WordSet difference( WordSet other ) {

        WordSet difference = new WordSet();

        if ( other != null ) {
            for ( String word : set ) {
                if ( !other.contains( word ) ) {
                    difference.add( word );
                    
                } // end if
                
            } // end for

        } else {
            for ( String word : set ) {
                difference.add( word );
                
            } // end for
                
        } // end else

        return difference;

    } // method difference( WordSet )

    /**
     * Get a word from the word list.
     * 
     * Modification: PA02 - from old WordList: refactored valid position
     * condition
     * 
     * @param position which word to get
     * @return the word at the specified position
     */
    public String get( int position ) {

        String word = null;

        if ( Utilities.isBetween( position, 0, size() - 1 ) ) {
            word = set.get( position );
            
        } // end if

        return word;

    } // method get( int )

    /**
     * Return the intersection of the 2 sets.
     * 
     * Modification - new for PA2
     * 
     * @param other - the other set
     * @return the intersection of the 2 sets
     */
    public WordSet intersection( WordSet other ) {

        WordSet intersection = new WordSet();

        if ( other != null ) {
            for ( String word : set ) {
                if ( other.contains( word ) ) {
                    intersection.add( word );
                    
                } // end if
                
            } // end for
            
        } // end if

        return intersection;

    } // method intersection( WordSet )

    /**
     * Return an iterator for this set.
     * 
     * Modification: PA02 - new for PA2
     * 
     * @return an Iterator for the WordSet
     */
    public Iterator< String > iterator() {

        return set.iterator();

    } // method iterator()

    /**
     * Remove the specified word from the WordSet.
     * 
     * Modification: PA02 - forced lower case & checked for null
     * 
     * @param word - the word to remove
     * @return true if the word was removed
     */
    public boolean remove( String word ) {

        boolean success = false;

        if ( Utilities.isValidWord( word ) ) {
            success = set.remove( word.toLowerCase() );
            
        } // end if

        return success;

    } // method remove( String )

    /**
     * The size of the word list.
     * 
     * Modification: PA02 - from old WordList
     * 
     * @return the size of the word list
     */
    public int size() {

        return set.size();

    } // method size()

    /**
     * The word list as a single string with space separators.
     * 
     * Modification: PA02 - from old WordList
     * 
     * @return the word list as a String
     */
    public String toString() {
        
        String words = "";

        for ( String word : set ) {
            words = word + " ";
            
        } // end for

        return words.trim();

    } // method toString()
    
    /**.
     * getArray - returns wordset as an array of strings
     * @return String[] - words in wordset as array
     */
    public String[] getArray() {
        Object[] objArr = set.toArray();
        String[] words = new String[objArr.length];
        
        for ( int i = 0; i < objArr.length; i++ ) {
            words[i] = objArr[i].toString();
        }
        return words;
    }
     

    /**
     * Return the union of the 2 sets.
     * 
     * Modification - new for PA2
     * 
     * @param other - the other set
     * @return the union of the 2 sets
     */
    public WordSet union( WordSet other ) {

        WordSet union = new WordSet();

        for ( String word : set ) {
            union.add( word );
            
            
        } // end for

        if ( other != null ) {
            for ( int i = 0; i < other.size(); i++ ) {
                union.add( other.get( i ) );

            } // end for
            
        } // end if
        return union;

    } // method union( WordSet other )

} // class WordSet
