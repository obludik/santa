package santa.santa2015;

import java.util.Arrays;

public class Santa11 {
    
    static char[] arr;
    static int max = 0;
    
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        String input = "cqjxjnds";
        
        arr = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            arr[i] = input.charAt(i);
        }
        run(0);
    }
    
    private static void run(int index)  {
        for (int l = 0; l < 30; l++) {
            if (index == 7) {
                if (!setValue(index)) {
                    break;
                }
                
                if (checkWord(arr)) {  
                    System.out.println("OK" + Arrays.toString(arr));
                    System.out.println(System.currentTimeMillis());
                    max++;
                    if (max == 5) {
                        System.exit(0);
                    }
                }
                
            } else {
                run(index+1);
                if (!setValue(index)) {
                    break;
                }
            }

            
        }
    }
    
    private static boolean setValue(int index) {
        if (arr[index] == 122) {
            arr[index] = 97;
            return false;
        } else {
            arr[index] += 1;
        }
        return true;
    }
     
    private static boolean checkWord(char[] word) {
        
        for (int i = 0; i < word.length; i++) {
            if (word[i] == 'l'
                    || word[i] == 'i'
                    || word[i] == 'o') {
                return false;
            }
        }
        
        char lastChar = word[0];
        int countDouble = 0;
        for (int i = 1; i < word.length; i++) {
            if (word[i] == lastChar) {
                countDouble++;
                i++;
            }
            if (i < word.length) {
                lastChar = word[i];
            }
        }
        if (countDouble < 2) {
            return false;
        }
        
        lastChar = word[0];
        int countTripple = 0;
        for (int i = 0; i < word.length - 2; i++) {
            int l = word[i];
            int k = word[i+1];
            int m = word[i+2];
            if (k == l+1 && m == k+1) {
                countTripple++;
            }
        }
        if (countTripple < 1) {
            return false;
        }
        
        return true;
    }
}

