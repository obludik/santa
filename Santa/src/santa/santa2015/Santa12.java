package santa.santa2015;

import java.util.ArrayList;
import java.util.List;

public class Santa12 {

	public static void main(String[] args) {

		String data = FileReader.read("F:\\temp\\santa_12.txt").get(0);

		int sum = 0;
		sum = getNumbers(data, sum);
		System.out.println(sum);

		List<String> betweenBrackets = new ArrayList<String>();
		int left = 0;
		int right = 0;

		int indexLeft = data.indexOf('{', 2);
		for (int i = 1; i < data.length() - 1; i++) {
			if (data.charAt(i) == '{') {
				left++;
			}
			if (data.charAt(i) == '}') {
				right++;
				if (left == right) {
					left = 0;
					right = 0;
					betweenBrackets.add(data.substring(indexLeft, i + 1));
					indexLeft = data.indexOf('{', i + 1);
					left++;
					i = indexLeft;
				}
			}
		}

		List<String> betweenRedBrackets = new ArrayList<String>();
		List<String> betweenRedBracketsUnique = new ArrayList<String>();
		for (String string : betweenBrackets) {
			betweenRedBrackets = new ArrayList<String>();
			int g = string.indexOf(":\"red\"");
			while (g > -1) {
				int endBracket = 0;
				// hledam koncovou zavorku ze red blokem
				int bracketCounter = 0;
				for (int i = g + 1; i < string.length(); i++) {
					if (string.charAt(i) == '{') {
						bracketCounter++;
					}
					if (string.charAt(i) == '}') {
						if (bracketCounter == 0) {
							endBracket = i;
							break;
						} else {
							bracketCounter--;
						}
					}
				}

				// hledam pocatecni zavorku pred red blokem
				left = 0;
				right = 0;
				for (int i = endBracket; i >= 0; i--) {
					if (string.charAt(i) == '{') {
						left++;
						if (left == right) {
							left = 0;
							right = 0;
							betweenRedBrackets.add(string.substring(i,
									endBracket + 1));
							break;
						}
					}
					if (string.charAt(i) == '}') {
						right++;
					}
				}
				g = string.indexOf(":\"red\"", g + 1);
			}
			for (String s1 : betweenRedBrackets) {
				boolean add = true;
				for (String s : betweenRedBrackets) {
					if (s.contains(s1) && !s.equals(s1)) {
						add = false;
					}
				}
				if (add) {
					betweenRedBracketsUnique.add(s1);
				}

			}
		}
		for (String string : betweenRedBracketsUnique) {
			data = data.replace(string, "");
			System.out.println(string);

		}
		sum = 0;
		System.out.println(getNumbers(data, sum));
		
	}

	private static int getNumbers(String string, int sum) {
		for (int i = 0; i < string.length(); i++) {
			int c = string.charAt(i);
			if (c == '-') {
				String num = "-";
				c = string.charAt(++i);
				while (c < 58 && c > 47) {
					num += string.charAt(i);
					c = string.charAt(++i);
				}
				if (num.length() > 0) {
					sum += Integer.parseInt(num);
					// System.out.println(num);
				}
			} else {
				String num = "";
				while (c < 58 && c > 47) {
					num += string.charAt(i);
					c = string.charAt(++i);
				}
				if (num.length() > 0) {
					sum += Integer.parseInt(num);
					// System.out.println(num);
				}
			}

		}
		return sum;
	}
}
