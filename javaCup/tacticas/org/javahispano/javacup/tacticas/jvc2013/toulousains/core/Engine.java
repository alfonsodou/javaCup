package org.javahispano.javacup.tacticas.jvc2013.toulousains.core;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.core.strategy.Strategy;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.core.strategy.StrategyOffensive;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.model.Ball;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.model.Match;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.model.Player;
import org.javahispano.javacup.tacticas.jvc2013.toulousains.util.ToulousaineUtils;

public class Engine {

	private final List<Command> commands = new LinkedList<Command>();

	private int goalKeeperFails = 0;

	private final Strategy strategy = new StrategyOffensive();

	private Match match = null;

	public Engine(Match match) {
		this.match = match;
	}

	public int getGoalKeeperFails() {
		return goalKeeperFails;
	}

	public void updateStrategy() {
		commands.clear();

		this.calculateStrategy();

		this.initializePositions();

		this.manageGoalKeeper();

		this.managePlayers();
	}

	public List<Command> getStrategyCommands() {
		return checkCommands(commands);
	}

	private void calculateStrategy() {
		// Next year...
	}

	private void initializePositions() {
		if (isRivalAttacking()) {
			moveToLineUp(strategy.getLineUpDefending());
			defend();
		} else {
			moveToLineUp(strategy.getLineUpAttacking());
		}
	}

	private void manageGoalKeeper() {

		Player goalKeeper = match.getToulosains().getGoalKeeper();
		Ball ball = match.getBall();

		// If we can, we kick
		if (goalKeeper.cankick() || playerCanKick(goalKeeper)) {
			ToulousaineUtils.log("GOALKEEPER KICK!!!");
			commands.add(new CommandHitBall(goalKeeper.getIndex(), Constants.centroArcoSup, 1.0, Constants.ANGULO_VERTICAL));
		} else if (isPlayerFail(goalKeeper)) {
			ToulousaineUtils.log("GOALKEEPER FAILS!!!" + " Total fails: " + ++this.goalKeeperFails);
		}

		double distanceRight = ball.getPosition().distance(Constants.posteDerArcoInf);
		double distanceLeft = ball.getPosition().distance(Constants.posteIzqArcoInf);
		double ballAngle = Math
				.acos(((Constants.LARGO_ARCO * Constants.LARGO_ARCO) - (distanceRight * distanceRight) - (distanceLeft * distanceLeft))
						/ (-2 * distanceRight * distanceLeft));
		double rightAngle = Math
				.acos(((distanceLeft * distanceLeft) - (Constants.LARGO_ARCO * Constants.LARGO_ARCO) - (distanceRight * distanceRight))
						/ (-2 * Constants.LARGO_ARCO * distanceRight));
		double finalAngle = Math.PI - (ballAngle / 2) - rightAngle;
		double dPorterRight = (distanceRight * Math.sin(ballAngle / 2)) / Math.sin(finalAngle);

		if (isRivalShootToGoal()) {
			ToulousaineUtils.log("SAVING GOAL GOALKEEPER!!!");
			commands.add(new CommandMoveTo(goalKeeper.getIndex(), new Position((Constants.posteDerArcoInf.getX() - dPorterRight),
					Constants.centroArcoInf.getY()), true));
		} else if ((isRivalInArea()) || getClosestPlayer().equals(goalKeeper)) {
			ToulousaineUtils.log("CHASING BALL GOALKEEPER!!!");
			commands.add(new CommandMoveTo(goalKeeper.getIndex(), ball.getPosition(), true));
		} else if (isRivalAttacking()) {
			ToulousaineUtils.log("CALCULATING GOALKEEPER*!!!");
			commands.add(new CommandMoveTo(goalKeeper.getIndex(), calculateGoalKeeperPosition(), true));
		}
	}

