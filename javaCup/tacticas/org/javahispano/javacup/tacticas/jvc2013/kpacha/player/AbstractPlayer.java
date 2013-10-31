package org.javahispano.javacup.tacticas.jvc2013.kpacha.player;


import java.util.ArrayList;
import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.analysis.Analysis;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy.MovingStrategy;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy.PassingStrategy;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy.RunStrategy;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy.ShootingStrategy;
import org.javahispano.javacup.tacticas.jvc2013.kpacha.strategy.Strategy;

public abstract class AbstractPlayer implements PlayerDetail {

    private String name;
    protected int number;
    private Color skin, hair;
    protected double speed, shoot, accurancy, energy = 1;
    protected boolean isGoalkeeper = false;
    protected Position position;
    protected List<Command> commands = new ArrayList<Command>();

    protected Strategy shooter = ShootingStrategy.getInstance();
    protected Strategy passer = PassingStrategy.getInstance();
    protected Strategy mover = MovingStrategy.getInstance();
    protected Strategy run = RunStrategy.getInstance();

    public AbstractPlayer(String name, int number, Color skin, Color hair,
	    double speed, double shoot, double accurancy) {
	this.name = name;
	this.number = number;
	this.skin = skin;
	this.hair = hair;
	this.speed = speed;
	this.shoot = shoot;
	this.accurancy = accurancy;
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
	return isGoalkeeper;
    }

    public double getSpeed() {
	return speed;
    }

    public double getPower() {
	return shoot;
    }

    public double getPrecision() {
	return accurancy;
    }

    public int getBallAtraction() {
	return 0;
    }

    public List<Command> execute(Analysis analysis) {
	position = analysis.getGameSituations().myPlayers()[getCurrentPlayer()];
	energy = analysis.getGameSituations().getMyPlayerEnergy(
		getCurrentPlayer());
	commands.clear();
	return doExecute(analysis);
    }

    public int getCurrentPlayer() {
	return number - 1;
    }

    public Position getPosition() {
	return position;
    }

    public double getEnergy() {
	return energy;
    }

    abstract protected List<Command> doExecute(Analysis analysis);

    protected boolean canKick(Analysis analysis) {
	boolean result = false;
	for (int i : analysis.getGameSituations().canKick()) {
	    if (i == getCurrentPlayer()) {
		result = true;
		break;
	    }
	}
	return result;
    }

    protected boolean shouldShoot(Analysis analysis) {
	int nearest = position.nearestIndex(analysis.getGameSituations()
		.myPlayers());
	Position rival = analysis.getGameSituations().myPlayers()[nearest];
	double distanceToRival = position.distance(rival);
	return !analysis.getGameSituations().isStarts()
		&& position.distance(Constants.centroArcoSup) < getMaxShootingDistance()
		&& ((analysis.getGameSituations().rivalPlayersDetail()[nearest]
			.isGoalKeeper() && distanceToRival <= Constants.ANCHO_AREA_GRANDE) || (Math
			.abs(position.angle(Constants.centroArcoSup)
				- position.angle(rival)) >= Math.PI / 4 || distanceToRival > 3 * Constants.DISTANCIA_CONTROL_BALON));
    }

    protected double getMaxShootingDistance() {
	return Constants.DISTANCIA_PENAL;
    }

    protected List<Command> move(Analysis analysis) {
	return mover.execute(this, analysis);
    }

    protected List<Command> pass(Analysis analysis) {
	return passer.execute(this, analysis);
    }

    protected List<Command> shoot(Analysis analysis) {
	return shooter.execute(this, analysis);
    }

    protected List<Command> run(Analysis analysis) {
	return run.execute(this, analysis);
    }
}
