package engine;

import javax.websocket.*;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

@ClientEndpoint
public class MultiplayerClientEndpoint {
    // This class will handle the WebSocket connection and messages
    // You can implement methods to handle onOpen, onClose, onMessage, etc.
    // For example:
    private MulitplayerSession multiplayerSession;
    @OnOpen
    public void onOpen(Session session) {
        this.multiplayerSession = MulitplayerSession.getInstance();
        System.out.println("Connected to server");
        // You can send a message to the server here if needed

    }
    @OnClose
    public void onClose() {
        System.out.println("Disconnected from server");
    }
    @OnMessage
    public void onMessage(String message) {
        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            int gameId = 0;
            switch (type) {
                case "matched":
                    gameId = jsonObject.get("game_id").getAsInt();
                    multiplayerSession.setGameId(gameId);
                    System.out.println("Matched game with this gameId: " + gameId);
                    break;
                case "queued":
                    System.out.println("Queued for a game");
                    break;
                case "move":
                    String move = jsonObject.get("move").getAsString();
                    gameId = jsonObject.get("game_id").getAsInt();
                    if (gameId == multiplayerSession.getGameId()) {
                        System.out.println("Received move: " + move);
                    } else {
                        System.out.println("Received move for a different game: " + gameId);
                    }
                    break;
                default:
                    System.out.println("Unknown message type: " + type);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String makeMove(Session session,String move) {
        String token = multiplayerSession.getToken();
        int gameId = multiplayerSession.getGameId();
        if (token != null && gameId != 0) {
            session.getAsyncRemote().sendText("{\"type\": \"make_move\", \"token\": \"" + token + "\", \"game_id\": " + gameId + ", \"move\": \"" + move + "\"}");
            return "Move sent to server";
        }
        return null;
    }
    public void authenticate(Session session) {
        String token = multiplayerSession.getToken();
        if (token != null) {
            session.getAsyncRemote().sendText("{\"type\": \"authenticate\", \"token\": + \""+ token + "\"}");
        }
    }
    public void joinQueue(Session session) {
        String token = multiplayerSession.getToken();
        if (token != null) {
            session.getAsyncRemote().sendText("{\"type\": \"join_queue\", \"token\": + \""+ token + "\"}");
        }
    }
}
