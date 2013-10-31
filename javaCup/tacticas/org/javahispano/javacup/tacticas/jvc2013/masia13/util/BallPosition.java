package org.javahispano.javacup.tacticas.jvc2013.masia13.util;

public class BallPosition extends Position{
	
	public final boolean isGoal;
	public final boolean isVaselineGoal;
	public final double speed;
	
	public final int iteration;
	
	BallPosition(Position position, boolean isGoal, boolean isVaselineGoal, double speed, int iteration) {
		super(position);
		this.isGoal = isGoal;
		this.speed = speed;
		this.isVaselineGoal = isVaselineGoal;
		this.iteration = iteration;
	}

	BallPosition(Position position, double speed, int iteration) {
		this(position, false, false, speed, iteration);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
