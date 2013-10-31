package org.javahispano.javacup.tacticas.jvc2013.kpacha.player;


import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;

public class Middfielder extends AbstractPlayer implements PlayerDetail {

    public Middfielder(String name, int number, Color skin, Color hair,
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
		command = pass(analysis);
	    }
	    if (shouldShoot || command == null || command.size() == 0) {
		command = shoot(analysis);
	    }
	    commands.addAll(command);
	}

	return commands;
    }

    public int getBallAtraction() {
	return 300;
    }

}
