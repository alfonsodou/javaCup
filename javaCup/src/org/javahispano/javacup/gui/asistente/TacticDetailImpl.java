package org.javahispano.javacup.gui.asistente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**Esta clase implementa TacticDetail es usada internamente por el Asistente, pero
tambien puede usarse para cargar dinamicamente desde un archivo guardado en el codigo, una clase TacticDetail */
public final class TacticDetailImpl implements TacticDetail {

    private static Logger logger = LoggerFactory.getLogger(TacticDetailImpl.class);
    private transient Position alineacion1[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-19.46564885496183, -31.6044776119403),
        new Position(0.2595419847328244, -31.082089552238806),
        new Position(19.984732824427482, -31.6044776119403),
        new Position(7.526717557251908, -11.753731343283583),
        new Position(-8.564885496183205, -11.753731343283583),
        new Position(-24.65648854961832, -2.3507462686567164),
        new Position(23.099236641221374, -2.873134328358209),
        new Position(-14.274809160305344, 30.559701492537314),
        new Position(-0.7786259541984732, 8.097014925373134),
        new Position(12.717557251908397, 29.51492537313433)
    };
    private transient Position alineacion2[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(27.251908396946565, -27.94776119402985),
        new Position(-29.84732824427481, -26.902985074626866),
        new Position(8.564885496183205, -7.574626865671642),
        new Position(-10.641221374045802, -7.052238805970149),
        new Position(27.251908396946565, 4.440298507462686),
        new Position(-29.32824427480916, 3.3955223880597014),
        new Position(-0.2595419847328244, 19.067164179104477),
        new Position(-0.2595419847328244, 35.78358208955224)
    };
    private transient Position alineacion3[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(26.732824427480914, -20.111940298507463),
        new Position(-29.32824427480916, -21.67910447761194),
        new Position(0.2595419847328244, -0.26119402985074625),
        new Position(-18.946564885496183, -0.26119402985074625),
        new Position(18.946564885496183, -0.26119402985074625),
        new Position(-19.46564885496183, 35.78358208955224),
        new Position(-0.2595419847328244, 19.067164179104477),
        new Position(18.946564885496183, 35.26119402985075)
    };
    private transient Position alineacion4[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -31.082089552238806),
        new Position(11.16030534351145, -31.6044776119403),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(11.16030534351145, -1.3059701492537314),
        new Position(-10.641221374045802, -0.7835820895522387),
        new Position(-27.251908396946565, 31.6044776119403),
        new Position(-10.641221374045802, 30.559701492537314),
        new Position(9.603053435114505, 28.992537313432837),
        new Position(25.69465648854962, 28.992537313432837)
    };
    private transient Position alineacion5[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -35.78358208955224),
        new Position(12.717557251908397, -35.26119402985075),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(14.793893129770993, -18.544776119402986),
        new Position(-17.389312977099234, -19.58955223880597),
        new Position(-23.618320610687025, -0.7835820895522387),
        new Position(5.969465648854961, -5.485074626865671),
        new Position(0.2595419847328244, -0.26119402985074625),
        new Position(22.580152671755727, -1.3059701492537314)
    };
    private transient Position alineacion6[] = new Position[]{
        new Position(0.2595419847328244, -50.41044776119403),
        new Position(-11.16030534351145, -35.78358208955224),
        new Position(12.717557251908397, -35.26119402985075),
        new Position(28.290076335877863, -28.470149253731343),
        new Position(-28.290076335877863, -28.470149253731343),
        new Position(14.793893129770993, -18.544776119402986),
        new Position(-17.389312977099234, -19.58955223880597),
        new Position(-23.618320610687025, -0.7835820895522387),
        new Position(6.4885496183206115, -6.529850746268657),
        new Position(-6.4885496183206115, -6.529850746268657),
        new Position(22.580152671755727, -1.3059701492537314)
    };
    private String nombre;
    private String pais;
    private String entrenador;
    private Color colorCamiseta;
    private Color colorPantalon;
    private Color colorCalcetas;
    private Color colorFranja;
    private Color colorPortero;
    private EstiloUniforme estilo;

    @Override
    public Color getSocksColor2() {
        return colorCalcetas2;
    }

    /**EStablece el color de las calcetas del uniforme alternativo*/
    void setColorCalcetas2(Color colorCalcetas2) {
        this.colorCalcetas2 = colorCalcetas2;
    }

    @Override
    public Color getShirtColor2() {
        return colorCamiseta2;
    }

    /**EStablece el color de la camiseta del uniforme alternativo*/
    void setColorCamiseta2(Color colorCamiseta2) {
        this.colorCamiseta2 = colorCamiseta2;
    }

    @Override
    public Color getShirtLineColor2() {
        return colorFranja2;
    }

    /**EStablece el color de la franja de la camiseta del uniforme alternativo*/
    void setColorFranja2(Color colorFranja2) {
        this.colorFranja2 = colorFranja2;
    }

    @Override
    public Color getShortsColor2() {
        return colorPantalon2;
    }

    /**EStablece el color del pantalon del uniforme alternativo*/
    void setColorPantalon2(Color colorPantalon2) {
        this.colorPantalon2 = colorPantalon2;
    }

    @Override
    public Color getGoalKeeper2() {
        return colorPortero2;
    }

    /**EStablece el color del portero del uniforme alternativo*/
    void setColorPortero2(Color colorPortero2) {
        this.colorPortero2 = colorPortero2;
    }

    @Override
    public EstiloUniforme getStyle2() {
        return estilo2;
    }

    /**EStablece el estilo del uniforme alternativo*/
    void setEstilo2(EstiloUniforme estilo2) {
        this.estilo2 = estilo2;
    }
    private Color colorCamiseta2;
    private Color colorPantalon2;
    private Color colorCalcetas2;
    private Color colorFranja2;
    private Color colorPortero2;
    private EstiloUniforme estilo2;
    private JugadorImpl[] jugadores = new JugadorImpl[11];
    private ArrayList<Position[]> alineaciones = new ArrayList<Position[]>();
    private ArrayList<Integer> tipoAlineacion = new ArrayList<Integer>();
    private transient Random rand = new Random();

    /**Modifica una alineacion*/
    protected void setAlineacion(int idx, Position[] alineacion, int tipo) {
        alineaciones.set(idx, alineacion);
        tipoAlineacion.set(idx, tipo);
    }

    /**Retorna una alineacion configurada en el asistente*/
    public Position[] getAlineacion(int idx) {
        return alineaciones.get(idx);
    }

    /**Retorna la cantidad de alienaciones configuradas en el asistente*/
    public int getAlineacionCount() {
        return alineaciones.size();
    }

    /**Retorna el tipo una alineacion*/
    int getTipoAlineacion(int idx) {
        return tipoAlineacion.get(idx);
    }

    /**Inserta un espacio para agregar una alineacion*/
    protected void addAlineacion(int idx) {
        Position[] pnex = new Position[11];
        Position[] pold = alineaciones.get(idx);
        for (int i = 0; i < 11; i++) {
            pnex[i] = new Position(pold[i].getX(), pold[i].getY());
        }
        tipoAlineacion.add(tipoAlineacion.get(idx));
        alineaciones.add(pnex);
    }

    /**Elimina una alineacion*/
    protected void delAlineacion(int idx) {
        alineaciones.remove(idx);
        tipoAlineacion.remove(idx);
    }

    /**Carga una tactica detalle a partir de un recurso url
     * Ej: si el recurso esta en el mismo paquete desde donde se invoca el constructor
     * podria llamarse de la siguiente forma:
     * new TacticDetailImpl(this.getClass().getResource('miTacticaDetalle'));
     * @param url
     */
    public TacticDetailImpl(URL url) {
        TacticDetailImpl impl = new TacticDetailImpl();
        try {
            impl = TacticDetailImpl.loadRecurso(url);
        } catch (Exception ex) {
            logger.error("Error al cargar TacticaDetalle", ex);
        }
        this.nombre = impl.nombre;
        this.pais = impl.pais;
        this.entrenador = impl.entrenador;
        this.colorCamiseta = impl.colorCamiseta;
        this.colorPantalon = impl.colorPantalon;
        this.colorCalcetas = impl.colorCalcetas;
        this.colorFranja = impl.colorFranja;
        this.colorPortero = impl.colorPortero;
        this.estilo = impl.estilo;
        try {
            this.colorCamiseta2 = impl.colorCamiseta2;
            this.colorPantalon2 = impl.colorPantalon2;
            this.colorCalcetas2 = impl.colorCalcetas2;
            this.colorFranja2 = impl.colorFranja2;
            this.colorPortero2 = impl.colorPortero2;
            this.estilo2 = impl.estilo2;
        } catch (Exception ex) {
            uniformeSecundarioAlAzar();
            logger.error("Error al asignar uniforme secundario", ex);
        }
        this.jugadores = impl.jugadores;
        this.alineaciones = impl.alineaciones;
        this.tipoAlineacion = impl.tipoAlineacion;
    }

    private Color nextRandomColor() {
        if (rand == null) {
            rand = new Random();
        }
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    TacticDetailImpl() {
        for (int i = 0; i < 11; i++) {
            jugadores[i] = new JugadorImpl("Jugador", i + 1);
        }
        jugadores[0].setPortero(true);
        alineaciones.add(alineacion1);
        alineaciones.add(alineacion2);
        alineaciones.add(alineacion3);
        alineaciones.add(alineacion4);
        alineaciones.add(alineacion5);
        alineaciones.add(alineacion6);
        tipoAlineacion.add(0);
        tipoAlineacion.add(0);
        tipoAlineacion.add(0);
        tipoAlineacion.add(0);
        tipoAlineacion.add(1);
        tipoAlineacion.add(2);
        nombre = "";
        pais = "";
        entrenador = "";
        uniformePrincipalAlAzar();
        uniformeSecundarioAlAzar();
    }
    private static XStream xs = new XStream(new DomDriver("utf-8"));

    /**Carga una TacticDetail a partir de un fichero*/
    protected static TacticDetailImpl loadFichero(File file) throws IOException {
        return (TacticDetailImpl) xs.fromXML(new FileInputStream(file));
    }

    /**Carga una TacticDetail a partir de un recurso url*/
    protected static TacticDetailImpl loadRecurso(URL url) throws IOException, ClassNotFoundException {
        return (TacticDetailImpl) xs.fromXML(url.openStream());
    }

    /**Guarda una TacticDetailImpl en un fichero*/
    protected static void save(TacticDetailImpl tacticaDetalle, File file) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(file);
        xs.toXML(tacticaDetalle, fos);
        fos.close();
    }

    /**Crea el uniforme principal al azar*/
    protected void uniformePrincipalAlAzar() {
        colorCamiseta = nextRandomColor();
        colorPantalon = nextRandomColor();
        colorCalcetas = nextRandomColor();
        colorFranja = nextRandomColor();
        colorPortero = nextRandomColor();
        EstiloUniforme[] estilos = EstiloUniforme.values();
        estilo = estilos[rand.nextInt(estilos.length)];
    }

    /**Crea el uniforme secundario al azar*/
    protected void uniformeSecundarioAlAzar() {
        colorCamiseta2 = nextRandomColor();
        colorPantalon2 = nextRandomColor();
        colorCalcetas2 = nextRandomColor();
        colorFranja2 = nextRandomColor();
        colorPortero2 = nextRandomColor();
        EstiloUniforme[] estilos = EstiloUniforme.values();
        estilo2 = estilos[rand.nextInt(estilos.length)];
    }

    @Override
    public String getTacticName() {
        return nombre;
    }

    @Override
    public String getCountry() {
        return pais;
    }

    @Override
    public String getCoach() {
        return entrenador;
    }

    @Override
    public Color getShirtColor() {
        return colorCamiseta;
    }

    @Override
    public Color getShortsColor() {
        return colorPantalon;
    }

    @Override
    public Color getShirtLineColor() {
        return colorFranja;
    }

    @Override
    public Color getSocksColor() {
        return colorCalcetas;
    }

    @Override
    public EstiloUniforme getStyle() {
        return estilo;
    }

    void setEstiloPrincipal(EstiloUniforme estilo) {
        this.estilo = estilo;
    }

    @Override
    public PlayerDetail[] getPlayers() {
        return jugadores;
    }

    void setNombre(String nombre) {
        this.nombre = nombre;
    }

    void setPais(String pais) {
        this.pais = pais;
    }

    void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    void setColorCamiseta(Color colorCamiseta) {
        this.colorCamiseta = colorCamiseta;
    }

    void setColorPantalon(Color colorPantalon) {
        this.colorPantalon = colorPantalon;
    }

    void setColorCalcetas(Color colorCalcetas) {
        this.colorCalcetas = colorCalcetas;
    }

    void setColorFranja(Color colorFranja) {
        this.colorFranja = colorFranja;
    }

    void setColorPortero(Color colorPortero) {
        this.colorPortero = colorPortero;
    }

    @Override
    public Color getGoalKeeper() {
        return colorPortero;
    }
}
