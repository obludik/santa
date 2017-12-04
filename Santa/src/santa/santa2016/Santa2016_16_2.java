package santa.santa2016;

public class Santa2016_16_2 {

    private static final String IN = "00111101111101000";
    private static final String IN_REV = "11101000001000011";
    //private static final String IN = "10000";
    //private static final String IN_REV = "11110";
    private static final int IN_LEN = IN.length();
    private static final int LEN = 35651584;
    
    public static void main(String[] args) {    
        long startTime = System.currentTimeMillis();

        int howManyRounds = 0;
        int num = LEN;
        while(num % 2 == 0) {
            num = num / 2;
            howManyRounds++;
        }
                
        // O - orig, R - reversed, changed, 0-1
        String currentPattern = "O";
        int origLen = 0;
        while (origLen < LEN) {
            StringBuilder temp = new StringBuilder(currentPattern);
            temp.append('0');
            origLen++;
            for (int j = currentPattern.length() - 1; j >= 0; j--) {
                Character ch = currentPattern.charAt(j);
                switch (ch) {
                case 'O':
                    temp.append('R');
                    origLen += IN_LEN;
                    break;
                case 'R':
                    temp.append('O');
                    origLen += IN_LEN;
                    break;
                case '1':
                    temp.append('0');
                    origLen++;
                    break;
                case '0':
                    temp.append('1');
                    origLen++;
                    break;
                default:
                    break;
                }
            }
            currentPattern = temp.toString();
        }
          
        StringBuilder currentString = new StringBuilder();
        int uncompressedLen = 0;
        for (int i = 0; i < currentPattern.length(); i++) {
            StringBuilder temp = new StringBuilder();
            Character ch = currentPattern.charAt(i);
            switch (ch) {
            case 'O':
                temp.append(IN);
                break;
            case 'R':
                temp.append(IN_REV);
                break;
            case '1':
                temp.append('1');
                break;
            case '0':
                temp.append('0');
                break;
            default:
                break;
            }
            currentString.append(temp.toString());
            uncompressedLen += temp.toString().length();
            if (uncompressedLen >= LEN) {
                int toAdd = temp.toString().length() - uncompressedLen + LEN;
                currentString.append(temp.toString().substring(0, toAdd));
                break;
            }
        }
     //   System.out.println(currentString);
        long endTime = System.currentTimeMillis() - startTime;

        System.out.println("Time: " + endTime / 1000 +  "." + endTime % 1000 + " s");
        char[] compressed = createCheckSum(howManyRounds, currentString.toString().toCharArray());
        System.out.println(String.valueOf(compressed));
        endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + endTime / 1000 +  "." + endTime % 1000 + " s");
    }

    private static char[] createCheckSum(int rounds, char[] input) { 
        char[] checkSum = input;
        int index = 0;
        for (int i = 1; i <= rounds; i++) {
            char[] temp = new char[input.length / (2 << (i-1))];
            index = 0;
            for (int j = 0; j < checkSum.length - 1; j+=2) {
                if ((checkSum[j] == '0' && checkSum[j+1] == '0') || (checkSum[j] == '1' && checkSum[j+1] == '1')) {
                    temp[index++] = '1';
                } else {
                    temp[index++] = '0';
                }
            }
            checkSum = temp;
        }
        return checkSum;
        
    }
}
