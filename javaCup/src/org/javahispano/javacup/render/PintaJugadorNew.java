package org.javahispano.javacup.render;

import java.awt.image.BufferedImage;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.TacticDetail;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

/** Esta clase dibuja los jugadores, uso interno*/
public final class PintaJugadorNew extends PintaJugador {

    private Image img[][] = new Image[56][6];
    private Image imgJug[][] = new Image[11][112];
    private Image sombra;
    private TacticDetail impl;

    public PintaJugadorNew(TacticDetail impl, boolean uniformeAlternativo) throws SlickException {
        this();
        setImpl(impl);
        update(uniformeAlternativo);
    }
    float w, h;
    private static int an[] = new int[]{50, 75, 100, 75, 50, 25, 0, 25};

    public PintaJugadorNew() throws SlickException {
    }
    private Color polera = new Color(99, 207, 109);
    private Color pantalon = new Color(239, 241, 57);
    private Color calcetas = new Color(97, 100, 207);
    private Color franja = new Color(123, 244, 251);
    private Color pelo = new Color(67, 40, 26);
    private Color piel = new Color(187, 140, 90);
    private Color zapatos = new Color(45, 46, 53);
    private Color uzapatos = new Color(0, 0, 0);

    /**Cambia los colores de una imagen*/
    public Image changeColor(Image img, float[] hueRatios, Color[] colors, boolean flip) throws SlickException {
        int width = img.getWidth();
        int height = img.getHeight();
        ImageBuffer imgBuf = new ImageBuffer(width, height);
        Color c;
        float[] v1 = new float[3];
        float[] v2 = new float[3];
        float brillo = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < colors.length; k = k + 2) {
                    v1 = java.awt.Color.RGBtoHSB(colors[k].getRed(), colors[k].getGreen(), colors[k].getBlue(), v1);//HSB del color a cambiar
                    c = img.getColor(i, j);
                    v2 = java.awt.Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), v2);//HSB del color del pixel
                    brillo = v2[2] - v1[2];
                    if ((c.getAlpha() > 0) && (Math.abs(v2[0] - v1[0]) < hueRatios[k / 2] || Math.abs(v1[0] + 1 - v2[0]) < hueRatios[k / 2])) {//optimizar... no repetir
                        v1 = java.awt.Color.RGBtoHSB(colors[k + 1].getRed(), colors[k + 1].getGreen(), colors[k + 1].getBlue(), v1);//HSB del color al que se cambiara
                        brillo = v1[2] + brillo;
                        if (brillo < 0) {
                            brillo = 0;
                        }
                        if (brillo > 1) {
                            brillo = 1;
                        }
                        c = new Color(java.awt.Color.HSBtoRGB(v1[0], v1[1], brillo));
                        k = colors.length;
                    }
                    if (flip) {
                        imgBuf.setRGBA(width - i - 1, j, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
                    } else {
                        imgBuf.setRGBA(i, j, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
                    }
                }
            }
        }
        return imgBuf.getImage();
    }

    public Image toSlick(java.awt.Image src) {
        BufferedImage b = new BufferedImage(src.getWidth(null), src.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        b.createGraphics().drawImage(src, 0, 0, null);
        ImageBuffer buf = new ImageBuffer(b.getWidth(), b.getHeight());
        int x, y, argb;
        for (y = 0; y < b.getHeight(); y++) {
            for (x = 0; x < b.getWidth(); x++) {
                argb = b.getRGB(x, y);
                buf.setRGBA(x, y, (argb >> 16) & 0xff, (argb >> 8) & 0xff, argb & 0xff, (argb >> 24) & 0xff);
            }
        }
        return buf.getImage();
    }

    /**Actualiza los cambios sobre la implementacion en los graficos
     */
    public synchronized void update(boolean alternativa) throws SlickException {
        Image all = null;
        if (impl.getStyle() == EstiloUniforme.SIN_ESTILO) {
            all = new Image("imagenes/running plain.png");
        } else {
            all = new Image("imagenes/running lines.png");
        }
        w = all.getWidth() / 10f;
        h = all.getHeight() / 13f;
        for (int j = 0; j < 56; j++) {
            for (int i = 1; i < 7; i++) {
                int ang = j / 7;
                int idx = an[ang] + (int) (j % 7 * 2);
                int x = (int) (idx % 10);
                int y = (int) (idx / 10);
                img[j][i - 1] = all.getSubImage((int) ((float) x * w), (int) ((float) y * h), (int) w, (int) h);
            }
        }
        sombra = new Image("imagenes/sombra.png");
        int idx;
        Color c0, c1 = null;
        Color upolera = alternativa ? new Color(impl.getShirtColor2().getRed(), impl.getShirtColor2().getGreen(), impl.getShirtColor2().getBlue()) : new Color(impl.getShirtColor().getRed(), impl.getShirtColor().getGreen(), impl.getShirtColor().getBlue());
        Color upantalon = alternativa ? new Color(impl.getShortsColor2().getRed(), impl.getShortsColor2().getGreen(), impl.getShortsColor2().getBlue()) : new Color(impl.getShortsColor().getRed(), impl.getShortsColor().getGreen(), impl.getShortsColor().getBlue());
        Color ucalcetas = alternativa ? new Color(impl.getSocksColor2().getRed(), impl.getSocksColor2().getGreen(), impl.getSocksColor2().getBlue()) : new Color(impl.getSocksColor().getRed(), impl.getSocksColor().getGreen(), impl.getSocksColor().getBlue());
        Color uportero = alternativa ? new Color(impl.getGoalKeeper2().getRed(), impl.getGoalKeeper2().getGreen(), impl.getGoalKeeper2().getBlue()) : new Color(impl.getGoalKeeper().getRed(), impl.getGoalKeeper().getGreen(), impl.getGoalKeeper().getBlue());
        Color ufranja = alternativa ? new Color(impl.getShirtLineColor2().getRed(), impl.getShirtLineColor2().getGreen(), impl.getShirtLineColor2().getBlue()) : new Color(impl.getShirtLineColor().getRed(), impl.getShirtLineColor().getGreen(), impl.getShirtLineColor().getBlue());


        for (int i = 0; i < 11; i++) {
            Color upelo = new Color(impl.getPlayers()[i].getHairColor().getRed(), impl.getPlayers()[i].getHairColor().getGreen(), impl.getPlayers()[i].getHairColor().getBlue());
            Color upiel = new Color(impl.getPlayers()[i].getSkinColor().getRed(), impl.getPlayers()[i].getSkinColor().getGreen(), impl.getPlayers()[i].getSkinColor().getBlue());
            for (int j = 0; j < 14 * 8; j++) {
                if (j % 14 < 7) {
                    idx = j % 14;
                } else {
                    idx = (13 - j % 14);
                }
                boolean flip = (j / 14) < 2 || (j / 14) == 7;
                Image original = img[7 * (j / 14) + idx][(alternativa ? impl.getStyle2() : impl.getStyle()).getNumero() - 1];
                float rd = 0.07f;
                if (impl.getPlayers()[i].isGoalKeeper()) {
                    imgJug[i][j] = changeColor(original, new float[]{rd, rd, rd, rd, rd, rd, rd}, new org.newdawn.slick.Color[]{
                                piel,
                                upiel,
                                pelo,
                                upelo,
                                franja,
                                uportero,
                                polera,
                                uportero,
                                pantalon,
                                uportero,
                                zapatos,
                                uzapatos,
                                calcetas,
                                ucalcetas,}, flip);
                } else {
                    imgJug[i][j] = changeColor(original, new float[]{rd, rd, rd, rd, rd, rd, rd}, new org.newdawn.slick.Color[]{
                                piel,
                                upiel,
                                pelo,
                                upelo,
                                franja,
                                ufranja,
                                pantalon,
                                upantalon,
                                polera,
                                upolera,
                                zapatos,
                                uzapatos,
                                calcetas,
                                ucalcetas}, flip);
                }
            }
        }
    }

    public void setImpl(TacticDetail impl) {
        this.impl = impl;
    }

    public void pintaJugador(int jugador, int iter, double angulo, double escala, double x, double y, Graphics g) {
        escala = escala / 14;
        x = x - 23 * escala;
        y = y - 37 * escala;
        if (angulo > 270) {
            angulo = angulo % 360 - 360;
        }
        while (angulo < -90) {
            angulo = angulo + 360;
        }
        int ang = 0;
        if (angulo >= -22.5 && angulo <= 22.5) {
            ang = 2;
        } else if (angulo >= 22.5 && angulo <= 67.5) {
            ang = 3;
        } else if (angulo >= 67.5 && angulo <= 112.5) {
            ang = 4;
        } else if (angulo >= 112.5 && angulo <= 157.5) {
            ang = 5;
        } else if (angulo >= 157.5 && angulo <= 202.5) {
            ang = 6;
        } else if (angulo >= 202.5 && angulo <= 247.5) {
            ang = 7;
        } else if (angulo >= 247.5 || angulo <= -67.5) {
            ang = 0;
        } else {
            ang = 1;
        }
        g.drawImage(imgJug[jugador][(14 * ang) + iter % 14].getScaledCopy((float) escala), (float) (int) x, (int) y);
    }
    private TrueTypeFont font = new TrueTypeFont(new java.awt.Font("lucida console", 1, 12), true);

    public void pintaNumero(int jugador, double x, double y, Graphics g) {
        g.setColor(Color.yellow);
        g.setFont(font);
        g.drawString("" + impl.getPlayers()[jugador].getNumber(), (int) x - 6, (int) y - (int) (3 * Constants.ESCALA));
    }

    public void pintaNombre(int jugador, double x, double y, Graphics g) {
        g.setColor(Color.white);
        g.setFont(font);
        String nombre = impl.getPlayers()[jugador].getPlayerName();
        g.drawString(nombre, (int) x - nombre.length() * 4, (int) y + (int) Constants.ESCALA);
    }

    public void pintaSombra(int jugador, int iter, double angulo, double escala, double x, double y, Graphics g) {
        escala = escala / 10;
        x = x - 15 * escala;
        y = y - 7 * escala;
        g.drawImage(sombra.getScaledCopy((float) escala), (int) (x + 10 * escala), (int) (y + 7 * escala));
    }
}
