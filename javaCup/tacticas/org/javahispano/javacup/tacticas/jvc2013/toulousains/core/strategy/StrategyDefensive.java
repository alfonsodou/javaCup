package org.javahispano.javacup.tacticas.jvc2013.toulousains.core.strategy;

import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.model.LineUp;

public class StrategyDefensive implements Strategy {

	@Override
	public Position[] getLineUpAttacking() {
		return LineUp.alineacion4;
	}

	@Override
	public Position[] getLineUpDefending() {
		return LineUp.alineacion1;
	}

}
