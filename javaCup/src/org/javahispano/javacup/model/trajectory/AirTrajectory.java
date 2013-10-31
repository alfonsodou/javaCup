package org.javahispano.javacup.model.trajectory;

public class AirTrajectory extends AbstractTrajectory {

    /**Aceleracion de la gravedad*/
    private final static double g = 9.8;
    /***/
    private final static double k = 0.7;
    /***/
    private final static double n = 1.5;
    /***/
    private final static double ay = 2.3;
    final AbstractTrajectory nextTrayectory;

    public AirTrajectory(double vx0, double vy0, double x0, double y0) {
        super(vx0, vy0, x0, y0);
        x0 = getX(dt);
        y0 = getY(dt);
        vx0 = (getX(dt) - getX(dt - .1)) * 4;
        vy0 = (getY(dt - .1) - getY(dt)) * 3;
        if (vy0 > 0.1) {
            nextTrayectory = new AirTrajectory(vx0, vy0, x0, y0);
        } else {
            nextTrayectory = new FloorTrajectory(Math.sqrt(vx0 * vx0 + vy0 * vy0) * 2, x0);
        }
    }

    @Override
    public double getX(double t) {
        if (t > dt && nextTrayectory != null) {
            return nextTrayectory.getX(t - dt);
        } else {
            //Aplica esta formula de acuerdo a los parametros iniciales
            return vx0 * (1 - Math.exp(-k * n * t)) / k + vx0 * t + x0;
        }
    }

    @Override
    public double getY(double t) {
        if (t > dt && nextTrayectory != null) {
            return nextTrayectory.getY(t - dt);
        } else {
            //Aplica esta formula de acuerdo a los parametros iniciales
            return y0 + vy0 * ay * t - g * t * t / 2d;
        }
    }

    @Override
    public double getDt() {
        double b = (vy0 * ay);
        double a = g / 2;
        if (b * b > -4 * a * y0) {
            double ta = (b + Math.sqrt(b * b + 4 * a * y0)) / 2 / a;
            double tb = (b - Math.sqrt(b * b + 4 * a * y0)) / 2 / a;
            return Math.max(ta, tb);
        } else {
            return 0;
        }
    }
}
