package org.javahispano.javacup.tacticas.jvc2013.novena;

import org.javahispano.javacup.model.engine.GameSituations;

/**
 *
 * @author yoemny
 */
public class Pruebas {
    public static void portero(GameSituations sp, DatosGlobales datos){

        if (sp.rivalGoals() != datos.getGolesRival()){
            System.out.println("Golllll");
            System.out.println("Balon "+ datos.getPosicionPreviaBalon());
            System.out.println("Balon altura "+ datos.getAlturaPreviaBalon());
            System.out.println("Portero "+ datos.getPosicionPreviaPortero());
            System.out.println("Distancia del balon "+datos.getPosicionPreviaPortero().distance(datos.getPosicionPreviaBalon()));
        }

        datos.setPosicionPreviaBalon(sp.ballPosition());
        datos.setPosicionPreviaPortero(sp.myPlayers()[5]);
        datos.setAlturaPreviaBalon(sp.ballAltitude());
        datos.setGolesRival(sp.rivalGoals());




    }
}
