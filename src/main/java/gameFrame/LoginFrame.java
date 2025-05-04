package gameFrame;
import javax.swing.*;
import java.awt.*;


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
            int responseCode = LoginModel.login(username, password, "127.0.0.1", 30000);
            if(responseCode==200) {
                JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés: " + username);
            }
            else {
                JOptionPane.showMessageDialog(null, "Sikertelen bejelentkezés: " + username);
            }
        });

        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());
            int responseCode = LoginModel.register(username, password, "127.0.0.1", 30000);
            if(responseCode==200) {
                JOptionPane.showMessageDialog(null, "Sikeres regisztráció: " + username);
            }
            else {
                JOptionPane.showMessageDialog(null, "Sikertelen regisztráció: " + username);
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
