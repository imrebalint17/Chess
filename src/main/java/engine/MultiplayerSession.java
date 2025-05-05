package engine;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class MultiplayerSession {
    private String token = null;
    private int gameId = -1;
    private static MultiplayerSession instance = null;
    private String move = null;
    private int userId = -1;
    public enum SessionState {
        DISCONNECTED,
        CONNECTED,
        QUEUED,
        MATCHED
    }
    private int color = -1; // 1 = white, 0 = black, -1 = not assigned
    private SessionState state = SessionState.DISCONNECTED;
    private String opponentName = null;
    private MultiplayerClientEndpoint clientEndpoint = null;
    private MultiplayerSession() {
        String ip = "127.0.0.1";
        int port = 30000;
        try {
            System.out.println("Connecting to " + ip + ":" + port);
            clientEndpoint = new MultiplayerClientEndpoint();
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = URI.create("ws://" + ip + ":" + port + "/ws");
            container.connectToServer(clientEndpoint, uri);
            setSessionState(MultiplayerSession.SessionState.CONNECTED);
            instance = this;
        } catch (Exception e) {
            System.err.println("Error connecting to WebSocket server: " + e.getMessage());
        }
    }
    public static MultiplayerSession getInstance() {
        if (instance == null) {
            new MultiplayerSession();
        }
        return instance;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public String getMove() {
        String move = this.move;
        this.move = null;
        return move;
    }
    public void setMove(String prevStep) {
        this.move = prevStep;
    }
    public void setSessionState(SessionState state) {
        this.state = state;
    }
    public SessionState getSessionState() {
        return state;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println(this.userId);
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public String getOpponentName() {
        return opponentName;
    }
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }
    public MultiplayerClientEndpoint getClientEndpoint() {
        return clientEndpoint;
    }
    public void setClientEndpoint(MultiplayerClientEndpoint clientEndpoint) {
        this.clientEndpoint = clientEndpoint;
    }
}
