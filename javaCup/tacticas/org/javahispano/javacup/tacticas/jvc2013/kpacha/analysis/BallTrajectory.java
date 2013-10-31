package org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis;

import java.util.ArrayList;
import java.util.List;

import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class BallTrajectory {

    private Position initialPosition;
    private List<Position> balls;
    private List<Double> z;
    private AbstractTrajectory trajectory;
    private double azimuth, elevation, velocity;
    private final static int DEFAULT_STEPS = 120;

    public BallTrajectory(Position ball, double azimuth, double elevation,
	    double velocity) {
	this.velocity = velocity;
	this.azimuth = azimuth;
	this.elevation = elevation;
	this.initialPosition = ball;
    }

    public BallTrajectory(Position ball, Position target, double elevation,
	    double velocity) {
	this(ball, ball.angle(target), elevation, velocity);
    }

    public void calculate() {
	calculate(DEFAULT_STEPS);
    }

    public void calculate(int steps) {
	init(steps);
	for (int i = 1; i < steps && balls.get(i - 1).isInsideGameField(0); i++) {
	    double ratio = getRatio(i);
	    z.add(getAltitude(i));
	    balls.add(new Position(initialPosition.getX() + ratio
		    * Math.cos(azimuth), initialPosition.getY() + ratio
		    * Math.sin(azimuth)));
	}
    }

    private void init(int steps) {
	balls = new ArrayList<Position>();
	balls.add(initialPosition);
	z = new ArrayList<Double>();
	z.add(0.0d);
	trajectory = new AirTrajectory(velocity * Math.cos(elevation), velocity
		* Math.sin(elevation), 0, 0);
    }

    private double getRatio(int iteration) {
	return trajectory.getX(getTime(iteration))
		* Constants.AMPLIFICA_VEL_TRAYECTORIA;
    }

    private double getAltitude(int iteration) {
	return trajectory.getY(getTime(iteration))
		* Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
    }

    public List<Position> getBallPosition() {
	return balls;
    }

    public List<Double> getBallAltitude() {
	return z;
    }

    public AbstractTrajectory getTrajectory() {
	return trajectory;
    }

    private double getTime(int iteration) {
	return (double) (iteration + 1) / 60d;
    }

}
