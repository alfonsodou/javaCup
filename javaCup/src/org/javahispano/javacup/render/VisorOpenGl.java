package org.javahispano.javacup.render;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.javahispano.javacup.gui.principal.PrincipalFrame;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.engine.Partido;
import org.javahispano.javacup.model.engine.PartidoGuardado;
import org.javahispano.javacup.model.engine.PartidoInterface;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.util.TacticValidate;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Visor OpenGL*/
public class VisorOpenGl implements Game {

    private static int fps = Constants.FPS;
    public static int estadioIdx = 0;
    public static boolean jugador3d;
    private static Logger logger = LoggerFactory.getLogger(VisorOpenGl.class);

    public static void setFPS(int fps) {
        VisorOpenGl.fps = fps;
    }
    private PrincipalFrame principal;
    private PartidoInterface partido = null;
    private int sx, sy;
    private static double escala = Constants.ESCALA;
    private PintaCancha pc = null;
    private PintaJugador pjLocal, pjVisita = null;
    private PintaBalon pb = null;
    private PintaMarcador pm = null;
    private PintaPublicidad pp = null;
    private double vx = 0, vy = 0;
    private double px = 0, py = 0;
    private int sx2, sy2;
    private int[][] iteraciones = new int[11][2];
    private double[][] angVisible = new double[11][2];
    private double[][] angulos = new double[11][2];
    private double[][][] angulosAnteriores = new double[11][2][4];
    private Position posPrev[][];
    private Position posActu[][];
    private Sound remate[] = new Sound[2], gol, silbato, rebote;
    private Sound[] ambiente = new Sound[9];
    private Sound[] poste = new Sound[2];
    private Sound[] ovacion = new Sound[2];
    private Position balon0 = new Position();
    private static boolean estadio = true;
    private static boolean entorno = true;
    private static boolean autoescala = true;
    private boolean showfps = false;
    private static boolean marcador = true;
    private static int tipoTexto = 0;
    private boolean pause = false;
    private int[] rel;
    private double dx, dy, z = 0, ang = 0;
    private double velgiro = 0;
    private double giro = 0;
    private int golIter = 0;
    private int offSideIter = 0;
    private int iterSaca = 0;
    private int saqueIter = 0;
    private Image golImage = null, xImage = null, cambioImage = null, offSideImage = null;
    private GameContainer gc = null;
    private int dxsaque = 0;
    private double escalaAjustada;
    private boolean uniformeAlternativoLocal = false;
    private boolean uniformeAlternativoVisita = false;
    private Image avImage;
    private Image paImage;
    private Image reImage;
    private boolean guardado = false;
    private int iteracionControl = 0;
    private PartidoGuardado pg = null;
    public static boolean progreso = false;
    private boolean showTexto = true;

    /**Inicia el Visor OpenGL indicando la instancia de partido, las dimensiones de la pantalla (sx,sy),
     * si Se ejecuta en pantalla completa(fullscreen), e indicando la instancia del jframe Principal(dejar nulo)
     * @param partido
     * @param sx
     * @param sy
     * @param fullscreen
     * @param principal
     * @throws SlickException
     */
    public VisorOpenGl(Partido partido, int sx, int sy, boolean fullscreen, PrincipalFrame principal) throws SlickException {
        this.partido = partido;
        this.sx = sx;
        this.sy = sy;
        this.dxsaque = (sx + 300 * 2) / 75;
        sx2 = sx / 2;
        sy2 = sy / 2;
        this.principal = principal;
        AppGameContainer container = new AppGameContainer(this);
        container.setForceExit(false);
        container.setDisplayMode(sx, sy, fullscreen);
        container.start();
        SoundStore.get().clear();
        InternalTextureLoader.get().clear();
    }

