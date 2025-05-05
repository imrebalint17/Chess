package engine;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;

@ClientEndpoint
public class MultiplayerClientEndpoint {
    // This class will handle the WebSocket connection and messages
    // You can implement methods to handle onOpen, onClose, onMessage, etc.
    // For example:
    private MultiplayerSession multiplayerSession;
    private Session session;
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server");
    }
    @OnClose
    public void onClose() {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.session = null;
        multiplayerSession.setSessionState(MultiplayerSession.SessionState.DISCONNECTED);
        System.out.println("Disconnected from server");
    }
    @OnMessage
    public void onMessage(String message) {
        this.multiplayerSession = MultiplayerSession.getInstance();
        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            int gameId = 0;
            switch (type) {
                case "matched":
                    multiplayerSession.setColor((jsonObject.get("color").getAsString().equals("white")) ? 1 : 2);
                    System.out.println(jsonObject.get("color").getAsString());
                    System.out.println(multiplayerSession.getColor());
                    multiplayerSession.setGameId(jsonObject.get("game_id").getAsInt());
                    multiplayerSession.setSessionState(MultiplayerSession.SessionState.MATCHED);
                    System.out.println("Matched game with this gameId: " + gameId);
                    break;
                case "queued":
                    System.out.println("Queued for a game");
                    multiplayerSession.setSessionState(MultiplayerSession.SessionState.QUEUED);
                    break;
                case "move":
                    String move = jsonObject.get("move").getAsString();
                    gameId = jsonObject.get("game_id").getAsInt();
                    if (gameId == multiplayerSession.getGameId()) {
                        System.out.println("Received move: " + move);
                        multiplayerSession.setMove(move);
                    } else {
                        System.out.println("Received move for a different game: " + gameId);
                    }
                    break;
                default:
                    System.out.println("Unknown message type: " + type +"\t"+ message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String makeMove(String move) {
        this.multiplayerSession = MultiplayerSession.getInstance();
        String token = multiplayerSession.getToken();
        int gameId = multiplayerSession.getGameId();
        if (token != null && gameId != 0) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "make_move");
            jsonObject.addProperty("token", token);
            jsonObject.addProperty("game_id", gameId);
            jsonObject.addProperty("move", move);
            jsonObject.addProperty("state","in_progress");
            System.out.println("Sending move: " + jsonObject);
            session.getAsyncRemote().sendText(jsonObject.toString());
            return "Move sent to server";
        }
        return null;
    }
    public void authenticate(){
        this.multiplayerSession = MultiplayerSession.getInstance();
        String token = multiplayerSession.getToken();
        if (token != null) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "authenticate");
            jsonObject.addProperty("token", token);
            session.getAsyncRemote().sendText(jsonObject.toString());
        }
    }
    public void joinQueue()
    {
        this.multiplayerSession = MultiplayerSession.getInstance();
        String token = multiplayerSession.getToken();
        authenticate();
        System.out.println("Received join queue: " + token);
        JsonObject jsonObject = new JsonObject();
        if (token != null) {
            jsonObject.addProperty("type", "join_queue");
            jsonObject.addProperty("token", token);
            System.out.println("Sent join queue: " + jsonObject);
            session.getAsyncRemote().sendText(jsonObject.toString());
        }
        System.out.println("Sent join queue");
    }
}