	private void managePlayers() {
		Player closestPlayer = getClosestPlayer();
		Ball ball = match.getBall();
		Player playerToPass = getMateToPass(closestPlayer);

		if (match.getToulosains().isKickOff()) {
			commands.add(new CommandMoveTo(closestPlayer.getIndex(), ball.getPosition(), true));
			if (closestPlayer.cankick()) {
				ToulousaineUtils.log("KICKOFF!!!");
				if (ball.getPosition().getY() > 35) {
					commands.add(new CommandHitBall(closestPlayer.getIndex(), Constants.centroArcoSup, 1.0, 10.0));
				} else {
					commands.add(new CommandHitBall(closestPlayer.getIndex(), Constants.centroArcoSup, 1.0,
							Constants.ANGULO_VERTICAL));
				}
			}
		} else {
			// Defender el balon
			if (rivalCanKick()) {
				List<Player> players = match.getToulosains().getPlayers();
				for (final Player player : players) {
					commands.add(new CommandMoveTo(player.getIndex(), ball.getPosition()));
				}
			}

			List<Command> commandsRecovery = this.recoveryBall();
			for (final Command command : commandsRecovery) {
				commands.add(command);
			}

			if (closestPlayer.cankick()) {
				if (canContinue(closestPlayer)) {
					ToulousaineUtils.log("CONTINUE!!!");
					commands.add(new CommandHitBall(closestPlayer.getIndex()));
					commands.add(new CommandMoveTo(closestPlayer.getIndex(), Constants.centroArcoSup, true));

				} else if (playerToPass != null && ball.getPosition().getY() > 0) {
					ToulousaineUtils.log("PASS!!!");
					commands.add(new CommandHitBall(closestPlayer.getIndex(), playerToPass.getPosition(), calculatePassPower(
							closestPlayer, playerToPass), false));
				} else {
					if (closestPlayer.getPosition().getY() < -0) {
						ToulousaineUtils.log("SHOOT 45!!!");
						commands.add(new CommandHitBall(closestPlayer.getIndex(), Constants.centroArcoSup, 1.0, 45.0));
					} else if (closestPlayer.getPosition().getY() <= 10) {
						ToulousaineUtils.log("SHOOT 25!!!");
						commands.add(new CommandHitBall(closestPlayer.getIndex(), Constants.centroArcoSup, 1.0, 25.0));
					} else if (closestPlayer.getPosition().getY() <= 20) {
						ToulousaineUtils.log("SHOOT 20!!!");
						commands.add(new CommandHitBall(closestPlayer.getIndex(), Constants.centroArcoSup, 1.0, 20.0));
					} else if (closestPlayer.getPosition().getY() <= 30) {
						ToulousaineUtils.log("SHOOT 15!!!");
						commands.add(new CommandHitBall(closestPlayer.getIndex(), Constants.centroArcoSup, 1.0, 15.0));
					} else if (closestPlayer.getPosition().getY() < 37) {
						ToulousaineUtils.log("SHOOT 10!!!");
						commands.add(new CommandHitBall(closestPlayer.getIndex(), Constants.centroArcoSup, 1.0, 10.0));
					} else if (closestPlayer.getPosition().getX() < 0) {
						ToulousaineUtils.log("SHOOT RIGHT!!!");
						commands.add(new CommandHitBall(closestPlayer.getIndex(), new Position(
								Constants.posteIzqArcoSup.getX() + 2.0, Constants.posteIzqArcoSup.getY()), 1.0, 10.0));
					} else {
						ToulousaineUtils.log("SHOOT LEFT!!!");
						commands.add(new CommandHitBall(closestPlayer.getIndex(), new Position(
								Constants.posteDerArcoSup.getX() - 2.0, Constants.posteDerArcoSup.getY()), 1.0, 10.0));
					}
				}
			}
		}
	}

	private Position calculateGoalKeeperPosition() {
		Ball ball = match.getBall();
		Position ballPosition = ball.getPosition();
		double goalKeeperAdvantage = -50.0;

		return calculatePointInLineByY(Constants.centroArcoInf, ballPosition, goalKeeperAdvantage);
	}

	private Position calculatePointInLineByY(Position point1, Position point2, double posY) {
		double x1 = point1.getX();
		double x2 = point2.getX();

		double diffX = x1 - x2;

		double y1 = point1.getY();
		double y2 = point2.getY();

		double diffY = y1 - y2;

		double baseY = posY - y1;

		double valueX = ((diffX * baseY) / diffY) + x1;

		return new Position(valueX, posY);
	}

