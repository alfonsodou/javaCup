package org.javahispano.javacup.tacticas.jvc2013.masia13;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.KICK_DISTANCE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.MY_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_LEFT_POST;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_RIGHT_POST;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.ITERATIONS;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.ITERATIONS_TO_KICK;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.MAX_VERTICAL_ANGLE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.CONTROL_DISTANCE;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.MAX_SPEED_SHOOT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.javahispano.javacup.tacticas.jvc2013.masia13.model.Ball;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.MyPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.BallPosition;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Trajectory;

public class Pass extends Strategy {
	
	private Shoot shoot = new Shoot();
	int[] iterationsToShoot = new int[11];
	
	double minAngleToGoal;
	double maxAngleToGoal;
	
	
	public Pass(Shoot shoot) {
		this.shoot = shoot;
	}
	
	private Future<PassData> passFuture;
	
	public void init(){
		passFuture = threadPool.submit(new Callable<PassData>() {

			@Override
			public PassData call() throws Exception {
				if(isGoalKick){
					return goalKick();
				}else if(isService)
					return service();
				else if(isEnabledToShoot){
					update();
					return calculatePass();
				};
				return null;
			}
		});	
	}
	
	public void execute(){
		try {
			PassData passData = passFuture.get();
			if(passData != null){
				pass(passData);
				if(!isService && ball.y >= -FIELD_HEIGHT/10)
					shoot.execute(passData);
			}	
		} catch (Exception e) {
		} 
	}
	
	private PassData service(){
		if(isEnabledToShoot){
			if(!serviceWaiting || rivalClosestToBall.distance(ball) >= KICK_DISTANCE){
				update();
				PassData bestPassData = calculatePass();
				if(!serviceWaiting || bestPassData.receivedAvg >= 0.6)
					return bestPassData;
			}
		}
		return null;
	}

	private PassData goalKick(){
		goalkeeper.moveTo(ball);
		return service();
	}

	private void pass(PassData passData) {
		pass(passData.ball, passData.speedShoot, passData.verticalAngle);
		passData.getReceiver().moveTo(passData.ball);
	}

	private void pass(Position position, double velocity, double verticalAngle){
		for (int i = 0; i < canShootPlayers.length; i++) {
			if(canShootPlayers[i].enabledToRecoveryBall || !attacking)
				pass(canShootPlayers[i], position, velocity, verticalAngle);
		}
	}
	
	private void pass(MyPlayer player, Position destiny, double speed, double verticalAngle) {
		double powerShoot = speed/player.speedShoot;
		if(isThrowIn){
			powerShoot /= .75;
		} 
		player.hitBall(destiny, powerShoot, verticalAngle);
	}
	
	private void update() {
		minAngleToGoal = ball.angle(RIVAL_GOAL_RIGHT_POST.movePosition(-Ball.RADIUS, 0));
		maxAngleToGoal = ball.angle(RIVAL_GOAL_LEFT_POST.movePosition(Ball.RADIUS, 0));
		
		for (int i = 0; i < myPlayers.length; i++) {
			iterationsToShoot[i] = myPlayers[i].iterationsToShoot;
		}
		for (int i = 0; i < canShootPlayers.length; i++) {
			iterationsToShoot[canShootPlayers[i].index] = ITERATIONS_TO_KICK;
		}
	}
	
	private final int anglesCount = 360;
	
	private PassData calculatePass(){
		final PassData drivingPassData = driving();
		try {
			double maxSpeedShoot = shooter.speedShoot;
			if(isThrowIn){
				maxSpeedShoot *= 0.75;
			}
			double minSpeedShoot = Math.min(drivingPassData.speedShoot, maxSpeedShoot);			
			double[] verticalAngles = new double[]{0, MAX_VERTICAL_ANGLE/12, MAX_VERTICAL_ANGLE/6, 2*MAX_VERTICAL_ANGLE/6, MAX_VERTICAL_ANGLE/2, 2*MAX_VERTICAL_ANGLE/3};
			int speedShootCount = 6;
			double speedShootStep = (maxSpeedShoot - minSpeedShoot)/speedShootCount;
			Collection<Callable<PassData>> tasks = new ArrayList<Callable<PassData>>();
			int[] verticalAnglesFrom = new int[] {0, 0, 1, 1, 2, 2};
			int[] verticalAnglesTo = new int[] {1, 3, 4, 5, 6, 6};
			for (int i = 0; i < speedShootCount; i++) {
				double speedShoot = minSpeedShoot + (i+1)*speedShootStep;
				for (int j = verticalAnglesFrom[i]; j < verticalAnglesTo[i]; j++) {
					tasks.add(createPassTask(speedShoot, verticalAngles[j]));
				}
			}
			PassData bestPassData = drivingPassData;
			List<Future<PassData>> futures = threadPool.invokeAll(tasks);
			for (Future<PassData> future : futures) {
				bestPassData = bestPassData.compare(future.get());
			}
			return bestPassData;
		} catch (Exception e) {
			return drivingPassData;
		}
	}

