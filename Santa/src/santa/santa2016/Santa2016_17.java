package santa.santa2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import util.Utils;

public class Santa2016_17 {

	private static final int GRID_X = 4;
	private static final int GRID_Y = 4;
	private static final int END_X = 4;
	private static final int END_Y = 1;
	private static final String PASS = "yjjvjgan";
	private static MessageDigest md;
	private static String shortestResult;
	private static String longestResult;
	
	static {        
        try {
        	md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(0);
        } 
	}
	
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();               
        
        getPath(1, 4, "");
        
        System.out.println("Shortest path: " + shortestResult);
        System.out.println("Longest path: " + longestResult);
        System.out.println("Longest path length: " + longestResult.length());
        
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + endTime / 1000 +  "." + endTime % 1000 + " s");
    }
    
    private static void getPath(int x, int y, String path) {
    	
    	if (x == END_X && y == END_Y) {
    		if (shortestResult == null || path.length() < shortestResult.length()) {
    			shortestResult = path;
    		}
    		if (longestResult == null || path.length() > longestResult.length()) {
    			longestResult = path;
    		}
    		return;
    	}
    	
    	byte[] thedigest = md.digest((PASS + path).getBytes());
        String hash = Utils.byte2hexString(thedigest).toLowerCase();

        if (canUp(y, hash.charAt(0))) {
        	 getPath(x, y + 1, path + "U");
        }

        if (canDown(y, hash.charAt(1))) {
        	getPath(x, y - 1, path + "D");
        }
        
        if (canLeft(x, hash.charAt(2))) {
        	getPath(x - 1, y, path + "L");
        }
        
        if (canRight(x, hash.charAt(3))) {
        	getPath(x + 1, y, path + "R");
        }
    }
    
    private static boolean canUp(int y, int ch) {
    	return (ch >= 98 && ch <= 102 && y + 1 <= GRID_Y);
    }
    
    private static boolean canDown(int y, int ch) {
    	return (ch >= 98 && ch <= 102 && y - 1 > 0);
    }
    
    private static boolean canLeft(int x, int ch) {
    	return (ch >= 98 && ch <= 102 && x - 1 > 0);
    }
    
    private static boolean canRight(int x, int ch) {
    	return (ch >= 98 && ch <= 102 && x + 1 <= GRID_X);
    }
  
}

