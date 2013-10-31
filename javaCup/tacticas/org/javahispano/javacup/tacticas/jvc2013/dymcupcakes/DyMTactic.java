/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2013.dymcupcakes;


import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

/**
 *
 * @author Mauricio
 */
public class DyMTactic  implements Tactic {
    
    
        Position alineacion1[] = new Position[]{
        new Position(0.951048951048951,-49.64932126696832),
        new Position(-19.020979020979023,-31.59502262443439),
        new Position(0.7132867132867133,-28.50678733031674),
        new Position(19.25874125874126,-31.59502262443439),
        new Position(1.6643356643356644,-7.126696832579185),
        new Position(-15.692307692307693,-7.364253393665159),
        new Position(-23.3006993006993,11.877828054298643),
        new Position(17.594405594405593,12.115384615384617),
        new Position(-26.867132867132867,35.8710407239819),
        new Position(-5.468531468531468,16.628959276018097),
        new Position(-1.188811188811189,40.38461538461539)
    };
    Position alineacion2[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(27.251908396946565, -27.94776119402985),
        new Position(-29.84732824427481, -26.902985074626866),
        new Position(8.564885496183205, -7.574626865671642),
        new Position(-10.641221374045802, -7.052238805970149),
        new Position(27.251908396946565, 4.440298507462686),
        new Position(-29.32824427480916, 3.3955223880597014),
        new Position(-0.2595419847328244, 19.067164179104477),
        new Position(-0.2595419847328244, 35.78358208955224)
    };

    

    class TacticDetailImpl implements TacticDetail {

        @Override
        public String getTacticName() {
            return "DYMCupcake";
        }

        @Override
        public String getCountry() {
            return "Chile";
        }

        @Override
        public String getCoach() {
            return "Mauricio";
        }

        @Override
        public Color getShirtColor() {
              return new Color(255, 51, 0);
        }

        @Override
        public Color getShortsColor() {
                return new Color(255, 51, 0);
        }

        @Override
        public Color getShirtLineColor() {
                return new Color(255, 51, 0);
        }

        @Override
        public Color getSocksColor() {
            return new Color(2, 206, 51);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(0, 0, 0);
        }

        @Override
        public EstiloUniforme getStyle() {
            return EstiloUniforme.LINEAS_VERTICALES;
        }

        @Override
        public Color getShirtColor2() {
           return new Color(255, 255, 255);
        }

        @Override
        public Color getShortsColor2() {
           return new Color(255, 255, 255);
        }

        @Override
        public Color getShirtLineColor2() {
           return new Color(255, 255, 255);
        }

        @Override
        public Color getSocksColor2() {
           return new Color(255, 255, 255);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(10, 171, 214);
        }

        @Override
        public EstiloUniforme getStyle2() {
            return EstiloUniforme.FRANJA_DIAGONAL;
        }

        class JugadorImpl implements PlayerDetail {

            String nombre;
            int numero;
            Color piel, pelo;
            double velocidad, remate, presicion;
            boolean portero;
            Position Position;

            public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
                    double velocidad, double remate, double presicion, boolean portero) {
                this.nombre = nombre;
                this.numero = numero;
                this.piel = piel;
                this.pelo = pelo;
                this.velocidad = velocidad;
                this.remate = remate;
                this.presicion = presicion;
                this.portero = portero;
            }

            @Override
            public String getPlayerName() {
                return nombre;
            }

            @Override
            public Color getSkinColor() {
                return piel;
            }

            @Override
            public Color getHairColor() {
                return pelo;
            }

            @Override
            public int getNumber() {
                return numero;
            }

            @Override
            public boolean isGoalKeeper() {
                return portero;
            }

            @Override
            public double getSpeed() {
                return velocidad;
            }

            @Override
            public double getPower() {
                return remate;
            }

            @Override
            public double getPrecision() {
                return presicion;
            }
        }

        @Override
        public PlayerDetail[] getPlayers() {
            return new PlayerDetail[]{
                        new JugadorImpl("Christofer", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, true),
                        new JugadorImpl("Ruben", 2, new Color(0, 0, 0), new Color(0, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Gabriel", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Gari", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Miguel", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Hugo", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Mark", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Mauricio", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.53d, 0.78d, false),
                        new JugadorImpl("Humberto", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false),
                        new JugadorImpl("Mago", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 0.81d, false),
                        new JugadorImpl("Alexis", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 1.0d, 1.0d, false)
                    };
        }
    }
    TacticDetail detalle = new TacticDetailImpl();

    Strategy strategy=new Strategy();
    
    @Override
    public TacticDetail getDetail() {
        return detalle;
    }
    


    @Override
    public List<Command> execute(GameSituations sp) {
        
        strategy.start(sp);
        return strategy.getCommands();
        
    }
    
    
    private void target(){
        
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
        return alineacion1;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion1;
    }
    
}
