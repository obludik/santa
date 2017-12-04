package santa.santa2015;

public class Santa15 {

    public static void main(String[] args) {

        int max = 0;
        for (int i = 1; i < 98; i++) {
            for (int j = 1; j < 98; j++) {
                for (int l =1; l < 98; l++) {
                    for (int k = 1; k < 98; k++) {
                        if (4*i - l <= 0) {
                            continue;
                        }
                        if (5 * j - 2 * i <= 0) {
                            continue;
                        }
                        if (5 * l - j - 2 * k <= 0) {
                            continue;
                        }
                        if ((i + j + l + k) == 100) {
                            int c = (4*i - l) * (5 * j - 2 * i) * (5 * l - j - 2 * k) * 2 * k;
                            int cals = 5 * i + 8 * j + 6 * l + k;
                            if (cals == 500 && c > max) {
                                max = c;
                            }
                            System.out.println(i + " " + j + " " + l + " " + k);
                        }
                    }
                }
            }
        }
        System.out.println(max);
        
    }
}


