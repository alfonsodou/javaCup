/**
 * 
 */
package org.javahispano.javacup.tacticas.jvc2013.twentythree;

import org.javahispano.javacup.model.util.Position;

/**
 * Posicion del balon en una iteracion dada
 * 
 * @author willBender
 * 
 */
public class PosicionBalon extends PosicionIteracion {

	/**
	 * Altura del balon en esa iteracion
	 */
	private double	alturaBalon;

	public PosicionBalon(Position posicion, Integer iteracion, double alturaBalon) {
		setPosicion(posicion);
		setIteracion(iteracion);
		this.alturaBalon = alturaBalon;
	}

	/**
	 * @return the alturaBalon
	 */
	public double getAlturaBalon() {
		return alturaBalon;
	}

	/**
	 * @param alturaBalon
	 *            the alturaBalon to set
	 */
	public void setAlturaBalon(double alturaBalon) {
		this.alturaBalon = alturaBalon;
	}

}