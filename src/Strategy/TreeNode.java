package Strategy;

class TreeNode {
	String feature;
	double threshold; // ngưỡng so sánh
	boolean isLeaf; // Leaf sẽ chứa nhãn spam hoặc không spam
	boolean spamLabel;// true là spam, false là không spam
	TreeNode left;
	TreeNode right;

	public TreeNode(String feature, double threshold) {
		this.feature = feature;
		this.threshold = threshold;
		this.isLeaf = false;
	}

	public TreeNode(boolean spamLabel) {
		this.isLeaf = true;
		this.spamLabel = spamLabel;
	}
}
