package org.javahispano.javacup.model.command;

import org.javahispano.javacup.model.util.Position;

/**Command that indicates the direction that the players are moved*/
/**Comando que especifica hacia que direccion se movilizan los jugadores*/
public final class CommandMoveTo extends Command {
	
	
    /**Player index which executes the command*/
    /**Indice del jugador que ejecutara el comando*/
    @Override
    public int getPlayerIndex() {
        return indJugador;
    }

    /**Returns destiny position*/
    /**Retorna la posicion destino*/
    public Position getMoveTo() {
        return irA;
    }
    
    /**Indicates if player is sprinting**/
    /**Indica si el jugador esta en sprint**/
    public boolean getSprint(){
    	return sprint;
    }
    

    /**Instances "Move to " command with the player index and the position*/
    /**Instancia el comando "ir a" indicando el indice del jugador y hacia que direccion se debe dirigir*/
    public CommandMoveTo(int playerIndex, Position goTo) {
        this.irA = goTo;
        this.indJugador = playerIndex;
        this.sprint = false;
    }
    
    /**Instances "Move to " command with the player index, position and if player is sprinting*/
    /**Instancia el comando "ir a" indicando el indice del jugador, la posicion hacia que direccion se debe dirigir y si esta en sprint*/
    
    public CommandMoveTo(int playerIndex, Position goTo, boolean sprint) {
        this.irA = goTo;
        this.indJugador = playerIndex;
        this.sprint = sprint;
    }

    
    /**Command type*/
    /**Tipo del comando*/
    @Override
    public CommandType getCommandType() {
        return CommandType.MOVE_TO;
    }
    
    private int indJugador = 0;
    private Position irA = new Position();
	boolean sprint = false;

}
