package org.javahispano.javacup.model.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.trajectory.FloorTrajectory;
import org.javahispano.javacup.model.util.BenchMark;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.util.TacticValidate;


/**Esta clase se encarga de la ejecucion de partidos*/
public final class Partido implements PartidoInterface {

    //private static Logger logger = LoggerFactory.getLogger(Partido.class);
	private static Logger logger = Logger.getLogger(Partido.class.getName());
    
    private Tactic tacticaLocal, tacticaVisita;//tactica local y visita
    private GameSituations spLocal = new GameSituations(), spVisita = new GameSituations();//situacion del partido, version local y visita
    private Position balon = new Position(Constants.centroCampoJuego);//posicion del ballPosition
    private double balonDx = 0, balonDy = 0, balonDz = 0;//velocidad del ballPosition
    
    private Position[] posLocal;//posicion de jugadores locales
    private Position[] posVisita;//posicion de jugadores visita
    private Position[] posLocalInv;//posiciones invertidas de jugadores locales
    private Position[] posVisitaInv;//posiciones invertidas de jugadores visita
    
    private Aceleracion[] aceleracionLocal;//aceleracion de los jugadores
    private Aceleracion[] aceleracionVisita;
    
    private double[] energiaLocal;//energia de los jugadores
    private double[] energiaVisita;
    
    private int golesLocal = 0;//cantidad de goles del local
    private int golesVisita = 0;//cantidad de goles de la visita
    private double alturaBalon = 0;//altura del ballPosition
    private int iteracion = 0;//iteration actual
    private int iteracionReal = 0;//iteration actual sin stops por saques de ballPosition
    private boolean remate = false;//true cuando un jugador golpea el ballPosition
    private boolean rebote = false;//true cuando rebota el ballPosition en el pasto
    private boolean poste = false;//true cuando rebota en el poste
    private boolean silbato = false;//true cuando debe sonar el silbato
    private boolean gol = false;//true cuando ocurre un gol
    private boolean ovacion = false;//true cuando ovacionan
    private boolean cambioSaque = false;//true cuando hay cambio en el saque
    private boolean sacaLocal = false, sacaVisita = false;//true cuando existe saque
    private int[][] golpeaBalonIter = new int[2][11];//cantidad de iteraciones desde la ultima vez que un jugador golpeo el ballPosition
    private int ultEquipoGolpeoBalon = -1;//ultimo equipo que golpeo el ballPosition;
    private int estado = 0;//estados durante el juego
    private int estadoant;//estado anterior
    private boolean iniciaLocal = true;//indica que el local inicia el juego
    private boolean puedenRematar[][] = new boolean[2][11];
    private double angDireccion[][] = new double[2][11];//direccion de movimiento de los jugadores
    ///////
    /////// Variables de uso interno
    ///////
    private ArrayList<ComandoEquipo> listGolpearBalon = new ArrayList<ComandoEquipo>(88);//usado internamente para almacenar los comando golpearbalon
    private ArrayList<ComandoEquipo> listIrA = new ArrayList<ComandoEquipo>(88);//usado internamente para almacenar los comando irA
    private Position balonInv = new Position(Constants.centroCampoJuego);//usado internamente para guardar la posicion invertida del ballPosition
    private Position balonVisible = new Position(Constants.centroCampoJuego);//usado internamente para guardar la posicion del ballPosition cuando sale del campo
    private Random rand = new Random();//random para la aleatoriedad
    private Position[] paLaFoto = new Position[11];//posicion donde se sacan la foto
    private Position[] paLaFoto2 = new Position[11];//posicion donde se sacan la foto
    private Position[] palCamarin = new Position[11];//posicion del camarin
    private Position[] palCamarin2 = new Position[11];//posicion del camarin
    private int iteracionesFoto = 0;//iteraciones al tomar una foto;
    private final static double MITAD_ANCHO = Constants.ANCHO_CAMPO_JUEGO / 2;
    private final static double MITAD_LARGO = Constants.LARGO_CAMPO_JUEGO / 2;
    private PartidoGuardado guardado = null;
    private boolean save = false;
    private static final double angConvert = Math.PI / 180d;
    private int iteracionSaque = 0;
    private Position posSaqueCentro[][] = null;
    private boolean[] offSidePlayers = new boolean[11]; //Indica si un jugador estaba o no en fuera de juego en el ultimo pase
    private boolean isOffSide = false; //Indica si se a producido un fuera de juego
    private int lastPlayerKickIndex = -1; //Indice del ultimo jugador que golpeo el balon

    private int ultimoSaque = 0; //Indica que tipo de saque fue el ultimo 
    private int estadoSaque = -1; //Indica si las diferentes fases del saque
    private final int SAQUE_EN_EJECUCION = 0;//El saque tiene que ejecutarse todavia  
    private final int SAQUE_EN_RECEPCION = 1;//El saque se ha realizado y se esta a la espera de la recepcion del balon por un jugador.
    private final int SAQUE_EJECUTADO = 2;// El saque se ha realizado en su totalidad.  

    private Position posLibreIndirecto = new Position(); //Posici�n desde la que se debe sacar la falta.
    private ArrayList<Integer>distanciaSaqueInsuficiente; //Array que contiene los jugadores que se hallan demasiado pr�ximos al bal�n.
    private final int EQUIPO_LOCAL = 0;
    private final int EQUIPO_VISITANTE = 1;

    private int iterGolAnulado = 0;
    
    private long timeLocal; // Tiempo empleado en ejecutar la táctica local en una iteración
    private long timeVisita; // Tiempo empleado en ejecutar la táctica visitante en una iteración

    @Override
    public boolean fueGrabado() {
        return save;
    }
   
        /**Instancia un partido vacio e inicializando las variables. Es usado por los otros contructores*/
    private Partido() {
        
    	posLocal = new Position[11];
        posVisita = new Position[11];
        posLocalInv = new Position[11];
        posVisitaInv = new Position[11];
        
        aceleracionLocal = new Aceleracion[11];
        aceleracionVisita = new Aceleracion[11];
        
        energiaLocal = new double[11];
        energiaVisita = new double[11];
        
        distanciaSaqueInsuficiente = new ArrayList<Integer>();

        for (int i = 0; i < 11; i++) {
            angDireccion[0][i] = Math.PI / 2;
            angDireccion[1][i] = Math.PI / 2;
            posLocalInv[i] = new Position();
            posVisitaInv[i] = new Position();
            golpeaBalonIter[0][i] = 0;
            golpeaBalonIter[1][i] = 0;
            paLaFoto[i] = new Position(-30, -5 - i * 2);
            paLaFoto2[i] = new Position(30, -5 - i * 2);
            palCamarin[i] = new Position(-60, 0);
            palCamarin2[i] = new Position(60, 0);

            aceleracionLocal[i] = new Aceleracion();
            aceleracionVisita[i] = new Aceleracion();
            
            energiaLocal[i] = 1;
            energiaVisita[i] = 1;
            
        }
        
        BenchMark benchMark = new BenchMark();
        logger.log(Level.INFO, "*** Tiempo benchMark: " + benchMark.getBenchMark() + " ***");
    }

    /**Instancia un nuevo partido, indicando la tactica local y la tactica visita*/
    public Partido(Tactic tacticaLocal, Tactic tacticaVisita, boolean save) throws Exception {
        this();
        this.tacticaLocal = new TacticaImpl(tacticaLocal);//deja inmutables las aptitudes, colores, nombres, etc.
        this.tacticaVisita = new TacticaImpl(tacticaVisita);//deja inmutables las aptitudes, colores, nombres, etc.
        this.save = save;
        TacticValidate.validateDetail("Tactica local:", tacticaLocal.getDetail());
        TacticValidate.validateDetail("Tactica visita:", tacticaVisita.getDetail());

        Position[][] i0 = new Position[2][11];
        Position[][] i1 = new Position[2][11];
        i0 = new Position[][]{tacticaLocal.getNoStartPositions(spLocal), tacticaVisita.getNoStartPositions(spVisita)};
        i1 = new Position[][]{tacticaLocal.getStartPositions(spLocal), tacticaVisita.getStartPositions(spVisita)};//solo para validar
        Position p0[][] = TacticValidate.validatePositions("Valida sacaVisita", i1[1], i0[0]);
        Position p1[][] = TacticValidate.validatePositions("Valida sacaLocal", i1[0], i0[1]);

        posSaqueCentro = new Position[][]{p0[1], p1[1]};
        //guarda las caracteristicas de los jugadores en los objetos GameSituations*/
        spLocal.set(new PlayerDetail[][]{tacticaLocal.getDetail().getPlayers(), tacticaVisita.getDetail().getPlayers()});
        spVisita.set(new PlayerDetail[][]{tacticaVisita.getDetail().getPlayers(), tacticaLocal.getDetail().getPlayers()});
        if (save) {
            guardado = new PartidoGuardado(new TacticaDetalleImpl(tacticaLocal.getDetail()), new TacticaDetalleImpl(tacticaVisita.getDetail()));
        }
        iterar();
    }

