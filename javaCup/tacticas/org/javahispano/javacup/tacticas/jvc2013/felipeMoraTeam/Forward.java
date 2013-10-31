package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Forward extends Player {
	
	boolean penalti = false;

	Forward(LinkedList<Command> comandos, int a_index) {
		super(comandos, a_index);
	}

	void execute()
	{	
		super.execute();
		
		if( !m_haveCommand )
		{
			if( Global.situation.m_sp.isStarts() && !( Global.situation.ballPosition.distance(Constants.centroCampoJuego) < 0.5 ) )
			{
				m_comandos.add(new CommandMoveTo(m_index, new Position(calcShotPositionToGoal(false), Constants.penalSup.getY()), false ) );
			}
			else
			{
				if( Global.situation.kickers[m_index] )
				{
					if( !Rematar() )
					{
						if( Math.floor(Global.situation.lastPlayerPos.getY()) < Math.floor(Global.situation.actualPositionsOwn[m_index].getY())
								&& Global.situation.actualPositionsOwn[m_index].distance( Global.situation.actualPositionsRival[Global.situation.rivalGoalKeeperIndex] ) > 15 )
						{
							m_comandos.add( new CommandHitBall( m_index, Constants.centroArcoSup, Global.GetPowerHitOnPosessionSprint( m_index ), false ) );
						}
						else
						{
							goAhead();
						}
					}
				}
				else 
				{
					if ( m_index == Global.situation.nearestOwnPlayerId )
					{
						m_comandos.add(new CommandMoveTo(m_index, new Position(Global.situation.ballPosition) ) );
					}
					else
					{
						if( Global.situation.m_sp.ballPosition().getY() > Global.situation.actualPositionsOwn[m_index].getY() )
						{
							if ( Global.situation.recuperators[m_index] )
							{
								m_comandos.add(new CommandMoveTo(m_index, new Position(Global.situation.posRecuperation[0], Global.situation.posRecuperation[1]), false ) );
							}
							else
							{
								m_comandos.add(new CommandMoveTo(m_index, new Position(calcShotPositionToGoal(false), Constants.penalSup.getY()), false ) );
							}
						}
						else 
						{
							if( Global.situation.ballPosition.getY() < 0 )
							{
								Position playerDestPosition = Global.GetPlayerPosition( m_index );
								
								if( playerDestPosition.distance( Global.situation.actualPositionsOwn[m_index] ) > 5 )
								{									
									m_comandos.add(new CommandMoveTo(m_index, new Position( ) ) );
								}
								else
								{
									CleanMyCommandos();
								}							
							}
							else
							{
								if ( ((Global.situation.lastPlayerPos.getY() - Global.situation.actualPositionsOwn[m_index].getY()) > 4)
										&& (Global.situation.actualPositionsOwn[m_index].getY() < Constants.penalSup.getY() ) )
								{
									m_comandos.add(new CommandMoveTo(m_index, new Position(Global.situation.actualPositionsOwn[m_index].getX(), Global.situation.lastPlayerPos.getY() - 1) ) );
								}
								else
								{
									Position dest = null;
									if( Global.situation.distFromMyPlayerToNearestRival[m_index] > 2 )
									{
										dest = new Position(calcShotPositionToGoal(false), Global.situation.actualPositionsOwn[m_index].getY() );
									}
									else
									{
										dest = new Position(calcShotPositionToGoal(true), Global.situation.actualPositionsOwn[m_index].getY() );
									}
									
									if( Global.situation.actualPositionsOwn[m_index].distance( dest ) > 4 )
									{
										m_comandos.add(new CommandMoveTo(m_index, dest ) );	
									}
									else
									{
										CleanMyCommandos();
									}
									
								}
							}
						}
					}
				}	
			}	
		}
		
		if( Global.situation.passDestPlayerId == m_index && !Global.situation.kickers[m_index] )
		{
			m_comandos.add( new CommandMoveTo( m_index, Global.situation.bestPlayerPos[m_index] ) );
		}
	}
	
	double calcShotPositionToGoal( boolean moreLen )
	{
		double mult = 1;
		if( moreLen )
			mult = 1.5;
			
		double posX = 0;
		if ( Global.GetPlayerPosition(m_index).getX() > 0 )
			posX = MyConstants.shotPositionPenalti * mult;
		else if ( Global.GetPlayerPosition(m_index).getX() < 0)
			posX = - MyConstants.shotPositionPenalti * mult;
		
		return posX;
	}
}
