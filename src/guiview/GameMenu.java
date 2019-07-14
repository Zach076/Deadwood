package guiview;

import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import core.*;

/**
 * Created by Caleb on 6/3/18.
 */
public class GameMenu extends BaseView implements GameObjectListener {

    private Game gameRef;

    JLabel mLabel = new JLabel("MENU");

    JLabel mRehearse = new JLabel("");
    JLabel mDay = new JLabel("Day:");
    JLabel mPlayer = new JLabel("Player:");
    JLabel mMoney = new JLabel("Money:");
    JLabel mFame = new JLabel("Fame:");

    JComboBox roleList = new JComboBox();
    JComboBox roomList = new JComboBox();
    JComboBox rankList = new JComboBox();

    // JButtons
    JButton bAct = new JButton("ACT");
    JButton bRehearse = new JButton("REHEARSE");
    JButton bMove = new JButton("MOVE");
    JButton bTake = new JButton("TAKE");
    JButton bUpgrade = new JButton("UPGRADE");
    JButton bEnd = new JButton("END");
    JButton bSelect = new JButton("SELECT");

    boolean ranking = false;
    boolean moving = false;
    boolean taking = false;
    String selectedRole = "";
    int offset;

    public GameMenu(JLayeredPane bPane, int offset, Game gameRef){
        super(bPane);
        this.offset = offset;
        this.gameRef = gameRef;
        this.gameRef.addLisener(this);
        this.gameRef.board.addLisener(this);

        mLabel.setBounds(offset + 40, 0, 100, 20);
        bPane.add(mLabel, new Integer(2));

        mPlayer.setBounds(offset + 10, 690, 100, 20);
        bPane.add(mPlayer, new Integer(2));

        mDay.setBounds(offset + 10, 660, 100, 20);
        bPane.add(mDay, new Integer(2));

        mMoney.setBounds(offset + 10, 720, 100, 20);
        bPane.add(mMoney, new Integer(2));

        mRehearse.setBounds(offset + 10, 630, 100, 20);
        bPane.add(mRehearse, new Integer(2));

        mFame.setBounds(offset + 10, 750, 100, 20);
        bPane.add(mFame, new Integer(2));

        bAct.setBackground(Color.white);
        bAct.setBounds(offset + 10, 30, 100, 20);
        bAct.addActionListener(new boardMouseListener());

        bRehearse.setBackground(Color.white);
        bRehearse.setBounds(offset + 10, 60, 100, 20);
        bRehearse.addActionListener(new boardMouseListener());

        bMove.setBackground(Color.white);
        bMove.setBounds(offset + 10, 90, 100, 20);
        bMove.addActionListener(new boardMouseListener());

        bTake.setBackground(Color.white);
        bTake.setBounds(offset + 10, 120, 100, 20);
        bTake.addActionListener(new boardMouseListener());

        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(offset + 10, 150, 100, 20);
        bUpgrade.addActionListener(new boardMouseListener());

        bEnd.setBackground(Color.white);
        bEnd.setBounds(offset + 10, 180, 100, 20);
        bEnd.addActionListener(new boardMouseListener());

        roleList.setBounds(offset + 10, 210, 100, 20);
        roleList.addActionListener(new roleMenuListener());

        roomList.setBounds(offset + 10, 210, 100, 20);
        roomList.addActionListener(new roleMenuListener());

        rankList.setBounds(offset + 10, 210, 100, 20);
        rankList.addActionListener(new roleMenuListener());

        bSelect.setBackground(Color.white);
        bSelect.setBounds(offset + 10, 240, 100, 20);
        bSelect.addActionListener(new boardMouseListener());

        bPane.add(bAct, new Integer(2));
        bPane.add(bRehearse, new Integer(2));
        bPane.add(bMove, new Integer(2));
        bPane.add(bTake, new Integer(2));
        bPane.add(bUpgrade, new Integer(2));
        bPane.add(bEnd, new Integer(2));

        render();

    }

    public void reset(){
        bPane.remove(bAct);
        bPane.remove(bRehearse);
        bPane.remove(bMove);
        bPane.remove(bTake);
        bPane.remove(bUpgrade);
        bPane.remove(roleList);
        bPane.remove(bSelect);
        bPane.remove(roomList);
        bPane.remove(rankList);
        bPane.remove(bSelect);
    }