    /**Retorna la iteriacion en curso*/
    @Override
    public int getIteracion() {
        return iteracion;
    }

    /**indica si debe sonar el silbato*/
    @Override
    public boolean estanSilbando() {
        return silbato;
    }

    /**indica si ovacionan*/
    @Override
    public boolean estanOvacionando() {
        return ovacion;
    }
   
    /**Indica si se ha producido fuera de juego**/
    @Override
    public boolean isOffSide()
    {
        return isOffSide;
    }

    /**Indica si se ha producido una falta que da lugar a un tiro libre indirecto**/
    @Override
    public boolean isLibreIndirecto()
    {
        return saque == SAQUE_LIBRE_INDIRECTO;
    }
   
    /**Retorna true si algun jugador esta golpeando el ballPosition*/
    @Override
    public boolean estanRematando() {
        return remate;
    }

    /**Retorna si el ballPosition esta rebotando en el pasto*/
    @Override
    public boolean estaRebotando() {
        return rebote;
    }

    /**Retorna si ha pasado mucho tiempo y se produce un cambio en el saque*/
    @Override
    public boolean cambioDeSaque() {
        return cambioSaque;
    }

    /**Retorna si el ballPosition reboto en el poste*/
    @Override
    public boolean esPoste() {
        return poste;
    }

    /**Retorna la tactica local*/
    public Tactic getTacticaLocal() {
        return tacticaLocal;
    }

    /**Retorna la tactica remota*/
    public Tactic getTacticaVisita() {
        return tacticaVisita;
    }

    /**Retorna true cuando ocurre un gol*/
    @Override
    public boolean esGol() {
        return gol;
    }
    //actualiza los jugadores que pueden rematar;

    private void puedenRematar() {
        double vel = Math.sqrt(balonDx * balonDx + balonDy * balonDy);
        double probabilidad = (7d - vel) / 7d;
        double altura = 0;
        double control = 0;
        double x, y;
        boolean esPortero;
        for (int i = 0; i < 11; i++) {
            if (!sacaVisita && golpeaBalonIter[0][i] == 0) {
                esPortero = tacticaLocal.getDetail().getPlayers()[i].isGoalKeeper();
                x = posLocal[i].getX();
                y = posLocal[i].getY();
                if (esPortero && Math.abs(x) <= Constants.LARGO_AREA_GRANDE / 2 && y < -Constants.LARGO_CAMPO_JUEGO / 2 + Constants.ANCHO_AREA_GRANDE) {
                    altura = Constants.ALTO_ARCO;
                    control = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
                } else {
                    altura = Constants.ALTURA_CONTROL_BALON;
                    control = Constants.DISTANCIA_CONTROL_BALON;
                }
                puedenRematar[0][i] = (rand.nextDouble() < probabilidad && balon.distance(posLocal[i]) <= control //alcance de posicion
                        && alturaBalon <= altura);//alcance de altura
            } else {
                puedenRematar[0][i] = false;
            }
        }
        for (int i = 0; i < 11; i++) {
            if (!sacaLocal && golpeaBalonIter[1][i] == 0) {
                esPortero = tacticaVisita.getDetail().getPlayers()[i].isGoalKeeper();
                x = posVisita[i].getX();
                y = posVisita[i].getY();
                if (esPortero && Math.abs(x) <= Constants.LARGO_AREA_GRANDE / 2 && y < -Constants.LARGO_CAMPO_JUEGO / 2 + Constants.ANCHO_AREA_GRANDE) {
                    altura = Constants.ALTO_ARCO;
                    control = Constants.DISTANCIA_CONTROL_BALON_PORTERO;
                } else {
                    altura = Constants.ALTURA_CONTROL_BALON;
                    control = Constants.DISTANCIA_CONTROL_BALON;
                }
                puedenRematar[1][i] = (rand.nextDouble() < probabilidad && balon.distance(posVisitaInv[i]) <= control //alcance de posicion
                        && alturaBalon <= altura);//alcance de altura
            } else {
                puedenRematar[1][i] = false;
            }
        }
        spLocal.set(puedenRematar[0], puedenRematar[1]);
        spVisita.set(puedenRematar[1], puedenRematar[0]);
    }

    /**Determina si es gol*/
    private int gol() {
    	
    	int esGol = 0;
    	         
        if (Math.abs(balon.getY()) > Constants.LARGO_CAMPO_JUEGO / 2) {//si sale el ballPosition detras del portico
            
        	double posY = 0;
            
            if (balon.getY() < 0) 
                posY = -Constants.LARGO_CAMPO_JUEGO / 2;
            else            	
                posY = Constants.LARGO_CAMPO_JUEGO / 2;
            
            
            double posX = (balonDx0 / balonDy0) * (posY - balon.getY()) + balon.getX();//proyeccion x de la trayectoria del ballPosition en la linea de meta
            double posZ = (balonDz0 / balonDy0) * (posY - balon.getY()) + alturaBalon;//proyeccion z de la trayectoria del ballPosition en la linea de meta
            
            if (posZ <= Constants.ALTO_ARCO) {
            
            	double abs = Math.abs(posX);
                
            	/* 2013-09-08 :: Modificado según comentarios foro:
            	 * http://www.javahispano.org/foro-de-la-javacup/post/2172752
            	 */
            	if (abs < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON) {
            		/* Modificado para evitar goles imparables
            		 * 
            		 */
            		if (alturaBalon - balonDz0 > Constants.ALTO_ARCO) {
            		//if (alturaBalon - balonDz > Constants.ALTO_ARCO) {
                    
            			if (balon.getY() < 0) 
                            esGol = -3;//da una jugada mas para tapar el ballPosition
                        else 
                            esGol = 3;//da una jugada mas para tapar el ballPosition
                        
                    } else {
                    	if (balon.getY() < 0) 
                    		esGol = -1;//gol
                    	else 
                    		esGol = 1;//gol
                    }
                    
                }
            	
                if (abs >= Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON
                        && abs <= Constants.LARGO_ARCO / 2 + Constants.RADIO_BALON) {//poste
                                        
                    Position reb = null;
                    reb = new Position(balon.getX() - balon0.getX(), balon.getY() - balon0.getY());
                    
                    double ang = -reb.angle();
                    double vel = Math.sqrt(reb.getX() * reb.getX() + reb.getY() * reb.getY()) * .8;
                    balonDx = Math.cos(ang) * vel;//modifica el angle
                    balonDy = Math.sin(ang) * vel;//modifica el angle

                    trayectoria = new AirTrajectory(Math.sqrt(balonDx * balonDx + balonDy * balonDy), balonDz, 0, alturaBalon / 4);
                    angTrayectoria = ang;
                    x0Trayectoria = balon.getX();
                    y0Trayectoria = balon.getY();
                    t0Trayectoria = iteracion;
                    _trayectoria = new AirTrajectory(Math.sqrt(balonDx * balonDx + balonDy * balonDy), balonDz, 0, alturaBalon / 4);
                    _angTrayectoria = ang;
                    _x0Trayectoria = balon.getX();
                    _y0Trayectoria = balon.getY();
                    _t0Trayectoria = iteracion;

                    esGol = 2;//rebote poste;
                }
            }
        }
        
               
        return esGol;
    }

    /**Ubica los jugadores en los camarines*/
    private void setPosicionCamarines() {
        for (int i = 0; i < 11; i++) {
            posLocal[i] = new Position(-60, 0);
            posVisita[i] = new Position(60, 0);
        }
        balon = new Position(0, 0);
    }

    /**Realiza una iteration donde los jugadores se mueven desde su posicion hasta una posicion final
     * mientras algun jugador no llegue a su destino retorna false*/
    private boolean toPosicion(Position[] posLoc, Position[] posVis) {
        boolean ok = true;
        for (int i = 0; i < 11; i++) {
            Position p = irA(tacticaLocal, i, posLocal[i], posLoc[i], false);
            if (!p.equals(posLocal[i])) {
                posLocal[i] = p;
                ok = false;
            }
            p = irA(tacticaVisita, i, posVisita[i], posVis[i], false);
            if (!p.equals(posVisita[i])) {
                posVisita[i] = p;
                ok = false;
            }
        }
        return ok;
    }

    /**Itera mientras se toman la foto*/
    private void iteraFoto() {
        if (iteracionesFoto < 50) {
            iteracionesFoto++;
        } else {
            iteracionesFoto = 0;
            estado = 3;
        }
    }

