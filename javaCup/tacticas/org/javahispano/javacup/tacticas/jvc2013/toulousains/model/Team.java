package org.javahispano.javacup.tacticas.jvc2013.toulousains.model;

import java.util.ArrayList;
import java.util.List;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;

public class Team {
	private final List<Player> players = new ArrayList<Player>(NUM_PLAYERS);
	private boolean rival;
	private boolean initializated;
	private static final int NUM_PLAYERS = 11;
	private boolean kickOff;

	public Team() {
	}

	public Team(boolean isRival) {
		this.rival = true;
	}

	public boolean isInitializated() {
		return initializated;
	}

	public void setInitializated(boolean initializated) {
		this.initializated = initializated;
	}

	public void updateStatus(GameSituations gameSituations) {
		if (this.initializated == false) {
			this.init();
			initializated = true;
		}

		// Data of my players
		int[] canKick = gameSituations.canKick();
		int[] iterToKick = gameSituations.iterationsToKick();
		boolean[] offside = gameSituations.getOffSidePlayers();

		// Data for rival
		int[] canKickRival = gameSituations.rivalCanKick();
		int[] iterToKickRival = gameSituations.rivalIterationsToKick();

		// TODO: to use
		/*
		 * double distanceIter = gameSituations.distanceIter(playerIndex, iter,
		 * isSprint) gameSituations.distanceTotal(playerIndex, iter)
		 */

		int playerIndex = 0;
		for (final Player player : players) {
			if (this.rival == false) {
				player.setDetails(gameSituations.myPlayersDetail()[playerIndex]);
				player.setPosition(gameSituations.myPlayers()[playerIndex]);
				player.setCankick(checkPlayerCanKick(canKick, playerIndex));
				player.setItersToKick(iterToKick[playerIndex]);
				player.setOffside(offside[playerIndex]);
				player.setEnergy(gameSituations.getMyPlayerEnergy(playerIndex));
				player.setAcceleration(gameSituations.getMyPlayerAceleration(playerIndex));
				player.setIndex(playerIndex);
				player.setMark(false);
				player.setDistanceToBall(gameSituations.ballPosition().distance(player.getPosition()));
			} else {
				player.setDetails(gameSituations.rivalPlayersDetail()[playerIndex]);
				player.setPosition(gameSituations.rivalPlayers()[playerIndex]);
				player.setCankick(checkPlayerCanKick(canKickRival, playerIndex));
				player.setItersToKick(iterToKickRival[playerIndex]);
				player.setEnergy(gameSituations.getRivalEnergy(playerIndex));
				player.setAcceleration(gameSituations.getRivalAceleration(playerIndex));
				player.setIndex(playerIndex);
				player.setMark(false);
				player.setDistanceToBall(gameSituations.ballPosition().distance(player.getPosition()));
			}
			playerIndex++;
		}

		if ((gameSituations.isStarts() && rival == false) || (gameSituations.isRivalStarts() && rival == true)) {
			kickOff = true;
		} else {
			kickOff = false;
		}
	}

	private void init() {
		for (int i = 1; i <= NUM_PLAYERS; i++) {
			players.add(new Player(i));
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getGoalKeeper() {
		return players.get(0);
	}

	public Player getPlayerByNumber(int number) {
		return players.get(number - 1);
	}

	public Player getPlayerByIndex(int index) {
		return players.get(index);
	}

	private boolean checkPlayerCanKick(int[] playersCankick, int playerToCheck) {
		for (final int player : playersCankick) {
			if (player == playerToCheck)
				return true;
		}
		return false;
	}

	public List<Position> getPlayerPositions() {
		List<Position> playerPositions = new ArrayList<Position>();
		List<Player> players = getPlayers();

		for (final Player player : players) {
			playerPositions.add(player.getPosition());
		}

		return playerPositions;
	}

	public boolean isKickOff() {
		return kickOff;
	}
}
