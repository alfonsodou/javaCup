package org.javahispano.javacup.tacticas.jvc2013.novena;

import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author yoemny
 */
public class Alineacion {
    
    private Position posiciones[];

    public Alineacion() {
    }

    public Alineacion(Position[] posiciones) {
        this.posiciones = posiciones;
    }

    public Position[] getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(Position[] posiciones) {
        this.posiciones = posiciones;
    }

    
}
