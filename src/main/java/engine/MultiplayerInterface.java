package engine;

import gameFrame.InterfaceMoveWrapper;
import pieces.Piece;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

public class MultiplayerInterface implements EngineInterface{


    private HttpClient client;
    private List<String> prevMoves;
    private final String ip;
    private final int port;
    public MultiplayerInterface(String ip, int port, boolean starts) {
        client = HttpClient.newHttpClient();
        this.ip = ip;
        this.port = port;
        prevMoves = new ArrayList<>();
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = URI.create("ws://" + ip + ":" + port);
            container.connectToServer(new MultiplayerClientEndpoint(), uri);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    /**
     * @param prevStep
     * @param promotion
     * @return
     */
    @Override
    public InterfaceMoveWrapper newRound(int[][] prevStep, Piece promotion) {

        return null;
    }
    /**
     *
     */
    @Override
    public void close() {

    }
}
