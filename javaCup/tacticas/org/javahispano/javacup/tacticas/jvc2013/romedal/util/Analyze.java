package org.javahispano.javacup.tacticas.jvc2013.romedal.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Analyze {
	private GameSituations sp;

	private final LinkedList<Ball> realTrayectory = new LinkedList<Ball>();
	private final TreeMap<Double, TreeSet<ShootingTrajectory>> passOptions = new TreeMap<Double, TreeSet<ShootingTrajectory>>();

	private int idxRivalGoalkeeper = -1;

	PreviousIteration previous;

	double myPosession = 0;
	double rivalPosession = 0;
	double totalPosession = 0;

	boolean[] myOffSide = new boolean[] { false, false, false, false, false, false, false, false, false, false, false };
	boolean[] rivalOffSide = new boolean[] { false, false, false, false, false, false, false, false, false, false, false };

	private TreeMap<Double, MyAceleration> acelerationData = new TreeMap<Double, MyAceleration>();

	private void init() {
		acelerationData.clear();
		acelerationData.put(0.63, new MyAceleration(0.9, 0.7));
		acelerationData.put(0.666, new MyAceleration(0.9, 0.74));
		acelerationData.put(0.702, new MyAceleration(0.9, 0.78));
		acelerationData.put(0.738, new MyAceleration(0.9, 0.82));
		acelerationData.put(0.774, new MyAceleration(0.9, 0.86));
		acelerationData.put(0.81, new MyAceleration(0.9, 0.9));
		acelerationData.put(0.658, new MyAceleration(0.94, 0.7));
		acelerationData.put(0.6956, new MyAceleration(0.94, 0.74));
		acelerationData.put(0.7332, new MyAceleration(0.94, 0.78));
		acelerationData.put(0.7708, new MyAceleration(0.94, 0.82));
		acelerationData.put(0.8084, new MyAceleration(0.94, 0.86));
		acelerationData.put(0.846, new MyAceleration(0.94, 0.9));
		acelerationData.put(0.8836, new MyAceleration(0.94, 0.94));
		acelerationData.put(0.686, new MyAceleration(0.98, 0.7));
		acelerationData.put(0.7252, new MyAceleration(0.98, 0.74));
		acelerationData.put(0.7644, new MyAceleration(0.98, 0.78));
		acelerationData.put(0.8036, new MyAceleration(0.98, 0.82));
		acelerationData.put(0.8428, new MyAceleration(0.98, 0.86));
		acelerationData.put(0.882, new MyAceleration(0.98, 0.9));
		acelerationData.put(0.9212, new MyAceleration(0.98, 0.94));
		acelerationData.put(0.9604, new MyAceleration(0.98, 0.98));
		acelerationData.put(0.7, new MyAceleration(1, 0.7));
		acelerationData.put(0.74, new MyAceleration(1, 0.74));
		acelerationData.put(0.78, new MyAceleration(1, 0.78));
		acelerationData.put(0.82, new MyAceleration(1, 0.82));
		acelerationData.put(0.86, new MyAceleration(1, 0.86));
		acelerationData.put(0.9, new MyAceleration(1, 0.9));
		acelerationData.put(0.94, new MyAceleration(1, 0.94));
		acelerationData.put(0.98, new MyAceleration(1, 0.98));
		acelerationData.put(1d, new MyAceleration(1, 1));
	}

	public class MyAceleration {
		public double x = 0d;
		public double y = 0d;

		public MyAceleration(double x, double y) {
			this.x = x;
			this.y = y;
		}

	}

	public class Direction {
		public double x = 0;
		public double y = 0;

		public Direction(double x, double y) {
			this.x = x;
			this.y = y;
		}

	}

	public class PreviousIteration {
		public Position[] myPlayers;
		public Position[] rivalPlayers;

		public double[] myEnergy;
		public double[] rivalsEnergy;
		public Ball ball;
		public CommandMoveTo[] commands;

		boolean sacaba;
		boolean sacaRival;

		public PreviousIteration() {
			myEnergy = new double[] { 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d };
			rivalsEnergy = new double[] { 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d };
			myPlayers = new Position[11];
			rivalPlayers = new Position[11];
			commands = new CommandMoveTo[] { null, null, null, null, null, null, null, null, null, null, null };
			ball = null;
		}
	}

	double maxIter = 0;
	double cantPass = 0;

	public Analyze(GameSituations sp) {
		this.sp = sp;
		setRivalGoalkeeper();
		setShootingOptions();
		previous = new PreviousIteration();
		init();
	}

	public void update(GameSituations sp) {
		this.sp = sp;
		setRealTrayectory();

		setOffSide();

		updatePosession();
	}

	private void updatePosession() {
		if (!sp.isStarts() && !sp.isRivalStarts()) {
			double py = sp.ballPosition().getY();
			if (py > 0) {
				if (py > RMConstants.MEDIO) {
					myPosession += 2;
					totalPosession += 2;
				} else {
					myPosession += 1;
					totalPosession += 1;
				}
			} else if (py < 0) {
				if (py < -RMConstants.MEDIO) {
					rivalPosession += 2;
					totalPosession += 2;
				} else {
					rivalPosession += 1;
					totalPosession += 1;
				}
			}
		}
	}

	private void setOffSide() {
		boolean saco = sp.isStarts();
		myOffSide = sp.getOffSidePlayers();
		for (int i = 0; i < 11; i++) {
			myOffSide[i] = myOffSide[i] && !saco && sp.myPlayers()[i].isInsideGameField(0d);
		}
		if (previous != null) {
			for (int i = 0; i < 11; i++) {
				if (sp.rivalIterationsToKick()[i] == 10) {
					calculateRivalOffSidePlayers(i);
					break;
				}
			}
		}
	}

	public boolean isOffSide(int idx, boolean rival) {
		return rival ? rivalOffSide[idx] : myOffSide[idx];
	}

	public boolean winning() {
		int difGolas = sp.myGoals() - sp.rivalGoals();
		return difGolas > 1 || (difGolas == 0 && getMyPosession() > 0.55) || sp.iteration() < 1800;
	}

	public double getMyPosession() {
		if (totalPosession == 0)
			return .5;
		double d = (double) myPosession / (double) totalPosession;
		return d;
	}

	void calculateRivalOffSidePlayers(int idx) {
		boolean actual = false;
		for (int idxKick : sp.rivalCanKick()) {
			if (idx == idxKick) {
				actual = true;
				break;
			}
		}

		Position[] positionRivals = actual ? sp.rivalPlayers() : previous.rivalPlayers;
		Position[] positionMyPlayers = actual ? sp.myPlayers() : previous.myPlayers;
		Position ball = actual ? sp.ballPosition() : previous.ball.getPosition();
		boolean esSaque = actual ? sp.isRivalStarts() : previous.sacaRival;

		Position posOffSide = ball;
		Position lastPosOffSide = ball;

		for (int x = 0; x < 11; ++x) {
			Position myPlayer = positionMyPlayers[x];
			if (lastPosOffSide.getY() > myPlayer.getY()) {
				posOffSide = lastPosOffSide;
				lastPosOffSide = myPlayer;
			} else {
				if (posOffSide.getY() > myPlayer.getY()) {
					posOffSide = myPlayer;
				}
			}
		}

		if (posOffSide == null) {
			posOffSide = ball;
		}

		if (posOffSide.getY() > 0)
			posOffSide = new Position(0, 0);

		for (int x = 0; x < 11; ++x) {
			rivalOffSide[x] = x != idx && (positionRivals[x].getY() < posOffSide.getY() && !esSaque && positionRivals[x].isInsideGameField(0d));
		}
	}

	public void finallyIteration(ArrayList<Command> comandos) {
		if (sp.iteration() > 0) {
			previous.rivalPlayers = sp.rivalPlayers();
			for (int i = 0; i < 11; i++) {
				previous.rivalsEnergy[i] = sp.getRivalEnergy(i);
				previous.myEnergy[i] = sp.getMyPlayerEnergy(i);
				previous.myPlayers[i] = sp.myPlayers()[i];
				previous.rivalPlayers[i] = sp.rivalPlayers()[i];
				previous.sacaba = sp.isStarts();
				previous.sacaRival = sp.isRivalStarts();
				for (Command command : comandos) {
					if (command instanceof CommandMoveTo && command.getPlayerIndex() == i) {
						CommandMoveTo cmd = (CommandMoveTo) command;
						previous.commands[i] = new CommandMoveTo(cmd.getPlayerIndex(), cmd.getMoveTo());
						break;
					}
				}
			}
		}
		if (realTrayectory.isEmpty()) {
			realTrayectory.add(new Ball(sp.ballPosition(), sp.ballAltitude()));
		}
		previous.ball = realTrayectory.get(0);
	}

	public Ball getPreviousBall() {
		return previous.ball;
	}

	public PreviousIteration getPrevious() {
		return previous;
	}

	public boolean iHaveBall() {
		return sp.isStarts() || (sp.canKick().length > 0 && !sp.isRivalStarts());
	}

	public boolean heHaveBall() {
		return sp.isRivalStarts() || (!sp.isStarts() && sp.rivalCanKick().length > 0);
	}

	private void setRivalGoalkeeper() {
		final PlayerDetail[] rivals = sp.rivalPlayersDetail();
		for (int i = 0; i < rivals.length; i++) {
			if (rivals[i].isGoalKeeper()) {
				idxRivalGoalkeeper = i;
				break;
			}
		}
	}

	public static double round(double val, final int places) {
		final long factor = (long) Math.pow(10, places);
		val = val * factor;
		final long tmp = Math.round(val);

		return (double) tmp / factor;
	}

	public void setRealTrayectory() {
		realTrayectory.clear();
		boolean insideField = true;
		int iteration = 0;
		double yPrev = 1000d;
		double xPrev = 1000d;

		final double height = sp.getTrajectory(0)[2];

		while (insideField) {
			final double[] values = sp.getTrajectory(iteration);
			final Position p = new Position(values[0], values[1]);
			final Ball b = new Ball(p, sp.ballAltitude() != height ? 0 : values[2], iteration);

			insideField = p.isInsideGameField(0);
			if (!insideField || (p.getY() == yPrev && p.getX() == xPrev)) {
				if (!insideField && Math.abs(p.getY()) > Constants.LARGO_CAMPO_JUEGO / 2 && Math.abs(p.getX()) <= Constants.LARGO_ARCO / 2) {
					realTrayectory.add(b);
				}
				break;
			}

			yPrev = p.getY();
			xPrev = p.getX();
			iteration++;
			realTrayectory.add(b);
		}
	}

	public boolean isGoalkeeper(final int idx, final boolean rival) {
		return rival ? idxRivalGoalkeeper == idx : idx == RMConstants.idxMyGoalkeeper;
	}

	public int getIdxRivalGoalkeeper() {
		return idxRivalGoalkeeper;
	}

	private void setShootingOptions() {
		passOptions.clear();

		setTrayectoryOptions(true, false);

		setTrayectoryOptions(false, true);

		setTrayectoryOptions(false, false);
	}

	public void setTrayectoryOptions(boolean banda, boolean autopass) {
		for (double strength = 1; strength >= 0.2; strength -= 0.005d) {
			final double shotPower = strength * RMConstants.MAX_SHOT_SPEED * (banda ? 0.75 : 1);

			if (!autopass) {
				for (double angZ = RMConstants.MAX_ANGLE; angZ >= RMConstants.MIN_ANGLE; angZ -= RMConstants.DELTA_ANGLE) {
					final double angRadZ = angZ * RMConstants.TO_RAD;

					double yMax = Math.pow(Math.sin(angRadZ) * shotPower, 2) * RMConstants.STATIC_HEIGHT_CALC;
					if (yMax > Constants.ALTURA_CONTROL_BALON) {
						addTrayectory(setTrayectory(shotPower, angRadZ, false, banda));
					}
				}
			}
			addTrayectory(setTrayectory(shotPower, 0, autopass, banda));
		}
	}

	private ShootingTrajectory setTrayectory(final double shotPower, final double angRadZ, boolean autopass, boolean banda) {
		final AbstractTrajectory airTrajectory = new AirTrajectory(Math.cos(angRadZ) * shotPower, Math.sin(angRadZ) * shotPower, 0, 0);

		final boolean paseRastrero = angRadZ == 0;

		final double altControl = paseRastrero ? 0 : Constants.ALTURA_CONTROL_BALON;

		final int iteracionesControl = Constants.ITERACIONES_GOLPEAR_BALON;

		boolean elevando = !paseRastrero;
		double oldtrY = 0d;
		boolean dzAntPosivivo = true;
		double dxAnt = 0d;
		final double x = 0;
		boolean altMax = false;
		ShootingTrajectory trajectory = new ShootingTrajectory(shotPower, angRadZ, banda);

		for (int step = 1; step <= (autopass ? iteracionesControl : 200); step++) {

			final double time = step / 60d;
			double dX = airTrajectory.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
			final double dY = airTrajectory.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
			final double y = dX;

			final Ball nuevoBalon = new Ball(x, y, dY, step);
			elevando = oldtrY < dY;

			dX = round(dX, 1);
			if (!paseRastrero && !elevando) {
				dzAntPosivivo = false;
				if (!altMax && dY < altControl) {
					return null;
				}
				altMax = true;
				if (step >= iteracionesControl && dY <= altControl) {
					trajectory.setDistancia(dX);
					trajectory.setIteraciones(step);
					return trajectory;
				}
			} else if (angRadZ == 0 && step >= iteracionesControl && dX - dxAnt <= Constants.DISTANCIA_CONTROL_BALON) {
				trajectory.setDistancia(dX);
				trajectory.setIteraciones(step);
				return trajectory;
			}
			if (dxAnt == dX || !nuevoBalon.getPosition().isInsideGameField(0) || ((elevando && !dzAntPosivivo) || (!elevando && dY < altControl))) {
				return null;
			}
			oldtrY = dY;
			dxAnt = dX;
		}
		return null;
	}

	private void addTrayectory(ShootingTrajectory passTr) {
		if (passTr != null) {
			double distance = round(passTr.getDistancia(), 1);

			TreeSet<ShootingTrajectory> set = new TreeSet<ShootingTrajectory>();

			if (passOptions.containsKey(distance)) {
				set = passOptions.get(distance);
			}
			set.add(passTr);
			passOptions.put(distance, set);
			if (maxIter < passTr.getIteraciones()) {
				maxIter = passTr.getIteraciones();
			}
			cantPass++;
		}
	}

	public LinkedList<Ball> getRealTrayectory() {
		return realTrayectory;
	}

	public TreeMap<Double, TreeSet<ShootingTrajectory>> getPassOptions() {
		return passOptions;
	}

	public boolean idBanda() {
		return Math.abs(sp.ballPosition().getX()) >= Constants.ANCHO_CAMPO_JUEGO / 2 && Math.abs(sp.ballPosition().getX()) < Constants.LARGO_CAMPO_JUEGO / 2;
	}

	public int getAngle(int idxPlayer, boolean rival) {
		boolean moving = isMoving(idxPlayer, rival);
		if (rival && moving) {
			return (int) (previous.rivalPlayers[idxPlayer].angle(sp.rivalPlayers()[idxPlayer]) * RMConstants.TO_ANG);
		}
		if (!rival && moving) {
			return (int) (previous.myPlayers[idxPlayer].angle(sp.myPlayers()[idxPlayer]) * RMConstants.TO_ANG);
		}
		return 90;
	}

	public boolean isMoving(int idxPlayer, boolean rival) {
		if (rival) {
			return previous.rivalPlayers[idxPlayer].distance(sp.rivalPlayers()[idxPlayer]) > 0;
		}
		return previous.myPlayers[idxPlayer].distance(sp.myPlayers()[idxPlayer]) > 0;
	}

	public double getDistance(Position playerPos, MyAceleration acel, int iter, double angle, double speed, double energy, Direction prevDir) {

		Position nextPos = playerPos.moveAngle(angle, iter * 0.46);

		double aceleracionX = acel.x;
		double aceleracionY = acel.y;

		Direction newDir = getDirection(playerPos, nextPos);

		if (prevDir.x != newDir.x)
			aceleracionX = Constants.ACELERACION_MINIMA_X;
		else {
			aceleracionX += Constants.ACELERACION_INCR;
			if (aceleracionX > 1)
				aceleracionX = 1;
		}

		if (prevDir.y != newDir.y)
			aceleracionY = Constants.ACELERACION_MINIMA_Y;
		else {
			aceleracionY += Constants.ACELERACION_INCR;
			if (aceleracionY > 1)
				aceleracionY = 1;
		}

		double distance = 0d;
		for (int i = 0; i < iter; i++) {
			if (aceleracionX < 1) {
				aceleracionX += Constants.ACELERACION_INCR;
				aceleracionX = (aceleracionX > 1) ? 1 : aceleracionX;
			}
			if (aceleracionY < 1) {
				aceleracionY += Constants.ACELERACION_INCR;
				aceleracionY = (aceleracionY > 1) ? 1 : aceleracionY;
			}

			energy -= Constants.ENERGIA_DIFF;

			energy = (energy < Constants.ENERGIA_MIN) ? Constants.ENERGIA_MIN : energy;

			distance += speed * aceleracionX * aceleracionY * energy;
		}
		return distance;
	}

	public MyAceleration getAceleration(int idx, boolean rival) {
		double acel = round(rival ? sp.getRivalAceleration(idx) : sp.getMyPlayerAceleration(idx), 4);
		return acelerationData.get(acel);
	}

	public Direction getDirection(Position posA, Position posB) {
		double dX = posB.getX() - posA.getX();
		double dY = posB.getY() - posA.getY();
		double dirX = (dX == 0) ? 0 : (Math.signum(dX));
		double dirY = (dY == 0) ? 0 : (Math.signum(dY));
		return new Direction(dirX, dirY);
	}

	public double getPower(int idxPlayer, double potencia) {
		double factor = sp.getMyPlayerPower(idxPlayer);
		double energy = sp.getMyPlayerEnergy(idxPlayer);
		double max = Constants.getVelocidadRemate(factor);

		if (energy < 0.8) {
			return potencia * energy * Constants.ENERGIA_DISPARO / max;
		}
		return potencia / max;
	}

	public double[] getDatosControl(final int idxJugador, final Position p, final boolean esRival) {
		if ((idxJugador == -1 || isGoalkeeper(idxJugador, esRival)) && enAreaGrande(p, esRival)) {
			return new double[] { Constants.DISTANCIA_CONTROL_BALON_PORTERO, Constants.ALTO_ARCO };
		}
		return new double[] { Constants.DISTANCIA_CONTROL_BALON, Constants.ALTURA_CONTROL_BALON };
	}

	public boolean enAreaGrande(final Position pos, final boolean rival) {
		final double minY = ((Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE);
		final boolean yEnArea = rival ? pos.getY() >= minY : pos.getY() < -minY;
		if (Math.abs(pos.getX()) <= Constants.LARGO_AREA_GRANDE / 2 && yEnArea) {
			return true;
		}
		return false;
	}

	public boolean enAreaChica(final Position pos, final boolean rival) {
		final double minY = ((Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_CHICA);
		final boolean yEnArea = rival ? pos.getY() >= minY : pos.getY() < -minY;
		if (Math.abs(pos.getX()) <= Constants.LARGO_AREA_CHICA / 2 && yEnArea) {
			return true;
		}
		return false;
	}

	public CommandMoveTo moveTo(int idx, Position destiny) {
		Position playerPos = sp.myPlayers()[idx];

		Position adjustDestiny = destiny.setInsideGameField();
		double angle = playerPos.angle(adjustDestiny);

		double distance = playerPos.distance(adjustDestiny);
		MyAceleration acel = getAceleration(idx, false);

		double aceleracion = acel.x * acel.y;

		double vel = Constants.getVelocidad(sp.getMyPlayerSpeed(idx)) * sp.getMyPlayerEnergy(idx) * aceleracion;

		if (distance < vel) {
			vel = distance;
		}
		return new CommandMoveTo(idx, playerPos.moveAngle(angle, vel));
	}
}
