package bulls.and.cows.anastasiia.tsygankova;

import java.util.*;

import java.util.*;

public class BullsAndCows {
    private static Scanner scanner = new Scanner(System.in);
    private final String code;
    private final int numOfSymbols;
    private final String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
    private final int codeLength;
    private Random random = new Random();

    public BullsAndCows() {
        this.codeLength = codeLength();
        this.numOfSymbols = numOfPossibleSymbols();
        this.code = generateCode(this.numOfSymbols, this.codeLength);
    }

    // Method scans and checks the code length
    private int codeLength() {

        System.out.println("Please, enter the secret code's length:");
        System.out.print("> ");
        String input = scanner.nextLine();
        int codeLength;

        if (!input.matches("\\d+")) {
            System.out.println("Error: entered input is not a digit.");
            System.exit(0);
        }
        codeLength = Integer.parseInt(input);

        if (!(codeLength > 0 && codeLength <= 36)) {
            System.out.print("Error: can't generate " +
                    "a secret number with a length of "
                    + input +
                    "."
            );
            System.exit(0);
        }
        codeLength = Integer.parseInt(input);
        return codeLength;
    }

    // Method scans and checks the desired number of symbols
    private int numOfPossibleSymbols() {

        System.out.println("Input the number of possible symbols in the code:");
        System.out.print("> ");
        String input = scanner.nextLine();

        if (!input.matches("\\d+")) {
            System.out.println("Error: Entered input is not a digit.");
            System.exit(0);
        }

        int numOfSymbols = Integer.parseInt(input);

        if (numOfSymbols < codeLength || numOfSymbols > 36) {
            System.out.println("Error: " + numOfSymbols + " isn't a valid number.");
            System.exit(0);
        }
        return numOfSymbols;
    }

    //Method generates a random code with unique digits and characters
    private String generateCode(int numOfSymbols, int codeLength) {

        StringBuilder code = new StringBuilder();
        Map<Character, Integer> values = new HashMap<>();
        int index = 0;

        while (true) {
            Character value = chars.charAt(random.nextInt(numOfSymbols));
            if (code.length() == codeLength) {
                break;
            }

            if (values.containsKey(value)) {
                continue;
            } else {
                values.put(value, index);
                code.append(value);
                index++;
            }
        }

        return code.toString();
    }

    //Method informs the user that the code was generated
    public void secretCode() {
        String hideCode = this.code.replaceAll("[0-9a-z]", "*");

        int index = 0;
        int lastNumIndex = 9;
        StringBuilder range = new StringBuilder();
        if (numOfSymbols <= 9) {
            range.append("("  + chars.charAt(index) + "-" + chars.charAt(lastNumIndex)
                    + ", " + chars.charAt(lastNumIndex + 1) + "-" + chars.charAt(numOfSymbols - 1) + ")");
        } else if(numOfSymbols == 10) {
            range.append("("  + chars.charAt(index) + "-" + chars.charAt(lastNumIndex)
                    + ", " + chars.charAt(lastNumIndex + 1) + ")");
        }
        else {
            range.append("("  + chars.charAt(index) + "-" + chars.charAt(numOfSymbols - 1) + ")");
        }
        System.out.println("The secret code is prepared: " + hideCode + " " + range + ".");
        System.out.println("Okay, let's start a game!");
    }

    // Method checks the number of bulls
    private int bulls(String userInput) {

        int bulls = 0;

        for (int i = 0; i < this.code.length(); ++i) {
            int secretDigit = this.code.charAt(i) - '0';
            int guessDigit = userInput.charAt(i) - '0';

            if (secretDigit == guessDigit) {
                bulls++;
            }
        }
        return bulls;
    }

    //Method checks the number of cows
    private int cows(String usersGuess) {
        int cows = 0;
        int[] secretCout = new int[chars.length()];
        int[] guessCount = new int[chars.length()];

        for (int i = 0; i < this.code.length(); ++i) {

            int secretDigit = chars.indexOf(this.code.charAt(i));
            int guessDigit = chars.indexOf(usersGuess.charAt(i));

            if (secretDigit != guessDigit) {
                ++secretCout[secretDigit];
                ++guessCount[guessDigit];
            }
        }
        for (int i = 0; i < secretCout.length; ++i) {
            cows += Math.min(secretCout[i], guessCount[i]);

        }
        return cows;
    }

    // Method requests user input and checks if the secrete code was guessed
    public void bullsAndCows() {

        int turn = 0;
        int bulls = 0;
        int cows = 0;

        while ( bulls != this.code.length()) {
            System.out.print("Turn " + turn
                    + ": " + "\n" +
                    "> " );

            String usersGuess = scanner.next();

            cows = cows(usersGuess);
            bulls = bulls(usersGuess);

            if (cows == 0 && bulls == 0) {
                System.out.println("None.");
            } else if (cows == 0 && bulls > 0 && bulls < this.code.length()) {
                System.out.println("Grade: " + bulls + " bull(s).");
            } else if (bulls == 0 && cows > 0) {
                System.out.println("Grade: " + cows + " cow(s).");
            } else if (cows > 0 && bulls > 0 && bulls < this.code.length()) {
                System.out.println("Grade: " + cows + " cow(s) and " + bulls
                        + " bull(s).");
            }
            turn++;
        }
        System.out.print("Grade: " + bulls  + " bulls." + "\n"
                + "Congrats! You guessed the secret code." );
        close();
    }

    // Method closes the scanner object
    private void close() {
        scanner.close();
    }

}
