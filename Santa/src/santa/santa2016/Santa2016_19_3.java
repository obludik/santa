package santa.santa2016;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Santa2016_19_3 {

    private static final int INPUT = 3018458;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        List<Integer> numbers = new LinkedList<>();
        for (int i = 1; i <= INPUT; i++) {
            numbers.add(i);
        }

        // 1424135
        int index = 0;
        int opposite = 0;
        int oppositeIndex = 0;

        int indexOfTaker = 0;
        opposite = numbers.size() / 2;
        oppositeIndex = (indexOfTaker + opposite) % numbers.size();

        while (numbers.size() > 1) {
            index = 0;
            for (Iterator iterator = numbers.iterator(); iterator.hasNext();) {
                if (index == oppositeIndex) {
                    boolean odd = (numbers.size() % 2) == 0;
                    indexOfTaker = remove(iterator, index, indexOfTaker, numbers);
                    if (odd) {
                        indexOfTaker = remove(iterator, index, indexOfTaker, numbers);                    
                    } 
                }
                if (index > oppositeIndex) {
                    indexOfTaker = remove(iterator, index, indexOfTaker, numbers);
                    indexOfTaker = remove(iterator, index, indexOfTaker, numbers);
                }
                if (iterator.hasNext()) {
                    iterator.next();
                }
                index++;
            }

            opposite = numbers.size() / 2;
            oppositeIndex = (indexOfTaker + opposite) % numbers.size();
        }

        System.out.println(numbers.get(0));

        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + endTime + " ms");
        System.out.println("Time: " + endTime / 1000 + "." + endTime % 1000 + " s");
    }
    
    private static int remove(Iterator iterator, int index, int indexOfTaker, List<Integer> numbers) {
        if (iterator.hasNext() && numbers.size() > 1) {
            iterator.next();
            iterator.remove();
            if (index > indexOfTaker) {
                indexOfTaker++;
            }
            indexOfTaker = indexOfTaker % numbers.size();
        }
        return indexOfTaker;
    }

}
