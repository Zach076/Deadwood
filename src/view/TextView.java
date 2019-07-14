package view;

import java.util.*;
import java.util.Scanner;
import core.*;


public class TextView {
  private Game gameRef;
  private PlayerView[] playersViews;
  private LinkedList<RoomView> roomViews = new LinkedList<RoomView>();
  private LinkedList<SceneView> sceneViews = new LinkedList<SceneView>();
  private TextGameView textGameView;

  public TextView(Game game) {
    this.gameRef = game;
    this.textGameView = new TextGameView(game);
    this.playersViews = new PlayerView[this.gameRef.players.length];
    for (int i = 0; i < this.gameRef.players.length; i++) {
      this.playersViews[i] = new PlayerView(this.gameRef.players[i]);
    }

    for (int i = 0; i < this.gameRef.board.rooms.size(); i++) {
      if(this.gameRef.board.rooms.get(i).type == RoomType.Scene){
        this.sceneViews.add(new SceneView(((Scene) this.gameRef.board.rooms.get(i))));
      }else{
        this.roomViews.add(new RoomView(this.gameRef.board.rooms.get(i)));
      }

    }
  }

  public void startListener() {
    Scanner sc = new Scanner(System.in);
    boolean inputValid = false;

    while (gameRef.gameIsActive) {
      String inputString = sc.nextLine();
      Scene currentScene;
      inputValid = false;
      switch (inputString) {
      case "help":
        System.out.println("Valid Input includes:");
        System.out.println("help     :   displays valid input");
        System.out.println("move     :   displays available rooms and prompts for which room you would like to move to");
        System.out.println("take     :   displays available roles and prompts for which role you would like to take");
        System.out.println("upgrade  :   displays available ranks and prompts for which rank you would like");
        System.out.println("act      :   acts your current role, this ends you turn");
        System.out.println("rehearse :   rehearses your current role, this ends your turn");
        System.out.println("end      :   ends your turn");
        System.out.println("board    :   Shows the entire board");
        System.out.println("players  :   Shows details about all players");
        break;

      case "move":
        if(!gameRef.getCurrentPlayer().hasMoved){
          if(gameRef.getCurrentPlayer().currentRole == null) {
            for (int i = 0; i < gameRef.getCurrentPlayer().getCurrentRoom().neighbors.size(); i++) {
              System.out.print(gameRef.getCurrentPlayer().getCurrentRoom().neighbors.get(i) + "   ");
            }
            System.out.println("");

            System.out.println("Which room would you like to move to?");
            inputString = sc.nextLine();

            for (int i = 0; i < gameRef.getCurrentPlayer().getCurrentRoom().neighbors.size(); i++) {
              if (TextView.compare(gameRef.getCurrentPlayer().getCurrentRoom().neighbors.get(i), inputString)) {
                inputValid = true;
              }
            }

            if (inputValid) {
              gameRef.board.movePlayer(gameRef.getCurrentPlayer(), inputString);
              inputValid = false;
            }
          }else{
            System.out.println("Cannot move while on a role.");
          }
        } else {
          System.out.println("Cannot move twice per turn.");
        }
        break;

      case "take":
        if (gameRef.getCurrentPlayer().getCurrentRoom().type != RoomType.Scene) {
          System.out.println("Must be in a Scene Room to take a role.");
        } else {
          currentScene = (Scene) gameRef.getCurrentPlayer().getCurrentRoom();
          if(!currentScene.wrapped) {
            SceneView.showScene(currentScene);
            inputString = sc.nextLine();

            Role r = null;
            for (int i = 0; i < currentScene.card.roles.size(); i++) {
              if (TextView.compare(currentScene.card.roles.get(i).name, inputString)) {
                r = currentScene.card.roles.get(i);
              }
            }
            for (int i = 0; i < currentScene.extras.size(); i++) {
              if (TextView.compare(currentScene.extras.get(i).name, inputString)) {
                r = currentScene.extras.get(i);
              }
            }
            if(r != null){
              if(r.player == null){
                if(r.level <= gameRef.getCurrentPlayer().level){
                  r.takeRole(gameRef.getCurrentPlayer());
                  inputValid = false;
                  gameRef.advanceTurn();
                }else{
                  System.out.println("Role level is too high");
                }
              }else{
                System.out.println("Role not available");
              }
            }else{
              System.out.println("Not a valid role");
            }

          } else {
            System.out.println("Scene has wrapped, no roles available");
          }
        }
        break;

      case "act":
        if(!gameRef.getCurrentPlayer().hasMoved){
          if(gameRef.getCurrentPlayer().currentRole != null){
            if (gameRef.getCurrentPlayer().getCurrentRoom().type != RoomType.Scene) {
              System.out.println("Must take a role to act.");
            } else {
              currentScene = (Scene) gameRef.getCurrentPlayer().getCurrentRoom();
              currentScene.act(gameRef.getCurrentPlayer().currentRole);
              gameRef.advanceTurn();
            }
          }else{
            System.out.println("Player has no role.");
          }

        } else {
          System.out.println("Cannot act in the same turn you move.");
        } break;

      case "upgrade": // next input should be valid rank, call rank function
        if (gameRef.getCurrentPlayer().getCurrentRoom().type != RoomType.CastingOffice) {
          System.out.println("Must be in the Casting Office to upgrade.");
        } else {
          System.out.println("Rank 2 costs $4  or 5  Fame");
          System.out.println("Rank 3 costs $10 or 10 Fame");
          System.out.println("Rank 4 costs $18 or 15 Fame");
          System.out.println("Rank 5 costs $28 or 20 Fame");
          System.out.println("Rank 6 costs $40 or 25 Fame");
          System.out.println("What rank would you like to purchase? (type just the rank number)");
          inputString = sc.nextLine();

          switch (inputString) {
          case "2":
            upgrade(2, 4 ,5);
            break;

          case "3":
            upgrade(3, 10,10);

            break;

          case "4":
            upgrade(4, 18,15);
            break;

          case "5":
            upgrade(5, 28,20);
            break;

          case "6":
            upgrade(6, 40,25);
            break;

          default:
            if (inputString.length() > 0) {
              System.out.println("Input invalid.");
            }
          }
        }
        break;

      case "rehearse":
        if(!gameRef.getCurrentPlayer().hasMoved){
          gameRef.getCurrentPlayer().currentRole.rehearse();
          gameRef.advanceTurn();
        } else {
          System.out.println("Cannot rehearse in the same turn you move.");
        }break;

      case "end":
        gameRef.advanceTurn();
        break;

      case "players":
        for( PlayerView p : this.playersViews){
          p.playerSummary();
        }
      break;

      case "board":
        for( RoomView p : this.roomViews){
          p.summary();
        }
        for( SceneView p : this.sceneViews){
          p.summary();
        }

      break;

      default:
        if (inputString.length() > 0) {
          System.out.println("Input not recognized. Type 'help' to view valid input.");
        }
      }
    }
  }

  private void upgrade(int level, int dollars, int fame){

    System.out.println("Rank " + String.valueOf(level) +" costs $" + String.valueOf(dollars) +"  or "+ String.valueOf(fame)+"  Fame, would you like to pay in money or fame");

    Scanner sc = new Scanner(System.in);
    String inputString = sc.nextLine();
    if (TextView.compare("money", inputString)) {
      if (gameRef.getCurrentPlayer().money >= dollars) {
        gameRef.getCurrentPlayer().upgrade(level, 0, dollars);
        gameRef.advanceTurn();
      }
    } else if (TextView.compare("fame", inputString)) {
      if (gameRef.getCurrentPlayer().fame >= fame) {
        gameRef.getCurrentPlayer().upgrade(level, fame, 0);
        gameRef.advanceTurn();
      }
    } else {
      System.out.println("Input invalid.");
    }
  }




  public static boolean compare(String a, String b){
    return a.toLowerCase().equals(b.toLowerCase());
  }
}
