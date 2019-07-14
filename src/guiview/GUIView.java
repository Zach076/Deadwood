package guiview;

import core.Game;
import core.RoomType;
import core.Scene;

import java.util.LinkedList;
import javax.swing.*;

/**
 * Created by Caleb on 6/3/18.
 */
public class GUIView extends JFrame {

  private Game gameRef;
  private PlayerView[] playersViews;
  private LinkedList<RoomView> roomViews = new LinkedList<RoomView>();
  private LinkedList<SceneView> sceneViews = new LinkedList<SceneView>();
  private GameView gameView;
  private GameMenu b;

  JLayeredPane bPane;
  JLabel boardlabel;

  public GUIView(Game game) {
    super("Deadwood");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    bPane = getLayeredPane();
    boardlabel = new JLabel();
    ImageIcon icon = new ImageIcon("board.jpg");
    boardlabel.setIcon(icon);
    boardlabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

    // Add the board to the lowest layer
    bPane.add(boardlabel, new Integer(0));

    // Set the size of the GUI
    setSize(icon.getIconWidth() + 200, icon.getIconHeight());

    b = new GameMenu(bPane,icon.getIconWidth(), game );

    this.gameRef = game;
    this.gameView = new GameView(game, bPane);
    this.playersViews = new PlayerView[this.gameRef.players.length];
    for (int i = 0; i < this.gameRef.players.length; i++) {
      this.playersViews[i] = new PlayerView(this.gameRef.players[i], bPane);
    }

    for (int i = 0; i < this.gameRef.board.rooms.size(); i++) {
      if (this.gameRef.board.rooms.get(i).type == RoomType.Scene) {
        this.sceneViews.add(new SceneView(((Scene) this.gameRef.board.rooms.get(i)), bPane));
      } else {
        this.roomViews.add(new RoomView(this.gameRef.board.rooms.get(i), bPane));
      }
      this.setVisible(true);
    }

  }
}
