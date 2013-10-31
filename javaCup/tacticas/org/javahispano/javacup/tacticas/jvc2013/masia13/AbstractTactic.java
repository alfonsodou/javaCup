package org.javahispano.javacup.tacticas.jvc2013.masia13;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_WIDTH;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.GOAL_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.MY_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.ITERATIONS;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.ITERATIONS_TO_KICK;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.GameSpecifications.WAIT_KICK_ITERATIONS;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.MAX_SPEED_SHOOT;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.Ball;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.MyPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.Player;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.RivalPlayer;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;

public abstract class AbstractTactic extends Strategy implements TacticDetail{

	protected Goalkeeper goalkeeper = new Goalkeeper();
	private Shoot shoot = new Shoot();
	protected Pass pass = new Pass(shoot);
	protected Defense defense = new Defense();
	protected Unmark unmark = new Unmark();
	
	private GameSituations sp;
	
	
	private int serviceIterationsCount = WAIT_KICK_ITERATIONS - 1;
	
	private int possessionPoints = 0;
	private int totalPossessionPoints = 0;
	
	private int actualPossessionPoints = 0;
	private int totalActualPossessionPoints = 0;
	
	private Strategy[] strategies; 
			
	public AbstractTactic() {
		strategies = new Strategy[]{goalkeeper, pass, defense, unmark, shoot};
		ball = new Ball();
		threadPool = Executors.newFixedThreadPool(20);
		for (int i = 0; i < strategies.length; i++) {
			strategies[i].ball = ball; 
			strategies[i].threadPool = threadPool;
		}
	}
	
	public List<Command> execute(GameSituations sp){
		update(sp);
		pass.init();
		defense.execute();
		pass.execute();
		goalkeeper.execute();
		unmark.execute();
		if(iterations == ITERATIONS){
			threadPool.shutdown();
		}
		return commands();
	}

	public TacticDetail getDetail() {
		return this;
	}

	protected final void update(GameSituations sp){
		this.sp = sp;
		iterations = sp.iteration();
		myGoals = sp.myGoals();
		rivalGoals = sp.rivalGoals();
		
		ball.update(sp);
		
		isService = sp.isStarts();
		isRivalService = sp.isRivalStarts();
		isThrowIn = isService && Math.abs(ball.x) == FIELD_WIDTH/2 && Math.abs(ball.y) != FIELD_HEIGHT/2;
		isRivalThrowIn = isRivalService && Math.abs(ball.x) == FIELD_WIDTH/2 && Math.abs(ball.y) != FIELD_HEIGHT/2;
		
		updatePlayers();
		
		if(isService || isRivalService){
			isMyPass = false;
			isServicePass = false;
		}
		if(isServicePass){
			lastShooter.enabledToRecoveryBall = false;
		}
		
		CalculateIterationsResults<MyPlayer> calculateIterationsResults = calculateIterationsToBall(myPlayers);
		iterationToBall = calculateIterationsResults.iterationToBall;
		playerClosestToBall = calculateIterationsResults.playerClosestToBall;
		recoveryBallPosition = calculateIterationsResults.recoveryBallPosition;
		
		CalculateIterationsResults<RivalPlayer> calculateRivalIterationsResults = calculateIterationsToBall(rivalPlayers);
		rivalIterationToBall = calculateRivalIterationsResults.iterationToBall;;
		rivalClosestToBall = calculateRivalIterationsResults.playerClosestToBall;
		rivalRecoveryBallPosition = calculateRivalIterationsResults.recoveryBallPosition;
	
		isGoalKick = isService && ball.insideMyGoalArea();
		if(isService){
			serviceWaiting = serviceIterations < serviceIterationsCount;
			serviceIterations++;
		}
		else{
			serviceWaiting = false;
			serviceIterations = 0;
		}
		attacking = attacking();
		possession = calculatePossession();
		actualPossession = calculateActualPossession();
		
		if(!playerClosestToBall.canRecoveryBall && !rivalClosestToBall.canRecoveryBall && Math.abs(recoveryBallPosition.x) >= FIELD_WIDTH/2 && Math.abs(recoveryBallPosition.y) < FIELD_HEIGHT/2){
			isThrowIn = !isMyPass;
			isRivalThrowIn = isMyPass;
		}
		
		calculateOffSide();
		
		int ballIterationsToGoal = attacking? (int)Math.ceil(recoveryBallPosition.distance(MY_GOAL_CENTER)/MAX_SPEED_SHOOT) + iterationToBall + 10: (int)Math.ceil(rivalRecoveryBallPosition.distance(MY_GOAL_CENTER)/MAX_SPEED_SHOOT) + rivalIterationToBall;
		int goalkeeperIterationsToGoal = super.goalkeeper.iterationsTo(MY_GOAL_CENTER);
		isGoalDefended = goalkeeperIterationsToGoal <= ballIterationsToGoal;
		
		updateStrategies();
	}
	
