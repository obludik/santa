package santa.santa2017;

import java.util.List;

import santa.SantaIssue;

public class Santa2017_11 implements SantaIssue {

	public void solve(String data, List<String> dataLines) {
		String[] instructions = data.split(",");
		int x = 0;
		int y = 0;		
		int z = 0;
		
		int maxDistance = 0;
		
		for (String instr : instructions) {
			switch (instr) {
			case "n":
				y++;
				z--;
				break;
			case "s":
				y--;
				z++;
				break;
			case "ne":
				x++;
				z--;
				break;
			case "nw":
				y++;
				x--;
				break;	
			case "se":
				y--;
				x++;
				break;
			case "sw":
				x--;
				z++;
				break;				
			default:
				break;
			}
			
			int dist = (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2;
			if (dist > maxDistance) {
				maxDistance = dist;
			}
		}
		
		System.out.println("Distance: " + (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2);
		System.out.println("Max distance: " + maxDistance);
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solve(data, dataLines);
	}

}
