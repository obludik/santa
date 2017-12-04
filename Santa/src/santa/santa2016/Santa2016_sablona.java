package santa.santa2016;

import java.io.File;
import java.util.List;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_sablona {

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_2").getAbsolutePath());    
        long startTime = System.currentTimeMillis();

        
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + endTime + " ms");
        System.out.println("Time: " + endTime / 1000 +  "." + endTime % 1000 + " s");
    }
  
}
