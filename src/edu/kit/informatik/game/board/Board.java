package edu.kit.informatik.game.board;

import java.util.Arrays;

import edu.kit.informatik.InvalidInputException;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.logic.ConnectSix;
import edu.kit.informatik.game.logic.GameState;

/**
 * Abstract base class for game boards.
 */
public abstract class Board {
    protected static final int WINNING_COUNT = 6;
    protected static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, // Vertical
        {0, 1}, {0, -1}, // Horizontal
        {1, 1}, {-1, -1}, // Diagonal (top-left to bottom-right)
        {1, -1}, {-1, 1}  // Diagonal (top-right to bottom-left)
    };

    protected Player[][] board;

    /**
     * Initializes the board with the specified size.
     *
     * @param size The size of the board (length and width).
     */
    public void initialize(int size) {
        board = new Player[size][size];
    }

    /**
     * Returns the current game board as a string.
     *
     * @return The game board.
     */
    public String getBoard() {
        // Some magic board creation
        return Arrays.deepToString(board)
                .replace("null", "**")
                .replace("], ", "\n")
                .replace("[[", "")
                .replace("]]", "")
                .replace("[", "")
                .replace(",", "")
                .replace("  ", " ");
    }

    /**
     * Prints a row or column of the board.
     *
     * @param index The index of the row or column.
     * @param horizontal True to print a row, false to print a column.
     * @return The row or column as a string.
     * @throws InvalidInputException If the index is out of bounds.
     */
    public String printRowOrColumn(int index, boolean horizontal) throws InvalidInputException {
        if (index < 0 || index >= board.length) {
            throw new InvalidInputException("invalid row or column index: " + index);
        }

        Player[] line = new Player[board.length];
        for (int i = 0; i < board.length; i++) {
            line[i] = horizontal ? board[index][i] : board[i][index];
        }
        
        return Arrays.deepToString(line)
                .replace("null", "**")
                .replace("[", "")
                .replace(" ", "")
                .replace(",", " ")
                .replace("]", "");
    }

    /**
     * Returns the state of a specific cell on the board.
     *
     * @param row The row of the cell.
     * @param column The column of the cell.
     * @return The state of the cell as a string.
     * @throws InvalidInputException If the cell is out of bounds.
     */
    public String state(int row, int column) throws InvalidInputException {
        row = wrapIndex(row, board.length);
        column = wrapIndex(column, board.length);

        if (!isValidPosition(row, column)) {
            throw new InvalidInputException("invalid row or column.");
        }

        return isEmptyCell(row, column) ? "**" : board[row][column].toString();
    }

    /**
     * Places two tokens on the board.
     *
     * @param firstRow Row position of the first token.
     * @param firstColumn Column position of the first token.
     * @param secondRow Row position of the second token.
     * @param secondColumn Column position of the second token.
     * @param connectSix Reference to the game logic.
     * @return A message indicating the result of the placement.
     * @throws InvalidInputException If the placement is invalid.
     */
    public String place(int firstRow, int firstColumn, int secondRow, int secondColumn, ConnectSix connectSix)
            throws InvalidInputException {
        validatePlacement(firstRow, firstColumn, secondRow, secondColumn);

        // Wrap indices for specific board types
        firstRow = wrapIndex(firstRow, board.length);
        firstColumn = wrapIndex(firstColumn, board.length);
        secondRow = wrapIndex(secondRow, board.length);
        secondColumn = wrapIndex(secondColumn, board.length);

        // Place tokens on the board
        setField(firstRow, firstColumn, secondRow, secondColumn, connectSix.getCurrentPlayer());
        connectSix.setGameCounter(connectSix.getGameCounter() + 1);

        // Determine and return the game state
        return determineGameState(connectSix);
    }

    /**
     * Abstract method to wrap indices for specific board types.
     *
     * @param index The index to wrap.
     * @param boardLength The length of the board.
     * @return The wrapped index.
     */
    protected abstract int wrapIndex(int index, int boardLength);

    /**
     * Checks if a position is valid on the board.
     *
     * @param row The row index.
     * @param column The column index.
     * @return True if the position is valid, false otherwise.
     */
    protected boolean isValidPosition(int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board.length;
    }

    /**
     * Checks if a cell is empty.
     *
     * @param row The row index.
     * @param column The column index.
     * @return True if the cell is empty, false otherwise.
     */
    protected boolean isEmptyCell(int row, int column) {
        return board[row][column] == null;
    }

    /**
     * Validates the placement of tokens on the board.
     *
     * @param firstRow Row position of the first token.
     * @param firstColumn Column position of the first token.
     * @param secondRow Row position of the second token.
     * @param secondColumn Column position of the second token.
     * @throws InvalidInputException If the placement is invalid.
     */
    private void validatePlacement(int firstRow, int firstColumn, int secondRow, int secondColumn)
            throws InvalidInputException {
        if (!isValidPosition(firstRow, firstColumn) || !isValidPosition(secondRow, secondColumn)) {
            throw new InvalidInputException("invalid row or column.");
        }
        if (!isEmptyCell(firstRow, firstColumn) || !isEmptyCell(secondRow, secondColumn)) {
            throw new InvalidInputException("one or both cells are already occupied.");
        }
        if (firstRow == secondRow && firstColumn == secondColumn) {
            throw new InvalidInputException("tokens must be placed in different positions.");
        }
    }

    /**
     * Determines the current game state and returns the appropriate message.
     *
     * @param connectSix Reference to the game logic.
     * @return A message indicating the current game state.
     */
    private String determineGameState(ConnectSix connectSix) {
        GameState state = getGameState(connectSix);
        connectSix.setCurrentGameState(state);

        if (state == GameState.WON) {
            return connectSix.getCurrentPlayer() + " wins";
        } else if (state == GameState.DRAW) {
            return "draw";
        }

        // Game is still running
        connectSix.setNextPlayer();
        return "OK";
    }

    /**
     * Returns the current game state.
     *
     * @param connectSix Reference to the game logic.
     * @return The current game state.
     */
    private GameState getGameState(ConnectSix connectSix) {
        if (hasWinningLine(connectSix.getCurrentPlayer())) {
            return GameState.WON;
        } else if (connectSix.getGameCounter() == (board.length * board.length) / 2) {
            return GameState.DRAW;
        } else {
            return GameState.RUNNING;
        }
    }

    /**
     * Checks if a player has a winning line on the board.
     *
     * @param currentPlayer The current player.
     * @return True if the player has a winning line, false otherwise.
     */
    private boolean hasWinningLine(Player currentPlayer) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                if (board[row][column] == currentPlayer && checkAllDirections(row, column, currentPlayer)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks all possible directions for a winning line starting from a given position.
     *
     * @param row The starting row.
     * @param column The starting column.
     * @param currentPlayer The current player.
     * @return True if a winning line is found, false otherwise.
     */
    private boolean checkAllDirections(int row, int column, Player currentPlayer) {
        for (int[] direction : DIRECTIONS) {
            if (checkDirection(row, column, direction[0], direction[1], currentPlayer, 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recursively checks a specific direction for a winning line.
     *
     * @param row The current row.
     * @param column The current column.
     * @param rowDelta The row increment for the direction.
     * @param columnDelta The column increment for the direction.
     * @param currentPlayer The current player.
     * @param count The current count of consecutive tokens.
     * @return True if a winning line is found, false otherwise.
     */
    private boolean checkDirection(int row, int column, int rowDelta, int columnDelta, Player currentPlayer, int count) {
        int newRow = wrapIndex(row + rowDelta, board.length);
        int newColumn = wrapIndex(column + columnDelta, board.length);

        if (!isValidPosition(newRow, newColumn) || board[newRow][newColumn] != currentPlayer) {
            return false;
        }

        count++;
        if (count == WINNING_COUNT) {
            return true;
        }

        return checkDirection(newRow, newColumn, rowDelta, columnDelta, currentPlayer, count);
    }

    /**
     * Sets two fields on the game board with the tokens of the current player.
     *
     * @param firstRow Row position of the first token.
     * @param firstColumn Column position of the first token.
     * @param secondRow Row position of the second token.
     * @param secondColumn Column position of the second token.
     * @param currentPlayer The current player.
     */
    private void setField(int firstRow, int firstColumn, int secondRow, int secondColumn, Player currentPlayer) {
        board[firstRow][firstColumn] = currentPlayer;
        board[secondRow][secondColumn] = currentPlayer;
    }
}
