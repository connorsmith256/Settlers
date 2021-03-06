package shared.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.definitions.*;
import shared.transport.TransportPlayer;
import shared.transport.TransportTurnTracker;

/**
 * The scoreboard manages the victory points of all players in the game. Each
 * time a player performs an action that can affect victory points (e.g.
 * building a settlement, upgrading a settlement to a city, building a road, or
 * playing a development card), the scoreboard will update
 */
public class Scoreboard {
	private PlayerNumber largestArmyPlayer;
	private PlayerNumber longestRoadPlayer;
	private Map<PlayerNumber, Integer> points;
	private Map<PlayerNumber, Integer> activeKnights;
	private Map<PlayerNumber, Integer> builtRoads;

	private static final int NUM_STARTING_ROADS = 15;
	private static final int ROADS_THRESHOLD = 5;
	private static final int SOLDIERS_THRESHOLD = 3;
	private static final int BONUS_POINTS = 2;
	private static final int WIN_THRESHOLD = 10;

	public Scoreboard(List<TransportPlayer> player, TransportTurnTracker turnTracker) {
		this.points = this.initializeMap();
		this.activeKnights = this.initializeMap();
		this.builtRoads = this.initializeMap();

		this.largestArmyPlayer = turnTracker.largestArmy;
		this.longestRoadPlayer = turnTracker.longestRoad;

		this.countRoads(player);
		this.countKnights(player);
		this.countPoints(player);
	}

	public Scoreboard() {
		this.points = this.initializeMap();
		this.activeKnights = this.initializeMap();
		this.builtRoads = this.initializeMap();

		this.largestArmyPlayer = PlayerNumber.BANK;
		this.longestRoadPlayer = PlayerNumber.BANK;
	}

	/**
	 * Extracts the PlayerNumber .roads, .soldiers, and .victoryPoints member
	 * variables information from the Scoreboard.
	 *
	 * @param player
	 * @param playerNumber
	 * @return TransportPlayer
	 */
	public TransportPlayer getTransportPlayer(TransportPlayer player, PlayerNumber playerNumber) {
		player.roads = this.builtRoads.get(playerNumber);
		player.victoryPoints = this.points.get(playerNumber);

		return player;
	}

	/**
	 * IMPORTANT: This must be called just before or just after
	 * getTransporTracker() is called on the Game class. It requires both
	 * classes to populate all the information.
	 *
	 * @param tracker
	 * @return TransportTurnTracker tracker
	 */
	public TransportTurnTracker getTransportTurnTracker(TransportTurnTracker tracker) {
		tracker.longestRoad = this.longestRoadPlayer;
		tracker.largestArmy = this.largestArmyPlayer;

		return tracker;
	}

	private Map<PlayerNumber, Integer> initializeMap() {
		HashMap<PlayerNumber, Integer> map = new HashMap<PlayerNumber, Integer>();

		map.put(PlayerNumber.ONE, 0);
		map.put(PlayerNumber.TWO, 0);
		map.put(PlayerNumber.THREE, 0);
		map.put(PlayerNumber.FOUR, 0);
		map.put(PlayerNumber.BANK, 0);

		return map;
	}

	private void countRoads(List<TransportPlayer> players) {
		for (TransportPlayer player : players) {
			if (player == null) {
				continue;
			}
			this.builtRoads.put(player.playerIndex, NUM_STARTING_ROADS - player.roads);
		}
	}

	private void countKnights(List<TransportPlayer> players) {
		for (TransportPlayer player : players) {
			if (player == null) {
				continue;
			}
			this.activeKnights.put(player.playerIndex, player.soldiers);
		}
	}

	private void countPoints(List<TransportPlayer> players) {
		for (TransportPlayer player : players) {
			if (player == null) {
				continue;
			}
			this.points.put(player.playerIndex, player.victoryPoints);
		}
	}

	private void updatePlayerMaps(Map<PlayerNumber, Integer> playerMap, PlayerNumber player,
			int difference) {
		int currentValue = playerMap.get(player);
		currentValue += difference;
		playerMap.put(player, currentValue);
	}

	/**
	 * Adds one victory point to the player who built the dwelling
	 *
	 * @param player
	 *            the player who built the dwelling
	 */
	public void dwellingBuilt(PlayerNumber player) {
		this.updatePlayerMaps(this.points, player, 1);
	}

	/**
	 * Adds a road to a player, then recalculates the owner of the longest road
	 * and adjusts victory points, if necessary.
	 */
	public void roadBuilt(PlayerNumber player) {
		this.updatePlayerMaps(this.builtRoads, player, 1);
		this.updateLongestRoad();
	}

	private void updateLongestRoad() {
		PlayerNumber newLongestRoadPlayer = this.longestRoadPlayer;

		for (PlayerNumber player : this.builtRoads.keySet()) {
			if (this.builtRoads.get(player) > this.builtRoads.get(newLongestRoadPlayer)
					&& this.builtRoads.get(player) >= ROADS_THRESHOLD) {
				newLongestRoadPlayer = player;
			}
		}
		this.longestRoadPlayer = newLongestRoadPlayer;
	}

	/**
	 * If the card is a monument card, the player's victory points is
	 * incremented. If the card is a soldier or road building, the owner of the
	 * longest road or largest army, respectively, is recalculated and victory
	 * points are adjusted, if necessary.
	 *
	 * @param player
	 * @param type
	 */
	public void devCardPlayed(PlayerNumber player, DevCardType type) {
		switch (type) {
		case MONUMENT:
			this.updatePlayerMaps(this.points, player, 1);
			break;
		case SOLDIER:
			this.updatePlayerMaps(this.activeKnights, player, 1);
			this.updateLargestArmy();
			break;
		case ROAD_BUILD:
			this.updatePlayerMaps(this.builtRoads, player, 2);
			this.updateLongestRoad();
			break;
		default:
			break;
		}
	}

	private void updateLargestArmy() {
		PlayerNumber newLargestArmyPlayer = this.largestArmyPlayer;
		for (PlayerNumber player : this.activeKnights.keySet()) {
			if (this.activeKnights.get(player) > this.activeKnights.get(newLargestArmyPlayer)
					&& this.activeKnights.get(player) >= SOLDIERS_THRESHOLD) {
				newLargestArmyPlayer = player;
			}
		}
		this.largestArmyPlayer = newLargestArmyPlayer;
	}

	/**
	 * Returns the number of victory points for the player
	 *
	 * @param player
	 *            the desired player
	 * @return the number of victory points for the player
	 */
	public int getPoints(PlayerNumber player) {
		int points = this.points.get(player);
		if (this.longestRoadPlayer == player) {
			points += BONUS_POINTS;
		}
		if (this.largestArmyPlayer == player) {
			points += BONUS_POINTS;
		}
		return points;
	}

	public PlayerNumber getWinner() {
		PlayerNumber winner = PlayerNumber.BANK;

		for (PlayerNumber player : PlayerNumber.values()) {
			int points = this.getPoints(player);
			if (points >= this.getPoints(winner) && points >= WIN_THRESHOLD) {
				winner = player;
			}
		}

		return winner;
	}

	public PlayerNumber getLargestArmyPlayer() {
		return this.largestArmyPlayer;
	}

	public PlayerNumber getLongestRoadPlayer() {
		return this.longestRoadPlayer;
	}
}
