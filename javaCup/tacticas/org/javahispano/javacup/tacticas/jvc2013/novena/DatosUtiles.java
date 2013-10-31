package org.javahispano.javacup.tacticas.jvc2013.novena;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author yoemny
 */
public class DatosUtiles {

    public static final int DESCONOCIDO   = 0;
    public static final int ATACANDO      = 1;
    public static final int DEFENDIENDO   = 2;
    
    public static final int PASE_SALIDA_BALON   	= 1;
    public static final int PASE_ARMAR_JUGADA      	= 2;
    public static final int PASE_CREANDO_ASISTENCIA	= 3;
    public static final int PASE_ASISTENCIA   		= 4;
    

    
    public static final int defensas[] = new int[]{1,2,0,3,5,6};
    public static final int pivotes[] = new int[]{5,6};
    public static final int delanteros[] = new int[]{7,8,9,10};
    public static final int receptores[] = new int[]{5,6,7,8,9,10};
    
    public static final int portero   = 4;
    public static final int delanteroCentro   	= 10;
    public static final int delanteroIzquierda  = 8;
    public static final int delanteroDerecha   	= 9;
    public static final int delanteroEnlace   	= 7;
    public static final int pivoteDerecha   	= 6;
    public static final int pivoteIzquierda   	= 5;
    public static final int lateralDerecha   	= 3;
    public static final int lateralIzquierda   	= 0;
    public static final int centralDerecha   	= 2;
    public static final int centralIzquierda   	= 1;
    
    public static final MovimientoJugador[] movimientos = new MovimientoJugador[]{

    	
    	new MovimientoJugador(delanteroCentro, delanteroIzquierda, -170, 25),
    	new MovimientoJugador(delanteroCentro, delanteroDerecha, -10, 25),
    	new MovimientoJugador(delanteroCentro, delanteroEnlace, -90, 10),

    	new MovimientoJugador(delanteroIzquierda, delanteroDerecha, 10, 40),
    	new MovimientoJugador(delanteroIzquierda, delanteroEnlace, -55, 10),
    	new MovimientoJugador(delanteroIzquierda, delanteroCentro, 70, 35),

    	new MovimientoJugador(delanteroDerecha, delanteroIzquierda, 170, 40),
    	new MovimientoJugador(delanteroDerecha, delanteroEnlace, -145, 10),
    	new MovimientoJugador(delanteroDerecha, delanteroCentro, 110, 35),

    	new MovimientoJugador(delanteroEnlace, delanteroIzquierda, 135, 25),
    	new MovimientoJugador(delanteroEnlace, delanteroDerecha, 45, 25),
    	new MovimientoJugador(delanteroEnlace, delanteroCentro, 90, 40),
    	
    	
    	new MovimientoJugador(pivoteDerecha, delanteroIzquierda, 120, 25),
    	new MovimientoJugador(pivoteDerecha, delanteroDerecha, 80, 15),
    	new MovimientoJugador(pivoteDerecha, delanteroEnlace, 90, 15),
    	new MovimientoJugador(pivoteDerecha, delanteroCentro, 90, 30),
    	
    	
    	new MovimientoJugador(pivoteIzquierda, delanteroIzquierda, 110, 15),
    	new MovimientoJugador(pivoteIzquierda, delanteroDerecha, 60, 25),
    	new MovimientoJugador(pivoteIzquierda, delanteroEnlace, 90, 15),
    	new MovimientoJugador(pivoteIzquierda, delanteroCentro, 90, 30),
    	
    	new MovimientoJugador(centralDerecha, delanteroIzquierda, 120, 45),
    	new MovimientoJugador(centralDerecha, delanteroDerecha, 60, 45),
    	new MovimientoJugador(centralDerecha, delanteroEnlace, 85, 45),
    	new MovimientoJugador(centralDerecha, delanteroCentro, 85, 50),

    	
    	new MovimientoJugador(centralIzquierda, delanteroIzquierda, 110, 45),
    	new MovimientoJugador(centralIzquierda, delanteroDerecha, 70, 45),
    	new MovimientoJugador(centralIzquierda, delanteroEnlace, 95, 45),
    	new MovimientoJugador(centralIzquierda, delanteroCentro, 95, 50),

    	new MovimientoJugador(lateralDerecha, delanteroIzquierda, 120, 45),
    	new MovimientoJugador(lateralDerecha, delanteroDerecha, 90, 35),
    	new MovimientoJugador(lateralDerecha, delanteroEnlace, 95, 45),
    	new MovimientoJugador(lateralDerecha, delanteroCentro, 95, 50),

    	new MovimientoJugador(lateralIzquierda, delanteroIzquierda, 110, 45),
    	new MovimientoJugador(lateralIzquierda, delanteroDerecha, 70, 45),
    	new MovimientoJugador(lateralIzquierda, delanteroEnlace, 85, 45),
    	new MovimientoJugador(lateralIzquierda, delanteroCentro, 85, 50),

    	new MovimientoJugador(portero, delanteroIzquierda, 120, 55),
    	new MovimientoJugador(portero, delanteroDerecha, 60, 55),
    	new MovimientoJugador(portero, delanteroEnlace, 90, 45),
    	new MovimientoJugador(portero, delanteroCentro, 90, 60)
    	
    	
    };
    
