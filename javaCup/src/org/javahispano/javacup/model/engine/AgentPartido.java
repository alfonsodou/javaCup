/**
 * 
 */
package org.javahispano.javacup.model.engine;

import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.util.Compressor;
import org.javahispano.javacup.model.util.Serializer;
import org.javahispano.javaleague.javacup.shared.Agent;
import org.javahispano.javaleague.javacup.shared.MatchShared;

/**
 * @author adou
 * 
 */
public class AgentPartido implements Agent {

	@Override
	public MatchShared execute(Object l, Object v) throws Exception {
		Partido partido;
		MatchShared matchShared = new MatchShared();

		partido = new Partido((Tactic) l, (Tactic) v, true);
		int iter = 0;
		for (int i = 0; partido.getEstado() != 7; i++) {
			partido.iterar();
			iter = partido.getIteracion();
			if (i > 10000) {
				throw new Exception("partido bloqueado");
			}
		}

		matchShared.setMatch(Compressor.compress(
				Serializer.serialize(partido.getPartidoGuardado()),
				partido.toString()));
		matchShared.setMatchBin(partido.getPartidoGuardado().binaryServe());
		matchShared.setGoalsLocal(partido.getGolesLocal());
		matchShared.setGoalsVisiting(partido.getGolesVisita());
		matchShared.setPosessionLocal(partido.getPosesionBalonLocal());
		matchShared.setTimeLocal(partido.getPartidoGuardado().getLocalTime());
		matchShared.setTimeVisita(partido.getPartidoGuardado().getVisitaTime());

		return matchShared;

	}

	@Override
	public boolean isTactic(Class<?> t) throws Exception {

		return Tactic.class.isAssignableFrom(t);
	}

}
