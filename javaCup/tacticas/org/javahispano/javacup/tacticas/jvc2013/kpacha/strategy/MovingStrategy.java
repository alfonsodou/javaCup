package org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.TacticData;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.GameStatus;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;

public class MovingStrategy implements Strategy {

    private static MovingStrategy instance = null;

    private Map<GameStatus, Position[]> positions = new HashMap<GameStatus, Position[]>() {
	{
	    put(GameStatus.NEUTRAL, TacticData.DEFAULT_POSITIONS);
	    put(GameStatus.ATTACKING, TacticData.OFFENSIVE_POSITIONS);
	    put(GameStatus.DEFENDING, TacticData.DEFENSIVE_POSITIONS);
	}
    };

    private MovingStrategy() {
    }

    public synchronized static MovingStrategy getInstance() {
	if (instance == null) {
	    instance = new MovingStrategy();
	}
	return instance;
    }

    public List<Command> execute(AbstractPlayer player, Analysis analysis) {
	List<Command> command = new ArrayList<Command>(1);
	Command move = recover(player, analysis);
	if (move == null) {
	    int currentPlayer = player.getCurrentPlayer();
	    if (analysis.getGameSituations().ballPosition()
		    .nearestIndex(analysis.getGameSituations().myPlayers()) == currentPlayer) {
		move = orderMove(currentPlayer, analysis.getGameSituations()
			.ballPosition());
	    } else {
		move = orderMove(currentPlayer,
			selectBestPosition(currentPlayer, analysis));
	    }
	}
	command.add(move);
	return command;
    }

    private Position selectBestPosition(int currentPlayer, Analysis analysis) {
	return (analysis.getGameSituations().getOffSidePlayers()[currentPlayer] && analysis
		.getGameStatus() != GameStatus.DEFENDING) ? analysis
		.getGameSituations().myPlayers()[currentPlayer].movePosition(0,
		-1) : positions.get(analysis.getGameStatus())[currentPlayer];
    }

    private Command recover(AbstractPlayer player, Analysis analysis) {
	int currentPlayer = player.getCurrentPlayer();
	Command recover = null;
	if (analysis.shouldIntercept(currentPlayer)) {
	    recover = orderMove(currentPlayer, analysis.getInterception());
	}
	return recover;
    }

    private Command orderMove(int currentPlayer, Position interception) {
	return new CommandMoveTo(currentPlayer, interception);
    }

}
