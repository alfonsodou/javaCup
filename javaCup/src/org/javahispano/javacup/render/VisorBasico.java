package org.javahispano.javacup.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.javahispano.javacup.gui.principal.PrincipalFrame;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.engine.PartidoInterface;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.TacticValidate;
import org.newdawn.slick.Sound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
Visor Basico, extiende de JFrame
 */
public class VisorBasico extends javax.swing.JFrame {

    /**variable estatica para setear los Frames por segundo*/
    public static int fps = Constants.FPS;
    private PrincipalFrame principal = null;
    private static Logger logger = LoggerFactory.getLogger(VisorBasico.class);
    /**variable estatica para setear si se dibujaran los jugadores o las esferas*/
    public static boolean dibujaJugadores = true;
    /**Variable estatica para setear si se usara doble buffer*/
    public static boolean dobleBuffer = true;
    /**Variable estatica para setear si se mostraran los numeros de los jugadores*/
    public static boolean numeros = false;
    private Image imgCampo, imgArcoSup, imgArcoInf, imgBalon, balon, balonSombra, sombra;
    private Image imgLocal[] = new Image[7];
    private Image imgVisita[] = new Image[7];
    private Image imgLoc, imgVis;
    private int sx = 327;
    private int sy = 504;
    private int sx2 = sx / 2;
    private int sy2 = sy / 2;
    private int dx = 40;
    private int dy = 47;
    private int[] posArcSup = transformVisorBasico(new Position(-Constants.LARGO_ARCO / 2, -Constants.LARGO_CAMPO_JUEGO / 2 - 2 + 0.2));
    private int[] posArcInf = transformVisorBasico(new Position(-Constants.LARGO_ARCO / 2, Constants.LARGO_CAMPO_JUEGO / 2 + 0.3));
    private Sound[] ambiente = new Sound[9];
    private Sound[] remate = new Sound[2];
    private Sound[] poste = new Sound[2];
    private Sound[] ovacion = new Sound[2];
    private Sound gol;
    private Sound rebote;
    private Sound silbato;

    private int[] transformVisorBasico(Position p) {
        int x = dx + sx2 + (int) (sx * p.getX() / Constants.ANCHO_CAMPO_JUEGO);
        int y = dy + sy2 + (int) (sy * p.getY() / Constants.LARGO_CAMPO_JUEGO);
        return new int[]{x, y};
    }

    private int[] size(Position p) {
        int x = (int) (sx * p.getX() / Constants.ANCHO_CAMPO_JUEGO);
        int y = (int) (sy * p.getY() / Constants.LARGO_CAMPO_JUEGO);
        return new int[]{x, y};
    }

