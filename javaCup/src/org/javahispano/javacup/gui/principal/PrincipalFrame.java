package org.javahispano.javacup.gui.principal;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JList;
import org.javahispano.javacup.render.VisorOpenGl;
import org.javahispano.javacup.render.VisorBasico;
import java.net.MalformedURLException;
import com.thoughtworks.xstream.XStream;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.engine.Partido;
import org.javahispano.javacup.model.engine.PartidoGuardado;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**Aplicacion que permite visualizar, grabar, repetir y configurar partidos*/
public final class PrincipalFrame extends javax.swing.JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private XStream xs = new XStream();
    PrincipalDatos datos = new PrincipalDatos();
    private DefaultListModel guardados = new DefaultListModel();
    private final DefaultListModel tacticasModel = new DefaultListModel();
    private JFileChooser jfc;
    private Class tl = null, tv = null;
    private ArrayList<File> filedirs = new ArrayList<File>();
    private static Logger logger = LoggerFactory.getLogger(PrincipalFrame.class);

    /**Busca tacticas en el classpath*/
    void scanForTactics(List<File> dir) {
        tacticasModel.clear();
        filedirs.clear();
        for (File f : dir) {
            try {
                scanDir(f);
            } catch (Exception e) {
                logger.error("Error al scanear directorio de clases", e);
            }
        }
    }

    void scanDir(File dir) throws Exception {
        Class[] classes = new Class[]{};
        try {
            classes = getClases(dir, "", Tactic.class, true);
        } catch (Exception e) {
            classes = new Class[]{};
        }
        for (Class c : classes) {
            if (!c.getName().startsWith("org.javahispano.javacup.modelo.")
                    && !c.getName().startsWith("org.javahispano.javacup.model.")
                    && !c.getName().startsWith("org.javahispano.javacup.gui.")
                    && !c.getName().equals("org.javahispano.javacup.tacticas_aceptadas.DaniP.Termineitor$JugadaEnsayadaAvanzayPasa")) {
                synchronized (tacticasModel) {
                    tacticasModel.addElement(c);
                    filedirs.add(dir);
                }
            }
        }
        tacticas.repaint();
        tacticas.updateUI();
    }

    private String getClassName(String name) {
        if (name == null) {
            return name;
        }
        int idx = name.lastIndexOf(".");
        if (idx > -1) {
            return name.substring(idx + 1);
        }
        return name;
    }

    /**Actualiza la tactica local y la tactica visita*/
    void updateLocalVisita() {
        if (datos.local != null) {
            jLabel8.setText(getClassName(datos.local));
        }
        if (datos.visita != null) {
            jLabel9.setText(getClassName(datos.visita));
        }
    }

    void setEnableSlickButtons(boolean enable) {
        jButton7.setEnabled(enable);
    }
    LinkedList<File> directorios = new LinkedList<File>();
    final HashMap<Class, TacticDetail> tactics = new HashMap<Class, TacticDetail>();

    /** Creates new form Principal */
    public PrincipalFrame() throws Exception {
        initComponents();

        final Font f = new Font("dialog", 1, 18);
        final Font f2 = new Font("arial", 0, 12);
        Image i1 = null;
        Image i2 = null;

        i1 = new ImageIcon(getClass().getResource("/imagenes/minipasto.png")).getImage();
        i2 = new ImageIcon(getClass().getResource("/imagenes/minipasto2.png")).getImage();

        final Image img1 = i1;
        final Image img2 = i2;
        final Dimension dim = new Dimension(0, 34);
        tacticas.setCellRenderer(new ListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Class tacticaClass = (Class) value;
                Object instance = null;
                @SuppressWarnings("element-type-mismatch")
                TacticDetail d = tactics.get(value);
                if (d == null) {
                    try {
                        instance = tacticaClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
                        Tactic t = (Tactic) instance;
                        d = t.getDetail();
                        tactics.put(tacticaClass, d);
                    } catch (Exception e) {
                        logger.error("Error al instanciar tactica " + value, e);
                    }
                }
                final String nombre = d.getTacticName();
                final String otros = "clase(" + tacticaClass.getSimpleName() + ") pais(" + d.getCountry() + ") entrenador(" + d.getCoach() + ")";
                final Color camiseta = new Color(d.getShirtColor().getRed(), d.getShirtColor().getGreen(), d.getShirtColor().getBlue());
                final Color pantalones = new Color(d.getShortsColor().getRed(), d.getShortsColor().getGreen(), d.getShortsColor().getBlue());
                final boolean seleccionado = isSelected;
                JPanel panel = new JPanel() {

                    @Override
                    public void paint(Graphics g) {
                        int cuadros = (getWidth() / 33) + 1;
                        for (int i = 0; i < cuadros; i++) {
                            if (!seleccionado) {
                                g.drawImage(img1, i * 33, 0, null);
                            } else {
                                g.drawImage(img2, i * 33, 0, null);
                            }
                        }
                        g.setFont(f);
                        g.setColor(pantalones);
                        g.drawString(nombre, 5, 17);
                        g.setColor(camiseta);
                        g.drawString(nombre, 4, 16);
                        g.setFont(f2);
                        g.setColor(Color.white);
                        g.drawString(otros, 50, 30);
                        g.setColor(new Color(0, 0, 0, 128));
                        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                    }
                };
                panel.setPreferredSize(dim);
                return panel;
            }
        });

        Image img = new ImageIcon(getClass().getResource("/imagenes/javacup.png")).getImage();
        setIconImage(img);
        this.getContentPane().setBackground(Color.black);
        directorios.add(new File("build" + File.separator + "classes"));
        directorios.add(new File("bin"));
        load();
        updateLocalVisita();
        jfc = new JFileChooser(datos.dir);
        if (datos.ubicacion != null) {
            setBounds(datos.ubicacion);
        }
        jList3.setModel(guardados);
        tacticas.setModel(tacticasModel);
        setVisible(true);
    }

    void load() throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("config.xml");
            datos = (PrincipalDatos) xs.fromXML(fis);
        } catch (Exception ex) {
            datos = new PrincipalDatos();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                }
            }
        }
        if (datos.guardados == null) {
            datos.guardados = new ArrayList<URL>();
        }
        scanForTactics(directorios);
        fromDatos();
    }

    void save() {
        toDatos();
        FileOutputStream fos = null;

        try {
            File out = new File("config.xml");
            fos = new FileOutputStream(out);
            xs.toXML(datos, fos);
        } catch (FileNotFoundException ex) {
            logger.error("Error al guardar configuracion", ex);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private void fromDatos() {
        try {
            jTextField6.setText("" + datos.sx);
        } catch (Exception e) {
        }
        try {
            jTextField7.setText("" + datos.sy);
        } catch (Exception e) {
        }
        try {
            jTextField8.setText("" + datos.escala);
        } catch (Exception e) {
        }
        try {
            jCheckBox4.setSelected(datos.fullscreen);
        } catch (Exception e) {
        }
        try {
            jCheckBox5.setSelected(datos.entrada);
        } catch (Exception e) {
        }
        try {
            jCheckBox3.setSelected(datos.marcador);
        } catch (Exception e) {
        }
        try {
            jCheckBox6.setSelected(datos.jugadores3d);
        } catch (Exception e) {
        }
        try {
            jCheckBox8.setSelected(datos.guardar);
        } catch (Exception e) {
        }
        try {
            jCheckBox1.setSelected(datos.dobleBuffer);
        } catch (Exception e) {
        }
        try {
            jCheckBox2.setSelected(datos.dibujaJugadores);
        } catch (Exception e) {
        }
        try {
            jComboBox1.setSelectedIndex(datos.visor);
        } catch (Exception e) {
        }
        try {
            jComboBox2.setSelectedIndex(datos.tipoTexto);
        } catch (Exception e) {
        }
        try {
            jComboBox4.setSelectedIndex(datos.estadioIdx);
        } catch (Exception e) {
        }
        try {
            jCheckBox7.setSelected(datos.entorno);
        } catch (Exception e) {
        }
        try {
            jCheckBox13.setSelected(datos.estadio);
        } catch (Exception e) {
        }
        try {
            jCheckBox9.setSelected(datos.numeros);
        } catch (Exception e) {
        }
        try {
            jCheckBox10.setSelected(datos.autoescala);
        } catch (Exception e) {
        }
        try {
            jCheckBox12.setSelected(datos.buffer);
        } catch (Exception e) {
        }
        try {
            jCheckBox11.setSelected(datos.sonidos);
        } catch (Exception e) {
        }
        try {
            jCheckBox13.setSelected(datos.estadio);
        } catch (Exception e) {
        }
        try {
            jTabbedPane1.setSelectedIndex(datos.tab);
        } catch (Exception e) {
        }
        try {
            jSlider1.setValue((int) (datos.volumenAmbiente * 100f));
        } catch (Exception e) {
        }
        try {
            jSlider2.setValue((int) (datos.volumenCancha * 100f));
        } catch (Exception e) {
        }
        try {
            this.setBounds(datos.ubicacion);
        } catch (Exception e) {
        }
        try {
            setSize(datos.ubicacion.width, datos.ubicacion.height);
        } catch (Exception e) {
        }
        try {
            setPreferredSize(new Dimension(datos.ubicacion.width, datos.ubicacion.height));
        } catch (Exception e) {
        }
        try {
            setMinimumSize(new Dimension(datos.ubicacion.width, datos.ubicacion.height));
        } catch (Exception e) {
        }
        try {
            setMaximumSize(new Dimension(datos.ubicacion.width, datos.ubicacion.height));
        } catch (Exception e) {
        }
        if (datos.guardados != null) {
            for (URL url : datos.guardados) {
                guardados.addElement(url);
            }
        }
        try {
            jTextField1.setText("" + datos.fps);
        } catch (Exception e) {
        }

        try {
            setResizable(true);
        } catch (Exception e) {
        }
        try {
            setMinimumSize(new Dimension(0, 0));
        } catch (Exception e) {
        }
        try {
            setMaximumSize(new Dimension(4000, 4000));
        } catch (Exception e) {
        }
        try {
            setResizable(true);
        } catch (Exception e) {
        }
        boolean active = jCheckBox11.isSelected();
        jLabel13.setEnabled(active);
        jLabel14.setEnabled(active);
        jSlider2.setEnabled(active);
        jSlider1.setEnabled(active);
    }

    private void toDatos() {
        try {
            datos.sx = Integer.parseInt(jTextField6.getText().trim());
            jTextField6.setForeground(Color.black);
        } catch (NumberFormatException ex) {
            jTextField6.setForeground(Color.red);
        }
        try {
            datos.sy = Integer.parseInt(jTextField7.getText().trim());
            jTextField7.setForeground(Color.black);
        } catch (NumberFormatException ex) {
            jTextField7.setForeground(Color.red);
        }
        try {
            datos.escala = Double.parseDouble(jTextField8.getText().trim());
            jTextField8.setForeground(Color.black);
        } catch (NumberFormatException ex) {
            jTextField8.setForeground(Color.red);
        }
        try {
            datos.fps = Integer.parseInt(jTextField1.getText().trim());
            jTextField1.setForeground(Color.black);
        } catch (NumberFormatException ex) {
            jTextField1.setForeground(Color.red);
        }
        datos.entrada = jCheckBox5.isSelected();
        datos.marcador = jCheckBox3.isSelected();
        datos.jugadores3d = jCheckBox6.isSelected();
        datos.guardar = jCheckBox8.isSelected();
        datos.tipoTexto = jComboBox2.getSelectedIndex();
        datos.visor = jComboBox1.getSelectedIndex();
        datos.fullscreen = jCheckBox4.isSelected();
        datos.entorno = jCheckBox7.isSelected();
        datos.estadio = jCheckBox13.isSelected();
        datos.dobleBuffer = jCheckBox1.isSelected();
        datos.dibujaJugadores = jCheckBox2.isSelected();
        datos.numeros = jCheckBox9.isSelected();
        datos.autoescala = jCheckBox10.isSelected();
        datos.buffer = jCheckBox12.isSelected();
        datos.sonidos = jCheckBox11.isSelected();
        datos.ubicacion = this.getBounds();
        datos.tab = jTabbedPane1.getSelectedIndex();
        datos.volumenAmbiente = (float) jSlider1.getValue() / 100f;
        datos.volumenCancha = (float) jSlider2.getValue() / 100f;
        datos.estadioIdx = jComboBox4.getSelectedIndex();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        configVisor = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tacticas = new javax.swing.JList();
        jPanel17 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jPanel9 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jToolBar2 = new javax.swing.JToolBar();
        jPanel28 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel23 = new javax.swing.JPanel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jSlider1 = new javax.swing.JSlider();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSlider2 = new javax.swing.JSlider();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jCheckBox12 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jCheckBox10 = new javax.swing.JCheckBox();
        jComboBox4 = new javax.swing.JComboBox();
        jCheckBox13 = new javax.swing.JCheckBox();

        configVisor.setTitle("Configuracion del Visor"); // NOI18N
        configVisor.setModal(true);
        configVisor.setResizable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Javacup"); // NOI18N
        setBackground(new java.awt.Color(153, 153, 255));
        setForeground(new java.awt.Color(102, 102, 102));
        setIconImage(new ImageIcon("recursos/imagenes/iconos/icon.png").getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        jTabbedPane1.setBackground(new java.awt.Color(0, 102, 153));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Verdana", 1, 11));

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setBorder(null);

        tacticas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        tacticas.setFont(new java.awt.Font("Verdana", 0, 11));
        tacticas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tacticas.setFocusable(false);
        tacticas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tacticasMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tacticas);

        jPanel1.add(jScrollPane1);

        jPanel17.setMaximumSize(new java.awt.Dimension(120, 2048));
        jPanel17.setLayout(new java.awt.CardLayout());

        jToolBar3.setBackground(new java.awt.Color(102, 102, 102));
        jToolBar3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jToolBar3.setFloatable(false);
        jToolBar3.setOrientation(1);
        jToolBar3.setRollover(true);
        jToolBar3.setMaximumSize(new java.awt.Dimension(160, 32925));
        jToolBar3.setMinimumSize(new java.awt.Dimension(160, 152));
        jToolBar3.setPreferredSize(new java.awt.Dimension(160, 25));

        jPanel9.setMaximumSize(new java.awt.Dimension(150, 50));
        jPanel9.setMinimumSize(new java.awt.Dimension(150, 50));
        jPanel9.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel9.setVerifyInputWhenFocusTarget(false);
        jPanel9.setLayout(new java.awt.CardLayout());

        jButton5.setFont(new java.awt.Font("Verdana", 0, 11));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/local.png"))); // NOI18N
        jButton5.setText("Tactica Local"); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setMaximumSize(new java.awt.Dimension(150, 35));
        jButton5.setMinimumSize(new java.awt.Dimension(150, 35));
        jButton5.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton5, "card2");

        jToolBar3.add(jPanel9);

        jPanel12.setBackground(new java.awt.Color(255, 153, 0));
        jPanel12.setMaximumSize(new java.awt.Dimension(150, 15));
        jPanel12.setMinimumSize(new java.awt.Dimension(150, 15));
        jPanel12.setPreferredSize(new java.awt.Dimension(150, 15));
        jPanel12.setLayout(new java.awt.CardLayout());

        jLabel8.setBackground(new java.awt.Color(255, 153, 0));
        jLabel8.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText(" "); // NOI18N
        jLabel8.setMaximumSize(new java.awt.Dimension(150, 15));
        jLabel8.setMinimumSize(new java.awt.Dimension(150, 15));
        jLabel8.setPreferredSize(new java.awt.Dimension(150, 15));
        jPanel12.add(jLabel8, "card2");

        jToolBar3.add(jPanel12);

        jPanel14.setBackground(new java.awt.Color(0, 102, 153));
        jPanel14.setMaximumSize(new java.awt.Dimension(150, 15));
        jPanel14.setMinimumSize(new java.awt.Dimension(150, 15));
        jPanel14.setPreferredSize(new java.awt.Dimension(150, 15));
        jToolBar3.add(jPanel14);

        jPanel6.setMaximumSize(new java.awt.Dimension(150, 50));
        jPanel6.setMinimumSize(new java.awt.Dimension(150, 50));
        jPanel6.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel6.setVerifyInputWhenFocusTarget(false);
        jPanel6.setLayout(new java.awt.CardLayout());

        jButton6.setFont(new java.awt.Font("Verdana", 0, 11));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/visita.png"))); // NOI18N
        jButton6.setText("Tactica Visita"); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setMaximumSize(new java.awt.Dimension(150, 35));
        jButton6.setMinimumSize(new java.awt.Dimension(150, 35));
        jButton6.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton6, "card2");

        jToolBar3.add(jPanel6);

        jPanel10.setBackground(new java.awt.Color(255, 153, 0));
        jPanel10.setMaximumSize(new java.awt.Dimension(150, 15));
        jPanel10.setMinimumSize(new java.awt.Dimension(150, 15));
        jPanel10.setPreferredSize(new java.awt.Dimension(150, 15));
        jPanel10.setLayout(new java.awt.CardLayout());

        jLabel9.setBackground(new java.awt.Color(255, 153, 0));
        jLabel9.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText(" "); // NOI18N
        jLabel9.setMaximumSize(new java.awt.Dimension(150, 15));
        jLabel9.setMinimumSize(new java.awt.Dimension(150, 15));
        jLabel9.setPreferredSize(new java.awt.Dimension(150, 15));
        jPanel10.add(jLabel9, "card2");

        jToolBar3.add(jPanel10);

        jPanel11.setBackground(new java.awt.Color(0, 102, 153));
        jPanel11.setMaximumSize(new java.awt.Dimension(150, 15));
        jPanel11.setMinimumSize(new java.awt.Dimension(150, 15));
        jPanel11.setPreferredSize(new java.awt.Dimension(150, 15));
        jToolBar3.add(jPanel11);

        jPanel2.setMaximumSize(new java.awt.Dimension(150, 50));
        jPanel2.setMinimumSize(new java.awt.Dimension(150, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel2.setVerifyInputWhenFocusTarget(false);
        jPanel2.setLayout(new java.awt.CardLayout());

        jButton1.setFont(new java.awt.Font("Verdana", 0, 11));
        jButton1.setText("Local <-> Visita"); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(400, 27));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, "card2");

        jToolBar3.add(jPanel2);

        jPanel13.setBackground(new java.awt.Color(0, 102, 153));
        jPanel13.setMaximumSize(new java.awt.Dimension(150, 15));
        jPanel13.setMinimumSize(new java.awt.Dimension(150, 15));
        jPanel13.setPreferredSize(new java.awt.Dimension(150, 15));
        jToolBar3.add(jPanel13);

        jPanel15.setBackground(new java.awt.Color(0, 102, 153));
        jPanel15.setMaximumSize(new java.awt.Dimension(150, 5000));
        jPanel15.setMinimumSize(new java.awt.Dimension(150, 15));
        jPanel15.setPreferredSize(new java.awt.Dimension(150, 15));
        jToolBar3.add(jPanel15);

        jPanel4.setMaximumSize(new java.awt.Dimension(150, 50));
        jPanel4.setMinimumSize(new java.awt.Dimension(150, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 50));
        jPanel4.setVerifyInputWhenFocusTarget(false);
        jPanel4.setLayout(new java.awt.CardLayout());

        jButton7.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/icon.png"))); // NOI18N
        jButton7.setText("Ver Partido"); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setMaximumSize(new java.awt.Dimension(150, 35));
        jButton7.setMinimumSize(new java.awt.Dimension(150, 35));
        jButton7.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7, "card2");

        jToolBar3.add(jPanel4);

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 10));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Visor Basico", "Visor OpenGL" }));
        jComboBox1.setFocusable(false);
        jComboBox1.setMaximumSize(new java.awt.Dimension(150, 20));
        jComboBox1.setMinimumSize(new java.awt.Dimension(150, 20));
        jComboBox1.setPreferredSize(new java.awt.Dimension(150, 20));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jToolBar3.add(jComboBox1);

        jPanel17.add(jToolBar3, "card2");

        jPanel1.add(jPanel17);

        jTabbedPane1.addTab("Tacticas", jPanel1);

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jList3.setBackground(new java.awt.Color(51, 51, 51));
        jList3.setFont(new java.awt.Font("Lucida Console", 0, 11));
        jList3.setForeground(new java.awt.Color(204, 204, 204));
        jList3.setFocusable(false);
        jScrollPane4.setViewportView(jList3);

        jPanel3.add(jScrollPane4);

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(1);
        jToolBar2.setRollover(true);
        jToolBar2.setMaximumSize(new java.awt.Dimension(2048, 2048));
        jToolBar2.setMinimumSize(new java.awt.Dimension(80, 104));
        jToolBar2.setPreferredSize(new java.awt.Dimension(128, 422));

        jPanel28.setLayout(new javax.swing.BoxLayout(jPanel28, javax.swing.BoxLayout.LINE_AXIS));

        jButton10.setFont(new java.awt.Font("Arial", 0, 10));
        jButton10.setText("Agregar partido"); // NOI18N
        jButton10.setFocusable(false);
        jButton10.setMaximumSize(new java.awt.Dimension(2048, 25));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel28.add(jButton10);

        jToolBar2.add(jPanel28);

        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.LINE_AXIS));

        jButton14.setFont(new java.awt.Font("Arial", 0, 10));
        jButton14.setText("Quitar partido"); // NOI18N
        jButton14.setFocusable(false);
        jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton14.setMaximumSize(new java.awt.Dimension(2048, 25));
        jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel29.add(jButton14);

        jToolBar2.add(jPanel29);

        jPanel30.setLayout(new javax.swing.BoxLayout(jPanel30, javax.swing.BoxLayout.LINE_AXIS));

        jPanel24.setMaximumSize(new java.awt.Dimension(10, 32767));
        jPanel24.setMinimumSize(new java.awt.Dimension(0, 10));
        jPanel24.setPreferredSize(new java.awt.Dimension(0, 100));
        jPanel30.add(jPanel24);

        jToolBar2.add(jPanel30);

        jPanel31.setLayout(new javax.swing.BoxLayout(jPanel31, javax.swing.BoxLayout.LINE_AXIS));

        jButton12.setFont(new java.awt.Font("Arial", 0, 10));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/icon.png"))); // NOI18N
        jButton12.setText("Ver Partido"); // NOI18N
        jButton12.setFocusable(false);
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton12.setMaximumSize(new java.awt.Dimension(2048, 50));
        jButton12.setMinimumSize(new java.awt.Dimension(87, 50));
        jButton12.setPreferredSize(new java.awt.Dimension(87, 50));
        jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel31.add(jButton12);

        jToolBar2.add(jPanel31);

        jComboBox3.setFont(new java.awt.Font("Arial", 0, 10));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Visor Basico", "Visor OpenGL" }));
        jComboBox3.setFocusable(false);
        jComboBox3.setMaximumSize(new java.awt.Dimension(32767, 25));
        jComboBox3.setMinimumSize(new java.awt.Dimension(25, 19));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jComboBox3);

        jPanel3.add(jToolBar2);

        jTabbedPane1.addTab("Guardados", jPanel3);

        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.RIGHT);
        jTabbedPane2.setFont(new java.awt.Font("Arial", 0, 10));

        jCheckBox5.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox5.setText(" Mostrar entrada al estado"); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel13.setText("Sonido Ambiente"); // NOI18N

        jLabel14.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel14.setText("Sonido Cancha"); // NOI18N

        jCheckBox8.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox8.setText("Pregunta si guardar los partidos"); // NOI18N

        jCheckBox11.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox11.setText("Activar Sonidos"); // NOI18N
        jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox11ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel1.setText("Frames por segundo"); // NOI18N

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jCheckBox12.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox12.setText("Ejecutar los partidos antes de visualizar"); // NOI18N
        jCheckBox12.setFocusable(false);
        jCheckBox12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jCheckBox12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jCheckBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(209, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox8)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("General", jPanel23);

        jPanel7.setLayout(null);

        jCheckBox1.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox1.setText("Usar doble buffer"); // NOI18N
        jPanel7.add(jCheckBox1);
        jCheckBox1.setBounds(10, 10, 150, 21);

        jCheckBox2.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox2.setText("Dibujar jugadores ( no circulos )"); // NOI18N
        jPanel7.add(jCheckBox2);
        jCheckBox2.setBounds(10, 30, 200, 21);

        jCheckBox9.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox9.setText("Numeros de jugadores"); // NOI18N
        jPanel7.add(jCheckBox9);
        jCheckBox9.setBounds(10, 50, 160, 21);

        jTabbedPane2.addTab("Visor Basico", jPanel7);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel10.setText("Resolucion : "); // NOI18N

        jTextField6.setFont(new java.awt.Font("Arial", 0, 10));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jCheckBox3.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox3.setText("Dibujar tablero electronico [F5]"); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("x"); // NOI18N

        jTextField7.setFont(new java.awt.Font("Arial", 0, 10));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jCheckBox4.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox4.setText("Pantalla Completa"); // NOI18N

        jCheckBox6.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox6.setText("Usar sprites nuevos de jugadores"); // NOI18N

        jCheckBox7.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox7.setText("Dibujar entorno de la ciudad [F2]"); // NOI18N

        jLabel15.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel15.setText("Escala "); // NOI18N

        jTextField8.setFont(new java.awt.Font("Arial", 0, 10));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jComboBox2.setFont(new java.awt.Font("Arial", 0, 10));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sin Informacion", "Numeros cercanos", "Nombres cercanos", "Numeros", "Nombres" }));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel16.setText("Texto de jugadores [F4] "); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 0, 10));
        jLabel17.setText("(Pixel/metro)"); // NOI18N

        jCheckBox10.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox10.setText("Auto escala"); // NOI18N

        jComboBox4.setFont(new java.awt.Font("Arial", 0, 10));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nou Camp", "Bernabeu" }));

        jCheckBox13.setFont(new java.awt.Font("Arial", 0, 10));
        jCheckBox13.setText("Dibujar Estadio [F1]"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox7)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCheckBox13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBox4)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckBox10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17))
                    .addComponent(jCheckBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(209, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox13)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox6)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Visor Open GL", jPanel8);

        jTabbedPane1.addTab("Configuracion", jTabbedPane2);

        getContentPane().add(jTabbedPane1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    run = false;
    save();
}//GEN-LAST:event_formWindowClosing

    private Class foundClass(String name) {
        for (int i = 0; i < tacticasModel.getSize(); i++) {
            Class c = (Class) tacticasModel.get(i);
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**Agrega un partido guardado a la lista de partidos*/
    public void addGuardadoLocal(File[] files) throws MalformedURLException {
        for (File file : files) {
            try {
                URL url = file.toURI().toURL();
                if (!datos.guardados.contains(url)) {
                    datos.guardados.add(url);
                    guardados.addElement(url);
                }
            } catch (Exception e) {
                logger.error("Error al agregar partido guardado", e);
            }

        }
    }

private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
    int idx = jList3.getSelectedIndex();
    if (idx > -1) {
        guardados.remove(idx);
        datos.guardados.remove(idx);
    }
}//GEN-LAST:event_jButton14ActionPerformed

    @SuppressWarnings(value = {"deprecation"})
private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        toDatos();
        final PrincipalFrame princ = this;
        new Thread(new Runnable() {

            @Override
            public void run() {
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                int idx = jList3.getSelectedIndex();
                if (idx == -1) {
                    JOptionPane.showMessageDialog(princ, "Seleccione un fichero");
                    setCursor(Cursor.getDefaultCursor());
                    return;
                }
                toDatos();
                URL url = (URL) guardados.get(jList3.getSelectedIndex());

                try {
                    PartidoGuardado p = new PartidoGuardado(url);
                    setCursor(Cursor.getDefaultCursor());
                    p.iterar();
                    int sx = Integer.parseInt(jTextField6.getText().trim());
                    int sy = Integer.parseInt(jTextField7.getText().trim());
                    if (jComboBox1.getSelectedIndex() == 0) {
                        setVisible(false);
                        VisorBasico.dibujaJugadores = datos.dibujaJugadores;
                        VisorBasico.dobleBuffer = datos.dobleBuffer;
                        VisorBasico.numeros = datos.numeros;
                        new VisorBasico(p, princ);
                    } else {
                        setVisible(false);
                        VisorOpenGl.setVolumenAmbiente(datos.volumenAmbiente);
                        VisorOpenGl.setVolumenCancha(datos.volumenCancha);
                        VisorOpenGl.setMarcadorVisible(datos.marcador);
                        VisorOpenGl.setEntornoVisible(datos.entorno);
                        VisorOpenGl.setEstadioVisible(datos.estadio);
                        VisorOpenGl.setEscala(datos.escala);
                        VisorOpenGl.setAutoescala(datos.autoescala);
                        VisorOpenGl.setTexto(datos.tipoTexto);
                        VisorOpenGl.setFPS(datos.fps);
                        new VisorOpenGl(p, sx, sy, jCheckBox4.isSelected(), princ);
                    }
                } catch (Exception ex) {
                    setCursor(Cursor.getDefaultCursor());
                    JOptionPane.showMessageDialog(princ, ex.getMessage(), "Error al ejecutar partido guardado", JOptionPane.ERROR_MESSAGE);
                    logger.error("Error al ejecutar partido guardado", ex);
                }

            }
        }).start();

}//GEN-LAST:event_jButton12ActionPerformed