	private Callable<PassData> createPassTask(final double speedShoot, final double verticalAngle) {
		return new Callable<PassData>() {
			@Override
			public PassData call() throws Exception {
				return calculatePass(speedShoot, verticalAngle);
			}
		};
	}
	
	private PassData driving() {
		return driving(ITERATIONS_TO_KICK);
	}
	
	private PassData driving(int iterations) {
		int numAngles = 360;
		double dAngle = 360.0/numAngles;
    	Trajectory[] trajectory = new Trajectory[numAngles];
    	double[] speedShoot = new double[numAngles];
    	for (int i = 0; i < numAngles; i++) {
    		double distance = shooter.runDistance(iterations, shooter.moveAngle(i*dAngle*Math.PI/180, 5));
    		speedShoot[i] = ball.speedToDistance(iterations, distance);
    		trajectory[i] = new Trajectory(ball, speedShoot[i], i*dAngle*Math.PI/180, 0);
		} 
    	PassData[] datas = new PassData[numAngles];
    	int errorAngle = (int) Math.ceil((shooter.maxErrorAngle/Math.PI*180)/dAngle);
    	int goals, received;
    	PassData bestPassData = null;
    	for (int i = 0; i < numAngles; i++) {
    		if(datas[i] == null){
    			datas[i] = new Calculator().calculate(trajectory[i],shooter);
    			datas[i].speedShoot = speedShoot[i];
    			datas[i].verticalAngle = 0;
    		}
    		goals = received = 0;
    		for (int j = i - errorAngle; j <= i + errorAngle; j++) {
				int k = (numAngles + j) % numAngles;
				if(datas[k] == null){
					datas[k] = new  Calculator().calculate(trajectory[k], shooter);
					datas[k].speedShoot = speedShoot[i];
	    			datas[k].verticalAngle = 0;
				}
				goals += datas[k].state == PassDataState.Goal ? 1 : 0;
				received += datas[k].state == PassDataState.Received? 1 : 0;
			}
    		datas[i].goalsAvg = ((double)goals)/(2*errorAngle + 1);
    		datas[i].receivedAvg = ((double)received)/(2*errorAngle + 1);
    		if(bestPassData == null)
    			bestPassData = datas[i];
    		else
    			bestPassData = bestPassData.compare(datas[i]);	
		}
    	return bestPassData;
	}

	private PassData calculatePass(double speedShoot, double verticalAngle) {
		PassData bestPassData = null;
		double dAngle = 360.0/anglesCount;
    	Trajectory[] trajectory = new Trajectory[anglesCount];    	
    	for (int i = 0; i < anglesCount; i++) {
    		trajectory[i] = new Trajectory(ball, speedShoot, i*dAngle*Math.PI/180, verticalAngle);
		} 
    	PassData[] datas = new PassData[anglesCount];
    	int errorAngle = (int) Math.ceil((shooter.maxErrorAngle/Math.PI*180)/dAngle);
    	int goals, received;
    	for (int i = 0; i < anglesCount; i++) {
    		if(datas[i] == null){
    			datas[i] = new Calculator().calculate(trajectory[i]);
    			datas[i].speedShoot = speedShoot;
    			datas[i].verticalAngle = verticalAngle;
    		}
    		goals = received = 0;
    		for (int j = i - errorAngle; j <= i + errorAngle; j++) {
				int k = (anglesCount + j) % anglesCount;
				if(datas[k] == null){
					datas[k] = new Calculator().calculate(trajectory[k]);
					datas[k].speedShoot = speedShoot;
	    			datas[k].verticalAngle = verticalAngle;
				}
				goals += datas[k].state == PassDataState.Goal? 1 : 0;
				received += datas[k].state == PassDataState.Received? 1 : 0;
			}
    		datas[i].goalsAvg = ((double)goals)/(2*errorAngle + 1);
    		datas[i].receivedAvg = ((double)received)/(2*errorAngle + 1);
    		if(bestPassData == null){
    			bestPassData = datas[i];
    		}
    		else
    			bestPassData = bestPassData.compare(datas[i]);	
		}
    	return bestPassData;
	}	

	class Calculator{
		private MyPlayer receiver = null;
		
