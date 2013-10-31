package org.javahispano.javacup.tacticas.jvc2013.elaga;


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

public class Elaga implements Tactic {

    Position alineacion1[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-19.46564885496183, -31.6044776119403),
        new Position(0.2595419847328244, -31.082089552238806),
        new Position(19.984732824427482, -31.6044776119403),
        new Position(7.608391608391608, -19.479638009049776),
        new Position(-12.363636363636363, -19.004524886877828),
        new Position(-26.62937062937063, -11.64027149321267),
        new Position(23.062937062937063, -11.165158371040723),
        new Position(-14.74125874125874, 18.29185520361991),
        new Position(-1.4265734265734267, 0.23755656108597287),
        new Position(14.265734265734267, 18.529411764705884)
    };
    Position alineacion2[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(27.251908396946565, -27.94776119402985),
        new Position(-29.84732824427481, -26.902985074626866),
        new Position(7.608391608391608, -14.25339366515837),
        new Position(-11.412587412587413, -13.065610859728507),
        new Position(25.202797202797203, -2.3755656108597285),
        new Position(-29.95804195804196, -2.6131221719457014),
        new Position(-1.6643356643356644, 8.076923076923077),
        new Position(-1.6643356643356644, 25.418552036199095)
    };
    Position alineacion3[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(26.732824427480914, -20.111940298507463),
        new Position(-29.32824427480916, -21.67910447761194),
        new Position(-2.8531468531468533, -13.778280542986426),
        new Position(-19.734265734265733, -9.502262443438914),
        new Position(18.307692307692307, -6.651583710407239),
        new Position(-19.25874125874126, 21.855203619909503),
        new Position(-1.6643356643356644, 8.314479638009049),
        new Position(18.06993006993007, 22.330316742081447)
    };
    Position alineacion4[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -35.78358208955224),
        new Position(12.717557251908397, -35.26119402985075),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(14.793893129770993, -18.544776119402986),
        new Position(-17.389312977099234, -19.58955223880597),
        new Position(-23.618320610687025, -0.7835820895522387),
        new Position(5.969465648854961, -5.485074626865671),
        new Position(0.2595419847328244, -0.26119402985074625),
        new Position(22.580152671755727, -1.3059701492537314)
    };
    Position alineacion5[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -35.78358208955224),
        new Position(12.717557251908397, -35.26119402985075),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(14.793893129770993, -18.544776119402986),
        new Position(-17.389312977099234, -19.58955223880597),
        new Position(-23.618320610687025, -0.7835820895522387),
        new Position(6.4885496183206115, -6.529850746268657),
        new Position(-6.4885496183206115, -6.529850746268657),
        new Position(22.580152671755727, -1.3059701492537314)
    };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Elaga";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Agustín";
        }

        public Color getShirtColor() {
            return new Color(27, 180, 51);
        }

        public Color getShortsColor() {
            return new Color(9, 57, 147);
        }

        public Color getShirtLineColor() {
            return new Color(249, 221, 29);
        }

        public Color getSocksColor() {
            return new Color(248, 177, 58);
        }

        public Color getGoalKeeper() {
            return new Color(64, 75, 40);
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.LINEAS_VERTICALES;
        }

        public Color getShirtColor2() {
            return new Color(152, 22, 65);
        }

        public Color getShortsColor2() {
            return new Color(165, 54, 25);
        }

        public Color getShirtLineColor2() {
            return new Color(41, 16, 18);
        }

        public Color getSocksColor2() {
            return new Color(97, 234, 170);
        }

        public Color getGoalKeeper2() {
            return new Color(238, 252, 185);
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.FRANJA_HORIZONTAL;
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
                new JugadorImpl("Manué", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, true),
                new JugadorImpl("Ozé", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("Antoñito", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("Eú", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("Er Dani", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("Franjisco Ozé", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("Uan", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("Cabesa", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("Er xino", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("Er nota", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false),
                new JugadorImpl("YoMismo", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1.0d, 0.5d, 1.0d, false)
            };
        }
    }
    TacticDetail detalle = new TacticDetailImpl();

    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
        return alineacion4;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
        return alineacion5;
    }
    //Lista de Commands
    LinkedList<Command> Commands = new LinkedList<Command>();

    @Override
    public List<Command> execute(GameSituations sp) {
      //Limpia la lista de Commands
        Commands.clear();
        //Obtiene las Positiones de tus jugadores
        Position[] jugadores = sp.myPlayers();
        // Obtengo la posicion del balon
        Position ball=sp.ballPosition();
        int cercano=ball.nearestIndex(jugadores);
         Commands.add(new CommandMoveTo(0, alineacion1[0]));
         this.ataque(sp);
         this.posInicio(sp, cercano);
         this.defensa(sp);
        return Commands;
    }
    
    
    private void defensa(GameSituations sp){
         //Si no saca el rival
        if (!sp.isRivalStarts()) {
            //Obtiene los datos de recuperacion del balon
            int[] recuperadores = sp.getRecoveryBall();
            //Si existe posibilidad de recuperar el balon
            if (recuperadores.length > 1) {
                //Obtiene las coordenadas del balon en el instante donde
                //se puede recuperar el balon
                double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
                //Recorre la lista de jugadores que pueden recuperar
                for (int i = 1; i < 2; i++) {
                    //Ordena a los jugadores recuperadores que se ubique
                    //en la Position de recuperacion
                    Commands.add(new CommandMoveTo(recuperadores[i],
                            new Position(posRecuperacion[0], posRecuperacion[1])));
                }
            }
        }
    }
    private void posInicio(GameSituations sp,int cercano){
        Position[] jugadores = sp.myPlayers();
        for (int i = 0; i < jugadores.length; i++) {
            if( i!=cercano ){
                Commands.add(new CommandMoveTo(i, alineacion1[i]));
            } 
        }
    }
    private void ataque(GameSituations sp){
         //Obtiene las Positiones de tus jugadores
        Position[] jugadores = sp.myPlayers();
        //Obtengo la posicion del portero contrario
        Position PositionPorteroContrario = sp.rivalPlayers()[0];
        Position ball=sp.ballPosition();
        //miro el mas cercano al balón y lo muevo hacia él con un sprint
        int cercano=ball.nearestIndex(jugadores);
        Commands.add(new CommandMoveTo(cercano,ball,true));
       
        //miro que haya cerca alguno que no sea de la parte alta (delanteros)
         if (cercano!=10 && cercano!=9 && cercano!=8){
             //calculo de manera aleatoria a quien se lo envio de los extremos en caso de que no sea uno de ellos
             int extremo=(int)(Math.random()*3)+8;
             Commands.add(new CommandHitBall(cercano, sp.myPlayers()[extremo], 1, true));
         }
         else{//soy uno de los extremos 
             if(sp.canKick().length>0){
                 //esto es que alguno de mis extremos están en condiciones de rematar y tiro a puerta
                 Position posTiro;
                 int tiro=(int)(Math.random()*6);
                 if(tiro==0){
                     posTiro=Constants.centroArcoSup;
                 }
                 else if(tiro==1){
                     posTiro=Constants.posteDerArcoSup;
                 }
                 else if(tiro==2){
                     posTiro=Constants.posteIzqArcoSup;
                 }
                 else if(tiro==3){
                     posTiro=Constants.centroArcoInf;
                 }
                 else if(tiro==4){
                     posTiro=Constants.posteIzqArcoInf;
                 }
                 else{
                     posTiro=Constants.posteDerArcoInf;
                 }
                  Commands.add(new CommandHitBall(sp.canKick()[0],Position.medium(PositionPorteroContrario,posTiro), 1, true));   
             }
             else{
                 //Soy un extremo pero no estoy encondiciones de rematar, entonces se la paso a otro extremo
                 if (cercano==10){
                     Commands.add(new CommandHitBall(cercano, sp.myPlayers()[9], 0.5, false));
                 }
                 else if(cercano==9){
                     Commands.add(new CommandHitBall(cercano, sp.myPlayers()[8],0.5, false));
                 }
                 else{
                     Commands.add(new CommandHitBall(cercano, sp.myPlayers()[10], 0.5, false));
                 }
             }
         }
    }
}