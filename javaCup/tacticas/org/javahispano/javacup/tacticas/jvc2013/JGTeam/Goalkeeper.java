package org.javahispano.javacup.tacticas.jvc2013.JGTeam;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Goalkeeper {

	private static double GOAL_DISTANCE_CONSTANT = (Constants.ANCHO_CAMPO_JUEGO / Constants.LARGO_ARCO) / 2;

	public void update(LinkedList<Command> commands, GameStatus gameStatus) {

		if (gameStatus.goalkeeperCanKick == true) {
			commands.add(new CommandHitBall(gameStatus.nearestPlayerToBall,
					gameStatus.kickDirection, 1, gameStatus.force));
		}

		if (gameStatus.isGoalKick == true) {
			commands.add(new CommandMoveTo(0, gameStatus.sp.ballPosition()));
		} else {
			if (gameStatus.isBallInMyArea && gameStatus.sp.ballAltitude() > 3) {
				if (gameStatus.ballTrayectoryPosition.getY() < Constants.posteIzqArcoInf
						.getY())
					commands.add(new CommandMoveTo(0, new Position(
							gameStatus.sp.ballPosition().getX(),
							Constants.posteIzqArcoInf.getY() + 1)));
				else
					commands.add(new CommandMoveTo(0,
							gameStatus.ballTrayectoryPosition));

			} else if (gameStatus.isBallInMyArea
					&& gameStatus.sp.ballAltitude() <= 3) {
				double xPosition;
				if (Math.abs(gameStatus.sp.ballPosition().getX()) > (Constants.LARGO_ARCO / 2)) {
					if (gameStatus.sp.ballPosition().getX() > 0)
						xPosition = Constants.posteDerArcoInf.getX();
					else
						xPosition = Constants.posteIzqArcoInf.getX();

				} else {
					xPosition = gameStatus.sp.ballPosition().getX();
				}
				commands.add(new CommandMoveTo(0, new Position(xPosition,
						Constants.posteIzqArcoInf.getY() + 1)));
			} else if (gameStatus.ballVerticalPosition != 4) {
				commands.add(new CommandMoveTo(0, new Position(gameStatus.sp
						.ballPosition().getX()
						/ Goalkeeper.GOAL_DISTANCE_CONSTANT,
						Constants.posteIzqArcoInf.getY() + 1)));
			} else {
				commands.add(new CommandMoveTo(0, new Position(0,
						Constants.posteIzqArcoInf.getY() + 1)));
			}
		}

	}
}
