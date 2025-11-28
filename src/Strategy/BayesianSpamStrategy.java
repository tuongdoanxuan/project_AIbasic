package Strategy;

public class BayesianSpamStrategy implements SpamDetectionStrategy {
    public boolean isSpam(String content) {
        return Math.random() < 0.5; // giả lập Bayesian
    }
}
