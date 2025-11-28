package Observer;

import Model.Email;

public interface EmailObserver {
    public void onEmailReceived(Email email);
}
