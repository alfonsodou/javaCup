package org.javahispano.javacup.tacticas.jvc2013.romedal.util;

import org.javahispano.javacup.model.util.Position;

public class BallDestiny implements Comparable<BallDestiny> {
	Position posBalon;
	double altura;
	int idxJugador;
	int iterJugador;
	int iterBalon;
	boolean ganaRival;

	public BallDestiny(final Position initPos, final Ball b, final int idxJugador, final int iterJugador, final int iterBalon) {
		posBalon = new Position(b.getPosition()).setInsideGameField();
		this.altura = b.getAltura();
		this.idxJugador = idxJugador;
		this.iterJugador = iterJugador;
		this.iterBalon = iterBalon;
		ganaRival = false;
	}

	public Position getPosBalon() {
		return posBalon;
	}

	public void setPosBalon(final Position posBalon) {
		this.posBalon = posBalon;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(final double altura) {
		this.altura = altura;
	}

	public int getIdxJugador() {
		return idxJugador;
	}

	public void setIdxJugador(final int idxJugador) {
		this.idxJugador = idxJugador;
	}

	public int getIterJugador() {
		return iterJugador;
	}

	public void setIterJugador(final int iterJugador) {
		this.iterJugador = iterJugador;
	}

	public int getIterBalon() {
		return iterBalon;
	}

	public void setIterBalon(final int iterBalon) {
		this.iterBalon = iterBalon;
	}

	public boolean isGanaRival() {
		return ganaRival;
	}

	public void setGanaRival(final boolean ganaRival) {
		this.ganaRival = ganaRival;
	}

	@Override
	public String toString() {
		return "X [" + posBalon.getX() + "] Y [" + posBalon.getY() + "] Z[" + altura + "] " + "\tjugador: " + idxJugador + "\tEs rival: " + ganaRival;
	}

	@Override
	public int compareTo(final BallDestiny db) {
		if (db == null) {
			return 1;
		}
		if (iterBalon == db.iterBalon) {
			return db.iterJugador - iterJugador;
		}
		return db.getIterBalon() - iterBalon;
	}
}