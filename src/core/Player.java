package core;

public class Player extends GameObject {
  public boolean hasMoved = false;
  public int playerNum;
  public int money;
  public int fame;
  private Room currentRoom;
  public Role currentRole = null;
  public int room;
  public int level = 1;
  private boolean isActivePlayer = false;

  public String name;

  public Room getCurrentRoom(){
    return currentRoom;
  }

  public void setCurrentRoom(Room r){
    this.currentRoom = r;
    this.onObjectChange(Messages.roomChange);
  }

  public void giveRole(Role r) {
    this.currentRole = r;
    this.onObjectChange(Messages.gainedRole);
  }

  public void removeRole() {
    this.currentRole = null;
  }

  public void giveTurn() {
    this.isActivePlayer = true;
    this.onObjectChange(Messages.startTurn);
  }

  public void endTurn() {
    this.isActivePlayer = false;
    this.hasMoved = false;
    this.onObjectChange(Messages.endTurn);
  }

  public Player(String name, int num) {
    this.name = name;
    this.playerNum = num;
  }

  public void giveReward(int money, int fame) {
    this.money = this.money + money;
    this.fame = this.fame + fame;
    this.onObjectChange(Messages.resourceChange);
  }

  public int calcScore() {
    return this.fame + this.money + (5 * this.level);
  }

  public void upgrade(int level, int costFame, int costMoney) {
    this.level = level;
    this.giveReward(-costMoney, -costFame);
    this.onObjectChange(Messages.upgrade);
  }

  public void isWinner() {
    this.onObjectChange(Messages.winner);
  }

  public static class Messages {
    public static final String startTurn = "STARTEDTURN";
    public static final String upgrade = "UPGRADE";
    public static final String resourceChange = "ResourceChange";
    public static final String endTurn = "ENDEDTURN";
    public static final String gainedRole = "GAINEDROLE";
    public static final String winner = "IWON";
    public static final String roomChange = "ROOMCHANGE";
  }

}
