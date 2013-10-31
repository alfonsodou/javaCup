package org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis;

import java.util.ArrayList;
import java.util.List;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;

public class AnalysisImpl implements Analysis {

    private List<AbstractPlayer> players;

    private GameSituations sp = null;

    private List<Integer> recoverers = new ArrayList<Integer>(2);
    private Position interception;
    private int interceptionIteration;

    private Position ball;
    private double[] positionValoration = new double[11];
    private GameStatus status = GameStatus.NEUTRAL;

    @Override
    public void update(GameSituations sp, List<AbstractPlayer> players) {
	this.sp = sp;
	this.players = players;
	updatePositionValoration();
	updateRecoverers();
	updateGameStatus();
    }

    private void updateGameStatus() {
	if (ball.getY() < -5) {
	    status = GameStatus.DEFENDING;
	} else if (ball.getY() > 15 && !sp.isRivalStarts()) {
	    status = GameStatus.ATTACKING;
	} else {
	    status = GameStatus.NEUTRAL;
	}
    }

    private void updatePositionValoration() {
	ball = sp.ballPosition();
	Position[] rivals = sp.rivalPlayers();
	Position[] players = sp.myPlayers();
	boolean[] isOffside = sp.getOffSidePlayers();
	for (int current : ball.nearestIndexes(sp.myPlayers(), 0)) {
	    double fieldValoration = -1500.0d;
	    if (!isOffside[current]) {
		fieldValoration = valoratePlayer(current)
			* (valorateDistance(ball, players[current])
				+ valorateY(ball, players[current])
				+ valorateRivalProximity(players[current],
					rivals) + this.players.get(current)
				.getBallAtraction());
	    }
	    positionValoration[current] = fieldValoration;
	}
    }

    private double valoratePlayer(int current) {
	return sp.getMyPlayerEnergy(current) * sp.getMyPlayerError(current)
		* sp.getMyPlayerPower(current) * sp.getMyPlayerSpeed(current);
    }

    private double valorateRivalProximity(Position position, Position[] rivals) {
	double valoration = 0.0d;
	int[] nearestRivals = position.nearestIndexes(rivals, 0);
	for (int i = 0; i < 2; i++) {
	    valoration += 100 + rivals[nearestRivals[i]].distance(position)
		    - Constants.LARGO_CAMPO_JUEGO;
	}
	return valoration;
    }

    private double valorateY(Position ball, Position target) {
	double dY = target.getY() - ball.getY();
	return 200 - 0.8d * (dY * dY) + 20 * dY;
    }

    private double valorateDistance(Position ball, Position target) {
	return Constants.LARGO_CAMPO_JUEGO
		/ (ball.distance(target) - Constants.DISTANCIA_CONTROL_BALON);
    }

    private void updateRecoverers() {
	recoverers.clear();
	int[] recoverData = sp.getRecoveryBall();
	if (recoverData.length > 1) {
	    interceptionIteration = recoverData[0];
	    double[] coordenates = sp.getTrajectory(interceptionIteration);
	    interception = new Position(coordenates[0], coordenates[1]);
	    int maxRecoverers = (status == GameStatus.ATTACKING) ? 1 : 2;
	    for (int i = 1; i < recoverData.length && i < maxRecoverers + 1; i++) {
		recoverers.add(recoverData[i]);
	    }
	}
    }

    @Override
    public GameSituations getGameSituations() {
	return sp;
    }

    @Override
    public Position getInterception() {
	return interception;
    }

    @Override
    public int getInterceptionIteration() {
	return interceptionIteration;
    }

    @Override
    public boolean shouldIntercept(int player) {
	return !sp.isRivalStarts() && recoverers.contains(player);
    }

    @Override
    public double getPositionValoration(int player) {
	return positionValoration[player];
    }

    @Override
    public double[] getPositionValorations() {
	return positionValoration;
    }

    @Override
    public GameStatus getGameStatus() {
	return status;
    }

}
