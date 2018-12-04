package santa.santa2018;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import santa.SantaIssue;

public class Santa2018_3 implements SantaIssue {
	
	private static final Pattern PATTERN = Pattern.compile("#([0-9]*)[ ]@[ ]([0-9]*),([0-9]*):[ ]([0-9]*)[x]([0-9]*)");
	
	public void solvePart12(String data, List<String> dataLines) {
		int[][] fabric = new int[1500][1500];
		List<Area> areas = new ArrayList<>();
		for (String line : dataLines) {
			Matcher m = PATTERN.matcher(line);
			m.find();
			int id = Integer.parseInt(m.group(1));
			int x = Integer.parseInt(m.group(2));
			int y = Integer.parseInt(m.group(3));
			int lenX = Integer.parseInt(m.group(4));
			int lenY = Integer.parseInt(m.group(5));
			areas.add(new Area(id, x, y, lenX, lenY));
			for (int i = x + 1; i <= x + lenX; i++) {
				for (int j = y + 1; j <= y + lenY; j++) {
					fabric[i][j]++;
				}
			}
		}
		int count = 0;
		for (int i = 0; i < fabric.length; i++) {
			for (int j = 0; j < fabric.length; j++) {
				if (fabric[i][j] >= 2) {
					count++;
				}
			}
		}

		System.out.println("Part 1: " + count);
		
		for (Area area : areas) {
			boolean found = true;
			for (int i = area.x + 1; i <= area.x + area.lenX; i++) {
				for (int j = area.y + 1; j <= area.y + area.lenY; j++) {
					if (fabric[i][j] != 1) {
						found = false;
					}
				}
			}
			if (found) {
				System.out.println("Part 2: " + area.id);
				break;
			}
		}
		
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart12(data, dataLines);
	}
	
	private class Area {
		int id;
		int x;
		int y;
		int lenX;
		int lenY;
		
		public Area(int id, int x, int y, int lenX, int lenY) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.lenX = lenX;
			this.lenY = lenY;
		}
	}

}
