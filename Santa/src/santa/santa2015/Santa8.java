package santa.santa2015;

import java.util.List;

public class Santa8 {

    public static void main(String[] args) {
        List<String> data = FileReader.read("D:\\temp\\input8.txt");
        int countData = 0;
        int countAll = 0;
        for (String string : data) {
            int allCount = string.trim().length();
            countAll += allCount;
            string = string.trim().substring(1, string.length() - 1).trim();  /*uvozovky*/
            int clearCount =  (string.length() - getCount(string, "\\\"") - getCount(string, "\\\\") 
                    - 3 * getCountHex(string));            
            countData += clearCount;
            System.out.println(allCount);
            System.out.println(clearCount);
            System.out.println(countAll - countData);
            System.out.println(string + "----------");
        }
        
        int result = countAll - countData;
        System.out.println("Výsledek: " + result);
        
        countData = 0;
        countAll = 0;
        for (String string : data) {
            int allCount = string.trim().length();
            countAll += allCount;
            int clearCount = (string.length() + getCount(string, "\"") + getCount(string, "\\"));
            countData += clearCount + 2;
            System.out.println(countData - countAll);
            System.out.println(string + "----------");
        }

        System.out.println("Výsledek 1: " + result);
        System.out.println("Výsledek 2: " + (countData - countAll));
    }
    
    private static int getCount(String a, String pattern) {
        int i = 0;
        int counter = 0;
        i = a.indexOf(pattern);
        while (i > -1) {
            counter++;
            i = a.indexOf(pattern, i+pattern.length());
        }
        System.out.println(pattern + " " + a + " " + counter);
        return counter;
    }
    
    private static int getCountHex(String a) {        
        int i = 0;
        int counter = 0;
        i = a.indexOf("\\x");
        while (i > -1) {
            int c = 0;
            for (int j = i; j >= 0; j--) {
                if (a.charAt(j) == '\\') {
                    c++;
                } else {
                    break;
                }
            }
            if (c % 2 == 1) {
                counter++;
            }
            i = a.indexOf("\\x", i+2);
        }
        System.out.println("\\x " + a + " " + counter);
        return counter;       
    }
}
