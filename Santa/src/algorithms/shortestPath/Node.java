package algorithms.shortestPath;

import java.util.List;

public abstract class Node {

	public abstract List<Node> getNeighbors();

	public abstract void printState();

	public abstract int getX();

	public abstract void setX(int x);

	public abstract int getY();

	public abstract void setY(int y);

	public abstract boolean isVisited();

	public abstract void setVisited(boolean visited);
	
	public abstract void setLowestVal(boolean visited);
}