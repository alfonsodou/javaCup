package org.javahispano.javacup.applet;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.javahispano.javacup.model.engine.PartidoGuardado;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.render.VisorOpenGlApplet;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.Texture;

public class JavacupApplet extends Applet {

	Canvas display_parent;

	/** Thread which runs the main game loop */
	Thread gameThread;

	/** is the game loop running */
	boolean running = false;

	String urlString;

	long dateMatch;
	long dayTime;
	long seconds;

	/** The fonts to draw to the screen */
	private TrueTypeFont font;
	private TrueTypeFont font2;

	private Texture texture;
	private Audio oggStream;

	/** Boolean flag on whether AntiAliasing is enabled or not */
	private boolean antiAlias = true;

	public void startLWJGL() {
		urlString = getParameter("URL") + getParameter("ID");
		dayTime = Long.parseLong(getParameter("DATE"));
		dateMatch = Long.parseLong(getDateUsingHttpText());
		seconds = dateMatch - dayTime;

		System.out.println("URL: " + urlString);
		System.out.println("DATE: " + dayTime);
		System.out.println("DATEMATCH: " + dateMatch);
		System.out.println("Seconds: " + seconds);

		gameThread = new Thread() {
			public void run() {
				running = true;
				try {
					Display.setParent(display_parent);
					Display.create();
					initGL();
				} catch (LWJGLException e) {
					e.printStackTrace();
					return;
				}
				gameLoop();
			}
		};
		gameThread.start();
	}

	/**
	 * Tell game loop to stop running, after which the LWJGL Display will be
	 * destoryed. The main thread will wait for the Display.destroy().
	 */
	private void stopLWJGL() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {

	}

	public void stop() {

	}

	/**
	 * Applet Destroy method will remove the canvas, before canvas is destroyed
	 * it will notify stopLWJGL() to stop the main game loop and to destroy the
	 * Display
	 */
	public void destroy() {
		remove(display_parent);
		super.destroy();
	}

	public void init() {
		setLayout(new BorderLayout());
		try {
			display_parent = new Canvas() {
				public final void addNotify() {
					super.addNotify();
					startLWJGL();
				}

				public final void removeNotify() {
					stopLWJGL();
					super.removeNotify();
				}
			};
			display_parent.setSize(getWidth(), getHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setVisible(true);

		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
	}

	protected void initGL() {

		glEnable(GL_TEXTURE_2D);

		glShadeModel(GL_SMOOTH);

		glDisable(GL_DEPTH_TEST);

		glDisable(GL_LIGHTING);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glClearDepth(1);

		glEnable(GL_BLEND);

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glViewport(0, 0, display_parent.getWidth(), display_parent.getHeight());

		glMatrixMode(GL_MODELVIEW);

		glMatrixMode(GL_PROJECTION);

		glLoadIdentity();

		glOrtho(0, display_parent.getWidth(), display_parent.getHeight(), 0, 1,
				-1);

		glMatrixMode(GL_MODELVIEW);

		Font awtFont = new Font("Times New Roman", Font.BOLD, 36);
		font = new TrueTypeFont(awtFont, antiAlias);
		// load font from file
/*		try {

			InputStream inputStream = ResourceLoader
					.getResourceAsStream("/display/zxspectr.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);

			awtFont2 = awtFont2.deriveFont(36f); // set font size

			font2 = new TrueTypeFont(awtFont2, antiAlias);

		} catch (Exception e) {

			e.printStackTrace();

		}

		try {
			texture = TextureLoader.getTexture("JPG", ResourceLoader
					.getResourceAsStream("/display/voleaquini-5258-640x640x80.jpg"));
			oggStream = AudioLoader.getStreamingAudio("OGG",
					ResourceLoader.getResource("/display/ovacion1.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	public String actualizarCrono(long seconds) {
		int hr = (int) (seconds / 3600);
		int min = (int) ((seconds - hr * 3600) / 60);
		int seg = (int) (seconds - hr * 3600 - min * 60);

		return hr + " : " + min + " : " + seg;
	}

	public void gameLoop() {
		/*Color.white.bind();

		texture.bind(); // or glBind(texture.getTextureID());

		glBegin(GL_QUADS);

		glTexCoord2f(0, 0);

		glVertex2f(100, 100);

		glTexCoord2f(1, 0);

		glVertex2f(100 + texture.getTextureWidth(), 100);

		glTexCoord2f(1, 1);

		glVertex2f(100 + texture.getTextureWidth(),
				100 + texture.getTextureHeight());

		glTexCoord2f(0, 1);

		glVertex2f(100, 100 + texture.getTextureHeight());

		glEnd();
		
		oggStream.playAsMusic(1.0f, 1.0f, true);*/

		while (seconds > 0) {
			try {
				gameThread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			seconds--;
			glClear(GL_COLOR_BUFFER_BIT);

			font.drawString(350, 275, actualizarCrono(seconds), Color.yellow);
			// System.out.println(seconds);

			Display.update();
			Display.sync(60);

		}
		
//		oggStream.stop();

		try {
			URL url = (URL) new URL(urlString);
			PartidoGuardado p = new PartidoGuardado(url);

			if ((seconds < 0) && (seconds > -180)) {
				p.setTiempo((int) (-seconds * Constants.FPS));
			}
			p.iterar();

			int sx = 800;
			int sy = 600;

			VisorOpenGlApplet.setVolumenAmbiente(0.5f);
			VisorOpenGlApplet.setVolumenCancha(0.5f);
			VisorOpenGlApplet.setMarcadorVisible(true);
			VisorOpenGlApplet.setEntornoVisible(true);
			VisorOpenGlApplet.setEstadioVisible(true);
			VisorOpenGlApplet.setEscala(15);
			VisorOpenGlApplet.setAutoescala(true);
			VisorOpenGlApplet.setTexto(1);
			VisorOpenGlApplet.setFPS(20);
			new VisorOpenGlApplet(p, sx, sy, false, null, false);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("No se puede reproducir el partido!");
		}

		while (running) {

			Display.sync(60);
			Display.update();
		}

		Display.destroy();
	}

	private String getDateUsingHttpText() {
		try {
			// Construct a URL referring to the servlet
			URL url = new URL(getCodeBase(), "/daytime");

			// Create a HttpMessage to communicate with that
			// URL
			HttpMessage msg = new HttpMessage(url);

			// Send a GET message to the servlet, with parameter matchID
			// Get the response as an InputStream
			Properties p = new Properties();
			p.put("matchID", getParameter("ID"));
			InputStream in = msg.sendGetMessage(p);

			// Wrap the InputStream with a DataInputStream
			DataInputStream result = new DataInputStream(
					new BufferedInputStream(in));

			// Read the first line of the response, which should be
			// a string representation of the current time
			String date = result.readLine();

			// Close the InputStream
			in.close();

			// Return the retrieved time
			return date;
		} catch (Exception e) {
			// If there was a problem, print to System.out
			// (typically the Java console) and return null
			e.printStackTrace();
			return null;
		}
	}

}