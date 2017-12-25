package santa.santa2017;

import java.util.ArrayList;
import java.util.List;

import santa.SantaIssue;

public class Santa2017_15 implements SantaIssue {

	public static final int INPUT_A = 277;
	public static final int FACTOR_A = 16807;
	public static final int MULTIPLAYER_A = 4;
	public static final int INPUT_B = 349;
	public static final int FACTOR_B = 48271;
	public static final int MULTIPLAYER_B = 8;
	public static final int DIVISOR = 2147483647;

	public void solvePart1(String data, List<String> dataLines) {
		long resultA = INPUT_A;
		long resultB = INPUT_B;

		int count = 0;
		for (int i = 0; i < 40000000; i++) {
			resultA = (resultA * FACTOR_A) % DIVISOR;
			resultB = (resultB * FACTOR_B) % DIVISOR;
			if ((resultA & 65535) == (resultB & 65535)) {
				count++;
			}
		}
		System.out.println(count);
	}

	public void solvePart2(String data, List<String> dataLines) {
		long resultA = INPUT_A;
		long resultB = INPUT_B;

		int count = 0;
		List<Long> resultsA = new ArrayList<>();
		List<Long> resultsB = new ArrayList<>();
		
		while (resultsA.size() < 5000000) {
			resultA = (resultA * FACTOR_A) % DIVISOR;
			if ((resultA % MULTIPLAYER_A) == 0) {
				resultsA.add(resultA);
			}
		}
		while (resultsB.size() < 5000000) {
			resultB = (resultB * FACTOR_B) % DIVISOR;
			if ((resultB % MULTIPLAYER_B) == 0) {
				resultsB.add(resultB);
			}

		}
		for (int i = 0; i < 5000000; i++) {
			if ((resultsA.get(i) & 65535) == (resultsB.get(i) & 65535)) {
				count++;
			}
		}
		System.out.println(count);
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}
}
