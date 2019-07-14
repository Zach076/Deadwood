package guiview;

import core.*;

import javax.swing.*;

public class RoomView extends BaseView implements GameObjectListener {
  private Room roomRef;

  public RoomView(Room r, JLayeredPane bPane) {
    super(bPane);
    this.roomRef = r;
    this.roomRef.addPriorityLisener(this);
  }
  public void call(String message) {
  }

  public void render(){

  }
}
