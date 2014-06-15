/**
 * 
 */
package org.javahispano.javaleague.ID_5818767066005504.Pringaos;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Color;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.render.EstiloUniforme;

/**
 * @author adou
 * 
 */
public class Pringaos implements Tactic {
	Position alineacion1[] = new Position[] {
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
			new Position(12.717557251908397, 29.51492537313433) };

	Position alineacion2[] = new Position[] {
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
			new Position(-0.2595419847328244, 35.78358208955224) };

	Position alineacion3[] = new Position[] {
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
			new Position(18.946564885496183, 35.26119402985075) };

	Position alineacion4[] = new Position[] {
			new Position(0.2595419847328244, -50.41044776119403),
			new Position(-11.16030534351145, -31.082089552238806),
			new Position(11.16030534351145, -31.6044776119403),
			new Position(25.202797202797203, -21.61764705882353),
			new Position(-26.391608391608393, -21.855203619909503),
			new Position(15.692307692307693, -0.47511312217194573),
			new Position(-14.74125874125874, 0.0),
			new Position(-1.4265734265734267, 16.866515837104075),
			new Position(-13.79020979020979, 32.07013574660634),
			new Position(-0.4755244755244755, 37.05882352941177),
			new Position(15.692307692307693, 31.83257918552036) };

	Position alineacion5[] = new Position[] {
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
			new Position(22.580152671755727, -1.3059701492537314) };

	Position alineacion6[] = new Position[] {
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
			new Position(22.580152671755727, -1.3059701492537314) };

	public LinkedList<Command> comandos = new LinkedList<Command>();

	public Pringaos() {

	}

	public TacticDetail detalle = new Pringaos2012Detalle();

	@Override
	public TacticDetail getDetail() {
		// TODO Auto-generated method stub
		return detalle;
	}

	@Override
	public List<Command> execute(GameSituations sp) {
		// Limpia la lista de comandos
		comandos.clear();

		// Obtiene las posiciones de tus jugadores
		Position[] jugadores = sp.myPlayers();

		// Instancia un generador aleatorio
		Random r = new Random();

		Position nueva = new Position();
		double angulo;

		for (int i = 0; i < jugadores.length; i++) {
			if (sp.iteration() == 0) {
				comandos.add(new CommandMoveTo(i, alineacion4[i]));
			} else {
				int nearest = jugadores[i].nearestIndex(sp.rivalPlayers());
				int count;
				double distance = jugadores[i]
						.distance(sp.rivalPlayers()[nearest]);
				if (distance < Constants.JUGADORES_SEPARACION) {
					nueva = jugadores[i];
					count = 0;
					while ((distance < Constants.JUGADORES_SEPARACION)
							&& (count < 20)) {
						angulo = r.nextInt(360) * Math.PI / 180;
						nueva = jugadores[i].moveAngle(angulo,
								Constants.getVelocidad(sp.getMyPlayerSpeed(i)));
						if (nueva.isInsideGameField(0)) {
							distance = nueva
									.distance(sp.rivalPlayers()[nearest]);
						} else {
							distance = 0;
						}
						count++;
					}
					comandos.add(new CommandMoveTo(i, nueva));
				} else {
					comandos.add(new CommandMoveTo(i, alineacion4[i]));
				}
			}
		}

		// Si no saca el rival
		if (!sp.isRivalStarts()) {
			// Obtiene los datos de recuperacion del balon
			int[] recuperadores = sp.getRecoveryBall();
			// Si existe posibilidad de recuperar el balon
			if (recuperadores.length > 1) {
				// Obtiene las coordenadas del balon en el instante donde
				// se puede recuperar el balon
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				// Recorre la lista de jugadores que pueden recuperar
				for (int i = 1; i < recuperadores.length; i++) {
					// Ordena a los jugadores recuperadores que se ubique
					// en la posicion de recuperacion
					comandos.add(new CommandMoveTo(
							recuperadores[i],
							new Position(posRecuperacion[0], posRecuperacion[1])));
				}
			}
		}

		// Recorre la lista de mis jugadores que pueden rematar
		for (int i : sp.canKick()) {
			// Si el jugador es de indice 8 o 10
			if (i == 8 || i == 10) {
				// condicion aleatoria
				if (r.nextBoolean()) {
					// Ordena que debe rematar al centro del arco
					comandos.add(new CommandHitBall(i, Constants.centroArcoSup,
							1, 12 + r.nextInt(6)));
				} else if (r.nextBoolean()) {
					// Ordena que debe rematar al poste derecho
					comandos.add(new CommandHitBall(i,
							Constants.posteDerArcoSup, 1, 12 + r.nextInt(6)));
				} else {
					// Ordena que debe rematar al poste izquierdo
					comandos.add(new CommandHitBall(i,
							Constants.posteIzqArcoSup, 1, 12 + r.nextInt(6)));
				}
			} else {
				double max_score = 0;
				int max_index = -1;

				LinkedList<Pases> pases = isPassSafeFromOpponent(sp, i);
				max_score = -1;
				max_index = -1;
				for (int c = 0; c < pases.size(); c++) {
					if (pases.get(c).getPos().getY() > sp.myPlayers()[i].getY()) {
						pases.get(c).addScore(1);
					}

					if (pases.get(c).getPos().getY() > 0) {
						pases.get(c).addScore(1);
					}

					pases.get(c).addScore(
							(double) (1.0 / pases.get(c).getIteraciones()));

					if (pases.get(c).getScore() > max_score) {
						max_score = pases.get(c).getScore();
						max_index = c;
					}
				}

				comandos.add(new CommandHitBall(i, jugadores[pases.get(
						max_index).getId_player_target()], pases.get(max_index)
						.getPower(), pases.get(max_index).getAngle()));
			}
		}

		// Retorna la lista de comandos
		return comandos;
	}

