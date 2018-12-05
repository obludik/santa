package santa.santa2018;

import java.util.List;

import santa.SantaIssue;

public class Santa2018_5 implements SantaIssue {

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		System.out.println("Part 1: " + process(new StringBuilder(data)).length());
		int minSize = Integer.MAX_VALUE;
		for (int i = 65; i <= 90; i++) {
			String input = data.replace((char)i, ' ').replace((char)(i + 32), ' ').replaceAll(" ", "");
			int size = process(new StringBuilder(input)).length();
			if (size < minSize) {
				minSize = size;
			}
		}
		System.out.println("Part 2: " + minSize);
	}
	
	private StringBuilder process(StringBuilder data) {
		int origSize = data.length();
		for (int i = 0; i < data.length() - 1; i++) {
			if (Math.abs((int)data.charAt(i) - ((int)data.charAt(i+1))) == 32) {
				data.replace(i, i+2, "");
			}
		}		
		if (data.length() == origSize) {
			return data;
		} else {
			process(data);
		}
		return data;
	}
	
}
