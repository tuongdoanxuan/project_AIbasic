package Model;

public class Email {
	 private String from;
	    private String to;
	    private String subject;
	    private String body;
	    private boolean isSpam;

	    public Email(String from, String to, String subject, String body, boolean isSpam) {
	        this.from = from;
	        this.to = to;
	        this.subject = subject;
	        this.body = body;
	        this.isSpam = isSpam;
	    }

	    public String getFrom() { return from; }
	    public String getTo() { return to; }
	    public String getSubject() { return subject; }
	    public String getBody() { return body; }
	    public boolean isSpam() { return isSpam; }

	    @Override
	    public String toString() {
	        return subject + " (From: " + from + ")" + (isSpam ? " [SPAM]" : "");
	    }
}
