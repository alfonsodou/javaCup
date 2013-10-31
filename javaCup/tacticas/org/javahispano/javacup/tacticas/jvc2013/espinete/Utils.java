package org.javahispano.javacup.tacticas.jvc2013.espinete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.util.Constants;

/**
 * @author kikejf
 *
 */
class Utils {
    
    static double maxLargo = (Constants.LARGO_CAMPO_JUEGO / 2);
    static double maxAncho = (Constants.ANCHO_CAMPO_JUEGO / 2);
    
    
    /**
     * movimiento del portero según la pelota corta con el eje x de la porteria
     * @param oldBall
     * @param bola
     * @return 
     */
    public static Position muevePortero(Position oldBall, GameSituations sp) {
        Position porteroPos = new Position(0, -52);            
        Position tiroPuerta = prediccionBolaPorteria(oldBall, sp);
                
        if ((tiroPuerta != null) && tiroPuerta.getY() == -52) {
            double xPortero = tiroPuerta.getX();
        
            if (tiroPuerta.getX() > (Constants.LARGO_ARCO / 2)) {
                xPortero = (Constants.LARGO_ARCO / 2);
            }
            if (xPortero < -(Constants.LARGO_ARCO / 2)) {
                xPortero = -(Constants.LARGO_ARCO / 2);
            }
            porteroPos = new Position(xPortero, -52);
        } else if ((tiroPuerta != null) && tiroPuerta.isInsideGameField(0)) {
            porteroPos = tiroPuerta;
        }      
        
        return porteroPos;        
    }
    
    public static boolean bolaQuieta(Position oldBall, Position bola) {
        return oldBall.equals(bola);
    }
    
    /**
     * lista de mas cercanos a la bola
     * @param sp
     * @param miEquipo
     * @return 
     */
    public static int[] getMasCercanosAPuntoList(GameSituations sp, Position bolaFut, boolean miEquipo){
        int dev1[]=getOrdenarEquipoMasCercano(miEquipo ? sp.myPlayers() : sp.rivalPlayers(), bolaFut);
        return dev1;        
    }
    
    public static int getMasCercanoAPunto(Position[] pos, Position punto, Position[] exclList) {
        double dist = 999;
        int max = 0;
        for(int i=0; i < pos.length; i++) {
            //exlList[i], si es != null el jugador i ya tiene un movimiento asignado
            if ((exclList == null || exclList[i] == null) && (punto.distance(pos[i]) < dist)) {
               dist = punto.distance(pos[i]); 
               max = i;
            }
        }
        return max;        
    }
    
    
    
    public static Position[] quitarFueraJuego(Position[] pos, Position[] riv, 
                                              PlayerDetail[] rivDet, Position bola, int portero, Position oldBola) {
        int ultimoDefRiv = ultimoDefensorRival(riv, rivDet);
        int delRivAdelan = delanteroRivalMasAdelantado(riv);
        
        for(int i=0; i < pos.length; i++) { 
           if ((pos[i] != null) && (i != portero)) {
                double x = pos[i].getX();
                double y = pos[i].getY();
                
                //ponerse detras del ultimo defensa contrario
                if ((y > 0) && (y > riv[ultimoDefRiv].getY()) && ((bola.getY()) < y) && (oldBola.getY() > bola.getY())) {
                   y = riv[ultimoDefRiv].getY() - 2;
                } 
                
                //ponerse delante del delantero contrario mas adelantado
                if ((riv[delRivAdelan].getY() < 0) && (y < riv[delRivAdelan].getY()) && (bola.getY() > y) && 
                    (bola.getY() < riv[delRivAdelan].getY())) {
                   y = riv[delRivAdelan].getY() + 1;
                }

                pos[i] = new Position(x,y);
           }
           
        }
        
        return pos;
    }
    
    public static boolean companyeroCerca(Position[] movPos, Position p) {
        for (int i=0; i<movPos.length; i++) {
            if ((movPos[i] != null) && (p.distance(movPos[i]) < 5)) {
                return true;
            }
        }
        return false;
    }
    
    public static double distCompanyeroMasCercano(Position[] movPos, int x) {
        double min = 1000;
        for (int i=0; i<movPos.length; i++) {
            if ((i != x) && (movPos[i] != null)) {
                double aux = movPos[x].distance(movPos[i]);
                if (aux < min) {
                    min = aux;
                }
            }
        }
        return min;
    }
    
    
    public static boolean jugadorEstaCerca(Position pos, Position bola, double dist) {
        return (pos.distance(bola) < dist);
    }
    
    public static int delanteroMioMasAdelantado(Position[] p) {
        int idx = 1;
        double maxY = -1000;
        for(int i=0; i < p.length; i++) {
          if (p[i].getY() > maxY) {
              idx = i;
              maxY = p[i].getY();
          }
        }
        return idx;
    }
    
