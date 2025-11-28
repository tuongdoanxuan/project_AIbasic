package Strategy;

public class KeywordSpamStrategy implements SpamDetectionStrategy {
	public boolean isSpam(String content) {
		return false;
//		String[] spamWords = { "win", "free", "prize", "money", "lottery", "claim", "winner", "gift", "buy now", "click" };
//		String lower = content.toLowerCase();
//		for (String w : spamWords) {
//			if (lower.contains(w))
//				return true;
//		}
//		return false;
	}
}
