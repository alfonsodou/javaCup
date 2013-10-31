package org.javahispano.javacup.tacticas.jvc2013.enavas;

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

public class RealUnion implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(15.692307692307693,-24.468325791855204),
        new Position(-16.167832167832167,-24.468325791855204),
        new Position(-4.755244755244756,-32.07013574660634),
        new Position(-1.188811188811189,-18.29185520361991),
        new Position(5.468531468531468,-32.07013574660634),
        new Position(-17.832167832167833,-6.414027149321266),
        new Position(15.216783216783217,-6.414027149321266),
        new Position(0.23776223776223776,-0.23755656108597287),
        new Position(-5.706293706293707,-11.402714932126697),
        new Position(6.895104895104895,-10.690045248868778)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(15.216783216783217,-28.269230769230766),
        new Position(-17.832167832167833,-28.031674208144796),
        new Position(-6.419580419580419,-33.970588235294116),
        new Position(-0.951048951048951,-21.380090497737555),
        new Position(5.468531468531468,-33.73303167420815),
        new Position(-13.076923076923078,-4.98868778280543),
        new Position(13.79020979020979,-5.226244343891403),
        new Position(-1.6643356643356644,-9.502262443438914),
        new Position(-9.272727272727272,-14.25339366515837),
        new Position(6.895104895104895,-15.203619909502263)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.0,-45.58371040723982),
        new Position(25.44055944055944,4.513574660633484),
        new Position(-25.44055944055944,2.8506787330316743),
        new Position(-8.321678321678322,0.601809954751132),
        new Position(-0.4755244755244755,13.065610859728507),
        new Position(6.6573426573426575,0.552036199095022),
        new Position(-19.020979020979023,31.119909502262445),
        new Position(14.027972027972028,31.59502262443439),
        new Position(-2.377622377622378,37.29638009049774),
        new Position(-10.937062937062937,22.330316742081447),
        new Position(5.468531468531468,21.855203619909503)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(16.88111888111888,-27.556561085972852),
        new Position(-17.356643356643357,-27.08144796380091),
        new Position(-5.468531468531468,-33.970588235294116),
        new Position(0.23776223776223776,-23.042986425339365),
        new Position(4.9930069930069925,-33.970588235294116),
        new Position(-15.93006993006993,-12.115384615384617),
        new Position(13.79020979020979,-14.25339366515837),
        new Position(-0.7132867132867133,-6.414027149321266),
        new Position(-5.706293706293707,-15.203619909502263),
        new Position(5.706293706293707,-16.153846153846153)
    };

    Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(5.969465648854961,-5.485074626865671),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(22.580152671755727,-1.3059701492537314)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(6.4885496183206115,-6.529850746268657),
        new Position(-6.4885496183206115,-6.529850746268657),
        new Position(22.580152671755727,-1.3059701492537314)
    };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Real Union de Irun";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Eduardo Navas";
        }

        public Color getShirtColor() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor() {
            return new Color(51, 51, 51);
        }

        public Color getShirtLineColor() {
            return new Color(0, 0, 0);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 255);
        }

        public Color getGoalKeeper() {
            return new Color(255, 255, 0        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.FRANJA_HORIZONTAL;
        }

        public Color getShirtColor2() {
            return new Color(0, 0, 0);
        }

        public Color getShortsColor2() {
            return new Color(204, 204, 204);
        }

        public Color getShirtLineColor2() {
            return new Color(255, 255, 255);
        }

        public Color getSocksColor2() {
            return new Color(0, 0, 0);
        }

        public Color getGoalKeeper2() {
            return new Color(153, 0, 0        );
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
                new JugadorImpl("Imanol Romero", 1, new Color(255,200,150), new Color(204,0,0),0.5d,1.0d,1.0d, true),
                new JugadorImpl("Roberto Carlos", 2, new Color(255,200,150), new Color(255,204,153),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Sergio Campos", 3, new Color(255,200,150), new Color(50,0,0),0.5d,1.0d,1.0d, false),
                new JugadorImpl("Michel Salgado", 4, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Iñigo Mitxelena", 5, new Color(255,200,150), new Color(204,204,0),1.0d,0.65d,0.5d, false),
                new JugadorImpl("Xabi Alonso", 6, new Color(255,200,150), new Color(50,0,0),0.85d,0.9d,0.75d, false),
                new JugadorImpl("Isco", 7, new Color(255,200,150), new Color(50,0,0),0.85d,0.85d,0.8d, false),
                new JugadorImpl("Luis Figo", 8, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Eduardo Navas", 9, new Color(219,141,69), new Color(17,1,1),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Zizou", 10, new Color(255,200,150), new Color(50,0,0),0.5d,1.0d,1.0d, false),
                new JugadorImpl("Eric Bengoetxea", 11, new Color(255,200,150), new Color(17,0,0),0.6d,1.0d,0.75d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion1;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion2;
    }

    LinkedList<Command> comandos = new LinkedList<Command>();
    DatosPartido datos= new DatosPartido();
    public List<Command> execute(GameSituations sp) {
    	comandos.clear();
	
    	for(int i:sp.canKick()){
    		if(sp.ballPosition().getX()==0&&sp.ballPosition().getY()==0){
    			Position posPase=sp.myPlayers()[1];
    			posPase=new Position(Constants.ANCHO_CAMPO/2, -2);
    			double potencia = datos.calculaPotencia(sp,sp.myPlayers()[i],posPase);
    			comandos.add(new CommandHitBall(i, posPase ,potencia*sp.myPlayersDetail()[i].getPower() , true));
    		}else if(sp.ballPosition().getY()<=-5){
    			if(sp.ballPosition().getX()<0){
    				Position posPase=sp.myPlayers()[2];
    				double potencia = datos.calculaPotencia(sp,sp.myPlayers()[i],posPase);
        			comandos.add(new CommandHitBall(i, posPase ,potencia*sp.myPlayersDetail()[i].getPower() , true));
    			}else{
    				Position posPase=sp.myPlayers()[1];
    				double potencia = datos.calculaPotencia(sp,sp.myPlayers()[i],posPase);
        			comandos.add(new CommandHitBall(i, posPase ,potencia*sp.myPlayersDetail()[i].getPower() , true));
    			}
    		}else if(sp.ballPosition().getY()>-5){
    			Position posPase=Constants.centroArcoSup;
    			double potencia = datos.calculaPotencia(sp,sp.myPlayers()[i],posPase);
    			if(potencia==1){
    				potencia=0.2;
    			}
    			comandos.add(new CommandHitBall(i,posPase,potencia , true));
    		}else{
    			Position posPase=Constants.centroArcoSup;
    			comandos.add(new CommandHitBall(i,posPase, datos.calculaPotencia(sp,sp.myPlayers()[i],posPase), true));
    		}
    	}
    	
    	posicionarJugadores3(sp);
    	comandos.add(new CommandMoveTo(0, datos.posicionarPortero(sp)));
    	
    	if(sp.ballPosition().getX()==0&&sp.ballPosition().getY()==0){
    		comandos.add(new CommandMoveTo(sp.ballPosition().nearestIndex(sp.myPlayers()), sp.ballPosition()));
    	}else if(sp.ballPosition().getY()<-12){
    		comandos.add(new CommandMoveTo(0, sp.ballPosition()));
    	}else if(sp.ballPosition().getY()<5){
    		int[] exclude = new int[]{0,1,2,-1,-1,5,6,7,8,9,10};
    		comandos.add(new CommandMoveTo(sp.ballPosition().nearestIndexes(sp.myPlayers(),exclude)[0], sp.ballPosition()));
    	}else{
    		comandos.add(new CommandMoveTo(sp.ballPosition().nearestIndex(sp.myPlayers()), sp.ballPosition()));
    	}
    	
    	return comandos;
    }

	private void posicionarJugadores3(GameSituations sp) {
		for(int i=1;i<sp.myPlayers().length;i++){
			if(sp.iterationsToKick()[i]!=0){
				comandos.add(new CommandMoveTo(i, sp.ballPosition()));
			}else{
				comandos.add(new CommandMoveTo(i, alineacion3[i]));
			}
		}
	}
}