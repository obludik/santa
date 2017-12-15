package santa.santa2017;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import santa.SantaIssue;

public class Santa2017_12 implements SantaIssue {

	public static final int NEW_NODE_STATE = 0;
	public static final int PROCESSED_NODE_STATE = 1;
	
	public void solve(String data, List<String> dataLines) {
		Graph graph = new Graph();
		// fill graph
		for (String line : dataLines) {
			String[] input = line.split(" ");
			Node node = graph.getNode(Integer.parseInt(input[0]));
			for (int i = 2; i < input.length; i++) {
				Node sibling = graph.getNode(Integer.parseInt(input[i].replaceAll(",", "")));
				if (sibling.id != node.id) {
					node.addSibling(sibling);
					sibling.addSibling(node);
				}
			}
		}

		int count = 0;
		int groupId = 1;
		// find members of the first group (connected nodes starting with node 0)
		Node[] nodes = findConnectedNodes(graph, 0, groupId);
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				count++;
			}
		}

		// count different groups
		Node nextGroupNode = graph.getFirstNodeWithNoGroup();
		int groups = 1;
		while (nextGroupNode != null) {
			groups++;
			findConnectedNodes(graph, nextGroupNode.id, ++groupId);
			nextGroupNode = graph.getFirstNodeWithNoGroup();
		}

		System.out.println("0 group size: " + count);
		System.out.println("Groups count: " + groups);
	}

	// breath first algorithm
	public static Node[] findConnectedNodes(Graph graph, int rootNr, int groupId) {
		Node[] groupNodes = new Node[graph.nodes.size()];
		
		Node rootNode = graph.getNode(rootNr);
		rootNode.groupId = groupId;
		rootNode.state = PROCESSED_NODE_STATE;
		groupNodes[rootNr] = rootNode; 

		Queue<Node> l = new LinkedList<Node>(); // fronta - pokud zde frontu nahradime zasobnikem, tak ziskame
												// prohledavani do hloubky
		l.add(rootNode);

		while (!l.isEmpty()) {
			Node node = l.poll();
			List<Node> successors = node.siblings;
			for (Node succ : successors) {
				if (succ.state == NEW_NODE_STATE) {
					l.add(succ);
					succ.state = PROCESSED_NODE_STATE;
					groupNodes[succ.id] = succ;
					succ.groupId = groupId;
				}
			}
		}
		return groupNodes;
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solve(data, dataLines);
	}

	public class Node {
		int id;
		List<Node> siblings = new ArrayList<>();
		int groupId;
		int state;

		public Node(int id) {
			this.id = id;
			this.state = NEW_NODE_STATE;
		}

		public void addSibling(Node node) {
			if (!siblings.contains(node)) {
				siblings.add(node);
			}
		}

		@Override
		public int hashCode() {
			return id;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (id != other.id)
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Node [id=").append(id).append(" children (");
			for (Node node : siblings) {
				builder.append(" id = ").append(node.id);
			}
			builder.append(")");

			return builder.toString();
		}
	}

	public class Graph {

		List<Node> nodes = new ArrayList<>();

		public Graph() {
		}

		public Node getNode(int id) {
			Node node = new Node(id);
			if (nodes.contains(node)) {
				return nodes.get(nodes.indexOf(node));
			} else {
				nodes.add(node);
				return node;
			}
		}

		public Node getFirstNodeWithNoGroup() {
			for (Node node : nodes) {
				if (node.groupId == 0) {
					return node;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return "Graph [nodes=" + nodes + "]";
		}
	}
}
