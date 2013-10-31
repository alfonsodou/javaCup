package org.javahispano.javacup.tacticas.jvc2013.romedal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.romedal.util.Analyze;
import org.javahispano.javacup.tacticas.jvc2013.romedal.util.Analyze.Direction;
import org.javahispano.javacup.tacticas.jvc2013.romedal.util.Analyze.MyAceleration;
import org.javahispano.javacup.tacticas.jvc2013.romedal.util.Ball;
import org.javahispano.javacup.tacticas.jvc2013.romedal.util.BallDestiny;
import org.javahispano.javacup.tacticas.jvc2013.romedal.util.HitBallDetail;
import org.javahispano.javacup.tacticas.jvc2013.romedal.util.RMConstants;
import org.javahispano.javacup.tacticas.jvc2013.romedal.util.ShootingTrajectory;

/**
 * @author Roland Cruz
 */
/**
 * @author Roland
 */
public class RomedalTeam implements Tactic, Alineaciones, RMConstants {

	private final ArrayList<Command>	comandos			= new ArrayList<Command>();
	private final DetalleTactica		detalle				= new DetalleTactica(this.getClass().getSimpleName());
	private int							iterSaco			= 0;
	private GameSituations				sp;
	boolean								laTengo				= false;
	private int							ultimoPasador		= -1;
	private int							destinoPase			= -1;
	private Ball						balonDestinoPase	= null;
	final TreeSet<Integer>				marcando			= new TreeSet<Integer>();
	final TreeSet<Integer>				recuperando			= new TreeSet<Integer>();
	private Analyze						analisys;
	int									goles				= 0;
	private int							iterDespeje;

	@Override
	public TacticDetail getDetail() {
		return detalle;
	}

	@Override
	public List<Command> execute(final GameSituations sp) {
		inicializar(sp);

		BallDestiny destino = getBestLocated(false, false);

		if (destino == null || destino.isGanaRival()) {
			destino = getBestLocated(true, false);

			if (destino == null || destino.isGanaRival() || (analisys.getRealTrayectory().size() <= 1 && !sp.isStarts()) || analisys.heHaveBall()) {
				laTengo = false;
			}
		}
		if (analisys.iHaveBall()) {
			destinoPase = -1;
			balonDestinoPase = null;
			golpearBalon();
			recuperando.add(ultimoPasador);
			marcando.add(ultimoPasador);
			recuperando.add(destinoPase);
			marcando.add(destinoPase);
		} else {
			for (int i = 0; i < 11; i++) {
				if (sp.iterationsToKick()[i] > 0) {
					ultimoPasador = i;
					break;
				}
			}
			if (analisys.heHaveBall()) {
				balonDestinoPase = null;
				ultimoPasador = -1;
				destinoPase = -1;
			}
			recuperarBalon(destino);
		}

		offSide();
		ubicarPortero(destino);
		setAlineacion();
		analisys.finallyIteration(comandos);
		marcando.clear();
		recuperando.clear();
		return comandos;
	}

	private void offSide() {
		double yBall = sp.ballPosition().getY();
		if (!(analisys.idBanda() && sp.isStarts())) {
			Position bestPlayer = sp.ballPosition();
			if (destinoPase > 0 && sp.myPlayers()[destinoPase].getY() > yBall) {
				bestPlayer = sp.myPlayers()[destinoPase];
			}
			for (int idx = 0; idx < 11; idx++) {
				if (!marcando.contains(idx) && !recuperando.contains(idx) && idx != destinoPase && (analisys.isOffSide(idx, false) && sp.myPlayers()[idx].getY() > bestPlayer.getY()) && idx != ultimoPasador) {
					comandos.add(analisys.moveTo(idx, sp.myPlayers()[idx].movePosition(0, -0.5)));
				}
			}
		}
		if (laTengo) {
			double minY = -52.5d;
			boolean enArea = false;
			for (int idx = 0; idx < 11; idx++) {
				double yRival = sp.rivalPlayers()[idx].getY();
				if (yRival <= -5 && yRival < yBall && yRival > minY) {
					minY = yRival;
					enArea = true;
				}
			}

			if (enArea) {
				for (int idx = 1; idx < 11; idx++) {
					if (!recuperando.contains(idx) && sp.myPlayers()[idx].getY() < minY) {
						comandos.add(analisys.moveTo(idx, new Position(sp.myPlayers()[idx].getX(), minY + 0.5)));
					}
				}
			}
		} else {
			if (sp.isRivalStarts() && analisys.idBanda()) {
				double maxY = 0d;
				boolean enArea = false;
				for (int idx = 0; idx < 11; idx++) {
					double yRival = sp.rivalPlayers()[idx].getY();
					if (yRival <= 0 && yRival < maxY) {
						maxY = yRival;
						enArea = true;
					}
				}
				if (enArea) {
					int cant = 0;
					for (int idx = 1; idx < 5; idx++) {
						if (!recuperando.contains(idx) && sp.myPlayers()[idx].getY() > maxY && cant < 4) {
							comandos.add(analisys.moveTo(idx, new Position(sp.myPlayers()[idx].getX(), maxY - 0.1)));
							recuperando.add(idx);
							cant++;
						}
					}
				}
			}
		}
	}

	private void inicializar(final GameSituations sp) {
		this.sp = sp;
		laTengo = true;
		if (sp.iteration() == 0) {
			iterDespeje = 0;
			analisys = new Analyze(sp);
		}
		analisys.update(sp);
		comandos.clear();

	}

