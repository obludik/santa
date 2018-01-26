package santa.santa2017;

import java.util.List;

import santa.SantaIssue;

public class Santa2017_19 implements SantaIssue {

	static char[][] grid = null; //new int[250][250];
	
	public void solvePart1(String data, List<String> dataLines) {
		
		int dimX = dataLines.get(0).length();
		int dimY = dataLines.size();
		grid = new char[dimY][dimX];
		
		int x = 0;
		int y = 0;
		for (String line : dataLines) {
			
			char[] chars = line.toCharArray();
			for (char c : chars) {
				switch (c) {
				case '|':
					grid[x][y] = 1;
					break;
				case '+':
					grid[x][y] = 2;
					break;
				case '-':
					grid[x][y] = 3;
					break;
				case ' ':
					grid[x][y] = 0;
					break;
				default:
					grid[x][y] = c;
					break;
				}
				x++;
			}
			y++;
			x = 0;
		}
		
		y = 0;
		x = dataLines.get(0).indexOf('|');
		
		// 1 - up, 2 - down, 3 - left, 4 - right
		int direction = 2;		
		int steps = 0;
		
		while (grid[x][y] != 0) {
			
			if (grid[x][y] == 2) {
				switch (direction) {
				case 1:
				case 2:
					if (checkDirection(x-1, y) && grid[x-1][y] >= 3) {
						direction = 4;
					} else if (checkDirection(x+1, y) && grid[x+1][y] >= 3) {
						direction =  3;
					}
					break;
				case 3:
				case 4:
					if (checkDirection(x, y+1) && (grid[x][y+1] == 1 || grid[x][y+1] > 3)) {
						direction =  2;
					} else if (checkDirection(x, y-1) && (grid[x][y-1] == 1 || grid[x][y+1] > 3)) {
						direction =  1;
					}
					break;
				}
			} else if (grid[x][y] > 3) {
				System.out.print(grid[x][y]);
			}
			
			
			switch (direction) {
			case 1:
				y -= 1; 
				break;
			case 2:
				y += 1; 
				break;
			case 3:
				x += 1; 
				break;
			case 4:
				x -= 1; 
				break;
			default:
				break;
			}
			steps++;
		}
		System.out.println();
		System.out.println(steps);
	}
	
	public static boolean checkDirection(int x, int y) {
		
		if (x < 0 || y < 0 || x >= grid[0].length || y >= grid.length) {
			return false;
		}
			return true;
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
	}

}
