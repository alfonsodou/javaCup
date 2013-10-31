/**
 * 
 */
package org.javahispano.javacup.tacticas.jvc2013.twentythree;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 * Posicion de cualquier objeto en una iteracion dada
 * 
 * @author willBender
 * 
 */
public class PosicionIteracion {

	/**
	 * Posicion para el historial de la marca
	 */
	private Position	posicion;

	/**
	 * Iteracion de la posicion
	 */
	private Integer		iteracion;

	/**
	 * @return the posicion
	 */
	public Position getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion
	 *            the posicion to set
	 */
	public void setPosicion(Position posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return the iteracion
	 */
	public Integer getIteracion() {
		return iteracion;
	}

	/**
	 * @param iteracion
	 *            the iteracion to set
	 */
	public void setIteracion(Integer iteracion) {
		this.iteracion = iteracion;
	}

	/**
	 * Distancia en esta posicion hacia mi arco
	 * 
	 * @return
	 */
	public double getDistanciaMiArco() {
		return getPosicion().distance(Constants.centroArcoInf);
	}

	/**
	 * Distancia en esta posicion hacia el arco rival
	 * 
	 * @return
	 */
	public double getDistanciaSuArco() {
		return getPosicion().distance(Constants.centroArcoSup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("It - ");
		sb.append(getIteracion());
		sb.append("\t");
		sb.append("X - ");
		sb.append(getPosicion().getX());
		sb.append("\t");
		sb.append("Y - ");
		sb.append(getPosicion().getY());
		return sb.toString();
	}

}