package View;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LoginForm extends JFrame {
	private JTextField emailField;
	private JPasswordField passField;
	private JButton loginBtn;
	private JLabel statusLabel;

	public LoginForm() {
		setTitle("Login");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		// Layout chính
		setLayout(new BorderLayout());

		// Header
		JLabel header = new JLabel("Welcome - Please Login");
		header.setFont(new Font("Arial", Font.BOLD, 20));
		header.setHorizontalAlignment(SwingConstants.CENTER);// căn chỉnh nội dung của JLabel theo chiều ngang
		header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));// top, left, bottom, right
		add(header, BorderLayout.NORTH);

		// Panel form
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Email
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(new JLabel("Email:"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		emailField = new JTextField(20);
		formPanel.add(emailField, gbc);

		// Password
		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(new JLabel("Password:"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		passField = new JPasswordField(20);
		formPanel.add(passField, gbc);

		// Login button
		gbc.gridx = 1;
		gbc.gridy = 2;
		loginBtn = new JButton("Login");
		loginBtn.setBackground(new Color(70, 130, 180));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setFocusPainted(false);
		loginBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		formPanel.add(loginBtn, gbc);

		add(formPanel, BorderLayout.CENTER);

		// Status label
		statusLabel = new JLabel(" ");
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		add(statusLabel, BorderLayout.SOUTH);

		// Action
		loginBtn.addActionListener(e -> {
			String email = emailField.getText().trim();
			String pass = new String(passField.getPassword());
			if (authenticate(email, pass)) {
				statusLabel.setForeground(new Color(0, 128, 0));
				statusLabel.setText("Login Success!");
				JOptionPane.showMessageDialog(this, "Login Success!");
				new InboxForm(email).setVisible(true);
				dispose();
			} else {
				statusLabel.setForeground(Color.RED);
				statusLabel.setText("Login Failed! Try again.");
			}
		});
	}

	private boolean authenticate(String email, String pass) {
		try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 2 && parts[0].equals(email) && parts[1].equals(pass)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
	}
}
