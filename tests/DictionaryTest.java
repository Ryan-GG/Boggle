package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.Dictionary;

public class DictionaryTest {

    private Dictionary dictionary;
    public static final int NUMBER = 10;

    @Before
    public void setup() {

        dictionary = Dictionary.getBoggleDictionary();
        dictionary.clear();
    }

    @Test
    public void testAddNormal() {

        testSize();
    }

    @Test
    public void testAddNull() {

        dictionary.add( null );
        assertEquals( 0, dictionary.size() );
    }

    @Test
    public void testAddEmpty() {

        dictionary.add( "" );
        assertEquals( 0, dictionary.size() );
    }

    @Test
    public void testClear() {

        int startSize;
        int endSize;

        addItems( NUMBER );
        startSize = dictionary.size();

        dictionary.clear();
        endSize = dictionary.size();

        assertEquals( NUMBER, startSize );
        assertEquals( 0, endSize );
    }

    @Test
    public void testContains() {

        final int NUMBER = 10;

        addItems( NUMBER );
        assertEquals( true, dictionary.contains( "" + ( NUMBER - 1 ) ) );
        assertEquals( false, dictionary.contains( "" + ( NUMBER + 2 ) ) );
    }

    @Test
    public void testGetEmptyDictionary() {

        ArrayList< String > dict;

        dict = dictionary.getDictionary();

        assertEquals( "Empty Dictionary should have 0 items", 0, dict.size() );

    }

    @Test
    public void testGetFullDictionary() {

        ArrayList< String > dict;
        addItems( NUMBER );

        dict = dictionary.getDictionary();

        assertEquals( dictionary.size(), dict.size() );

        for ( int i = 0; i < dict.size(); i++ )
            assertTrue( "ArrayList not same as Dictionary",
                            dictionary.contains( dict.get( i ) ) );
    }

    @Test
    public void testSize() {

        int startSize;
        int endSize;
        final int NUMBER = 10;

        startSize = dictionary.size();

        addItems( NUMBER );

        endSize = dictionary.size();

        assertEquals( "Start size of the Dictionary should be 0", 0,
                        startSize );
        assertEquals( "Added 10 items, size should be 10", NUMBER, endSize );
    }

    @Test
    public void testIterator() {

        int count = 0;

        addItems( NUMBER * 2 );
        Iterator< String > dictIterator = dictionary.iterator();

        while ( dictIterator.hasNext() ) {
            dictIterator.next();
            count++;
        }

        assertEquals( "Iterator does not match the Dictionary", dictionary.size(),
                        count );
    }

    private void addItems( int number ) {

        for ( int i = 0; i < number; i++ )
            dictionary.add( "" + i );

    }

}
