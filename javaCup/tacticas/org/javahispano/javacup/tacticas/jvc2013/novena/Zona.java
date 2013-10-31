package org.javahispano.javacup.tacticas.jvc2013.novena;

import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author yoemny
 */
public class Zona {

    private Position bordeSuperiorIzquierdo;
    private Position bordeSuperiorDerecho;
    private Position bordeInferiorIzquierdo;
    private Position bordeInferiorDerecho;

    public Zona(Position bordeSuperiorIzquierdo, Position bordeSuperiorDerecho, Position bordeInferiorIzquierdo, Position bordeInferiorDerecho) {
        this.bordeSuperiorIzquierdo = bordeSuperiorIzquierdo;
        this.bordeSuperiorDerecho = bordeSuperiorDerecho;
        this.bordeInferiorIzquierdo = bordeInferiorIzquierdo;
        this.bordeInferiorDerecho = bordeInferiorDerecho;
    }

    public boolean estaEnLaZona(PosicionBalon posicionDelBalon){
        if ( posicionDelBalon.getPosicion().getX() >= bordeSuperiorIzquierdo.getX() &&
               posicionDelBalon.getPosicion().getX() <= bordeSuperiorDerecho.getX() &&
               posicionDelBalon.getPosicion().getY() >= bordeSuperiorIzquierdo.getY() &&
               posicionDelBalon.getPosicion().getY() >= bordeInferiorIzquierdo.getY() )
            return true;

        return false;
    }

    public boolean estaEnLaZona(Position posicion){
        if ( posicion.getX() >= bordeSuperiorIzquierdo.getX() &&
               posicion.getX() <= bordeSuperiorDerecho.getX() &&
               posicion.getY() <= bordeSuperiorIzquierdo.getY() &&
               posicion.getY() >= bordeInferiorIzquierdo.getY() )
            return true;

        return false;
    }

    public Position getBordeInferiorIzquierdo() {
        return bordeInferiorIzquierdo;
    }

    public Position getBordeInferiorDerecho() {
        return bordeInferiorDerecho;
    }

    public Position getBordeSuperiorIzquierdo() {
        return bordeSuperiorIzquierdo;
    }

    public Position getBordeSuperiorDerecho() {
        return bordeSuperiorDerecho;
    }

    
}
