package org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Interception;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.utils.Geometry;

public class GoalSaveStrategy implements Strategy {

    private static GoalSaveStrategy instance = null;

    private GoalSaveStrategy() {
    }

    public synchronized static GoalSaveStrategy getInstance() {
	if (instance == null) {
	    instance = new GoalSaveStrategy();
	}
	return instance;
    }

    private double rightPostRatio;
    private double leftPostRatio;

    public List<Command> execute(AbstractPlayer player, Analysis analysis) {
	Interception rivalInterception = (analysis.getGameSituations()
		.ballPosition().getY() < 5) ? getFirstRivalInterception(analysis)
		: null;
	int currentPlayer = player.getCurrentPlayer();
	List<Command> command = new ArrayList<Command>(1);
	if (rivalInterception != null
		&& rivalInterception.getIteration() < analysis
			.getInterceptionIteration()) {
	    if (!analysis.getGameSituations().isStarts()) {
		command.add(orderMove(
			currentPlayer,
			basculate(analysis, rivalInterception.getPosition(),
				currentPlayer)));
	    } else if (analysis.getGameSituations().isStarts()
		    && analysis.getGameSituations().ballPosition().getY() < Constants.ANCHO_AREA_GRANDE
			    - Constants.LARGO_CAMPO_JUEGO / 2) {
		command.add(orderMove(currentPlayer, analysis
			.getGameSituations().ballPosition()));
	    }
	} else {
	    Command move = recover(player, analysis, rivalInterception);
	    if (move == null) {
		Position target = basculate(analysis, analysis
			.getGameSituations().ballPosition(), currentPlayer);
		if (target != null) {
		    move = orderMove(currentPlayer, target);
		}
	    }
	    if (move != null) {
		command.add(move);
	    }
	}
	return command;
    }

    private Command orderMove(int currentPlayer, Position interception) {
	return new CommandMoveTo(currentPlayer, interception);
    }

    private Interception getFirstRivalInterception(Analysis analysis) {
	Interception interception = null;
	int iterToIntercept = analysis.getInterceptionIteration();
	GameSituations gs = analysis.getGameSituations();
	for (int iter = 0; iter < iterToIntercept + 10; iter++) {
	    double[] ballCoordenates = gs.getTrajectory(iter);
	    if (ballCoordenates[2] <= Constants.ALTURA_CONTROL_BALON) {
		Position ball = new Position(ballCoordenates[0],
			ballCoordenates[1]);
		if (!ball.isInsideGameField(0)) {
		    break;
		}
		int[] nearestRivals = Arrays.copyOfRange(
			ball.nearestIndexes(gs.rivalPlayers()), 0, 3);
		for (int rival : nearestRivals) {
		    double distance = gs.rivalPlayers()[rival].distance(ball);
		    double maxDistance = getMaxDistance(
			    gs.getRivalEnergy(rival),
			    gs.getRivalPlayerSpeed(rival),
			    gs.getRivalAceleration(rival), iter, true);
		    if (maxDistance >= distance) {
			interception = new Interception(rival, iter, ball);
			break;
		    }
		}
	    }
	}

	return interception;
    }

    private Command recover(AbstractPlayer player, Analysis analysis,
	    Interception rivalInterception) {
	int currentPlayer = player.getCurrentPlayer();
	Command recover = null;
	if (analysis.shouldIntercept(currentPlayer)) {
	    recover = orderMove(
		    currentPlayer,
		    getBestInterceptionPosition(player, analysis,
			    rivalInterception));
	}
	return recover;
    }

    private Position getBestInterceptionPosition(AbstractPlayer player,
	    Analysis analysis, Interception rivalInterception) {
	Position interception = analysis.getInterception();
	int iteration = analysis.getInterceptionIteration();
	int maxIterations = (rivalInterception == null) ? 20
		: rivalInterception.getIteration() - iteration;
	int bestIter = 0;
	double bestProb = calculateControlProbability(player, analysis,
		iteration);
	for (int extraIter = 1; extraIter < maxIterations; extraIter++) {
	    double controlProb = calculateControlProbability(player, analysis,
		    iteration + extraIter);
	    if (controlProb > bestProb) {
		bestIter = iteration + extraIter;
		bestProb = controlProb;
	    }
	}
	if (bestIter > iteration) {
	    double[] current = analysis.getGameSituations().getTrajectory(
		    bestIter);
	    interception = new Position(current[0], current[1]);
	}
	return interception;
    }

