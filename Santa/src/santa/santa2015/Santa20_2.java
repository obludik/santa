package santa.santa2015;


public class Santa20_2 {

	public static void main(String[] args) {
		int count = 0;
		int min = 36000000;
		
		// 36936000 899640
		// 37914240 900900
		// 37978720 932400
		// 39312000 950040
		// 37252800 1000440
		int j = 1;
		while (count < min) {
			count = 0;
			for (int i = 2; i <= j/2; i++) {
				System.out.println(j + " " +i + " "+ (j % i) + "-" + (j & (i-1)));
				//x % 7 == (x+x/7) & 7
				if ((j & (i-1)) == 0) {
					count += 10 * i;
					
				}
			}
			count += 10 + 10 * j;
			System.out.println(count + " " + j);
			j++;
		}
		System.out.println(count + " " + (j-1));

	}

}
