package santa.santa2016;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Santa2016_19_2 {

	private static final int INPUT = 3018458;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		List<Integer> numbers = new ArrayList<>();
		for (int i = 1; i <= INPUT; i++) {
			numbers.add(i);
		}

		List<Integer> temp = new ArrayList<>();
		
		int index = 0;
		while (numbers.size() > 1) {
		//	System.out.println(index);	
		//	temp = new ArrayList<>();
			int opposite = numbers.size() / 2;
			opposite = (index + opposite) % numbers.size();
			numbers.remove(opposite);
			
			/*for (int i = 0; i < numbers.size(); i++) {
				if (i != opposite) {
					temp.add(numbers.get(i));
				}
			}*/
			
			//System.out.println(numbers.toString());	
			index = (index + 1) <= numbers.size() ? (index + 1) : 0;
		//	numbers = temp;
		}
		

		System.out.println(numbers.get(0));

		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("Time: " + endTime + " ms");
		System.out.println("Time: " + endTime / 1000 + "." + endTime % 1000 + " s");
	}

}
