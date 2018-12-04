package santa.santa2018;

import java.util.List;

import santa.SantaIssue;

public class Santa2018_2 implements SantaIssue {
	
	public void solvePart1(String data, List<String> dataLines) {
		int doubles = 0;
		int tripples = 0;

		for (String line : dataLines) {
			char[] chars = line.toCharArray();
			boolean incDouble = false;
			boolean incTripple = false;
			for (int i = 0; i < chars.length; i++) {
				int ch = chars[i];
				long count = line.chars().filter(num -> num == ch).count();
				if (count == 2) {
					incDouble = true;
				}
				if (count == 3) {
					incTripple = true;
				}
			}
			if (incDouble) {
				doubles++;
			}
			if (incTripple) {
				tripples++;
			}
		}
		System.out.println(doubles);
		System.out.println(tripples);
		System.out.println(doubles * tripples);
	}
	
	public void solvePart2(String data, List<String> dataLines) {
		for (String line : dataLines) {
			char[] chars = line.toCharArray();
			for (String line2 : dataLines) {				
				char[] chars2 = line2.toCharArray();
				int diffCount = 0;
				int index = 0;
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] != chars2[i]) {
						diffCount++;
						index = i;
					}
				}
				
				if (diffCount == 1) {
					System.out.println(line.substring(0, index) + line.substring(index + 1, line.length()));
					System.exit(0);
				}
			}
		}
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}

}
