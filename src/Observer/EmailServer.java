package Observer;

import Model.Email;
import java.util.*;

public class EmailServer {
	private static EmailServer instance;
	private List<EmailObserver> observers = new ArrayList<>();

	private EmailServer() {
	}

	public static EmailServer getInstance() {
		if (instance == null)
			instance = new EmailServer();
		return instance;
	}

	public void addObserver(EmailObserver o) {
		observers.add(o);
	}

	public void notifyEmailReceived(Email email) {
		for (EmailObserver o : observers)
			o.onEmailReceived(email);
	}
}
