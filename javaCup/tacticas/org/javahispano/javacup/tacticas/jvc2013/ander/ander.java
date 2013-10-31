package org.javahispano.javacup.tacticas.jvc2013.ander;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;


public class ander implements Tactic {
	boolean anteriorSacaban=false;
	Random r=new Random();
	GameSituations spAct;
	LinkedList<Command> comandos= new LinkedList<Command>();
	boolean primero=true;
	double posMasAdelantadaValida=0;
	int indicePorteroEnemigo=-1;
	Position posRecuperacion;
	int numGoles=0;
	double alturaAnterior=0;
	boolean podianDispararAntes[]=new boolean[]{false,false,false,false,false,false,false,false,false,false,false};
	boolean enemigosCubiertos[]=new boolean[]{false,false,false,false,false,false,false,false,false,false,false};
	Position posicionesObjs[]=new Position[]{
	        new Position(0,-50.0),
	        new Position(-5,-45),
	        new Position(5,-45),
	        new Position(-13,-35),
	        new Position(13,-35),
	        new Position(0,-30),
	        new Position(-20,-14),
	        new Position(20,-14),
	        new Position(0,-5),
	        new Position(-9,14),
	        new Position(0,31)
	    };
    Position alineacion1[]=new Position[]{
        new Position(0,-50.0),
        new Position(-5,-45),
        new Position(5,-45),
        new Position(-13,-35),
        new Position(13,-35),
        new Position(0,-30),
        new Position(-20,-14),
        new Position(20,-14),
        new Position(0,-5),
        new Position(-9,14),
        new Position(0,31)
    };

