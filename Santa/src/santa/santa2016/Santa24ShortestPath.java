package santa.santa2016;

import algorithms.shortestPath.Node;
import algorithms.shortestPath.ShortPathWithHeuristic;

public class Santa24ShortestPath extends ShortPathWithHeuristic {

	@Override
	public int estimateShortestPath(Node from, Node to) {
		return Math.abs(from.getY() - to.getY()) + Math.abs(from.getX() - to.getX()) - 1;
	}
	
	@Override
	public int getDistanceBetweenNodes(Node from, Node to) {
		return 1;
	}

}