		public PassData calculate(Trajectory trajectory, MyPlayer receiver) {
			return calculate(trajectory, new MyPlayer[]{receiver}, new int[]{iterationsToShoot[receiver.index]});
		}
		
		public PassData calculate(Trajectory trajectory) {
			return calculate(trajectory, myPlayers, iterationsToShoot);
		}
		
		public PassData calculate(Trajectory trajectory, MyPlayer[] receivers, int[] iterationsToShoot) {
			PassData passData = null;
			for (BallPosition ball : trajectory) {
				passData = calculatePass(ball, trajectory.angle, receivers, iterationsToShoot);
				if(passData.state != PassDataState.Undefine)
					break;
			}
			return passData;
		}
		
		private PassData calculatePass(BallPosition ball, double angle, MyPlayer[] receivers, int[] iterationsToShoot) {
			if(ball.isGoal){
				if(!ball.isVaselineGoal)
					return new GoalPassData(ball, angle);
			}else if(!ball.isInsideGameField())
				return new LargePassData(ball);
			int rivalIterationsToBall = calculateRivalIterationsToBall(ball);
			if(rivalIterationsToBall <= ball.iteration)
				return new InterceptablePassData(ball);
			if(ball.isVaselineGoal)
				return new GoalPassData(ball, angle);
			int iterationsToBall = calculateIterationsToBall(ball, receivers, iterationsToShoot);
			if(iterationsToBall > ball.iteration)
				return new UndefinedPassData(ball);
			return new ReceivedPassData(ball, receiver);			
		}
		
		public int calculateIterationsToBall(Position ball, MyPlayer[] receivers, int[] iterationsToShoot) {
			int it, best = ITERATIONS;
			receiver = null;
			for (int i = 0; i < receivers.length; i++) {
				if((shooter.index == receivers[i].index && !isService) || (shooter.index != receivers[i].index && !receivers[i].inOffside)){
					it = receivers[i].iterationsToBall(ball);
					if (it < best && iterationsToShoot[i] <= it) {
		            	best = it;   
		            	receiver = receivers[i];
		            }
				}		            
	        }         
			return best;		
		}
		
		public int calculateRivalIterationsToBall(Position ball) {
			int it, best = ITERATIONS;
			for (int i = 0; i < rivalPlayers.length; i++) {
				it = rivalPlayers[i].iterationsToBall(ball);
				if (it < best && rivalPlayers[i].iterationsToShoot <= it) {
	            	best = it;                            
	            }			            
	        }         
			return best;		
		}
	}
	
	enum PassDataState{
		Received(0),
		Goal(1),
		Large(2),
		Undefine(3),
		Interceptable(4);
		
		public int priority;

		private PassDataState(int priority) {
			this.priority = priority;
		}
	}

	abstract class PassData{
		final BallPosition ball;
		final PassDataState state;
		double speedShoot;
		double verticalAngle;
	
		double receivedAvg;
		double goalsAvg;
		
		protected PassData(PassDataState state, BallPosition ball) {
			this.state = state;
			this.ball = ball;
		}
		
		public PassData compare(PassData other){
			if(state.priority < other.state.priority)
				return this;
			if(state.priority > other.state.priority)
				return other;
			return compareEquals(other);
		}

		protected abstract PassData compareEquals(PassData o);
		
		public MyPlayer getReceiver(){
			return ball.nearest(myPlayers);
		}
	}
	
	class GoalPassData extends PassData{
		private double goalsAvg;
		double shootOnGoalProbability;
		
		protected GoalPassData(BallPosition ball, double angle) {
			super(PassDataState.Goal, ball);
			shootOnGoalProbability = Math.min( Math.min(Math.max(angle - minAngleToGoal, 0), Math.max(maxAngleToGoal - angle, 0))/shooter.maxErrorAngle, 1);
		}

		@Override
		protected PassData compareEquals(PassData o) {
			GoalPassData other = (GoalPassData) o;
			if(goalsAvg > 0 && other.goalsAvg == 0)
				return this;
			if(goalsAvg == 0 && other.goalsAvg > 0)
				return other;
			if(shootOnGoalProbability > other.shootOnGoalProbability)
				return this;
			if(shootOnGoalProbability < other.shootOnGoalProbability)
				return other;
			if(goalsAvg > other.goalsAvg)
				return this;
			if(goalsAvg < other.goalsAvg)
				return other;
			if(ball.z < other.ball.z)
				return this;
			if(ball.z > other.ball.z)
				return other;
			if(ball.speed >= other.ball.speed)
				return this;
			return other;
		}
	}
	
