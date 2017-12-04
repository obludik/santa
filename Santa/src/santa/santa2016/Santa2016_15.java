package santa.santa2016;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_15 {

    public static class Disc {
        int startPosition;
        int positions;
        
        public Disc(int startPosition, int positions) {
            this.startPosition = startPosition;
            this.positions = positions;
        }
    }

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_15").getAbsolutePath());    
        long startTime = System.currentTimeMillis();
        
        Stream<String> lines = data.stream();
        List<Disc> discs = lines.map(line -> line.split(" "))
                .map(item -> new Disc(Integer.parseInt(item[11].substring(0, item[11].length() - 1)), Integer.parseInt(item[3])))
                .collect(Collectors.toList());
        
        for (int time = 0; time < Integer.MAX_VALUE; time++) {
            boolean ok = true;
            int index = 1;
            for (Disc disc : discs) {
                if (((time + index++ + disc.startPosition) % disc.positions) != 0) {
                    ok = false;
                    break;
                }                
            }
            if (ok) {
                System.out.println(time);
                break;
            }
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Time: " + endTime / 1000 +  "." + endTime % 1000 + " s");
    }

}
