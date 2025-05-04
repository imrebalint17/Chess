package engine;

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
    private boolean color = false; // true = white, false = black
    private SessionState state = SessionState.DISCONNECTED;
    private void MultiplayerSession() {

    }
    public static MultiplayerSession getInstance() {
        if (instance == null) {
            instance = new MultiplayerSession();
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
    }
    public boolean getColor() {
        return color;
    }
    public void setColor(boolean color) {
        this.color = color;
    }
}
