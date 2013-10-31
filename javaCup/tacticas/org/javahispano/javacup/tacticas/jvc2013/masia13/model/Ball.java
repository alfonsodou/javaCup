package org.javahispano.javacup.tacticas.jvc2013.masia13.model;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.GOAL_WIDTH;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.GOAL_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.MY_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.MAX_SPEED_SHOOT;

import java.util.ArrayList;
import java.util.List;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.BallPosition;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Trajectory;

public class Ball extends Position{

	public final static double RADIUS = Constants.RADIO_BALON;
	
	public boolean isTowardsGoal;
	
	public Position[] trayectory;

	public int quartile;
	
	public Ball() {
		super(FIELD_CENTER);
	}
	
	public final void update(GameSituations sp){
		this.x = sp.ballPosition().getX();
		this.y = sp.ballPosition().getY();
		this.z = sp.ballAltitude();
		isTowardsGoal = false;
		int maxIterations = 100;
		List<Position> trayectory = new ArrayList<Position>(maxIterations);
		trayectory.add(new Position(this));
		Position previus = this;
		Position previusToPrevius = this;
		int i = 0;
		do{
			i++;
			double[] temp = sp.getTrajectory(i);
			trayectory.add(new Position(temp[0], temp[1], temp[2]));
			if(!isTowardsGoal && isRivalGoal(trayectory.get(i), previus, previusToPrevius))
				isTowardsGoal = true;
			previusToPrevius = previus;
			previus = trayectory.get(i);
		}while(trayectory.get(i).isInsideGameField() && i < maxIterations);
		this.trayectory = new Position[trayectory.size()];
		this.trayectory = trayectory.toArray(this.trayectory);
		this.quartile = (int)Math.floor(Math.min(FIELD_HEIGHT, Math.max(y - MY_GOAL_CENTER.y, 0))/FIELD_HEIGHT*4);
	}
	
	public boolean isRivalGoal(int iteration) {
		return isRivalGoal(trayectory[iteration], trayectory[Math.max(iteration - 1, 0)], trayectory[Math.max(iteration - 2, 0)]);
	}
	
	private boolean isRivalGoal(Position current, Position previous, Position previousToPrevious) {
		return isGoal(current.getInvertedPosition(), previous.getInvertedPosition(), previousToPrevious.getInvertedPosition());
	}
	
	private boolean isGoal(Position current, Position previous, Position previousToPrevious){
		double Dx = previous.x - previousToPrevious.x;
		double Dy = previous.y - previousToPrevious.y;
		if(!current.isInsideGameField()){
			if(current.y > FIELD_HEIGHT/2){				
				double posX = (Dx / Dy) * (FIELD_HEIGHT/2 - current.y) + current.x;
	            double Dz = previous.z - previousToPrevious.z;
				double posZ = (Dz  / Dy) * (FIELD_HEIGHT/2 - current.y) + current.z;
				if(posZ <= GOAL_HEIGHT && Math.abs(posX) < GOAL_WIDTH / 2 - RADIUS){
					return true;
				}
			}
		}
		return false;
	}
	
	public double speedToDistance(int iterations, double distance){
		return speedToPosition(iterations, moveAngle(0, distance));
	}

	public double speedToPosition(int iterations, Position position) {
		double angle = angle(position);
		double distance = distance(position);
		double speedFrom = 0, speedTo = MAX_SPEED_SHOOT;
		double distanceFrom = 0, distanceTo = new Trajectory(this, speedTo, angle, 0).get(iterations).distance(this);
		double speed, d, speedBall;
		if(distance  >= distanceTo)
			return speedTo;
		do{
			speed = speedFrom + (speedTo - speedFrom)*(distance - distanceFrom)/(distanceTo - distanceFrom);
			Trajectory trajectory = new Trajectory(this, speed, angle, 0);
			BallPosition ball = trajectory.get(iterations);
			d = ball.distance(this);
			speedBall = ball.speed;
			if(distance > d){
				speedFrom = speed;
				distanceFrom = d;
			}
			else{
				speedTo = speed;
				distanceTo = d;
			}
		}while(Math.abs(distance - d) > 0.005 && speedBall < 0.01);
		return speed;
	}
	
	public double minSpeedToPosition(Position position) {
		double distance = distance(position);
		double angle = angle(position);
		double speedFrom = 0, speedTo = MAX_SPEED_SHOOT;
		int t = 1000;
		Trajectory trajectory = new Trajectory(this, speedTo, angle, 0);
		double distanceFrom = 0, distanceTo = trajectory.get(t).distance(this);
		double speed, d;
		if(distance >= distanceTo)
			return speedTo;
		do{
			speed = speedFrom + (speedTo - speedFrom)*(distance - distanceFrom)/(distanceTo - distanceFrom);
			trajectory = new Trajectory(this, speed, angle, 0);
			d = trajectory.get(t).distance(this);
			if(distance > d){
				speedFrom = speed;
				distanceFrom = d;
			}
			else{
				speedTo = speed;
				distanceTo = d;
			}
		}while(Math.abs(distance - d) > 0.005);
		return speed;
	}
}
