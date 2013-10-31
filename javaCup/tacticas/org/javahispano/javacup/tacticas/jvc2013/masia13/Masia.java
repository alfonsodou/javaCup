package org.javahispano.javacup.tacticas.jvc2013.masia13;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;
import org.javahispano.javacup.tacticas.jvc2013.masia13.model.MyPlayerDetail;

public class Masia extends AbstractTactic implements Tactic{
	
	@Override
	public String getTacticName() {
        return "La Masia";
    }

	@Override
    public String getCountry() {
        return "Cuba";
    }

    @Override
    public String getCoach() {
        return "Karel Osorio";
    }

    @Override
    public Color getShirtColor() {
        return new Color(51, 0, 153);
    }

    @Override
    public Color getShortsColor() {
        return new Color(51, 0, 153);
    }
    
    @Override
    public Color getShirtLineColor() {
        return new Color(153, 0, 0);
    }

    @Override
    public Color getSocksColor() {
        return new Color(51, 0, 153);
    }

    @Override
    public Color getGoalKeeper() {
        return new Color(51, 51, 51);
    }

    @Override
    public EstiloUniforme getStyle() {
        return EstiloUniforme.LINEAS_VERTICALES;
    }

    @Override
    public Color getGoalKeeper2() {
    	return new Color(51, 51, 51);
    }
    
    public Color getShirtColor2() {
        return new Color(153, 0, 0);
    }

    public Color getShortsColor2() {
        return new Color(153, 0, 0);
    }

    public Color getShirtLineColor2() {
        return new Color(255, 255, 0);
    }

    public Color getSocksColor2() {
        return new Color(255, 255, 0);
    }

    public EstiloUniforme getStyle2() {
        return EstiloUniforme.LINEAS_VERTICALES;
    }
    
    @Override
    public PlayerDetail[] getPlayers() {
        return new PlayerDetail[]{
        		new MyPlayerDetail("Valdes", 1, new Color(255,200,150), new Color(50,0,0),		1.0d,0.85d,1.0d, true),
        		new MyPlayerDetail("Pique", 3, new Color(255,200,150), new Color(50,0,0),		1.0d,0.25d,0.60d, false),
                new MyPlayerDetail("Puyol", 5, new Color(255,200,150), new Color(50,0,0),		1.0d,0.25d,0.60d, false),
                new MyPlayerDetail("Alba", 4, new Color(255,200,150), new Color(50,0,0),		1.0d,0.25d,0.60d, false),
                new MyPlayerDetail("Montoya", 2, new Color(255,200,150), new Color(50,0,0),		1.0d,0.25d,0.60d, false),
                new MyPlayerDetail("Busquet", 7, new Color(255,200,150), new Color(50,0,0),		1.0d,0.95d,0.90d, false),
                new MyPlayerDetail("Xavi", 6, new Color(255,200,150), new Color(50,0,0),		1.0d,0.95d,0.90d, false),
                new MyPlayerDetail("Iniesta", 8, new Color(255,200,150), new Color(50,0,0),		1.0d,0.95d,0.90d, false),
                new MyPlayerDetail("Pedro", 9, new Color(255,200,150), new Color(50,0,0),		1.0d,0.85d,1.0d, false),
                new MyPlayerDetail("Messi", 10, new Color(255,200,150), new Color(50,0,0),		1.0d,1.0d,1.0d, false),
                new MyPlayerDetail("Tello", 11, new Color(255,200,150), new Color(50,0,0),		1.0d,0.85d,1.0d, false)
        };
    }

	@Override
	public Position[] getNoStartPositions(GameSituations sp) {
		return new Position[]{
		        new Position(0,-50),
		        new Position(5.0,-26.25),
		        new Position(-5.0,-26.25),
		        new Position(-27.20,-15.0),
		        new Position(27.20,-15.0),
		        new Position(0,-9.5),
		        new Position(13.6,-9.5),
		        new Position(-13.6,-9.5),
		        new Position(27.20,-5),
		        new Position(-9.5,0),
		        new Position(-27.20,-5)
		    };
	}

	@Override
	public Position[] getStartPositions(GameSituations sp) {
		return new Position[]{
		        new Position(0,-50),
		        new Position(13.6,-26.25),
		        new Position(-13.6,-26.25),
		        new Position(-27.20,-15.0),
		        new Position(27.20,-15.0),
		        new Position(0,-9.5),
		        new Position(13.6,0),
		        new Position(-13.6,0),
		        new Position(27.20,0),
		        new Position(0,0),
		        new Position(-27.20,0)
		    };
	}

}
