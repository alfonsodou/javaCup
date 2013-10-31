package org.javahispano.javacup.tacticas.jvc2013.toulousains.model;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.util.ToulousaineUtils;

public class Scoreboard {

	private int ourGoals;
	private int rivalGoals;
	private int iterationsPassed;

	public void update(GameSituations gameSituations) {
		ourGoals = gameSituations.myGoals();
		rivalGoals = gameSituations.rivalGoals();
		iterationsPassed = gameSituations.iteration();

		this.logMatchStatus();
	}

	public String getScore() {
		return ourGoals + "-" + rivalGoals;
	}

	// 90 minutes
	public int getMatchTimeConsumed() {
		return (iterationsPassed * 90) / Constants.ITERACIONES;
	}

	public void logMatchStatus() {
		ToulousaineUtils.log(getScore() + " - We are in the minute " + getMatchTimeConsumed() + " of the match");
	}
}
