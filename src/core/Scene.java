package core;

import java.util.*;

public class Scene extends Room {

  public boolean wrapped = false;
  public int shotMarkers;
  public LinkedList<Area> shotMarkersLocations;
  public int usedShotMarkers = 0;
  public boolean cardFlipped = false;
  public Card card;
  public LinkedList<Role> extras;

  public Scene(LinkedList<Area> shots, LinkedList<Role> extras, String name, LinkedList<String> neighbors) {
    super(RoomType.Scene, name, neighbors);
    shotMarkers = shots.size();
    shotMarkersLocations = shots;
    cardFlipped = false;
    this.extras = extras;
  }

  public void playerEntersRoom(Player p) {
    super.playerEntersRoom(p);
    this.flipCard();
  }

  public void flipCard() {
    cardFlipped = true;
    this.onObjectChange(Messages.flip);
  }

  public void takeRole(Player player, Role role) {
    role.takeRole(player);
    this.onObjectChange(Messages.takeRole);
  }

  public void act(Role r) {
    int roll = Helpers.randNum(1, 6);
    if (r.type == 0) {
      if (roll + r.reToken >= card.budget) {
        usedShotMarkers++;
        this.onObjectChange(Messages.rollSucc);
        r.player.giveReward(0, 2);
      } else {
        this.onObjectChange(Messages.rollFail);
      }
    } else {
      if (roll + r.reToken >= card.budget) {
        usedShotMarkers++;
        r.player.giveReward(1, 1);
        this.onObjectChange(Messages.rollSucc);
      } else {
        r.player.giveReward(1, 0);
        this.onObjectChange(Messages.rollFail);
      }
    }
    if (usedShotMarkers == shotMarkers) {
      calcReward();
      this.onObjectChange(Messages.sceneWrap);
    }
  }

  private void calcReward() {

    for (int i = 0; i < extras.size(); i++) {
      if (extras.get(i).player != null) {
        extras.get(i).player.giveReward(extras.get(i).level, 0);
        extras.get(i).removePlayer();
      }
    }
    int[] rewards = new int[card.budget];

    for (int i = 0; i < card.budget; i++) {
      rewards[i] = Helpers.randNum(1, 6);
    }
    Arrays.sort(rewards);
    for (int i = 0; i < card.budget; i++) {
      if (card.roles.get(card.roles.size() - (i % card.roles.size()) - 1).player != null) {
        card.roles.get(card.roles.size() - (i % card.roles.size()) - 1).player.giveReward(rewards[card.budget - i - 1],
            0);
      }
    }
    for (int i = 0; i < card.roles.size(); i++) {
      if (card.roles.get(i).player != null) {
        card.roles.get(i).removePlayer();
      }
    }
    this.wrapped = true;
  }

  public void resetRoom(Card c) {
    super.resetRoom();
    card = c;
    usedShotMarkers = 0;
    cardFlipped = false;
    wrapped = false;
    this.onObjectChange(Messages.flip);
  }

  public static class Messages {
    public static final String flip = "CARD FLIPPED";
    public static final String takeRole = "TOOK ROLE";
    public static final String rollSucc = "ROLL SUCCESSFUL";
    public static final String rollFail = "ROLL FAILED";
    public static final String sceneWrap = "SCENE WRAPPED";
  }
}
