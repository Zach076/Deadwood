package view;

import core.*;

public class SceneView extends RoomView{
  private Scene sceneRef;
  SceneView(Scene s){
    super((Room) s);
    this.sceneRef = s;
  }


  public void call(String message){
    super.call(message);
      if (message.equals(Scene.Messages.rollFail)) {
          System.out.println("Role failed");
      } else if (message.equals(Scene.Messages.rollSucc)){
          System.out.println("Role completed!");
      } else if(message.equals(Scene.Messages.sceneWrap)){
          System.out.println("Scene Wrapped!");
      }
  }

  public static void showScene(Scene s){
      System.out.println("Name: " + String.valueOf(s.name));
      System.out.println("Card Budget: " + String.valueOf(s.card.budget));
      System.out.println("Shot Markers: " + String.valueOf(s.shotMarkers) + "\\" + String.valueOf(s.usedShotMarkers));
      SceneView.showRoles(s);
  }


  public void summary(){
    super.summary();
    System.out.println("Card Budget: " + String.valueOf(this.sceneRef.card.budget));
    System.out.println("Shot Markers: " + String.valueOf(this.sceneRef.shotMarkers) + "\\" + String.valueOf(this.sceneRef.usedShotMarkers));
    SceneView.showRoles(this.sceneRef);
  }

  
  public static void showRoles(Scene s){
    if(s.cardFlipped){
      System.out.println("Lead Roles:");
      for (int i = 0; i < s.card.roles.size(); i++) {
        if (s.card.roles.get(i).player == null) {
          RoleView.displayRole(s.card.roles.get(i));
        }
      }
      System.out.println();
    }
      System.out.println("Extra roles:");
      for (int i = 0; i < s.extras.size(); i++) {
        if (s.extras.get(i).player == null) {
          RoleView.displayRole(s.extras.get(i));
        }
      }
      System.out.println();
  }

}