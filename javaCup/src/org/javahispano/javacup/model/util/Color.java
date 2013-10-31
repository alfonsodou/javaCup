package org.javahispano.javacup.model.util;

import java.io.Serializable;

public class Color implements Serializable {
	private int red;
	private int green;
	private int blue;
	
    public static final Color PINK = new Color(255, 192, 203);
    public static final Color GRAY = new Color(190, 190, 190);
    public static final Color DARK_GRAY = new Color(105, 105,105);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(255, 0, 0);

		
	public Color(int r, int g, int b) {
		this.red = r;
		this.green = g;
		this.blue = b;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	public int getRGB() {
		return ((255 & 0xFF) << 24) |
				((red & 0xFF) << 16) |
				((green & 0xFF) << 8) |
				((blue & 0xFF) << 0);
	}
}
