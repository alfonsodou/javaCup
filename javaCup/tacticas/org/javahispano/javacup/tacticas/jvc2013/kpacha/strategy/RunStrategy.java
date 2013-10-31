package org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;

public class RunStrategy implements Strategy {

    private static RunStrategy instance = null;
    private List<Command> commands = new ArrayList<Command>(2);

    private RunStrategy() {
    }

    public synchronized static RunStrategy getInstance() {
	if (instance == null) {
	    instance = new RunStrategy();
	}
	return instance;
    }

    public List<Command> execute(AbstractPlayer player, Analysis analysis) {
	commands.clear();

	Position next = getBestTarget(analysis, player.getPosition());

	commands.add(new CommandMoveTo(player.getCurrentPlayer(), next, true));
	commands.add(new CommandHitBall(player.getCurrentPlayer(), next, 0.3, 0));

	return commands;
    }

    private Position getBestTarget(Analysis analysis, Position current) {
	Position next = current.moveAngle(
		current.angle(Constants.centroArcoSup),
		Constants.DISTANCIA_PENAL * 0.6);
	Position[] rivals = analysis.getGameSituations().rivalPlayers();
	int[] nearest = Arrays.copyOfRange(current.nearestIndexes(rivals, 0),
		0, 4);
	for (int rival : nearest) {
	    next = next.moveAngle(
		    rivals[rival].angle(current),
		    2 * Constants.DISTANCIA_PENAL
			    / (current.distance(rivals[rival]) + 1));
	}

	if (!next.isInsideGameField(-5)) {
	    if (Math.abs(next.getX()) + 5 > Constants.LARGO_CAMPO_JUEGO / 2) {
		next = next.movePosition(-5 * Math.signum(next.getX()), 0);
	    }
	    if (Math.abs(next.getY()) + 5 > Constants.ANCHO_CAMPO_JUEGO / 2) {
		next = next.movePosition(0, -5 * Math.signum(next.getY()));
	    }
	}
	return next;
    }

}
