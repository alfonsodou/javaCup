package org.javahispano.javacup.render;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**Esta clase se encarga de dibujar el campo de juego, los arcos y el entorno, uso interno*/
public final class PintaCancha {

    private Image pasto, arcilla, arcoSup, arcoInf, image, estadio, cancha, entorno;
    private int wm, hm;
    private final Color c1 = new Color(1f, 1f, 1f, .75f);
    private final Color c2 = new Color(.3f, .6f, .31f, .4f);
    private Position esqSupEntorno = new Position(-450, -479);
    private Position esqSupEstadio = new Position(-166, -169);
    private Position esqSupCancha = Constants.esqSupIzqCampoJuego.movePosition(-8.4, -10);
    private int estadioIdx = 0;

    public PintaCancha(int wm, int hm, int estadioIdx) throws SlickException {
        this.wm = wm;
        this.hm = hm;
        this.estadioIdx = estadioIdx;
        if (this.estadioIdx == 0) {
            entorno = new Image("imagenes/noucamp/noucamp_entorno.jpg");
            estadio = new Image("imagenes/noucamp/noucamp_estadio.png");
        } else {
            entorno = new Image("imagenes/bernabeu/entorno.bernabeu.jpg");
            estadio = new Image("imagenes/bernabeu/bernabeu.png");
        }
        cancha = new Image("imagenes/cancha.png", Color.black);
        pasto = new Image("imagenes/pasto.png");
        arcilla = new Image("imagenes/arcilla.png");
        arcoSup = new Image("imagenes/arco_inferior.1.2.png");
        arcoInf = new Image("imagenes/arco_superior.1.2.png");
    }
    boolean ok = false;

    public void pintaEstadio(Graphics g, Position p, double escala) throws SlickException {
        ok = !ok;
        if (true) {
            if (estadioIdx == 0) {
                int inf[] = Transforma.transform(esqSupEstadio, p, wm, hm, escala);
                float factor = (float) (escala * Constants.LARGO_ARCO / Constants.AMP_ARCO / 145);
                g.drawImage(estadio.getScaledCopy(factor * 6.66666f), inf[0], inf[1]);
            } else {
                int inf[] = Transforma.transform(esqSupEstadio, p.movePosition(-44, 13.5), wm, hm, escala);
                float factor = (float) (escala * Constants.LARGO_ARCO / Constants.AMP_ARCO / 145);
                g.drawImage(estadio.getScaledCopy(factor * 6.56614f), inf[0], inf[1]);
            }
        }
    }

    public void pintaEntorno(Graphics g, Position p, double escala) throws SlickException {
        if (estadioIdx == 0) {
            int inf[] = Transforma.transform(esqSupEntorno, p, wm, hm, escala);
            float factor = (float) (escala * Constants.LARGO_ARCO / Constants.AMP_ARCO / 145);
            g.drawImage(entorno.getScaledCopy(factor * 18.8f), inf[0], inf[1]);
        } else {
            int inf[] = Transforma.transform(esqSupEntorno, p.movePosition(-236, -145), wm, hm, escala);
            float factor = (float) (escala * Constants.LARGO_ARCO / Constants.AMP_ARCO / 145);
            g.drawImage(entorno.getScaledCopy(factor * 11.8f), inf[0], inf[1]);
        }
    }

    public void pintaCancha(Graphics g, Position p, double escala) {
        int inf[] = Transforma.transform(esqSupCancha, p, wm, hm, escala);
        float factor = (float) (escala / 15d);
        g.drawImage(cancha.getScaledCopy(factor), inf[0], inf[1]);
    }

    public void pintaArcos(Graphics g, Position p, double escala) {
        int inf[] = Transforma.transform(Constants.posteIzqArcoInf, p, wm, hm, escala);
        float factor = (float) (escala * Constants.LARGO_ARCO / 165);
        double dy = 0.25 * Constants.LARGO_ARCO * Constants.AMP_ARCO;
        double dx = 0.023 * Constants.LARGO_ARCO;
        g.drawImage(arcoInf.getScaledCopy(factor), inf[0] - Transforma.transform(dx, escala), inf[1] - Transforma.transform(dy, escala));
        inf = Transforma.transform(Constants.posteIzqArcoSup, p, wm, hm, escala);
        g.drawImage(arcoSup.getScaledCopy(factor), inf[0] - Transforma.transform(dx, escala), inf[1]);
    }

