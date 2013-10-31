package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

import org.javahispano.javacup.model.util.Position;

public class EvaluatePosition {

	EvaluatePosition( int a_nPlayer, Position a_position, boolean a_isPlayerPos )
	{
		nPlayer = a_nPlayer;
		position = a_position;
		isPlayerPos = a_isPlayerPos;
		
		value = 0;
		safePassLevel = 0;
		distanceToNearestRival = 0;
		nearestRivalId = 0;
	}
	public int nPlayer;
	public Position position;
	public double value;
	public int safePassLevel;
	public boolean isPlayerPos;
	public double distanceToNearestRival;
	public int nearestRivalId;
}
