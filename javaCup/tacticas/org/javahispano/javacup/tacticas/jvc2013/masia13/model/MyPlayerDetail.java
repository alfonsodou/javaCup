package org.javahispano.javacup.tacticas.jvc2013.masia13.model;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Color;

public class MyPlayerDetail implements PlayerDetail {

    String playerName;
    int number;
    Color skinColor;
    Color hairColor;
    double speed;
    double power;
    double precision;
    boolean isGoalKeeper;

    public MyPlayerDetail(String playerName, int number, Color skinColor, Color hairColor,
            double speed, double power, double precision, boolean isGoalKeeper) {
        this.playerName = playerName;
        this.number = number;
        this.skinColor = skinColor;
        this.hairColor = hairColor;
        this.speed = speed;
        this.power = power;
        this.precision = precision;
        this.isGoalKeeper = isGoalKeeper;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Color getSkinColor() {
        return skinColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public int getNumber() {
        return number;
    }

    public boolean isGoalKeeper() {
        return isGoalKeeper;
    }

    public double getSpeed() {
        return speed;
    }

    public double getPower() {
        return power;
    }

    public double getPrecision() {
        return precision;
    }

}