    public void dibujaCancha(Graphics g, Position p, double escala) {

        int[] inf = Transforma.transform(Constants.esqSupIzqCampo, p, wm, hm, escala);
        int[] dim = Transforma.transform(Constants.dimCampo, escala);
        int[] cen = Transforma.transform(Constants.centroCampoJuego, p, wm, hm, escala);
        int larg;
        int sx = dim[0] / 200 + 1;
        int sy = dim[1] / 200 + 1;

        for (int x0 = -sx; x0 < sx; x0++) {
            for (int y0 = -sy; y0 < sy; y0++) {
                g.drawImage(pasto, x0 * 100 + cen[0], y0 * 100 + cen[1]);
            }
        }
        for (int x0 = -sx; x0 < sx; x0++) {
            g.drawImage(arcilla, x0 * 100 + cen[0], inf[1] - 100);
            g.drawImage(arcilla, x0 * 100 + cen[0], inf[1] + dim[1]);
        }
        for (int y0 = -sy - 1; y0 < sy + 1; y0++) {
            g.drawImage(arcilla, inf[0] - 100, y0 * 100 + cen[1]);
            g.drawImage(arcilla, inf[0] + dim[0], y0 * 100 + cen[1]);
        }
        g.setColor(c2);
        g.setLineWidth(6);
        g.drawRect(inf[0], inf[1], dim[0], dim[1]);
        g.setColor(c1);
        g.setLineWidth(2);
        inf = Transforma.transform(Constants.esqSupIzqCampoJuego, p, wm, hm, escala);
        dim = Transforma.transform(Constants.dimCampoJuego, escala);
        g.drawRect(inf[0], inf[1], dim[0], dim[1]);
        g.drawLine(inf[0] + 1, cen[1], inf[0] + dim[0] - 2, cen[1]);
        inf = Transforma.transform(Constants.esqSupIzqCircCentral, p, wm, hm, escala);
        larg = Transforma.transform(Constants.RADIO_CIRCULO_CENTRAL * 2, escala);
        g.drawOval(inf[0], inf[1], larg, larg);
        inf = Transforma.transform(Constants.esqSupIzqAreaGrandeSup, p, wm, hm, escala);
        dim = Transforma.transform(Constants.dimAreaGrande, escala);
        g.drawRect(inf[0], inf[1], dim[0], dim[1]);
        inf = Transforma.transform(Constants.esqSupIzqAreaGrandeInf, p, wm, hm, escala);
        g.drawRect(inf[0], inf[1], dim[0], dim[1]);
        inf = Transforma.transform(Constants.esqSupIzqAreaChicaSup, p, wm, hm, escala);
        dim = Transforma.transform(Constants.dimAreaChica, escala);
        g.drawRect(inf[0], inf[1], dim[0], dim[1]);
        inf = Transforma.transform(Constants.esqSupIzqAreaChicaInf, p, wm, hm, escala);
        g.drawRect(inf[0], inf[1], dim[0], dim[1]);
        inf = Transforma.transform(Constants.esqSupIzqPenalSup, p, wm, hm, escala);
        larg = Transforma.transform(Constants.RADIO_PENAL * 2, escala);
        g.drawArc(inf[0], inf[1], larg, larg, 36, 144);
        inf = Transforma.transform(Constants.esqSupDerPenalInf, p, wm, hm, escala);
        g.drawArc(inf[0], inf[1], larg, larg, 216, 324);
        inf = Transforma.transform(Constants.centroCampoJuego, p, wm, hm, escala);
        g.fillOval(inf[0] - 2, inf[1] - 1, 4, 4);
        inf = Transforma.transform(Constants.penalInf, p, wm, hm, escala);
        g.fillOval(inf[0] - 2, inf[1] - 2, 4, 4);
        inf = Transforma.transform(Constants.penalSup, p, wm, hm, escala);
        g.fillOval(inf[0] - 2, inf[1] - 2, 4, 4);
    }
}