    public static final Pase[] pases = new Pase[]{
    	new Pase(4, 7, Pase.PASE_JUGADOR),
    	new Pase(4, 8, Pase.PASE_JUGADOR),
    	new Pase(4, 9, Pase.PASE_JUGADOR),
    	new Pase(0, 8, Pase.PASE_JUGADOR),
    	new Pase(0, 7, Pase.PASE_JUGADOR),
    	new Pase(1, 7, Pase.PASE_JUGADOR),
    	new Pase(1, 8, Pase.PASE_JUGADOR),
    	new Pase(1, 9, Pase.PASE_JUGADOR),
    	new Pase(2, 7, Pase.PASE_JUGADOR),
    	new Pase(2, 8, Pase.PASE_JUGADOR),
    	new Pase(2, 9, Pase.PASE_JUGADOR),
    	new Pase(3, 9, Pase.PASE_JUGADOR),
    	new Pase(3, 7, Pase.PASE_JUGADOR),
    	new Pase(5, 7, Pase.PASE_JUGADOR),
    	new Pase(5, 8, Pase.PASE_ESPACIO),
    	new Pase(5, 9, Pase.PASE_ESPACIO),
    	new Pase(6, 7, Pase.PASE_JUGADOR),
    	new Pase(6, 8, Pase.PASE_ESPACIO),
    	new Pase(6, 9, Pase.PASE_ESPACIO),
    	new Pase(7, 8, Pase.PASE_ESPACIO),
    	new Pase(7, 9, Pase.PASE_ESPACIO),
    	new Pase(7, 10,Pase.PASE_JUGADOR),
    	new Pase(8, 7, Pase.PASE_JUGADOR),
    	new Pase(8, 10,Pase.PASE_ESPACIO),
    	new Pase(9, 7, Pase.PASE_JUGADOR),
    	new Pase(9, 10,Pase.PASE_ESPACIO),
    	new Pase(10, 7,Pase.PASE_JUGADOR),
    	new Pase(10, 8,Pase.PASE_ESPACIO),
    	new Pase(10, 9,Pase.PASE_ESPACIO)
    };

    
    public static MovimientoJugador obtenerMovimiento(int referencia, int indiceJugador){
    	
    	for(int i=0 ; i<movimientos.length ; i++)
    		if (movimientos[i].getIndiceJugadorRefencia() == referencia && movimientos[i].getIndiceJugadorMueve() == indiceJugador)
    			return movimientos[i];
    	
    	return null;
    }
    /**
	 * @return the receptores
	 */
	public static int[] getReceptores() {
		return receptores;
	}
	public static Pase[] obtenerPosiblesPases(){
    	return pases;
    }
    public static int indicePortero(){
        return portero;
    }

    public static int[] indicesDefensas(){
        return defensas;
    }

    public static int[] indicesPivotes() {
		return pivotes;
	}

	public static int[] indicesDelanteros() {
		return delanteros;
	}

	public static int getDelanterocentro() {
		return delanteroCentro;
	}

	public static int getDelanteroizquierda() {
		return delanteroIzquierda;
	}

	public static int getDelanteroderecha() {
		return delanteroDerecha;
	}

	public static int getDelanteroenlace() {
		return delanteroEnlace;
	}
	
	public Position[] getNormalPositions(GameSituations sp) {
		return Alineaciones.getAlineacion(0).getPosiciones();
	}
	
	public static boolean esDefensa(int indiceJugador){
        if (  indiceJugador== 0 || indiceJugador== 1 || indiceJugador== 2 || indiceJugador== 3  )
            return true;
        return false;
    }
	
