package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.BoggleBoard;
import model.BoggleGame;
import model.BogglePlayer;
import model.Dictionary;
import model.WordSet;

/**
 * BoggleGameTest - this class tests the Boggle game
 * 
 * @author Michael Norton
 * @version PA01 (19 September 2016)
 */
public class BoggleGameTest {

    private char[][] initialBoard;
    private int initialRoundNumber;
    private BoggleGame game;

    @Before
    public void setup() {

        game = new BoggleGame();
        initialRoundNumber = game.getRoundNumber();
        initialBoard = getBoard();

    }

    @Test
    public void testGetBoard() {

        assertNotNull( "BoggleBoard object should not be null",
                        BoggleBoard.getBoggleBoard() );
    }

    @Test
    public void testGetDictionary() {

        assertNotNull( "Dictionary object should not be null",
                        Dictionary.getBoggleDictionary() );
    }

    @Test
    public void testGetPlayer() {

        assertNotNull( "BogglePlayer object should not be null",
                        game.getPlayer( BoggleGame.COMPUTER ) );
    }

    @Test
    public void testIntialState() {

        assertEquals( "Starting round number should be 0", 0,
                        initialRoundNumber );
    }

    @Test
    public void testPlayRound() {

        boolean isEqualBoard = true;
        BoggleBoard board = BoggleBoard.getBoggleBoard();
        game.playRound();

        for ( int row = 0; row < 4 && isEqualBoard; row++ )
            for ( int col = 0; col < 4 && isEqualBoard; col++ )
                if ( initialBoard[ row ][ col ] != board.getCell( row, col ) )
                    isEqualBoard = false;

        assertTrue( "Round number should increment in playRound()",
                        game.getRoundNumber() == initialRoundNumber + 1 );
        assertFalse( "Board should be different after playRound()",
                        isEqualBoard );

    }

    @Test
    public void testDifficultyLevelValidLow() {

        assertEquals( "Difficulty Level should be 5 at start", 5,
                        game.getDifficultyLevel() );
        game.setDifficulty( 1 );
        assertEquals( "Difficulty Level should be 1 after set", 1,
                        game.getDifficultyLevel() );
    }

    @Test
    public void testDifficultyLevelValidHigh() {

        assertEquals( "Difficulty Level should be 5 at start", 5,
                        game.getDifficultyLevel() );
        game.setDifficulty( 10 );
        assertEquals( "Difficulty Level should be 10 after set", 10,
                        game.getDifficultyLevel() );
    }

    @Test
    public void testDifficultyLevelValidMid() {

        assertEquals( "Difficulty Level should be 5 at start", 5,
                        game.getDifficultyLevel() );
        game.setDifficulty( 4 );
        assertEquals( "Difficulty Level should be 4 after set", 4,
                        game.getDifficultyLevel() );
    }

    @Test
    public void testDifficultyLevelTooLow() {

        assertEquals( "Difficulty Level should be 5 at start", 5,
                        game.getDifficultyLevel() );
        game.setDifficulty( 0 );
        assertEquals( "Difficulty Level should be 5 after setting to 0", 5,
                        game.getDifficultyLevel() );
    }

    @Test
    public void testDifficultyLevelTooHigh() {

        assertEquals( "Difficulty Level should be 5 at start", 5,
                        game.getDifficultyLevel() );
        game.setDifficulty( 11 );
        assertEquals( "Difficulty Level should be 5 after setting setting to 11",
                        5, game.getDifficultyLevel() );
    }

    public void testPointsToWinLow() {

        assertEquals( "Points to Win should be 100 at start", 100,
                        game.getPointsToWin() );
        game.setPointsToWin( 10 );
        assertEquals( "Points to Win should be 10 after set", 10,
                        game.getPointsToWin() );

    }

    @Test
    public void testPointsToWinHigh() {

        assertEquals( "Points to win should be 100 at start", 100,
                        game.getPointsToWin() );
        game.setPointsToWin( 10000 );
        assertEquals( "Points to Win should be 10000 after set", 10000,
                        game.getPointsToWin() );

    }

    @Test
    public void testPointsToWinMid() {

        assertEquals( "Points to win should be 100 at start", 100,
                        game.getPointsToWin() );
        game.setPointsToWin( 5000 );
        assertEquals( "Points to Win should be 5000 after set", 5000,
                        game.getPointsToWin() );

    }

    @Test
    public void testPointsToWinTooLow() {

        assertEquals( "Points to win should be 100 at start", 100,
                        game.getPointsToWin() );
        game.setPointsToWin( 0 );
        assertEquals( "Points to Win should remain 100 after setting to 0", 100,
                        game.getPointsToWin() );

    }

    @Test
    public void testPointsToWinTooHigh() {

        assertEquals( "Points to win should be 100 at start", 100,
                        game.getPointsToWin() );
        game.setPointsToWin( 11000 );
        assertEquals( "Points to Win should remain 100 after setting to 11000",
                        100, game.getPointsToWin() );

    }

    @Test
    public void testStartNewGame() {

        BogglePlayer computer = game.getPlayer( BoggleGame.COMPUTER );
        BogglePlayer human = game.getPlayer( BoggleGame.HUMAN );
        WordSet computerSet = computer.getUniqueSet();
        WordSet humanSet = human.getUniqueSet();

        addWordsToSet( computerSet, humanSet );
        game.playRound();
        human.computeScore();
        computer.computeScore();

        assertTrue( computer.getRoundScore() > 0 );
        assertTrue( human.getRoundScore() > 0 );
        assertTrue( game.getRoundNumber() > 0 );

        game.startNewGame();

        assertTrue( computer.getRoundScore() == 0 );
        assertTrue( human.getRoundScore() == 0 );
        assertTrue( game.getRoundNumber() == 0 );
    }

    private void addWordsToSet( WordSet computerSet, WordSet humanSet ) {

        computerSet.add( "hello" );
        humanSet.add( "particularly" );
    }

    private char[][] getBoard() {

        char[][] board = new char[ 4 ][ 4 ];
        BoggleBoard orgBoard = BoggleBoard.getBoggleBoard();

        for ( int row = 0; row < 4; row++ )
            for ( int col = 0; col < 4; col++ )
                board[ row ][ col ] = orgBoard.getCell( row, col );

        return board;
    }

}
