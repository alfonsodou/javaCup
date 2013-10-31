package org.javahispano.javacup.tacticas.jvc2013.kpacha.utils;

import org.javahispano.javacup.model.util.Position;

public class Geometry {

    public static double distanceToLine(Position linePoint1,
	    Position linePoint2, Position point) {
	double x1 = linePoint1.getX();
	double x2 = linePoint2.getX() - x1;
	double y1 = linePoint1.getY();
	double y2 = linePoint2.getY() - y1;
	double px = point.getX() - x1;
	double py = point.getY() - y1;
	double dotprod = px * x2 + py * y2;
	double projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
	double lenSq = px * px + py * py - projlenSq;
	if (lenSq < 0) {
	    lenSq = 0;
	}
	return Math.sqrt(lenSq);
    }

    public static Position nearestPositionOnLine(Position linePoint1,
	    Position linePoint2, Position point) {
	double apx = point.getX() - linePoint1.getX();
	double apy = point.getY() - linePoint1.getY();
	double abx = linePoint2.getX() - linePoint1.getX();
	double aby = linePoint2.getY() - linePoint1.getY();

	double ab2 = abx * abx + aby * aby;
	double ap_ab = apx * abx + apy * aby;
	double t = ap_ab / ab2;
	return new Position(linePoint1.getX() + abx * t, linePoint1.getY()
		+ aby * t);
    }

    public static Position nearestPositionOnSegment(Position linePoint1,
	    Position linePoint2, Position point) {
	Position nearest = nearestPositionOnLine(linePoint1, linePoint2, point);
	if (nearest.getX() < Math.min(linePoint1.getX(), linePoint2.getX())
		|| nearest.getX() > Math.max(linePoint1.getX(),
			linePoint2.getX())
		|| nearest.getY() < Math.min(linePoint1.getY(),
			linePoint2.getY())
		|| nearest.getY() > Math.max(linePoint1.getY(),
			linePoint2.getY())) {
	    nearest = (linePoint1.norm(point) < linePoint2.norm(point)) ? linePoint1
		    : linePoint2;
	}
	return nearest;
    }

    public static Position[] getCirclesIntersection(Position center1,
	    double ratio1, Position center2, double ratio2) {
	Position[] intersection = null;
	double distance = center1.distance(center2);
	if (distance > ratio1 + ratio2 || distance < Math.abs(ratio1 - ratio2)) {
	    intersection = new Position[1];
	    intersection[0] = Position.medium(center1, center2);
	} else {
	    double a = center1.getX();
	    double b = center1.getY();
	    double c = center2.getX();
	    double d = center2.getY();
	    double sigma = 0.5
		    * Math.sqrt((distance + ratio1 + ratio2)
			    * (distance + ratio1 - ratio2)
			    * (distance - ratio1 + ratio2)
			    * (-distance + ratio1 + ratio2))
		    / (distance * distance);
	    double xFactor = sigma * (b - d);
	    double yFactor = sigma * (a - c);
	    double ratioFactor = (ratio1 * ratio1 - ratio2 * ratio2)
		    / (2 * distance * distance);
	    double x = ((a + c) / 2) + ((c - a) * ratioFactor);
	    double y = ((b + d) / 2) + ((d - b) * ratioFactor);
	    intersection = new Position[2];
	    intersection[0] = new Position(x + xFactor, y - yFactor);
	    intersection[1] = new Position(x - xFactor, y + yFactor);
	}

	return intersection;
    }

    public static Position[] getCircleLineIntersections(Position center,
	    double ratio, Position p1, Position p2) {
	Position[] intersection = null;
	if (distanceToLine(p1, p2, center) > ratio) {
	    intersection = new Position[1];
	    intersection[0] = nearestPositionOnLine(p1, p2, center);
	} else {
	    double m = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
	    double d = p1.getY() - m * p1.getX();
	    double a = center.getX();
	    double b = center.getY();
	    double sigma = Math.sqrt(ratio * ratio * (1 + m * m)
		    - Math.pow(b - m * a - d, 2));
	    intersection = new Position[2];
	    intersection[0] = new Position((a + b * m - d * m + sigma)
		    / (1 + m * m), (d + a * m + b * m * m + m * sigma)
		    / (1 + m * m));
	    intersection[1] = new Position((a + b * m - d * m - sigma)
		    / (1 + m * m), (d + a * m + b * m * m - m * sigma)
		    / (1 + m * m));
	}
	return intersection;
    }

}
