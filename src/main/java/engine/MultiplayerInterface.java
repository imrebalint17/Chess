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
    private final MultiplayerClientEndpoint multiPlayerEndpoint = new MultiplayerClientEndpoint();
    private final String ip;
    private final int port;
    public MultiplayerInterface(String ip, int port, boolean starts) {
        client = HttpClient.newHttpClient();
        this.ip = ip;
        this.port = port;
        prevMoves = new ArrayList<>();
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = URI.create("ws://" + ip + ":" + port + "/ws");
            container.connectToServer(multiPlayerEndpoint, uri);
            MultiplayerSession session = MultiplayerSession.getInstance();
            session.setSessionState(MultiplayerSession.SessionState.CONNECTED);
        } catch (Exception e) {
            System.err.println("Error connecting to WebSocket server: " + e.getMessage());
        }
    }
    /**
     * @param prevStep
     * @param promotion
     * @return
     */
    @Override
    public InterfaceMoveWrapper newRound(int[][] prevStep, Piece promotion) {
        if (prevStep != null){
            String currstep = convertStepstoUCI(prevStep, promotion);
            prevMoves.add(currstep);
            multiPlayerEndpoint.makeMove(currstep);
        }
        String response = null;
        MultiplayerSession session = MultiplayerSession.getInstance();
        while (response == null){
            response = session.getMove();
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
        System.out.println(response);
        prevMoves.add(response);
        return convertUCItoSteps(response);
    }
    /**
     *
     */
    @Override
    public void close() {
        try {
            multiPlayerEndpoint.onClose();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
