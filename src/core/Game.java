package core;

import java.util.*;

public class Game extends GameObject implements GameObjectListener{
  public Board board;
  public Player players[];
  public int day = 1;
  public int gameLength;
  private int playerTurn = 0; // index of current player
  public boolean gameIsActive = false;

  public Game(int gamelength, Player players[], LinkedList<Room> rooms, LinkedList<Card> cards) {
    this.gameLength = gamelength;
    this.players = players;
    this.board = new Board(rooms, cards);
    this.board.addLisener(this);
    Room r = this.board.findRoom("trailer");
    this.board.resetBoard();
    for (Player player : players) {
      r.playerEntersRoom(player);
    }
  }

  private void startDay() {
    this.onObjectChange(Messages.dayStart);
    this.board.resetBoard();
    Room r = this.board.findRoom("trailer");
    for (Player player : players) {
      player.removeRole();
      r.playerEntersRoom(player);
    }
  }

  private void endDay(){
    day++;
    if(day > gameLength){
      endGame();
    }else{
      startDay();
    }
  }

  private void endGame() {
    this.onObjectChange(Messages.gameEnded);
    Player p = calcWinner();
    p.isWinner();
    this.gameIsActive = false;
  }

  public Player calcWinner() {
    Player bestPlayer = this.players[0];
    for (Player player : players) {
      if (player.calcScore() > bestPlayer.calcScore()) {
        bestPlayer = player;
      }
    }
    return bestPlayer;
  }

  public void startGame() {
    this.gameIsActive = true;
    this.onObjectChange(Messages.dayStart);
    this.players[this.playerTurn].giveTurn();
    this.onObjectChange(Messages.turnChange);
  }

  public Player getCurrentPlayer() {
    return this.players[this.playerTurn];
  }

  public void advanceTurn() {
    this.players[this.playerTurn].endTurn();
    this.playerTurn = (this.playerTurn + 1) % this.players.length;
    this.players[this.playerTurn].giveTurn();
    this.onObjectChange(Messages.turnChange);
  }


  public void call(String message){
    if(message.equals(Board.Messages.lastScene)){
      this.endDay();
    }
  }


  public static class Messages {
    public static final String gameEnded = "GameEnded";
    public static final String dayStart = "DayStart";
    public static final String turnChange = "TurnChange";
  }
}
