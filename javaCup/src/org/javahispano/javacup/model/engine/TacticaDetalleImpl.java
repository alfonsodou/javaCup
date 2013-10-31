package org.javahispano.javacup.model.engine;

import java.io.Serializable;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.render.EstiloUniforme;

/**Implementación de TacticDetail, usada internamente*/
 final class TacticaDetalleImpl implements TacticDetail, Serializable {
	private static final long serialVersionUID = 1L;

    private final String nombre;
    private final String pais;
    private final String entrenador;
    private final Color colorCamiseta;
    private final Color colorPantalon;
    private final Color colorCalcetas;
    private final Color colorFranja;
    private final Color colorPortero;
    private final EstiloUniforme estilo;
    private final Color colorCamiseta2;
    private final Color colorPantalon2;
    private final Color colorCalcetas2;
    private final Color colorFranja2;
    private final Color colorPortero2;
    private final EstiloUniforme estilo2;
    private final JugadorImpl[] jugadores = new JugadorImpl[11];

	/**Copia el detalle y deja inmutables sus valores*/
    TacticaDetalleImpl(TacticDetail impl) {
        this.nombre = impl.getTacticName();
        this.pais = impl.getCountry();
        this.entrenador = impl.getCoach();
        this.colorCamiseta = impl.getShirtColor();
        this.colorPantalon = impl.getShortsColor();
        this.colorCalcetas = impl.getSocksColor();
        this.colorFranja = impl.getShirtLineColor();
        this.colorPortero = impl.getGoalKeeper();
        this.estilo = impl.getStyle();
        this.colorCamiseta2 = impl.getShirtColor2();
        this.colorPantalon2 = impl.getShortsColor2();
        this.colorCalcetas2 = impl.getSocksColor2();
        this.colorFranja2 = impl.getShirtLineColor2();
        this.colorPortero2 = impl.getGoalKeeper2();
        this.estilo2 = impl.getStyle2();
        for (int i = 0; i < 11; i++) {
            this.jugadores[i] = new JugadorImpl(impl.getPlayers()[i]);
        }
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

	@Override
    public PlayerDetail[] getPlayers() {
        return jugadores;
    }

	@Override
    public Color getGoalKeeper() {
        return colorPortero;
    }

    @Override
    public Color getShirtColor2() {
        return colorCamiseta2;
    }

    @Override
    public Color getShortsColor2() {
        return colorPantalon2;
    }

    @Override
    public Color getShirtLineColor2() {
        return colorFranja2;
    }

    @Override
    public Color getSocksColor2() {
        return colorCalcetas2;
    }

    @Override
    public Color getGoalKeeper2() {
        return colorPortero2;
    }

    @Override
    public EstiloUniforme getStyle2() {
        return estilo2;
    }
}

