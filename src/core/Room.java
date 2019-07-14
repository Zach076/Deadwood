package core;

import java.util.*;

public class Room extends GameObject {

  public LinkedList<Player> players = new LinkedList<Player>();
  public RoomType type;
  public String name;
  public LinkedList<String> neighbors;

  public Room(RoomType roomType, String name, LinkedList<String> neighbors) {
    this.name = name;
    this.neighbors = neighbors;
    type = roomType;
  }

  public void playerEntersRoom(Player p) {
    players.add(p);
    p.setCurrentRoom(this);
    this.onObjectChange(Messages.playerEnters);
  }

  public void playerLeavesRoom(Player p) {
    players.remove(p);
  }

  public void resetRoom() {
    for(Player p : players) {
      playerLeavesRoom(p);
    }
  }

  public static class Messages {
    public static final String playerEnters = "PlayerEnters";
  }

}
