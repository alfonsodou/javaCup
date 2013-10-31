package org.javahispano.javacup.tacticas.jvc2013.valedores;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.render.EstiloUniforme;

public class TacticDetailValedores implements TacticDetail {

	public String getTacticName() {
		return "Valedores MX";
	}

	public String getCountry() {
		return "México";
	}

	public String getCoach() {
		return "Ivan Serrano Garcia";
	}

	public Color getShirtColor() {
		return new Color(255, 255, 255);
	}

	public Color getShortsColor() {
		return new Color(255, 0, 0);
	}

	public Color getShirtLineColor() {
		return new Color(255, 0, 0);
	}

	public Color getSocksColor() {
		return new Color(255, 255, 255);
	}

	public Color getGoalKeeper() {
		return new Color(255, 255, 255);
	}

	public EstiloUniforme getStyle() {
		return EstiloUniforme.LINEAS_VERTICALES;
	}

	public Color getShirtColor2() {
		return new Color(51, 51, 51);
	}

	public Color getShortsColor2() {
		return new Color(51, 51, 51);
	}

	public Color getShirtLineColor2() {
		return new Color(255, 255, 255);
	}

	public Color getSocksColor2() {
		return new Color(51, 51, 51);
	}

	public Color getGoalKeeper2() {
		return new Color(255, 255, 255);
	}

	public EstiloUniforme getStyle2() {
		return EstiloUniforme.SIN_ESTILO;
	}

	

	public PlayerDetail[] getPlayers() {
		return new PlayerDetail[] {
				new JugadorValedores("KKs", 1, new Color(207,164,124), new Color(255,102,102),1.0d,1.0d,0.82d, true),
                new JugadorValedores("Mai", 2, new Color(255,200,150), new Color(50,0,0),0.8d,0.54d,0.71d, false),
                new JugadorValedores("Pepe", 3, new Color(255,200,150), new Color(50,0,0),0.94d,0.65d,0.81d, false),
                new JugadorValedores("Pelos", 4, new Color(255,200,150), new Color(50,0,0),0.88d,0.68d,0.82d, false),
                new JugadorValedores("Morris", 5, new Color(255,200,150), new Color(50,0,0),0.63d,0.5d,0.79d, false),
                new JugadorValedores("Jose", 6, new Color(255,200,150), new Color(50,0,0),0.8d,0.94d,0.76d, false),
                new JugadorValedores("Gordo", 7, new Color(255,200,150), new Color(50,0,0),0.71d,0.77d,0.82d, false),
                new JugadorValedores("Chapa", 8, new Color(255,200,150), new Color(50,0,0),0.89d,0.88d,1.0d, false),
                new JugadorValedores("Cabe", 9, new Color(255,200,150), new Color(50,0,0),0.81d,0.91d,1.0d, false),
                new JugadorValedores("Juan", 10, new Color(255,200,150), new Color(50,0,0),0.89d,1.0d,0.98d, false),
                new JugadorValedores("Pet", 11, new Color(255,200,150), new Color(50,0,0),0.85d,1.0d,0.92d, false)
	};
}
}


