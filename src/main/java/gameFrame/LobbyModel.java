package gameFrame;

import engine.MultiplayerClientEndpoint;
import engine.MultiplayerSession;

import javax.swing.*;
public class LobbyModel {
    private final MultiplayerSession session;

    public LobbyModel() {
        this.session = MultiplayerSession.getInstance();
    }

    public void monitorSessionState() {
        session.setSessionState(MultiplayerSession.SessionState.QUEUED);
            while (true) {
                if (session.getSessionState() == MultiplayerSession.SessionState.MATCHED) {
                    SwingUtilities.invokeLater(() -> {
                        int color = session.getColor();
                        String colorText = (color == 1) ? "black" : "white";
                        JOptionPane.showMessageDialog(null, "Game matched! You are playing as " + colorText);
                    });
                    break;
                }
                try {
                    Thread.sleep(500); // Check every 500 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
    public void joinQueue() {
        session.setSessionState(MultiplayerSession.SessionState.QUEUED);
        MultiplayerClientEndpoint clientEndpoint = session.getClientEndpoint();
        if (clientEndpoint != null) {
            clientEndpoint.joinQueue();
        } else {
            System.err.println("Client endpoint is not initialized.");
        }
    }
}