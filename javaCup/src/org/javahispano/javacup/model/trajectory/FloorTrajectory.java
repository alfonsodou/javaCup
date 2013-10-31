package org.javahispano.javacup.model.trajectory;

public class FloorTrajectory extends AbstractTrajectory {

    /***/
    private final static double k = 4;
    /**Distancia final recorrida por el suelo*/
    private final double xf;

    public FloorTrajectory(double vx0, double x0) {
        super(vx0, 0, x0, 0);
        xf = getX(dt);
    }

    public double getX(double t) {
        if (t > dt) {
            return xf;
        } else {
            //Aplica esta formula de acuerdo a los parametros iniciales
            return vx0 * t - k * t * t /2 + x0;
        }
    }

    public double getY(double t) {
        return 0;
    }

    public double getDt() {
        return vx0 / k;
    }
}
