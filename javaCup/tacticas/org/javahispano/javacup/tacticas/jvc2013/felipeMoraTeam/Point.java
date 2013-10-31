package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

public class Point {
	public double x;
	public double y;
	
	Point( double a_x, double a_y )
	{
		x = a_x + 100;
		y = a_y + 100;
	}
	
	Point( double a_x, double a_y, boolean inc )
	{
		int increment = ( inc ) ? 100 : 0;
		x = a_x + increment;
		y = a_y + increment;
	}
}
