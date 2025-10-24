package Controller;

import Model.Email;
import Model.EmailStorage;
import Model.SpamFilter;

public class EmailController {
    private final SpamFilter spamFilter = SpamFilter.getInstance();

    public void sendEmail(String from, String to, String subject, String body) {
        boolean isSpam = spamFilter.isSpam(subject + " " + body);
        Email email = new Email(from, to, subject, body, isSpam);
        EmailStorage.saveEmail(to, email);
    }
}
