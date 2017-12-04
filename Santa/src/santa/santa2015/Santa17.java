package santa.santa2015;

import java.util.ArrayList;
import java.util.List;

public class Santa17 {
	
	private static List<String> results = new ArrayList<String>();
	private static int min = 20;

	public static void main(String[] args) {
		int[] containers = new int[] { 7, 10, 11, 18, 18, 21, 22, 24, 26, 32,
				36, 40, 40, 42, 43, 44, 46, 47, 49, 50 };

		run(new ArrayList<Integer>(), containers, 0, containers.length, 0);
		int minimumCount = 0;
		for (String string : results) {
			if (string.split("-").length == 4) {
				minimumCount++;
			}

		}
		System.out.println(minimumCount);
		System.out.println(results.size());

	}

	public static void run(List<Integer> currentData, int[] data,
			int fromIndex, int endIndex, int sum) {

		if (sum == 150) {
			StringBuilder sb = new StringBuilder();
			sb.append(150).append(" = ");
			for (Integer i : currentData) {
				sb.append(i).append("-");
			}
			String result = sb.deleteCharAt(sb.length() - 1).toString();
			results.add(result);
			System.out.println(result);
			if (currentData.size() < min) {
				min = currentData.size();
			}
		}

		for (int currentIndex = fromIndex; currentIndex < endIndex; currentIndex++) {
			if (sum + data[currentIndex] <= 150) {
				currentData.add(data[currentIndex]);
				sum += data[currentIndex];
				run(currentData, data, currentIndex + 1, endIndex, sum);
				sum -= currentData.get(currentData.size() - 1);
				currentData.remove(currentData.size() - 1);
			}
		}
	}
}
