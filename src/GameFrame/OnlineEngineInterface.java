package GameFrame;

import Pieces.Piece;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.lang.Thread;

//TODO: Do this class multithreaded!

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
        StringBuilder body = new StringBuilder();
        if(prevStep == null){

            body.append("{\"moves\":[]}");
        }
        else {
        body.append("{\"moves\":[");
        String currstep = convertStepstoUCI(prevStep, promotion);
        prevMoves.add(currstep);
        for(String move : prevMoves){
            body.append("\"");
            body.append(move);
            body.append("\",");
        }
        body.append("]}");
        }
        String response = null;
        while (response == null){
            response = sendRequest(url.toString(),body.toString());
            try{
                Thread.sleep(30000);
            }catch (InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
        String[] responssplit = response.split(":");
        if(responssplit.length >1 && responssplit[0].equals("{\"nextmove\"")){
            response = responssplit[1].replace("\"","").replace("}","");
        }
        prevMoves.add(response);
        InterfaceMoveWrapper wrapper = convertUCItoSteps(response);
        return wrapper;
    }
    private String sendRequest(String url,String body){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
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
