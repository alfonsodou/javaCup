package org.javahispano.javacup.tacticas.jvc2013.toulousains;

import java.util.List;

import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.core.Engine;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.model.LineUp;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.model.Match;

public class Toulousains implements Tactic {

	TacticDetail detalle = new TacticDetailImpl();

	Match match = new Match();

	Engine engine = new Engine(match);

	@Override
	public TacticDetail getDetail() {
		return detalle;
	}

	@Override
	public Position[] getStartPositions(GameSituations sp) {
		return LineUp.alineacion3;
	}

	@Override
	public Position[] getNoStartPositions(GameSituations sp) {
		return LineUp.alineacion2;
	}

	@Override
	public List<Command> execute(GameSituations sp) {

		// Updates the model (status of the match)
		match.update(sp);

		// Updates and calculates the strategy to follow
		engine.updateStrategy();

		if (sp.iteration() == Constants.ITERACIONES - 1) {
			match.getScoreBoard().logMatchStatus();
		}

		// Returns all the commands for the current strategy
		return engine.getStrategyCommands();
	}
}