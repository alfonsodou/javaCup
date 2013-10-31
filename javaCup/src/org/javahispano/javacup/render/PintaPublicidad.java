package org.javahispano.javacup.render;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
Esta clase dibuja la publicidad estatica, uso interno
 */
public final class PintaPublicidad {

	private Image h, v;
	private int wm, hm;

        /**Instancia la pintura de la publicidad horizontal y vertical*/
	public PintaPublicidad(String h, String v, int wm, int hm) throws SlickException {
		this.wm = wm;
		this.hm = hm;
		this.v = new Image(v);
		this.h = new Image(h);
	}
	private Position p0 = Constants.esqSupIzqCampo.movePosition(0, -2);
	private Position p1 = Constants.esqSupIzqCampo.movePosition(0, Constants.LARGO_CAMPO);
	private Position p2 = Constants.esqSupIzqCampo.movePosition(Constants.ANCHO_CAMPO, 0);
	private Position p3 = Constants.esqSupIzqCampo.movePosition(-2, 0);
	private Position sizeh = new Position(Constants.ANCHO_CAMPO, 2);
	private Position sizev = new Position(2, Constants.LARGO_CAMPO);

        /**Pinta la publicidad*/
	public void pintaPublicidad(Graphics g, Position p, double escala) {
		int[] inf = Transforma.transform(sizeh, escala);
		int[] pos = Transforma.transform(p0, p, wm, hm, escala);
		g.drawImage(h.getScaledCopy(inf[0], inf[1]), pos[0], pos[1]);

		pos = Transforma.transform(p1, p, wm, hm, escala);
		Image img = h.getScaledCopy(inf[0], inf[1]);
		img.rotate(180);
		g.drawImage(img, pos[0], pos[1]);

		img = v.getScaledCopy(inf[0], inf[1]);
		inf = Transforma.transform(sizev, escala);
		pos = Transforma.transform(p2, p, wm, hm, escala);
		img = v.getScaledCopy(inf[0], inf[1]);
		g.drawImage(img, pos[0], pos[1]);

		pos = Transforma.transform(p3, p, wm, hm, escala);
		img.rotate(180);
		g.drawImage(img, pos[0], pos[1]);
	}
}
