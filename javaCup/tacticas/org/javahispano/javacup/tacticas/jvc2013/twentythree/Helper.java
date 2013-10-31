/**
 * 
 */
package org.javahispano.javacup.tacticas.jvc2013.twentythree;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

/**
 * @author willBender
 * 
 */
public class Helper {

	private static double distanciaMinimaMarca = 0.97;// 0.98;
	private static double distanciaMarca2 = Constants.ANCHO_AREA_GRANDE + 2;

	public enum CUADRANTES {
		I, II, III, IV
	}

	public static final Position PUNTO_DISPARO_IZQ = new Position(Constants.posteIzqArcoSup.getX() + 2,
			Constants.posteIzqArcoSup.getY());
	public static final Position PUNTO_DISPARO_DER = new Position(Constants.posteDerArcoSup.getX() - 2,
			Constants.posteDerArcoSup.getY());

	private static Position[] rectaIntersecArquero = new Position[] {
			new Position(Constants.centroArcoInf.getX() + (Constants.LARGO_AREA_CHICA / 2),
					Constants.centroArcoInf.getY() + Constants.ANCHO_AREA_CHICA),
			new Position(Constants.centroArcoInf.getX() - (Constants.LARGO_AREA_CHICA / 2),
					Constants.centroArcoInf.getY() + Constants.ANCHO_AREA_CHICA) };

	public static CUADRANTES getCuadranteBalon(Position balonPos) {
		if (balonPos.getY() > 0) {
			if (balonPos.getX() > 0) {
				return CUADRANTES.III;
			}
			return CUADRANTES.II;
		}
		if (balonPos.getX() > 0) {
			return CUADRANTES.IV;
		}
		return CUADRANTES.I;
	}

	public static Position obtenerPuntoMejorMarcaArco(GameSituations sp) {
		Position posMiArquero = sp.myPlayers()[0];
		double dIzquierda = posMiArquero.distance(Constants.posteIzqArcoInf);
		double dDerecha = posMiArquero.distance(Constants.posteDerArcoInf);
		double distanciaEspacio;
		double angulo;
		Position pResultado;
		if (dIzquierda > dDerecha) {
			distanciaEspacio = dIzquierda / 2;
			angulo = posMiArquero.angle(Constants.posteIzqArcoInf);
			pResultado = posMiArquero.moveAngle(angulo, distanciaEspacio);
		} else {
			distanciaEspacio = dDerecha / 2;
			angulo = posMiArquero.angle(Constants.posteDerArcoInf);
			pResultado = posMiArquero.moveAngle(angulo, distanciaEspacio);
		}
		return pResultado;
	}

	public static Position obtenerPuntoMejorDisparoArco(GameSituations sp) {
		int arqueroRival = 0;
		for (int i = 0; i < sp.rivalPlayersDetail().length; i++) {
			if (sp.rivalPlayersDetail()[i].isGoalKeeper()) {
				arqueroRival = i;
				break;
			}
		}
		Position posSuArquero = sp.rivalPlayers()[arqueroRival];
		double dIzquierda = posSuArquero.distance(Constants.posteIzqArcoSup);
		double dDerecha = posSuArquero.distance(Constants.posteDerArcoSup);
		double distanciaEspacio;
		double angulo;
		Position pResultado;
		if (dIzquierda > dDerecha) {
			distanciaEspacio = dIzquierda / 2;
			angulo = posSuArquero.angle(Constants.posteIzqArcoSup);
			pResultado = posSuArquero.moveAngle(angulo, distanciaEspacio);
		} else {
			distanciaEspacio = dDerecha / 2;
			angulo = posSuArquero.angle(Constants.posteDerArcoSup);
			pResultado = posSuArquero.moveAngle(angulo, distanciaEspacio);
		}
		return pResultado;
	}

	public static double getDistanciaMarca(GameSituations sp, int idRival) {
		PlayerDetail detalleRival = sp.rivalPlayersDetail()[idRival];
		double diferenciaLimitesRemate = Constants.REMATE_VELOCIDAD_MAX - Constants.REMATE_VELOCIDAD_MIN;
		double remateRival = detalleRival.getPower();
		double factorMarca = diferenciaLimitesRemate * remateRival;
		double distMarca = Constants.REMATE_VELOCIDAD_MIN + factorMarca;
		return distMarca;
	}

