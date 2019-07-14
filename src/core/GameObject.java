package core;

import java.util.*;
import java.util.concurrent.Callable;

public class GameObject {

  public Area area;

  private LinkedList<GameObjectListener> listeners = new LinkedList<GameObjectListener>();

  public void addLisener(GameObjectListener func) {
    // adds a function that will be called everytime the object changes.
    this.listeners.addLast(func);
  }

  public void addPriorityLisener(GameObjectListener func) {
    // adds a function that will be called everytime the object changes.
    this.listeners.addFirst(func);
  }


  protected void onObjectChange(String message) {
    for (GameObjectListener temp : this.listeners) {
      try {
        temp.call(message);
      } catch (Exception e) {
      }
    }
  }

}
