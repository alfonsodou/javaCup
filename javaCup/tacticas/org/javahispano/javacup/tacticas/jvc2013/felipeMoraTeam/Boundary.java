package org.javahispano.javacup.tacticas.jvc2013.felipeMoraTeam;

public class Boundary {
	
	public final Point[] points = new Point[4];
	
	Boundary( Point pos1, Point pos2, int level )
	{
		double slope = Common.slope( pos1, pos2 );
		
		double widthPar = getLevelWidthPar(level);
		double widthBehind = getLevelWidthBehind(level);
		
		if( slope >= 1 || slope <= -1 )
		{
			if( pos1.y <= pos2.y )
			{
				points[0] = new Point( pos1.x - widthPar, pos1.y, false );
				points[1] = new Point( pos1.x + widthPar, pos1.y, false );
				points[2] = new Point( pos2.x + widthPar, pos2.y + widthBehind, false );
				points[3] = new Point( pos2.x - widthPar, pos2.y + widthBehind, false );
			}
			else
			{
				points[0] = new Point( pos2.x - widthPar, pos2.y - widthBehind, false );
				points[1] = new Point( pos2.x + widthPar, pos2.y - widthBehind, false );
				points[2] = new Point( pos1.x + widthPar, pos1.y, false );
				points[3] = new Point( pos1.x - widthPar, pos1.y, false );
			}
		}
		else
		{
			if( pos1.x <= pos2.x )
			{
				points[0] = new Point( pos1.x, pos1.y + widthPar, false );
				points[1] = new Point( pos1.x, pos1.y - widthPar, false );
				points[2] = new Point( pos2.x + widthBehind, pos2.y - widthPar, false );
				points[3] = new Point( pos2.x + widthBehind, pos2.y + widthPar, false );
			}
			else
			{
				points[0] = new Point( pos2.x - widthBehind, pos2.y + widthPar, false );
				points[1] = new Point( pos2.x - widthBehind, pos2.y - widthPar, false );
				points[2] = new Point( pos1.x, pos1.y - widthPar, false );
				points[3] = new Point( pos1.x, pos1.y + widthPar, false );
			}
		}
	}
	
	public double getLevelWidthPar( int level )
	{
		switch( level )
		{
		case 1:
			return MyConstants.widthPar1;
		case 2:
			return MyConstants.widthPar2;
		case 3:
			return MyConstants.widthPar3;
		}
		return 0;
	}
	
	public double getLevelWidthBehind( int level )
	{
		switch( level )
		{
		case 1:
			return MyConstants.widthBehind1;
		case 2:
			return MyConstants.widthBehind2;
		case 3:
			return MyConstants.widthBehind3;
		}
		return 0;
	}
	
	public boolean contains(Point test) {
	      int i;
	      int j;
	      boolean result = false;
	      for (i = 0, j = points.length - 1; i < points.length; j = i++)
	      {
		        if ((points[i].y > test.y) != (points[j].y > test.y) &&
		            (test.x < (points[j].x - points[i].x) * (test.y - points[i].y) / (points[j].y-points[i].y) + points[i].x))
		        {
		          result = !result;
		        }
	      }
	      return result; 
	    }
}
