package org.javahispano.javacup.tacticas.jvc2013.toulousains.model;

import org.javahispano.javacup.model.engine.GameSituations;

public class Match {

	private GameSituations gameSituations;

	private final Team toulosains = new Team();
	private final Team adversary = new Team(true);

	private final Ball ball = new Ball();

	private final Scoreboard scoreBoard = new Scoreboard();

	public void update(GameSituations gameSituations) {
		this.gameSituations = gameSituations;

		this.updateScoreboard();
		this.updateBall();
		this.updateTeamsData();

	}

	private void updateScoreboard() {
		if (this.gameSituations.iteration() % 10 == 0) {
			scoreBoard.update(this.gameSituations);
		}
	}

	private void updateBall() {
		ball.update(this.gameSituations);
	}

	private void updateTeamsData() {
		toulosains.updateStatus(this.gameSituations);
		adversary.updateStatus(this.gameSituations);
	}

	public Team getToulosains() {
		return toulosains;
	}

	public Team getAdversary() {
		return adversary;
	}

	public Ball getBall() {
		return ball;
	}

	public Scoreboard getScoreBoard() {
		return scoreBoard;
	}
}
