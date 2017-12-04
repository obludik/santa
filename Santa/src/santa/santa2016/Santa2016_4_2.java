package santa.santa2016;

import java.io.File;
import java.util.List;

import file.FileReaderImpl;

public class Santa2016_4_2 {

    public static void main(String[] args) {
        file.FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_4").getAbsolutePath());    
        long startTime = System.currentTimeMillis();

        int result = 0;
        for (String row : data) {
            String newRow = "";
            int num = isReal(row);
            if (num > 0) {
                for (int i = 0; i < row.length(); i++) {
                    int character =  ((int)row.charAt(i)) - 97;
                    int newChar = ((character + num) % 26) + 97;
                    newRow += (char)newChar;
                }
                if (newRow.indexOf("northpole") > -1) {
                    System.out.println(newRow);
                    System.out.println(num);
               }
            }
            
        }
        System.out.println("Result: " + result);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }
    
    private static int isReal(String row) {
        int[] letters = new int[26];
        
        String code = row.substring(row.indexOf("[") + 1, row.indexOf("]"));
        String sector = "";
        
        for (int i = 0; i < row.length(); i++) {
            char letter = row.charAt(i);
            int l = (int) letter;
            if (l >= 97 && l <= 122) {
                letters[l - 97]++;
            }
            if (l >= 48 && l <= 57) {
                sector = sector.concat(String.valueOf(letter));
            }
            if (letter == '[') {
                break;
            }
        }
        int sectorNum = Integer.valueOf(sector);
        
        int[] result = new int[5];
        for (int i = 0; i < 5;) {
            int max = 0;
            for (int j : letters) {
                if (j > max) {
                    max = j;
                }
            }
            for (int j = 0; j < letters.length; j++) {
                if (letters[j] == max) {
                    result[i] = j;
                    i++;
                    letters[j] = 0;
                    if (i == 5) {
                        break;
                    }
                }
            }
        }
        
        for (int i = 0; i < code.length(); i++) {
            if ((int)code.charAt(i) != (result[i] + 97)) {
                return 0;
            }
        }
        return sectorNum;
    }
  
}
