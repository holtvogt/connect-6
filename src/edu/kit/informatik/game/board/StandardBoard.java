package edu.kit.informatik.game.board;

/**
 * Represents a standard game board.
 */
public class StandardBoard extends Board {
    @Override
    protected int wrapIndex(int index, int boardLength) {
        // No wrapping for standard boards
        return index;
    }
}
