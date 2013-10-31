package org.javahispano.javacup.tacticas.jvc2013.novena;


/**
 *
 * @author yoemny
 */
public class ParametrosRemate {
    private double anguloHorizontal;
    private double angulovertical;
    private double fuerza;
    private int iteracion;
    private PosicionBalon posicionLinea;
    private PosicionBalon posicionDentro;
    private PosicionBalon posicionFuera;
    private ParametrosRemate error0;
    private ParametrosRemate error1;

    private double calidadFuerzaRemate;
    private double calidadRematesGol;
    private double calidadImparableVertical;
    private double calidadImparableHorizontal;


    public ParametrosRemate(double anguloHorizontal, double angulovertical, double fuerza, int iteracion, PosicionBalon posicionLinea, PosicionBalon posicionDentro, PosicionBalon posicionFuera) {
        this.anguloHorizontal = anguloHorizontal;
        this.angulovertical = angulovertical;
        this.fuerza = fuerza;
        this.iteracion = iteracion;
        this.posicionLinea = posicionLinea;
        this.posicionDentro = posicionDentro;
        this.posicionFuera = posicionFuera;
        this.error0 = null;
        this.error1 = null;
        this.calidadFuerzaRemate = 0d;
        this.calidadRematesGol = 0d;
        this.calidadImparableVertical = 0d;
        this.calidadImparableHorizontal = 0d;

    }

    public double getCalidad() {
        return (calidadFuerzaRemate*0.8 + calidadRematesGol + calidadImparableVertical + calidadImparableHorizontal )/3.8d;
    }

    public double getCalidadFuerzaRemate() {
        return calidadFuerzaRemate;
    }

    public void setCalidadFuerzaRemate(double calidadFuerzaRemate) {
        this.calidadFuerzaRemate = calidadFuerzaRemate;
    }

    public double getCalidadImparableHorizontal() {
        return calidadImparableHorizontal;
    }

    public void setCalidadImparableHorizontal(double calidadImparableHorizontal) {
        this.calidadImparableHorizontal = calidadImparableHorizontal;
    }

    public double getCalidadImparableVertical() {
        return calidadImparableVertical;
    }

    public void setCalidadImparableVertical(double calidadImparableVertical) {
        this.calidadImparableVertical = calidadImparableVertical;
    }

    public double getCalidadRematesGol() {
        return calidadRematesGol;
    }

    public void setCalidadRematesGol(double calidadRematesGol) {
        this.calidadRematesGol = calidadRematesGol;
    }

    public double getAnguloHorizontal() {
        return anguloHorizontal;
    }

    public void setAnguloHorizontal(double anguloHorizontal) {
        this.anguloHorizontal = anguloHorizontal;
    }

    public double getAngulovertical() {
        return angulovertical;
    }

    public void setAngulovertical(double angulovertical) {
        this.angulovertical = angulovertical;
    }

    public ParametrosRemate getError0() {
        return error0;
    }

    public void setError0(ParametrosRemate error0) {
        this.error0 = error0;
    }

    public ParametrosRemate getError1() {
        return error1;
    }

    public void setError1(ParametrosRemate error1) {
        this.error1 = error1;
    }

    public double getFuerza() {
        return fuerza;
    }

    public void setFuerza(double fuerza) {
        this.fuerza = fuerza;
    }

    public PosicionBalon getPosicionDentro() {
        return posicionDentro;
    }

    public void setPosicionDentro(PosicionBalon posicionDentro) {
        this.posicionDentro = posicionDentro;
    }

    public PosicionBalon getPosicionFuera() {
        return posicionFuera;
    }

    public void setPosicionFuera(PosicionBalon posicionFuera) {
        this.posicionFuera = posicionFuera;
    }

    public PosicionBalon getPosicionLinea() {
        return posicionLinea;
    }

    public void setPosicionLinea(PosicionBalon posicionLinea) {
        this.posicionLinea = posicionLinea;
    }

    public int getIteracion() {
        return iteracion;
    }

    public void setIteracion(int iteracion) {
        this.iteracion = iteracion;
    }


    
}
