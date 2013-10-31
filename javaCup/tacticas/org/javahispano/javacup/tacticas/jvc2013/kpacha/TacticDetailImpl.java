package org.javahispano.javacup.tacticas.jvc2013.kpacha;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.render.EstiloUniforme;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.Defender;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.Forward;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.GoalKeeper;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.player.Middfielder;

class TacticDetailImpl implements TacticDetail {

    private static final Color WHITE_SKIN = new Color(255, 200, 150);
    private static final Color BROWN_HAIR = new Color(50, 0, 0);

    private PlayerDetail[] players = new PlayerDetail[] {
	    new GoalKeeper("Pulpo", 1, Color.PINK, Color.GRAY, 1.0d, 1.0d, 0.5d),
	    new Defender("Asesino", 2, WHITE_SKIN, Color.DARK_GRAY, 1.0d,
		    0.75d, 0.1d),
	    new Defender("Destripador", 3, WHITE_SKIN, Color.DARK_GRAY, 0.9d,
		    0.75d, 0.1d),
	    new Defender("Carnicero", 4, WHITE_SKIN, Color.DARK_GRAY, 1.0d,
		    0.75d, 0.1d),
	    new Middfielder("Seguro", 5, Color.PINK, BROWN_HAIR, 0.9d, 0.75d,
		    0.5d),
	    new Middfielder("Pelotero", 6, Color.PINK, BROWN_HAIR, 0.9d, 0.75d,
		    0.75d),
	    new Forward("Carrilero", 7, WHITE_SKIN, Color.YELLOW, 1.0d, 1.0d,
		    1.0d),
	    new Forward("Goleador", 8, WHITE_SKIN, Color.YELLOW, 1.0d, 1.0d,
		    1.0d),
	    new Forward("Correcaminos", 9, WHITE_SKIN, Color.YELLOW, 1.0d,
		    1.0d, 1.0d),
	    new Forward("Millonario", 10, Color.BLACK, BROWN_HAIR, 1.0d, 1.0d,
		    1.0d),
	    new Forward("Vividor", 11, Color.BLACK, BROWN_HAIR, 1.0d, 1.0d,
		    1.0d) };

    public String getTacticName() {
	return "Supu 0.1";
    }

    public String getCountry() {
	return "España";
    }

    public String getCoach() {
	return "kpacha";
    }

    public Color getShirtColor() {
	return Color.RED;
    }

    public Color getShortsColor() {
	return Color.BLACK;
    }

    public Color getShirtLineColor() {
	return Color.BLACK;
    }

    public Color getSocksColor() {
	return Color.BLACK;
    }

    public Color getGoalKeeper() {
	return Color.BLACK;
    }

    public EstiloUniforme getStyle() {
	return EstiloUniforme.FRANJA_DIAGONAL;
    }

    public Color getShirtColor2() {
	return Color.YELLOW;
    }

    public Color getShortsColor2() {
	return Color.DARK_GRAY;
    }

    public Color getShirtLineColor2() {
	return Color.DARK_GRAY;
    }

    public Color getSocksColor2() {
	return Color.DARK_GRAY;
    }

    public Color getGoalKeeper2() {
	return Color.DARK_GRAY;
    }

    public EstiloUniforme getStyle2() {
	return EstiloUniforme.LINEAS_VERTICALES;
    }

    public PlayerDetail[] getPlayers() {
	return players;
    }
}