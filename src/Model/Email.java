package Model;

public class Email {
	private String sender;
	private String recipient;
	private String subject;
	private String content;
	private boolean isSpam;

	public Email(String sender, String recipient, String subject, String content) {
		this.sender = sender;
		this.recipient = recipient;
		this.subject = subject;
		this.content = content;
		this.isSpam = false;
	}

	public String getSender() {
		return sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public boolean isSpam() {
		return isSpam;
	}

	public void setSpam(boolean spam) {
		this.isSpam = spam;
	}
}
