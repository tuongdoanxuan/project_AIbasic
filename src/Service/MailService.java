package Service;

import Model.Email;
import java.io.*;
//quản lý việc gửi và lưu trữ email
public class MailService {
	private static MailService instance;  //singleton: đảm bảo chỉ có 1 MailService duy nhất

	private MailService() {
	}

	public static MailService getInstance() {
		if (instance == null)
			instance = new MailService();
		return instance;
	}

	public void sendEmail(Email email) {
		saveToInbox(email);
		saveToSent(email);
	}

	private void saveToInbox(Email email) {
		String fileName = "data/" + email.getRecipient() + "_inbox.csv";
		boolean newFile = !new File(fileName).exists();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
			if (newFile)
				bw.write("Sender,Subject,Content\n");
			bw.write(email.getSender() + "," + email.getSubject() + "," + email.getContent() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveToSent(Email email) {
		String fileName = "data/" + email.getSender() + "_sent.csv";
		boolean newFile = !new File(fileName).exists();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
			if (newFile)
				bw.write("Receiver,Subject,Content\n");
			bw.write(email.getRecipient() + "," + email.getSubject() + "," + email.getContent() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
