package org.javahispano.javacup.tacticas.jvc2013.masia13;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.GOAL_WIDTH;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.MY_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.PlayerSpecifications.MAX_SPEED_SHOOT;

import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;

public class Goalkeeper extends Strategy{
	
	public void execute(){
		Position position = calculateMove();
		goalkeeper.moveTo(position);
	}
	
	private Position calculateMove() {
		if(isGoalKick)
			return recoveryBallPosition;
		if(attacking && playerClosestToBall.isGoalKeeper && goalkeeper.canRecoveryBall && !isService){
			if(!ball.isTowardsGoal)
				return recoveryBallPosition;
			Position best = recoveryBallPosition;
			int i = iterationToBall + 1;
			while( i < ball.trayectory.length && i < rivalIterationToBall && !ball.isRivalGoal(i)){
				if(ball.trayectory[i].isInsideGameField()){
					if (goalkeeper.iterationsToBall(ball.trayectory[i]) <= i)
		            	best = ball.trayectory[i];
					i++;
				}else break;
			}
			return best;	
		}
		double ratio = Math.max(Math.ceil(rivalRecoveryBallPosition.distance(MY_GOAL_CENTER)/MAX_SPEED_SHOOT)*0.45, GOAL_WIDTH/2);
		return MY_GOAL_CENTER.moveAngle(MY_GOAL_CENTER.angle(rivalRecoveryBallPosition), ratio, MY_GOAL_CENTER.distance(rivalRecoveryBallPosition));
	}
}
