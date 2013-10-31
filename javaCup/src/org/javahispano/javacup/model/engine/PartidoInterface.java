package org.javahispano.javacup.model.engine;

import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Position;

/**
Interfaz partido, usada para ejecutar o visualizar partidos
 */
public interface PartidoInterface {

    /**retorna si ocurrio un gol*/
    public boolean esGol();

    /**retorna si el balon a golpeado el poste*/
    public boolean esPoste();

    /**retorna si el balon esta rebotando en el cesped*/
    public boolean estaRebotando();

    /**Retorna si el publico esta ovacionando*/
    public boolean estanOvacionando();

    /**Retorna si un jugadore esta rematando el balon*/
    public boolean estanRematando();

    /**retorna si se debe ralizar un saque*/
    public boolean estanSacando();

    /**Retorna si estan silbando*/
    public boolean estanSilbando();

    /**Retorna la altura del balon*/
    public double getAlturaBalon();

    /**Retorna si el partido fue grabado*/
    public boolean fueGrabado();

    /**Retorna si ha ocurrido un cambio en el saque*/
    public boolean cambioDeSaque();

    /**Retorna la TacticDetail del local*/
    public TacticDetail getDetalleLocal();

    /**Retorna la TacticDetail de la visita*/
    public TacticDetail getDetalleVisita();

    /**Retorna el partido guardado*/
    public PartidoGuardado getPartidoGuardado();

    /**Retorna la posicion del balon visible*/
    public Position getPosVisibleBalon();

    /**Retorna la posicion de los jugadores*/
    public Position[][] getPosiciones();

    /**realiza una iteracion dentro del juego*/
    public void iterar() throws Exception;

    /**Retorna los goles del local*/
    public int getGolesLocal();

    /**Retorna los goles de la visita*/
    public int getGolesVisita();

    /**Retorna el numero de la iteracion*/
    public int getIteracion();

    /**Retorna la posecion del balon del local*/
    public double getPosesionBalonLocal();
    
    /**Retorna si se ha producido fuera de juego**/
    public boolean isOffSide();
    
    /**Retorna si se ha producido una falta que da lugar a un tiro libre indirecto**/
    public boolean isLibreIndirecto();    

}