    public void render(){
        reset();
        if(!gameRef.getCurrentPlayer().hasMoved
           && gameRef.getCurrentPlayer().currentRole != null) {
            bPane.add(bAct);
        }

        if(!gameRef.getCurrentPlayer().hasMoved
           && gameRef.getCurrentPlayer().currentRole != null) {
            bPane.add(bRehearse);
        }

        if(!gameRef.getCurrentPlayer().hasMoved
            && gameRef.getCurrentPlayer().currentRole == null) {
            bPane.add(bMove);
        }

        if(gameRef.getCurrentPlayer().getCurrentRoom().type == RoomType.Scene) {
          Scene currentScene = (Scene) gameRef.getCurrentPlayer().getCurrentRoom();
          if(!currentScene.wrapped && gameRef.getCurrentPlayer().currentRole == null) {
              bPane.add(bTake);
          }
        }

        if(gameRef.getCurrentPlayer().getCurrentRoom().type == RoomType.CastingOffice) {
            bPane.add(bUpgrade);
        }

        if(taking) {
            bPane.add(roleList, new Integer(2));
            bPane.add(bSelect, new Integer(2));
            Scene currentScene = (Scene) gameRef.getCurrentPlayer().getCurrentRoom();
            LinkedList<Role> roles = SceneView.getRoles(currentScene);
            LinkedList<String> rolestring = new LinkedList<String>();
            for( Role r: roles){
              if(r.level <= gameRef.getCurrentPlayer().level){
                rolestring.add(r.name);
              }
            }
            if(rolestring.size() > 0){
                selectedRole = rolestring.get(0);
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel( rolestring.toArray() );
            roleList.setModel( model );
        }

        if(moving) {
          bPane.add(roomList, new Integer(2));
          bPane.add(bSelect, new Integer(2));
            LinkedList<String> rooms = gameRef.getCurrentPlayer().getCurrentRoom().neighbors;
            if(rooms.size() > 0) {
                selectedRole = rooms.get(0);
            }
          DefaultComboBoxModel model = new DefaultComboBoxModel( rooms.toArray());
            roomList.setModel( model );
        }

        if(ranking) {
          bPane.add(rankList, new Integer(2));
          bPane.add(bSelect, new Integer(2));
          LinkedList<String> ranks = getRanks();
            if(ranks.size() > 0) {
                selectedRole = ranks.get(0);
            }
          DefaultComboBoxModel model = new DefaultComboBoxModel( ranks.toArray() );
            rankList.setModel( model );
        }

        if(gameRef.getCurrentPlayer().currentRole != null){
            mRehearse.setText("Rehearse: " + Integer.toString(gameRef.getCurrentPlayer().currentRole.reToken));
        }else{
            mRehearse.setText("");
        }


      mDay.setText("Day: " + Integer.toString(gameRef.day));
      mPlayer.setText("Player: " + Integer.toString(gameRef.getCurrentPlayer().playerNum + 1));
      mMoney.setText("Money: " + Integer.toString(gameRef.getCurrentPlayer().money));
      mFame.setText("Fame: " + Integer.toString(gameRef.getCurrentPlayer().fame));
      bPane.repaint();
    }


    public void call(String message) {
        if (message.equals(Game.Messages.dayStart)) {
            System.out.println("Day " + gameRef.day + " has started");
        } else if (message.equals(Game.Messages.gameEnded)) {
            System.out.println("Game is over");
        }
        this.render();
    }


    class roleMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            selectedRole = (String)cb.getSelectedItem();
        }
    }

    class boardMouseListener implements ActionListener {

        // Code for the different button clicks
        @Override
        public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bAct) {
            Scene currentScene = (Scene) gameRef.getCurrentPlayer().getCurrentRoom();
            currentScene.act(gameRef.getCurrentPlayer().currentRole);
            gameRef.advanceTurn();
        } else if (e.getSource() == bRehearse) {
            gameRef.getCurrentPlayer().currentRole.rehearse();
            gameRef.advanceTurn();
        } else if (e.getSource() == bMove) {
            taking = false;
            moving = true;
            ranking = false;
            render();
        }else if (e.getSource() == bTake) {
            taking = true;
            moving = false;
            ranking = false;
            render();
        }else if (e.getSource() == bUpgrade) {
            taking = false;
            moving = false;
            ranking = true;
            render();
        }else if (e.getSource() == bEnd) {
          gameRef.advanceTurn();
          taking = false;
          moving = false;
          ranking = false;
          render();
          selectedRole = "";
        }else if (e.getSource() == bSelect) {
          if(taking){
            if(!selectedRole.equals("")) {

              Scene currentScene = (Scene) gameRef.getCurrentPlayer().getCurrentRoom();

              Role r = null;
              for (int i = 0; i < currentScene.card.roles.size(); i++) {
                if (GameMenu.compare(currentScene.card.roles.get(i).name, selectedRole)) {
                  r = currentScene.card.roles.get(i);
                }
              }
              for (int i = 0; i < currentScene.extras.size(); i++) {
                if (GameMenu.compare(currentScene.extras.get(i).name, selectedRole)) {
                  r = currentScene.extras.get(i);
                }
              }

              r.takeRole(gameRef.getCurrentPlayer());

              }
          } if(moving) {
            gameRef.board.movePlayer(gameRef.getCurrentPlayer(), selectedRole);
          } if (ranking) {
            String rank = selectedRole.substring(0,1);
            String payment = selectedRole.substring(4,5);
            switch(rank) {
              case "2":
                if(payment.equals("$")) {
                  gameRef.getCurrentPlayer().upgrade(2, 0, 4);
                }else {
                  gameRef.getCurrentPlayer().upgrade(2, 5, 0);
                }
                break;
              case "3":
                if(payment.equals("$")) {
                  gameRef.getCurrentPlayer().upgrade(3, 0, 10);
                }else {
                  gameRef.getCurrentPlayer().upgrade(3, 10, 0);
                }
                break;
              case "4":
                if(payment.equals("$")) {
                  gameRef.getCurrentPlayer().upgrade(4, 0, 18);
                }else {
                  gameRef.getCurrentPlayer().upgrade(4, 15, 0);
                }
                break;
              case "5":
                if(payment.equals("$")) {
                  gameRef.getCurrentPlayer().upgrade(5, 0, 28);
                }else {
                  gameRef.getCurrentPlayer().upgrade(5, 20, 0);
                }
                break;
              case "6":
                if(payment.equals("$")) {
                  gameRef.getCurrentPlayer().upgrade(6, 0, 40);
                }else {
                  gameRef.getCurrentPlayer().upgrade(6, 25, 0);
                }
                break;
            }
            gameRef.advanceTurn();
          }
          taking = false;
          moving = false;
          ranking = false;
          render();
          selectedRole = "";
        }
      }

    }

    public static boolean compare(String a, String b){
      return a.toLowerCase().equals(b.toLowerCase());
    }

    public LinkedList<String> getRanks() {
      LinkedList<String> output = new LinkedList<String>();
      for(int i = 2; i <= 6; i++) {
        if(gameRef.getCurrentPlayer().level < i) {
          if(gameRef.getCurrentPlayer().money >= GameMenu.calcRankCostM(i)) {
            output.add(Integer.toString(i) + " : $" + Integer.toString(GameMenu.calcRankCostM(i)));
          }
          if(gameRef.getCurrentPlayer().fame >= GameMenu.calcRankCostF(i)) {
            output.add(Integer.toString(i) + " : F" + Integer.toString(GameMenu.calcRankCostF(i)));
          }
        }
      }
      return output;
    }

    public static int calcRankCostM(int i) {
      switch(i) {
        case 2:
          return 4;
        case 3:
          return 10;
        case 4:
          return 18;
        case 5:
          return 28;
        case 6:
          return 40;
      }
      return 0;
    }

    public static int calcRankCostF(int i) {
      switch(i) {
        case 2:
          return 5;
        case 3:
          return 10;
        case 4:
          return 15;
        case 5:
          return 20;
        case 6:
          return 25;
      }
      return 0;
    }
}
