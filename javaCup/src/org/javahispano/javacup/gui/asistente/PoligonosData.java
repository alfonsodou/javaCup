package org.javahispano.javacup.gui.asistente;

import java.awt.Color;
import java.util.ArrayList;

/**Clase usada internamente por el asistente, que permite obtener los dibujos de las vestimentas*/
class PoligonosData {

    public ArrayList<Integer[]> intx;
    public ArrayList<Integer[]> inty;
    public ArrayList<Color> cols;

    private PoligonosData() {
    }

    public PoligonosData(ArrayList<Integer[]> intx, ArrayList<Integer[]> inty, ArrayList<Color> cols) {
        this.intx = intx;
        this.inty = inty;
        this.cols = cols;
    }
}
