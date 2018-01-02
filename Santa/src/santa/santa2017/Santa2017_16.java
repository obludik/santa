package santa.santa2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import santa.SantaIssue;

public class Santa2017_16 implements SantaIssue {

	private static final Pattern PATTERN = Pattern.compile("[sxp]([\\da-p]*)\\/?([\\da-p]*)");
	private static final String INPUT = "abcdefghijklmnop";
	
	public void solvePart1(String data, List<String> dataLines) {		
		String[] moves = data.split(",");
		System.out.println(processMoves(INPUT, moves));
		
	}
	
	public String processMoves(String in, String[] moves) {
		StringBuilder input = new StringBuilder(in);
		for (String move : moves) {
			Matcher m = PATTERN.matcher(move);
			m.find();
			switch (move.charAt(0)) {
			case 's':
				int spin = Integer.parseInt(m.group(1));
				input = new StringBuilder(input.substring(input.length() - spin, input.length())
						+ input.substring(0, input.length() - spin));
				break;
			case 'x':
				int indexA = Integer.parseInt(m.group(1));
				int indexB = Integer.parseInt(m.group(2));
				char charA = input.charAt(indexA);
				input.replace(indexA, indexA + 1, String.valueOf(input.charAt(indexB)));
				input.replace(indexB, indexB + 1, String.valueOf(charA));
				break;
			case 'p':
				indexA = input.indexOf(m.group(1));
				indexB = input.indexOf(m.group(2));
				charA = input.charAt(indexA);
				input.replace(indexA, indexA + 1, String.valueOf(input.charAt(indexB)));
				input.replace(indexB, indexB + 1, String.valueOf(charA));
				break;
			}
		}
		return input.toString();
	}


	public void solvePart2(String data, List<String> dataLines) {

		String[] moves = data.split(",");
		Map<String, String> results = new HashMap<>();
		boolean cycleStart = false;
		String cycleStr = null;
		int cycleLen = 0;
		String in = INPUT;
		int cycleStartIndex = 0;
		String out = null;
		for (int i = 0; i < 1000000000; i++) {
			if (results.containsKey(in)) {
				if (!cycleStart) {
					cycleStart = true;
					cycleStr = in;
					cycleStartIndex = i;
				} else if (cycleStr.equals(in)) {
					break;
				}
				cycleLen++;
				in = results.get(in);
				continue;
			}
			out = processMoves(in, moves);
			results.put(in, out);
			in = out;
		}
		in = INPUT;
		for (int i = 0; i < (1000000000 % cycleLen) + cycleStartIndex; i++) {
			out = processMoves(in, moves);
			in = out;
		}
		System.out.println(out);
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}
}