private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
    jComboBox1.setSelectedIndex(jComboBox3.getSelectedIndex());
}//GEN-LAST:event_jComboBox3ActionPerformed

private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
    jfc.setMultiSelectionEnabled(true);
    if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        try {
            addGuardadoLocal(jfc.getSelectedFiles());
        } catch (MalformedURLException ex) {
            logger.error("Error al agregar partido guardado", ex);
        }
    }
    jfc.setMultiSelectionEnabled(false);
}//GEN-LAST:event_jButton10ActionPerformed

private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    toDatos();
    try {
        scanForTactics(directorios);
    } catch (Exception e) {
    }
    String msg = "Error Slick, revice la consola";
    try {
        tl = foundClass(datos.local);
    } catch (Exception ex) {
        msg = "No se pudo cargar la tactica local";
    }
    try {
        tv = foundClass(datos.visita);
    } catch (Exception ex) {
        msg = "No se pudo cargar la tactica visita";
    }
    if (tl != null && tv != null) {
        try {
            Tactic tlocal = (Tactic) tl.newInstance(), tvisita = (Tactic) tv.newInstance();
            Partido p = new Partido(tlocal, tvisita, jCheckBox12.isSelected() || jCheckBox8.isSelected());
            if (!datos.entrada) {
                p.inicioRapido();
            }

            int sx = Integer.parseInt(jTextField6.getText().trim());
            int sy = Integer.parseInt(jTextField7.getText().trim());

            if (jComboBox1.getSelectedIndex() == 0) {
                setVisible(false);
                VisorBasico.dibujaJugadores = datos.dibujaJugadores;
                VisorBasico.dobleBuffer = datos.dobleBuffer;
                VisorBasico.numeros = datos.numeros;
                VisorBasico.volumenAmbiente = datos.volumenAmbiente;
                VisorBasico.volumenCancha = datos.volumenCancha;
                VisorBasico.sonidos = datos.sonidos;
                VisorBasico.fps = datos.fps;
                if (jCheckBox12.isSelected()) {
                    while (p.getIteracion() < Constants.ITERACIONES) {
                        try {
                            p.iterar();
                        } catch (Exception e) {
                            logger.error("Error al iterar partido", e);
                        }
                    }
                    p.getPartidoGuardado().iterar();
                    new VisorBasico(p.getPartidoGuardado(), this);
                } else {
                    new VisorBasico(p, this);
                }
            } else {
                setVisible(false);
                VisorOpenGl.estadioIdx = datos.estadioIdx;
                VisorOpenGl.jugador3d = jCheckBox6.isSelected();
                VisorOpenGl.setVolumenAmbiente(datos.volumenAmbiente);
                VisorOpenGl.setVolumenCancha(datos.volumenCancha);
                VisorOpenGl.setMarcadorVisible(datos.marcador);
                VisorOpenGl.setEntornoVisible(datos.entorno);
                VisorOpenGl.setEstadioVisible(datos.estadio);
                VisorOpenGl.setAutoescala(datos.autoescala);
                VisorOpenGl.setEscala(datos.escala);
                VisorOpenGl.setFPS(datos.fps);
                VisorOpenGl.setTexto(datos.tipoTexto);
                VisorOpenGl.sonidos = datos.sonidos;
                if (jCheckBox12.isSelected()) {
                    while (p.getIteracion() < Constants.ITERACIONES) {
                        try {
                            p.iterar();
                        } catch (Exception e) {
                            logger.error("Error al iterar partido", e);
                        }
                    }
                    p.getPartidoGuardado().iterar();
                    new VisorOpenGl(p.getPartidoGuardado(), sx, sy, jCheckBox4.isSelected(), this);
                } else {
                    new VisorOpenGl(p, sx, sy, jCheckBox4.isSelected(), this);
                }
            }
            msg = "";
        } catch (Exception ex) {
            logger.error("Error al ejecutar partido", ex);
            msg = ex.getMessage();
        }
    } else {
        msg = "Primero debe seleccionar establecer el local y la visita";
    }
    if (msg.length() > 0) {
        JOptionPane.showMessageDialog(this, msg);
    }
}//GEN-LAST:event_jButton7ActionPerformed