	public static boolean esPivote(int indiceJugador){
        if (  indiceJugador== 5 || indiceJugador== 6 )
            return true;
        return false;
    }	
	public static boolean esDelantero(int indiceJugador){
        if (  indiceJugador== 7 || indiceJugador== 8 || indiceJugador== 9 || indiceJugador== 10  )
            return true;
        return false;
    }

	public static boolean esDelanteroCentro(int indiceJugador){
        if (  indiceJugador== 10  )
            return true;
        return false;
    }

	public static boolean esPortero(int indiceJugador){
        if (  indiceJugador== 4  )
            return true;
        return false;
    }
	
    public static int indiceporteroRival(){
        return 0;
    }


    public static final double LINEA_DEFENSIVA_DEFENSAS = -20;
    public static final double LINEA_DEFENSIVA_PIVOTES = 0;
    
    private static final double bordesDefensivos[] = new double[]{
        -(Constants.ANCHO_CAMPO_JUEGO/2),
        -(Constants.ANCHO_CAMPO_JUEGO/4),
        0,
        Constants.ANCHO_CAMPO_JUEGO/4,
        Constants.ANCHO_CAMPO_JUEGO/2
        
        };


    private static Zona   zonasDefensivas[] = new Zona[]{
        
        //zona lateral izquierdo
        new Zona(new Position(bordesDefensivos[0], LINEA_DEFENSIVA_DEFENSAS),
        new Position(bordesDefensivos[2], LINEA_DEFENSIVA_DEFENSAS),
        new Position(bordesDefensivos[0], Constants.centroArcoInf.getY()),
        new Position(bordesDefensivos[2], Constants.centroArcoInf.getY())),
        //zona central izquierdo
        new Zona(new Position(bordesDefensivos[0], LINEA_DEFENSIVA_DEFENSAS),
        new Position(bordesDefensivos[3], LINEA_DEFENSIVA_DEFENSAS),
        new Position(bordesDefensivos[0], Constants.centroArcoInf.getY()),
        new Position(bordesDefensivos[3], Constants.centroArcoInf.getY())),
        //zona central derecho
        new Zona(new Position(bordesDefensivos[1], LINEA_DEFENSIVA_DEFENSAS),
        new Position(bordesDefensivos[4], LINEA_DEFENSIVA_DEFENSAS),
        new Position(bordesDefensivos[1], Constants.centroArcoInf.getY()),
        new Position(bordesDefensivos[4], Constants.centroArcoInf.getY())),
        //zona lateral derecho
        new Zona(new Position(bordesDefensivos[2], LINEA_DEFENSIVA_DEFENSAS),
        new Position(bordesDefensivos[4], LINEA_DEFENSIVA_DEFENSAS),
        new Position(bordesDefensivos[2], Constants.centroArcoInf.getY()),
        new Position(bordesDefensivos[4], Constants.centroArcoInf.getY())),
        //pa llenar
        null,
        //zona pivote izquierdo
        new Zona(new Position(bordesDefensivos[0], LINEA_DEFENSIVA_PIVOTES),
        new Position(bordesDefensivos[3], LINEA_DEFENSIVA_PIVOTES),
        new Position(bordesDefensivos[0], LINEA_DEFENSIVA_DEFENSAS-5),
        new Position(bordesDefensivos[3], LINEA_DEFENSIVA_DEFENSAS-5)),
        //zona pivote derecho
        new Zona(new Position(bordesDefensivos[1], LINEA_DEFENSIVA_PIVOTES),
        new Position(bordesDefensivos[4], LINEA_DEFENSIVA_PIVOTES),
        new Position(bordesDefensivos[1], LINEA_DEFENSIVA_DEFENSAS-5),
        new Position(bordesDefensivos[4], LINEA_DEFENSIVA_DEFENSAS-5)),
    };
    
    public static Zona obtenerZonaDisparo0(){
    	Position referencia = new Position( - ( Constants.LARGO_AREA_CHICA/2 ),
    			Constants.LARGO_CAMPO_JUEGO/2 - Constants.ANCHO_AREA_CHICA );
    	return new Zona(new Position(referencia.getX(), referencia.getY()),
    	        		new Position(referencia.getX()+ ( Constants.LARGO_AREA_CHICA ), referencia.getY()),
    	        		new Position(referencia.getX(), referencia.getY() - 10),
    	        		new Position(referencia.getX()+ ( Constants.LARGO_AREA_GRANDE ), referencia.getY() - 10));
    }
    
