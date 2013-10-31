package org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;

public class PassingStrategy implements Strategy {
    private static PassingStrategy instance = null;
    private Random r;

    public synchronized static PassingStrategy getInstance() {
	if (instance == null) {
	    instance = new PassingStrategy(new Random());
	}
	return instance;
    }

    private PassingStrategy(Random r) {
	this.r = r;
    }

    public List<Command> execute(AbstractPlayer player, Analysis analysis) {
	List<Command> command = new ArrayList<Command>(1);
	Position target = findTarget(player, analysis);
	if (target != null) {
	    command.add(new CommandHitBall(
		    player.getCurrentPlayer(),
		    target,
		    getHitPower(player.getPosition(), target),
		    player.getPosition().distance(target) > Constants.ANCHO_CAMPO_JUEGO / 3));
	}
	return command;
    }

    private double getHitPower(Position ball, Position target) {
	return 0.5d + (ball.distance(target) / (2 * Constants.LARGO_CAMPO_JUEGO));
    }

    private Position findTarget(AbstractPlayer player, Analysis analysis) {
	int current = player.getCurrentPlayer();
	Position target = null;
	double[] targetCandidates = analysis.getPositionValorations();
	double bestValoration = -900.0d;
	for (int candidate = 1; candidate < targetCandidates.length; candidate++) {
	    if (current != candidate && r.nextInt(3) > 0
		    && targetCandidates[candidate] > bestValoration) {
		bestValoration = targetCandidates[candidate];
		target = analysis.getGameSituations().myPlayers()[candidate];
	    }
	}
	return target;
    }
}
