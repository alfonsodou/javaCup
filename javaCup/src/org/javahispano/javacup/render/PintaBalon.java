package org.javahispano.javacup.render;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
Esta clase se encarga de dibujar el balon, uso interno
 */
public final class PintaBalon {

    private Image balon[] = new Image[6];
    private Image sombra;

    public PintaBalon() throws SlickException {
        for (int i = 0; i < 6; i++) {
            balon[i] = new Image("imagenes/balon/balon" + i + ".png");
        }
        sombra = new Image("imagenes/balon/sombra.png");
    }

    public void pintaBalon(int iter, double angulo, double escala, double x, double y, double z, Graphics g) {
        escala = escala / 10;
        double esc = 0.225 * escala * (1 + z / 16);
        double esc1 = 0.225 * escala * (1 + z / 8);
        double factor = 14 * esc1;
        x = x - factor;
        y = y - factor;
        g.rotate((float) (x + factor), (float) (y + factor), (float) -((angulo - Math.PI / 2) * 180 / Math.PI));
        g.drawImage(balon[iter % 6].getScaledCopy((float) esc), (int) x, (int) y);
        g.rotate((float) (x + factor), (float) (y + factor), (float) ((angulo - Math.PI / 2) * 180 / Math.PI));
    }

    public void pintaSombra(double escala, double x, double y, double z, Graphics g) {
        escala = escala / 10;
        double esc = 0.225 * escala;
        x = x + escala / 3;
        y = y + escala / 3;
        g.drawImage(sombra.getScaledCopy((float) esc), (int) (x + escala * z * 3), (int) (y + escala * z * 3));
    }
}
