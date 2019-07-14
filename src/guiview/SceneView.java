package guiview;

import core.*;

import javax.swing.*;
import java.util.LinkedList;

public class SceneView extends RoomView {
  private Scene sceneRef;
  JLabel cardlabel = new JLabel();
  public LinkedList<JLabel> shotMarkers = new LinkedList<JLabel>();
  SceneView(Scene s, JLayeredPane bPane) {
    super((Room) s, bPane);
    this.sceneRef = s;
    bPane.add(cardlabel, new Integer(1));
    for (Area shot : this.sceneRef.shotMarkersLocations){
      JLabel shotLabel = new JLabel();
      shotLabel.setBounds(shot.x, shot.y, shot.w, shot.h);
      shotMarkers.add(shotLabel);
      bPane.add(shotLabel, new Integer(2));
    }
    this.render();
  }

  public void call(String message) {
    super.call(message);
    this.render();
  }


  public void render() {
    super.render();


    if (this.sceneRef.cardFlipped && !this.sceneRef.wrapped) {
      ImageIcon cIcon = new ImageIcon("./cards/" + this.sceneRef.card.image);
      cardlabel.setIcon(cIcon);
      cardlabel.setBounds(this.sceneRef.area.x, this.sceneRef.area.y, cIcon.getIconWidth() + 2, cIcon.getIconHeight());
    } else if(this.sceneRef.wrapped){
      ImageIcon cIcon = new ImageIcon("");
      cardlabel.setIcon(cIcon);
      cardlabel.setBounds(this.sceneRef.area.x, this.sceneRef.area.y, cIcon.getIconWidth() + 2, cIcon.getIconHeight());
    } else{
      ImageIcon cIcon = new ImageIcon("./assets/cardback.png");
      cardlabel.setIcon(cIcon);
      cardlabel.setBounds(this.sceneRef.area.x, this.sceneRef.area.y, cIcon.getIconWidth() + 2, cIcon.getIconHeight());
    }

    int i = 0;
    for (JLabel s :shotMarkers){
      if(i < this.sceneRef.usedShotMarkers){
        ImageIcon pIcon = new ImageIcon("assets/shot.png");
        s.setIcon(pIcon);
      }else{
        ImageIcon pIcon = new ImageIcon("assets/unshot.png");
        s.setIcon(pIcon);
      }
      i++;
    }
  }

  public static LinkedList<Role> getRoles(Scene s){
    LinkedList<Role> output = new LinkedList<Role>();
    if(s.cardFlipped){
      for (int i = 0; i < s.card.roles.size(); i++) {
        if (s.card.roles.get(i).player == null) {
          output.add(s.card.roles.get(i));
        }
      }
    }
      for (int i = 0; i < s.extras.size(); i++) {
        if (s.extras.get(i).player == null) {
          output.add(s.extras.get(i));
        }
      }
      return output;
  }
}
