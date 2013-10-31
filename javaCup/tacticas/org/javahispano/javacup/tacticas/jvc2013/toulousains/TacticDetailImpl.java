package org.javahispano.javacup.tacticas.jvc2013.toulousains;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

class TacticDetailImpl implements TacticDetail {

	@Override
	public String getTacticName() {
		return "Toulousains";
	}

	@Override
	public String getCountry() {
		return "Francia";
	}

	@Override
	public String getCoach() {
		return "Alacour";
	}

	@Override
	public Color getShirtColor() {
		return new Color(0, 0, 0);
	}

	@Override
	public Color getShortsColor() {
		return new Color(0, 0, 0);
	}

	@Override
	public Color getShirtLineColor() {
		return new Color(255, 0, 0);
	}

	@Override
	public Color getSocksColor() {
		return new Color(0, 0, 0);
	}

	@Override
	public Color getGoalKeeper() {
		return new Color(255, 255, 255);
	}

	@Override
	public EstiloUniforme getStyle() {
		return EstiloUniforme.LINEAS_HORIZONTALES;
	}

	@Override
	public Color getShirtColor2() {
		return new Color(7, 93, 1);
	}

	@Override
	public Color getShortsColor2() {
		return new Color(59, 97, 31);
	}

	@Override
	public Color getShirtLineColor2() {
		return new Color(203, 101, 187);
	}

	@Override
	public Color getSocksColor2() {
		return new Color(162, 233, 184);
	}

	@Override
	public Color getGoalKeeper2() {
		return new Color(89, 61, 180);
	}

	@Override
	public EstiloUniforme getStyle2() {
		return EstiloUniforme.FRANJA_VERTICAL;
	}

	class JugadorImpl implements PlayerDetail {

		String nombre;
		int numero;
		Color piel, pelo;
		double velocidad, remate, presicion;
		boolean portero;
		Position Position;

		public JugadorImpl(String nombre, int numero, Color piel, Color pelo, double velocidad, double remate, double presicion,
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
				new JugadorImpl("Pierre", 1, new Color(255, 200, 150), new Color(204, 204, 0), 1.0d, 1.0d, 0.0d, true),
				new JugadorImpl("Michael", 2, new Color(255, 200, 150), new Color(204, 153, 0), 1.0d, 1.0d, 0.0d, false),
				new JugadorImpl("Nicolas", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 0.0d, false),
				new JugadorImpl("Gildas", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 0.0d, false),
				new JugadorImpl("Remy", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.54d, 0.0d, false),
				new JugadorImpl("Jerome", 6, new Color(255, 200, 150), new Color(255, 255, 0), 1.0d, 0.96d, 1.0d, false),
				new JugadorImpl("Thomas", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
				new JugadorImpl("Fred", 8, new Color(255, 200, 150), new Color(255, 204, 153), 1.0d, 1.0d, 1.0d, false),
				new JugadorImpl("Emile", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
				new JugadorImpl("Javier", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
				new JugadorImpl("Damien", 11, new Color(255, 200, 150), new Color(255, 102, 0), 1.0d, 1.0d, 1.0d, false) };
	}
}
