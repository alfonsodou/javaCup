package org.javahispano.javacup.model.engine;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import java.util.ArrayList;
import java.util.LinkedList;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**Esta clase provee información de la situacion del partido a las tacticas*/
public final class GameSituations {

    /**Inicializa una situacion de partido*/
    public GameSituations() {
        misJugadores = new Position[11];
        rivales = new Position[11];
        for (int i = 0; i < 11; i++) {
            misJugadores[i] = new Position();
            rivales[i] = new Position();
            iteracionesParaRematar[0][i] = 0;
            iteracionesParaRematar[1][i] = 0;
            puedenRematar[i] = false;
            puedenRematarRival[i] = false;
            miEnergia[i] = 1;
            rivalEnergia[i]=1;            
        }
    }
    /**Aceleracion de mis jugadores**/
    public double getMyPlayerAceleration(int jugIndex) {
    	return miAceleracion[jugIndex].obtenerAceleracionGlobal(); //La aceleracion se compone de las dos aceleraciones en los dos ejes
    }
    
    /**Aceleracion del rival**/
    public double getRivalAceleration(int jugIndex) {
    	return rivalAceleracion[jugIndex].obtenerAceleracionX() * rivalAceleracion[jugIndex].obtenerAceleracionY(); //La aceleracion se compone de las dos aceleraciones en los dos ejes
    }
            
    /**Energia de los jugadores**/
    public double getMyPlayerEnergy(int jugIndex) {
    	return miEnergia[jugIndex];
    }
    
    /**Energia de los jugadores rivales **/
    public double getRivalEnergy(int jugIndex) {
    	return rivalEnergia[jugIndex];
    }
  
    /**Retorna la posicion de mis jugadores*/
    public Position[] myPlayers() {
        return misJugadores;
    }
  
    /**Retorna la posicion de los jugadores rivalPlayers*/
    public Position[] rivalPlayers() {
        return rivales;
    }

    /**Array de indices de jugadores propios que pueden rematar*/
    public int[] canKick() {
        quienes.clear();
        for (int i = 0; i < 11; i++) {
            if (puedenRematar[i]) {
                quienes.add(i);
            }
        }
        int[] tmp = new int[quienes.size()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = quienes.get(i);
        }
        return tmp;
    }

    /**Array de indices de jugadores rivalPlayers que pueden rematar*/
    public int[] rivalCanKick() {
        quienes.clear();
        for (int i = 0; i < 11; i++) {
            if (puedenRematarRival[i]) {
                quienes.add(i);
            }
        }
        int[] tmp = new int[quienes.size()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = quienes.get(i);
        }
        return tmp;
    }

    /**Retorna las iteraciones que restan para que mis jugadores puedan rematar*/
    public int[] iterationsToKick() {
        return iteracionesParaRematar[0];
    }

    /**Retorna las iteraciones que restan para que los rivalPlayers puedan rematar*/
    public int[] rivalIterationsToKick() {
        return iteracionesParaRematar[1];
    }

    /**Retorna la configuración de mis jugadores*/
    public PlayerDetail[] myPlayersDetail() {        
        return jugadores[0];
    }

    /**Retorna la configuración de los jugadores rivalPlayers*/
    public PlayerDetail[] rivalPlayersDetail() {
        return jugadores[1];
    }

    /**Retorna la posición del ballPosition*/
    public Position ballPosition() {
        return balon; 
        	
    }

    /**Retorna la altura del ballPosition*/
    public double ballAltitude() {
        return alturaBalon;
    }

    /**Retorna la cantidad de goles convertidos*/
    public int myGoals() {
        return golesMios;
    }

    /**Retorna la cantidad de goles convertidos por el rival*/
    public int rivalGoals() {
        return golesContrarios;
    }

    /**Retorna true si tengo que realizar un saque*/
    public boolean isStarts() {
        return saco;
    }

    /**Retorna true si el rival realizara un saque*/
    public boolean isRivalStarts() {
        return sacaRival;
    }