	public int[] getRecoveryBall(Trayectoria tra, GameSituations sp) {
		int it = 0;
		boolean found = false;
		Position pJug;
		double dist0, dist;
		int idxFound = -1;
		double[] posBalon;
		int i = 0;
		int[] ret;

		ret = new int[2];
		ret[0] = -1;
		ret[1] = -1;
		while ((!found) && (it < 100)) {
			posBalon = tra.getPos(it);
			if (!(new Position(posBalon[0], posBalon[1])).isInsideGameField(2)) {
				return null;
			}

			if (posBalon[2] <= Constants.ALTO_ARCO) {
				for (i = 0; i < sp.myPlayers().length; i++) {
					if (posBalon[2] <= (sp.myPlayersDetail()[i].isGoalKeeper() ? Constants.ALTO_ARCO
							: Constants.ALTURA_CONTROL_BALON)) {
						pJug = sp.myPlayers()[i];
						dist0 = (double) it
								* Constants
										.getVelocidad(sp.getMyPlayerSpeed(i));
						dist = pJug.distance(new Position(posBalon[0],
								posBalon[1]));
						if (dist0 >= dist) {
							found = true;
							idxFound = it;
							ret[0] = it;
							ret[1] = i;
						}
					}
				}
			}
			it++;
		}

		return ret;
	}

	public int getRecoveryBallRival(Trayectoria tra, GameSituations sp) {
		int it = 0;
		boolean found = false;
		Position pJug;
		double dist0, dist;
		int idxFound = -1;
		double[] posBalon;
		int i = 0;

		while ((!found) && (it < 100)) {
			posBalon = tra.getPos(it);
			if (!(new Position(posBalon[0], posBalon[1])).isInsideGameField(2)) {
				return -1;
			}

			if (posBalon[2] <= Constants.ALTO_ARCO) {
				for (i = 0; i < sp.rivalPlayers().length; i++) {
					if (posBalon[2] <= (sp.rivalPlayersDetail()[i]
							.isGoalKeeper() ? Constants.ALTO_ARCO
							: Constants.ALTURA_CONTROL_BALON)) {
						pJug = sp.rivalPlayers()[i];
						dist0 = (double) it
								* Constants.getVelocidad(sp
										.getRivalPlayerSpeed(i));
						dist = pJug.distance(new Position(posBalon[0],
								posBalon[1]));
						if (dist0 >= dist) {
							found = true;
							idxFound = it;
						}
					}
				}
			}
			it++;
		}

		return idxFound;
	}

