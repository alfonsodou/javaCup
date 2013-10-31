package org.javahispano.javacup.tacticas.jvc2013.kpacha.player;


import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy.RefuseStrategy;

public class Defender extends AbstractPlayer implements PlayerDetail {

    public Defender(String name, int number, Color skin, Color hair,
	    double speed, double shoot, double accurancy) {
	super(name, number, skin, hair, speed, shoot, accurancy);
	shooter = RefuseStrategy.getInstance();
    }

    @Override
    public List<Command> doExecute(Analysis analysis) {
	commands.addAll(move(analysis));

	if (canKick(analysis)) {
	    commands.addAll(shoot(analysis));
	}

	return commands;
    }

}
