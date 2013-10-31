package org.javahispano.javacup.tacticas.jvc2013.novena;


public class MovimientoJugador {
	
	private int 	indiceJugadorRefencia;
	private int 	indiceJugadorMueve;
	private double 	anguloRefencia;
	private int 	distanciaRefencia;
	public MovimientoJugador(int indiceJugadorRefencia, int indiceJugadorMueve,
			double anguloRefencia, int distanciaRefencia) {
		super();
		this.indiceJugadorRefencia = indiceJugadorRefencia;
		this.indiceJugadorMueve = indiceJugadorMueve;
		this.anguloRefencia = anguloRefencia;
		this.distanciaRefencia = distanciaRefencia;
	}
	/**
	 * @return the indiceJugadorRefencia
	 */
	public int getIndiceJugadorRefencia() {
		return indiceJugadorRefencia;
	}
	/**
	 * @param indiceJugadorRefencia the indiceJugadorRefencia to set
	 */
	public void setIndiceJugadorRefencia(int indiceJugadorRefencia) {
		this.indiceJugadorRefencia = indiceJugadorRefencia;
	}
	/**
	 * @return the indiceJugadorMueve
	 */
	public int getIndiceJugadorMueve() {
		return indiceJugadorMueve;
	}
	/**
	 * @param indiceJugadorMueve the indiceJugadorMueve to set
	 */
	public void setIndiceJugadorMueve(int indiceJugadorMueve) {
		this.indiceJugadorMueve = indiceJugadorMueve;
	}
	/**
	 * @return the anguloRefencia
	 */
	public double getAnguloRefencia() {
		return anguloRefencia;
	}
	/**
	 * @param anguloRefencia the anguloRefencia to set
	 */
	public void setAnguloRefencia(double anguloRefencia) {
		this.anguloRefencia = anguloRefencia;
	}
	/**
	 * @return the distanciaRefencia
	 */
	public int getDistanciaRefencia() {
		return distanciaRefencia;
	}
	/**
	 * @param distanciaRefencia the distanciaRefencia to set
	 */
	public void setDistanciaRefencia(int distanciaRefencia) {
		this.distanciaRefencia = distanciaRefencia;
	}
	
	
	
	
}
