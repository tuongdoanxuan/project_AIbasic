package Main;

import Controller.LoginController;
import View.LoginView;

public class App {
    public static void main(String[] args) {
        LoginController controller = new LoginController();
        new LoginView(controller).setVisible(true);
    }
}