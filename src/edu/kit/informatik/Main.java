package edu.kit.informatik;

import edu.kit.informatik.game.ConnectSix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The main class is the entry point of the connect 6 game application.
 */
public final class Main {

    /**
     * Reads text from the "standard" input stream, buffering characters to
     * provide for the efficient reading of characters, arrays, and lines. This
     * stream is already open and ready to supply input data and corresponds to
     * keyboard input.
     */
    private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Private constructor to avoid object generation.
     */
    private Main() {
        throw new AssertionError("Utility class constructor.");
    }

    /**
     * This is the program entry method main.
     *
     * @param args Array of strings of the given command line arguments.
     */
    public static void main(final String[] args) {
        ConnectSix connectSix = new ConnectSix();
        Command command = null;
        if (connectSix.entryCheck(args)) {
            do {
                try {
                    command = Command.matchingCommand(IN.readLine(), connectSix);
                } catch (InvalidInputException invalidInputException) {
                    System.out.println("Error, " + invalidInputException.getMessage());
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Error, input isn't equal to an integer.");
                } catch (IOException ioException) {
                    throw new RuntimeException(ioException);
                }
            } while (command == null || command.isRunning());
        }
    }
}
