package View;

import Controller.EmailController;
import Model.Email;
import Model.EmailStorage;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MailboxView extends JFrame {
    private User user;
    private DefaultListModel<String> inboxModel = new DefaultListModel<>();
    private DefaultListModel<String> spamModel = new DefaultListModel<>();
    private JList<String> inboxList = new JList<>(inboxModel);
    private JList<String> spamList = new JList<>(spamModel);
    private EmailController emailController = new EmailController();

    public MailboxView(User user) {
        this.user = user;
        setTitle("Mailbox - " + user.getEmail());
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(inboxList), new JScrollPane(spamList));
        split.setDividerLocation(300);

        JPanel buttonPanel = new JPanel();
        JButton composeBtn = new JButton("Compose");
        JButton refreshBtn = new JButton("Refresh");
        buttonPanel.add(composeBtn);
        buttonPanel.add(refreshBtn);

        add(split, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        composeBtn.addActionListener(e -> new ComposeView(user.getEmail(), emailController).setVisible(true));
        refreshBtn.addActionListener(e -> loadEmails());

        loadEmails();
    }

    private void loadEmails() {
        inboxModel.clear();
        spamModel.clear();
        List<Email> emails = EmailStorage.loadEmails(user.getEmail());
        for (Email e : emails) {
            if (e.isSpam()) spamModel.addElement(e.toString());
            else inboxModel.addElement(e.toString());
        }
    }
}
