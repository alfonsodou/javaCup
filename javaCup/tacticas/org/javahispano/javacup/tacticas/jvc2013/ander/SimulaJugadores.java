package org.javahispano.javacup.tacticas.jvc2013.ander;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class SimulaJugadores {

	/**
	 * @param args
	 */
	public static int obtenerIteracionesMinimasDeJugadorHastaPos(GameSituations spAct,Position posObj,int indiceJugador,boolean deMiEquipo,boolean conSprint){
		double realSpedMax;
		Position posJugador;
		double energy;
		double aceleracion;
		double acelSprint=1;
		if(conSprint){
			acelSprint=1.2;
		}
		if(deMiEquipo){
			realSpedMax=0.25+0.25*spAct.getMyPlayerSpeed(indiceJugador);
			posJugador=spAct.myPlayers()[indiceJugador];
			energy=spAct.getMyPlayerEnergy(indiceJugador);
			aceleracion=spAct.getMyPlayerAceleration(indiceJugador);
		}else{
			realSpedMax=0.25+0.25*spAct.getRivalPlayerSpeed(indiceJugador);
			posJugador=spAct.rivalPlayers()[indiceJugador];
			energy=spAct.getRivalEnergy(indiceJugador);
			aceleracion=spAct.getRivalAceleration(indiceJugador);
		}
		aceleracion=0.6;
		double distanciaRecorrer=posJugador.distance(posObj);
		double distanciaRecorrida=0;
		int iteraciones=0;
		for(int i=0;i<500;i++){
			if(distanciaRecorrer<=distanciaRecorrida+Constants.DISTANCIA_CONTROL_BALON){
				iteraciones=i;
				break;
			}else{
				if(energy<Constants.SPRINT_ENERGIA_MIN){
					acelSprint=1;
				}
				distanciaRecorrida+=realSpedMax*energy*aceleracion*acelSprint;
				energy=energy-Constants.ENERGIA_DIFF;
				if(acelSprint!=1){
					energy=energy-Constants.SPRINT_ENERGIA_EXTRA;
				}
				if(energy<Constants.ENERGIA_MIN){
					energy=Constants.ENERGIA_MIN;
				}
				aceleracion=aceleracion+Constants.ACELERACION_INCR;
				aceleracion=Math.min(aceleracion, 1);
			}
		}
			
		
		
		return iteraciones;
	}
	public static int distanciaMinEquipoAPosicion(GameSituations spAct,Position posObj,boolean deMiEquipo,boolean conSprint){
		int min=Integer.MAX_VALUE;
		for(int i=0;i<11;i++){
			min=Math.min(obtenerIteracionesMinimasDeJugadorHastaPos(spAct,posObj,i,deMiEquipo,conSprint), min);
		}
		return min;
				
	}
}