    Position alineacion2[]=new Position[]{
    		new Position(0,-50.0),
            new Position(-11,-5),
            new Position(11,-5),
            new Position(-16,8),
            new Position(16,8),
            new Position(0,19),
            new Position(-18,25),
            new Position(18,25),
            new Position(0,32),
            new Position(-5,40),
            new Position(5,40)
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
    Position alineacionAtacando[]=new Position[]{
            new Position(0,-52),
            new Position(-7.5,0),
            new Position(7.5,0),
            new Position(-20,0),
            new Position(20,0),
            new Position(0,21),
            new Position(-22,21),
            new Position(22,21),
            new Position(-13,32),
            new Position(13,32),
            new Position(0,42)
    		
        };
    Position alineacionMedio[]=new Position[]{
            new Position(0,-52),
            new Position(-7.5,-11),
            new Position(7.5,-11),
            new Position(-20,-11),
            new Position(20,-11),
            new Position(0.1,10),
            new Position(-22,12),
            new Position(22,12),
            new Position(-13,25),
            new Position(13,25),
            new Position(0,42)
        };
    Position alineacionSaco[]=new Position[]{
            new Position(0,-52),
            new Position(-7.5,-11),
            new Position(7.5,-11),
            new Position(-20,-11),
            new Position(20,-11),
            new Position(20,10),
            new Position(-22,0),
            new Position(22,0),
            new Position(-22,24),
            new Position(22,24),
            new Position(0,0)
        };
    Position alineacionDefender[]=new Position[]{
            new Position(0,-52),
            new Position(-7.5,-42),
            new Position(7.5,-42),
            new Position(-20,-30),
            new Position(20,-30),
            new Position(0,-16),
            new Position(-22,0),
            new Position(22,0),
            new Position(-22,17),
            new Position(22,17),
            new Position(0,10)
        };

    class TacticDetailImpl implements TacticDetail {

        public String getTacticName() {
            return "Equipo deTal";
        }

        public String getCountry() {
            return "España";
        }

        public String getCoach() {
            return "Ander deTal";
        }

        public Color getShirtColor() {
            return new Color(255, 204, 204);
        }

        public Color getShortsColor() {
            return new Color(153, 102, 0);
        }

        public Color getShirtLineColor() {
            return new Color(153, 102, 0);
        }

        public Color getSocksColor() {
            return new Color(255, 204, 204);
        }

        public Color getGoalKeeper() {
            return new Color(255, 204, 204        );
        }

        public EstiloUniforme getStyle() {
            return EstiloUniforme.FRANJA_DIAGONAL;
        }

        public Color getShirtColor2() {
            return new Color(241, 103, 186);
        }

        public Color getShortsColor2() {
            return new Color(68, 235, 193);
        }

        public Color getShirtLineColor2() {
            return new Color(148, 142, 126);
        }

        public Color getSocksColor2() {
            return new Color(172, 129, 104);
        }

        public Color getGoalKeeper2() {
            return new Color(12, 253, 114        );
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
                /*new JugadorImpl("Jugador", 1, new Color(255,204,204), new Color(50,0,0),1.0d,1.0d,0.5d, true),
                new JugadorImpl("Jugador", 2, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Jugador", 3, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Jugador", 4, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,0.5d, false),
                new JugadorImpl("Jugador", 5, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,0.5d, false),
                new JugadorImpl("Jugador", 6, new Color(255,200,150), new Color(50,0,0),1.0d,0.75d,0.5d, false),
                new JugadorImpl("Jugador", 7, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Jugador", 8, new Color(255,200,150), new Color(50,0,0),1.0d,0.5d,0.5d, false),
                new JugadorImpl("Jugador", 9, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Jugador", 10, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false),
                new JugadorImpl("Jugador", 11, new Color(255,200,150), new Color(50,0,0),1.0d,1.0d,1.0d, false)*/
                new JugadorImpl("0", 1, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.7d, 1.0d, true),//
    			new JugadorImpl("1", 2, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.9d, 1.0d, false),//
    			new JugadorImpl("2", 3, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
    			new JugadorImpl("3", 4, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.9d, 1.0d, false),//
    			new JugadorImpl("4", 5, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
    			new JugadorImpl("5", 6, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
    			new JugadorImpl("6", 7, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
    			new JugadorImpl("7", 8, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 0.5d, 0.5d, false),//
    			new JugadorImpl("8", 9, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false),//
    			new JugadorImpl("9", 10, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false),//
    			new JugadorImpl("10", 11, new Color(255, 200, 150), new Color(50, 0, 0), 1d, 1d, 1d, false)
            };
        }
    }

    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacionSaco;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacionSaco;
    }
  public void colocarJugadorEntreAliacion1y2porBalon(int indice,Position posBalon){
	  Position posAl1=alineacion1[indice];
	  Position posAl2=alineacion2[indice];
	  double relBalonY=(posBalon.getY()+52.5)/105.0;
	  double relBalonX=(posBalon.getX()+34)/68.0;
	  double movMaxVertical=15;
	  Position posFinal=new Position();
	  double posXFinal=posAl1.getX()+(posAl2.getX()-posAl1.getX())*relBalonY+movMaxVertical*relBalonX;
	  double posYFinal=posAl1.getY()+(posAl2.getY()-posAl1.getY())*relBalonY;
	  posFinal=new Position(Math.max(Math.min(posXFinal,30),-30), Math.min(posMasAdelantadaValida,posYFinal));
	  boolean correr=false;
	  if(spAct.myPlayers()[indice].getY()>spAct.ballPosition().getY()&&spAct.iteration()%10==0){
		  posFinal=desmarcarPosicion(posFinal);
		  correr=true;
	  }
	  comandos.add(new CommandMoveTo(indice,posFinal,correr));
  }
  public void colocarJugadorEntreAliacionesNuevas(int indice,Position posBalon){
	  Position posAl1;
	  Position posAl2;
	  double relBalonY=(posRecuperacion.getY()+52.5)/105.0;
	  double relBalonX=(posRecuperacion.getX()+34)/68.0;
	  if(relBalonY<0.33&&!spAct.isStarts()){
		  posAl1=alineacionDefender[indice];
		  posAl2=alineacionDefender[indice];
	  }else if(relBalonY<0.66){
		  posAl1=alineacionMedio[indice];
		  posAl2=alineacionMedio[indice];
	  }else{
		  posAl1=alineacionAtacando[indice];
		  posAl2=alineacionAtacando[indice];
	  }
	  
	  double movMaxVertical=15;
	  Position posFinal=new Position();
	  double posXFinal=posAl1.getX()+movMaxVertical*relBalonX/2;
	  double posYFinal=posAl1.getY();
	  posFinal=new Position(Math.max(Math.min(posXFinal,30),-30), Math.min(posMasAdelantadaValida,posYFinal));
	  if(spAct.myPlayers()[indice].getY()>spAct.ballPosition().getY()){
		  posFinal=desmarcarPosicion(posFinal);
	  }
	  posicionesObjs[indice]=posFinal;
	  comandos.add(new CommandMoveTo(indice,posFinal));
  }
  private Position desmarcarPosicion(Position posEstandar){
	 int cuantasFilas=0;
	 int cuantasColumnas=5;
	 int movMax1Direccion=6;
	 
	 Position posDef=null;
	 double distMaxima=-1;
	 for(int i=cuantasFilas;i>=0;i--){
		 int fila=0;//i-3;
		 double posY=posEstandar.getY()+fila*movMax1Direccion/2.0;
		 if(posY<=posMasAdelantadaValida){
			 for(int j=cuantasColumnas;j>=0;j--){
				 int columna=j-3;
				 double posX=posEstandar.getX()+columna*movMax1Direccion/2.0;
				 if(Math.abs(posX)<=28){
					 Position posiblePos=new Position(posX, posY);
					 double distAct=this.distMinDeRivalHastaPosicion(posiblePos);
					 if(distAct>distMaxima){
						 distMaxima=distAct;
						 posDef=posiblePos;
					 }
				 }
			 }
		 }
	 }
	  if(distMaxima>0&&false){
		  return posDef;
	  }else{
		  return posEstandar;
	  }
	  
	  
	  
}
  public void colocarPortero(Position posBalon){
	  Position pBalon=posBalon;
	  Position posPorteria=Constants.centroArcoInf;
	  double angulo=posPorteria.angle(pBalon);
	  double largoPorteria=Constants.LARGO_ARCO;
	  Position posPortero=new Position(Math.cos(angulo)*largoPorteria/2,-52.5+ Math.sin(angulo)*largoPorteria/3);
	  comandos.add(new CommandMoveTo(0,posPortero));
  }
  public void controlarFueraDeJuego(){
	  posMasAdelantadaValida=0;
	  posMasAdelantadaValida=Math.max(posMasAdelantadaValida, spAct.ballPosition().getY());
	  double jugador1atras=0;
	  double jugador2atras=0;
	  for(int i=0;i<spAct.rivalPlayers().length;i++){
		  Position posRival=spAct.rivalPlayers()[i];
		  if(posRival.getY() >=jugador1atras){
			  jugador2atras=jugador1atras;
			  jugador1atras=posRival.getY();
		  }else if(posRival.getY() >=jugador2atras){
			  jugador2atras=posRival.getY();
		  }
	  }
	 
	  posMasAdelantadaValida=Math.max(posMasAdelantadaValida, jugador2atras-0.5);
	  
  }
 public void chutarPorteria(int indice){
	 double resultados[]={-1,-1};
	 double distanciaCentroPorteria=spAct.ballPosition().distance(Constants.centroArcoSup);
	 if(distanciaCentroPorteria>14&&distanciaCentroPorteria<53&&Math.sin(spAct.ballPosition().angle(Constants.centroArcoSup))>0.8){
		 double distanciaPaloIzqPorteria=spAct.ballPosition().distance(Constants.posteIzqArcoSup);
		 double distanciaPaloDerPorteria=spAct.ballPosition().distance(Constants.posteDerArcoSup);
		 double distMax=Math.max(distanciaCentroPorteria, Math.max(distanciaPaloIzqPorteria,distanciaPaloDerPorteria));
		 double distMin=Math.min(distanciaCentroPorteria, Math.min(distanciaPaloIzqPorteria,distanciaPaloDerPorteria));
		 double distanciaDeseada=(distanciaCentroPorteria+distanciaPaloIzqPorteria+distanciaPaloDerPorteria)/3.0;
	 	 resultados=this.anguloyFuerzaParaLLegarPorArribaHastaDistanciaChutando(indice,distanciaCentroPorteria);
	 	 System.out.println("resultado tiro imparadble "+resultados[0]+" "+resultados[1]);
	 }
	 if(resultados[0]!=-1&&resultados[1]!=-1&&distanciaCentroPorteria>24){
		 comandos.add(new CommandHitBall(indice,Constants.centroArcoSup,resultados[1],resultados[0]));
		 System.out.println("fuerza calulada apra tiro por arriba "+resultados[1]+" angulo "+resultados[0]);
	 }else if(distanciaCentroPorteria>40){
		 comandos.add(new CommandHitBall(indice,Constants.centroArcoSup,1,30));
	 }else{
		 double fuerzaJugador01=spAct.getMyPlayerPower(indice);//0-1 -> 1.2-2.4
		 double errorJugador01=spAct.getMyPlayerError(indice);//0-1 -> 30-10
		 double fuerzaReal=1.2+1.2*fuerzaJugador01;
		 double errorRealPorciento=10+20*(1-errorJugador01);
		 double errorRealAbsolutoGrados=errorRealPorciento*1.8;
		 double errorRealAbsolutoRadianes=Math.toRadians(errorRealAbsolutoGrados);
		 double anguloPaloIzq=Math.toDegrees(spAct.ballPosition().angle(Constants.posteIzqArcoSup));
		 double anguloPaloDer=Math.toDegrees(spAct.ballPosition().angle(Constants.posteDerArcoSup));
		 double anguloPortero=Math.toDegrees(spAct.ballPosition().angle(spAct.rivalPlayers()[indicePorteroEnemigo]));
		 double anguloTotalPorteria=Math.abs(anguloPaloDer-anguloPaloIzq);
		 double anguloDisparo;
		 double fuerzaDisparo=1;
		 if(anguloTotalPorteria<=errorRealAbsolutoGrados){//tirar al centro
			 anguloDisparo=spAct.ballPosition().angle(Constants.centroArcoSup);
		 }else{//apuntar a un palo
			 double difAnguloPorteroPaloIzq=Math.abs(anguloPaloIzq-anguloPortero);
			 double difAnguloPorteroPaloDer=Math.abs(anguloPaloDer-anguloPortero);
			 if(difAnguloPorteroPaloDer>difAnguloPorteroPaloIzq){
				 //disparar ajustando al palo derecho
				 anguloDisparo=spAct.ballPosition().angle(Constants.posteDerArcoSup)+errorRealAbsolutoRadianes/2;
			 }else{
				//disparar ajustando al palo izquierdo
				 anguloDisparo=spAct.ballPosition().angle(Constants.posteIzqArcoSup)-errorRealAbsolutoRadianes/2;
			 }
		 }
		 
		 comandos.add(new CommandHitBall(indice,Math.toDegrees(anguloDisparo),1,15));
	 }
	 
 }
  public List<Command> execute(GameSituations sp) {
	  
	  
	  spAct=sp;
	 
	  anteriorSacaban=spAct.isRivalStarts();
	  if(spAct.myGoals()+spAct.rivalGoals()!=numGoles){
		  numGoles=spAct.myGoals()+spAct.rivalGoals();
		  System.out.println("GOOOOOOL altura anterior-> "+alturaAnterior);
		}
	  alturaAnterior=spAct.ballAltitude();
	  if(primero){
		  primero=false;
		  for(int i=0;i<spAct.rivalPlayersDetail().length;i++){
			  PlayerDetail detAct= spAct.rivalPlayersDetail()[i];
			  if(detAct.isGoalKeeper()){
				  indicePorteroEnemigo=i;
			  } 
		  }
	  }
	  comandos.clear();
	  int[] recuperadores1 = sp.getRecoveryBall();
	  if (recuperadores1.length > 1) {
		  double[] posRecuperacion1 = sp.getTrajectory(recuperadores1[0]);
	  	  posRecuperacion=new Position(posRecuperacion1[0],posRecuperacion1[1]);
	  }
	  
		// Obtiene las posiciones de tus jugadores
	  
		Position[] jugadores = sp.myPlayers();
		
		//////
		//////COLOCAR JUGADORES
		//////
		controlarFueraDeJuego();
		for (int i = 0; i < jugadores.length; i++) {
			// Ordena a cada jugador que se ubique segun la alineacion1
			if(i!=0){
				colocarJugadorEntreAliacionesNuevas(i,sp.ballPosition());
				
			}else{
				colocarPortero(sp.ballPosition());
			}
	
		}
		//////
		//////COLOCAR JUGADORES FIN
		//////
		
		///////
		///////CUBRIR
		///////
		//this.cubrir();
		///////
		///////CUBRIR FIN
		///////
		
		//////
		//////IR A POR EL BALON
		//////
		// Si no saca el rival
		if (!sp.isRivalStarts()) {
			// Obtiene los datos de recuperacion del balon
			int[] recuperadores = sp.getRecoveryBall();
			// Si existe posibilidad de recuperar el balon
			if (recuperadores.length > 1) {
				// Obtiene las coordenadas del balon en el instante donde
				// se puede recuperar el balon
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				Position posRecu=new Position(posRecuperacion[0], posRecuperacion[1]);
				if(posRecu.getY()>0){
					posRecu.moveAngle(posRecu.angle(Constants.centroArcoSup), Constants.DISTANCIA_CONTROL_BALON*0.7);
				}else{
					posRecu.moveAngle(posRecu.angle(Constants.centroArcoInf), Constants.DISTANCIA_CONTROL_BALON*0.7);
				}
				// Recorre la lista de jugadores que pueden recuperar
				for (int i = 1; i < recuperadores.length; i++) {
					// Ordena a los jugadores recuperadores que se ubique
					// en la posicion de recuperacion
					int indiceJugador=recuperadores[i];
					double radioPorteroMov=15;
					boolean voy=true;
					if(indiceJugador==0&&spAct.ballPosition().distance(Constants.centroArcoInf)>radioPorteroMov){
						voy=false;
					}
					if(indiceJugador==10&&recuperadores.length>=4){
						voy=false;
					}
					boolean sprint=indiceJugador==0;
					if(voy){
						comandos.add(new CommandMoveTo(indiceJugador,posRecu,sprint));
					}
				}
				int indexRecuperaPortero=this.primeraPosParaMiPortero(recuperadores[0]+1);
				if(indexRecuperaPortero>=0&&indexRecuperaPortero<recuperadores[0]){
					double []posRecuperaPortero=spAct.getTrajectory(indexRecuperaPortero);
					Position posRecuPortero=new Position(posRecuperaPortero[0], posRecuperaPortero[1]);
					double distanciaPorteria=Constants.centroArcoInf.distance(posRecuPortero);
					if(distanciaPorteria<16){
						comandos.add(new CommandMoveTo(0,posRecuPortero,true));
					}else{
						comandos.add(new CommandMoveTo(2,posRecuPortero,true));
					}
				}
			}
		}
			//////
			//////IR A POR EL BALON FIN
			//////
		
		
		
			//////
			//////CHUTAR
			//////
			boolean sacarMedioCampo=spAct.ballPosition().distance(Constants.centroCampoJuego)==0;
			double distanciaRazonable=26;
			double distanciaMax=40;
			double distRespectoMiPorteria=Constants.centroArcoInf.distance(spAct.ballPosition());
			boolean lejosMiPorteroa=spAct.ballPosition().getY()>(-52+18);
			boolean rivalPuedeChutar=spAct.rivalCanKick().length>0;
			boolean avanzo=false;
			for(int i=0;i<spAct.canKick().length;i++){
				if(!spAct.isStarts()&&!sacarMedioCampo){
					if(sp.ballPosition().distance(Constants.centroArcoSup)<=distanciaRazonable){
						System.out.println("iteracion "+spAct.iteration()+": tiro por estar cerca");
						chutarPorteria(spAct.canKick()[i]);
					}else{
						if(!spAct.isStarts()&&avanzarConBalon(spAct.canKick()[i])&&spAct.canKick()[i]!=0){
							System.out.println("iteracion "+spAct.iteration()+": avanzo con balon");
							avanzo=true;
						}else if(paseSeguro(spAct.canKick()[i])&&lejosMiPorteroa){
							System.out.println("iteracion "+spAct.iteration()+": hago pase seguro");
						}else if(paseAlHueco(spAct.canKick()[i])&&lejosMiPorteroa){
							System.out.println("iteracion "+spAct.iteration()+": hago pase al hueco");
						}else if(paseInseguro(spAct.canKick()[i])){
							System.out.println("iteracion "+spAct.iteration()+": hago pase inseguro");
						}else{
								if(r.nextInt(3)!=0){
									System.out.println("iteracion "+spAct.iteration()+": tiro a corner");
									this.tirarAUnCorner(spAct.canKick()[i]);
								}else{
								System.out.println("iteracion "+spAct.iteration()+": tiro lejano");
									chutarPorteria(spAct.canKick()[i]);
								}
							
						}
						
					}
				}else{//tiros cuando sacamos
					//System.out.println("distnacia centro campo "+spAct.ballPosition().distance(Constants.centroCampoJuego));
					if(spAct.ballPosition().distance(Constants.centroArcoSup)<20){//saque desde su porteria
						comandos.add(new CommandHitBall(spAct.canKick()[i],new Position(0,0),0.40,45));
						
					}else if(spAct.ballPosition().distance(Constants.centroCampoJuego)<0.1){//saque medio campo
						this.chutarPorteria(spAct.canKick()[i]);
						
						avanzo=true;
					}else{//resto de saques
					
						if(paseSeguro(spAct.canKick()[i])&&lejosMiPorteroa){
							//System.out.println("iteracion "+spAct.iteration()+": hago pase seguro");
						}else if(paseInseguro(spAct.canKick()[i])&&lejosMiPorteroa){
							//System.out.println("iteracion "+spAct.iteration()+": hago pase inseguro");
						}else if(paseAlHueco(spAct.canKick()[i])&&lejosMiPorteroa){
							//System.out.println("iteracion "+spAct.iteration()+": hago pase al hueco");
						}else{
							//System.out.println("iteracion "+spAct.iteration()+": tiro lejano");
								chutarPorteria(spAct.canKick()[i]);
							
						}
					}
					
				}
				if(!avanzo){
					Position posMejor=spAct.ballPosition().moveAngle(spAct.ballPosition().angle(Constants.centroArcoInf), Constants.DISTANCIA_CONTROL_BALON*2);
					comandos.add(new CommandMoveTo(spAct.canKick()[i],posMejor,true));
				}
			}
		
			for(int i=0;i<11;i++){
				podianDispararAntes[i]=false;
			}
			for(int i=0;i<spAct.canKick().length;i++){
				podianDispararAntes[spAct.canKick()[i]]=true;
			}
		
			//////
			//////CHUTAR FIN
			//////
		
		
			//System.out.println("iteracion "+spAct.ballAltitude()+": <- altura balon");
		
		// Retorna la lista de comandos
		return comandos;
		
		//FALTA:
		//Marcar en defensa
		//Desmarcarse ataque   ---?
		//comprobar trayectoria de pases seguros ---? no funciona bien
		//pases al hueco ---?¿?nop
		//"ahorrar" energia
		//mejorar regates (ver romedalus)
		
    }
  private boolean paseAlHueco(int indice) {
	// TODO Auto-generated method stub
			double distanciaActPorteria=spAct.ballPosition().distance(Constants.centroArcoSup);
			
			double minMejora=7;
			double distanciaMinDePase=Double.MAX_VALUE;
			int jugObj=-1;
			for(int i=0;i<spAct.myPlayers().length;i++){
				Position posAmigo=spAct.myPlayers()[i];
				double distanciaPorteriaAmigo=posAmigo.distance(Constants.centroArcoSup);
				double distanciaPase=spAct.ballPosition().distance(posAmigo);
				if(distanciaPase<distanciaMinDePase&&distanciaPorteriaAmigo+minMejora<=distanciaActPorteria&&this.paseAlHuecoAJugador(indice, i, false)){
					distanciaMinDePase=distanciaPase;
					jugObj=i;
				}
			}
			if(jugObj!=-1){
				paseAlHuecoAJugador(indice,jugObj,true);
				return true;
			}else{
				return false;
			}
	  
  }
  	private boolean paseAlHuecoAJugador(int indiceConBalon,int indiceJugObj,boolean realizarPase){
  		boolean algunPase=false;
  		Position posPase=null;
  		double fuerzaSelec=-1;
  		int iteracionesVentaja=-1;
  		double distanciaMax=17;
  		double cambio=4;
  		Position posJugador=spAct.myPlayers()[indiceJugObj];
  		double distAct=spAct.ballPosition().distance(Constants.centroArcoSup);
	  for(double cambioY=-distanciaMax;cambioY<=distanciaMax;cambioY=cambioY+cambio){
		  for(double cambioX=-distanciaMax;cambioX<=distanciaMax;cambioX=cambioX+cambio){
			  Position posPosible=new Position(posJugador.getX()+cambioX,posJugador.getY()+cambioY);
			  double distPosible=posPosible.distance(Constants.centroArcoSup);
			  if(this.posicionValidaParaAvanzarConBalon(posPosible)&&!this.hayRivalEnMedioParaPaseSeguroBajo(posPosible)&&distAct>distPosible+7){
				  double distanciaDeseada=spAct.ballPosition().distance(posPosible);
				  double fuerza=fuerzaAdecuadaParaPaseRapido(indiceConBalon, 13, distanciaDeseada,true);
				  Vector<PosicionBalon> lPosicionesSimuladas=this.simula(indiceConBalon, fuerza, 10);
				  Position jugObjPos=posPosible;
					double menorDistancia=Double.MAX_VALUE;
					int iteracion=-1;
					for(int i=0;i<lPosicionesSimuladas.size();i++){
						PosicionBalon posBalonAct=lPosicionesSimuladas.get(i);
						if(Math.abs(distanciaDeseada-posBalonAct.X)<menorDistancia){
							menorDistancia=Math.abs(distanciaDeseada-posBalonAct.X);
							iteracion=i;
						}
					}
					int iteracionesRival=this.numIteracionesMinimasDeRivalHastaPosicion(jugObjPos);
					int iteracionesMias=this.numIteracionesMinimasMiasHastaPosicion(jugObjPos);
					if(iteracion!=-1&& iteracion<iteracionesRival&&iteracion>=iteracionesMias){
						algunPase=true;
						int ventajaAct=iteracionesRival-iteracionesMias;
						if(realizarPase&&ventajaAct>iteracionesVentaja){
							iteracionesVentaja=ventajaAct;
							posPase=jugObjPos;
							fuerzaSelec=fuerza;
						}else{
							
							break;
						}
						
					}
			  }
		  }
		  
	  }
	  
	  if(realizarPase){
			comandos.add(new CommandHitBall(indiceConBalon,posPase,fuerzaSelec,10));
			Position posIr=new Position(posPase.getX(),Math.min(posMasAdelantadaValida, posPase.getY()));
			comandos.add(new CommandMoveTo(indiceJugObj, posIr, true));
		}
		
		return algunPase;
	}
  
	private boolean paseSeguro(int indice) {
		// TODO Auto-generated method stub
		double distanciaActPorteria=spAct.ballPosition().distance(Constants.centroArcoSup);
		
		double minMejora=7;
		double distanciaMinDePase=Double.MAX_VALUE;
		int jugObj=-1;
		for(int i=0;i<spAct.myPlayers().length;i++){
			Position posAmigo=spAct.myPlayers()[i];
			double distanciaPorteriaAmigo=posAmigo.distance(Constants.centroArcoSup);
			double distanciaPase=spAct.ballPosition().distance(posAmigo);
			if(distanciaPase<distanciaMinDePase&&distanciaPorteriaAmigo+minMejora<=distanciaActPorteria&&this.paseSeguroAJugador(indice, i, false)){
				distanciaMinDePase=distanciaPase;
				jugObj=i;
			}
		}
		if(jugObj!=-1){
			paseSeguroAJugador(indice,jugObj,true);
			return true;
		}else{
			return false;
		}
	}
	private boolean paseSeguroAJugador(int indiceConBalon,int indiceJugObj,boolean realizarPase){
		if(!this.hayRivalEnMedioParaPaseSeguroBajo(spAct.myPlayers()[indiceJugObj])){
			double distanciaDeseada=spAct.ballPosition().distance(spAct.myPlayers()[indiceJugObj]);
			double fuerza=fuerzaAdecuadaParaPaseRapido(indiceConBalon, 13, distanciaDeseada,false);
			Vector<PosicionBalon> lPosicionesSimuladas=this.simula(indiceConBalon, fuerza, 10);
			Position jugObjPos=spAct.myPlayers()[indiceJugObj];
			double menorDistancia=Double.MAX_VALUE;
			int iteracion=-1;
			for(int i=0;i<lPosicionesSimuladas.size();i++){
				PosicionBalon posBalonAct=lPosicionesSimuladas.get(i);
				if(Math.abs(distanciaDeseada-posBalonAct.X)<menorDistancia){
					menorDistancia=Math.abs(distanciaDeseada-posBalonAct.X);
					iteracion=i;
				}
			}
			
			if(iteracion!=-1&& iteracion<this.numIteracionesMinimasDeRivalHastaPosicion(jugObjPos)){
				if(realizarPase){
					comandos.add(new CommandHitBall(indiceConBalon,spAct.myPlayers()[indiceJugObj],fuerza,10));
					//System.out.println("pasarAamigoPorAlto seguro distancia:"+distanciaDeseada+" fuerza calculada="+fuerza);
				}
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	private boolean hayRivalEnMedioParaPaseSeguroBajo(Position posObj){
		double anguloComprobacioncadaLado=40;
		boolean hay=false;
		double distHastaPase=spAct.ballPosition().distance(posObj);
		double angulohastaJugador=this.normalizeAngle(Math.toDegrees(spAct.ballPosition().angle(posObj)));
		for(int i=0;i<spAct.rivalPlayers().length;i++){
			Position posRival=spAct.rivalPlayers()[i];
			if(distHastaPase>posRival.distance(spAct.ballPosition())){
				double anguloRival=this.normalizeAngle(Math.toDegrees(spAct.ballPosition().angle(posRival)));
				double difAngulo=Math.abs(anguloRival-angulohastaJugador);
				if(difAngulo>180){
					difAngulo=difAngulo-180;
				}
				if(difAngulo<=anguloComprobacioncadaLado){
					hay=true;
					break;
				}
			}
			
		}
		
		return hay;
	}
	private double normalizeAngle(double pAngulo){
		double newAngle=pAngulo;
	    while (newAngle <= -180) newAngle += 360;
	    while (newAngle > 180) newAngle -= 360;
	    return newAngle;
	}
	
	
	private boolean avanzarConBalon(int indice) {
		
		// TODO Auto-generated method stub
		double realSpeed=0.25+0.25*spAct.getMyPlayerSpeed(indice);
		double anguloPorteria=Math.toDegrees(spAct.ballPosition().angle(Constants.centroArcoSup));
		boolean avanzamos=false;
		double fuerza=fuerzaIndicadaParaAutoPase(indice);
		
		Position mejorPos=null;
		int iteracionesSobran=-1;
		for(int i=0;i<10;i++){
			double anguloPosible1=i*3;
			double anguloPosible2=i*-3;
			double distanciaRecorrida=spAct.distanceTotal(indice, Constants.ITERACIONES_GOLPEAR_BALON);
			Position pos1=spAct.ballPosition().moveAngle(Math.toRadians(anguloPorteria+anguloPosible1), distanciaRecorrida);
			Position pos2=spAct.ballPosition().moveAngle(Math.toRadians(anguloPorteria+anguloPosible2), distanciaRecorrida);
			int iteracionesRivalPos1=this.numIteracionesMinimasDeRivalHastaPosicion(pos1)-1;
			int iteracionesRivalPos2=this.numIteracionesMinimasDeRivalHastaPosicion(pos2)-1;
			//int iteracionesRivalPos1=SimulaJugadores.distanciaMinEquipoAPosicion(spAct, pos1, false, true)+2;
			//int iteracionesRivalPos2=SimulaJugadores.distanciaMinEquipoAPosicion(spAct, pos2, false, true)+2;
			if(posicionValidaParaAvanzarConBalon(pos1)&&iteracionesRivalPos1>Constants.ITERACIONES_GOLPEAR_BALON+0&&iteracionesRivalPos1>iteracionesRivalPos2){
				if(iteracionesRivalPos1-Constants.ITERACIONES_GOLPEAR_BALON>iteracionesSobran){
					avanzamos=true;
					mejorPos=pos1;
					iteracionesSobran=iteracionesRivalPos1-Constants.ITERACIONES_GOLPEAR_BALON;
					if(iteracionesSobran>5){
						break;
					}
					//comandos.add(new CommandHitBall(indice,pos1,fuerza,5)); 
					//comandos.add(new CommandMoveTo(indice,pos1));
					//System.out.println("Avanzamos de x:"+spAct.ballPosition().getX()+" y:"+spAct.ballPosition().getY() +" hasta-> x:"+pos1.getX()+" y:"+pos1.getY());
				}
			}else if(posicionValidaParaAvanzarConBalon(pos2)&&iteracionesRivalPos2>Constants.ITERACIONES_GOLPEAR_BALON+0&&iteracionesRivalPos2>iteracionesRivalPos1){
				if(iteracionesRivalPos2-Constants.ITERACIONES_GOLPEAR_BALON>iteracionesSobran){
					avanzamos=true;
					mejorPos=pos2;
					iteracionesSobran=iteracionesRivalPos2-Constants.ITERACIONES_GOLPEAR_BALON;
					//comandos.add(new CommandHitBall(indice,pos2,fuerza,5));
					//comandos.add(new CommandMoveTo(indice,pos2));
					//System.out.println("Avanzamos de x:"+spAct.ballPosition().getX()+" y:"+spAct.ballPosition().getY() +" hasta-> x:"+pos2.getX()+" y:"+pos2.getY());
				}
			}
		}
		
		if(avanzamos){
			comandos.add(new CommandHitBall(indice,mejorPos,fuerza,20));
			comandos.add(new CommandMoveTo(indice,mejorPos,true));
		}
		
		
		return avanzamos;
	}
	private boolean posicionValidaParaAvanzarConBalon(Position pos){
		boolean valido=true;
		if(Math.abs(pos.getY())>50||Math.abs(pos.getX())>30){
			valido=false;
		}
		return valido;
	}
	private double fuerzaIndicadaParaAutoPase(int indice){
		double fuerzaIndicada=0;
		double minError=Double.MAX_VALUE;
		double velocidadReal=(0.25+0.25*spAct.getMyPlayerSpeed(indice))*1.2;
		double distanciaRecorrida=spAct.distanceTotal(indice, Constants.ITERACIONES_GOLPEAR_BALON);
		//double distanciaRecorrida=velocidadReal*Constants.ITERACIONES_GOLPEAR_BALON*0.85;
		
		/*for(double i=0.05;i<=1;i=i+0.05){
			double fuerza=i;
			Vector<PosicionBalon> lPos= this.simula(indice, fuerza, 5);
			PosicionBalon posInteresante=lPos.get(Constants.ITERACIONES_GOLPEAR_BALON);
			double errorAct=Math.abs(distanciaRecorrida-posInteresante.X);
			if(errorAct<minError){
				minError=errorAct;
				fuerzaIndicada=fuerza;
				if(errorAct<Constants.DISTANCIA_CONTROL_BALON/2){
					break;
				}
			}
		}*/
		for(double i=1;i>0.0;i=i-0.025){
			double fuerza=i;
			Vector<PosicionBalon> lPos= this.simula(indice, fuerza, 20);
			PosicionBalon posInteresante=lPos.get(Constants.ITERACIONES_GOLPEAR_BALON);
			double errorAct=Math.abs(distanciaRecorrida-posInteresante.X);
			if(posInteresante.altura<=Constants.ALTURA_CONTROL_BALON){
				if(errorAct<minError){
					minError=errorAct;
					fuerzaIndicada=fuerza;
					/*if(errorAct<Constants.DISTANCIA_CONTROL_BALON/3){
						break;
					}*/
				}
			}
		}
		
		
		return fuerzaIndicada;
	}
	
	private boolean paseInseguro(int indice) {
		// TODO Auto-generated method stub
		double distanciaActPorteria=spAct.ballPosition().distance(Constants.centroArcoSup);
		double mejorDistancia=distanciaActPorteria;
		double minMejora=7;
		int jugObj=-1;
		for(int i=0;i<spAct.myPlayers().length;i++){
			Position posAmigo=spAct.myPlayers()[i];
			double distanciaPorteriaAmigo=posAmigo.distance(Constants.centroArcoSup);
			if(distanciaPorteriaAmigo+minMejora<=mejorDistancia&&llegoPorAlto(indice,posAmigo)){
				mejorDistancia=distanciaPorteriaAmigo;
				jugObj=i;
			}
		}
		if(jugObj!=-1){
			pasarAamigoPorAlto(indice,jugObj);
			return true;
		}else{
			return false;
		}
	}
	
	private void pasarAamigoPorAlto(int jugPasador, int jugObj) {
		// TODO Auto-generated method stub
		double distancia=spAct.ballPosition().distance(spAct.myPlayers()[jugObj]);
		double fuerza=fuerzaAdecuadaParaLLegarPorArribaHastaDistancia(jugPasador, 45, distancia);
		comandos.add(new CommandHitBall(jugPasador,spAct.myPlayers()[jugObj],fuerza,45));
		//System.out.println("pasarAamigoPorAlto distancia:"+distancia+" fuerza calculada="+fuerza);
	}
	
	private boolean llegoPorAlto(int indice, Position posObj) {
		// TODO Auto-generated method stub
		//30 con 0.5 de fuerza  50 con 1
		double distanciaMax=this.distanciaTiroPorArribaHastaAlturaControlable(indice, 1, 45)*1.1;
		double distancia=spAct.ballPosition().distance(posObj);
		return distanciaMax>=distancia;
	}
	private Vector<Integer> cuandoYquienesLleganAlBalon(boolean soyYo,boolean scprint){
		Vector<Integer> cuandoYQuienes = new Vector<Integer>();
		Position[] jugadores;
		if(soyYo){
			jugadores=spAct.myPlayers();
		}else{
			jugadores=spAct.rivalPlayers();
		}
		for(int i=0;i<200;i++){
			double[] xyz=spAct.getTrajectory(i);
			Position posBalon=new Position(xyz[0],xyz[1]);
			for(int j=0;j<jugadores.length;j++){
				Position posJugador=jugadores[j];
				double distancia=posBalon.distance(posJugador);
				
			}
		}
		
		
		
		return cuandoYQuienes;
	}
	
	private double distanciaTiroPorArribaHastaAlturaControlable(int indiceJugador,double fuerzaTiro, double anguloVerticalGrados){
		double distancia=0;
		Vector<PosicionBalon> posicionesFuturas=this.simula(indiceJugador, fuerzaTiro, anguloVerticalGrados);
		boolean porArriba=false;
		for(int i=0;i<posicionesFuturas.size();i++){
			PosicionBalon posAct=posicionesFuturas.get(i);
			if(posAct.altura>Constants.ALTURA_CONTROL_BALON){
				porArriba=true;
			}
			if(porArriba && posAct.altura <= Constants.ALTURA_CONTROL_BALON){
				distancia=posAct.X;
				break;
			}
		}
		return distancia;
	}
	private double[] distanciaTiroPorArribaHastaAlturaPorteria(int indiceJugador,double fuerzaTiro, double anguloVerticalGrados){
		double distancia[]={-1,-1};
		Vector<PosicionBalon> posicionesFuturas=this.simula(indiceJugador, fuerzaTiro, anguloVerticalGrados);
		boolean porArriba=false;
		for(int i=0;i<posicionesFuturas.size();i++){
			PosicionBalon posAct=posicionesFuturas.get(i);
			if(posAct.altura+spAct.ballAltitude()>Constants.ALTO_ARCO){
				porArriba=true;
			}
			if(porArriba && posAct.altura +spAct.ballAltitude()< Constants.ALTO_ARCO){
				distancia[1]=posAct.X;
				distancia[0]=posicionesFuturas.get(i-1).X;
				
				break;
			}
		}
		double distancia2=distancia[1]-distancia[0];
		return distancia;
	}
	private double fuerzaAdecuadaParaLLegarPorArribaHastaDistancia(int indiceJugador,double anguloVerticalGrados,double distanciaDeseada){
		double fuerzaAdecuada=-1;
		double mejorDistanciaAprox=Double.MAX_VALUE;
		for(double f=1;f>=0;f=f-0.025){
			double distanciaConseguida=distanciaTiroPorArribaHastaAlturaControlable(indiceJugador,f,anguloVerticalGrados);
			double diferenciaAct=Math.abs(distanciaConseguida-distanciaDeseada);
			if(diferenciaAct<mejorDistanciaAprox){
				fuerzaAdecuada=f;
				mejorDistanciaAprox=diferenciaAct;
			}
		}
		
		return fuerzaAdecuada;
	}
	private double[] anguloyFuerzaParaLLegarPorArribaHastaDistanciaChutando(int indiceJugador,double distanciaDeseada){
		double mejorAngulo=-1;
		double mejorFuerza=-1;
		double mayorMargen=-1;
		double minError=Double.MAX_VALUE;
		float anguloIni=25;
		if(spAct.rivalCanKick().length>0){
			anguloIni=45;
		}
		for(float angulo=anguloIni;angulo<=60;angulo=angulo+1f){
			double resultados[]=fuerzaAdecuadaParaLLegarPorArribaHastaDistanciaChutandoYMargen(indiceJugador,angulo,distanciaDeseada);
			if(resultados[0]!=-1&&resultados[1]!=-1){
				if(minError>=resultados[2]){
					minError=resultados[2];
					mayorMargen=resultados[1];
					mejorAngulo=angulo;
					mejorFuerza=resultados[0];
				}
			}
		}
		
		double resultados[]={mejorAngulo,mejorFuerza};
		return resultados;
	}
	private double[] fuerzaAdecuadaParaLLegarPorArribaHastaDistanciaChutandoYMargen(int indiceJugador,double anguloVerticalGrados,double distanciaDeseada){
		double fuerzaAdecuada=-1;
		double mejorDistanciaAprox=Double.MAX_VALUE;
		double anguloBalonPorteria=spAct.ballPosition().angle(Constants.centroArcoSup);
		
		double mejoresDistancias[]={-1,-1,-1};
		for(double f=1;f>=0;f=f-0.002){
			double distanciasConseguidas[]=distanciaTiroPorArribaHastaAlturaPorteria(indiceJugador,f,anguloVerticalGrados);
			double distanciaConseguida=(distanciasConseguidas[0]*0.9+distanciasConseguidas[1]*0.1)/2.0;
			double diferenciaAct=Math.abs(distanciaConseguida-distanciaDeseada);
			if(distanciasConseguidas[0]<distanciaDeseada&&distanciasConseguidas[1]>distanciaDeseada&&diferenciaAct<mejorDistanciaAprox){
				fuerzaAdecuada=f;
				mejorDistanciaAprox=diferenciaAct;
				mejoresDistancias=distanciasConseguidas;
			}
		}
		double datos[]={fuerzaAdecuada,mejoresDistancias[1]-mejoresDistancias[0],mejorDistanciaAprox};
		//System.out.println(" resultado->"+datos[0]+" "+datos[1]+" "+datos[2]+" ");
		return datos;
	}
	private double fuerzaAdecuadaParaPaseRapido(int indiceJugador,double anguloVerticalGrados,double distanciaDeseada,boolean esAlHueco){
		double fuerzaAdecuada=-1;
		double velAdecuadaRecepcionPase;
		if(esAlHueco){
			velAdecuadaRecepcionPase=1.0;
		}else{
			velAdecuadaRecepcionPase=1.2;
		}
		double mejorErrorAprox=Double.MAX_VALUE;
		for(double f=1;f>=0;f=f-0.025){
			Vector<PosicionBalon> lPosiciones= this.simula(indiceJugador, f, anguloVerticalGrados);
			for(int i=0;i<lPosiciones.size();i++){
				PosicionBalon posAct=lPosiciones.get(i);
				if(posAct.X>=distanciaDeseada){
					double difVelBalonVelDeseado=Math.abs(velAdecuadaRecepcionPase-posAct.deltaX);
					if(mejorErrorAprox>difVelBalonVelDeseado){
						mejorErrorAprox=difVelBalonVelDeseado;
						fuerzaAdecuada=f;
					}
					break;
				}
			}
		}
		return fuerzaAdecuada;
	}
	private Vector<PosicionBalon> simula(int indiceJugador,double fuerzaTiro, double anguloVerticalGrados) {
        
           
            //ang -> rad
            double factorReduccion=spAct.getMyPlayerEnergy(indiceJugador)*Constants.ENERGIA_DISPARO;
            if(factorReduccion>1){
            	factorReduccion=1;
            }
            double av = Math.toRadians(anguloVerticalGrados);
            double fuerzaJugador=1.2+1.2*spAct.getMyPlayerPower(indiceJugador);
            //velocidad
            double vel = fuerzaTiro * fuerzaJugador*factorReduccion;
            //direccion;
            double dz = vel * Math.sin(av);
            double dr = vel * Math.cos(av);
            AbstractTrajectory trayectoria = new AirTrajectory(dr, dz, 0, 0);
            //coordenadas
            double r = 0;
            double z = 0;
            double rAnterior=0;
            double zAnterior=0;
            Vector<PosicionBalon> lista=new Vector<PosicionBalon>();
            for (int i = 0; i < 2500; i++) {
            	
                r = trayectoria.getX((double) i / (double)60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
                z = trayectoria.getY((double) i /(double) 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA*2;
            	//r = trayectoria.getX( i) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
                //z = trayectoria.getY(i ) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
                double deltaR=r-rAnterior;
                double deltaZ=z-zAnterior;
               lista.add(new PosicionBalon(r, z,deltaR,deltaZ));
               rAnterior=r;
               zAnterior=z;
               if(i>15&&deltaR==0){
            	   break;
               }
            }
            return lista;
         
    }
	private int numIteracionesMinimasDeRivalHastaPosicion(Position position){
		int minIteraciones=Integer.MAX_VALUE;
		for(int i=0;i<spAct.rivalPlayers().length;i++){
			double velRivalRel=spAct.getRivalPlayerSpeed(i);
			double realRivalSpeed=0.25+0.25*velRivalRel;
			Position posActRival=spAct.rivalPlayers()[i];
			double distancia=posActRival.distance(position);
			double distanciaRecorrida=Constants.DISTANCIA_CONTROL_BALON;
			for(int j=0;j<100;j++){
				if(distanciaRecorrida>=distancia){
					minIteraciones=Math.min(minIteraciones, j);
					break;
				}else{
					distanciaRecorrida=distanciaRecorrida+realRivalSpeed;
				}
				if(j>=minIteraciones){
					break;
				}
			}
		}
		return minIteraciones;
		
}private int numIteracionesMinimasMiasHastaPosicion(Position position){
	int minIteraciones=Integer.MAX_VALUE;
	for(int i=0;i<spAct.myPlayers().length;i++){
		double velRivalRel=spAct.getMyPlayerSpeed(i);
		double realRivalSpeed=0.25+0.25*velRivalRel;
		Position posActRival=spAct.myPlayers()[i];
		double distancia=posActRival.distance(position);
		double distanciaRecorrida=Constants.DISTANCIA_CONTROL_BALON;
		for(int j=0;j<100;j++){
			if(distanciaRecorrida>=distancia){
				minIteraciones=Math.min(minIteraciones, j);
				break;
			}else{
				distanciaRecorrida=distanciaRecorrida+realRivalSpeed;
			}
			if(j>=minIteraciones){
				break;
			}
		}
	}
	return minIteraciones;
	
}
	private double distMinDeRivalHastaPosicion(Position position){
		double distMin=Double.MAX_VALUE;
		for(int i=0;i<spAct.rivalPlayers().length;i++){
			Position posActRival=spAct.rivalPlayers()[i];
			double distancia=posActRival.distance(position);
			distMin=Math.min(distancia,distMin);
		}
		return distMin;
	}
	private int primeraPosParaMiPortero(int limiteIteraciones){
		int indexPosicion=-1;
		double velPortero=(0.25+0.25*spAct.getMyPlayerSpeed(0))*1.2;
		for(int i=0;i<limiteIteraciones;i++){
			double[] posAct=spAct.getTrajectory(i);
			double altura=posAct[2];
			if(altura<=Constants.ALTO_ARCO){
				Position pos=new Position(posAct[0],posAct[1]);
				double distancia=pos.distance(spAct.myPlayers()[0]);
				if(distancia<=i*velPortero+Constants.DISTANCIA_CONTROL_BALON_PORTERO){
					indexPosicion=i;
					break;
				}
			}
		}
		
		
		
		return indexPosicion;
	}
	private void cubrir(){
		for(int i=0;i<11;i++){
			enemigosCubiertos[i]=false;
		}
		Vector<Position> jugEnemigosAdelantados=new Vector<Position>();
		for(int i=1;i<11;i++){
			Position posObjJug=spAct.rivalPlayers()[i];
			boolean metido=false;
			for(int j=0;j<jugEnemigosAdelantados.size();j++){
				Position posComparar=jugEnemigosAdelantados.get(j);
				if(posComparar.getY()>posObjJug.getY()){
					jugEnemigosAdelantados.insertElementAt(posObjJug,j);
					metido=true;
					break;
				}
			}
			if(!metido){
				jugEnemigosAdelantados.add(posObjJug);
			}
		}
		
		Vector<Position> jugEnemigosAdelantadosIzq=new Vector<Position>();
		for(int i=0;i<4;i++){
			Position posObjJug=jugEnemigosAdelantados.get(i);
			boolean metido=false;
			for(int j=0;j<jugEnemigosAdelantadosIzq.size();j++){
				Position posComparar=jugEnemigosAdelantadosIzq.get(j);
				if(posComparar.getX()<posObjJug.getX()){
					jugEnemigosAdelantadosIzq.insertElementAt(posObjJug,j);
					metido=true;
					break;
				}
			}
			if(!metido){
				jugEnemigosAdelantadosIzq.add(posObjJug);
			}
		}
		for(int i=0;i<4;i++){
			int indexMiJUgador=i+1;
			Position posCubrir=jugEnemigosAdelantadosIzq.get(i);
			Position posCubrirArreglado=new Position(posCubrir.getX(), Math.max(this.posicionesObjs[1].getY(), posCubrir.getY()));
			comandos.add(new CommandMoveTo(indexMiJUgador,posCubrirArreglado));
		}
		
	}
	
	private boolean jugadorEnPosicionDebeCubrir(Position pos){
		if(posRecuperacion.getY()>0){
			double distBalon=spAct.ballPosition().distance(Constants.centroArcoSup);
			double distJug=pos.distance(Constants.centroArcoSup);
			return distJug>distBalon;
		}else{
			double distBalon=spAct.ballPosition().distance(Constants.centroArcoInf);
			double distJug=pos.distance(Constants.centroArcoInf);
			return distJug<distBalon;
		}
	}
	private Vector<Integer> indicesRivalCercaMiPorteria(boolean rivales){
		Position []posicionesJugadores;
		int indiceInicial=0;
		if(rivales){
			posicionesJugadores=spAct.rivalPlayers();
		}else{
			posicionesJugadores=spAct.myPlayers();
			indiceInicial=1;
		}
		Vector<Integer> lIndices=new Vector<Integer>();
		Vector<Double> lDistancias=new Vector<Double>();
		for(int i=indiceInicial;i<posicionesJugadores.length;i++){
			Integer indice=new Integer(i);
			Double distancia=Constants.centroArcoInf.distance(posicionesJugadores[i]);
			boolean metido=false;
			for(int j=0;j<lDistancias.size();j++){
				Double distAct=lDistancias.get(j);
				if(distancia<distAct){
					metido=true;
					lDistancias.insertElementAt(distancia, j);
					lIndices.insertElementAt(indice, j);
					break;
				}
			}
			if(!metido){
				lDistancias.add(distancia);
				lIndices.add(indice);
			}
		}
		return lIndices;
	}
	private boolean tirarAUnCorner(int indiceJug){
		Position posObj;
		if(spAct.ballPosition().getX()<0){
			posObj=Constants.cornerSupDer;
		}else{
			posObj=Constants.cornerSupIzq;
		}
		double dist=spAct.ballPosition().distance(posObj)*0.8;
		double errorMin=Double.MAX_VALUE;
		double fuerzaMejor=-1;
		for(double f=1;f>=0;f=f-0.025){
			double distanciaConseguida=distanciaHastaQueElBalonVaLento(indiceJug,f,45);
			if(distanciaConseguida<dist&&errorMin>(dist-distanciaConseguida)){
				errorMin=dist-distanciaConseguida;
				fuerzaMejor=f;
			}
		}
		if(fuerzaMejor!=-1){
			//System.out.println("para un error de "+errorMin+" una fuerza de "+fuerzaMejor);
			comandos.add(new CommandHitBall(indiceJug,posObj,fuerzaMejor,45));
			return true;
		}else{
			return false;
		}
		
	}
	
	private double distanciaHastaQueElBalonVaLento(int indiceJug,double fuerza,double angulo){
		double velocidadLenta=0.00003;
		Vector<PosicionBalon> lPosiciones= this.simula(indiceJug, fuerza, angulo);
		for(int i=1;i<lPosiciones.size();i++){
			PosicionBalon posBalon=lPosiciones.get(i);
			if(posBalon.deltaX<=velocidadLenta){
				return posBalon.X;
			}
		}
		
		return -1;
	}
//0.86 1.2-2.4 (altura control 3...)
	
}