    public static Zona obtenerZonaDisparo1(){
    	Position referencia = new Position( - ( Constants.LARGO_AREA_GRANDE/2 - 9),
    			Constants.LARGO_CAMPO_JUEGO/2 - 3);
    	return new Zona(new Position(referencia.getX(), referencia.getY()),
    	        		new Position(referencia.getX()+2*( Constants.LARGO_AREA_GRANDE/2 - 9), referencia.getY()),
    	        		new Position(referencia.getX(), referencia.getY()-Constants.ANCHO_AREA_GRANDE - 5),
    	        		new Position(referencia.getX()+2*( Constants.LARGO_AREA_GRANDE/2 - 9), referencia.getY()-Constants.ANCHO_AREA_GRANDE - 5));
    }

    public static Zona obtenerZonaDisparo2(){
    	Position referencia = new Position( -(Constants.LARGO_AREA_CHICA/2 + 1),
    			Constants.LARGO_CAMPO_JUEGO/2 -Constants.ANCHO_AREA_GRANDE);
    	return new Zona(new Position(referencia.getX(), referencia.getY()),
    	        		new Position(referencia.getX()+2*(Constants.LARGO_AREA_CHICA/2+1), referencia.getY()),
    	        		new Position(referencia.getX(), referencia.getY()-25),
    	        		new Position(referencia.getX()+2*(Constants.LARGO_AREA_CHICA/2+1), referencia.getY()-25));
    }
    
    public static Zona getZonaDefensiva(int indiceJugador){
        if (indiceJugador < zonasDefensivas.length)
            return zonasDefensivas[indiceJugador];
        else
            return null;

    }

    public static boolean estaEnZonaDefensiva(int indiceJugador, Position posicion){
        if (zonasDefensivas[indiceJugador].estaEnLaZona(posicion))
            return true;
        else
            return false;
    }

    public static boolean estaEnZonaDefensiva(int indiceJugador, PosicionBalon posicionBalon){
        if (zonasDefensivas[indiceJugador].estaEnLaZona(posicionBalon.getPosicion()))
            return true;
        else
            return false;
    }

    public static boolean estaEnMiArea(Position posicion) {
    	if(Math.abs(posicion.getX()) <= Constants.LARGO_AREA_GRANDE/2 &&
            posicion.getY() <= Constants.centroArcoInf.getY()+Constants.ANCHO_AREA_GRANDE)
            return true;
    	return false;
    }

    public static boolean estaEnMiAreaChica(Position posicion) {
    	if(Math.abs(posicion.getX()) <= Constants.LARGO_AREA_CHICA/2 &&
            posicion.getY() <= Constants.centroArcoInf.getY()+Constants.ANCHO_AREA_CHICA)
            return true;
    	return false;
    }

    private static final Position puntoReferenciaDefensa = new Position(-(Constants.ANCHO_CAMPO_JUEGO / 2 - Constants.LARGO_AREA_GRANDE / 2), Constants.centroArcoInf.getY()+ Constants.ANCHO_AREA_GRANDE);

    private static Position    posicionesDefensivas[] = new Position[] {
        //posicion lateral izquierdo
        new Position(puntoReferenciaDefensa),
        //posicion central izquierdo
        new Position(puntoReferenciaDefensa.getX()/2,puntoReferenciaDefensa.getY()),
        //posicion central derecho
        new Position(puntoReferenciaDefensa.getX()/2*-1,puntoReferenciaDefensa.getY()),
        //posicion lateral derecho
        new Position(puntoReferenciaDefensa.getX()*-1,puntoReferenciaDefensa.getY()),
        //pa llenar
        new Position(0,0),
        //pa llenar
        new Position(0,0),
        //posicion interior izquierdo
        new Position(-15,-15),
        //posicion interior derecho
        new Position(15,-15),
        //posicion delantero izquierdo
        new Position(-10,0),
        //posicion pivot
        new Position(0,10),
        //posicion delantero derecho
        new Position(10,0),

    };

    public static Position getPosicionDefensiva(int indiceJugador){
        if (indiceJugador < posicionesDefensivas.length)
            return posicionesDefensivas[indiceJugador];
        else
            return null;

    }

    public static final LimiteTerreno lineaSuperior     = new LimiteTerreno(Constants.cornerSupIzq, Constants.cornerSupDer);
    public static final LimiteTerreno lineaInferior     = new LimiteTerreno(Constants.cornerInfIzq ,Constants.cornerInfDer);
    public static final LimiteTerreno lineaDerecha      = new LimiteTerreno(Constants.cornerSupDer, Constants.cornerInfDer);
    public static final LimiteTerreno lineaIzquierda    = new LimiteTerreno(Constants.cornerSupIzq, Constants.cornerInfIzq);


    
}