    public static int delanteroRivalMasAdelantado(Position[] p) {
        int idx = 1;
        double minY = 0;
        for(int i=0; i < p.length; i++) {
          if (p[i].getY() < minY) {
              idx = i;
              minY = p[i].getY();
          }
        }
        return idx;
    }
    
    public static Position buscaPase(GameSituations sp) {
        List<Position> candidatos = new ArrayList<>();
        
        Position result = Constants.penalSup;        
        double desde = Constants.penalSup.getY();
        double hasta = sp.ballPosition().getY() + 30;
        
        if (sp.ballPosition().getY() < -25) {
           desde = sp.ballPosition().getY() + 45;
           hasta = sp.ballPosition().getY() + 30;
           result = new Position(-25,0);
        }
        
        if (sp.ballPosition().getY() < Constants.penalSup.getY()) {
            
            if (sp.ballPosition().getY() > -5) {
               int[] masAdelantados = Utils.getMasCercanosAPuntoList(sp, Constants.centroArcoSup, false);
                for (int i=0; i < 2; i++) {
                    if ((sp.myPlayers()[masAdelantados[i]].getY() > sp.ballPosition().getY()) &&
                        (sp.ballPosition().distance(sp.myPlayers()[masAdelantados[i]]) > 15)) {
                        candidatos.add(sp.myPlayers()[masAdelantados[i]]);    
                    }
                } 
            }
            for (double y = desde; y>=hasta; y-=2) {
                for (double x=-20; x<20; x+=4) {            
                    Position aux = new Position(x,y);
                    boolean anyade = true;
                    for (int i=0; i< 11; i++) {
                        if (jugadorEstaCerca(sp.rivalPlayers()[i],aux,10) || 
                            !jugadorEstaCerca(sp.myPlayers()[i],aux,10)) {
                            anyade = false;
                            break;
                        }                                    
                    }
                    if (anyade) {
                        candidatos.add(aux);    
                    }
                }
            }
        } else {
           candidatos.add(Constants.penalSup);     
        }
        double minDist = sp.ballPosition().distance(result);
        for (Position pos : candidatos) {
            if (sp.myPlayers()[getMasCercanoAPunto(sp.myPlayers(), pos, null)].distance(pos) < minDist) {
               minDist = sp.myPlayers()[getMasCercanoAPunto(sp.myPlayers(), pos, null)].distance(pos);
               result = pos;
            }
        }
       
        return result;
    }
    
    public static Position puntoPerpen(Position bola, Position otro) {
        Position result = Constants.centroCampoJuego;
        try {
            double m = -1 / ((bola.getY() - otro.getY()) / (bola.getX() - otro.getX()));
            double a = bola.getY() - (m * bola.getX());        
            result = new Position((bola.getY()+1-a)/m ,bola.getY()+1);
        } catch (Exception ex) {
            //nada
        }
        
        return result;
    }
    
    public static double calcularFuerzaTiro(Position bola, PlayerDetail[] det, Position to, int yo) {
        double obj = bola.distance(to);
        double dist = 1000;
        double result = 1;
        
        
        while ((result > 0.3) && (dist > obj)) {
            dist = 8.3 * Math.pow(1.2 + (det[yo].getPower() * 1.2), 2d) * result;            
            if (dist > obj) {
                result -= 0.05;
            }
        }
                 
        return result;
    }
    
    public static boolean haciaPorteriaRival(Position oldBall, Position bola) {
        return (oldBall.getY() < bola.getY());
    }
    
    public static boolean enAreaInfluenciaPortero(Position p) {
             
            return (Math.abs(p.getX()) <= (Constants.LARGO_AREA_GRANDE / 3) && (p.getY() < ((-Constants.LARGO_CAMPO_JUEGO / 2) + Constants.ANCHO_AREA_GRANDE) + 10));
    }
    
    public static Position[] getBolaFuturaJug(Position oldBall, GameSituations sp, Position[] movPos) {
        int minIter = 101;
        int minJug = 10;
        Position posMin = null; 
        HashMap aux = null;
        for (int i=0; i<sp.myPlayers().length; i++) {
            aux = prediccionBolaJug(oldBall, sp, i);
            if (aux != null) {
                if (((Integer)aux.get("iteraciones")).intValue() < minIter) {
                    minIter = ((Integer)aux.get("iteraciones")).intValue();
                    posMin = (Position)aux.get("posicion");
                    minJug = i;
                } 
            }            
        }
        if (posMin != null) {
            movPos[minJug] = posMin; 
            if (aux != null) {
                for (int i = 0; i<sp.myPlayers().length; i++) {
                    if ((minJug != i)) {
                       double distCorrer = sp.distanceTotal(i, minIter); //Distancia recorrer en iter iteracciones
                       double dist = sp.myPlayers()[i].distance(posMin); //Distancia entre el jugador y el balon 
                       if (distCorrer > dist) {
                          if ((i != 0)  || 
                              (enAreaInfluenciaPortero(posMin)) ||
                              (distCompanyeroMasCercano(sp.myPlayers(),i) > 15)) { 
                            movPos[i] = posMin;  
                          }  
                       }
                    }
                }
            }
            //otro jugador
            int jug2 = getMasCercanoAPunto(sp.myPlayers(), posMin, movPos);
            if (jug2 != 0)  {
                movPos[jug2] = posMin; 
            }
            
            
        }   
        
        return movPos;
        
    }
       
    
    /************** privados **********/
    
    
    
