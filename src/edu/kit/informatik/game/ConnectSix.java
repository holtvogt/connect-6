package edu.kit.informatik.game;

import edu.kit.informatik.InvalidInputException;

/**
 * This class processes all game interactions from user(s) and responds via command line.
 */
public class ConnectSix {

    private static final int MIN_EDGE_LENGTH = 17;
    private static final int MAX_EDGE_LENGTH = 21;
    private static final int MIN_AMOUNT_OF_PLAYERS = 2;

    private Board board;
    private Player currentPlayer;
    private GameState currentGameState;
    private int boardLength;
    private int playerAmount;
    private int gameCounter;

    /**
     * Creates the game control.
     */
    public ConnectSix() {
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
    public GameState getCurrentGamestate() {
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
     * Checks command line input for correctness.
     *
     * @param commandLineInput Array of strings which contains the game mode
     *                         specification.
     * @return True, if the command line input contains exactly three main
     * characteristics: Game mode, which can be "standard" or "torus". Field
     * size factor, which has a given minimum and maximum length and also
     * has to be an even number. Number of players, who are a minimum of two
     * and a maximum of four. False, if not.
     */
    public boolean entryCheck(final String[] commandLineInput) {
        try {
            if (commandLineInput.length == 3
                    && (commandLineInput[0].equals("standard") || commandLineInput[0].equals("torus"))
                    && Integer.parseInt(commandLineInput[1]) > MIN_EDGE_LENGTH
                    && Integer.parseInt(commandLineInput[1]) < MAX_EDGE_LENGTH
                    && Integer.parseInt(commandLineInput[1]) % 2 == 0
                    && Integer.parseInt(commandLineInput[2]) >= MIN_AMOUNT_OF_PLAYERS
                    && Integer.parseInt(commandLineInput[2]) <= currentPlayer.getMaxAmountOfPlayers()) {

                String gameType = commandLineInput[0];
                board = gameType.equals("standard") ? Board.STANDARD : Board.TORUS;
                boardLength = Integer.parseInt(commandLineInput[1]);
                playerAmount = Integer.parseInt(commandLineInput[2]);
                // Empty board is creatable, if every input was correct
                createBoard();
            } else {
                System.out.println("Error, invalid amount of command line arguments or unknown game mode entered.");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Error, invalid command line arguments.");
        }
        return true;
    }

    /**
     * Returns "OK" as an answer of the method's execution and sets token based on
     * the current game mode.
     *
     * @param firstRow     Row position of the first token.
     * @param firstColumn  Column position of the first token.
     * @param secondRow    Row position of the second token.
     * @param secondColumn Column position of the second token.
     * @param game         Reference to game control.
     * @return "OK", if first- and second token positions are okay.
     * @throws InvalidInputException if anything of the token positions are invalid.
     */
    public String placeToken(final int firstRow, final int firstColumn, final int secondRow, final int secondColumn,
                             final ConnectSix game) throws InvalidInputException {

        if (board == Board.STANDARD) {
            return Board.STANDARD.place(firstRow, firstColumn, secondRow, secondColumn, game);
        } else {
            return Board.TORUS.place(firstRow, firstColumn, secondRow, secondColumn, game);
        }
    }

    /**
     * Returns a row or column as a result of the printRowOrCol - method.
     *
     * @param index      Row or column index which wants to be printed.
     * @param horizontal True, if it's a row. False, if it's a column.
     * @return The row or column of the game board as a string.
     * @throws InvalidInputException if index isn't located on the game board.
     */
    public String printBoardLine(final int index, final boolean horizontal) throws InvalidInputException {
        return Board.STANDARD.printRowOrCol(index, horizontal);
    }

    /**
     * Returns the game board.
     *
     * @return The game board as string.
     */
    public String printBoard() {
        return board.getBoard();
    }

    /**
     * Returns the result of the state - method based on the current game mode.
     *
     * @param row    Row position of the token.
     * @param column Column position of the token.
     * @return The occupancy of the chosen game field.
     * @throws InvalidInputException if the given token position doesn't exist in
     *                               "standard" game mode.
     */
    public String stateBoard(final int row, final int column) throws InvalidInputException {
        if (board == Board.STANDARD) {
            return Board.STANDARD.state(row, column);
        } else {
            return Board.TORUS.state(row, column);
        }
    }

    /**
     * Resets the game.
     *
     * @return "OK" as an answer of this methods execution.
     */
    public String resetGame() {
        createBoard();
        setGameCounter(0);
        setCurrentPlayer(Player.P1);
        setCurrentGameState(GameState.RUNNING);
        return "OK";
    }

    /**
     * Creates a new and empty game board.
     */
    private void createBoard() {
        board.setBoard(boardLength);
    }
}
