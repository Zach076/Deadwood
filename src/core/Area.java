package core;

public class Area {
  public int x;
  public int y;
  public int h;
  public int w;

  public Area add(Area a){
    Area n = new Area();
    n.x = this.x + a.x;
    n.y = this.y + a.y;
    n.h = this.w + a.w;
    n.w = this.w + a.w;
    return n;
  }
}
