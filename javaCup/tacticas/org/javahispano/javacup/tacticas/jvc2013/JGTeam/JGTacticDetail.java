package org.javahispano.javacup.tacticas.jvc2013.JGTeam;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class JGTacticDetail implements TacticDetail {

	public String getTacticName() {
		return "JGTeam";
	}

	public String getCountry() {
		return "España";
	}

	public String getCoach() {
		return "JG";
	}

	public Color getShirtColor() {
		return new Color(141, 229, 139);
	}

	public Color getShortsColor() {
		return new Color(20, 206, 121);
	}

	public Color getShirtLineColor() {
		return new Color(199, 158, 130);
	}

	public Color getSocksColor() {
		return new Color(150, 254, 58);
	}

	public Color getGoalKeeper() {
		return new Color(22, 133, 172);
	}

	public EstiloUniforme getStyle() {
		return EstiloUniforme.FRANJA_HORIZONTAL;
	}

	public Color getShirtColor2() {
		return new Color(22, 22, 13);
	}

	public Color getShortsColor2() {
		return new Color(206, 227, 31);
	}

	public Color getShirtLineColor2() {
		return new Color(158, 167, 45);
	}

	public Color getSocksColor2() {
		return new Color(160, 188, 242);
	}

	public Color getGoalKeeper2() {
		return new Color(144, 85, 112);
	}

	public EstiloUniforme getStyle2() {
		return EstiloUniforme.FRANJA_HORIZONTAL;
	}

	class JugadorImpl implements PlayerDetail {

		String nombre;
		int numero;
		Color piel, pelo;
		double velocidad, remate, presicion;
		boolean portero;
		Position Position;

		public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
				double velocidad, double remate, double presicion,
				boolean portero) {
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
				new JugadorImpl("Jugador", 1, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 0.0d, true),
				new JugadorImpl("Jugador", 2, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 0.58d, 1.0d, false),
				new JugadorImpl("Jugador", 3, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 0.53d, 0.53d, false),
				new JugadorImpl("Jugador", 4, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 0.54d, 0.55d, false),
				new JugadorImpl("Jugador", 5, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 0.5d, false),
				new JugadorImpl("Jugador", 6, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 0.57d, false),
				new JugadorImpl("Jugador", 7, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 0.57d, false),
				new JugadorImpl("Jugador", 8, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 0.54d, false),
				new JugadorImpl("Jugador", 9, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 0.55d, false),
				new JugadorImpl("Jugador", 10, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
				new JugadorImpl("Jugador", 11, new Color(255, 200, 150),
						new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false) };
	}
}
