/**
 * 
 */
package org.javahispano.javacup.model.engine;

import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.util.Agent;
import org.javahispano.javacup.model.util.Compressor;
import org.javahispano.javacup.model.util.Serializer;


/**
 * @author adou
 * 
 */
public class AgentPartido implements Agent {

	@Override
	public byte[] execute(Object l, Object v) throws Exception {
		Partido partido;

		partido = new Partido((Tactic) l, (Tactic) v, true);
		int iter = 0;
		for (int i = 0; partido.getEstado() != 7; i++) {
			partido.iterar();
			iter = partido.getIteracion();
			if (i > 10000) {
				throw new Exception("partido bloqueado");
			}
		}

		return Compressor.compress(Serializer.serialize(
				partido.getPartidoGuardado()), partido.toString());

	}

	@Override
	public boolean isTactic(Class<?> t) throws Exception {
		
		return Tactic.class.isAssignableFrom(t);
	}

}
