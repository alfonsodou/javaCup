package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class Global {
	
	public static Situation situation = new Situation();
	static TeamDecisions teamDecisions = new TeamDecisions();
	
	static int NPLAYERS = 11;
	static char normalFormation[] = {'G','L','C','C','L','E','M','M','E','F','F'};
	static int normalFormationKickers[] = {5, 8, 1, 4, 2, 3, 6, 7};

    static Position normal[]=new Position[]{
    	new Position(0.2595419847328244,-50.41044776119403),
		new Position(-26.153846153846157,-21.380090497737555),
		new Position(-7.846153846153847,-30.88235294117647),
		new Position(8.55944055944056,-31.119909502262445),
		new Position(25.916083916083913,-20.90497737556561),
		new Position(-20.20979020979021,2.3755656108597285),
		new Position(-7.846153846153847,-11.402714932126697),
		new Position(8.321678321678322,-11.165158371040723),
		new Position(19.25874125874126,2.3755656108597285),
		new Position(-8.55944055944056,18.529411764705884),
		new Position(6.6573426573426575,24.943438914027148)
    };
	
    static char offensiveFormation[] = {'G','L','C','C','L','E','M','M','E','F','F'};
	static int offensiveFormationKickers[] = {5, 8, 1, 4, 2, 3, 6, 7};
    
	static Position offensive[]=new Position[]{
        new Position(-0.23776223776223776,-47.036199095022624),
        new Position(-27.104895104895103,-11.402714932126697),
        new Position(-8.55944055944056,-21.142533936651585),
        new Position(8.797202797202797,-21.142533936651585),
        new Position(24.727272727272727,-11.64027149321267),
        new Position(-20.447552447552447,12.352941176470589),
        new Position(-11.174825174825173,-1.4253393665158371),
        new Position(10.937062937062937,-1.1877828054298643),
        new Position(17.594405594405593,11.877828054298643),
        new Position(-6.895104895104895,28.744343891402718),
        new Position(6.6573426573426575,48.744343891402718)
    };
	
	static char ultraOffensiveFormation[] = {'G','C','M','F','C','E','M','M','E','F','F'};
	static int ultraOffensiveFormationKickers[] = {5, 8, 1, 4, 1, 4, 6, 7};

    static Position ultraOffensive[]=new Position[]{
        new Position(-0.23776223776223776,-37.77149321266968),
        new Position(-8.083916083916083,-15.203619909502263),
        new Position(-0.23776223776223776,0.47511312217194573),
        new Position(-0.4755244755244755,18.766968325791854),
        new Position(6.6573426573426575,-14.728506787330318),
        new Position(-26.391608391608393,28.98190045248869),
        new Position(-15.93006993006993,12.115384615384617),
        new Position(14.97902097902098,11.877828054298643),
        new Position(27.58041958041958,28.744343891402718),
        new Position(-5.944055944055944,34.920814479638004),
        new Position(6.181818181818182,34.920814479638004)
    };
	
    static char ultraDefensiveFormation[] = {'G','L','C','C','L','M','C','M','M','M','F'};
    static int ultraDefensiveFormationKickers[] = {9, 9, 1, 4, 5, 8, 9, 9};

    static Position ultraDefensive[]=new Position[]{
	    new Position(0.2595419847328244,-50.41044776119403),
        new Position(-20.923076923076923,-35.15837104072398),
        new Position(-10.223776223776223,-37.53393665158371),
        new Position(10.461538461538462,-38.24660633484163),
        new Position(20.447552447552447,-34.68325791855204),
        new Position(-6.895104895104895,-26.131221719457013),
        new Position(-0.23776223776223776,-39.671945701357465),
        new Position(-0.7132867132867133,-21.61764705882353),
        new Position(5.944055944055944,-26.131221719457013),
        new Position(-0.4755244755244755,-11.402714932126697),
        new Position(0.0,21.855203619909503)
	};

    static Position initOwn[]=new Position[]{
            new Position(0.23776223776223776,-48.699095022624434),
            new Position(-27.104895104895103,-21.380090497737555),
            new Position(-8.797202797202797,-34.44570135746606),
            new Position(8.083916083916083,-34.44570135746606),
            new Position(26.867132867132867,-21.61764705882353),
            new Position(-20.685314685314687,-1.4253393665158371),
            new Position(-8.797202797202797,-14.490950226244346),
            new Position(7.37062937062937,-14.728506787330318),
            new Position(20.447552447552447,-1.4253393665158371),
            new Position(-1.902097902097902,0.0),
            new Position(1.4265734265734267,-0.23755656108597287)
        };

	static Position initRival[]=new Position[]{
	        new Position(0.2595419847328244,-50.41044776119403),
	        new Position(-20.923076923076923,-27.794117647058822),
	        new Position(-8.321678321678322,-34.68325791855204),
	        new Position(7.846153846153847,-34.44570135746606),
	        new Position(20.923076923076923,-28.031674208144796),
	        new Position(-16.167832167832167,-11.165158371040723),
	        new Position(-4.755244755244756,-17.57918552036199),
	        new Position(2.6153846153846154,-13.303167420814479),
	        new Position(14.503496503496503,-12.115384615384617),
	        new Position(-9.272727272727272,0.0),
	        new Position(9.272727272727272,0.0)
	};
	
    static Position defCorner[]=new Position[]{
            new Position(-0.23776223776223776,-48.93665158371041),
            new Position(-8.55944055944056,-51.31221719457013),
            new Position(-5.468531468531468,-44.89819004524887),
            new Position(5.468531468531468,-44.89819004524887),
            new Position(8.55944055944056,-51.31221719457013),
            new Position(-26.867132867132867,-18.054298642533936),
            new Position(-10.223776223776223,-34.20814479638009),
            new Position(9.034965034965035,-34.44570135746606),
            new Position(27.58041958041958,-18.054298642533936),
            new Position(-9.748251748251748,0.47511312217194573),
            new Position(5.468531468531468,8.552036199095022)
    };
	
	public static Position GetPlayerPosition( int index )
	{
		if ( Global.situation.m_sp.isRivalStarts() &&
			(Global.situation.ballPosition.distance(Constants.cornerInfIzq) < 0.5 || Global.situation.ballPosition.distance(Constants.cornerInfDer) < 0.5) )
		{
			return Global.defCorner[index]; 
		}
		else
		{
			switch( teamDecisions.tactic )
			{
			case UltraDefensive:
	    		return GetPlayerUltraDefensivePosition( index );
	    	case Defensive:
	    	case Normal:
	    		return GetPlayerNormalPosition( index );
	    	case Offensive:
	    		return GetPlayerOffensivePosition( index );
	    	case UltraOffensive:
	    		return GetPlayerUltraOffensivePosition( index );
			}
		}
		return null;
	}
	
	public static Position GetPlayerUltraOffensivePosition( int index )
	{
		Position p = Global.ultraOffensive[index];
		
		if( Global.situation.ballPosition.getY() >= -20 )
		{
			double deltaX = Global.situation.ballPosition.getX() * 0.2;
			Position myPos = Global.situation.actualPositionsOwn[index];
			
			double difX = Global.situation.ballPosition.getX() - myPos.getX();
			deltaX = difX * 0.5;
			
			double deltaY = Global.situation.ballPosition.getY() + 5;
			if( !Global.situation.havePosession )
			{
				deltaY -= 10; 
			}
			p = p.movePosition( deltaX, deltaY );
			
			p = ApplyLimits( index, p );
		}		
		
		return p;
	}
	
	public static Position GetPlayerOffensivePosition( int index )
	{
		Position p = Global.offensive[index];

		if( Global.situation.ballPosition.getY() >= -25 )
		{
			double deltaX = Global.situation.ballPosition.getX() * 0.2;
			Position myPos = Global.situation.actualPositionsOwn[index];
			
			double difX = Global.situation.ballPosition.getX() - myPos.getX();
			deltaX = difX * 0.5;
			
			double deltaY = Global.situation.ballPosition.getY();
			
			if( !Global.situation.havePosession )
			{
				deltaY -= 10;
			}
			p = p.movePosition( deltaX, deltaY );
			
			p = ApplyLimits( index, p );
		}		

		return p;
	}
	
	public static Position GetPlayerNormalPosition( int index )
	{
		Position p = Global.normal[index];
		
		if( Global.situation.ballPosition.getY() >= -30 )
		{
			double deltaX = Global.situation.ballPosition.getX() * 0.2;
			Position myPos = Global.situation.actualPositionsOwn[index];
			
			double difX = Global.situation.ballPosition.getX() - myPos.getX();
			deltaX = difX * 0.5;
			
			double deltaY = Global.situation.ballPosition.getY() - 5;
			if( !Global.situation.havePosession )
			{
				deltaY -= 10;
			}
			p = p.movePosition( deltaX, deltaY );
			
			p = ApplyLimits( index, p );
		}		

		return p;
	}
	
	public static Position GetPlayerUltraDefensivePosition( int index )
	{
		return Global.ultraDefensive[index];
	}
	
	public static Position ApplyLimits( int index, Position p )
	{
		Position pos = p;
		char c = GetFormationPlayerTypes()[index];
		
		switch( c )
		{
		case 'G':
			pos = pos.setPosition( pos.getX(), Math.min( pos.getY() , -(Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_CHICA ) ) );
			break;
		case 'C':
			if( Global.situation.havePosession )
				pos = pos.setPosition( pos.getX(), Math.max( -35, Math.min( 0, pos.getY() ) ) );
			else
				pos = pos.setPosition( pos.getX(), Math.max( -40, Math.min( -15, pos.getY() ) ) );
			break;
		case 'M':
			pos = pos.setPosition( pos.getX(), Math.max( -30, Math.min( Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_GRANDE, pos.getY() ) ) );
			break;
		case 'L':
			pos = pos.setPosition( Math.min( Constants.ANCHO_CAMPO_JUEGO - 1, Math.max( - Constants.ANCHO_CAMPO_JUEGO + 1, pos.getX() ) ),
					Math.max( -40, Math.min( pos.getY(), Global.situation.lastPlayerPos.getY() ) ) );
			break;
		case 'E':
			pos = pos.setPosition( pos.getX(), Math.max( -30, Math.min( Constants.LARGO_CAMPO_JUEGO / 2 - Constants.ANCHO_AREA_CHICA, pos.getY() ) ) );
			break;
		}
		
		return pos;
	}
	
	public static int [] GetFormationKikersType( )
    {
    	switch( Global.teamDecisions.tactic )
    	{
    	case UltraDefensive:
    		return Global.ultraDefensiveFormationKickers;
    	case Defensive:
    		return Global.normalFormationKickers;
    	case Normal:
    		return Global.normalFormationKickers;
    	case Offensive:
    		return Global.offensiveFormationKickers;
    	case UltraOffensive:
    		return Global.ultraOffensiveFormationKickers;
    	}
    	
		return null;
    }
	

    public static char [] GetFormationPlayerTypes( )
    {
    	switch( Global.teamDecisions.tactic )
    	{
    	case UltraDefensive:
    		return ultraDefensiveFormation;
    	case Defensive:
    		return normalFormation;
    	case Normal:
    		return normalFormation;
    	case Offensive:
    		return offensiveFormation;
    	case UltraOffensive:
    		return ultraOffensiveFormation;
    	}
    	
		return null;
    }
	
    public static double GetPowerByMeterOnPass( int index )
    {
    	double playerPower = situation.m_sp.getMyPlayerPower( index );
    	
    	return MyConstants.powerByMeterOnPass / playerPower;
    }
    
    public static double GetPowerHitOnPosession( int index )
    {
    	double playerPower = situation.m_sp.getMyPlayerPower( index );
    	
    	Position rivalPosition = situation.actualPositionsRival[situation.idMyPlayerToNearestRival[ index ]];
    	Position myPos = situation.actualPositionsOwn[index];
    	
    	double deltaY = rivalPosition.getY() - myPos.getY();
    	
    	double mult = 1;
    	if( deltaY > 0 && deltaY < 5 )
    		mult = deltaY / 5;
    	
    	return ( MyConstants.powerHitOnPosession / playerPower ) * mult;
    }
    
    public static double GetPowerHitOnPosessionSprint( int index )
    {
    	double playerPower = situation.m_sp.getMyPlayerPower( index );
    	
    	return MyConstants.powerHitOnPosessionSprint / playerPower;
    }
    
}
