/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2013.dymcupcakes;

import java.util.*;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author Mauricio
 */
public class Strategy {

    private List<Command> cmds;
    private GameSituations sp;
    private PositionHistory ph = new PositionHistory();
    private BaloonHistory bh = new BaloonHistory();
    private List<Jugada> jugadas = new ArrayList<Jugada>();
    Position alineacion1[] = new Position[]{
        new Position(0.951048951048951, -49.64932126696832),
        new Position(-19.020979020979023, -31.59502262443439),
        new Position(0.7132867132867133, -28.50678733031674),
        new Position(19.25874125874126, -31.59502262443439),
        new Position(1.6643356643356644, -7.126696832579185),
        new Position(-15.692307692307693, -7.364253393665159),
        new Position(-23.3006993006993, 11.877828054298643),
        new Position(17.594405594405593, 12.115384615384617),
        new Position(-26.867132867132867, 35.8710407239819),
        new Position(-5.468531468531468, 16.628959276018097),
        new Position(-1.188811188811189, 40.38461538461539)
    };

    public void start(GameSituations sp) {
        this.sp = sp;
        ph.addPosition(sp.rivalPlayers());
        bh.addPosition(sp.ballPosition(), sp.ballAltitude());
        cmds = new ArrayList<Command>();
    }

    public Strategy() {
        Jugada def = new Jugada.Default();
        //Jugada defensa=new Jugada.Defensa();
        //Jugada ataque=new Jugada.Ataque();
        //Jugada follow=new Jugada.Follow();
        //Jugada paseA=new Jugada.PreparePase();
        //Jugada paseB=new Jugada.PreparePase();
        
        def.addPlayer(5);
        def.addPlayer(2);
        def.addPlayer(10);
        
        def.addPlayer(4);
        def.addPlayer(1);
        def.addPlayer(8);
        
        def.addPlayer(0);
        //defensa.addPlayer(1);
        //defensa.addPlayer(2);
        def.addPlayer(3);
        //def.addPlayer(4);
        //def.addPlayer(5);
        def.addPlayer(6);
        def.addPlayer(7);
        def.addPlayer(9);
        //ataque.addPlayer(9);
        //ataque.addPlayer(10);
        
        jugadas.add(def);
        //jugadas.add(defensa);
        //jugadas.add(ataque);
        //jugadas.add(follow);
        //jugadas.add(paseA);
        //jugadas.add(paseB);
    }

    public List<Command> getCommands() {
        for (Jugada jugada : jugadas) {
            jugada.update(sp);
        }

        for (Jugada jugada : jugadas) {
            if (jugada.apply()) {
                cmds.addAll(jugada.commands());
            }
        }
        for(Command c:cmds){
            if( c.getCommandType().equals(Command.CommandType.MOVE_TO)){
            
            System.out.println(c.getPlayerIndex()+":to:"+((CommandMoveTo)c).getMoveTo());
            
        }else{
              System.out.println(c.getPlayerIndex()+":destiny:"+((CommandHitBall)c).getDestiny());  
              
            }
        }
       System.out.println("size:"+cmds.size());
        return cmds;
    }

    public class PositionHistory {

        private List<Position[]> history = new ArrayList<Position[]>();

        public void addPosition(Position[] pos) {
            history.add(pos);
        }
    }

    public class BaloonHistory {

        private List<Position> history = new ArrayList<Position>();
        private List<Double> historyAlt = new ArrayList<Double>();

        public void addPosition(Position pos, double altitude) {
            historyAlt.add(altitude);
            history.add(pos);
        }
    }
}
