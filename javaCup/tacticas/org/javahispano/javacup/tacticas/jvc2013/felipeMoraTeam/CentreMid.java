package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;

public class CentreMid extends Player {

	CentreMid(LinkedList<Command> comandos, int a_index) {
		super(comandos, a_index);
	}
	
	void execute( )
	{
		super.execute();
		
		if( !m_haveCommand )
		{
			if( Global.situation.kickers[m_index] && !Global.situation.m_sp.isStarts() )
			{
				if( !Rematar() )
				{
					if( Global.situation.passDestPlayerId != -1 )
					{
						if( Global.situation.distFromMyPlayerToNearestRival[m_index] > 10 )
						{
							goAhead( );
						}
						else
						{
							double dist = Global.situation.actualPositionsOwn[m_index].distance( Global.situation.actualPositionsOwn[Global.situation.passDestPlayerId] );
							double power = Global.GetPowerByMeterOnPass( m_index ) * dist;
							m_comandos.add( new CommandHitBall( m_index, Global.situation.bestEvaluatePosition.position, power, Global.situation.bestEvaluatePosition.safePassLevel == 0 ) );	
						}
					}
				}
			}
		}
	}
}
