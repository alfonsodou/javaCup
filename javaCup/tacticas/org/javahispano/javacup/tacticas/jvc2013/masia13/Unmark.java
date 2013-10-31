package org.javahispano.javacup.tacticas.jvc2013.masia13;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_WIDTH;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_LEFT_POST;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_RIGHT_POST;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.ENERGY_RATE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.MIN_ACELERATION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javahispano.javacup.tacticas.jvc2013.masia13.model.MyPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;

public class Unmark extends Strategy{
	
	private List<UnmarkPosition> selected = new ArrayList<UnmarkPosition>();
	private double[] rivalBallAngle = new double[11];
	private Position ballPosition;
	private double minAngleBall;
	private double maxAngleBall;
	
	public void execute(){
		selected.clear();
		ballPosition = recoveryBallPosition.getInsideGameField();
		maxAngleBall = ballPosition.angle(RIVAL_GOAL_LEFT_POST);
		minAngleBall = ballPosition.angle(RIVAL_GOAL_RIGHT_POST);
		int[] exclude = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		int count = 11;
		for (MyPlayer player : myPlayers) {
			if(player.nextPosition != null){
				selected.add(new UnmarkPosition(player.nextPosition));
				exclude[player.index] = player.index;
				count--;
			}else if(!attacking && !isEnabledToShoot && (!playerClosestToBall.canRecoveryBall || iterationToBall > rivalIterationToBall)
					&& (player.lastSleepIteration + 2 < iterations || (player.energy <= 1 - 2*ENERGY_RATE && !player.inOffside))){
				exclude[player.index] = player.index;
				count--;
				player.lastSleepIteration = iterations;
			}
		}
		List<UnmarkPosition> positions = calculatePositions(count);
		exclude[goalkeeper.index] = goalkeeper.index;
		for (UnmarkPosition attackPosition : positions) {
			MyPlayer player = attackPosition.nearest(myPlayers, exclude);
			if(isEnabledToShoot && player.y <= offside && attackPosition.y == offside){
				player.moveTo(new Position(attackPosition.x, attackPosition.y + player.speed*(player.energy - ENERGY_RATE)*MIN_ACELERATION));
			}
			else
				player.moveTo(attackPosition);
			exclude[player.index] = player.index;
		}
	}

	final static double MIN_RATIO = FIELD_HEIGHT / 2;
	final static double MAX_RATIO = 3*FIELD_HEIGHT / 4;
	final static double DELTA_RATIO = MAX_RATIO - MIN_RATIO;
	
	private List<UnmarkPosition> calculatePositions(int count) {
		double power = playerClosestToBall.power;
		if(!attacking)
			power = 0;
		else if(isThrowIn)
			power *= 0.75;
		double ratio = MIN_RATIO + power*DELTA_RATIO;
		for (int i = 0; i < rivalBallAngle.length; i++) {
			rivalBallAngle[i] = ballPosition.angle(rivalPlayers[i]);
		}
		List<UnmarkPosition> positions = new ArrayList<UnmarkPosition>();
		double offside = Math.max(this.offside, ballPosition.y);
		while(ratio > 0){
			double angle = 0;
			while(angle < 2*Math.PI){
				Position position = ballPosition.moveAngle(angle, ratio);
				position.y = Math.min(position.y, offside);
				if(position.isInsideGameField() && !position.insideRivalGoalArea()){
					positions.add(new UnmarkPosition(position));
				}
				angle += 5*Math.PI/180;
			}
			ratio -= 3;
		}
		List<UnmarkPosition> result = new ArrayList<Unmark.UnmarkPosition>(count);
		for (int i = 0; i < count; i++) {
			Collections.sort(positions);
			selected.add(positions.get(0));
			result.add(positions.get(0));
			positions.remove(0);
		}
		return result;
	}
	
	
	class UnmarkPosition extends Position implements Comparable<UnmarkPosition>{
		double fitness;
		double ballAngle;
		
		private UnmarkPosition(Position position) {
			super(position);
			ballAngle = ballPosition.angle(this);
			this.fitness = 0;
			this.fitness += sq(calculateAngleFactor(ballPosition));
			this.fitness += (1 - normalize(insideRivalPenaltyArea()? 0 : distance(RIVAL_GOAL_CENTER), FIELD_HEIGHT));
			if(minAngleBall < maxAngleBall)
				this.fitness += sq(1 - normalize(Math.min(Math.max(ballAngle - minAngleBall, 0), Math.max(maxAngleBall - ballAngle, 0)), (minAngleBall + maxAngleBall)/2));
			else
				this.fitness += 1;
			this.fitness += (normalize(Math.abs(angle(RIVAL_GOAL_LEFT_POST) - angle(RIVAL_GOAL_RIGHT_POST)), Math.PI));
		}
		
		private double calculateAngleFactor(Position ball){
			double minAngle = 2*Math.PI;
			double d = distance(ball);
			for (int j = 0; j < rivalPlayers.length; j++) {
				if(ball.distance(rivalPlayers[j]) <= d){
					double angle = Math.abs(ballAngle -  rivalBallAngle[j]);
					if(angle < minAngle)
						minAngle = angle;
				}
			}
			return normalize(minAngle, playerClosestToBall.maxErrorAngle);
		}
		
		@Override
		public int compareTo(UnmarkPosition other) {
			if(isGoalKick){
				if(!insideMyPenaltyArea() && other.insideMyPenaltyArea())
					return -1;
				if(insideMyPenaltyArea() && !other.insideMyPenaltyArea())
					return 1;
			}
			return other.calculateFitness().compareTo(calculateFitness());
		}

		private Double calculateFitness() {
			return (fitness + sq(calculateSelectedFactor()))/5;
		}
		
		private double calculateSelectedFactor() {
			if(selected.size() == 0)
				return 1;
			UnmarkPosition nearest = selected.get(0);
			double minDistance = distance(nearest);
	        for (int i = 0; i < selected.size(); i++) {
	        	double distance = distance(selected.get(i));
	        	if(distance < minDistance){
					minDistance = distance;
					nearest = selected.get(i);
				}
	        }
	        if(minDistance < FIELD_WIDTH/6)
	        	return  normalize(Math.abs(x - nearest.x ), FIELD_WIDTH/6);
	        return 1;
		}
	}
}

