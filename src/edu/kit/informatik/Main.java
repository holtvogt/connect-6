package edu.kit.informatik;

import edu.kit.informatik.game.ConnectSix;

/**
 * 
 * @author Björn Holtvogt
 *
 */
public final class Main {

    /**
     * Private constructor to avoid object generation.
     * 
     * @deprecated Utility-class constructor.
     */
    @Deprecated
    private Main() {

        throw new AssertionError("Utility class constructor.");
    }

    /**
     * This is the program entry method main.
     * 
     * @param args
     *            Array of strings of the given command line arguments.
     */
    public static void main(final String[] args) {

        ConnectSix connectSix = new ConnectSix();
        Command command = null;
        if (connectSix.entryCheck(args)) {
            do {
                try {
                    command = Command.matchingCommand(Terminal.readLine(), connectSix);
                } catch (InvalidInputException invalidInputException) {
                    Terminal.printError(invalidInputException.getMessage());
                } catch (NumberFormatException numberFormatException) {
                    Terminal.printError("input isn't equal to an integer.");
                }
            } while (command == null || command.isRunning());
        }
    }

    /**
     * Communication of the program with the user/s.
     * 
     * @param text
     *            Answer of an command input to the user/s.
     */
    public static void textOutput(String text) {

        Terminal.printLine(text);
    }
}
