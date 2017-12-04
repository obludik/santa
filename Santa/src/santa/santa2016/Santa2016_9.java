package santa.santa2016;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_9 {

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_9").getAbsolutePath());
        long startTime = System.currentTimeMillis();

        for (String string : data) {
            String newString = "";
            Pattern p4 = Pattern.compile("(\\(\\d++x\\d++\\))");
            Matcher m4 = p4.matcher(string);
            int index = 0;
            int lastIndex = 0;
            int letters = 0;
            String instruction = "";
            String lastInstruction = "";
            while (m4.find(index + instruction.length() + letters)) {               
                instruction = m4.group(1);
                index = m4.start();
                newString += string.substring(lastIndex + lastInstruction.length() + letters, index);             
                String cutInstruction = instruction.replace("(", "");
                cutInstruction = cutInstruction.replace(")", "");
                String[] parts = cutInstruction.split("x");
                letters = Integer.parseInt(parts[0]);
                int repeat = Integer.parseInt(parts[1]);
                String toRepeat = string.substring(index + instruction.length(), index + instruction.length() + letters);
           
                for (int j = 0; j < repeat; j++) {
                    newString += toRepeat;
                }
                lastInstruction = instruction;
                lastIndex = index;
              //  newString += string.substring(index + instruction.length() + letters, string.length());
            }

            newString += string.substring(lastIndex + lastInstruction.length() + letters, string.length());
           
            System.out.println(string + "->" + newString + "->" + newString.length());
        }
        
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }

}
