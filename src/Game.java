
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;

public class Game {

    public static void main(String[] args) throws IOException {
        Scanner userScanner = new Scanner(System.in);
        System.out.println("""
                Scegli la Categoria:
                1. Film
                2. Serie TV
                3. Capitali

                """);
        int categoria = userScanner.nextInt();
        while (categoria > 3) {
            System.out.println("scelta non valida. Scegliere numero tra 1 e 3");
            categoria = userScanner.nextInt();
        }
        List<String> lista = new ArrayList<>();
        switch (categoria) {
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
                System.out.println("Scelta non valida!!");
                break;
            }

        }

        String[] listaToArray = lista.toArray(new String[lista.size()]);

        String selectedMovie = selectRandomWord(listaToArray).toUpperCase();
        // System.out.println("The randomly selected Word is: " + selectedMovie);

        Gallows gallows = new Gallows(selectedMovie);
        // è necessario aggiungere un carattere spazio " " a getLettersGuessed
        // altrimenti non si verifica mai condizione hasWon nelle stringhe dei titoli
        // che presentano spazi
        gallows.getLettersGuessed().add(' ');
        gallows.displayHangman();

        System.out.println(displayAsLines(selectedMovie));

        while (gallows.errorCount > 0) {
            System.out.println(" ");

            System.out.println("Inserisci una lettera. Se vuoi provare a indovinare la risposta completa premi 1");
            char userGuess = userScanner.next().charAt(0);
            if (userGuess == '1') {
                boolean vero = indovinaParolaCompleta(selectedMovie);

                if (vero) {
                    // System.out.println("Complimenti hai vinto!");
                    break;
                } else {
                    gallows.errorCount--;
                    gallows.displayHangman();
                    System.out.println("Tentativi rimasti: " + gallows.errorCount);
                    continue;
                }

            } else {
                userGuess = Character.toUpperCase(userGuess);
                System.out.println("Hai scelto = " + userGuess);
                boolean checkGuess = isCharInTitle(userGuess, selectedMovie);

                if (gallows.getLettersGuessed().contains(userGuess)) {
                    System.out.println("Hai già tentato questa lettera. Inserire lettera diversa.");
                } else if (!checkGuess) {
                    System.out.println("Sbagliato!");
                    gallows.getLettersGuessed().add(userGuess);

                    gallows.errorCount--;
                    gallows.displayHangman();
                    System.out.println("Tentativi rimasti: " + gallows.errorCount);
                } else {
                    gallows.getLettersGuessed().add(userGuess);

                }
                System.out.println("Tentativi precedenti: " + gallows.getLettersGuessed());
                
            System.out.println(" ");
            revealTitle(selectedMovie, gallows.getLettersGuessed());
            System.out.println(" ");
            if (gallows.errorCount == 0) {
                System.out.println();
                System.out.println("Hai perso!");
                System.out.println("La risposta era: " + gallows.getMovieChoice());
            }
            boolean hasUserWon = hasWon(gallows.getMovieChoice(), gallows.getLettersGuessed());
            if (hasUserWon) {
                System.out.println();
                System.out.println("Complimenti! Hai vinto!");
                break;
            }
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

        for (int i = 0; i < title.length(); i++) {
            if (titleChar[i] == ' ') {
                System.out.print(' ');
                ;
            } else if (guessedLetter.contains(titleChar[i])) {
                System.out.print(titleChar[i]);
            } else {
                System.out.print("_");
            }
        }

    }

    public static boolean hasWon(String title, List<Character> guessedLetter) {
        char[] titleArray = title.toCharArray();
        ArrayList<Character> letterList = new ArrayList<>(guessedLetter);

        boolean flag = false;
        int i = 0;
        while (i < title.length()) {
            if (guessedLetter.contains(titleArray[i])) {
                flag = true;
                i++;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static boolean indovinaParolaCompleta(String titolo) {
        Scanner scanner = new Scanner(System.in);
        String tentativoUtente = scanner.nextLine();

        if (tentativoUtente.equalsIgnoreCase(titolo)) {
            System.out.println("Complimenti! Hai vinto!");
            scanner.close();
            return true;
        } else {
            System.out.println("Sbagliato!");
            //scanner.close();
            return false;
        }
        
    }
}
