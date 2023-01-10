package bullscows;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.Arrays;


public class Main {
    /*  Simple code generator. It takes a string, shuffles it randomly
        and cut to desired length. */
    static String generateCode(int length, int numOfSymbols, String set) {
        StringBuilder sb = new StringBuilder(64);
        sb.append(set);
        sb.delete(numOfSymbols, sb.length());
        String sbCode = sb.toString();
        List<String> characters = Arrays.asList(sbCode.split(""));
        Collections.shuffle(characters);
        String afterShuffle = "";
        for (String character : characters) {
                afterShuffle += character;
        }
        StringBuilder sb2 = new StringBuilder(afterShuffle);
        sb2.delete(length, sb2.length());
        return sb2.toString();
    }

    public static void main(String[] args) {
        // Set is used to generate random number.
        String set = "0123456789abcdefghijklmnopqrstuvwxyz";
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter secret code's length:");
        String strLength = sc.nextLine();
        // result1 and result2 are boolean to check if user input is a valid number.
        boolean result1 = strLength.matches("[0-9 ]+");
        if (!result1) {
            System.out.format("Error: \"%s\" isn't a valid number.", strLength);
        } else if (Integer.parseInt(strLength) > 36 || Integer.parseInt(strLength) < 1) {
            System.out.format("Error: can't generate a secret number with a length of %d " +
                    "because there aren't enough unique digits.", Integer.parseInt(strLength));
        } else {
            int length = Integer.parseInt(strLength);
            System.out.println("Input the number of possible symbols in the code:");
            String strNumOfSymbols = sc.nextLine();
            boolean result2 = strNumOfSymbols.matches("[0-9 ]+");
            if (!result2) {
                System.out.format("Error: \"%s\" isn't a valid number.", strNumOfSymbols);
            } else if (length > Integer.parseInt(strNumOfSymbols)) {
                System.out.format("Error: it's not possible to generate a code with a length of " +
                        "%d with %d unique symbols.", length, Integer.parseInt(strNumOfSymbols));
            } else if (Integer.parseInt(strNumOfSymbols) > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else {
                int numOfSymbols = Integer.parseInt(strNumOfSymbols);
                System.out.println("The secret is prepared: ");
                for (int i = 0; i < length; i++) {
                    System.out.print("*");
                }
                if (numOfSymbols > 10) {
                    System.out.format(" (0-9, a-%s).%n", set.charAt(numOfSymbols - 1));
                } else if (numOfSymbols < 10 && numOfSymbols > 1) {
                    System.out.format(" (0-%s).%n", set.charAt(numOfSymbols - 1));
                } else {
                    System.out.println(" (0).");
                }
                System.out.println("Okay, let's start a game!");
                String strCode = generateCode(length, numOfSymbols, set);
                int turn = 1;
                int bulls = 0;
                // Loop for checking how many bulls and cows user guess has.
                // It terminates after guess match generated code.
                while (bulls != strCode.length()) {
                    System.out.format("Turn %d:\n", turn);
                    turn += 1;
                    String guess = sc.nextLine();
                    char[] guessArr = guess.toCharArray();
                    int counter = 0;
                    bulls = 0;
                    for (int i = 0; i < strCode.length(); i++) {
                        if (strCode.indexOf(guessArr[i]) != -1) {
                            counter++;
                        }
                        if (guess.charAt(i) == strCode.charAt(i)) {
                            bulls++;
                        }
                    }
                    int cows = counter - bulls;
                    if (bulls == 0 && cows == 0) {
                        System.out.println("Grade: None.\n");
                    } else if (bulls == strCode.length()) {
                        System.out.format("Grade: %d bull(s)\n", bulls);
                        System.out.println("Congratulations! You guessed the secret code.");
                    } else {
                        System.out.format("Grade: %d bull(s) and %d cow(s).\n", bulls, cows);
                    }
                }
            }
        }
    }
}
