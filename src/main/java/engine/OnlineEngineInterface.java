package engine;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameFrame.InterfaceMoveWrapper;
import pieces.Piece;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class OnlineEngineInterface implements EngineInterface{
    private HttpClient client;
    private List<String> prevMoves;
    private final String ip;
    private final int port;
    public OnlineEngineInterface(String ip, int port, boolean starts) {
        client = HttpClient.newHttpClient();
        this.ip = ip;
        this.port = port;
        prevMoves = new ArrayList<>();
    }
    public InterfaceMoveWrapper newRound(int[][] prevStep, Piece promotion){
        StringBuilder url = new StringBuilder("http://");
        url.append(ip);
        url.append(":");
        url.append(port);
        JsonObject jsonObject = new JsonObject();
        if(prevStep != null){
        String currstep = convertStepstoUCI(prevStep, promotion);
        prevMoves.add(currstep);
        }
        JsonArray jsonarray = new JsonArray(prevMoves.size());
        for (String move : prevMoves) {
            jsonarray.add(move);
        }
        jsonObject.add("moves", jsonarray);
        System.out.println("Uj json"+jsonObject.toString());
        String response = null;
        while (response == null){
            System.out.println(jsonObject.toString());
            response = sendRequest(url.toString(),jsonObject.toString());
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
        jsonObject = JsonParser.parseString(response).getAsJsonObject();
        String nextMove = jsonObject.get("nextmove").getAsString();
        prevMoves.add(nextMove);
        InterfaceMoveWrapper wrapper = convertUCItoSteps(nextMove);
        return wrapper;
    }
    private String sendRequest(String url,String body){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMinutes(5))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }
    public void close(){

    }
}
