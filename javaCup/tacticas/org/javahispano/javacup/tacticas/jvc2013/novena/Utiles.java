
package org.javahispano.javacup.tacticas.jvc2013.novena;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author yoemny
 */
public final class Utiles {

    public static PosicionBalon ultimoEnCola(DatosGlobales datos){
        Queue<PosicionBalon> trayectoria = datos.getTrayectoriaBalon();
        if (trayectoria.isEmpty())
            return null;
        else
        {
            PosicionBalon lastPosition = null;
            Iterator<PosicionBalon> it = trayectoria.iterator();

            while (it.hasNext()){
                lastPosition =  (PosicionBalon)it.next();
            }
            return lastPosition;
        }
    }

    public static PosicionBalon ultimoEnCola(Queue<PosicionBalon> trayectoria){
        if (trayectoria.isEmpty())
            return null;
        else
        {
            PosicionBalon lastPosition = null;
            Iterator<PosicionBalon> it = trayectoria.iterator();

            while (it.hasNext()){
                lastPosition =  (PosicionBalon)it.next();
            }
            return lastPosition;
        }
    }

    public static  PosicionBalon puntoDeSalidaBalon(PosicionBalon posicionDentro, PosicionBalon posicionFuera){

        double posicionDx = posicionDentro.getPosicion().getX();
        double posicionDy = posicionDentro.getPosicion().getY();
        double posicionDz = posicionDentro.getAltura();

        double posicionFx = posicionFuera.getPosicion().getX();
        double posicionFy = posicionFuera.getPosicion().getY();
        double posicionFz = posicionFuera.getAltura();

        double dX = posicionFx-posicionDx;
        double dY = posicionFy-posicionDy;
        double dZ = posicionFz-posicionDz;

        if (Math.abs(posicionFy) > Constants.LARGO_CAMPO_JUEGO / 2) {//si sale el ballPosition detras del portico
            double posY = 0;
            if (posicionFy < 0) {
                posY = -Constants.LARGO_CAMPO_JUEGO / 2;
            } else {
                posY = Constants.LARGO_CAMPO_JUEGO / 2;
            }
            double posX = (dX / dY) * (posY - posicionFy) + posicionFx;//proyeccion x de la trayectoria del ballPosition en la linea de meta
            double posZ = (dZ / dY) * (posY - posicionFy) + posicionFz;//proyeccion z de la trayectoria del ballPosition en la linea de meta
            return new PosicionBalon(new Position(posX, posY), posZ);

        }else{
            double posX = 0;
            if (posicionFx < 0) {
                posX = -Constants.ANCHO_CAMPO_JUEGO / 2;
            } else {
                posX = Constants.ANCHO_CAMPO_JUEGO / 2;
            }
            double posY = (dY / dX) * (posX - posicionFx) + posicionFy;//proyeccion y de la trayectoria del ballPosition en la linea de lateral
            double posZ = (dZ / dX) * (posX - posicionFx) + posicionFz;//proyeccion z de la trayectoria del ballPosition en la linea de lateral
            return new PosicionBalon(new Position(posX, posY), posZ);

        }

    }

    public static boolean rivalDelanteDelBalon( GameSituations sp ){
        Position[] jugadoresRivales = sp.rivalPlayers();
        for (int i=0;i<jugadoresRivales.length;i++)
            if ( jugadoresRivales[i].getY() < sp.ballPosition().getY() )
                return true;
        return false;
    }

    public static int rivalMasCercaDeMiPorteria( GameSituations sp ){
        Position[] posicionesRival = sp.rivalPlayers();
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( Constants.centroArcoInf.distance(posicionesRival[i]) < distMin ){
                index = i;
                distMin = Constants.centroArcoInf.distance(posicionesRival[i]);
            }

        return index;
    }

    public static int rivalMasCercaDeBalon( GameSituations sp ){
        Position[] posicionesRival = sp.rivalPlayers();
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( sp.ballPosition().distance(posicionesRival[i]) < distMin ){
                index = i;
                distMin = sp.ballPosition().distance(posicionesRival[i]);
            }

        return index;
    }
    
    public static boolean rivalDelanteDelBalonSinMarcaEnZonaDefensiva( GameSituations sp, DatosGlobales datos, Zona zd){
        Position[] jugadoresRivales = sp.rivalPlayers();
        for (int i=0;i<jugadoresRivales.length;i++)
            if ( zd.estaEnLaZona(jugadoresRivales[i]) && jugadoresRivales[i].getY() < sp.ballPosition().getY() && estaSinMarcaElRival(datos, i) )
                return true;
        return false;
    }

    public static boolean rivalMarcadoPorOtroDefensa(GameSituations sp, DatosGlobales datos, int rivalPlayer, int defensePlayer){
        int[] marcajes = datos.getMarcajes();
        int[] defensas = DatosUtiles.indicesDefensas();
        for (int i=0 ; i<defensas.length ; i++)
            if ( defensas[i] != defensePlayer && marcajes[defensas[i]] == rivalPlayer ) //&&
           //   sp.rivalPlayers()[rivalPlayer].distance(sp.myPlayers()[defensas[i]]) <= Constants.JUGADORES_SEPARACION * 2)
                    return true;
        return false;
    }

    public static boolean rivalMarcadoPorDefensa(GameSituations sp, DatosGlobales datos, int rivalPlayer){
        int[] marcajes = datos.getMarcajes();
        int[] defensas = DatosUtiles.indicesDefensas();
        for (int i=0 ; i<defensas.length ; i++)
            if (marcajes[defensas[i]] == rivalPlayer && 
                sp.rivalPlayers()[rivalPlayer].distance(sp.myPlayers()[defensas[i]]) <= Constants.JUGADORES_SEPARACION * 2)
                return true;
        return false;
    }

    public static int indiceDefensaMasCercaRivalMarcado(GameSituations sp, DatosGlobales datos, int indiceRivalMarcado){
        int[] marcajes = datos.getMarcajes();
        Position posicionRivalMarcado = sp.rivalPlayers()[indiceRivalMarcado];
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<DatosUtiles.defensas.length ; i++){
            int indexDefense = DatosUtiles.defensas[i];
            if ( DatosUtiles.getZonaDefensiva(indexDefense).estaEnLaZona(posicionRivalMarcado) &&
                    sp.myPlayers()[indexDefense].distance(posicionRivalMarcado) < distMin && marcajes[indexDefense] == indiceRivalMarcado){
                index = indexDefense;
                distMin = sp.myPlayers()[indexDefense].distance(posicionRivalMarcado);
            }

        }

