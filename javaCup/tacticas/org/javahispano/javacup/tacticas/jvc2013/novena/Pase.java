package org.javahispano.javacup.tacticas.jvc2013.novena;


public class Pase {

	public static int PASE_ESPACIO 	= 1;
	public static int PASE_JUGADOR 	= 2;
	public static int AUTO_PASE 	= 3;
	
	private Integer pasador;
	private Integer receptor;
	private int tipoPase;
	

	public Pase(Integer pasador, Integer receptor,
			int tipoPase) {
		super();
		this.pasador = pasador;
		this.receptor = receptor;
		this.tipoPase = tipoPase;
	}

	public Integer getPasador() {
		return pasador;
	}

	public void setPasador(Integer pasador) {
		this.pasador = pasador;
	}

	public Integer getReceptor() {
		return receptor;
	}

	public void setReceptor(Integer receptor) {
		this.receptor = receptor;
	}

	public boolean esPaseEspacio(){
		return (tipoPase == PASE_ESPACIO) ? true : false;
	}
	
	public boolean esPaseJugador(){
		return (tipoPase == PASE_JUGADOR) ? true : false;
	}
	
	public boolean esAutoPase(){
		return (tipoPase == AUTO_PASE) ? true : false;
	}
	
}
