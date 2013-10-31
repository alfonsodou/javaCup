package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Sider extends Player{

	Sider(LinkedList<Command> comandos, int a_index) {
		super(comandos, a_index);
	}
	
	void execute()
	{
		super.execute();
		
		if ( !m_haveCommand )
		{
			if( Global.situation.kickers[m_index] && !Global.situation.m_sp.isStarts() )
			{
				if( !Rematar() && Global.situation.ballPosition.getY() > 0 )
				{
					if( Global.situation.nearestOwnPlayerId == m_index && !Global.situation.kickers[m_index] && !Global.situation.m_sp.isStarts() )
					{
						m_comandos.add( new CommandMoveTo( m_index, new Position( Global.situation.posRecuperation[0], Global.situation.posRecuperation[1]), true ) );
					}
					else
					{
						if( Global.situation.actualPositionsOwn[m_index].getY() >= Constants.LARGO_CAMPO_JUEGO / 2 - 10 )
						{
							m_comandos.add( new CommandHitBall( m_index, new Position( MyConstants.areaPassPosition ), 0.8, false ) );
						}
						else
						{
							double dist = Global.situation.actualPositionsOwn[m_index].distance( Global.situation.actualPositionsRival[Global.situation.nearestRivalPlayerId] );
							boolean amIInFrontOfRival = Global.situation.actualPositionsOwn[m_index].getY() <= Global.situation.actualPositionsRival[Global.situation.nearestRivalPlayerId].getY();
							boolean amIInFrontOfRivalAndImNotTheFirst = dist < 2 && m_index != Global.situation.firstOwnPlayerId;
							if( ( dist < MyConstants.minimumDistanceToPass && amIInFrontOfRival ) || amIInFrontOfRivalAndImNotTheFirst )
							{
								if( Global.situation.bestSafePassId != -1 )
								{
									double distBest = Global.situation.actualPositionsOwn[m_index].distance( Global.situation.bestEvaluatePosition.position );
									double power = Global.GetPowerByMeterOnPass( m_index ) * distBest;
									m_comandos.add( new CommandHitBall( m_index, Global.situation.evaluatePositionList.get(Global.situation.bestSafePassId).position, power, false ) );
								}
								else
								{
									double power = Global.GetPowerByMeterOnPass( m_index ) * Global.situation.nearestSecondOwnPlayerDist;
									m_comandos.add( new CommandHitBall( m_index, Global.situation.actualPositionsOwn[Global.situation.nearestSecondOwnPlayerId], power, true ) );
								}
							}
							else
							{
							goAhead();				
							}
						}
					}
				}
			}
		}
	}
}
