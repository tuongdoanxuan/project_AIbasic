package View;

import javax.swing.*;
import java.awt.*;
import Service.MailService;
import Model.Email;

public class ComposeForm extends JFrame {
    private JTextField toField, subjectField;
    private JTextArea contentArea;
    private JButton sendBtn, discardBtn;
    private String senderEmail;

    public ComposeForm(String senderEmail) {
        this.senderEmail = senderEmail;
        setTitle("New Message - " + senderEmail);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header giống Gmail popup
        JLabel header = new JLabel("✉ New Message", SwingConstants.LEFT);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        add(header, BorderLayout.NORTH);

        // Panel nhập To + Subject
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        toField = new JTextField();
        subjectField = new JTextField();
        topPanel.add(new JLabel("To:")); topPanel.add(toField);
        topPanel.add(new JLabel("Subject:")); topPanel.add(subjectField);
        add(topPanel, BorderLayout.NORTH);

        // Nội dung email
        contentArea = new JTextArea();
        contentArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        // Panel nút dưới
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sendBtn = new JButton("Send");
        sendBtn.setBackground(new Color(60, 179, 113));
        sendBtn.setForeground(Color.WHITE);
        discardBtn = new JButton("Discard");
        btnPanel.add(sendBtn);
        btnPanel.add(discardBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Action
        sendBtn.addActionListener(e -> {
            String to = toField.getText().trim();
            String sub = subjectField.getText().trim();
            String content = contentArea.getText().trim();
            if (to.isEmpty() || sub.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }
            Email email = new Email(senderEmail, to, sub, content);
            MailService.getInstance().sendEmail(email);
            JOptionPane.showMessageDialog(this, "Email Sent!");
            dispose();
        });

        discardBtn.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComposeForm("me@example.com").setVisible(true));
    }
}
