package santa.santa2017;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import santa.SantaIssue;

public class Santa2017_1 implements SantaIssue {

	public void solvePart1(String data, List<String> dataLines) {
		data = data + data.substring(0, 1);
		Pattern p = Pattern.compile("([0-9])\\1+");
		Matcher m = p.matcher(data);
		int count = 0;
		while (m.find()) {
			String group = m.group();
			count += ((group.length() - 1) * Integer.valueOf(group.substring(0, 1)));
			 
		}
		System.out.println(count);		
	}

	public void solvePart2(String data, List<String> dataLines) {
		int count = 0;
		int circ = data.length() / 2;
		for (int i = 0; i < data.length() / 2; i++) {
			int digit = Integer.valueOf(data.substring(i, i + 1));
			int nextIndex = (i + circ) % data.length();
			int compare = Integer.valueOf(data.substring(nextIndex, nextIndex + 1));
			if (digit == compare) {
				count += digit + compare;
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
