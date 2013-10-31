package org.javahispano.javacup.tacticas.jvc2013.toulousains.core.strategy;

import org.javahispano.javacup.model.util.Position;

public interface Strategy {

	Position[] getLineUpAttacking();

	Position[] getLineUpDefending();
}
