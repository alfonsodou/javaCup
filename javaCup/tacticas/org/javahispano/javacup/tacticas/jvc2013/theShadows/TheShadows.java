package org.javahispano.javacup.tacticas.jvc2013.theShadows;


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

public class TheShadows implements Tactic {
		
	//Lista de comandos
	LinkedList<Command> comandos = new LinkedList<Command>();

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "The Shadows";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Megustalagafas";
        }

        public Color getShirtColor() {
            return new Color(0, 0, 0);
        }

        public Color getShortsColor() {
            return new Color(0, 0, 0);
        }

        public Color getShirtLineColor() {
            return new Color(0, 0, 0);
        }

        public Color getSocksColor() {
            return new Color(0, 0, 0);
        }

        public Color getGoalKeeper() {
            return new Color(255, 255, 255        );
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
            return new Color(255, 255, 255);
        }

        public Color getSocksColor2() {
            return new Color(255, 255, 255);
        }

        public Color getGoalKeeper2() {
            return new Color(0, 0, 0        );
        }

        public EstiloUniforme getStyle2() {
            return EstiloUniforme.SIN_ESTILO;
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
                new JugadorImpl("Jugador", 1, new Color(0,0,0), new Color(0,0,0),1.0d,0.7d,0.3d, true),
                new JugadorImpl("Jugador", 2, new Color(0,0,0), new Color(0,0,0),1.0d,0.55d,0.7d, false),
                new JugadorImpl("Jugador", 3, new Color(0,0,0), new Color(0,0,0),1.0d,0.75d,0.5d, false),
                new JugadorImpl("Jugador", 4, new Color(0,0,0), new Color(0,0,0),1.0d,0.75d,0.5d, false),
                new JugadorImpl("Jugador", 5, new Color(0,0,0), new Color(0,0,0),1.0d,0.55d,0.7d, false),
                new JugadorImpl("Jugador", 6, new Color(0,0,0), new Color(0,0,0),1.0d,0.75d,1.0d, false),
                new JugadorImpl("Jugador", 7, new Color(0,0,0), new Color(0,0,0),0.75d,0.75d,1.0d, false),
                new JugadorImpl("Jugador", 8, new Color(0,0,0), new Color(0,0,0),0.75d,0.75d,1.0d, false),
                new JugadorImpl("Jugador", 9, new Color(0,0,0), new Color(0,0,0),1.0d,0.75d,1.0d, false),
                new JugadorImpl("Jugador", 10, new Color(0,0,0), new Color(0,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Jugador", 11, new Color(0,0,0), new Color(0,0,0),1.0d,1.0d,1.0d, false)
            };
	        }
	    }

    TacticDetail detalle = new TacticDetailImpl();
    
    public TacticDetail getDetail() {
        return detalle;
    }

    static Position alineacion1[]=new Position[]{
        new Position(-0.23776223776223776,-37.77149321266968),
        new Position(-8.083916083916083,-15.203619909502263),
        new Position(-0.23776223776223776,0.47511312217194573),
        new Position(-0.4755244755244755,18.766968325791854),
        new Position(6.6573426573426575,-14.728506787330318),
        new Position(-26.391608391608393,28.98190045248869),
        new Position(-15.93006993006993,12.115384615384617),
        new Position(14.97902097902098,11.877828054298643),
        new Position(27.58041958041958,28.744343891402718),
        new Position(-5.944055944055944,34.920814479638004),
        new Position(6.181818181818182,34.920814479638004)
    };

    static Position initOwn[]=new Position[]{
            new Position(0.23776223776223776,-48.699095022624434),
            new Position(-27.104895104895103,-21.380090497737555),
            new Position(-8.797202797202797,-34.44570135746606),
            new Position(8.083916083916083,-34.44570135746606),
            new Position(26.867132867132867,-21.61764705882353),
            new Position(-20.685314685314687,-1.4253393665158371),
            new Position(-8.797202797202797,-14.490950226244346),
            new Position(7.37062937062937,-14.728506787330318),
            new Position(20.447552447552447,-1.4253393665158371),
            new Position(-1.902097902097902,0.0),
            new Position(1.4265734265734267,-0.23755656108597287)
        };

	static Position initRival[]=new Position[]{
	        new Position(0.2595419847328244,-50.41044776119403),
	        new Position(-20.923076923076923,-27.794117647058822),
	        new Position(-8.321678321678322,-34.68325791855204),
	        new Position(7.846153846153847,-34.44570135746606),
	        new Position(20.923076923076923,-28.031674208144796),
	        new Position(-16.167832167832167,-11.165158371040723),
	        new Position(-4.755244755244756,-17.57918552036199),
	        new Position(2.6153846153846154,-13.303167420814479),
	        new Position(14.503496503496503,-12.115384615384617),
	        new Position(-9.272727272727272,0.0),
	        new Position(9.272727272727272,0.0)
	};

    public Position[] getStartPositions(GameSituations sp) {
    	return initOwn;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    	return initRival;
    }

	@Override
    public List<Command> execute(GameSituations sp) {

		//Limpia la lista de comandos
		comandos.clear();
		
		//Obtiene las posiciones de tus jugadores
		Position[] jugadores = sp.myPlayers();
		
		for (int i = 0; i < jugadores.length; i++)
		{
			//Ordena a cada jugador que se ubique segun la alineacion1
			comandos.add(new CommandMoveTo(i, alineacion1[i]));
		}
		
		//Si no saca el rival
		if (!sp.isRivalStarts())
		{
			//Obtiene los datos de recuperacion del balon
			int[] recuperadores = sp.getRecoveryBall();
			
			//Si existe posibilidad de recuperar el balon
			if (recuperadores.length > 1)
			{
				//Obtiene las coordenadas del balon en el instante donde se puede recuperar el balon
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				
				//Recorre la lista de jugadores que pueden recuperar
				for (int i = 1; i < recuperadores.length; i++)
				{
					//Ordena a los jugadores recuperadores que se ubique
					//en la posicion de recuperacion
					comandos.add(new CommandMoveTo(recuperadores[i],
					new Position(posRecuperacion[0], posRecuperacion[1])));
				}
			}
		}
		
		//Instancia un generador aleatorio
		Random r = new Random();
		
		//Recorre la lista de mis jugadores que pueden rematar
		for (int i : sp.canKick())
		{
			//Si el jugador es de indice 8 o 10
			if (i == 8 || i == 10)
			{
				//condicion aleatoria
				if (r.nextBoolean())
				{
					//Ordena que debe rematar al centro del arco
					comandos.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 12 + r.nextInt(6)));
				} else if (r.nextBoolean())
				{
					//Ordena que debe rematar al poste derecho
					comandos.add(new CommandHitBall(i, Constants.posteDerArcoSup, 1, 12 + r.nextInt(6)));
				} else
				{
					//Ordena que debe rematar al poste izquierdo
					comandos.add(new CommandHitBall(i, Constants.posteIzqArcoSup, 1, 12 + r.nextInt(6)));
				}
			}
			else
			{
				//inicia contador en cero
				int count = 0;
				int jugadorDestino;
				
				//Repetir mientras el jugador destino sea igual al jugador que remata
				//o mientras la coordenada y del jugador que remata
				//es mayor que la coordenada y del que recibe
				//Y mientras el contador es menor a 20
				while (((jugadorDestino = r.nextInt(11)) == i || jugadores[i].getY() > jugadores[jugadorDestino].getY()) && count < 20)
				{
					//incrementa el contador
					count++;
				}
				
				//Si el receptor del balon es el que remata
				if (i == jugadorDestino)
				{
					while ((jugadorDestino = r.nextInt(jugadores.length)) == i) {
					}
				}
				
				//Ordena que el jugador que puede rematar que de un pase al jugador destino
				comandos.add(new CommandHitBall(i, jugadores[jugadorDestino], 1, r.nextInt(45)));
			}
		}
		
		//Retorna la lista de comandos
		return comandos;
	}
}