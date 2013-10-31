package org.javahispano.javacup.model.engine;

import java.io.Serializable;
import org.javahispano.javacup.model.util.Position;

/** Datos de una iteracion en un partido, usada para guardar partidos, uso interno */
final class Iteracion implements Serializable {

    private static final long serialVersionUID = 756445962537556468L;

	boolean gol;
	boolean poste;
	boolean rebotando;
	boolean ovacionando;
	boolean rematando;
	boolean sacando;
	boolean silbando;
	boolean cambioSaque;
	boolean isOffSide;
	boolean isLibreIndirecto;
	short alturaBalon;
	short posVisibleBalonX;
	short posVisibleBalonY;
	short[][][] posiciones;
	int iteracion;
	int golesLocal;
	int golesVisita;
	short posecionBalonLocal;

	public Iteracion(boolean gol, boolean poste, boolean rebotando, boolean ovacionando, boolean rematando, boolean sacando,
			boolean silbando, boolean cambioSaque, boolean offSide, boolean libreIndirecto, double alturaBalon, Position posVisibleBalon, Position[][] posiciones,
			int iteracion, int golesLocal, int golesVisita, double posecionBalonLocal) {
		this.gol = gol;
		this.poste = poste;
		this.rebotando = rebotando;
		this.ovacionando = ovacionando;
		this.rematando = rematando;
		this.sacando = sacando;
		this.silbando = silbando;
		this.cambioSaque = cambioSaque;
		this.isOffSide = offSide;
		this.isLibreIndirecto = libreIndirecto;
		this.alturaBalon = (short) (alturaBalon*256d);
		this.posVisibleBalonX = (short)( posVisibleBalon.getX()*256d);
		this.posVisibleBalonY = (short)( posVisibleBalon.getY()*256d);
		this.posiciones = new short[3][][];
		this.posiciones[0] = new short[11][];
		this.posiciones[1] = new short[11][];
		this.posiciones[2] = new short[1][];
		for (int i = 0; i < 11; i++) {
			this.posiciones[0][i] = new short[2];
			this.posiciones[0][i][0] = (short)( posiciones[0][i].getX()*256d);
			this.posiciones[0][i][1] = (short)( posiciones[0][i].getY()*256d);
			this.posiciones[1][i] = new short[2];
			this.posiciones[1][i][0] = (short)( posiciones[1][i].getX()*256d);
			this.posiciones[1][i][1] = (short)( posiciones[1][i].getY()*256d);
		}
		this.posiciones[2][0] = new short[2];
		this.posiciones[2][0][0] = (short)( posiciones[2][0].getX()*256d);
		this.posiciones[2][0][1] = (short)( posiciones[2][0].getY()*256d);
		this.iteracion = iteracion;
		this.golesLocal = golesLocal;
		this.golesVisita = golesVisita;
		this.posecionBalonLocal = (short)( posecionBalonLocal*256d);
	}

}
