package util;

public class GridUtils {

	
	public static int sumNeighbors(int[][] grid, int x, int y, int gridSize) {
		return getValue(grid, x - 1, y, gridSize) 
				+ getValue(grid, x + 1, y, gridSize) 
				+ getValue(grid, x, y - 1, gridSize) 
				+ getValue(grid, x, y + 1, gridSize)
				+ getValue(grid, x - 1, y - 1, gridSize)
				+ getValue(grid, x - 1, y + 1, gridSize) 
				+ getValue(grid, x + 1, y - 1, gridSize)
				+ getValue(grid, x + 1, y + 1, gridSize);
	}
	
	public static int getValue(int[][] grid, int x, int y, int gridSize) {
		if (x <= 0 || y <= 0 || x >= gridSize || y >= gridSize) {
			return 0;
		}
		return grid[x][y];
	}
	
}