    private static int ultimoDefensorRival(Position[] p, PlayerDetail[] det) {
        int idx = 1;
        double maxY = 0;
        for(int i=0; i < p.length; i++) {
          if ((!det[i].isGoalKeeper()) && (p[i].getY() > maxY)) {
              idx = i;
              maxY = p[i].getY();
          }
        }
        return idx;
    }
        
    private static boolean estaEnArray(int vector[], int num){
        if (vector==null) return false;
        for (int n=0;n<vector.length;n++) {
            if (vector[n]==num) {
                return true;
            }
        }
        return false;
    }
    
    
    private static int[] getOrdenarEquipoMasCercano(Position[] lista, Position punto){
        int[] result = new int[11];
        for (int i=0; i< 11; i++) {
            result[i] = -1;
        }
        int[] masCercanoIdx = getInfoMasCercano(lista, punto, null, false);
        result[0] = masCercanoIdx[0];
        for (int i=1; i<11; i++) {
            masCercanoIdx = getInfoMasCercano(lista, punto, result, false);
            result[i] = masCercanoIdx[0];
        }
        return result;
    }
    
    private static int[] getInfoMasCercano(Position[] lista, Position punto, int excluir[], boolean soloDelante){
        int dev[]={-1,999};
        int nDis;
        for (int n=0;n<lista.length;n++){
            if (!estaEnArray(excluir, n)){
                if (!soloDelante || lista[n].getX() > punto.getX()){
                    nDis=(int) lista[n].distance(punto);
                    if (nDis<dev[1]){
                        dev[0]=n;
                        dev[1]=nDis;
                    }
                }
            }
        }
        return dev;
    }
        
    private static HashMap prediccionBolaJug(Position oldBall, GameSituations sp, int jug) {
        boolean found = false;
        Position pos = sp.ballPosition();
        int iter = 0;
        
        
        if ((oldBall.getX() == sp.ballPosition().getX() && oldBall.getY() == sp.ballPosition().getY()) ||
             sp.isRivalStarts() || sp.isStarts()) {
            return null; 
        }
        
        while (!found && iter < 100) {
            double[] trayectoria = sp.getTrajectory(++iter); 
            
            if (!(new Position(trayectoria[0], trayectoria[1])).isInsideGameField(0)) {
                iter = 101;
            } else  if ((trayectoria[2] <= Constants.ALTO_ARCO)) {
                pos = new Position(trayectoria[0], trayectoria[1]);
                
                if (trayectoria[2] <= (sp.myPlayersDetail()[jug].isGoalKeeper() ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)) {
                    double distCorrer = sp.distanceTotal(jug, iter); //Distancia recorrer en iter iteracciones
                    double dist = sp.myPlayers()[jug].distance(pos); //Distancia entre el jugador y el balon
                    if (distCorrer >= dist) {
                        found = true;                            
                    }
                }
            }            
        }
                
        if (!found) {
            return null;
        } else {
            HashMap resul = new HashMap();
            resul.put("iteraciones", new Integer(iter));
            resul.put("posicion", pos);
            return resul;
        }
        
    }
    
    private static Position prediccionBolaPorteria(Position oldBall, GameSituations sp) {
        boolean found = false;
        Position pos = sp.ballPosition();
        int iter = 0;
        
        if ((oldBall.getX() == sp.ballPosition().getX() && oldBall.getY() == sp.ballPosition().getY()) ||
             haciaPorteriaRival(oldBall,sp.ballPosition()) || sp.isRivalStarts() || sp.isStarts()) {
            return null; 
        }
        
        while (!found && iter < 100) {
            double[] trayectoria = sp.getTrajectory(++iter); 
            
            if (new Position(trayectoria[0], trayectoria[1]).equals(pos)) {
                //pelota parada. Si está cerca hay que ir a por ella
                found = true;
            } else {
                if (trayectoria[1] <= -52) {                    
                    pos = (new Position(trayectoria[0], -52));                    
                    found = true;                            
                }
            }            
        }
        if (!found) {
            return null;
        }
        return pos;
    }
    
    
	
}
