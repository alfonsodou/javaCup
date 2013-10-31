package org.javahispano.javacup.tacticas.jvc2013.novena;


import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.render.EstiloUniforme;

/**
 *
 * @author yoemny
 */

    class Equipo implements TacticDetail {

        @Override
        public String getTacticName() {
            return "9na";
        }

        @Override
        public String getCountry() {
            return "Cuba";
        }

        @Override
        public String getCoach() {
            return "yoemny";
        }

        @Override
        public Color getShirtColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getShortsColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getShirtLineColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getSocksColor() {
            return new Color(255, 255, 255);
        }

        @Override
        public Color getGoalKeeper() {
            return new Color(204, 255, 51        );
        }

        @Override
        public EstiloUniforme getStyle() {
            return EstiloUniforme.SIN_ESTILO;
        }

        @Override
        public Color getShirtColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShortsColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getShirtLineColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getSocksColor2() {
            return new Color(0, 0, 0);
        }

        @Override
        public Color getGoalKeeper2() {
            return new Color(204, 255, 102        );
        }

        @Override
        public EstiloUniforme getStyle2() {
            return EstiloUniforme.SIN_ESTILO;
        }


        @Override
        public PlayerDetail[] getPlayers() {
            return new PlayerDetail[]{
            		new Jugador("Roberto Carlos", 3, new Color(51,51,51), new Color(50,0,0),1.0d,0.63d,0.5d, false),
                    new Jugador("Hierro", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.62d,0.5d, false),
                    new Jugador("Helguera", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.62d,0.5d, false),
                    new Jugador("Michel Salgado", 2, new Color(255,200,150), new Color(50,0,0),1.0d,0.63d,0.5d, false),
                    new Jugador("Casillas", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, true),
                    new Jugador("Solari", 21, new Color(255,200,150), new Color(255,255,153),1.0d,0.75d,0.5d, false),
                    new Jugador("Makelele", 24, new Color(255,200,150), new Color(255,255,153),1.0d,0.75d,0.5d, false),
                    new Jugador("Raul", 7, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                    new Jugador("Zidane", 5, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.75d, false),
                    new Jugador("Figo", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.75d, false),
                    new Jugador("Morientes", 9, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)
            };
        }

    }