    /**Itera mientras se toman la foto*/
    private void iteraGol() throws Exception {
        if (iteracionesFoto < 50) {
            if (iteracionesFoto == 2) {
                silbato = true;
            }
            if (iniciaLocal) {
                toPosicion(posLocal, tacticaVisita.getNoStartPositions(spVisita));
            } else {
                toPosicion(tacticaLocal.getNoStartPositions(spLocal), posVisita);
            }
            iteracionesFoto++;
        } else {
            iteracionesFoto = 0;
            estado = 3;
            balon = new Position(0, 0);
            trayectoria = new FloorTrajectory(0, 0);
            angTrayectoria = 0;
            x0Trayectoria = 0;
            y0Trayectoria = 0;
            _trayectoria = new FloorTrajectory(0, 0);
            _angTrayectoria = 0;
            _x0Trayectoria = 0;
            _y0Trayectoria = 0;
            alturaBalon = 0;
            if (iniciaLocal) {
                posSaqueCentro = new Position[][]{tacticaLocal.getStartPositions(spLocal), tacticaVisita.getNoStartPositions(spVisita)};
                Position[][] fixed = TacticValidate.validatePositions("sacaLocal", posSaqueCentro[0], posSaqueCentro[1]);
                posSaqueCentro = new Position[][]{fixed[0], fixed[1]};
            } else {
                posSaqueCentro = new Position[][]{tacticaLocal.getNoStartPositions(spLocal), tacticaVisita.getStartPositions(spVisita)};
                Position[][] fixed = TacticValidate.validatePositions("sacaVisita", posSaqueCentro[1], posSaqueCentro[0]);
                posSaqueCentro = new Position[][]{fixed[1], fixed[0]};
            }
        }
    }

    /**Traslada los jugadores desde su posicion actual hasta la posicion para tomar la foto*/
    private void toFoto() {
        if (toPosicion(paLaFoto, paLaFoto2)) {
            estado = 2;
            iteracion = 0;
        }
    }

    /**Traslada los jugadores hacia la posicion de inicio del juego*/
    private void toAlineacion(boolean iniciaElLocal) {
        if (iniciaElLocal) {
            if (toPosicion(posSaqueCentro[0], posSaqueCentro[1])) {
                estado = 4;
                silbato = true;
            }
        } else {
            if (toPosicion(posSaqueCentro[0], posSaqueCentro[1])) {
                estado = 4;
                silbato = true;
            }
        }

    }

    /**Invierte la posicion del eje "y" para un arreglo de Posiciones,
    el resultado lo setea en otro Array de Posiciones*/
    private Position[] invertir(Position[] posicion) {
        Position[] destino = new Position[posicion.length];
        for (int i = 0; i < posicion.length; i++) {
            destino[i] = posicion[i].getInvertedPosition();
        }
        return destino;
    }

    /**Al llamar este metodo no se ejecutara la entrada al estadio*/
    public void inicioRapido() {
        Position tl[] = tacticaLocal.getNoStartPositions(spLocal);
        Position tv[] = tacticaVisita.getNoStartPositions(spVisita);
        for (int i = 0; i < 11; i++) {
            posLocal[i] = new Position(tl[i]);
            posVisita[i] = new Position(tv[i]);
        }
        balon = new Position(0, 0);
        estado = 4;
    }

  /** Itera en un saque hasta que los jugadores se han retirado lo suficiente del bal�n para respetar con la distancia m�nima para el saque**/
   private void iteraDistanciaMinima() {
   
	if (saque == SAQUE_LIBRE_INDIRECTO) 	{
		isOffSide = false; //Cancelamos el marcador de fuera de juego para que el visor pueda mostrar el texto de fuera de juego
		saque = NO_ES_SAQUE; //Seteamos el saque para que en el visor solo suene el silbato una vez.
	}
		
	
	++iteracionReal;
	moverBalon(iteracion, iteracionReal);//mueve el ballPosition

	
	comprobarDistanciaSaque(); //Rellena el arrayList distanciaSaqueInsuficiente
        
	if (distanciaSaqueInsuficiente.size() == 0)
		estado = 4;		
	else
	   corregirDistanciaSaque(); //Mueve a los jugadores fuera de la distancia m�nima de saque
  }

    /**Realiza una iteration*/
    @Override
    public void iterar() throws Exception {
        silbato = false;
        gol = false;
        remate = false;
        rebote = false;
        poste = false;
        ovacion = false;
        cambioSaque = false;
        estadoant = estado;
        switch (estado) {
       
              case 0:
                  setPosicionCamarines();
                      estado = 1;
                  break;
              case 1:
                  toFoto();
                  break;

              case 2:
                  iteraFoto();
                  break;
         
              case 3:
                  toAlineacion(iniciaLocal);
                  break;

              case 4:
                  iterarJuego();
                  break;
              case 5:
                  iteraGol();
                  break;
         
              case 6:
                  if (toPosicion(palCamarin, palCamarin2)) estado = 7;
                      break;
         
              case 7:
                  //Fin de juego
                  break;

              case 8:
           		  iteraDistanciaMinima();
            	  break;
                         	             
           }
   
        updatePosiciones();
        if (save) {
            guardado.partido.add(new Iteracion(gol, poste, rebote, ovacion, remate, sacaLocal | sacaVisita, silbato, cambioSaque, isOffSide, isLibreIndirecto(), alturaBalon, getPosVisibleBalon(), getPosiciones(), iteracion, golesLocal, golesVisita, getPosesionBalonLocal(), timeLocal, timeVisita));
        }
    }

    /**Retorna la posicion del ballPosition que se dibuja en el la pantalla*/
    @Override
    public Position getPosVisibleBalon() {
        Position p;
        if (balonVisible == null) {
            p = balonInv;
        } else {
            p = balonVisible;
        }
        return new Position(-p.getX(), p.getY());
    }

    /**Retorna el partido guardado*/
    @Override
    public PartidoGuardado getPartidoGuardado() {
        return guardado;
    }
    private double balonDx0 = 0, balonDy0 = 0, balonDz0 = 0;
    private Position balon0;

    /**Ejecuta la logica del movimiento del ballPosition*/
    private void moverBalon(double iteracion, double iteracionReal) {
        balon0 = new Position(balon);
        double timeReal = (iteracionReal - _t0Trayectoria + 1) / 60d;
        double time = (iteracion - t0Trayectoria + 1) / 60d;
        double time0 = (iteracion - t0Trayectoria) / 60d;
        double trX = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double _trX = _trayectoria.getX(timeReal) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
        double _trY = _trayectoria.getY(timeReal) * Constants.AMPLIFICA_VEL_TRAYECTORIA;

        balon = new Position(x0Trayectoria + trX * Math.cos(angTrayectoria),
                y0Trayectoria + trX * Math.sin(angTrayectoria));
        balonVisible = new Position(_x0Trayectoria + _trX * Math.cos(_angTrayectoria),
                _y0Trayectoria + _trX * Math.sin(_angTrayectoria)).getInvertedPosition();
        alturaBalon = _trY * 2;
        if (time0 > 0) {
            double trX0 = trayectoria.getX(time0) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
            double trY0 = trayectoria.getY(time0) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
            Position b0 = new Position(x0Trayectoria + trX0 * Math.cos(angTrayectoria),
                    y0Trayectoria + trX0 * Math.sin(angTrayectoria));
            double alturaBalon0 = trY0 * 2;
            balonDx0 = balonDx;
            balonDy0 = balonDy;
            balonDz0 = balonDz;
            balonDx = balon.getX() - b0.getX();
            balonDy = balon.getY() - b0.getY();
            balonDz = alturaBalon - alturaBalon0;
            rebote = trayectoria.isRebound(time0, time);
        } else {
            rebote = false;
        }
    }
    private int golActual = 0;
    private int golAnterior = 0;
    private int anteriorCambio = 0;
    
    private final int NO_ES_SAQUE = 0;      
    private final int SAQUE_FONDO = 1;
    private final int SAQUE_BANDA = 2;
    private final int SAQUE_LIBRE_INDIRECTO = 3;
    private int saque = NO_ES_SAQUE ;

    private int iterAnterior = 0;
    private int countBug = 0;