    /**Retorna el numero de iteraciones cursadas en el partido, el total de iteraciones esta dado por Constants.ITERACIONES
     */
    public int iteration() {
        return iteracion;
    }

    /**Retorna un array con las coordenadas x,y,z de la trayectoria del ballPosition en la
    iteration indicada.*/
    public double[] getTrajectory(int iteracion) {
        double time = (iteracion + this.iteracion - t0) / 60d;
        double radio = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double z = trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
        double x = x0 + radio * Math.cos(a0);
        double y = y0 + radio * Math.sin(a0);
        if (invert) {
            x = -x;
            y = -y;
        }
        return new double[]{x, y, z};
    }
    
    /**Retorna la distancia que recorrera el jugador en la iteraccion que se le pasa como parametro.
     * Devuelve la distancia recorrida en dicha iteraccion, no la suma de todas las anteriores.**/
    public double distanceIter(int playerIndex, int iter, boolean isSprint){
    	
    	//Obtenemos la aceleracion con la que el jugador llegara a la iteraccion
    	//Debe ser igual a la aceleracion
    	double acelIter = 1;
    	
    	//Calculamos la aceleracion en cada uno de los ejes
    	double incrementoAcel = (Constants.ACELERACION_INCR * iter);
    	
    	double acelXiter = miAceleracion[playerIndex].obtenerAceleracionX() + incrementoAcel;
    	double acelYiter = miAceleracion[playerIndex].obtenerAceleracionY() + incrementoAcel;
    	
    	//Si superan el valor maximo de 1 las establecemos en ese valor.
    	
    	acelXiter = (acelXiter > 1) ? 1 : acelXiter;
    	acelYiter = (acelYiter > 1) ? 1 : acelYiter;
    	
    	//Finalmente calculamos la aceleracion global en esa iteraccion
    	
    	acelIter = acelXiter * acelYiter;
    	
    	//Obtenemos la energia en la iteraccion: 
    	
    	//Calculamos la reduccion de la energia en las iteracciones anteriores
    	double reduccionEnergia = (iter * Constants.ENERGIA_DIFF);
    	
    	//Calculamos la energia en esa iteraccion
    	double energiaIter = miEnergia[playerIndex] - reduccionEnergia;
    	
    	//Si es inferior al minimo la establecemos en el minimo
    	
    	energiaIter = (energiaIter < Constants.ENERGIA_MIN) ? Constants.ENERGIA_MIN : energiaIter;
    	
    	//Si el jugador esta sprintando
    	double sprint = (isSprint && energiaIter > Constants.SPRINT_ENERGIA_MIN) ? Constants.SPRINT_ACEL : 1;
    	
    	//Obtenemos la distancia recorrida en la iteraccion
    	return Constants.getVelocidad(myPlayersDetail()[playerIndex].getSpeed()) * energiaIter * acelIter * sprint;
    	    	
    }
    
    /**Devuelve la distancia total recorrida por un jugador en las iteracciones que se le pasa como parametro sin tener en cuenta el factor sprint**/
    public double distanceTotal(int playerIndex, int iter){
    	
    	double distTotal = 0;
    	
    	for (int it = 0; it < iter; ++it){
    		distTotal += distanceIter(playerIndex, iter, false);
    	}
    		
    	return distTotal;
    }
    
    

