package org.javahispano.javacup.tacticas.jvc2013.novena;

import org.javahispano.javacup.model.util.Position;

/**
 *
 * @author yoemny
 */
public class PosicionBalon {

    private Position    posicion;
    private double      altura;


    public PosicionBalon(Position posicion, double altura) {
        this.posicion = posicion;
        this.altura = altura;
    }

    public PosicionBalon(double x, double y, double altura) {
        this.posicion = new Position(x, y);
        this.altura = altura;
    }

    public Position getPosicion() {
        return this.posicion;
    }

    public double getAltura() {
        return altura;
    }

    @Override
    public String toString() {
        return "Posicion del Balon{" + posicion + ":" + altura + '}';
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(altura);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((posicion == null) ? 0 : posicion.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PosicionBalon)) {
			return false;
		}
		PosicionBalon other = (PosicionBalon) obj;
		if (Double.doubleToLongBits(altura) != Double
				.doubleToLongBits(other.altura)) {
			return false;
		}
		if (posicion == null) {
			if (other.posicion != null) {
				return false;
			}
		} else if (!posicion.equals(other.posicion)) {
			return false;
		}
		return true;
	}
    

}
