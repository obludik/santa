package santa.santa2018;

import java.util.List;

import santa.SantaIssue;

public class Santa2018_11 implements SantaIssue {

	private static final int SERIAL_NUM = 6878;
	private static final int GRID_SIZE = 300;

	int[][] grid = new int[GRID_SIZE][GRID_SIZE];

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		for (int i = 1; i <= grid.length; i++) {
			for (int j = 1; j <= grid.length; j++) {
				int val = ((i + 10) * j + SERIAL_NUM) * (i + 10);
				String value = String.valueOf(val);
				value = value.substring(value.length() - 3, value.length() - 2);
				val = Integer.valueOf(value) - 5;
				grid[i - 1][j - 1] = val;
			}
		}

		calculateSum(3, 3);
		calculateSum(1, 300);
	}

	private void calculateSum(int minSquareSize, int maxSquareSize) {

		int max = 0;
		int maxX = 0;
		int maxY = 0;
		int squareSize = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				int lastSquareSum = grid[i][j];
				for (int k = 1; k < maxSquareSize; k++) {
					if (i + k > grid.length - 1 || j + k > grid.length - 1) {
						break;

					}
					for (int l = i; l < i + k; l++) {
						lastSquareSum += grid[l][j + k];
					}
					for (int l = j; l <= j + k; l++) {
						lastSquareSum += grid[i + k][l];
					}

					if (lastSquareSum > max && k >= minSquareSize - 1) {
						max = lastSquareSum;
						maxX = i;
						maxY = j;
						squareSize = k;
					}
				}
			}
		}
		System.out.println(maxX + 1 + "," + (maxY + 1) + "," + (squareSize + 1));

	}

}
