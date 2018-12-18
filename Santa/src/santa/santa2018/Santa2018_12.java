package santa.santa2018;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import santa.SantaIssue;

public class Santa2018_12 implements SantaIssue {

	private static final Pattern PATTERN = Pattern.compile("([#.]*) => #");
	private static final String INITIAL_STATE = "..#..####.##.####...#....#######..#.#..#..#.#.#####.######..#.#.#.#..##.###.#....####.#.#....#.#####";

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		List<String> patterns = new ArrayList<>();
		for (String line : dataLines) {
			Matcher m = PATTERN.matcher(line);

			if (m.find()) {
				patterns.add(m.group(1));
			};
		}
		String lastState = INITIAL_STATE;
		lastState = "...." + lastState + "....";
		int startIndex = 2;
		String last = "";
		int previousRowStartIndex = 0;
		int sameRowStartIndex = 0;
		int sameRowIndex = 0;
			for (int i = 0; i < 500; i++) {
			StringBuilder nextState = new StringBuilder();
			for (int j = 2; j < lastState.length() - 2; j++) {
				if (patterns.contains(lastState.substring(j-2, j+3))) {
					nextState.append("#");
				} else {
					nextState.append(".");
				}
				
			}			
			String next = nextState.toString();
				if (next.equals(last)) {
					sameRowStartIndex = startIndex;
					sameRowIndex = i;
					break;
				}
			
			if (i == 19) {
				long sum = 0;
				char[] pots = next.toCharArray();
				for (int j = 0; j < pots.length; j++) {
					if (pots[j] == '#') {
						sum += j - startIndex;
					}
				}
				System.out.println("Part 1 sum: " + sum);
			}
			
			System.out.println(i + " " + startIndex + "->" + next);
			previousRowStartIndex = startIndex;
			last = next;
			int sharpIndex = next.indexOf("#");
			int lastSharpIndex=next.lastIndexOf("#");
			lastState =  "...." + next.substring(sharpIndex,lastSharpIndex+1).toString() + "....";
			
			startIndex += 2 - sharpIndex;
		}
			int difference = sameRowStartIndex - previousRowStartIndex;
			long start = ((49999999999L - sameRowIndex) * difference) + sameRowStartIndex;
		
			System.out.println(start);
			
			long sum = 0;		      
			char[] pots = last.toCharArray();
			for (int i = 0; i < pots.length; i++) {
				if (pots[i] == '#') {
					sum += i - start;
				}
				
			}
			System.out.println(sum);		
	}
}
