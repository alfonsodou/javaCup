package org.javahispano.javacup.gui.asistente;


import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.util.Color;

/**Esta clase implementa PlayerDetail es usada internamente por el Asistente*/
class JugadorImpl implements PlayerDetail {

    private JugadorImpl() {
    }

    static final Color cpiel = new Color(255, 200, 150);
    static final Color cpelo = new Color(50, 0, 0);
    private String nombre;
    private Color colorPiel = cpiel;
    private Color colorPelo = cpelo;
    private int numero;
    private double velocidad = .5;
    private double remate = .5;
    private double presicion = .5;
    private boolean portero = false;

    public JugadorImpl(String nombre, int numero) {
        this.numero = numero;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre + " " + numero + (portero ? " (Portero)" : "");
    }

    @Override
    public String getPlayerName() {
        return nombre;
    }

    @Override
    public Color getSkinColor() {
        return colorPiel;
    }

    @Override
    public Color getHairColor() {
        return colorPelo;
    }

    @Override
    public int getNumber() {
        return numero;
    }

    @Override
    public boolean isGoalKeeper() {
        return portero;
    }

    @Override
    public double getSpeed() {
        return velocidad;
    }

    @Override
    public double getPower() {
        return remate;
    }

    @Override
    public double getPrecision() {
        return presicion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setColorPiel(Color colorPiel) {
        this.colorPiel = colorPiel;
    }

    public void setColorPelo(Color colorPelo) {
        this.colorPelo = colorPelo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public void setRemate(double remate) {
        this.remate = remate;
    }

    public void setPresicion(double presicion) {
        this.presicion = presicion;
    }

    public void setPortero(boolean portero) {
        this.portero = portero;
    }
}
