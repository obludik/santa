package santa.santa2016;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_20 {

    private static class Range {
        long from;
        long to;
        
        public Range(long from, long to) {
            this.from = from;
            this.to = to;
        }
        
        public boolean isInRange(long val) {
            if (val <= to && val >= from) {
                return true;
            }
            return false;
        }
    }
    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_20").getAbsolutePath());    
        long startTime = System.currentTimeMillis();

            Stream<String> lines = data.stream();
           List<Range> directs = lines.map(line -> line.split("-"))
                   .map(item -> new Range(Long.parseLong(item[0]), Long.parseLong(item[1])))
                   .collect(Collectors.toList());
           
        int allowedCount = 0;
        for (long i = 0; i < 4294967295L;) {
            boolean allowed = true;
            for (Range range : directs) {
                if (range.isInRange(i)) {
                    allowed = false;
                    i = range.to + 1;
                    break;
                }
            }
            if (allowed) {
                allowedCount += 1;
                i++;
                System.out.println("Allowed: " + i);
                //break;
            }
        }
        
        System.out.println(allowedCount);
        
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + endTime + " ms");
        System.out.println("Time: " + endTime / 1000 +  "." + endTime % 1000 + " s");
    }
  
}
