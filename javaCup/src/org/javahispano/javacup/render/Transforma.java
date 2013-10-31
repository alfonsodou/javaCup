package org.javahispano.javacup.render;

import org.javahispano.javacup.model.util.Position;

/**
Esta clase realiza transformaciones para escalar las imagenes y posiciones*/
public final class Transforma {

	/**Obtiene las ubicacion en pixeles, de una posicion real relativa de otra con una escala de pixeles por unidad*/
	public static int[] transform(Position pos, Position posRel, int xPixel, int yPixel, double escala) {
		int px = (int) ((pos.getX() - posRel.getX()) * escala + xPixel);
		int py = (int) ((pos.getY() - posRel.getY()) * escala + yPixel);
		return new int[]{px, py};
	}

	/**Obtiene las dimensiones en pixeles, de una dimension real relativa a una escala*/
	public static int[] transform(Position pos, double escala) {
		int px = (int) ((pos.getX()) * escala);
		int py = (int) ((pos.getY()) * escala);
		return new int[]{px, py};
	}

	/**Obtiene la dimension en pixeles de una dimension real relativa a una escala*/
	public static int transform(Double dimReal, double escala) {
		return (int) (dimReal * escala);
	}
}
