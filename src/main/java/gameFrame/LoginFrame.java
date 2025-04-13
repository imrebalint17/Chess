package gameFrame;
import javax.swing.*;
import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import game.Game;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Bejelentkezés");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Felhasználónév:");
        JTextField userText = new JTextField();
        JLabel passLabel = new JLabel("Jelszó:");
        JPasswordField passText = new JPasswordField();
        JButton registerButton = new JButton("Regisztráció");
        JButton loginButton = new JButton("Bejelentkezés");

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            try {
                String url = "http://85.66.121.59:5000/login";
                URL obj = new URL(url);
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
                System.out.println("Response Code: " + responseCode);
                if(responseCode==200) {
                    JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés: " + username);
                    new Game();
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, con.getResponseMessage());
                }

            } catch (Exception f) {
                f.printStackTrace();
            }
        });

        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            try {
                String url = "http://85.66.121.59:5000/register";
                URL obj = new URL(url);
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
                System.out.println("Response Code: " + responseCode);
                if(responseCode==200) {
                    JOptionPane.showMessageDialog(null, "Sikeres regisztráció: " + username);
                }
                else {
                    JOptionPane.showMessageDialog(null, con.getResponseMessage());
                }

            } catch (Exception f) {
                f.printStackTrace();
            }
        });

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(registerButton);
        panel.add(loginButton);

        add(panel);

        this.setVisible(true);
    }


}
