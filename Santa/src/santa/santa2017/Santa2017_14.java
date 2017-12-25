package santa.santa2017;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import santa.SantaIssue;

public class Santa2017_14 implements SantaIssue {

	public static final String INPUT = "ugkiagan";

	public void solvePart1(String data, List<String> dataLines) {
		Santa2017_10 santa10 = new Santa2017_10();
		int count = 0;
		for (int i = 0; i < 128; i++) {
			count += Arrays.stream(santa10.getKnotHash(INPUT + "-" + i).split(""))
					.map(j -> Integer.toBinaryString(Integer.parseInt(j, 16))).map(j -> j.replace("0", ""))
					.mapToInt(j -> j.length()).sum();
		}
		System.out.println(count);
	}

	public void solvePart2(String data, List<String> dataLines) {
		Santa2017_10 santa10 = new Santa2017_10();
		int[][] grid = new int[128][128];

		for (int i = 0; i < 128; i++) {
			grid[i] = Arrays.stream(santa10.getKnotHash(INPUT + "-" + i).split(""))
					.map(j -> String.format("%04d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(j, 16)))))
					.map(j -> j.split(""))
					.flatMap(j -> Arrays.stream(j))
					.mapToInt(j -> Integer.parseInt(j))
					.map(j -> j * -1).toArray();
		}
		
		int regionCount = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid.length; k++) {

				if (grid[i][k] == -1) {
					regionCount++;
					Queue<Field> groupFields = new LinkedList<Field>();
					groupFields.add(new Field(i, k));
					while (groupFields.size() > 0) {
						Field field = groupFields.poll();
						grid[field.x][field.y] = regionCount;
						checkAndAddNeigbourgh(grid, field.x + 1, field.y, groupFields);
						checkAndAddNeigbourgh(grid, field.x, field.y + 1, groupFields);
						checkAndAddNeigbourgh(grid, field.x - 1, field.y, groupFields);
						checkAndAddNeigbourgh(grid, field.x, field.y - 1, groupFields);
					}

				}
			}
		}
		System.out.println(regionCount);
	}

	private void checkAndAddNeigbourgh(int[][] grid, int x, int y, Queue<Field> groupFields) {
		if (x < 0 || y < 0 || x > 127 || y > 127) {
			return;
		}
		if (grid[x][y] == -1) {
			Field field = new Field(x, y);
			if (!groupFields.contains(field)) {
				groupFields.add(field);
			}
		}
	}
	
	private class Field {
		int x;
		int y;
		public Field(int x, int y) {
			this.x = x;
			this.y = y;
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
		
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}
}
