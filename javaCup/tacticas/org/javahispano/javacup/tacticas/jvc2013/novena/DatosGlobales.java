package org.javahispano.javacup.tacticas.jvc2013.novena;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author yoemny
 */
public class DatosGlobales {

    private Position balon0 = null;
    private Position balon1 = null;
    private Position posicionPreviaPortero = null;
    private double   alturaPreviaBalon = 0;
    private int      golesRival = 0;
    int iteracionSaque = 0;

    private int      estadoJuego    = DatosUtiles.DESCONOCIDO;
    
    PosicionBalon puntoSalida, puntoFuera = null; 

    private int golpeoBalon = -1;
    private int referenciaSaque = -1;
    private boolean enMoviemiento = false;
    private Position posCorreccion = null;
    private double[]    anguloMovimientoRivales = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
    private Position[]  posicionPreviaRivales = null;

    private int marcajes[] = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    private int[] iteracionesRecuperar = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    
    private Queue<PosicionBalon> trayectoriaBalon;
    private Aceleracion[] aceleraciones = new Aceleracion[11];
    private ParametrosPase pPase = null;
    private boolean corregirPosicionPase = false;
    
    private double posicionFueraJuego;
    private double posicionFueraJuegoRival;

    public Aceleracion[] getAceleraciones() {
		return aceleraciones;
	}

	public void setAceleraciones(Aceleracion[] aceleraciones) {
		this.aceleraciones = aceleraciones;
	}

	public Position getPosCorreccion() {
		return posCorreccion;
	}

	public void setPosCorreccion(Position posCorreccion) {
		this.posCorreccion = posCorreccion;
	}

	public ParametrosPase getPPase() {
		return pPase;
	}

	public void setPPase(ParametrosPase pPase) {
		this.pPase = pPase;
	}

	public double getPosicionFueraJuego() {
		return posicionFueraJuego;
	}

	public void setPosicionFueraJuego(double posicionFueraJuego) {
		this.posicionFueraJuego = posicionFueraJuego;
	}

	
	/**
	 * @return the posicionFueraJuegoRival
	 */
	public double getPosicionFueraJuegoRival() {
		return posicionFueraJuegoRival;
	}

	/**
	 * @return the itreracionesRecuperar
	 */
	public int getIteracionesRecuperar(int indiceJugador) {
		return iteracionesRecuperar[indiceJugador];
	}

	public DatosGlobales() {
        trayectoriaBalon = new LinkedList<PosicionBalon>();
        posicionFueraJuego = Constants.LARGO_CAMPO_JUEGO/2;
        for (int i = 0; i < 11; i++) {
        	aceleraciones[i] = new Aceleracion();
        }
        balon0 = new Position();
        balon1 = new Position();
    }

    public boolean isCorregirPosicionPase() {
		return corregirPosicionPase;
	}

	/**
	 * @return the iteracionSaque
	 */
	public int getIteracionSaque() {
		return iteracionSaque;
	}

	/**
	 * @param iteracionSaque the iteracionSaque to set
	 */
	public void setIteracionSaque(int iteracionSaque) {
		this.iteracionSaque = iteracionSaque;
	}

	public void setCorregirPosicionPase(boolean corregirPosicionPase) {
		this.corregirPosicionPase = corregirPosicionPase;
	}

	public Queue<PosicionBalon> getTrayectoriaBalon(){
        return trayectoriaBalon;
    }

    public int cantidadAnguloMovimientoRivales(){
        return anguloMovimientoRivales.length;
    }

    public double getAnguloMovimientoRival(int indice){
        return anguloMovimientoRivales[indice];
    }


    public void actualizarAceleracion(List<Command> comandos, GameSituations sp){
    	Command.CommandType tipo;//almacena un tipo de comando: irA o golpearBalon
        int indJugador;//almacena un indice de jugador para ser usado en un comando
        
        comandos = limpiarComandos(comandos, sp);
        
        for (Command c : comandos) {//recorre todos los comandos
        	tipo = c.getCommandType();//obtiene el tipo del comando
            indJugador = c.getPlayerIndex();//obtiene el indice del jugador indicado en el comando
        	if (tipo.equals(Command.CommandType.MOVE_TO)) {
        		CommandMoveTo cia = (CommandMoveTo) c;//obtiene el comando irA
        		Position p = cia.getMoveTo();//Obtiene la posicion destino del comando
        		aceleraciones[indJugador].actualizar(p);
        	}
        }
    }
    
