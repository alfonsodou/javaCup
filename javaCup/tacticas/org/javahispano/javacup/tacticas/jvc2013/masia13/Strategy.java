package org.javahispano.javacup.tacticas.jvc2013.masia13;

import java.util.concurrent.ExecutorService;

import org.javahispano.javacup.tacticas.jvc2013.masia13.model.Ball;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.MyPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.RivalPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;

public class Strategy {
	
	protected MyPlayer[] myPlayers = new MyPlayer[11];
	protected RivalPlayer[] rivalPlayers = new RivalPlayer[11];
	protected MyPlayer goalkeeper;
	protected int iterations;
	
	protected Ball ball;
	
	protected boolean isMyPass = false;
	protected MyPlayer lastShooter;
	protected boolean isServicePass = false;
	
	protected boolean attacking;
	
	protected int iterationToBall;
	protected MyPlayer playerClosestToBall;
	protected Position recoveryBallPosition;
	protected MyPlayer[] canShootPlayers;
	protected MyPlayer shooter;
	protected boolean isEnabledToShoot;
	
	protected int rivalIterationToBall;
	protected RivalPlayer rivalClosestToBall;
	protected Position rivalRecoveryBallPosition;
	
	protected double offside;
	protected double rivalOffside;
	
	protected int myGoals;
	protected int rivalGoals;
	
	protected double possession;
	
	protected double actualPossession = 0;
	
	protected boolean isService;
	protected boolean isRivalService;
	protected boolean isThrowIn;
	protected boolean isRivalThrowIn;
	
	protected int serviceIterations = 0;
	
	protected boolean isGoalKick;
	protected boolean serviceWaiting;
	
	protected boolean isGoalDefended; 
	
	protected ExecutorService threadPool;
	
	protected double sq(double value) {
		return value*value;
	}

	protected double normalize(double value, double min, double max){
		return (Math.max(min, Math.min(value, max)) - min)/(max - min);
	}
	
	protected double normalize(double value, double max){
		return normalize(value, 0, max);
	}
	 
}
