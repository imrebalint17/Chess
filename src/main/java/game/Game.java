package game;
import gameFrame.GameFrame;

public class Game {

    public Game() {
        Table table = new Table();
        GameFrame gameFrame = new GameFrame(table);
        gameFrame.setVisible(true);
    }
}
