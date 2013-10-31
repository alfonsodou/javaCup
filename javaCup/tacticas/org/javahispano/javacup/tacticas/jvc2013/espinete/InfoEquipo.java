package org.javahispano.javacup.tacticas.jvc2013.espinete;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.render.EstiloUniforme;

class InfoEquipo implements TacticDetail {
   
	public InfoEquipo(){ }
	
	private PlayerDetail[] equipo = new PlayerDetail[]{
     
        new InfoJugadores("Urruti",      1, new Color(235,220,189), new Color(255,255,153),1.0d,1.0d,1.0d, true),     
        new InfoJugadores("Alexanco",    2, new Color(235,220,189), new Color(255,255,  0),1.0d,1.0d,1.0d, false),     
        new InfoJugadores("Camacho",     3, new Color(235,220,189), new Color(255,153,  0),1.0d,1.0d,1.0d, false),    
        new InfoJugadores("Migueli",     4, new Color(235,220,189), new Color(  0,  0,  0),1.0d,1.0d,0.5d, false),     
        new InfoJugadores("Beckenbauer", 5, new Color(235,220,189), new Color(204,204,204),1.0d,0.5d,0.5d, false),     
        new InfoJugadores("Schuster",    6, new Color(235,220,189), new Color( 51, 51,255),1.0d,0.5d,0.5d, false),     
        new InfoJugadores("Juanito",     7, new Color(235,220,189), new Color(255,  0, 51),1.0d,0.75d,0.25d, false),     
        new InfoJugadores("Michel",      8, new Color(235,220,189), new Color(0,  255,102),1.0d,1.0d,0.5d, false),     
        new InfoJugadores("Quini",       9, new Color(235,220,189), new Color(255, 51,255),1.0d,1.0d,0.5d, false),     
        new InfoJugadores("Maradona",   10, new Color(235,220,189), new Color( 14,104, 23),1.0d,1.0d,0.5d, false),    
        new InfoJugadores("Laudrup",    11, new Color(235,220,189), new Color(153,153,  0),1.0d,1.0d,0.5d, false)     
    };
	
    public String getTacticName() {
        return "Espinete";
    }

    public String getCountry() {
        return "España";
    }

    public String getCoach() {
        return "Kike";
    }

    public Color getShirtColor() {
        return new Color(222, 14, 212);
    }

    public Color getShortsColor() {
        return new Color(144, 200, 227);
    }

    public Color getShirtLineColor() {
        return new Color(10, 0, 10);
    }

    public Color getSocksColor() {
        return new Color(204, 0, 204);
    }

    public Color getGoalKeeper() {
        return new Color(0, 0, 0        );
    }

    public EstiloUniforme getStyle() {
        return EstiloUniforme.LINEAS_HORIZONTALES;
    }

    public Color getShirtColor2() {
        return new Color(104, 166, 188);
    }

    public Color getShortsColor2() {
        return new Color(31, 129, 229);
    }

    public Color getShirtLineColor2() {
        return new Color(101, 8, 142);
    }

    public Color getSocksColor2() {
        return new Color(192, 67, 91);
    }

    public Color getGoalKeeper2() {
        return new Color(94, 5, 254        );
    }

    public EstiloUniforme getStyle2() {
        return EstiloUniforme.FRANJA_VERTICAL;
    }

    public PlayerDetail[] getPlayers() {
        return equipo;
    } 
    
}
