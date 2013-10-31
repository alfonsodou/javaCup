/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2013.cucarachaAru;

import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author covazquez
 */
public class Cercano {
   int playerCercano;
   double distancia;
   Position position; 

    public Cercano(int playerCercano, double distancia, Position position) {
        this.playerCercano = playerCercano;
        this.distancia = distancia;
        this.position = position;
    }

    public int getPlayerCercano() {
        return playerCercano;
    }

    public void setPlayerCercano(int playerCercano) {
        this.playerCercano = playerCercano;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
}