    /**Ejecuta una iteration durande la ejecucion de un partido, enviando el estado del partido a
    cada tactica participante*/
    private void iterarJuego() {
    	long startTime;
    	
        isOffSide = false; // se setea el fuera de juego
        saque = NO_ES_SAQUE; //se setea el saque
        gol = false;//se setea gol en falso
        remate = false;//se setean los remates en falso
        puedenRematar();//obtiene quienes pueden rematar
                       
        List<Command> comandosLocales = new LinkedList<Command>();
        try {
        	startTime = System.nanoTime();
        	spLocal.setStartTime(startTime);
            comandosLocales = tacticaLocal.execute(spLocal);//envia la situacion del partido y obtiene los comandos de la tactica local
        	timeLocal = System.nanoTime() - startTime;
        } catch (Exception e) {
            logger.severe("Error al ejecutar tactica local: " + e.getMessage());
        }
        List<Command> comandosVisita = new LinkedList<Command>();
        try {
        	startTime = System.nanoTime();
        	spVisita.setStartTime(startTime);
            comandosVisita = tacticaVisita.execute(spVisita);//envia la situacion del partido y obtiene los comandos de la tactica visita
        	timeVisita = System.nanoTime() - startTime;
        } catch (Exception e) {
            logger.severe("Error al ejecutar tactica visita: " + e.getMessage());
        }
        
        executeCommands(comandosLocales, comandosVisita);//ejecuta los comandos
        actualizarEnergia(comandosLocales, comandosVisita); //ajusta la energia de los jugadores tras ejecutar los comandos
        
        Position balonAnterior = new Position(balon);//guarda la posicion anterior del ballPosition
        moverBalon(iteracion, iteracionReal);//mueve el ballPosition

        golAnterior = golActual;
        if ((golAnterior == -3 || golAnterior == 3) || (dentroCampo(balonAnterior) && !dentroCampo(balon))) {//condicion de que ha salido el ballPosition
            golActual = gol();//obtiene si la salida del ballPosition corresponde a un gol
            if (golActual == 1 || (golAnterior == 3 && !dentroCampo(balon))) {//si el gol es del local
            
            	if (estadoSaque == SAQUE_EN_RECEPCION) {//Si se ha disparado directamente a puerta desde un libre indirecto cancelamos el gol
            			saque = SAQUE_FONDO;
            	} else {
	            		//Si es un gol valido sube al marcador
	        			golActual = 0;
	        			sacaLocal = false;
	        			sacaVisita = false;
	        			gol = true;
	        			golesLocal++;
	        			estado = 5;
	        			iniciaLocal = false;
	        			alturaBalon = 0;
	        			moverBalon(iteracion + 1, iteracionReal + 1);
	            
	        			balonDx = 0;
	        			balonDy = 0;
	
	        			if (balon.getY() > Constants.LARGO_CAMPO_JUEGO / 2 + 1.5) 
	        				balon = balon.setPosition(balon.getX(), Constants.LARGO_CAMPO_JUEGO / 2 + 1.5);
	        			
	        			if (Math.abs(balon.getX()) > Constants.LARGO_ARCO / 2 - 0.5) 
	        				balon = balon.setPosition((Constants.LARGO_ARCO / 2 - 0.5) * Math.signum(balon.getX()), balon.getY());
            	}            	    
            		
            } else if (golActual == -1 || (golAnterior == -3 && !dentroCampo(balon))) {//si el gol es de la visita
            	
            	if (estadoSaque == SAQUE_EN_RECEPCION) {//Si se ha disparado directamente a puerta desde un libre indirecto cancelamos el gol
	            		saque = SAQUE_FONDO;
            	} else {
	            
	            		//Si es un gol valido sube al marcador
						golActual = 0;
						sacaLocal = false;
						sacaVisita = false;
						gol = true;
						golesVisita++;
						estado = 5;
						iniciaLocal = true;
						alturaBalon = 0;
	    
						moverBalon(iteracion + 1, iteracionReal + 1);
						balonDx = 0;
						balonDy = 0;
	
						if (balon.getY() < -Constants.LARGO_CAMPO_JUEGO / 2 - 1.5) 
							balon = balon.setPosition(balon.getX(), -Constants.LARGO_CAMPO_JUEGO / 2 - 1.5);
	        			                
						if (Math.abs(balon.getX()) > Constants.LARGO_ARCO / 2 - 0.5) 
							balon = balon.setPosition((Constants.LARGO_ARCO / 2 - 0.5) * Math.signum(balon.getX()), balon.getY());
            	}               
            				
            } else if (golActual == 2) {//si el ballPosition a dado en el poste
                poste = true;
                silbato = false;
                sacaLocal = false;
                sacaVisita = false;
            } else if (golAnterior != 3 && golAnterior != -3 && golActual == 0) {//no ha ocurrido un gol, solo es un saque
                               
	    		//Si el balon esta proximo a la porteria activamos el sonido de ovacion
                if ( (balon.distance(Constants.centroArcoInf) < Constants.LARGO_ARCO / 1.5d) || (balon.distance(Constants.centroArcoSup) < Constants.LARGO_ARCO / 1.5d) )
                	ovacion = true;
               
                if (Math.abs(balon.getY()) >= Constants.LARGO_CAMPO_JUEGO / 2) //El balon ha salido por el fondo del campo asi que o corner o saque de puerta.
                  saque = SAQUE_FONDO;                 
                else //El balon ha salido por la banda asi que posicionamos el balon en el borde
                	saque = SAQUE_BANDA;                		                	
                             
            }
        }
       
         
       //Colocamos el balon en la posicion adecuada en el caso de que se haya producido algun saque
	if (saque != NO_ES_SAQUE)
	{
		ultimoSaque = saque;
		
		sacaLocal = (ultEquipoGolpeoBalon == EQUIPO_VISITANTE);//condicion cuando saca el local
        sacaVisita = (ultEquipoGolpeoBalon == EQUIPO_LOCAL);//condicion cuando saca la visita                       
  	    
        estado = 8;
        
		silbato = true;
		estadoSaque = SAQUE_EN_EJECUCION;
    	
		posicionarBalonSaque(); //Asigna al balon su posicion para el saque
        
	}

        if (!balon.isInsideGameField(0)) {
            if (iterAnterior == iteracion - 1) {
                countBug++;
                if (countBug > 1) {
                    logger.info("Bug " + golActual + " " + golAnterior + " " + iteracion);
                }
            } else {
                countBug = 0;
            }
            iterAnterior = iteracion;
        } else {
            countBug = 0;
        }
        iteracionReal++;
        if (!sacaLocal && !sacaVisita) {
            iteracion++;
            balonVisible = null;
            iteracionSaque = 0;
        } else {
            iteracionSaque++;
            if (iteracionSaque > Constants.ITERACIONES_SAQUE) {
                if (anteriorCambio == iteracion) {
                    balon = Constants.centroCampoJuego;
                    alturaBalon = 50;
                    sacaLocal = false;
                    sacaVisita = false;
                    cambioSaque = false;	       
                } else {
                    anteriorCambio = iteracion;
                    iteracionSaque = 0;
                    sacaLocal = !sacaLocal;
                    sacaVisita = !sacaVisita;
                    cambioSaque = true;
                    estado = 8; //Comprobamos la distancia de separaci�n
                    if (balon.equals(metaAbajoDerecha)) {
                        balon = Constants.cornerInfDer;
                    } else if (balon.equals(metaAbajoIzquierda)) {
                        balon = Constants.cornerInfIzq;
                    } else if (balon.equals(metaArribaDerecha)) {
                        balon = Constants.cornerSupDer;
                    } else if (balon.equals(metaArribaIzquierda)) {
                        balon = Constants.cornerSupIzq;
                    }
                }
            }
        }
        if (iteracion > Constants.ITERACIONES) {
            estado = 6;
            silbato = true;
            alturaBalon = 0;
            balon = new Position(0, 0);
        }
    }
    private static Position metaAbajoIzquierda = Constants.esqSupIzqAreaChicaSup.movePosition(0, Constants.ANCHO_AREA_CHICA);
    private static Position metaAbajoDerecha = Constants.esqSupIzqAreaChicaSup.movePosition(Constants.LARGO_AREA_CHICA, Constants.ANCHO_AREA_CHICA);
    private static Position metaArribaIzquierda = Constants.esqSupIzqAreaChicaInf;
    private static Position metaArribaDerecha = Constants.esqSupIzqAreaChicaInf.movePosition(Constants.LARGO_AREA_CHICA, 0);

    /**Retorna la altura del ballPosition*/
    @Override
    public double getAlturaBalon() {
        return alturaBalon;
    }

    /**Usado internamente para mantener las posiciones*/
    private void updatePosiciones() {
        posLocalInv = invertir(posLocal);
        posVisitaInv = invertir(posVisita);
        balonInv = balon.getInvertedPosition();
        spLocal.set(balon, alturaBalon, golesLocal, golesVisita, iteracion, posLocal, posVisitaInv, aceleracionLocal, aceleracionVisita, energiaLocal, energiaVisita, sacaLocal, sacaVisita, golpeaBalonIter[0], golpeaBalonIter[1], trayectoria, x0Trayectoria, y0Trayectoria, t0Trayectoria, angTrayectoria, iteracionReal, false);
        spVisita.set(balonInv, alturaBalon, golesVisita, golesLocal, iteracion, posVisita, posLocalInv, aceleracionVisita, aceleracionLocal, energiaVisita, energiaLocal, sacaVisita, sacaLocal, golpeaBalonIter[1], golpeaBalonIter[0], trayectoria, x0Trayectoria, y0Trayectoria, t0Trayectoria, angTrayectoria, iteracionReal, true);
    }

    private Position ubicarEnBorde(Position p) {
        if (p.getX() > MITAD_ANCHO) {
            p = new Position(MITAD_ANCHO, p.getY());
        }
        if (p.getX() < -MITAD_ANCHO) {
            p = new Position(-MITAD_ANCHO, p.getY());
        }
        if (p.getY() > MITAD_LARGO) {
            p = new Position(p.getX(), MITAD_LARGO);
        }
        if (p.getY() < -MITAD_LARGO) {
            p = new Position(p.getX(), -MITAD_LARGO);
        }
        return p;
    }

