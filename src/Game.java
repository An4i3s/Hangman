import com.sun.jdi.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;
import java.util.function.Supplier;


public class Game {


    public static void main(String[] args) throws IOException {
        Scanner userScanner = new Scanner(System.in);
        // TODO: 05/10/2023 make the guessedChar a filed of gallows. Then the displayLines will access this field to verify wich letters already exist
        // TODO: 05/10/2023 ?? make the guessedFilmMovie immutable and a property of the gallow
       // ArrayList<Character> guessedChar = new ArrayList<>();
       // char[] letterArray = new char[26];


        List<String> moviesList = Files.readAllLines(Path.of("moviesList.txt"));
        // moviesList.forEach(System.out::println);
        String[] movies = moviesList.toArray(new String[moviesList.size()]);

        String selectedMovie = selectRandomMovie(movies).toUpperCase();
        System.out.println("The randomly selected Movie Title is: " + selectedMovie);

       // final char[]  selectedMovieChar = selectedMovie.toCharArray();
        //PROVA
       // char[] hiddenMovieChar = selectedMovieChar.clone();
        String hiddenMovie = displayAsLines(selectedMovie);

        //String selectedMovieHidden = displayAsLines(selectedMovie);
        // System.out.println(selectedMovieHidden);


        Gallows gallows = new Gallows(selectedMovie);
        gallows.displayHangman();


        // TODO: 05/10/2023 create a game loop from here
       // int j = 0;



        while (gallows.errorCount > 0) {
            System.out.println("Enter a letter:");
            char userGuess = userScanner.next().charAt(0);
            userGuess = Character.toUpperCase(userGuess);

            System.out.println("User Guess is = " + userGuess);

            boolean checkGuess = isCharInTitle(userGuess, selectedMovie);

            if (gallows.getLettersGuessed().contains(userGuess)) {
                System.out.println("You already tried this letter. Please input a different letter2");
            }else if (!checkGuess) {
                System.out.println("Wrong guess!");
                gallows.getLettersGuessed().add(userGuess);
                System.out.println("guessedChar arrayList = "+gallows.getLettersGuessed());
               /* letterArray[j] = userGuess;
                j++;*/
                gallows.errorCount--;
                gallows.displayHangman();
                System.out.println("You have " + gallows.errorCount + " guess left");
            } else {
                gallows.getLettersGuessed().add(userGuess);
                System.out.println("guessedChar arrayList = "+gallows.getLettersGuessed());

                //selectedMovieChar = c;
                System.out.println("My result");
                System.out.println(gallows.getMovieChoice());
                //System.out.println(displayAsLines(selectedMovieChar, guessedChar));
                //displayAsLines(hiddenMovieChar,guessedChar);

            }
          //  displayAsLines(gallows.getMovieChoice(), (ArrayList<Character>) gallows.getLettersGuessed());
            revealTitle(selectedMovie, gallows.getLettersGuessed());
        }
        userScanner.close();

    }

    public static String selectRandomMovie(String[] stringArray) {
        Random random = new Random();
        int selectedInt = (random.nextInt(0, stringArray.length - 1));
        return stringArray[selectedInt];
    }

    // TODO: 05/10/2023 togliere display of lines con paramettro String?
    public static String displayAsLines(String title) {
        char[] titleArray = title.toCharArray();
        for (int i = 0; i < titleArray.length; i++) {
            if (titleArray[i] == ' ') {
                titleArray[i] = ' ';
            } else {
                titleArray[i] = '_';
            }
        }

        String s = new String(titleArray);
        return s;
    }

    public static void displayAsLines(char[] title) {
        //char[] titleArray = title.toCharArray();
        for (int i = 0; i < title.length; i++) {
            if (title[i] == ' ') {
                title[i] = ' ';
            } else {
                title[i] = '_';
            }
        }

        String s = new String(title);
        System.out.println(s);
    }

    //PROVA
    public static String displayAsLines(String title, ArrayList<Character> guessedLetter) {

        // TODO: 05/10/2023 void
        Character[] guessedLetterArray = guessedLetter.toArray(new Character[0]);
        System.out.println("guessedLetterArray = ");
        Arrays.asList(guessedLetterArray).forEach(System.out::println);
         char[] titleChar = title.toCharArray();
        System.out.println("titleChar = ");
        Arrays.asList(titleChar).forEach(System.out::println);


        // TODO: 05/10/2023 sistemare errore logico in title char [i]
            for (Character character : guessedLetterArray) {
                for (int i = 0; i < titleChar.length ; i++) {
                if (titleChar[i] == ' ') {
                    titleChar[i] = ' ';
                } else if (titleChar[i] == character) {
                    titleChar[i] = character;
                    System.out.println("title char is "+titleChar[i]);
                } else {
                    titleChar[i] = '_';
                }
            }
        }
        System.out.println(titleChar);
            return titleChar.toString();
    }

    public static void revealTitle(String title, List<Character> guessedLetter) {

        // TODO: 05/10/2023 void
        Character[] guessedLetterArray = guessedLetter.toArray(new Character[0]);
        System.out.println("guessedLetterArray = ");
        Arrays.asList(guessedLetterArray).forEach(System.out::println);
        char[] titleChar = title.toCharArray();
        System.out.println("titleChar = ");
        Arrays.asList(titleChar).forEach(System.out::println);

        for (int i = 0;  i < title.length() ;i++){
            if (titleChar[i] == ' ') {
                System.out.print(' ');;
            } else if (guessedLetter.contains(titleChar[i])) {
                System.out.print(titleChar[i]);
                //System.out.println("title char is "+titleChar[i]);
            }else {
                System.out.print("_");
            }
        }

        //System.out.println(titleChar);
    }

    public static boolean isCharInTitle(char guess, String title) {
        String guessString = Character.toString(guess);
        if (title.contains(guessString)) {
            return true;
        } else {
            return false;
        }
    }

    public static char[] revealLetters(String hiddenTitle, String title, char guess) {
        //transform both Strings into Char Array. Replace each element where char[i] = 'guess' with the guess
        //return the hiddenTitle modified String
        //?Assign the result to String hiddenTitle?

        char[] hiddenArray = hiddenTitle.toCharArray();
        char[] titleArray = title.toCharArray();
        for (int i = 0; i < titleArray.length; i++) {
            if (titleArray[i] == guess) {
                hiddenArray[i] = guess;
            }
        }

        System.out.println("result of reveal letters is:");
        List.of(hiddenArray).forEach(System.out::print);
        System.out.println(" ");

        return hiddenArray;
    }

    //PROVA
/*
    public static void revealLetters(char[] myTitleArray, char[] guessList) {
        // TODO: 05/10/2023 deve apparire la parola con le lettere nascoste tranne che per le lettere presenti in guessList

        displayAsLines(myTitleArray, guessList);
       */
/* for (char c:myTitleArray) {
            if (guessList.contains(c)){
                System.out.print(c);
            }
        }
*//*


        // System.out.println("Result of revealLetters is: " + Arrays.toString(hiddenArray));

    }
*/

}
