package org.javahispano.javacup.tacticas.jvc2013.masia13.util;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.Ball.RADIUS;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.GOAL_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.GOAL_WIDTH;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.TRAJECTORY_SPEED_AMPLIFIES;

import java.util.Iterator;

import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;

public class Trajectory implements Iterable<BallPosition>{
	public final double angle;
	private final Position ball;
	private final int minIterations = 75;
	
	private final AbstractTrajectory trajectory;
	private final double angleSin;
	private final double angleCos;
	
	public Trajectory(Position ball, double speed, double angle, double verticalAngle) {
		this.angle = angle;
		this.angleSin = Math.sin(angle);
		this.angleCos = Math.cos(angle);
		this.ball = new Position(ball);
		this.trajectory = new AirTrajectory(speed*Math.cos(verticalAngle), speed*Math.sin(verticalAngle), 0, 0);
	}
	
	public Trajectory(Position ball0, Position ball1) {
		this.angle = ball0.angle(ball1);
		this.angleSin = Math.sin(angle);
		this.angleCos = Math.cos(angle);
		this.ball = new Position(ball0);
		double speed = Math.sqrt((ball1.x - ball0.x)*(ball1.x - ball0.x) + (ball1.y - ball0.y)*(ball1.y - ball0.y));
		double verticalAngle = new Position(0, 0).angle(new Position(ball0.distance(ball1), ball1.z - ball0.z));
		this.trajectory = new AirTrajectory(speed*Math.cos(verticalAngle ), speed*Math.sin(verticalAngle), 0, ball.z);
	}
	
	public BallPosition get(int t){
		return getBallPosition(getPosition(t), getPosition(t-1), getPosition(t-2), t);
	}

	private Position getPosition(int t){
		t = Math.max(t, 0);
		double radio = trajectory.getX((double)(t)/60d)* TRAJECTORY_SPEED_AMPLIFIES;
		return new Position(ball.x + radio*angleCos, ball.y + radio*angleSin, trajectory.getY((double)(t)/60d)* TRAJECTORY_SPEED_AMPLIFIES * 2);
	}
	
	private BallPosition getBallPosition(Position current, Position previous, Position previousToPrevious, int t){
		double Dx = current.x - previous.x;
		double Dy = current.y - previous.y;
		double speed = Math.sqrt(Dx*Dx + Dy*Dy);
		Dx = previous.x - previousToPrevious.x;
		Dy = previous.y - previousToPrevious.y;
		if(!current.isInsideGameField()){
			if(current.y > FIELD_HEIGHT/2){				
				double posX = (Dx / Dy) * (FIELD_HEIGHT/2 - current.y) + current.x;
	            double Dz = previous.z - previousToPrevious.z;
				double posZ = (Dz  / Dy) * (FIELD_HEIGHT/2 - current.y) + current.z;
				if(posZ <= GOAL_HEIGHT && Math.abs(posX) < GOAL_WIDTH / 2 - RADIUS){
					if(current.z - Dz > GOAL_HEIGHT){
						return new BallPosition(current, true, true, speed, t);
					}
					else
						return new BallPosition(current, true, false, speed, t);
				}
			}
		}
		return new BallPosition(current, speed, t);
	}
	
	@Override
	public Iterator<BallPosition> iterator() {
		return new Iterator<BallPosition>() {
			int t = 1;
			BallPosition previus = new BallPosition(ball, 0, 0);
			BallPosition previusToPrevius = previus;
			boolean insideGameField = previus.isInsideGameField();
			
			@Override
			public void remove() {
			}
			
			@Override
			public BallPosition next() {
				BallPosition temp = previus;
				previus = getBallPosition(getPosition(t), previus, previusToPrevius, t++);
				previusToPrevius = temp;
				return previus;
			}
			
			@Override
			public boolean hasNext() {
				if(previus.isInsideGameField()){
					if(previus.speed == 0 && t >= minIterations)
						return false; 
					insideGameField = true;
					return true;
				}
				return !insideGameField;
			}
		};
	}
}