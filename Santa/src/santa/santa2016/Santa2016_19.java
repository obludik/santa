package santa.santa2016;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Santa2016_19 {

	private static final int INPUT = 3018458;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		List<Integer> numbers = new ArrayList<>();
		for (int i = 1; i <= INPUT; i++) {
			numbers.add(i);
		}

		List<Integer> temp = new ArrayList<>();

		while (numbers.size() > 1) {
			temp = new ArrayList<>();
			for (Iterator iterator = numbers.iterator(); iterator.hasNext();) {
				Integer i = (Integer) iterator.next();
				temp.add(i);
				if (iterator.hasNext()) {
					iterator.next();
				}
			}
			if (numbers.size() % 2 == 1) {
				temp.remove(0);
			}
			numbers = temp;
		}

		System.out.println(numbers.get(0));

		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("Time: " + endTime + " ms");
		System.out.println("Time: " + endTime / 1000 + "." + endTime % 1000 + " s");
	}

}
