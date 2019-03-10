package edu.kit.informatik;

import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.GameState;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;

/**
 * This class implements all necessary commands for game usage.
 * 
 * @author Björn Holtvogt
 *
 */
public enum Command {

    /**
     * Implementation of the command "place" as mentioned in the task.
     */
    // place <row number 1>;<column number 1>;<row number 2>;<column number 2>
    CMD_PLACE("place (-?\\d+);(-?\\d+);(-?\\d+);(-?\\d+)") {

        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix)
                throws InvalidInputException, NumberFormatException {

            // Game over proof in both game modes
            if (connectSix.getCurrentGamestate() == GameState.WON
                    || connectSix.getCurrentGamestate() == GameState.DRAW) {
                throw new InvalidInputException("game is over.");
            } else {
                int firstRow = Integer.parseInt(matcher.group(1));
                int firstColumn = Integer.parseInt(matcher.group(2));
                int secondRow = Integer.parseInt(matcher.group(3));
                int secondColumn = Integer.parseInt(matcher.group(4));
                connectSix.output(
                        connectSix.placeToken(firstRow, firstColumn, secondRow, secondColumn, connectSix));
            }
        }
    },

    /**
     * Implementation of the command "rowprint" as mentioned in the task.
     */
    // rowprint <row number>
    CMD_ROWPRINT("rowprint (\\d+)") {

        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix)
                throws InvalidInputException, NumberFormatException {

            boolean horizontal = true;
            int row = Integer.parseInt(matcher.group(1));
            connectSix.output(connectSix.printBoardLine(row, horizontal));
        }
    },

    /**
     * Implementation of the command "colprint" as mentioned in the task.
     */
    // colprint <column number>
    CMD_COLPRINT("colprint (\\d+)") {

        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix)
                throws InvalidInputException, NumberFormatException {

            boolean horizontal = false;
            int column = Integer.parseInt(matcher.group(1));
            connectSix.output(connectSix.printBoardLine(column, horizontal));
        }
    },

    /**
     * Implementation of the command "print" as mentioned in the task.
     */
    // print
    CMD_PRINT("print") {

        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) {

            connectSix.output(connectSix.printBoard());
        }
    },

    /**
     * Implementation of the command "state" as mentioned in the task.
     */
    // state <row number>;<column number>
    CMD_STATE("state (-?\\d+);(-?\\d+)") {

        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix)
                throws InvalidInputException, NumberFormatException {

            int row = Integer.parseInt(matcher.group(1));
            int column = Integer.parseInt(matcher.group(2));
            connectSix.output(connectSix.stateBoard(row, column));
        }
    },

    /**
     * Implementation of the command "reset" as mentioned in the task.
     */
    // reset
    CMD_RESET("reset") {

        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) {

            connectSix.output(connectSix.resetGame());
        }
    },

    /**
     * Implementation of the command "quit" as mentioned in the task.
     */
    // quit
    CMD_QUIT("quit") {

        @Override
        public void commandMethod(MatchResult matcher, ConnectSix connectSix) {

            running = false;
        }
    };

    private static boolean running = true;
    private final Pattern pattern;

    /**
     * Creates a command.
     * 
     * @param regex
     *            Regular expression for the command.
     */
    Command(final String regex) {

        this.pattern = Pattern.compile(regex);
    }

    /**
     * Returns the command which matches to the user input.
     * 
     * @param userInput
     *            Command and/or arguments for it, based on the command itself.
     * @param connectSix
     *            Reference to game control.
     * @return The matching command.
     * @throws InvalidInputException
     *             if any user input doesn't match with the command pattern.
     */
    public static Command matchingCommand(final String userInput, final ConnectSix connectSix)
            throws InvalidInputException {

        for (Command commands: Command.values()) {
            Matcher matcher = commands.pattern.matcher(userInput);
            if (matcher.matches()) {
                commands.commandMethod(matcher, connectSix);
                return commands;
            }
        }
        throw new InvalidInputException("invalid command.");
    }

    /**
     * Command dependent execution method.
     * 
     * @param matcher
     *            Successfully compiled command pattern which contains the user input for the command dependent methods.
     * @param connectSix
     *            Reference to game control.
     * @throws InvalidInputException
     *             if any user input was invalid.
     */
    protected abstract void commandMethod(MatchResult matcher, ConnectSix connectSix) throws InvalidInputException;

    /**
     * Returns the current program state.
     * 
     * @return True, if the game is still on and hasn't been quit yet. False, if the user wants to quit the game by the
     *         command quit or by entering false command line arguments at the beginning of the game.
     */
    public boolean isRunning() {

        return running;
    }
}
