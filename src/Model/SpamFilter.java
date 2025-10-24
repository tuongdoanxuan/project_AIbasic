package Model;

import java.util.*;

public class SpamFilter {
    private static SpamFilter instance;
    private final Set<String> spamWords = new HashSet<>(Arrays.asList(
            "win", "free", "money", "offer", "click", "prize", "reward", "lottery", "urgent", "credit"
    ));

    private SpamFilter() {}

    public static SpamFilter getInstance() {
        if (instance == null) instance = new SpamFilter();
        return instance;
    }

    public boolean isSpam(String text) {
        text = text.toLowerCase();
        for (String w : spamWords)
            if (text.contains(w))
                return true;
        return false;
    }
}
