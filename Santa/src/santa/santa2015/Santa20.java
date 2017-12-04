package santa.santa2015;


public class Santa20 {

	public static void main(String[] args) {
	    //part1();
	    part2();

	}
	
    public static void part1() {
        int count = 0;
        int min = 36000000;
        // 36902400 831600 - result
        // 36009600 861840
        // 36288000 876960
        // 36691200 884520
        // 36936000 899640
        // 37914240 900900
        // 37978720 932400
        // 39312000 950040
        // 37252800 1000440
        int j = 759640;
        while (count < min) {
            count = 0;
            for (int i = 2; i <= j/2; i++) {
                if (j % i == 0) {
                    count += 10 * i;
                }
            }
            count += 10 + 10 * j;
        //  System.out.println(count + " " + j);
            j++;
        }
        System.out.println(count + " " + (j-1));

    }
    
	   public static void part2() {
	        int count = 0;
	        int min = 36000000;
	        //36191936 884520
	        int j = 831600;
	        while (count < min) {
	            count = 0;
	            for (int i = 2; i <= j/2; i++) {
	                if (j / i > 50) {
	                    continue;
	                }
	                if (j % i == 0) {
	                    count += 11 * i;
	                }
	            }
	            count += 11 + 11 * j;
	          //System.out.println(count + " " + j);
	            j++;
	        }
	        System.out.println(count + " " + (j-1));

	    }

}
