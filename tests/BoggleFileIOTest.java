package tests;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import view.file.BoggleFileIO;

public class BoggleFileIOTest {

    private BoggleFileIO dictionaryIO;
    private File dictFile;

    @Before
    public void setup() {

        dictionaryIO = new BoggleFileIO( "MyDictionary.txt" );
        dictFile = new File( "MyDictionary.txt" );

    }

    @Test
    public void testClose() throws IOException {

        testReadLine();
    }

    @Test
    public void testOpen() throws IOException {

        dictFile.delete(); // remove the file if it exists

        boolean noFileToRead = dictionaryIO.open( BoggleFileIO.READER );
        dictionaryIO.close( BoggleFileIO.READER );

        dictionaryIO.open( BoggleFileIO.WRITER );
        dictionaryIO.close( BoggleFileIO.WRITER );
        boolean fileToRead = dictionaryIO.open( BoggleFileIO.READER );

        assertFalse( noFileToRead );
        assertTrue( fileToRead );

    }

    @Test
    public void testReadLine() throws IOException {

        String line1;
        String line2;

        dictionaryIO.open( BoggleFileIO.WRITER );
        dictionaryIO.write( "hello" );
        dictionaryIO.write( "there" );
        dictionaryIO.close( BoggleFileIO.WRITER );

        dictionaryIO.open( BoggleFileIO.READER );
        line1 = dictionaryIO.readLine();
        line2 = dictionaryIO.readLine();

        assertEquals( "hello", line1 );
        assertEquals( "there", line2 );
    }

    @Test
    public void testWrite() throws IOException {

        testReadLine();
    }

}
