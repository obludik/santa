package santa.santa2016;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_9_3 {

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_9").getAbsolutePath());
        long startTime = System.currentTimeMillis();

        for (String string : data) {
        	String formula = "";
        	formula = process(formula, string);
        	System.out.println(formula);
        	ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            try {
				System.out.println(String.valueOf(engine.eval(formula)));
			} catch (ScriptException e) {
				e.printStackTrace();
			}
        }       
        
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }

    private static String process(String formula, String inputString) {
        Pattern p4 = Pattern.compile("(\\(\\d++x\\d++\\))");
        Matcher m4 = p4.matcher(inputString);
        if (m4.find()) {                     
        	String instruction = m4.group(1);
            int index = m4.start();
            String[] parts = instruction.replace(")", "").replace("(", "").split("x");
            int lettersLen = Integer.parseInt(parts[0]);            
            int repeat = Integer.parseInt(parts[1]);
            int part1Start = index + instruction.length();
            int part1End = part1Start + lettersLen;
            String part1 = inputString.substring(part1Start, part1End);
            String part2 = inputString.substring(part1End, inputString.length());
            
            if (index > 0) {
            	formula += " " + index + " + ";
            }
            formula += " " + repeat + " * (";
            formula = process(formula, part1);
            formula += ")";
            if (part2.length() > 0) {
            	formula += " + ";
            	formula = process(formula, part2);
            }
        } else {
        	formula += "" + inputString.length();
        }
       
        return formula;
    }
}

