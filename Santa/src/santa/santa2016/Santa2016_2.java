package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import file.FileReaderImpl;

public class Santa2016_2 {

    public static void main(String[] args) {
        file.FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_2").getAbsolutePath());

        long startTime = System.currentTimeMillis();

        int[][] numbers = new int[][] {{7,4,1},{8,5,2},{9,6,3}};
        int[] currentPosition = new int[] {1,1};
        
        for (String row : data) {
            for (int i = 0; i < row.length(); i++) {
                switch (row.charAt(i)) {
                case 'U':
                    currentPosition[1] = increaseIndex(currentPosition[1]);
                    break;
                case 'R':
                    currentPosition[0] = increaseIndex(currentPosition[0]);
                    break;
                case 'D':
                    currentPosition[1] = decreaseIndex(currentPosition[1]);
                    break;
                case 'L':
                    currentPosition[0] = decreaseIndex(currentPosition[0]);
                    break;                    
                default:
                    break;
                } 
            }
            System.out.println(currentPosition[0] + " " + currentPosition[1] + "--" + numbers[currentPosition[0]][currentPosition[1]]);
        }
        
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }
    
    private static int increaseIndex(int current) {
        current += 1;
        if (current > 2) {
            current -= 1;
        }
        return current;
    }
    
    private static int decreaseIndex(int current) {
        current -= 1;
        if (current < 0) {
            current += 1;
        }
        return current;
    }
}
