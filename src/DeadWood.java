import core.*;
import model.BoardXML;
import view.*;
import model.*;
import guiview.*;
import java.util.*;

public class DeadWood {
  public static void main(String[] args) {

    Player[] players;
    if (args.length > 0) {
      int playerCount = Integer.parseInt(args[0]);
      players = DeadWood.setupPlayers(playerCount);
    } else {
      players = DeadWood.setupPlayers(2);
    }
    if (args.length > 1) {
      Helpers.adminmode = true;
    }

    BoardXML b = new BoardXML("board.xml");
    CardXML c = new CardXML("cards.xml");
    LinkedList<Card> cards = c.getCards();
    LinkedList<Room> rooms = b.readBoard();

    Game g = new Game(3, players, rooms, cards);
    TextView v = new TextView(g);
    GUIView gui = new GUIView(g);
    g.startGame();
    v.startListener();
  }

  private static Player[] setupPlayers(int count) {
    Player[] players = new Player[count];
    for (int i = 0; i < count; i++) {
      Player p = new Player("Player " + String.valueOf(i + 1), i);
      players[i] = p;
    }

    return players;
  }
}