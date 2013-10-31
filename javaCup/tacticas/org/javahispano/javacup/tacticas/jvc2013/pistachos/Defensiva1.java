package org.javahispano.javacup.tacticas.jvc2013.pistachos;

import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.engine.GameSituations;

public class Defensiva1 {
	private GameSituations sp;
	private List<Command> comandos;
	private final DatosPartido data;
	private final int goalkeeper = 0;
	private int countDefense = 5;
	
	public void setCountDefense(int countDefense) {
		this.countDefense = countDefense;
	}

	public Defensiva1(List<Command> comandos, DatosPartido data) {
		super();		
		this.comandos = comandos;
		this.data = data;
	}
	
	public void execute(GameSituations sp, boolean attacking){
		this.sp = sp;
		int[] opponents = Constants.centroArcoInf.nearestIndexes(sp.rivalPlayers());
		Position[] players = sp.myPlayers();
		boolean[] pressing = new boolean[11];
		int playerToBall = getBall(attacking);
		pressing[playerToBall] = true;
		for(int opponent: opponents){
			int[] closest = sp.rivalPlayers()[opponent].nearestIndexes(sp.myPlayers());
			for(int player: closest){
	    		if((isDefender(player, playerToBall))&& !pressing[player]){
	    			players[player] = press(opponent, attacking);
	    			comandos.add(new CommandMoveTo(player, players[player]));
	    			pressing[player] = true;
	    			break;
	    		}
	    	}
		}		
	}

	private int getBall(boolean attacking) {
		if(data.getIterToBall() >= 0)
			comandos.add(new CommandMoveTo(data.getPlayerClosetBall(), data.getPosBall(data.getIterToBall())));
		if(!attacking){
			int opponentIterToBall = data.getOpponentIterToBall();
			if(opponentIterToBall >= 0) {
				Position pos = data.getPosBall(opponentIterToBall).moveAngle(data.getPosBall(opponentIterToBall).angle(Constants.centroArcoInf),Constants.DISTANCIA_CONTROL_BALON);
				comandos.add(new CommandMoveTo(pos.nearestIndex(sp.myPlayers(), goalkeeper), pos));
				return pos.nearestIndex(sp.myPlayers(), goalkeeper);
			}			
		}
		return data.getPlayerClosetBall();
	}

	private Position press(int opponent, boolean attacking){
		if(attacking)
			return  sp.rivalPlayers()[opponent].moveAngle(sp.rivalPlayers()[opponent].angle(sp.ballPosition()), Constants.DISTANCIA_CONTROL_BALON - Constants.VELOCIDAD_MAX);
		return  sp.rivalPlayers()[opponent].moveAngle(sp.rivalPlayers()[opponent].angle(Constants.centroArcoInf),Constants.DISTANCIA_CONTROL_BALON - Constants.VELOCIDAD_MAX);
	}

	private boolean isDefender(int player, int playerToBall) {
		int countDefense = this.countDefense + (playerToBall< this.countDefense? 1 : 0);
		return player != goalkeeper && (player < countDefense);
	}
}
