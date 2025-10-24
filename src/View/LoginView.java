package View;

import Controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField emailField = new JTextField(20);
    private JButton loginButton = new JButton("Login");

    public LoginView(LoginController controller) {
        setTitle("Email Login");
        setSize(300, 120);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            controller.login(emailField.getText().trim());
            dispose();
        });
    }
}
