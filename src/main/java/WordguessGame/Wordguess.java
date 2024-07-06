package WordguessGame;
import java.util.Random;
import java.util.Scanner;

// Wordguess class
public class Wordguess {
    private final String[] peterWords = {"cars", "cows", "cake", "cups"};
    // array of characters guessed by player
    private char[] currentGuess;
    // declare a string for secret word
    private String secretWord;
    // set guess limit
    private int maxGuesses = 8;
    // declare variable to count guesses
    private int currentGuesses;

    // main class / global
    public static void main(String[] args) {
        Wordguess playGame = new Wordguess();
        playGame.runGame();
    }

    // run game method
    public void runGame() {
        // scanner to get letter guess from user
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        // run game at least once while conditions met of not guessed yet
        do {
            announceGame(); // announceGame() prints a welcome
            // get secret word
            secretWord = getRandom(peterWords);
            // set game to the 'start' state
            initializeGameState(secretWord);
            // set guesses to 0, haven't guessed yet
            currentGuesses = 0;
            // word is not guessed yet, so start with false
            boolean wordGuessed = false;
            // loop the display so that it continues to run until the game is ended
            // check if word HAS NOT been guessed yet
            // check if current guesses is less than max guesses
            while (!wordGuessed && currentGuesses < maxGuesses) {
                printCurrentState();
                char guess = getNextGuess();
                processGuess(guess);
                wordGuessed = isWordGuessed();
                currentGuesses++;
            }
            // user guessed the word!
            if (wordGuessed) {
                playerWon();
            } else {
                // user used up the guess limit!
                playerLost();
            }
            // activate gameover, regardless of win or lose
            gameOver();
            playAgain = askToPlayAgain(scanner);
        } while (playAgain);
        // close scanner
        scanner.close();
    }

    // METHOD TO: random word from array
    public String getRandom(String[] array) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(array.length);
        return array[randomIndex];
    }

    public void announceGame() {
        System.out.println("Welcome to the World of Wordguess!");
    }

    public void initializeGameState(String word) {
        // initialize_game_state() sets up char[] for secret word and guesses
        currentGuess = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            currentGuess[i] = '_';
        }
    }

    public void playerWon() {
        System.out.println("**************\n" +secretWord + "\n**************" +"\nCorrect ! The word was " + secretWord +"\nWay to go!");
    }

    public void playerLost() {
        System.out.println("Sorry...you just lost.");
    }

    public void gameOver() {
        System.out.println("G A M E   O V E R");
    }

    public boolean isWordGuessed() {
        for (char c : currentGuess) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public char getNextGuess() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your guess? ");
        String input;
        char guess;
        do {
            System.out.print("Your guess? ");
            input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
            } else if (input.length() != 1) {
                System.out.println("1 letter at a time!");
            } else if (!Character.isLetter(input.charAt(0))) {
                System.out.println("Invalid entry.");
            }
        } while (input.isEmpty() || input.length() != 1 || !Character.isLetter(input.charAt(0)));
        guess = input.charAt(0);
        return guess;
    }


    public boolean askToPlayAgain(Scanner scanner) {
        System.out.print("Play again? (y/n): ");
        // scanner to get response, trim white space
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y");
    }

    public void printCurrentState() {
        System.out.println(new String(currentGuess));
    }

    public void processGuess(char guess) {
        // default boolean is false, not guessed character
        boolean foundLetter = false;
        // loop through index of secret word
        for (int i = 0; i < secretWord.length(); i++) {
            // if character/letter matched
            if (secretWord.charAt(i) == guess) {
                currentGuess[i] = guess;
                foundLetter = true;
            }
        }
        // message if character/letter doesn't match
        if (!foundLetter) {
            System.out.println("Guess again! " + (maxGuesses - currentGuesses - 1) + " guesses left.");
        }




    }
}
