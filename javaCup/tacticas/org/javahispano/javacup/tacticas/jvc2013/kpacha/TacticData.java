package org.javahispano.javacup.tacticas.jvc2013.kpacha;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class TacticData {

    private static final double DISTANCE = Math.sqrt(2)
	    * Constants.RADIO_CIRCULO_CENTRAL / 2;

    public final static Position START_POSITIONS[] = new Position[] {
	    new Position(0, -50.4), new Position(-20, -15),
	    new Position(0, -15), new Position(20, -15),
	    new Position(-DISTANCE, -DISTANCE),
	    new Position(DISTANCE, -DISTANCE), new Position(-17, -5),
	    new Position(0, 0), new Position(17, -5), new Position(-2, -0.5),
	    new Position(11.174825174825173, -0.5) };

    public final static Position NO_START_POSITIONS[] = new Position[] {
	    new Position(0, -50.4), new Position(-20, -15),
	    new Position(0, -15), new Position(20, -15),
	    new Position(-DISTANCE, -DISTANCE),
	    new Position(DISTANCE, -DISTANCE), new Position(-17, -5),
	    new Position(0, -Constants.RADIO_CIRCULO_CENTRAL),
	    new Position(17, -5),
	    new Position(-Constants.RADIO_CIRCULO_CENTRAL, -0.5),
	    new Position(Constants.RADIO_CIRCULO_CENTRAL, -0.5) };

    public final static Position DEFAULT_POSITIONS[] = new Position[] {
	    new Position(0, -50.4), new Position(-18, -5), new Position(0, -5),
	    new Position(18, -5), new Position(-9, 9), new Position(9, 9),
	    new Position(-20, 22), new Position(-0, 22), new Position(20, 22),
	    new Position(-9, 32), new Position(9, 32) };

    public final static Position OFFENSIVE_POSITIONS[] = new Position[] {
	    new Position(0, -50.4), new Position(-18, -4), new Position(0, -4),
	    new Position(18, -4), new Position(-9, 9), new Position(9, 9),
	    new Position(-22, 22), new Position(-0, 22), new Position(22, 22),
	    new Position(-9, 35), new Position(9, 35) };

    public final static Position DEFENSIVE_POSITIONS[] = new Position[] {
	    new Position(0, -50.4), new Position(-15, -6), new Position(0, -6),
	    new Position(15, -6), new Position(-9, 9), new Position(9, 9),
	    new Position(-18, 22), new Position(-0, 22), new Position(18, 22),
	    new Position(-9, 32), new Position(9, 32) };

}