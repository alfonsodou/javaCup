package org.javahispano.javacup.gui.principal;

import java.awt.Rectangle;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import org.javahispano.javacup.model.util.Constants;

/**Almacena los datos que se persitiran al cerrar la aplicacion Principal, uso interno*/
public class PrincipalDatos {

    public static final long serialVersionUID = 1l;
    public File dir = new File(System.getProperty("user.dir"));
    public int sx = 800, sy = 600;
    public boolean fullscreen = false;
    public boolean maximizado = false;
    public boolean marcador = true;
    public boolean entrada = true;
    public boolean guardar = false;
    public boolean entorno = true;
    public boolean estadio = true;
    public boolean autoescala = true;
    public boolean numeros = true;
    public boolean sonidos = true;
    public boolean jugadores3d = false;
    public boolean buffer = true;
    public int estadioIdx = 0;
    public double escala = Constants.ESCALA;
    public Rectangle ubicacion = new Rectangle(200, 200, 640, 480);
    public int splitpos;
    public int tab;
    public int visor = 1;
    public float volumenAmbiente = .5f;
    public float volumenCancha = .5f;
    public boolean dibujaJugadores = true;
    public boolean dobleBuffer = true;
    public int fps = Constants.FPS;
    public int tipoTexto = 0;
    public String local;
    public String visita;
    public ArrayList<URL> guardados = new ArrayList<URL>();
}
