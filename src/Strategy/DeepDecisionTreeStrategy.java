package Strategy;

import java.io.*;
import java.util.*;

public class DeepDecisionTreeStrategy implements SpamDetectionStrategy {

	private TreeNode root;

	// Khởi tạo cây quyết định hard-coded
	public DeepDecisionTreeStrategy() {
		buildTree();
	}

	private void buildTree() {
		root = new TreeNode("spamWordsCount", 2); // root: spamWordsCount

		// Nhánh trái: spamWordsCount <= 2
		root.left = new TreeNode("linksCount", 0);
		root.left.left = new TreeNode("contentLength", 50); // spamWordsCount <=2 && linksCount <=0
		root.left.left.left = new TreeNode(false); // contentLength <=50 -> Not spam
		root.left.left.right = new TreeNode(true); // contentLength >50 -> Spam
		root.left.right = new TreeNode(true); // linksCount >0 -> Spam

		// Nhánh phải: spamWordsCount > 2
		root.right = new TreeNode(true); // Spam
	}

//	public DeepDecisionTreeStrategy(String datasetPath) {
//		try {
//			List<DataEntry> data = readDataset(datasetPath);
//			root = buildTree(data);
//		} catch (Exception e) {
//			e.printStackTrace();
//			root = null;
//		}
//	}
//
//	private List<DataEntry> readDataset(String path) throws Exception {
//		List<DataEntry> list = new ArrayList<>();
//		BufferedReader br = new BufferedReader(new FileReader(path));
//		br.readLine(); // skip header
//		String line;
//		while ((line = br.readLine()) != null) {
//			String[] parts = line.split(",", 3);
//			String content = parts[0] + " " + parts[1];
//			boolean spam = parts[2].equals("1");
//			EmailFeatures f = new EmailFeatures(content);
//			list.add(new DataEntry(f, spam));
//		}
//		br.close();
//		return list;
//	}

	@Override
	public boolean isSpam(String content) {
		if (root == null)
			return false;
		EmailFeatures f = new EmailFeatures(content);
		return dfs(root, f);
	}

	private boolean dfs(TreeNode node, EmailFeatures f) {
		if (node.isLeaf)
			return node.spamLabel;
		double val = 0;// lưu giá trị của feature hiện tại
		switch (node.feature) {
		case "spamWordsCount":
			val = f.spamWordsCount;
			break;
		case "linksCount":
			val = f.linksCount;
			break;
		case "contentLength":
			val = f.contentLength;
			break;
		}
		return val <= node.threshold ? dfs(node.left, f) : dfs(node.right, f);
	}

//	private TreeNode buildTree(List<DataEntry> data) {
//		if (data.isEmpty())
//			return new TreeNode(false);
//
//		boolean allSpam = true, allHam = true;
//		for (DataEntry d : data) {
//			if (d.spam)
//				allHam = false;
//			else
//				allSpam = false;
//		}
//		if (allSpam)
//			return new TreeNode(true);
//		if (allHam)
//			return new TreeNode(false);
//
//		// Chọn feature với threshold đơn giản (median)
//		String[] features = { "spamWordsCount", "linksCount", "contentLength" };
//		TreeNode best = null;
//		int maxGain = -1;
//		for (String feat : features) {
//			List<Double> vals = new ArrayList<>();
//			for (DataEntry d : data) {
//				switch (feat) {
//				case "spamWordsCount":
//					vals.add((double) d.features.spamWordsCount);
//					break;
//				case "linksCount":
//					vals.add((double) d.features.linksCount);
//					break;
//				case "contentLength":
//					vals.add((double) d.features.contentLength);
//					break;
//				}
//			}
//			Collections.sort(vals);
//			double thresh = vals.get(vals.size() / 2);
//			List<DataEntry> left = new ArrayList<>();
//			List<DataEntry> right = new ArrayList<>();
//			for (DataEntry d : data) {
//				double v = 0;
//				switch (feat) {
//				case "spamWordsCount":
//					v = d.features.spamWordsCount;
//					break;
//				case "linksCount":
//					v = d.features.linksCount;
//					break;
//				case "contentLength":
//					v = d.features.contentLength;
//					break;
//				}
//				if (v <= thresh)
//					left.add(d);
//				else
//					right.add(d);
//			}
//			if (left.isEmpty() || right.isEmpty())
//				continue;
//			best = new TreeNode(feat, thresh);
//			best.left = buildTree(left);
//			best.right = buildTree(right);
//			break;
//		}
//		if (best == null)
//			return new TreeNode(false);
//		return best;
//	}

}
