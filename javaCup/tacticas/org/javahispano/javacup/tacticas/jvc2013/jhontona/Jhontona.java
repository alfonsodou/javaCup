package org.javahispano.javacup.tacticas.jvc2013.jhontona;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

public class Jhontona implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-10.937062937062937,-35.63348416289593),
        new Position(0.0,-38.4841628959276),
        new Position(10.6993006993007,-36.58371040723982),
        new Position(-6.419580419580419,-25.18099547511312),
        new Position(-24.013986013986013,-19.479638009049776),
        new Position(25.916083916083913,-18.766968325791854),
        new Position(6.895104895104895,-25.656108597285066),
        new Position(-17.832167832167833,9.502262443438914),
        new Position(-1.188811188811189,2.3755656108597285),
        new Position(16.88111888111888,9.264705882352942)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-15.454545454545453,-27.08144796380091),
        new Position(0.0,-38.4841628959276),
        new Position(16.167832167832167,-28.031674208144796),
        new Position(-10.223776223776223,-1.6628959276018098),
        new Position(-25.916083916083913,-8.789592760180994),
        new Position(29.48251748251748,-9.739819004524888),
        new Position(10.223776223776223,-1.6628959276018098),
        new Position(-20.685314685314687,18.766968325791854),
        new Position(0.7132867132867133,23.042986425339365),
        new Position(24.013986013986013,19.717194570135746)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-14.027972027972028,-12.352941176470589),
        new Position(0.0,-27.31900452488688),
        new Position(14.503496503496503,-10.927601809954751),
        new Position(-14.265734265734267,11.165158371040723),
        new Position(-26.62937062937063,-1.1877828054298643),
        new Position(29.48251748251748,-0.7126696832579186),
        new Position(13.79020979020979,11.877828054298643),
        new Position(-14.97902097902098,27.31900452488688),
        new Position(0.23776223776223776,33.02036199095023),
        new Position(14.503496503496503,27.794117647058822)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(0.0,11.877828054298643),
        new Position(-13.076923076923078,-10.690045248868778),
        new Position(14.503496503496503,-10.927601809954751),
        new Position(-15.216783216783217,25.18099547511312),
        new Position(-26.867132867132867,-0.7126696832579186),
        new Position(29.48251748251748,-0.7126696832579186),
        new Position(15.216783216783217,26.368778280542987),
        new Position(-8.321678321678322,35.39592760180996),
        new Position(0.4755244755244755,39.19683257918552),
        new Position(8.083916083916083,35.39592760180996)
    };

    Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-10.937062937062937,-35.63348416289593),
        new Position(0.0,-38.4841628959276),
        new Position(10.6993006993007,-36.58371040723982),
        new Position(-6.419580419580419,-25.18099547511312),
        new Position(-24.013986013986013,-19.479638009049776),
        new Position(25.916083916083913,-18.766968325791854),
        new Position(6.895104895104895,-25.656108597285066),
        new Position(-0.951048951048951,-1.1877828054298643),
        new Position(0.0,-7.839366515837104),
        new Position(8.321678321678322,-1.4253393665158371)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-10.937062937062937,-35.63348416289593),
        new Position(0.0,-38.4841628959276),
        new Position(10.6993006993007,-36.58371040723982),
        new Position(-6.419580419580419,-25.18099547511312),
        new Position(-24.013986013986013,-19.479638009049776),
        new Position(25.916083916083913,-18.766968325791854),
        new Position(6.895104895104895,-25.656108597285066),
        new Position(-8.321678321678322,-5.701357466063349),
        new Position(-0.23776223776223776,-10.214932126696834),
        new Position(9.986013986013985,-1.900452488687783)
    };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Jhontona";
        }

        public String getCountry() {
            return "Colombia";
        }

        public String getCoach() {
            return "Jhonathan Borda Moreno";
        }

        public Color getShirtColor() {
            return new Color(0, 0, 0);
        }

        public Color getShortsColor() {
            return new Color(0, 0, 0);
        }

        public Color getShirtLineColor() {
            return new Color(0, 153, 0);
        }

        public Color getSocksColor() {
            return new Color(0, 153, 0);
        }

        public Color getGoalKeeper() {
            return new Color(0, 153, 0        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.SIN_ESTILO;
        }

        public Color getShirtColor2() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor2() {
            return new Color(255, 255, 255);
        }

        public Color getShirtLineColor2() {
            return new Color(0, 153, 0);
        }

        public Color getSocksColor2() {
            return new Color(255, 255, 255);
        }

        public Color getGoalKeeper2() {
            return new Color(0, 153, 0        );
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.FRANJA_VERTICAL;
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
                new JugadorImpl("Robert", 1, new Color(255,200,150), new Color(50,0,0),0.91d,1.0d,0.5d, true),
                new JugadorImpl("Eberth", 2, new Color(153,51,0), new Color(50,0,0),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Matt", 3, new Color(255,200,150), new Color(50,0,0),0.71d,0.88d,0.5d, false),
                new JugadorImpl("Riko", 4, new Color(255,200,150), new Color(50,0,0),0.5d,0.88d,0.5d, false),
                new JugadorImpl("Jimmy", 5, new Color(255,200,150), new Color(255,204,51),0.81d,0.84d,0.84d, false),
                new JugadorImpl("Lucas", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.83d,0.5d, false),
                new JugadorImpl("Alex", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.86d,0.5d, false),
                new JugadorImpl("Carl", 8, new Color(102,51,0), new Color(0,0,0),0.81d,1.0d,0.84d, false),
                new JugadorImpl("Christian", 9, new Color(255,200,150), new Color(102,0,0),0.9d,0.99d,1.0d, false),
                new JugadorImpl("Alejandro", 10, new Color(255,200,150), new Color(50,0,0),1.0d,0.99d,1.0d, false),
                new JugadorImpl("Jhon", 11, new Color(255,200,150), new Color(50,0,0),0.92d,0.99d,1.0d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion5;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion6;
    }
    
    LinkedList<Command> comandos = new LinkedList<Command>();
    
    public void formacion(Position[] jugadores, Position ball, int cercaBalon, PlayerDetail[] jugadoresDetalle, GameSituations sp, int[] recuperadores)
    {
        for (int i = 0; i < jugadores.length; i++) {
            if (i == cercaBalon){
                if (jugadoresDetalle[i].isGoalKeeper() && ball.getX() < -40 && ball.getY() > -15 && ball.getY() < 15){
                    comandos.add(new CommandMoveTo(cercaBalon, ball, true));                    
                    comandos.add(new CommandMoveTo(1, ball, true));
                    comandos.add(new CommandMoveTo(2, ball, true));
                }else if (!jugadoresDetalle[i].isGoalKeeper()){
                    comandos.add(new CommandMoveTo(cercaBalon, ball, true));
                } else {
                    comandos.add(new CommandMoveTo(i, alineacion1[i]));
                }
            } else {
                if (ball.getX() < 0){
                    comandos.add(new CommandMoveTo(i, alineacion1[i]));
                } else if (ball.getX() >= 0 && ball.getX() < 12){
                    comandos.add(new CommandMoveTo(i, alineacion2[i]));
                } else if (ball.getX() >= 12 && ball.getX() < 28){
                    comandos.add(new CommandMoveTo(i, alineacion3[i]));
                } else {
                    comandos.add(new CommandMoveTo(i, alineacion4[i]));
                }
                recuperarBalon(sp, recuperadores);
            }
        }
    }
    
    protected void rechazarBalon(GameSituations sp, int cercaBalon, Position[] jugadores, Position ball)
    {
        comandos.add(new CommandHitBall(cercaBalon, jugadores[9], 4, true));
    }
    
    protected void usarDefensa(PlayerDetail[] rivalDetalle, Position[] jugadores, Position[] jugadoresRivales)
    {
        for (int i=5; i < jugadores.length; i++){
            for (int j = 0; j < jugadoresRivales.length; j++){
                if (jugadoresRivales[j].getX()> 0){
                    comandos.add(new CommandMoveTo(i, jugadoresRivales[j], true));
                }
            }
        }
    }
    
    protected void recuperarBalon(GameSituations sp, int[] recuperadores)
    {
    	/*
    	 * 2013-10-09: Modificado para evitar excepción
    	 */
    	if (recuperadores.length > 0) {
    		double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
    		for (int i = 1; i < recuperadores.length; i++) {
    			comandos.add(new CommandMoveTo(recuperadores[i], 
    					new Position(posRecuperacion[0], posRecuperacion[1]))); 
    		}
    	}

    }
    
    @Override
    public List<Command> execute(GameSituations sp) {
        comandos.clear();
        Position ball = sp.ballPosition();
        Position[] jugadores = sp.myPlayers();
        PlayerDetail[] jugadoresDetalle = sp.myPlayersDetail();
        Position[] jugadoresRivales = sp.rivalPlayers();
        PlayerDetail[] rivalDetalle = sp.rivalPlayersDetail();
        Random r = new Random(); 
        int cercaBalon = ball.nearestIndex(jugadores);
        int[] recuperadores = sp.getRecoveryBall();
        int[] puedenRematar = sp.canKick();
        
        formacion(jugadores, ball, cercaBalon, jugadoresDetalle, sp, recuperadores);
        
        if (ball.getX() < 0){
            usarDefensa(rivalDetalle, jugadores, jugadoresRivales);
            rechazarBalon(sp, cercaBalon, jugadores, ball);
        }
        
        if (recuperadores.length > 1) {
            recuperarBalon(sp, recuperadores);
        } 
        
        for (int i : puedenRematar) { 
            if (ball.getX() > 25) { 
                if (r.nextBoolean()) { 
                    comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 12 + r.nextInt(6))); 
                } else if (r.nextBoolean()) { 
                    comandos.add(new CommandHitBall(i, Constants.posteDerArcoSup, 1, 12 + r.nextInt(6))); 
                } else { 
                    comandos.add(new CommandHitBall(i, Constants.posteIzqArcoSup, 1, 12 + r.nextInt(6))); 
                } 
            } else { 
                int count = 0; 
                int jugadorDestino; 
                while (((jugadorDestino = r.nextInt(11)) == i 
                || jugadores[i].getY() > jugadores[jugadorDestino].getY()) 
                && count < 20) { 
                    count++; 
                } 
                 
                comandos.add(new CommandHitBall(i, jugadores[jugadorDestino], 1, r.nextInt(45))); 
            } 
        }
        
        return comandos;
    }
}