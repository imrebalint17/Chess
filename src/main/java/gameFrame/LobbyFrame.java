package gameFrame;

import engine.MultiplayerSession;

import javax.swing.*;

public class LobbyFrame extends JDialog {
    public LobbyFrame() {
        setTitle("Lobby");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setModal(true); // Makes the dialog modal

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Lobby");
        label.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label);

        MultiplayerSession session = MultiplayerSession.getInstance();
        JButton createGameButton = new JButton("Csatlakozás a várólistához");
        JButton exitButton = new JButton("Kilépés");

        if (session.getUserId() == -1) {
            createGameButton.setEnabled(false);
        }

        panel.add(createGameButton);
        panel.add(exitButton);
        add(panel);

        createGameButton.addActionListener(e -> {
            LobbyModel lobbyModel = new LobbyModel();
            lobbyModel.joinQueue();
            lobbyModel.monitorSessionState();
        });

        exitButton.addActionListener(e -> {
            session.setSessionState(MultiplayerSession.SessionState.DISCONNECTED);
            dispose();
        });
    }
}