package org.javahispano.javacup.tacticas.jvc2013.masia13.model;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.ACELERATION_RATE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.CONTROL_DISTANCE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.ENERGY_RATE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.GOALKEEPER_CONTROL_DISTANCE_INSIDE_AREA;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.GOALKEEPER_HEIGHT_INSIDE_AREA;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.PLAYER_HEIGHT;

import java.util.Iterator;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Aceleration;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;


public abstract class Player extends Position{

	public final int index;
	public final boolean isGoalKeeper;
	public final double speedFactor;
	public final double speed;
	public final double power;
	public final double maxSpeedShoot;
	public final double maxPrecision;
	
	public double speedShoot;
	public double precision;
	public double maxErrorAngle;
	public int iterationsToShoot;
	public boolean canShoot;
	public double energy;
	public double aceleration;
	public boolean inOffside;
	
	protected boolean updateAceleration; 
	
	
	public boolean enabledToRecoveryBall;
	public boolean canRecoveryBall;
	public Position recoveryBallPosition;
	public int iterationToBall;
	
	public Aceleration innerAceleration;
	protected Position lastMove = new Position(0, 0);
	
	public Player(int index, PlayerDetail playerDetail){
		super(0, 0);
		this.index = index;
		this.isGoalKeeper = playerDetail.isGoalKeeper();
		this.speedFactor = playerDetail.getSpeed();
		this.speed = Constants.getVelocidad(speedFactor);
		this.power = playerDetail.getPower();
		this.maxSpeedShoot = Constants.getVelocidadRemate(power);
		this.maxPrecision = playerDetail.getPrecision();
		
		this.enabledToRecoveryBall = true;
	}
	
	public void update(double x, double y, double energy, double aceleration ) {
		this.x = x;
		this.y = y;
		this.aceleration = aceleration;
		if(this.innerAceleration == null)
			this.innerAceleration = new Aceleration();
		else if (updateAceleration)
			innerAceleration.update(lastMove);
		if(innerAceleration.getAceleration() != aceleration){
			innerAceleration.update(aceleration);
		}
		this.energy = energy;
		this.speedShoot = maxSpeedShoot*Math.min(energy*Constants.ENERGIA_DISPARO, 1);
		this.precision = maxPrecision*energy;
		this.maxErrorAngle = getErrorAnglePercent()*Math.PI/2;
		this.canRecoveryBall = false;
		this.canShoot = false;
		this.updateAceleration = false;
	}
	
	public double getErrorAnglePercent(){
		return Constants.getErrorAngular(precision);
	}
	
	public boolean canRecoveryBall(Position ball, int iterations) {
		double height;
		double ballControlDistance;
		if (isGoalKeeper && insideArea(ball)) {
            height = GOALKEEPER_HEIGHT_INSIDE_AREA;
            ballControlDistance = GOALKEEPER_CONTROL_DISTANCE_INSIDE_AREA;
        } else {
            height = PLAYER_HEIGHT;
            ballControlDistance = CONTROL_DISTANCE;
        }
		return iterationsToShoot <= iterations && ball.z <= height && runDistance(iterations, ball) + ballControlDistance >= distance(ball);
	}

	public int iterationsToBall(Position ball) {
		double height;
		double ballControlDistance;
		if (isGoalKeeper && insideArea(ball)) {
            height = GOALKEEPER_HEIGHT_INSIDE_AREA;
            ballControlDistance = GOALKEEPER_CONTROL_DISTANCE_INSIDE_AREA;
        } else {
            height = PLAYER_HEIGHT;
            ballControlDistance = CONTROL_DISTANCE;
        }
		if(height >= ball.z){
			return iterationsToRunDistance(Math.max(distance(ball) - ballControlDistance, 0), ball);
		}	
		return Integer.MAX_VALUE;
	}
	
	public int iterationsTo(Position position) {
		return iterationsToRunDistance(distance(position),position);
	}
	
	public int iterationsToRunDistance(double distance, Position dir) {
		if(distance <= 0)
			return 0;
		Aceleration aceleration = new Aceleration(innerAceleration, dir);
		double aMin = Math.min(aceleration.x, aceleration.y);
    	int I = (int)Math.ceil((1 - aMin)/ACELERATION_RATE);
		double maxAcelerationDistance = runDistance(I, aceleration);
		if(distance > maxAcelerationDistance){
			double K = 2*(distance - maxAcelerationDistance)/speed + 2*I*energy + ENERGY_RATE*I - I*I*ENERGY_RATE; 
			return (int) Math.ceil((2*energy + ENERGY_RATE - Math.sqrt((2*energy + ENERGY_RATE)*(2*energy + ENERGY_RATE) - 4*K*ENERGY_RATE))/(2*ENERGY_RATE)); 
		}
		int from = 1, to = I - 1;
		double distanceFrom = runDistance(from, aceleration);
		double distanceTo = maxAcelerationDistance;
		if(distance <= distanceFrom)
			return 1;
		while(to > from){
			int t = (int)Math.ceil(from + (distance - distanceFrom)/(distanceTo - distanceFrom)*(to - from));
			double distancet = runDistance(t, aceleration);
			if(distancet == distance)
				return t;
			if(distancet > distance){
				to = t - 1;
				distanceTo = runDistance(to, aceleration);
				if(distanceTo == distance)
					return to;
				if(distanceTo < distance)
					return to + 1;
			}
			else{
				from = t + 1;
				distanceFrom = runDistance(from, aceleration);
				if(distanceFrom >= distance)
					return from;
			} 
		}
		return from;
	}

