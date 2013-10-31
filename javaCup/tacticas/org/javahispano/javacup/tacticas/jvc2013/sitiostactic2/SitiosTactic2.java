package org.javahispano.javacup.tacticas.jvc2013.sitiostactic2;


import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class SitiosTactic2 implements Tactic {

	 Position alineacion1[]=new Position[]{
		        new Position(0.951048951048951,-50.36199095022624),
		        new Position(18.307692307692307,-32.30769230769231),
		        new Position(-19.734265734265733,-30.407239819004527),
		        new Position(-6.6573426573426575,-31.357466063348415),
		        new Position(6.419580419580419,-10.452488687782806),
		        new Position(4.5174825174825175,-30.16968325791855),
		        new Position(22.58741258741259,3.5633484162895925),
		        new Position(-5.944055944055944,-17.57918552036199),
		        new Position(2.13986013986014,27.556561085972852),
		        new Position(-21.636363636363637,2.6131221719457014),
		        new Position(0.23776223776223776,10.214932126696834)
		    };

		    Position alineacion2[]=new Position[]{
		        new Position(0.23776223776223776,-47.98642533936651),
		        new Position(18.78321678321678,-36.58371040723982),
		        new Position(-18.78321678321678,-37.29638009049774),
		        new Position(-7.132867132867133,-37.05882352941177),
		        new Position(7.132867132867133,-26.606334841628957),
		        new Position(6.419580419580419,-35.8710407239819),
		        new Position(21.874125874125873,-3.800904977375566),
		        new Position(-9.272727272727272,-25.893665158371043),
		        new Position(0.0,-1.4253393665158371),
		        new Position(-24.013986013986013,-4.513574660633484),
		        new Position(0.0,-14.25339366515837)
		    };

		    Position alineacion3[]=new Position[]{
		        new Position(0.4755244755244755,-49.17420814479638),
		        new Position(18.78321678321678,-36.58371040723982),
		        new Position(-18.78321678321678,-37.29638009049774),
		        new Position(-7.132867132867133,-37.05882352941177),
		        new Position(7.132867132867133,-26.606334841628957),
		        new Position(6.419580419580419,-35.8710407239819),
		        new Position(23.062937062937063,-21.61764705882353),
		        new Position(-9.272727272727272,-25.893665158371043),
		        new Position(-0.7132867132867133,-10.452488687782806),
		        new Position(-23.3006993006993,-21.380090497737555),
		        new Position(-1.188811188811189,-19.004524886877828)
		    };

		    Position alineacion4[]=new Position[]{
		        new Position(-0.951048951048951,-40.859728506787334),
		        new Position(24.013986013986013,-11.402714932126697),
		        new Position(-23.776223776223777,-9.97737556561086),
		        new Position(-9.510489510489512,-11.165158371040723),
		        new Position(10.223776223776223,8.314479638009049),
		        new Position(8.321678321678322,-11.877828054298643),
		        new Position(17.594405594405593,29.457013574660635),
		        new Position(-10.223776223776223,7.839366515837104),
		        new Position(0.0,35.63348416289593),
		        new Position(-19.020979020979023,29.694570135746606),
		        new Position(0.0,22.805429864253394)
		    };

		    Position alineacion5[]=new Position[]{
		        new Position(-0.951048951048951,-40.859728506787334),
		        new Position(24.48951048951049,-20.90497737556561),
		        new Position(-24.251748251748253,-22.56787330316742),
		        new Position(-8.321678321678322,-21.61764705882353),
		        new Position(9.272727272727272,2.1380090497737556),
		        new Position(8.797202797202797,-21.61764705882353),
		        new Position(19.97202797202797,19.95475113122172),
		        new Position(-9.986013986013985,-4.276018099547511),
		        new Position(0.4755244755244755,29.21945701357466),
		        new Position(-20.685314685314687,22.330316742081447),
		        new Position(-0.23776223776223776,15.91628959276018)
		    };
    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Los Sitios de Zaragoza";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Alex";
        }

        public Color getShirtColor() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor() {
            return new Color(0, 0, 204);
        }

        public Color getShirtLineColor() {
            return new Color(0, 51, 204);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 255);
        }

        public Color getGoalKeeper() {
            return new Color(255, 255, 0        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.SIN_ESTILO;
        }

        public Color getShirtColor2() {
            return new Color(255, 255, 0);
        }

        public Color getShortsColor2() {
            return new Color(255, 0, 0);
        }

        public Color getShirtLineColor2() {
            return new Color(255, 0, 0);
        }

        public Color getSocksColor2() {
            return new Color(255, 0, 0);
        }

        public Color getGoalKeeper2() {
            return new Color(0, 0, 0        );
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.LINEAS_VERTICALES;
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
                this.nombre=nombre;
                this.numero=numero;
                this.piel=piel;
                this.pelo=pelo;
                this.velocidad=velocidad;
                this.remate=remate;
                this.presicion=presicion;
                this.portero=portero;
            }

            public String getPlayerName() {
                return nombre;
            }

            public Color getSkinColor() {
                return piel;
            }

            public Color getHairColor() {
                return pelo;
            }

            public int getNumber() {
                return numero;
            }

            public boolean isGoalKeeper() {
                return portero;
            }

            public double getSpeed() {
                return velocidad;
            }

            public double getPower() {
                return remate;
            }

            public double getPrecision() {
                return presicion;
            }

        }

        public PlayerDetail[] getPlayers() {
            return new PlayerDetail[]{
                new JugadorImpl("Andoni Cedrún", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, true),
                new JugadorImpl("Alberto Belsué", 2, new Color(255,200,150), new Color(255,255,0),0.54d,0.24d,0.85d, false),
                new JugadorImpl("Jesús 'Chucho' Solana", 3, new Color(255,200,150), new Color(50,0,0),0.46d,0.26d,0.9d, false),
                new JugadorImpl("Fernando Cáceres", 4, new Color(153,0,0), new Color(50,0,0),1.0d,0.5d,1.0d, false),
                new JugadorImpl("Nayim", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.75d, false),
                new JugadorImpl("Xavi Aguado", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,1.0d, false),
                new JugadorImpl("Pardeza", 7, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.75d, false),
                new JugadorImpl("Santiago Aragón", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,1.0d, false),
                new JugadorImpl("Esnáider", 9, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Higuera", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.75d, false),
                new JugadorImpl("Poyet", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion2;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion3;
    }

    LinkedList<Command> comandos = new LinkedList<Command>();
    
    public List<Command> execute(GameSituations sp) {
        comandos.clear();
        Position ball = sp.ballPosition();
        Position[] players = sp.myPlayers();
        int cercano = ball.nearestIndex(players);
        comandos.add(new CommandMoveTo(cercano, ball));
        int[] canKick = sp.canKick();
        for(int i:canKick){
        	comandos.add(new CommandHitBall(i, Constants.centroArcoSup, i, true));
        }
        return comandos;
    }
}