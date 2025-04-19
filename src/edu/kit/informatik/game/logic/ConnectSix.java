package edu.kit.informatik.game.logic;

import java.util.Objects;

import edu.kit.informatik.InvalidInputException;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.board.Board;

/**
 * Manages the core logic and state of a Connect Six game.
 * <p>
 * Connect Six is a strategy board game where players take turns placing two tokens
 * on a board, aiming to connect six tokens in a row, column, or diagonal. This class
 * handles the game board, players, and game state, and provides methods for interacting
 * with the game.
 * </p>
 * 
 * <p>
 * The {@code ConnectSix} class works with a {@link Board} to manage the game board
 * and uses {@link Player} and {@link GameState} to track the current player and
 * the overall game state.
 * </p>
 */
public class ConnectSix {
    private Board board;
    private int boardSize;
    private int playerAmount;

    private Player currentPlayer;
    private GameState currentGameState;
    private int gameCounter;

    /**
     * Initializes a new Connect Six game.
     *
     * @param board The game board to use (e.g., standard or torus board).
     * @param boardSize The size of the board (must be a valid size).
     * @param playerAmount The number of players participating in the game.
     * @throws IllegalArgumentException If the board, board size, or player amount is invalid.
     */
    public ConnectSix(Board board, int boardSize, int playerAmount) {
        this.board = Objects.requireNonNull(board);
        this.boardSize = boardSize;
        this.playerAmount = playerAmount;

        this.board.initialize(boardSize);
        setCurrentPlayer(Player.P1);
        setCurrentGameState(GameState.RUNNING);
        setGameCounter(0);
    }

    /**
     * Returns the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the current player.
     *
     * @param currentPlayer Currently acting player.
     */
    public void setCurrentPlayer(final Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Sets the next player.
     */
    public void setNextPlayer() {
        currentPlayer = currentPlayer.getNextPlayer(playerAmount);
    }

    /**
     * Returns the current game state.
     *
     * @return The current game state.
     */
    public GameState getCurrentGameState() {
        return currentGameState;
    }

    /**
     * Sets the current game state.
     *
     * @param currentGameState Current game state.
     */
    public void setCurrentGameState(final GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    /**
     * Returns the game counter.
     *
     * @return The game counter.
     */
    public int getGameCounter() {
        return gameCounter;
    }

    /**
     * Sets the game counter.
     *
     * @param gameCounter Counts every game turn.
     */
    public void setGameCounter(final int gameCounter) {
        this.gameCounter = gameCounter;
    }

    /**
     * Places two tokens on the board for the current player.
     *
     * @param firstRow The row index of the first token.
     * @param firstColumn The column index of the first token.
     * @param secondRow The row index of the second token.
     * @param secondColumn The column index of the second token.
     * @return "OK" if the placement is valid, or an error message if the placement is invalid.
     * @throws InvalidInputException If the placement violates game rules (e.g., out of bounds, overlapping tokens).
     */
    public String placeToken(final int firstRow, final int firstColumn, final int secondRow, final int secondColumn)
            throws InvalidInputException {
        return board.place(firstRow, firstColumn, secondRow, secondColumn, this);
    }

    /**
     * Retrieves a row or column from the board and formats it as a string.
     *
     * @param index The index of the row or column to retrieve.
     * @param horizontal True to retrieve a row; false to retrieve a column.
     * @return A string representation of the specified row or column.
     * @throws InvalidInputException If the index is out of bounds.
     */
    public String printBoardLine(final int index, final boolean horizontal) throws InvalidInputException {
        return board.printRowOrColumn(index, horizontal);
    }

    /**
     * Returns the game board as a string.
     *
     * @return The game board as a string.
     */
    public String printBoard() {
        return board.getBoard();
    }

    /**
     * Retrieves the state of a specific cell on the board.
     *
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return A string representing the state of the cell ("**" for empty or the player's token).
     * @throws InvalidInputException If the specified cell is out of bounds.
     */
    public String stateBoard(final int row, final int column) throws InvalidInputException {
        return board.state(row, column);
    }

    /**
     * Resets the game to its initial state. The board is cleared, the game counter is reset, 
     * and the current player is set to Player 1.
     *
     * @return "OK" to indicate the game has been successfully reset.
     */
    public String resetGame() {
        board.initialize(boardSize);
        setGameCounter(0);
        setCurrentPlayer(Player.P1);
        setCurrentGameState(GameState.RUNNING);
        return "OK";
    }
}
