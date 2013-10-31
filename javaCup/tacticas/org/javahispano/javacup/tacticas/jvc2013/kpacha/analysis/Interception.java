package org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis;

import org.javahispano.javacup.model.util.Position;

public class Interception implements Comparable<Interception> {

    private double distance;
    private int rival;
    private int iteration;
    private Position position;

    public Interception(double distance, int rival, int iteration) {
	this.distance = distance;
	this.rival = rival;
	this.iteration = iteration;
    }

    public Interception(int rival, int iteration, Position position) {
	this.rival = rival;
	this.iteration = iteration;
	this.position = position;
    }

    public double getDistance() {
	return distance;
    }

    public int getRival() {
	return rival;
    }

    public int getIteration() {
	return iteration;
    }

    public Position getPosition() {
	return position;
    }

    @Override
    public int compareTo(Interception o) {
	return ((Double) distance).compareTo(o.getDistance());
    }

}
