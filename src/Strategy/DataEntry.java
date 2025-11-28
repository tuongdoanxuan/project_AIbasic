package Strategy;

public class DataEntry {
	EmailFeatures features;
	boolean spam;

	public DataEntry(EmailFeatures f, boolean s) {
		features = f;
		spam = s;
	}
}
