package gameFrame;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import engine.MultiplayerSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("username", username);
            jsonObj.addProperty("password", password);

            try (java.io.DataOutputStream wr = new java.io.DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(jsonObj.toString());
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
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", username);
            jsonObject.addProperty("password", password);
            try (java.io.DataOutputStream wr = new java.io.DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(jsonObject.toString());
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
}