    /**Es true cuando la posicion esta dentro del campo de juego*/
    private boolean dentroCampo(Position p) {
        return (p.getX() >= -MITAD_ANCHO && p.getX() <= MITAD_ANCHO
                && p.getY() >= -MITAD_LARGO && p.getY() <= MITAD_LARGO);
    }

    /**Elimina los comandos duplicados por jugador, si la lista de comandos es un null, retorna una lista sin elementos*/
    private List<Command> limpiarComandos(List<Command> comandos, boolean local) {
        
    	LinkedList<Command> list = new LinkedList<Command>();//lista de comandos
        
        if (comandos == null) return list;
        
        Command c;
        
        for (int i = comandos.size() - 1; i >= 0; i--) {//recorre los comandos en orden inverso
        
        	c = comandos.get(i);//obtiene un comando
                              
            //Condiciones por la que un comando no es valido y no se ejecuta
            if ( list.contains(c)  //si el comando esta duplicado
                || (c.getPlayerIndex() < 0 && c.getPlayerIndex() > 10) //indice dentro del rango
                 || (c.getCommandType() == Command.CommandType.MOVE_TO && ((CommandMoveTo) c).getMoveTo() == null) //irA no nulo
                 || ( (c.getCommandType() == Command.CommandType.HIT_BALL && ((CommandHitBall) c).isCoordinate() && ((CommandHitBall) c).getDestiny() == null) )  //destino del ballPosition no nulo
                 || (c.getCommandType() == Command.CommandType.HIT_BALL && (!puedenRematar[(local) ? EQUIPO_LOCAL : EQUIPO_VISITANTE][c.getPlayerIndex()])) )//El jugador no puede rematar
                 continue;

                
                 if (c.getCommandType() == Command.CommandType.MOVE_TO) {
                         CommandMoveTo cia = (CommandMoveTo) c;
                         
                         if (Double.isNaN(cia.getMoveTo().getX()) || Double.isNaN(cia.getMoveTo().getY()))
                            continue;

						//Si todavia no se ha efectuado el saque ningun jugador del equipo contrario puede aproximarse al balon a menos de la distancia minima establecida
						if ( (sacaLocal != local) && (estadoSaque == SAQUE_EN_EJECUCION) )
						{
							Position [] posJug = (local) ? posLocal : posVisitaInv;
		
							if ( balon.distance(posJug[c.getPlayerIndex()]) < Constants.DISTANCIA_SAQUE + 1 )
								continue;
						}                 
                 }

			

            list.add(c);//agrego el comando a la lista
        }
       
        return list;
    }
    private AbstractTrajectory trayectoria = new FloorTrajectory(0, 0);
    private double angTrayectoria = 0, x0Trayectoria = 0, y0Trayectoria = 0, t0Trayectoria;
    private AbstractTrajectory _trayectoria = new FloorTrajectory(0, 0);
    private double _angTrayectoria = 0, _x0Trayectoria = 0, _y0Trayectoria = 0, _t0Trayectoria;

