package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Common {
	
	public static boolean insideLogicField( Position p )
	{
		double logicXLimit = ( Constants.ANCHO_CAMPO_JUEGO / 2 ) - 5;
		double logicYLimit = ( Constants.LARGO_CAMPO_JUEGO / 2 ) - 5;
		
		boolean ret = between( - logicXLimit, logicXLimit, p.getX() );
		ret = ret && between( - logicYLimit, logicYLimit, p.getY() );
		
		return ret;
	}
	
	public static boolean insideMyArea(Position p) {
		double halfBigAreaX = Constants.LARGO_AREA_GRANDE / 2;
        return (p.getX() >= -halfBigAreaX && p.getX() <= halfBigAreaX &&
                p.getY() >= -Constants.LARGO_CAMPO_JUEGO / 2 && p.getY() <= (-Constants.LARGO_CAMPO_JUEGO / 2) + Constants.ANCHO_AREA_GRANDE);
    }

	public static boolean insideRivalArea(Position p) {
		double halfBigAreaX = Constants.LARGO_AREA_GRANDE / 2;
        return (p.getX() >= -halfBigAreaX && p.getX() <= halfBigAreaX &&
                p.getY() <= Constants.LARGO_CAMPO_JUEGO / 2 && p.getY() >= (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.ANCHO_AREA_GRANDE);
    }
	
	public static boolean insideMyLittleArea(Position p) {
		double halfBigAreaX = Constants.LARGO_AREA_CHICA / 2;
        return (p.getX() >= -halfBigAreaX && p.getX() <= halfBigAreaX &&
                p.getY() >= -Constants.LARGO_CAMPO_JUEGO / 2 && p.getY() <= (-Constants.LARGO_CAMPO_JUEGO / 2) + Constants.LARGO_AREA_CHICA);
    }

	public static boolean insideRivalLittleArea(Position p) {
		double halfBigAreaX = Constants.LARGO_AREA_CHICA / 2;
        return (p.getX() >= -halfBigAreaX && p.getX() <= halfBigAreaX &&
                p.getY() <= Constants.LARGO_CAMPO_JUEGO / 2 && p.getY() >= (Constants.LARGO_CAMPO_JUEGO / 2) - Constants.LARGO_AREA_CHICA);
    }
	
	static double slope( Point p1, Point p2 )
	{
		double slope = Math.abs( p1.y - p2.y ) / Math.abs( p1.x - p2.x );
		
		return slope;
	}
	
	static boolean isAnybodyInTrajectory(Position posOrig, Position posDest){
		boolean outcome = false;
		double rivalX;
		double rivalY;
		double slope = Math.abs( posOrig.getY() - posDest.getY() ) / Math.abs( posOrig.getX() - posDest.getX() );
		
		for(int i = 0; i < Global.situation.actualPositionsRival.length; i++){
			rivalX = Global.situation.actualPositionsRival[i].getX();
			rivalY = Global.situation.actualPositionsRival[i].getY();
			if(between(posOrig.getX(), posDest.getX(), rivalX) && between(posOrig.getY(), posDest.getY(), rivalY)){
				double yTraj = Math.abs( rivalY - posOrig.getY() );
				double xTraj = slope / yTraj;
				
				if( Math.round(xTraj) == Math.round(rivalX) )
					outcome = true;
			}
		}
		return outcome;
	}
	
	static boolean between(double value1, double value2, double valueObj){
		return ((valueObj >= value1 && valueObj <= value2) || (valueObj <= value1 && valueObj >= value2));
	}
	
	static boolean highPassToArea(int recuperatorIndex) {
		return ( Global.situation.recuperators[recuperatorIndex] && insideMyArea(new Position(Global.situation.posRecuperation[0], Global.situation.posRecuperation[1])) && Global.situation.m_sp.ballAltitude() > 0 );
	}
}
