package org.javahispano.javacup.tacticas.jvc2013.JGTeam;

import java.util.LinkedList;
import java.util.List;

import org.javahispano.javacup.model.Tactic;
import org.javahispano.javacup.model.TacticDetail;
import org.javahispano.javacup.model.command.Command;
import org.javahispano.javacup.model.engine.GameSituations;
import org.javahispano.javacup.model.util.Position;

public class JGTactic implements Tactic {

  private LinkedList<Command> commands = new LinkedList<Command>();
  private Goalkeeper goalkeeper = new Goalkeeper();
  private Defenders defense = new Defenders();
  private Midfielders middlefield = new Midfielders();
  private Strikers striker = new Strikers();
  private GameStatus gameStatus = new GameStatus();

  public List<Command> execute(GameSituations sp) {
    commands.clear();
    gameStatus.update(sp, this);
    goalkeeper.update(commands, gameStatus);
    defense.update(commands, gameStatus);
    middlefield.update(commands, gameStatus);
    striker.update(commands, gameStatus);
    return commands;
  }

  Position alineacion1[] = new Position[] {
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.888111888111888, -32.54524886877828),
      new Position(-0.23776223776223776, -29.457013574660635),
      new Position(13.314685314685315, -33.02036199095023),
      new Position(-6.895104895104895, 0.0),
      new Position(0.4755244755244755, -13.065610859728507),
      new Position(-17.11888111888112, -6.651583710407239),
      new Position(15.454545454545453, -7.364253393665159),
      new Position(4.5174825174825175, 8.552036199095022),
      new Position(-9.986013986013985, 23.993212669683256),
      new Position(7.846153846153847, 29.21945701357466) };

  Position alineacion2[] = new Position[] {
      new Position(0.2595419847328244, -50.41044776119403),
      new Position(-11.888111888111888, -32.54524886877828),
      new Position(-0.23776223776223776, -29.457013574660635),
      new Position(13.314685314685315, -33.02036199095023),
      new Position(-6.895104895104895, 0.0),
      new Position(0.4755244755244755, -13.065610859728507),
      new Position(-17.11888111888112, -6.651583710407239),
      new Position(15.454545454545453, -7.364253393665159),
      new Position(4.5174825174825175, 8.552036199095022),
      new Position(-9.986013986013985, 23.993212669683256),
      new Position(7.846153846153847, 29.21945701357466) };

  public Position[] getStartPositions(GameSituations sp) {
    return alineacion2;
  }

  public Position[] getNoStartPositions(GameSituations sp) {
    return alineacion1;
  }

  public TacticDetail getDetail() {
    return new JGTacticDetail();
  }
}