	public static CommandMoveTo getMovimientoGoleador(GameSituations sp, int idxJugador, Util23 util23) {
		int idxArqueroRival = 0;
		double distMarca = 0.99;
		if (idxJugador == 10) {
			distMarca = distanciaMarca2;
		}
		for (int i = 0; i < sp.rivalPlayersDetail().length; i++) {
			if (sp.rivalPlayersDetail()[i].isGoalKeeper()) {
				idxArqueroRival = i;
				break;
			}
		}
		double anguloMarca = sp.rivalPlayers()[idxArqueroRival].angle(sp.ballPosition());
		if (idxJugador == 10) {
			anguloMarca = Constants.centroArcoSup.angle(sp.ballPosition());
		}
		Position pResult;
		if (idxJugador == 10 && sp.ballPosition().distance(Constants.centroArcoSup) <= distanciaMarca2) {
			pResult = sp.ballPosition();
		} else if (idxJugador == 10) {
			pResult = Constants.centroArcoSup.moveAngle(anguloMarca, distMarca);
		} else {
			pResult = sp.rivalPlayers()[idxArqueroRival].moveAngle(anguloMarca, distMarca);
		}
		CommandMoveTo result = new CommandMoveTo(idxJugador, pResult);
		return result;
	}

	public static CommandMoveTo marcar(int idxMiJugador, int idxRival, int rivalMasCercano, boolean balonAMiArco,
			HistorialMarcas historialMarcas, GameSituations sp, Util23 util23, int rivalMasPeligro) {
		Position pMarca;
		double distanciaMarca = distanciaMinimaMarca;
		Position pMio = sp.myPlayers()[idxMiJugador];
		Position pRival = historialMarcas.predecirPosicionRival(sp, idxRival, 0);
		double anguloMarca;
		anguloMarca = pRival.angle(Helper.obtenerPuntoMejorMarcaArco(sp));
		double distanciaRival = pRival.distance(Constants.centroArcoInf);
		boolean mioMasCercano = idxMiJugador == sp.ballPosition().nearestIndex(sp.myPlayers());
		boolean romedal = false;
		if (sp.rivalPlayersDetail()[0].getPlayerName().equalsIgnoreCase("Ospina")
				|| sp.rivalPlayersDetail()[0].getPlayerName().equalsIgnoreCase("0")) {
			romedal = true;
			distanciaMarca = 0.98;// 1.2;
			if(sp.rivalPlayersDetail()[idxRival].getNumber()==10){
				distanciaMarca = 1.2;
			}
		}
		if (!romedal) {
			if (distanciaRival > (Constants.LARGO_CAMPO_JUEGO / 2) + Constants.RADIO_CIRCULO_CENTRAL
					&& idxRival != rivalMasPeligro
					&& (idxRival != rivalMasCercano || (pMio.distance(sp.ballPosition()) < pRival.distance(sp
							.ballPosition()) && sp.ballAltitude() <= Constants.ALTURA_CONTROL_BALON))) {
				anguloMarca = pRival.angle(sp.ballPosition());
				distanciaMarca = 0.75;
			}
		}
		boolean suyoMasCercano = idxRival == sp.ballPosition().nearestIndex(sp.rivalPlayers());
		if (sp.isRivalStarts() && suyoMasCercano) {
			anguloMarca = pRival.angle(Helper.obtenerPuntoMejorMarcaArco(sp));
			distanciaMarca = Constants.DISTANCIA_SAQUE + 2;
		}
		pMarca = pRival.moveAngle(anguloMarca, distanciaMarca);
		CommandMoveTo marcar = null;
		boolean distanciaMenorMio = sp.ballPosition().distance(pMio) < sp.ballPosition().distance(pRival);
		boolean balonControlable = sp.ballAltitude() <= Constants.ALTURA_CONTROL_BALON;
		
		// boolean rivalConBalon = idxRival ==
		// sp.ballPosition().nearestIndex(sp.rivalPlayers());
		if (!romedal && distanciaMenorMio && balonControlable && mioMasCercano || (mioMasCercano && sp.isStarts())) {
			marcar = new CommandMoveTo(idxMiJugador, sp.ballPosition(),false);
		} else {
			marcar = new CommandMoveTo(idxMiJugador, pMarca.setInsideGameField(),false);
		}
		return marcar;
	}

