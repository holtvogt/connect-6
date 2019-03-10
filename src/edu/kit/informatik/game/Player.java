package edu.kit.informatik.game;

import java.util.Map;
import java.util.HashMap;

/**
 * This class represents the players in the game.
 * 
 * @author Björn Holtvogt
 *
 */
public enum Player {

    /**
     * Player 1.
     */
    P1(0),

    /**
     * Player 2.
     */
    P2(1),

    /**
     * Player 3.
     */
    P3(2),

    /**
     * Player 4.
     */
    P4(3);

    private static Map<Integer, Player> playerMap = new HashMap<>();
    private int playerID;

    /**
     * Creates a player with his player ID.
     * 
     * @param playerID
     *            Player ID.
     */
    private Player(final int playerID) {

        this.playerID = playerID;
    }

    static {

        for (Player player: Player.values()) {
            playerMap.put(player.getPlayerID(), player);
        }
    }

    /**
     * Returns the player ID.
     * 
     * @return The player ID.
     */
    private int getPlayerID() {

        return playerID;
    }

    /**
     * Returns the player by his player ID.
     * 
     * @param id
     *            Player ID.
     * @return The Player.
     */
    private Player getPlayerByID(final int id) {

        return playerMap.get(id);
    }

    /**
     * Returns the maximum amount of players in this game.
     * 
     * @return The maximum amount of players in this game.
     */
    public int getMaxAmountOfPlayers() {

        return playerMap.size();
    }

    /**
     * Returns the next player.
     * 
     * @param playerAmount
     *            The maximum amount of players in this game.
     * @return The next player.
     */
    public Player getNextPlayer(final int playerAmount) {

        if (playerAmount != playerMap.size()) {
            return getPlayerByID((playerID + 1) % playerAmount);
        } else {
            return getPlayerByID((playerID + 1) % playerMap.size());
        }
    }
}
