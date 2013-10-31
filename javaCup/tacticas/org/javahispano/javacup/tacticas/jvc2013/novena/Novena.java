package org.javahispano.javacup.tacticas.jvc2013.novena;

import java.util.ArrayList;

import org.javahispano.javacup.model.*;
import org.javahispano.javacup.model.util.*;
import org.javahispano.javacup.model.command.*;
import org.javahispano.javacup.model.engine.GameSituations;

import java.util.List;

public class Novena implements Tactic {

    private DatosGlobales datosGlobales;


    public Novena() {
       datosGlobales = new DatosGlobales();
       
    }



    TacticDetail detalleEquipo=new Equipo();
    @Override
    public TacticDetail getDetail() {
        return detalleEquipo;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
    	Position[] a = Alineaciones.getAlineacion(1).getPosiciones();
    return a;
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
    return Alineaciones.getAlineacion(2).getPosiciones();
    }

   @Override
    public List<Command> execute(GameSituations sp) {
        
        List<Command> comandos = new ArrayList<Command>();

        
        datosGlobales.actualizarDatos(sp);
        
        boolean realicePase = false;
        
        for (int indiceJugador=0 ; indiceJugador < 11 ; indiceJugador++){
        	if( Utiles.puedeRematarElJugador(sp, indiceJugador) ){
        		double anguloAvanzarBalon = Utiles.anguloMovimientoBalonControlado180(sp, datosGlobales, indiceJugador);
        		if (Utiles.estaElBalonEnZonaTiroAmplia(sp) //&& !Utiles.estaElBalonEnZonaTiroBalonChica(sp) 
        				&& !sp.isStarts() && anguloAvanzarBalon == Double.MAX_VALUE ){
    				ParametrosRemate valores = Utiles.obtenerValoresRemateAPuerta(sp,datosGlobales, indiceJugador);
                    if (valores != null){
                    	comandos.add(new CommandHitBall(indiceJugador, valores.getPosicionLinea().getPosicion(), valores.getFuerza(), valores.getAngulovertical()));
                    	datosGlobales.setPPase(null);
                    	datosGlobales.setDefendiendo();
                    	datosGlobales.setgolpeoBalon(indiceJugador);
                    }else{
                    	valores = Utiles.obtenerValoresRemateACentroPuerta(sp,datosGlobales, indiceJugador);
                    	if (valores != null){
                        	comandos.add(new CommandHitBall(indiceJugador, valores.getPosicionLinea().getPosicion(), valores.getFuerza(), valores.getAngulovertical()));
                        	datosGlobales.setPPase(null);
                        	datosGlobales.setDefendiendo();
                        	datosGlobales.setgolpeoBalon(indiceJugador);
                        }
                    }
                  
    			}else if (Utiles.estaElBalonEnZonaTiroChica(sp) && !sp.isStarts()){
    				ParametrosRemate valores = Utiles.obtenerValoresRemateAPuerta(sp,datosGlobales, indiceJugador);
                    if (valores != null){
                    	comandos.add(new CommandHitBall(indiceJugador, valores.getPosicionLinea().getPosicion(), valores.getFuerza(), valores.getAngulovertical()));
                    	datosGlobales.setPPase(null);
                    	datosGlobales.setDefendiendo();
                    	datosGlobales.setgolpeoBalon(indiceJugador);
                    }else{
                    	valores = Utiles.obtenerValoresRemateACentroPuerta(sp,datosGlobales, indiceJugador);
                    	if (valores != null){
                        	comandos.add(new CommandHitBall(indiceJugador, valores.getPosicionLinea().getPosicion(), valores.getFuerza(), valores.getAngulovertical()));
                        	datosGlobales.setPPase(null);
                        	datosGlobales.setDefendiendo();
                        	datosGlobales.setgolpeoBalon(indiceJugador);
                        }
                    }
    			}
        		
        		else{
					 
					ParametrosPase pp = Utiles.obtenerValoresPase(sp, datosGlobales,indiceJugador);
				
					if (pp != null && !Utiles.trajectoriaPeligrosa(sp, pp)){
						Position posicionPase = pp.getPosicionRecepcion();
						double anguloVertical = pp.getAngulovertical();
						double fuerza = pp.getFuerza();
						datosGlobales.setPPase(pp);
						datosGlobales.setgolpeoBalon(indiceJugador);
						realicePase = true;
						comandos.add(new CommandHitBall(indiceJugador, posicionPase, fuerza, anguloVertical));
					}else if (pp != null){
						ParametrosRemate valores = Utiles.obtenerDespeje(sp, indiceJugador);
						if (valores != null){
							comandos.add(new CommandHitBall(indiceJugador, valores.getPosicionLinea().getPosicion(), valores.getFuerza(), valores.getAngulovertical()));
							datosGlobales.setPPase(null);
							datosGlobales.setgolpeoBalon(indiceJugador);
						}
					}else{
						ParametrosRemate valores = Utiles.obtenerValoresRemateACentroPuerta(sp,datosGlobales, indiceJugador);
							if (valores != null && !Utiles.trajectoriaPeligrosa(sp, valores, indiceJugador) ){
									comandos.add(new CommandHitBall(indiceJugador, valores.getPosicionLinea().getPosicion(), valores.getFuerza(), valores.getAngulovertical()));
									datosGlobales.setPPase(null);
									datosGlobales.setgolpeoBalon(indiceJugador);
							}else{
								valores = Utiles.obtenerDespeje(sp, indiceJugador);
								if (valores != null){
									comandos.add(new CommandHitBall(indiceJugador, valores.getPosicionLinea().getPosicion(), valores.getFuerza(), valores.getAngulovertical()));
									datosGlobales.setPPase(null);
									datosGlobales.setgolpeoBalon(indiceJugador);
								}
							}
        		
        			}
				}
    				
    			
        	}
        }  
        
        for (int indiceJugador=7 ; indiceJugador < 11 ; indiceJugador++){
        	if (datosGlobales.estoyAtacando()){
        		if (sp.isStarts()){
        			int referencia ;
        			if (realicePase)
            			referencia = datosGlobales.getPPase().getPase().getReceptor();
        			else
        				referencia = Utiles.masCercaDelBalon(sp);
        			
        			if ( realicePase && referencia == indiceJugador ){
            			datosGlobales.setCorregirPosicionPase(true);
            			if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], datosGlobales.getPPase().getPosicionRecepcion()))
            				comandos.add(new CommandMoveTo(indiceJugador,datosGlobales.getPPase().getPosicionRecepcion()));
            			 
            		}else if (!realicePase && referencia == indiceJugador){
        				comandos.add(new CommandMoveTo(indiceJugador, sp.ballPosition()));
            			}
        		
            			else{
        				double anguloMov = Double.MAX_VALUE;
            			double distMov = 0;
            			Position nuevaPosicion = null;
            			
            			MovimientoJugador mov = DatosUtiles.obtenerMovimiento(referencia, indiceJugador);
            			
            			if (mov != null){
            				anguloMov = mov.getAnguloRefencia() * (Math.PI/180);
            				distMov = mov.getDistanciaRefencia();
            			}
            				
            			
            			if (anguloMov != Double.MAX_VALUE){
            				Position posicionReferencia = sp.myPlayers()[referencia].moveAngle(anguloMov, distMov);
            				if (Utiles.estaEnZonaTiroChica(posicionReferencia))
                				nuevaPosicion = Constants.penalSup;
            				else{
            					double angulo = sp.myPlayers()[indiceJugador].angle(posicionReferencia);
            					nuevaPosicion = sp.myPlayers()[indiceJugador].moveAngle(angulo, 0.5);
            				}
            				
                			if (!sp.isStarts() && nuevaPosicion.getY() > datosGlobales.getPosicionFueraJuego() && nuevaPosicion.getY() < Constants.LARGO_CAMPO_JUEGO - Constants.ANCHO_AREA_GRANDE)
                					nuevaPosicion =  nuevaPosicion.setPosition(Constants.penalSup.getX(), datosGlobales.getPosicionFueraJuego());
                			
                				nuevaPosicion = Utiles.setInsideGameField(nuevaPosicion);
                				
                				if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], nuevaPosicion)){
                					comandos.add(new CommandMoveTo(indiceJugador,nuevaPosicion));
                				}
            			}
        			}
        		}
        		else if ( realicePase && datosGlobales.getPPase().getPase().getReceptor() == indiceJugador ){
        			datosGlobales.setCorregirPosicionPase(true);
        			if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], datosGlobales.getPPase().getPosicionRecepcion()))
        				comandos.add(new CommandMoveTo(indiceJugador,datosGlobales.getPPase().getPosicionRecepcion()));
        		}
        		else if (datosGlobales.getPPase() != null && datosGlobales.getPPase().getPase().getReceptor()==indiceJugador){
        			
        					
        					Position newPosition = Utiles.corregirPosicionPase(sp, datosGlobales, datosGlobales.getPPase());
        					
        					if (newPosition != null){
        						datosGlobales.getPPase().setPosicionRecepcion(newPosition);
        						
            					
        					}
        						
        				
        			if (datosGlobales.getPPase() != null && Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], datosGlobales.getPPase().getPosicionRecepcion())){
        				comandos.add(new CommandMoveTo(indiceJugador, Utiles.protegerBalon(sp, datosGlobales.getPPase().getPosicionRecepcion())));
        			}
        				
        		}
        		else if (datosGlobales.getPPase() != null){
        			     			
        			
        			double anguloMov = Double.MAX_VALUE;
        			double distMov = 0;
        			Position nuevaPosicion = null;
        			
        			MovimientoJugador mov = DatosUtiles.obtenerMovimiento(datosGlobales.getPPase().getPase().getReceptor(), indiceJugador);
        			
        			if (mov != null){
        				anguloMov = mov.getAnguloRefencia() * (Math.PI/180);
        				distMov = mov.getDistanciaRefencia();
        			}
        			
        				
        			
        			if (anguloMov != Double.MAX_VALUE){
        				Position posicionReferencia = sp.myPlayers()[datosGlobales.getPPase().getPase().getReceptor()].moveAngle(anguloMov, distMov);
        				if (Utiles.estaJugadorEnZonaTiroChica(sp, indiceJugador))
            				nuevaPosicion = Constants.penalSup;
        				else{
        					double angulo = sp.myPlayers()[indiceJugador].angle(posicionReferencia);
            				nuevaPosicion = sp.myPlayers()[indiceJugador].moveAngle(angulo, 0.5);
        				}
        				
    					
        				
            			if (nuevaPosicion.getY() > datosGlobales.getPosicionFueraJuego() && nuevaPosicion.getY() < Constants.LARGO_CAMPO_JUEGO - Constants.ANCHO_AREA_GRANDE)
            					nuevaPosicion =  nuevaPosicion.setPosition(0, datosGlobales.getPosicionFueraJuego());
            			
            				nuevaPosicion = nuevaPosicion.setInsideGameField();
            				
            			
            				
            				
            				if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], nuevaPosicion)){
            					comandos.add(new CommandMoveTo(indiceJugador,nuevaPosicion));
            				}
        			}
        
        			
        		}
        		else{
        			if (!datosGlobales.isEnMoviemiento() && Utiles.masCercaDelBalon(sp) == indiceJugador){
            			comandos.add(new CommandMoveTo(indiceJugador, sp.ballPosition()));
            		}else if (datosGlobales.isEnMoviemiento() && Utiles.masCercaDeTrayectoriaBalon(sp, datosGlobales, indiceJugador)){
            			Position nuevaPosicion = datosGlobales.posicionEnTrayectoria(sp, datosGlobales.getIteracionesRecuperar(indiceJugador));
            			if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], nuevaPosicion)){
            				comandos.add(new CommandMoveTo(indiceJugador,Utiles.protegerBalon(sp, nuevaPosicion)));
            			}
            		}else if ( DatosUtiles.esDelantero(indiceJugador) && Utiles.balonEnPosicionOfensiva(sp) && datosGlobales.isEnMoviemiento()){
            			double anguloMov = datosGlobales.getPosicionPreviaBalon().angle(sp.ballPosition());
            			Position nuevaPosicion = sp.myPlayers()[indiceJugador].moveAngle(anguloMov, 0.5);
            			if (nuevaPosicion.getY() > datosGlobales.getPosicionFueraJuego() && nuevaPosicion.getY() < Constants.LARGO_CAMPO_JUEGO - Constants.ANCHO_AREA_GRANDE)
        					nuevaPosicion =  nuevaPosicion.setPosition(nuevaPosicion.getX(), datosGlobales.getPosicionFueraJuego());
        			
        				nuevaPosicion = nuevaPosicion.setInsideGameField();
        				
        				if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], nuevaPosicion)){
        					comandos.add(new CommandMoveTo(indiceJugador,nuevaPosicion));
        				}
            		}
        			
        		}
        	}else if (DatosUtiles.esDelantero(indiceJugador)){
        		
        		
        		if (!datosGlobales.isEnMoviemiento() && Utiles.masCercaDelBalon(sp) == indiceJugador){
        			comandos.add(new CommandMoveTo(indiceJugador, sp.ballPosition()));
        		}else if (datosGlobales.isEnMoviemiento() && Utiles.masCercaDeTrayectoriaBalon(sp, datosGlobales, indiceJugador)){
        			Position nuevaPosicion = datosGlobales.posicionEnTrayectoria(sp, datosGlobales.getIteracionesRecuperar(indiceJugador));
        			if ( Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], nuevaPosicion)){
        				comandos.add(new CommandMoveTo(indiceJugador,nuevaPosicion));
        			}
        		}
        		
        		else{
        			Position nuevaPosicion = Alineaciones.getAlineacion(0).getPosiciones()[indiceJugador];
        			if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceJugador], nuevaPosicion)){
        				comandos.add(new CommandMoveTo(indiceJugador,nuevaPosicion));
        			}
        		}
        			
        	}
        }
        
        
        	
        	int indicePortero = DatosUtiles.indicePortero();
        	if ( Utiles.disparoAPuerta(datosGlobales) ){
                Position nuevaPosicion = Utiles.posicionEnLaTrayectoriaMasCercaDelJugador(sp, datosGlobales, indicePortero, true);
                if (nuevaPosicion == null){
                	nuevaPosicion = datosGlobales.getPuntoFuera().getPosicion();
                	if (Utiles.esNuevaPosicion(sp.myPlayers()[indicePortero], nuevaPosicion)){
            			comandos.add(new CommandMoveTo(indicePortero,nuevaPosicion));
            		}
                }else if (Utiles.esNuevaPosicion(sp.myPlayers()[indicePortero], nuevaPosicion)){
        			comandos.add(new CommandMoveTo(indicePortero,nuevaPosicion));
        		}
        	}
        	else if (Utiles.masCercaDeTrayectoriaBalonTodos(sp, datosGlobales, indicePortero)){
        		Position nuevaPosicion = datosGlobales.posicionEnTrayectoria(sp, datosGlobales.getIteracionesRecuperar(indicePortero));
        		if ( Utiles.esNuevaPosicion(sp.myPlayers()[indicePortero], nuevaPosicion)){
    				comandos.add(new CommandMoveTo(indicePortero,nuevaPosicion));
    			}
        	}
        	else if (!sp.isStarts() && !Utiles.balonEnPosicionOfensiva(sp)){
        		double angulo = Constants.centroArcoInf.angle(sp.ballPosition());
        		Position nuevaPosicion = Constants.centroArcoInf.moveAngle(angulo, Constants.LARGO_ARCO/2);
        		if (Utiles.esNuevaPosicion(sp.myPlayers()[indicePortero], nuevaPosicion)){
        			comandos.add(new CommandMoveTo(indicePortero,nuevaPosicion));
        		}
            
        	}if (!datosGlobales.isEnMoviemiento() && Utiles.masCercaDelBalon(sp) == indicePortero){
    			comandos.add(new CommandMoveTo(indicePortero, sp.ballPosition()));
    		}
   
        for (int i = 0; i < DatosUtiles.indicesDefensas().length ; i++ ){
        	int indiceDefensa = DatosUtiles.indicesDefensas()[i];
        	Zona zd = DatosUtiles.getZonaDefensiva(indiceDefensa);
        	if ( Utiles.existeRivalSinMarcaEnZonaDefensiva(sp, datosGlobales, zd) ){
        		int indiceRival = Utiles.rivalSinMarcaMasCercaDelDefensaEnZonaDefensiva(sp, datosGlobales, indiceDefensa, zd);
        		if ( !datosGlobales.tieneMarca(indiceDefensa) ){      			
        			datosGlobales.marcarARival(indiceDefensa, indiceRival);
        		}else{
        			if (Utiles.existeDefensaSinMarca(sp, datosGlobales)){
        				int indiceRivalMarcado = datosGlobales.getMarca(indiceDefensa);
        				int indiceDefensaSinMarca = Utiles.indiceDefensaSinMarcaMasCercaRivalMarcado(sp, datosGlobales, indiceRivalMarcado);
        				datosGlobales.marcarARival(indiceDefensa, indiceRival);
        				datosGlobales.marcarARival(indiceDefensaSinMarca, indiceRivalMarcado);
        			}
        		}
        	}
        	if (datosGlobales.tieneMarca(indiceDefensa)){
        		Position posMarca = sp.rivalPlayers()[datosGlobales.getMarca(indiceDefensa)];
        		if ( Utiles.marcaFueraZonasDefensivas(sp, datosGlobales, posMarca) ){
        			datosGlobales.dejarMarca(indiceDefensa);
        		}else if ( Utiles.marcaFueraZonaDefensiva(sp, datosGlobales, indiceDefensa) && Utiles.existeDefensaSinMarca(sp, datosGlobales, posMarca) ){
        			int indiceRivalMarcado = datosGlobales.getMarca(indiceDefensa);
    				int indiceDefensaSinMarca = Utiles.indiceDefensaSinMarcaMasCercaRivalMarcado(sp, datosGlobales, indiceRivalMarcado);
    				datosGlobales.dejarMarca(indiceDefensa);
    				datosGlobales.marcarARival(indiceDefensaSinMarca, indiceRivalMarcado);
        		}
        		
        	}
        }
        
        for (int i = 0; i < DatosUtiles.indicesDefensas().length ; i++ ){
        	int indiceDefensa = DatosUtiles.indicesDefensas()[i];
        	
        	if (!datosGlobales.isEnMoviemiento() && Utiles.masCercaDelBalon(sp) == indiceDefensa){
    			comandos.add(new CommandMoveTo(indiceDefensa, sp.ballPosition()));
    		}else if (!datosGlobales.estoyAtacando() && datosGlobales.isEnMoviemiento() && Utiles.masCercaDeTrayectoriaBalon(sp, datosGlobales, indiceDefensa)){
    			Position nuevaPosicion = datosGlobales.posicionEnTrayectoria(sp, datosGlobales.getIteracionesRecuperar(indiceDefensa));
    			if (datosGlobales.tieneMarca(indiceDefensa)){
    				Position posMarca = sp.rivalPlayers()[datosGlobales.getMarca(indiceDefensa)];
    				if ( Utiles.existeDefensaSinMarca(sp, datosGlobales, posMarca) ){
    					int indiceRivalMarcado = datosGlobales.getMarca(indiceDefensa);
        				int indiceDefensaSinMarca = Utiles.indiceDefensaSinMarcaMasCercaRivalMarcado(sp, datosGlobales, indiceRivalMarcado);
        				datosGlobales.dejarMarca(indiceDefensa);
        				datosGlobales.marcarARival(indiceDefensaSinMarca, indiceRivalMarcado);
    				}
    			}
    			if ( Utiles.esNuevaPosicion(sp.myPlayers()[indiceDefensa], nuevaPosicion)){
    				comandos.add(new CommandMoveTo(indiceDefensa,nuevaPosicion));
    			}
    		}
    		else if(datosGlobales.tieneMarca(indiceDefensa)){
    			int indiceRival = datosGlobales.getMarca(indiceDefensa);
    			Position posicionMarcaje = Utiles.posicionMarcaPorDelante(sp, indiceRival);
    			if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceDefensa], posicionMarcaje)){
    				comandos.add(new CommandMoveTo(indiceDefensa,posicionMarcaje));
    			}
    		}else{
    			Position posicionDefensiva = Alineaciones.getAlineacion(0).getPosiciones()[indiceDefensa];
    			if ( posicionDefensiva.getY() < datosGlobales.getPosicionFueraJuegoRival() )
    				posicionDefensiva = posicionDefensiva.setPosition(posicionDefensiva.getX(), datosGlobales.getPosicionFueraJuegoRival());
    			
    			if (Utiles.esNuevaPosicion(sp.myPlayers()[indiceDefensa], posicionDefensiva)){
    				comandos.add(new CommandMoveTo(indiceDefensa,posicionDefensiva));
    			}
    		}
        }
        
        
        
        
        	comandos = Utiles.optimizarMoveTo(comandos, sp);
        	datosGlobales.actualizarAceleracion(comandos, sp);

        	
        return comandos;
    }







}