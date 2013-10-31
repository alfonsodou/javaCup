package org.javahispano.javacup.tacticas.jvc2013.masia13.model;

import org.javahispano.javacup.model.util.Constants;

public class PlayerSpecifications {
	
    public static final double GOALKEEPER_HEIGHT_INSIDE_AREA = Constants.ALTO_ARCO;
    public static final double GOALKEEPER_CONTROL_DISTANCE_INSIDE_AREA = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
    public static final double PLAYER_HEIGHT = Constants.ALTURA_CONTROL_BALON;
    public static final double CONTROL_DISTANCE = Constants.DISTANCIA_CONTROL_BALON;
    public static final double PLAYER_WIDTH = Constants.JUGADORES_SEPARACION;
    
    public static final double MAX_SPEED_SHOOT = Constants.REMATE_VELOCIDAD_MAX;
    
    public static final double MIN_ENERGY = Constants.ENERGIA_MIN;
    public static final double ENERGY_RATE = Constants.ENERGIA_DIFF;
    public static final double MIN_ENERGY_SPRINT = Constants.SPRINT_ENERGIA_MIN;
    
    public static final double MIN_ACELERATION_X = Constants.ACELERACION_MINIMA_X;
    public static final double MIN_ACELERATION_Y = Constants.ACELERACION_MINIMA_Y;
    public static final double MIN_ACELERATION = MIN_ACELERATION_X*MIN_ACELERATION_Y;
    public static final double ACELERATION_RATE = Constants.ACELERACION_INCR;
    
}
