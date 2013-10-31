package org.javahispano.javacup.tacticas.jvc2013.twentythree;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class Team2313 implements Tactic {

	double distanciaMarca = 0;
	public static final double RAD = Math.PI / 180;

	public Team2313(final double distanciaMarca) {
		this.distanciaMarca = distanciaMarca;
	}

	public Team2313() {
		this.distanciaMarca = 0.98;
	}

	Position alineacion1[] = new Position[] { new Position(0.2595419847328244, -50.41044776119403),
			new Position(-0.951048951048951, -10.402714932126697),
			new Position(17.832167832167833, -10.402714932126697),
			new Position(26.391608391608393, -10.64027149321267),
			new Position(31.622377622377623, -30.031674208144796),
			new Position(9.034965034965035, -10.402714932126697),
			new Position(-9.034965034965035, -10.402714932126697),
			new Position(-17.832167832167833, -10.165158371040723),
			new Position(-26.153846153846157, -10.927601809954751),
			new Position(-31.384615384615387, -30.269230769230766), new Position(0.0, -25.855203619909503) };

	Position alineacion2[] = new Position[] { new Position(0.2595419847328244, -50.41044776119403),
			new Position(-0.951048951048951, -10.402714932126697),
			new Position(17.832167832167833, -10.402714932126697),
			new Position(26.391608391608393, -10.64027149321267),
			new Position(-26.153846153846157, -10.927601809954751),
			new Position(9.034965034965035, -10.402714932126697),
			new Position(-9.034965034965035, -10.402714932126697),
			new Position(-17.832167832167833, -10.165158371040723),
			new Position(31.622377622377623, -30.031674208144796),
			new Position(-31.384615384615387, -30.269230769230766), new Position(0.0, -25.855203619909503) };

	Position alineacion3[] = new Position[] { new Position(0.2595419847328244, -50.41044776119403),
			new Position(-0.951048951048951, -10.402714932126697),
			new Position(17.832167832167833, -10.402714932126697),
			new Position(26.391608391608393, -10.64027149321267),
			new Position(-26.153846153846157, -10.927601809954751),
			new Position(9.034965034965035, -10.402714932126697),
			new Position(-9.034965034965035, -10.402714932126697),
			new Position(-17.832167832167833, -10.165158371040723),
			new Position(31.622377622377623, -30.031674208144796),
			new Position(-31.384615384615387, -30.269230769230766), new Position(0.0, -25.855203619909503) };

	Position alineacion4[] = new Position[] { new Position(0.2595419847328244, -50.41044776119403),
			new Position(-0.951048951048951, -10.402714932126697),
			new Position(17.832167832167833, -10.402714932126697),
			new Position(26.391608391608393, -10.64027149321267),
			new Position(-26.153846153846157, -10.927601809954751),
			new Position(9.034965034965035, -10.402714932126697),
			new Position(-9.034965034965035, -10.402714932126697),
			new Position(-17.832167832167833, -10.165158371040723),
			new Position(31.622377622377623, -30.031674208144796),
			new Position(-31.384615384615387, -30.269230769230766), new Position(0.0, -25.855203619909503) };

	Position alineacion5[] = new Position[] { new Position(0.2595419847328244, -50.41044776119403),
			new Position(-0.951048951048951, -10.402714932126697),
			new Position(17.832167832167833, -10.402714932126697),
			new Position(26.391608391608393, -10.64027149321267),
			new Position(-26.153846153846157, -10.927601809954751),
			new Position(9.034965034965035, -10.402714932126697),
			new Position(-9.034965034965035, -10.402714932126697),
			new Position(-17.832167832167833, -10.165158371040723),
			new Position(31.622377622377623, -30.031674208144796), new Position(0, 0),
			new Position(0.0, -25.855203619909503) };

	Position alineacion6[] = new Position[] { new Position(0.2595419847328244, -50.41044776119403),
			new Position(-0.951048951048951, -10.402714932126697),
			new Position(17.832167832167833, -10.402714932126697),
			new Position(26.391608391608393, -10.64027149321267),
			new Position(-26.153846153846157, -10.927601809954751),
			new Position(9.034965034965035, -10.402714932126697),
			new Position(-9.034965034965035, -10.402714932126697),
			new Position(-17.832167832167833, -10.165158371040723),
			new Position(31.622377622377623, -30.031674208144796),
			new Position(-31.384615384615387, -30.269230769230766), new Position(0.0, -25.855203619909503) };

	class TacticDetailImpl implements TacticDetail {

		@Override
		public String getTacticName() {
			return "TwentyThree FC";
		}

		@Override
		public String getCountry() {
			return "Colombia";
		}

		@Override
		public String getCoach() {
			return "Willbender";
		}

		public Color getShirtColor() {
			return new Color(255, 0, 51);
		}

		public Color getShortsColor() {
			return new Color(0, 0, 204);
		}

		public Color getShirtLineColor() {
			return new Color(255, 255, 0);
		}

		public Color getSocksColor() {
			return new Color(255, 0, 51);
		}

		public Color getGoalKeeper() {
			return new Color(102, 0, 102);
		}

		public EstiloUniforme getStyle() {
			return EstiloUniforme.SIN_ESTILO;
		}

		public Color getShirtColor2() {
			return new Color(255, 255, 0);
		}

		public Color getShortsColor2() {
			return new Color(0, 0, 204);
		}

		public Color getShirtLineColor2() {
			return new Color(0, 0, 153);
		}

		public Color getSocksColor2() {
			return new Color(255, 255, 255);
		}

		public Color getGoalKeeper2() {
			return new Color(153, 255, 0);
		}

		public EstiloUniforme getStyle2() {
			return EstiloUniforme.SIN_ESTILO;
		}

		class JugadorImpl implements PlayerDetail {

			String nombre;
			int numero;
			Color piel, pelo;
			double velocidad, remate, presicion;
			boolean portero;
			Position Position;

			public JugadorImpl(final String nombre, final int numero, final Color piel, final Color pelo,
					final double velocidad, final double remate, final double presicion, final boolean portero) {
				this.nombre = nombre;
				this.numero = numero;
				this.piel = piel;
				this.pelo = pelo;
				this.velocidad = velocidad;
				this.remate = remate;
				this.presicion = presicion;
				this.portero = portero;
			}

			@Override
			public String getPlayerName() {
				return nombre;
			}

			@Override
			public Color getSkinColor() {
				return piel;
			}

			@Override
			public Color getHairColor() {
				return pelo;
			}

			@Override
			public int getNumber() {
				return numero;
			}

			@Override
			public boolean isGoalKeeper() {
				return portero;
			}

			@Override
			public double getSpeed() {
				return velocidad;
			}

			@Override
			public double getPower() {
				return remate;
			}

			@Override
			public double getPrecision() {
				return presicion;
			}

		}

		@Override
		public PlayerDetail[] getPlayers() {
			return new PlayerDetail[] {
					new JugadorImpl("23", 1, new Color(255, 200, 150), new Color(255, 255, 255), 1.0d, 0.72d, 0.5d,
							true),
					new JugadorImpl("23", 2, new Color(255, 200, 150), new Color(255, 255, 0), 1.0d, 0.67d, 0.5d, false),
					new JugadorImpl("23", 3, new Color(255, 200, 150), new Color(0, 0, 153), 1.0d, 0.67d, 0.5d, false),
					new JugadorImpl("23", 4, new Color(255, 200, 150), new Color(255, 0, 0), 1.0d, 0.67d, 0.5d, false),
					new JugadorImpl("23", 5, new Color(255, 200, 150), new Color(255, 255, 51), 1.0d, 0.67d, 0.5d,
							false),
					new JugadorImpl("23", 6, new Color(255, 200, 150), new Color(0, 0, 204), 1.0d, 1.0d, 0.5d, false),
					new JugadorImpl("23", 7, new Color(255, 200, 150), new Color(255, 0, 0), 1.0d, 1.0d, 0.5d, false),
					new JugadorImpl("23", 8, new Color(255, 200, 150), new Color(255, 255, 0), 1.0d, 0.8d, 1.0d, false),
					new JugadorImpl("23", 9, new Color(255, 200, 150), new Color(0, 0, 204), 1.0d, 0.8d, 1.0d, false),
					new JugadorImpl("23", 23, new Color(255, 204, 153), new Color(255, 255, 255), 1.0d, 1.0d, 1.0d,
							false),
					new JugadorImpl("23", 11, new Color(255, 200, 150), new Color(255, 0, 0), 1.0d, 1.0d, 1.0d, false) };
		}
	}

	TacticDetail detalle = new TacticDetailImpl();

	@Override
	public TacticDetail getDetail() {
		return detalle;
	}

	@Override
	public Position[] getStartPositions(final GameSituations sp) {
		return alineacion5;
	}

	@Override
	public Position[] getNoStartPositions(final GameSituations sp) {
		return alineacion6;
	}

	ArrayList<Command> comandos = new ArrayList<Command>();
	Random r = new Random();
	Util23 util23 = null;
	HistorialMarcas historialMarcas = new HistorialMarcas();
	HistorialBalon historialBalon = new HistorialBalon();
	Position balonPasado = new Position();
	int iteracionesSaque = 0;

	@Override
	public List<Command> execute(final GameSituations sp) {
		comandos.clear();
		final List<Command> com = new ArrayList<Command>();
		if (sp.iteration() > 0) {
			new Random().nextDouble();
			historialMarcas.addMarcasRivalIteracionActual(sp);
			historialBalon.addPosicionBalonIteracionActual(sp);
			if (sp.iteration() == 1) {
				util23 = new Util23(sp);
			} else {
				util23.reasignarMarcas(sp);
			}
			final int rivalMasCercano = sp.ballPosition().nearestIndex(sp.rivalPlayers());
			final int rivalMasPeligro = Constants.centroArcoInf.nearestIndex(sp.rivalPlayers());
			Position pRivalMasCercano = sp.rivalPlayers()[rivalMasCercano];
			final Collection<Integer> rivales = util23.getMarcas().keySet();
			for (final Integer rival : rivales) {
				final Integer marcador = util23.getMarcas().get(rival);
				if (marcador != -1) {
					final CommandMoveTo marcar = Helper.marcar(marcador, rival, rivalMasCercano,
							historialBalon.aMiArco(), historialMarcas, sp, util23, rivalMasPeligro);
					com.add(marcar);
				}
			}
			if (sp.rivalPlayersDetail()[0].getPlayerName().equalsIgnoreCase("Ospina")
					|| sp.rivalPlayersDetail()[0].getPlayerName().equalsIgnoreCase("0")) {
				com.add(new CommandMoveTo(4, sp.rivalPlayers()[8].moveAngle(
						sp.rivalPlayers()[8].angle(sp.ballPosition()), 0.75)));
				com.add(new CommandMoveTo(10, sp.rivalPlayers()[10].moveAngle(
						sp.rivalPlayers()[10].angle(sp.ballPosition()), 0.75)));
				
			}

			for (final int i : sp.canKick()) {
				final CommandHitBall cg = mejorDisparoAlArco(i, 10, Constants.ANGULO_VERTICAL_MAX, sp);
				final Position posKick = sp.myPlayers()[i];
				Integer rivalMarca = null;
				for (Iterator<Integer> iterator = util23.getMarcas().keySet().iterator(); iterator.hasNext();) {
					Integer riv = iterator.next();
					if (util23.getMarcas().get(riv) == i) {
						rivalMarca = riv;
						break;
					}
				}
				Position posMiMarca = null;
				if (rivalMarca != null) {
					posMiMarca = sp.rivalPlayers()[rivalMarca];
				}
				final double distanciaArco = posKick.distance(Constants.centroArcoSup);
				if (sp.isStarts() && iteracionesSaque >= Constants.ITERACIONES_SAQUE / 2) {
					if (cg != null && distanciaArco > Constants.ANCHO_AREA_GRANDE) {
						com.add(cg);
					} else {
						if (sp.ballPosition().distance(Constants.centroArcoSup) > 40) {
							Position posDisparo = Constants.centroArcoSup;
							com.add(new CommandHitBall(i, posDisparo, 1.0, Constants.ANGULO_VERTICAL));
						} else {
							com.add(new CommandHitBall(i, Helper.obtenerPuntoMejorDisparoArco(sp), 1.0,
									Constants.ANGULO_VERTICAL * r.nextDouble()));
						}
					}
					iteracionesSaque = 0;
				}
				if (!sp.isStarts()) {
					if (cg != null && distanciaArco > Constants.ANCHO_AREA_GRANDE
							&& distanciaArco < Constants.LARGO_CAMPO_JUEGO / 2) {
						com.add(cg);
					} else {
						if (sp.ballPosition().distance(Constants.centroArcoSup) > 40) {
							Position posDisparo = Constants.centroArcoSup;
							double anguloDisparo = Constants.ANGULO_VERTICAL;
							Position miPosicion = sp.myPlayers()[i];
							if (posMiMarca != null && posMiMarca.distance(miPosicion) < 1) {
								int[] pases = miPosicion.nearestIndexes(sp.myPlayers());
								boolean pase = false;
								if (posMiMarca.getX() > miPosicion.getX()) {
									for (int x = 0; x < 3; x++) {
										if (sp.myPlayers()[pases[x]].getX() >= posMiMarca.getX()) {
											pase = true;
											posDisparo = sp.myPlayers()[pases[x]];
											anguloDisparo = 0;
											break;
										}
									}
								} else {
									for (int x = 0; x < 3; x++) {
										if (sp.myPlayers()[pases[x]].getX() <= posMiMarca.getX()) {
											pase = true;
											posDisparo = sp.myPlayers()[pases[x]];
											anguloDisparo = 0;
											break;
										}
									}
								}
								if (!pase) {
									posDisparo = Constants.centroArcoSup;
									anguloDisparo = Constants.ANGULO_VERTICAL_MAX;
								}

							}
							com.add(new CommandHitBall(i, posDisparo, 1.0, anguloDisparo));
						} else {
							com.add(new CommandHitBall(i, Helper.obtenerPuntoMejorDisparoArco(sp), 1.0,
									Constants.ANGULO_VERTICAL * r.nextDouble()));
						}
					}
				} else {
					if (sp.myPlayers()[i].distance(Constants.centroArcoSup) < Constants.LARGO_AREA_CHICA) {
						com.add(new CommandHitBall(i, Constants.centroArcoSup, 0.5, false));
					}
				}
			}
			Position pMiArquero = sp.myPlayers()[0];
			Position posArquero = Helper.getPosicionArquero(sp.ballPosition(), pRivalMasCercano, pMiArquero,
					sp.ballAltitude(), balonPasado);
			com.add(new CommandMoveTo(0, posArquero,true));

			if (sp.isStarts()) {
				final int mj = sp.ballPosition().nearestIndex(sp.myPlayers(), 0);
				if (mj == 0) {
					posArquero = sp.ballPosition();
				}
				com.add(new CommandMoveTo(mj, sp.ballPosition()));
				if (iteracionesSaque < Constants.ITERACIONES_SAQUE / 2) {
					iteracionesSaque++;
				}
			}
			balonPasado = sp.ballPosition().setInsideGameField();

			final ArrayList<CommandMoveTo> listaBorrar = new ArrayList<CommandMoveTo>();
			for (final Command c : com) {
				if (c instanceof CommandMoveTo) {
					final CommandMoveTo cmd = (CommandMoveTo) c;
					if (cmd.getPlayerIndex() != 0 && cmd.getMoveTo().equals(posArquero)) {
						listaBorrar.add(cmd);
					}// estas siguientes lineas de cambio
					else if (cmd.getPlayerIndex() != 0
							&& cmd.getMoveTo().distance(sp.myPlayers()[cmd.getPlayerIndex()]) < 0.4) {
						listaBorrar.add(cmd);
					}
				}
			}
			com.removeAll(listaBorrar);
		}
		return com;
	}

	@SuppressWarnings("unused")
	private CommandHitBall mejorDisparoAlArco(final int idxPateador, final double minAngZ, final double maxAngZ,
			final GameSituations sp) {
		int idxPortRival = 0;
		for (int i = 0; i < sp.rivalPlayersDetail().length; i++) {
			if (sp.rivalPlayersDetail()[i].isGoalKeeper()) {
				idxPortRival = i;
				break;
			}
		}
		// datos iniciales del balon
		final Ball balon = new Ball(sp.ballPosition(), sp.ballAltitude());
		// informacion del pateador
		final PlayerDetail pateador = detalle.getPlayers()[idxPateador];

		final double velocidadRemate = Constants.getVelocidadRemate(pateador.getPower());
		final double maxX = (Constants.LARGO_ARCO / 2);
		final LinkedList<Position> opciones = new LinkedList<Position>();
		final Position pInicial = Constants.centroArcoSup;

		opciones.add(pInicial);
		for (double x = 0.5; x < maxX; x += 0.5d) {
			opciones.add(pInicial.movePosition(x, 0));
			opciones.add(pInicial.movePosition(-x, 0));
		}
		for (final Position position : opciones) {
			final double angleXY = sp.ballPosition().angle(position);
			// se prueban diferentes opciones de fuerza
			for (double fuerza = 1; fuerza > 0.6; fuerza -= 0.1d) {
				// potecia obtenida dependiendo de la fuerza aplicada
				final double potencia = fuerza * velocidadRemate * (sp.isStarts() ? 0.75 : 1);
				// se prueban diferentes opciones de angulos
				for (double angZ = minAngZ; angZ <= maxAngZ; angZ += 0.5) {
					// angulo vertical en radianes
					final double angRadZ = Math.max(0, Math.min(angZ, Constants.ANGULO_VERTICAL_MAX)) * (RAD);
					// si el tiro es preciso al punto calculado
					final LinkedList<Ball> trayectoriaBalon = new LinkedList<Ball>();
					// se obtiene la trayectoria del balon al punto
					final int iteraciones = obtenerTrayectoriaCalculada(trayectoriaBalon, balon, potencia, angleXY,
							angRadZ, position, false, -1, 100, sp);
					// si el metodo retorna mas de una iteracion, es pq se
					// alcanza el punto deseado
					if (iteraciones >= 0) {
						final BallDestiny destino = null;
						/*
						 * if (obstaculo) { destino =
						 * obtenerRivalMejorUbicado(trayectoriaBalon, false); }
						 */
						int idxRival = position.nearestIndex(sp.rivalPlayers());
						if (destino != null) {
							idxRival = destino.getIdxJugador();
						}
						// distancia del rival al punto del pase
						final double distRivAlPunto = sp.rivalPlayers()[idxRival].distance(position);
						int iterRival = (int) Math.ceil(distRivAlPunto
								/ Constants.getVelocidad(sp.rivalPlayersDetail()[idxRival].getSpeed()));
						if (destino != null) {
							idxRival = destino.getIdxJugador();
							iterRival = destino.getIterJugador();
						}
						// si "nadie" intercepta el pase
						if (destino == null || (destino.getIterJugador() > iteraciones)
								|| (angZ > 0 && destino.getIdxJugador() == idxPortRival)) {
							final double distJugadorAlPunto = position.distance(sp.ballPosition());
							// si se trata de un disparo a meta
							return new CommandHitBall(idxPateador, trayectoriaBalon.getLast().getPosition(), fuerza,
									angZ);
						}
					}
				}
			}
		}
		return null;
	}

	private int obtenerTrayectoriaCalculada(final LinkedList<Ball> trayectoriaBalon, final Ball balon,
			final double potencia, final double angleXY, final double angleZ, final Position pFinal,
			final boolean completa, final int idxReceptor, final int maxPasos, final GameSituations sp) {
		trayectoriaBalon.clear();
		balon.setIteracion(0);
		trayectoriaBalon.add(balon);
		final AbstractTrajectory trajectory = new AirTrajectory(Math.cos(angleZ) * potencia, Math.sin(angleZ)
				* potencia, 0, 0);

		final double[] datosControl = new double[] { Constants.DISTANCIA_CONTROL_BALON_PORTERO, Constants.ALTO_ARCO };

		// final int iteracionesControl = idxReceptor == -1 ?
		// sp.rivalIterationsToKick()[idxPortRival] :
		// Constants.ITERACIONES_GOLPEAR_BALON;
		double distanciaAPunto = 1000d;
		int iteracionesAlcance = -1;
		boolean elevando = true;
		double oldtrY = 0d;
		boolean dzAntPosivivo = true;
		double dxAnt = 0d;
		Ball b0 = null;
		// si el balon se encuentra en el centro del campo, se dispara a meta
		// esperando a que sea gol
		final boolean centro = sp.ballPosition().distance(Constants.centroCampoJuego) == 0;
		for (int step = 1; step <= maxPasos; step++) {

			final double time = step / 60d;
			final double dX = trajectory.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
			final double dY = trajectory.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
			final double x = balon.getPosition().getX() + dX * Math.cos(angleXY);
			final double y = balon.getPosition().getY() + dX * Math.sin(angleXY);
			final Ball nuevoBalon = new Ball(x, y, dY, step);
			elevando = oldtrY < dY;
			// distancia del balon al punto final deseado
			distanciaAPunto = nuevoBalon.getPosition().setInsideGameField().distance(pFinal);
			// si el balon no se est� elevando se asume que lleg� a su altura
			// maxima
			if (!elevando) {
				dzAntPosivivo = false;
				if (iteracionesAlcance == -1) {
					// si es un tiro al arco, la idea es que el arquero no la
					// alcance
					if (idxReceptor == -1 && !trayectoriaBalon.isEmpty()) {
						if (angleZ > 0) {
							final Ball balonAnterior = trayectoriaBalon.getLast();
							// se verifica que el balon anterior ente en el
							// campo y el actual no
							final boolean antAdentro = balonAnterior.getAltura() > Constants.ALTO_ARCO
									&& balonAnterior.getPosition().getY() <= (Constants.LARGO_CAMPO_JUEGO / 2);
							final boolean actAfuera = nuevoBalon.getAltura() < Constants.ALTO_ARCO
									&& nuevoBalon.getPosition().getY() > (Constants.LARGO_CAMPO_JUEGO / 2);
							if (antAdentro && actAfuera) {

								final double deltaX = balonAnterior.getPosition().distance(nuevoBalon.getPosition());

								// se determina la altura en la linea de meta
								final double deltaY = balonAnterior.getAltura() - nuevoBalon.getAltura();
								final double h = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
								// arc cos[(b� + c� - a�)/2bc]
								final double teta = Math.acos((deltaX * deltaX + h * h - deltaY * deltaY)
										/ (2 * deltaX * h));
								// tan (teta)=opuesto/adyacente
								final double nuevoX = deltaX
										- ((Constants.LARGO_CAMPO_JUEGO / 2) - balonAnterior.getPosition().getY());
								final double alturaMeta = nuevoBalon.getAltura() + (Math.tan(teta) * nuevoX);
								// si la altura en linea de meta es aceptable
								if (alturaMeta <= Constants.ALTO_ARCO
										&& (nuevoBalon.getAltura() - (balonAnterior.getAltura() - b0.getAltura()) <= Constants.ALTO_ARCO || centro)) {
									trayectoriaBalon.add(nuevoBalon);
									return step;
								}
							}
						} else if (nuevoBalon.getPosition().getY() >= Constants.LARGO_CAMPO_JUEGO / 2
								&& Math.abs(nuevoBalon.getPosition().getX()) <= Constants.LARGO_ARCO) {
							trayectoriaBalon.add(nuevoBalon);
							return step;
						}
					}
					// en caso de un pase a un jugador
					else if (iteracionesAlcance == -1 && distanciaAPunto <= datosControl[0]
							&& (angleZ == 0 || (dY <= datosControl[1]))) {
						iteracionesAlcance = Math.max(step, Constants.ITERACIONES_GOLPEAR_BALON);
						// si no se quiere la ruta completa y se alcanz� el
						// balon en algun punto, se retorna
						if (!completa) {
							trayectoriaBalon.add(nuevoBalon);
							return iteracionesAlcance;
						}
					}
				}
			}
			// si el balon se est� elevando y ya toc� el piso, se trata de un
			// rebote
			if (!completa && iteracionesAlcance == -1
					&& (angleZ > 0 && ((elevando && !dzAntPosivivo) || (!elevando && dY < datosControl[1])))) {
				return iteracionesAlcance;
			}
			// si el balon se detuvo o sale del campo, se retorna
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

}