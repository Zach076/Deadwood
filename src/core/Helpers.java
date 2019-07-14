package core;

import java.util.*;

public class Helpers {
  private Helpers() {
  }

  public static boolean adminmode = false;

  public static int randNum(int min, int max) {
    if(adminmode){
      return max;
    }else{
    Random rand = new Random();
    return rand.nextInt(max - min + 1) + min;
    }
  }

}