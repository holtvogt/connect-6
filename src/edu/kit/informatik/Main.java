package edu.kit.informatik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.kit.informatik.command.Command;
import edu.kit.informatik.command.CommandExecutor;
import edu.kit.informatik.command.CommandParser;
import edu.kit.informatik.game.logic.ConnectSix;
import edu.kit.informatik.game.logic.GameInitializer;

/**
 * The main class is the entry point of the Connect Six game.
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
     * The entry point for the Connect Six game.
     * <p>
     * This class initializes the game using command-line arguments and processes user commands
     * in a loop until the game ends.
     * </p>
     * 
     * <h3>Command-Line Arguments:</h3>
     * <ul>
     *   <li><b>Game Mode:</b> "standard" or "torus".</li>
     *   <li><b>Board Size:</b> An even number between 17 and 21.</li>
     *   <li><b>Player Count:</b> An integer between 2 and the maximum supported players.</li>
     * </ul>
     * 
     * <p>Example: {@code java edu.kit.informatik.Main standard 20 2}</p>
     * 
     * @param args The command line arguments.
     * @throws InvalidInputException If the input is invalid.
     * @throws NumberFormatException If the input is not a valid integer.
     */
    public static void main(final String[] args) throws InvalidInputException {
        ConnectSix connectSix = GameInitializer.initializeGame(args);

        while (Command.isRunning()) {
            try {
                String userInput = IN.readLine();
                Command command = CommandParser.parse(userInput);
                CommandExecutor.execute(command, userInput, connectSix);
            } catch (InvalidInputException invalidInputException) {
                System.out.println("Error, " + invalidInputException.getMessage());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Error, input isn't equal to an integer.");
            } catch (IOException ioException) {
                System.err.println("Error, " + ioException.getMessage());
            }
        }
    }
}