        return index;
    }

    public static int indiceDefensaSinMarcaMasCercaRivalMarcado(GameSituations sp, DatosGlobales datos, int indiceRivalMarcado){
        Position posicionRivalMarcado = sp.rivalPlayers()[indiceRivalMarcado];
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<DatosUtiles.defensas.length ; i++){
            int indexDefense = DatosUtiles.defensas[i];
            if ( !datos.tieneMarca(indexDefense) && sp.myPlayers()[indexDefense].distance(posicionRivalMarcado) < distMin ){
                index = indexDefense;
                distMin = sp.myPlayers()[indexDefense].distance(posicionRivalMarcado);
            }

        }

        return index;
    }
    
    public static boolean defensaCercaRivalMarcado(Position posicionDefensa, Position posicionRivalMarcado){
        if (posicionRivalMarcado.distance(posicionDefensa) <= Constants.JUGADORES_SEPARACION * 2)
            return true;

        return false;
    }

    public static boolean existePaseHaciaRivalMarcado( DatosGlobales datos, Position posicionRivalMarcado ){
         Queue<PosicionBalon> trayectoriaBalon = datos.getTrayectoriaBalon();
        if (trayectoriaBalon.isEmpty())
            return false;
        else{
            Iterator<PosicionBalon> it = trayectoriaBalon.iterator();
            while (it.hasNext()){
                PosicionBalon aBallPosition =  (PosicionBalon)it.next();
                if (posicionRivalMarcado.distance(aBallPosition.getPosicion()) <= Constants.DISTANCIA_CONTROL_BALON * 2 &&
                        aBallPosition.getAltura() <= Constants.ALTURA_CONTROL_BALON)
                    return true;
            }
        }

        return false;
    }

    public static boolean  existeRivalEnZonaDefensiva(GameSituations sp, Zona zd ){
        Position[] posicionesRivales = sp.rivalPlayers();
        for (int i=0 ; i<posicionesRivales.length ; i++)
            if (zd.estaEnLaZona(posicionesRivales[i]))
                return true;
        return false;
    }

    public static boolean  existeRivalSinMarcaEnZonaDefensiva(GameSituations sp, DatosGlobales datos, Zona zd ){
        Position[] posicionesRival = sp.rivalPlayers();
        for (int i=0 ; i<posicionesRival.length ; i++)
            if (estaSinMarcaElRival(datos, i) && zd.estaEnLaZona(posicionesRival[i]))
                return true;
        return false;
    }

    public static boolean  marcaFueraZonasDefensivas(GameSituations sp, DatosGlobales datos, Position posMarca ){
    	for (int i=0 ; i<DatosUtiles.defensas.length ; i++){
            int indiceDefensa = DatosUtiles.defensas[i];
            Zona zd = DatosUtiles.getZonaDefensiva(indiceDefensa);
            if ( zd.estaEnLaZona(posMarca) )
            	return false;
    	}
        return true;
    }

    public static boolean  marcaFueraZonaDefensiva(GameSituations sp, DatosGlobales datos, int indiceDefensa ){

    	Zona zd = DatosUtiles.getZonaDefensiva(indiceDefensa);
    	int indiceMarca = datos.getMarca(indiceDefensa);
    	Position posMarca = sp.rivalPlayers()[indiceMarca];
        if ( zd.estaEnLaZona( posMarca) )
            return false;
        else
        	return true;
    }

    public static boolean estaSinMarcaElRival(DatosGlobales datos, int indiceRival){
        int[] marcajes = datos.getMarcajes();
        for (int i=0 ; i<marcajes.length ; i++)
            if (marcajes[i] == indiceRival)
                return false;

        return true;
    }

    public static int cantidadRivalesEnZonaDefensiva(GameSituations sp, Zona dz){
        Position[] posicionesRivales = sp.rivalPlayers();
        int count = 0;
        for (int i=0 ; i<posicionesRivales.length ; i++)
            if (dz.estaEnLaZona(posicionesRivales[i]))
                count++;
        return count;
    }

    public static int cantidadRivalesSinMarcaEnZonaDefensiva(GameSituations sp, DatosGlobales datos, Zona dz){
        Position[] posicionesRival = sp.rivalPlayers();
        int count = 0;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if (estaSinMarcaElRival(datos, i) && dz.estaEnLaZona(posicionesRival[i]))
                count++;
        return count;
    }

    public static int rivalSinMarcaMasCercaDelDefensaEnZonaDefensiva(GameSituations sp, DatosGlobales datos, int indiceDefensa, Zona dz ){
        Position[] posicionesRival = sp.rivalPlayers();
        Position posicionDefensa = sp.myPlayers()[indiceDefensa];
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( dz.estaEnLaZona(posicionesRival[i]) && posicionDefensa.distance(posicionesRival[i]) < distMin && estaSinMarcaElRival(datos, i) ){
                index = i;
                distMin = posicionDefensa.distance(posicionesRival[i]);
            }

        return index;
    }

    public static boolean existeDefensaSinMarca(GameSituations sp, DatosGlobales datos){
    	for (int i = 0; i < DatosUtiles.indicesDefensas().length ; i++ ){
        	int indiceDefensa = DatosUtiles.indicesDefensas()[i];
        	if ( !datos.tieneMarca(indiceDefensa) )
        		return true;
        	
    	}
    	return false;
    }
    
    public static boolean existeDefensaSinMarca(GameSituations sp, DatosGlobales datos, Position posRival){
    	for (int i = 0; i < DatosUtiles.indicesDefensas().length ; i++ ){
        	int indiceDefensa = DatosUtiles.indicesDefensas()[i];
        	Zona zd = DatosUtiles.getZonaDefensiva(indiceDefensa);
        	if ( zd.estaEnLaZona(posRival) && !datos.tieneMarca(indiceDefensa) )
        		return true;
        	
    	}
    	return false;
    }
    
    public static int rivalSinMarcaMasCercaDelDefensa(GameSituations sp, DatosGlobales datos, int indiceDefensa){
        Position[] posicionesRival = sp.rivalPlayers();
        Position posicionDefensa = sp.myPlayers()[indiceDefensa];
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( posicionDefensa.distance(posicionesRival[i]) < distMin && estaSinMarcaElRival(datos, i) ){
                index = i;
                distMin = posicionDefensa.distance(posicionesRival[i]);
            }

        return index;
    }

    public static int rivalMasCercaDelJugador(GameSituations sp, int indiceJugador ){
        Position posicionJugador = sp.myPlayers()[indiceJugador];
        Position[] posicionesRival = sp.rivalPlayers();
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( posicionJugador.distance(posicionesRival[i]) < distMin ){
                index = i;
                distMin = posicionJugador.distance(posicionesRival[i]);
            }

        return index;
    }

    public static int rivalMasCercaDePosicion(GameSituations sp, Position pos ){
        
        Position[] posicionesRival = sp.rivalPlayers();
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( pos.distance(posicionesRival[i]) < distMin ){
                index = i;
                distMin = pos.distance(posicionesRival[i]);
            }

        return index;
    }
    
    
    public static int rivalDetrasDefensaMasCercaDeLaPuertaEnZonaDefensiva(GameSituations sp, int indiceDefensa ){
        Position posicionDefensa = sp.myPlayers()[indiceDefensa];
        Position[] posicionesRival = sp.rivalPlayers();
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( posicionesRival[i].getY() < posicionDefensa.getY() && Constants.penalInf.distance(posicionesRival[i]) < distMin ){
                index = i;
                distMin = posicionDefensa.distance(posicionesRival[i]);
            }

        return index;
    }

    public static int rivalMasCercaDeLaPuertaEnZonaDefensiva( GameSituations sp, Zona mydefeDefensiveZone){
        Position[] posicionesRival = sp.rivalPlayers();
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( Constants.centroArcoInf.distance(posicionesRival[i]) < distMin && mydefeDefensiveZone.estaEnLaZona(posicionesRival[i])){
                index = i;
                distMin = Constants.centroArcoInf.distance(posicionesRival[i]);
            }

        return index;
    }

    public static int rivalSinMarcaMasCercaDeLaPuertaEnZonaDefensiva( GameSituations sp, DatosGlobales datos, Zona zonaDefensiva ){
        Position[] posicionesRival = sp.rivalPlayers();
        int index = 0;
        double distMin = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( estaSinMarcaElRival(datos, i) && Constants.centroArcoInf.distance(posicionesRival[i]) < distMin && zonaDefensiva.estaEnLaZona(posicionesRival[i])){
                index = i;
                distMin = Constants.centroArcoInf.distance(posicionesRival[i]);
            }

        return index;
    }

    public static boolean existeMasDeUnRivalEnZonaDefensiva( GameSituations sp, Zona zonaDefensiva ){
        Position[] posicionesRival = sp.rivalPlayers();
        int count = 0;
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( zonaDefensiva.estaEnLaZona(posicionesRival[i])){
                count++;
                if ( count>1 )
                    return true;
            }
        return false;
    }

    public static int rivalMasAdelantadoEnLaZonaDefensiva( GameSituations sp, Zona zonaDefensiva){
        Position[] posicionesRival = sp.rivalPlayers();
        int indexRival = 0;
        double distLineEnd = Double.MAX_VALUE;
        for (int i=0 ; i<posicionesRival.length ; i++){
            double dY = Constants.LARGO_CAMPO_JUEGO/2 - Math.abs(posicionesRival[i].getY());
            if ( dY  < distLineEnd && zonaDefensiva.estaEnLaZona(posicionesRival[i])){
                indexRival = i;
                distLineEnd = dY;
            }

        }

        return indexRival;
    }

    public static Position posicionMarcaPorDelante(GameSituations sp, int indiceRival ){
        Position posicionRival = sp.rivalPlayers()[indiceRival];
        Position posicionBalon = sp.ballPosition();
        double angle = posicionRival.angle(posicionBalon);
        return posicionRival.moveAngle(angle, 0.51);
    }

    public static Queue<Position> generarPosibleTrayectoriaDelRival(GameSituations sp, DatosGlobales datos, int indiceJugadorRival){
        Queue<Position> trajectory = new LinkedList<Position>();
        Position posicionRival = sp.rivalPlayers()[indiceJugadorRival];
        trajectory.add(posicionRival);
        double angle = datos.getAnguloMovimientoRival(indiceJugadorRival);
        int count = 1;
        boolean out = false;
        if (angle == Double.MAX_VALUE)
            out = true;
        

        while (!out && count <= 20 ){
            Position newPosition = posicionRival.moveAngle(angle, 0.5 * count);
            if (newPosition.isInsideGameField(0))
                out = true;
            else{
                trajectory.add(newPosition);
                count++;
            }
        }

        return trajectory;
    }

    public static Position posicionEnLaTrayectoriaMasCercana(Position posicionDefensa, Queue<Position> trayectoriaGenerada){
        Position p = null;
        double distMin = Double.MAX_VALUE;

        Iterator<Position> it = trayectoriaGenerada.iterator();
            while (it.hasNext()){
                Position position =  (Position)it.next();
                if ( posicionDefensa.distance(position) < distMin ){
                    distMin = posicionDefensa.distance(position);
                    p = position;
                }
            }
        return p;
    }

    public static Position posicionMarcaPorDetras(GameSituations sp, DatosGlobales datos, int indiceDefensa, int indiceRival ){
        Position newRivalPosition = sp.rivalPlayers()[indiceRival];
        if (datos.getAnguloMovimientoRival(indiceRival) != Double.MAX_VALUE){
            // buscar el punto mas cercano de la posible trayectoria del rival
            //newRivalPosition = rivalPosition.moveAngle(aRivals[rivalIndex], vRivals[rivalIndex]);
            Queue<Position> trayectoryRival = generarPosibleTrayectoriaDelRival(sp, datos, indiceRival);
            newRivalPosition = posicionEnLaTrayectoriaMasCercana(sp.myPlayers()[indiceDefensa], trayectoryRival);
            trayectoryRival.clear();

        }
        double angle = newRivalPosition.angle(Constants.centroArcoInf);

        return newRivalPosition.moveAngle(angle, Constants.JUGADORES_SEPARACION * 2 );
    }

    public static Position posicionParaBloquearPase(GameSituations sp, DatosGlobales datos, int indiceDefensa, int indiceRival){
        double angulo;
        Queue<PosicionBalon> trayectoriaBalon = datos.getTrayectoriaBalon();
        if (trayectoriaBalon.isEmpty())
            angulo = sp.rivalPlayers()[indiceRival].angle(sp.ballPosition());
        else
            angulo = sp.rivalPlayers()[indiceRival].angle(trayectoriaBalon.peek().getPosicion());

        Position newRivalPosition = sp.rivalPlayers()[indiceRival];
        if ( datos.getAnguloMovimientoRival(indiceRival) != Double.MAX_VALUE){
            // buscar el punto mas cercano de la posible trayectoria del rival
            Queue<Position> trayectoryRival = generarPosibleTrayectoriaDelRival(sp, datos, indiceRival);
            newRivalPosition = posicionEnLaTrayectoriaMasCercana(sp.myPlayers()[indiceDefensa], trayectoryRival);
            trayectoryRival.clear();
        }

        return newRivalPosition.moveAngle(angulo, Constants.JUGADORES_SEPARACION/2);
    }

    public static boolean puedeRematarPortero(int[] rematadores){
        for(int i=0;i<rematadores.length;i++)
            if (rematadores[i] == DatosUtiles.indicePortero())
                return true;
        return false;
    }

    public static boolean puedeRematarElJugador(GameSituations sp, int indexPlayer){
        int[] rematadores = sp.canKick();
        for(int i=0;i<rematadores.length;i++)
            if ( rematadores[i] == indexPlayer)
                return true;
        return false;
    }
    public static boolean dejoRivalRematar(GameSituations sp){
    	
    	if ( sp.rivalCanKick().length == 0 ){
    		return false;
    	}else if ( sp.rivalCanKick().length == 1 && sp.ballPosition().getY() > 0 - (Constants.LARGO_CAMPO_JUEGO / 4) )
    		return true;
    	else 
    		return false;
    	
    }
    
    public static Position posicionEnLaTrayectoriaMasCercaDelJugador(GameSituations sp, DatosGlobales datos, int indiceJugador, boolean esPortero){
        Position p = null;
        Position posicionJugador = sp.myPlayers()[indiceJugador];
        double distMin = Double.MAX_VALUE;
        Queue<PosicionBalon> trayectoriaBalon = datos.getTrayectoriaBalon();
        Iterator<PosicionBalon> it = trayectoriaBalon.iterator();
            while (it.hasNext()){
                PosicionBalon aBallPosition =  (PosicionBalon)it.next();
                if ( esPortero )
                    if ( DatosUtiles.estaEnMiAreaChica(aBallPosition.getPosicion()) && posicionJugador.distance(aBallPosition.getPosicion()) < distMin && aBallPosition.getAltura() <= Constants.ALTO_ARCO){
                        distMin = posicionJugador.distance(aBallPosition.getPosicion());
                        p = aBallPosition.getPosicion();
                    }
                else
                    if (posicionJugador.distance(aBallPosition.getPosicion()) < distMin && aBallPosition.getAltura() <= Constants.ALTURA_CONTROL_BALON){
                        distMin = posicionJugador.distance(aBallPosition.getPosicion());
                        p = aBallPosition.getPosicion();
                    }

            }

        return p;
    }

    public static boolean disparoAPuerta(DatosGlobales datos){
        PosicionBalon pos = datos.getPuntoSalida();
        if ( pos != null){
            
            if (pos.getPosicion().getY() == Constants.centroArcoInf.getY() &&
                pos.getPosicion().getX() >= Constants.posteIzqArcoInf.getX() &&
                pos.getPosicion().getX() <= Constants.posteDerArcoInf.getX() )
                
                return true;

        }else
            return false;

        return false;
    }

    public static boolean rivalDelanteDelBalonSinMarca(GameSituations sp, DatosGlobales datos){
        Position[] jugadoresRivales = sp.rivalPlayers();
        for (int i=0;i<jugadoresRivales.length;i++)
            if ( jugadoresRivales[i].getY() < sp.ballPosition().getY() && estaSinMarcaElRival(datos,i) )
                return true;
        return false;
    }

    public static boolean estaElBalonEnZonaDefensiva(GameSituations sp, int indiceJugador){
        if (DatosUtiles.getZonaDefensiva(indiceJugador).estaEnLaZona(sp.ballPosition()))
            return true;
        else
            return false;
    }

    public static boolean estaElBalonEnZonaTiroAmplia(GameSituations sp){
    	Zona zp1 = DatosUtiles.obtenerZonaDisparo1();
    	Zona zp2 = DatosUtiles.obtenerZonaDisparo2();
        if (zp1.estaEnLaZona(sp.ballPosition())  || zp2.estaEnLaZona(sp.ballPosition()))
            return true;
        else
            return false;
    }
    
    public static boolean estaJugadorEnZonaTiroAmplia(GameSituations sp, int indiceJugador){
    	Zona zp1 = DatosUtiles.obtenerZonaDisparo1();
    	Zona zp2 = DatosUtiles.obtenerZonaDisparo2();
        if ( zp1.estaEnLaZona(sp.myPlayers()[indiceJugador]) || zp2.estaEnLaZona(sp.myPlayers()[indiceJugador]) )
            return true;
        else
            return false;
    }
    
    public static boolean estaElBalonEnZonaTiroChica(GameSituations sp){
    	Zona zp1 = DatosUtiles.obtenerZonaDisparo1();
    	
        if ( zp1.estaEnLaZona(sp.ballPosition()) )
            return true;
        else
            return false;
    }

    public static boolean estaEnZonaTiroChica(Position pos){
    	Zona zp1 = DatosUtiles.obtenerZonaDisparo1();
    	
        if ( zp1.estaEnLaZona(pos) )
            return true;
        else
            return false;
    }

    public static boolean estaJugadorEnZonaTiroChica(GameSituations sp, int indiceJugador){
    	Zona zp1 = DatosUtiles.obtenerZonaDisparo1();
    	
        if ( zp1.estaEnLaZona(sp.myPlayers()[indiceJugador])  )
            return true;
        else
            return false;
    }

    public static boolean balonCercaDelJugador(GameSituations sp, int indiceJugador){
        if (sp.myPlayers()[indiceJugador].distance(sp.ballPosition()) <= Constants.DISTANCIA_CONTROL_BALON * 5)
            return true;
        else
            return false;
    }

    public static boolean balonControladoPorAlgunRival(GameSituations sp){
        Position[] posicionesRival = sp.rivalPlayers();
        Position posicionBalon = sp.ballPosition();
        for (int i=0 ; i<posicionesRival.length ; i++)
            if ( posicionesRival[i].distance(posicionBalon) <= Constants.DISTANCIA_CONTROL_BALON )
            return true;
        return false;
    }

    public static boolean balonControladoPorRival(GameSituations sp, int indiceRival){
        Position posicionesRival = sp.rivalPlayers()[indiceRival];
        Position posicionBalon = sp.ballPosition();
        if ( posicionesRival.distance(posicionBalon) <= Constants.DISTANCIA_CONTROL_BALON )
            return true;
        return false;
    }

    public static boolean existeRivalCercaDelJugador(GameSituations sp, int indiceJugador){
        int indiceRivalMasCerca = rivalMasCercaDelJugador(sp, indiceJugador);
        if (sp.myPlayers()[indiceJugador].distance(sp.rivalPlayers()[indiceRivalMasCerca]) < 3)
            return true;
        else
            return false;
    }

    public static int jugadorMasCercaDelBalon(GameSituations sp, DatosGlobales datosGlobales){
        int indiceJugador = 0;
        if (!datosGlobales.isEnMoviemiento())
        	indiceJugador = masCercaDeTrayectoriaBalon(sp,datosGlobales);
        
        return indiceJugador;
    }
    
    public static int masCercaDelBalon(GameSituations sp){
    	 int indiceJugador = 0;
         double distanciaMinima = Double.MAX_VALUE;
         for (int indice = 0; indice < sp.myPlayers().length ; indice++){
             if ( sp.ballPosition().distance(sp.myPlayers()[indice]) < distanciaMinima){
                 indiceJugador = indice;
                 distanciaMinima = sp.ballPosition().distance(sp.myPlayers()[indice]);
             }
         }
         return indiceJugador;
    }
    
    public static boolean masCercaDeTrayectoriaBalon(GameSituations sp, DatosGlobales datosGlobales, int indiceJugador){
    	
    	int indice = -1;
    	int iteracionMin = Integer.MAX_VALUE;
    	for (int i=0;i<11;i++){
    		int iter = datosGlobales.getIteracionesRecuperar(i);
    		if (iter != -1 && iter < iteracionMin){
    			iteracionMin = iter;
    			indice = i;
    		}
    			
    	}
    	
    	if (indice == indiceJugador)
    		return true;
    	else
    		return false;
    	
    }

    public static boolean masCercaDeTrayectoriaBalonTodos(GameSituations sp, DatosGlobales datosGlobales, int indiceJugador){

    	int indice = -1;
    	int iteracionMin = Integer.MAX_VALUE;
    	for (int i=0;i<11;i++){
    		int iter = datosGlobales.getIteracionesRecuperar(i);
    		if (iter != -1 && iter < iteracionMin){
    			iteracionMin = iter;
    			indice = i;
    		}
    			
    	}
    	Position nuevaPosicion = datosGlobales.posicionEnTrayectoria(sp, datosGlobales.getIteracionesRecuperar(indiceJugador));
    	
    	if ( !DatosUtiles.estaEnMiArea(nuevaPosicion))
    		return false;
    	
    	double distRivalMasCerca = distanciaRivalMasCercano(sp, nuevaPosicion);
    	double distJugador = sp.myPlayers()[indiceJugador].distance(nuevaPosicion);
    	
    	if ( indice == indiceJugador && distJugador < distRivalMasCerca)
    		return true;
    	else
    		return false;
    	
    }
    
    public static int masCercaDeTrayectoriaBalon(GameSituations sp, DatosGlobales datosGlobales){
   	 	int indiceJugador = -1;
        double[] distanciaMinima = new double[11];
        Queue<PosicionBalon> t = datosGlobales.getTrayectoriaBalon();
        for (int indice = 0; indice < sp.myPlayers().length ; indice++){
            distanciaMinima[indice] = Double.MAX_VALUE;
            boolean esPortero = sp.myPlayersDetail()[indice].isGoalKeeper();
            double alturaControl = (esPortero) ? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON;
            
            Position pj = sp.myPlayers()[indice];
            Iterator<PosicionBalon> iter = t.iterator();
            while ( iter.hasNext() ){
            	PosicionBalon pb = iter.next();
            	if (pb.getAltura() < alturaControl && pj.distance(pb.getPosicion()) < distanciaMinima[indice] ){
            		distanciaMinima[indice] = pj.distance(pb.getPosicion());
            	}
            }
        }
        
        double distMin = Double.MAX_VALUE;
        for (int indice = 0; indice < sp.myPlayers().length ; indice++){
        	if (distanciaMinima[indice] < distMin){
        		distMin = distanciaMinima[indice];
        		indiceJugador = indice;
        	}
        }
        
        if (distMin == Double.MAX_VALUE)
        	return -1;
        else
        	return indiceJugador;
        
   }

    public static boolean esNuevaPosicion(Position posicionActual, Position posicionNueva){
    	if (posicionActual.equals(posicionNueva))
    		return false;
    	else
    		return true;
    }
    
    public static Position cornerMasCerca(GameSituations sp){
    	Position balon = sp.ballPosition();
    	if (balon.distance(Constants.cornerSupIzq) < balon.distance(Constants.cornerSupDer) )
    		return Constants.cornerSupIzq;
    	else
    		return Constants.cornerSupDer;
    }
    
    public static double anguloMovimientoBalonControlado360(GameSituations sp, DatosGlobales datos, int indiceJugador){
    	double anguloRadianes = sp.ballPosition().angle(Constants.penalSup);
    	
    	int rivalMasCerca = rivalMasCercaDelJugador(sp, indiceJugador);
    	Position posRivalMasCerca = sp.rivalPlayers()[rivalMasCerca];
    	Position posJugador = sp.myPlayers()[indiceJugador];
    	Position balon = sp.ballPosition();
    	
    	if( posRivalMasCerca.distance(posJugador) < 2 && posRivalMasCerca.distance(Constants.penalSup) < posJugador.distance(Constants.penalSup) )
    		anguloRadianes = balon.angle(cornerMasCerca(sp));

    	double anguloGrados = anguloRadianes * (180d / Math.PI);
        for( int i=0 ; i<180 ; i++ ){
            double anguloDerecha = anguloGrados - i;
            double anguloIzquierda = anguloGrados + i;
            
            if ( verificarAnguloConduccion(sp, datos, anguloIzquierda *(Math.PI/180d), indiceJugador) )
                return anguloIzquierda *(Math.PI/180d);
            if ( verificarAnguloConduccion(sp, datos, anguloDerecha *(Math.PI/180d), indiceJugador) )
                return anguloDerecha *(Math.PI/180d);

        }

        return Double.MAX_VALUE;
    }

    public static double anguloMovimientoBalonControlado180(GameSituations sp, DatosGlobales datos, int indiceJugador){
    	double anguloRadianes = sp.ballPosition().angle(Constants.penalSup);
    	
    	int rivalMasCerca = rivalMasCercaDelJugador(sp, indiceJugador);
    	Position posRivalMasCerca = sp.rivalPlayers()[rivalMasCerca];
    	Position posJugador = sp.myPlayers()[indiceJugador];
    	Position balon = sp.ballPosition();
    	
    	if( posRivalMasCerca.distance(posJugador) < 2 && posRivalMasCerca.distance(Constants.penalSup) < posJugador.distance(Constants.penalSup) )
    		anguloRadianes = balon.angle(cornerMasCerca(sp));
    	
        double anguloGrados = anguloRadianes * (180d / Math.PI);
        int grados = (int) Math.max(Math.abs(0-anguloGrados), Math.abs(180-anguloGrados));
        for( int i=0 ; i<grados ; i++ ){
            double anguloDerecha = anguloGrados - i;
            double anguloIzquierda = anguloGrados + i;

            if ( anguloIzquierda <=180 && verificarAnguloConduccion(sp, datos, anguloIzquierda *(Math.PI/180d), indiceJugador) )
                return anguloIzquierda *(Math.PI/180d);
            if ( anguloDerecha >=0 && verificarAnguloConduccion(sp, datos, anguloDerecha *(Math.PI/180d), indiceJugador) )
                return anguloDerecha *(Math.PI/180d);

        }

        return Double.MAX_VALUE;
    }

    private static boolean verificarAnguloConduccion(GameSituations sp, DatosGlobales datos, double angulo, int indiceJugador){
        double error = Constants.getErrorAngular(sp.myPlayersDetail()[indiceJugador].getPrecision() * sp.getMyPlayerEnergy(indiceJugador));
        double anguloError0 = angulo + (0 - error / 2) * Math.PI;
        double anguloError1 = angulo + (error - error / 2) * Math.PI;
        Position posicionBalon = sp.ballPosition();
        Aceleracion aceleracion = datos.getAceleraciones()[indiceJugador];
        
        Position p  = posicionBalon.moveAngle(angulo, 5.99);
        Position p0 = posicionBalon.moveAngle(anguloError0, 5.99);
        Position p1 = posicionBalon.moveAngle(anguloError1, 5.99);

        double dp 	= distanceTest(sp, indiceJugador, aceleracion, p, 10, false);
        double dp0 	= distanceTest(sp, indiceJugador, aceleracion, p0, 10, false);
        double dp1 	= distanceTest(sp, indiceJugador, aceleracion, p1, 10, false);

        p  = posicionBalon.moveAngle(angulo, dp + 0.99);
        p0  = posicionBalon.moveAngle(angulo, dp0 + 0.99);
        p1  = posicionBalon.moveAngle(angulo, dp1 + 0.99);
        
        
        		
        if (!p.isInsideGameField(-1) || existeRivalCercaDelBalonParaConducirlo(sp, p))
            return false;
        if (!p0.isInsideGameField(-1) || existeRivalCercaDelBalonParaConducirlo(sp, p0))
            return false;
        if (!p1.isInsideGameField(-1) || existeRivalCercaDelBalonParaConducirlo(sp, p1))
            return false;

        return true;
    }

    public static boolean existeRivalCercaDelBalonParaConducirlo(GameSituations sp, Position posicionBalon){
        
        for (int i=0 ; i<sp.rivalPlayers().length ; i++){
            Position posicionRival = sp.rivalPlayers()[i];
            if (posicionBalon.distance(posicionRival) <= 6.19  && sp.rivalIterationsToKick()[i] < 10)
                return true;
        }
        return false;
    }

    public static double fuerzaAvanzarBalon(GameSituations sp, int indiceJugador, double dist){
        double fuerza = 0;
        double diferencia = Double.MAX_VALUE;
        double remateJugador = sp.myPlayersDetail()[indiceJugador].getPower();
        double factorReduccion = sp.getMyPlayerEnergy(indiceJugador); 
        factorReduccion *= Constants.ENERGIA_DISPARO;
        
        if (factorReduccion > 1) factorReduccion = 1;
        
        for (double i=0 ; i<=1 ; i+=0.001){
            double vel = i * Constants.getVelocidadRemate(remateJugador) * factorReduccion;
            AbstractTrajectory trayectoria = new AirTrajectory(Math.cos(0) * vel, 0, 0, 0);
            double time=(double)10/60d;
            double desplazamientoHorizontal = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
            if ( Math.abs(dist-desplazamientoHorizontal) < diferencia ){
                fuerza = i;
                diferencia = Math.abs(dist-desplazamientoHorizontal);
            }

        }
        
        return fuerza;
    }

    public static boolean estaJugadorMasCercaBalonQueRivales(GameSituations sp, int indiceJugador){
        Position posicionBalon = sp.ballPosition();
        double distancia = posicionBalon.distance(sp.myPlayers()[indiceJugador]);
        for(int i=0;i<sp.rivalPlayers().length;i++)
            if (posicionBalon.distance(sp.rivalPlayers()[i]) < distancia)
                return false;
        return true;
    }

   public static Position obtenerPosteARematar(GameSituations sp){
       if (sp.ballPosition().distance(Constants.posteDerArcoSup) <=  sp.ballPosition().distance(Constants.posteIzqArcoSup))
           return Constants.posteIzqArcoSup;
       else
           return Constants.posteDerArcoSup;
   }
   
   public static ParametrosRemate obtenerValoresRemateACentroPuerta(GameSituations sp, DatosGlobales datos, int indiceJugador){
	   List<ParametrosRemate> valores = new ArrayList<ParametrosRemate>();
       double error = Constants.getErrorAngular(sp.myPlayersDetail()[indiceJugador].getPrecision() * sp.getMyPlayerEnergy(indiceJugador));
       double anguloError = (error - error / 2) * Math.PI;
       
       double angH = sp.ballPosition().angle(Constants.centroArcoSup);
    		   
       for (double f=1 ; f>=0.5 ; f-=0.02)
           for (int angV=40; angV>=0 ; angV--){
                   double angVerRad = Math.max(0, Math.min(angV, Constants.ANGULO_VERTICAL_MAX)) * (Math.PI / 180d);
                   ParametrosRemate p = parametroRemate(sp, datos, angH, angVerRad, f, indiceJugador);
                   if (p!= null && esGol(p)){
                       ParametrosRemate error0 = parametroRemate(sp, datos, angH+anguloError, angVerRad, f, indiceJugador);
                       ParametrosRemate error1 = parametroRemate(sp, datos, angH-anguloError, angVerRad, f, indiceJugador);
                       if (error0!=null && error1!=null ){
                           p.setError0(error0);
                           p.setError1(error1);
                           valores.add(p);
                       }
                           
                   }
               }
       
       if (!valores.isEmpty()){
           valores = determinarCalidad(valores, obtenerPosicionPorteroRival(sp), sp.ballPosition());
           return obtenerMejor(valores);
       }
           
       else
           return null;
   }

   public static ParametrosRemate obtenerValoresRemateAPuerta(GameSituations sp, DatosGlobales datos, int indiceJugador){
       List<ParametrosRemate> valores = new ArrayList<ParametrosRemate>();
        double error = Constants.getErrorAngular(sp.myPlayersDetail()[indiceJugador].getPrecision() * sp.getMyPlayerEnergy(indiceJugador));
        double anguloError = (error - error / 2) * Math.PI;
        double anguloInicial = sp.ballPosition().angle(Constants.posteDerArcoSup) + anguloError;
        double anguloFinal = sp.ballPosition().angle(Constants.posteIzqArcoSup)   - anguloError;
        for (double f=1 ; f>=0.5 ; f-=0.02)
            for (int angV=40; angV>=0 ; angV--)
                for (double angH=anguloInicial ; angH<=anguloFinal ; angH+=0.01){
                    double angVerRad = Math.max(0, Math.min(angV, Constants.ANGULO_VERTICAL_MAX)) * (Math.PI / 180d);
                    ParametrosRemate p = parametroRemate(sp, datos, angH, angVerRad, f, indiceJugador);
                    if (p!= null && esGol(p)){
                        ParametrosRemate error0 = parametroRemate(sp, datos, angH+anguloError, angVerRad, f, indiceJugador);
                        ParametrosRemate error1 = parametroRemate(sp, datos, angH-anguloError, angVerRad, f, indiceJugador);
                        if (error0!=null && error1!=null ){
                            p.setError0(error0);
                            p.setError1(error1);
                            valores.add(p);
                        }
                            
                    }
                }
        
        if (!valores.isEmpty()){
            valores = determinarCalidad(valores, obtenerPosicionPorteroRival(sp), sp.ballPosition());
            return obtenerMejor(valores);
        }
            
        else
            return null;
   }

   private static ParametrosRemate obtenerMejor(List<ParametrosRemate> parametros){
        ParametrosRemate p = null;
        double max = 0d;
        for (int i=0 ; i<parametros.size() ; i++){
            if ( parametros.get(i).getCalidad() > max ){
                max = parametros.get(i).getCalidad();
                p = parametros.get(i);
            }
        }
        return p;
   }

   private static List<ParametrosRemate> determinarCalidad(List<ParametrosRemate> parametros, Position posicionPorteroRival, Position pb){
	   
	   
	   double distPuerta = pb.distance(Constants.centroArcoSup);
	   double importanciaGoles  = 1;
	   double importanciaAltura = 1;
	   double importanciaHorizontal = 1;
	   
	   if ( distPuerta < 7 ){
		   importanciaGoles = 0;
		   importanciaAltura = 0;
	   }else{
		   importanciaHorizontal = 0;
	   }
	   
       for (int i=0 ; i<parametros.size() ; i++){
           ParametrosRemate p = (ParametrosRemate)parametros.get(i);
           double xL0 = p.getError0().getPosicionLinea().getPosicion().getX();
           double zL0 = p.getError0().getPosicionLinea().getAltura();

           double xL1 = p.getError1().getPosicionLinea().getPosicion().getX();
           double zL1 = p.getError1().getPosicionLinea().getAltura();

           double dxL = (xL0 - xL1)/50d;
           double dzL = (zL0 - zL1)/50d;

           double zD0 = p.getError0().getPosicionDentro().getAltura();

           double zD1 = p.getError1().getPosicionDentro().getAltura();

           double dzD = (zD0 - zD1)/50d;

           int goles = 0;
           int vertical = 0;

           for(int j=0;j<50;j++){
               double x = xL0 - dxL * j;
               double z = zL0 - dzL * j;
               double zD = zD0 - dzD * j;
               
               if ( Math.abs(x) < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON - 0.01d && z <= Constants.ALTO_ARCO){
                   goles++;

                   if (zD > Constants.ALTO_ARCO)
                       vertical++;
               }
                    
           }
           
           
           Position paloBueno = paloMasLejosPortero(posicionPorteroRival);
           double calidaH = (Constants.LARGO_ARCO - paloBueno.distance(p.getPosicionLinea().getPosicion())) / Constants.LARGO_ARCO;
           
           parametros.get(i).setCalidadRematesGol((goles/50d)*importanciaGoles);
           parametros.get(i).setCalidadImparableHorizontal( calidaH * importanciaHorizontal );
           parametros.get(i).setCalidadImparableVertical((vertical/50d) * importanciaAltura);
       }

       return parametros;
   }
   
   public static Position paloMasLejosPortero(Position p){
	   
	   
	   double distPosteDerecho   = p.distance(Constants.posteDerArcoSup);
	   double distPosteIzquierdo = p.distance(Constants.posteIzqArcoSup);
	   
	   if ( distPosteDerecho < distPosteIzquierdo)
		   return Constants.posteIzqArcoSup;
	   else
		   return Constants.posteDerArcoSup;
	   
	   
	   
   }

   public static boolean esGol(ParametrosRemate p){
       if (p.getPosicionLinea().getPosicion().getY() == Constants.LARGO_CAMPO_JUEGO / 2){
           double abs = Math.abs(p.getPosicionLinea().getPosicion().getX());
           if ( abs < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON - 0.01d  && 
                  //posicionSalida.getAltura() > Constants.ALTO_ARCO-0.1 &&
                  p.getPosicionLinea().getAltura() <= Constants.ALTO_ARCO ) {
                return true;
           }
       }

       return false;
   }

   private static Position obtenerPosicionPorteroRival(GameSituations sp){
       int indicePortero = 0;
       for (int i=0 ; i<sp.rivalPlayersDetail().length ; i++)
           if ( sp.rivalPlayersDetail()[i].isGoalKeeper() )
               indicePortero = i;
       return sp.rivalPlayers()[indicePortero];
   }

   public static boolean esGol(double x, double y, double z){
       if (y == Constants.LARGO_CAMPO_JUEGO / 2){
           double abs = Math.abs(x);
           if ( abs < Constants.LARGO_ARCO / 2 - Constants.RADIO_BALON - 0.01d  &&
                  //posicionSalida.getAltura() > Constants.ALTO_ARCO-0.1 &&
                  z <= Constants.ALTO_ARCO ) {
                return true;
           }
       }

       return false;
   }

   public static ParametrosRemate parametroRemate(GameSituations sp, DatosGlobales datos,double anguloHorizontal, double anguloVertical, double fuerza, int indiceJugador){

       Position balon = sp.ballPosition();
       Position balon0 = balon;
       double altura0 = 0;
       double remateJugador = sp.myPlayersDetail()[indiceJugador].getPower();
       double factorReduccion = sp.getMyPlayerEnergy(indiceJugador); 
       factorReduccion *= Constants.ENERGIA_DISPARO;
       
       if (factorReduccion > 1) factorReduccion = 1;
       
       double vel = fuerza * Constants.getVelocidadRemate(remateJugador);
       vel *= factorReduccion;
       
       AbstractTrajectory trayectoria = new AirTrajectory(Math.cos(anguloVertical) * vel, Math.sin(anguloVertical) * vel, 0, 0);
       
       int iteracion = 1;
       double time1 = (double)iteracion/60d;
       double desplazamientoHorizontal = trayectoria.getX(time1) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
       double desplazamientoVertical = trayectoria.getY(time1) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
       double x = balon.getX() + desplazamientoHorizontal * Math.cos(anguloHorizontal);
       double y = balon.getY() + desplazamientoHorizontal * Math.sin(anguloHorizontal);
       Position balon1 = new Position(x, y);
       double altura1 = desplazamientoVertical * 2;
       boolean salir = false;
       
       while ( !salir ){
           if ( !balon1.isInsideGameField(0) || balon1.equals(balon0) ){
               salir = true;
           }else{
               iteracion++;
               balon0 = balon1;
               altura0 = altura1;
               double time = (double)iteracion/60d;
               desplazamientoHorizontal = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
               desplazamientoVertical   = trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
               x = balon.getX() + desplazamientoHorizontal * Math.cos(anguloHorizontal);
               y = balon.getY() + desplazamientoHorizontal * Math.sin(anguloHorizontal);
               balon1  = new Position(x, y);
               altura1 = desplazamientoVertical * 2;
           }
       }
        if (!balon1.isInsideGameField(0)){
            PosicionBalon posicionDentro    = new PosicionBalon(balon0, altura0);
            PosicionBalon posicionFuera     = new PosicionBalon(balon1, altura1);
            PosicionBalon posicionLinea     = puntoDeSalidaBalon(posicionDentro, posicionFuera);
            ParametrosRemate p = new ParametrosRemate(anguloHorizontal, anguloVertical*(180d / Math.PI), fuerza, iteracion-1, posicionLinea, posicionDentro, posicionFuera);
            p.setCalidadFuerzaRemate(vel/2.4);
            return p;
       }
        else
           return null;
   }

   private static LinkedList<Pase> posiblesPases(int indiceJugador){
	   LinkedList<Pase> posibles = new LinkedList<Pase>();
	   Pase[] pases = DatosUtiles.pases;
	   int count = pases.length;
	   for (int i=0 ; i<count ; i++){
		  if (pases[i].getPasador() == indiceJugador)
			  posibles.add(pases[i]);
	   }
	   return posibles;
   }

   public static LinkedList<Pase> obtenerPases(GameSituations sp, int indiceJugador){
	   LinkedList<Pase> pases = posiblesPases(indiceJugador);
	   
	   int count = pases.size();
	   
	   for (int i=0 ; i < count-1 ; i++)
		   for (int j=i ; j < count ; j++){
		   Position posicionReceptorI = sp.myPlayers()[pases.get(i).getReceptor().intValue()];
		   Position posicionReceptorJ = sp.myPlayers()[pases.get(j).getReceptor().intValue()];
		   double distI = distanciaRivalMasCercano(sp, posicionReceptorI);
		   double distJ = distanciaRivalMasCercano(sp, posicionReceptorJ);
		   
		   if (distJ > distI){
			   Pase p = pases.get(i);
			   pases.set(i, pases.get(j));
			   pases.set(j, p);
		   }			  
	   }
	   return pases;
   }

   public static Pase paseReceptorEnZonaDisparo(GameSituations sp, DatosGlobales datos, int indicePasador){
	   Pase p = null;
	   List<Pase> pases = posiblesPases(indicePasador);
	   for (int i=0 ; i<pases.size() ; i++){
		   if (estaJugadorEnZonaTiroChica(sp, pases.get(i).getReceptor().intValue())  && !estaJugadorEnZonaTiroChica(sp, pases.get(i).getPasador().intValue()))
			   p = pases.get(i);
	   }
	   return p;
   }
   
   public static Pase obtenerPase(GameSituations sp, int indiceJugador){
	   List<Pase> pases = posiblesPases(indiceJugador);
	   Pase p = null;
	   double distanciaMax = 0;
	   for (int i=0 ; i<pases.size() ; i++){
		   Position posicionReceptor = sp.myPlayers()[pases.get(i).getReceptor().intValue()];
		   double dist = distanciaRivalMasCercano(sp, posicionReceptor);
		   if (distanciaMax < dist){
			   p = pases.get(i);
			   distanciaMax = dist;
		   }			  
	   }
	   return p;
   }
   
   public static double distanciaRivalMasCercano(GameSituations sp, Position posicionReceptor){
	   double distanciaMinima = Double.MAX_VALUE;
	   Position[] posRival = sp.rivalPlayers();
	   for (int i=0 ; i<11 ; i++){
		   if (distanciaMinima > posicionReceptor.distance(posRival[i])){
			   distanciaMinima = posicionReceptor.distance(posRival[i]);
		   }
	   }
	   return distanciaMinima;
   }
   
   public static ParametrosPase obtenerValoresPase(GameSituations sp, DatosGlobales datos, int indicePasador){
	   
	   Position posicionBalon = sp.ballPosition();
	 	      
	   ParametrosPase pp = null;
	   Pase paseObligado = paseReceptorEnZonaDisparo(sp, datos, indicePasador);
	   double anguloAvanzarBalon = Double.MAX_VALUE;
			   
	   if ( sp.ballPosition().getY() > -5 )
		   anguloAvanzarBalon = Utiles.anguloMovimientoBalonControlado360(sp, datos, indicePasador);
	   else
		   anguloAvanzarBalon = Utiles.anguloMovimientoBalonControlado180(sp, datos, indicePasador);
	   
	   
	   if ( paseObligado!=null && anguloAvanzarBalon == Double.MAX_VALUE ){
	   	
		   Position posicionReceptor = sp.myPlayers()[paseObligado.getReceptor()];
		   
		   double anguloI = 0;
		   double inc;

		   Position posicionPasador = sp.myPlayers()[paseObligado.getPasador()];
		   double Dx = posicionReceptor.getX() - posicionPasador.getX();
		   double Dy = posicionReceptor.getY() - posicionPasador.getY();
		   
		   if ( Math.abs(Dx) > Math.abs(Dy)){
			   anguloI = Math.PI/2;
			   if ( Math.signum(Dx) == -1 ){
				   inc = -1;
			   }else{
				   inc = 1;
			   }
				   
		   }else{
			   if ( Math.signum(Dx) == -1 ){
				   anguloI = 0;
				   if ( Math.signum(Dy) == -1 ){
					   inc = 1;
				   }else{						   
					   inc = -1;
				   }
					   
			   }else
				   anguloI = Math.PI;
			   		if ( Math.signum(Dy) == -1 ){
			   			inc = -1;
			   		}else{					   			
			   			inc = 1;
			   		}
			}
		   
		   
		   pp = obtenerValoresPase2_0(sp, datos, paseObligado, anguloI, inc);

	    
	   }
	   
	   if ( paseObligado!=null  && estaJugadorEnZonaTiroChica(sp, paseObligado.getReceptor())){
		   	
		   Position posicionReceptor = sp.myPlayers()[paseObligado.getReceptor()];
		   //double distanciaPase = posicionBalon.distance(posicionReceptor);
		   //if ( paseObligado.esPaseEspacio() && distanciaPase > 15 )
			//   pp = obtenerValoresPaseEspacio(sp, datos, paseObligado, false);
		   
		   double anguloI = 0;
		   double inc;
		   Position posicionPasador = sp.myPlayers()[paseObligado.getPasador()];
		   double Dx = posicionReceptor.getX() - posicionPasador.getX();
		   double Dy = posicionReceptor.getY() - posicionPasador.getY();
		   
		   if ( Math.abs(Dx) > Math.abs(Dy)){
			   anguloI = Math.PI/2;
			   if ( Math.signum(Dx) == -1 ){
				   inc = -1;
			   }else{
				   inc = 1;
			   }
				   
		   }else{
			   if ( Math.signum(Dx) == -1 ){
				   anguloI = 0;
				   if ( Math.signum(Dy) == -1 ){
					   inc = 1;
				   }else{						   
					   inc = -1;
				   }
					   
			   }else
				   anguloI = Math.PI;
			   		if ( Math.signum(Dy) == -1 ){
			   			inc = -1;
			   		}else{					   			
			   			inc = 1;
			   		}
			}
		   
		   
		   pp = obtenerValoresPase2_0(sp, datos, paseObligado, anguloI, inc);
	    
	   }
	   
	   if (pp == null && DatosUtiles.esDelantero(indicePasador)){
		   
			if ( !DatosUtiles.esPortero(indicePasador) && anguloAvanzarBalon != Double.MAX_VALUE && !sp.isStarts()){
				Position posicionReceptor = sp.myPlayers()[indicePasador];
				Aceleracion aceleracion = datos.getAceleraciones()[indicePasador];
				Position nuevaPos = posicionReceptor.moveAngle(anguloAvanzarBalon, 5);
				double dist = distanceTest(sp, indicePasador, aceleracion, nuevaPos, 10, false);
				double fuerza = Utiles.fuerzaAvanzarBalon(sp, indicePasador,dist);
				
				Position posPase = posicionReceptor.moveAngle(anguloAvanzarBalon, dist);
				pp = new ParametrosPase(anguloAvanzarBalon, 0, fuerza, posicionReceptor, posPase, null);
				pp.setPase(new Pase(indicePasador, indicePasador, Pase.AUTO_PASE));
				
			}
	   }
	   
	   if (pp == null){
		   LinkedList<Pase> pases = obtenerPases(sp, indicePasador);
		   Iterator<Pase> iter = pases.iterator();
		   while (iter.hasNext() && pp== null){
			   Pase pase = iter.next();
			   
			   Position posicionReceptor = sp.myPlayers()[pase.getReceptor()];
			   Position posicionPasador = sp.myPlayers()[indicePasador];
			   
			   double distanciaPase = posicionBalon.distance(posicionReceptor);
			   
			   double diferenciaY = posicionReceptor.getY() - posicionPasador.getY();
			   
			   boolean paseMalo = false;
			   if ( diferenciaY < 0 && posicionReceptor.getY() < Constants.LARGO_CAMPO_JUEGO/2  && !sp.isStarts())
				   paseMalo = true;
			   
			   if (pp == null && distanciaPase > 5 && !paseMalo){
				   
				   double anguloI = 0;

				   
				   double Dx = posicionReceptor.getX() - posicionPasador.getX();
				   double Dy = posicionReceptor.getY() - posicionPasador.getY();
				   double inc;
				   
				   if ( Math.abs(Dx) > Math.abs(Dy)){
					   anguloI = Math.PI/2;
					   if ( Math.signum(Dx) == -1 ){
						   inc = -1;
					   }else{
						   inc = 1;
					   }
						   
				   }else{
					   if ( Math.signum(Dx) == -1 ){
						   anguloI = 0;
						   if ( Math.signum(Dy) == -1 ){
							   inc = 1;
						   }else{						   
							   inc = -1;
						   }
							   
					   }else
						   anguloI = Math.PI;
					   		if ( Math.signum(Dy) == -1 ){
					   			inc = -1;
					   		}else{					   			
					   			inc = 1;
					   		}
					}
				   
				   if ( posicionReceptor.getY() <= datos.getPosicionFueraJuego() )
					   pp = obtenerValoresPase2_0(sp, datos, pase, anguloI, inc);
				  
			   }
			   
		   }
		   
	   }
		   
	   
	return pp;
   }
   
   
   
   public static ParametrosPase obtenerValoresPase(GameSituations sp, DatosGlobales datos, int indicePasador,int indiceReceptor){
	   Pase pase = new Pase(indicePasador, indiceReceptor, Pase.PASE_ESPACIO);
	   
	   return obtenerValoresPaseEspacio(sp, datos, pase, false);
   }
   
   public static ParametrosPase obtenerValoresPaseAsistencia(GameSituations sp, DatosGlobales datos, Pase pase){
	   return null;
   }
   
    
   public static ParametrosPase obtenerParamPaseMasCalidad(GameSituations sp, LinkedList<ParametrosPase> params){
	   ParametrosPase mejor = null;
	   double calMax = 0;
	   Iterator<ParametrosPase> iter = params.iterator();
	   
	   while (iter.hasNext()){
		   ParametrosPase pp = iter.next();
		   double distancia = distanciaRivalMasCercano(sp, pp.getPosicionRecepcion());
		   double cal =  distancia;
		   if (cal > calMax ){
			   calMax = cal;
			   mejor = pp;
		   }
	   }
	   
	   return mejor;
   }

   public static Position comprobar2_0(GameSituations sp, DatosGlobales datos, Pase pase, double angH, double angH1, double angH2, double f, double angV){
	   Queue<PosicionBalon> trayectoria = crearTrayectoriaBalon(sp, angH, angV, f, pase.getPasador());
	   //Queue<PosicionBalon> trayectoria1 = crearTrayectoriaBalon(sp, angH1, angV, f, pase.getPasador());
       Queue<PosicionBalon> trayectoria2 = crearTrayectoriaBalon(sp, angH2, angV, f, pase.getPasador());
	   
       Position pos2 = posicionAlcanzadaTrayectoria(sp, datos, pase, trayectoria2);
       Position pos = posicionAlcanzadaTrayectoria(sp, datos, pase, trayectoria);
       if (pos != null && pos2 != null)
    	   return pos2;
       
	   return null;
   }
   
   public static Position posicionAlcanzadaTrayectoria(GameSituations sp, DatosGlobales datos, Pase pase, Queue<PosicionBalon> t){
	   
	   int indiceReceptor = pase.getReceptor();
	   Position posReceptor = sp.myPlayers()[indiceReceptor];
	   Aceleracion acel = datos.getAceleraciones()[indiceReceptor];
	   
	   Iterator<PosicionBalon> it = t.iterator();
	   int iteracion = 1;
	   
       while (it.hasNext()){
    	   PosicionBalon position =  (PosicionBalon)it.next();
           double distRecorrida = distanceTest(sp, indiceReceptor, acel, position.getPosicion(), iteracion, false);
           double diferencia = posReceptor.distance(position.getPosicion());
           
           if (position.getPosicion().isInsideGameField(0) && position.getAltura() < Constants.ALTURA_CONTROL_BALON && distRecorrida > diferencia - Constants.DISTANCIA_CONTROL_BALON - 0.99d)
        	   return position.getPosicion();
           
           iteracion++;
       }
	   return null;
   }

   public static ParametrosPase obtenerValoresPase2_0(GameSituations sp, DatosGlobales datos, Pase pase, double anguloI, double inc){
	   
	   
	   LinkedList<ParametrosPase> params = new LinkedList<ParametrosPase>();
	   double incrementoAngular = inc * Math.PI / 20;
	   ParametrosPase pp = null;
	   Position posicionReceptor = sp.myPlayers()[pase.getReceptor()];
	   int iteraciones = 5;
	   if (posicionReceptor.getY() > 30)
		   iteraciones = 10;
	   
	   for (int i=0 ; i < 5 ; i++){
		   double angulo = anguloI + incrementoAngular * i;
		   for (int iteracion=1 ; iteracion <= iteraciones ; iteracion++){
			   double velocidad = distanceTest(sp, pase.getReceptor(), datos.getAceleraciones()[pase.getReceptor()], posicionReceptor.moveAngle(angulo, 9), iteracion, false);
			   Position p = posicionReceptor.moveAngle(angulo,velocidad);
			   if ( p.isInsideGameField(1) ){
				   pp = encontrarParametrosPase(sp, datos, p, pase,false);
				   if (pp != null)
		    		   params.add(pp);
			   }
		   }
	   }
		   

	   
	   //if (pase.esPaseEspacio() && !estaJugadorEnZonaTiroAmplia(sp, pase.getReceptor()))
		   pp = obtenerParamPaseMasCalidad(sp, params);
	   //else if (!params.isEmpty())
		//   pp = params.getFirst();
	   
	   return pp;
   }
   
   public static Position setInsideGameField(Position p) {
       double mx = Constants.ANCHO_CAMPO_JUEGO / 2 - 5;
       double my = Constants.LARGO_CAMPO_JUEGO / 2 - 5;
       double x0 = p.getX(), y0 = p.getY();
       if (x0 > mx) {
           x0 = mx;
       }
       if (x0 < -mx) {
           x0 = -mx;
       }
       if (y0 > my) {
           y0 = my;
       }
       if (y0 < -my) {
           y0 = -my;
       }
       return new Position(x0, y0);
   }

   
   public static ParametrosPase obtenerValoresPaseDirecto(GameSituations sp, DatosGlobales datos, Pase pase, double angulo){
	   
	   
	   LinkedList<ParametrosPase> params = new LinkedList<ParametrosPase>();
	   
	   ParametrosPase pp = null;
	   Position posicionReceptor = sp.myPlayers()[pase.getReceptor()];
	   Position p0 = posicionReceptor;
	   int iteracion = 0;
	   double velocidad = 0;
	   Position p1 = p0;
	   
	   while ( p1.isInsideGameField(1) ){

		   pp = encontrarParametrosPase(sp, datos, p1, pase,false);
		   if (pp != null)
    		   params.add(pp);
		   
		   p0 = p1;
		   iteracion++;
		   velocidad = distanceTest(sp, pase.getReceptor(), datos.getAceleraciones()[pase.getReceptor()], p0.moveAngle(angulo, 0.6), iteracion, false);
		   p1 = p0.moveAngle(angulo,velocidad);
		   
		   
	   }
	   
	   if (pase.esPaseEspacio() && !estaJugadorEnZonaTiroAmplia(sp, pase.getReceptor()))
		   pp = obtenerParamPaseMasCalidad(sp, params);
	   else if (!params.isEmpty())
		   pp = params.getFirst();
	   
	   return pp;
   }
   
   public static ParametrosPase obtenerValoresPaseEspacio(GameSituations sp, DatosGlobales datos, Pase pase, boolean isSprint){
       
	   Position posicionReceptor = sp.myPlayers()[pase.getReceptor()];
	   ParametrosPase pp = null;
	   
    	   
           double angulo = Math.PI/2;
    	   Position p0 = posicionReceptor;
    	   int iteracion = 1;
    	   double velocidad =  distanceTest(sp, pase.getReceptor(), datos.getAceleraciones()[pase.getReceptor()], p0.moveAngle(angulo, 0.6), iteracion, isSprint);
    	   
    	   
           Position p1 = p0.moveAngle(angulo, velocidad);

    	   while ( p1.getY() < Constants.LARGO_CAMPO_JUEGO/2 ){

    		   if (comprobarDireccion(sp, p1, pase))
    			   pp = encontrarParametrosPase(sp, datos, p1, pase,isSprint);
    		   if (pp != null)
        		   return pp;
    		   
    		   p0 = p1;
    		   iteracion++;
    		   velocidad = distanceTest(sp, pase.getReceptor(), datos.getAceleraciones()[pase.getReceptor()], p0.moveAngle(angulo, 0.6), iteracion, isSprint);
    		   p1 = p0.moveAngle(angulo,velocidad);
    		   //distanciaRecorrida += velocidad;
    		   
    	   }
 

       
	   
	   return pp;
	   
   }
   
   public static ParametrosPase mejorPase(List<ParametrosPase> pases){
	   ParametrosPase mejor = null;
	   double calidad = 0;
	   if (pases.isEmpty())
		   return null;
	   else{
		   for (int i=0 ; i<pases.size() ; i++){
			   if (calidad < pases.get(i).getPosicionRecepcion().getY()){
				   calidad = pases.get(i).getPosicionRecepcion().getY();
				   mejor = pases.get(i);
			   }
		   }
		   return mejor;
	   }
   }
   
   public static ParametrosPase encontrarParametrosPase(GameSituations sp, DatosGlobales datos,Position posPase, Pase pase, boolean isSprint){
	   
	   Position pocisionReceptor = sp.myPlayers()[pase.getReceptor()];
	   for (double f=1.0 ; f>=0.1 ; f-=0.1)
           for (int angV=46; angV>=0 ; angV-=2){
        	   double angVerRad = Math.max(0, Math.min(angV, Constants.ANGULO_VERTICAL_MAX)) * (Math.PI / 180d);
        	   double anguloHorizontal = sp.ballPosition().angle(posPase);
        	   if ( comprobar(sp, datos, posPase, angVerRad, f, pase, isSprint) ){
        		   ParametrosPase pp = new ParametrosPase(anguloHorizontal, angV, f, pocisionReceptor, posPase, null);
        		   pp.setPase(pase);
        		   return pp;
        	   }
        		   
           }
	   
	   return null;
	   
   }
   
   public static Position corregirPosicion(DatosGlobales datos, Position posicionPase){
	   Position newPosition = null;
	   double distMin = Double.MAX_VALUE;
	   if (datos.existeTrayectoria()){
		   Queue<PosicionBalon> trajectoryTemp = datos.getTrayectoriaBalon();
		   Iterator<PosicionBalon> it = trajectoryTemp.iterator();

		   
           while (it.hasNext()){
               PosicionBalon pos =  (PosicionBalon)it.next();
               System.out.println(" posBalonT " + pos );
               if ( posicionPase.distance(pos.getPosicion()) < distMin && pos.getAltura() <= Constants.ALTURA_CONTROL_BALON){
                   distMin = posicionPase.distance(pos.getPosicion());
                   newPosition = pos.getPosicion();
               }
           }
	   }
		   
	   
	   return newPosition;
   }
   
   public static boolean puedoPasar(GameSituations sp, DatosGlobales datos){
	   int refenciaSaque = datos.getReferenciaSaque();
	   Position posReferencia = sp.myPlayers()[refenciaSaque];
	   if (refenciaSaque == -1)
		   return true;
	   
	   for (int i=7 ; i<=11 ; i++){
		   MovimientoJugador mov = DatosUtiles.obtenerMovimiento(refenciaSaque, i);
		   if (mov != null){
			   Position posI = sp.myPlayers()[i];
			   double anguloMov = mov.getAnguloRefencia() * (Math.PI/180);
			   double distMov = mov.getDistanciaRefencia();
			   Position posMov = posReferencia.moveAngle(anguloMov, distMov);
			   if ( posMov.isInsideGameField(0) && posI.distance(posMov) > 2 )
				   return false;
		   }
		   
	   }
	   
	   return true;
   }
   
   public static Position protegerBalon(GameSituations sp, Position posRecep){

	   Position p = null;
	   int rival = rivalMasCercaDePosicion(sp, posRecep);
	   Position posRival = sp.rivalPlayers()[rival];
	   double dist = posRecep.distance(posRival);
	   double angRival  = posRecep.angle(posRival);
	   double angBalon  = posRecep.angle(sp.ballPosition());
	   if (dist > 2)
		   p = posRecep.moveAngle(angBalon, 0.99);
	   else if (dist > 1.5)
	       	p = posRecep.moveAngle(angRival, 0.99);
	   else if(dist > 0.5 && dist <= 1.5)
	    	p = posRecep.moveAngle(angRival, dist-0.51);
	   else
	    	p = posRecep;
	   
	   return p;
   }

   public static Position corregirPosicionPase(GameSituations sp, DatosGlobales datos, ParametrosPase pp){
	   Position newPosition = null;
   
	   int indiceJugador = pp.getPase().getReceptor();
	   Position posInicialJugador = sp.myPlayers()[indiceJugador];
	   
	   double distRecorreJugador = 0;
		
	   

	   if ( datos.existeTrayectoria() ){
		   Queue<PosicionBalon> trajectoryTemp = datos.getTrayectoriaBalon();
		   Iterator<PosicionBalon> it = trajectoryTemp.iterator();
		   int iteracion = 1;
		   int iteracionesRematar = sp.iterationsToKick()[indiceJugador];

		   if (iteracionesRematar > 0)
        	   iteracionesRematar--;
		   it.next();
		   
           while ( it.hasNext() ){
               PosicionBalon posIter =  (PosicionBalon)it.next();            
               
               double distHastaPosIter = posInicialJugador.distance(posIter.getPosicion());
               
               distRecorreJugador = distanceTest(sp, indiceJugador, datos.getAceleraciones()[indiceJugador], posIter.getPosicion(), iteracion, false);
               
               if ( iteracionesRematar == 0 && distRecorreJugador > distHastaPosIter - Constants.DISTANCIA_CONTROL_BALON  && posIter.getAltura() < Constants.ALTURA_CONTROL_BALON){
            	   
            	    
            	   if ( it.hasNext() ){         		   
            		   //Position psiguiente = ((PosicionBalon)it.next()).getPosicion();
            		   return posIter.getPosicion();
            		   
            	   }else
            		   return posIter.getPosicion();
            	   
               }
            	 
               iteracion++;
               if (iteracionesRematar > 0)
            	   iteracionesRematar--;
    
           }
	   }
		   
	   
	   return newPosition;
   }
   public static double distanceTest(GameSituations sp, int indiceJugador, Aceleracion aceleracion, Position nuevaPos, int iteracion, boolean isSprint){
	   double dist = 0;
	   //List<Double> dists = new ArrayList<Double>();
	   
	   for (int i=1 ; i<=iteracion ; i++){
		   double vel = distanceIteracionTest(sp, indiceJugador, aceleracion, nuevaPos, i, isSprint);
		   dist += vel;
		  // dists.add(dist);
	   }
	   
	  // System.out.println(dists);	   
	   
	   return dist;
   }
   
   public static double distance(GameSituations sp, int indiceJugador, Aceleracion aceleracion, Position nuevaPos, int iteracion, boolean isSprint){
	   double dist = 0;
	   List<Double> dists = new ArrayList<Double>();
	   
	   for (int i=1 ; i<=iteracion ; i++){
		   double vel = distanceIteracion(sp, indiceJugador, aceleracion, nuevaPos, i, isSprint);
		   dist += vel;
		   dists.add(dist);
	   }
	   
	   System.out.println(dists);	   
	   
	   return dist;
   }

   public static double calcularVelocidad(List<Command> comandos, GameSituations sp, DatosGlobales datosGlobales){
	   
	   return 0;
   }
   
   public static List<Command> optimizarMoveTo(List<Command> comandos, GameSituations sp){
   	List<Command> list = new ArrayList<Command>();//lista de comandos
   	if (comandos == null) return list;
   	
   	Command.CommandType tipo;//almacena un tipo de comando: irA o golpearBalon
       int indJugador;//almacena un indice de jugador para ser usado en un comando
       
       for (Command c : comandos) {
       	tipo = c.getCommandType();//obtiene el tipo del comando
       	indJugador = c.getPlayerIndex();//obtiene el indice del jugador indicado en el comando
       	if (tipo.equals(Command.CommandType.MOVE_TO)) {
       		CommandMoveTo cia = (CommandMoveTo) c;//obtiene el comando irA
       		Position p0 = cia.getMoveTo();//Obtiene la posicion destino del comando
       		
       		if ( sp.myPlayers()[indJugador].distance(p0) != 0 ){
       			double angDireccion = sp.myPlayers()[indJugador].angle(p0);
       			double vel = 0.6;
       			
       			double dist = sp.myPlayers()[indJugador].distance(p0);
       			if (dist < vel) vel = dist;
       			
       			Position p1 = sp.myPlayers()[indJugador].moveAngle(angDireccion, vel);
       			
       			
       			CommandMoveTo newCia =new CommandMoveTo(indJugador,p1,cia.getSprint());
       			list.add(newCia);
       		}
       	}else{
       		list.add(c);
       	}
                       
       }
       
       return list;
   }
    
   public static double distanceIteracionTest(GameSituations sp, int indiceJugador, Aceleracion aceleracion, Position nuevaPos, int iteracion, boolean isSprint){
	   	//Obtenemos la aceleracion con la que el jugador llegara a la iteraccion
	   	//Debe ser igual a la aceleracion
	   	double acelIter = 1;
	   	
	   	
	   	
	   	double acel1X = aceleracion.obtenerAceleracionX();
	   	double acel1Y = aceleracion.obtenerAceleracionY();
	   	
	   	Aceleracion a1 = new Aceleracion(aceleracion.getAceleracionX(), aceleracion.getAceleracionY());
	   	a1.setDireccionX(aceleracion.getDireccionX());
	   	a1.setDireccionY(aceleracion.getDireccionY());
	   	a1.setPosJugador(aceleracion.getPosJugador());
	   	
	   	a1.actualizar(nuevaPos);
	   	
	   	if (aceleracion.obtenerAceleracionGlobal() != a1.obtenerAceleracionGlobal()){
	   		acel1X = a1.obtenerAceleracionX();
	   		acel1Y = a1.obtenerAceleracionY();
	   	}
	      	
	  	if (iteracion == 1){	  	
		   	acelIter = acel1X * acel1Y;
		   	
	  	}else{
	  		//Calculamos la aceleracion en cada uno de los ejes
	  	   	double incrementoAcel = (Constants.ACELERACION_INCR * (iteracion));
	  	   	
	  	   	double acelXiter = acel1X + incrementoAcel;
	  	   	double acelYiter = acel1Y + incrementoAcel;
	  	   	
	  	   	//Si superan el valor maximo de 1 las establecemos en ese valor.
	  	   	
	  	   	acelXiter = (acelXiter > 1) ? 1 : acelXiter;
	  	   	acelYiter = (acelYiter > 1) ? 1 : acelYiter;
	  	   	
	  	   	//Finalmente calculamos la aceleracion global en esa iteraccion
	  	   	
	  	   	acelIter = acelXiter * acelYiter;
	  	}
	  		
	  	
	   	
	   	//Obtenemos la energia en la iteraccion: 
	   	
	   	//Calculamos la reduccion de la energia en las iteracciones anteriores
	   	double reduccionEnergia = (iteracion * Constants.ENERGIA_DIFF);
	   	double reduccionEnergiaSpring = ((iteracion-1) * Constants.SPRINT_ENERGIA_EXTRA);
	   	//Calculamos la energia en esa iteraccion
	   	double energiaIter = 1;
	   	if ( isSprint )
	   		energiaIter = sp.getMyPlayerEnergy(indiceJugador) - reduccionEnergia - reduccionEnergiaSpring;
	   	
	   	
	   	
	   	//Si es inferior al minimo la establecemos en el minimo
	   	
	   	energiaIter = (energiaIter < Constants.ENERGIA_MIN) ? Constants.ENERGIA_MIN : energiaIter;
	   	
	   	//Si el jugador esta sprintando
	   	double sprint = (isSprint && energiaIter > Constants.SPRINT_ENERGIA_MIN) ? Constants.SPRINT_ACEL : 1;
	   	
	   	//Obtenemos la distancia recorrida en la iteraccion
	   	return Constants.getVelocidad(sp.getMyPlayerSpeed(indiceJugador)) * energiaIter * acelIter * sprint;
	   }
   
   
   
   public static double distanceIteracion(GameSituations sp, int indiceJugador, Aceleracion aceleracion, Position nuevaPos, int iteracion, boolean isSprint){
	   	//Obtenemos la aceleracion con la que el jugador llegara a la iteraccion
	   	//Debe ser igual a la aceleracion
	   	double acelIter = 1;
	   	
	   	
	   	
	   	double acel1X = aceleracion.obtenerAceleracionX();
	   	double acel1Y = aceleracion.obtenerAceleracionY();
	   	
	   	Aceleracion a1 = new Aceleracion(aceleracion.getAceleracionX(), aceleracion.getAceleracionY());
	   	a1.setDireccionX(aceleracion.getDireccionX());
	   	a1.setDireccionY(aceleracion.getDireccionY());
	   	a1.setPosJugador(aceleracion.getPosJugador());
	   	
	   	a1.actualizar(nuevaPos);
	   	
	   	if (aceleracion.obtenerAceleracionGlobal() != a1.obtenerAceleracionGlobal()){
	   		acel1X = a1.obtenerAceleracionX();
	   		acel1Y = a1.obtenerAceleracionY();
	   	}
	      	
	  	if (iteracion == 1){	  	
		   	acelIter = acel1X * acel1Y;
		   	
	  	}else{
	  		//Calculamos la aceleracion en cada uno de los ejes
	  	   	double incrementoAcel = (Constants.ACELERACION_INCR * (iteracion-1));
	  	   	
	  	   	double acelXiter = acel1X + incrementoAcel;
	  	   	double acelYiter = acel1Y + incrementoAcel;
	  	   	
	  	   	//Si superan el valor maximo de 1 las establecemos en ese valor.
	  	   	
	  	   	acelXiter = (acelXiter > 1) ? 1 : acelXiter;
	  	   	acelYiter = (acelYiter > 1) ? 1 : acelYiter;
	  	   	
	  	   	//Finalmente calculamos la aceleracion global en esa iteraccion
	  	   	
	  	   	acelIter = acelXiter * acelYiter;
	  	}
	  		
	  	
	   	
	   	//Obtenemos la energia en la iteraccion: 
	   	
	   	//Calculamos la reduccion de la energia en las iteracciones anteriores
	   	double reduccionEnergia = (iteracion * Constants.ENERGIA_DIFF);
	   	double reduccionEnergiaSpring = ((iteracion-1) * Constants.SPRINT_ENERGIA_EXTRA);
	   	//Calculamos la energia en esa iteraccion
	   	double energiaIter = 1;
	   	if ( isSprint )
	   		energiaIter = sp.getMyPlayerEnergy(indiceJugador) - reduccionEnergia - reduccionEnergiaSpring;
	   	
	   	
	   	
	   	//Si es inferior al minimo la establecemos en el minimo
	   	
	   	energiaIter = (energiaIter < Constants.ENERGIA_MIN) ? Constants.ENERGIA_MIN : energiaIter;
	   	
	   	//Si el jugador esta sprintando
	   	double sprint = (isSprint && energiaIter > Constants.SPRINT_ENERGIA_MIN) ? Constants.SPRINT_ACEL : 1;
	   	
	   	//Obtenemos la distancia recorrida en la iteraccion
	   	return Constants.getVelocidad(sp.getMyPlayerSpeed(indiceJugador)) * energiaIter * acelIter * sprint;
	   }
     
   private static boolean comprobarDireccion(GameSituations sp, Position posPase, Pase pase){
       
	   double anguloHorizontal = sp.ballPosition().angle(posPase);
	   
	   double error = Constants.getErrorAngular(sp.myPlayersDetail()[pase.getPasador()].getPrecision() * sp.getMyPlayerEnergy(pase.getPasador()) );
       double anguloError = (error - error / 2) * Math.PI;
       double angH1 = anguloHorizontal + anguloError;
       double angH2 = anguloHorizontal - anguloError;
       
       Position posJugador0 = sp.myPlayers()[pase.getReceptor()];
       double angPase = posJugador0.angle(posPase);
       Position posJugador1 = posJugador0.moveAngle(angPase, 15);

       Position posBalon0 = sp.ballPosition();
       Position posBalonError1 = sp.ballPosition().moveAngle(angH1, 15);
       Position posBalonError2 = sp.ballPosition().moveAngle(angH2, 15);
       
      // sp.distanceIter(playerIndex, iter, isSprint)
      // Position intercepcion = intercepcion(posJugador0, posJugador1, posBalon0, posBalon1);
       Position intercepcion1 = Position.Intersection(posJugador0, posJugador1, posBalon0, posBalonError1);
       Position intercepcion2 = Position.Intersection(posJugador0, posJugador1, posBalon0, posBalonError2);
       
       double Dy1 = intercepcion1.getY() - posJugador0.getY();
       double direccionY1 = (Dy1 == 0) ? 0 : (Math.signum(Dy1));
       double Dy2 = intercepcion2.getY() - posJugador0.getY();
       double direccionY2 = (Dy2 == 0) ? 0 : (Math.signum(Dy2));
       if (direccionY1 != 1 || direccionY2 != 1)
    	   return false;
       else
    	   return true;

   }
   
   public static boolean trajectoriaPeligrosa(GameSituations sp, ParametrosRemate pr, int indiceJugador){
	   if ( Math.abs(sp.ballPosition().getX()) < Constants.LARGO_AREA_GRANDE/2 && 
			   sp.ballPosition().getY() < - (Constants.LARGO_CAMPO_JUEGO/2 - Constants.ANCHO_AREA_GRANDE) && 
			   paseInterceptado(sp, pr, indiceJugador)){
		   return true;
	   }else
		   return false;
		   
	   
   }

   public static boolean trajectoriaPeligrosa(GameSituations sp, ParametrosPase pp){
	   if ( Math.abs(sp.ballPosition().getX()) < Constants.LARGO_AREA_GRANDE/2 && 
			   sp.ballPosition().getY() < - (Constants.LARGO_CAMPO_JUEGO/2 - Constants.ANCHO_AREA_GRANDE) && 
			   paseInterceptado(sp, pp)){
		   return true;
	   }else
		   return false;
		   
	   
   }
   
   public static boolean paseInterceptado(GameSituations sp, ParametrosRemate pr, int indiceJugador){

	   Queue<PosicionBalon> t = crearTrayectoriaBalon(sp, pr.getAnguloHorizontal(), pr.getAngulovertical(), pr.getFuerza(), indiceJugador);
	   Iterator<PosicionBalon> it = t.iterator();
       if ( it.hasNext() ){
    	   PosicionBalon positionB =  (PosicionBalon)it.next();
    	   for (int i=0 ; i<11 ; i++ ){
    		   Position posRival = sp.rivalPlayers()[i];
    		   if ( positionB.getPosicion().distance(posRival) <= Constants.DISTANCIA_CONTROL_BALON * 2 && 
    				positionB.getAltura() <= Constants.ALTURA_CONTROL_BALON)
    			   return true;
    	   }
 	   
       }
	   
	   return false;
   }

   public static boolean paseInterceptado(GameSituations sp, ParametrosPase pp){

	   Queue<PosicionBalon> t = crearTrayectoriaBalon(sp, pp.getAnguloHorizontal(), pp.getAngulovertical(), pp.getFuerza(), pp.getPase().getPasador());
	   Iterator<PosicionBalon> it = t.iterator();
       if ( it.hasNext() ){
    	   PosicionBalon positionB =  (PosicionBalon)it.next();
    	   for (int i=0 ; i<11 ; i++ ){
    		   Position posRival = sp.rivalPlayers()[i];
    		   if ( positionB.getPosicion().distance(posRival) <= Constants.DISTANCIA_CONTROL_BALON * 2 && 
    				positionB.getAltura() <= Constants.ALTURA_CONTROL_BALON)
    			   return true;
    	   }
 	   
       }
	   
	   return false;
   }

   public static ParametrosRemate obtenerDespeje(GameSituations sp, int indiceJugador){
	   int rival = rivalMasCercaDeBalon(sp);
	   Position posRival = sp.rivalPlayers()[rival];
	   Position posBalon = sp.ballPosition();
	   
	   double error = Constants.getErrorAngular(sp.myPlayersDetail()[indiceJugador].getPrecision() * sp.getMyPlayerEnergy(indiceJugador) );
       double anguloError = (error - error / 2) * Math.PI;
       double anguloH = 0;
       
       if ( posBalon.getX() > posRival.getX() ){
    	   anguloH = posBalon.angle(Constants.cornerInfDer) + anguloError;
       }else{
    	   anguloH = posBalon.angle(Constants.cornerInfIzq) - anguloError;
       }
	   
	   return new ParametrosRemate(anguloH, 35, 1, 1, new PosicionBalon(posBalon.moveAngle(anguloH, 50), 0), null, null);
   }
   
   public static boolean paseInterceptado(GameSituations sp, Queue<PosicionBalon> t, PosicionBalon p, int indiceJugador){
	  
	   boolean salir = false;
	   int iteracion = 1;
	   Position posReceptor = sp.myPlayers()[indiceJugador];
	   Iterator<PosicionBalon> it = t.iterator();
       while (it.hasNext() && !salir){
    	   PosicionBalon positionB =  (PosicionBalon)it.next();
    	   for (int i=0 ; i<11 ; i++ ){
    		   Position posRival = sp.rivalPlayers()[i];
    		   if ( iteracion > 1 &&
    				positionB.getPosicion().distance(posReceptor) > Constants.DISTANCIA_CONTROL_BALON + iteracion * 0.5 && 
    				positionB.getPosicion().distance(posRival) <= Constants.DISTANCIA_CONTROL_BALON * 3 && 
    				positionB.getAltura() <= Constants.ALTURA_CONTROL_BALON)
    			   return true;
    	   }
    	   
    	   iteracion++;
    	   if (positionB.equals(p))
    		   salir = true;
    	   
       }
	   
	   return false;
   }
   
   public static boolean tengoVelocidadMaxima(GameSituations sp, Queue<PosicionBalon> t, PosicionBalon p, int indiceJugador, Aceleracion aceleracion){
	  
	   
	   Position posReceptor = sp.myPlayers()[indiceJugador];
	   int iteracion = 1;
	   Position p0 = null;
	   Iterator<PosicionBalon> it = t.iterator();
       while (it.hasNext()){
    	   PosicionBalon positionB =  (PosicionBalon)it.next();
    	   if (p0 ==null)
    		   p0 = positionB.getPosicion();
    	   
    	   double distJugador = distanceTest(sp, indiceJugador, aceleracion, positionB.getPosicion(), iteracion, false);
    	   //Position posJug = posReceptor.moveAngle(posReceptor.angle(positionB.getPosicion()), distJugador);
    	   
    	   if (positionB.getPosicion().distance(posReceptor) <= Constants.DISTANCIA_CONTROL_BALON + distJugador && 
   				positionB.getAltura() <= Constants.ALTURA_CONTROL_BALON )
    		   if ( positionB.getPosicion().distance(p0) < 2 )
    			   return true;
    	   
    	   p0 = positionB.getPosicion();
    	   
       }
    	   
	   return false;
   }
   
   private static boolean comprobar(GameSituations sp, DatosGlobales datos,Position posPase, double anguloVertical, double fuerza, Pase pase, boolean isSprint){
	   
       int indiceJugador = pase.getReceptor();
       double anguloHorizontal = sp.ballPosition().angle(posPase);
       double error = Constants.getErrorAngular(sp.myPlayersDetail()[pase.getPasador()].getPrecision() * sp.getMyPlayerEnergy(pase.getPasador()) );
       double anguloError = (error - error / 2) * Math.PI;
       double angH1 = anguloHorizontal + anguloError;
       double angH2 = anguloHorizontal - anguloError;
       
       Position posJugador0 = sp.myPlayers()[pase.getReceptor()];
       double angPase = posJugador0.angle(posPase);
       Position posJugador1 = posJugador0.moveAngle(angPase, 15);

       Position posBalon0 = sp.ballPosition();
       Position posBalonN      = sp.ballPosition().moveAngle(anguloHorizontal, 15);
       Position posBalonError1 = sp.ballPosition().moveAngle(angH1, 15);
       Position posBalonError2 = sp.ballPosition().moveAngle(angH2, 15);
       
      // sp.distanceIter(playerIndex, iter, isSprint)
       Position intercepcionN = Position.Intersection(posJugador0, posJugador1, posBalon0, posBalonN);
       Position intercepcion1 = Position.Intersection(posJugador0, posJugador1, posBalon0, posBalonError1);
       Position intercepcion2 = Position.Intersection(posJugador0, posJugador1, posBalon0, posBalonError2);
       
       
       Queue<PosicionBalon> trayectoriaN = crearTrayectoriaBalon(sp, anguloHorizontal, anguloVertical, fuerza, pase.getPasador());
       Queue<PosicionBalon> trayectoria1 = crearTrayectoriaBalon(sp, angH1, anguloVertical, fuerza, pase.getPasador());
       Queue<PosicionBalon> trayectoria2 = crearTrayectoriaBalon(sp, angH2, anguloVertical, fuerza, pase.getPasador());

       PosicionBalon posPaseN = posicionEnLaTrayectoriaMasCercana(trayectoriaN,intercepcionN);
       PosicionBalon posPase1 = posicionEnLaTrayectoriaMasCercana(trayectoria1,intercepcion1);
       PosicionBalon posPase2 = posicionEnLaTrayectoriaMasCercana(trayectoria2,intercepcion2);
       
       if ( paseInterceptado(sp, trayectoriaN, posPaseN, indiceJugador)|| 
    		paseInterceptado(sp, trayectoria1, posPase1, indiceJugador) || 
    		paseInterceptado(sp, trayectoria2, posPase2, indiceJugador))
    	   return false; 
       
       PosicionBalon posMasLejana = null;
       Queue<PosicionBalon> trayectoriaMasLejana = null;
       //PosicionBalon posMasCercana = null;
       //Queue<PosicionBalon> trayectoriaMasCercana = null;
       
       if ( posPase1 == null ||  posPase2 == null )
    	   return false;
       
       if ( posJugador0.distance(posPase1.getPosicion()) > posJugador0.distance(posPase2.getPosicion())){
    	   posMasLejana = posPase1;
    	   trayectoriaMasLejana = trayectoria1;
    	 //  posMasCercana = posPase2;
    	  // trayectoriaMasCercana = trayectoria2;
       }else{
    	   posMasLejana = posPase2;
    	   trayectoriaMasLejana = trayectoria2;
    	  // posMasCercana = posPase1;
    	  // trayectoriaMasCercana = trayectoria1;
       }
    	   
       
       int iteracionesPosMasAlejada = iteracionPosicionEnLaTrayectoria(trayectoriaMasLejana, posMasLejana);
      // int iteracionesPosMasCercana = iteracionPosicionEnLaTrayectoria(trayectoriaMasCercana, posMasCercana);
       
       double distJugPosMasAlejada = posJugador0.distance(posMasLejana.getPosicion());
      // double distJugPosMasCercana = posJugador0.distance(posMasCercana.getPosicion());
       
       Position nuevaPosAlejada = posJugador0.moveAngle(posJugador0.angle(posMasLejana.getPosicion()), distJugPosMasAlejada+1);
      // Position nuevaPosCercana = posJugador0.moveAngle(posJugador0.angle(posMasCercana.getPosicion()), distJugPosMasCercana+1);
       
       Aceleracion aceleracion = datos.getAceleraciones()[indiceJugador];
       double distDirPosMasAlejada = distanceTest(sp, indiceJugador, aceleracion, nuevaPosAlejada, iteracionesPosMasAlejada, isSprint);
       //double distDirPosMasCercana = distanceTest(sp, indiceJugador, aceleracion, nuevaPosCercana, iteracionesPosMasCercana, isSprint);
       
       if (distDirPosMasAlejada > distJugPosMasAlejada &&
    		   posMasLejana.getAltura() < Constants.ALTURA_CONTROL_BALON && 
    		   posMasLejana.getPosicion().isInsideGameField(0)  /*&& 
    		   distJugPosMasCercana-distDirPosMasCercana <= Constants.DISTANCIA_CONTROL_BALON*/ /*&&
    		   tengoVelocidadMaxima(sp, trayectoriaMasCercana, posMasCercana, indiceJugador,aceleracion)*/)
    	   return true;
       else
    	   return false;     
       
   }

   public static double distanciaMinima(Queue<PosicionBalon> t){
       double distMin = Double.MAX_VALUE;
       Position p0 = null;

       Iterator<PosicionBalon> it = t.iterator();
       
       if (it.hasNext())
    	   p0 =  ((PosicionBalon)it.next()).getPosicion();
       
       while (it.hasNext()){
    	   Position p1 =  ((PosicionBalon)it.next()).getPosicion();
           if ( p0.distance(p1) < distMin ){
        	   distMin = p0.distance(p1);
               p0 = p1;
           }
      }
       
      return distMin;
   }
   
   public static PosicionBalon posicionEnLaTrayectoriaMasCercana(Queue<PosicionBalon> t, Position pos){
	   PosicionBalon p = null;
       double distMin = Double.MAX_VALUE;

       Iterator<PosicionBalon> it = t.iterator();
           while (it.hasNext()){
        	   PosicionBalon position =  (PosicionBalon)it.next();
               if ( pos.distance(position.getPosicion()) < distMin ){
                   distMin = pos.distance(position.getPosicion());
                   p = position;
               }
           }
       return p;
   }

   public static int iteracionPosicionEnLaTrayectoria(Queue<PosicionBalon> t, PosicionBalon pos){

       Iterator<PosicionBalon> it = t.iterator();
       int iteracion = 1;
           while (it.hasNext()){
        	   PosicionBalon position =  (PosicionBalon)it.next();
               if ( pos.getPosicion().getX() ==  position.getPosicion().getX() &&
            		   pos.getPosicion().getY() ==  position.getPosicion().getY() &&
            		   pos.getAltura() ==  position.getAltura())
                   return iteracion;
               
               iteracion++;
           }
       return 0;
   }



   
   public static Queue<PosicionBalon> crearTrayectoriaBalon(GameSituations sp, double anguloHorizontal, double anguloVertical, double fuerza, int indicePasador){
	   Queue<PosicionBalon> trayTemp = new LinkedList<PosicionBalon>();
	   
	   Position balon = sp.ballPosition();
       Position balon0 = balon;
       double altura0 = 0;
       double angH = anguloHorizontal;
       double remateJugador = sp.myPlayersDetail()[indicePasador].getPower();
       double vel = fuerza * Constants.getVelocidadRemate(remateJugador);
       
       double factorReduccion = sp.getMyPlayerEnergy(indicePasador);
       factorReduccion *= Constants.ENERGIA_DISPARO;
       if (factorReduccion > 1) factorReduccion = 1;
       
       vel *= factorReduccion;
       
       AbstractTrajectory trayectoria = new AirTrajectory(Math.cos(anguloVertical) * vel, Math.sin(anguloVertical) * vel, 0, 0);
       int iteracion = 1;
       double time1 = (double)iteracion/60d;
       double desplazamientoHorizontal = trayectoria.getX(time1) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
       double desplazamientoVertical = trayectoria.getY(time1) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
       double x = balon.getX() + desplazamientoHorizontal * Math.cos(angH);
       double y = balon.getY() + desplazamientoHorizontal * Math.sin(angH);
       Position balon1 = new Position(x, y);
       double altura1 = desplazamientoVertical * 2;
       boolean salir = false;
	   
       while ( !salir ){
    	   
           if ( !balon1.isInsideGameField(0) || balon1.equals(balon0) ){
               salir = true;
           }
           else{
               iteracion++;
               balon0 = balon1;
               altura0 = altura1;
               double time = (double)iteracion/60d;
               desplazamientoHorizontal = trayectoria.getX(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
               desplazamientoVertical   = trayectoria.getY(time) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
               x = balon.getX() + desplazamientoHorizontal * Math.cos(angH);
               y = balon.getY() + desplazamientoHorizontal * Math.sin(angH);
               balon1  = new Position(x, y);
               altura1 = desplazamientoVertical * 2;
               trayTemp.add(new PosicionBalon( balon0, altura0));
           }
       }	   
	   
	   return trayTemp;
   }
   
   
   public static boolean balonEnPosicionOfensiva(GameSituations sp){
	   Position balon = sp.ballPosition();
	   return ( (balon.isInsideGameField(0) && balon.getY() > 0) ) ? true : false;
   }
   
   public static double anguloDesmarque(GameSituations sp, int indiceJugador){
	   int indiceRivalMasCerca = rivalMasCercaDelJugador(sp, indiceJugador);
	   Position posicionJugador = sp.myPlayers()[indiceJugador];
	   Position posicionRival = sp.rivalPlayers()[indiceRivalMasCerca];
	   double angulo = posicionJugador.angle(posicionRival);
	   
	   return angulo-Math.PI;
   }

}
