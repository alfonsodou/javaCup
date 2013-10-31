package org.javahispano.javacup.tacticas.jvc2013.toulousains.model;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Position;

public class Player {
	private int index;
	private Position position;
	private PlayerDetail details;
	private int number;
	private boolean cankick;
	private double energy;
	private double acceleration;
	private int itersToKick;
	private boolean offside;
	private boolean mark;
	private double distanceToBall;

	Player(int number) {
		this.number = number;
	}

	public PlayerDetail getDetails() {
		return details;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setDetails(PlayerDetail details) {
		this.details = details;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean cankick() {
		return cankick;
	}

	public void setCankick(boolean cankick) {
		this.cankick = cankick;
	}

	public double getSpeed(int idx) {
		return details.getSpeed();
	}

	public double getPower(int idx) {
		return details.getPower();
	}

	public double getError(int idx) {
		return details.getPrecision();
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public int getItersToKick() {
		return itersToKick;
	}

	public void setItersToKick(int itersToKick) {
		this.itersToKick = itersToKick;
	}

	public boolean isOffside() {
		return offside;
	}

	public void setOffside(boolean offside) {
		this.offside = offside;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isGoalKeeper() {
		return this.index == 0;
	}

	public boolean isMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}

	public boolean isCankick() {
		return cankick;
	}

	public void setDistanceToBall(double distanceToBall) {
		this.distanceToBall = distanceToBall;
	}

	public double getDistanceToBall() {
		return distanceToBall;
	}
}