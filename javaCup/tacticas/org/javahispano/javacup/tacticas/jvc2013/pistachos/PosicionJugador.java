package org.javahispano.javacup.tacticas.jvc2013.pistachos;

import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.engine.GameSituations;

public class PosicionJugador {
	private GameSituations sp;
	private List<Command> comandos;
	private final int index = 0;
	private final DatosPartido data;
	
	public PosicionJugador(List<Command> comandos, DatosPartido data) {
		super();		
		this.comandos = comandos;
		this.data = data;
	}
	
	public void execute(GameSituations sp){
		this.sp = sp;
		Position pos = newPos();
		comandos.add(new CommandMoveTo(index, pos));
	}

	
	private Position newPos() {
		if(sp.isStarts() && !inArea(sp.ballPosition()))
			return move(sp.ballPosition(), Integer.MAX_VALUE);
		int iterBallOpponent = data.getOpponentIterToBall();
		if(iterBallOpponent == -1)
			iterBallOpponent = Integer.MAX_VALUE;
		int i = 0; 
		while(i < iterBallOpponent){
			if(data.getPosBall(i).isInsideGameField(0)){
				boolean inArea = inArea(data.getPosBall(i));
				double dist = sp.myPlayers()[index].distance(data.getPosBall(i));
				if(dist <= i*Constants.VELOCIDAD_MAX + Constants.DISTANCIA_CONTROL_BALON_PORTERO && data.getZBall(i) <= (inArea? Constants.ALTO_ARCO : Constants.ALTURA_CONTROL_BALON)){
					return data.getPosBall(i);//.moverAngulo(data.getPosBall(i).angulo(Constants.centroArcoInf), inArea?Constants.DISTANCIA_CONTROL_BALON_PORTERO:Constants.DISTANCIA_CONTROL_BALON, data.getPosBall(i).distancia(Constants.centroArcoInf));
				}
			}else
				return move(data.getPosBall(i).setInsideGameField(), Integer.MAX_VALUE);
			i++;
		}
		return move(data.getPosBall(iterBallOpponent), iterBallOpponent);
	}

	private Position move(Position posBall, int iter) {
		Position pos = Constants.centroArcoInf.moveAngle(Constants.centroArcoInf.angle(posBall), Constants.LARGO_ARCO/2, Constants.centroArcoInf.distance(posBall));
		int iter1 = (int) Math.ceil((sp.myPlayers()[index].distance(pos) - Constants.DISTANCIA_CONTROL_BALON_PORTERO)/Constants.VELOCIDAD_MAX);
		while(iter<iter1){
			pos = pos.moveAngle(pos.angle(Constants.centroArcoInf), Constants.REMATE_VELOCIDAD_MAX);
			if(!pos.isInsideGameField(0)){
				pos = pos.moveAngle(pos.angle(Constants.centroArcoInf), Constants.REMATE_VELOCIDAD_MAX, pos.distance(Constants.centroArcoInf));
				break;
			}
			iter1 = (int) Math.ceil((sp.myPlayers()[index].distance(pos) - Constants.DISTANCIA_CONTROL_BALON_PORTERO)/Constants.VELOCIDAD_MAX);
		}
		return pos;
	}

	private boolean inArea(Position pos) {
		if(Math.abs(pos.getX()) <= Constants.LARGO_AREA_GRANDE/2 &&
				pos.getY() <= Constants.centroArcoInf.getY()+Constants.ANCHO_AREA_GRANDE)
			return true;
		return false;
	}
}
