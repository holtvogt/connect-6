package edu.kit.informatik.game;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum represents the players in the Connect Six game.
 * Each player is identified by a unique player ID.
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

    private static final Map<Integer, Player> PLAYER_MAP = new HashMap<>();
    private final int playerID;

    static {
        for (Player player : Player.values()) {
            PLAYER_MAP.put(player.playerID, player);
        }
    }

    /**
     * Creates a player with the specified player ID.
     *
     * @param playerID The unique ID of the player.
     */
    Player(final int playerID) {
        this.playerID = playerID;
    }

    /**
     * Returns the player ID.
     *
     * @return The player ID.
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Returns the player by their player ID.
     *
     * @param id The player ID.
     * @return The {@link Player} corresponding to the given ID, or {@code null} if no such player exists.
     */
    public static Player getPlayerByID(final int id) {
        return PLAYER_MAP.get(id);
    }

    /**
     * Returns the maximum number of players in the game.
     *
     * @return The maximum number of players.
     */
    public static int getMaxAmountOfPlayers() {
        return PLAYER_MAP.size();
    }

    /**
     * Returns the next player in the sequence.
     *
     * @param playerAmount The total number of players in the game.
     * @return The next {@link Player} in the sequence.
     * @throws IllegalArgumentException If the playerAmount is invalid.
     */
    public Player getNextPlayer(final int playerAmount) {
        if (playerAmount <= 0 || playerAmount > PLAYER_MAP.size()) {
            throw new IllegalArgumentException("Invalid player amount: " + playerAmount);
        }
        return getPlayerByID((playerID + 1) % playerAmount);
    }
}
