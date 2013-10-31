package org.javahispano.javacup.model;

import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.render.EstiloUniforme;


/**Interfaz que define la configuración de una táctica*/
public interface TacticDetail {

    /**Nombre del equipo*/
    public String getTacticName();

    /**Pais del equipo*/
    public String getCountry();

    /**Nombre del entrenador*/
    public String getCoach();

    /**Color de la camiseta*/
    public Color getShirtColor();

    /**Color del pantalon*/
    public Color getShortsColor();

    /**Color de la franja*/
    public Color getShirtLineColor();

    /**Color de las calcetas*/
    public Color getSocksColor();

    /**Color de la vestimenta del portero*/
    public Color getGoalKeeper();

    /**Estilo de la vestimenta*/
    public EstiloUniforme getStyle();

    /**Color de la camiseta2*/
    public Color getShirtColor2();

    /**Color del pantalon2*/
    public Color getShortsColor2();

    /**Color de la franja2*/
    public Color getShirtLineColor2();

    /**Color de las calcetas2*/
    public Color getSocksColor2();

    /**Color de la vestimenta2 del portero*/
    public Color getGoalKeeper2();

    /**Estilo de la vestimenta2*/
    public EstiloUniforme getStyle2();

    /**Array de Objetos PlayerDetail,
     * que definen las caracteristicas de los jugadores*/
    public PlayerDetail[] getPlayers();
}

