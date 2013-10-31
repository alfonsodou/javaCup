package org.javahispano.javacup.tacticas.jvc2013.twentythree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Util23 {

	private static final Integer			RIVALES_SIN_MARCA	= 0;

	private static Map<Integer, Integer>	marcas				= new HashMap<Integer, Integer>();

	private boolean							optimizarTrayectoria;

	public Util23(GameSituations gs) {
		optimizarTrayectoria = true;
		ArrayList<Integer> miosMarcando = new ArrayList<Integer>();
		int[] posRivales = Constants.centroCampoJuego.nearestIndexes(gs.rivalPlayers());
		int sinMarca = 0;
		
		for (int i = 1; i < posRivales.length - RIVALES_SIN_MARCA; i++) {
			Position posRival = gs.rivalPlayers()[i - 1];
			int[] posMias = posRival.nearestIndexes(gs.myPlayers(), 0);
			int mioMarca = 0;
			for (int j = 0; j < posMias.length; j++) {
				if (!miosMarcando.contains(posMias[j])) {
					mioMarca = posMias[j];
					miosMarcando.add(posMias[j]);
					break;
				}
			}
			marcas.put(posRivales[i - 1], mioMarca);
			sinMarca = i;
		}
		// Se guardan los rivales que no tienen marca
		for (int i = 0; i < RIVALES_SIN_MARCA; i++) {
			marcas.put(posRivales[sinMarca + i], -1);
		}
	}

	public void reasignarMarcas(GameSituations gs) {
		if (gs.isRivalStarts() || gs.isStarts()) {
			optimizarTrayectoria = true;
		}
	}

	public int getRivalSinMarca() {
		for (Iterator<Integer> iterator = marcas.keySet().iterator(); iterator.hasNext();) {
			Integer riv = (Integer) iterator.next();
			Integer mio = marcas.get(riv);
			if (mio == null) {
				return riv;
			}
		}
		return 0;
	}

	/**
	 * @return the marcas
	 */
	public Map<Integer, Integer> getMarcas() {
		return marcas;
	}

	/**
	 * @param optimizarTrayectoria the optimizarTrayectoria to set
	 */
	public void setOptimizarTrayectoria(boolean optimizarTrayectoria) {
		this.optimizarTrayectoria = optimizarTrayectoria;
	}

	/**
	 * @return the optimizarTrayectoria
	 */
	public boolean isOptimizarTrayectoria() {
		return optimizarTrayectoria;
	}

}