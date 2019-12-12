package utilities;

/**
 * Finals for general use in the Boggle game.
 *
 * @author Michael Norton
 * @version PA03 (27 October 2019)
 */
public interface BoggleConstants {

    int COMPUTER = 0;
    int HUMAN = 1;

    int LIST = 0; // added for PA3
    int VALID = 1; // added for PA3
    int INVALID = 2; // added for PA3
    int UNIQUE = 3; // added for PA3

    int DEFAULT_DIFFICULTY = 5;
    int DEFAULT_POINTS_TO_WIN = 100;
    int MAX_DIFFICULTY = 10;
    int MAX_POINTS_TO_WIN = 10000;
    int MIN_DIFFICULTY = 1;
    int MIN_POINTS_TO_WIN = 10;

    int MAX_WORDS = 300;

    int READER = 0;
    int WRITER = 1;

    String NL = "\n";
    String TAB = "\t";

    int GAME_TIME = 180;

    int ALL_WORDS = 0; // added for PA3
    int INVALID_WORDS = 1; // added for PA3
    int HUMAN_UNIQUE = 2; // added for PA3
    int COMPUTER_UNIQUE = 3; // added for PA3

} // interface BoggleConstants
