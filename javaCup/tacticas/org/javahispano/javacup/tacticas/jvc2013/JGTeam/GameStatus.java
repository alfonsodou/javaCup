package org.javahispano.javacup.tacticas.jvc2013.JGTeam;

import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

public class GameStatus {

  public boolean goalkeeperCanKick;
  public boolean defenseCanKick;
  public boolean middlefieldCanKick;
  public GameSituations sp;
  public boolean isGoalKick;
  public boolean isBallInMyField;
  public int ballVerticalPosition;
  public int nearestPlayerToBall;
  public boolean isFreeKick;
  public Position[] alineation;
  public boolean strikerCanKick;
  public int force;
  public Position kickDirection;
  public boolean isBallInMyArea;

  public int[] nearestPlayers = new int[] { -1, -1, -1 };
  public Position ballTrayectoryPosition;

  public void update(GameSituations sp, JGTactic tactic) {
    resetStatus();

    // set game situation
    this.sp = sp;
    this.alineation = tactic.alineacion1;

    for (int i = 0; i < sp.canKick().length; i++) {
      if (sp.canKick()[i] == 0) {
        goalkeeperCanKick = true;
      } else if (sp.canKick()[i] >= 1 && sp.canKick()[i] < 4) {
        defenseCanKick = true;
        nearestPlayerToBall = sp.canKick()[i];
      } else if (sp.canKick()[i] >= 4 && sp.canKick()[i] < 8) {
        middlefieldCanKick = true;
        nearestPlayerToBall = sp.canKick()[i];
      } else if (sp.canKick()[i] >= 8) {
        strikerCanKick = true;
        nearestPlayerToBall = sp.canKick()[i];
      }
    }

    if (sp.isStarts() && positionInMyArea(sp.ballPosition()))
      isGoalKick = true;
    else if (sp.isStarts() && !positionInMyArea(sp.ballPosition()))
      isFreeKick = true;

    ballVerticalPosition = getBallVerticalPosition(sp.ballPosition());

    isBallInMyArea = positionInMyArea(sp.ballPosition());

    force = 45;
    kickDirection = Constants.centroArcoSup;

    if (sp.ballAltitude() > 2 && sp.getRecoveryBall().length > 0) {
      ballTrayectoryPosition = new Position(sp.getTrajectory(sp
          .getRecoveryBall()[0])[0],
          sp.getTrajectory(sp.getRecoveryBall()[0])[1]);
    } else {
      ballTrayectoryPosition = sp.ballPosition();
    }

    checkNearestPlayers();
  }

  private void checkNearestPlayers() {
    double[] distanceToBall = new double[11];

    for (int i = 0; i < 11; i++) {
      distanceToBall[i] = getDistanceToBall(sp.myPlayers()[i]);
    }

    for (int j = 0; j < 3; j++) {
      for (int k = 1; k < 11; k++) {
        if ((nearestPlayers[j] == -1 || distanceToBall[nearestPlayers[j]] > distanceToBall[k])
            && ((j == 0) || ((j == 1 && nearestPlayers[0] != k) || (j == 2
                && nearestPlayers[0] != k && nearestPlayers[1] != k)))) {
          nearestPlayers[j] = k;
        }
      }
    }

  }

  private double getDistanceToBall(Position p) {
    double x = p.getX() - ballTrayectoryPosition.getX();
    double y = p.getY() - ballTrayectoryPosition.getY();

    return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  }

  private int getBallVerticalPosition(Position ballPosition) {
    if (ballPosition.getY() > Constants.LARGO_CAMPO_JUEGO * 0.16)
      return 3;
    else if (ballPosition.getY() > Constants.LARGO_CAMPO_JUEGO * -0.16)
      return 2;
    else
      return 1;
  }

  private boolean positionInMyArea(Position position) {
    if (Math.abs(position.getX()) > (Constants.LARGO_AREA_GRANDE / 2)
        || position.getY() > (Constants.ANCHO_AREA_GRANDE - (Constants.LARGO_CAMPO_JUEGO / 2)))
      return false;
    else
      return true;
  }

  private void resetStatus() {
    goalkeeperCanKick = false;
    defenseCanKick = false;
    middlefieldCanKick = false;
    strikerCanKick = false;
    isGoalKick = false;
    isBallInMyField = false;
    ballVerticalPosition = 1;
    nearestPlayerToBall = 0;
    isFreeKick = false;
    nearestPlayers = new int[] { -1, -1, -1 };

  }
}
