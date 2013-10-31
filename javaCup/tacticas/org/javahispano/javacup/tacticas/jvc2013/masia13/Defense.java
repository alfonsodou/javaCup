package org.javahispano.javacup.tacticas.jvc2013.masia13;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.MY_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.MY_GOAL_LEFT_POST;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.MY_GOAL_RIGHT_POST;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.CONTROL_DISTANCE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.ENERGY_RATE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.WAIT_KICK_ITERATIONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javahispano.javacup.tacticas.jvc2013.masia13.model.MyPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.RivalPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;

public class Defense extends Strategy{
	
	private final int MIN_DEFENSE_COUNT = 4;
	private final int CENTRAL_DEFENSE_COUNT = 2;
	private int defenseCount = MIN_DEFENSE_COUNT;
	private double offsideLine;
	private boolean throwingOffside = true;
	
	public void execute(){
		defenseCount = attacking? MIN_DEFENSE_COUNT : MIN_DEFENSE_COUNT + 4;
		throwingOffside = true;
		boolean goForward = true;
		offsideLine = rivalOffside;
		if(!attacking){
			if(offsideLine >= rivalRecoveryBallPosition.y){
				offsideLine = rivalRecoveryBallPosition.y;
				goForward = false;
			}else if(rivalClosestToBall.canShoot)
				goForward = false;
		}else{
			if (offsideLine >= recoveryBallPosition.y){
				offsideLine = recoveryBallPosition.y;
				goForward = false;
			}
		} 
		List<DangerousPosition> dangerousPositions = calculateDangerousPositions();
		defenseCount = dangerousPositions.size();
		boolean[] exclude = new boolean[11];
		boolean[] defending = new boolean[11]; 
		exclude[goalkeeper.index] = true;
		defending[goalkeeper.index] = true;
		for (int i = defenseCount + 1; i < exclude.length; i++) {
				exclude[i] = true;
		}
		for (DangerousPosition dangerousPosition : dangerousPositions) {
			MyPlayer defender;
			if(dangerousPosition.isBallPosition){
				defender = recoveryBall(defending);
				if(offsideLine >= defender.nextPosition.y){
					offsideLine = defender.nextPosition.y;
					goForward = false;
				}
			}
			else
				defender = mark(dangerousPosition, exclude);
			if(defender != null){
				exclude[defender.index] = true;
				defending[defender.index] = true;	
			}
		}
		if(!rivalClosestToBall.canShoot && (attacking || throwingOffside)){
			if(goForward)
				offsideLine = Math.min(offsideLine + 0.5, 0);
			for (MyPlayer myPlayer : myPlayers) {
				if(defending[myPlayer.index] && myPlayer.nextActualPosition != null && myPlayer.nextActualPosition.y < offsideLine)
					myPlayer.moveTo(new Position(myPlayer.nextActualPosition.x, offsideLine));
			}
		}
		
	}

	private MyPlayer mark(DangerousPosition dangerousPosition , boolean[] exclude) {
		int iterations = 1;
		Position best = null;
		double bestInterations = Integer.MAX_VALUE;
		MyPlayer bestDefender = null;
		boolean unstoppable = true; 
		for (Position position : dangerousPosition.rival.trajectoryToGoal(dangerousPosition.rival.runPosition(1, MY_GOAL_CENTER).getInsideGameField(), 1)) {
			MyPlayer nearestDefender = null;
			int nearestDefenderIterations = Integer.MAX_VALUE;
			for (MyPlayer defender : myPlayers) {
				if(!exclude[defender.index]){
					int defenderIterations = defender.iterationsTo(position);
					if(defenderIterations < nearestDefenderIterations){
						nearestDefenderIterations = defenderIterations;
						nearestDefender = defender;
					}
				}
			}
			if(nearestDefenderIterations <= iterations + 1){
				bestDefender = nearestDefender;
				best = position;
				unstoppable = false;
				break;
			}
			if(nearestDefenderIterations < bestInterations){
				bestDefender = nearestDefender;
				bestInterations = nearestDefenderIterations;
				best = position;
			}
			iterations++;
		}
		if(bestDefender == null)
			return null;
		if(attacking && (!rivalClosestToBall.canRecoveryBall || iterationToBall < rivalIterationToBall)
				&& (bestDefender.lastSleepIteration + (bestDefender.energy <= 1 - 2*ENERGY_RATE? 1:2) < iterations ) && bestDefender.y >= offsideLine){
			bestDefender.moveTo(new Position(bestDefender));
			bestDefender.lastSleepIteration = iterations;
			return bestDefender;
		}
		if(bestDefender.index <= CENTRAL_DEFENSE_COUNT)
			bestDefender.moveTo(new Position(best.x, Math.min(best.y, 0)));
		else
			bestDefender.moveTo(best);
		if(unstoppable && dangerousPosition.y >= offsideLine)
			throwingOffside = false;
		return bestDefender;
	}

