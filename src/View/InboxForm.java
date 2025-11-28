package View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import Model.Email;
import Observer.EmailServer;
import Observer.SpamDetectorObserver;
import Service.MailService;

public class InboxForm extends JFrame {
    private JTable table;
    private String userEmail;
    private DefaultTableModel model;

    public InboxForm(String userEmail) {
        this.userEmail = userEmail;
        setTitle("Gmail Clone - " + userEmail);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOPBAR =====
        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setBackground(Color.WHITE);
        topbar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel logo = new JLabel("📧 Gmail", SwingConstants.LEFT);
        logo.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField searchField = new JTextField("Search mail...");
        searchField.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JLabel avatar = new JLabel("👤 " + userEmail);
        avatar.setHorizontalAlignment(SwingConstants.RIGHT);

        topbar.add(logo, BorderLayout.WEST);
        topbar.add(searchField, BorderLayout.CENTER);
        topbar.add(avatar, BorderLayout.EAST);
        add(topbar, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(250, 250, 250));
        sidebar.setPreferredSize(new Dimension(180, 0));

        JButton composeBtn = new JButton("✉ Compose");
        JButton inboxBtn = new JButton("📥 Inbox");
        JButton spamBtn = new JButton("🚫 Spam");
        JButton logoutBtn = new JButton("🚪 Logout");

        for (JButton btn : new JButton[]{composeBtn, inboxBtn, spamBtn, logoutBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(160, 40));
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(btn);
        }
        add(sidebar, BorderLayout.WEST);

        // ===== TABLE EMAILS =====
        String[] cols = { "From", "Subject", "Content", "Spam?" };
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                String spam = (String) t.getValueAt(row, 3);
                if ("YES".equals(spam)) {
                    c.setBackground(new Color(255, 200, 200));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                if (isSelected) c.setBackground(new Color(173, 216, 230));
                return c;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== OBSERVER =====
        EmailServer.getInstance().addObserver(new SpamDetectorObserver());

        // Load mặc định Inbox
        loadInbox();

        // ===== ACTIONS =====
        composeBtn.addActionListener(e -> {
            new ComposeForm(userEmail).setVisible(true);
        });
        inboxBtn.addActionListener(e -> {
            model.setRowCount(0);
            loadInbox();
        });
        spamBtn.addActionListener(e -> {
            model.setRowCount(0);
            loadSpam();
        });
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
    }

    // =====INBOX =====
    private void loadInbox() {
        try {
            File f = new File("data/" + userEmail + "_inbox.csv");
            if (!f.exists()) {
                JOptionPane.showMessageDialog(this, "No emails in Inbox");
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                Email e = new Email(parts[0], userEmail, parts[1], parts[2]);
                EmailServer.getInstance().notifyEmailReceived(e);
                if (e.isSpam()) continue; // bỏ qua spam trong inbox
                Object[] row = { e.getSender(), e.getSubject(), e.getContent(), "NO" };
                model.addRow(row);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====SPAM =====
    private void loadSpam() {
        try {
            File f = new File("data/" + userEmail + "_inbox.csv");
            if (!f.exists()) {
                JOptionPane.showMessageDialog(this, "No spam emails");
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                Email e = new Email(parts[0], userEmail, parts[1], parts[2]);
                EmailServer.getInstance().notifyEmailReceived(e);
                if (!e.isSpam()) continue; // chỉ lấy spam
                Object[] row = { e.getSender(), e.getSubject(), e.getContent(), "YES" };
                model.addRow(row);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InboxForm("test@example.com").setVisible(true));
    }
}
