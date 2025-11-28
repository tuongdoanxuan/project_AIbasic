package Strategy;

public class EmailFeatures {
	public int spamWordsCount;
	public int linksCount;
	public int contentLength;

	public EmailFeatures(String content) {
		spamWordsCount = countSpamWords(content);
		linksCount = countLinks(content);
		contentLength = content.length();
	}

	private int countSpamWords(String content) {
		String[] spamWords = { "win", "free", "prize", "money", "lottery", "claim", "winner", "gift", "buy now", "click" };
		int count = 0;
		String lower = content.toLowerCase();
		for (String w : spamWords)
			if (lower.contains(w))
				count++;
		return count;
	}

	private int countLinks(String content) {
		int count = 0;
		int idx = content.toLowerCase().indexOf("http");
		while (idx >= 0) {
			count++;
			idx = content.toLowerCase().indexOf("http", idx + 1); // tìm được http là count + 1
		}
		return count;
	}
}
