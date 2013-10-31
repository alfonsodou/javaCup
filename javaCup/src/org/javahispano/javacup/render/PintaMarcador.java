/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javahispano.javacup.render;

import org.javahispano.javacup.model.util.Constants;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
Esta clase dibuja el marcador electronico, uso interno
 */
public final class PintaMarcador {

	private String local, visita;

	public PintaMarcador(String local, String visita) {
		local = local.replaceAll("\\s+", " ").toUpperCase();
		visita = visita.replaceAll("\\s+", " ").toUpperCase();
		String[] ss;
		ss = local.split(" ");
		if (ss.length == 1) {
			if (local.length() > 3) {
				local = ss[0].substring(0, 3);
			}
		} else {
			local = "";
			for (String s : ss) {
				if (local.length() < 3) {
					local = local + s.charAt(0);
				}
			}
		}
		ss = visita.split(" ");
		if (ss.length == 1) {
			if (visita.length() > 3) {
				visita = ss[0].substring(0, 3);
			}
		} else {
			visita = "";
			for (String s : ss) {
				if (visita.length() < 3) {
					visita = visita + s.charAt(0);
				}
			}
		}
		while (local.length() < 3) {
			local = local + " ";
		}
		while (visita.length() < 3) {
			visita = visita + " ";
		}
		this.local = local;
		this.visita = visita;
	}
	private java.awt.Font _f = new java.awt.Font("monospaced", java.awt.Font.BOLD, 20);
	private TrueTypeFont f = new TrueTypeFont(_f, false);
	private java.awt.Font _f2 = new java.awt.Font("monospaced", java.awt.Font.BOLD, 18);
	private TrueTypeFont f2 = new TrueTypeFont(_f2, false);
	private java.awt.Font _f3 = new java.awt.Font("monospaced", java.awt.Font.BOLD, 14);
	private TrueTypeFont f3 = new TrueTypeFont(_f3, false);
	private StringBuffer sb = new StringBuffer(14);
	private Color gray = new Color(48, 48, 48, 148);

	public void pintaMarcador(int local, int visita, int iter, double posecionLocal, Graphics g) {
		sb.delete(0, sb.length());
		g.setColor(Color.black);
		g.fillRect(10, 10, 130, 65);

		g.setFont(f);
		g.setColor(Color.yellow);
		sb.append(local);
		while (sb.length() < 3) {
			sb.append(' ');
		}
		sb.append(" - ");
		sb.append(visita);
		g.drawString(sb.toString(), 15, 24);
		g.drawString(this.local + " - " + this.visita, 15, 5);
		g.setFont(f2);
		int seg = iter / Constants.FPS;
		int min = seg / 60;
		seg = seg % 60;
		sb.delete(0, sb.length());
		if (min < 10) {
			sb.append("0");
		}
		sb.append(min);
		sb.append(":");
		if (seg < 10) {
			sb.append("0");
		}
		sb.append(seg);
		g.setColor(Color.orange);
		g.drawString(sb.toString(), 80, 51);

		g.setFont(f3);
		sb.delete(0, sb.length());
		min = (int) (posecionLocal * 100);
		seg = 100 - min;
		if (min < 10) {
			sb.append(" ");
		}
		sb.append(min);
		sb.append("%-");
		if (seg < 10) {
			sb.append(" ");
		}
		sb.append(seg);
		sb.append("%");
		g.setColor(Color.green);
		g.drawString(sb.toString(), 15, 55);
		int pos = (int) (65 - posecionLocal * 50);
		g.drawLine(pos, 52, 40, 52);
		g.drawLine(pos, 53, 40, 53);
		g.drawLine(pos, 51, 40, 51);
		g.setColor(Color.yellow);
		g.drawLine(15, 52, 65, 52);
		g.drawLine(15, 48, 15, 56);
		g.drawLine(65, 48, 65, 56);
		g.drawLine(40, 48, 40, 56);


		g.setColor(gray);
		for (int i = 10; i < 140; i = i + 2) {
			g.drawLine(i, 10, i, 75);
		}
		for (int i = 10; i < 75; i = i + 2) {
			g.drawLine(10, i, 140, i);
		}
		g.setColor(Color.black);
		g.fillRect(40, 0, 10, 10);
		g.fillRect(110, 0, 10, 10);
		g.fillRect(0, 30, 10, 10);
		g.drawRect(10, 10, 131, 66);
	}
}
