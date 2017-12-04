package santa.santa2016;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import algorithms.shortestPath.Dijkstra;

public class Santa2016_13 {

	private static final int MAGIC_NUMBER = 1352;
	private static final int GRID_LEN = 80; //31,39
	private static final int DESTINATION = 31 * GRID_LEN + 39;
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		int[][] matrix = new int[GRID_LEN*GRID_LEN][GRID_LEN*GRID_LEN];
		
		int[][] grid = new int[GRID_LEN][GRID_LEN];
		
		for (int i = 0; i < GRID_LEN; i++) {
			for (int j = 0; j < GRID_LEN; j++) {
				grid[i][j] = isWall(i, j) ? 1 : 0;
			}
		}
		
		for (int i = 0; i < GRID_LEN*GRID_LEN; i++) {
			for (int j = 0; j < GRID_LEN*GRID_LEN; j++) {
				int gridFromX = i / GRID_LEN;
				int gridFromY = i % GRID_LEN;
				int gridToX = j / GRID_LEN;
				int gridToY = j % GRID_LEN;
				matrix[j][i] = isConnectionBetweenPoints(grid, gridFromX, gridFromY, gridToX, gridToY) ? 1 : Integer.MAX_VALUE;
				if (i==j) {
					matrix[j][i] = 0;
				}
			}
		}
		
		for (int i = 0; i < GRID_LEN; i++) {
			for (int j = 0; j < GRID_LEN; j++) {
				System.out.print(grid[j][i] == 1 ? "#" : ".");
			}
			System.out.println();
		}
				
		int[] tree = Dijkstra.doDijkstra(matrix, GRID_LEN + 1);
		
		System.out.println(Arrays.toString(tree));
		
		
		int next = DESTINATION; 
		int count = 0; 
		while (next != -1) {
			System.out.print(tree[next] + " ");
			next = tree[next];
			count++;
		}			

		System.out.println();
		System.out.println(count-1);
		
		int lessThan50Count = 0;
		for (int i = 0; i < tree.length; i++) {
			next = i; 
			count = 0; 
			while (next != -1) {
			//	System.out.print(tree[next] + " ");
				int curr = next;
				next = tree[next];
				if (next == 0) {
					break;
				}				count++;
				if (next == -1 && count <= 51) {
					System.out.print(curr + " ");
					lessThan50Count++;
				}
			}
		}
		System.out.println();
		System.out.println(lessThan50Count);
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}
	
	private static boolean isWall(int x, int y) {
		int calc = x*x + 3*x + 2*x*y + y + y*y + MAGIC_NUMBER;
		String binString = Integer.toBinaryString(calc);
		Pattern p = Pattern.compile("(1)");
		Matcher m = p.matcher(binString);
		int count = 0;
		while (m.find()) {
			count++;
		}
		//System.out.println(binString + ": " + count);
		if (count % 2 > 0) {
			return true;
		}
		return false;
	}
	
	private static boolean isConnectionBetweenPoints(int[][] grid, int point1X, int point1Y, int point2X, int point2Y) {
		if ((Math.abs(point2X - point1X) == 1 && point2Y == point1Y)
				|| (point2X == point1X && Math.abs(point2Y - point1Y) == 1)) {
			if (grid[point1X][point1Y] == 0 && grid[point2X][point2Y] == 0) {
				return true;
			}
		}
		return false;
	}

}
