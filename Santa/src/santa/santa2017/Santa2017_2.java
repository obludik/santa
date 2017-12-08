package santa.santa2017;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import santa.SantaIssue;

public class Santa2017_2 implements SantaIssue {

	private static final String DELIMITER = "\\t";

	public void solvePart1(String data, List<String> dataLines) {
		int checksum = 0;
		for (String line : dataLines) {
			String[] split = line.split(DELIMITER);
			OptionalInt max = Arrays.stream(split).mapToInt(Integer::parseInt).max();
			OptionalInt min = Arrays.stream(split).mapToInt(Integer::parseInt).min();
			checksum += max.getAsInt() - min.getAsInt();
		}
		System.out.println(checksum);
	}

	public void solvePart2(String data, List<String> dataLines) {
		int checksum = 0;
		for (String line : dataLines) {
			int[] numbers = Arrays.stream(line.split(DELIMITER)).mapToInt(Integer::parseInt).toArray();
			checksum += findDivisors(numbers);
			
		}
		System.out.println(checksum);
	}

	private int findDivisors(int[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			int num1 = numbers[i];
			for (int j = i + 1; j < numbers.length; j++) {
				int num2 = numbers[j];
				if (num1 % num2 == 0) {
					return num1 / num2;
				}
				if (num2 % num1 == 0) {
					return num2 / num1;
				}
			}
		}
		return 0;
	}
	
	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}
}
