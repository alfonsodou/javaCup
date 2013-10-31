/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.tacticas.jvc2013.cucarachaAru;




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

		
	
               //  System.out.println("tiros a gol: "+tirosGol);
public class TacticaCucaracha implements Tactic {
    
    public static final double RADIO_INTERCEPTA=23.0;
    public static final double DISTANCIA_PA_CONDUCIR=14;
    
    public static final int EDO_CONDUCIENDO=0;
    public static final int EDO_TIRANDO=1;
    public static final int EDO_PASANDO=2;
    public static final int EDO_DIBLANDO=3;
    public static final int EDO_REVENTANDO=4;
    public static final double RADIO_MARCA=12;
    
    public Position posicionTiro = null;
    int tirosGol=0;
    boolean conduciendo=false;
    int jugadorConBalonGlobal=-1;
    double direccion=90;
    double fuerzaG=0;
    int edoGlobaJuego=-1;
    boolean dirGol=false;
    
	Position alineacion1[] = new Position[] {
			new Position(0.2595419847328244, -50.41044776119403),
			new Position(20.447552447552447, -32.54524886877828),
			new Position(-22.825174825174827, -32.07013574660634),
			new Position(7.37062937062937, -33.257918552036195),
			new Position(-7.37062937062937, -32.782805429864254),
			new Position(-1.188811188811189, -20.429864253393667),
			new Position(-26.153846153846157, -17.34162895927602),
			new Position(19.97202797202797, -12.352941176470589),
			new Position(-13.076923076923078, -7.364253393665159),
			new Position(-2.377622377622378, 8.314479638009049),
			new Position(7.608391608391608, -3.5633484162895925) };

	Position alineacion2[] = new Position[] {
			new Position(0.23776223776223776, -41.57239819004525),
			new Position(-20.685314685314687, -13.778280542986426),
			new Position(21.16083916083916, -15.441176470588236),
			new Position(8.797202797202797, -22.092760180995477),
			new Position(-7.132867132867133, -22.56787330316742),
			new Position(4.041958041958042, -12.828054298642533),
			new Position(-18.06993006993007, 0.0),
			new Position(14.97902097902098, -1.900452488687783),
			new Position(-8.321678321678322, -8.789592760180994),
			new Position(23.062937062937063, -0.47511312217194573),
			new Position(0.7132867132867133, -0.23755656108597287) };
        
        Position alineacion3[] = new Position[] {
			new Position(-0.4755244755244755, -44.18552036199095),
			new Position(-19.25874125874126, -24.468325791855204),
			new Position(16.405594405594407, -23.993212669683256),
			new Position(8.797202797202797, -31.59502262443439),
			new Position(-10.461538461538462, -31.357466063348415),
			new Position(3.090909090909091, -20.667420814479637),
			new Position(-25.202797202797203, -15.678733031674208),
			new Position(-9.272727272727272, -2.3755656108597285),
			new Position(-6.6573426573426575, -13.303167420814479),
			new Position(3.5664335664335667, -8.552036199095022),
			new Position(9.510489510489512, 0.0) };
	
        Position alineacion4[]=new Position[]{
	        new Position(-0.23776223776223776,-43.47285067873303),
	        new Position(20.447552447552447,-19.95475113122172),
	        new Position(-23.538461538461537,-21.380090497737555),
	        new Position(6.6573426573426575,-28.031674208144796),
	        new Position(-9.986013986013985,-28.50678733031674),
	        new Position(-7.132867132867133,-15.203619909502263),
	        new Position(-22.58741258741259,2.6131221719457014),
	        new Position(18.307692307692307,7.601809954751132),
	        new Position(5.944055944055944,-8.314479638009049),
	        new Position(-1.188811188811189,11.402714932126697),
	        new Position(-14.503496503496503,24.230769230769234)
	    };
	
        Position alineacion5[]=new Position[]{
		        new Position(0.0,-47.98642533936651),
		        new Position(12.363636363636363,-15.678733031674208),
		        new Position(-18.78321678321678,-27.08144796380091),
		        new Position(0.4755244755244755,-33.73303167420815),
		        new Position(-0.7132867132867133,-25.656108597285066),
		        new Position(-8.083916083916083,-11.165158371040723),
		        new Position(-15.93006993006993,8.552036199095022),
		        new Position(19.734265734265733,17.816742081447966),
		        new Position(-2.8531468531468533,5.463800904977376),
		        new Position(2.13986013986014,25.893665158371043),
		        new Position(-12.125874125874127,29.21945701357466)
		    };

