package santa.santa2015;

public class Santa10 {

    public static void main(String[] args) {

        String input = "1321131112";
        String output = "";
        for (int j = 0; j < 50; j++) {
            int same = 0;
            output = "";
            char lastChar = input.charAt(0);
            for (int i = 0; i < input.length(); i++) {
                if (lastChar == input.charAt(i)) {
                    same++;
                    if (i+1 == input.length()) {
                        output += same;
                        output += lastChar;
                    }
                } else {
                    output += same;
                    output += lastChar;
                    same = 1;
                    lastChar = input.charAt(i);
                    if (i+1 == input.length()) {
                        output += same;
                        output += lastChar;
                    }
                } 
            }
            input = output;
            System.out.println(output.length());
        }
        System.out.println(output.length());
    }
}

