package santa.santa2017;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import santa.SantaIssue;

public class Santa2017_4 implements SantaIssue {

	private static final String DELIMITER = " ";

	@Override
	public void solvePart1(String data, List<String> dataLines) {
		int sum = 0;
		for (String line : dataLines) {
			String[] split = line.split(DELIMITER);
			long count = Arrays.stream(split).distinct().count();
			if (count == split.length) {
				sum++;
			}
		}
		System.out.println(sum);
	}

	@Override
	public void solvePart2(String data, List<String> dataLines) {
		int sum = 0;
		for (String line : dataLines) {
			String[] split = line.split(DELIMITER);
			for (int i = 0; i < split.length; i++) {
				char[] arr = split[i].toCharArray();
				Arrays.sort(arr);
				split[i] = String.valueOf(arr);
			} 
			long count = Arrays.stream(split).distinct().count();
			if (count == split.length) {
				sum++;
			}
		}
		System.out.println(sum);
	}
}
