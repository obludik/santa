package santa.santa2016;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_7 {

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> data = reader
                .readFileByLines(new File("src\\santa\\input\\santa_7").getAbsolutePath());
        long startTime = System.currentTimeMillis();

        int counter = 0;
        for (String string : data) {
           
            if (Pattern.matches(".*(\\w)(\\w)(?!\\1)\\2\\1.*", string)) {
                if (Pattern.matches(".*\\[\\w*(\\w)(\\w)(?!\\1)\\2\\1\\w*\\].*", string)) {
                    continue;
                }
                System.out.println("ok" + string);
                counter++;
            }
        }
        
        System.out.println("ok" + counter);

        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }

}
