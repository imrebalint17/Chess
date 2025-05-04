package gameFrame;

import javax.swing.*;
import java.awt.*;

public class NewGameDialog extends JDialog {
    GameFrame frame;
    JPanel panel = new JPanel();
    public NewGameDialog(GameFrame frame) {
        this.frame = frame;
        this.setTitle("Új játék");
        this.setSize(800, 400);
        init();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        panel.setVisible(true);
    }
    private void init(){
        panel.setLayout(new GridLayout(1,3));
        JButton enginewhite = new JButton("Új játék Engine ellen feketeként");
        JButton engineblack = new JButton("Új játék Engine ellen fehérként");
        JButton player = new JButton("Új játék Játékos ellen");
        JButton online = new JButton("Online játék indítása fehérként");
        JButton onlineb = new JButton("Online játék indítása feketeként");
        JButton multiplayer = new JButton("Online játék indítása");
        panel.add(engineblack);
        panel.add(enginewhite);
        panel.add(player);
        panel.add(online);
        panel.add(onlineb);
        panel.add(multiplayer);
        player.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.NO_ENGINE,0);
            dispose();
        });
        enginewhite.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.WHITE,0);
            dispose();
        });
        engineblack.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.BLACK,0);
            dispose();
        });
        onlineb.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.BLACK,1);
            dispose();
        });
        online.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.WHITE,1);
            dispose();
        });
        multiplayer.addActionListener(e -> {
            LobbyFrame lobbyFrame = new LobbyFrame();
            lobbyFrame.setVisible(true);
            //frame.newtable(GameFrame.EngineState.WHITE,2);
            dispose();
        });
    }
}
