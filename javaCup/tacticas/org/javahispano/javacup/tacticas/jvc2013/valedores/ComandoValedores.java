package org.javahispano.javacup.tacticas.jvc2013.valedores;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class ComandoValedores {
	Random rand = new Random();
	LinkedList<Command> command = new LinkedList<Command>();
	int mygoals=0;
	int rivalgoals=0;
	
	public List<Command> execute(GameSituations sp) {
		command.clear();
		mygoals=sp.myGoals();
		rivalgoals=sp.rivalGoals();
		alineacion(sp);
		if(sp.isStarts()){

			int jugadorSaque=sp.ballPosition().nearestIndex(sp.myPlayers());
			if(sp.ballPosition().getY()==0)
				pasar(sp, jugadorSaque, 8);
			else
				despejar(jugadorSaque);
		}
		if(sp.isRivalStarts()){
			setAlineacion(5, sp);
		}
		if (!sp.isRivalStarts()) 
		{
			jugar(sp);
		}
		//System.out.println("Sin balon X:" +sp.ballPosition().getX()+" y: "+sp.ballPosition().getY());
		posesionBalon(sp);
		return command;
	}

	private void alineacion(GameSituations sp){
		if(mygoals>rivalgoals){
			setAlineacion(3, sp); //Attack
		}else if(mygoals==rivalgoals){
			setAlineacion(3, sp); //Attack
		}else{
			setAlineacion(2, sp); //Defense
		}
	}
	private void pasar(GameSituations sp, int jugador1, int jugador2) {
		boolean offside[] = sp.getOffSidePlayers();
		Position[] jugadores = sp.myPlayers();
		if (!offside[jugador2]) {
			command.add(new CommandHitBall(jugador1, jugadores[jugador2], rand.nextInt(20),
					rand.nextInt(30)));
		}
	}

	private void jugar(GameSituations sp) {
		int[] jugadorCercano = sp.ballPosition().nearestIndexes(sp.myPlayers(),0);
		double[] ball = sp.getTrajectory(jugadorCercano[0]);
		command.add(new CommandMoveTo(jugadorCercano[0], new Position(ball[0],
				ball[1])));
		//Defiende el portero
		if(sp.ballPosition().getY()<-40)
			command.add(new CommandMoveTo(0, new Position(ball[0],
				ball[1])));
		if(sp.ballPosition().getY()<-15){
			command.add(new CommandMoveTo(jugadorCercano[1], new Position(ball[0],
					ball[1])));
			int[] marca=sp.ballPosition().nearestIndexes(sp.rivalPlayers());
			if(jugadorCercano[2]!=0)
				command.add(new CommandMoveTo(jugadorCercano[2],sp.rivalPlayers()[marca[1]]));
			else
				command.add(new CommandMoveTo(jugadorCercano[3],sp.rivalPlayers()[marca[1]]));
		}

	}

	private void posesionBalon(GameSituations sp) {
		if(sp.isStarts()){
			int jugadorSaque=sp.ballPosition().nearestIndex(sp.myPlayers());
			despejar(jugadorSaque);
		}
		
		
		for (int i : sp.canKick()) {
			if(i!=8&&sp.ballPosition().getY()>-20)
				command.add(new CommandMoveTo(8, Constants.esqSupIzqAreaGrandeInf));
			if(i!=10&&sp.ballPosition().getY()>-20)
				command.add(new CommandMoveTo(10, Constants.esqSupIzqAreaChicaSup));
			if(i!=9&&sp.ballPosition().getY()>-20)
				command.add(new CommandMoveTo(9, Constants.esqSupIzqAreaGrandeSup));
			switch (i) {
			case 0:
				despejar(i);
				break;
			case 9:
				if(sp.ballPosition().getY()<10){
					int[] cercanos=sp.ballPosition().nearestIndexes(sp.myPlayers(),0);
					int jugador2 = cercanos[2];
					pasar(sp, i, jugador2);
				}else{
					if (rand.nextBoolean())
						command.add(new CommandHitBall(i, Constants.centroArcoSup,
								1, rand.nextInt(8) + 14));
					else
						command.add(new CommandHitBall(i,
								Constants.posteDerArcoSup, 1, rand.nextInt(8) + 14));
					break;
				}
			case 10:
				if(sp.ballPosition().getY()<30){
					int[] cercanos=sp.ballPosition().nearestIndexes(sp.myPlayers(),0);
					int jugador2 = cercanos[1];
					pasar(sp, i, jugador2);
				}else{
				if (rand.nextBoolean())
					command.add(new CommandHitBall(i, Constants.centroArcoSup,
							1, rand.nextInt(8) + 14));
				else
					command.add(new CommandHitBall(i,
							Constants.posteDerArcoSup, 1, rand.nextInt(8) + 14));
				}
				break;
			case 8:
				if(sp.ballPosition().getY()<25){
					int[] cercanos=sp.ballPosition().nearestIndexes(sp.myPlayers(),0);
					int jugador2 = cercanos[1];
					pasar(sp, i, jugador2);
				}else{
				if (rand.nextBoolean())
					command.add(new CommandHitBall(i, Constants.centroArcoSup,
							1, rand.nextInt(8) + 14));
				else
					command.add(new CommandHitBall(i,
							Constants.posteIzqArcoSup, 1, rand.nextInt(8) + 14));
				}
				break;
			default:
				//System.out.println("X:" +sp.ballPosition().getX()+" y: "+sp.ballPosition().getY()+" jugador: "+i);
				if(sp.ballPosition().getY()<-20){
					despejar(i);
				}else{
					int[] cercanos=sp.ballPosition().nearestIndexes(sp.myPlayers(),0);
					int jugador2 = cercanos[2];
					for(int tmp:cercanos){
						if(tmp>i+2){
							jugador2=tmp;
							break;
						}
					}
					pasar(sp, i, jugador2);
				}
				break;
			}

		}
	}

	

	
	private void setAlineacion(int alineacion, GameSituations sp) {
		Position[] jugadores = sp.myPlayers();
		switch (alineacion) {
		case 1:
			for (int i = 0; i < jugadores.length; i++) {
				command.add(new CommandMoveTo(i,
						AlineacionValedores.alineacion1[i]));
			}
			break;
		case 2:
			for (int i = 0; i < jugadores.length; i++) {
				command.add(new CommandMoveTo(i,
						AlineacionValedores.alineacion2[i]));
			}
			break;
		case 3:
			for (int i = 0; i < jugadores.length; i++) {
				command.add(new CommandMoveTo(i,
						AlineacionValedores.alineacion3[i]));
			}
			break;
		case 4:
			for (int i = 0; i < jugadores.length; i++) {
				command.add(new CommandMoveTo(i,
						AlineacionValedores.alineacion4[i]));
			}
			break;
		case 5:
			for (int i = 0; i < jugadores.length; i++) {
				command.add(new CommandMoveTo(i,
						AlineacionValedores.alineacion5[i]));
			}
			break;
		case 6:
			for (int i = 0; i < jugadores.length; i++) {
				command.add(new CommandMoveTo(i,
						AlineacionValedores.alineacion6[i]));
			}
			break;
		}
	}

	
	private void despejar(int i){
		if(rand.nextBoolean())
			command.add(new CommandHitBall(i, Constants.centroCampoJuego,
				1, true));
		else{
			if(rand.nextBoolean())
			command.add(new CommandHitBall(i, Constants.esqSupIzqCampo,
					1, true));
			else
			command.add(new CommandHitBall(i, Constants.esqSupDerPenalInf,
						1, true));
		}
	}
}