	private boolean isPlayerFail(Player player) {
		double controlDist = (player.isGoalKeeper()) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO
				: Constants.DISTANCIA_CONTROL_BALON;
		double maxAltitude = (player.isGoalKeeper()) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;

		return (player.getDistanceToBall() <= controlDist && match.getBall().getAltitude() <= maxAltitude
				&& !match.getToulosains().isKickOff() && player.cankick() == false);
	}

	private boolean playerCanKick(Player player) {
		double controlDist = (player.isGoalKeeper()) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO
				: Constants.DISTANCIA_CONTROL_BALON;
		double maxAltitude = (player.isGoalKeeper()) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;

		if (isPlayerFail(player)) {
			Ball ball = match.getBall();
			double altitude = ball.getAltitude();
			double positionX = ball.getPosition().getX();
			double positionY = ball.getPosition().getY();

			ToulousaineUtils.log("FAILS TO KICK: " + ++goalKeeperFails);
			ToulousaineUtils.log(" Ball Data: Altitude: " + altitude + " positionX: " + positionX + " positionY: " + positionY);
			ToulousaineUtils.log(" Player Data: " + " positionX: " + player.getPosition().getX() + " positionY: "
					+ player.getPosition().getY());
		}

		return (player.getDistanceToBall() <= controlDist && match.getBall().getAltitude() <= maxAltitude);
	}

	private boolean isRivalShootToGoal() {
		Ball ball = match.getBall();
		Position ballPos = ball.getPosition();
		double posX = ball.getTrajectory()[0];
		double posY = ball.getTrajectory()[1];

		Position positionInGoal = calculatePointInLineByY(ballPos, new Position(posX, posY), Constants.centroArcoInf.getY());

		return posY < Constants.centroArcoInf.getY() && positionInGoal.getX() > Constants.posteIzqArcoInf.getX()
				&& positionInGoal.getX() < Constants.posteDerArcoInf.getX();
	}

	private boolean isRivalInArea() {
		double beginAreaY = Constants.centroArcoInf.getY() + Constants.ANCHO_AREA_GRANDE;
		double leftAreaX = 20d;
		double rightAreaX = -20d;

		Position ballPosition = match.getBall().getPosition();

		if (ballPosition.getY() < beginAreaY && (ballPosition.getX() < leftAreaX && ballPosition.getX() > rightAreaX)) {
			return true;
		}

		return false;
	}

	private boolean rivalCanKick() {
		List<Player> rivalPlayers = match.getAdversary().getPlayers();
		for (final Player rivalPlayer : rivalPlayers) {
			if (rivalPlayer.cankick()) {
				return true;
			}
		}
		return false;
	}

	private boolean isRivalAttacking() {
		Position ballPosition = match.getBall().getPosition();
		double[] trajectory = match.getBall().getTrajectory();

		if (ballPosition.getY() > trajectory[1]) {
			return true;
		}
		return false;
	}

	private Player getClosestPlayer(int... exceptions) {
		Position ballPosition = match.getBall().getPosition();

		List<Position> playerPositions = match.getToulosains().getPlayerPositions();

		int playerIndexToMove = ballPosition.nearestIndex(playerPositions.toArray(new Position[playerPositions.size()]), 0);

		return match.getToulosains().getPlayers().get(playerIndexToMove);
	}

	private List<Command> checkCommands(List<Command> commands) {
		List<Integer> playersWithCommand = new ArrayList<Integer>();
		for (final Command command : commands) {
			int playerIndex = command.getPlayerIndex();
			if (!playersWithCommand.contains(Integer.valueOf(playerIndex))) {
				playersWithCommand.add(Integer.valueOf(playerIndex));
			} else {
				// ToulosaineUtils.log("Multiple commands for player with index: "
				// + playerIndex);
			}
		}

		return commands;
	}

	private List<Integer> getPlayersWithMoveCommand(List<Command> commands) {
		List<Integer> playersWithMoveCommand = new ArrayList<Integer>();
		for (final Command command : commands) {
			int playerIndex = command.getPlayerIndex();
			playersWithMoveCommand.add(playerIndex);
		}
		return playersWithMoveCommand;
	}

