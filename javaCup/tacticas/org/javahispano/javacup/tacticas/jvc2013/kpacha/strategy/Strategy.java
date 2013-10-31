package org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy;

import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;

public interface Strategy {
    public List<Command> execute(AbstractPlayer player, Analysis analysis);
}