    /**Inicia el Visor OpenGL indicando la instancia de partido guardado, las dimensiones de la pantalla (sx,sy),
     * si Se ejecuta en pantalla completa(fullscreen), e indicando la instancia del jframe Principal(dejar nulo)
     * */
    public VisorOpenGl(PartidoGuardado partido, int sx, int sy, boolean fullscreen, PrincipalFrame principal) throws SlickException {
        pg = (PartidoGuardado) partido;
        guardado = true;
        progreso = true;
        inicio = 0;
        fin = pg.getIterciones() - 1;
        //System.out.println("Reproduciendo partido guardado...");
        this.partido = partido;
        this.sx = sx;
        this.sy = sy;
        this.dxsaque = (sx + 300 * 2) / 75;
        sx2 = sx / 2;
        sy2 = sy / 2;
        this.principal = principal;
        AppGameContainer container = new AppGameContainer(this);
        container.setForceExit(false);
        container.setDisplayMode(sx, sy, fullscreen);
        container.start();
        SoundStore.get().clear();
        InternalTextureLoader.get().clear();
    }
    /**Activa o desactiva los sonidos*/
    public static boolean sonidos = true;
    private int incremento = 1;

    /**Inicializacion del juego, uso interno*/
    @Override
    public void init(GameContainer gc) throws SlickException {
        uniformeAlternativoLocal = false;
        uniformeAlternativoVisita = TacticValidate.useAlternativeColors(partido.getDetalleLocal(), partido.getDetalleVisita());
        this.gc = gc;
        pp = new PintaPublicidad("imagenes/logos/pubvert.png", "imagenes/logos/pubhor.png", gc.getWidth() / 2, gc.getHeight() / 2);
        golImage = new Image("imagenes/gol.png");
        cambioImage = new Image("imagenes/cambio.png");
        offSideImage = new Image("imagenes/fuerajuego.png");
        xImage = new Image("imagenes/x.png");
        avImage = new Image("imagenes/avanza.png");
        paImage = new Image("imagenes/pausa.png");
        reImage = new Image("imagenes/retrocede.png");
        if (sonidos) {
            try {
                for (int i = 0; i < ambiente.length; i++) {
                    ambiente[i] = new Sound("audio/" + i + ".ogg");
                }
                remate[0] = new Sound("audio/remate1.ogg");
                remate[1] = new Sound("audio/remate2.ogg");
                poste[0] = new Sound("audio/poste1.ogg");
                poste[1] = new Sound("audio/poste2.ogg");
                ovacion[0] = new Sound("audio/ovacion1.ogg");
                ovacion[1] = new Sound("audio/ovacion2.ogg");
                gol = new Sound("audio/gol.ogg");
                rebote = new Sound("audio/rebote.ogg");
                silbato = new Sound("audio/silbato.ogg");
            } catch (Exception ex) {
                logger.error("Error al cargar sonidos", ex);
                //nosound
            }
        }
        gc.setShowFPS(false);
        pc = new PintaCancha(gc.getWidth() / 2, gc.getHeight() / 2, estadioIdx);
        if (partido != null) {
            if (jugador3d) {
                pjLocal = new PintaJugadorNew();
            } else {
                pjLocal = new PintaJugador();
            }
            pjLocal.setImpl(partido.getDetalleLocal());
            pjLocal.update(uniformeAlternativoLocal);
            if (jugador3d) {
                pjVisita = new PintaJugadorNew();
            } else {
                pjVisita = new PintaJugador();
            }
            pjVisita.setImpl(partido.getDetalleVisita());
            pjVisita.update(uniformeAlternativoVisita);
            pb = new PintaBalon();
            posActu = partido.getPosiciones();
            posPrev = new Position[11][2];
            for (int i = 0; i < 11; i++) {
                iteraciones[i][0] = 0;
                iteraciones[i][1] = 0;
                posPrev[i][0] = posActu[0][i];
                posPrev[i][1] = posActu[1][i];
            }
        }
        gc.setTargetFrameRate(VisorOpenGl.fps);
        gc.setVSync(false);
        pm = new PintaMarcador(partido.getDetalleLocal().getTacticName(), partido.getDetalleVisita().getTacticName());
        gc.setMouseGrabbed(false);
    }
    JFileChooser jfc = new JFileChooser();