	private void moveToLineUp(Position[] playerPositions) {
		List<Integer> playersWithMoveCommand = getPlayersWithMoveCommand(this.commands);
		int index = 0;
		for (final Position playerPosition : playerPositions) {
			if (!playersWithMoveCommand.contains(Integer.valueOf(index))) {
				this.commands.add(new CommandMoveTo(index, playerPosition, false));
			}
			index++;
		}
	}

	private void defend() {
		List<Integer> playersWithMoveCommand = getPlayersWithMoveCommand(this.commands);
		List<Player> players = match.getToulosains().getPlayers();

		for (final Player player : players) {
			if (!playersWithMoveCommand.contains(player.getIndex())) {
				Player closestRival = getClosestRival(player.getPosition());
				if (closestRival.getPosition().getY() < -0.0 && closestRival.isMark() == false) {
					closestRival.setMark(true);
					player.setMark(true);
					this.commands.add(new CommandMoveTo(player.getIndex(), closestRival.getPosition(), true));
				}
			}
		}
	}

	private Player getClosestRival(Position position) {
		List<Player> rivalPlayers = match.getAdversary().getPlayers();
		Player closestRivalPlayer = null;

		for (final Player rivalPlayer : rivalPlayers) {
			if (closestRivalPlayer == null) {
				closestRivalPlayer = rivalPlayer;
				continue;
			}

			if (position.distance(rivalPlayer.getPosition()) < position.distance(closestRivalPlayer.getPosition())) {
				closestRivalPlayer = rivalPlayer;
			}
		}

		return closestRivalPlayer;
	}

	private Player getMateToPass(Player player) {
		List<Player> players = match.getToulosains().getPlayers();
		Player bestMatePlayer = null;

		for (final Player matePlayer : players) {
			Position matePos = matePlayer.getPosition();
			Position playerPos = player.getPosition();

			if (matePlayer.isGoalKeeper() || (matePlayer.getIndex() == player.getIndex())) {
				continue;
			}

			if (matePlayer.getPosition().getY() < player.getPosition().getY()) {
				continue;
			}

			if (matePlayer.isOffside()) {
				continue;
			}

			List<Player> rivals = match.getAdversary().getPlayers();
			boolean rivalTooClose = false;
			for (final Player rivalPlayer : rivals) {
				double distanceToTrajectory = Line2D.ptSegDist(playerPos.getX(), playerPos.getY(), matePos.getX(),
						matePos.getY(), rivalPlayer.getPosition().getX(), rivalPlayer.getPosition().getY());

				if (distanceToTrajectory < 5.0) {
					rivalTooClose = true;
					break;
				}
			}

			if (!rivalTooClose) {
				bestMatePlayer = matePlayer;
				break;
			}
		}

		return bestMatePlayer;
	}

	private boolean canContinue(Player player) {
		List<Player> rivals = match.getAdversary().getPlayers();
		boolean continueRunning = true;

		for (final Player rivalPlayer : rivals) {
			double distance = player.getPosition().distance(rivalPlayer.getPosition());

			if (rivalPlayer.getPosition().getY() > player.getPosition().getY()
					&& distance < Constants.DISTANCIA_CONTROL_BALON * 20) {
				ToulousaineUtils.log("Rival with number " + rivalPlayer.getNumber() + " too close to continue");
				continueRunning = false;
				break;
			}
		}

		return continueRunning;
	}

	private double calculatePassPower(Player player, Player playerToPass) {
		double distance = player.getPosition().distance(playerToPass.getPosition());
		return distance * .035;
	}

	private List<Command> recoveryBall() {
		LinkedList<Command> commands = new LinkedList<Command>();
		Ball ball = match.getBall();
		int[] recoveryBall = ball.getRecovery();
		if (recoveryBall.length > 1) {
			double[] posRecovery = ball.getTrajectory(recoveryBall[0]);
			for (int i = 1; i < recoveryBall.length; i++) {
				Player player = match.getToulosains().getPlayerByIndex(recoveryBall[i]);
				if (!player.isOffside()) {
					commands.add(new CommandMoveTo(recoveryBall[i], new Position(posRecovery[0], posRecovery[1]), true));
				} else {
					commands.add(new CommandMoveTo(recoveryBall[i], Constants.centroArcoInf, true));
				}

			}
		}
		return commands;
	}
}
