package org.javahispano.javacup.model.engine;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JFrame;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;


/**
 * Clase usada para cargar y guardar partidos, uso interno
 */
public final class PartidoGuardado implements PartidoInterface, Serializable {

    //private static Logger logger = LoggerFactory.getLogger(PartidoGuardado.class);
    private static Logger logger = Logger.getLogger(PartidoGuardado.class.getName());
    private static final long serialVersionUID = 1L;
    transient private int tiempo = -1;
    transient private Iteracion iteracion;
    /**
     * Lista de iteraciones, uso interno
     */
    public ArrayList<Iteracion> partido = new ArrayList<>(Constants.ITERACIONES);
    private TacticDetail detalleLocal;
    private TacticDetail detalleVisita;

    /**
     * Instancia un partido guardado indicando los detalles de el local y la visita
     */
    public PartidoGuardado(TacticDetail detalleLocal, TacticDetail detalleVisita) {
        this.detalleLocal = detalleLocal;
        this.detalleVisita = detalleVisita;
    }

    /**
     * Elimina desde la iteracion inicio hasta la iteracion fin, usado para resumir partidos
     */
    public void delete(int inicio, int fin) {
        for (int i = inicio; i < fin; i++) {
            partido.remove(inicio);
        }
        setTiempo(inicio);
    }

    /**
     * Inidica la cantidad de iteraciones del partido
     */
    public int getIterciones() {
        return partido.size();
    }

    /**
     * Estable la iteracion actual
     */
    public void setTiempo(int tiempo) {
        if (tiempo < 0) {
            tiempo = 0;
        }
        if (tiempo >= partido.size()) {
            tiempo = partido.size() - 1;
        }
        this.tiempo = tiempo;
        iteracion = partido.get((int) tiempo);
    }

    /**
     * Retorna la iteracion actual
     */
    public int getTiempo() {
        return tiempo;
    }

    @Override
    public boolean esGol() {
        return iteracion.gol;
    }

    @Override
    public boolean esPoste() {
        return iteracion.poste;
    }

    @Override
    public boolean estaRebotando() {
        return iteracion.rebotando;
    }

    @Override
    public boolean estanOvacionando() {
        return iteracion.ovacionando;
    }

    @Override
    public boolean estanRematando() {
        return iteracion.rematando;
    }

    @Override
    public boolean estanSacando() {
        return iteracion.sacando;
    }

    @Override
    public boolean estanSilbando() {
        return iteracion.silbando;
    }

    @Override
    public boolean cambioDeSaque() {
        return iteracion.cambioSaque;
    }

    @Override
    public boolean isOffSide() {

        return iteracion.isOffSide;
    }

    @Override
    public boolean isLibreIndirecto() {

        return iteracion.isLibreIndirecto;
    }

    @Override
    public double getAlturaBalon() {
        return (double) iteracion.alturaBalon / 256d;
    }

    @Override
    public TacticDetail getDetalleLocal() {
        return detalleLocal;
    }

    @Override
    public TacticDetail getDetalleVisita() {
        return detalleVisita;
    }

    @Override
    public Position getPosVisibleBalon() {
        return new Position((double) iteracion.posVisibleBalonX / 256d, (double) iteracion.posVisibleBalonY / 256d);
    }

    @Override
    public Position[][] getPosiciones() {
        Position[][] pos = new Position[3][11];
        for (int i = 0; i < 11; i++) {
            pos[0][i] = new Position((double) iteracion.posiciones[0][i][0] / 256d, (double) iteracion.posiciones[0][i][1] / 256d);
            pos[1][i] = new Position((double) iteracion.posiciones[1][i][0] / 256d, (double) iteracion.posiciones[1][i][1] / 256d);
        }
        pos[2][0] = new Position((double) iteracion.posiciones[2][0][0] / 256d, (double) iteracion.posiciones[2][0][1] / 256d);
        return pos;
    }

    @Override
    public void iterar() throws Exception {
        if (tiempo < partido.size() - 1) {
            tiempo++;
        }
        setTiempo(tiempo);
    }

    @Override
    public int getGolesLocal() {
        return iteracion.golesLocal;
    }

    @Override
    public int getGolesVisita() {
        return iteracion.golesVisita;
    }

    @Override
    public int getIteracion() {
        return iteracion.iteracion;
    }

    @Override
    public double getPosesionBalonLocal() {
        return (double) iteracion.posecionBalonLocal / 256d;
    }

    @Override
    public PartidoGuardado getPartidoGuardado() {
        return this;
    }
    private URL url = null;

    /**
     * Retorna la url donde se almacena el partido guardado
     */
    public URL getURL() {
        return url;
    }

