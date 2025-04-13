package gameFrame;

import javax.swing.*;
import java.awt.*;

public class NewGameDialog extends JDialog {
    GameFrame frame;
    JPanel panel = new JPanel();
    public NewGameDialog(GameFrame frame) {
        this.frame = frame;
        this.setTitle("Új játék");
        this.setSize(800, 150);
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
        panel.add(engineblack);
        panel.add(enginewhite);
        panel.add(player);
        panel.add(online);
        player.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.NO_ENGINE,false);
            dispose();
        });
        enginewhite.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.WHITE,false);
            dispose();
        });
        engineblack.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.BLACK,false);
            dispose();
        });
        online.addActionListener(e -> {
            frame.newtable(GameFrame.EngineState.BLACK,true);
            dispose();
        });

    }
}
