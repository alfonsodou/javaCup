package org.javahispano.javacup.tacticas.jvc2013.romedal.util;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.CommandHitBall;


public class HitBallDetail implements Comparable<HitBallDetail> {
	private CommandHitBall		comando;
	private double				anguloXY;
	private int					iterJugador;
	private int					iterRival;
	private int					iterBalon;
	private LinkedList<Ball>	trayectoria;
	private final int			difIter;

	private final boolean		arriba;
	private boolean				autoPase;
	private boolean				mejorSkill;

	public HitBallDetail(final CommandHitBall comando, final LinkedList<Ball> trayectoria, final double anguloXY, final int iterJugador, final int iterRival, final boolean autoPase, final boolean mejorSkill) {
		super();
		this.comando = comando;
		this.trayectoria = trayectoria;
		this.anguloXY = anguloXY;
		this.iterJugador = iterJugador;
		this.iterRival = iterRival;
		this.difIter = this.iterRival - this.iterJugador;
		arriba = trayectoria.get(0).getPosition().getY() < comando.getDestiny().getY();
	}

	public CommandHitBall getComando() {
		return comando;
	}

	public void setComando(final CommandHitBall comando) {
		this.comando = comando;
	}

	public double getAnguloXY() {
		return anguloXY;
	}

	public void setAnguloXY(final double anguloXY) {
		this.anguloXY = anguloXY;
	}

	public int getIterJugador() {
		return iterJugador;
	}

	public void setIterJugador(final int iterJugador) {
		this.iterJugador = iterJugador;
	}

	public int getIterRival() {
		return iterRival;
	}

	public void setIterRival(final int iterRival) {
		this.iterRival = iterRival;
	}

	public LinkedList<Ball> getTrayectoria() {
		return trayectoria;
	}

	public void setTrayectoria(final LinkedList<Ball> trayectoria) {
		this.trayectoria = trayectoria;
	}

	public int getDifIter() {
		return difIter;
	}

	public boolean isArriba() {
		return arriba;
	}

	public boolean isAutoPase() {
		return autoPase;
	}

	public boolean isMejorSkill() {
		return mejorSkill;
	}

	public int getIterBalon() {
		return iterBalon;
	}

	public void setIterBalon(int iterBalon) {
		this.iterBalon = iterBalon;
	}

	@Override
	public String toString() {
		return comando.getPlayerIndex() + "\t" + comando.getDestiny() + "\tAngXY: " + anguloXY + "\tAngZ: " + Analyze.round(comando.getVerticalAngle(), 1) + "\tIter: " + iterJugador + "\tIterRival: " + iterRival + "\tdifIter" + difIter;
	}

	@Override
	public int compareTo(final HitBallDetail o) {
		if (iterRival != o.iterRival) {
			final int difA = iterRival - iterJugador;
			final int difB = o.iterRival - o.iterJugador;
			if (difA > 0 || difB > 0) {
				return difA - difB;
			}
		}
		return 0;
	}
}