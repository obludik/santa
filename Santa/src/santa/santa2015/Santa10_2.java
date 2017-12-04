package santa.santa2015;

public class Santa10_2 {

    public static void main(String[] args) {

        String input = "1321131112";
        int[] arr = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            arr[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        int len = arr.length;
        int[] output;
        for (int j = 0; j < 50; j++) {
            int same = 0;
            int lastChar = arr[0];
            int counter = 0;
            output = new int[10000000];
            for (int i = 0; i < len; i++) {
                if (lastChar == arr[i]) {
                    same++;
                    if (i+1 == len) {
                        output[counter] = same;
                        counter++;
                        output[counter] = lastChar;
                        counter++;
                    }
                } else {
                    output[counter] = same;
                    counter++;
                    output[counter] = lastChar;
                    counter++;
                    same = 1;
                    lastChar = arr[i];
                    if (i+1 == len) {
                        output[counter] = same;
                        counter++;
                        output[counter] = lastChar;
                        counter++;
                    }
                } 
            }
            arr = output;
            len = counter;
            System.out.println(counter);
        }
    }
}