	private void updateStrategies(){
		for (int i = 0; i < strategies.length; i++) {
			strategies[i].iterations = iterations;
			strategies[i].offside = offside;
			strategies[i].rivalOffside = rivalOffside;
			
			strategies[i].myGoals = myGoals;
			strategies[i].rivalGoals = rivalGoals;
			
			strategies[i].isService = isService;
			strategies[i].isRivalService = isRivalService;
			
			
			strategies[i].iterationToBall = iterationToBall;
			strategies[i].playerClosestToBall = playerClosestToBall;
			strategies[i].recoveryBallPosition = recoveryBallPosition;
			
			strategies[i].rivalIterationToBall = rivalIterationToBall;
			strategies[i].rivalClosestToBall = rivalClosestToBall;
			strategies[i].rivalRecoveryBallPosition = rivalRecoveryBallPosition;
		
			strategies[i].isGoalKick = isGoalKick;
			strategies[i].serviceWaiting = serviceWaiting;
			
			strategies[i].attacking = attacking;
			strategies[i].possession = possession;
			strategies[i].actualPossession = actualPossession;
			
			strategies[i].isMyPass = isMyPass;
			strategies[i].shooter = shooter;
			strategies[i].canShootPlayers = canShootPlayers;
			strategies[i].isEnabledToShoot = isEnabledToShoot;
			
			strategies[i].isGoalDefended = isGoalDefended;
			
			strategies[i].serviceIterations = serviceIterations;
		}
	}
	
	private void updatePlayers() {
		if(iterations == 0){
			PlayerDetail[] playerDetails = sp.myPlayersDetail();
			for (int i = 0; i < playerDetails.length; i++) {
				myPlayers[i] = new MyPlayer(i, playerDetails[i]);
				if(myPlayers[i].isGoalKeeper)
					super.goalkeeper = myPlayers[i];
			}
			PlayerDetail[] rivalPlayerDetails = sp.rivalPlayersDetail();
			for (int i = 0; i < rivalPlayerDetails.length; i++) {
				rivalPlayers[i] = new RivalPlayer(i, rivalPlayerDetails[i]);
			}
			for (int i = 0; i < strategies.length; i++) {
				strategies[i].myPlayers = myPlayers;
				strategies[i].rivalPlayers = rivalPlayers;
				strategies[i].goalkeeper = super.goalkeeper;
			}
		}
		
		boolean updateOffsidePlayers = false;
		int[] iterationsToKick = sp.iterationsToKick();
		for (int i = 0; i < myPlayers.length; i++) {
			myPlayers[i].update(sp.myPlayers()[i].getX(), sp.myPlayers()[i].getY(), sp.getMyPlayerEnergy(i), sp.getMyPlayerAceleration(i));
			myPlayers[i].iterationsToShoot = iterationsToKick[i];
			if(myPlayers[i].iterationsToShoot == ITERATIONS_TO_KICK){
				isMyPass = true;
				lastShooter = myPlayers[i];
				isServicePass = serviceIterations > 0;
				updateOffsidePlayers = true;
			}
			myPlayers[i].ballAngle = ball.angle(myPlayers[i]);
		}
		
		iterationsToKick = sp.rivalIterationsToKick();
		for (int i = 0; i < rivalPlayers.length; i++) {
			rivalPlayers[i].update(sp.rivalPlayers()[i].getX(), sp.rivalPlayers()[i].getY(), sp.getRivalEnergy(i), sp.getRivalAceleration(i)); 
			rivalPlayers[i].iterationsToShoot = iterationsToKick[i];
			if(rivalPlayers[i].iterationsToShoot == ITERATIONS_TO_KICK){
				isMyPass = false;
				isServicePass = false;
				updateOffsidePlayers = true;
			}
		}

		boolean[] offSidePlayers = sp.getOffSidePlayers();
		for (int i = 0; i < 11; i++) {
			if(updateOffsidePlayers) 
				myPlayers[i].enabledToRecoveryBall = !isMyPass || !myPlayers[i].inOffside || lastShooter.index == myPlayers[i].index; 
			myPlayers[i].inOffside = offSidePlayers[i] && !isThrowIn;
		}
		
		int[] canKick = sp.canKick();
		shooter = null;
		canShootPlayers = new MyPlayer[canKick.length];
		for (int i = 0; i < canKick.length; i++) {
			canShootPlayers[i] = myPlayers[canKick[i]];
			canShootPlayers[i].canShoot = true;
			if(shooter == null || canShootPlayers[i].power < shooter.power){
				shooter = canShootPlayers[i];
			}
		}
		isEnabledToShoot = shooter != null;
	}
	
    private void calculateOffSide(){
    	offside = ball.y;
    	rivalOffside = offside;
        double lastOffside = offside;
        double lastRivalOffside = offside;
        for (int i = 0; i < 11; i++) {
        	if (rivalPlayers[i].y > lastOffside) {
        		offside = lastOffside;
                lastOffside = rivalPlayers[i].y;
            } else {
                if (rivalPlayers[i].y > offside) {
                    offside = rivalPlayers[i].y;
                }
            }
        	if (myPlayers[i].y < lastRivalOffside) {
        		rivalOffside = lastRivalOffside;
                lastRivalOffside = myPlayers[i].y;
            } else {
                if (myPlayers[i].y < rivalOffside) {
                    rivalOffside = myPlayers[i].y;
                }
            }
        }
        if(isThrowIn)
        	offside = FIELD_HEIGHT/2;
        else
        	offside = Math.max(0, offside);
        if(isRivalThrowIn)
        	rivalOffside = -FIELD_HEIGHT/2;
        else
        	rivalOffside = Math.min(0, rivalOffside);
    }
    