	private int obtenerTrayectoriaCalculada(final LinkedList<Ball> trayectoriaBalon, final Ball balon, final double potencia, final double angleXY, final double angleZ, final Position pFinal, final boolean completa, final int idxReceptor,
			final int maxPasos) {
		trayectoriaBalon.clear();
		balon.setIteracion(0);
		trayectoriaBalon.add(balon);
		final AbstractTrajectory trajectory = new AirTrajectory(Math.cos(angleZ) * potencia, Math.sin(angleZ) * potencia, 0, 0);

		final double[] datosControl = analisys.getDatosControl(idxReceptor, pFinal, false);

		double distanciaAPunto = 1000d;
		int iteracionesAlcance = -1;
		boolean elevando = true;
		double oldtrY = 0d;
		boolean dzAntPosivivo = true;
		double dxAnt = 0d;
		Ball b0 = null;

		final boolean centro = sp.ballPosition().distance(Constants.centroCampoJuego) == 0;
		for (int step = 1; step <= maxPasos; step++) {
			final double time = step / 60d;
			final double dX = trajectory.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
			final double dY = trajectory.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
			final double x = balon.getPosition().getX() + dX * Math.cos(angleXY);
			final double y = balon.getPosition().getY() + dX * Math.sin(angleXY);
			final Ball nuevoBalon = new Ball(x, y, dY, step);
			elevando = oldtrY < dY;
			distanciaAPunto = nuevoBalon.getPosition().setInsideGameField().distance(pFinal);
			if (!elevando) {
				dzAntPosivivo = false;
				if (iteracionesAlcance == -1) {
					if (idxReceptor == -1 && !trayectoriaBalon.isEmpty()) {
						if (angleZ > 0) {
							final Ball balonAnterior = trayectoriaBalon.getLast();
							final boolean antAdentro = balonAnterior.getAltura() > Constants.ALTO_ARCO && balonAnterior.getPosition().getY() <= META_Y;
							final boolean actAfuera = nuevoBalon.getAltura() < Constants.ALTO_ARCO && nuevoBalon.getPosition().getY() > META_Y;
							if (antAdentro && actAfuera) {
								final double deltaX = balonAnterior.getPosition().distance(nuevoBalon.getPosition());
								final double deltaY = balonAnterior.getAltura() - nuevoBalon.getAltura();
								final double h = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
								final double teta = Math.acos((deltaX * deltaX + h * h - deltaY * deltaY) / (2 * deltaX * h));
								final double nuevoX = deltaX - (META_Y - balonAnterior.getPosition().getY());
								final double alturaMeta = nuevoBalon.getAltura() + (Math.tan(teta) * nuevoX);
								if (alturaMeta <= Constants.ALTO_ARCO && (nuevoBalon.getAltura() - (balonAnterior.getAltura() - b0.getAltura()) <= Constants.ALTO_ARCO || centro)) {
									trayectoriaBalon.add(nuevoBalon);
									return step;
								}
							}
						} else if (nuevoBalon.getPosition().getY() >= Constants.LARGO_CAMPO_JUEGO / 2 && Math.abs(nuevoBalon.getPosition().getX()) <= Constants.LARGO_ARCO) {
							trayectoriaBalon.add(nuevoBalon);
							return step;
						}
					} else if (iteracionesAlcance == -1 && distanciaAPunto <= datosControl[0] && (angleZ == 0 || (dY <= datosControl[1]))) {
						iteracionesAlcance = Math.max(step, Constants.ITERACIONES_GOLPEAR_BALON);
						if (!completa) {
							trayectoriaBalon.add(nuevoBalon);
							return iteracionesAlcance;
						}
					}
				}
			}
			if (!completa && iteracionesAlcance == -1 && (angleZ > 0 && ((elevando && !dzAntPosivivo) || (!elevando && dY < datosControl[1])))) {
				return iteracionesAlcance;
			}
			if (dxAnt == dX || !nuevoBalon.getPosition().isInsideGameField(0)) {
				return iteracionesAlcance;
			}
			oldtrY = dY;
			dxAnt = dX;
			if (trayectoriaBalon.size() > 0) {
				b0 = trayectoriaBalon.getLast();
			}
			trayectoriaBalon.add(nuevoBalon);

		}
		return iteracionesAlcance;
	}

	private void ubicarPortero(BallDestiny destino) {
		if (sp.iteration() > 1) {
			Position goalkeeperPos = sp.myPlayers()[idxMyGoalkeeper];
			CommandMoveTo cmd = null;

			if (!sp.isStarts() && (sp.ballPosition().getY() < 0 || !analisys.enAreaChica(goalkeeperPos, false))) {
				for (final Command c : comandos) {
					if (c.getPlayerIndex() == idxMyGoalkeeper && c instanceof CommandMoveTo && ultimoPasador != destinoPase) {
						comandos.remove(c);
						break;
					}
				}
				BallDestiny destiny = destino;
				if (destino == null || destino.getIdxJugador() != idxMyGoalkeeper || destino.isGanaRival()) {
					destiny = getBestLocated(true, false);
				}
				if (destiny != null && !destiny.isGanaRival()) {
					cmd = analisys.moveTo(idxMyGoalkeeper, destiny.getPosBalon());

				} else if (sp.ballPosition().distance(goalkeeperPos) > Constants.DISTANCIA_CONTROL_BALON) {
					cmd = analisys.moveTo(idxMyGoalkeeper, mejorAnguloPosArquero());
				} else {
					cmd = analisys.moveTo(idxMyGoalkeeper, sp.ballPosition());
				}
			} else {
				if (sp.isStarts() && analisys.enAreaGrande(sp.ballPosition(), false)) {
					cmd = analisys.moveTo(idxMyGoalkeeper, sp.ballPosition());
				} else if (sp.ballPosition().getY() < 0) {
					cmd = analisys.moveTo(idxMyGoalkeeper, mejorAnguloPosArquero());
				}
			}

			if (cmd != null) {
				comandos.add(cmd);
				final ArrayList<CommandMoveTo> listaBorrar = new ArrayList<CommandMoveTo>();
				for (final Command c : comandos) {
					if (c instanceof CommandMoveTo) {
						final CommandMoveTo otherCmd = (CommandMoveTo) c;
						if (otherCmd.getPlayerIndex() != idxMyGoalkeeper && otherCmd.getMoveTo().equals(cmd.getMoveTo())) {
							listaBorrar.add(otherCmd);
						}
					}
				}
				comandos.removeAll(listaBorrar);
			}
		}
	}

