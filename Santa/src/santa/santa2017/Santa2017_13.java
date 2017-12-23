package santa.santa2017;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import santa.SantaIssue;

public class Santa2017_13 implements SantaIssue {

	public void solvePart1(String data, List<String> dataLines) {
		Map<Integer, Integer> input = dataLines.stream().map(i -> i.split(": "))
				.collect(Collectors.toMap(i -> Integer.parseInt(i[0]), i -> Integer.parseInt(i[1])));

		int sum = 0;
		for (Integer depth : input.keySet()) {
			int range = input.get(depth);
			if (depth % (2 * (range - 1)) == 0) {
				sum += depth * range;
			}

		}
		System.out.println(sum);
	}

	public void solvePart2(String data, List<String> dataLines) {
		Map<Integer, Integer> input = dataLines.stream().map(i -> i.split(": "))
				.collect(Collectors.toMap(i -> Integer.parseInt(i[0]), i -> Integer.parseInt(i[1])));

		int delay = -1;
		boolean catched = true;
		while (catched == true) {
			delay++;
			catched = false;
			for (Integer depth : input.keySet()) {
				int range = input.get(depth);
				if ((depth + delay) % (2 * (range - 1)) == 0) {
					catched = true;
					break;
				}
			}

		}
		System.out.println(delay);
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}

}
