package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Goalkeeper extends Player {

	Goalkeeper(LinkedList<Command> comandos, int a_index) {
		super(comandos, a_index);
	}
	
	void execute(){
		
		Position destiny;
		
		super.execute();
		
		if ( !m_haveCommand )
		{
			if( Global.situation.rivalStarts && (Global.situation.ballPosition.distance( Constants.cornerInfIzq ) < 1 || Global.situation.ballPosition.distance( Constants.cornerInfDer ) < 1) )
			{
				m_comandos.add( new CommandMoveTo( m_index, new Position(Constants.centroArcoInf.getX(), Constants.centroArcoInf.getY() + Constants.ANCHO_AREA_CHICA)) );
			}
			else
			{
				if ( Global.situation.kickers[m_index] )
				{
					if( Global.situation.ballPosition.getX() > 0 )
					{
						m_comandos.add(new CommandHitBall(m_index, Global.situation.actualPositionsOwn[8], 1, 45) );
					}
					else
					{
						m_comandos.add(new CommandHitBall(m_index, Global.situation.actualPositionsOwn[5], 1, 45) );
					}
				}
				else
				{
					if( Common.highPassToArea(m_index))
					{
						m_comandos.add( new CommandMoveTo(m_index, new Position(Global.situation.posRecuperation[0], Global.situation.posRecuperation[1]), true) );
					}
					else
					{
						if( isKickToGoal() || isHandToHand() )
						{
							m_comandos.add(new CommandMoveTo(m_index, new Position(Global.situation.posRecuperation[0], Global.situation.posRecuperation[1]), true) );
						}
						else
						{
							if ( Global.situation.ballPosition.distance(Global.situation.actualPositionsOwn[m_index]) < Global.situation.nearestRivalPlayerDist )
							{
								m_comandos.add(new CommandMoveTo(m_index, new Position(Global.situation.posRecuperation[0], Global.situation.posRecuperation[1]), true) );
							}
							else
							{
								if( Global.situation.ballPosition.getY() > 0 )
								{
									if( Global.situation.actualPositionsOwn[m_index].getY() < Constants.penalInf.getY() )
									{
										destiny = new Position(Constants.centroCampoJuego.getX(), Constants.centroArcoInf.getY() + ((Global.situation.actualPositionsOwn[Global.situation.lastOwnPlayerId].getY() - Constants.centroArcoInf.getY()) / 2 ) );
										m_comandos.add(new CommandMoveTo(m_index, destiny, false) );
									}
								}
								else
								{
									if( Global.situation.ballPosition.getX() > 0 )
									{
										destiny = new Position(Position.Intersection(Global.situation.ballPosition, Constants.centroArcoInf, Constants.posteDerArcoInf, new Position(Constants.centroArcoInf.getX(), -(Constants.LARGO_CAMPO_JUEGO / 2) + Constants.ANCHO_AREA_CHICA) ) );
									}
									else
									{
										destiny = new Position(Position.Intersection(Global.situation.ballPosition, Constants.centroArcoInf, Constants.posteIzqArcoInf, new Position(Constants.centroArcoInf.getX(), -(Constants.LARGO_CAMPO_JUEGO / 2) + Constants.ANCHO_AREA_CHICA) ) );
									}
									if( Global.situation.ballPosition.getY() <  -(Constants.LARGO_CAMPO_JUEGO / 4) )
									{
										m_comandos.add(new CommandMoveTo(m_index, destiny, true) );
									}
									else
									{
										m_comandos.add(new CommandMoveTo(m_index, destiny, false) );
									}
								}
							}	
						}
					}
				}
			}
		}
	}

	

	boolean isKickToGoal() {
		if ( Global.situation.ballPosition.getY() > Global.situation.ballPositionAnt.getY() )
		{
			return false;
		}
		else
		{
			double slope = Math.abs( Global.situation.ballPositionAnt.getY() - Global.situation.ballPosition.getY() ) / 
			Math.abs( Global.situation.ballPositionAnt.getX() - Global.situation.ballPosition.getX() );
			
			double xDest = ( (Constants.centroArcoInf.getY() - Global.situation.ballPosition.getY()) + (Global.situation.ballPosition.getX()*slope) ) / slope;
			
			return (xDest > Constants.posteIzqArcoInf.getX() - 1 && xDest < Constants.posteDerArcoInf.getX() + 1 && Global.situation.ballPosition.distance(Constants.centroArcoInf) < 20 );
		}
	}
	
	boolean isHandToHand() {
		int nearestOwnPlayerId = Global.situation.nearestOwnPlayerId;
		
		if ( nearestOwnPlayerId == 0 )
		{
			nearestOwnPlayerId = Global.situation.nearestSecondOwnPlayerId;
		}
			
		return ( !Global.situation.havePosession &&
				(Global.situation.actualPositionsOwn[nearestOwnPlayerId].distance(Constants.centroArcoInf) <
				Global.situation.actualPositionsOwn[Global.situation.lastOwnPlayerId].distance(Constants.centroArcoInf)) &&
				(Global.situation.actualPositionsRival[Global.situation.nearestRivalPlayerId].distance(Constants.centroArcoInf) <
				Global.situation.actualPositionsOwn[nearestOwnPlayerId].distance(Constants.centroArcoInf)) && 
				(Global.situation.ballPosition.getY() < Constants.centroArcoInf.getY() + 20 &&
				Global.situation.ballPosition.getX() > Constants.centroArcoInf.getX() - 27 &&
				Global.situation.ballPosition.getX() < Constants.centroArcoInf.getX() + 27) );
	}
}
