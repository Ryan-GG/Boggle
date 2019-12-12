import java.io.IOException;

import view.gui.BoggleGui;

/**
 * Boggle - starts the Boggle game.
 *
 * @author Michael Norton, Ryan Gross
 * @version PA04( 06, December 2019),PA01 (23 September 2019)
 */
public class Boggle {

    /**
     * Main method.
     * 
     * @param args The String array
     * @throws IOException The IOException
     */
    public static void main( String[] args ) throws IOException {

        new BoggleGui();
    }
}