    private double calculateControlProbability(AbstractPlayer player,
	    Analysis analysis, int iteration) {
	double[] previous = analysis.getGameSituations().getTrajectory(
		iteration - 1);
	double[] current = analysis.getGameSituations()
		.getTrajectory(iteration);
	Position ball = new Position(current[0], current[1]);
	boolean inBox = Math.abs(ball.getX()) < Constants.LARGO_AREA_GRANDE / 2
		&& ball.getY() < Constants.ANCHO_AREA_GRANDE
			- Constants.LARGO_CAMPO_JUEGO / 2;
	double maxAltitude = (inBox && player.isGoalKeeper()) ? Constants.ALTO_ARCO
		: Constants.ALTURA_CONTROL_BALON;
	double controlProb = (7d - Math.sqrt(Math.pow(current[0] - previous[0],
		2) + Math.pow(current[1] - previous[1], 2))) / 7d;
	return (current[2] <= maxAltitude && ball.isInsideGameField(0.01)) ? controlProb
		: 0;
    }

    protected Position basculate(Analysis analysis, Position interest,
	    int currentPlayer) {
	Position result = null;
	updatePostRatios(analysis, interest, currentPlayer);
	double maxPostRatio = Math.max(rightPostRatio, leftPostRatio);
	Position post = (maxPostRatio == rightPostRatio) ? Constants.posteDerArcoInf
		: Constants.posteIzqArcoInf;

	if (interest.getX() == 0) {
	    result = new Position(0, Math.sqrt(maxPostRatio * maxPostRatio
		    - Constants.LARGO_ARCO * Constants.LARGO_ARCO / 4)
		    - Constants.LARGO_CAMPO_JUEGO / 2);
	} else {
	    Position[] candidates = Geometry.getCircleLineIntersections(post,
		    maxPostRatio, Constants.centroArcoInf, interest);

	    for (Position position : candidates) {
		if (position.isInsideGameField(0)) {
		    result = position;
		    break;
		}
	    }
	}
	return result;
    }

    private void updatePostRatios(Analysis analysis, Position interest,
	    int currentPlayer) {
	double dV = getVelocityFactor(analysis, currentPlayer);
	rightPostRatio = dV * Constants.posteDerArcoInf.distance(interest);
	leftPostRatio = dV * Constants.posteIzqArcoInf.distance(interest);
    }

    private double getVelocityFactor(Analysis analysis, int currentPlayer) {
	double goalkeeperVelocity = Constants.getVelocidad(analysis
		.getGameSituations().getMyPlayerSpeed(currentPlayer))
		* analysis.getGameSituations().getMyPlayerSpeed(currentPlayer);
	double maxBallVelocity = Constants.getVelocidadRemate(1);
	return goalkeeperVelocity / maxBallVelocity;
    }

    private double getMaxDistance(double energy, double speed,
	    double acceleration, int iteration, boolean isSprint) {
	double distance = 0;
	for (int iter = 0; iter < iteration; iter++) {
	    distance += getMaxDistanceAtIteration(energy, speed, acceleration,
		    iteration, isSprint);
	}
	return distance;
    }

    private double getMaxDistanceAtIteration(double energy, double speed,
	    double acceleration, int iteration, boolean isSprint) {
	double accelIter = Math.pow(
		Math.min(1, Math.sqrt(acceleration)
			+ Constants.ACELERACION_INCR * iteration), 2);

	double energyIter = Math.max(energy - iteration
		* Constants.ENERGIA_DIFF, Constants.ENERGIA_MIN);
	double sprint = (isSprint && energyIter > Constants.SPRINT_ENERGIA_MIN) ? Constants.SPRINT_ACEL
		: 1;

	return Constants.getVelocidad(speed) * energyIter * accelIter * sprint;
    }

}
