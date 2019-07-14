package view;

import core.*;

public class PlayerView implements GameObjectListener {
  private Player playerRef;

  public PlayerView(Player p) {
    this.playerRef = p;
    this.playerRef.addLisener(this);
    System.out.println("Player " + playerRef.name + " added.");
  }

  public void playerSummary(){
    System.out.println();
    System.out.println( this.playerRef.name);
    System.out.println("Level: " + String.valueOf(this.playerRef.level));
    System.out.println("Fame: " + String.valueOf(this.playerRef.fame));
    System.out.println("Money: " + String.valueOf(this.playerRef.money));
    System.out.println("Current Room: " + this.playerRef.getCurrentRoom().name);
    if(this.playerRef.currentRole != null){
      System.out.println("Current Role: " + this.playerRef.currentRole.name);
    }
    System.out.println();
    
  }

  public void call(String message) {

    if (message.equals(Player.Messages.startTurn)) {
      System.out.println(this.playerRef.name + " has started Turn. Level: " + String.valueOf(this.playerRef.level));
    }
    else if(message.equals(Player.Messages.upgrade)){
      System.out.println(this.playerRef.name + " has upgraded! to " + this.playerRef.level);
    }
    else if (message.equals(Player.Messages.gainedRole)){
      Scene s = (Scene) this.playerRef.getCurrentRoom();
      System.out.println(this.playerRef.name + " has taken up role " + this.playerRef.currentRole.name + " in the "  + s.name);
    }
    else if (message.equals(Player.Messages.resourceChange)) {
      System.out.println(this.playerRef.name + " now has " + String.valueOf(this.playerRef.fame) + " fame and " + String.valueOf(this.playerRef.money) + " money.");
    }
    else if(message.equals(Player.Messages.winner)) {
      System.out.println(this.playerRef.name + " has won!!");
    }
    }
  }
