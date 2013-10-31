package org.javahispano.javacup.render;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.TacticDetail;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

/** Esta clase dibuja los jugadores, uso interno*/
public class PintaJugador {

	private Image img[][] = new Image[7][6];
	private Image imgJug[][] = new Image[11][14];
	private Image sombra;
	private TacticDetail impl;

	public PintaJugador(TacticDetail impl, boolean uniformeAlternativo) throws SlickException {
		this();
		setImpl(impl);
		update(uniformeAlternativo);
	}

	public PintaJugador() throws SlickException {
		for (int j = 0; j < 7; j++) {
			for (int i = 1; i < 7; i++) {
				img[j][i - 1] = new Image("imagenes/jugador/" + i + j + ".png");
			}
		}
		sombra = new org.newdawn.slick.Image("imagenes/sombra.png");
	}
	private Color polera = new Color(255, 255, 0);
	private Color pantalon = new Color(255, 0, 255);
	private Color calcetas = new Color(0, 0, 255);
	private Color franja = new Color(0, 255, 0);
	private Color pelo = new Color(255, 255, 255);
	private Color piel = new Color(215, 176, 150);
	private Color zapatos = new Color(0, 0, 0);

	/**Actualiza los cambios sobre la implementacion en los graficos
	 */
	public synchronized void update(boolean alternativa) throws SlickException {
		int idx;
		Color c0, c1 = null;
		Color upolera = alternativa?new Color(impl.getShirtColor2().getRed(), impl.getShirtColor2().getGreen(), impl.getShirtColor2().getBlue()):new Color(impl.getShirtColor().getRed(), impl.getShirtColor().getGreen(), impl.getShirtColor().getBlue());
		Color upantalon = alternativa?new Color(impl.getShortsColor2().getRed(), impl.getShortsColor2().getGreen(), impl.getShortsColor2().getBlue()):new Color(impl.getShortsColor().getRed(), impl.getShortsColor().getGreen(), impl.getShortsColor().getBlue());
		Color ucalcetas = alternativa?new Color(impl.getSocksColor2().getRed(), impl.getSocksColor2().getGreen(), impl.getSocksColor2().getBlue()):new Color(impl.getSocksColor().getRed(), impl.getSocksColor().getGreen(), impl.getSocksColor().getBlue());
		Color uportero = alternativa?new Color(impl.getGoalKeeper2().getRed(), impl.getGoalKeeper2().getGreen(), impl.getGoalKeeper2().getBlue()):new Color(impl.getGoalKeeper().getRed(), impl.getGoalKeeper().getGreen(), impl.getGoalKeeper().getBlue());
		Color ufranja = alternativa?new Color(impl.getShirtLineColor2().getRed(), impl.getShirtLineColor2().getGreen(), impl.getShirtLineColor2().getBlue()):new Color(impl.getShirtLineColor().getRed(), impl.getShirtLineColor().getGreen(), impl.getShirtLineColor().getBlue());

		for (int i = 0; i < 11; i++) {
			Color upelo = new Color(impl.getPlayers()[i].getHairColor().getRed(), impl.getPlayers()[i].getHairColor().getGreen(), impl.getPlayers()[i].getHairColor().getBlue());
			Color upiel = new Color(impl.getPlayers()[i].getSkinColor().getRed(), impl.getPlayers()[i].getSkinColor().getGreen(), impl.getPlayers()[i].getSkinColor().getBlue());
			for (int j = 0; j < 14; j++) {
				if (j < 7) {
					idx = j;
				} else {
					idx = (13 - j);
				}
				ImageBuffer ib = new ImageBuffer(20, 20);
				for (int x = 0; x < 20; x++) {
					for (int y = 0; y < 20; y++) {
						c0 = img[idx][(alternativa?impl.getStyle2():impl.getStyle()).getNumero() - 1].getColor(x, y);
						c1 = null;
						if (c0.equals(franja)) {
							if (impl.getPlayers()[i].isGoalKeeper()) {
								c1 = uportero;
							} else {
								c1 = ufranja;
							}
						} else if (c0.equals(polera)) {
							if (impl.getPlayers()[i].isGoalKeeper()) {
								c1 = uportero;
							} else {
								c1 = upolera;
							}
						} else if (c0.equals(piel)) {
							c1 = upiel;
						} else if (c0.equals(pelo)) {
							c1 = upelo;
						} else if (c0.equals(pantalon)) {
							if (impl.getPlayers()[i].isGoalKeeper()) {
								c1 = uportero;
							} else {
								c1 = upantalon;
							}
						} else if (c0.equals(calcetas)) {
							c1 = ucalcetas;
						} else if (c0.equals(zapatos)) {
							c1 = zapatos;
						}
						if (c1 != null) {
							ib.setRGBA(x, y, c1.getRed(), c1.getGreen(), c1.getBlue(), c1.getAlpha());
						}
					}
				}
				imgJug[i][j] = ib.getImage();
			}
		}
	}

	public void setImpl(TacticDetail impl) {
		this.impl = impl;
	}

	public void pintaJugador(int jugador, int iter, double angulo, double escala, double x, double y, Graphics g) {
		escala = escala / 8;
		x = x - 10 * escala;
		y = y - 10 * escala;
		g.rotate((float) (x + 10 * escala), (float) (y + 10 * escala), (float) angulo);
		g.drawImage(imgJug[jugador][iter % 14].getScaledCopy((float) escala), (int) x, (int) y);
		g.rotate((float) (x + 10 * escala), (float) (y + 10 * escala), -(float) angulo);
	}
	private TrueTypeFont font = new TrueTypeFont(new java.awt.Font("lucida console", 1, 12), false);

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
		x = x - 10 * escala;
		y = y - 7 * escala;
		g.drawImage(sombra.getScaledCopy((float) escala), (int) (x + 10 * escala), (int) (y + 7 * escala));
	}
}
