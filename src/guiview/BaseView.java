package guiview;

import javax.swing.*;

public abstract class BaseView {

  JLayeredPane bPane;

  BaseView(JLayeredPane bPane) {
    this.bPane = bPane;
  }

  abstract void render();
}