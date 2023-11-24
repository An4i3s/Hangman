

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;

public class Game {

    public static void main(String[] args) throws IOException {
        Scanner userScanner = new Scanner(System.in);
        System.out.println("""
                Choose the Category:
                1. Film
                2. Serie TV
                3. Capitali
  
                """);
        int categoria = userScanner.nextInt();
        while (categoria>3){
            System.out.println("scelta non valida. Scegliere numero tra 1 e 3");
            categoria = userScanner.nextInt();
        }
        List<String> lista = new ArrayList<>();
        switch (categoria){
            case 1 -> {
                 lista = Files.readAllLines(Path.of("listaFilm.txt"));
            }
            case 2 -> {
                lista = Files.readAllLines(Path.of("listaSerieTv.txt"));
            }
            case 3 -> {
                lista = Files.readAllLines(Path.of("listaCity.txt"));
            }
            default -> {
                System.out.println("Incorrect!!");
                break;
            }

        }

        //List<String> moviesList = Files.readAllLines(Path.of("moviesList.txt"));
        //String[] movies = moviesList.toArray(new String[moviesList.size()]);
        String[] listaToArray = lista.toArray(new String[lista.size()]);


        String selectedMovie = selectRandomWord(listaToArray).toUpperCase();
        //System.out.println("The randomly selected Word is: " + selectedMovie);


        Gallows gallows = new Gallows(selectedMovie);
        gallows.getLettersGuessed().add(' ');
        gallows.displayHangman();

        System.out.println(displayAsLines(selectedMovie));


        while (gallows.errorCount > 0) {
            System.out.println();
            System.out.println("Enter a letter:");
            char userGuess = userScanner.next().charAt(0);
            userGuess = Character.toUpperCase(userGuess);
            System.out.println("User Guess is = " + userGuess);
            boolean checkGuess = isCharInTitle(userGuess, selectedMovie);

            if (gallows.getLettersGuessed().contains(userGuess)) {
                System.out.println("You already tried this letter. Please input a different letter");
            }else if (!checkGuess) {
                System.out.println("Wrong guess!");
                gallows.getLettersGuessed().add(userGuess);
                gallows.errorCount--;
                gallows.displayHangman();
                System.out.println("You have " + gallows.errorCount + " guess left");
            } else {
                gallows.getLettersGuessed().add(userGuess);

            }
            revealTitle(selectedMovie, gallows.getLettersGuessed());
            if (gallows.errorCount == 0){
                System.out.println();
                System.out.println("You lost!");
                System.out.println("The answer is: "+gallows.getMovieChoice());
            }
            boolean hasUserWon = hasWon(gallows.getMovieChoice(), gallows.getLettersGuessed());
            if (hasUserWon){
                System.out.println();
                System.out.println("Congrats! you won!");
                break;
            }
        }
        userScanner.close();

    }


    public static String selectRandomWord(String[] stringArray) {
        Random random = new Random();
        int selectedInt = (random.nextInt(0, stringArray.length - 1));
        return stringArray[selectedInt];
    }

    public static boolean isCharInTitle(char guess, String title) {
        String guessString = Character.toString(guess);
        if (title.contains(guessString)) {
            return true;
        } else {
            return false;
        }
    }
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

    public static void revealTitle(String title, List<Character> guessedLetter) {

        Character[] guessedLetterArray = guessedLetter.toArray(new Character[0]);
        char[] titleChar = title.toCharArray();

        for (int i = 0;  i < title.length() ;i++){
            if (titleChar[i] == ' ') {
                System.out.print(' ');;
            } else if (guessedLetter.contains(titleChar[i])) {
                System.out.print(titleChar[i]);
            }else {
                System.out.print("_");
            }
        }

    }

    public static boolean hasWon(String title, List<Character> guessedLetter){
      char[] titleArray = title.toCharArray();
      ArrayList<Character> letterList = new ArrayList<>(guessedLetter);

        boolean flag = false;
        int i = 0;
        while (i<title.length()){
            if (guessedLetter.contains(titleArray[i])){
                flag = true;
                i++;
            }else {
                flag = false;
                break;
            }
        }
        return flag;
    }

}
