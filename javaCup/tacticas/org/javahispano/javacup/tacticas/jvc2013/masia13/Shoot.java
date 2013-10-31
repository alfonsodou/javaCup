package org.javahispano.javacup.tacticas.jvc2013.masia13;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_LEFT_POST;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_RIGHT_POST;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.ITERATIONS;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.ITERATIONS_TO_KICK;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.MAX_VERTICAL_ANGLE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.CONTROL_DISTANCE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.GOALKEEPER_CONTROL_DISTANCE_INSIDE_AREA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.javahispano.javacup.tacticas.jvc2013.masia13.Pass.PassData;
import org.javahispano.javacup.tacticas.jvc2013.masia13.Pass.PassDataState;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.Ball;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.MyPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.RivalPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.BallPosition;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Trajectory;

public class Shoot extends Strategy{
	
	public int shootCount = 0;
	
	void execute(PassData passData){
		ShootData shootData = calculateShoot(ball, shooter);
		double threshold = calculateThreshold();
		if(shootData.probability > 0 && shootData.probability >= threshold){
			double passGoalprobability = 0;
			if(passData.state == PassDataState.Received && (!ball.insideRivalPenaltyArea() || shootData.probability < 0.40)) 
				passGoalprobability = ((ball.insideRivalPenaltyArea()? 0.90 : 1.0) - shooter.getErrorAnglePercent())*passData.receivedAvg * calculateShoot(passData.ball, passData.getReceiver()).probability;
			if (shootData.probability >= passGoalprobability){ 
				shoot(shootData);
				shootCount++;
			}
		}
	}
	
	private void shoot(ShootData shootData) {
		for (int i = 0; i < canShootPlayers.length; i++) {
			if(canShootPlayers[i].enabledToRecoveryBall || !attacking){
				canShootPlayers[i].hitBall(shootData.angle, shootData.speedShoot/canShootPlayers[i].speedShoot, shootData.verticalAngle);
			}
						
		}
	}
	
	private double calculateThreshold() {
		if(unstoppableIterations() > 2*ITERATIONS_TO_KICK)
			return 0.70;
		if(ball.insideRivalPenaltyArea())
			return 0;
		if(myGoals > rivalGoals + (possession> 0.55? 2: 3) || (myGoals >= rivalGoals + (possession> 0.70? 1: 2) && iterations/(shootCount + 1) < ITERATIONS/15))
			return 0.35 + Math.min(Math.max(myGoals - rivalGoals, 0), 7)*0.05;
		return 0.09;
	} 
	
	public ShootData calculateShoot(Position ballPosition, MyPlayer player) {
		double minAngle = ballPosition.angle(RIVAL_GOAL_RIGHT_POST.movePosition(-Ball.RADIUS, 0));
		double maxAngle = ballPosition.angle(RIVAL_GOAL_LEFT_POST.movePosition(Ball.RADIUS, 0));
		ShootData shoot = null;
		List<Future<ShootData>> shoots = new ArrayList<Future<ShootData>>();
		int errorAngle = (int) Math.ceil(player.maxErrorAngle/Math.PI*180);
		double angle = minAngle;
		double maxSpeedShoot = player.speedShoot;
		double minSpeedShoot = Math.min(ball.minSpeedToPosition(RIVAL_GOAL_CENTER), maxSpeedShoot);
		while(minSpeedShoot <= maxSpeedShoot){
			double verticalAngle = 0;
			while(verticalAngle <= MAX_VERTICAL_ANGLE){
				angle = minAngle;
			 	shoots.clear();
				while(angle <= maxAngle){
					shoots.add(threadPool.submit(createShootTask(player, ballPosition, minSpeedShoot, angle, verticalAngle)));
					angle += Math.PI/180;
				}
				for (int i = 0; i < shoots.size(); i++) {
					if(shoot == null)
						shoot = reevaluateShoot(i, shoots, errorAngle);
					else
						shoot = shoot.compare(reevaluateShoot(i, shoots, errorAngle));
				}
				verticalAngle += Math.PI/180;
			}
			if(minSpeedShoot < maxSpeedShoot)
				minSpeedShoot = Math.min(minSpeedShoot + 0.05, maxSpeedShoot);
			else break;
		}
		return shoot;
	}
	
	private Callable<ShootData> createShootTask(final MyPlayer player, final Position ballPosition, final double minSpeedShoot, final double angle, final double verticalAngle) {
		
		return new Callable<Shoot.ShootData>() {
			
			@Override
			public ShootData call() throws Exception {
				return new Calculator().evaluate(player, ballPosition, minSpeedShoot, angle, verticalAngle);
			}
		};
	}

