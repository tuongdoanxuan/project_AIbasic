package Controller;

import Model.User;
import View.MailboxView;

public class LoginController {
    public void login(String email) {
        User user = new User(email);
        new MailboxView(user).setVisible(true);
    }
}