	private Position mejorAnguloPosArquero() {
		final double distDer = Constants.posteDerArcoInf.distance(sp.ballPosition()) / 3;
		final double distIzq = Constants.posteIzqArcoInf.distance(sp.ballPosition()) / 3;
		final double minDist = Math.min(distDer, distIzq);
		final double distRival = sp.ballPosition().distance(sp.rivalPlayers()[sp.ballPosition().nearestIndex(sp.rivalPlayers())]);
		if (minDist <= Constants.ANCHO_AREA_GRANDE && distRival <= Constants.DISTANCIA_CONTROL_BALON) {
			final double anguloPoste = Constants.posteIzqArcoInf.angle(sp.ballPosition());
			final Position posRet = Position.Intersection(sp.ballPosition(), Constants.centroArcoInf, Constants.posteDerArcoInf, Constants.posteIzqArcoInf.moveAngle(anguloPoste, distIzq));
			if (posRet != null) {
				double angle = sp.myPlayers()[idxMyGoalkeeper].angle(posRet);
				return sp.myPlayers()[idxMyGoalkeeper].moveAngle(angle, 0.3);
			}
		}
		final double angulo = Constants.centroArcoInf.angle(sp.ballPosition());
		return Constants.centroArcoInf.moveAngle(angulo, (Constants.LARGO_ARCO - Constants.DISTANCIA_CONTROL_BALON_PORTERO) / 2);
	}

	private void recuperarBalon(final BallDestiny destino) {
		boolean autopase = false;
		boolean paseAlVacio = false;

		if (destino != null && !destino.isGanaRival()) {
			destinoPase = destino.getIdxJugador();
		}
		if (ultimoPasador == destinoPase && destinoPase >= 0 && (laTengo || ultimoPasador != idxMyGoalkeeper)) {
			autopase = true;
			recuperando.add(ultimoPasador);
			comandos.add(analisys.moveTo(destinoPase, analisys.getRealTrayectory().getLast().getPosition()));
		} else if (destinoPase >= 0 && balonDestinoPase != null && (laTengo || destinoPase != idxMyGoalkeeper) && moverAlHueco(analisys.getRealTrayectory())) {
			paseAlVacio = true;
			recuperando.add(destinoPase);
			if (ultimoPasador >= 0 && ultimoPasador != idxMyGoalkeeper && ultimoPasador != destinoPase) {
				comandos.add(analisys.moveTo(ultimoPasador, sp.myPlayers()[ultimoPasador].moveAngle(90 * TO_RAD, sp.getMyPlayerSpeed(ultimoPasador)).setInsideGameField()));
				recuperando.add(ultimoPasador);
			}
		}
		if (destino != null && !destino.isGanaRival() && !autopase && !paseAlVacio) {
			comandos.add(analisys.moveTo(destino.getIdxJugador(), destino.getPosBalon().setInsideGameField()));
			recuperando.add(destino.getIdxJugador());
		}
		if (!laTengo) {
			if (!analisys.getRealTrayectory().isEmpty() && (analisys.getRealTrayectory().getLast().getPosition().getY() >= 52.5 || sp.ballPosition().equals(metaArribaDerecha) || sp.ballPosition().equals(metaArribaIzquierda))) {
				final int idx = Constants.centroArcoSup.nearestIndex(sp.myPlayers());
				recuperando.add(idx);
				if (analisys.getRealTrayectory().getLast().getPosition().getX() < 0) {
					comandos.add(analisys.moveTo(idx, metaArribaIzquierda));
				} else {
					comandos.add(analisys.moveTo(idx, metaArribaDerecha));
				}
			}
			final BallDestiny nuevoDestino = getBestLocated(false, true);
			if (nuevoDestino != null) {
				comandos.add(analisys.moveTo(nuevoDestino.getIdxJugador(), nuevoDestino.getPosBalon()));
				recuperando.add(nuevoDestino.getIdxJugador());
			} else if (!analisys.getRealTrayectory().isEmpty()) {
				final Position pDest = destino != null && destino.isGanaRival() ? destino.getPosBalon() : analisys.getRealTrayectory().getLast().getPosition();
				int idxCerca = pDest.nearestIndex(sp.myPlayers(), idxMyGoalkeeper);
				comandos.add(analisys.moveTo(idxCerca, pDest));
				recuperando.add(idxCerca);
			}
		}

	}

	private boolean moverAlHueco(final LinkedList<Ball> trayectoriaBalon) {
		boolean llegaAlPase = true;
		if (!trayectoriaBalon.contains(balonDestinoPase)) {
			boolean alcanzaBalon = false;
			Ball opcion = null;
			for (final Ball balon : trayectoriaBalon) {
				if (balon.getPosition().isInsideGameField(0)) {
					final double distancia = balonDestinoPase.getPosition().distance(balon.getPosition());
					if (distancia <= Constants.DISTANCIA_CONTROL_BALON && balon.getAltura() <= Constants.ALTURA_CONTROL_BALON && balon.getIteracion() >= sp.iterationsToKick()[destinoPase]) {
						alcanzaBalon = true;
						opcion = balon;
					} else if (alcanzaBalon) {
						break;
					}
				}
			}
			if (opcion != null) {
				balonDestinoPase = opcion;
			} else {
				llegaAlPase = false;
			}
		}
		if (llegaAlPase) {
			comandos.add(analisys.moveTo(destinoPase, balonDestinoPase.getPosition()));
			return true;
		}

		return false;
	}

