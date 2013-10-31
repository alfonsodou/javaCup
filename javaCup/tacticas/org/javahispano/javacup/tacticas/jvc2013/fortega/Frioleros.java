package org.javahispano.javacup.tacticas.jvc2013.fortega;


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
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class Frioleros implements Tactic {
	// Normal
	Position alineacion1[] = new Position[] { new Position(0.20, -50.40),
			new Position(-15, -30), new Position(15, -30),
			new Position(25, -2), new Position(8, -11), new Position(-8, -11),
			new Position(-25, -2), new Position(9, 12), new Position(-9, 12),
			new Position(-5, 25), new Position(0, 32) };

	// Posesion en saque
	Position alineacion2[] = new Position[] { new Position(0.20, -50.40),
			new Position(-15, -30), new Position(15, -30),
			new Position(25, -2), new Position(8, -11), new Position(-8, -11),
			new Position(-25, -2), new Position(9, -1), new Position(-9, -1),
			new Position(-1, -1), new Position(13, -1) };

	// Sin posesion en saque
	Position alineacion3[] = new Position[] { new Position(0.20, -50.40),
			new Position(-15, -30), new Position(15, -30),
			new Position(25, -2), new Position(8, -11), new Position(-8, -11),
			new Position(-25, -2), new Position(9, 12), new Position(-9, -1),
			new Position(-13, -1), new Position(13, -1) };

	// Defensa
	Position alineacion4[] = new Position[] { new Position(0.20, -50.40),
			new Position(-11, -45), new Position(11, -45),
			new Position(22, -32), new Position(8, -36), new Position(-8, -36),
			new Position(-22, -32), new Position(10, -12),
			new Position(-7, -12), new Position(-8, 20), new Position(1, 25) };

	class TacticDetailImpl implements TacticDetail {

		public String getTacticName() {
			return "Frioleros";
		}

		public String getCountry() {
			return "Groenlandia";
		}

		public String getCoach() {
			return "Sr Ortega";
		}

		public Color getShirtColor() {
			return new Color(0, 0, 0);
		}

		public Color getShortsColor() {
			return new Color(0, 0, 0);
		}

		public Color getShirtLineColor() {
			return new Color(255, 255, 0);
		}

		public Color getSocksColor() {
			return new Color(0, 0, 0);
		}

		public Color getGoalKeeper() {
			return new Color(255, 255, 0);
		}

		public EstiloUniforme getStyle() {
			return EstiloUniforme.SIN_ESTILO;
		}

		public Color getShirtColor2() {
			return new Color(255, 255, 51);
		}

		public Color getShortsColor2() {
			return new Color(0, 0, 0);
		}

		public Color getShirtLineColor2() {
			return new Color(255, 255, 0);
		}

		public Color getSocksColor2() {
			return new Color(0, 0, 0);
		}

		public Color getGoalKeeper2() {
			return new Color(0, 0, 0);
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

			public JugadorImpl(String nombre, int numero, Color piel,
					Color pelo, double velocidad, double remate,
					double presicion, boolean portero) {
				this.nombre = nombre;
				this.numero = numero;
				this.piel = piel;
				this.pelo = pelo;
				this.velocidad = velocidad;
				this.remate = remate;
				this.presicion = presicion;
				this.portero = portero;
			}

			public String getPlayerName() {
				return nombre;
			}

			public Color getSkinColor() {
				return piel;
			}

			public Color getHairColor() {
				return pelo;
			}

			public int getNumber() {
				return numero;
			}

			public boolean isGoalKeeper() {
				return portero;
			}

			public double getSpeed() {
				return velocidad;
			}

			public double getPower() {
				return remate;
			}

			public double getPrecision() {
				return presicion;
			}
		}

		public PlayerDetail[] getPlayers() {
			return new PlayerDetail[] {
					new JugadorImpl("Eustoquio", 1, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, true),
					new JugadorImpl("Atanasio", 2, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.5d, false),
					new JugadorImpl("Ambrosio", 3, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Gaudencio", 4, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Evaristo", 5, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Prudencio", 6, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Segismundo", 7, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 0.0d, false),
					new JugadorImpl("Eufrasio", 8, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false),
					new JugadorImpl("Gaudencio", 9, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false),
					new JugadorImpl("Ermenegildo", 10, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false),
					new JugadorImpl("Juanjo", 11, new Color(51, 51, 51),
							new Color(0, 0, 0), 1.0d, 1.0d, 1.0d, false) };
		}
	}

	TacticDetail detalle = new TacticDetailImpl();

	public TacticDetail getDetail() {
		return detalle;
	}

	public Position[] getStartPositions(GameSituations sp) {
		return alineacion2;
	}

	public Position[] getNoStartPositions(GameSituations sp) {
		return alineacion3;
	}

	LinkedList<Command> comandos = new LinkedList<Command>();

	@Override
	public List<Command> execute(GameSituations sp) {
		// Variables
		comandos.clear();
		Random r = new Random();
		Position[] jugadores = sp.myPlayers();
		int fuerza = 45;
		Position apunta = new Position();

		// Control de fuerza y punteria
		if (sp.ballPosition().getY() > -50 && sp.ballPosition().getY() < -20) {
			fuerza = 35;
			if (r.nextBoolean()) {
				apunta = Constants.posteIzqArcoSup;
			} else {
				apunta = Constants.posteDerArcoSup;
			}
		}

		if (sp.ballPosition().getY() > -20 && sp.ballPosition().getY() < 0) {
			fuerza = 30;
			if (r.nextBoolean()) {
				apunta = Constants.posteIzqArcoSup;
			} else {
				apunta = Constants.posteDerArcoSup;
			}
		}

		if (sp.ballPosition().getY() == 0.0 && sp.ballPosition().getX() == 0.0) {
			fuerza = 10;
			if (r.nextBoolean()) {
				apunta = Constants.posteIzqArcoSup;
			} else {
				apunta = Constants.posteDerArcoSup;
			}
		}
		if (sp.ballPosition().getY() > 0 && sp.ballPosition().getY() < 10) {
			fuerza = 20;
			if (r.nextBoolean()) {
				apunta = Constants.posteIzqArcoSup;
			} else {
				apunta = Constants.posteDerArcoSup;
			}
		}
		if (sp.ballPosition().getY() < 20 && sp.ballPosition().getY() > 10) {
			fuerza = 15;
			apunta = Constants.centroArcoSup;
		}

		if (sp.ballPosition().getY() < 30 && sp.ballPosition().getY() > 20) {
			fuerza = 10;
			apunta = Constants.centroArcoSup;

		}
		if (sp.ballPosition().getY() < 40 && sp.ballPosition().getY() > 30) {
			fuerza = 8;
			apunta = Constants.centroArcoSup;
		}
		if (sp.ballPosition().getY() < 50 && sp.ballPosition().getY() > 40) {
			fuerza = 5;
			apunta = Constants.centroArcoSup;
		}

		// Defensa o ataque
		if (sp.ballPosition().getY() > -5) {
			for (int i = 1; i < jugadores.length; i++) {
				comandos.add(new CommandMoveTo(i, alineacion1[i]));
			}
		} else {
			for (int i = 0; i < jugadores.length; i++) {
				comandos.add(new CommandMoveTo(i, alineacion4[i]));
			}
		}

		// Defender el balon
		if (sp.rivalCanKick().length > 0) {
			for (int i = 0; i < jugadores.length; i++) {
				comandos.add(new CommandMoveTo(i, sp.ballPosition()));
			}
		}
		// Defender la posicion
		if (!sp.isRivalStarts()) {
			@SuppressWarnings("deprecation")
			int[] recuperadores = sp.getRecoveryBall();
			if (recuperadores.length > 0) {
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				for (int i = 1; i < recuperadores.length; i++) {
					comandos.add(new CommandMoveTo(
							recuperadores[i],
							new Position(posRecuperacion[0], posRecuperacion[1])));
				}
			} else {
				for (int i = 1; i < jugadores.length; i++) {
					comandos.add(new CommandMoveTo(i, alineacion4[i]));
				}
			}
		}
		// Disparo a puerta
		if (sp.canKick().length > 0) {
			// Regate
			if (r.nextBoolean()) {
				for (int i = 0; i < jugadores.length; i++) {
					comandos.add(new CommandHitBall(0));
				}
				// Tiro
			} else
				for (int i = 0; i < jugadores.length; i++) {
					comandos.add(new CommandHitBall(i, apunta, 1, fuerza));
				}
		}

		return comandos;
	}
}
