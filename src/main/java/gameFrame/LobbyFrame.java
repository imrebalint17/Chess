package gameFrame;

import engine.MultiplayerSession;

import javax.swing.*;

public class LobbyFrame extends JFrame {
    public LobbyFrame() {
        setTitle("Lobby");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Lobby");
        label.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label);
        MultiplayerSession session = MultiplayerSession.getInstance();
        JButton createGameButton = new JButton("Csatlakozás a várólisához");
        JButton joinGameButton = new JButton("Csatlakozás egy játékhoz");
        JButton exitButton = new JButton("Kilépés");
        if (session.getUserId() ==-1) {
            createGameButton.setEnabled(false);
            joinGameButton.setEnabled(false);
        }
        panel.add(createGameButton);
        panel.add(joinGameButton);
        panel.add(exitButton);

        add(panel);
        createGameButton.addActionListener(e -> {

        })
        exitButton.addActionListener(e -> {
            session.setSessionState(MultiplayerSession.SessionState.DISCONNECTED);
            dispose();
        });

    }
}
