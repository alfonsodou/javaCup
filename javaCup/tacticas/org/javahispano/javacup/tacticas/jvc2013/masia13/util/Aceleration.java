package org.javahispano.javacup.tacticas.jvc2013.masia13.util;


import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.ACELERATION_RATE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.MIN_ACELERATION_X;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.MIN_ACELERATION_Y;

public class Aceleration {

	public double x = MIN_ACELERATION_X;
	public double y = MIN_ACELERATION_Y;
		
	private Position playerPosition = new Position(0, 0);
	
	private double dirX = 0;
	private double dirY = 0;
		
	public Aceleration(){
		this(1, 1);
	}
	
	public Aceleration(double x, double y) {
		this.x = x;
		this.y = y;		
	}
	
	public Aceleration(Aceleration aceleration0, Position moveTo) {
		this(aceleration0.x, aceleration0.y);
		this.dirX = aceleration0.dirX;
		this.dirY = aceleration0.dirY;
		this.playerPosition = new Position(aceleration0.playerPosition);
		update(moveTo);
	}
	
	public void update(Position playerPosition) {
		double previusDirX = dirX;
		double previusDirY = dirY;
		
		double Dx = playerPosition.x - this.playerPosition.x;
		dirX = (Dx == 0) ? 0 : (Math.signum(Dx)); 
			
		double Dy = playerPosition.y - this.playerPosition.y;
		dirY = (Dy == 0) ? 0 : (Math.signum(Dy));
		
		x = previusDirX != dirX? MIN_ACELERATION_X : Math.min(x + ACELERATION_RATE, 1);
		y = previusDirY != dirY? MIN_ACELERATION_Y : Math.min(y + ACELERATION_RATE, 1);
		this.playerPosition = new Position(playerPosition);		
	}
	
	public double getAceleration(){
		return x * y;
	}

	private final int nX = (int)Math.ceil((1 - MIN_ACELERATION_X)/ACELERATION_RATE) + 1;
	private final int nY = (int)Math.ceil((1 - MIN_ACELERATION_Y)/ACELERATION_RATE) + 1;
	
	public void update(double aceleration) {
		double ax = MIN_ACELERATION_X;
		for (int i = 0; i < nX; i++) {
			double ay = MIN_ACELERATION_Y;
			for (int j = 0; j < nY; j++) {
				if(aceleration == ax*ay){
					x = ax;
					y = ay;
					return;
				}
				ay = Math.min(ay + ACELERATION_RATE, 1);
			}
			ax = Math.min(ax + ACELERATION_RATE, 1);
		}
	}
}