	public double runDistance(int iterations, Aceleration aceleration){
		return runDistance(iterations, aceleration, energy);
	}
	
	public double runDistance(int iterations, Aceleration aceleration, double energy){
		if(iterations <= 0)
    		return 0;
		if(aceleration.getAceleration() == 1)
			return speed*(energy*iterations - iterations*(iterations - 1)*ENERGY_RATE/2d);
    	double aMin = Math.min(aceleration.x, aceleration.y);
    	double aMax = Math.max(aceleration.x, aceleration.y);
    	int I0 = (int)Math.ceil((1 - aMax)/ACELERATION_RATE);
    	if(iterations <= I0)
    		return speed*(energy*(aMin * aMax* iterations + iterations*(iterations - 1)*(aMin + aMax)*ACELERATION_RATE/2d + iterations*(iterations - 1)*(2*iterations - 1)*ACELERATION_RATE*ACELERATION_RATE/6d) -(iterations*(iterations - 1)*aMin * aMax/2d + iterations*(iterations - 1)*(2*iterations - 1)*(aMin + aMax)*ACELERATION_RATE/6d + (iterations*(iterations - 1)/2d)*(iterations*(iterations - 1)/2d)*ACELERATION_RATE*ACELERATION_RATE) *ENERGY_RATE);
    	int I = (int)Math.ceil((1 - aMin)/ACELERATION_RATE);
    	double d0 = speed*(energy*(aMin * aMax* I0 + I0*(I0 - 1)*(aMin + aMax)*ACELERATION_RATE/2d + I0*(I0 - 1)*(2*I0 - 1)*ACELERATION_RATE*ACELERATION_RATE/6d) -(I0*(I0 - 1)*aMin * aMax/2d + I0*(I0 - 1)*(2*I0 - 1)*(aMin + aMax)*ACELERATION_RATE/6d + (I0*(I0 - 1)/2d)*(I0*(I0 - 1)/2d)*ACELERATION_RATE*ACELERATION_RATE) *ENERGY_RATE);
    	if(iterations <= I)	{
    		int K = iterations - I0;
    		return d0 + speed*((energy - I0*ENERGY_RATE)*(K*(aMin + I0*ACELERATION_RATE) + K*(K - 1)*ACELERATION_RATE/2d) - (K*(K - 1)*(aMin + I0*ACELERATION_RATE) + K*(K - 1)*(2*K - 1))*ENERGY_RATE);
    	}
    	int K = I - I0;
    	double d1 = d0 + speed*((energy - I0*ENERGY_RATE)*(K*(aMin + I0*ACELERATION_RATE) + K*(K - 1)*ACELERATION_RATE/2d) - (K*(K - 1)*(aMin + I0*ACELERATION_RATE) + K*(K - 1)*(2*K - 1))*ENERGY_RATE);
    	K = iterations - I;
    	return d1 + speed*((energy - I*ENERGY_RATE)*K - K*(K - 1)*ENERGY_RATE/2d);
	}
	
    public double runDistance(int iterations, Position dir){
    	return runDistance(iterations, new Aceleration(innerAceleration, dir));
    }
    
    public Position runPosition(int iterations, Position dir) {
		return moveAngle(angle(dir), runDistance(iterations, dir));
	}
    
    public Iterable<Position> trajectory(final Position from, final int iterationsToFrom, final Position to){
		return new Iterable<Position>() {
			
			@Override
			public Iterator<Position> iterator() {
				return new Iterator<Position>() {
					Aceleration aceleration = distance(from) > 0? new Aceleration(new Aceleration(innerAceleration, from), to): new Aceleration(innerAceleration, to);
					double energy = Player.this.energy - ENERGY_RATE*iterationsToFrom;
					Position current = from;
					double angle = from.angle(to);
					int i = 1;
					@Override
					public void remove() {
					}
					
					@Override
					public Position next() {
						return current;
					}
					
					@Override
					public boolean hasNext() {
						current = from.moveAngle(angle, runDistance(i, aceleration, energy));
						i++;
						return current.isInsideGameField();
					}
				};
			}
		};
	}
    
    public abstract Iterable<Position> trajectoryToGoal(final Position from, final int iterationsToFrom);
    
	protected abstract boolean insideArea(Position ball);
}