	class ReceivedPassData extends PassData{
		final MyPlayer receiver;

		final int quartile;
		final double shootAngleFactor;
		final double goalAngleFactor;
		
		double fitness = -1;
		
		protected ReceivedPassData(BallPosition ball, MyPlayer receiver) {
			super(PassDataState.Received, ball);
			quartile = (int)Math.floor(Math.min(FIELD_HEIGHT, Math.max(ball.y - MY_GOAL_CENTER.y, 0))/FIELD_HEIGHT*4);
			this.receiver = receiver;
			if(quartile == 3){
				double angle = Math.abs(ball.angle(RIVAL_GOAL_LEFT_POST) - ball.angle(RIVAL_GOAL_RIGHT_POST));
				goalAngleFactor = normalize(angle, Math.PI);
				shootAngleFactor = normalize(angle, 2*receiver.maxErrorAngle);
			}else{
				goalAngleFactor = 0;
				shootAngleFactor = 0;
			}
		}

		@Override
		public MyPlayer getReceiver() {
			return receiver;
		}
		
		@Override
		protected PassData compareEquals(PassData o) {
			ReceivedPassData other = (ReceivedPassData) o;
			if((!receiver.inOffside || receiver.index == shooter.index) && (other.receiver.inOffside && other.receiver.index != shooter.index))
				return this;
			if((receiver.inOffside && receiver.index != shooter.index) && (!other.receiver.inOffside || other.receiver.index == shooter.index))
				return other;
			if(isService){
				if(receiver.index != shooter.index && (other.receiver.index == shooter.index))
					return this;
				if(receiver.index == shooter.index && (other.receiver.index != shooter.index))
					return other;
			}
			return fitness() >= other.fitness() ? this : other;
		}
		
		private double fitness() {
			if(fitness == -1){
				fitness = 0;
				fitness += sq(normalize(quartile + 1, 4));
				fitness += sq(1 - normalize(ball.distance(RIVAL_GOAL_CENTER),FIELD_HEIGHT/2));
				fitness += sq(1 - normalize(FIELD_HEIGHT/2 - ball.y, FIELD_HEIGHT));
				fitness += sq(1 - normalize(- ball.y, FIELD_HEIGHT/2));
				fitness += sq(shootAngleFactor);
				fitness += sq(goalAngleFactor);
				fitness += sq(1 - normalize(ball.speed, CONTROL_DISTANCE, MAX_SPEED_SHOOT));
				if(Pass.this.ball.quartile == 3 || shooter.isGoalKeeper || !isGoalDefended || isService ){
					fitness/= 7;
					fitness = Math.sqrt(fitness);
					fitness = receivedAvg*fitness;
				}else{
					fitness += sq(receivedAvg);
					fitness /= 8;
					fitness = Math.sqrt(fitness);
				}
			}
			return fitness;
		}
	}
	
	class LargePassData extends PassData{
		
		protected LargePassData(BallPosition ball) {
			super(PassDataState.Large, ball);
		}

		@Override
		protected PassData compareEquals(PassData o) {
			LargePassData other = (LargePassData) o;
			if(goalsAvg > other.goalsAvg)
				return this;
			if(goalsAvg < other.goalsAvg)
				return other;
			if(receivedAvg > other.receivedAvg)
				return this;
			if(receivedAvg < other.receivedAvg)
				return other;
			double distance = ball.distance(RIVAL_GOAL_CENTER);
			double otherDistance = other.ball.distance(RIVAL_GOAL_CENTER);
			if(distance <= otherDistance)
				return this;
			return other;
		}
	}
	
	class UndefinedPassData extends PassData{
		
		protected UndefinedPassData(BallPosition ball) {
			super(PassDataState.Undefine, ball);
		}

		@Override
		protected PassData compareEquals(PassData o) {
			return this;
		}
	}
	
	class InterceptablePassData extends PassData{
		
		protected InterceptablePassData(BallPosition ball) {
			super(PassDataState.Interceptable, ball);
		}

		@Override
		protected PassData compareEquals(PassData o) {
			InterceptablePassData other = (InterceptablePassData) o;
			if(goalsAvg > other.goalsAvg)
				return this;
			if(goalsAvg < other.goalsAvg)
				return other;
			if(receivedAvg > other.receivedAvg)
				return this;
			if(receivedAvg < other.receivedAvg)
				return other;
			double distance = ball.distance(RIVAL_GOAL_CENTER);
			double otherDistance = other.ball.distance(RIVAL_GOAL_CENTER);
			if(distance <= otherDistance)
				return this;
			return other;
		}
	}
}

