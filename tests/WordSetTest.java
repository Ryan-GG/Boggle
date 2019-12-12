package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.WordSet;

public class WordSetTest {

    private WordSet set;
    public static final int NUMBER = 10;

    @Before
    public void setup() {

        set = new WordSet();
    }

    @Test
    public void testAddNormal() {

        testSize();
    }

    @Test
    public void testAddNull() {

        set.add( null );
        assertEquals( 0, set.size() );
    }

    @Test
    public void testAddEmpty() {

        set.add( "" );
        assertEquals( 0, set.size() );
    }

    @Test
    public void testClear() {

        int startSize;
        int endSize;

        addItems( set, 0, NUMBER );
        startSize = set.size();

        set.clear();
        endSize = set.size();

        assertEquals( NUMBER, startSize );
        assertEquals( 0, endSize );
    }

    @Test
    public void testContains() {

        addItems( set, 0, NUMBER );
        assertEquals( true, set.contains( "" + ( NUMBER - 1 ) ) );
        assertEquals( false, set.contains( "" + ( NUMBER + 2 ) ) );
    }

    @Test
    public void testDifferenceNormal() {

        WordSet other = new WordSet();
        WordSet difference;

        addItems( set, 0, NUMBER );
        addItems( other, NUMBER / 2, NUMBER + NUMBER / 2 );

        difference = set.difference( other );

        assertEquals( NUMBER - NUMBER / 2, difference.size() );
        assertEquals( "0", difference.get( 0 ) );
        assertEquals( "4", difference.get( difference.size() - 1 ) );

    }

    @Test
    public void testDifferenceNoOverlap() {

        WordSet other = new WordSet();
        WordSet difference;

        addItems( set, 0, NUMBER );
        addItems( other, NUMBER, NUMBER * 2 );

        difference = set.difference( other );

        assertEquals( NUMBER, difference.size() );

    }

    @Test
    public void testDifferenceNullSet() {

        WordSet other = null;
        WordSet difference;

        addItems( set, 0, NUMBER );

        difference = set.difference( other );

        assertEquals( NUMBER, difference.size() );

    }

    @Test
    public void testDifferenceEmptySet() {

        WordSet other = new WordSet();
        WordSet difference;

        addItems( set, 0, NUMBER );

        difference = set.difference( other );

        assertEquals( NUMBER, difference.size() );

    }

    @Test
    public void testIntersectionNormal() {

        WordSet other = new WordSet();
        WordSet intersection;

        addItems( set, 0, NUMBER );
        addItems( other, NUMBER / 2, NUMBER * 2 );

        intersection = set.intersection( other );

        assertEquals( NUMBER - NUMBER / 2, intersection.size() );
    }

    @Test
    public void testIntersectionNoOverlap() {

        WordSet other = new WordSet();
        WordSet intersection;

        addItems( set, 0, NUMBER );
        addItems( other, NUMBER, NUMBER * 2 );

        intersection = set.intersection( other );

        assertEquals( 0, intersection.size() );

    }

    @Test
    public void testIntersectionNullSet() {

        WordSet other = null;
        WordSet intersection;

        addItems( set, 0, NUMBER );

        intersection = set.intersection( other );

        assertEquals( 0, intersection.size() );

    }

    @Test
    public void testIntersectionEmptySet() {

        WordSet other = new WordSet();
        WordSet intersection;

        addItems( set, 0, NUMBER );

        intersection = set.intersection( other );

        assertEquals( 0, intersection.size() );

    }

    @Test
    public void testRemoveFound() {

        int endSize;

        addItems( set, 0, NUMBER );
        set.remove( "8" );

        endSize = set.size();

        assertEquals( NUMBER - 1, endSize );
        assertFalse( set.contains( "8" ) );
    }

    @Test
    public void testRemoveNotFound() {

        int endSize;

        addItems( set, 0, NUMBER );
        set.remove( "" + ( NUMBER + 1 ) );

        endSize = set.size();

        assertEquals( NUMBER, endSize );
    }

    @Test
    public void testRemoveNull() {

        int endSize;

        addItems( set, 0, NUMBER );
        set.remove( null );

        endSize = set.size();

        assertEquals( NUMBER, endSize );
    }

    @Test
    public void testRemoveEmptyString() {

        int endSize;

        addItems( set, 0, NUMBER );
        set.remove( "" );

        endSize = set.size();

        assertEquals( NUMBER, endSize );
    }

    @Test
    public void testSize() {

        int startSize;
        int endSize;

        startSize = set.size();

        addItems( set, 0, NUMBER );

        endSize = set.size();

        assertEquals( 0, startSize );
        assertEquals( NUMBER, endSize );
    }

    @Test
    public void testUnionWithOverlap() {

        WordSet other = new WordSet();
        WordSet union;

        addItems( set, 0, NUMBER );
        addItems( other, NUMBER / 2, NUMBER + NUMBER / 2 );

        union = set.union( other );

        assertEquals( NUMBER + NUMBER / 2, union.size() );
    }

    @Test
    public void testUnionNoOverlap() {

        WordSet other = new WordSet();
        WordSet union;

        addItems( set, 0, NUMBER );
        addItems( other, NUMBER, NUMBER * 2 );

        union = set.union( other );

        assertEquals( NUMBER * 2, union.size() );

    }

    @Test
    public void testUnionNullSet() {

        WordSet other = null;
        WordSet union;

        addItems( set, 0, NUMBER );

        union = set.union( other );

        assertEquals( NUMBER, union.size() );

    }

    @Test
    public void testUnionEmptySet() {

        WordSet other = new WordSet();
        WordSet union;

        addItems( set, 0, NUMBER );

        union = set.union( other );

        assertEquals( NUMBER, union.size() );

    }

    @Test
    public void testIterator() {

        int count = 0;

        addItems( set, 0, NUMBER * 2 );
        Iterator< String > setIterator = set.iterator();

        while ( setIterator.hasNext() ) {
            setIterator.next();
            count++;
        }

        assertEquals( "Iterator does not match the WordSet", set.size(),
                        count );
    }

    private void addItems( WordSet whichSet, int start, int end ) {

        for ( int i = start; i < end; i++ )
            whichSet.add( "" + i );

    }

}
