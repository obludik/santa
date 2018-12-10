package santa.santa2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import santa.SantaIssue;

public class Santa2018_8 implements SantaIssue {

	long metadataSum = 0;
	int globalIndex = 0;
	int rootSum = 0;
	List<Node> nodes = new ArrayList<>();

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		String[] numbers = data.split(" ");
		List<String> nums = Arrays.asList(numbers);
		readNode(nums);
		System.out.println("Part 1: " + metadataSum);
		calculateValue(nodes.get(0));
		System.out.println("Part 2: " + rootSum);
	}

	private Node readNode(List<String> nums) {
		int childrenCount = Integer.parseInt(nums.get(globalIndex));
		globalIndex++;
		int metadataCount = Integer.parseInt(nums.get(globalIndex));
		globalIndex++;
		Node node = new Node();
		nodes.add(node);
		for (int i = 0; i < childrenCount; i++) {
			node.getChildren().add(readNode(nums));
		}
		for (int i = 0; i < metadataCount; i++) {
			metadataSum += Integer.parseInt(nums.get(globalIndex));
			node.getMetadata().add(Integer.parseInt(nums.get(globalIndex)));
			globalIndex++;
		}
		return node;
	}

	private void calculateValue(Node node) {
		if (node.getChildren().size() == 0) {
			int value = 0;
			for (int i = 0; i < node.getMetadata().size(); i++) {
				value += node.getMetadata().get(i);
			}
			rootSum += value;
		} else {
			for (int i = 0; i < node.getMetadata().size(); i++) {
				int index = node.getMetadata().get(i);
				if (node.getChildren().size() >= index && index > 0) {
					calculateValue(node.getChildren().get(index - 1));
				}
			}
		}
	}

	private class Node {
		private List<Integer> metadata = new ArrayList<>();
		private List<Node> children = new ArrayList<>();

		public List<Integer> getMetadata() {
			return metadata;
		}

		public List<Node> getChildren() {
			return children;
		}
	}

}
