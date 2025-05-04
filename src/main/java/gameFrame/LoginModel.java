package gameFrame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import engine.*;
import game.Game;

import javax.swing.*;

public class LoginModel {
    public static int login(String username, String password, String ip, int port) {
        try {
            StringBuilder url = new StringBuilder("http://");
            url.append(ip);
            url.append(":");
            url.append(port);
            url.append("/login");
            URL obj = new URL(url.toString());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String jsonInput = "{"
                    + "\"username\":\"" + username + "\","
                    + "\"password\":\"" + password + "\""
                    + "}";

            try (java.io.DataOutputStream wr = new java.io.DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(jsonInput);
                wr.flush();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            String token = jsonResponse.get("token").getAsString();
            int userId = jsonResponse.get("user_id").getAsInt();
            MultiplayerSession session = MultiplayerSession.getInstance();
            session.setToken(token);
            session.setUserId(userId);
            return con.getResponseCode();
        }catch (Exception f) {
            f.printStackTrace();
        }
        return 0;
    }
    public static int register(String username, String password, String ip, int port) {
        try {
            StringBuilder url = new StringBuilder("http://");
            url.append(ip);
            url.append(":");
            url.append(port);
            url.append("/register");
            URL obj = new URL(url.toString());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String jsonInput = "{"
                    + "\"username\":\"" + username + "\","
                    + "\"password\":\"" + password + "\""
                    + "}";

            try (java.io.DataOutputStream wr = new java.io.DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(jsonInput);
                wr.flush();
            }
            int responseCode = con.getResponseCode();
            System.out.println(con.getResponseMessage());
            System.out.println("Response Code: " + responseCode);
            return con.getResponseCode();
        } catch (Exception f) {
            f.printStackTrace();
        }
        return 0;
    }
//    private static String sendRequest(String url, String body){
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .POST(HttpRequest.BodyPublishers.ofString(body))
//                .header("Content-Type", "application/json")
//                .build();
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            return response.body();
//        }catch (Exception e){
//            System.err.println(e.getMessage());
//        }
//        return null;
//    }
}
