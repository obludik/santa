package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_6 {

    private static class Chars implements Comparable<Chars> {
        int count;
        char letter;

        public Chars(int count, char letter) {
            this.count = count;
            this.letter = letter;
        }

        @Override
        public String toString() {
            return count + " " + letter;
        }
        
        @Override
        public int compareTo(Chars o) {
            return count > o.count ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> data = reader
                .readFileByLines(new File("src\\santa\\input\\santa_6").getAbsolutePath());
        long startTime = System.currentTimeMillis();

        List<String> columns = new ArrayList<String>();

        for (int i = 0; i < data.get(0).length(); i++) {
            StringBuilder text = new StringBuilder();
            for (String string : data) {
                text.append(string.charAt(i));
            }
            columns.add(text.toString());
        }

        for (String text : columns) {
            Map<Character, Chars> chars = new HashMap<Character, Chars>();
            for (int i = 0; i < text.length(); i++) {
                Chars cha = chars.get(text.charAt(i));
                if (cha != null) {
                    cha.count++;
                } else {
                    Chars ch = new Chars(1, text.charAt(i));
                    chars.put(text.charAt(i), ch);
                }

            }
            List<Chars> a = new ArrayList(chars.values());
            Collections.sort(a);
            System.out.print(a.get(0).letter);
        }

        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }

}
