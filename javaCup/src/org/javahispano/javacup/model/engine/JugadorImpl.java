package org.javahispano.javacup.model.engine;


import java.io.Serializable;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Color;

/**Implementacion de PlayerDetail que define la configuración de un jugador, uso interno*/
class JugadorImpl implements PlayerDetail, Serializable {

	private final String nombre;
	private final Color piel;
	private final Color pelo;
	private final int numero;
	private final double velocidad;
	private final double remate;
	private final double presicion;
	private final boolean portero;

	public JugadorImpl(PlayerDetail det) {
		this.nombre = det.getPlayerName();
		this.numero = det.getNumber();
		this.piel = det.getSkinColor();
		this.pelo = det.getHairColor();
		double vel = det.getSpeed();
		double rem = det.getPower();
		double pre = det.getPrecision();
		if (vel > 1) {
			vel = 1;
		}
		if (vel < 0) {
			vel = 0;
		}
		if (rem > 1) {
			rem = 1;
		}
		if (rem < 0) {
			rem = 0;
		}
		if (pre > 1) {
			pre = 1;
		}
		if (pre < 0) {
			pre = 0;
		}
		this.velocidad = vel;
		this.remate = rem;
		this.presicion = pre;
		this.portero = det.isGoalKeeper();
	}

	public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
					double velocidad, double remate, double presicion, boolean portero) {
		this.nombre = nombre;
		this.numero = numero;
		this.piel = piel;
		this.pelo = pelo;
		if (velocidad > 1) {
			velocidad = 1;
		}
		if (velocidad < 0) {
			velocidad = 0;
		}
		if (remate > 1) {
			remate = 1;
		}
		if (remate < 0) {
			remate = 0;
		}
		if (presicion > 1) {
			presicion = 1;
		}
		if (presicion < 0) {
			presicion = 0;
		}
		this.velocidad = velocidad;
		this.remate = remate;
		this.presicion = presicion;
		this.portero = portero;
	}

	@Override
	public String getPlayerName() {
		return nombre;
	}

	@Override
	public Color getSkinColor() {
		return piel;
	}

	@Override
	public Color getHairColor() {
		return pelo;
	}

	@Override
	public int getNumber() {
		return numero;
	}

	@Override
	public boolean isGoalKeeper() {
		return portero;
	}

	@Override
	public double getSpeed() {
		return velocidad;
	}

	@Override
	public double getPower() {
		return remate;
	}

	@Override
	public double getPrecision() {
		return presicion;
	}

}
