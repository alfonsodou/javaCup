package org.javahispano.javacup.model.util;

import java.io.Serializable;

/**Class representing horizontal spatial coordinates
 * Clase que representa las coordenadas espaciales horizontales*/
public final class Position implements Serializable {

    private final double x, y;
    private transient static final Position cero = new Position(0, 0);

    /**Creates a new position in 0,0*/
    /**Instancia una nueva posicion en 0,0*/
    public Position() {
        x = 0;
        y = 0;
    }

    /**Creates a new position copying from another one*/
    /**Instancia una nueva posicion copiando de otra posicion*/
    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    /**Move in angle and ratio*/
    /**Mueve en un angle y radio*/
    public Position moveAngle(double angle, double ratio) {
        return new Position(x + Math.cos(angle) * ratio, y + Math.sin(angle) * ratio);
    }

    /**Move in an angle and ratio, with maxRatio as limit*/
    /**Mueve en un angle y un radio, limitando solo a avanzar no mas de un radio maximo*/
    public Position moveAngle(double angle, double ratio, double maxRatio) {
        return new Position(x + Math.cos(angle) * Math.min(ratio, maxRatio), y + Math.sin(angle) * Math.min(ratio, maxRatio));
    }

    /**Move delta in x and y*/
    /**Mueve un delta x y delta y*/
    public Position movePosition(double dx, double dy) {
        return new Position(x + dx, y + dy);
    }

    /**Move delta in x and y, limited by a max ratio*/
    /**Mueve un delta x y delta y, limitando por un radio maximo*/
    public Position movePosition(double dx, double dy, double maxRatio) {
        Position dest = new Position(x + dx, y + dy);
        double angle = angle(dest);
        double ratio = distance(dest);
        return new Position(x + Math.cos(angle) * Math.min(ratio, maxRatio), y + Math.sin(angle) * Math.min(ratio, maxRatio));
    }

    /**Creates a new position*/
    /**Instancia una nueva posicion*/
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**Get x coordinate*/
    /**Obtiene la coordenada x*/
    public double getX() {
        return x;
    }

    /**Get y coordinate*/
    /**Obtiene la coordenada y*/
    public double getY() {
        return y;
    }

    /**Set the position equals to another one, but inverted in axis y*/
    /**Establece la posicion igual a otra posicion, pero invertida en el eje Y*/
    public Position getInvertedPosition() {
        return new Position(-x, -y);
    }

    /**Set the position*/
    /**Establece la posicion*/
    public Position setPosition(double x, double y) {
        return new Position(x, y);
    }

    /**Indicates if the position is inside the game field*/
    /**Indica si la posicion esta dentro del campo de juego*/
    public boolean isInsideGameField(double mas) {
        double mx = Constants.ANCHO_CAMPO_JUEGO / 2 + mas;
        double my = Constants.LARGO_CAMPO_JUEGO / 2 + mas;
        return Math.abs(x) <= mx && Math.abs(y) <= my;
    }