    private <T extends Player> CalculateIterationsResults<T> calculateIterationsToBall(T[] players) {
    	int iteration = 0;
		CalculateIterationsResults<T> result = null;
		boolean stop = false;
        while (iteration < ball.trayectory.length && !stop) {
            Position ballPosition = ball.trayectory[iteration];
            if(ballPosition.z <= GOAL_HEIGHT && (ballPosition.isInsideGameField() || ball.isTowardsGoal)){
            	stop = true;
            	for (int i = 0; i < players.length; i++) {
            		if(players[i].enabledToRecoveryBall && !players[i].canRecoveryBall){
            			if(players[i].canRecoveryBall(ballPosition, iteration)){
            				if(result == null)
    							result = new CalculateIterationsResults<T>(iteration, players[i], ballPosition);
    						players[i].canRecoveryBall = true;
    						players[i].iterationToBall = iteration;
    						players[i].recoveryBallPosition = ballPosition;
            			}else
            				stop = false;
            		}
                }
            }
            if (!ballPosition.isInsideGameField()) {
            	for (T player : players) {
					if(!player.canRecoveryBall){
						player.iterationToBall = ball.trayectory.length;
						player.recoveryBallPosition = ball.trayectory[iteration];
					}
				}
            	stop = true;
            	if(result == null)
            		result = new CalculateIterationsResults<T>(ball.trayectory.length, ballPosition.nearest(players), ball.trayectory[iteration]);
            }
            iteration++;
        }
        if(result == null)
        	result = new CalculateIterationsResults<T>(ball.trayectory.length -1, ball.trayectory[ball.trayectory.length - 1].nearest(players), ball.trayectory[ball.trayectory.length - 1]);
        if(ball.trayectory[ball.trayectory.length - 1].isInsideGameField()){
        	for (T player : players) {
            	if(!player.canRecoveryBall && player.enabledToRecoveryBall){
            		player.canRecoveryBall = true;
    				player.iterationToBall = ball.trayectory.length - 1;
    				player.recoveryBallPosition = ball.trayectory[ball.trayectory.length - 1];
    			}
    		}
        }
        return result;
	}
    
    private double calculateActualPossession() {
		if(isService || iterationToBall <= rivalIterationToBall){
			if(ball.y >= 0){
				actualPossessionPoints += 2;
				totalActualPossessionPoints += 2;
			}else{
				actualPossessionPoints++;
				totalActualPossessionPoints++;
			}
		}
		if(isRivalService || iterationToBall >= rivalIterationToBall){
			if(ball.y > 0)
				totalActualPossessionPoints++;
			else
				totalActualPossessionPoints += 2;
		}
		return ((double)actualPossessionPoints)/totalActualPossessionPoints;
	}

	private boolean attacking() {
		return 	isService || 
				(!isRivalService && 
										(playerClosestToBall.canRecoveryBall && (!rivalClosestToBall.canRecoveryBall || iterationToBall < rivalIterationToBall)) 
										|| (!playerClosestToBall.canRecoveryBall && !rivalClosestToBall.canRecoveryBall && !isMyPass));
	}
	
	private double calculatePossession() {
		if (!isService && !isRivalService) {
            if (ball.y > 0) {
                if (ball.y > FIELD_HEIGHT/4) {
                    possessionPoints += 2;
                    totalPossessionPoints += 2;
                } else {
                	possessionPoints += 1;
                    totalPossessionPoints += 1;
                }
            } else if (ball.y < 0) {
                if (ball.y < - FIELD_HEIGHT/4) {
                    totalPossessionPoints += 2;
                } else {
                	totalPossessionPoints += 1;
                }
            }
        }
		if(totalPossessionPoints == 0)
			return .5;
		return possessionPoints/(double)totalPossessionPoints;
	}
	
	private List<Command> commands() {
		List<Command> commands = new LinkedList<Command>();
		for (int i = 0; i < myPlayers.length; i++) {
			if(myPlayers[i].getCommandMoveTo() != null){
				commands.add(myPlayers[i].getCommandMoveTo());
			}
			if(myPlayers[i].getCommandHitBall() != null){
				commands.add(myPlayers[i].getCommandHitBall());
			}
		}
		return commands;
	}
	
    private class CalculateIterationsResults<T extends Player>{
		
		public int iterationToBall;
		public T playerClosestToBall;
		public Position recoveryBallPosition;
		
		private CalculateIterationsResults(int iterationToBall, T playerClosestToBall, Position recoveryBallPosition) {
			this.iterationToBall = iterationToBall;
			this.playerClosestToBall = playerClosestToBall;
			this.recoveryBallPosition = recoveryBallPosition;
		}
	} 
}
