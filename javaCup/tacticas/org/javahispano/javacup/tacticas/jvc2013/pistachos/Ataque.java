package org.javahispano.javacup.tacticas.jvc2013.pistachos;

import java.util.List;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.engine.GameSituations;

public class Ataque {
	private GameSituations sp;
	private List<Command> comandos;
	private final DatosPartido data;
	private final int goalkeeper = 0;
	private final Position positions1[]=new Position[]{
			new Position(0.2595419847328244,-50.41044776119403),
			new Position(-8.321678321678322,-20.192307692307693),
			new Position(5.706293706293707,-20.667420814479637),
			new Position(20.923076923076923,-20.90497737556561),
			new Position(-21.3986013986014,-19.95475113122172),
			new Position(0.0,-8.789592760180994),
			new Position(9.748251748251748,8.076923076923077),
			new Position(-10.6993006993007,7.601809954751132),
			new Position(21.16083916083916,26.606334841628957),
			new Position(0.4755244755244755,30.6447963800905),
			new Position(-18.06993006993007,24.705882352941178)
	    	};	
	
	 Position positions2[]=new Position[]{
		 	new Position(0.2595419847328244,-50.41044776119403),
	        new Position(-16.405594405594407,-23.755656108597286),
	        new Position(13.79020979020979,-24.705882352941178),
	        new Position(0.0,-24.468325791855204),
	        new Position(7.846153846153847,-5.226244343891403),
	        new Position(-7.37062937062937,-4.038461538461538),
	        new Position(-15.93006993006993,20.90497737556561),
	        new Position(0.4755244755244755,10.690045248868778),
	        new Position(16.643356643356643,23.28054298642534),
	        new Position(5.706293706293707,32.30769230769231),
	        new Position(-6.181818181818182,27.794117647058822)
		    };

	 Position positions3[]=new Position[]{
			new Position(0.2595419847328244,-50.41044776119403),
			new Position(-16.167832167832167,-18.054298642533936),
			new Position(14.503496503496503,-18.29185520361991),
			new Position(0.0,-18.529411764705884),
			new Position(-0.4755244755244755,-6.414027149321266),
			new Position(-9.034965034965035,8.789592760180994),
			new Position(-0.951048951048951,19.479638009049776),
			new Position(8.55944055944056,9.027149321266968),
			new Position(13.076923076923078,28.744343891402718),
			new Position(-2.8531468531468533,35.63348416289593),
			new Position(-14.97902097902098,26.368778280542987)
		    };
	
	 Position positions4[]=new Position[]{
		        new Position(0.2595419847328244,-50.41044776119403),
		        new Position(-11.888111888111888,-41.57239819004525),
		        new Position(0.7132867132867133,-42.28506787330317),
		        new Position(12.839160839160838,-42.76018099547511),
		        new Position(-8.321678321678322,-21.380090497737555),
		        new Position(-25.678321678321677,-30.407239819004527),
		        new Position(-22.11188811188811,1.4253393665158371),
		        new Position(24.965034965034967,-30.16968325791855),
		        new Position(-0.7132867132867133,28.269230769230766),
		        new Position(4.755244755244756,-19.95475113122172),
		        new Position(14.74125874125874,4.751131221719457)			    };

	 Position contraBamba[]=new Position[]{
		        new Position(0.2595419847328244,-50.41044776119403),
		        new Position(5.468531468531468,-35.8710407239819),
		        new Position(-24.965034965034967,-34.20814479638009),
		        new Position(19.97202797202797,-37.05882352941177),
		        new Position(0.951048951048951,-19.479638009049776),
		        new Position(-9.510489510489512,-36.10859728506787),
		        new Position(-13.79020979020979,-18.766968325791854),
		        new Position(13.552447552447552,-19.95475113122172),
		        new Position(-0.4755244755244755,33.73303167420815),
		        new Position(-0.7132867132867133,-3.088235294117647),
		        new Position(10.223776223776223,16.391402714932127)			    };

	 public Ataque(List<Command> comandos, DatosPartido data) {
		this.comandos = comandos;
		this.data = data;
	}
	
	public void execute(GameSituations sp, MentalidadEquipo mentality){
		this.sp = sp;
		Position[] newPositions = calculatePositions(mentality == MentalidadEquipo.Normal? positions1:
													mentality == MentalidadEquipo.Offensive? positions2: 
														mentality == MentalidadEquipo.Mostasera? positions4:
															mentality == MentalidadEquipo.Contragolpe? contraBamba:positions3);
		for (int i = 0; i < newPositions.length; i++) {
			if(i != goalkeeper) {
				comandos.add(new CommandMoveTo(i, newPositions[i]));
			}
		}		
		getBall();
	}

	private void getBall() {
		if(data.getIterToBall() >= 0)
			comandos.add(new CommandMoveTo(data.getPlayerClosetBall(), data.getPosBall(data.getIterToBall())));
	}

	private Position[] calculatePositions(Position[] positions){
		double height = Math.max(Constants.LARGO_CAMPO_JUEGO/3, 2*Math.min(Math.abs(Constants.centroArcoInf.getY()-sp.ballPosition().getY()), Math.abs(Constants.centroArcoSup.getY()-sp.ballPosition().getY())));
		double width = Math.max(Constants.ANCHO_CAMPO_JUEGO*2/3, 2*Math.min(Math.abs(Constants.cornerInfIzq.getX()-sp.ballPosition().getX()), Math.abs(Constants.cornerInfDer.getX()-sp.ballPosition().getX())));
		Position center = new Position(sp.ballPosition().getX(), sp.ballPosition().getY());
		if(center.getX() + width/2 > Constants.cornerInfDer.getX()){
			center = center.movePosition(-(center.getX() + width/2 - Constants.cornerInfDer.getX()), 0);
		}
		if(center.getX() - width/2 < Constants.cornerInfIzq.getX()){
			center = center.movePosition(Constants.cornerInfIzq.getX() - (center.getX()-width/2), 0);
		}
		if(center.getY() + height/2 > Constants.centroArcoSup.getY()){
			center = center.movePosition(0, -(center.getY() + height/2 - Constants.centroArcoSup.getY()));
		}
		if(center.getY() - height/2 < Constants.centroArcoInf.getY()){
			center = center.movePosition(0, Constants.centroArcoInf.getY() - (center.getY() - height/2));
		}
		Position[] newPositions = new Position[11];
		for (int i = 0; i < newPositions.length; i++) {
			newPositions[i] = new Position(positions[i].getX()*width/Constants.ANCHO_CAMPO_JUEGO + center.getX(), positions[i].getY()*height/Constants.LARGO_CAMPO_JUEGO + center.getY());
		}
		return newPositions;
	}
}