    private void loadSound() {
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
        }
    }

    private void loadImages() throws IOException {
        boolean uniformeAlternativo = TacticValidate.useAlternativeColors(p.getDetalleLocal(), p.getDetalleVisita());
        int polera = new Color(255, 255, 0).getRGB();
        int pantalon = new Color(255, 0, 255).getRGB();
        int calcetas = new Color(0, 0, 255).getRGB();
        int franja = new Color(0, 255, 0).getRGB();
        int pelo = new Color(255, 255, 255).getRGB();

        int[] d0 = size(new Position(Constants.LARGO_ARCO, 2));
        imgCampo = ImageIO.read(getClass().getResource("/imagenes/cancha.png")).getScaledInstance(408, 600, Image.SCALE_SMOOTH);
        imgArcoInf = ImageIO.read(getClass().getResource("/imagenes/arco_inferior.png")).getScaledInstance(d0[0], d0[1], Image.SCALE_SMOOTH);
        imgArcoSup = ImageIO.read(getClass().getResource("/imagenes/arco_superior.png")).getScaledInstance(d0[0], d0[1], Image.SCALE_SMOOTH);
        balon = ImageIO.read(getClass().getResource("/imagenes/minibalon.png"));
        balonSombra = ImageIO.read(getClass().getResource("/imagenes/balon/sombra.png")).getScaledInstance(5, 5, Image.SCALE_SMOOTH);
        sombra = ImageIO.read(getClass().getResource("/imagenes/sombra.png")).getScaledInstance(13, 13, Image.SCALE_SMOOTH);
        int el = p.getDetalleLocal().getStyle().getNumero();
        int ev = p.getDetalleVisita().getStyle().getNumero();
        for (int i = 0; i < 7; i++) {
            imgLocal[i] = ImageIO.read(getClass().getResource("/imagenes/jugador/" + el + "" + i + ".png"));
            imgVisita[i] = ImageIO.read(getClass().getResource("/imagenes/jugador/" + ev + "" + i + ".png"));
        }
        TacticDetail impl = p.getDetalleLocal();
        int upolera;
        int upantalon;
        int ucalcetas;
        int ufranja;
        upolera = new Color(impl.getShirtColor().getRed(), impl.getShirtColor().getGreen(), impl.getShirtColor().getBlue()).getRGB();
        upantalon = new Color(impl.getShortsColor().getRed(), impl.getShortsColor().getGreen(), impl.getShortsColor().getBlue()).getRGB();
        ucalcetas = new Color(impl.getSocksColor().getRed(), impl.getSocksColor().getGreen(), impl.getSocksColor().getBlue()).getRGB();
        ufranja = new Color(impl.getShirtLineColor().getRed(), impl.getShirtLineColor().getGreen(), impl.getShirtLineColor().getBlue()).getRGB();
        int upelo = Color.black.getRGB();
        {
            BufferedImage buf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            Graphics gr = buf.getGraphics();
            gr.setColor(new Color(upolera));
            gr.fillArc(3, 3, 13, 13, 0, 180);
            gr.setColor(new Color(upantalon));
            gr.fillArc(3, 3, 13, 13, 180, 180);
            gr.setColor(new Color(ufranja));
            gr.drawOval(2, 2, 15, 15);
            imgLoc = buf.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        }
        for (int k = 0; k < 7; k++) {
            BufferedImage buf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            buf.getGraphics().drawImage(imgLocal[k], 0, 0, null);
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    int color = buf.getRGB(i, j);
                    if (color == polera) {
                        buf.setRGB(i, j, upolera);
                    } else if (color == pantalon) {
                        buf.setRGB(i, j, upantalon);
                    } else if (color == calcetas) {
                        buf.setRGB(i, j, ucalcetas);
                    } else if (color == franja) {
                        buf.setRGB(i, j, ufranja);
                    } else if (color == pelo) {
                        buf.setRGB(i, j, upelo);
                    }
                }
            }
            imgLocal[k] = buf.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        }

        impl = p.getDetalleVisita();

        if (!uniformeAlternativo) {
            ucalcetas = new Color(impl.getSocksColor().getRed(), impl.getSocksColor().getGreen(), impl.getSocksColor().getBlue()).getRGB();
            upantalon = new Color(impl.getShortsColor().getRed(), impl.getShortsColor().getGreen(), impl.getShortsColor().getBlue()).getRGB();
            upolera = new Color(impl.getShirtColor().getRed(), impl.getShirtColor().getGreen(), impl.getShirtColor().getBlue()).getRGB();
            ufranja = new Color(impl.getShirtLineColor().getRed(), impl.getShirtLineColor().getGreen(), impl.getShirtLineColor().getBlue()).getRGB();
        } else {
            ucalcetas = new Color(impl.getSocksColor2().getRed(), impl.getSocksColor2().getGreen(), impl.getSocksColor2().getBlue()).getRGB();
            upantalon = new Color(impl.getShortsColor2().getRed(), impl.getShortsColor2().getGreen(), impl.getShortsColor2().getBlue()).getRGB();
            upolera = new Color(impl.getShirtColor2().getRed(), impl.getShirtColor2().getGreen(), impl.getShirtColor2().getBlue()).getRGB();
            ufranja = new Color(impl.getShirtLineColor2().getRed(), impl.getShirtLineColor2().getGreen(), impl.getShirtLineColor2().getBlue()).getRGB();
        }

        {
            BufferedImage buf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            Graphics gr = buf.getGraphics();
            gr.setColor(new java.awt.Color(impl.getShirtColor().getRGB()));
            gr.fillArc(4, 4, 11, 11, 0, 180);
            gr.setColor(new java.awt.Color(impl.getShortsColor().getRGB()));
            gr.fillArc(4, 4, 11, 11, 180, 180);
            gr.setColor(new java.awt.Color(impl.getShirtLineColor().getRGB()));
            gr.drawOval(2, 2, 15, 15);
            imgVis = buf.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        }
        for (int k = 0; k < 7; k++) {
            BufferedImage buf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            buf.getGraphics().drawImage(imgVisita[k], 0, 0, null);
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    int color = buf.getRGB(i, j);
                    if (color == polera) {
                        buf.setRGB(i, j, upolera);
                    } else if (color == pantalon) {
                        buf.setRGB(i, j, upantalon);
                    } else if (color == calcetas) {
                        buf.setRGB(i, j, ucalcetas);
                    } else if (color == franja) {
                        buf.setRGB(i, j, ufranja);
                    } else if (color == pelo) {
                        buf.setRGB(i, j, upelo);
                    }
                }
            }
            imgVisita[k] = buf.getScaledInstance(13, 13, Image.SCALE_SMOOTH);
        }
        for (int i = 0; i < 7; i++) {
            imgLocal[i] = imgLocal[i].getScaledInstance(13, 13, Image.SCALE_SMOOTH);
            imgVisita[i] = imgVisita[i].getScaledInstance(13, 13, Image.SCALE_SMOOTH);
        }
    }
    private PartidoInterface p;
    private BufferedImage bimg = new BufferedImage(408, 628, BufferedImage.TYPE_INT_ARGB);
    private Position panterior[][] = new Position[2][11];
    private Position[][] pos = new Position[2][11];
    private Font f = new Font("arial", 0, 9);
    private DecimalFormat df = new DecimalFormat("##.##");

    public void pinta(Graphics g) {
        Graphics2D gr;

        if (dobleBuffer) {
            gr = (Graphics2D) bimg.getGraphics();
        } else {
            gr = (Graphics2D) g;
        }
        gr.setFont(f);
        double altura = p.getAlturaBalon();
        gr.drawImage(imgCampo, 0, 0, null);
        int pp[], pb[];


        pb = transformVisorBasico(p.getPosVisibleBalon());
        gr.drawImage(balonSombra, pb[0] + (int) (altura * 2d), pb[1] + (int) (altura * 2d), null);
        if (altura <= 2.9) {
            gr.drawImage(balon, pb[0] - 2, pb[1] - 2, null);
        }
        if (dibujaJugadores) {
            for (int i = 0; i < 11; i++) {
                pp = transformVisorBasico(pos[0][i]);
                gr.drawImage(sombra, pp[0], pp[1], null);
                pp = transformVisorBasico(pos[1][i]);
                gr.drawImage(sombra, pp[0], pp[1], null);
            }
            int it;
            for (int i = 0; i < 11; i++) {
                pp = transformVisorBasico(pos[0][i]);
                gr.translate(pp[0], pp[1]);
                gr.rotate(ang[0][i]);
                it = iter[0][i];
                if (it > 6) {
                    it = 13 - it;
                }
                gr.drawImage(imgLocal[it], - 6, - 6, null);
                gr.rotate(-ang[0][i]);
                if (numeros) {
                    gr.setColor(Color.yellow);
                    gr.drawString("" + p.getDetalleLocal().getPlayers()[i].getNumber(), -2, 18);
                }
                gr.translate(-pp[0], -pp[1]);
                pp = transformVisorBasico(pos[1][i]);
                gr.translate(pp[0], pp[1]);
                gr.rotate(ang[1][i]);
                it = iter[1][i];
                if (it > 6) {
                    it = 13 - it;
                }
                gr.drawImage(imgVisita[it], - 6, - 6, null);
                gr.rotate(-ang[1][i]);
                if (numeros) {
                    gr.setColor(Color.yellow);
                    gr.drawString("" + p.getDetalleVisita().getPlayers()[i].getNumber(), -2, -10);
                }
                gr.translate(-pp[0], -pp[1]);
            }
        }
        if (!dibujaJugadores) {
            for (int i = 0; i < 11; i++) {
                pp = transformVisorBasico(pos[0][i]);
                gr.drawImage(imgLoc, pp[0] - 10, pp[1] - 10, null);
                if (numeros) {
                    int num = p.getDetalleLocal().getPlayers()[i].getNumber();
                    gr.setColor(Color.black);
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 2, pp[1] + 2);
                    } else {
                        gr.drawString("" + num, pp[0] - 5, pp[1] + 2);
                    }
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 2, pp[1] + 4);
                    } else {
                        gr.drawString("" + num, pp[0] - 5, pp[1] + 4);
                    }
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 3, pp[1] + 3);
                    } else {
                        gr.drawString("" + num, pp[0] - 6, pp[1] + 3);
                    }
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 1, pp[1] + 3);
                    } else {
                        gr.drawString("" + num, pp[0] - 4, pp[1] + 3);
                    }
                    gr.setColor(Color.yellow);
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 2, pp[1] + 3);
                    } else {
                        gr.drawString("" + num, pp[0] - 5, pp[1] + 3);
                    }
                }
                pp = transformVisorBasico(pos[1][i]);
                gr.drawImage(imgVis, pp[0] - 10, pp[1] - 10, null);
                if (numeros) {
                    int num = p.getDetalleVisita().getPlayers()[i].getNumber();
                    gr.setColor(Color.black);
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 2, pp[1] + 2);
                    } else {
                        gr.drawString("" + num, pp[0] - 5, pp[1] + 2);
                    }
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 2, pp[1] + 4);
                    } else {
                        gr.drawString("" + num, pp[0] - 5, pp[1] + 4);
                    }
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 3, pp[1] + 3);
                    } else {
                        gr.drawString("" + num, pp[0] - 6, pp[1] + 3);
                    }
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 1, pp[1] + 3);
                    } else {
                        gr.drawString("" + num, pp[0] - 4, pp[1] + 3);
                    }
                    gr.setColor(Color.yellow);
                    if (num < 10) {
                        gr.drawString("" + num, pp[0] - 2, pp[1] + 3);
                    } else {
                        gr.drawString("" + num, pp[0] - 5, pp[1] + 3);
                    }
                }
            }
        }
        gr.drawImage(imgArcoSup, posArcSup[0], posArcSup[1], null);
        gr.drawImage(imgArcoInf, posArcInf[0], posArcInf[1], null);
        if (p.getAlturaBalon() > 2.9) {
            gr.drawImage(balon, pb[0] - 2, pb[1] - 2, null);
        }
        gr.setColor(Color.yellow);
        String time = "0";
        int sec = p.getIteracion() * Constants.DELAY / 1000;
        time = time + sec / 60 + ":";
        sec = sec % 60;
        if (sec < 10) {
            time = time + "0";
        }
        time = time + sec;
        gr.drawString(time, 30, 585);
        time = p.getGolesLocal() + " - " + p.getGolesVisita();
        gr.drawString(time, 195, 585);
        time = df.format(100d * p.getPosesionBalonLocal()) + "% - " + df.format(100d * (1d - p.getPosesionBalonLocal())) + "%";
        gr.drawString(time, 300, 585);
        if (dobleBuffer) {
            if (getWidth() == 408 && getHeight() == 600) {
                g.drawImage(bimg, 0, 0, null);
            } else {
                g.drawImage(bimg.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, null);
            }
        }
    }
    private final Timer timer;
    private double[][] ang = new double[2][11];
    private int[][] iter = new int[2][11];
    private int audioIdx = 0;
    private int audioAmbiente = 0;
    private int audioAmbienteIdx = 0;
    /**Activa o desactiva los sonidos*/
    public static boolean sonidos = true;

    /**Instancia un visor basicom indicando el partido y jframe principal(dejar nulo)
     *
     * @param partido
     * @param principal
     * @throws IOException
     */
    public VisorBasico(PartidoInterface partido, PrincipalFrame principal) throws IOException {

        this.principal = principal;
        p = partido;
        loadImages();
        if (sonidos) {
            loadSound();
        }
        initComponents();
        Image img = new ImageIcon(getClass().getResource("/imagenes/javacup.png")).getImage();
        setIconImage(img);
        setResizable(dobleBuffer);
        int delay = 920 / fps;
        final VisorBasico este = this;
        try {
            p.iterar();
        } catch (Exception e) {
        }
        for (int i = 0; i < 11; i++) {
            panterior[0][i] = new Position();
            panterior[1][i] = new Position();
        }
        pos = p.getPosiciones();

        timer = new Timer(delay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (audioAmbiente == audioAmbienteIdx) {
                    if (sonidos) {
                        ambiente[audioIdx].play(pinch(), volumenAmbiente);
                    }
                    audioAmbiente = 50 + rand.nextInt(20);
                    audioAmbienteIdx = 0;
                    audioIdx = rand.nextInt(ambiente.length);
                }
                audioAmbienteIdx++;

                if (p.getIteracion() < Constants.ITERACIONES) {
                    try {
                        for (int i = 0; i < 11; i++) {
                            panterior[0][i] = pos[0][i];
                            panterior[1][i] = pos[1][i];
                        }
                        p.iterar();
                        if (p.estanSilbando()) {
                            if (sonidos) {
                                silbato.play(pinch(), volumenCancha);
                            }

                        }
                        if (p.estanRematando()) {
                            if (sonidos) {
                                remate[rand.nextInt(2)].play(pinch(), volumenCancha);
                            }
                        }
                        if (p.estanOvacionando()) {
                            if (sonidos) {
                                ovacion[rand.nextInt(2)].play(pinch(), volumenAmbiente);
                            }
                        }
                        if (p.esGol()) {
                            if (sonidos) {
                                gol.play(pinch(), volumenAmbiente);
                            }
                        }
                        if (p.cambioDeSaque()) {
                            if (sonidos) {
                                silbato.play(pinch(), volumenAmbiente);
                            }
                        }
                        if (p.isOffSide()) {//Es fuera de juego
                            if (sonidos) {
                                silbato.play(pinch(), volumenAmbiente);
                            }
                        }
                        if (p.estaRebotando()) {
                            if (sonidos) {
                                rebote.play(pinch(), volumenCancha);
                            }
                        }
                        if (p.esPoste()) {
                            if (sonidos) {
                                poste[rand.nextInt(2)].play(pinch(), volumenCancha);
                                ovacion[rand.nextInt(2)].play(pinch(), volumenAmbiente);
                            }
                        }
                        pos = p.getPosiciones();
                        for (int i = 0; i < 11; i++) {
                            if (!panterior[0][i].equals(pos[0][i])) {
                                ang[0][i] = panterior[0][i].angle(pos[0][i]) + Math.PI / 2;
                                iter[0][i] = (iter[0][i] + 1) % 14;
                            } else {
                                iter[0][i] = 3;
                            }
                            if (!panterior[1][i].equals(pos[1][i])) {
                                ang[1][i] = panterior[1][i].angle(pos[1][i]) + Math.PI / 2;
                                iter[1][i] = (iter[1][i] + 1) % 14;
                            } else {
                                iter[1][i] = 3;
                            }
                        }
                    } catch (Exception ex) {
                        logger.error("Error al iterar", ex);
                    }
                    este.repaint();
                }
            }
        });
        timer.start();
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        if (principal != null) {
            principal.setVisible(false);
        }
    }
    Thread thread;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel(){

            @Override
            public void paint(Graphics g){
                pinta(g);
            }

        };

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Visor Basico");
        setAlwaysOnTop(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setMaximumSize(new java.awt.Dimension(408, 580));
        jPanel1.setMinimumSize(new java.awt.Dimension(408, 580));
        jPanel1.setPreferredSize(new java.awt.Dimension(408, 580));
        jPanel1.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    JFileChooser jfc = new JFileChooser();

    private void stop() {
        timer.stop();
        dispose();
        if (p.fueGrabado()) {
            if (JOptionPane.showConfirmDialog(principal, "Desea guardar el partido?", "Guardar Partido", JOptionPane.YES_NO_OPTION) == 0) {
                if (jfc.showSaveDialog(principal) == JFileChooser.APPROVE_OPTION) {
                    try {
                        p.getPartidoGuardado().save(jfc.getSelectedFile());
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
            for (Sound s : ambiente) {
                s.stop();
            }
        }
        if (principal == null) {
            System.exit(0);
        } else {
            principal.setVisible(true);
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        stop();
    }//GEN-LAST:event_formWindowClosing

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            stop();
        }
    }//GEN-LAST:event_formKeyPressed
    private Random rand = new Random();

    private float pinch() {
        return .95f + rand.nextFloat() * .1f;
    }
    /**Volumen del sonido ambiente*/
    public static float volumenAmbiente = .5f;
    /**Volumen de los sonidos dentro de la cancha*/
    public static float volumenCancha = 1;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
