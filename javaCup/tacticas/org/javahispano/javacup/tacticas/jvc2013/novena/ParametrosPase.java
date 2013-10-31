package org.javahispano.javacup.tacticas.jvc2013.novena;

import org.javahispano.javacup.model.util.Position;

public class ParametrosPase {
    private double anguloHorizontal;
    private double angulovertical;
    private double fuerza;
    private Position posicionRecepcion;
    private Position posicionReceptor;
    private Position posicionLejana;
    private ParametrosPase error0;
    private ParametrosPase error1;
    private Pase pase;

    private double calidad;

	public ParametrosPase(double anguloHorizontal, double angulovertical,
			double fuerza, Position posicionReceptor, Position posicionRecepcion,  Position posicionLejana) {
		super();
		this.anguloHorizontal = anguloHorizontal;
		this.angulovertical = angulovertical;
		this.fuerza = fuerza;
		this.posicionRecepcion = posicionRecepcion;
		this.posicionReceptor = posicionReceptor;
		this.posicionLejana = posicionLejana;
		this.error0 = null;
		this.error1 = null;
		this.calidad = 0d;
		this.pase = null;
	}


	public Position getPosicionReceptor() {
		return posicionReceptor;
	}


	public void setPosicionReceptor(Position posicionReceptor) {
		this.posicionReceptor = posicionReceptor;
	}


	public double getAnguloHorizontal() {
		return anguloHorizontal;
	}

	public void setAnguloHorizontal(double anguloHorizontal) {
		this.anguloHorizontal = anguloHorizontal;
	}

	public double getAngulovertical() {
		return angulovertical;
	}

	public void setAngulovertical(double angulovertical) {
		this.angulovertical = angulovertical;
	}

	public double getFuerza() {
		return fuerza;
	}

	public void setFuerza(double fuerza) {
		this.fuerza = fuerza;
	}

	public Position getPosicionRecepcion() {
		return posicionRecepcion;
	}

	public void setPosicionRecepcion(Position posicionRecepcion) {
		this.posicionRecepcion = posicionRecepcion;
	}

	public ParametrosPase getError0() {
		return error0;
	}

	public void setError0(ParametrosPase error0) {
		this.error0 = error0;
	}

	public ParametrosPase getError1() {
		return error1;
	}

	public void setError1(ParametrosPase error1) {
		this.error1 = error1;
	}

	public double getCalidad() {
		return calidad;
	}

	public void setCalidad(double calidad) {
		this.calidad = calidad;
	}

	public Pase getPase() {
		return pase;
	}

	public void setPase(Pase pase) {
		this.pase = pase;
	}


	/**
	 * @return the posicionLejana
	 */
	public Position getPosicionLejana() {
		return posicionLejana;
	}


	/**
	 * @param posicionLejana the posicionLejana to set
	 */
	public void setPosicionLejana(Position posicionLejana) {
		this.posicionLejana = posicionLejana;
	}
    
    
}
