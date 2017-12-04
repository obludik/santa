package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithms.shortestPath.Node;
import algorithms.shortestPath.ShortPathWithHeuristic;
import file.FileReaderImpl;

public class Santa2016_24 {

	static Field[][] grid;
	static int gridMaxX;
	static int gridMaxY;
	static int maxNum;
	static Map<Field, Map<Field, Integer>> distances = new HashMap<>();
	static Map<Integer, Field> numbersList = new HashMap<>();
	static boolean addZero = false;
 
	public static void main(String[] args) {
		file.FileReader reader = new FileReaderImpl();
		List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_24").getAbsolutePath());
		long startTime = System.currentTimeMillis();

		grid = new Field[data.get(0).length()][data.size()];

		maxNum = 0;

		for (int j = 0; j < data.size(); j++) {
			String row = data.get(j);
			for (int i = 0; i < row.length(); i++) {
				Field f = new Field();

				switch (row.charAt(i)) {
				case '.':
					break;
				case '#':
					f.wall = true;
					break;
				default:
					if (Character.isDigit(row.charAt(i))) {
						f.number = Integer.parseInt(String.valueOf(row.charAt(i)));
						if (maxNum < f.number) {
							maxNum = f.number;
						}
						numbersList.put(f.number, f);
					} else {
						throw new RuntimeException("Unknown character: " + row.charAt(i));
					}
				}
				f.x = i;
				f.y = j;
				grid[f.x][f.y] = f;
			}

		}

		gridMaxX = grid.length;
		gridMaxY = grid[0].length;

		printGrid(grid);
		System.out.println("Numbers: " + maxNum);

		for (int i = 0; i <= maxNum; i++) {
			Map<Field, Integer> dists = new HashMap<>();
			distances.put(numbersList.get(i), dists);
			for (int j = 0; j < i; j++) {
				dists.put(numbersList.get(j), distances.get(numbersList.get(j)).get(numbersList.get(i)));
			}
			for (int j = i + 1; j <= maxNum; j++) {
				Field from = numbersList.get(i);
				Field to = numbersList.get(j);
				ShortPathWithHeuristic pathAlg = new Santa24ShortestPath();
				List<Node> sPath = pathAlg.run(from, to);
				dists.put(to, sPath.size());
			}
		}

		for (Field key : distances.keySet()) {
			Map<Field, Integer> dist = distances.get(key);
			System.out.print(key.number + "->");
			for (Field field : dist.keySet()) {
				System.out.print(field.number + "(" + dist.get(field) + ")");
			}
			System.out.println();
		}

		findPath(numbersList.get(0), new ArrayList<>());
		System.out.println("Shortest path: " + shortest);
		
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}

	static int shortest = Integer.MAX_VALUE;

	private static void findPath(Field current, List<Field> visited) {
		visited.add(current);

		if (visited.size() == maxNum + 1) {
			int len = 0;			
			Iterator<Field> iterator = visited.iterator();
			Field from = iterator.next();
			while (iterator.hasNext()) {
				Field to = iterator.next();
				len += distances.get(from).get(to);
				from = to;
			}
			if (addZero) {
				len += distances.get(current).get(numbersList.get(0));
			}
			if (len < shortest) {
				shortest = len;
				return;
			}
		}
		// Map<Field, Map<Field, Integer>> distances = new HashMap<>();/
		for (Field field : distances.get(current).keySet()) {
			if (!visited.contains(field)) {
				List<Field> copy = new ArrayList<>();
				copy.addAll(visited);
				findPath(field, copy);
			}
		}
	}

	private static void printGrid(Field[][] grid) {
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i].wall) {
					System.out.print("#");
				} else if (grid[j][i].number >= 0) {
					System.out.print(grid[j][i].number);
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}

	private static boolean canMoveTo(int fromX, int fromY, int toX, int toY) {
		if (toX >= 0 && toX < gridMaxX && toY >= 0 && toY < gridMaxY) {
			if (!grid[toX][toY].wall) {
				return true;
			}
		}
		return false;
	}

	private static class Field extends Node {
		int x;
		int y;
		int number = -1;
		boolean wall;
		boolean visited;

		@Override
		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		@Override
		public List<Node> getNeighbors() {
			List<Node> list = new ArrayList<>();
			if (canMoveTo(x, y, x, y - 1)) {
				list.add(grid[x][y - 1]);
			}
			if (canMoveTo(x, y, x, y + 1)) {
				list.add(grid[x][y + 1]);
			}
			if (canMoveTo(x, y, x - 1, y)) {
				list.add(grid[x - 1][y]);
			}
			if (canMoveTo(x, y, x + 1, y)) {
				list.add(grid[x + 1][y]);
			}
			return list;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Field other = (Field) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public void printState() {
			// printGrid(grid);

		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public void setX(int x) {
			this.x = x;
		}

		@Override
		public int getY() {
			return y;
		}

		@Override
		public void setY(int y) {
			this.y = y;

		}

		@Override
		public boolean isVisited() {
			return visited;
		}

		@Override
		public void setLowestVal(boolean visited) {
			// TODO Auto-generated method stub

		}

		@Override
		public String toString() {
			return "Field [x=" + x + ", y=" + y + ", number=" + number + ", wall=" + wall + "]";
		}
	}
}
