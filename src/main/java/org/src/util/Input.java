package org.src.util;

import java.util.Scanner;

/**
 * Utility class for handling console input operations.
 *
 * This class provides static methods to read strings, integers, and doubles
 * from the standard input, with built-in error handling for invalid formats.
 */

public final class Input {

    private static final Scanner userInput = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message);
        return userInput.nextLine();
    }

    public static int readInt(String message){
        while (true){
            try{
                System.out.print(message);
                return Integer.parseInt(userInput.nextLine());
            }
            catch (NumberFormatException exception){
                System.out.println("Invalid number...Try again.");
            }
        }

    }

    public static double readDouble(String message){
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(userInput.nextLine());
            }
            catch (NumberFormatException exception){
                System.out.println("Invalid Number..Try again.");
            }
        }

    }
}