private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
    jComboBox3.setSelectedIndex(jComboBox1.getSelectedIndex());
}//GEN-LAST:event_jComboBox1ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String tmp = datos.local;
    datos.local = datos.visita;
    datos.visita = tmp;
    updateLocalVisita();
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    if (tacticas.getSelectedIndex() > -1) {
        tv = (Class) tacticas.getSelectedValue();
        datos.visita = tv.getName();
        updateLocalVisita();
    } else {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una tactica");
    }
}//GEN-LAST:event_jButton6ActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    if (tacticas.getSelectedIndex() > -1) {
        tl = (Class) tacticas.getSelectedValue();
        datos.local = tl.getName();
        updateLocalVisita();
    } else {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una tactica");
    }
}//GEN-LAST:event_jButton5ActionPerformed

private void tacticasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tacticasMouseReleased
    if (evt.getButton() == MouseEvent.BUTTON3 && tacticas.getSelectedIndex() > -1) {
    }
}//GEN-LAST:event_tacticasMouseReleased

private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox11ActionPerformed
    boolean active = jCheckBox11.isSelected();
    jLabel13.setEnabled(active);
    jLabel14.setEnabled(active);
    jSlider2.setEnabled(active);
    jSlider1.setEnabled(active);
}//GEN-LAST:event_jCheckBox11ActionPerformed

    private static int[][][] doLiguilla(int n) {
        int partidos = n * (n - 1) / 2;
        int fechas = partidos / (n / 2);
        int partidosPorFecha = partidos / fechas;
        //System.out.println(partidos + " partidos, " + fechas + " fechas, " + partidosPorFecha + " partidos por fecha");
        int[][][] tmp = new int[fechas][partidosPorFecha][2];
        ArrayList<int[]> list = new ArrayList<int[]>();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((i + j) % 2 == 0) {
                    list.add(new int[]{i, j});
                } else {
                    list.add(new int[]{i, j});
                }
            }
        }
        boolean asignados[] = new boolean[n];
        for (int i = 0; i < fechas; i++) {
            for (int k = 0; k < n; k++) {
                asignados[k] = false;
            }
            for (int j = 0; j < partidosPorFecha; j++) {
                int[] found = null;
                int idx = 0;
                while (found == null && idx < list.size()) {
                    int[] element = list.get(idx);
                    if (!asignados[element[0]] && !asignados[element[1]]) {
                        found = element;
                        list.remove(idx);
                        asignados[found[0]] = true;
                        asignados[found[1]] = true;
                    }
                    idx++;
                }
                if (found == null) {
                    //System.out.println("liguilla no encontrada");
                    return null;
                }
                tmp[i][j][0] = found[0];
                tmp[i][j][1] = found[1];
                //System.out.println("fecha " + i + ", partido " + j + ", juegan " + found[0] + "-" + found[1]);
            }
        }
        return tmp;
    }

    /**Obtiene una lista de clases ubicadas dentro de un paquete que son asignables a una clase especifica*/
    @SuppressWarnings("unchecked")
    Class[] getClases(File src, String paquete, Class claseAsignable, boolean recursivo) throws Exception {
        paquete = paquete.replace('.', '/');

        LinkedList<Class> classes = new LinkedList<Class>();
        if (!paquete.endsWith("//svn")) {
            File directory = null;
            try {
                URLClassLoader cld = URLClassLoader.newInstance(new URL[]{src.toURI().toURL()});
                if (cld == null) {
                    throw new ClassNotFoundException(
                            "Cargador de clases no encontrado.");
                }
                directory = new File(src, paquete);
                if (directory.exists()) {
                    File[] files = directory.listFiles();
                    String name, cls;
                    for (int i = 0; i < files.length; i++) {
                        name = files[i].getName();
                        if (files[i].isFile() && name.endsWith(".class")) {
                            cls = (paquete + '/' + name.substring(0, name.length() - 6)).replace('/', '.');
                            if (cls.startsWith(".")) {
                                cls = cls.substring(1);
                            }
                            Class c = cld.loadClass(cls);
                            if (!c.equals(claseAsignable) && claseAsignable.isAssignableFrom(c)) {
                                classes.add(c);
                            }
                        } else if (files[i].isDirectory()) {
                            cls = paquete + "/" + name;
                            for (Class c : getClases(src, cls, claseAsignable,
                                    recursivo)) {
                                classes.add(c);
                            }
                        }
                    }
                } else {
                    throw new ClassNotFoundException(paquete + " no es un paquete valido.");
                }
            } catch (MalformedURLException ex) {
                throw new MalformedURLException(paquete + " (" + src + ") no es una url valido.");
            } catch (NullPointerException x) {
                throw new ClassNotFoundException(paquete + " (" + directory + ") no es un paquete valido.");
            }
        }
        Class[] classesA = new Class[classes.size()];
        classes.toArray(classesA);
        return classesA;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JDialog configVisor;
    javax.swing.JButton jButton1;
    javax.swing.JButton jButton10;
    javax.swing.JButton jButton12;
    javax.swing.JButton jButton14;
    javax.swing.JButton jButton5;
    javax.swing.JButton jButton6;
    javax.swing.JButton jButton7;
    javax.swing.JCheckBox jCheckBox1;
    javax.swing.JCheckBox jCheckBox10;
    javax.swing.JCheckBox jCheckBox11;
    javax.swing.JCheckBox jCheckBox12;
    javax.swing.JCheckBox jCheckBox13;
    javax.swing.JCheckBox jCheckBox2;
    javax.swing.JCheckBox jCheckBox3;
    javax.swing.JCheckBox jCheckBox4;
    javax.swing.JCheckBox jCheckBox5;
    javax.swing.JCheckBox jCheckBox6;
    javax.swing.JCheckBox jCheckBox7;
    javax.swing.JCheckBox jCheckBox8;
    javax.swing.JCheckBox jCheckBox9;
    javax.swing.JComboBox jComboBox1;
    javax.swing.JComboBox jComboBox2;
    javax.swing.JComboBox jComboBox3;
    javax.swing.JComboBox jComboBox4;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel10;
    javax.swing.JLabel jLabel11;
    javax.swing.JLabel jLabel13;
    javax.swing.JLabel jLabel14;
    javax.swing.JLabel jLabel15;
    javax.swing.JLabel jLabel16;
    javax.swing.JLabel jLabel17;
    javax.swing.JLabel jLabel8;
    javax.swing.JLabel jLabel9;
    javax.swing.JList jList3;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel10;
    javax.swing.JPanel jPanel11;
    javax.swing.JPanel jPanel12;
    javax.swing.JPanel jPanel13;
    javax.swing.JPanel jPanel14;
    javax.swing.JPanel jPanel15;
    javax.swing.JPanel jPanel17;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel23;
    javax.swing.JPanel jPanel24;
    javax.swing.JPanel jPanel28;
    javax.swing.JPanel jPanel29;
    javax.swing.JPanel jPanel3;
    javax.swing.JPanel jPanel30;
    javax.swing.JPanel jPanel31;
    javax.swing.JPanel jPanel4;
    javax.swing.JPanel jPanel6;
    javax.swing.JPanel jPanel7;
    javax.swing.JPanel jPanel8;
    javax.swing.JPanel jPanel9;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane4;
    javax.swing.JSlider jSlider1;
    javax.swing.JSlider jSlider2;
    javax.swing.JTabbedPane jTabbedPane1;
    javax.swing.JTabbedPane jTabbedPane2;
    javax.swing.JTextField jTextField1;
    javax.swing.JTextField jTextField6;
    javax.swing.JTextField jTextField7;
    javax.swing.JTextField jTextField8;
    javax.swing.JToolBar jToolBar2;
    javax.swing.JToolBar jToolBar3;
    javax.swing.JList tacticas;
    // End of variables declaration//GEN-END:variables
    boolean run = true;

    @Override
    public void run() {
        while (run) {
            tacticas.repaint();
            tacticas.updateUI();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
