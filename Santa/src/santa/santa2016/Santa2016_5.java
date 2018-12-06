package santa.santa2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Predicate;

import util.Utils;

public class Santa2016_5 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        String d = "abc";
        MessageDigest md; 
        Predicate<String> isNumber = s -> s.matches(".*[0-7].*");
        try {
            md = MessageDigest.getInstance("MD5");
            for (int i = 0; i < 55000000; i++) {
                byte[] thedigest = md.digest(new String(d + i).getBytes());
                String a = Utils.byte2hexString(thedigest);
                if (a.startsWith("00000")) {
                  //  System.out.println(new String(d + i));
                  //  System.out.println(a);
                    String b = a.substring(5,6);
                    if (isNumber.test(b)) {
                        System.out.print(a.substring(5,7));
                    }
                }
            }
            //System.out.println(Conversions.byte2hexString(md.digest("pqrstuv1048970".getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }        
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }
    
 
  
}
