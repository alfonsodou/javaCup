package org.javahispano.javacup.tacticas.jvc2013.txami;


public class Utils {

	public static final int SAGU = 0;
	public static final int BOMBO = 1;
	public static final int PEPI = 2;
	public static final int LLANO = 3;
	public static final int PITXE = 4;
	public static final int PITI = 5;
	public static final int PIKI = 6;
	public static final int GUTXO = 7;
	public static final int SAEZ = 8;
	public static final int KUIK = 9;
	public static final int LARGUI = 10;

	/**
	 * Devuelve si el jugador pasado (indice) es Defensa
	 * 
	 * @param i
	 * @return
	 */
	public static boolean esDefensa(int i) {

		switch (i) {
			case BOMBO:
				return true;
			case PEPI:
				return true;
			case PITXE:
				return true;
			case LLANO:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Devuelve si el jugador pasado (indice) es Medio
	 * 
	 * @param i
	 * @return
	 */
	public static boolean esMedio(int i) {

		switch (i) {
			case GUTXO:
				return true;
			case PIKI:
				return true;
			case PITI:
				return true;
			default:
				return false;
		}

	}
	
	/**
	 * Devuelve si el jugador pasado (indice) es Medio
	 * 
	 * @param i
	 * @return
	 */
	public static boolean esMediapunta(int i) {

		switch (i) {
			case SAEZ:
				return true;			
			default:
				return false;
		}

	}

	/**
	 * Devuelve si el jugador pasado (indice) es Delantero
	 * 
	 * @param i
	 * @return
	 */
	public static boolean esDelantero(int i) {

		switch (i) {
			case SAGU:
				return true;
			case KUIK:
				return true;
			default:
				return false;
		}

	}

}