    /**retorna un array donde el primer elemento es la iteration donde se puede recuperar el ballPosition,
    los siguientes números corresponden a los indices de los jugadores que pueden recuperar el ballPosition,
    ordenados desde el mas cercano al mas lejano del punto de recuperación.*/
    @Deprecated
    public int[] getRecoveryBall() {
        int it = 0;
        boolean found = false;
        Position pJug;
        double dist0, dist;
        int idxFound = -1;
        LinkedList<Double> founds = new LinkedList<Double>();
        PlayerDetail detalles[] = myPlayersDetail();
        
        while (!found) {
            double[] posBalon = getTrajectory(it);//Posicion del balon en la iteraccion it
            if (!(new Position(posBalon[0], posBalon[1])).isInsideGameField(2)) {
                return new int[]{};
            }
            if (posBalon[2] <= Constants.ALTO_ARCO) {
                for (int i = 0; i < misJugadores.length; i++) {
                    if (posBalon[2] <= (detalles[i].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                        pJug = misJugadores[i];
                        dist0 = distanceTotal(i, it); //Distancia que puede recorrer el jugador en it iteracciones
                        dist = pJug.distance(new Position(posBalon[0], posBalon[1])); //Distancia entre el jugador y el balon
                        if (dist0 >= dist) {
                            found = true;
                            founds.add(dist);
                            founds.add((double) i);
                            idxFound = it;
                        }
                    }
                }
            }
            it++;
        }
        for (int i = 2; i < founds.size(); i = i + 2) {
            for (int j = 0; j < i; j = j + 2) {
                if (founds.get(i) < founds.get(j)) {
                    dist0 = founds.get(i);
                    dist = founds.get(i + 1);
                    founds.set(i, founds.get(j));
                    founds.set(i + 1, founds.get(j + 1));
                    founds.set(j, dist0);
                    founds.set(j + 1, dist);
                }
            }
        }
        for (int i = founds.size() - 1; i >= 0; i = i - 2) {
            founds.remove(i - 1);
        }
        founds.add(0, (double) idxFound);
        int[] result = new int[founds.size()];
        for (int i = 0; i < founds.size(); i++) {
            result[i] = (int) founds.get(i).doubleValue();
        }
        return result;
    }

    /**Retorna la velocidad del jugador de indice idx*/
    public double getMyPlayerSpeed(int idx) {
        return myPlayersDetail()[idx].getSpeed();
    }

    /**Retorna la potencia del remate del jugador de indice idx*/
    public double getMyPlayerPower(int idx) {
        return myPlayersDetail()[idx].getPower();
    }

    /**Retorna el error del jugador de indice idx*/
    public double getMyPlayerError(int idx) {
        return myPlayersDetail()[idx].getPrecision();
    }

    /**Retorna la velocidad del rival de indice idx*/
    public double getRivalPlayerSpeed(int idx) {
        return rivalPlayersDetail()[idx].getSpeed();
    }

    /**Retorna  la potencia del remate del rival de indice idx*/
    public double getRivalPlayerPower(int idx) {
        return rivalPlayersDetail()[idx].getPower();
    }

    /**Retorna el error del rival de indice idx*/
    public double getRivalPlayerError(int idx) {
        return rivalPlayersDetail()[idx].getPrecision();
    }
    
    /**Retorna un array indicando verdadero o falso si el jugador del indice correspondiente esta fuera de juego**/
    public boolean[] getOffSidePlayers(){
    	calculateOffSidePlayers();
    	return offSidePlayers;
    }
    
    
    ///////////// Metodo protegidos
    
       
    /**Establece quienes pueden rematar*/
    protected void set(boolean puedeRematar[], boolean puedeRematarRival[]) {
        for (int i = 0; i < 11; i++) {
            this.puedenRematar[i] = puedeRematar[i];
            this.puedenRematarRival[i] = puedeRematarRival[i];
        }
    }

    /**Usada internamente para establecer la situacion en una iteration*/
    protected void set(Position balon, double alturaBalon, int golesMios, int golesContrarios, int iteracion, Position[] mios, Position[] contrarios, Aceleracion[] miAceleracion, Aceleracion[] rivalAceleracion, double[] miEnergia, double[] rivalEnergia, boolean saco, boolean sacaRival, int[] iterGolpearBalonLocal, int[] iterGolpearBalonVisita, AbstractTrajectory trayectoria, double x0, double y0, double t0, double a0, int iterReal, boolean invert) {
        this.balon = balon;
        this.golesMios = golesMios;
        this.golesContrarios = golesContrarios;
        this.iteracion = iteracion;
        this.alturaBalon = alturaBalon;

        for (int i = 0; i < 11; i++) {
            this.misJugadores[i] = mios[i];
            this.rivales[i] = contrarios[i];
            this.iteracionesParaRematar[0][i] = iterGolpearBalonLocal[i];
            this.iteracionesParaRematar[1][i] = iterGolpearBalonVisita[i];
            this.miAceleracion[i] = miAceleracion[i];
            this.rivalAceleracion[i] = rivalAceleracion[i];
            this.miEnergia = miEnergia;
            this.rivalEnergia = rivalEnergia;
        }
        this.saco = saco;
        this.sacaRival = sacaRival;
        this.trayectoria = trayectoria;
        this.x0 = x0;
        this.y0 = y0;
        this.t0 = t0;
        this.a0 = a0;
        this.iterReal = iterReal;
        this.invert = invert;
    }

    /**Usada internamente, para establecer los detalles de los jugadores*/
    protected void set(PlayerDetail[][] jugadores) {
        this.jugadores = jugadores;
    }
    /**
	 * Calcula los jugadores que estan en el pase actual y rellena el array outOfGamePlayers con dicha informaci�n.
	 * @param isLocal Verdadero si es el equipo local el que ha rematado
	 */
private	void calculateOffSidePlayers() {
	
	    Position[] positionPlayers = myPlayers();
	    Position[] rivalPlayerPosition = rivalPlayers();
	    
	    //Posicion que marca el fuera de juego (ultimo defensa o balon)
	    Position posOffSide = balon;
	    Position lastPosOffSide = balon;
	
	    
	    //Vemos si hay algun defensa por delante del balon y asignamos su posicion a posOffSide (deben de haber dos jugadores del equipo contrario por delante del balon para evitar el fuera de juego)
	    for (int x = 0; x < 11; ++x) {
	
	        
            if (lastPosOffSide.getY() < rivalPlayerPosition[x].getY()) {
                posOffSide = lastPosOffSide;
                lastPosOffSide = rivalPlayerPosition[x];
            } else {
                if (posOffSide.getY() < rivalPlayerPosition[x].getY()) {
                    posOffSide = rivalPlayerPosition[x];
                }
            }
	        
	    }
	
	    if (posOffSide == null) posOffSide = balon;//Si no hay ning�n defensor por delante el fuera de juego lo marca el balon
	
	    //No puede producirse fuera de juego dentro de la mitad de su campo.
	    if (posOffSide.getY() < 0)  posOffSide = new Position(0,0);
	
	    //Rellenamos el array outOfGamePlayers indicando que jugadores del equipo que ataca estan fuera de juego con relacion a la posicion calculada anteriormente
	    //y teniendo en cuenta que cuando se saca desde banda o desde puerta no es fuera de juego.
	    for (int x = 0; x < 11; ++x) {
	        offSidePlayers[x] = (positionPlayers[x].getY() > posOffSide.getY());
	    }   
	
	}
	private Position balon = new Position();
    private double alturaBalon;
    private int golesMios, golesContrarios, iteracion, iterReal;
    private Position[] misJugadores, rivales;
    private boolean saco, sacaRival;
    private ArrayList<Integer> quienes = new ArrayList<Integer>(11);
    private PlayerDetail[][] jugadores;
    private int[][] iteracionesParaRematar = new int[2][11];
    private boolean[] puedenRematar = new boolean[11];
    private boolean[] puedenRematarRival = new boolean[11];
    private AbstractTrajectory trayectoria;
    private double x0, y0, t0, a0;
    private boolean invert;
    private double miEnergia[] = new double[11];
    private double rivalEnergia[] = new double[11];
    private Aceleracion miAceleracion[] = new Aceleracion[11];
    private Aceleracion rivalAceleracion[] = new Aceleracion[11];
    private boolean offSidePlayers[] = new boolean[11];

}