    /**
     * Instancia un partido guardado a partir de una url
     */
    public PartidoGuardado(final URL url) throws Exception {
        PartidoGuardado pg = null;
        try {
            pg = PartidoGuardado.load(url);
            this.url = url;
        } catch (Exception ex) {
            logger.severe("Error al cargar partido guardado: " + ex.getMessage());
        }
        detalleLocal = pg.detalleLocal;
        detalleVisita = pg.detalleVisita;
        partido = pg.partido;
        iteracion = partido.get(0);
    }

    private static File unzip(File file) throws Exception {
        ZipFile zf = new ZipFile(file.getAbsolutePath());
        Enumeration e = zf.entries();

        ZipEntry ze = (ZipEntry) e.nextElement();
        File f = new File(new File(ze.getName()).getName());
        FileOutputStream fout = new FileOutputStream(f);
        InputStream in = zf.getInputStream(ze);
        byte[] buffer = new byte[4096];
        int read;
        while ((read = in.read(buffer)) > -1) {
            fout.write(buffer, 0, read);
        }
        in.close();
        fout.close();
        zf.close();
        return f;
    }

    private static void zip(String entry, File file) throws Exception {
        FileOutputStream fout = new FileOutputStream(file);
        ZipOutputStream zout = new ZipOutputStream(fout);
        zout.setLevel(java.util.zip.Deflater.BEST_SPEED);
        FileInputStream fin = new FileInputStream(entry);
        try {
            zout.putNextEntry(new ZipEntry(entry));
            byte[] buffer = new byte[4096];
            int read;
            while ((read = fin.read(buffer)) > -1) {
                zout.write(buffer, 0, read);
            }
        } finally {
            fin.close();
        }
        zout.close();
    }
    /**
     * Muesta un jframe que indica que el partido se esta cargando
     */
    public static boolean SHOWFRAME = true;

    private static PartidoGuardado load(URL url) throws Exception {
        JFrame frame = null;
        if (SHOWFRAME) {
            frame = new JFrame("Cargando Partido ");
            //frame.add(new JLabel(url.toURI().getPath()));
            frame.pack();
            frame.setAlwaysOnTop(true);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        InputStream is = null;
        Exception e = null;
        byte bytes[] = new byte[4096];
        File tempFile = null;
        File unziped = null;
        try {
            tempFile = File.createTempFile("tmp", ".zip", new File("."));
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            is = url.openStream();
            int read;
            while ((read = is.read(bytes)) > -1) {
                fos.write(bytes, 0, read);
            }
            fos.close();
            is.close();
            unziped = unzip(tempFile);
            //System.out.println("creando " + unziped);
            unziped.deleteOnExit();
            FileInputStream fis = new FileInputStream(unziped);
            ObjectInputStream ois = new ObjectInputStream(fis);
            PartidoGuardado p = (PartidoGuardado) ois.readObject();
            ois.close();
            fis.close();
            tempFile.delete();
            unziped.delete();
            //System.out.println("borrando " + unziped);
            p.iteracion = p.partido.get(0);
            if (SHOWFRAME) {
                frame.setVisible(false);
                frame.dispose();
            }
            return p;
        } catch (ClassNotFoundException | IOException ex) {
            e = ex;
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                e = ex;
            }
        }
        if (e != null) {
            logger.severe("Error al cargar partido guardado: " + e.getMessage());
            if (SHOWFRAME) {
                frame.setVisible(false);
                frame.dispose();
            }
            throw e;
        }
        frame.setVisible(false);
        frame.dispose();
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
        if (unziped != null && unziped.exists()) {
            unziped.delete();
        }
        return null;
    }

    /**
     * Guarda el partido en un fichero
     */
    public void save(File file) throws Exception {

        FileOutputStream fos = null;
        Exception e = null;
        ObjectOutputStream oos = null;
        File tempFile = File.createTempFile("tmp", ".jvc");
        try {
            fos = new FileOutputStream(tempFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
            zip(tempFile.getAbsolutePath(), file);
            tempFile.delete();
        } catch (IOException ex) {
            e = ex;
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                e = ex;
            }
            try {
                oos.close();
            } catch (IOException ex) {
                e = ex;
            }
        }
        if (e != null) {
            logger.severe("Error al guardar partido: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean fueGrabado() {
        return false;
    }
    /////////////////////////////////////////////////////////////////////////
    /*Guarda el partido en binario*/
    /////////////////////////////////////////////////////////////////////////
    private static final int _flagAmp[] = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768};

    private static short bits2Short(boolean... values) throws Exception {
        int size = values.length;
        int tmp = 0;
        if (size <= 16) {
            for (int i = 0; i < values.length; i++) {
                if (values[i]) {
                    tmp = tmp + 1 * _flagAmp[i];
                }
            }
        }
        return (short) tmp;
    }

    private static void shorts2Stream(OutputStream os, short... values) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(2 * values.length);
        bb.asShortBuffer().put(values);
        os.write(bb.array());
    }

