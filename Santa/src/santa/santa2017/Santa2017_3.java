package santa.santa2017;

import java.util.List;

import santa.SantaIssue;

public class Santa2017_3 implements SantaIssue {

	private static final int END = 289326;
	private static final int GRID_SIZE = 540;
	
	private enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	@Override
	public void solvePart1(String data, List<String> dataLines) {
		solve(false);
	}

	@Override
	public void solvePart2(String data, List<String> dataLines) {
		solve(true);
	}
	
	private void solve(boolean withSums) {
		int[][] grid = new int[GRID_SIZE][GRID_SIZE];
		int start = GRID_SIZE / 2;
		grid[start][start] = 1;
		int currentX = start;
		int currentY = start;
		Direction direction = Direction.RIGHT;
		for (int i = 2; i <= END; i++) {			
			switch (direction) {
			case RIGHT:
				currentX++;
				grid[currentX][currentY] = withSums ? sumNeighbors(grid, currentX, currentY) : i;
				if (grid[currentX][currentY+1] == 0) {
					direction = Direction.UP;
				}
				break;
			case UP:
				currentY++;
				grid[currentX][currentY] = withSums ? sumNeighbors(grid, currentX, currentY) : i;
				if (grid[currentX-1][currentY] == 0) {
					direction = Direction.LEFT;
				}
				break;
			case DOWN:
				currentY--;
				grid[currentX][currentY] = withSums ? sumNeighbors(grid, currentX, currentY) : i;
				if (grid[currentX+1][currentY] == 0) {
					direction = Direction.RIGHT;
				}
				break;
			case LEFT:
				currentX--;
				grid[currentX][currentY] = withSums ? sumNeighbors(grid, currentX, currentY) : i;
				if (grid[currentX][currentY-1] == 0) {
					direction = Direction.DOWN;
				}
				break;
			default:
				break;
			}
			if (withSums && grid[currentX][currentY] > END) {
				break;
			}
		}
		
		if (!withSums) {
			System.out.println(Math.abs(currentX - start) + Math.abs(currentY - start));
		} else {
			System.out.println(grid[currentX][currentY]);
		}
	}
	
	private int sumNeighbors(int[][] grid, int x, int y) {
		return getValue(grid, x - 1, y) 
				+ getValue(grid, x + 1, y) 
				+ getValue(grid, x, y - 1) 
				+ getValue(grid, x, y + 1)
				+ getValue(grid, x - 1, y - 1)
				+ getValue(grid, x - 1, y + 1) 
				+ getValue(grid, x + 1, y - 1)
				+ getValue(grid, x + 1, y + 1);
	}
	
	private int getValue(int[][] grid, int x, int y) {
		if (x <= 0 || y <= 0 || x >= GRID_SIZE || y >= GRID_SIZE) {
			return 0;
		}
		return grid[x][y];
	}
 
	
	
	
}
