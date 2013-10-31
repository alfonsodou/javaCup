package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import org.javahispano.javacup.model.util.Constants;


public class TeamDecisions {
	
	TacticsEnum tactic = TacticsEnum.Unknown;
	boolean needToRefreshPlayers = true;
	
	void calcTactic(  )
	{
		double scoreVal = 0;
		int myScore = Global.situation.m_sp.myGoals();
		int hisScore = Global.situation.m_sp.rivalGoals();
		int dif = myScore - hisScore;
		scoreVal = 1;
		if( dif >= -3 )
			scoreVal -= 0.1 * ( dif + 4 );
		scoreVal = Math.max( 0, scoreVal );
		double timeVal = 0;
		double timeProm = Global.situation.m_sp.iteration() / Constants.ITERACIONES;
		timeVal = 1 - timeProm;
		
		//Cansancio
		double fatigueVal = 0;
		fatigueVal = Global.situation.fatigueAvg;
		
		fatigueVal = Math.max( 0, fatigueVal - Global.situation.fatigueVar );
		double totalVal = scoreVal * MyConstants.scoreWeight
				+ timeVal * MyConstants.timeWeight
				+ fatigueVal * MyConstants.fatigueWeight;
		
		TacticsEnum lastTactic = tactic;
		
		if( totalVal < 0.2 )
		{
			tactic = TacticsEnum.UltraDefensive;
		}
		else if( totalVal < 0.4 )
		{
			tactic = TacticsEnum.Defensive;
		}
		else if( totalVal < 0.6 )
		{
			tactic = TacticsEnum.Normal;
		}
		else if( totalVal < 0.8 )
		{
			tactic = TacticsEnum.Offensive;
		}
		else
		{
			tactic = TacticsEnum.UltraOffensive;
		}
		if( tactic != lastTactic )
		{
			needToRefreshPlayers = true;
		}
	}
}
