package org.javahispano.javacup.tacticas.jvc2013.JGTeam;

import java.util.LinkedList;

import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.command.CommandHitBall;
import org.javahispano.javacup.model.command.CommandMoveTo;

public class Strikers {

  public void update(LinkedList<Command> commands, GameStatus gameStatus) {

    if (gameStatus.strikerCanKick == true) {
      commands.add(new CommandHitBall(gameStatus.nearestPlayerToBall,
          gameStatus.kickDirection, 1, 15));
      return;
    }

    if (gameStatus.isFreeKick == true && gameStatus.ballVerticalPosition == 3) {
      commands.add(new CommandMoveTo(8, gameStatus.sp.ballPosition()));
      return;
    }

    for (int i = 8; i < 11; i++) {
      boolean isNearest = false;
      for (int j = 0; j < 3; j++) {
        if (gameStatus.nearestPlayers[j] == i) {
          isNearest = true;
        }
      }
      if (isNearest) {
        commands.add(new CommandMoveTo(i, gameStatus.ballTrayectoryPosition));
      } else {

        commands.add(new CommandMoveTo(i, gameStatus.alineation[i]));
      }

    }
  }

}
