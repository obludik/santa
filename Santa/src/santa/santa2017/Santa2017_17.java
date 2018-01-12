package santa.santa2017;

import java.util.ArrayList;
import java.util.List;

import santa.SantaIssue;

public class Santa2017_17 implements SantaIssue {

	public static final int STEPS = 354;

	public void solvePart1(String data, List<String> dataLines) {
		List<Integer> numbers = new ArrayList<>();
		numbers.add(0);
		int current = 1;
		int actualIndex = 0;
		for (int i = 0; i < 2017; i++) {
			actualIndex = (actualIndex + STEPS) % numbers.size() + 1;
			numbers.add(actualIndex, current++);
		}
		System.out.println(numbers.get(numbers.indexOf(2017) + 1));
	}

	public void solvePart2(String data, List<String> dataLines) {
		int size = 1;
		int actualIndex = 0;
		int numAfterZero = 0;
		for (int i = 1; i < 50000001; i++) {
			actualIndex = (actualIndex + STEPS) % size + 1;
			if (actualIndex == 1) {
				numAfterZero = i;
			}
			size++;
		}
		System.out.println(numAfterZero);
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}
}