    public List<Command> limpiarComandos(List<Command> comandos, GameSituations sp) {
        
    	List<Command> list = new ArrayList<Command>();//lista de comandos
        
        if (comandos == null) return list;
        
        Command c;
        
        for (int i = comandos.size() - 1; i >= 0; i--) {//recorre los comandos en orden inverso
        
        	c = comandos.get(i);//obtiene un comando
                              
            //Condiciones por la que un comando no es valido y no se ejecuta
            if ( list.contains(c)  //si el comando esta duplicado
                || (c.getPlayerIndex() < 0 && c.getPlayerIndex() > 10) //indice dentro del rango
                 || (c.getCommandType() == Command.CommandType.MOVE_TO && ((CommandMoveTo) c).getMoveTo() == null) //irA no nulo
                 || ( (c.getCommandType() == Command.CommandType.HIT_BALL && ((CommandHitBall) c).isCoordinate() && ((CommandHitBall) c).getDestiny() == null) ) )  //destino del ballPosition no nulo
                 
                 continue;

                
                 if (c.getCommandType() == Command.CommandType.MOVE_TO) {
                         CommandMoveTo cia = (CommandMoveTo) c;
                         
                         if (Double.isNaN(cia.getMoveTo().getX()) || Double.isNaN(cia.getMoveTo().getY()))
                            continue;

						//Si todavia no se ha efectuado el saque ningun jugador del equipo contrario puede aproximarse al balon a menos de la distancia minima establecida
						if ( sp.isRivalStarts() )
						{
							Position [] posJug = sp.myPlayers();
		
							if ( sp.ballPosition().distance(posJug[c.getPlayerIndex()]) < Constants.DISTANCIA_SAQUE + 1 )
								continue;
						}                 
                 }

			

            list.add(c);//agrego el comando a la lista
        }
       
        return list;
    }
    
    /**
	 * @param golpeoBalon the realizoPaseJugador to set
	 */
	public void setgolpeoBalon(int golpeoBalon) {
		this.golpeoBalon = golpeoBalon;
	}

	public void actualizarDatos(GameSituations sp){
        actualizarEstadoDelJuego(sp);
        actualizarPosicionesRival(sp);
        actualizarTrayectoriaBalon(sp);
        actualizarPosicionFueraJuego(sp);
        actualizarPosicionFueraJuegoRival(sp);
        
        balon0 = balon1;
        balon1 = sp.ballPosition();
        
        if (balon0.equals(balon1))
            enMoviemiento = false;
        else
            enMoviemiento = true;
        
        
        if (sp.iteration() == 0)
        	iniciarAcelerecion(sp);
        
        
        actualizarIteracionesRecuperacion(sp);
        
        golpeoBalon = -1;
        
        
        if (sp.isStarts() && iteracionSaque == 0){
        	iteracionSaque = Constants.ITERACIONES_SAQUE/3;
        }
        
        if (iteracionSaque > 0)
        	iteracionSaque--;
        
    }

    
    /**
	 * @return the referenciaSaque
	 */
	public int getReferenciaSaque() {
		return referenciaSaque;
	}

	public void actualizarIteracionesRecuperacion(GameSituations sp){
    	int[] iterRec = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    	Iterator<PosicionBalon> iter = trayectoriaBalon.iterator();
    	
    	if (iter.hasNext())
    		iter.next();
    	
    	int iteracion = 1;
    	
    	while (iter.hasNext()){
    		PosicionBalon pb = iter.next();
    		for (int indice = 0; indice < 11 ; indice++){
    			if ( iterRec[indice]==-1 ){
        			boolean esPortero = sp.myPlayersDetail()[indice].isGoalKeeper();
                    double alturaControl = (esPortero) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;
                    double distanciaControl = (esPortero) ? Constants.DISTANCIA_CONTROL_BALON_PORTERO : Constants.DISTANCIA_CONTROL_BALON;
                    Aceleracion aceleracion = aceleraciones[indice];
                    double distanciaRecorrida = Utiles.distanceTest(sp, indice, aceleracion, pb.getPosicion(), iteracion, false);
                    double distanciaPB = sp.myPlayers()[indice].distance(pb.getPosicion());
                    if ( distanciaRecorrida > distanciaPB-distanciaControl && pb.getAltura() < alturaControl )
                    	iterRec[indice] = iteracion;
    				
    			}
    			
    		}
    		iteracion++;
    		
    	}
    	
    	iteracionesRecuperar = iterRec;
    }
    
    public void iniciarAcelerecion(GameSituations sp){
    	for (int i=0 ; i<11 ; i++)
    		aceleraciones[i].setPosJugador(sp.myPlayers()[i]);
    		
    }
    public boolean isEnMoviemiento() {
        return enMoviemiento;
    }