    /**Ejecuta la lista de comandos enviados por las tacticas*/
    private void executeCommands(List<Command> comandosLocal, List<Command> comandosVisita) {

        Command.CommandType tipo;//almacena un tipo de comando: irA o golpearBalon
        int indJugador;//almacena un indice de jugador para ser usado en un comando

        comandosLocal = limpiarComandos(comandosLocal, true);//limpia e invierte la listade comandos del local
        comandosVisita = limpiarComandos(comandosVisita, false);//limpia e invierte la listade comandos de la visita

        listGolpearBalon.clear();//vacia la lista de comandos golpearBalon
        listIrA.clear();//vacia la lista de comandos irA

       //recorre los comandos del local
        for (Command c : comandosLocal) {//recorre todos los comandos del local
            tipo = c.getCommandType();//obtiene el tipo del comando
            indJugador = c.getPlayerIndex();//obtiene el indice del jugador indicado en el comando
                                  
            if (tipo.equals(Command.CommandType.MOVE_TO)) {//si el tipo de comado es ir_a
                    listIrA.add(new ComandoEquipo(c, 0));//agrego el comando irA a la lista
            } else {//sino: osea el tipo de comando es golpearBalon
                    listGolpearBalon.add(new ComandoEquipo(c, 0));//agregar el comando a la lista
            }
        }
       
        
        //recorre los comandos de la visita
        for (Command c : comandosVisita) {//recorre todos los comandos de la visita
            tipo = c.getCommandType();//obtiene el tipo del comando
            indJugador = c.getPlayerIndex();//obtiene el indice del jugador indicado en el comando
                      
            if (tipo.equals(Command.CommandType.MOVE_TO)) {//si el tipo de comado es ir_a
                listIrA.add(new ComandoEquipo(c, 1));//agrego el comando irA a la lista
            } else {//sino: osea el tipo de comando es golpearBalon
                listGolpearBalon.add(new ComandoEquipo(c, 1));//agregar el comando a la lista
            }    
        }

        ComandoEquipo ce = null;//un comandoEquipo
        CommandHitBall cgp;//un comandoGolpearBalon
        CommandMoveTo cia;//un comandoIrA
        boolean comandoGolpearOk;//valida un comando golpearBalon;

        double angulo = 0, error = 0;//el angle y el error
        Position p = null, p0 = null;
        PlayerDetail j = null;//detalles del jugador para obtener sus aptitudes (remate y error)
        int idx;//indice;

        ////// Calcula el angle de movimiento para ser usado cuando se avanza con el ballPosition ////
        ///////////////////////////////////////////////////////////////////////////////////////////
        for (ComandoEquipo ceq : listIrA) {//recorre la lista de comandos irA
            cia = (CommandMoveTo) ceq.c;//obtiene el comando irA
            indJugador = cia.getPlayerIndex();//obtiene el indice del jugador que ejecuta irA
            p0 = cia.getMoveTo();//Obtiene la posicion destino del comando
            if (ceq.eq == 0) {
                angDireccion[EQUIPO_LOCAL][indJugador] = posLocal[indJugador].angle(p0);
            } else {
                angDireccion[EQUIPO_VISITANTE][indJugador] = posVisita[indJugador].angle(p0);
            }
        }

        /////////////////////////////////////////////
        //////Ejecuta el comando Golpear Balon///////
        /////////////////////////////////////////////
        if (listGolpearBalon.size() > 0) {//si existe al menos un comando golpear ballPosition
            do {//elije un comando
                idx = rand.nextInt(listGolpearBalon.size());//obtiene un indice al azar
                ce = listGolpearBalon.get(idx);//se obtiene el comando golpear ballPosition
                listGolpearBalon.remove(idx);//se elimina el comando seleccionado de la lista
                comandoGolpearOk = (!sacaLocal && !sacaVisita) || (ce.eq == 0 && sacaLocal) || (ce.eq == 1 && sacaVisita);
            } while (listGolpearBalon.size() > 0 && !comandoGolpearOk);//mietras el que remata no es rival de quien saca
            if (comandoGolpearOk) {
                cgp = (CommandHitBall) ce.c;//comando golpear ballPosition
                			
                
			    //-----------------------------------------------------------           
                //TOCAR DOS VECES EL BALON EL MISMO JUGADOR TRAS UN SAQUE
                //-----------------------------------------------------------
			
                //Faltas castigadas con tiro libre indirectos   
               
                //Si el jugador que ha sacado desde fuera es el primero en golpear el balon entonces se castiga con falta sacando un libre indirecto
                if ( (estadoSaque == SAQUE_EN_RECEPCION) && (ce.eq == ultEquipoGolpeoBalon) && (lastPlayerKickIndex == cgp.getPlayerIndex()) )
                {
                    saque = SAQUE_LIBRE_INDIRECTO;
                }
                             
                //----------------
                //FUERA DE JUEGO
                //----------------

                //Si el jugador que va ha disparar es del equipo que golpeo el balon la �ltima vez y no ha sido el mismo jugador el que ha golpeado anteriormente (pues entonces no seria fuera juego sino un autopase).

                if (ce.eq == ultEquipoGolpeoBalon && lastPlayerKickIndex != cgp.getPlayerIndex()) //Comprobamos que el jugador que va a rematar no halla recibido el balon estando en fuera de juego.
                   isOffSide = offSidePlayers[cgp.getPlayerIndex()];
                   
               calculateOffSidePlayers(ce.eq == 0); //Rellenamos el array con la informaci�n de fuera de juego

               
              //Si estamos en fuera de juego y no se ha activado el indicador de LibreIndirecto por otros motivos lo activamos
                if (isOffSide){
                	saque = SAQUE_LIBRE_INDIRECTO;
                	ultimoSaque = saque;
                }
                                      
               
                
                
              //Si se ha producido alguna infraccion y ha de ejecutarse un libre indirecto guardamos la posicion
                if ( saque == SAQUE_LIBRE_INDIRECTO)
                    posLibreIndirecto = (ce.eq == EQUIPO_LOCAL)  ? posLocal[cgp.getPlayerIndex()] : posVisitaInv[cgp.getPlayerIndex()];
               
                    
              //En los saques de banda se reduce la potencia
                if ((saque != NO_ES_SAQUE) && (saque == SAQUE_BANDA))
                	cgp.setHitPower(cgp.getHitPower() * .75); 
		    
                
		    //Actualizamos el estado del saque	
		    if (estadoSaque == SAQUE_EN_RECEPCION )estadoSaque = SAQUE_EJECUTADO;      
                if (estadoSaque == SAQUE_EN_EJECUCION )estadoSaque = SAQUE_EN_RECEPCION;
             
                
                
                if (ce.eq == EQUIPO_LOCAL) {//si el equipo que golpea el ballPosition es el local
                    j = tacticaLocal.getDetail().getPlayers()[cgp.getPlayerIndex()];//obtiene al jugador
                } else {//sino el equipo que golpea el ballPosition es el visita
                    j = tacticaVisita.getDetail().getPlayers()[cgp.getPlayerIndex()];//obtiene al jugador
                }
               
                //Obtiene el error de remate del jugador incrementado por el cansancio del jugador. A mas energia menos error.
                //error = j.getPrecision() / (((ce.eq == EQUIPO_LOCAL) ? spLocal.getMyPlayerEnergy(cgp.getPlayerIndex()) : spLocal.getRivalEnergy(cgp.getPlayerIndex())));
                
                /* 2013-09-08 :: Modificado según comentarios foro:
                 * http://www.javahispano.org/foro-de-la-javacup/post/2192299
                 */
                error = j.getPrecision() * (((ce.eq == EQUIPO_LOCAL) ? spLocal.getMyPlayerEnergy(cgp.getPlayerIndex()) : spLocal.getRivalEnergy(cgp.getPlayerIndex())));

                if (cgp.isForwardBall()) {
                    double vel = Constants.getVelocidad(j.getSpeed()) + .2;
                    vel *= (((ce.eq == EQUIPO_LOCAL) ? spLocal.getMyPlayerEnergy(cgp.getPlayerIndex()) : spLocal.getRivalEnergy(cgp.getPlayerIndex()))); //Reducimos la velocidad segun la energia del jugador.

                    /* 2013-09-28 :: http://www.javahispano.org/foro-de-la-javacup/post/2205062 */
                    double ang = angDireccion[ce.eq][cgp.getPlayerIndex()]+(ce.eq==EQUIPO_VISITANTE?Math.PI:0);
                    //double ang = angDireccion[ce.eq][cgp.getPlayerIndex()];

                    t0Trayectoria = iteracion;
                    x0Trayectoria = balon.getX();
                    y0Trayectoria = balon.getY();
                    trayectoria = new FloorTrajectory(vel * 3.5, 0);
                    angTrayectoria = ang;

                    _t0Trayectoria = iteracionReal;
                    _x0Trayectoria = balon.getX();
                    _y0Trayectoria = balon.getY();
                    _trayectoria = new FloorTrajectory(vel * 3.5, 0);
                    _angTrayectoria = ang;

                    balonDz = 0;
                    balonDx = Math.cos(ang) * vel;
                    balonDy = Math.sin(ang) * vel;
                    if (ce.eq == 1) {//si el equipo que golpea el ballPosition es el visita
                        balonDy = -balonDy;
                        balonDx = -balonDx;
                    }
                    alturaBalon = 0;
                    remate = true;
                    sacaLocal = false;
                    sacaVisita = false;
                    golpeaBalonIter[ce.eq][cgp.getPlayerIndex()] = Constants.ITERACIONES_GOLPEAR_BALON + 1;

                } else {
                    if (cgp.isAngle()) {//si el destino es un angle
                        if (ce.eq == 0) {
                            angulo = cgp.getAngle() * angConvert;//obtiene el angle para el local
                        } else {
                            angulo = (180 + cgp.getAngle()) * angConvert;//obtiene el angle para la visita
                        }
                    } else {// si el destino es una coordenada
                        p = cgp.getDestiny();
                        if (ce.eq == 1) {//si el equipo que golpea el ballPosition es el visita
                            p = p.getInvertedPosition();//obtiene las coordenadas del ballPosition, pero las invierte
                        }
                        angulo = balon.angle(p);//calcula el angle
                    }
                }
                
                ultEquipoGolpeoBalon = ce.eq;
                lastPlayerKickIndex = cgp.getPlayerIndex();

                error = Constants.getErrorAngular(error);//obtiene el error angular
                angulo = angulo + (rand.nextDouble() * error - error / 2) * Math.PI;//calcula el angle destino
                
                double vel = cgp.getHitPower() * Constants.getVelocidadRemate(j.getPower());//calcula la velocidad del remate
              
                //Reducimos la velocidad segun la energia del jugador 
                double factorReduccion = (((ce.eq == 0) ? spLocal.getMyPlayerEnergy(cgp.getPlayerIndex()) : spLocal.getRivalEnergy(cgp.getPlayerIndex()))); 
                
                //corregimos la velocidad con el factor ENERGIA_DISPARO para impedir que la energia afecte demasiado a la potencia de disparo
               
                factorReduccion *= Constants.ENERGIA_DISPARO;
                
                if (factorReduccion > 1) factorReduccion = 1;
                
                vel *= factorReduccion;
                
                double angVer = Math.min(cgp.getVerticalAngle(), Constants.ANGULO_VERTICAL_MAX);
                angVer = Math.max(angVer, 0);
                angVer = angVer * angConvert;
                
                if (vel != 0) {//si el remate tiene velocidad
                    //calcula la velocidad en el plano x/y
                    t0Trayectoria = iteracion;
                    x0Trayectoria = balon.getX();
                    y0Trayectoria = balon.getY();
                    trayectoria = new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel, 0, 0);
                    angTrayectoria = angulo;
                    _t0Trayectoria = iteracionReal;
                    _x0Trayectoria = balon.getX();
                    _y0Trayectoria = balon.getY();
                    _trayectoria = new AirTrajectory(Math.cos(angVer) * vel, Math.sin(angVer) * vel, 0, 0);
                    _angTrayectoria = angulo;
                    //establece la altura inicial en cero
                    alturaBalon = 0;
                    remate = true;
                    sacaLocal = false;
                    sacaVisita = false;
                    //marca al jugador que remato para que no vuelva a rematar de inmediato
                    golpeaBalonIter[ce.eq][cgp.getPlayerIndex()] = Constants.ITERACIONES_GOLPEAR_BALON + 1;
                    //marca el ultimo equipo que golpeo el ballPosition
                    ultEquipoGolpeoBalon = ce.eq;
                }
            }
        }
        /////////////////////////////////
        /////Ejecuta los comando irA/////
        /////////////////////////////////
        Position newPos[][] = new Position[2][11];
        for (int i = 0; i < 11; i++) {
            newPos[0][i] = posLocal[i];
            newPos[1][i] = posVisita[i];
        }
        for (ComandoEquipo ceq : listIrA) {//recorre la lista de comandos irA
            cia = (CommandMoveTo) ceq.c;//obtiene el comando irA
            indJugador = cia.getPlayerIndex();//obtiene el indice del jugador que ejecuta irA
            p = cia.getMoveTo();//Obtiene la posicion destino del comando
                                    
		if (ceq.eq == EQUIPO_LOCAL) //ejecuta los comandos irA para el local y para la visita               
                newPos[EQUIPO_LOCAL][indJugador] = irA(tacticaLocal, indJugador, posLocal[indJugador], p, cia.getSprint());                
            else                
                newPos[EQUIPO_VISITANTE][indJugador]  = irA(tacticaVisita, indJugador, posVisita[indJugador], p, cia.getSprint());                
                       
        }
        //Implementa la separacion entre jugadores
        double sep = Constants.JUGADORES_SEPARACION;
        double dist, ang0, ang1;
        Position med, inv;
        for (int i = 0; i < 11; i++) {
            for (int k = 0; k < 11; k++) {
                if (k != i) {
                    for (int m = 0; m < 2; m++) {
                        dist = newPos[m][i].distance(newPos[m][k]);
                        if (dist < sep) {
                            med = Position.medium(newPos[m][i], newPos[m][k]);
                            ang0 = med.angle(newPos[m][i]);
                            ang1 = med.angle(newPos[m][k]);
                            newPos[m][i] = med.moveAngle(ang0, sep / 2);
                            newPos[m][k] = med.moveAngle(ang1, sep / 2);
                        }
                    }
                }
                inv = newPos[1][k].getInvertedPosition();
                dist = newPos[0][i].distance(inv);
                if (dist < sep) {
                    med = Position.medium(newPos[0][i], inv);
                    ang0 = med.angle(newPos[0][i]);
                    ang1 = med.angle(inv);
                    newPos[0][i] = med.moveAngle(ang0, sep / 2);
                    newPos[1][k] = med.moveAngle(ang1, sep / 2).getInvertedPosition();
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            posLocal[i] = newPos[0][i];
            posVisita[i] = newPos[1][i];
        }
        if (!sacaLocal && !sacaVisita) {
            double py = balon.getY();
            if (py > 0) {
                if (py > medio) {
                    puntosLocal = puntosLocal + 2;
                    puntosTotal = puntosTotal + 2;
                } else {
                    puntosLocal = puntosLocal + 1;
                    puntosTotal = puntosTotal + 1;
                }
            } else if (py < 0) {
                if (py < -medio) {
                    puntosVisita = puntosVisita + 2;
                    puntosTotal = puntosTotal + 2;
                } else {
                    puntosVisita = puntosVisita + 1;
                    puntosTotal = puntosTotal + 1;
                }
            }
        }

        //reduce en uno las iteraciones para volver a golpear el ballPosition
        for (int i = 0; i < 11; i++) {
            
        	if (golpeaBalonIter[0][i] > 0)  golpeaBalonIter[0][i]--;
            
            if (golpeaBalonIter[1][i] > 0) golpeaBalonIter[1][i]--;
            
        }
    }
    private int puntosLocal = 0;//puntos de posesion local
    private int puntosVisita = 0;//puntos de posesion visita
    private int puntosTotal = 0;//puntos de poesion total
    private double medio = Constants.LARGO_CAMPO_JUEGO / 4;

    private double redondeaMultiplo(double valor, double divisor) {
        return Math.round(valor / divisor) * divisor;
    }

    /**Retorna el porcentaje de posecion del ballPosition del local*/
    @Override
    public double getPosesionBalonLocal() {
        
    	if (puntosTotal == 0) return .5;
        
        double d = (double) puntosLocal / (double) puntosTotal;
        return d;
    }

    /**Retorna el estado en que se encuentra el partido*/
    public int getEstado() {
        return estado;
    }

    @Override
    public boolean estanSacando() {
        return (sacaLocal || sacaVisita) && ( (estado == 4 && estadoant == 4) || (estado == 8 && estadoant == 8) );
    }

    @Override
    public int getGolesLocal() {
        return golesLocal;
    }

    @Override
    public int getGolesVisita() {
        return golesVisita;
    }

    /**Mueve un jugador*/
    private Position irA(Tactic t, int indJugador, Position posicion, Position irA, boolean sprint) {
        
    	double dist = irA.distance(posicion);
       
        //reducimos la velocidad en proporcion a la energia del jugador y la aceleracion
    	double energia = ((t == tacticaLocal) ? spLocal.getMyPlayerEnergy(indJugador) : spLocal.getRivalEnergy(indJugador));
    	
    	//Actualizamos la aceleracion con el movimiento actual
    	if (t == tacticaLocal) 
    		aceleracionLocal[indJugador].actualizar(irA);
    	else
    		aceleracionVisita[indJugador].actualizar(irA);
    	
    	
    	//Comprobamos si el jugador esta en sprint y comprobamos que cumpla las condiciones para ello (Tener una energia superior a Constants.SPRINT_ENERGIA_MIN
    	double aceleracion_sprint = 1;
    	
    	if (sprint)
    	{	
    		double [] energiaEquipo = null;
    		double energiaJugador = 0;
    	
    		energiaEquipo =  (t == tacticaLocal) ?	energiaLocal : energiaVisita;
    		energiaJugador = energiaEquipo[indJugador];
    		
    		if (energiaJugador  > Constants.SPRINT_ENERGIA_MIN){
    			aceleracion_sprint = Constants.SPRINT_ACEL;
    			
    			//Rebajamos la energia del jugador
    			energiaEquipo[indJugador] -= Constants.SPRINT_ENERGIA_EXTRA;
    		}
    				 
    	}
    	
    	double aceleracion = ((t == tacticaLocal) ? spLocal.getMyPlayerAceleration(indJugador) : spLocal.getRivalAceleration(indJugador));
        	
        double vel = Constants.getVelocidad(t.getDetail().getPlayers()[indJugador].getSpeed()) * energia * aceleracion * aceleracion_sprint;
        
        if (dist < vel) vel = dist;
                
        return posicion.movePosition(irA.getX() - posicion.getX(), irA.getY() - posicion.getY(), vel);
    }

    @Override
    public Position[][] getPosiciones() {
        
    	Position[] p0 = new Position[11];
        Position[] p1 = new Position[11];
        Position p;
        
        for (int i = 0; i < 11; i++) {
            p = posLocalInv[i];
            p0[i] = new Position(-p.getX(), p.getY());
            p = posVisita[i];
            p1[i] = new Position(-p.getX(), p.getY());
        }
        
        p = new Position(-balonInv.getX(), balonInv.getY());
        
        return new Position[][]{p0, p1, new Position[]{p}};
    }

    @Override
    public TacticDetail getDetalleLocal() {
        return tacticaLocal.getDetail();
    }

    @Override
    public TacticDetail getDetalleVisita() {
        return tacticaVisita.getDetail();
    }
    public boolean buffered = false;

    public void buffer(int iteraciones) {
        
    	for (int i = 0; i < iteraciones; i++) {
            try {
                iterar();
            } catch (Exception e) {
                logger.severe("Error al iterar: " + e.getMessage());
            }
        }
        
    	buffered = true;
        
    	new Runnable() {

            @Override
            public void run() {
            }
        };
    }

    /**Clase interna para empaquetar comandos por equipo*/
    class ComandoEquipo {

        Command c;
        int eq;

        public ComandoEquipo(Command c, int eq) {
            this.c = c;
            this.eq = eq;
        }

        @Override
        public String toString() {
            if (eq == EQUIPO_LOCAL) {
                return "local:" + c;
            } else {
                return "visita:" + c;
            }
        }
    }
   
    /**
     * Calcula los jugadores que estan en el pase actual y rellena el array outOfGamePlayers con dicha informaci�n.
     * @param isLocal Verdadero si es el equipo local el que ha rematado
     */
    void calculateOffSidePlayers(boolean isLocal) {

        Position[] positionPlayers = (isLocal) ? posLocal : posVisitaInv;

        //Posicion que marca el fuera de juego (ultimo defensa o balon)
        Position posOffSide = balon;
        Position lastPosOffSide = balon;
              
        //Vemos si hay algun defensa por delante del balon y asignamos su posicion a posOffSide (deben de haber dos jugadores del equipo contrario por delante del balon para evitar el fuera de juego)
        for (int x = 0; x < 11; ++x) {
       
            if (isLocal) {
                if (lastPosOffSide.getY() < posVisitaInv[x].getY()) {
                    posOffSide = lastPosOffSide;
                    lastPosOffSide = posVisitaInv[x];
                } else {
                    if (posOffSide.getY() < posVisitaInv[x].getY()) {
                        posOffSide = posVisitaInv[x];
                    }
                }
               
            } else {
                if (lastPosOffSide.getY() > posLocal[x].getY()) {
                    posOffSide = lastPosOffSide;
                    lastPosOffSide = posLocal[x];
                } else {
                    if (posOffSide.getY() > posLocal[x].getY()) {
                        posOffSide = posLocal[x];
                    }
                   
                }   
            }
        }
    
        if (posOffSide == null) posOffSide = balon;//Si no hay ning�n defensor por delante el fuera de juego lo marca el balon
       
        //No puede producirse fuera de juego dentro de la mitad de su campo.
        if ((isLocal && posOffSide.getY() < 0) || (!isLocal && posOffSide.getY() > 0) )
                posOffSide = new Position(0,0);

        //Rellenamos el array outOfGamePlayers indicando que jugadores del equipo que ataca estan fuera de juego con relacion a la posicion calculada anteriormente
        //y teniendo en cuenta que cuando se saca desde banda o desde puerta no es fuera de juego.
        for (int x = 0; x < 11; ++x) {
        	
        	//Si se ha marcado gol y el jugador fuera de juego toca el balon dentro de la porteria, no es valido el fuera de juego
        	boolean dentroPorteria = (estado == 5);
       
    		/* 2013-09-24 :: Modificado para que se sancione el fuera de juego en los saques de libre indirecto:
             * http://www.javahispano.org/foro-de-la-javacup/post/2191473
             */
            if (isLocal)
            	offSidePlayers[x] = (positionPlayers[x].getY() > posOffSide.getY() && !dentroPorteria && ((estadoSaque != SAQUE_EN_EJECUCION && (ultimoSaque != SAQUE_FONDO || ultimoSaque != SAQUE_BANDA)) || (estadoSaque == SAQUE_EN_EJECUCION && ultimoSaque == SAQUE_LIBRE_INDIRECTO)));
            else
            	offSidePlayers[x] = (positionPlayers[x].getY() < posOffSide.getY() && !dentroPorteria && ((estadoSaque != SAQUE_EN_EJECUCION && (ultimoSaque != SAQUE_FONDO || ultimoSaque != SAQUE_BANDA)) || (estadoSaque == SAQUE_EN_EJECUCION && ultimoSaque == SAQUE_LIBRE_INDIRECTO)));

        }   

    }

    /** Rellena el array distanciaSaqueInsuficiente con los jugadores que se hallan demasiado proximos al balon durante un saque**/
    private void comprobarDistanciaSaque()
    {
        distanciaSaqueInsuficiente.clear();
       
        Position [] posJugadores = (sacaLocal) ?  posVisitaInv : posLocal;
               
        for (int x = 0; x < 11; ++x)
        {
            if (posJugadores[x].distance(balon) < Constants.DISTANCIA_SAQUE)
                distanciaSaqueInsuficiente.add(new Integer(x));
        }
    }
   
    /**Genera los comandos MOVE TO de los jugadores que deben retirarse del balon para proceder al saque**/
	/* 2013-09-21 :: Modificado según comentarios foro:
     * http://www.javahispano.org/foro-de-la-javacup/post/2200515
     */    
    private void corregirDistanciaSaque() {
    	Position [] posJugadoresSaca = (sacaLocal) ? posLocal : posVisita;
    	Tactic tacticaSaca = (sacaLocal) ? tacticaLocal : tacticaVisita;
    	GameSituations spSaca = (sacaLocal) ? spLocal : spVisita;

    	Position [] posJugadoresNoSaca = (sacaLocal) ? posVisita : posLocal;
    	Tactic tacticaNoSaca = (sacaLocal) ? tacticaVisita : tacticaLocal;
    	GameSituations spNoSaca = (sacaLocal) ? spVisita : spLocal;
    	Position posBalonNoSaca = (sacaLocal) ? balon.getInvertedPosition() : balon;

    	List<Command> comandosSaca = tacticaSaca.execute(spSaca);
    	List<Command> comandosNoSaca = tacticaNoSaca.execute(spNoSaca);
    	limpiarComandos(comandosSaca, sacaLocal);
    	limpiarComandos(comandosNoSaca, !sacaLocal);

    	for (Command cmd : comandosSaca) {
    		if (cmd.getCommandType().equals(Command.CommandType.MOVE_TO)) 
    			posJugadoresSaca[cmd.getPlayerIndex()] = irA(tacticaSaca, cmd.getPlayerIndex(), posJugadoresSaca[cmd.getPlayerIndex()], ((CommandMoveTo) cmd).getMoveTo(), ((CommandMoveTo) cmd).getSprint());
    	}

    	for (Command cmd : comandosNoSaca) {
    		if (cmd.getCommandType().equals(Command.CommandType.MOVE_TO)) {
    			int indJugador = cmd.getPlayerIndex();
    			if (posJugadoresNoSaca[indJugador].distance(posBalonNoSaca) > Constants.DISTANCIA_SAQUE) {
    				if(((CommandMoveTo) cmd).getMoveTo().distance(posBalonNoSaca) > Constants.DISTANCIA_SAQUE)
    					posJugadoresNoSaca[indJugador] = irA(tacticaNoSaca, indJugador, posJugadoresNoSaca[indJugador], ((CommandMoveTo) cmd).getMoveTo(), ((CommandMoveTo) cmd).getSprint());
    			} 
    		} 
    	}

    	for (Integer jugIndex : distanciaSaqueInsuficiente) {
    		double ang = posBalonNoSaca.angle(posJugadoresNoSaca[jugIndex]);

    		/* 2013-09-21 :: Modificado según comentarios foro:
             * http://www.javahispano.org/foro-de-la-javacup/post/2191473
             */
    		Position p = new Position();
            do {
            	p = new Position(posBalonNoSaca.getX() + Math.cos(ang) *(Constants.DISTANCIA_SAQUE + 1), posBalonNoSaca.getY() + Math.sin(ang) * (Constants.DISTANCIA_SAQUE + 1));
            	ang += Math.PI / 2;
            } while ((!p.isInsideGameField(0)));            

    		
    		posJugadoresNoSaca[jugIndex] = irA(tacticaNoSaca, jugIndex, posJugadoresNoSaca[jugIndex], p, false);
    	}
    }    
    
       
    
    /**
     * Posiciona el balon dependiendo del saque a efectuar
     */
	void posicionarBalonSaque()
	{
			
		switch (saque){

			case SAQUE_FONDO:				
				if (sacaLocal) {
      	                  if (balon.getY() > 0) {
            	                if (balon.getX() > 0) 
                  	              balon = Constants.cornerSupDer;
                        	    else 
                              	  balon = Constants.cornerSupIzq;
                            	} else {
            	            	if (balon.getX() > 0) 
                                		balon = metaAbajoDerecha;
                       	      	else 
                                		balon = metaAbajoIzquierda;
                              }
      	              } else if (sacaVisita) {
            	            if (balon.getY() < 0) {
                  	          if (balon.getX() > 0) 
                        	        balon =  Constants.cornerInfDer;
	                            else 
      	                          balon = Constants.cornerInfIzq;
            	             } else {
	                            if (balon.getX() > 0) 
      	                          balon = metaArribaDerecha;
            	                else 
                  	              balon = metaArribaIzquierda;
                        	 }
            	        }
				  break;
			
			case SAQUE_BANDA:
				balon = ubicarEnBorde(balon);//ubica el ballPosition en el borde del campo
				break;

			case SAQUE_LIBRE_INDIRECTO:
				 balon = posLibreIndirecto;
				 break;
			}

					
			iteracionSaque = 0;
            balonDx = 0;
            balonDy = 0;
            alturaBalon = 0;
                       
            trayectoria = new FloorTrajectory(0, 0);
            x0Trayectoria = balon.getX();
            y0Trayectoria = balon.getY();
            t0Trayectoria = iteracion;
			
	}
	

    /**Reducimos la energ�a del jugador**/
    protected void reduceEnergia(int jugIndex, double[] energia)
    {
    	if (energia[jugIndex] - Constants.ENERGIA_DIFF < Constants.ENERGIA_MIN) 
    		energia[jugIndex] = Constants.ENERGIA_MIN;
    	else
    		energia[jugIndex] -= Constants.ENERGIA_DIFF;
    }
    
    /**Incrementamos la energ�a del jugador**/
    private void incrementaEnergia(int jugIndex, double[] energia)
    {
    	if (energia[jugIndex] + Constants.ENERGIA_DIFF * 2> 1) 
    		energia[jugIndex] = 1;
    	else
    		energia[jugIndex] += Constants.ENERGIA_DIFF * 2;
    }

    /**Actualia la energia de los jugadores**/
    private void actualizarEnergia(List <Command> comandosLocales, List <Command> comandosVisita){
        
    	//Indice del jugador que ejecuta el comando
    	int indJugador; 
    	
    	/// COMANDOS LOCALES ///
    	
    	 //Jugadores a los que no se les asigna comando
        ArrayList <Integer>playersNoCommand = new ArrayList <Integer>();
        for (int x=0; x < 11; ++x)
        {
            playersNoCommand.add(new Integer(x));
        }
       
        //recorre los comandos del local
        for (Command c : comandosLocales) {//recorre todos los comandos del local
        
        	indJugador = c.getPlayerIndex();//obtiene el indice del jugador indicado en el comando
            playersNoCommand.remove(new Integer(indJugador));//eliminamos el jugador de la lista de jugadores sin comandos
            reduceEnergia(indJugador, energiaLocal); //Reducimos la energia del jugador local
        }
      
      //Incrementamos la energia a aquellos jugadores que no se les ha asignado ning�n comando
        for (Integer jugadorIndex:playersNoCommand)
        {
            incrementaEnergia(jugadorIndex, energiaLocal);
            
        }
       

        /// COMANDOS VISITA ///
      //Volvemos a regenerar la lista de jugadores sin comandos para incrementar la energia a los jugadores sin comandos de la tactica visita
       
        playersNoCommand.clear();
       
        for (int x=0; x < 11; ++x)
        {
            playersNoCommand.add(new Integer(x));
        }
       
        //recorre los comandos de la visita
        for (Command c : comandosVisita) {//recorre todos los comandos de la visita

            indJugador = c.getPlayerIndex();//obtiene el indice del jugador indicado en el comando
            playersNoCommand.remove(new Integer(indJugador));//eliminamos el jugador de la lista de jugadores sin comandos
            reduceEnergia(indJugador, energiaVisita);//Reducimos la energia del jugador visitante
        }

      //Incrementamos la energia a aquellos jugadores que no se les ha asignado ning�n comando
        for (Integer jugadorIndex:playersNoCommand)
        {
            incrementaEnergia(jugadorIndex, energiaVisita);            
        }
        
    }

	@Override
	public long[] getLocalTime() {
		long[] result = new long[Constants.ITERACIONES];
		for(int i = 0; i < Constants.ITERACIONES; i++) {
			result[i] = guardado.partido.get(i).timeLocal;
		}
		return result;
	}

	@Override
	public long[] getVisitaTime() {
		long[] result = new long[Constants.ITERACIONES];
		for(int i = 0; i < Constants.ITERACIONES; i++) {
			result[i] = guardado.partido.get(i).timeVisita;
		}
		return result;
	}
    
}