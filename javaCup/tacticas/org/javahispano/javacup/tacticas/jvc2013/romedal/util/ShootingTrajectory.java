package org.javahispano.javacup.tacticas.jvc2013.romedal.util;


public class ShootingTrajectory implements Comparable<ShootingTrajectory> {

	double potencia;
	double angulo;
	boolean banda;

	double distancia=0;
	int iteraciones=0;
	
	public ShootingTrajectory(final double potencia, final double angulo, boolean banda, double distance, int iter) {
		super();
		this.potencia = potencia;
		this.angulo = angulo;
		this.banda= banda;
		this.distancia= distance;
		this.iteraciones= iter;
	}

	public ShootingTrajectory(final double potencia, final double angulo, boolean banda) {
		super();
		this.potencia = potencia;
		this.angulo = angulo;
		this.banda= banda;
	}

	public ShootingTrajectory(final ShootingTrajectory info) {
		this(info.getPotencia(), info.getAngulo(), info.isBanda(),info.getDistancia(),info.getIteraciones());
	}

	public double getAngulo() {
		return angulo;
	}

	public void setAngulo(final double angulo) {
		this.angulo = angulo;
	}

	public boolean isBanda() {
		return banda;
	}

	public void setBanda(boolean banda) {
		this.banda = banda;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(final double distancia) {
		this.distancia = distancia;
	}

	public double getPotencia() {
		return potencia;
	}

	public void setPotencia(final double potencia) {
		this.potencia = potencia;
	}

	public int getIteraciones() {
		return iteraciones;
	}

	public void setIteraciones(final int iteraciones) {
		this.iteraciones = iteraciones;
	}

	@Override
	public int compareTo(final ShootingTrajectory o) {

		double dif = iteraciones - o.getIteraciones();
		if (dif < 0) {
			return -1;
		}
		if (dif > 0) {
			return 1;
		}
		dif = angulo - o.getAngulo();
		if (dif > 0) {
			return 1;
		}
		if (dif < 0) {
			return -1;
		}
		dif = potencia - o.getPotencia();
		if (dif > 0) {
			return 1;
		}
		if (dif < 0) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "P: " + Analyze.round(potencia, 2) + "\tAng: " + Analyze.round(angulo * RMConstants.TO_ANG, 2) + "\tDist:" + distancia + "\tIter: " + iteraciones +"\tBand: "+banda+ "\n";
	}
}
