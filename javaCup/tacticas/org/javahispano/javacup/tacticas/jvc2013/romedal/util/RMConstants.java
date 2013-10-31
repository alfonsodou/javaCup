package org.javahispano.javacup.tacticas.jvc2013.romedal.util;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public interface RMConstants {
	static final double TO_ANG = 180 / Math.PI;
	static final double TO_RAD = Math.PI / 180;
	static final double META_Y = Constants.LARGO_CAMPO_JUEGO / 2;
	static final double MAX_ANGLE = 45;
	static final double MIN_ANGLE = 13;
	static final double DELTA_ANGLE = 0.5;
	static final double SENO_TETA = 2.3;
	static final double G = 9.8;

	static final double STATIC_HEIGHT_CALC = Math.pow(SENO_TETA, 2) * Constants.AMPLIFICA_VEL_TRAYECTORIA / G;

	static final double MAX_SHOT_SPEED = Constants.getVelocidadRemate(1);

	static final int MAX_ITER = 50;
	static final double SPEED_APROX = 0.36d;
	static final double FLAT_SHOT = 12;

	static Position metaAbajoIzquierda = Constants.esqSupIzqAreaChicaSup.movePosition(0, Constants.ANCHO_AREA_CHICA);
	static Position metaAbajoDerecha = Constants.esqSupIzqAreaChicaSup.movePosition(Constants.LARGO_AREA_CHICA, Constants.ANCHO_AREA_CHICA);
	static Position metaArribaIzquierda = Constants.esqSupIzqAreaChicaInf;
	static Position metaArribaDerecha = Constants.esqSupIzqAreaChicaInf.movePosition(Constants.LARGO_AREA_CHICA, 0);

	public final int idxMyGoalkeeper = 0;
	
	public static double MEDIO = Constants.LARGO_CAMPO_JUEGO / 4;

}
