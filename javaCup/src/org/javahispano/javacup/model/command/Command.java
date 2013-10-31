package org.javahispano.javacup.model.command;


/**Abstract class that will be inherited to create commands*/
public abstract class Command {

	/**Enumeration of available commands*/
        /**Enumeracion de comandos disponibles*/
	public enum CommandType {
		/**Type move to*/
                /**Tipo mover a*/
		MOVE_TO,                
		/**Type hit ball*/
                /**Tipo golpear balon */
		HIT_BALL
	}

	/**Returns command type*/
        /**Retorna el tipo de comando*/
	public abstract CommandType getCommandType();

	/**Returns the index of the player which uses the command*/
        /**Retorna el indice del jugador que ejecuta el comando*/
	public abstract int getPlayerIndex();

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Command)) {
			return false;
		}
		Command c = (Command) obj;
		return c.getCommandType() == getCommandType() && c.getPlayerIndex() == getPlayerIndex();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		if (getCommandType().equals(CommandType.HIT_BALL)) {
			return "Hit ball(" + getPlayerIndex() + ")";
		} else if (getCommandType().equals(CommandType.MOVE_TO)) {
			return "Go To(" + getPlayerIndex() + ")";
		} else {
			return "none";
		}
	}
}