	private ShootData reevaluateShoot(int i, List<Future<ShootData>> shoots, int errorAngle) {
		try {
			double probability = 0;
			int shootOnGoalCount = 0;
			for (int j = Math.max(i - errorAngle, 0); j <= Math.min(i + errorAngle, shoots.size() - 1); j++) { 
				ShootData shootData = shoots.get(j).get(); 
				probability += shootData.probability;
				if(shootData.probability > 0)
					shootOnGoalCount++;
			}
			probability /= (2*errorAngle + 1);
			ShootData shootData = shoots.get(i).get();
			return new ShootData(shootData.ball, shootData.angle, shootData.speedShoot, shootData.verticalAngle, probability, ((double)shootOnGoalCount)/(2*errorAngle + 1));
		} catch (Exception e) {
			return null;
		}
	}
	
	private int unstoppableIterations() {
		int unstoppableIterations = 1; 
		for (Position position : shooter.trajectoryToGoal()) {
			int nearestDefenderIterations = Integer.MAX_VALUE;
			for (RivalPlayer defender : rivalPlayers) {
				int defenderIterations = defender.iterationsTo(position);
				if(defenderIterations < nearestDefenderIterations){
					nearestDefenderIterations = defenderIterations;
				}
			}
			if(nearestDefenderIterations <= unstoppableIterations){
				break;
			}
			unstoppableIterations++;
		}
		return unstoppableIterations;
	}
	
	class Calculator{
	 
		double intercepProbability;
		int playerIndex;
		
		public ShootData evaluate(MyPlayer shooter, Position ballPosition, double speedShoot, double angle, double verticalAngle) {
			Trajectory trajectory = new Trajectory(ballPosition, speedShoot, angle, verticalAngle);
			intercepProbability = 0;
			double probability = 0;
			playerIndex = -1;
			BallPosition ball = null;
			for (Iterator<BallPosition> iterator = trajectory.iterator(); iterator.hasNext();) {
				ball = iterator.next();
				probability = calculateProbability(ball);
				if(probability >= 0){
					break;
				}
			}
			probability = Math.max(probability, 0);
			return new ShootData(ball, angle, speedShoot, verticalAngle, probability, 0);
		}
		
		private double calculateProbability(BallPosition ball) { 
			if(ball.isGoal){
				if(!ball.isVaselineGoal)
					return 1 - intercepProbability;
			}else if(!ball.isInsideGameField())
				return 0;
			int it;
			for (int i = 0; i < rivalPlayers.length; i++) {
				it = rivalPlayers[i].iterationsToBall(ball);
				if (it <= ball.iteration && rivalPlayers[i].iterationsToShoot <= it) {
					if(playerIndex == -1){
						playerIndex = i;
						intercepProbability = (7d - ball.speed) / 7d;
						return ball.isVaselineGoal? 1 - intercepProbability : -1;
					}else if (playerIndex == i){
						if(ball.speed > (rivalPlayers[i].isGoalKeeper && ball.insideRivalGoalArea()? GOALKEEPER_CONTROL_DISTANCE_INSIDE_AREA : CONTROL_DISTANCE)){
							intercepProbability = (7d - ball.speed) / 7d;
							return ball.isVaselineGoal? 1 - intercepProbability : -1;
						}else
							return 0;
					}else
						return 0;
	            }		            
	        }         
			return ball.isVaselineGoal? 1 - intercepProbability : -1;
		}
	}
	
	class ShootData{
		final double angle;
		final double speedShoot;
		final double verticalAngle;
		final double shootOnGoalProbability;
		double probability;
		final BallPosition ball;
		
		public ShootData(BallPosition ball, double angle, double speedShoot, double verticalAngle, double probability, double shootOnGoalProbability) {
			super();
			this.angle = angle;
			this.speedShoot = speedShoot;
			this.verticalAngle = verticalAngle;
			this.probability = probability;
			this.shootOnGoalProbability = shootOnGoalProbability;
			this.ball = ball;
		}
		
		public ShootData compare(ShootData other) {
			if(probability*shootOnGoalProbability > other.probability*other.shootOnGoalProbability)
				return this;
			if(probability*shootOnGoalProbability < other.probability*other.shootOnGoalProbability)
				return other;
			if(probability > other.probability)
				return this;
			if(probability < other.probability)
				return other;
			if(ball.speed > other.ball.speed)
				return this;
			if(ball.speed < other.ball.speed)
				return other;
			return this;
		}
	}
}
