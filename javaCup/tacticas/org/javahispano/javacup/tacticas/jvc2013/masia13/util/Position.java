package org.javahispano.javacup.tacticas.jvc2013.masia13.util;

import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.FIELD_WIDTH;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.GOAL_AREA_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.GOAL_AREA_WIDTH;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.MY_GOAL_CENTER;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.PENALTY_AREA_HEIGHT;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.PENALTY_AREA_WIDTH;
import static org.javahispano.javacup.tacticas.jvc2013.masia13.model.FieldSpecifications.RIVAL_GOAL_CENTER;

public class Position {
	
	public double x;
	public double y;
	public double z;
	
	public Position(double x, double y) {
		this(x, y, 0);
	}
	
	public Position(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}	
	
	public Position(Position position) {
		this(position.x, position.y, position.z);
	}
	
	public Position moveAngle(double angle, double ratio) {
        return new Position(x + Math.cos(angle) * ratio, y + Math.sin(angle) * ratio);
    }
	
	public Position moveAngle(double angle, double ratio, double maxRatio) {
        return new Position(x + Math.cos(angle) * Math.min(ratio, maxRatio), y + Math.sin(angle) * Math.min(ratio, maxRatio));
    }

    public Position movePosition(double dx, double dy) {
        return new Position(x + dx, y + dy);
    }

    public Position movePosition(double dx, double dy, double maxRatio) {
        Position dest = new Position(x + dx, y + dy);
        double angle = angle(dest);
        double ratio = distance(dest);
        return new Position(x + Math.cos(angle) * Math.min(ratio, maxRatio), y + Math.sin(angle) * Math.min(ratio, maxRatio));
    }
    
    public Position getInvertedPosition() {
        return new Position(-x, -y, z);
    }

    public boolean isInsideGameField(double mas) {
        double mx = FIELD_WIDTH / 2 + mas;
        double my = FIELD_HEIGHT / 2 + mas;
        return Math.abs(x) <= mx && Math.abs(y) <= my;
    }

    public boolean isInsideGameField() {
        return isInsideGameField(0);
    }
    
    public Position getInsideGameField() {
        double mx = FIELD_WIDTH / 2;
        double my = FIELD_HEIGHT / 2;
        double x0 = x, y0 = y;
        if (x0 > mx) {
            x0 = mx;
        }
        if (x0 < -mx) {
            x0 = -mx;
        }
        if (y0 > my) {
            y0 = my;
        }
        if (y0 < -my) {
            y0 = -my;
        }
        return new Position(x0, y0);
    }
    
    public boolean insideMyPenaltyArea() {
		return Math.abs(x) <= PENALTY_AREA_WIDTH/2 &&
				y <= MY_GOAL_CENTER.y + PENALTY_AREA_HEIGHT;
	}
    
    public boolean insideRivalPenaltyArea() {
		return Math.abs(x) <= PENALTY_AREA_WIDTH/2 &&
				y >= RIVAL_GOAL_CENTER.y - PENALTY_AREA_HEIGHT;
	}

    public boolean insideMyGoalArea() {
		return Math.abs(x) <= GOAL_AREA_WIDTH /2 &&
				y <= MY_GOAL_CENTER.y + GOAL_AREA_HEIGHT;
	}
    
    public boolean insideRivalGoalArea() {
		return Math.abs(x) <= GOAL_AREA_WIDTH/2 &&
				y >= RIVAL_GOAL_CENTER.y - GOAL_AREA_HEIGHT;
	}
    
    public double angle(Position p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return Math.atan2(dy, dx);
    }

    public double distance(Position p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }
    
    public Position medium(Position position) {
        return new Position((x + position.x) / 2, (y + position.y) / 2);
    }
    
    public int nearestIndex(Position[] positions) {
        double max = Double.MAX_VALUE;
        double dist;
        int idx = -1;
        for (int i = 0; i < positions.length; i++) {
            dist = norm(positions[i]);
            if (dist < max) {
                max = dist;
                idx = i;
            }
        }
        return idx;
    }
    
    public <T extends Position> T nearest(T[] positions) {
        return positions[nearestIndex(positions)];
    }

    public int nearestIndex(Position[] positions, int... exclude) {
        double max = Double.MAX_VALUE;
        double dist;
        int idx = -1;
        boolean found;
        for (int i = 0; i < positions.length; i++) {
            dist = norm(positions[i]);
            found = false;
            for (int k : exclude) {
                if (k == i) {
                    found = true;
                    break;
                }
            }
            if (!found && dist < max) {
                max = dist;
                idx = i;
            }

        }
        return idx;
    }
    
    public <T extends Position> T nearest(T[] positions, int... exclude) {
        return positions[nearestIndex(positions, exclude)];
    }

    public int[] nearestIndexes(Position[] positions) {
        int[] tmp = new int[positions.length];
        double[] dst = new double[positions.length];
        for (int i = 0; i < positions.length; i++) {
            tmp[i] = i;
            dst[i] = this.norm(positions[i]);
        }
        int ii;
        double dd;
        for (int i = 0; i < positions.length; i++) {
            for (int j = i + 1; j < positions.length; j++) {
                if (dst[i] > dst[j]) {
                    dd = dst[j];
                    dst[j] = dst[i];
                    dst[i] = dd;
                    ii = tmp[j];
                    tmp[j] = tmp[i];
                    tmp[i] = ii;
                }
            }
        }
        return tmp;
    }

    public int[] nearestIndexes(Position[] positions, int... exclude) {
        int[] tmp = new int[positions.length];
        double[] dst = new double[positions.length];
        boolean found;
        for (int i = 0; i < positions.length; i++) {
            tmp[i] = i;
            found = false;
            for (int k : exclude) {
                if (k == i) {
                    dst[i] = Integer.MAX_VALUE;
                    found = true;
                    break;
                }
            }
            if (!found) {
                dst[i] = this.norm(positions[i]);
            }
        }
        int ii;
        double dd;
        for (int i = 0; i < positions.length; i++) {
            for (int j = i + 1; j < positions.length; j++) {
                if (dst[i] > dst[j]) {
                    dd = dst[j];
                    dst[j] = dst[i];
                    dst[i] = dd;
                    ii = tmp[j];
                    tmp[j] = tmp[i];
                    tmp[i] = ii;
                }
            }
        }
        return tmp;
    }
    
    public double norm(Position p) {
        double dx = p.x - x;
        double dy = p.y - y;
        return dx * dx + dy * dy;
    }
    
    @Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
