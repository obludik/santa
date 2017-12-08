package santa.santa2017;

import java.util.List;

import santa.SantaIssue;

public class Santa2017_5 implements SantaIssue {

	public void solvePart1(String data, List<String> dataLines) {
		int[] numbers = dataLines.stream().mapToInt(Integer::parseInt).toArray();
		int index = 0;
		int steps = 0;
		while (index < numbers.length) {
			int len = numbers[index];
			numbers[index]++;
			index = index + len;
			steps++;
		}
		System.out.println(steps);
	}

	public void solvePart2(String data, List<String> dataLines) {
		int[] numbers = dataLines.stream().mapToInt(Integer::parseInt).toArray();
		int index = 0;
		int steps = 0;
		while (index < numbers.length) {
			int len = numbers[index];
			if (len >= 3) {
				numbers[index]--;
			} else {
				numbers[index]++;
			}
			index = index + len;
			steps++;
		}
		System.out.println(steps);
		
	}
	
	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}
}
