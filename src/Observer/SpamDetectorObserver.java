package Observer;

import Model.Email;
import Strategy.SpamDetectionStrategy;
import Strategy.DeepDecisionTreeStrategy;

public class SpamDetectorObserver implements EmailObserver {
	private SpamDetectionStrategy detector;

	public SpamDetectorObserver() {
		detector = new DeepDecisionTreeStrategy();
//		detector = new DeepDecisionTreeStrategy("data/dataset.csv"); // đường dẫn dataset
	}

	@Override
	public void onEmailReceived(Email email) {
		email.setSpam(detector.isSpam(email.getSubject() + " " + email.getContent()));
	}
}