	public LinkedList<Pases> isPassSafeFromOpponent(GameSituations sp,
			int player) {
		double angulo;
		double fuerza;
		double fuerzaTotal;
		double anguloH;
		double angulorad;
		Position p1 = sp.myPlayers()[player];
		LinkedList<Pases> pases;
		Trayectoria tra;

		pases = new LinkedList<Pases>();
		for (angulo = 0; angulo <= Constants.ANGULO_VERTICAL_MAX; angulo += 10) {
			angulorad = (angulo * Math.PI) / 180;
			for (fuerza = 0.2f; fuerza <= 1; fuerza += 0.2f) {
				fuerzaTotal = sp.getMyPlayerPower(player) * fuerza;
				for (anguloH = 0.0f; anguloH < 360; anguloH += 10) {
					tra = new Trayectoria(p1,
							Constants.getVelocidadRemate(fuerzaTotal),
							(anguloH * Math.PI) / 180, angulorad);
					double distance2 = p1.distance(new Position(
							tra.getPos(99)[0], tra.getPos(99)[1]));

					int iteracionesrival = getRecoveryBallRival(tra, sp);
					int[] iteraciones = getRecoveryBall(tra, sp);

					if ((iteraciones != null) && (iteraciones[0] != -1)
							&& (iteraciones[0] <= 99)) {

						if ((iteraciones[0] < iteracionesrival)
								|| ((iteracionesrival == -1) && (iteraciones[0] != -1))) {

							pases.add(new Pases(new Position(tra
									.getPos(iteraciones[0])[0], tra
									.getPos(iteraciones[0])[1]), 0, angulo,
									fuerza, iteraciones[0], player,
									iteraciones[1]));
						}
					}
				}
			}
		}

		return pases;
	}

	@Override
	public Position[] getStartPositions(GameSituations sp) {
		// TODO Auto-generated method stub
		return alineacion5;
	}

	@Override
	public Position[] getNoStartPositions(GameSituations sp) {
		// TODO Auto-generated method stub
		return alineacion6;
	}

}

class Trayectoria {
	public double[][] positions;

	public Trayectoria(Position pos, double vel, double ang, double angV) {
		double radio;

		positions = new double[100][3];
		AbstractTrajectory t = new AirTrajectory(vel * Math.cos(angV), vel
				* Math.sin(angV), 0, 0);
		for (int i = 0; i < 100; i++) {
			radio = t.getX((double) (i + 1) / 60d)
					* Constants.AMPLIFICA_VEL_TRAYECTORIA;
			positions[i][0] = pos.getX() + radio * Math.cos(ang);
			positions[i][1] = pos.getY() + radio * Math.sin(ang);
			positions[i][2] = t.getY((double) (i + 1) / 60d)
					* Constants.AMPLIFICA_VEL_TRAYECTORIA * 2;
		}
	}

	public double[] getPos(int i) {
		return positions[i];
	}
}

class Pases {
	Position pos;
	double score;
	double angle;
	double power;
	int iteraciones;
	int id_player_source;
	int id_player_target;

	public Pases(Position p, double s) {
		pos = p;
		score = s;
		angle = 0;
		power = 0;
		iteraciones = 0;
	}

