package org.javahispano.javacup.model.command;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**Command que especifica de que forma se rematara el balon*/
public final class CommandHitBall extends Command {

    /**Establece la fuerza del remate*/
    /**Set the kick power*/
    public void setHitPower(double power) {
        this.fuerzaRemate = power;
    }

    /**retorna true si se especifico como destino un angle, en vez de una coordenada*/
    /**Is true when destiny is an angle*/
    public boolean isAngle() {
        return angulo;
    }

    /**retorna true si se especifico como destino una coordenada, en vez de un angle*/
    /**Is true when destiny is a coordinate*/
    public boolean isCoordinate() {
        return destino;
    }

    /**Player index which executes the command*/
    /**Indice del jugador que ejecutara el comando*/
    @Override
    public int getPlayerIndex() {
        return indJugador;
    }

    /**Command type*/
    /**Tipo del comando*/
    @Override
    public CommandType getCommandType() {
        return CommandType.HIT_BALL;
    }

    /**Retorna el angle de direccion del remate*/
    /**Returns the angle of kick*/
    public double getAngle() {
        return anguloRemate;
    }

    /**Retorna la fuerza del remate (de 0 a 1)*/
    /**Returns the kick power*/
    public double getHitPower() {
        return fuerzaRemate;
    }

    /**Retorna el coordenada destino del remate*/
    /**Returns the destiny coordinate*/
    public Position getDestiny() {
        return destinoBola;
    }

    /**Retorna el angle vertical*/
    /**Returns the vertical angle*/
    public double getVerticalAngle() {
        return anguloVertical;
    }

    /**Indica si el comando esta en modalidad avanzar con el balon*/
    /**Is true when the player forward with the ball*/
    public boolean isForwardBall() {
        return avanza;
    }

    /**Corresponde a el comando avanzar con el balon, se indica el indice del juagador que avanzara con el balon*/
    /**Instance a CommandHitBall Object to set the player forward with the ball*/
    public CommandHitBall(int playerIdx) {
        this.indJugador = playerIdx;
        this.avanza = true;
    }

    /**Instancia el comando golpear balon, indicando: el indice del jugador que rematara,
     * las coordenadas de destino, la fuerza del remate (entre 0 y 1) y si es por alto, el remate por alto
    es con angle Constants.ANGULO_VERTICAL */
    /**Instance a CommandHitBall Object to set the player index, the destiny coordenate, the power (0-1)
     * and if is high kick (High kick have a vertical angle=Constants.ANGULO_VERTICAL)*/
    public CommandHitBall(int playerIdx, Position destiny, double power, boolean highKick) {
        this.indJugador = playerIdx;
        this.destinoBola = new Position(destiny);
        if (power > 1) {
            power = 1;
        }
        if (power < 0) {
            power = 0;
        }
        this.fuerzaRemate = power;
        this.anguloVertical = highKick ? Constants.ANGULO_VERTICAL : 0;
        this.destino = true;
        this.angulo = false;
    }

    /**Instancia el comando golpear balon, indicando: el indice del jugador que rematara,
     * las coordenadas de destino, la fuerza del remate (entre 0 y 1) y el angle vertical, el angle vertical debe estar entre
    0 y Constants.ANGULO_VERTICAL_MAX */
    /**Instance a CommandHitBall Object to set the player index, the destiny coordenate, the power (0-1)
     * and the vertical angle (0-Constants.ANGULO_VERTICAL_MAX)*/
    public CommandHitBall(int playerIdx, Position destiny, double power, double verticalAngle) {
        this.indJugador = playerIdx;
        this.destinoBola = new Position(destiny);
        if (power > 1) {
            power = 1;
        }
        if (power < 0) {
            power = 0;
        }
        this.fuerzaRemate = power;
        if (verticalAngle > Constants.ANGULO_VERTICAL_MAX) {
            verticalAngle = Constants.ANGULO_VERTICAL_MAX;
        }
        if (verticalAngle < 0) {
            verticalAngle = 0;
        }
        this.anguloVertical = verticalAngle;
        this.destino = true;
        this.angulo = false;
    }

    /**Instancia el comando golpear balon, indicando: el indice del jugador que rematara,
     * el angle de destino, la fuerza del remate (entre 0 y 1) y si es por alto, el remate por alto
    es con angle Constants.ANGULO_VERTICAL */
    /**Instance a CommandHitBall Object to set the player index, the destiny angle, the power (0-1)
     * and if is high kick (High kick have a vertical angle=Constants.ANGULO_VERTICAL)*/
    public CommandHitBall(int playerIdx, double angle, double power, boolean highKick) {
        this.indJugador = playerIdx;
        this.anguloRemate = angle;
        if (power > 1) {
            power = 1;
        }
        if (power < 0) {
            power = 0;
        }
        this.fuerzaRemate = power;
        this.anguloVertical = highKick ? Constants.ANGULO_VERTICAL : 0;
        this.angulo = true;
        this.destino = false;
    }

    /**Instancia el comando golpear balon, indicando: el indice del jugador que rematara,
     * el angle de destino, la fuerza del remate (entre 0 y 1) y el angle vertical, el angle vertical debe estar entre
    0 y Constants.ANGULO_VERTICAL_MAX*/
    /**Instance a CommandHitBall Object to set the player index, the destiny angle, the power (0-1)
     * and the vertical angle (0-Constants.ANGULO_VERTICAL_MAX)*/
    public CommandHitBall(int playerIdx, double angle, double power, double highKick) {
        this.indJugador = playerIdx;
        this.indJugador = playerIdx;
        this.anguloRemate = angle;
        if (power > 1) {
            power = 1;
        }
        if (power < 0) {
            power = 0;
        }
        this.fuerzaRemate = power;
        if (highKick > Constants.ANGULO_VERTICAL_MAX) {
            highKick = Constants.ANGULO_VERTICAL_MAX;
        }
        if (highKick < 0) {
            highKick = 0;
        }
        this.anguloVertical = highKick;
        this.angulo = true;
        this.destino = false;
    }
    private int indJugador;
    private Position destinoBola = new Position();
    private double anguloRemate = 0;
    private double fuerzaRemate = 0;
    private boolean angulo = false;
    private boolean destino = false;
    private double anguloVertical = 0;
    private boolean avanza = false;
}
