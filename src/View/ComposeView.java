package View;

import Controller.EmailController;

import javax.swing.*;
import java.awt.*;

public class ComposeView extends JFrame {
    private JTextField toField = new JTextField(20);
    private JTextField subjectField = new JTextField(20);
    private JTextArea bodyArea = new JTextArea(8, 20);
    private JButton sendBtn = new JButton("Send");
    private String sender;
    private EmailController controller;

    public ComposeView(String sender, EmailController controller) {
        this.sender = sender;
        this.controller = controller;

        setTitle("Compose - " + sender);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(2, 2));
        top.add(new JLabel("To:"));
        top.add(toField);
        top.add(new JLabel("Subject:"));
        top.add(subjectField);
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(bodyArea), BorderLayout.CENTER);
        panel.add(sendBtn, BorderLayout.SOUTH);

        add(panel);

        sendBtn.addActionListener(e -> {
            controller.sendEmail(sender, toField.getText().trim(), subjectField.getText(), bodyArea.getText());
            JOptionPane.showMessageDialog(this, "Email sent!");
            dispose();
        });
    }
}
