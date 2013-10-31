package org.javahispano.javacup.tacticas.jvc2013.txami;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;

public class PlayerImpl implements PlayerDetail {

	String name;
	int number;
	Color skin, hair;
	double velocity, shot, presicion;
	boolean goalKeeper;
	Position Position;

	public PlayerImpl(String name, int number, Color skin, Color hair,
			double velocity, double shot, double presicion,
			boolean portero) {
		this.name = name;
		this.number = number;
		this.skin = skin;
		this.hair = hair;
		this.velocity = velocity;
		this.shot = shot;
		this.presicion = presicion;
		this.goalKeeper = portero;
	}

	public String getPlayerName() {
		return name;
	}

	public Color getSkinColor() {
		return skin;
	}

	public Color getHairColor() {
		return hair;
	}

	public int getNumber() {
		return number;
	}

	public boolean isGoalKeeper() {
		return goalKeeper;
	}

	public double getSpeed() {
		return velocity;
	}

	public double getPower() {
		return shot;
	}

	public double getPrecision() {
		return presicion;
	}


}