    void stop() {
        SoundStore.get().clear();
        InternalTextureLoader.get().clear();
        if (partido.fueGrabado()) {
            if (JOptionPane.showConfirmDialog(principal, "Desea guardar el partido?", "Guardar Partido", JOptionPane.YES_NO_OPTION) == 0) {
                if (jfc.showSaveDialog(principal) == JFileChooser.APPROVE_OPTION) {
                    try {
                        partido.getPartidoGuardado().save(jfc.getSelectedFile());
                        if (principal != null) {
                            principal.addGuardadoLocal(new File[]{jfc.getSelectedFile()});
                        }
                    } catch (Exception ex) {
                        logger.error("Error al guardar partido", ex);
                    }
                }
            }
        }
        if (sonidos) {
            for (Sound s : ambiente) {
                s.stop();
            }
            gol.stop();
            remate[0].stop();
            remate[1].stop();
            poste[0].stop();
            poste[1].stop();
            ovacion[0].stop();
            ovacion[1].stop();
            rebote.stop();
            silbato.stop();
        }
        if (principal != null) {
            principal.setVisible(true);
            principal.requestFocus();
        } else {
            System.exit(0);
        }
    }
    Random rand = new Random();

    float pinch() {
        return .95f + rand.nextFloat() * .1f;
    }
    int audioIdx = 0;
    int audioAmbiente = 0;
    int audioAmbienteIdx = 0;
    int inicio = 0;
    int fin = 0;
    int paso = 0;