	public Pases(Position p, double s, double a, double po, int it, int source,
			int target) {
		pos = p;
		score = s;
		angle = a;
		power = po;
		iteraciones = it;
		id_player_source = source;
		id_player_target = target;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void addScore(double score) {
		this.score += score;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public int getIteraciones() {
		return iteraciones;
	}

	public void setIteraciones(int iteraciones) {
		this.iteraciones = iteraciones;
	}

	public int getId_player_source() {
		return id_player_source;
	}

	public void setId_player_source(int id_player_source) {
		this.id_player_source = id_player_source;
	}

	public int getId_player_target() {
		return id_player_target;
	}

	public void setId_player_target(int id_player_target) {
		this.id_player_target = id_player_target;
	}

}

class Pringaos2012Detalle implements TacticDetail {

	public String getTacticName() {
		return "Los Pringaos";
	}

	public String getCountry() {
		return "España";
	}

	public String getCoach() {
		return "Sito";
	}

	public Color getShirtColor() {
		return new Color(255, 0, 0);
	}

	public Color getShortsColor() {
		return new Color(47, 66, 203);
	}

	public Color getShirtLineColor() {
		return new Color(255, 255, 255);
	}

	public Color getSocksColor() {
		return new Color(255, 0, 0);
	}

	public Color getGoalKeeper() {
		return new Color(255, 255, 0);
	}

	public EstiloUniforme getStyle() {
		return EstiloUniforme.LINEAS_VERTICALES;
	}

	public Color getShirtColor2() {
		return new Color(0, 0, 0);
	}

	public Color getShortsColor2() {
		return new Color(0, 0, 0);
	}

	public Color getShirtLineColor2() {
		return new Color(255, 0, 0);
	}

	public Color getSocksColor2() {
		return new Color(0, 0, 0);
	}

	public Color getGoalKeeper2() {
		return new Color(255, 255, 0);
	}

	public EstiloUniforme getStyle2() {
		return EstiloUniforme.SIN_ESTILO;
	}

	class JugadorImpl implements PlayerDetail {
		String nombre;
		int numero;
		Color piel, pelo;
		double velocidad, remate, precision;
		boolean portero;
		Position posicion;

		public JugadorImpl(String nombre, int numero, Color piel, Color pelo,
				double velocidad, double remate, double precision,
				boolean portero) {
			this.nombre = nombre;
			this.numero = numero;
			this.piel = piel;
			this.pelo = pelo;
			this.velocidad = velocidad;
			this.remate = remate;
			this.precision = precision;
			this.portero = portero;
		}

		public String getPlayerName() {
			return nombre;
		}

		public Color getSkinColor() {
			return piel;
		}

		public Color getHairColor() {
			return pelo;
		}

		public int getNumber() {
			return numero;
		}

		public boolean isGoalKeeper() {
			return portero;
		}

		public double getSpeed() {
			return velocidad;
		}

		public double getPower() {
			return remate;
		}

		public double getPrecision() {
			return precision;
		}

	}

	public PlayerDetail[] getPlayers() {
		return new PlayerDetail[] {
				new JugadorImpl("Calamardo", 1, new Color(255, 200, 150),
						new Color(255, 200, 150), 1.0d, 1.0d, 0.67d, true),
				new JugadorImpl("Bob", 2, new Color(255, 102, 102), new Color(
						255, 200, 150), 1.0d, 1.0d, 0.5d, false),
				new JugadorImpl("Patricio", 3, new Color(255, 200, 150),
						new Color(255, 200, 150), 1.0d, 1.0d, 0.5d, false),
				new JugadorImpl("Billy", 4, new Color(255, 200, 150),
						new Color(255, 200, 150), 1.0d, 1.0d, 0.56d, false),
				new JugadorImpl("Scooby", 5, new Color(255, 200, 150),
						new Color(255, 200, 150), 0.71d, 0.7d, 0.49d, false),
				new JugadorImpl("Calimero", 6, new Color(255, 200, 150),
						new Color(255, 200, 150), 0.67d, 1.0d, 0.69d, false),
				new JugadorImpl("Burt", 7, new Color(255, 200, 150), new Color(
						255, 200, 150), 0.67d, 1.0d, 0.69d, false),
				new JugadorImpl("Hommer", 8, new Color(255, 200, 150),
						new Color(255, 200, 150), 0.75d, 1.0d, 0.64d, false),
				new JugadorImpl("Lisa", 9, new Color(255, 200, 150), new Color(
						255, 200, 150), 0.87d, 1.0d, 0.79d, false),
				new JugadorImpl("Mandy", 10, new Color(255, 200, 150),
						new Color(255, 200, 150), 0.85d, 1.0d, 0.75d, false),
				new JugadorImpl("Berni", 11, new Color(255, 200, 150),
						new Color(255, 200, 150), 1.0d, 1.0d, 1.0d, false) };
	}

}