    private void actualizarPosicionFueraJuegoRival(GameSituations sp){
    	double pos = -20;
    	for (int i=0 ; i < 4 ; i++){
    		int indiceDefensa = i;
    		if ( tieneMarca(indiceDefensa) ){
    			int marca = marcajes[indiceDefensa];
    			Position posMarca = sp.rivalPlayers()[marca];
    			if ( posMarca.getY() < pos )
    				pos = posMarca.getY();
    		}
        		
    	}
    	posicionFueraJuegoRival = pos + 0.51;
    }
    
    private void actualizarPosicionFueraJuego(GameSituations sp){
    	
    	Position[]  rivales= new Position[sp.rivalPlayers().length];;
    	System.arraycopy(sp.rivalPlayers(), 0, rivales, 0, sp.rivalPlayers().length);
    	for (int i=0 ; i<2 ; i++)
    		for (int j=i+1 ; j<rivales.length ; j++){
    			if (rivales[j].getY() > rivales[i].getY()){
    				Position temp = rivales[i];
    				rivales[i] = rivales[j];
    				rivales[j] = temp;
    				
    			}
    	}
    	
    	posicionFueraJuego = rivales[1].getY() - 1;
    	
    	if (sp.ballPosition().getY()> posicionFueraJuego)
    		posicionFueraJuego = sp.ballPosition().getY();
    	
    	if (posicionFueraJuego < 0)
    		posicionFueraJuego = 0;
    	
    }
    
    public void actualizarPosicionesRival(GameSituations sp){
        if (posicionPreviaRivales == null){
            posicionPreviaRivales = new Position[sp.rivalPlayers().length];
            System.arraycopy(sp.rivalPlayers(), 0, posicionPreviaRivales, 0, sp.rivalPlayers().length);
        }else{
            for (int i=0 ; i<sp.rivalPlayers().length ; i++){
                if (posicionPreviaRivales[i].equals(sp.rivalPlayers()[i]))
                    anguloMovimientoRivales[i] = Double.MAX_VALUE;
                else
                    anguloMovimientoRivales[i] = posicionPreviaRivales[i].angle(sp.rivalPlayers()[i]);

                posicionPreviaRivales[i] = sp.rivalPlayers()[i];
            }
        }
        
    }

    public void actualizarEstadoDelJuego(GameSituations sp){
        
    	int maxIter = maximoItearionesToKick(sp, false);
    	int maxIterRival = maximoItearionesToKick(sp, true);
    	
    	
    	if (golpeoBalon != -1 && sp.iterationsToKick()[golpeoBalon] == 0)
        	estadoJuego = DatosUtiles.DEFENDIENDO;
    	else if (sp.rivalCanKick().length != 0 && sp.canKick().length == 0){ // el rival va a comenzar una jugada ofensiva
            estadoJuego = DatosUtiles.DEFENDIENDO;
        }else if (sp.rivalCanKick().length == 0 && sp.canKick().length != 0){ // voy a comenzar una jugada ofensiva
            estadoJuego = DatosUtiles.ATACANDO;
        }else if (sp.isStarts())
        	estadoJuego = DatosUtiles.ATACANDO;
        else if (sp.isRivalStarts())
        	estadoJuego = DatosUtiles.DEFENDIENDO;
        else if (maxIter > 0 && maxIter > maxIterRival )
        	estadoJuego = DatosUtiles.ATACANDO;
        else if (maxIterRival > 0 && maxIterRival > maxIter )
        	estadoJuego = DatosUtiles.DEFENDIENDO;
        else if (!enMoviemiento && !sp.isStarts()){
    		estadoJuego = DatosUtiles.DEFENDIENDO;
    	}
    }
    
    private int maximoItearionesToKick(GameSituations sp, boolean rival){
    	int[] iteToKick;
    	if (rival)
    		iteToKick = sp.rivalIterationsToKick();
    	else
    		iteToKick = sp.iterationsToKick();
    	int maxValue = 0;
    	for (int i=0 ; i<11 ; i++)
    		if (iteToKick[i] > maxValue)
    			maxValue = iteToKick[i];
    	
    	return maxValue;
    }

    public boolean estoyAtacando(){
        return (estadoJuego == DatosUtiles.ATACANDO) ? true :  false;
         
    }
    
    public String estadoJuego(){
    	return ( estadoJuego == DatosUtiles.ATACANDO ) ? "ATACANDO" : "DEFENDIENDO";
    }

    public int getEstadoJuego(){
        return estadoJuego;
    }
    
    public void setDefendiendo(){
        estadoJuego = DatosUtiles.DEFENDIENDO;
    }

