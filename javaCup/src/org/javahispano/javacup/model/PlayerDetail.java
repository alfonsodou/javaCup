package org.javahispano.javacup.model;

import org.javahispano.javacup.model.util.Color;


/**Interfaz que define la configuración de un jugador*/
public interface PlayerDetail {

    /**Nombre del jugador*/
    public String getPlayerName();

    /**Color de la piel del jugador*/
    public Color getSkinColor();

    /**Color de pelo del jugador*/
    public Color getHairColor();

    /**Número del jugador*/
    public int getNumber();

    /**Retorna si el jugador es o no portero*/
    public boolean isGoalKeeper();

    /**Factor de velocidad del jugador [0..1]*/
    public double getSpeed();

    /**Factor de velocidad de remate del jugador [0..1]*/
    public double getPower();

    /**Factor de precisión del jugador [0..1]*/
    public double getPrecision();
   

}

