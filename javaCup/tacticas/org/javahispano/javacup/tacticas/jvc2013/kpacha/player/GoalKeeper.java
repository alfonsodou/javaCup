package org.javahispano.javacup.tacticas.jvc2013.kpacha.player;



import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy.GoalSaveStrategy;

public class GoalKeeper extends Defender implements PlayerDetail {

    public GoalKeeper(String name, int number, Color skin, Color hair,
	    double speed, double shoot, double accurancy) {
	super(name, number, skin, hair, speed, shoot, accurancy);
	this.isGoalkeeper = true;
	mover = GoalSaveStrategy.getInstance();
    }

}
