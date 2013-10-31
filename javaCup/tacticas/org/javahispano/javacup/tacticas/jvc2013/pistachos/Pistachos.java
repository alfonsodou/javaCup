package org.javahispano.javacup.tacticas.jvc2013.pistachos;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.render.EstiloUniforme;

import java.awt.Color;
import java.util.ArrayList;

import org.javahispano.javacup.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class Pistachos implements Tactic {
	private List<Command> comandos = new LinkedList<Command>();
	private GameSituations sp;
	private DatosPartido data = new DatosPartido(); 
	private PosicionJugador goalkeeper = new PosicionJugador(comandos, data);
	private TacticaToques pass = new TacticaToques(comandos, data);
	private Defensiva1 defense = new Defensiva1(comandos, data);
	private Ataque attack = new Ataque(comandos, data);	


    Position alineacion1[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(7.132867132867133,-33.02036199095023),
        new Position(-21.636363636363637,-28.98190045248869),
        new Position(17.594405594405593,-28.031674208144796),
        new Position(4.9930069930069925,-9.502262443438914),
        new Position(-12.125874125874127,-33.970588235294116),
        new Position(-28.76923076923077,-17.34162895927602),
        new Position(25.678321678321677,-18.766968325791854),
        new Position(-17.832167832167833,-11.402714932126697),
        new Position(-5.230769230769231,-9.97737556561086),
        new Position(14.503496503496503,-9.264705882352942)
    };

    Position alineacion2[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(9.034965034965035,-28.269230769230766),
        new Position(-29.006993006993007,-21.61764705882353),
        new Position(26.732824427480914,-20.111940298507463),
        new Position(-2.377622377622378,-8.314479638009049),
        new Position(-10.461538461538462,-30.407239819004527),
        new Position(-22.11188811188811,7.364253393665159),
        new Position(18.06993006993007,6.176470588235294),
        new Position(-19.46564885496183,35.78358208955224),
        new Position(-0.4755244755244755,23.042986425339365),
        new Position(18.946564885496183,35.26119402985075)
    };

    Position alineacion3[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(27.251908396946565,-27.94776119402985),
        new Position(-29.84732824427481,-26.902985074626866),
        new Position(8.564885496183205,-7.574626865671642),
        new Position(-10.641221374045802,-7.052238805970149),
        new Position(27.251908396946565,4.440298507462686),
        new Position(-29.32824427480916,3.3955223880597014),
        new Position(-0.2595419847328244,19.067164179104477),
        new Position(-0.2595419847328244,35.78358208955224)
    };

    Position alineacion4[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-31.082089552238806),
        new Position(11.16030534351145,-31.6044776119403),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(11.16030534351145,-1.3059701492537314),
        new Position(-10.641221374045802,-0.7835820895522387),
        new Position(-27.251908396946565,31.6044776119403),
        new Position(-10.641221374045802,30.559701492537314),
        new Position(9.603053435114505,28.992537313432837),
        new Position(25.69465648854962,28.992537313432837)
    };

    Position alineacion5[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(5.969465648854961,-5.485074626865671),
        new Position(0.2595419847328244,-0.26119402985074625),
        new Position(22.580152671755727,-1.3059701492537314)
    };

    Position alineacion6[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-11.16030534351145,-35.78358208955224),
        new Position(12.717557251908397,-35.26119402985075),
        new Position(28.290076335877863,-28.470149253731343),
        new Position(-28.290076335877863,-28.470149253731343),
        new Position(14.793893129770993,-18.544776119402986),
        new Position(-17.389312977099234,-19.58955223880597),
        new Position(-23.618320610687025,-0.7835820895522387),
        new Position(6.4885496183206115,-6.529850746268657),
        new Position(-6.4885496183206115,-6.529850746268657),
        new Position(22.580152671755727,-1.3059701492537314)
    };

    
    TacticDetail detalle=new TacticDetailImpl();
    public TacticDetail getDetail() {
        return detalle;
    }

    public Position[] getStartPositions(GameSituations sp) {
    return alineacion5;
    }

    public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion1;
    }

    public List<Command> execute(GameSituations sp) {
		init(sp);
		boolean attacking = attacking();
		MentalidadEquipo mentality = getMentality();
		attack.execute(sp, mentality);
		defense.execute(sp, attacking);
		pass.execute(sp, mentality);
		goalkeeper.execute(sp);		
		return comandos;
    }
    
	private void init(GameSituations sp) {
		this.sp = sp;
		comandos.clear();
		data.update(sp);
	}

	private boolean attacking() {
		int iterToBall = data.getIterToBall();
		int opponentIterToBall = data.getOpponentIterToBall();
		return sp.isStarts() || (!sp.isRivalStarts() && iterToBall >= 0 && (opponentIterToBall < 0 || iterToBall < opponentIterToBall));
	}
	
	private MentalidadEquipo getMentality() {
		int iter = sp.iteration();
		if(sp.myGoals() == sp.rivalGoals() + 1){
			if (sp.rivalGoals() > 0) {
				defense.setCountDefense(4);
				return MentalidadEquipo.Mostasera;
			}
		}
		if(sp.myGoals() >= sp.rivalGoals() + 5){
			defense.setCountDefense(4);
			return MentalidadEquipo.Offensive;
		}
		if(sp.myGoals() > sp.rivalGoals()){
			defense.setCountDefense(5);
			return MentalidadEquipo.Normal;
		}
		if(sp.myGoals() == sp.rivalGoals()){
			if(iter < Constants.ITERACIONES/3){
				defense.setCountDefense(4);
				return MentalidadEquipo.Aggressive;
			}
			if(iter < Constants.ITERACIONES*2/3){
				defense.setCountDefense(4);
				return MentalidadEquipo.Offensive;
			}
			else {
			defense.setCountDefense(4);
			return MentalidadEquipo.Contragolpe;
			}
		}
		if(iter < Constants.ITERACIONES/3){
			defense.setCountDefense(4);
			return MentalidadEquipo.Offensive;
		}
		if(iter < Constants.ITERACIONES*2/3){
			defense.setCountDefense(4);
			return MentalidadEquipo.Aggressive;
		}
		defense.setCountDefense(4);
		return MentalidadEquipo.Aggressive;
	}
	

}