    /**Actualiza el juego, uso interno*/
    @Override
    public void update(GameContainer gc, int fps) throws SlickException {
        Input i = gc.getInput();
        if (guardado && progreso) {
            if (i.isMouseButtonDown(0)) {
                double y = 1 - (double) (Math.max(20, Math.min(i.getMouseY(), sy - 20)) - 20) / (double) (sy - 40);
                PartidoGuardado pguardado = (PartidoGuardado) partido;
                pguardado.setTiempo((int) (y * (double) pguardado.getIterciones()));
            }
        }
        if (i.isKeyDown(Input.KEY_SUBTRACT)) {
            escala = escala * 0.970;
        } else if (i.isKeyDown(Input.KEY_ADD)) {
            escala = escala / 0.970;
        }
        if (i.isKeyPressed(Input.KEY_F1)) {
            estadio = !estadio;
        }
        if (i.isKeyPressed(Input.KEY_F2)) {
            entorno = !entorno;
        }
        if (i.isKeyPressed(Input.KEY_F3)) {
            showfps = !showfps;
            gc.setShowFPS(showfps);
        }
        if (i.isKeyPressed(Input.KEY_F4)) {
            tipoTexto = (tipoTexto + 1) % 5;
        }
        if (i.isKeyPressed(Input.KEY_F5)) {
            marcador = !marcador;
        }
        if (i.isKeyDown(Input.KEY_ESCAPE)) {
            gc.setSoundVolume(0);
            gc.setSoundOn(false);
            stop();
            gc.exit();
        }
        if (i.isKeyDown(Input.KEY_P)) {
            pause = !pause;
            if (pause) {
                gc.pause();
            } else {
                gc.resume();
            }
        }
        if (guardado && i.isKeyPressed(Input.KEY_LEFT)) {
            if (incremento == 0) {
                incremento = -1;
            } else if (incremento == -1) {
                incremento = -2;
            } else if (incremento == -2) {
                incremento = -4;
            } else if (incremento == -4) {
                incremento = -4;
            } else if (incremento == 1) {
                incremento = 0;
            } else if (incremento == 2) {
                incremento = 1;
            } else if (incremento == 4) {
                incremento = 2;
            }
            iteracionControl = 30;
        }
        if (guardado && i.isKeyPressed(Input.KEY_RIGHT)) {
            if (incremento == 0) {
                incremento = 1;
            } else if (incremento == 1) {
                incremento = 2;
            } else if (incremento == 2) {
                incremento = 4;
            } else if (incremento == 4) {
                incremento = 4;
            } else if (incremento == -1) {
                incremento = 0;
            } else if (incremento == -2) {
                incremento = -1;
            } else if (incremento == -4) {
                incremento = -2;
            }
            iteracionControl = 30;
        }
        paso = 0;
        if (guardado && i.isKeyPressed(Input.KEY_UP)) {
            incremento = 0;
            paso = 1;
        }
        if (guardado && i.isKeyPressed(Input.KEY_DOWN)) {
            incremento = 0;
            paso = -1;
        }
        if (guardado && i.isKeyPressed(Input.KEY_HOME)) {
            inicio = pg.getTiempo();
        }
        if (guardado && i.isKeyPressed(Input.KEY_END)) {
            fin = pg.getTiempo();
        }
        if (guardado && i.isKeyPressed(Input.KEY_DELETE)) {
            if (JOptionPane.showConfirmDialog(null, "Desea eliminar los frames seleccionados?", "Eliminar Frames", JOptionPane.YES_NO_OPTION) == 0) {
                if (inicio < fin) {
                    pg.delete(inicio, fin);
                    fin = inicio;
                } else {
                    pg.delete(inicio, pg.getIterciones() - 1);
                    pg.delete(0, fin);
                    inicio = 0;
                    fin = pg.getIterciones() - 1;
                    pg.setTiempo(0);
                }
            }
        }
        if (guardado && i.isKeyPressed(Input.KEY_S)) {
            if (JOptionPane.showConfirmDialog(null, "Desea gardar el partido?", "Guardar Partido", JOptionPane.YES_NO_OPTION) == 0) {
                //System.out.println(pg.getURL().getProtocol());
                if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        pg.save(jfc.getSelectedFile());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al guardar partido...");
                        if (principal != null) {
                            try {
                                principal.addGuardadoLocal(new File[]{jfc.getSelectedFile()});
                            } catch (Exception ex) {
                            }

                        }
                    }
                }
            }
        }
        if (iteracionControl > 0) {
            iteracionControl--;
        }

        vx = (partido.getPosiciones()[2][0].getX() - px) / Constants.SEGUIMIENTO_CAMARA;
        vy = (partido.getPosiciones()[2][0].getY() - py) / Constants.SEGUIMIENTO_CAMARA;

        px = px + vx;
        py = py + vy;

        for (int j = 0; j < 11; j++) {
            posPrev[j][0] = posActu[0][j];
            posPrev[j][1] = posActu[1][j];
        }

        if (partido != null) {
            if (guardado) {
                try {
                    pg.setTiempo(pg.getTiempo() + incremento + paso);
                } catch (Exception ex) {
                    logger.error("Error al establecer tiempo de partido guardo", ex);
                    throw new SlickException(ex.getLocalizedMessage());
                }
            } else {
                try {
                    partido.iterar();
                } catch (Exception ex) {
                    logger.error("Error al iterar partido", ex);
                    throw new SlickException(ex.getLocalizedMessage());
                }
            }

            boolean oldSound = sonidos;
            if (guardado && incremento == 0) {
                sonidos = false;
            }

            if (audioAmbiente == audioAmbienteIdx) {
                if (sonidos) {
                    ambiente[audioIdx].play(pinch(), volumenAmbiente);
                }
                audioAmbiente = 50 + rand.nextInt(20);
                audioAmbienteIdx = 0;
                audioIdx = rand.nextInt(ambiente.length);
            }
            audioAmbienteIdx++;

            if (partido.estanSilbando()) {
                if (sonidos) {
                    silbato.play(pinch(), volumenCancha);
                }
            }
            if (partido.estanRematando()) {
                if (sonidos) {
                    remate[rand.nextInt(2)].play(pinch(), volumenCancha);
                }
            }
            if (partido.estanOvacionando()) {
                if (sonidos) {
                    ovacion[rand.nextInt(2)].play(pinch(), volumenAmbiente);
                }
            }
            if (partido.esGol()) {
                if (sonidos) {
                    gol.play(pinch(), volumenAmbiente);
                }
                golIter = 1;
            }
            
            if (golIter > 0) {
                golIter++;
                if (golIter == 50) {
                    golIter = 0;
                }
            }
            
            if (partido.isLibreIndirecto()) {
                if (sonidos) {
                	silbato.play(pinch(), volumenAmbiente);
                }
            }
            
            if (partido.isOffSide())  offSideIter = 1;
                        

            if (offSideIter > 0) {
                offSideIter += dxsaque;
                if (offSideIter > 800) {
                	offSideIter = 0;
                }
            }
            
            if (partido.cambioDeSaque()) {
                if (sonidos) {
                    silbato.play(pinch(), volumenAmbiente);
                }
                saqueIter = 1;
            }

            if (saqueIter > 0) {
                saqueIter = saqueIter + dxsaque;
                if (saqueIter > sx + 2 * 177) {
                    saqueIter = 0;
                }
            }
            if (partido.estaRebotando()) {
                if (sonidos) {
                    rebote.play(pinch(), volumenCancha);
                }
            }
            if (partido.esPoste()) {
                if (sonidos) {
                    poste[rand.nextInt(2)].play(pinch(), volumenCancha);
                    ovacion[rand.nextInt(2)].play(pinch(), volumenAmbiente);
                }
            }
            sonidos = oldSound;
            if (partido.estanSacando()) {
                iterSaca = (iterSaca + 1) % 6;
            }
        }



        posActu = partido.getPosiciones();
        for (int j = 0; j < 11; j++) {
            dx = posPrev[j][0].getX() - posActu[0][j].getX();
            dy = posPrev[j][0].getY() - posActu[0][j].getY();
            if (dy != 0 || dx != 0) {
                iteraciones[j][0] = iteraciones[j][0] + 1;
                angulos[j][0] = posPrev[j][0].angle(posActu[0][j]) * 180 / Math.PI + 90;
                if (incremento < 0) {
                    angulos[j][0] = angulos[j][0] + 180;
                }
            } else {
                iteraciones[j][0] = 3;
            }

            dx = posPrev[j][1].getX() - posActu[1][j].getX();
            dy = posPrev[j][1].getY() - posActu[1][j].getY();
            if (dy != 0 || dx != 0) {
                iteraciones[j][1] = iteraciones[j][1] + 1;
                angulos[j][1] = posPrev[j][1].angle(posActu[1][j]) * 180 / Math.PI + 90;
                if (incremento < 0) {
                    angulos[j][1] = angulos[j][1] + 180;
                }
            } else {
                iteraciones[j][1] = 3;
            }
        }

        for (int j = 1; j < angulosAnteriores[0][0].length; j++) {
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 2; y++) {
                    angulosAnteriores[x][y][j - 1] = angulosAnteriores[x][y][j];
                }
            }
        }
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 2; y++) {
                angulosAnteriores[x][y][angulosAnteriores[0][0].length - 1] = angulos[x][y];
            }
        }
        boolean ok = true;
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 2; y++) {
                angVisible[x][y] = 0;
                for (int j = 0; j < angulosAnteriores[0][0].length; j++) {
                    angVisible[x][y] = angVisible[x][y] + angulosAnteriores[x][y][j];
                }
                angVisible[x][y] = angVisible[x][y] / (double) angulosAnteriores[0][0].length;
            }
        }


        if (autoescala) {
            int[] escalas = (Transforma.transform(partido.getPosVisibleBalon(), Constants.centroCampoJuego, -Transforma.transform(px, escala), -Transforma.transform(py, escala), escala));
            escalaAjustada = escala * Math.min(0.7d * sx2 / (double) Math.abs(escalas[0]), 0.7d * sy2 / (double) Math.abs(escalas[1]));
        }
        if (!noAutoEscalar && partido.esGol()) {
            noAutoEscalar = true;
        }
        if (noAutoEscalar && partido.estanRematando()) {
            noAutoEscalar = false;
        }
    }
    double escalaGradual = 0;
    boolean noAutoEscalar = false;
    ArrayList<Object[]> lista = new ArrayList<Object[]>();

    /**Renderiza el juego, uso interno*/
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        double escalaTemporal = 0;
        if (autoescala) {
            escalaTemporal = escala;
            if (!noAutoEscalar) {
                escala = Math.min(escala, escalaAjustada);
                if (partido.getIteracion() < 50) {
                    escalaGradual = escalaGradual + (escala - escalaGradual) * 0.01;
                } else {
                    escalaGradual = escalaGradual + (escala - escalaGradual) * 0.2;
                }
            }

            escala = escalaGradual;
        }
        Position p = new Position(px, py);
        if (entorno) {
            pc.pintaEntorno(g, p, escala);
        }
        pc.pintaCancha(g, p, escala);
        Position[][] pos = partido.getPosiciones();
        if (!partido.esGol() && partido.estanSacando()) {
            double zoom = 1 * escala * (1 + 0.02 * (double) iterSaca);
            rel = Transforma.transform(pos[2][0], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
            g.drawImage(xImage.getScaledCopy((int) zoom, (int) zoom), rel[0] - (int) (zoom / 2), rel[1] - (int) (zoom / 2));

        }
        Position ball = partido.getPosVisibleBalon();
        for (int i = 0; i < 11; i++) {
            rel = Transforma.transform(pos[0][i], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
            pjLocal.pintaSombra(i, iteraciones[i][0], angVisible[i][0], escala, rel[0], rel[1], g);
            rel = Transforma.transform(pos[1][i], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
            pjVisita.pintaSombra(i, iteraciones[i][1], angVisible[i][1], escala, rel[0], rel[1], g);
        }
        z = partido.getAlturaBalon();// 16*Math.sin(Math.abs(ang % Math.PI));

        if (partido.estaRebotando() || z == 0) {
            ang = -balon0.angle(ball);
            velgiro = balon0.distance(ball) * 1.5;
        }
        if (partido.estanRematando()) {
            ang = rand.nextDouble() * Math.PI * 2;
            velgiro = rand.nextDouble();
        }

        balon0 = ball;
        giro = giro + velgiro * 1.5;
        if (giro < 0) {
            giro = 6 + giro;
        }
        rel = Transforma.transform(ball, Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
        pb.pintaSombra(escala, rel[0], rel[1], z, g);
        if (partido.getAlturaBalon() <= 2) {
            pb.pintaBalon((int) (giro), ang, escala, rel[0], rel[1], z * 2, g);
        }
        pp.pintaPublicidad(g, p, escala);

        if (jugador3d) {
            lista.clear();
            for (int i = 0; i < 11; i++) {
                rel = Transforma.transform(pos[0][i], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
                lista.add(new Object[]{pjLocal, i, iteraciones[i][0], angVisible[i][0], escala, rel[0], rel[1]});
                rel = Transforma.transform(pos[1][i], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
                lista.add(new Object[]{pjVisita, i, iteraciones[i][1], angVisible[i][1], escala, rel[0], rel[1]});
            }
            Object[] tmp1, tmp2;
            for (int i = 0; i < lista.size() - 1; i++) {
                for (int j = i + 1; j < lista.size(); j++) {
                    tmp1 = lista.get(i);
                    tmp2 = lista.get(j);
                    if ((Integer) tmp1[6] > (Integer) tmp2[6]) {
                        lista.set(i, tmp2);
                        lista.set(j, tmp1);
                    }
                }
            }

            for (Object obj[] : lista) {
                PintaJugador pj = (PintaJugador) obj[0];
                pj.pintaJugador((Integer) obj[1], (Integer) obj[2], (Double) obj[3], (Double) obj[4], (Integer) obj[5], (Integer) obj[6], g);
            }

            for (int i = 0; i < 11; i++) {
                rel = Transforma.transform(pos[0][i], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
                if (tipoTexto == 3 || (pos[0][i].distance(pos[2][0]) < 8 && tipoTexto == 1)) {
                    pjLocal.pintaNumero(i, rel[0], rel[1], g);
                }
                if (tipoTexto == 4 || (pos[0][i].distance(pos[2][0]) < 8 && tipoTexto == 2)) {
                    pjLocal.pintaNombre(i, rel[0], rel[1], g);
                }
                rel = Transforma.transform(pos[1][i], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
                if (tipoTexto == 3 || (pos[1][i].distance(pos[2][0]) < 8 && tipoTexto == 1)) {
                    pjVisita.pintaNumero(i, rel[0], rel[1], g);
                }
                if (tipoTexto == 4 || (pos[1][i].distance(pos[2][0]) < 8 && tipoTexto == 2)) {
                    pjVisita.pintaNombre(i, rel[0], rel[1], g);
                }
            }
        } else {
            for (int i = 0; i < 11; i++) {
                rel = Transforma.transform(pos[0][i], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
                pjLocal.pintaJugador(i, iteraciones[i][0], angVisible[i][0], escala, rel[0], rel[1], g);
                if (tipoTexto == 3 || (pos[0][i].distance(pos[2][0]) < 8 && tipoTexto == 1)) {
                    pjLocal.pintaNumero(i, rel[0], rel[1], g);
                }
                if (tipoTexto == 4 || (pos[0][i].distance(pos[2][0]) < 8 && tipoTexto == 2)) {
                    pjLocal.pintaNombre(i, rel[0], rel[1], g);
                }
                rel = Transforma.transform(pos[1][i], Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
                pjVisita.pintaJugador(i, iteraciones[i][1], angVisible[i][1], escala, rel[0], rel[1], g);
                if (tipoTexto == 3 || (pos[1][i].distance(pos[2][0]) < 8 && tipoTexto == 1)) {
                    pjVisita.pintaNumero(i, rel[0], rel[1], g);
                }
                if (tipoTexto == 4 || (pos[1][i].distance(pos[2][0]) < 8 && tipoTexto == 2)) {
                    pjVisita.pintaNombre(i, rel[0], rel[1], g);
                }
            }
        }

        rel = Transforma.transform(ball, Constants.centroCampoJuego, -Transforma.transform(px, escala) + sx2, -Transforma.transform(py, escala) + sy2, escala);
        if (partido.getAlturaBalon() > 2) {
            pb.pintaBalon((int) (giro), ang, escala, rel[0], rel[1], z * 2, g);
        }
        pc.pintaArcos(g, p, escala);
        if (estadio) {
            pc.pintaEstadio(g, p, escala);
        }
        if (marcador) {
            pm.pintaMarcador(partido.getGolesLocal(), partido.getGolesVisita(), partido.getIteracion(), partido.getPosesionBalonLocal(), g);
        }
        if (golIter > 0) {
            double zoom = 1 + 0.05 * (golIter % 3);
            g.drawImage(golImage.getScaledCopy((int) (361d * zoom), (int) (81d * zoom)), sx2 - (int) (180d * zoom), sy2 - (int) (40d * zoom));
        }
        
        if (offSideIter > 0) {
        	g.drawImage(offSideImage, sx2-70, sy2 - 20);
        	//g.drawImage(offSideImage, offSideIter - 300, sy2 - 20);        	
        }
        
        if (saqueIter > 0) {
            g.drawImage(cambioImage, saqueIter - 300, sy2 - 20);
        }
        if (autoescala) {
            escala = escalaTemporal;
        }
        if (guardado && iteracionControl > 0) {
            Image img = paImage;
            if (incremento > 0) {
                img = avImage;
            }
            if (incremento < 0) {
                img = reImage;
            }
            g.drawImage(img, sx2 - 10, sy2 - 10);
            g.setColor(Color.white);
            g.drawString("" + incremento + "x", sx2 + 20, sy2 - 10);
        }
        if (guardado && progreso) {
            g.setColor(Color.black);
            g.drawRect(sx - 20, 20, 10, sy - 40);
            g.setColor(Color.darkGray);
            g.fillRect(sx - 19, 21, 8, sy - 42);
            int valor = (int) (((double) sy - 41.0) * ((double) pg.getTiempo() / (double) pg.getIterciones()));
            int valorInicio = (int) (((double) sy - 42.0) * ((double) inicio / (double) pg.getIterciones()));
            int valorFin = (int) (((double) sy - 42.0) * ((double) fin / (double) pg.getIterciones()));
            g.setColor(Color.red);
            g.fillRect(sx - 19, sy - 21 - valor, 8, valor);
            g.setColor(Color.white);
            g.drawLine(sx - 19, sy - 22 - valorFin, sx - 12, sy - 22 - valorFin);
            g.drawLine(sx - 19, sy - 21 - valorFin, sx - 18, sy - 21 - valorFin);
            g.drawLine(sx - 13, sy - 21 - valorFin, sx - 12, sy - 21 - valorFin);
            g.drawLine(sx - 19, sy - 22 - valorInicio, sx - 12, sy - 22 - valorInicio);
            g.drawLine(sx - 19, sy - 23 - valorInicio, sx - 18, sy - 23 - valorInicio);
            g.drawLine(sx - 13, sy - 23 - valorInicio, sx - 12, sy - 23 - valorInicio);
        }
        if (showTexto) {
            if (partido.getIteracion() < 50) {
                g.setColor(Color.black);
                g.drawString(partido.getDetalleVisita().getTacticName() + " (Visita)", sx2 + 11, sy2 + 11);
                g.drawString("vs", sx2 + 41, sy2 + 41);
                g.drawString(partido.getDetalleLocal().getTacticName() + " (Local)", sx2 + 71, sy2 + 71);
                g.setColor(Color.white);
                g.drawString(partido.getDetalleVisita().getTacticName() + " (Visita)", sx2 + 10, sy2 + 10);
                g.drawString("vs", sx2 + 40, sy2 + 40);
                g.drawString(partido.getDetalleLocal().getTacticName() + " (Local)", sx2 + 70, sy2 + 70);
            }
        }
        if (showTexto) {
            if (partido.getIteracion() > Constants.ITERACIONES) {
                g.setColor(Color.black);
                g.drawString("Gana", sx2 + 11, sy2 + 11);
                if ((partido.getGolesLocal() > partido.getGolesVisita())
                        || (partido.getGolesLocal() == partido.getGolesVisita() && partido.getPosesionBalonLocal() >= .5d)) {
                    g.drawString(partido.getDetalleLocal().getTacticName(), sx2 + 41, sy2 + 41);
                } else {
                    g.drawString(partido.getDetalleVisita().getTacticName(), sx2 + 41, sy2 + 41);
                }
                g.setColor(Color.white);
                g.drawString("Gana", sx2 + 10, sy2 + 10);
                if ((partido.getGolesLocal() > partido.getGolesVisita())
                        || (partido.getGolesLocal() == partido.getGolesVisita() && partido.getPosesionBalonLocal() >= .5d)) {
                    g.drawString(partido.getDetalleLocal().getTacticName(), sx2 + 40, sy2 + 40);
                } else {
                    g.drawString(partido.getDetalleVisita().getTacticName(), sx2 + 40, sy2 + 40);
                }
            }
        }
        /*if (isRain) {
            g.setColor(lluvia);
            for (int i = 0; i < 200; i++) {
                double an = rand.nextDouble() * Math.PI * 2d;
                double rad = rand.nextDouble() * 1024;
                int x0 = (int) (sx2 + Math.sin(an) * rad);
                int y0 = (int) (sy2 + Math.cos(an) * rad);
                int x1 = (int) (sx2 + Math.sin(an) * rad * 1.1d);
                int y1 = (int) (sy2 + Math.cos(an) * rad * 1.1d);
                g.drawLine(x0, y0, x1, y1);
            }
            if (rand.nextDouble() < 0.05) {
                g.setColor(relampago);
                g.fillRect(0, 0, sx, sy);
            }
            isRain = rand.nextDouble() < 0.995;
        } else {
            isRain = rand.nextDouble() < 0.005;
        }*/
    }
    boolean isRain = rand.nextDouble() < 0.05d;
    Color lluvia = new Color(200, 200, 255, 192);
    Color relampago = new Color(255, 255, 255, 48);
    Font texto = new Font("Lucida Console", Font.ITALIC, 24);

    /**metodo llamado cuando se cierra la ventana, uso interno*/
    @Override
    public boolean closeRequested() {
        gc.setSoundVolume(0);
        gc.setSoundOn(false);
        stop();
        return true;
    }

    /**Metodo llamado para obtener el titulo de la ventana, uso interno*/
    @Override
    public String getTitle() {
        return "JavaCup";
    }
    private static float volumenAmbiente = 1;

    /**Setea el volumen ambiente*/
    public static void setVolumenAmbiente(float volumen) {
        volumenAmbiente = volumen;
    }
    private static float volumenCancha = 1;

    /**Setea el volumen dentro del campo de juego*/
    public static void setVolumenCancha(float volumen) {
        volumenCancha = volumen;
    }

    /**Setea la escala*/
    public static void setEscala(double escala) {
        VisorOpenGl.escala = escala;
    }

    /**Setea la modalida auto-escala*/
    public static void setAutoescala(boolean auto) {
        VisorOpenGl.autoescala = auto;
    }

    /**Setea si el marcador esta visible*/
    public static void setMarcadorVisible(boolean visible) {
        marcador = visible;
    }

    /**Setea se el entorno externo al estadio es visible*/
    public static void setEntornoVisible(boolean visible) {
        entorno = visible;
    }

    /**Setea si el estadio es visible*/
    public static void setEstadioVisible(boolean visible) {
        estadio = visible;
    }

    /**Cambia la modalidad del texto de los nombres y numeros de los jugadores*/
    public static void setTexto(int tipo) {
        tipoTexto = tipo;
    }
}
