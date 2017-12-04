package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import file.FileReaderImpl;

public class Santa2016_3_2 {

    public static void main(String[] args) {
        file.FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_3").getAbsolutePath());    
        long startTime = System.currentTimeMillis();
        
        int count = 0;
        List<String> list = new ArrayList<String>();
        for (String string : data) {
            String[] input = string.split("  ");           
            for (String string2 : input) {
                if (string2.trim().length() > 0) {
                    list.add(string2);
                }
            }
        }
        
        for (int i = 0; (i + 5) < list.size(); i+=9) {
            int a = Integer.parseInt(list.get(i).trim());
            int b = Integer.parseInt(list.get(i+3).trim());
            int c = Integer.parseInt(list.get(i+6).trim());
            
            if (isTriangle(a, b, c)) {
                count++;
            }
        }         
        
        for (int i = 1; (i + 5) < list.size(); i+=9) {
            int a = Integer.parseInt(list.get(i).trim());
            int b = Integer.parseInt(list.get(i+3).trim());
            int c = Integer.parseInt(list.get(i+6).trim());
            
            if (isTriangle(a, b, c)) {
                count++;
            }
        }         
        
        for (int i = 2; (i + 5) < list.size(); i+=9) {
            int a = Integer.parseInt(list.get(i).trim());
            int b = Integer.parseInt(list.get(i+3).trim());
            int c = Integer.parseInt(list.get(i+6).trim());
            
            if (isTriangle(a, b, c)) {
                count++;
            }
        } 

        System.out.println("Count: " + count);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }
    
    private static boolean isTriangle(int a, int b, int c) {
        if ((a + b) <= c) {
            return false;
        }
        if ((a + c) <= b) {
            return false;
        }
        if ((c + b) <= a) {
            return false;
        }
        return true;
    }
}