	Position alineacion6[]=new Position[]{
	        new Position(0.0,-47.98642533936651),
	        new Position(17.11888111888112,-11.64027149321267),
	        new Position(-17.356643356643357,-19.95475113122172),
	        new Position(0.4755244755244755,-33.73303167420815),
	        new Position(0.23776223776223776,-21.142533936651585),
	        new Position(-6.6573426573426575,-9.97737556561086),
	        new Position(-13.552447552447552,12.115384615384617),
	        new Position(18.062937062937063,32.39592760180996),
	        new Position(4.041958041958042,7.839366515837104),
	        new Position(7.132867132867133,25.656108597285066),
	        new Position(-10.461538461538462,30.16968325791855)
	    };

    
    

	class TacticDetailImpl implements TacticDetail {

		public String getTacticName() {
			return "La Cucaracha Arunima";
		}

		public String getCountry() {
			return "Mexico";
		}

		public String getCoach() {
			return "Cesar Octavio Vazquez Perez";
		}

		public Color getShirtColor() {
			return new Color(255, 255, 255);
		}

		public Color getShortsColor() {
			return new Color(255, 255, 255);
		}

		public Color getShirtLineColor() {
			return new Color(102, 0, 0);
		}

		public Color getSocksColor() {
			return new Color(102, 0, 0);
		}

		public Color getGoalKeeper() {
			return new Color(102, 0, 0);
		}

		public EstiloUniforme getStyle() {
			return EstiloUniforme.FRANJA_HORIZONTAL;
		}

		public Color getShirtColor2() {
			return new Color(0, 0, 0);
		}

		public Color getShortsColor2() {
			return new Color(255,255, 255);
		}

		public Color getShirtLineColor2() {
			return new Color(0, 0, 0);
		}

		public Color getSocksColor2() {
			return new Color(0, 0, 0);
		}

		public Color getGoalKeeper2() {
			return new Color(239, 202, 94);
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

			public JugadorImpl(String nombre, int numero, Color piel,
					Color pelo, double velocidad, double remate,
					double presicion, boolean portero) {
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
			return new PlayerDetail[] {
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("El Guapo", 1, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.87d, 0.71d, 0.91d, true),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Refri", 2, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.8d, 0.8d, 0.85d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Popochin", 3, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.77d, 0.81d, 0.84d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Pambi Edward", 4, new Color(51, 255, 0),
							new Color(50, 0, 0), 0.88d, 0.83d, 0.93d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Angel Bigotes", 5, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.88d, 0.76d, 0.93d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Marco", 6, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.76d, 0.76d, 0.9d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Gerard", 7, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.96d, 0.87d, 0.79d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Saul", 8, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.9d, 0.84d, 0.68d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Rubaz", 9, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.78d, 0.87d, 0.77d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("Zesar", 10, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.84d, 0.84d, 0.94d, false),
					new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl.JugadorImpl("El Tio", 11, new Color(255, 204, 102),
							new Color(50, 0, 0), 0.75d, 0.88d, 0.79d, false) };
		}
	}

	TacticDetail detalle = new org.javahispano.javacup.tacticas.jvc2013.cucarachaAru.TacticaCucaracha.TacticDetailImpl();

	public TacticDetail getDetail() {
		return detalle;
	}

	public Position[] getStartPositions(GameSituations sp) {
		return alineacion2;
	}

	public Position[] getNoStartPositions(GameSituations sp) {
		return alineacion3;
	}
        /**
         * Metodo para saber si hay un jugador en la trayectoria de pase
         * @param posBal posicion del balon
         * @param jugadores lista de jugadores 
         * @param cercanos array de indice de jugadores mas cercanos
         * @param angle angulo entre el balon y el jugador destino
         * @param distancia distancia entre el balon y el jugador destino
         */
	private boolean interceptaBalon(Position posBal,Position[] jugadores,int[] cercanos,double angle,double distancia){
            
        boolean bandera =false;
            double anguloJug=0.0;
        double distanciaJug=0.0;
        for (int i = 0; i < cercanos.length; i++) {
            
            distanciaJug=posBal.distance(jugadores[cercanos[i]]);
            
                anguloJug = posBal.angle(jugadores[cercanos[i]]);
                anguloJug=Math.toDegrees(anguloJug);
                if(angle<anguloJug) {
                   if(angle+this.RADIO_INTERCEPTA>anguloJug
                      && distanciaJug<distancia) {
                       bandera=true;
                       break;
                   }
                }else if(angle>anguloJug){
                    if(angle<anguloJug+this.RADIO_INTERCEPTA
                       && distanciaJug<distancia) {
                       bandera=true;
                       break;
                   }
                }           
            
        }
        
        
            return bandera;
        }
        
   
     
