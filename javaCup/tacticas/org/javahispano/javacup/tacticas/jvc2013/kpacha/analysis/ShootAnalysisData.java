package org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis;

public class ShootAnalysisData implements Comparable<ShootAnalysisData> {
    private BallTrajectory trajectory;
    private double azimuth, hitPower, elevation, valoration;

    public ShootAnalysisData(BallTrajectory trajectory, double azimuth,
	    double hitPower, double elevation, double valorate) {
	this.trajectory = trajectory;
	this.azimuth = azimuth;
	this.hitPower = hitPower;
	this.elevation = elevation;
	this.valoration = valorate;
    }

    public BallTrajectory getTrajectory() {
	return trajectory;
    }

    public double getAzimuth() {
	return azimuth * 180 / Math.PI;
    }

    public double getHitPower() {
	return hitPower;
    }

    public double getElevation() {
	return elevation * 180 / Math.PI;
    }

    public double getValoration() {
	return valoration;
    }

    @Override
    public int compareTo(ShootAnalysisData o) {
	return ((Double) valoration).compareTo(o.getValoration());
    }

}
