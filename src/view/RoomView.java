package view;

import core.*;

public class RoomView implements GameObjectListener {
  private Room roomRef;

  public RoomView(Room r) {
    this.roomRef = r;
    this.roomRef.addPriorityLisener(this);
  }

  public void summary(){
    System.out.println(this.roomRef.name);
    System.out.println("Neighbors:");
    for (String n :this.roomRef.neighbors){
      System.out.println("  " + n);
    }
    if(this.roomRef.type == RoomType.CastingOffice){
      System.out.println("Type: Office");
    }
    else if(this.roomRef.type == RoomType.Trailer){
      System.out.println("Type: Trailer");
    }
    else if(this.roomRef.type == RoomType.Scene){
      System.out.println("Type: Scene");
    }

  }

  public void call(String message) {
    if (message.equals(Room.Messages.playerEnters)) {
      System.out.println("Player entered " + this.roomRef.name );
    }
  }
}
