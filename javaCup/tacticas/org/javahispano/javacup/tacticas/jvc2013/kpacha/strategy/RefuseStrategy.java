package org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;

public class RefuseStrategy implements Strategy {

    private static RefuseStrategy instance = null;

    public synchronized static RefuseStrategy getInstance() {
	if (instance == null) {
	    instance = new RefuseStrategy();
	}
	return instance;
    }

    private RefuseStrategy() {
    }

    public List<Command> execute(AbstractPlayer player, Analysis analysis) {
	List<Command> command = new ArrayList<Command>(1);
	Position target = getTarget(player, analysis);
	command.add(new CommandHitBall(player.getCurrentPlayer(), target, 0.7d
		+ target.distance(player.getPosition())
		/ Constants.LARGO_CAMPO_JUEGO, 45));
	return command;
    }

    private Position getTarget(AbstractPlayer player, Analysis analysis) {
	Position shootingPosition = player.getPosition();
	int sign = (Math.abs(shootingPosition.getX()) > Constants.ANCHO_CAMPO_JUEGO / 4) ? 1
		: -1;
	sign *= Math.signum(shootingPosition.getX());
	Position target = shootingPosition.movePosition(sign
		* Constants.ANCHO_CAMPO_JUEGO,
		Constants.LARGO_CAMPO_JUEGO / 2.0d);
	double shootingAzimuth = shootingPosition.angle(target);

	Position[] rivals = analysis.getGameSituations().rivalPlayers();
	List<Double> danger = getDangerAzimuths(shootingPosition, rivals,
		shootingPosition.nearestIndexes(rivals, 0));

	Position[] players = analysis.getGameSituations().myPlayers();
	if (danger.size() > 0) {
	    target = getSafeTarget(player, shootingPosition, shootingAzimuth,
		    players, danger);
	} else {
	    target = players[target.nearestIndex(players)];
	}
	return target;
    }

    private Position getSafeTarget(AbstractPlayer player,
	    Position shootingPosition, double shootingAzimuth,
	    Position[] players, List<Double> danger) {
	List<Double> azimuthCandidates = new ArrayList<Double>();
	double error = Constants.getErrorAngular(player.getPrecision()
		* player.getEnergy())
		* Math.PI / 2;
	for (int i = 1; i < players.length; i++) {
	    if (players[i].getY() > player.getPosition().getY()) {
		double azimuth = shootingPosition.angle(players[i]);
		for (Double dangerAzimuth : danger) {
		    if (Math.abs(dangerAzimuth - shootingAzimuth - error) > Math.PI / 4) {
			azimuthCandidates.add(azimuth);
		    }
		}
	    }
	}
	Position target = null;
	if (azimuthCandidates.size() == 0) {
	    target = new Position(Math.signum(shootingPosition.getX())
		    * Constants.ANCHO_CAMPO_JUEGO / 2, shootingPosition.getY());
	} else {
	    Collections.shuffle(azimuthCandidates);
	    target = players[shootingPosition
		    .moveAngle(azimuthCandidates.get(0),
			    0.7 * Constants.LARGO_CAMPO_JUEGO).nearestIndex(
			    players)];
	}
	return target;
    }

    private List<Double> getDangerAzimuths(Position shootingPosition,
	    Position[] rivals, int[] nearest) {
	List<Double> danger = new ArrayList<Double>();
	for (int rival : nearest) {
	    if (rivals[rival].distance(shootingPosition) > 0.4 * Constants.DISTANCIA_PENAL) {
		break;
	    }
	    danger.add(shootingPosition.angle(rivals[rival]));
	}
	return danger;
    }
}
