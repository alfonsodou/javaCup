package org.javahispano.javacup.tacticas.jvc2013.xabe;

import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Util {	
	
	public Util() {
	}
	
	public int generateRandom(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public void recover(GameSituations sp, List<Command> comandos) {
		int[] recover = sp.getRecoveryBall();
		if (recover.length > 1) 
		{
			double[] posRecuperacion = sp.getTrajectory(recover[0]);
			for (int i = 1; i < recover.length; i++) {
				comandos.add(new CommandMoveTo(recover[i],new Position(posRecuperacion[0],posRecuperacion[1])));
			}
		}
	}

	public void canShoter(GameSituations sp, List<Command> comandos){
		Position[] jugadores = sp.myPlayers();
		for (int i : sp.canKick()) {
			if (((i == 8 || i == 9 || i == 10 || i == 11) && sp.ballPosition().getY() > 25)) {
				Position goalkeeper = sp.rivalPlayers()[0];
				if (goalkeeper.getX() > 4 || goalkeeper.getX()<-4) 
				{
					comandos.add(new CommandHitBall(i, Constants.centroArcoInf, 1, 12 + generateRandom(1, 6)));
				} 
				else if (goalkeeper.getX()<4) 
				{
					comandos.add(new CommandHitBall(i,Constants.posteDerArcoInf, 1, 12 + generateRandom(1, 6)));						
				} 
				else 
				{
					comandos.add(new CommandHitBall(i, Constants.posteIzqArcoInf, 1, 12 + generateRandom(1, 6)));
				}
			} 	
			else
			{
				int count = 0;
				int jugadorDestino;
				while (
						(
							(jugadorDestino = generateRandom(0, 10)) == i || jugadores[i].getY() > jugadores[jugadorDestino].getY()
						)
							&& count < 15) {				
					count++;
				}
				if (i == jugadorDestino) 
				{
					while ((jugadorDestino = generateRandom(0,jugadores.length-1)) == i) {
					}
				}
				comandos.add(new CommandHitBall(i, jugadores[jugadorDestino], 1, generateRandom(1, 45)));
			}
		}
	}
}
