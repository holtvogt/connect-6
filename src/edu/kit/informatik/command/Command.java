package edu.kit.informatik.command;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import edu.kit.informatik.InvalidInputException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.GameState;

/**
 * This class implements all necessary commands for game usage.
 */
public enum Command {
    /**
     * Command to place tokens on the board.
     * <p>
     * Example:
     * <pre>
     * place 1;2;3;4
     * </pre>
     * Places tokens at (1,2) and (3,4).
     */
    PLACE("place (-?\\d+);(-?\\d+);(-?\\d+);(-?\\d+)") {
        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) throws InvalidInputException {
            if (connectSix.getCurrentGamestate() == GameState.WON || connectSix.getCurrentGamestate() == GameState.DRAW) {
                throw new InvalidInputException("game is over.");
            }
            int firstRow = Integer.parseInt(matcher.group(1));
            int firstColumn = Integer.parseInt(matcher.group(2));
            int secondRow = Integer.parseInt(matcher.group(3));
            int secondColumn = Integer.parseInt(matcher.group(4));
            System.out.println(connectSix.placeToken(firstRow, firstColumn, secondRow, secondColumn, connectSix));
        }
    },

    /**
     * Command to print a specific row of the board.
     * <p>
     * Example:
     * <pre>
     * rowprint 3
     * </pre>
     * Prints the third row of the board.
     */
    ROWPRINT("rowprint (\\d+)") {
        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) throws InvalidInputException {
            int row = Integer.parseInt(matcher.group(1));
            System.out.println(connectSix.printBoardLine(row, true));
        }
    },

    /**
     * Command to print a specific column of the board.
     * <p>
     * Example:
     * <pre>
     * colprint 2
     * </pre>
     * Prints the second column of the board.
     */
    COLPRINT("colprint (\\d+)") {
        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) throws InvalidInputException {
            int column = Integer.parseInt(matcher.group(1));
            System.out.println(connectSix.printBoardLine(column, false));
        }
    },

    /**
     * Command to print the entire board.
     * <p>
     * Example:
     * <pre>
     * print
     * </pre>
     * Prints the current state of the board.
     */
    PRINT("print") {
        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) {
            System.out.println(connectSix.printBoard());
        }
    },

    /**
     * Command to get the state of a specific cell on the board.
     * <p>
     * Example:
     * <pre>
     * state 1;2
     * </pre>
     * Prints the state of the cell at (1,2).
     */
    STATE("state (-?\\d+);(-?\\d+)") {
        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) throws InvalidInputException {
            int row = Integer.parseInt(matcher.group(1));
            int column = Integer.parseInt(matcher.group(2));
            System.out.println(connectSix.stateBoard(row, column));
        }
    },

    /**
     * Command to reset the game.
     * <p>
     * Example:
     * <pre>
     * reset
     * </pre>
     * Resets the game to its initial state.
     */
    RESET("reset") {
        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) {
            System.out.println(connectSix.resetGame());
        }
    },

    /**
     * Command to quit the game.
     * <p>
     * Example:
     * <pre>
     * quit
     * </pre>
     * Exits the game.
     */
    QUIT("quit") {
        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) {
            RUNNING = false;
        }
    };

    private static boolean RUNNING = true;
    private final Pattern pattern;

    /**
     * Creates a command.
     *
     * @param regex Regular expression for the command.
     */
    Command(final String regex) {
        this.pattern = Pattern.compile(regex);
    }

    /**
     * Returns the regex pattern for this command.
     *
     * @return The regex pattern.
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Executes the command-specific logic.
     *
     * @param matcher Successfully compiled command pattern containing user input.
     * @param connectSix Reference to the game control.
     * @throws InvalidInputException if the user input is invalid.
     */
    protected abstract void commandMethod(MatchResult matcher, ConnectSix connectSix) throws InvalidInputException;

    /**
     * Checks if the game is still running.
     *
     * @return True if the game is running, false if the user has quit.
     */
    public static boolean isRunning() {
        return RUNNING;
    }
}
