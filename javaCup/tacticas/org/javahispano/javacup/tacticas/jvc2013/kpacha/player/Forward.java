package org.javahispano.javacup.tacticas.jvc2013.kpacha.player;


import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;

public class Forward extends AbstractPlayer implements PlayerDetail {

    public Forward(String name, int number, Color skin, Color hair,
	    double speed, double shoot, double accurancy) {
	super(name, number, skin, hair, speed, shoot, accurancy);
    }

    @Override
    public List<Command> doExecute(Analysis analysis) {
	commands.addAll(move(analysis));

	if (canKick(analysis)) {
	    List<Command> command = null;
	    boolean shouldShoot = shouldShoot(analysis);
	    if (!shouldShoot) {
		if (analysis.getGameSituations().isStarts()
			|| !shouldRun(analysis)) {
		    command = pass(analysis);
		}
		if (command == null || command.size() == 0) {
		    commands.addAll(run(analysis));
		}
	    } else {
		command = shoot(analysis);
	    }
	    if (command != null) {
		commands.addAll(command);
	    }
	}

	return commands;
    }

    private boolean shouldRun(Analysis analysis) {
	int nearest = position.nearestIndex(analysis.getGameSituations()
		.rivalPlayers());
	boolean isFaceToFace = analysis.getGameSituations()
		.rivalPlayersDetail()[nearest].isGoalKeeper();
	double distance = position.distance(analysis.getGameSituations()
		.rivalPlayers()[nearest]);
	return (isFaceToFace && distance >= Constants.ANCHO_AREA_GRANDE)
		|| (!isFaceToFace && distance >= Constants.ANCHO_AREA_GRANDE / 2);
    }

    protected double getMaxShootingDistance() {
	return 3.5 * Constants.DISTANCIA_PENAL;
    }

    public int getBallAtraction() {
	return 600;
    }

}