	public static Position getPosicionArquero(Position balonPos, Position pRivalMasCercano, Position pMiArquero,
			double alturaBalon, Position... balonposAntA) {
		if (pMiArquero.distance(balonPos) < pRivalMasCercano.distance(balonPos) && alturaBalon < Constants.ALTO_ARCO) {
			return balonPos;
		}
		Position balonposAnt = (balonposAntA == null || balonposAntA.length < 1) ? Constants.centroArcoInf
				: balonposAntA[0];
		Position[] rectaIntersecArquero = getLineaInterseccionArquero(balonPos);
		Position posIdeal = Position.Intersection(rectaIntersecArquero[0], rectaIntersecArquero[1], balonPos,
				balonposAnt);
		// posIdeal = posIdeal.setPosicion(posIdeal.getX() * -1, posIdeal.getY()
		// * -1);
		if (posIdeal == null) {
			posIdeal = Constants.cornerInfIzq;
		}
		if ((posIdeal.getX() > rectaIntersecArquero[0].getX() || posIdeal.getX() < rectaIntersecArquero[1].getX())) {
			posIdeal = Position.Intersection(rectaIntersecArquero[0], rectaIntersecArquero[1], balonPos,
					Constants.centroArcoInf);
			if (posIdeal == null) {
				posIdeal = Constants.cornerInfIzq;
			}
			if ((posIdeal.getX() > rectaIntersecArquero[0].getX() || posIdeal.getX() < rectaIntersecArquero[1].getX())) {
				posIdeal = Constants.centroArcoInf;
				switch (getCuadranteBalon(balonPos)) {
				case I:
					posIdeal = new Position(Constants.posteIzqArcoInf.getX(), Constants.posteIzqArcoInf.getY()
							+ (Constants.ANCHO_AREA_CHICA / 3));
					break;
				case IV:
					posIdeal = new Position(Constants.posteDerArcoInf.getX(), Constants.posteDerArcoInf.getY()
							+ (Constants.ANCHO_AREA_CHICA / 3));
					break;
				default:
					posIdeal = Constants.centroArcoInf;
					break;
				}
			}
		}
		return posIdeal;
	}

	public static Position[] getLineaInterseccionArquero(Position balonPos) {
		double nuevoY = balonPos.distance(Constants.centroArcoInf) / 25;
		if (nuevoY > 1) {
			return rectaIntersecArqueroDefault;
		}
		Position[] ret = new Position[] {
				new Position(rectaIntersecArqueroDefault[0].getX(), Constants.centroArcoInf.getY()
						+ (Constants.ANCHO_AREA_CHICA * nuevoY)),
				new Position(rectaIntersecArqueroDefault[1].getX(), Constants.centroArcoInf.getY()
						+ (Constants.ANCHO_AREA_CHICA * nuevoY)), };
		return ret;
	}

	private static Position[] rectaIntersecArqueroDefault = new Position[] {
			new Position(Constants.centroArcoInf.getX() + (Constants.LARGO_ARCO / 2), Constants.centroArcoInf.getY()
					+ Constants.ANCHO_AREA_CHICA),
			new Position(Constants.centroArcoInf.getX() - (Constants.LARGO_ARCO / 2), Constants.centroArcoInf.getY()
					+ Constants.ANCHO_AREA_CHICA) };

	public static Position getPositionDisparo(int indicador) {
		if (indicador % 3 == 0) {
			return PUNTO_DISPARO_DER;
		}
		if (indicador % 3 == 1) {
			return Constants.centroArcoSup;
		}
		return PUNTO_DISPARO_IZQ;
	}

	public static Position[] getRectaArqueroByPosBalon(Position posBalon) {
		if (posBalon.getY() > 0) {
			return rectaIntersecArquero;
		} else {
			double multiplicador = (Constants.LARGO_CAMPO_JUEGO / 2) - (posBalon.getY() * -1);
			if (multiplicador != 0) {
				multiplicador = multiplicador / (Constants.LARGO_CAMPO_JUEGO / 2);
			}
			Position res[] = new Position[] {
					new Position(Constants.centroArcoInf.getX() + (Constants.LARGO_AREA_CHICA / 2),
							Constants.centroArcoInf.getY() + (Constants.ANCHO_AREA_CHICA * multiplicador)),
					new Position(Constants.centroArcoInf.getX() - (Constants.LARGO_AREA_CHICA / 2),
							Constants.centroArcoInf.getY() + (Constants.ANCHO_AREA_CHICA) * multiplicador) };
			return res;
		}
	}

}