	private MyPlayer recoveryBall(boolean[] exclude) {
		if(isService && !isGoalKick){
			MyPlayer player = null;
			for (int i = 0; i < 11; i++) {
				if(myPlayers[i].iterationToBall + serviceIterations < WAIT_KICK_ITERATIONS){
					if(player == null || player.isGoalKeeper || (myPlayers[i].index > MIN_DEFENSE_COUNT && player.index <= MIN_DEFENSE_COUNT) || myPlayers[i].iterationToBall < player.iterationToBall)
						player = myPlayers[i];
				}else if(player == null || (player.iterationToBall + serviceIterations >= WAIT_KICK_ITERATIONS && (player.isGoalKeeper || myPlayers[i].iterationToBall < player.iterationToBall)))
					player = myPlayers[i];
			}
			player.moveTo(recoveryBallPosition);
			return player;
		}
		if(attacking || (playerClosestToBall.canRecoveryBall && iterationToBall == rivalIterationToBall)){
			int i = iterationToBall + 1;
			while(i < rivalIterationToBall && i < ball.trayectory.length){
				if(ball.trayectory[i].isInsideGameField()){
					int it = playerClosestToBall.iterationsToBall(ball.trayectory[i]);
					if (it <= i) {
		            	if(ball.trayectory[i].distance(ball.trayectory[i-1]) < 2*CONTROL_DISTANCE){
		            		playerClosestToBall.moveTo(ball.trayectory[i-1].medium(ball.trayectory[i]));
		            		return playerClosestToBall;
		            	}
		            	else
		            		i++;
		            }else break;
				}else break;
			}
			i = Math.min(i, ball.trayectory.length);
			playerClosestToBall.moveTo(ball.trayectory[i-1]);
			return playerClosestToBall;
		}
		int iterations = 1;
		Position best = null;
		double bestInterations = Integer.MAX_VALUE;
		MyPlayer bestDefender = null;
		for (Position position : rivalClosestToBall.trajectoryToGoal(recoveryBallPosition.getInsideGameField(), rivalIterationToBall)) {
			MyPlayer nearestDefender = null;
			int nearestDefenderIterations = Integer.MAX_VALUE;
			for (MyPlayer defender : myPlayers) {
				if(!exclude[defender.index]){
					int defenderIterations = defender.iterationsTo(position);
					if(defenderIterations < nearestDefenderIterations){
						nearestDefenderIterations = defenderIterations;
						nearestDefender = defender;
					}
				}
			}
			if(nearestDefenderIterations <= rivalIterationToBall + iterations){
				nearestDefender.moveTo(position);
				return nearestDefender;
			}
			if(nearestDefenderIterations < bestInterations){
				bestDefender = nearestDefender;
				bestInterations = nearestDefenderIterations;
				best = position;
			}
			iterations++;
		}
		throwingOffside = false;
		bestDefender.moveTo(best);
		return bestDefender;
	}
	
