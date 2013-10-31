package org.javahispano.javacup.tacticas.jvc2013.romedal;


import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class DetalleTactica implements TacticDetail, Alineaciones {

	String	nombre	= "RomedalTeam";

	public DetalleTactica(final String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String getTacticName() {
		return nombre;
	}

	@Override
	public String getCountry() {
		return "Colombia";
	}

	@Override
	public String getCoach() {
		return "Roland Cruz";
	}

	public Color getShirtColor() {
		return new Color(255, 255, 0);
	}

	public Color getShortsColor() {
		return new Color(0, 0, 204);
	}

	public Color getShirtLineColor() {
		return new Color(0, 0, 0);
	}

	public Color getSocksColor() {
		return new Color(255, 255, 255);
	}

	public Color getGoalKeeper() {
		return new Color(0, 0, 0);
	}

	public EstiloUniforme getStyle() {
		return EstiloUniforme.SIN_ESTILO;
	}

	public Color getShirtColor2() {
		return new Color(0, 51, 153);
	}

	public Color getShortsColor2() {
		return new Color(0, 0, 102);
	}

	public Color getShirtLineColor2() {
		return new Color(255, 204, 0);
	}

	public Color getSocksColor2() {
		return new Color(255, 255, 255);
	}

	public Color getGoalKeeper2() {
		return new Color(0, 153, 204);
	}

	public EstiloUniforme getStyle2() {
		return EstiloUniforme.SIN_ESTILO;
	}

	class JugadorImpl implements PlayerDetail {

		String		nombre;
		int			numero;
		Color		piel, pelo;
		double		velocidad, remate, presicion;
		boolean		portero;
		Position	Position;
		double		velocidadAvance	= 0;

		public JugadorImpl(final String nombre, final int numero, final Color piel, final Color pelo, final double velocidad, final double remate, final double presicion, final boolean portero) {
			this.nombre = nombre;
			this.numero = numero;
			this.piel = piel;
			this.pelo = pelo;
			this.velocidad = velocidad;
			this.remate = remate;
			this.presicion = presicion;
			this.portero = portero;
		}

		public double getVelocidadAvance() {
			return velocidadAvance;
		}

		public void setVelocidadAvance(final double velocidadAvance) {
			this.velocidadAvance = velocidadAvance;
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
		return jugadores;
	}

	PlayerDetail[]	jugadores	= new JugadorImpl[] {//
								//
			new JugadorImpl("David Ospina", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1.0d, 1.0d, true),//
			new JugadorImpl("Juan Camilo Zuñiga", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.7d, 0.95d, false),//
			new JugadorImpl("Cristian Zapata", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.4d, 0.0d, false),//
			new JugadorImpl("Mario Alberto Yepez", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.7d, 0.95d, false),//
			new JugadorImpl("Pablo Armero", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1.0d, 1.0d, false),//
			new JugadorImpl("Freddy Guarin", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.4d, 0.0d, false),//
			new JugadorImpl("Juan Guillermo Cuadrado", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1.0d, 1.0d, false),//
			new JugadorImpl("Abel Aguilar", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.4d, 0.0d, false),//
			new JugadorImpl("James Rodriguez", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false),//
			new JugadorImpl("Radamel Falcao", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false),//
			new JugadorImpl("Jackson Martinez", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false) //
								};	//

	public TacticDetail getDetail() {
		return this;
	}

	public Position[] getStartPositions(final GameSituations sp) {
		return alineacion4;
	}

	public Position[] getNoStartPositions(final GameSituations sp) {
		return alineacion5;
	}

	public List<Command> execute(final GameSituations sp) {
		return null;
	}

}