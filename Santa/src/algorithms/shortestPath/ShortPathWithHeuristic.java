package algorithms.shortestPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ShortPathWithHeuristic {

	// odhad vzdalenosti, musi byt mensi nez opravdova, monotonni fce
	public abstract int estimateShortestPath(Node from, Node to);
	
	// realna vzdalenost mezi uzly (vaha, jednicka...)
	public abstract int getDistanceBetweenNodes(Node from, Node to);
	
	public List<Node> run(Node start, Node goal) {
		// The set of nodes already evaluated.
		Set<Node> closedSet = new HashSet<>();
		// The set of currently discovered nodes that are already evaluated.
		// Initially, only the start node is known.
		Set<Node> openSet = new HashSet<>();
		openSet.add(start);
		// For each node, which node it can most efficiently be reached from.
		// If a node can be reached from many nodes, cameFrom will eventually
		// contain the
		// most efficient previous step.
		Map<Node, Node> cameFrom = new HashMap<>();

		// For each node, the cost of getting from the start node to that node.
		Map<Node, Integer> gScore = new HashMap<>();// map with default value of
													// Infinity	
		// The cost of going from start to start is zero.
		gScore.put(start, 0);

		// For each node, the total cost of getting from the start node to the
		// goal
		// by passing by that node. That value is partly known, partly
		// heuristic.
		Map<Node, Integer> fScore = new HashMap<>();// map with default value of
													// Infinity

		// For the first node, that value is completely heuristic.
		fScore.put(start, estimateShortestPath(start, goal));

		while (openSet.size() > 0) {

			// the node in openSet having the lowest fScore[] value
			Node current = null;
			int lowestFScore = Integer.MAX_VALUE;
			for (Node node2 : openSet) {
				if (getValueOrInfinity(fScore, node2) < lowestFScore) {
					current = node2;
					lowestFScore = getValueOrInfinity(fScore, node2);
					
				}
			}

			if (current == goal) {
				return reconstructPath(cameFrom, current);
			}

			openSet.remove(current);
			closedSet.add(current);

			for (Node neighbor : current.getNeighbors()) {

				if (closedSet.contains(neighbor)) {
					continue; // Ignore the neighbor which is already evaluated.
					// The distance from start to a neighbor
				}

				int tentative_gScore = getValueOrInfinity(gScore, current)
						+ getDistanceBetweenNodes(current, neighbor);
				if (!openSet.contains(neighbor)) {
					// neighbor not in openSet // Discover a new node
					openSet.add(neighbor);
				} else if (tentative_gScore >= getValueOrInfinity(gScore, neighbor)) {
					continue; // This is not a better path.
				}
			
				// This path is the best until now. Record it!
				cameFrom.put(neighbor, current);
				gScore.put(neighbor, tentative_gScore);
				fScore.put(neighbor,
						getValueOrInfinity(gScore, neighbor) + estimateShortestPath(neighbor, goal));
			}
		}
		return null;

	}

	private static int getValueOrInfinity(Map<Node, Integer> score, Node key) {
		if (score.containsKey(key)) {
			return score.get(key);
		}
		return Integer.MAX_VALUE;
	}

	public List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
		List<Node> totalPath = new ArrayList<>();
		//totalPath.add(current);
		while (cameFrom.keySet().contains(current)) {
			current = cameFrom.get(current);
			totalPath.add(current);
		}
		return totalPath;
	}
}
