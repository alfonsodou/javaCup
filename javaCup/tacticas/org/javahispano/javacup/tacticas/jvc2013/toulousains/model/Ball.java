package org.javahispano.javacup.tacticas.jvc2013.toulousains.model;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;

public class Ball {
	private GameSituations gameSituations;

	public void update(GameSituations gameSituations) {
		this.gameSituations = gameSituations;
	}

	public Position getPosition() {
		return gameSituations.ballPosition();
	}

	public double getAltitude() {
		return gameSituations.ballAltitude();
	}

	public double[] getTrajectory() {
		return gameSituations.getTrajectory(gameSituations.iteration());
	}

	public double[] getTrajectory(int iteration) {
		return gameSituations.getTrajectory(iteration);
	}

	@SuppressWarnings("deprecation")
	public int[] getRecovery() {
		return gameSituations.getRecoveryBall();
	}
}
