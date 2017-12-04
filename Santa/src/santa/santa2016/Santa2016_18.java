package santa.santa2016;

public class Santa2016_18 {

	private static final String INPUT = "^.^^^.^..^....^^....^^^^.^^.^...^^.^.^^.^^.^^..^.^...^.^..^.^^.^..^.....^^^.^.^^^..^^...^^^...^...^.";
	private static final int ROWS = 400000;
	
    public static void main(String[] args) {  
        long startTime = System.currentTimeMillis();

        StringBuilder previousRow = new StringBuilder("." + INPUT + ".");
        StringBuilder nextRow = new StringBuilder();
        
        int counter = INPUT.length() - INPUT.replace(".", "").length();
    
        for (int j = 0; j < ROWS - 1; j++) {
        	nextRow = new StringBuilder();
        	nextRow.append(".");
	        for (int i = 0; i < previousRow.toString().length() - 2; i++) {
				String threeTiles = previousRow.substring(i, i+3);
				switch (threeTiles) {
				case "^^.":
				case ".^^":
				case "..^":
				case "^..":
					nextRow.append("^");
					break;				
				default:
					nextRow.append(".");
					counter++;
					break;
				}
			}
	        nextRow.append(".");
	        previousRow = nextRow;
	       // System.out.println(nextRow.toString().substring(1, nextRow.length() - 1));
		}
        System.out.println("Safe tiles: " + counter);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + endTime + " ms");
        System.out.println("Time: " + endTime / 1000 +  "." + endTime % 1000 + " s");
    }
  
}
