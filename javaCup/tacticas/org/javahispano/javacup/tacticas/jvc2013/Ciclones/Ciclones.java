package org.javahispano.javacup.tacticas.jvc2013.Ciclones;


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

public class Ciclones implements Tactic {
	
	// Declaramos constantes para los índices de nuestros jugadores
	public final int JoseLuis = 0;
	public final int Rafael = 1;
	public final int Adrian = 2;
	public final int Sheke = 3;
	public final int Habimael = 4;
	public final int JoseAngel = 5;
	public final int Carrenho = 6;
	public final int Julio = 7;
	public final int Roger = 8;
	public final int Christian = 9;
	public final int Picos = 10;

    Position alineacion1[]=new Position[]{
        /** new Position(0.2595419847328244,-50.41044776119403),
        new Position(-20.20979020979021,-13.303167420814479),
        new Position(-11.888111888111888,-27.556561085972852),
        new Position(10.937062937062937,-27.794117647058822),
        new Position(17.832167832167833,-13.540723981900454),
        new Position(-29.006993006993007,0.47511312217194573),
        new Position(1.902097902097902,10.214932126696834),
        new Position(-1.188811188811189,-9.97737556561086),
        new Position(27.81818181818182,0.47511312217194573),
        new Position(-14.027972027972028,19.95475113122172),
        new Position(12.363636363636363,26.84389140271493) **/
		new Position(0.2595419847328244,-50.41044776119403),
        new Position(-19.97202797202797,-21.855203619909503),
        new Position(-6.6573426573426575,-27.556561085972852),
        new Position(6.6573426573426575,-27.556561085972852),
        new Position(20.447552447552447,-22.330316742081447),
        new Position(-12.839160839160838,-12.115384615384617),
        new Position(-0.4755244755244755,5.9389140271493215),
        new Position(-0.951048951048951,-18.29185520361991),
        new Position(10.461538461538462,-12.352941176470589),
        new Position(-14.027972027972028,19.95475113122172),
        new Position(12.363636363636363,26.84389140271493)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-26.153846153846157,-19.004524886877828),
        new Position(-10.223776223776223,-31.59502262443439),
        new Position(8.55944055944056,-31.119909502262445),
        new Position(21.874125874125873,-19.717194570135746),
        new Position(-13.076923076923078,-11.402714932126697),
        new Position(0.7132867132867133,9.027149321266968),
        new Position(-2.13986013986014,-22.330316742081447),
        new Position(8.55944055944056,-10.452488687782806),
        new Position(-14.74125874125874,18.29185520361991),
        new Position(14.027972027972028,26.84389140271493)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(26.732824427480914,-20.111940298507463),
        new Position(-29.32824427480916,-21.67910447761194),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(-18.946564885496183,-0.26119402985074625),
        new Position(18.946564885496183,-0.26119402985074625),
        new Position(-19.46564885496183,35.78358208955224),
        new Position(-0.2595419847328244,19.067164179104477),
        new Position(18.946564885496183,35.26119402985075)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(11.16030534351145,-1.3059701492537314),
        new Position(-10.641221374045802,-0.7835820895522387),
        new Position(-27.251908396946565,31.6044776119403),
        new Position(-10.641221374045802,30.559701492537314),
        new Position(9.603053435114505,28.992537313432837),
        new Position(25.69465648854962,28.992537313432837)
    };

    Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-22.825174825174827,-20.429864253393667),
        new Position(-4.5174825174825175,-26.131221719457013),
        new Position(5.230769230769231,-25.893665158371043),
        new Position(23.776223776223777,-20.667420814479637),
        new Position(-22.349650349650346,-0.7126696832579186),
        new Position(-13.076923076923078,-11.64027149321267),
        new Position(13.314685314685315,-11.64027149321267),
        new Position(23.776223776223777,0.0),
        new Position(-0.23776223776223776,-0.23755656108597287),
        new Position(12.601398601398602,-0.47511312217194573)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-27.34265734265734,-11.64027149321267),
        new Position(-9.510489510489512,-20.667420814479637),
        new Position(6.6573426573426575,-20.429864253393667),
        new Position(24.251748251748253,-11.402714932126697),
        new Position(-10.223776223776223,-0.47511312217194573),
        new Position(-20.685314685314687,-5.463800904977376),
        new Position(15.692307692307693,-5.463800904977376),
        new Position(9.986013986013985,-0.23755656108597287),
        new Position(-5.230769230769231,-8.076923076923077),
        new Position(4.9930069930069925,-8.314479638009049)
    };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Cherios";
        }

        public String getCountry() {
            return "México";
        }

        public String getCoach() {
            return "Christian";
        }

        public Color getShirtColor() {
            return new Color(255, 255, 255);
        }

        public Color getShortsColor() {
            return new Color(255, 255, 255);
        }

        public Color getShirtLineColor() {
            return new Color(0, 51, 204);
        }

        public Color getSocksColor() {
            return new Color(255, 255, 255);
        }

        public Color getGoalKeeper() {
            return new Color(0, 0, 153        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.FRANJA_HORIZONTAL;
        }

        public Color getShirtColor2() {
            return new Color(153, 21, 155);
        }

        public Color getShortsColor2() {
            return new Color(201, 109, 122);
        }

        public Color getShirtLineColor2() {
            return new Color(11, 129, 88);
        }

        public Color getSocksColor2() {
            return new Color(218, 241, 15);
        }

        public Color getGoalKeeper2() {
            return new Color(142, 144, 134        );
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
                new JugadorImpl("José Luis", 1, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, true),
                new JugadorImpl("Rafael", 2, new Color(255,200,150), new Color(50,0,0),0.6d,1.0d,0.5d, false),
                new JugadorImpl("Adrian", 3, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Sheke", 4, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Habimael", 5, new Color(255,200,150), new Color(50,0,0),0.6d,0.71d,0.5d, false),
                new JugadorImpl("José Ángel", 6, new Color(255,200,150), new Color(50,0,0),0.6d,0.67d,0.5d, false),
                new JugadorImpl("Carreño", 7, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Julio", 8, new Color(255,200,150), new Color(50,0,0),0.6d,0.67d,0.75d, false),
                new JugadorImpl("Roger", 9, new Color(255,200,150), new Color(50,0,0),0.6d,0.7d,0.5d, false),
                new JugadorImpl("Christian", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Picos", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)
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
    
    public List<Command> execute(GameSituations sp) {
    	// Limpiamos los comandos
    	comandos.clear();
    	
    	
    	/** V A R I A B L E S **/
    	// Declaramos un random para generar decisiones aleatorias
    	Random r = new Random();
    	// Obtenemos la posición de nuestros jugadores
    	Position[] misJugadores = sp.myPlayers();
    	// Obtenemos los detalles de nuestros jugadores
    	PlayerDetail[] misJugadoresDet = sp.myPlayersDetail();
    	// Obtenemos la posición de los rivales
    	Position[] rivales = sp.rivalPlayers();
    	// Obtenemos los detalles de los rivales
    	PlayerDetail[] rivalesDet = sp.rivalPlayersDetail();
		// Obetenemos un delantero al azar para pasarle el balón
		//int delanteroDestino = ObtenDelanteroAlAzar();
		// Obetenemos un medio al azar para pasarle el balón
		int medioDestino = ObtenMedioAlAzar();
		// Obtenemos el índice del portero rival
		int indicePorteroRival = ObtenIndicePorteroRival(rivales, rivalesDet);
		// Obtenemos la posición del balón
		Position balon = sp.ballPosition();
		// Obtenemos los índices de los rivales que pueden rematar
		int[] rivalesQuePuedenRematar = sp.rivalCanKick();
		// Variable boleana para saber si el rival realizará un saque
		boolean rivalRealizaraSaque = sp.isRivalStarts();
		// Obtenemos las posiciones de los posibles jugadores
		int[] posiblesRecuperadores = sp.getRecoveryBall();
		// Obtenemos las pocisiones de los posibles rematadores
		int[] posiblesRematadores = sp.canKick();
		// Variable para almacenar la potencia con la que se dará un pase
		double potenciaPase = 0;
		// Obtenemos el índice del rival más cercano al balón
		int rivalCercanoAlBalon = sp.ballPosition().nearestIndex(rivales);
		/** ---------------------------------------------------------------------------------------------- **/
		
		
		/** I N I C I A M O S   A   G E N E R A R   N U E S T R A   T Á C T I C A **/
		comandos.add(new CommandMoveTo(JoseLuis, new Position(Constants.centroArcoSup.getX(), -50)));
		// Establecemos alineación ofensiva o defensiva, según sea la posición del balón
		EstableceAlineacion(misJugadores, balon);
		// Recuperamos el balón
		RecuperaBalon(rivalesQuePuedenRematar, balon, misJugadores);
		// Recuperamos la posición de nuestros jugadores
		RecuperaPosiciones(rivalRealizaraSaque, posiblesRecuperadores, sp, misJugadores, rivales[rivalCercanoAlBalon]);
		
		// Iniciamos el ataque
		for (int indJugador : posiblesRematadores) {
			// Más variables
			int delanteroDestino = ObtenDelanteroAlAzar(indJugador);
			
			if (sp.isStarts()) {
				potenciaPase = CalculaPotenciaPase(misJugadores[indJugador], misJugadores[delanteroDestino]);
				comandos.add(new CommandHitBall(indJugador, misJugadores[delanteroDestino], potenciaPase, true));
			} else 
			if (indJugador == Christian || indJugador == Picos) {
				if (r.nextBoolean()) {
					if (misJugadores[indJugador].getY() > 35) {
						potenciaPase = CalculaPotenciaPase(misJugadores[indJugador], misJugadores[delanteroDestino]);
						comandos.add(new CommandHitBall(indJugador, misJugadores[delanteroDestino], potenciaPase, 15));
					} else {
						Position direccionDelRemate = CalculaDireccionRemate(rivales[indicePorteroRival], balon);
						comandos.add(new CommandHitBall(indJugador, direccionDelRemate, 1, 15));
					}
				} else {
					potenciaPase = CalculaPotenciaPase(misJugadores[indJugador], misJugadores[delanteroDestino]);
					comandos.add(new CommandHitBall(indJugador, misJugadores[delanteroDestino], potenciaPase, 15));
				}
			} else if (indJugador == JoseLuis || indJugador == Adrian || indJugador == Sheke) {
				//double distanciaPorteroBalon = misJugadores[indJugador].distance(sp.ballPosition());
				//double distanciaRivalBalon = rivales[rivalCercanoAlBalon].distance(sp.ballPosition());
				//double distanciaJugadorRival = misJugadores[indJugador].distance(rivales[rivalCercanoAlBalon]);
				
				/**if (distanciaJugadorRival <=5) {
					double balonX = 0;
					double balonY = 0;
					
					balonY = Constants.cornerSupIzq.getY() + 10;
					if (sp.ballPosition().getX() < 0) {
						balonX = -30;
					} else {
						balonX = 30;
					}
					comandos.add(new CommandHitBall(indJugador, new Position(balonX, balonY), 1, true));
				} else {**/
					comandos.add(new CommandHitBall(indJugador, misJugadores[delanteroDestino], 1, true));
				//}
			} else {
				potenciaPase = CalculaPotenciaPase(misJugadores[indJugador], misJugadores[delanteroDestino]);
				comandos.add(new CommandHitBall(indJugador, misJugadores[delanteroDestino], potenciaPase, 15));
			}
		}
		/** ---------------------------------------------------------------------------------------------- **/
		
        return comandos;
    }
    
    // Obtiene un delantero al azar, ya sea Christian o Picos
    public int ObtenDelanteroAlAzar(int jugadorActual) {
    	int delantero = 0;
    	Random r = new Random();
    	
    	if (jugadorActual == Picos) {
			delantero = Christian;
		} else {
			delantero = Picos;
		}
    	
    	return delantero;
    }
    
    // Obtiene un medio al azar, ya sea Carrenho o Julio
    public int ObtenMedioAlAzar() {
    	int medio = 0;
    	Random r = new Random();
    	
    	if (r.nextBoolean()) {
    		medio = Carrenho;
		} else {
			medio = Julio;
		}
    	
    	return medio;
    }
    
    // Obtiene el índice del portero rival
 	public int ObtenIndicePorteroRival(Position[] rivales, PlayerDetail[] rivalesDet) {
 		int indPortero = 0;
 		
 		for (int indJugador = 0; indJugador < rivales.length - 1; indJugador++) {
 	 		if (rivalesDet[indJugador].isGoalKeeper()) {
 	 			indPortero = indJugador;
 	 		}
 	 	}
 		
 		return indPortero;
 	}
 	
 	// Establece alineacón ofensiva o defensiva, según sea la posición del balón
 	public void EstableceAlineacion(Position[] misJugadores, Position balon) {
 		if (balon.getY() > -10) {
			for (int indJugador = 1; indJugador < misJugadores.length; indJugador++) {
				comandos.add(new CommandMoveTo(indJugador, alineacion1[indJugador]));
			}
		} else {
			for (int indJugador = 1; indJugador < misJugadores.length; indJugador++) {
				comandos.add(new CommandMoveTo(indJugador, alineacion1[indJugador]));
			}
		}
 	}
 	
 	// Recupera el balón, en base a los rivales que puedan rematar
 	public void RecuperaBalon(int[] rivalesQuePuedenRematar, Position balon, Position[] misJugadores) {
 		if (rivalesQuePuedenRematar.length > 0) {
			for (int indJugador = 0; indJugador < misJugadores.length - 1; indJugador++) {
				comandos.add(new CommandMoveTo(indJugador, balon));
			}
		}
 	}
    
 	// Recupera la posición de nuestros jugadores
 	public void RecuperaPosiciones(boolean rivalRealizaraSaque, int[] posiblesRecuperadores, GameSituations sp, Position[] misJugadores, Position rivalCercanoAlBalon) {
 		if (!rivalRealizaraSaque) {
			if (posiblesRecuperadores.length > 0) {
				double[] trayectoriaBalon = sp.getTrajectory(posiblesRecuperadores[0]);
				for (int indJugador = 1; indJugador < posiblesRecuperadores.length; indJugador++) {
					comandos.add(new CommandMoveTo(
							posiblesRecuperadores[indJugador],
							new Position(trayectoriaBalon[0], trayectoriaBalon[1])));
				}
			} else {
				for (int indJugador = 0; indJugador < misJugadores.length - 1; indJugador++) {
					comandos.add(new CommandMoveTo(indJugador, alineacion1[indJugador]));
				}
			}
		}
 	}

 	// Calcula la potencia con la que dará un pase
 	public double CalculaPotenciaPase(Position jugadorQuePasa, Position jugadorQueRecibe) {
 		double maximaDistanciaAlRematar = 30;
 		double distanciaARecorrer = jugadorQuePasa.distance(jugadorQueRecibe);
		double potencia = 0;
		
		if (distanciaARecorrer <= maximaDistanciaAlRematar) {
			potencia = ((100/maximaDistanciaAlRematar) * distanciaARecorrer) / 100;
		} else {
			potencia = 1;
		}
		
		return potencia;
 	}

 	// Calcula la dirección en la que se realizará el remate
 	public Position CalculaDireccionRemate(Position portero, Position balon) {
 		Position direccionDelRemate;
 		
 		if ((portero.getX() >= -4 || portero.getX() <= 4) && (balon.getY() <= 35)){
			direccionDelRemate = Constants.centroArcoSup;
		} else if (portero.getX() <= -4) {
 			direccionDelRemate = new Position(Constants.posteIzqArcoSup.getX() - 1, Constants.posteIzqArcoSup.getY());
		} else {
			direccionDelRemate = new Position(Constants.posteDerArcoSup.getX() + 1, Constants.posteDerArcoSup.getY());
		}
 		
 		return direccionDelRemate;
 	}

}


