        private boolean sePuedeConducir(Position posBal,Position[] jugadores,int[] cercanos){
            
            boolean bandera =true;
            double anguloJug=0.0;
            double distanciaJug=0.0;
            for (int i = 0; i < cercanos.length; i++) {
            
            distanciaJug=posBal.distance(jugadores[cercanos[i]]);
            anguloJug = posBal.angle(jugadores[cercanos[i]]);
            anguloJug=Math.toDegrees(anguloJug);
               
                   if(distanciaJug<DISTANCIA_PA_CONDUCIR
                      && (anguloJug>60 && anguloJug<160)     
                           ) {
                       
                       if(posBal.getY()>-15 && (posBal.getX()>-25 && posBal.getX()<25))
                       {
                          if(distanciaJug<RADIO_MARCA)
                           {
                                bandera=false;
                                break;
                           }else if(distanciaJug>RADIO_MARCA)
                           {
                                
                                     dirGol=true;
                                        break;
                                
                           }
                       }
                       else
                       {                           
                        bandera=false;
                        break;
                       }
                       
                       
                   }else if(distanciaJug>RADIO_MARCA) direccion=90;
                   else{
                       bandera=false;
                       break;
                   }
                   /*if(distanciaJug<DISTANCIA_PA_CONDUCIR/4
                       ) {
                       bandera=false;
                       break;
                   }*/
            }
          
            return bandera;
        }
        /**
         * metodo si hay jugador frente a ti a muy corta ditancia
         * @param posBal posicion del balon
         * @param jugadores lista de jugadores 
         * @param cercanos array de indice de jugadores mas cercanos
         * @param angle angulo entre el balon y el jugador destino
         * @param distancia distancia entre el balon y el jugador destino
         */
	private boolean peligroAnguloDistancia(Position posBal,Position[] jugadores,int[] cercanos,double angle,double distancia){
            
        boolean bandera =false;
            double anguloJug=0.0;
        double distanciaJug=0.0;
        for (int i = 0; i < cercanos.length; i++) {
            
            distanciaJug=posBal.distance(jugadores[cercanos[i]]);
            
                anguloJug = posBal.angle(jugadores[cercanos[i]]);
                anguloJug=Math.toDegrees(anguloJug);
                if(distanciaJug<distancia)
                {
                if(angle<anguloJug) {
                   if(angle+this.RADIO_INTERCEPTA>anguloJug
                      ) {
                       bandera=true;
                       break;
                   }
                }else if(angle>anguloJug){
                    if(angle<anguloJug+this.RADIO_INTERCEPTA
                       && distanciaJug<distancia) {
                       bandera=true;
                       break;
                   }
                }
                }else
                    break;
            
        }
        
        
            return bandera;
        }
        
	// Lista de comandos
	LinkedList<Command> comandos = new LinkedList<Command>();
           public int numRivales(Position [] pos){
            int numRivales = 0;
            //int[] rivales = new int[11];
            for(int i = 0;i<pos.length;i++){
                if(pos[i].getY() < -5){
                    numRivales++;
                    //rivales[i] = i;
                }
            }
            return numRivales;
        }
        
        
	
        	
        public int[] determinaRivales(Position [] pos){
            //int numRivales = 0;
            int[] rivales = new int[11];
            int j=0;
            for(int i = 0;i<pos.length;i++){
                if(pos[i].getY() < -5){
              //      numRivales++;
                    rivales[j] = i;
                j++;
                }
            }
            return rivales;
        }
        
        public String determinaEstado(GameSituations sp){
            String estado = "";
            Position balon = sp.ballPosition();
            int [] tiradores = sp.canKick();
            
            if(tiradores.length > 0 && balon.getY() > 5){
                estado = "Atacando";
            }else{
                estado = "Defendiendo";
            }
            return estado;
        }
        