	private boolean puedoGolpear() {
		if (sp.canKick().length > 0 && sp.ballPosition().distance(Constants.centroCampoJuego) == 0) {
			return true;
		}
		if (sp.canKick().length == 0 || sp.isStarts() || iterSaco <= Constants.ITERACIONES_SAQUE * 3.5 / 5) {
			iterSaco++;
			double maxIter = ((Constants.ITERACIONES_SAQUE * 4 / 5) - iterSaco) * 0.45;
			boolean hayOpcion = false;
			int i = -1;
			for (int idx = 0; idx < 11; idx++) {
				if (sp.myPlayers()[idx].distance(sp.ballPosition()) <= maxIter && sp.getMyPlayerError(idx) >= 0.9 && sp.getMyPlayerPower(idx) > 0.5) {
					if (idx != idxMyGoalkeeper || analisys.enAreaGrande(sp.ballPosition(), false)) {
						hayOpcion = true;
						i = idx;
						break;
					}
				}
			}
			if (!hayOpcion) {
				i = sp.ballPosition().nearestIndex(sp.myPlayers(), idxMyGoalkeeper);
			}
			comandos.add(analisys.moveTo(i, sp.ballPosition()));
			recuperando.add(i);
			int receptor = -1;
			if (sp.isStarts()) {
				int[] closed = sp.ballPosition().nearestIndexes(sp.myPlayers(), i, idxMyGoalkeeper);
				for (int close : closed) {
					if (sp.getMyPlayerPower(close) >= 0.8) {
						receptor = close;
						break;
					}
				}
				recuperando.add(receptor);
				double angle = sp.ballPosition().angle(sp.myPlayers()[receptor]);
				CommandMoveTo cmd = analisys.moveTo(receptor, sp.ballPosition().moveAngle(angle, 6.5));
				boolean saca = sp.myPlayers()[i].distance(sp.ballPosition()) < 0.1;
				if (saca && sp.myPlayers()[receptor].distance(cmd.getMoveTo()) < 0.1) {
					return true;
				}
				comandos.add(cmd);
			}
			if (sp.ballPosition().distance(Constants.cornerSupDer) == 0 || sp.ballPosition().distance(Constants.cornerSupIzq) == 0) {
				final int j = Constants.penalSup.nearestIndex(sp.myPlayers(), i, receptor);
				comandos.add(analisys.moveTo(j, Constants.penalSup));
				recuperando.add(j);
			}
		}
		if (sp.canKick().length > 0) {
			for (int i : sp.canKick()) {
				if (sp.ballPosition().distance(sp.myPlayers()[i]) <= (i == idxMyGoalkeeper && analisys.enAreaGrande(sp.ballPosition(), false) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean hacerPase(final int minIterDif, boolean autopase, boolean toBack) {
		final int[] receptores = Constants.centroArcoSup.nearestIndexes(sp.myPlayers(), idxMyGoalkeeper, ultimoPasador);
		HitBallDetail mejorDestino = null;
		int mejorReceptor = 0;
		double bestSkill = 0;
		boolean myArea = sp.ballPosition().getY() < -10;
		HitBallDetail dest = null;
		if (!sp.isStarts() && autopase) {
			dest = getOpcionPase(ultimoPasador, ultimoPasador, minIterDif + 1, toBack);
			if (dest != null) {
				if (toBack || (!myArea || (dest.getComando().getDestiny().getY() > sp.ballPosition().getY()))) {
					mejorReceptor = ultimoPasador;
					mejorDestino = dest;
				}
			}
		}
		if (!toBack && mejorDestino == null) {
			for (final int idxReceptor : receptores) {
				if (!analisys.isOffSide(idxReceptor, false)) {
					if (idxReceptor == ultimoPasador || (idxReceptor == idxMyGoalkeeper && analisys.getMyPosession() < 0.55)) {
						continue;
					}
					dest = getOpcionPase(ultimoPasador, idxReceptor, minIterDif, false);
					if (dest != null) {
						boolean descartar = false;
						if (idxReceptor == idxMyGoalkeeper) {
							double ang = sp.ballPosition().angle(dest.getComando().getDestiny());
							Position pFinal = Position.Intersection(sp.ballPosition(), sp.ballPosition().moveAngle(ang, 100), Constants.centroArcoInf.movePosition(-Constants.LARGO_ARCO, 0),
									Constants.centroArcoInf.movePosition(Constants.LARGO_ARCO, 0));
							if (pFinal != null && Math.abs(pFinal.getX()) <= Constants.LARGO_ARCO) {
								descartar = true;
							}
						}
						if (!descartar) {
							double skill = sp.getMyPlayerEnergy(idxReceptor) + sp.getMyPlayerError(idxReceptor) + sp.getMyPlayerPower(idxReceptor);
							if (mejorDestino == null) {
								mejorDestino = dest;
								mejorReceptor = idxReceptor;
								bestSkill = skill;
							} else {
								int difIters = dest.getIterJugador() - dest.getIterRival();
								final double yMax = dest.getComando().getDestiny().getY();

								if (difIters >= mejorDestino.getDifIter() && ((skill > bestSkill && yMax > sp.ballPosition().getY()) || yMax > mejorDestino.getComando().getDestiny().getY())) {
									mejorDestino = dest;
									mejorReceptor = idxReceptor;
									bestSkill = skill;
								}
							}
						}
					}
				}
			}
		}
		if (mejorDestino != null) {
			final CommandHitBall comando = mejorDestino.getComando();
			destinoPase = mejorReceptor;
			balonDestinoPase = new Ball(comando.getDestiny(), mejorDestino.getTrayectoria().get(mejorDestino.getIterJugador()).getAltura());
			comandos.add(comando);
			comandos.add(analisys.moveTo(destinoPase, comando.getDestiny()));
			return true;
		}
		return false;
	}

	private boolean hacerDisparo(final boolean obstaculo, final double minAngZ, final double maxAngZ) {
		HitBallDetail command = mejorDisparoAlArco(ultimoPasador, obstaculo, minAngZ, maxAngZ);
		if (command != null) {
			comandos.add(command.getComando());
			return true;
		}
		return false;
	}

	private void despejarBalon() {
		if (sp.ballPosition().getY() > 35 || ultimoPasador == idxMyGoalkeeper) {
			comandos.add(new CommandHitBall(ultimoPasador, Constants.centroArcoSup, 1, ultimoPasador == idxMyGoalkeeper));
			return;
		}
		if (!sp.isStarts() && sp.ballPosition().getY() <= 0) {
			Position rival = sp.rivalPlayers()[sp.ballPosition().nearestIndex(sp.rivalPlayers())];
			if (rival.distance(sp.myPlayers()[ultimoPasador]) > 1.5) {
				comandos.add(new CommandHitBall(ultimoPasador, Constants.penalSup, 1, 60));
			} else if (!hacerPase(1, true, true)) {
				if (ultimoPasador == idxMyGoalkeeper || (sp.iteration() - iterDespeje == 1 && sp.iteration() > 1 && sp.ballPosition().distance(analisys.getPreviousBall().getPosition()) < 0.1)) {
					comandos.add(new CommandHitBall(ultimoPasador, Constants.penalSup, 1, true));
				} else {
					double angInicial = rival.angle(sp.myPlayers()[ultimoPasador]);
					Position myPlayer = sp.myPlayers()[ultimoPasador];
					comandos.add(analisys.moveTo(ultimoPasador, myPlayer.moveAngle(angInicial, 0.5)));
				}
				marcando.add(ultimoPasador);
				ultimoPasador = -1;
			}
		} else if (!sp.isStarts()) {
			if (sp.rivalCanKick().length > 0) {
				comandos.add(analisys.moveTo(ultimoPasador, Constants.penalInf));
				marcando.add(ultimoPasador);
			} else if (!hacerDisparo(false, 20, 45)) {
				comandos.add(new CommandHitBall(ultimoPasador, Constants.penalSup, 1, Constants.ANGULO_VERTICAL_MAX));
			}
		} else if (!hacerDisparo(false, 20, 45)) {
			comandos.add(new CommandHitBall(ultimoPasador, Constants.penalSup, 1, Constants.ANGULO_VERTICAL_MAX));
		}
		iterDespeje = sp.iteration();
	}

	private void golpearBalon() {

		if (puedoGolpear()) {
			iterSaco = 0;
			double distanceToGoal = sp.ballPosition().distance(Constants.centroArcoSup);
			boolean saco = sp.isStarts();
			int lastPlayer = ultimoPasador;
			int idxRival = sp.ballPosition().nearestIndex(sp.rivalPlayers());
			double rivalDistance = sp.ballPosition().distance(sp.rivalPlayers()[idxRival]);
			for (int pateador : sp.canKick()) {
				if (!analisys.isOffSide(pateador, false) || pateador == lastPlayer) {
					ultimoPasador = pateador;
					boolean autopase = !saco && (sp.ballPosition().getY() > -7 || rivalDistance > 12) && sp.getMyPlayerError(ultimoPasador) >= 0.8;
					if (ultimoPasador == idxMyGoalkeeper && sp.ballPosition().getY() > -20) {
						autopase = false;
					}
					final boolean puedeRematar = (autopase && puedoRematar()) || analisys.enAreaGrande(sp.ballPosition(), true);
					if (!puedeRematar || saco || !hacerDisparo(distanceToGoal > RMConstants.FLAT_SHOT, 0, 60)) {
						if (!hacerPase(2, autopase, false)) {
							despejarBalon();
						}
					}
				}
			}
		}
	}

	private boolean puedoRematar() {
		return sp.getMyPlayerEnergy(ultimoPasador) > 0.8 && sp.getMyPlayerPower(ultimoPasador) >= 0.6;

	}

	private void setAlineacion() {
		final List<Integer> excluir = new ArrayList<Integer>();
		for (final Command com : comandos) {
			if (com instanceof CommandMoveTo) {
				excluir.add(com.getPlayerIndex());
			}
		}
		if (sp.ballPosition().getY() < -45 || (sp.isRivalStarts() && sp.ballPosition().getY() < 0)) {
			alinear(alineacion0, excluir);
		} else {
			double refY = 0;
			if (sp.iteration() > 0 && !analisys.getRealTrayectory().isEmpty()) {
				analisys.getRealTrayectory().getLast().getPosition().getY();
			} else if (balonDestinoPase != null) {
				refY = balonDestinoPase.getPosition().getY();
			} else {
				refY = sp.ballPosition().getY();
			}
			refY += sp.isRivalStarts() ? -15 : 15;
			final double y = Math.max(0, refY + Constants.centroArcoSup.getY() - 1);
			int seccionAct = Math.max(0, Math.min(alineaciones.length - 1, (int) Math.floor(y / seccion)));
			alinear(alineaciones[seccionAct], excluir);
		}
		final ArrayList<CommandMoveTo> listaBorrar = new ArrayList<CommandMoveTo>();
		for (final Command c : comandos) {
			if (c instanceof CommandMoveTo) {
				CommandMoveTo cm = ((CommandMoveTo) c);
				if (sp.myPlayers()[cm.getPlayerIndex()].distance(cm.getMoveTo()) < Constants.VELOCIDAD_MIN) {
					listaBorrar.add(cm);
				}
			}
		}
		comandos.removeAll(listaBorrar);
	}

	private void alinear(final Position alineacion[], final List<Integer> excluir) {
		double maxY = 0d;
		double prevY = 0;
		for (Position rival : sp.rivalPlayers()) {
			if (rival.getY() > 0) {
				if (prevY < rival.getY()) {
					prevY = rival.getY();
				} else if (maxY < rival.getY()) {
					maxY = rival.getY();
				}
			}
		}
		if (maxY == 0 && sp.ballPosition().getY() > 0) {
			maxY = sp.ballPosition().getY();
		}
		if (destinoPase > 0 && sp.myPlayers()[destinoPase].getY() > maxY) {
			maxY = sp.myPlayers()[destinoPase].getY();
		}
		final double dy = analisys.getPreviousBall() != null && !sp.isStarts() ? (sp.ballPosition().getY() - analisys.getPreviousBall().getPosition().getY()) : sp.isStarts() ? 3 : 0;
		final double delta = 0.5;

		for (int i = 1; i < alineacion.length; i++) {
			if (!excluir.contains(i)) {
				final Position pos = alineacion[i];
				if (laTengo) {
					double angulo = 0;
					if (pos.getX() < 0) {
						angulo = pos.angle(Constants.posteIzqArcoSup);
					} else {
						angulo = pos.angle(Constants.posteDerArcoSup);
					}
					CommandMoveTo cmd = analisys.moveTo(i, pos.moveAngle(angulo, delta, dy).setInsideGameField());
					if (maxY < 0 || cmd.getMoveTo().getY() < maxY || i == destinoPase) {
						comandos.add(cmd);
					}
				} else {
					if (analisys.isOffSide(i, false) && pos.getY() > sp.myPlayers()[i].getY()) {
						comandos.add(analisys.moveTo(i, pos.movePosition(0, -0.5)));
						continue;
					}
					CommandMoveTo cmd = analisys.moveTo(i, pos);
					if (maxY < 0 || cmd.getMoveTo().getY() < maxY) {
						comandos.add(cmd);
					}
				}
			}
		}
	}

	private BallDestiny getBestLocated(final boolean analizarPortero, final boolean soloMios) {
		BallDestiny myDestiny = null;
		BallDestiny rivalDestiny = null;

		if (!analizarPortero) {
			for (int i = 0; i < 11; i++) {
				if (i != idxMyGoalkeeper) {
					final BallDestiny destiny = getPlayerDestiny(analisys.getRealTrayectory(), i, false, myDestiny == null ? 100 : myDestiny.getIterBalon(), true);
					if (destiny != null && destiny.compareTo(myDestiny) > 0) {
						myDestiny = destiny;
					}
				}
			}
		} else {
			myDestiny = getPlayerDestiny(analisys.getRealTrayectory(), idxMyGoalkeeper, false, 100, true);
		}
		if (soloMios) {
			return myDestiny;
		}
		rivalDestiny = getRivalBestLocated(analisys.getRealTrayectory(), true, false);

		if (analizarPortero && myDestiny != null && rivalDestiny != null) {
			boolean area = analisys.enAreaGrande(myDestiny.getPosBalon(), false);
			double alt = myDestiny.getAltura();
			if (((area && alt <= Constants.ALTO_ARCO && myDestiny.compareTo(rivalDestiny) >= 0) || (!area && alt < Constants.ALTURA_CONTROL_BALON && myDestiny.compareTo(rivalDestiny) > 0))) {
				return myDestiny;
			}
		}
		if (myDestiny != null && rivalDestiny == null) {
			return myDestiny;
		}
		if (myDestiny == null && rivalDestiny != null) {
			return rivalDestiny;
		}
		if (myDestiny != null && rivalDestiny != null) {
			return myDestiny.compareTo(rivalDestiny) > 0 || (myDestiny.compareTo(rivalDestiny) == 0 && myDestiny.getIdxJugador() != idxMyGoalkeeper) ? myDestiny : rivalDestiny;
		}
		return myDestiny;
	}

	private BallDestiny getRivalBestLocated(final LinkedList<Ball> trajectory, final boolean isRealTrajectory, boolean autopase) {
		final int[] indices = sp.ballPosition().nearestIndexes(sp.rivalPlayers());
		BallDestiny bestDestiny = null;
		for (final int i : indices) {

			final BallDestiny destiny = getPlayerDestiny(trajectory, i, true, bestDestiny == null ? 1000 : bestDestiny.getIterBalon(), isRealTrajectory);
			if (destiny != null) {
				boolean canKick = false;
				if (!autopase && destiny.getIterJugador() == 0) {
					for (int idx : sp.rivalCanKick()) {
						if (idx == i) {
							canKick = true;
							break;
						}
					}

				} else {
					canKick = true;
				}
				if (canKick && destiny.compareTo(bestDestiny) > 0) {
					bestDestiny = destiny;
					bestDestiny.setGanaRival(true);
				}
			}
		}
		return bestDestiny;
	}

	private BallDestiny getPlayerDestiny(final LinkedList<Ball> trajectory, final int idx, final boolean rival, final int maxIter, final boolean realTrajectory) {
		if (trajectory.size() > 1) {

			Position initPos = trajectory.get(0).getPosition();
			final Position playerPos = rival ? sp.rivalPlayers()[idx] : sp.myPlayers()[idx];

			final double maxHeight = analisys.isGoalkeeper(idx, rival) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;

			int iterControl = rival ? sp.rivalIterationsToKick()[idx] : (realTrajectory || idx != ultimoPasador ? sp.iterationsToKick()[idx] : Constants.ITERACIONES_GOLPEAR_BALON);

			MyAceleration aceleration = analisys.getAceleration(idx, rival);

			double speed = Constants.getVelocidad(rival ? sp.getRivalPlayerSpeed(idx) : sp.getMyPlayerPower(idx));

			double energy = rival ? sp.getRivalEnergy(idx) : sp.getMyPlayerEnergy(idx);

			Position prevPos = rival ? analisys.getPrevious().rivalPlayers[idx] : analisys.getPrevious().myPlayers[idx];
			Direction prevDir = analisys.getDirection(prevPos, playerPos);

			int iteration = 0;

			do {
				final Ball infoBalonSig = trajectory.get(iteration);
				if (infoBalonSig.getAltura() <= maxHeight) {

					final double[] controlData = analisys.getDatosControl(idx, infoBalonSig.getPosition(), rival);

					final double controlDistance = playerPos.distance(infoBalonSig.getPosition()) - controlData[0];

					double ballAngle = playerPos.angle(infoBalonSig.getPosition());
					double playerDistance = analisys.getDistance(playerPos, aceleration, iteration, ballAngle, speed, energy, prevDir);

					if (controlDistance <= playerDistance && infoBalonSig.getAltura() <= controlData[1] && iteration >= iterControl) {
						return new BallDestiny(initPos, infoBalonSig, idx, iteration, iteration);
					}
				}
				iteration++;
			} while (iteration < trajectory.size() && iteration <= maxIter);
		}
		return null;
	}

	@Override
	public Position[] getStartPositions(final GameSituations sp) {
		return alineacionSaca;
	}

	@Override
	public Position[] getNoStartPositions(final GameSituations sp) {
		return alineacionRecibe;
	}

	private HitBallDetail getOpcionPase(final int idxPasador, final int idxReceptor, final int minIterDif, boolean toBack) {
		final Position balon = sp.ballPosition();
		final Position receptor = sp.myPlayers()[idxReceptor];
		final boolean autoPase = idxPasador == idxReceptor;
		boolean preciso = sp.getMyPlayerError(idxPasador) >= 0.8;

		final LinkedHashMap<Integer, TreeSet<HitBallDetail>> angulosPosibles = new LinkedHashMap<Integer, TreeSet<HitBallDetail>>();

		int angInicial = analisys.getAngle(idxReceptor, false);
		if (toBack) {
			angInicial = (int) (sp.rivalPlayers()[sp.ballPosition().nearestIndex(sp.rivalPlayers())].angle(sp.myPlayers()[idxPasador]) * RMConstants.TO_ANG);
		}

		final int delta = angInicial <= 90 ? -1 : 1;

		angulosPosibles.put(angInicial, null);
		if (!toBack) {
			for (int i = 1; i < 180; i += 5) {
				final int a = angInicial - (i * delta);
				final int b = angInicial + (i * delta);
				angulosPosibles.put(a, null);
				angulosPosibles.put(b, null);
			}
		}
		final boolean banda = analisys.idBanda();
		final int pasoIni = autoPase ? Constants.ITERACIONES_GOLPEAR_BALON : 0;
		final double distanciaMinima = sp.isStarts() ? 6 : 15;
		HitBallDetail cgRet = null;
		MyAceleration aceleration = analisys.getAceleration(idxReceptor, false);
		double speed = Constants.getVelocidad(sp.getMyPlayerPower(idxReceptor));
		double energy = sp.getMyPlayerEnergy(idxReceptor);
		Position prevPos = analisys.getPrevious().myPlayers[idxReceptor];
		Direction prevDir = analisys.getDirection(prevPos, receptor);
		Position posCmd = receptor;
		CommandMoveTo cmd = analisys.getPrevious().commands[idxReceptor];
		if (cmd != null) {
			posCmd = cmd.getMoveTo();
		}
		for (final int ang : angulosPosibles.keySet()) {
			final double angReceptor = ang * TO_RAD;
			for (int paso = pasoIni; paso <= MAX_ITER; paso += 5) {
				double distance = analisys.getDistance(posCmd, aceleration, paso, angReceptor, speed, energy, prevDir);
				final Position pFinal = receptor.moveAngle(angReceptor, distance);
				if (!pFinal.isInsideGameField(-2)) {
					break;
				}
				if (autoPase && idxReceptor == idxMyGoalkeeper && pFinal.getY() < sp.ballPosition().getY()) {
					break;
				}
				final double distancia = balon.distance(pFinal);
				if ((autoPase || (distancia >= distanciaMinima))) {
					ShootingTrajectory infoTrayectoria = null;
					HitBallDetail comando = null;
					if (!autoPase) {
						infoTrayectoria = getPassOption(idxPasador, banda, distancia + (Constants.DISTANCIA_CONTROL_BALON / 2), Constants.ALTURA_CONTROL_BALON, paso);
						comando = disparoAlPunto(infoTrayectoria, idxPasador, pFinal, idxReceptor, paso, minIterDif);
					}
					if (autoPase || comando == null) {
						int closed = pFinal.nearestIndex(sp.rivalPlayers());
						boolean possible = pFinal.distance(sp.rivalPlayers()[closed]) > distance + Constants.DISTANCIA_CONTROL_BALON;
						if (possible) {
							infoTrayectoria = getPassOption(idxPasador, banda, distancia + Constants.DISTANCIA_CONTROL_BALON, 0, paso);
							comando = disparoAlPunto(infoTrayectoria, idxPasador, pFinal, idxReceptor, paso, minIterDif);
						}
					}
					if (comando != null) {
						comando.setIterBalon(paso);
						if (!autoPase && preciso && comando.getIterJugador() == paso) {
							return comando;
						}
						comando.setAnguloXY(ang);
						TreeSet<HitBallDetail> list = new TreeSet<HitBallDetail>();
						if (angulosPosibles.get(ang) != null) {
							list = angulosPosibles.get(ang);
						}
						list.add(comando);
						angulosPosibles.put(ang, list);
					}
				}
			}
			if (angulosPosibles.get(ang) != null) {
				final TreeSet<HitBallDetail> opciones = angulosPosibles.get(ang);
				for (final HitBallDetail c : opciones.descendingSet()) {

					if (cgRet == null) {
						cgRet = c;
					} else {
						int dif = c.getDifIter() > cgRet.getDifIter() ? 1 : c.getDifIter() == cgRet.getDifIter() ? 0 : -1;
						boolean masLejos = (c.getComando().getDestiny().getY() > cgRet.getComando().getDestiny().getY());
						if (dif == 1 || (dif == 0 && masLejos)) {
							cgRet = c;
						}
					}
				}
			}
		}
		return cgRet;
	}

	private HitBallDetail disparoAlPunto(final ShootingTrajectory shotInfo, final int idxPateador, final Position pFinal, final int idxReceptor, int maxIteraciones, double minIterDif) {
		if (shotInfo != null) {
			if (pFinal.getY() < 0) {
				minIterDif += 1;
			}
			maxIteraciones = Math.max(maxIteraciones, shotInfo.getIteraciones());
			final Ball balon = new Ball(sp.ballPosition(), sp.ballAltitude());
			final int minIter = idxPateador == idxReceptor || idxReceptor == -1 ? Constants.ITERACIONES_GOLPEAR_BALON : sp.iterationsToKick()[idxReceptor];
			final double angleXY = sp.ballPosition().angle(pFinal);
			final LinkedList<Ball> trayectoriaBalon = new LinkedList<Ball>();
			final int interAlcance = obtenerTrayectoriaCalculada(trayectoriaBalon, balon, shotInfo.getPotencia(), angleXY, shotInfo.getAngulo(), pFinal, true, idxReceptor, 100);
			if ((interAlcance <= maxIteraciones && interAlcance >= minIter) || (interAlcance >= 0 && idxReceptor == -1)) {
				final BallDestiny destino = getRivalBestLocated(trayectoriaBalon, false, idxPateador == idxReceptor);
				int iterRival = 1000;
				if (destino != null) {
					iterRival = destino.getIterJugador();
				}
				if (destino == null || (idxReceptor == -1 && destino.getIdxJugador() == analisys.getIdxRivalGoalkeeper()) || destino.getIterJugador() - interAlcance > minIterDif) {
					Position posFin = pFinal;
					if (trayectoriaBalon.size() > interAlcance) {
						posFin = trayectoriaBalon.get(interAlcance).getPosition();
					}
					double power = analisys.getPower(idxPateador, shotInfo.getPotencia());
					double ang = shotInfo.getAngulo() * TO_ANG;
					return new HitBallDetail(new CommandHitBall(idxPateador, posFin, power, ang), trayectoriaBalon, shotInfo.getAngulo(), interAlcance, iterRival, false, false);
				}
				return null;
			}
		}
		return null;
	}

	private ShootingTrajectory getPassOption(final int idxPlayer, final boolean banda, final double distancia, final double altFinal, final int minIteraciones) {
		ShootingTrajectory trayectoria = null;
		double maxPower = Constants.getVelocidadRemate(sp.getMyPlayerPower(idxPlayer));
		double factorReduccion = sp.getMyPlayerEnergy(idxPlayer);
		factorReduccion *= Constants.ENERGIA_DISPARO;
		if (factorReduccion > 1) {
			factorReduccion = 1;
		}
		maxPower *= factorReduccion;
		final double key = Analyze.round(distancia, 1);

		TreeSet<ShootingTrajectory> values = new TreeSet<ShootingTrajectory>();
		Double celingKey = analisys.getPassOptions().ceilingKey(key);
		if (celingKey != null && (celingKey.equals(key) || altFinal == 0)) {
			values.addAll(analisys.getPassOptions().ceilingEntry(key).getValue());
		}
		Double florrKey = analisys.getPassOptions().floorKey(key);
		if (florrKey != null && (celingKey == null || florrKey.doubleValue() != celingKey.doubleValue())) {
			values.addAll(analisys.getPassOptions().floorEntry(key).getValue());
		}
		int mejorOpcion = 100;
		for (final ShootingTrajectory opc : values) {
			if (altFinal == 0 && opc.getAngulo() > 0) {
				continue;
			}
			if (opc.getPotencia() <= maxPower && opc.isBanda() == banda && opc.getIteraciones() >= minIteraciones && opc.getIteraciones() < mejorOpcion) {
				mejorOpcion = opc.getIteraciones();
				trayectoria = new ShootingTrajectory(opc);
			}
		}
		return trayectoria;
	}

	private HitBallDetail mejorDisparoAlArco(final int idxPateador, final boolean obstaculo, final double minAngZ, final double maxAngZ) {
		final Ball balon = new Ball(sp.ballPosition(), sp.ballAltitude());
		final PlayerDetail pateador = detalle.getPlayers()[idxPateador];
		final double velocidadRemate = Constants.getVelocidadRemate(pateador.getPower());
		final double maxX = (Constants.LARGO_ARCO / 2) - Constants.RADIO_BALON;
		final LinkedList<Position> opciones = new LinkedList<Position>();
		final Position pInicial = Constants.centroArcoSup;
		opciones.add(pInicial);
		for (double x = 0.5; x < maxX; x += 0.5d) {
			opciones.add(pInicial.movePosition(x, 0));
			opciones.add(pInicial.movePosition(-x, 0));
		}
		for (final Position position : opciones) {
			final double angleXY = sp.ballPosition().angle(position);
			for (double fuerza = 1; fuerza > (maxAngZ > 0 ? 0.8 : 0.95); fuerza -= 0.05d) {
				fuerza = Analyze.round(fuerza, 2);
				final double potencia = fuerza * velocidadRemate;
				for (double angZ = minAngZ; angZ <= maxAngZ; angZ += 0.5) {
					final double angRadZ = Math.max(0, Math.min(angZ, Constants.ANGULO_VERTICAL_MAX)) * (TO_RAD);
					final LinkedList<Ball> trayectoriaBalon = new LinkedList<Ball>();
					final int iterAlcance = obtenerTrayectoriaCalculada(trayectoriaBalon, balon, potencia, angleXY, angRadZ, position, false, -1, 100);
					if (iterAlcance >= 0) {
						BallDestiny destino = null;
						if (obstaculo) {
							destino = getRivalBestLocated(trayectoriaBalon, false, false);
						}
						int iterRival = 1000;
						if (destino != null) {
							iterRival = destino.getIterJugador();
						}
						if (destino == null || destino.getIterJugador() > iterAlcance || (destino.getIdxJugador() == analisys.getIdxRivalGoalkeeper() && trayectoriaBalon.get(destino.getIterJugador()).getPosition().getY() > 52.5)) {
							return new HitBallDetail(new CommandHitBall(idxPateador, trayectoriaBalon.getLast().getPosition(), fuerza, angZ), trayectoriaBalon, angleXY, iterAlcance, iterRival, false, false);
						}
					}
				}
			}
		}
		return null;
	}
}
