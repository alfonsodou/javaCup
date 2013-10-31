package org.javahispano.javacup.tacticas.jvc2013.masia13.model;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.tacticas.jvc2013.masia13.util.Position;

public class FieldSpecifications {
	
    public static final double FIELD_HEIGHT = Constants.LARGO_CAMPO_JUEGO;
    public static final double FIELD_WIDTH = Constants.ANCHO_CAMPO_JUEGO;
    
    public static final double GOAL_WIDTH = Constants.LARGO_ARCO;
    public static final double GOAL_HEIGHT = Constants.ALTO_ARCO;
    
    public static final double PENALTY_AREA_WIDTH = Constants.LARGO_AREA_GRANDE;
    public static final double PENALTY_AREA_HEIGHT = Constants.ANCHO_AREA_GRANDE;
    
    public static final double PENALTY_DISTANCE = Constants.DISTANCIA_PENAL;
    public static final double PENALTY_RADIUS = Constants.RADIO_PENAL;
    
    public static final double KICK_DISTANCE = Constants.DISTANCIA_SAQUE;
    
    public static final double GOAL_AREA_WIDTH = Constants.LARGO_AREA_CHICA;
    public static final double GOAL_AREA_HEIGHT = Constants.ANCHO_AREA_CHICA;
    
    public static final Position MY_PENALTY = new Position(Constants.penalInf.getX(), Constants.penalInf.getY());
    public static final Position RIVAL_PENALTY = new Position(Constants.penalSup.getX(), Constants.penalSup.getY());
    
    public static final Position MY_GOAL_LEFT_POST = new Position(Constants.posteIzqArcoInf.getX(), Constants.posteIzqArcoInf.getY());
    public static final Position MY_GOAL_RIGHT_POST = new Position(Constants.posteDerArcoInf.getX(), Constants.posteDerArcoInf.getY());
    public static final Position MY_GOAL_CENTER = new Position(Constants.centroArcoInf.getX(), Constants.centroArcoInf.getY());
    
    public static final Position RIVAL_GOAL_LEFT_POST = new Position(Constants.posteIzqArcoSup.getX(), Constants.posteIzqArcoSup.getY());
    public static final Position RIVAL_GOAL_RIGHT_POST = new Position(Constants.posteDerArcoSup.getX(), Constants.posteDerArcoSup.getY());
    public static final Position RIVAL_GOAL_CENTER = new Position(Constants.centroArcoSup.getX(), Constants.centroArcoSup.getY());
    
    public static final Position FIELD_CENTER = new Position(Constants.centroCampoJuego.getX(), Constants.centroCampoJuego.getY());
    
    public static final Position BOTTOM_LEFT_CORNER  = new Position(Constants.cornerInfIzq.getX(), Constants.cornerInfIzq.getY());
    public static final Position BOTTOM_RIGHT_CORNER  = new Position(Constants.cornerInfDer.getX(), Constants.cornerInfDer.getY());
    public static final Position TOP_LEFT_CORNER  = new Position(Constants.cornerSupIzq.getX(), Constants.cornerSupIzq.getY());
    public static final Position TOP_RIGHT_CORNER  = new Position(Constants.cornerSupDer.getX(), Constants.cornerSupDer.getY());
    
}
