package org.javahispano.javacup.tacticas.jvc2013.xabe;


import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class AdamTeam implements Tactic {

    Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-19.46564885496183,-31.6044776119403),
        new Position(0.2595419847328244,-31.082089552238806),
        new Position(19.984732824427482,-31.6044776119403),
        new Position(13.79020979020979,-19.717194570135746),
        new Position(-15.93006993006993,-19.2420814479638),
        new Position(-27.34265734265734,-9.97737556561086),
        new Position(25.916083916083913,-6.889140271493213),
        new Position(-2.6153846153846154,-0.47511312217194573),
        new Position(-0.4755244755244755,-10.214932126696834),
        new Position(0.4755244755244755,-0.47511312217194573)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(24.727272727272727,-16.391402714932127),
        new Position(-24.727272727272727,-19.2420814479638),
        new Position(14.027972027972028,-1.900452488687783),
        new Position(-11.412587412587413,-2.6131221719457014),
        new Position(22.349650349650346,15.91628959276018),
        new Position(-19.496503496503497,10.927601809954751),
        new Position(-6.181818181818182,19.717194570135746),
        new Position(2.8531468531468533,33.970588235294116)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(27.34265734265734,-25.18099547511312),
        new Position(-29.32824427480916,-21.67910447761194),
        new Position(-5.706293706293707,-17.34162895927602),
        new Position(-24.965034965034967,-6.176470588235294),
        new Position(8.55944055944056,-17.57918552036199),
        new Position(-11.412587412587413,-1.4253393665158371),
        new Position(11.412587412587413,-1.1877828054298643),
        new Position(26.867132867132867,-1.900452488687783)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(25.44055944055944,-11.64027149321267),
        new Position(-23.538461538461537,-17.34162895927602),
        new Position(-1.902097902097902,-11.877828054298643),
        new Position(5.706293706293707,12.115384615384617),
        new Position(-25.678321678321677,17.57918552036199),
        new Position(2.13986013986014,34.20814479638009),
        new Position(-8.55944055944056,23.518099547511312),
        new Position(24.251748251748253,21.142533936651585)
    };

    Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(25.916083916083913,-27.08144796380091),
        new Position(-24.013986013986013,-28.269230769230766),
        new Position(12.125874125874127,-15.91628959276018),
        new Position(-13.076923076923078,-16.628959276018097),
        new Position(-24.013986013986013,-6.414027149321266),
        new Position(-2.377622377622378,-5.463800904977376),
        new Position(0.0,-24.943438914027148),
        new Position(23.776223776223777,-5.701357466063349)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(9.510489510489512,-19.717194570135746),
        new Position(-9.510489510489512,-19.95475113122172),
        new Position(-28.76923076923077,-7.126696832579185),
        new Position(6.4885496183206115,-6.529850746268657),
        new Position(-6.4885496183206115,-6.529850746268657),
        new Position(26.62937062937063,-6.414027149321266)
    };

    Position alineacion7[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(0.7132867132867133,-36.34615384615385),
        new Position(20.447552447552447,-26.606334841628957),
        new Position(26.867132867132867,8.552036199095022),
        new Position(-17.356643356643357,-26.368778280542987),
        new Position(0.23776223776223776,6.651583710407239),
        new Position(0.23776223776223776,-12.59049773755656),
        new Position(-24.48951048951049,7.601809954751132),
        new Position(-20.447552447552447,35.63348416289593),
        new Position(-0.951048951048951,23.755656108597286),
        new Position(21.636363636363637,35.15837104072398)
    };

    Position alineacion8[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(0.7132867132867133,-36.34615384615385),
        new Position(25.44055944055944,-32.30769230769231),
        new Position(26.867132867132867,-7.601809954751132),
        new Position(-19.97202797202797,-30.6447963800905),
        new Position(13.79020979020979,-17.57918552036199),
        new Position(-6.6573426573426575,-16.628959276018097),
        new Position(-21.3986013986014,-7.601809954751132),
        new Position(-13.314685314685315,23.993212669683256),
        new Position(15.454545454545453,24.705882352941178),
        new Position(-1.188811188811189,7.839366515837104)
    };

    Position alineacion9[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(0.7132867132867133,-36.34615384615385),
        new Position(25.44055944055944,-32.30769230769231),
        new Position(26.867132867132867,-7.601809954751132),
        new Position(-19.97202797202797,-30.6447963800905),
        new Position(13.79020979020979,-17.57918552036199),
        new Position(-6.6573426573426575,-16.628959276018097),
        new Position(-21.3986013986014,-7.601809954751132),
        new Position(0.23776223776223776,16.628959276018097),
        new Position(0.7132867132867133,0.9502262443438915),
        new Position(0.4755244755244755,29.21945701357466)
    };

    Position alineacion10[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(0.7132867132867133,-26.131221719457013),
        new Position(27.34265734265734,-18.766968325791854),
        new Position(28.055944055944057,19.717194570135746),
        new Position(-23.3006993006993,-17.57918552036199),
        new Position(20.923076923076923,-5.701357466063349),
        new Position(-17.11888111888112,-6.651583710407239),
        new Position(-26.867132867132867,16.866515837104075),
        new Position(0.0,18.529411764705884),
        new Position(0.7132867132867133,0.9502262443438915),
        new Position(0.4755244755244755,29.21945701357466)
    };

    Position alineacion11[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(0.7132867132867133,-26.131221719457013),
        new Position(27.34265734265734,-18.766968325791854),
        new Position(28.293706293706293,24.943438914027148),
        new Position(-23.3006993006993,-17.57918552036199),
        new Position(11.888111888111888,-1.900452488687783),
        new Position(-10.937062937062937,-2.8506787330316743),
        new Position(-28.293706293706293,23.755656108597286),
        new Position(-10.223776223776223,16.866515837104075),
        new Position(11.174825174825173,14.96606334841629),
        new Position(0.4755244755244755,29.21945701357466)
    };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "CMA Team";
        }

        public String getCountry() {
            return "Marruecos";
        }

        public String getCoach() {
            return "Chabir";
        }

        public Color getShirtColor() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor() {
            return new Color(0, 0, 0);
        }

        public Color getShirtLineColor() {
            return new Color(0, 102, 255);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 255);
        }

        public Color getGoalKeeper() {
            return new Color(255, 0, 0        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.FRANJA_DIAGONAL;
        }

        public Color getShirtColor2() {
            return new Color(113, 16, 228);
        }

        public Color getShortsColor2() {
            return new Color(187, 28, 38);
        }

        public Color getShirtLineColor2() {
            return new Color(224, 225, 163);
        }

        public Color getSocksColor2() {
            return new Color(235, 114, 48);
        }

        public Color getGoalKeeper2() {
            return new Color(222, 181, 139        );
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
                new JugadorImpl("Pedro", 1, new Color(255,204,204), new Color(0,0,0),0.35d,0.24d,0.75d, true),
                new JugadorImpl("Juan", 2, new Color(255,204,204), new Color(0,0,0),0.63d,0.61d,0.75d, false),
                new JugadorImpl("Ruben", 3, new Color(255,204,204), new Color(0,0,0),0.62d,0.65d,0.76d, false),
                new JugadorImpl("Alvaro", 4, new Color(255,204,204), new Color(0,0,0),0.63d,0.6d,0.68d, false),
                new JugadorImpl("Jose", 5, new Color(255,204,204), new Color(0,0,0),0.61d,0.72d,0.68d, false),
                new JugadorImpl("Pepe", 6, new Color(255,204,204), new Color(0,0,0),0.62d,0.66d,0.66d, false),
                new JugadorImpl("Ivan", 7, new Color(255,204,204), new Color(0,0,0),0.58d,0.69d,0.87d, false),
                new JugadorImpl("Ibrahim", 8, new Color(255,204,204), new Color(0,0,0),0.67d,0.68d,0.83d, false),
                new JugadorImpl("Xabi", 9, new Color(255,204,204), new Color(0,0,0),0.89d,0.87d,0.95d, false),
                new JugadorImpl("Adam", 10, new Color(255,204,204), new Color(0,0,0),0.95d,0.95d,0.94d, false),
                new JugadorImpl("Pedro", 11, new Color(255,204,204), new Color(0,0,0),0.88d,0.83d,0.89d, false)
            };
        }
    }
    
    private Util util;
    private TacticDetail detalle;
    private List<Command> comandos;
    private Position positionAttack[][];
    private Position positionDefense[][];
    private int indexAttack = 0;
    private int indexDefense = 0;
    private int stateCurrent = 0;
    
    public AdamTeam() {
		util = new Util();
		detalle = new TacticDetailImpl();
		comandos = new LinkedList<Command>();
		positionAttack = new Position[6][];
		positionAttack[0]  = alineacion2;
		positionAttack[1]  = alineacion4;
		positionAttack[2]  = alineacion7;
		positionAttack[3]  = alineacion8;
		positionAttack[4]  = alineacion9;
		positionAttack[5]  = alineacion10;
		positionDefense = new Position[4][];
		positionDefense[0]  = alineacion1;
		positionDefense[1]  = alineacion3;
		positionDefense[2]  = alineacion5;
		positionDefense[3]  = alineacion6;
		generate();
	}
    
    private void generate(){
    	indexAttack = util.generateRandom(0, positionAttack.length - 1);
		indexDefense = util.generateRandom(0, positionDefense.length - 1);
    }
    
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    	return alineacion1;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    	return alineacion3;
    }
    
    public void stateTie(GameSituations sp){
    	Position[] jugadores = sp.myPlayers();
		for (int i = 0; i < jugadores.length; i++) {
			comandos.add(new CommandMoveTo(i, positionAttack[indexAttack][i]));
		}
		util.recover(sp, comandos);
		util.canShoter(sp, comandos);
    }
    
    public void  stateWin(GameSituations sp){
    	Position[] jugadores = sp.myPlayers();
		for (int i = 0; i < jugadores.length; i++) {
			comandos.add(new CommandMoveTo(i, positionDefense[indexDefense][i]));
		}
		util.recover(sp, comandos);
		util.canShoter(sp, comandos);
    }
    
    public void stateLose(GameSituations sp){
    	Position[] jugadores = sp.myPlayers();
		for (int i = 0; i < jugadores.length; i++) {
			comandos.add(new CommandMoveTo(i, positionAttack[indexAttack][i]));
		}
		util.recover(sp, comandos);
		util.canShoter(sp, comandos);
    }
    
	public List<Command> execute(GameSituations sp) {
		comandos.clear();
		int state = 0;
		if(sp.rivalGoals() > sp.myGoals())
		{
			state = -1;
		}
		else if(sp.rivalGoals() < sp.myGoals())
		{
			state = 1;
		}
		if(stateCurrent != state)
		{
			generate();
			stateCurrent = state;
		}
		
		switch (state) {
			case 0 :	stateTie(sp);		
						break;
			case 1 :	stateWin(sp);		
						break;
			case -1 :	stateLose(sp);		
						break;
		}
		return comandos;
	}
}
