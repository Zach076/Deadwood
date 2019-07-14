package core;

public class Role extends GameObject {
  public int type; // 0 is role 1 is extra
  public int level;
  public Player player = null;
  public int reToken;
  public String name;
  public String line;

  public Role(String name, int l, int t, String line) {
    this.name = name;
    this.line = line;
    reToken = 0;
    type = t;
    level = l;
  }

  public void rehearse() {
    reToken++;
  }

  public void takeRole(Player p) {
    player = p;
    p.giveRole(this);
  }

  public void removePlayer() {
    player.removeRole();
    player = null;
  }
}
