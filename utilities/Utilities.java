package utilities;

/**
 * Static utility methods for the Boggle game.
 * 
 * @author Michael Norton
 * @version PA02 (12 October 2019)
 */
public class Utilities {

    /**
     * Is the target value between the lower and upper bounds (inclusive)?
     * 
     * @param target The target value
     * @param lowerBound The lower bound (inclusive)
     * @param upperBound The upper bound (inclusive)
     * @return true if target is between the upper and lower bounds (inclusive)
     */
    public static boolean isBetween( int target, int lowerBound,
                    int upperBound ) {

        return target >= lowerBound && target <= upperBound;

    } // method isBetween( int, int, int )

    /**
     * Dtermines whether the incoming String is a valid int between the lower
     * and upper bounds given (inclusive).
     *
     * @param test (String)
     * @param lower (int)
     * @param upper (int)
     * @return boolean
     */
    public static boolean isValidInt( String test, int lower, int upper ) {

        boolean valid = false; // assume it won't work
        int testInt = -1; // init to something ridiculous

        if ( test != null ) {
            if ( lower <= upper ) {
                try {
                    testInt = Integer.parseInt( test );

                    if ( isBetween( testInt, lower, upper ) ) {
                        valid = true;
                        
                    } // end if

                } catch ( NumberFormatException e ) { } // do nothing
                
            } // end if
            
        } // end if

        return valid;

    } // method isValidInt

    /**
     * Is this a valid word?
     * 
     * Modification: **MLN PA02 - new for PA2
     * 
     * @param word The word to test
     * @return true if the word is valid (not null & not empty)
     */
    public static boolean isValidWord( String word ) {

        return word != null && word.length() > 0;

    } // method isValidWord( String )

}
