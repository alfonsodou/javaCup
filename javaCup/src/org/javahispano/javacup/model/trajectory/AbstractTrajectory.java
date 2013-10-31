package org.javahispano.javacup.model.trajectory;

/**
 * Clase que al extenderla permite definir trayectorias por tramos.
 * Luego de manera recursiva se obtiene el valor de la trayectoria en
 * cualquier instante de tiempo futuro.
 */
public abstract class AbstractTrajectory {

    protected final double vx0, vy0, x0, y0;//Velocidad y coordenadas iniciales
    protected final double dt;//Delta de tiempo donde la trayectoria es valida

    /*Constructor por defecto*/
    public AbstractTrajectory(double vx0, double vy0, double x0, double y0) {
        this.vx0 = vx0;
        this.vy0 = vy0;
        this.x0 = x0;
        this.y0 = y0;
        this.dt = getDt();
    }

    /**Retorna el objeto trayectoria correpondiente a un instante de tiempo especifico*/
    public AbstractTrajectory getTrajectory(double time) {
        if (this instanceof AirTrajectory) {
            if (time < dt) {
                return this;
            } else {
                AirTrajectory tr0 = (AirTrajectory) this;
                return tr0.nextTrayectory.getTrajectory(time - dt);
            }
        } else {
            return this;
        }
    }

    /**Distancia horizontal recorrida por la trayectoria en el instante t*/
    public abstract double getX(double t);

    /**Distancia vertical recorrida por la trayectoria en el instante t*/
    public abstract double getY(double t);

    /**Instante de tiempo hasta donde es valida la trayectoria*/
    public abstract double getDt();

    /**Indica si entre el instante t0 y t1 ocurrio un rebote*/
    public boolean isRebound(double t0, double t1) {
        AbstractTrajectory tr0 = getTrajectory(t0);
        AbstractTrajectory tr1 = getTrajectory(t1);
        return (tr0 instanceof AirTrajectory && tr1 instanceof AirTrajectory && tr0 != tr1);
    }
}
