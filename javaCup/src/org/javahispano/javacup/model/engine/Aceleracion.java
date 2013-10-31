package org.javahispano.javacup.model.engine;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Aceleracion {

	
	//  Aceleración en el eje X e Y, valor entre 0 y 1
	
	private double aceleracionX = 0;
	private double aceleracionY = 0;
		
	//Ultima posicion del jugador donde se calculo la aceleracion
	private Position posJugador = new Position();
	
	//Dirección en el eje X e Y. Valores -1, 0 (DETENIDO) y 1
	
	private double direccionX = 0;
	private double direccionY = 0;
		
	protected Aceleracion(){
		
		aceleracionX = 1;
		aceleracionY = 1;
	}
	
	protected Aceleracion(float x, float y) {
	
		aceleracionX = x;
		aceleracionY = y;		
		
	}
	
	public void actualizar(Position posActual) {
		
		//Guardamos las direcciones actuales
		double anteriorDireccionX = direccionX;
		double anteriorDireccionY = direccionY;
		
		//Calculamos las direcciones nuevas
		double Dx = posActual.getX() - posJugador.getX();
		direccionX = (Dx == 0) ? 0 : (Math.signum(Dx)); 
			
		double Dy = posActual.getY() - posJugador.getY();
		direccionY = (Dy == 0) ? 0 : (Math.signum(Dy));
		
		//Si ha habido algun cambio en alguna de ellas bajamos su aceleracion sino la incrementamos
		if (anteriorDireccionX != direccionX)
			aceleracionX = Constants.ACELERACION_MINIMA_X;
		else {
			aceleracionX += Constants.ACELERACION_INCR;
			if (aceleracionX > 1) aceleracionX = 1; //Máximo de la acelaracion
		}
		
		if (anteriorDireccionY != direccionY)
			aceleracionY = Constants.ACELERACION_MINIMA_Y;
		else {
			aceleracionY += Constants.ACELERACION_INCR;
			if (aceleracionY > 1) aceleracionY = 1; //Máximo de la acelaracion
		}
		
		//Actualizamos la posicion del jugador
		posJugador = new Position(posActual.getX(), posActual.getY());		
	}
	
	public double obtenerAceleracionX(){
		return aceleracionX;
	}
	
	public double obtenerAceleracionY(){
		return aceleracionY;
	}
	
	/** Devuelve la aceleracion global compuesta por la de ambos ejes**/
	public double obtenerAceleracionGlobal(){
		return aceleracionX * aceleracionY;
	}
	
}
