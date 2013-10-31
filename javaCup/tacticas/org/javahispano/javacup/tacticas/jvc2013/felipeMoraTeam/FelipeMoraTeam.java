package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;


import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

public class FelipeMoraTeam implements Tactic {
		
	//Lista de comandos
	LinkedList<Command> comandos = new LinkedList<Command>();
	
	LinkedList<Player> players = new LinkedList<Player>();
	
	boolean initialized = false;
	
	boolean getInitialized(){
		return initialized;
	}
	
	void setInitialized(boolean init){
		initialized = init;
	}

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "FelipeMoraTeam";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "FMCoders";
        }

        public Color getShirtColor() {
            return new Color(255, 255, 0);
        }

        public Color getShortsColor() {
            return new Color(0, 0, 255);
        }

        public Color getShirtLineColor() {
            return new Color(0, 0, 255);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 0);
        }

        public Color getGoalKeeper() {
            return new Color(0, 0, 0        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.FRANJA_HORIZONTAL;
        }

        public Color getShirtColor2() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor2() {
            return new Color(255, 255, 255);
        }

        public Color getShirtLineColor2() {
            return new Color(204, 0, 0);
        }

        public Color getSocksColor2() {
            return new Color(204, 0, 0);
        }

        public Color getGoalKeeper2() {
            return new Color(102, 0, 0        );
        }

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
	                new JugadorImpl("Jugador", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.3d, true),
	                new JugadorImpl("Jugador", 2, new Color(255,200,150), new Color(50,0,0),0.85d,0.55d,0.7d, false),
	                new JugadorImpl("Jugador", 3, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,0.5d, false),
	                new JugadorImpl("Jugador", 4, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,0.5d, false),
	                new JugadorImpl("Jugador", 5, new Color(255,200,150), new Color(50,0,0),0.85d,0.55d,0.7d, false),
	                new JugadorImpl("Jugador", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,1.0d, false),
	                new JugadorImpl("Jugador", 7, new Color(255,200,150), new Color(50,0,0),0.75d,0.75d,1.0d, false),
	                new JugadorImpl("Jugador", 8, new Color(255,200,150), new Color(50,0,0),0.75d,0.75d,1.0d, false),
	                new JugadorImpl("Jugador", 9, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,1.0d, false),
	                new JugadorImpl("Jugador", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
	                new JugadorImpl("Jugador", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)
	            };
	        }
	    }

    TacticDetail detalle = new TacticDetailImpl();
    
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    	return Global.initOwn;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    	return Global.initRival;
    }
    
    
    public Player GetInstance( char c, int index )
    {
    	Player player = null;
    	
    	switch( c )
    	{
    	case 'G':
    		player = new Goalkeeper( comandos, index );
    		break;
    	case 'L':
    		player = new SideBack( comandos, index );
    		break;
    	case 'C':
    		player = new CentreBack( comandos, index );
    		break;
    	case 'E':
    		player = new SideMid( comandos, index );
    		break;
    	case 'M':
    		player = new CentreMid( comandos, index );
    		break;
    	case 'F':
    		player = new Forward( comandos, index );
    		break;
    	default:
    		break;
    	}
    	
    	return player;
    }
    
    public void RefreshTactic( )
    {
    	players.clear();
    	char [] tacticPos = Global.GetFormationPlayerTypes( );
    	for( int i = 0; i < Global.NPLAYERS; i++ )
    	{
    		players.add( GetInstance( tacticPos[i], i ) );
    	}
    }
    
	private void haveCommandClear() {
		if( players != null && players.size() > 0)
		{
			for(int i = 0; i < Global.NPLAYERS; i++)
			{
				players.get(0).set_haveCommand(false);
			}
		}
	}

	@Override
    public List<Command> execute(GameSituations sp) {

    	if(!getInitialized()){
    		
    		Global.situation.init( sp );    		
    		setInitialized(true);
    	}
    	comandos.clear();
    	Global.situation.update(sp);
    	Global.teamDecisions.calcTactic();
    	
    	if( Global.teamDecisions.needToRefreshPlayers )
    	{
    		RefreshTactic( );
    	}
    	
    	haveCommandClear();
    	
    	for (int i = 0; i < players.size(); i++) {
    		players.get(i).execute();
    	}
    	
    	Global.situation.ballPositionAntUpd();
    	
    	return comandos;
    }
}