package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import java.util.ArrayList;

import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Situation {
	
	Position[] actualPositionsOwn;
	Position[] actualPositionsRival;
	Position lastPlayerPos;
	int lastPlayerId;
	Position firstPlayerPos;
	int firstPlayerId;
	int firstOwnPlayerId;
	int lastOwnPlayerId;
	int nearestRivalPlayerId;
	double nearestRivalPlayerDist;
	int nearestOwnPlayerId;
	double nearestOwnPlayerDist;
	int nearestSecondOwnPlayerId;
	double nearestSecondOwnPlayerDist;
	int rivalGoalKeeperIndex = -1;
	double[] distFromMyPlayerToNearestRival;
	int[] idMyPlayerToNearestRival;
	int passDestPlayerId = -1;
	int bestSafePassId = -1;
	Position[] bestPlayerPos;
	double[] bestPlayerValue;
	Position throughBallPosition;
	EvaluatePosition bestEvaluatePosition;
	ArrayList<EvaluatePosition> evaluatePositionList;
	int rivalAreaPlayers = 0;
	GameSituations m_sp;
	boolean[] recuperators;
	boolean[] kickers;
	int iterRecuperation;
	double[] posRecuperation;
	boolean rivalStarts;
	Position ballPosition;
	Position ballPositionAnt;
	boolean havePosession = false;
	double fatigueAvg = 0;
	double fatigueVar = 0;	
	
	void init( GameSituations sp ){
		recuperators = new boolean[Global.NPLAYERS];
		kickers = new boolean[Global.NPLAYERS];
		
		ballPositionAnt = new Position( Constants.centroCampoJuego );
		
		distFromMyPlayerToNearestRival = new double[Global.NPLAYERS];
		idMyPlayerToNearestRival = new int[Global.NPLAYERS];
		
		bestPlayerPos = new Position[Global.NPLAYERS];
		bestPlayerValue = new double[Global.NPLAYERS];
		
		PlayerDetail[] detail= sp.rivalPlayersDetail();
		boolean found = false;
		for( int i = 0; i < detail.length && !found; i++ )
		{
			if( detail[i].isGoalKeeper() )
			{
				rivalGoalKeeperIndex = i;
				found = true;
			}
		}
		
		evaluatePositionList = new ArrayList<EvaluatePosition>();
		
	}

	void update(GameSituations sp){
		m_sp = sp;
		ballPosition = sp.ballPosition();
		actualPositionsOwn = sp.myPlayers();
		actualPositionsRival = sp.rivalPlayers();
		rivalStarts = sp.isRivalStarts();
		markRecuperators();
		markKickers();
		posession();
		calcFatigue();
		calcFirstAndLastPlayer();
		calcNearestRivalPlayer();
		calcNearestOwnPlayers();
		
		calcRivalAreaPlayers( );
		
		calcEvaluatePositions( );
		
		calcNearestRivalPlayersFromOurs();

		calcPlayerToPass();		
		evaluatePasses( );
	}

	void calcRivalAreaPlayers( )
	{
		rivalAreaPlayers = 0;
		for( int i = 1; i < Global.NPLAYERS; i++ )
		{
			if( Common.insideRivalArea ( Global.situation.actualPositionsRival[i] ) )
			{
				rivalAreaPlayers++;
			}
		}
	}
	
	void calcEvaluatePositions( )
	{
		evaluatePositionList.clear();
		
		for( int i = 1; i < Global.NPLAYERS; i++ )
		{
			Position pos = actualPositionsOwn[i];

			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX() + MyConstants.distToRelevPosition, pos.getY() + MyConstants.distToRelevPosition ), false ) );
			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX() - MyConstants.distToRelevPosition, pos.getY() + MyConstants.distToRelevPosition ), false ) );
			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX(), pos.getY() + MyConstants.distToRelevPosition ), false ) );
			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX() + MyConstants.distToRelevPosition, pos.getY() + MyConstants.distToRelevPosition ), false ) );
			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX() - MyConstants.distToRelevPosition, pos.getY() ), false ) );
			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX() + MyConstants.distToRelevPosition, pos.getY() ), false ) );
			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX() - MyConstants.distToRelevPosition, pos.getY() - MyConstants.distToRelevPosition ), false ) );
			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX(), pos.getY() - MyConstants.distToRelevPosition ), false ) );
			evaluatePositionList.add( new EvaluatePosition( i, new Position( pos.getX() + MyConstants.distToRelevPosition, pos.getY() - MyConstants.distToRelevPosition ), false ) );
		}
	}

	void calcNearestOwnPlayers( )
	{
		nearestOwnPlayerId = -1;
		nearestSecondOwnPlayerDist = Constants.LARGO_CAMPO_JUEGO * 2;
		
		double minDist = Constants.LARGO_CAMPO_JUEGO * 2;
		
		for( int i = 1; i < Global.NPLAYERS; i++ )
		{
			double dist = ballPosition.distance( actualPositionsOwn[i] );
			if( dist < minDist )
			{
				minDist = dist;
				if( nearestOwnPlayerId != -1 )
				{
					nearestSecondOwnPlayerDist = nearestOwnPlayerDist;
					nearestSecondOwnPlayerId = nearestOwnPlayerId;
				}
				nearestOwnPlayerDist = dist;
				nearestOwnPlayerId = i;
			}
			else
			{
				if( dist < nearestSecondOwnPlayerDist )
				{
					nearestSecondOwnPlayerDist = dist;
					nearestSecondOwnPlayerId = i;
				}
			}
		}
	}
	
	void calcNearestRivalPlayer( )
	{
		double minDist = Constants.LARGO_CAMPO_JUEGO;
		for( int i = 0; i < Global.NPLAYERS; i++ )
		{
			double dist = ballPosition.distance( actualPositionsRival[i] );
			if( dist < minDist )
			{
				minDist = dist;
				nearestRivalPlayerDist = dist;
				nearestRivalPlayerId = i;
			}
		}
	}

	void calcFatigue( )
	{
		double totalFatigue = 0;
		double dispVar = 0;
		for( int i = 1; i < Global.NPLAYERS; i++ )
		{
			double val = m_sp.getMyPlayerEnergy( i );
			totalFatigue += val;
		}
		fatigueAvg = totalFatigue / (Global.NPLAYERS - 1);
		
		for( int i = 1; i < Global.NPLAYERS; i++ )
		{
			double val = m_sp.getMyPlayerEnergy( i );
			dispVar += Math.pow( val - fatigueAvg, 2 );
		}		
		fatigueVar = dispVar / (Global.NPLAYERS - 1);
	}
	
	void markRecuperators(){
		for(int j = 0; j < recuperators.length; j++){
			recuperators[j] = false;
		}
		int[] recs = m_sp.getRecoveryBall();
		for(int i = 1; i < recs.length; i++){
			recuperators[recs[i]] = true;
		}
		if(recs.length > 0){
			iterRecuperation = recs[0];
			posRecuperation = m_sp.getTrajectory(iterRecuperation);
		}
	}
	
	void markKickers(){
		for(int j = 0; j < kickers.length; j++){
			kickers[j] = false;
		}
		int[] kicks = m_sp.canKick();
		for(int i = 0; i < kicks.length; i++){
			kickers[kicks[i]] = true;
		}
	}
	
	void posession(){
		boolean enc = false;
		havePosession = false;
		
		if ( m_sp.isStarts() )
		{
			havePosession = true;
			enc = true;
		}
		else if ( m_sp.isRivalStarts() || m_sp.rivalCanKick().length > 0)
		{
			enc = true;
		}
		
    	for(int j = 0; j < kickers.length && !enc; j++){
    		if( kickers[j] == true  )
    		{
    			havePosession = true;
    			enc = true;
    		}
    	}
	}
	
	void calcFirstAndLastPlayer( )
	{
		double minPosition = Constants.LARGO_CAMPO_JUEGO;
		double maxPosition = 0;
		double minOwnPosition = Constants.LARGO_CAMPO_JUEGO;
		double maxOwnPosition = 0;
		
		double distance = 0, ownDistance;
		for( int i = 1; i < Global.NPLAYERS; i++ )
		{
			distance = ( Constants.LARGO_CAMPO_JUEGO / 2 ) - actualPositionsRival[i].getY();
			
			if( distance < minPosition )
			{
				minPosition = distance;
				lastPlayerId = i;
				lastPlayerPos = actualPositionsRival[i];
			}
			if( distance > maxPosition )
			{
				maxPosition = distance;
				firstPlayerId = i;
				firstPlayerPos = actualPositionsRival[i];
			}
			ownDistance = ( Constants.LARGO_CAMPO_JUEGO / 2 ) - actualPositionsOwn[i].getY();
			if( ownDistance < minOwnPosition )
			{
				minOwnPosition = ownDistance;
				firstOwnPlayerId = i;
			}
			if( ownDistance > maxOwnPosition )
			{
				maxOwnPosition = ownDistance;
				lastOwnPlayerId = i;
			}			
		}
	}
	
	void calcPlayerToPass( )
	{
		if( kickers[nearestOwnPlayerId] )
		{
			bestSafePassId = -1;
			
			Position myPos = Global.situation.actualPositionsOwn[nearestOwnPlayerId];
			
			double minDistance = Constants.LARGO_CAMPO_JUEGO;
			for( int i = 0; i < evaluatePositionList.size(); i++ )
			{
				int safePassLevel = 0;
				evaluatePositionList.get(i).safePassLevel = safePassLevel;
				if( evaluatePositionList.get( i ).nPlayer != nearestOwnPlayerId && Common.insideLogicField( evaluatePositionList.get(i).position ) )
				{				
					Position posToEval = evaluatePositionList.get(i).position;
					for( int level = 3; level > 0 && safePassLevel == 0; level-- )
					{
						Boundary b =new Boundary( new Point( myPos.getX(), myPos.getY() ), new Point( posToEval.getX(), posToEval.getY() ), level );

						boolean safePass = true;
						for( int r = 0; r < Global.NPLAYERS && safePass; r++ )
						{
							Position rivalPos = Global.situation.actualPositionsRival[r];
							Point rivalPoint = new Point( rivalPos.getX(), rivalPos.getY() );
							
							safePass = !b.contains( rivalPoint );
						}
						
						
						if( safePass )	
						{
							safePassLevel = level;
							
							evaluatePositionList.get(i).safePassLevel = level;
							
							double dist = myPos.distance( posToEval );
							if( dist < minDistance )
							{
								bestSafePassId = i;
								minDistance = dist;
							}
						}
					}					
				}
			}
		}
	}
	
	void evaluatePasses( )
	{		
		if( !havePosession || ( passDestPlayerId > -1 && kickers[passDestPlayerId] ) )
			passDestPlayerId = -1;
		
		for( int np = 0; np < Global.NPLAYERS; np++ )
		{
			bestPlayerValue[np] = 0;
			bestPlayerPos[np] = null;
		}
		if( kickers[nearestOwnPlayerId] )
		{						
			double maxValue = 0;
			for( int i = 0; i < evaluatePositionList.size(); i++ )
			{
				evaluatePositionList.get(i).value = 0;
				
				if( nearestOwnPlayerId != evaluatePositionList.get(i).nPlayer && Common.insideLogicField( evaluatePositionList.get(i).position ) )
				{
					double levelPassValue = ( 0.33 * evaluatePositionList.get(i).safePassLevel ) + 0.01;
					
					Position posToEval = evaluatePositionList.get(i).position;
					double destPlayerFreedomValue = Math.min( evaluatePositionList.get( i ).distanceToNearestRival, MyConstants.destPlayerFreedomMax ) / MyConstants.destPlayerFreedomMax;
					
					double behindBall = posToEval.getY() - Global.situation.ballPosition.getY();
					destPlayerFreedomValue += behindBall * 0.01; 					
					double formationValue = 0;
					
					switch( Global.teamDecisions.tactic )
					{
					case UltraOffensive:
						formationValue = calcUltraOffensivePassValue( nearestOwnPlayerId, posToEval );
						break;
					case Offensive:
						formationValue = calcOffensivePassValue( nearestOwnPlayerId, posToEval );
						break;
					case Normal:
					case Defensive:
						formationValue = calcNormalPassValue( nearestOwnPlayerId, posToEval );
						break;
					case UltraDefensive:
						formationValue = calcUltraDefensivePassValue( nearestOwnPlayerId, posToEval );
						break;
					}
					
					Position myPos = actualPositionsOwn[nearestOwnPlayerId];
					Position destPos = evaluatePositionList.get( i ).position;
					double goAheadValue = Math.max( destPos.getY() - myPos.getY(), 0 ) / MyConstants.longPass;
					
					evaluatePositionList.get(i).value = ( levelPassValue * MyConstants.levelPassWeight ) + ( destPlayerFreedomValue * MyConstants.destPlayerFreedomWeight )
							+ ( formationValue * MyConstants.teamFormationWeight ) + ( goAheadValue * MyConstants.goAheadWeight );
					
					if( evaluatePositionList.get(i).value > maxValue )
					{
						maxValue = evaluatePositionList.get(i).value;
						passDestPlayerId = evaluatePositionList.get(i).nPlayer;
						throughBallPosition = evaluatePositionList.get(i).position;
						bestEvaluatePosition = evaluatePositionList.get( i );
					}
					
					if( bestPlayerValue[evaluatePositionList.get(i).nPlayer] < evaluatePositionList.get(i).value )
					{
						bestPlayerValue[evaluatePositionList.get(i).nPlayer] = evaluatePositionList.get(i).value;
						bestPlayerPos[evaluatePositionList.get(i).nPlayer] = evaluatePositionList.get(i).position;
					}
				}
			}
		}
	}
	
	double calcUltraOffensivePassValue( int myIndex, Position destPos )
	{
		double value = 0;
		
		Position myPos = actualPositionsOwn[myIndex];
		
		double dist = myPos.distance( destPos );
		if( !(myPos.getY() > 0 && destPos.getY() < 0) )
		{
			if( dist > MyConstants.ultraOffensiveMinDistanceFav && ( Constants.posteDerArcoSup.getY() - destPos.getY() ) < 20 )
			{
				value = 1;
			}
			else
			{
				if( dist > -5 )
				{
					value = dist * 0.2;
				}
			}
		}
		
		return value;
	}
	
	double calcOffensivePassValue( int myIndex, Position destPos )
	{
		double value = 0;
		
		return value;
	}
	
	double calcNormalPassValue( int myIndex, Position destPos )
	{
		double value = 0;
		
		return value;
	}
	
	double calcUltraDefensivePassValue( int myIndex, Position destPos )
	{
		double value = 0;
		
		return value;
	}
	
	void calcNearestRivalPlayersFromOurs( )
	{
		for( int i = 0; i < evaluatePositionList.size(); i++ )
		{
			double minDist = Constants.LARGO_CAMPO_JUEGO;
			Position myPos = evaluatePositionList.get( i ).position;
			for( int j = 0; j < Global.NPLAYERS; j++ )
			{
				double dist = myPos.distance( actualPositionsRival[j] );
				if( dist < minDist )
				{
					evaluatePositionList.get( i ).distanceToNearestRival = dist;
					evaluatePositionList.get( i ).nearestRivalId = j;
					distFromMyPlayerToNearestRival[evaluatePositionList.get(i).nPlayer] = dist;
					idMyPlayerToNearestRival[evaluatePositionList.get(i).nPlayer] = j;
					minDist = dist;
				}
			}
		}
	}

	void ballPositionAntUpd() {
		ballPositionAnt = ballPosition;
	}
}