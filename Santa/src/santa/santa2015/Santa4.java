package santa.santa2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import util.Utils;

public class Santa4 {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        
        String d = "iwrupvqb";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            for (int i = 0; i < 10000000; i++) {
                byte[] thedigest = md.digest(new String("iwrupvqb" + i).getBytes());
                String a = Utils.byte2hexString(thedigest);
                if (a.startsWith("000000")) {
                    System.out.println(new String("iwrupvqb" + i));
                    System.out.println(a);
                }
            }
            System.out.println(Utils.byte2hexString(md.digest("pqrstuv1048970".getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        System.out.println("Elapsed ms:" + (System.currentTimeMillis() - startTime));
    }

}
