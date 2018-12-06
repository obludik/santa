package santa.santa2018;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import santa.SantaIssue;
import santa.common.objects.TwoDimCoordinates;

public class Santa2018_6 implements SantaIssue {

	private static final Pattern PATTERN = Pattern.compile("(\\d*), (\\d*)");
	List<CoordinatesWithCount> coords = new ArrayList<>();
	int[][] grid = new int[400][400];
	int maxId = 0;
	
	private void solvePart2() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
				if (grid[x][y] >= 1 && (grid[x][y] < maxId)) {
					continue;
				}
				int sum = 0;
				for (CoordinatesWithCount coordinates : coords) {
					sum += (int)(Math.abs(coordinates.getX() - x) + Math.abs(coordinates.getY() - y));
				}
				if (sum < maxId) {
					grid[x][y] = maxId;
				} else {
					grid[x][y] = sum;
				}
			}
		}
		
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i] < 10000) {
					
					
					if (grid[j][i] >= 1 && (grid[j][i] < maxId)) {
						System.out.print("_");
						if (grid[j-1][i] < 10000 || grid[j][i-1] < 10000 || grid[j+1][i] < 10000 || grid[j][i+1] < 10000) {
							count++;
						}
					} else {
						System.out.print(grid[j][i] < 10000 ? "#" : " ");
						count++;
					}
				} else {
					System.out.print(" ");
				}
				
				
				
			}
			System.out.println();
		}
		System.out.println(count);
	}
	
	private void solvePart1() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
				if (grid[x][y] >= 1 && (grid[x][y] < maxId)) {
					continue;
				}
				int minDistance = Integer.MAX_VALUE;
				int minDistIndex = 0;
				boolean equal = false;
				for (CoordinatesWithCount coordinates : coords) {
					int distance = (int)(Math.abs(coordinates.getX() - x) + Math.abs(coordinates.getY() - y));
					if (distance < minDistance) {
						minDistance = distance;	
						minDistIndex = coords.indexOf(coordinates);
						equal = false;
					} else if (distance == minDistance) {
						equal = true;
					}
				}
				if (!equal) {
					grid[x][y] = (char)(minDistIndex + maxId);
					coords.get(minDistIndex).count++;
				} else {
					grid[x][y] = -1;
				}
			}
		}
		
		
	/*	for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				System.out.print(grid[j][i]);
			}
			System.out.println();
		}
	*/	
		for (int i = 0; i < grid.length; i++) {	
			setInfinite(i, 0);
			setInfinite(0, i);
			setInfinite(i , grid.length - 1);
			setInfinite(grid.length - 1 , i);
		}
		
		int max = 0;
		for (CoordinatesWithCount coordinates : coords) {
			if (!coordinates.infinite && coordinates.count > max) {
				max = coordinates.count;
			}
		}
		
		System.out.println(max + 1);
	}
	
	@Override
	public void solveBothParts(String data, List<String> dataLines) {
				
		int id = 1;
		for (String line : dataLines) {
			Matcher m = PATTERN.matcher(line);
			m.find();
			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(2));
			grid[x][y] = id;
			coords.add(new CoordinatesWithCount(x, y, id));
			id++;
		}
		maxId = id;
		
		solvePart2(); 
	}
	
	private void setInfinite(int x, int y) {
		if (grid[x][y] != -1 && grid[x][y] >= maxId) {
			coords.get(grid[x][y] - maxId).infinite = true;
		}
	}
	
	private class CoordinatesWithCount extends TwoDimCoordinates {
		int count;
		boolean infinite;
		
		public CoordinatesWithCount(long x, long y) {
			super(x, y);
		}
	}
}
