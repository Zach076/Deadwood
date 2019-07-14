package core;

import java.util.*;

public class Board extends GameObject implements GameObjectListener {
  public LinkedList<Room> rooms;
  public LinkedList<Card> cards;

  public Board(LinkedList<Room> rooms, LinkedList<Card> cards) {

    this.rooms = rooms;
    for (Room r : this.rooms) {
      if (r.type == RoomType.Scene) {
        Scene s = (Scene) r;
        s.addLisener(this);
      }
    }
    this.cards = cards;
  }

  public void resetBoard() { // move everyone to trailer
    LinkedList<Card> tempCards = new LinkedList<Card>();
    for (Card c : this.cards) {
      tempCards.add(c);
    }
    int i;
    Scene s;
    for (Room r : this.rooms) {
      if(r.type == RoomType.Scene) {
        s = (Scene) r;
        i = Helpers.randNum(0, tempCards.size()-1);
        s.resetRoom(tempCards.get(i));
        tempCards.remove(i);
      } else {
        r.resetRoom();
      }
    }
  }

  public void movePlayer(Player player, String room) {
    if (player.getCurrentRoom().neighbors.contains(room)) {
      player.getCurrentRoom().playerLeavesRoom(player);
      this.findRoom(room).playerEntersRoom(player);
      player.hasMoved = true;
    } else {
      this.onObjectChange(Messages.badMove);
    }
  }

  public Room findRoom(String roomName) {
    Room out = null;
    for (Room r : this.rooms) {
      if (r.name.equals(roomName)) {
        out = r;
      }
    }
    return out;
  }

  public int getActiveSceneCount(){
    int i = 0;
    for (Room r : this.rooms) {
      if (r.type == RoomType.Scene) {
        Scene s = (Scene) r;
        if(!s.wrapped){
          i++;
        }
      }
    }
    return i;
  }

  public static class Messages {
    public static final String badMove = "BadMove";
    public static final String lastScene = "LastScene";
    public static final String playerMoved = "PlayerMoved";
  }

  public void call(String message){
    if(message.equals(Scene.Messages.sceneWrap)){
      if(this.getActiveSceneCount() <= 1){
        this.onObjectChange(Messages.lastScene);
      }
    }
    if(message.equals(Room.Messages.playerEnters)){
      this.onObjectChange(Messages.playerMoved);
    }
  }

}
