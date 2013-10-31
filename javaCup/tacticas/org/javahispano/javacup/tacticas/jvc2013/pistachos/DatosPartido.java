package org.javahispano.javacup.tacticas.jvc2013.pistachos;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.engine.GameSituations;

public class DatosPartido {
	private GameSituations sp;
	private int opponentIterToBall = -2;
	private int iterToBall = -2;
	private int playerClosetBall = -1;
	private final double[][] posBall = new double[100][3];
	
	
	public void update(GameSituations sp){
		this.sp = sp;
		for (int i = 0; i < 100; i++) {
			posBall[i] = sp.getTrajectory(i);
		}
		opponentIterToBall = -2;
		iterToBall = -2;
		playerClosetBall = -2;
	}
	
	public int getOpponentIterToBall() {
		if(opponentIterToBall >=  -1)
			return opponentIterToBall;
        int it = 0;
        boolean found = false;
        Position pJug;
        double dist0, dist;
        opponentIterToBall = -1;
        PlayerDetail detalles[] = sp.rivalPlayersDetail();
        Position[] rivales = sp.rivalPlayers();
        while (!found) {
            Position posBalon = getPosBall(it);
            if (!posBalon.isInsideGameField(2)) {
            	opponentIterToBall = -1;
                return opponentIterToBall;
            }
            if(getZBall(it) <= Constants.ALTO_ARCO) {
				for (int i = 0; i < rivales.length; i++) {
                    if (getZBall(it) <= (detalles[i].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                        pJug = rivales[i];
                        dist0 = (double) it * Constants.getVelocidad(detalles[i].getSpeed()) + (detalles[i].isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                        //dist0 = (double) it * Constants.getSpeed(detalles[i].getSpeed());
                        dist = pJug.distance(posBalon);
                        if (dist0 >= dist) {
                            found = true;
                            opponentIterToBall = it;
                        }
                    }
                }
            }
            it++;
        }
        return opponentIterToBall;
    }

	
	public int getIterToBall() {
		if(iterToBall >=  -1)
			return iterToBall;
		calculateIterToBall();
        return iterToBall;
    }
	
	
	
	public int getPlayerClosetBall() {
		if(playerClosetBall >=  -1)
			return playerClosetBall;
		calculateIterToBall();
		return playerClosetBall;
	}

	private void calculateIterToBall() {
		int it = 0;
        boolean found = false;
        Position pJug;
        double dist0, dist;
        iterToBall = -1;
        PlayerDetail detalles[] = sp.myPlayersDetail();
        Position[] jugadores = sp.myPlayers();
        while (!found) {
            Position posBalon = getPosBall(it);
            if (!posBalon.isInsideGameField(2)) {
            	iterToBall = -1;
            	playerClosetBall = posBalon.nearestIndex(jugadores);
                break;
            }
            if (getZBall(it) <= Constants.ALTO_ARCO) {
				for (int i = 0; i < jugadores.length; i++) {
                    if (getZBall(it) <= (detalles[i].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                        pJug = jugadores[i];
                        dist0 = (double) it * Constants.getVelocidad(detalles[i].getSpeed()) + (detalles[i].isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON);
                        //dist0 = (double) it * Constants.getSpeed(detalles[i].getSpeed());
                        dist = pJug.distance(posBalon);
                        if (dist0 >= dist) {
                            found = true;
                            iterToBall = it;
                            playerClosetBall = i;
                        }
                    }
                }
            }
            it++;
        }		
	}
	
	public int calculateIterToBall(Position position, double z, Position[] players, PlayerDetail[] details, int[] iterToShoot) {
		int it, best = Constants.ITERACIONES;
		for (int i = 0; i < players.length; i++) {
			if((details[i].isGoalKeeper()? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON) >= z){
				it = (int) Math.ceil(Math.max(0, players[i].distance(position)- (details[i].isGoalKeeper() ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON))/Constants.getVelocidad(details[i].getSpeed()));
				//it = (int) Math.ceil((players[i].distancia(position))/Constants.getSpeed(details[i].getSpeed()));
				if (iterToShoot[i] <= it && it < best) {
	            	best = it;                            
	            }
			}			            
        }         
		return best;		
	}

	public Position getPosBall(int iter){
		if(iter>99)
		{
			double[] trayectoria = sp.getTrajectory(iter);
			return new Position(trayectoria[0], trayectoria[1]);
		}
		return new Position(posBall[iter][0], posBall[iter][1]);
			
	}
	
	public double getZBall(int iter){
		if(iter>99)
		{
			return sp.getTrajectory(iter)[2];
		}		
		return posBall[iter][2];
	}
}
