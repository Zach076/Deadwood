package view;
import core.*;

public class TextGameView implements GameObjectListener{
    private Game gameRef;

    public TextGameView(Game r) {
        this.gameRef = r;
        this.gameRef.addLisener(this);
    }

    public void call(String message) {
        if (message.equals(Game.Messages.dayStart)) {
            System.out.println("Day " + gameRef.day + " has started");
        }else if (message.equals(Game.Messages.gameEnded)){
            System.out.println("Game is over");
        }
    }
}