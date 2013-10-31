package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Player {
	
	LinkedList<Command> m_comandos;
	int m_index;
	boolean m_haveCommand = false;

	Player(LinkedList<Command> comandos, int a_index){
		this.m_comandos = comandos;
		m_index = a_index;
	}
	
	public int getIndex() {
		return m_index;
	}

	public void setIndex(int index) {
		this.m_index = index;
	}
	
	public boolean haveCommand() {
		return m_haveCommand;
	}

	public void set_haveCommand(boolean m_haveCommand) {
		this.m_haveCommand = m_haveCommand;
	}
	
	boolean SacarBalonParado( )
	{
		boolean ret = false;
		int kickerIndex = 0;
		int [] kickersType = Global.GetFormationKikersType();
		
		if( (m_index == 9 || m_index == 10) && ( Global.situation.ballPosition.distance(Constants.centroCampoJuego) < 0.000001 ) )
		{
			InitialKickOff( );
			ret = true;
		}
		else
		{
			if( Global.situation.m_sp.isStarts() )
			{
				double distToLeftSide = Global.situation.ballPosition.distance( new Position( Constants.cornerSupIzq.getX(), Global.situation.ballPosition.getY() ) );
				double distToRightSide = Global.situation.ballPosition.distance( new Position( Constants.cornerSupDer.getX(), Global.situation.ballPosition.getY() ) );
				if ( Global.situation.ballPosition.getY() < 0 )
				{
					if( !Common.insideMyArea(Global.situation.ballPosition) )
					{
						if ( distToLeftSide < Constants.ANCHO_CAMPO_JUEGO / 4)
						{
							kickerIndex = kickersType[2];
						}
						else
						{
							if( distToLeftSide < Constants.ANCHO_CAMPO_JUEGO / 2 )
							{
								kickerIndex = kickersType[4];
							}
							else
							{
								if (distToRightSide < Constants.ANCHO_CAMPO_JUEGO / 4 )
								{
									kickerIndex = kickersType[3];
								}
								else
								{
									kickerIndex = kickersType[5];
								}
							}
						}
					}
					else
					{
						kickerIndex = 0;
					}
				}
				else
				{
					if ( distToLeftSide < Constants.ANCHO_CAMPO_JUEGO / 4 )
					{
						kickerIndex = kickersType[0];
					}
					else
					{
						if ( distToLeftSide < Constants.ANCHO_CAMPO_JUEGO / 2 )
						{
							kickerIndex = kickersType[6];
						}
						else
						{
							if ( distToRightSide < Constants.ANCHO_CAMPO_JUEGO / 4 )
							{
								kickerIndex = kickersType[1];
							}
							else
							{
								kickerIndex = kickersType[7];
							}
						}
					}
				}
				
				if ( m_index == kickerIndex )
				{
					if( !Global.situation.kickers[m_index] )
					{
						m_comandos.add( new CommandMoveTo( m_index, Global.situation.ballPosition, true ) );
					}
					else
					{
						if( Global.situation.ballPosition.getY() < 0 && !Common.insideMyArea(Global.situation.ballPosition) )
						{
							if ( Global.situation.ballPosition.getX() > 0 )
							{
								m_comandos.add(new CommandHitBall(m_index, Global.situation.actualPositionsOwn[8], 1, 45));
							}
							else
							{
								m_comandos.add(new CommandHitBall(m_index, Global.situation.actualPositionsOwn[5], 1, 45));
							}
						}
						else
						{
							m_comandos.add( new CommandHitBall( m_index, Constants.penalSup, MyConstants.cornerHitPower, MyConstants.cornetHitVerticalAngle ) );
						}
					}
					ret = true;
				}			
			}
		}
		return ret;
	}
	boolean Rematar( )
	{
		if( Global.situation.kickers[m_index] && !Global.situation.m_sp.isStarts() )
		{
			double distanceToLeftPost = Global.situation.actualPositionsOwn[m_index].distance( Constants.posteIzqArcoSup );
			double distanceToRigthPost = Global.situation.actualPositionsOwn[m_index].distance( Constants.posteDerArcoSup );
			double distanceToCentre = Global.situation.actualPositionsOwn[m_index].distance( MyConstants.rivalGoalCentre );
			
			double minimumDistance = Math.min( distanceToRigthPost, distanceToLeftPost );
			minimumDistance = Math.min( minimumDistance, distanceToCentre );
			
			double shotDistanceToGoal = MyConstants.shotDistanceToGoal;
			double distanceToGK = Global.situation.actualPositionsRival[Global.situation.rivalGoalKeeperIndex].distance( Global.situation.actualPositionsOwn[m_index] );
			if( Global.situation.actualPositionsOwn[m_index].getY() >= Global.situation.lastPlayerPos.getY() )
			{
				if( distanceToGK >= 10 )
					shotDistanceToGoal = MyConstants.shotDistanceToGoalIfAlone;
			}
			else
			{
				if( minimumDistance < 40 && Global.situation.rivalAreaPlayers > 2 )
				{
					shotDistanceToGoal = 40;
				}
			}
			
			if( distanceToLeftPost < shotDistanceToGoal || distanceToCentre < shotDistanceToGoal || distanceToRigthPost < shotDistanceToGoal )
			{
				double gkDistanceToLeftPost = Global.situation.actualPositionsRival[Global.situation.rivalGoalKeeperIndex].distance( Constants.posteIzqArcoSup );
				double gkDistanceToDerPost = Global.situation.actualPositionsRival[Global.situation.rivalGoalKeeperIndex].distance( Constants.posteDerArcoSup );
				
				Position hitPosition = null;
				if( gkDistanceToLeftPost > gkDistanceToDerPost )
				{
					hitPosition = new Position( Constants.posteIzqArcoSup.getX() + ErrorMarginOnShot( ), Constants.posteIzqArcoSup.getY() );
				}
				else
				{
					hitPosition = new Position( Constants.posteDerArcoSup.getX() - ErrorMarginOnShot( ), Constants.posteDerArcoSup.getY() );
				}
				
				m_comandos.add( new CommandHitBall( m_index, hitPosition, MyConstants.maxHitPower, MyConstants.hitVerticalAngle ) );
				return true;
			}
			else
			{
				if( Global.situation.ballPosition.getY() > 0 && !Common.insideRivalLittleArea( Global.situation.actualPositionsRival[Global.situation.rivalGoalKeeperIndex] ) )
				{
					m_comandos.add( new CommandHitBall( m_index, MyConstants.rivalGoalCentre, MyConstants.maxHitPower, MyConstants.hitVerticalAngle ) );
					return true;
				}
			}
		}
		return false;
	}
	
	double ErrorMarginOnShot( )
	{
		double playerEnergy = Global.situation.m_sp.getMyPlayerEnergy( m_index );
		
		return ( ( 1 - playerEnergy ) * 5 ) + 0.5;
	}
	
	void InitialKickOff()
	{
		if( Global.situation.kickers[m_index] )
		{
			double gkDistanceToLeftPost = Global.situation.actualPositionsRival[Global.situation.rivalGoalKeeperIndex].distance( Constants.posteIzqArcoSup );
			double gkDistanceToDerPost = Global.situation.actualPositionsRival[Global.situation.rivalGoalKeeperIndex].distance( Constants.posteDerArcoSup );
			
			Position hitPosition = null;
			
			if( gkDistanceToLeftPost > gkDistanceToDerPost )
			{
				hitPosition = new Position( Constants.posteIzqArcoSup.getX() + 1, Constants.posteIzqArcoSup.getY() );
			}
			else
			{
				hitPosition = new Position( Constants.posteDerArcoSup.getX() - 1, Constants.posteDerArcoSup.getY() );
			}
			
			m_comandos.add( new CommandHitBall( m_index, hitPosition, MyConstants.maxHitPower, 45 ) );
			
			m_comandos.add( new CommandHitBall( m_index, MyConstants.rivalGoalCentre, MyConstants.maxHitPower, 25 ) );
		}
		else
		{
			m_comandos.add(new CommandMoveTo(m_index, Global.situation.ballPosition, false ) );
		}
	}
	
	void goAhead( )
	{
		Position nearestPlayerPos = Global.situation.actualPositionsRival[Global.situation.nearestRivalPlayerId];
		double verticalDist = nearestPlayerPos.getY() - Global.situation.actualPositionsOwn[m_index].getY();
		double horizontalDist = nearestPlayerPos.getX() - Global.situation.actualPositionsOwn[m_index].getX();
		
		if( verticalDist < 1 )
		{
			if( horizontalDist >= 0 )
			{
				Position pos = new Position( - Constants.LARGO_AREA_GRANDE / 2, Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_GRANDE );
				m_comandos.add( new CommandHitBall( m_index, pos, Global.GetPowerHitOnPosession( m_index ), false ) );
			}
			else
			{
				Position pos = new Position( Constants.LARGO_AREA_GRANDE / 2, Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_GRANDE );
				m_comandos.add( new CommandHitBall( m_index, pos, Global.GetPowerHitOnPosession( m_index ), false ) );
			}
		}
		else
		{
			m_comandos.add( new CommandHitBall( m_index, Constants.centroArcoSup, Global.GetPowerHitOnPosession( m_index ), false ) );	
		}
	}

	void CleanMyCommandos( )
	{
		for( int i = m_comandos.size() - 1; i >= 0 ; i-- )
		{
			if( m_index == m_comandos.get( i ).getPlayerIndex() )
			{
				m_comandos.remove( i );
			}
		}
	}
	
	void execute()
	{
		m_haveCommand = SacarBalonParado();
		
		if ( !m_haveCommand )
		{
			if ( Global.situation.m_sp.getOffSidePlayers( )[m_index] )
			{
				m_comandos.add(new CommandMoveTo(m_index, new Position(Global.situation.actualPositionsOwn[m_index].getX(), (Global.situation.lastPlayerPos.getY() - 1)), false ) );
				m_haveCommand = true;
			}
			else
			{	
				if ( Global.situation.kickers[m_index] )
				{
					m_comandos.add(new CommandHitBall(m_index, Global.situation.actualPositionsOwn[10], 1, 45));
				}
				else
				{
					if ( Global.situation.recuperators[m_index] )
					{
						m_comandos.add(new CommandMoveTo(m_index, new Position(Global.situation.posRecuperation[0], Global.situation.posRecuperation[1])));
						m_haveCommand = true;
					}
					else
					{	
						if( Global.situation.nearestOwnPlayerId == m_index )
						{
							m_comandos.add(new CommandMoveTo(m_index, Global.situation.ballPosition ) );
							m_haveCommand = true;
						}
						else
						{
							m_comandos.add( new CommandMoveTo( m_index, Global.GetPlayerPosition( m_index ) ) );							
						}
						
					}
		    	}
			}
		}
	}
}
