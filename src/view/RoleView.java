package view;
import core.*;

public class RoleView{
  public static void displayRole(Role r){
    System.out.print(r.name + " (" + r.level + ")   ");
    if(r.player != null){
      System.out.print("Actor: " + r.player.name);
    }
    System.out.print("\n");
  }
}