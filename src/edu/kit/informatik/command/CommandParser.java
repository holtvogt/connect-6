package edu.kit.informatik.command;

import java.util.regex.Matcher;

import edu.kit.informatik.InvalidInputException;

/**
 * The {@code CommandParser} class is responsible for parsing user input
 * and matching it to a valid {@link Command}.
 */
public class CommandParser {
    /**
     * Parses the user input and matches it to a valid {@link Command}.
     *
     * @param userInput The input string provided by the user.
     * @return The {@link Command} that matches the user input.
     * @throws InvalidInputException If the input does not match any valid command.
     */
    public static Command parse(String userInput) throws InvalidInputException {
        for (Command command : Command.values()) {
            Matcher matcher = command.getPattern().matcher(userInput);
            if (matcher.matches()) {
                return command;
            }
        }
        throw new InvalidInputException("invalid command.");
    }
}
