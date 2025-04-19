package edu.kit.informatik.game.board;

/**
 * Represents a torus game board.
 */
public class TorusBoard extends Board {
    @Override
    protected int wrapIndex(int index, int boardLength) {
        // Wrap indices for torus boards
        return Math.floorMod(index, boardLength);
    }
}
