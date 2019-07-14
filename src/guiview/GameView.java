package guiview;

import core.*;
import javax.swing.*;


public class GameView extends BaseView implements GameObjectListener {
  private Game gameRef;

  public GameView(Game r, JLayeredPane bPane) {
    super(bPane);
    this.gameRef = r;
    this.gameRef.addLisener(this);
  }

  public void call(String message) {
    if(message.equals(Game.Messages.gameEnded)){
      Player p = gameRef.calcWinner();
      JOptionPane.showMessageDialog(null, p.name + " has won!");
    }
  }

  public void render(){

  }
}