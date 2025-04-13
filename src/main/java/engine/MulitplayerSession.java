package engine;

public class MulitplayerSession {
    private String token = null;
    private int gameId = 0;
    private static MulitplayerSession instance = null;
    private void MultiplayerSession() {

    }
    public static MulitplayerSession getInstance() {
        if (instance == null) {
            instance = new MulitplayerSession();
        }
        return new MulitplayerSession();
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
}
