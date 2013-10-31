package org.javahispano.javacup.model;

import org.javahispano.javacup.model.command.Command;
import java.util.List;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.engine.GameSituations;

/**Interfaz de una táctica*/
public interface Tactic {

    /**Retorna objeto TacticDetail
     * que define la configuración de la táctica*/
     /**Returns the detail of the tactic*/
    public TacticDetail getDetail();
    /**Retorna lista de comandos
     * que los jugadores intentaran ejecutar en la siguiente iteración,
     * en base a la información entregada por la situación del partido*/
    /**Returns the list of commands of the players execute in the next iteration*/
    public List<Command> execute(GameSituations sp);

    /**Retorna array de posiciones de los jugadores
     * cuando tu equipo inicia el juego desde el centro de la cancha*/
    /**Sets the positions of the players when starts with the ball*/
    public Position[] getStartPositions(GameSituations sp);

    /**Retorna array de posiciones de los jugadores
     * cuando el adversario inicia el juego desde el centro de la cancha*/
    /**Sets the positions of the players when don't starts with the ball*/
    public Position[] getNoStartPositions(GameSituations sp);
}
