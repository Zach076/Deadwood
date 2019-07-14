package core;

import java.util.*;

public class Card extends GameObject {

  public int budget;
  public String image;
  public String name;
  public int number;
  public String set;
  public LinkedList<Role> roles;

  public Card(int b, String i, String n, int num, String s, LinkedList<Role> rolez) {
    budget = b;
    image = i;
    name = n;
    number = num;
    set = s;
    roles = rolez;
  }

  public void addRole(String name, int l, String line) {
    roles.add(new Role(name, l, 0, line));
  }
}