    private boolean cambioTrayectoria(GameSituations sp){
        if (trayectoriaBalon.isEmpty())
            return true;
        else{
                PosicionBalon firstBallPosition = trayectoriaBalon.peek();
                if (sp.ballPosition().getX() != firstBallPosition.getPosicion().getX() ||
    			sp.ballPosition().getY() != firstBallPosition.getPosicion().getY())
                return true;
            }

        return false;
    }

    public Position posicionEnTrayectoria(GameSituations sp, int iteracion){
    	double[] trajectory = sp.getTrajectory(iteracion+1);
    	Position p = new Position(trajectory[0], trajectory[1]);
    	return p;
    }
    
    private Queue<PosicionBalon> crearTrayectoriaBalon(GameSituations sp){
        Queue<PosicionBalon> trajectoryTemp = new LinkedList<PosicionBalon>();
        puntoSalida = null;
        puntoFuera = null;

        boolean out     = false;
        int     count   = 0;


        while (sp.ballPosition().isInsideGameField(0) && !out){
            double[] trajectory = sp.getTrajectory(count);
            if (trajectoryTemp.isEmpty())
                trajectoryTemp.add(new PosicionBalon(new Position(trajectory[0], trajectory[1]), trajectory[2]));
            else{
                PosicionBalon lastPosition = Utiles.ultimoEnCola(trajectoryTemp);

                if (trajectory[0] == lastPosition.getPosicion().getX() &&
                        trajectory[1] == lastPosition.getPosicion().getY() &&
                        trajectory[2] == lastPosition.getAltura()) {

                            out = true;
		}else{
                    PosicionBalon ballPositionInsert = new PosicionBalon(new Position(trajectory[0], trajectory[1]), trajectory[2]);
                    if (ballPositionInsert.getPosicion().isInsideGameField(0))
                        trajectoryTemp.add(ballPositionInsert);
                    else{
                    	puntoSalida = Utiles.puntoDeSalidaBalon(lastPosition, ballPositionInsert);
                        puntoFuera =  ballPositionInsert;
                        
                        out = true;
                    }

                }

            }
            count++;
        }
        
        //trajectoryTemp.poll();

        return trajectoryTemp;
    }

    public void actualizarTrayectoriaBalon(GameSituations sp){
        if (cambioTrayectoria(sp)){
            trayectoriaBalon = crearTrayectoriaBalon(sp);
        }else
            trayectoriaBalon.poll();
    }

    public boolean existeTrayectoria(){
        if (trayectoriaBalon.isEmpty())
            return false;
        else
            return true;
    }

    public int[] getMarcajes() {
        return marcajes;
    }

    public int getMarca(int indiceMiJugador){
        return marcajes[indiceMiJugador];
    }

    public boolean tieneMarca(int indiceMiJugador){
        if (marcajes[indiceMiJugador] == -1)
            return false;
        else
            return true;
    }

    public void dejarMarca(int indiceMiJugador){
        marcajes[indiceMiJugador] = -1;
    }

    public void marcarARival(int indiceMiJugador, int indiceRival){
        marcajes[indiceMiJugador] = indiceRival;
    }

    public double getAlturaPreviaBalon() {
        return alturaPreviaBalon;
    }

    public void setAlturaPreviaBalon(double alturaPreviaBalon) {
        this.alturaPreviaBalon = alturaPreviaBalon;
    }

    public int getGolesRival() {
        return golesRival;
    }

    public void setGolesRival(int golesRival) {
        this.golesRival = golesRival;
    }

    public Position getPosicionPreviaBalon() {
        return balon0;
    }

    public void setPosicionPreviaBalon(Position posicionPreviaBalon) {
        this.balon0 = posicionPreviaBalon;
    }

    public Position getPosicionPreviaPortero() {
        return posicionPreviaPortero;
    }

    public void setPosicionPreviaPortero(Position posicionPreviaPortero) {
        this.posicionPreviaPortero = posicionPreviaPortero;
    }

    private Queue<PosicionBalon> trayectoriaDisparoPuerta;

    public Queue<PosicionBalon> getTrayectoriaDisparoPuerta() {
        return trayectoriaDisparoPuerta;
    }

    public void setTrayectoriaDisparoPuerta(Queue<PosicionBalon> trayectoriaDisparoPuerta) {
        this.trayectoriaDisparoPuerta = trayectoriaDisparoPuerta;
    }

	/**
	 * @return the puntoSalida
	 */
	public PosicionBalon getPuntoSalida() {
		return puntoSalida;
	}

	/**
	 * @return the puntoFuera
	 */
	public PosicionBalon getPuntoFuera() {
		return puntoFuera;
	}



}
