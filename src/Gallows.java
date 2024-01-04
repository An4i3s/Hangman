import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gallows {

    private String[] initialState = {
            "  +---+",
            "  |   |",
            "      |",
            "      |",
            "      |",
            "      |",
            "========="
    };
    private final int MAX_ERRORS = 6;

    protected final ArrayList<Character> lettersGuessed = new ArrayList<>();
    private  String movieChoice = null;

    protected int errorCount = 6;

    public Gallows(String[] initialState) {
        this.initialState = initialState;
    }

    public Gallows() {
    }

    public Gallows(String movieChoice) {
        this.movieChoice = movieChoice;
    }

    public Gallows(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getMAX_ERRORS() {
        return MAX_ERRORS;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public List<Character> getLettersGuessed() {
        return lettersGuessed;
    }

    public String getMovieChoice() {
        return movieChoice;
    }

    public void displayHangman(){
        switch (this.errorCount){
            case 6 -> Arrays.asList(initialState).forEach(System.out::println);
            case 5 -> System.out.println(
                    """
                       +---+
                       |   |
                       O   |
                           |
                           |
                           |
                     =========
                            """);
            case 4 -> System.out.println(
                    """
                     +-----+
                     |     |
                     O     |
                     |     |
                           |
                           |
                   ===========
                            """);

            case 3 -> System.out.println(
                    """
                     +-----+
                     |     |
                     O     |
                   --|     |
                           |
                           |
                  ===========
                            """);

            case 2 -> System.out.println(
                    """
                     +-----+
                     |     |
                     O     |
                   --|--   |
                           |
                           |
                   ==========
                            """);

            case 1 -> System.out.println(
                    """
                     +-----+
                     |     |
                     O     |
                   --|--   |
                    |      |
                           |
                    ==========
                            """);

            case 0 -> System.out.println(
                    """
                     +-----+
                     |     |
                     O     |
                   --|--   |
                    | |    |
                           |
                    ==========
                            """);

            default-> System.out.println("invalid error count");
            }

        }
    }

