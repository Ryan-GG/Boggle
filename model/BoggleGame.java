package model;

import utilities.BoggleConstants;
import utilities.Utilities;

/**
 * BoggleGame - manages the game information
 * 
 * Modifications: PA02 - Added parameters dictionary, difficultyLevel
 *                       pointsToWin \
 *                     - Added methods: getDictionary(), getDifficultyLevel(),
 *                       getPointsToWin(), setDifficulty(), setPointsToWin() 
 *                     - Refactored logic for "between" and put in static method
 *                       in Utilities class. P
 *                PA03 - Used Singleton versions of Dictionary and BoggleBoard  
 *                     - Added human & computer players, startGame() method 
 *                     - Modified getPlayer() method to specify which player to
 *                       return
 * 
 * @author Michael Norton
 * @version PA03 (27 October 2019), PA02 (8 October 2019), PA01 (20 September
 *          2019)
 */
public class BoggleGame implements BoggleConstants {

    private int difficulty; // new for PA2
    private int pointsToWin; // new for PA2
    private int roundNumber;

    private BoggleBoard board;
    private BogglePlayer computer; // new for PA3
    private BogglePlayer human; // new for PA3

    /**
     * default constructor.
     */
    public BoggleGame() {

        roundNumber = 0;
        difficulty = DEFAULT_DIFFICULTY;
        pointsToWin = DEFAULT_POINTS_TO_WIN;

        board = BoggleBoard.getBoggleBoard();
        human = new HumanPlayer();
        computer = new ComputerPlayer();

    } // default constructor

    /**
     * Return the difficulty level.
     * 
     * Modification: PA02 - New for PA2
     * 
     * @return the difficulty level
     */
    public int getDifficultyLevel() {

        return difficulty;

    } // method getDifficultyLevel()

    /**
     * Return the Boggle player.
     * 
     * Modification: PA03 - specify which player to return
     * 
     * @param which Which player to return
     * @return the BogglePlayer
     */
    public BogglePlayer getPlayer( int which ) {

        return which == COMPUTER ? computer : human;

    } // method getPlayer()

    /**
     * Return the points required to win the game.
     * 
     * Modification: PA02 - New for PA2
     * 
     * @return the points to win
     */
    public int getPointsToWin() {

        return pointsToWin;

    } // method getPointsToWin()

    /**
     * Return the current round number.
     * 
     * @return the current round number
     */
    public int getRoundNumber() {

        return roundNumber;

    } // method getRoundNumber()

    /**
     * Play a single round of Boggle.
     */
    public void playRound() {

        roundNumber++;
        board.mix();

    } // method playRound()

    /**
     * Set the difficulty level.
     * 
     * Modification: PA02 - New for PA2
     * 
     * @param level The difficulty level
     */
    public void setDifficulty( int level ) {

        difficulty = DEFAULT_DIFFICULTY;

        if ( Utilities.isBetween( level, MIN_DIFFICULTY, MAX_DIFFICULTY ) ) {
            difficulty = level;
            
        } // end if

    } // method setDifficulty( int )

    /**
     * Set the points required to win.
     * 
     * Modification: PA02 - New for PA2
     * 
     * @param points The points need to win
     */
    public void setPointsToWin( int points ) {

        pointsToWin = DEFAULT_POINTS_TO_WIN;

        if ( Utilities.isBetween( points, MIN_POINTS_TO_WIN,
                        MAX_POINTS_TO_WIN ) ) {
            pointsToWin = points;
            
        } // end if

    } // method setPointsToWin( int )

    /**
     * Reset both players and set the round number to 0.
     * 
     * Modification: PA03 - New for PA3
     */
    public void startNewGame() {

        human.resetGame();
        computer.resetGame();
        roundNumber = 0;

    } // method startGame()

} // class BoggleGame
