package org.javahispano.javacup.tacticas.jvc2013.kpacha;

import java.util.ArrayList;
import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.AnalysisImpl;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;

public class SupuTactic implements Tactic {

    private Analysis analysis = new AnalysisImpl();
    private TacticDetail details = new TacticDetailImpl();

    public TacticDetail getDetail() {
	return details;
    }

    public Position[] getStartPositions(GameSituations sp) {
	return TacticData.START_POSITIONS;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
	return TacticData.NO_START_POSITIONS;
    }

    @Override
    public List<Command> execute(GameSituations sp) {
	List<Command> commands = new ArrayList<Command>();
	List<AbstractPlayer> players = getPlayers();
	analysis.update(sp, players);
	for (AbstractPlayer player : players) {
	    commands.addAll(player.execute(analysis));
	}
	return commands;
    }

    private List<AbstractPlayer> getPlayers() {
	List<AbstractPlayer> players = new ArrayList<AbstractPlayer>(11);
	for (PlayerDetail player : details.getPlayers()) {
	    try {
		players.add(((AbstractPlayer) player));
	    } catch (ClassCastException e) {
		System.out.println(e.getMessage());
	    }
	}
	return players;
    }

}