        //Metodo para saber si puede meter gol
        private boolean puedeMeterGol(Position[] jugadoresRivales, int[] rivalesMasCarcanos, Position position) {
                
            if(position.getY()>35)
            {
                posicionTiro = new Position(0, Constants.LARGO_CAMPO_JUEGO / 2);
                return true;
            }
            return false;
            
        }

        
        String Situacion         = "";
        String SituacionAnterior = "";
        int []indicesRivales = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        int rivalesAtacando = 0;
        
	@Override
	public List<Command> execute(GameSituations sp) {
		// Limpia la lista de comandos
		comandos.clear();
		// Instancia un generador aleatorio
		Random r = new Random();
		// Obtiene las posiciones de tus jugadores
		Position[] jugadores = sp.myPlayers();
		Position[] jugadoresRivales = sp.rivalPlayers(); 
                int jugadorMejorOpcion=0;
                Cercano jugadorMejorOpcionAtras=null;
                boolean encontroJugadorDestino=false;
                boolean opcionJugAdelante=false;
                double anguloBalJug=0.0;
                double distanciaBalJug=0.0;
                
		if((sp.rivalCanKick().length>0 &&  sp.ballPosition().getY()>-15) || sp.isRivalStarts()  )
                {
                    //System.out.println("EL BALON ESTÁ EN EL AIRE");
                conduciendo=false;
                
                //jugadorConBalonGlobal=-1;
                
                }
                else if(jugadorConBalonGlobal>=0)
                {
                   // System.out.println("LA TIENE UN JUGADOR");
                    conduciendo=true;;
                }
                //Por ahora regularmente estará en defensivo, solo será ofensivo si se puede chutar
                // y el balón se ubica adelante de medio campo (y > 5)
                Situacion = determinaEstado(sp);
                //No implementada aun la situacion anterior
                if (Situacion.equals(SituacionAnterior)){
                    if(Situacion.equals("Defendiendo")){
                        for(int i = 0; i < rivalesAtacando; i++){
                            comandos.add(new CommandMoveTo(i+1, new Position(jugadoresRivales[indicesRivales[i]].getX(),jugadoresRivales[indicesRivales[i]].getY()-2)));
                        }
                    }else{
                        for (int i = 0; i < jugadores.length; i++) {
				// Ordena a cada jugador que se ubique segun la alineacion6
				comandos.add(new CommandMoveTo(i, alineacion6[i]));
			}
                    }
                }else{
                    if(Situacion.equals("Defendiendo")){
                    //Se obtiene los indices de solo los jugadores considerados como atacantes rivales    
                        int[] indexAtacantes = new int[11]; 
                        indexAtacantes = determinaRivales(jugadoresRivales);
                        
                   //Se obtiene el numero de atacantes (je version beta todo esto) 
                        rivalesAtacando = numRivales(jugadoresRivales);
                        
                   //Se copian las posiciones de solo los jugadores que interesan que son los atacantes rivales
                        Position [] rivalAtacante = new Position[rivalesAtacando];
                        for(int j=0;j<rivalesAtacando;j++){
                            rivalAtacante[j] = jugadoresRivales[indexAtacantes[j]];
                        }
                    
              //EN orden irá asignando a cada jugador cucaracha el atacante rival más cercano a él          
                        if(rivalesAtacando > 0)
                            indicesRivales[0] = jugadores[1].nearestIndex(rivalAtacante);
                            //indicesRivales[0] = jugadores[1].nearestIndex(jugadoresRivales);
                        if(rivalesAtacando > 1)
                            indicesRivales[1] = jugadores[2].nearestIndex(rivalAtacante,indicesRivales[0]);                         
                            //indicesRivales[1] = jugadores[2].nearestIndex(jugadoresRivales,indicesRivales[0]);                
                        if(rivalesAtacando > 2)
                            indicesRivales[2] = jugadores[3].nearestIndex(rivalAtacante,indicesRivales[0],indicesRivales[1]);                        
                            //indicesRivales[2] = jugadores[3].nearestIndex(jugadoresRivales,indicesRivales[0],indicesRivales[1]);                        
                        if(rivalesAtacando > 3)
                          indicesRivales[3] = jugadores[4].nearestIndex(rivalAtacante,indicesRivales[0],indicesRivales[1],indicesRivales[2]);                           
                           // indicesRivales[3] = jugadores[4].nearestIndex(jugadoresRivales,indicesRivales[0],indicesRivales[1],indicesRivales[2]);                        
                        if(rivalesAtacando > 4)
                            indicesRivales[4] = jugadores[5].nearestIndex(rivalAtacante,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3]);      
                        //    indicesRivales[4] = jugadores[5].nearestIndex(jugadoresRivales,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3]);
                        if(rivalesAtacando > 5)
                              indicesRivales[5] = jugadores[6].nearestIndex(rivalAtacante,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4]);          
                        //    indicesRivales[5] = jugadores[6].nearestIndex(jugadoresRivales,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4]);           
                        if(rivalesAtacando > 6)
                           indicesRivales[6] = jugadores[7].nearestIndex(rivalAtacante,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4],indicesRivales[5]);                         
                            //    indicesRivales[6] = jugadores[7].nearestIndex(jugadoresRivales,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4],indicesRivales[5]);
                        if(rivalesAtacando > 7)
                           indicesRivales[7] = jugadores[8].nearestIndex(rivalAtacante,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4],indicesRivales[5],indicesRivales[6]);
                            //indicesRivales[7] = jugadores[8].nearestIndex(jugadoresRivales,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4],indicesRivales[5],indicesRivales[6]);
                        if(rivalesAtacando > 8)
                            indicesRivales[8] = jugadores[9].nearestIndex(rivalAtacante,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4],indicesRivales[5],indicesRivales[6],indicesRivales[7]);        
                        //    indicesRivales[8] = jugadores[9].nearestIndex(jugadoresRivales,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4],indicesRivales[5],indicesRivales[6],indicesRivales[7]);
                        if(rivalesAtacando > 9)
                          indicesRivales[9] = jugadores[10].nearestIndex(rivalAtacante,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4],indicesRivales[5],indicesRivales[6],indicesRivales[7],indicesRivales[8]);         
                            //indicesRivales[9] = jugadores[10].nearestIndex(jugadoresRivales,indicesRivales[0],indicesRivales[1],indicesRivales[2],indicesRivales[3],indicesRivales[4],indicesRivales[5],indicesRivales[6],indicesRivales[7],indicesRivales[8]);
                        
                        //Esto ya no tiene sentido
                        int prueba=0;
                        if(rivalesAtacando > 10)
                            prueba = 10;
                        else
                            prueba = rivalesAtacando;

                        int i = 0;
                        //prueba++;
                        
                        //Indica a donde deben moverse los defensas de acuerdo a la posicion del atacante asignado anteriormente
                        for(i = 0; i < prueba; i++){
                                comandos.add(new CommandMoveTo(i+1, new Position(rivalAtacante[indicesRivales[i]].getX(),rivalAtacante[indicesRivales[i]].getY()-2)));
                        }
                    
                        //i=rivalesAtacando;
                       
                        //A los jugadores restantes por ahora les indica q regresan a la posicion original de la alineacion 6
                        for(i=prueba; i < 10; i++){
                                comandos.add(new CommandMoveTo(i+1, alineacion6[i]));
                        } 
                        //para el portero    
                        comandos.add(new CommandMoveTo(0, alineacion6[0]));


                        }else{
                            //Por ahora si se esta atcando los mueve a la alineacion original
                            for (int i = 0; i < jugadores.length; i++) {
                                            // Ordena a cada jugador que se ubique segun la alineacion1
                                            comandos.add(new CommandMoveTo(i, alineacion6[i]));
                            }
                            
                    }
                }
                
               // SituacionAnterior = Situacion;
                // Si no saca el rival
		                
                int []jugadorDestino= null;//sp.ballPosition().nearestIndexes(jugadores,jugadorConBalon );
                if(conduciendo)
                {
                    
                    jugadorDestino= sp.ballPosition().nearestIndexes(jugadores,jugadorConBalonGlobal );
                    
                    for(int d=0;d<5;d++)
                    {
                        if(jugadores[jugadorConBalonGlobal].getX()>jugadores[jugadorDestino[d]].getX())
                    comandos.add(new CommandMoveTo(
                                jugadorDestino[d],
				new Position(jugadores[jugadorDestino[d]].getX(),
                                jugadores[jugadorDestino[d]].getY())));
                        else
                            comandos.add(new CommandMoveTo(
                                jugadorDestino[d],
				new Position(jugadores[jugadorDestino[d]].getX(),
                                jugadores[jugadorConBalonGlobal].getY())));
                    
                    }
                    for (int i = 1; i < 5; i++) {
					// Ordena a los jugadores recuperadores que se ubique
					// en la posicion de recuperacion
                                    if(i!=jugadorConBalonGlobal)
					comandos.add(new CommandMoveTo(
							i,
							new Position(alineacion6[i].getX(),-5 )));
				}
                    comandos.add(new CommandMoveTo(
							0,
							new Position(alineacion6[0] )));
                } 
                if (!sp.isRivalStarts()) {
			// Obtiene los datos de recuperacion del balon
                    
			int[] recuperadores = sp.getRecoveryBall();
                         // Si existe posibilidad de recuperar el balon
			if (recuperadores.length > 1) {
				
				// Obtiene las coordenadas del balon en el instante donde
				// se puede recuperar el balon
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				// Recorre la lista de jugadores que pueden recuperar
				for (int i = 1; i < recuperadores.length; i++) {
					// Ordena a los jugadores recuperadores que se ubique
					// en la posicion de recuperacion
					comandos.add(new CommandMoveTo(
							recuperadores[i],
							new Position(posRecuperacion[0], posRecuperacion[1])));
				}
			}  
				
			
		}
		if (sp.canKick().length > 0) {
                    int jugadorConBalon = sp.canKick()[0];
                    int []rivalesMasCarcanos=sp.ballPosition().nearestIndexes(jugadoresRivales,jugadorConBalon );
                    //boolean qconduzca=sePuedeConducir(jugadores[jugadorConBalon],jugadoresRivales,rivalesMasCarcanos);
                    jugadorDestino= sp.ballPosition().nearestIndexes(jugadores,jugadorConBalon);
                    ///**********************************Logica en defensa*******************************************
                    /////////////////////////////////////////////////////////////////////////////////////////////////
                    if(jugadores[jugadorConBalon].getY()<-20 /*&& jugadorConBalon==0*/)
                    {
                         //Balonazo al jugador más alejado
                            anguloBalJug=jugadores[jugadorConBalon].angle(jugadores[jugadorDestino.length-2]);
                            anguloBalJug=90;//Math.toDegrees(anguloBalJug);
                            distanciaBalJug=5;//jugadores[jugadorConBalon].distance(jugadores[jugadorDestino.length-2]);
                                
                                if(!this.peligroAnguloDistancia(jugadores[jugadorConBalon],jugadoresRivales,rivalesMasCarcanos,
                                        anguloBalJug,distanciaBalJug))
                                    {    
                                        comandos.add(new CommandHitBall(jugadorConBalon, 
                                        jugadores[jugadorDestino.length-2], 1, true));
                                    }
                                else
                                {
                                    if(jugadores[jugadorConBalon].getX()<0)
                                        comandos.add(new CommandHitBall(jugadorConBalon, 
                                        new Position(-30, jugadores[jugadorConBalon].getY()), 1, true));
                                    else
                                    comandos.add(new CommandHitBall(jugadorConBalon, 
                                        new Position(Constants.ANCHO_CAMPO, jugadores[jugadorConBalon].getY()), 1, true));
                                }
                    }
                    ///**********************************Logica de ataque*******************************************
                    /////////////////////////////////////////////////////////////////////////////////////////////////
                    else
                    {
                            //**********Puede meter gol?
                        //************si puede meter gol
                        if( puedeMeterGol(jugadoresRivales,rivalesMasCarcanos,jugadores[jugadorConBalon]))
			{
				comandos.add(new CommandHitBall(jugadorConBalon, posicionTiro
                                        , 1, 12));
                                edoGlobaJuego=TacticaCucaracha.EDO_TIRANDO;
                        }
                        //************no puede meter gol
                        else
                        {
                           boolean [] jugadoresFueraLugar=sp.getOffSidePlayers();
                            for (int i = 0; i <jugadorDestino.length-2; i++) {
                                anguloBalJug=jugadores[jugadorConBalon].angle(jugadores[jugadorDestino[i]]);
                                anguloBalJug=Math.toDegrees(anguloBalJug);
                                distanciaBalJug=jugadores[jugadorConBalon].distance(jugadores[jugadorDestino[i]]);
                                
                                //System.out.println("Miosjugador: "+(jugadorDestino[i]+1)+
                                  //      "--Angulo: "+anguloBalJug+
                                    //    "--Distancia: "+distanciaBalJug);
                                if(jugadoresFueraLugar[jugadorDestino[i]]==false)
                                {    
                                    if(!this.interceptaBalon(jugadores[jugadorConBalon],jugadoresRivales,rivalesMasCarcanos,
                                        anguloBalJug,distanciaBalJug))
                                        {
                                             jugadorMejorOpcion=jugadorDestino[i];   
                                            encontroJugadorDestino=true;
                                            //break;
                                            if(jugadores[jugadorMejorOpcion].getY() > (sp.ballPosition().getY())){
                                                 opcionJugAdelante=true;
                                                break;
                                            }
                                            else
                                            {
                                                 if(jugadorMejorOpcionAtras!=null)
                                                {
                                                    if(distanciaBalJug<jugadorMejorOpcionAtras.getDistancia())
                                                      jugadorMejorOpcionAtras=new Cercano(jugadorMejorOpcion,distanciaBalJug,jugadores[jugadorMejorOpcion]);  
                                                }
                                                else
                                                    jugadorMejorOpcionAtras=new Cercano(jugadorMejorOpcion,distanciaBalJug,jugadores[jugadorMejorOpcion]);  
                                         }
                                    }
                                }
                            }
				 
                              //conduciendo=false;
                              //this.jugadorConBalonGlobal=-1;
                            if(encontroJugadorDestino && opcionJugAdelante &&
                                    (jugadores[jugadorMejorOpcion].getY() >(sp.ballPosition().getY()+15))){
                              
                              conduciendo=false;     
                                   // if(jugadores[jugadorMejorOpcion].getY()>-20)
                                    comandos.add(new CommandHitBall(jugadorConBalon,
							jugadores[jugadorMejorOpcion], 0.79,0)); 
                                   //else
                                     //   comandos.add(new CommandHitBall(jugadorConBalon,Constants.centroArcoSup,.7,35));
                                
                                ///tocar atras
                                /* */
                            }    
                            else
                            {
                                //Puede conducir
                            boolean puedeConducir=false;
                            
                            puedeConducir=sePuedeConducir(jugadores[jugadorConBalon],jugadoresRivales,rivalesMasCarcanos);
                          
                                if((puedeConducir && !sp.isStarts())|| (puedeConducir && sp.isStarts()&& sp.ballPosition().getY()==0 && sp.ballPosition().getX()==0))
                                {
                                    conduciendo=true;
                                    this.jugadorConBalonGlobal=jugadorConBalon;
                                    if(dirGol)
                                    comandos.add(new CommandHitBall(jugadorConBalon,Constants.centroArcoSup,.3,0)); 
                                    else
                                    comandos.add(new CommandHitBall(jugadorConBalon,direccion,.3,0)); 
                                    
                                    dirGol=false;
                                    
                                 }
                                else  if(encontroJugadorDestino && opcionJugAdelante && (jugadores[jugadorMejorOpcion].getX()>25 || jugadores[jugadorMejorOpcion].getX()<-25)) comandos.add(new CommandHitBall(jugadorConBalon,Constants.centroArcoSup,1,17)); 
                                else if(encontroJugadorDestino && opcionJugAdelante ){
                                    
                                    comandos.add(new CommandHitBall(jugadorConBalon,
							jugadores[jugadorMejorOpcion], 0.79,0)); 
                                    conduciendo=false;
                                   
                            }
                                /*else if(encontroJugadorDestino && !opcionJugAdelante){
                                    comandos.add(new CommandHitBall(jugadorConBalon,
							jugadores[jugadorMejorOpcionAtras.getPlayerCercano()], 0.75, false));
                                conduciendo=false;
                                }*/
                                else   comandos.add(new CommandHitBall(jugadorConBalon,Constants.centroArcoSup,1,17)); 
                            }
                              //else pasado  comandos.add(new CommandHitBall(jugadorConBalon,Constants.centroArcoSup,1,25)); 
                        }
                    
                    }           
			}
                return comandos;
                       }
                             
}                   
                         

		
		

