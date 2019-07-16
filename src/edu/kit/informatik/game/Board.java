package edu.kit.informatik.game;

import java.util.Arrays;

import edu.kit.informatik.exceptions.InvalidInputException;

/**
 * This class implements the given board types.
 * 
 * @author Björn Holtvogt
 *
 */
public enum Board {

	/**
	 * Standard game board.
	 */
	STANDARD(),

	/**
	 * Torus game board.
	 */
	TORUS() {

		@Override
		public String place(final int firstRow, final int firstColumn, final int secondRow, final int secondColumn,
				final ConnectSix connectSix) throws InvalidInputException {

			// Calculates a valid position on the board, if previous input wasn't placeable
			int newFirstRow = Math.floorMod(firstRow, board.length);
			int newFirstColumn = Math.floorMod(firstColumn, board.length);
			int newSecondRow = Math.floorMod(secondRow, board.length);
			int newSecondColumn = Math.floorMod(secondColumn, board.length);
			return super.place(newFirstRow, newFirstColumn, newSecondRow, newSecondColumn, connectSix);
		}

		@Override
		public String state(final int row, final int column) throws InvalidInputException {

			int newRow = Math.floorMod(row, board.length);
			int newColumn = Math.floorMod(column, board.length);
			return super.state(newRow, newColumn);
		}
	};

	private static Player[][] board;

	/**
	 * Game board constructor.
	 */
	private Board() {

	}

	/**
	 * Returns the current game board as a string.
	 * 
	 * @return The game board.
	 */
	public String getBoard() {

		return Arrays.deepToString(board).replace("null", "**").replace("], ", "\n").replace("[[", "").replace("]]", "")
				.replace("[", "").replace(",", "").replace("  ", " ");
	}

	/**
	 * Creates a game board with a new board length.
	 * 
	 * @param newBoardLength Board length.
	 */
	public void setBoard(final int newBoardLength) {

		board = new Player[newBoardLength][newBoardLength];
	}

	/**
	 * Places two player tokens on the game board with given positions.
	 * 
	 * @param firstRow     Row position of the first token.
	 * @param firstColumn  Column position of the first token.
	 * @param secondRow    Row position of the second token.
	 * @param secondColumn Column position of the second token.
	 * @param connectSix   Reference to game control.
	 * @return "OK", if the fields were empty and the token positions were different
	 *         and valid. "PX wins", if player x wins the game with his current
	 *         token placements. "draw", if the game ends with a draw.
	 * @throws InvalidInputException if the fields weren't empty nor the token
	 *                               positions were different and valid.
	 */
	public String place(final int firstRow, final int firstColumn, final int secondRow, final int secondColumn,
			final ConnectSix connectSix) throws InvalidInputException {

		String output = "";
		// Fields should be empty, token positions different and token positions valid
		if (state(firstRow, firstColumn).equals("**") && state(secondRow, secondColumn).equals("**")
				&& !(firstRow == secondRow && firstColumn == secondColumn) && firstRow >= 0 && firstRow < board.length
				&& firstColumn >= 0 && firstColumn < board.length && secondRow >= 0 && secondRow < board.length
				&& secondColumn >= 0 && secondColumn < board.length) {

			connectSix.setGameCounter(connectSix.getGameCounter() + 1);
			setField(firstRow, firstColumn, secondRow, secondColumn, connectSix.getCurrentPlayer());
			output = "OK";
		} else {
			throw new InvalidInputException("invalid placement.");
		}
		// Game state check
		if (gameState(connectSix) == GameState.WON) {
			output = connectSix.getCurrentPlayer() + " wins";
		} else if (gameState(connectSix) == GameState.DRAW) {
			output = "draw";
		} else {
			connectSix.setNextPlayer();
		}
		return output;
	}

	/**
	 * Overwrites two fields on the game board by the tokens of a current player.
	 * 
	 * @param firstRow      Row position of the first token.
	 * @param firstColumn   Column position of the first token.
	 * @param secondRow     Row position of the second token.
	 * @param secondColumn  Column position of the second token.
	 * @param currentPlayer Current player.
	 */
	private void setField(final int firstRow, final int firstColumn, final int secondRow, final int secondColumn,
			final Player currentPlayer) {

		board[firstRow][firstColumn] = currentPlayer;
		board[secondRow][secondColumn] = currentPlayer;
	}

	/**
	 * Returns the current game state.
	 * 
	 * @param game Reference to game control.
	 * @return The current game state, which is "running", "draw" or "won".
	 */
	private GameState gameState(final ConnectSix connectSix) {

		GameState state = GameState.RUNNING;
		if (checkBoard(connectSix.getCurrentPlayer())) {
			state = GameState.WON;
		} else if (!checkBoard(connectSix.getCurrentPlayer())
				&& connectSix.getGameCounter() == (board.length * board.length) / 2) {

			state = GameState.DRAW;
		}
		connectSix.setCurrentGameState(state);
		return state;
	}

