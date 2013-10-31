package org.javahispano.javacup.tacticas.jvc2013.valedores;

import java.util.List;

import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;

public class Valedores implements Tactic {
	
	TacticDetailValedores detalle = new TacticDetailValedores();

	public TacticDetail getDetail() {
		return detalle;
	}

	public Position[] getStartPositions(GameSituations sp) {
		return AlineacionValedores.alineacion5;
	}

	public Position[] getNoStartPositions(GameSituations sp) {
		return AlineacionValedores.alineacion6;
	}

	public List<Command> execute(GameSituations sp) {
		return new ComandoValedores().execute(sp);
	}
}