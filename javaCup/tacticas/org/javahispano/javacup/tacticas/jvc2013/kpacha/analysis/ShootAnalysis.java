package org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.AbstractPlayer;

public class ShootAnalysis {

    public static final double DEGREES_TO_RADIAN = Math.PI / 180;

    private AbstractPlayer player;

    private double minShootingAzimuth;
    private double maxShootingAzimuth;

    private List<ShootAnalysisData> shoots;

    private Analysis analysis;

    public ShootAnalysis(AbstractPlayer player, Analysis analysis) {
	this.player = player;
	this.analysis = analysis;
	initializeAzimuthRange();
    }

    private void initializeAzimuthRange() {
	double error = 0.5
		* Math.PI
		* Constants.getErrorAngular(player.getPrecision()
			* player.getEnergy());
	double leftPostAzimuth = player.getPosition().angle(
		Constants.posteIzqArcoSup);
	double rightPostAzimuth = player.getPosition().angle(
		Constants.posteDerArcoSup);
	minShootingAzimuth = Math.min(leftPostAzimuth, rightPostAzimuth)
		+ error;
	maxShootingAzimuth = Math.max(leftPostAzimuth, rightPostAzimuth)
		- error;
	if (minShootingAzimuth > maxShootingAzimuth) {
	    minShootingAzimuth = player.getPosition().angle(
		    Constants.centroArcoSup);
	    maxShootingAzimuth = player.getPosition().angle(
		    Constants.centroArcoSup);
	}
    }

    public List<ShootAnalysisData> getShootCandidates(double hitPower) {
	shoots = new ArrayList<ShootAnalysisData>();
	for (double azimuth = minShootingAzimuth; azimuth <= maxShootingAzimuth; azimuth += DEGREES_TO_RADIAN) {
	    for (double elevation = 0.0d; elevation < Constants.ANGULO_VERTICAL_MAX
		    * DEGREES_TO_RADIAN; elevation += DEGREES_TO_RADIAN) {
		ShootAnalysisData shootData = evaluate(azimuth, hitPower,
			elevation);
		if (shootData != null) {
		    shoots.add(shootData);
		}
	    }
	}
	Collections.sort(shoots);
	return shoots;
    }

    private ShootAnalysisData evaluate(double azimuth, double hitPower,
	    double elevation) {
	ShootAnalysisData shoot = null;
	int steps = 120;
	BallTrajectory trajectory = new BallTrajectory(player.getPosition(),
		azimuth, elevation, getBallVelocity(hitPower));
	trajectory.calculate(steps);
	List<Double> z = trajectory.getBallAltitude();
	List<Position> positions = trajectory.getBallPosition();
	for (int i = 0; i < positions.size(); i++) {
	    Position ball = positions.get(i);
	    if (!ball.isInsideGameField(0)) {
		double x = ball.getX();
		if (isGoal(ball, z.get(i))) {
		    shoot = new ShootAnalysisData(trajectory, azimuth,
			    hitPower, elevation, valorate(trajectory));
		}
		break;
	    }
	}
	return shoot;
    }

    private double valorate(BallTrajectory trajectory) {
	return valorateGoal(trajectory) + valorateInterception(trajectory);
    }

    private double valorateGoal(BallTrajectory trajectory) {
	int iterations = trajectory.getBallAltitude().size();
	double altitude = trajectory.getBallAltitude().get(iterations - 1);
	if (altitude >= Constants.ALTO_ARCO) {
	    return -100;
	}
	Position ball = trajectory.getBallPosition().get(iterations - 1);
	Position ball0 = trajectory.getBallPosition().get(iterations - 2);
	double altitude0 = trajectory.getBallAltitude().get(iterations - 2);

	double ballDx0 = ball.getX() - ball0.getX();
	double ballDy0 = ball.getY() - ball0.getY();
	double ballDz0 = altitude - altitude0;

	double posY = Constants.LARGO_CAMPO_JUEGO / 2;
	double posX = (ballDx0 / ballDy0) * (posY - ball.getY()) + ball.getX();
	double posZ = (ballDz0 / ballDy0) * (posY - ball.getY()) + altitude;

	if (posZ > Constants.ALTO_ARCO) {
	    return -100;
	}

	double distance = analysis.getGameSituations().rivalPlayers()[0]
		.distance(new Position(posX, posY));

	return 3 * distance * (posZ * posZ) / iterations;
    }

    private double valorateInterception(BallTrajectory trajectory) {
	double valoration = 0;
	List<Interception> interceptions = getInterceptablePositions(trajectory);
	if (interceptions != null) {
	    for (Interception interception : interceptions) {
		valoration += interception.getDistance() - 20;
	    }
	}
	return valoration;
    }

    private List<Interception> getInterceptablePositions(
	    BallTrajectory trajectory) {
	Position[] rivals = analysis.getGameSituations().rivalPlayers();
	PlayerDetail rivalDetails[] = analysis.getGameSituations()
		.rivalPlayersDetail();
	List<Interception> interception = null;
	List<Integer> indexed = new ArrayList<Integer>();
	for (int it = 0; it < trajectory.getBallAltitude().size(); it++) {
	    Position ball = trajectory.getBallPosition().get(it);
	    double z = trajectory.getBallAltitude().get(it);
	    if (ball.isInsideGameField(2) && z <= Constants.ALTO_ARCO) {
		for (int rival = 0; rival < rivals.length; rival++) {
		    if (isAccessibleBall(rivalDetails[rival], z)
			    && !indexed.contains(rival)) {
			double maxDistance = analysis.getGameSituations()
				.distanceTotal(rival, it);
			double distance = rivals[rival].distance(ball);
			if (maxDistance >= distance) {
			    if (interception == null) {
				interception = new ArrayList<Interception>();
			    }
			    interception.add(new Interception(distance, rival,
				    it));
			    indexed.add(rival);
			    break;
			}
		    }
		}
	    }
	}
	return interception;
    }

    private boolean isAccessibleBall(PlayerDetail rivalDetail, double z) {
	return z <= (rivalDetail.isGoalKeeper() ? Constants.ALTO_ARCO
		: Constants.ALTURA_CONTROL_BALON);
    }

    private double getBallVelocity(double hitPower) {
	double velocity = hitPower
		* Constants.getVelocidadRemate(player.getPower());
	double factor = Constants.ENERGIA_DISPARO * player.getEnergy();
	if (factor > 1) {
	    factor = 1;
	}
	return velocity * factor;
    }

    private boolean isGoal(Position ball, double altitude) {
	return altitude < Constants.ALTO_ARCO - Constants.RADIO_BALON
		&& Math.abs(ball.getX()) < Constants.LARGO_ARCO / 2
			- Constants.RADIO_BALON;
    }

}