	/**
	 * Checks board for possible win lines.
	 * 
	 * @param currentPlayer Current Player.
	 * @return True, if a player won the game. False, if not.
	 */
	private boolean checkBoard(final Player currentPlayer) {

		int winCounter = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == currentPlayer) {
					winCounter = 1;
					// Possible win line
					if (lineCheck(i, j, winCounter, currentPlayer)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks possible win situations.
	 * 
	 * @param row           Latest row position of a player token.
	 * @param column        Latest column position of a player token.
	 * @param winCounter    Counts the number of tokens in a row. That means, by
	 *                      adding the win counter to six, this is a win.
	 * @param currentPlayer Current player.
	 * @return True, if a line has six in a row. False, if not.
	 */
	private boolean lineCheck(final int row, final int column, final int winCounter, final Player currentPlayer) {

		// Win situations like: Vertical down, vertical up, horizontal right, horizontal
		// left, diagonal right down,
		// diagonal right up, diagonal left down, diagonal left up
		return sixInOneRow(row, column, 1, 0, winCounter, currentPlayer)
				|| sixInOneRow(row, column, -1, 0, winCounter, currentPlayer)
				|| sixInOneRow(row, column, 0, 1, winCounter, currentPlayer)
				|| sixInOneRow(row, column, 0, -1, winCounter, currentPlayer)
				|| sixInOneRow(row, column, 1, 1, winCounter, currentPlayer)
				|| sixInOneRow(row, column, -1, 1, winCounter, currentPlayer)
				|| sixInOneRow(row, column, 1, -1, winCounter, currentPlayer)
				|| sixInOneRow(row, column, -1, -1, winCounter, currentPlayer);
	}

	/**
	 * Checks every partnered field of a player token for a possible six in a row.
	 * 
	 * @param row                Latest row position of a player token.
	 * @param column             Latest column position of a player token.
	 * @param rowChangeFactor    Changing factor of a row: 1 is down, -1 is up.
	 * @param columnChangeFactor Changing factor of a column: 1 is right, -1 is
	 *                           left.
	 * @param winCounter         Counts the number of tokens in a row. That means,
	 *                           by adding the win counter to six, this is a win.
	 * @param currentPlayer      Current player.
	 * @return True, if there has been six partnered player tokens in a row. False,
	 *         if not.
	 */
	private boolean sixInOneRow(final int row, final int column, final int rowChangeFactor,
			final int columnChangeFactor, final int winCounter, final Player currentPlayer) {

		int currentWinCounter = winCounter;
		int newRow = row + rowChangeFactor;
		int newColumn = column + columnChangeFactor;
		boolean notInField = newRow < 0 || newRow == board.length || newColumn < 0 || newColumn == board.length;
		// Next token would be out of array (and wouldn't be worth to proof in standard
		// game mode)
		if (!(notInField && winCounter < 6)) {
			if (board[newRow][newColumn] == currentPlayer) {
				currentWinCounter++;
				if (currentWinCounter == 6 || sixInOneRow(newRow, newColumn, rowChangeFactor, columnChangeFactor,
						currentWinCounter, currentPlayer)) {

					return true;
				}
			}
			// Out of array token is only be available in torus game mode
		} else if (notInField && this == TORUS) {
			// Should never be executed in standard game mode
			return gameModeSpecificator(newRow, newColumn, rowChangeFactor, columnChangeFactor, currentWinCounter,
					currentPlayer);
		}
		return false;
	}

	/**
	 * Algorithm to check partnered player tokens on a torus game board.
	 * <p>
	 * <b>Will never be executed in a standard game mode.</b>
	 * 
	 * @param newRow             A possible partnered player token with row position
	 *                           modified by row change factor.
	 * @param newColumn          A possible partnered player token with column
	 *                           position modified by column change factor.
	 * @param rowChangeFactor    Changing factor of a row: 1 is down, -1 is up.
	 * @param columnChangeFactor Changing factor of a column: 1 is right, -1 is
	 *                           left.
	 * @param winCounter         Counts the number of tokens in a row. That means,
	 *                           by adding the win counter to six, this is a win.
	 * @param currentPlayer      Current player.
	 * @return True, if the game mode is torus and a partnered six in a row player
	 *         token has been found. False, if not.
	 */
	private boolean gameModeSpecificator(int newRow, int newColumn, int rowChangeFactor, int columnChangeFactor,
			int winCounter, Player currentPlayer) {

		int currentWinCounter = winCounter;
		// Calculates the fitting partner to the current sixInOneRow - algorithm which
		// actually be outside the board
		int torusRow = Math.floorMod(newRow, board.length);
		int torusColumn = Math.floorMod(newColumn, board.length);
		if (board[torusRow][torusColumn] == currentPlayer) {
			currentWinCounter++;
			if (currentWinCounter == 6 || sixInOneRow(torusRow, torusColumn, rowChangeFactor, columnChangeFactor,
					currentWinCounter, currentPlayer)) {

				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a row as a string, if horizontal is true or a column as a string, if
	 * horizontal is false and if the given index is valid.
	 * 
	 * @param index      Row or column index which wants to be printed.
	 * @param horizontal True, if it's a row. False, if it's a column.
	 * @return The row or column of the game board as a string.
	 * @throws InvalidInputException if index isn't located on the game board.
	 */
	public String printRowOrCol(final int index, final boolean horizontal) throws InvalidInputException {

		if (index >= 0 && index < board.length) {
			Player[] line = new Player[board.length];
			// Row print
			if (horizontal) {
				for (int i = 0; i < board.length; i++) {
					line[i] = board[index][i];
				}
				// Column print
			} else {
				for (int i = 0; i < board.length; i++) {
					line[i] = board[i][index];
				}
			}
			return Arrays.deepToString(line).replace("null", "**").replace("[", "").replace(" ", "").replace(",", " ")
					.replace("]", "");
		}
		throw new InvalidInputException("unprintable row/column chosen.");
	}

	/**
	 * Returns the occupancy of the chosen game field.
	 * 
	 * @param row    Row position of the token.
	 * @param column Column position of the token.
	 * @return The occupancy of the chosen game field.
	 * @throws InvalidInputException if the given token position doesn't exist in
	 *                               "standard" game mode.
	 */
	public String state(final int row, final int column) throws InvalidInputException {

		if (row >= 0 && row < board.length && column >= 0 && column < board.length) {
			// Empty field
			if (board[row][column] == null) {
				return "**";
			} else {
				return board[row][column].toString();
			}
		}
		throw new InvalidInputException("invalid row or column.");
	}
}
