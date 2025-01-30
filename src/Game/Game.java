package Game;
import GameFrame.GameFrame;
import Game.Table;

public class Game {

    public Game() {
        Table table = new Table();
        GameFrame gameFrame = new GameFrame(table);
        gameFrame.setVisible(true);
    }
}
