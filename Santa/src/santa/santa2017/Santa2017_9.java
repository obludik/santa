package santa.santa2017;

import java.util.List;

import santa.SantaIssue;

public class Santa2017_9 implements SantaIssue {
	
	
	public void solve(String data, List<String> dataLines) {
		boolean inGarbage = false;
		char[] chars = data.toCharArray();
		int sum = 0;
		int level = 0;
		int garbageCount = 0;
		for (int i = 0; i < chars.length; i++) {
			switch (chars[i]) {
			case '!':
				if (inGarbage) {
					i++;
				}
				break;
			case '{':
				if (!inGarbage) {
					sum += 1 + level;
					level++;
				} else {
					garbageCount++;
				}
				break;
			case '}':
				if (!inGarbage) {
					level--;
				} else {
					garbageCount++;
				}
				break;
			case '<':
				if (inGarbage) {
					garbageCount++;
				}
				inGarbage = true;				
				break;
			case '>':
				inGarbage = false;
				break;
			default:
				if (inGarbage) {
					garbageCount++;
				}
				break;
			}
		}
		System.out.println(sum);
		System.out.println(garbageCount);		
	}
	
	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solve(data, dataLines);
	}

}
