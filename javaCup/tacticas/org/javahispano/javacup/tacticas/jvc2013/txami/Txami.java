package org.javahispano.javacup.tacticas.jvc2013.txami;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Txami implements Tactic {

	private LinkedList<Command> comandos = new LinkedList<Command>();

	// VARIABLES GENERALES
	private Position[] myPlayers = null;
	private boolean[] offsidePlayers = null;
	private int[] recuperadores = null;

	@Override
	public TacticDetail getDetail() {
		return new TacticDetailImpl();
	}

	@Override
	public Position[] getStartPositions(GameSituations sp) {
		return Alineaciones.SACAR;
	}

	@Override
	public Position[] getNoStartPositions(GameSituations sp) {
		return Alineaciones.RECIBIR;
	}

	@Override
	public List<Command> execute(GameSituations sp) {
		/**
		 * Limpiamos las órdenes
		 */
		comandos.clear();

		// VARIABLES GENERALES
		myPlayers = sp.myPlayers();
		offsidePlayers = sp.getOffSidePlayers();
		recuperadores = sp.getRecoveryBall();

		/**
		 * Generamos las nuevas órdenes
		 */
		comandos.addAll(movimientos(sp)); // Movimientos
		comandos.addAll(disparos(sp)); // Disparos

		/**
		 * Devolvemos las órdenes
		 */
		return comandos;
	}

	/**
	 * Obtiene los movimientos a realizar
	 * 
	 * @param sp
	 *            GameSituations
	 * @return LinkedList<Command> movimientos
	 */
	private List<Command> movimientos(GameSituations sp) {
		LinkedList<Command> movimientos = new LinkedList<Command>();

		/**
		 * Por defecto: Los jugadores se colocan en la alineación JUEGO
		 */
		for (int i = 0; i < myPlayers.length; i++) {
			movimientos.add(new CommandMoveTo(i, Alineaciones.JUEGO[i]));
		}

		/**
		 * Sacamos: Los jugadores se colocan en la alineación SACAR
		 */
		if (sp.isStarts()) {
			for (int i = 0; i < myPlayers.length; i++) {
				movimientos.add(new CommandMoveTo(i, Alineaciones.SACAR[i]));
			}
		}
		/**
		 * Saca el rival: Los jugadores se colocan en la alineación RECIBIR
		 */
		else if (sp.isRivalStarts()) {
			for (int i = 0; i < myPlayers.length; i++) {
				movimientos.add(new CommandMoveTo(i, Alineaciones.RECIBIR[i]));
			}
		}		
		
		// Retrasamos a los jugadores en fuera de juego
		for (int i = 0; i < myPlayers.length; i++) {
			if (offsidePlayers[i]){				
				movimientos.add(new CommandMoveTo(i, new Position(myPlayers[i].getX(), myPlayers[i].getY() - 5) ));
			}
		}

		/**
		 * Recuperar balón
		 */
		if (!sp.isRivalStarts()) {
			// Si existe posibilidad de recuperar el balon
			if (recuperadores.length > 1) {
				// Obtiene las coordenadas del balon en el instante donde
				// se puede recuperar el balon
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				// Recorre la lista de jugadores que pueden recuperar
				for (int i = 1; i < recuperadores.length; i++) {
					// Ordena a los jugadores recuperadores que se ubique
					// en la posicion de recuperacion (SPRINT)
					movimientos.add(new CommandMoveTo(
							recuperadores[i],
							new Position(posRecuperacion[0], posRecuperacion[1]),
							true));
				}
			}

		}		

		return movimientos;
	}

	/**
	 * Obtiene los disparos (pases) a realizar
	 * 
	 * @param sp
	 *            GameSituations
	 * @return List<Command> disparos
	 */
	private List<Command> disparos(GameSituations sp) {
		LinkedList<Command> disparos = new LinkedList<Command>();

		// Instancia un generador aleatorio
		Random r = new Random();

		for (int i : sp.canKick()) {
			if (Utils.esDelantero(i)) { /** Delantero (TIRO)**/
				Position pos = null;
				if (i == Utils.KUIK){
					pos = new Position(Constants.posteDerArcoSup.getX()-2,Constants.posteDerArcoSup.getY());
				} else if (i == Utils.SAGU){
					pos = new Position(Constants.posteIzqArcoSup.getX()+2,Constants.posteIzqArcoSup.getY());
				}
				
				disparos.add(new CommandHitBall(i,pos,1,10));
			} else if (Utils.esMediapunta(i)) { /** Mediapunta (TIRO)**/
				int variacion = 0;
				if (r.nextBoolean()){
					variacion = 1;
				} else {
					variacion = -1;
				}				
				Position pos = new Position(Constants.centroArcoSup.getX() + variacion,Constants.centroArcoSup.getY());		
				disparos.add(new CommandHitBall(i,pos,1,17.5));
			} else { /** Resto de Jugadores: Medios, Defensa, Portero: PASE **/
				int jugadorDestino = 0;
				if (r.nextBoolean()){
					jugadorDestino = Utils.KUIK;
				} else {
					jugadorDestino = Utils.SAGU;
				}
				
				// Si el delantero está en fuera de juego, pasamos al mediapunta
				if (offsidePlayers[jugadorDestino]){
					jugadorDestino = Utils.SAEZ;
				}
				
					// Pase
					double newX = 0;
					if (myPlayers[jugadorDestino].getX() > 0){
						newX = myPlayers[jugadorDestino].getX() - 5;
					} else {
						newX = myPlayers[jugadorDestino].getX() + 5;
					}
					
					double newY = myPlayers[jugadorDestino].getY() - 3;
					
					
					Position newPos = new Position(newX, newY);
					
					// Pasamos el balón al lado del 'jugadordestino' y movemos al 'jugadordestino' a esa posicion
					disparos.add(new CommandHitBall(i, newPos, 1, 45));
					disparos.add(new CommandMoveTo(jugadorDestino, newPos, true));
			}

		}

		return disparos;
	}

}
