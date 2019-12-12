package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import model.BoggleBoard;
import model.BoggleGame;
import model.BogglePlayer;
import model.Dictionary;
import model.HumanPlayer;
import model.WordSet;
import utilities.BoggleConstants;
import utilities.EggTimer;
import utilities.TickListener;
import utilities.Utilities;
import view.file.BoggleFileIO;
import view.gui.BoggleGui;


/**.
 * GuiListener - Listener for button actions
 * 
 * Acknowledgements: I acknowledge that I have neither given nor
 * received assistance for this assignment except as noted
 * 
 * @author Ryan Gross
 * @version 12/6/2019
 */
public class GuiListener implements ActionListener,
    WindowListener, BoggleConstants, TickListener {

    private BoggleGui gui;
    private BoggleBoard board;
    private BoggleFileIO fileIO;
    private BoggleGame game;
    private EggTimer timer;
    private int totalReject = 0;
    
    /**.
     * guiListener - Constructor
     * 
     * @throws IOException - io excpetion
     */
    
    public GuiListener() throws IOException {
        board = BoggleBoard.getBoggleBoard();
        fileIO = new BoggleFileIO();
        game = new BoggleGame();
    }
    
    
    /**.
     * setLabelClass - links with BoggleGui
     * 
     * @param gui - BoggleGui
     */
    
    public void setLabelClass( BoggleGui gui ) {
        this.gui = gui;
    }
    
    
    /**.
     * windowOpened- when the window is opened
     * 
     * @param e - Windowevent
     */
    
    @Override
    public void windowOpened( WindowEvent e ) {
        try {
            readFile(); // Reads File
        } catch ( IOException e1 ) {
            
            e1.printStackTrace();
        }
        
    }

    /**.
     * windowClosing - when window is closed
     * 
     * @param e - Windowevent
     */
    
    @Override
    public void windowClosing( WindowEvent e ) {
        try {
            closingMethod(); //Writes dict and closes boggle
        } catch ( IOException e1 ) {
            
            e1.printStackTrace();
        }
        
    }

    /**.
     * windowClosed - not in use
     * 
     * @param e - Windowevent
     */
    @Override
    public void windowClosed( WindowEvent e ) {
        // TODO Auto-generated method stub
        
    }

    /**.
     * windowIconified - not in use
     * 
     * @param e - Windowevent
     */
    @Override
    public void windowIconified( WindowEvent e ) {
        // TODO Auto-generated method stub
        
    }

    /**.
     * windowDeiconified - not in use
     * 
     * @param e - Windowevent
     */
    @Override
    public void windowDeiconified( WindowEvent e ) {
        // TODO Auto-generated method stub
        
    }

    /**.
     * windowActivated - not in use
     * @param e - Windowevent
     */
    @Override
    public void windowActivated( WindowEvent e ) {
        // TODO Auto-generated method stub
        
    }

    /**.
     * WindowDeactivated - not in use
     * @param e - Windowevent
     */
    @Override
    public void windowDeactivated( WindowEvent e ) {
        // TODO Auto-generated method stub
        
    }

    /**.
     * actionPerformed - when a user clicks a button
     * 
     * @param e - Pressing of buttons
     */
    
    @Override
    public void actionPerformed( ActionEvent e ) {
        
        BogglePlayer[] players = new BogglePlayer[2];
        
        players[ HUMAN ] = game.getPlayer( HUMAN );
        players[ COMPUTER ] = game.getPlayer( COMPUTER );
        
        
        JButton btn = (JButton) e.getSource();
        
        if ( btn.getText().equals( "Quit" ) ) { //Actions for quit
            try {
                closingMethod();
            } catch ( IOException e1 ) {
                
                e1.printStackTrace();
            }
        } else if ( btn.getText().equals( "Start" ) ) { //Starts a new Round
            setGuiNewRound();
            players[ HUMAN ].reset();
            players[ COMPUTER ].reset();
            try {
                readFile();
                beginGame();
                playRounds();
                gui.getRoundNumber().setText( "" + game.getRoundNumber() );
            } catch ( IOException e1 ) {
                
                e1.printStackTrace();
            }
            

        } else if ( btn.getText().equals( "Restart" ) ) { //Restarts boggle
            game.startNewGame();
            timer.stop();
            setGuiNewRound();
            gui.getStartBtn().setEnabled( true );
            gui.getComTotalScoreLabel().setText( "0" );
            gui.getTotalScoreLabel().setText( "0"  );
            
            
            
            for ( int r = 0; r < 4; r++ ) {
                for ( int c = 0; c < 4; c++ ) {
                    gui.getBoggleButtons()[ r ][ c ].setText( "---" );
                }
            }

        } else if ( btn.getText().equals( "Reject" ) ) { //Rejects words
            
            
            try {
                getRejectWords( players );
            } catch ( IOException e1 ) {
                
                e1.printStackTrace();
            }
            
        }
    
    }
    
    
    
    /**.
     * Read file and populate Dictionary.
     *
     * Modifications: PA02 - added for PA2
     *
     * @return true if the file can be read
     * @throws IOException The IOException
     **/
    private boolean readFile() throws IOException {

        Dictionary dict = Dictionary.getBoggleDictionary();

        boolean canRead = fileIO.open( READER );

        if ( canRead ) {
            String word = fileIO.readLine();
            while ( word != null ) {
                dict.add( word.toLowerCase() );
                word = fileIO.readLine();

            } // end while

            fileIO.close( READER );

        } // end if

        return canRead;

    } // method readFile
    
    
    /**
     * Handles beginning of game events.
     * 
     * Modification: P0A2 - added call to readFile 
     *                    - added code to get/set pointsToWin and difficulty 
     *               PA03 - removed call to readFile
     *               
     * @throws IOException The IOException
     */
    private void beginGame() throws IOException {

        game.setPointsToWin( gui.getPointsToWin() );
        game.setDifficulty( gui.getDifficultyLevel() );

    } // method beginGame
    
    /**
     * Plays multiple rounds of Boggle.
     * 
     * Modifications: PA02 - modified for PA2 to play multiple rounds
     *
     * @throws IOException The IOException
     */
    private void playRounds() throws IOException {

        beginRound();
        setBoard();
        playSingleRound();
        

       

    } // method playRounds()
    
    /**
     * Handles end of round events.
     *
     * Modifications: PA02 - return type changed to boolean to indicate 
     *                       whether or not to continue 
     *                     - if game score >= ** points needed, then
     *                       carryOn = false; 
     *                PA03 - added computer player, sent human & computer 
     *                       players to displayWords method
     *                     - remove beep.
     *                     - add to dictionary after computer plays
     *
     * @return boolean
     * @throws IOException The IOException
     */
    private boolean endRound() throws IOException {
        gui.getWords().setEnabled( false );

        boolean carryOn = true; // added for PA2
        int maxScore = 0;
        

        BogglePlayer[] players = new BogglePlayer[ 2 ];

        players[ HUMAN ] = game.getPlayer( HUMAN );
        players[ COMPUTER ] = game.getPlayer( COMPUTER );
        
        
        addWordsToPlayer( gui.getWords().getText() );
        
        loadLists( players ); //loads JLists
        

        displayPlayerScores( 0 ); //displays scores
        displayComputerScores();
        
        // add human words to dictionary after rejecting words
        addWordsToDictionary();


        

        maxScore = Math.max( 
                players[HUMAN].getTotalScore(),
                players[ COMPUTER ].getTotalScore() );

        if ( maxScore >= game.getPointsToWin() ) {
            carryOn = false;
            if ( players[ HUMAN ].getTotalScore() 
                    > players[ COMPUTER ].getTotalScore() ) {
                gui.getStartBtn().setText( "You Win!" );
            } else if ( players[ HUMAN ].getTotalScore() 
                    < players[ COMPUTER ].getTotalScore() ) {         
                gui.getStartBtn().setText( "You Lose!" );
            } else {
                gui.getStartBtn().setText( "Tie!" );
            }
            
        } // end if

        return carryOn;

    } // method endRound
    


    /**.
     * displayComputerScores - displays scores
     */
    
    private void displayComputerScores() {
 
        BogglePlayer machine = game.getPlayer( COMPUTER );  

        machine.computeScore();

        gui.getComRoundScoreLabel().setText( 
                String.valueOf( machine.getRoundScore() ) );
        gui.getComTotalScoreLabel().setText( 
                String.valueOf( machine.getTotalScore() ) );
        
    }
    
    
    /**.
     * displayPlayerScores - display player scores
     * @param rejectScore - score to remove after rejected words
     */
    private void displayPlayerScores( int rejectScore ) {

        BogglePlayer human = game.getPlayer( HUMAN );
        human.computeScore();
        
        int rs = human.getRoundScore() - rejectScore;
        totalReject += rejectScore;
        int ts = human.getTotalScore() - totalReject;
        gui.getRoundScoreLabel().setText( 
                String.valueOf( rs ) );
        gui.getTotalScoreLabel().setText( 
                String.valueOf( ts ) );
    }

    /**.
     * setGuiNewRound - resets gui
     */
    
    private void setGuiNewRound() {
        
        gui.getStartBtn().setText( "Start" );
        gui.getRejectBtn().setEnabled( false );
        gui.getStartBtn().setEnabled( false );
        gui.getWords().setEnabled( true );
        gui.getWords().setText( null );
        gui.getUniqueList().setListData( new String[0] );
        gui.getCommonList().setListData( new String[0] );
        gui.getInvalidList().setListData( new String[0] );
        gui.getComputerList().setListData( new String[0] );
        gui.getComRoundScoreLabel().setText( "0" );
        gui.getRoundScoreLabel().setText( "0" );
        gui.getRoundNumber().setText( "0" );
        gui.getTimer().setText( "0:00" );
        
    }
    
    /**
     * Generate the WordSets for a user.
     * 
     * Modifications: PA03 - new for PA3
     *
     * @param players A BogglePlayer array
     * @return an array of WordSets
     * @throws IOException The IOException
     */
    private WordSet[] generateSets( BogglePlayer[] players ) {

        WordSet[] sets = new WordSet[ 4 ];
        

        // don't try this if the incoming array is not correct
        if ( players != null && players.length == 2 ) {
            
            // generate the valid/invalid lists for each player
            for ( int i = 0; i < players.length; i++ ) {
                
                players[ i ].validate();
                
            } // end for

            // get the intersection of the two lists
            
            sets[ ALL_WORDS ] = ( players[ HUMAN ].getSet()
                    .intersection( players[ COMPUTER ].getValidSet()  ) )
                    .difference( players[ HUMAN ].getRejectSet() );

            
            // human list - computer list
            sets[ HUMAN_UNIQUE ] = ( players[ HUMAN ].getValidSet()
                            .difference( players[ COMPUTER ].getValidSet() ) )
                    .difference( players[ HUMAN ].getRejectSet() );

            // computer list - human list
            sets[ COMPUTER_UNIQUE ] = ( players[ COMPUTER ].getValidSet()
                            .difference( players[ HUMAN ].getValidSet() ) )
                    .difference( players[ HUMAN ].getRejectSet() );

            // human invalid list
            sets[ INVALID_WORDS ] = ( players[ HUMAN ].getInvalidSet() )
                    .difference( players[ HUMAN ].getRejectSet() );

            // set the unique list for each player
            
            
            players[ HUMAN ].setUniqueSet( sets[ HUMAN_UNIQUE ] );
            players[ COMPUTER ].setUniqueSet( sets[ COMPUTER_UNIQUE ] );

        } // end if

        return sets;

    } // method generateSets
    
    /**
     * Add words to the dictionary based on difficulty level.
     */
    private void addWordsToDictionary() {
        
        int difficulty = game.getDifficultyLevel();
        Dictionary dictionary = Dictionary.getBoggleDictionary();
        Random random = new Random();
        WordSet set = game.getPlayer( HUMAN ).getSet();
            
        for ( int i = 0; i < set.size(); i++ ) {
            
            if ( difficulty < random.nextInt( difficulty ) ) {
                dictionary.add( set.get( i ) );
            
            } // end if
        
        } // end for
    }

    
    /***
     * Get words from user to remove from the dictionary.
     *
     * Modification: PA02 - added for PA2
     * 
     * @param players - the BogglePlayers
     * @throws IOException The IOException
     **/
    private void getRejectWords( BogglePlayer[] players ) throws IOException {
        
        
        
        players[HUMAN].getRejectSet().add( 
                gui.getUniqueList().getSelectedValue() );
        
        players[HUMAN].getUniqueSet().remove( 
                gui.getUniqueList().getSelectedValue() );
   
        loadLists( players );  
        
        displayPlayerScores( players[HUMAN].getRoundScore() );
    }
    
    

    
    
    /**
     * Handles beginning of round events.
     *
     * @throws IOException The IOException
     */
    private void beginRound() throws IOException {

        game.playRound(); // init game


    }
    
    /**
     * Play a single round of Boggle.
     * 
     * Modifications: PA02 - Refactored for PA2
     * 
     * @throws IOException The IOException
     */
    private void playSingleRound() throws IOException {
        timer = new EggTimer( 180 );
        timer.addTickListener( this );
        gui.getTimer().setText( "3:00" );
 

    } // method playSingleRound()
    
    
    
    /**
     * Add the list of words from the player to the Player's word list.
     * 
     * Modification: PA02 - added difficulty level to param of player
     *                    - refactor: add null and length tests
     *               PA03 - remove difficulty level - handle this at end
     *                      of round
     *                          
     *
     * @param words The words to add
     */
    private void addWordsToPlayer( String words ) {

        if ( words != null ) {
            BogglePlayer player = game.getPlayer( HUMAN );
            String[] wordArray = words.split( "\\s+" );

            for ( String word : wordArray ) {
                if ( Utilities.isValidWord( word ) ) {
                    HumanPlayer human = (HumanPlayer) player;
                    human.add( word );

                } // end if
                
            } // end for

        } // end if

    } // method addWordsToPlayer
    
    /**.
     * setBoard - sets board with letters
     */
    private void setBoard() {
        for ( int r = 0; r < gui.getBoggleButtons().length; r++ ) {
            for ( int c = 0; c < gui.getBoggleButtons()[r].length; c++ ) {
                gui.getBoggleButtons()[r][c].setText( 
                        Character.toString( board.getCell( r, c ) ) );
            }
        }
    }
    
    /**.
     * closingMethod - writes file before closing
     * @throws IOException - ioexception
     */
    private void closingMethod() throws IOException {
        writeFile();
        System.exit( 0 );
    }

    /**.
     * tick - Does every second
     * @param timer - Timer obj
     */
    @Override
    public void tick( EggTimer timer ) {
        gui.getTimer().setText( timer.getTimeLeft() );
        if ( timer.getSecondsLeft() == 0 ) {
            try {
                if ( endRound() ) {
                    gui.getStartBtn().setEnabled( true );
                    gui.getRejectBtn().setEnabled( true );
                }
                
            } catch ( IOException e ) {
                
                e.printStackTrace();
            }
        }
    }
    
    /**.
     * loadLists - loads JLists with wordsets
     * @param players - BogglePlayers, human, and Computer
     */
    
    private void loadLists( BogglePlayer[] players ) {
        WordSet[] sets = new WordSet[4];
        sets = generateSets( players );
        
        Dimension size = gui.getUniqueList().getSize();


        
        gui.getUniqueList().setListData( sets[HUMAN_UNIQUE].getArray() );
        gui.getUniqueList().setFixedCellWidth( size.width );
        
        
        

        size = gui.getCommonList().getSize();

        gui.getCommonList().setListData( sets[ALL_WORDS].getArray() );
        gui.getCommonList().setFixedCellWidth( size.width );
        
        


        size = gui.getComputerList().getSize();

        gui.getComputerList().setListData( sets[COMPUTER_UNIQUE].getArray() );
        gui.getComputerList().setFixedCellWidth( size.width );
        


        size = gui.getInvalidList().getSize();

        gui.getInvalidList().setListData( sets[INVALID_WORDS].getArray() );
        gui.getInvalidList().setFixedCellWidth( size.width );
        
    
    }
    

    /**
     * Write file from Dictionary - if over 300 words randomly remove words
     * in excess of 300.
     *
     * Modificationa: PA02 - added for PA2 (alternate version)
     *
     * @return true if writing is possible
     * @throws IOException The IOException
     **/
    private boolean writeFile() throws IOException {

        boolean canWrite = fileIO.open( BoggleFileIO.WRITER );

        Dictionary dict = Dictionary.getBoggleDictionary();
        ArrayList< String > dictList = dict.getDictionary();

        if ( canWrite ) {

            Random rand = new Random();

            // randomly remove size() - 300
            while ( dictList.size() > 300 ) {

                dictList.remove( rand.nextInt( dictList.size() ) );

            } // end while -- should be 300 left

            for ( int index = 0; index < dictList.size(); index++ ) {

                // write this if it hasn't already been written
                fileIO.write( dictList.get( index ) );

            } // end for

            fileIO.close( BoggleFileIO.WRITER );

        } // end if

        return canWrite;

    } // method writeFile()
        
    


}
