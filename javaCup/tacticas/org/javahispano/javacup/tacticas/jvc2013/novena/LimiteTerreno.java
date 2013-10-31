package org.javahispano.javacup.tacticas.jvc2013.novena;

import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author yoemny
 */
public class LimiteTerreno {
    private Position p1;
    private Position p2;

    public LimiteTerreno(Position p1, Position p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Position getP1() {
        return p1;
    }

    public Position getP2() {
        return p2;
    }

    
}
