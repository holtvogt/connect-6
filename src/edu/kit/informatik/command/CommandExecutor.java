package edu.kit.informatik.command;

import java.util.regex.Matcher;

import edu.kit.informatik.InvalidInputException;
import edu.kit.informatik.game.logic.ConnectSix;

/**
 * The {@code CommandExecutor} class is responsible for executing a parsed {@link Command}.
 */
public class CommandExecutor {
    /**
     * Executes the given {@link Command} using the provided user input and game instance.
     *
     * @param command The {@link Command} to execute.
     * @param userInput The raw user input string that triggered the command.
     * @param connectSix The {@link ConnectSix} game instance to operate on.
     * @throws InvalidInputException If the user input does not match the expected format for the command.
     */
    public static void execute(Command command, String userInput, ConnectSix connectSix) throws InvalidInputException {
        Matcher matcher = command.getPattern().matcher(userInput);
        if (matcher.matches()) {
            command.commandMethod(matcher, connectSix);
        } else {
            throw new InvalidInputException("invalid command format.");
        }
    }
}