    private static void bytes2Stream(OutputStream os, byte... values) throws IOException {
        for (byte b : values) {
            os.write(b);
        }
    }

    private static void strings2Stream(OutputStream os, String... values) throws IOException {
        for (String s : values) {
            os.write(s.getBytes());
            os.write(0);
        }
    }

    private static void colors2Stream(OutputStream os, Color... values) throws IOException {
        for (Color c : values) {
            os.write(new byte[]{(byte) c.getRed(), (byte) c.getGreen(), (byte) c.getBlue()});
        }
    }

    private static void detail2Stream(OutputStream os, TacticDetail... values) throws FileNotFoundException, IOException {
        for (TacticDetail detail : values) {
            strings2Stream(os, detail.getTacticName(), detail.getCoach(), detail.getCountry());
            bytes2Stream(os, (byte) detail.getStyle().getNumero(), (byte) detail.getStyle2().getNumero());
            colors2Stream(os,
                    detail.getGoalKeeper(), detail.getGoalKeeper2(),
                    detail.getShirtColor(), detail.getShirtColor2(),
                    detail.getShirtLineColor(), detail.getShirtLineColor2(),
                    detail.getShortsColor(), detail.getShortsColor2(),
                    detail.getSocksColor(), detail.getSocksColor2());
            byte goalKeeperIdx = -1;
            byte idx = 0;
            for (PlayerDetail player : detail.getPlayers()) {
                bytes2Stream(os, (byte) player.getNumber());
                strings2Stream(os, player.getPlayerName());
                colors2Stream(os, player.getSkinColor(), player.getHairColor());
                if (player.isGoalKeeper()) {
                    goalKeeperIdx = idx;
                }
                idx++;
            }
            System.out.println(goalKeeperIdx);
            bytes2Stream(os, goalKeeperIdx);
        }
    }

    private static void iterationStream(OutputStream os, List<Iteracion> values) throws FileNotFoundException, IOException, Exception {
        for (Iteracion i : values) {
            shorts2Stream(os, bits2Short(i.gol, i.poste, i.rebotando, i.ovacionando, i.rematando, i.sacando, i.silbando, i.cambioSaque, i.isLibreIndirecto, i.isOffSide));
            shorts2Stream(os, i.alturaBalon, i.posecionBalonLocal, i.posVisibleBalonX, i.posVisibleBalonY);
            shorts2Stream(os, i.posiciones[2][0]);
            bytes2Stream(os, (byte) i.golesLocal, (byte) i.golesVisita);
            for (int eq = 0; eq < 2; eq++) {
                for (int jug = 0; jug < 11; jug++) {
                    shorts2Stream(os, i.posiciones[eq][jug]);
                }
            }
        }
    }

    public void binarySave(File file) throws FileNotFoundException, IOException, Exception {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            detail2Stream(fos, detalleLocal, detalleVisita);
            shorts2Stream(fos, (short) partido.size());
            iterationStream(fos, partido);
        }
    }
    
    public byte[] binaryServe() throws IOException, Exception {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	try  {
            detail2Stream(bos, detalleLocal, detalleVisita);
            shorts2Stream(bos, (short) partido.size());
            iterationStream(bos, partido);		
    	} finally {
    		logger.info("Partido en modo binario");
    	}
    	
    	return bos.toByteArray();
    }

    public static ArrayList<File> getFiles(ArrayList<File> files, File parentDir) {
        if (files == null) {
            files = new ArrayList<>();
        }
        File[] childs = parentDir.listFiles();
        for (File child : childs) {
            if (!child.isDirectory() && child.getName().toLowerCase().endsWith(".jvc")) {
                files.add(child);
            } else if (child.isDirectory()) {
                getFiles(files, child);
            }
        }
        return files;
    }

    public static void main(String[] args) throws MalformedURLException, FileNotFoundException, IOException, Exception {
        ArrayList<File> files = getFiles(null, new File("/home/adou/jvc2013/partidos/eliminatorias/FF"));
        for (File f : files) {
            new PartidoGuardado(f.toURL()).binarySave(new File(f.getAbsolutePath() + "web"));
        }
    }

	@Override
	public long[] getLocalTime() {
		long[] result = new long[Constants.ITERACIONES];
		for(int i = 0; i < Constants.ITERACIONES; i++) {
			result[i] = partido.get(i).timeLocal;
		}
		return result;
	}

	@Override
	public long[] getVisitaTime() {
		long[] result = new long[Constants.ITERACIONES];
		for(int i = 0; i < Constants.ITERACIONES; i++) {
			result[i] = partido.get(i).timeVisita;
		}
		return result;
	}
}
