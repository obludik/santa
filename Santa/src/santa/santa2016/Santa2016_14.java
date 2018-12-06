package santa.santa2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Utils;

public class Santa2016_14 {

    private static Map<Integer, String> hashes = new HashMap<>();  
    private static final int REPEATS = 2016; // 2016
    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int counter = 0;
        MessageDigest md;
        String input = "ihaygndm";
        int hashCounter = 0;

        Pattern pattern = Pattern.compile("((\\w)\\2\\2)");
        try {
            md = MessageDigest.getInstance("MD5");
            
            while (counter < 64) {
                String hash = generateHash(md, input, hashCounter);              
                
                hashCounter++;

                Matcher matcher = pattern.matcher(hash);
                if (matcher.find()) {

                    String sequence = matcher.group(1);                  
                    Pattern pattern2 = Pattern.compile(
                            "((" + sequence.substring(0,1) + ")\\2\\2\\2\\2)");
                    boolean found = false;
                    for (int i = hashCounter; i < hashCounter + 1000; i++) {
                        hash = generateHash(md, input, i);
                        Matcher matcher2 = pattern2.matcher(hash);
                        if (matcher2.find()) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        counter++;
                    }
                }
            }
            System.out.println(counter + " " +(hashCounter-1));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + endTime);
        System.out.println("Time: " + endTime / 1000 +  "." + endTime % 1000 + " s");
    }
    
    private static String generateHash(MessageDigest md, String input, int index) {
        String hash = null;
        if (hashes.containsKey(index)) {
            hash = hashes.get(index);
        } else {
            byte[] thedigest = md
                    .digest(new String(input + index).getBytes());
            hash = Utils.byte2hexString(thedigest).toLowerCase();
            for (int j = 0; j < REPEATS; j++) {
                thedigest = md.digest(new String(hash).getBytes());
                hash = Utils.byte2hexString(thedigest).toLowerCase();
               
            }
        }
        hashes.put(index, hash);
        return hash;
    }

}
