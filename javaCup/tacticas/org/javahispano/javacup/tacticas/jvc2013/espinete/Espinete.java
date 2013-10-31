package org.javahispano.javacup.tacticas.jvc2013.espinete;

import org.javahispano.javacup.model.*;
import org.javahispano.javacup.model.util.*;
import org.javahispano.javacup.model.command.*;
import org.javahispano.javacup.model.engine.GameSituations;
import java.util.LinkedList;
import java.util.List;


public class Espinete implements Tactic {
    //Lista de comandos
    List<Command> commands = new LinkedList<>();
    TacticDetail equipo = new InfoEquipo();
    Position oldBall = new Position(0,0);	
    Alineaciones alineaciones = new Alineaciones();
    boolean golContrario = false;
    int difGoles = 0;
    int tirosPuerta = 0;
    int pasesHueco = 0;
    int despejes = 0;
    int paseDefensa = 0;
    int despejesPerpen = 0;
    
    
    @Override
    public TacticDetail getDetail() {
        return equipo;
    }

    @Override
    public Position[] getStartPositions(GameSituations sp) {
    	return alineaciones.sacoYo();
    }

    @Override
    public Position[] getNoStartPositions(GameSituations sp) {
    	return alineaciones.sacanEllos();
    }
    
    @Override
    public List<Command> execute(GameSituations sp) {
    	
        commands.clear();

        Position[] alinDef = alineaciones.dameAlineacion(sp.iteration(), tirosPuerta, difGoles, golContrario);
        
        golContrario = false;
        
        //Obtiene las posiciones de tus jugadores  
        Position[] miPos = sp.myPlayers();
        Position bola = sp.ballPosition();
        // movPos
        
        int[] masCercanosABola = Utils.getMasCercanosAPuntoList(sp, bola, true);
        int[] masAdelantadosOtros = Utils.getMasCercanosAPuntoList(sp, Constants.centroArcoInf, false);
         
        
        
        Position[] movPos = new Position[11];
        
        //portero
        movPos[0] = Utils.muevePortero(oldBall, sp);
        
        //cubrir al hombre                
        for(int i=1; i<11; i++) {    
            Position otroPos = sp.rivalPlayers()[masAdelantadosOtros[i-1]];          
            int candi = Utils.getMasCercanoAPunto(miPos, otroPos, movPos);            
            if (!Utils.companyeroCerca(miPos, otroPos)) {
                movPos[candi] = new Position(otroPos.getX() + ((bola.getX() < otroPos.getX()) ? -1 : 1), 
                                             otroPos.getY() + ((bola.getY() < otroPos.getY()) ? -1 : 1)) ;                                                  
            } 
        }   
        
        for(int i=1; i<11; i++) {             
            if (movPos[i] == null) {
                movPos[i] = alinDef[i];
            }            
        }
        
        //mas cercano a bola 
        if ((masCercanosABola[0] != 0) || (Utils.bolaQuieta(oldBall,bola))) {
            movPos[masCercanosABola[0]] = bola;             
        }        
        
        movPos = Utils.getBolaFuturaJug(oldBall, sp, movPos);
        
        
        
        //peligro
        if ((bola.getY() < -20) && sp.rivalCanKick().length > 0) {
            if (sp.rivalCanKick().length > 0) {
                for (int i = 0; i < miPos.length; i++) {
                    if (Utils.jugadorEstaCerca(miPos[i], bola, 5)) {
                         movPos[i] = bola;
                    }
                }
            }            
        } else {
            movPos = Utils.quitarFueraJuego(movPos, sp.rivalPlayers(), sp.rivalPlayersDetail(), bola,  0, oldBall);
        }
                
        
       
        
        for (int i=0; i<movPos.length; i++) {
            if (movPos[i] != null) {
                commands.add(new CommandMoveTo(i, movPos[i], (sp.getMyPlayerEnergy(i) > Constants.SPRINT_ENERGIA_MIN)));                                
            }
        }  
        
        
        for (int i : sp.canKick()) {     
            
            if (!sp.isStarts() && (bola.getY() > 20) && (Math.abs(bola.getX()) < 30)) {
                
                System.out.println(i + " tiro a puerta:" + bola);
                commands.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 15)); 
                tirosPuerta++;
                
            } else if (!sp.isStarts() && (bola.getY() >= -25) && 
                       (!bola.equals(Constants.centroCampoJuego))) {
                
                System.out.println(i + " pase:" + bola); 
                Position hueco = Utils.buscaPase(sp);
                commands.add(new CommandHitBall(i, hueco, 
                             Utils.calcularFuerzaTiro(bola, sp.myPlayersDetail(), hueco, i), 45)); 
                pasesHueco++;
            } else if (!sp.isStarts() && (bola.getY() < -25) && 
                       (bola.distance(sp.rivalPlayers()[masAdelantadosOtros[0]]) > 4) &&
                       (sp.rivalPlayers()[masAdelantadosOtros[0]].getY() > bola.getY())) {
                System.out.println(i + " pasedefensa:" + bola); 
                Position hueco = Utils.buscaPase(sp);
                commands.add(new CommandHitBall(i, hueco, 
                             Utils.calcularFuerzaTiro(bola, sp.myPlayersDetail(), hueco, i), 45));
                paseDefensa++; 
            } else if (!sp.isStarts() && (bola.getY() < -25) && 
                       (bola.distance(sp.rivalPlayers()[masAdelantadosOtros[0]]) <= 4) &&
                       (sp.rivalPlayers()[masAdelantadosOtros[0]].getY() > bola.getY())) {
                System.out.println(i + " perpendicular:" + bola); 
                Position perpen = Utils.puntoPerpen(bola, sp.rivalPlayers()[masAdelantadosOtros[0]]);
                commands.add(new CommandHitBall(i, perpen, 1, 45));
                despejesPerpen++;
            } else {
                commands.add(new CommandHitBall(i, Constants.centroArcoSup, 1, 45)); 
                despejes++;
    
            }
        
        }
        
        //ball for next iteration
        oldBall = bola;
        
        if (sp.iteration() % 100 == 0) {
           System.out.println(" % % % % % iteración: " + sp.iteration() + "   *****  " + sp.myGoals() + " - " + sp.rivalGoals()); 
        }
        
        if (((sp.myGoals() - sp.rivalGoals()) != difGoles) || (sp.iteration() == 3599)) {
            if ((sp.myGoals() - sp.rivalGoals()) < difGoles) {
                golContrario = true;
            }
            difGoles = sp.myGoals() - sp.rivalGoals();
         
            System.out.println(" ##################**************" + sp.iteration());
            System.out.println( sp.myGoals() + " - " + sp.rivalGoals() );
            System.out.println(" @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            if (sp.iteration() == 3599) {
                System.out.println(" Despejes    : " + despejes);
                System.out.println(" Pases       : " + pasesHueco);
                System.out.println(" PasesDefensa: " + paseDefensa);
                System.out.println(" Perpen      : " + despejesPerpen);
                System.out.println(" Tirospuerta : " + tirosPuerta);
            }
        }
        
        //Retorna la lista de comandos 
    	return commands;    	
    }
    

}