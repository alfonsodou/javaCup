package org.javahispano.javacup.tacticas.jvc2013.masia13.model;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_CENTER;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;

public class MyPlayer extends Player{

	public Position nextPosition = null;
	public Position nextActualPosition = null;
	public int lastSleepIteration = 0;
	public double ballAngle;
	
	private CommandHitBall commandHitBall = null;
	private CommandMoveTo commandMoveTo = null;
	
	
	public MyPlayer(int index, PlayerDetail playerDetail) {
		super(index, playerDetail);
	}
	
	@Override
	public void update(double x, double y, double energy, double aceleration) {
		super.update(x, y, energy, aceleration);
		this.nextPosition = null;
		this.commandHitBall = null;
		this.commandMoveTo = null;
		this.nextActualPosition = null;
	}
	
	@Override
	protected boolean insideArea(Position ball) {
		return ball.insideMyPenaltyArea();
	}
	
	public void moveTo(Position position) {
		double distance = distance(position);
		if(distance > 0){
			nextActualPosition = moveAngle(angle(position), Math.min(distance, speed * energy));
			commandMoveTo = new CommandMoveTo(index, getPosition(nextActualPosition), false);
			this.lastMove = new Position(nextActualPosition);
			updateAceleration = true;
		}
		this.nextPosition = position; 
	}
	
	public void sprintTo(Position position) {
		double distance = distance(position);
		if(distance > 0){
			nextActualPosition = moveAngle(angle(position), Math.min(distance, speed * energy * 1.2));
			commandMoveTo = new CommandMoveTo(index, getPosition(nextActualPosition), true);
			this.lastMove = new Position(nextActualPosition);
			updateAceleration = true;
		}
		this.nextPosition = position; 
	}

	private org.javahispano.javacup.model.util.Position getPosition(
			Position position) {
		return new org.javahispano.javacup.model.util.Position(position.x, position.y);
	}

	public void hitBall(Position destiny, double power, double verticalAngle) {
		commandHitBall = new CommandHitBall(index, getPosition(destiny),  power, verticalAngle*180/Math.PI);
	}

	public void hitBall(double angle, double power, double verticalAngle) {
		commandHitBall = new CommandHitBall(index, angle*180/Math.PI, power, verticalAngle*180/Math.PI);
	}
	
	public CommandHitBall getCommandHitBall() {
		return commandHitBall;
	}
	
	public CommandMoveTo getCommandMoveTo() {
		return commandMoveTo;
	}

	@Override
	public Iterable<Position> trajectoryToGoal(Position from, int iterationsToFrom) {
		return trajectory(from, iterationsToFrom, RIVAL_GOAL_CENTER);
	}

	public Iterable<Position> trajectoryToGoal() {
		return trajectoryToGoal(this, 0);
	}
}
