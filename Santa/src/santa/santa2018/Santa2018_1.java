package santa.santa2018;

import java.util.ArrayList;
import java.util.List;

import santa.SantaIssue;

public class Santa2018_1 implements SantaIssue {
	
	public void solvePart1(String data, List<String> dataLines) {
		long result = 0;
		for (String input : dataLines) {			
			result += Integer.parseInt(input);
		}
		System.out.println(result);
	}
	
	public void solvePart2(String data, List<String> dataLines) {
		long result = 0;
		boolean found = false;
		List<Long> results = new ArrayList<>();
		results.add(0L);
		while (!found) {
			for (String input : dataLines) {			
				result += Integer.parseInt(input);
				if (!found && results.contains(result)) {
					found = true;
					System.out.println(result);
					break;
				}
				results.add(result);
			}
		}
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}

}
