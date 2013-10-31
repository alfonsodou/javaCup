package org.javahispano.javacup.render;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import org.javahispano.javacup.gui.principal.PrincipalDatos;
import org.javahispano.javacup.model.engine.PartidoGuardado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase usada para visualizar partidos guardados mediante java web start
 */
public class JavaWebStart {

    private static XStream xs = new XStream();
    private static Logger logger = LoggerFactory.getLogger(JavaWebStart.class);

    public static void main(String[] args) throws Exception {
        String url = System.getProperty("partido.url");
        boolean visorBasico = System.getProperty("visor.basico") != null;
        PartidoGuardado pg = new PartidoGuardado(new URL(url));
        PrincipalDatos datos = new PrincipalDatos();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(System.getProperty("user.home") + File.separator + "javacup2009" + File.separator + "config.xml");
            datos = (PrincipalDatos) xs.fromXML(fis);
        } catch (Exception ex) {
            datos = new PrincipalDatos();
            FileOutputStream fos = null;

            try {
                File out = new File(System.getProperty("user.home") + File.separator + "javacup2009" + File.separator + "config.xml");
                if (!out.getParentFile().exists()) {
                    out.getParentFile().mkdirs();
                }
                fos = new FileOutputStream(out);
                xs.toXML(datos, fos);
            } catch (FileNotFoundException e) {
                logger.error("Error al guardar configuracion",e);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                }
            }
        }
        if (visorBasico) {
            VisorBasico.dibujaJugadores = datos.dibujaJugadores;
            VisorBasico.dobleBuffer = datos.dobleBuffer;
            VisorBasico.numeros = datos.numeros;
            VisorBasico.volumenAmbiente = datos.volumenAmbiente;
            VisorBasico.volumenCancha = datos.volumenCancha;
            VisorBasico.sonidos = datos.sonidos;
            VisorBasico.fps = datos.fps;
            new VisorBasico(pg, null);
        } else {
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
            new VisorOpenGl(pg, datos.sx, datos.sy, datos.fullscreen, null);
        }

    }
}
