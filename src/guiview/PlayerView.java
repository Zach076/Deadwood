package guiview;

import javax.swing.*;
import core.*;

public class PlayerView extends BaseView implements GameObjectListener {
  private Player playerRef;
  private JLabel playerlabel;
  public static final String[] playerColors = { "v", "b", "y", "c", "g", "o", "p", "r" };

  public PlayerView(Player p, JLayeredPane bPane) {
    super(bPane);
    this.playerRef = p;
    this.playerRef.addLisener(this);
    playerlabel = new JLabel();
    ImageIcon pIcon = new ImageIcon(this.getImage());
    playerlabel.setIcon(pIcon);
    bPane.add(playerlabel, new Integer(3));
    Area a = playerRef.getCurrentRoom().area;
    playerlabel.setBounds(0,0,pIcon.getIconWidth(),pIcon.getIconHeight());
    this.render();
  }

  public void call(String message) {
    this.render();
  }

  public void render() {
    ImageIcon pIcon = new ImageIcon(this.getImage());
    playerlabel.setIcon(pIcon);
    if(this.playerRef.currentRole == null){
      movePlayer(this.playerRef.getCurrentRoom().area);
    }else{
      if(this.playerRef.currentRole.type == 1){
        movePlayer(this.playerRef.currentRole.area);
      }else{
        movePlayer(this.playerRef.currentRole.area.add(this.playerRef.getCurrentRoom().area));
      }
    }
  }

  private String getImage() {
    return "./dice/" +  playerColors[this.playerRef.playerNum] + Integer.toString(this.playerRef.level) + ".png";
  }

  public void movePlayer(Area a){
    playerlabel.setLocation(a.x,a.y);
  }
}
