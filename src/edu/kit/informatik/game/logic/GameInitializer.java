package edu.kit.informatik.game.logic;

import edu.kit.informatik.InvalidInputException;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.board.Board;
import edu.kit.informatik.game.board.StandardBoard;
import edu.kit.informatik.game.board.TorusBoard;

/**
 * Utility class for initializing a Connect Six game.
 * <p>
 * The {@code GameInitializer} class is responsible for validating command-line arguments
 * and creating a fully initialized {@link ConnectSix} game instance. It ensures that the
 * game mode, board size, and player count are valid before initializing the game.
 * </p>
 * 
 * @see ConnectSix
 */
public class GameInitializer {
    private static final int MIN_EDGE_LENGTH = 17;
    private static final int MAX_EDGE_LENGTH = 21;
    private static final int MIN_AMOUNT_OF_PLAYERS = 2;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameInitializer() {
        throw new AssertionError("Utility class constructor.");
    }

    /**
     * Initializes a ConnectSix game instance based on the provided arguments.
     *
     * @param args An array of strings containing the game mode ("standard" or "torus"),
     *             the board size, and the number of players.
     * @return A fully initialized ConnectSix instance.
     * @throws InvalidInputException If any of the arguments are invalid.
     * @throws NumberFormatException If the board size or player count is not a valid integer.
     */
    public static ConnectSix initializeGame(final String[] args) throws InvalidInputException {
        if (args.length != 3) {
            throw new InvalidInputException("invalid number of arguments. Expected 3 arguments.");
        }

        String gameType = args[0];
        if (!isValidGameType(gameType)) {
            throw new InvalidInputException("invalid game type. Expected 'standard' or 'torus'.");
        }

        int boardSize = parseInteger(args[1], "board size must be a valid integer.");
        if (!isValidBoardSize(boardSize)) {
            throw new InvalidInputException("invalid board size. Must be an even number between " + MIN_EDGE_LENGTH + " and " + MAX_EDGE_LENGTH + ".");
        }

        int playerCount = parseInteger(args[2], "player count must be a valid integer.");
        if (!isValidPlayerCount(playerCount)) {
            throw new InvalidInputException("invalid player count. Must be between " + MIN_AMOUNT_OF_PLAYERS + " and " + Player.getMaxAmountOfPlayers() + ".");
        }

        Board board = gameType.equals("standard") ? new StandardBoard() : new TorusBoard();
        return new ConnectSix(board, boardSize, playerCount);
    }

    /**
     * Parses a string into an integer and throws an exception with a custom message if parsing fails.
     *
     * @param input The string to parse.
     * @param errorMessage The error message to include in the exception if parsing fails.
     * @return The parsed integer.
     * @throws NumberFormatException If the input is not a valid integer.
     */
    private static int parseInteger(String input, String errorMessage) throws NumberFormatException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(errorMessage);
        }
    }

    /**
     * Validates the game type.
     *
     * @param gameType The game type to validate.
     * @return True if the game type is valid, false otherwise.
     */
    private static boolean isValidGameType(String gameType) {
        return gameType.equals("standard") || gameType.equals("torus");
    }

    /**
     * Validates the board size.
     *
     * @param boardSize The board size to validate.
     * @return True if the board size is valid, false otherwise.
     */
    private static boolean isValidBoardSize(int boardSize) {
        return boardSize >= MIN_EDGE_LENGTH && boardSize <= MAX_EDGE_LENGTH && boardSize % 2 == 0;
    }

    /**
     * Validates the player count.
     *
     * @param playerCount The player count to validate.
     * @return True if the player count is valid, false otherwise.
     */
    private static boolean isValidPlayerCount(int playerCount) {
        return playerCount >= MIN_AMOUNT_OF_PLAYERS && playerCount <= Player.getMaxAmountOfPlayers();
    }
}
