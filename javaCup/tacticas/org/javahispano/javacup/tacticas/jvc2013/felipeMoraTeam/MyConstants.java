package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class MyConstants {
	public static final double scoreWeight = 0.75;
	public static final double timeWeight = 0.25;
	public static final double fatigueWeight = 0;
	public static final double powerByMeterOnPass = 0.02;
	public static final double powerHitOnPosession = 0.3;
	public static final double powerHitOnPosessionSprint = 0.4;
	public static final double maxHitPower = 1.0;
	public static final double cornerHitPower = 0.85;
	public static final double cornetHitVerticalAngle = 45;
	public static final Position areaPassPosition = new Position( Constants.penalSup.getX(), Constants.penalSup.getY() - 2 );
	public static final double hitVerticalAngle = 15;
	public static final Position rivalGoalCentre = new Position( ( Constants.posteDerArcoSup.getX() + Constants.posteIzqArcoSup.getX() ) / 2, Constants.posteDerArcoSup.getY() );
	public static final double shotPositionPenalti = 6;
	public static final double shotDistanceToGoal = 20;
	public static final double shotDistanceToGoalIfAlone = 15;
	public static final double minimumDistanceToPass = 10;
	public static final double widthBehind1 = 0.5;
	public static final double widthPar1 = 2;
	public static final double widthBehind2 = 0.75;
	public static final double widthPar2 = 5;
	public static final double widthBehind3 = 1;
	public static final double widthPar3 = 10;
	public static final double levelPassWeight = 0.2;
	public static final double destPlayerFreedomWeight = 0.3;
	public static final double teamFormationWeight = 0.1;
	public static final double goAheadWeight = 0.4;
	public static final double destPlayerFreedomMax = 25;
	public static final double ultraOffensiveMinDistanceFav = 20;
	public static final double longPass = 50;
	public static final double distToRelevPosition = 10;
}