    /**Limit the position to be inside the game field*/
    /**Limita la posicion a estar dentro del campo de juego*/
    public Position setInsideGameField() {
        double mx = Constants.ANCHO_CAMPO_JUEGO / 2;
        double my = Constants.LARGO_CAMPO_JUEGO / 2;
        double x0 = x, y0 = y;
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

    /**Returns the angle between two positions*/
    /**Obtiene el angle entre dos posiciones*/
    public double angle(Position p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return Math.atan2(dy, dx);
    }

    /**Returns the distance between two positions
     */
    /**Obtiene la distancia entre dos posiciones
     */
    public double distance(Position p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }

    /**Returns the index of the nearest position to this position*/
    /**Obtiene el indice de la posicion mas cercana a esta posicion*/
    public int nearestIndex(Position[] pos) {
        double max = Double.MAX_VALUE;
        double dist;
        int idx = -1;
        for (int i = 0; i < pos.length; i++) {
            dist = norm(pos[i]);
            if (dist < max) {
                max = dist;
                idx = i;
            }
        }
        return idx;
    }

    /**Returns the index of the nearest position to this position, excluding a list*/
    /**Obtiene el indice de la posicion mas cercana a esta posicion, excluyendo una lista*/
    public int nearestIndex(Position[] pos, int... exclude) {
        double max = Double.MAX_VALUE;
        double dist;
        int idx = -1;
        boolean found;
        for (int i = 0; i < pos.length; i++) {
            dist = norm(pos[i]);
            found = false;
            for (int k : exclude) {
                if (k == i) {
                    found = true;
                    break;
                }
            }
            if (!found && dist < max) {
                max = dist;
                idx = i;
            }

        }
        return idx;
    }

    /**Returns a sorted array of the nearest indexes to the more distant*/
    /**Obtiene un arreglo ordenado de los indices mas cercanos a los mas lejanos*/
    public int[] nearestIndexes(Position[] pos) {
        int[] tmp = new int[pos.length];
        double[] dst = new double[pos.length];
        for (int i = 0; i < pos.length; i++) {
            tmp[i] = i;
            dst[i] = this.norm(pos[i]);
        }
        int ii;
        double dd;
        for (int i = 0; i < pos.length; i++) {
            for (int j = i + 1; j < pos.length; j++) {
                if (dst[i] > dst[j]) {
                    dd = dst[j];
                    dst[j] = dst[i];
                    dst[i] = dd;
                    ii = tmp[j];
                    tmp[j] = tmp[i];
                    tmp[i] = ii;
                }
            }
        }
        return tmp;
    }

    /**Returns a sorted array of the nearest indexes to the more distant, exlcuding a list*/
    /**Obtiene un arreglo ordenado de los indices mas cercanos a los mas lejanos, excluyendo una lista*/
    public int[] nearestIndexes(Position[] pos, int... exclude) {
        int[] tmp = new int[pos.length];
        double[] dst = new double[pos.length];
        boolean found;
        for (int i = 0; i < pos.length; i++) {
            tmp[i] = i;
            found = false;
            for (int k : exclude) {
                if (k == i) {
                    dst[i] = Integer.MAX_VALUE;
                    found = true;
                    break;
                }
            }
            if (!found) {
                dst[i] = this.norm(pos[i]);
            }
        }
        int ii;
        double dd;
        for (int i = 0; i < pos.length; i++) {
            for (int j = i + 1; j < pos.length; j++) {
                if (dst[i] > dst[j]) {
                    dd = dst[j];
                    dst[j] = dst[i];
                    dst[i] = dd;
                    ii = tmp[j];
                    tmp[j] = tmp[i];
                    tmp[i] = ii;
                }
            }
        }
        return tmp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**Returns the intersection between two lines*/
    /**Retorna la interseccion entre 2 rectas*/
    public static Position Intersection(Position n1, Position n2, Position m1, Position m2) {
        double a1 = n1.y - n2.y;
        double b1 = n2.x - n1.x;
        double c1 = n1.x * n2.y - n2.x * n1.y;

        double a2 = m1.y - m2.y;
        double b2 = m2.x - m1.x;
        double c2 = m1.x * m2.y - m2.x * m1.y;

        double d = a1 * b2 - a2 * b1;
        double d1 = -c1 * b2 + c2 * b1;
        double d2 = -a1 * c2 + a2 * c1;

        if (d != 0) {
            return new Position(d1 / d, d2 / d);
        } else {
            return null;
        }
    }

    /**Returns the middle point*/
    /**Retorna el punto medio*/
    public static Position medium(Position n1, Position n2) {
        return new Position((n1.x + n2.x) / 2, (n1.y + n2.y) / 2);
    }

    /**Returns the angle of the position in relation to point 0,0*/
    /**Retorna el angle de la posicion respecto del 0,0*/
    public double angle() {
        return cero.angle(this);
    }

    /**Returns the distance of the position in relation to point 0,0*/
    /**Retorna la distancia de la posicion respecto del 0,0*/
    public double distance() {
        return cero.distance(this);
    }

    /**Define a norm to compare distance, the calculation is fatster than use distance methods*/
    /**Define una norma para comparar distancias, es mas rapido el calculo que usar el metodos distancia*/
    public double norm() {
        return x * x + y * y;
    }

    /**Define una norma para comparar distancias, es mas rapido el calculo que usar el metodos distancia*/
    public double norm(Position p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return dx * dx + dy * dy;
    }

    @Override
    public String toString() {
        return "" + x + ":" + y;
    }
}