	private List<DangerousPosition> calculateDangerousPositions() {
		int offsideIterations = 10 + (attacking? iterationToBall : rivalIterationToBall);  
		List<DangerousPosition> dangerousPositions = new ArrayList<DangerousPosition>();
		for (int i = 0; i < rivalPlayers.length; i++) {
			if(i != rivalClosestToBall.index && rivalPlayers[i].y + rivalPlayers[i].runDistance(offsideIterations, new Position(rivalPlayers[i].x, offsideLine))>= offsideLine)
				dangerousPositions.add(new DangerousPosition(rivalPlayers[i]));
		}
		int maxDangerousPositionCount = defenseCount;
		if(attacking || (playerClosestToBall.canRecoveryBall && iterationToBall == rivalIterationToBall)){
			Collections.sort(dangerousPositions);
			if(!isGoalKick){
				dangerousPositions.add(0, new DangerousPosition(recoveryBallPosition));
				maxDangerousPositionCount++;
			}
		}else {
			dangerousPositions.add(new DangerousPosition(rivalClosestToBall, rivalRecoveryBallPosition));
			Collections.sort(dangerousPositions);
			maxDangerousPositionCount++;
		}
		int k = dangerousPositions.size() - 1;
		while(dangerousPositions.size() > maxDangerousPositionCount){
			if(!dangerousPositions.get(k).isBallPosition)
				dangerousPositions.remove(k);
			k--;
		}
		return dangerousPositions;
	}
	
	class DangerousPosition extends Position implements Comparable<DangerousPosition>{
		
		public final RivalPlayer rival;
		public final boolean isBallPosition;
		private double fitness = -1;
		
		private DangerousPosition(RivalPlayer rival) {
			super(rival);
			this.rival = rival;
			this.isBallPosition = false;
		}
		
		private DangerousPosition(RivalPlayer rival, Position ball) {
			super(ball);
			this.rival = rival;
			this.isBallPosition = true;
		}
		
		private DangerousPosition(Position ball) {
			super(ball);
			this.isBallPosition = true;
			this.rival = null;
		}
		
		private Position unstoppablePosition(){
			int iterations = 1;
			Position best = null;
			double bestInterations = Integer.MAX_VALUE;
			for (Position position : rival.trajectoryToGoal(this, 0)) {
				int nearestDefenderIterations = Integer.MAX_VALUE;
				for (MyPlayer defender : myPlayers) {
					int defenderIterations = defender.iterationsTo(position);
					if(defenderIterations < nearestDefenderIterations){
						nearestDefenderIterations = defenderIterations;
					}
				}
				if(nearestDefenderIterations <= iterations){
					best = position;
					break;
				}
				if(nearestDefenderIterations < bestInterations){
					bestInterations = nearestDefenderIterations;
					best = position;
				}
				iterations++;
			}
			if(best == null)
				return this;
			return best;
		}
		
		private double fitness(Position position){
			double fitness = 0;
			fitness += sq(1 - normalize(position.distance(MY_GOAL_CENTER),FIELD_HEIGHT/2));
			fitness += sq(normalize(FIELD_HEIGHT/2 - position.y, FIELD_HEIGHT));
			if(position.y <= 0){
				double angle = Math.abs(position.angle(MY_GOAL_RIGHT_POST) - position.angle(MY_GOAL_LEFT_POST));
				fitness += sq(normalize(angle, 2*rival.maxErrorAngle));
				fitness += sq(normalize(angle, Math.PI));
			}
			fitness/= 4;
			fitness = Math.sqrt(fitness);
			return fitness;
		}
		
		private double fitness(){
			if(fitness == -1){
				fitness = Math.sqrt((sq(fitness(this)) + sq(fitness(unstoppablePosition())))/2);
			}
			return fitness;
		}
		
		@Override
		public int compareTo(DangerousPosition other) {
			return ((Double)other.fitness()).compareTo(fitness());
		}
	}
}
