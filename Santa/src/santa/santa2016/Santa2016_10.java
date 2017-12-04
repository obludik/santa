package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import file.FileReaderImpl;

public class Santa2016_10 {
	
	static Map<Integer, Bot> bots;
	static Map<Integer, List<Integer>> outputs = new HashMap<Integer, List<Integer>>();
			
	public static class Bot {
		int id;
		int low;
		int high;
		int lowToGive;
		String lowType = ""; // output or bot
		int highToGive;
		String highType = "";
		
		public Bot(int id, int val) {
			this.id = id;
			addVal(val);
		}
		
		public Bot(int id, int lowToGive, int highToGive, String lowType, String highType) {
			this.id = id;
			this.lowToGive = lowToGive;
			this.lowType = lowType;
			this.highToGive = highToGive;
			this.highType = highType;
		}

		public Bot addVal(int val) {
			if (val < high) {
				if (high > 0) {
					System.out.println("Bot " + id + " is comparing " + val + " " + high);
				}
				low = val;
			} else {
				if (low > 0) {
					System.out.println("Bot " + id + " is comparing " + low + " " + val);
				}
				low = high;
				high = val;
			}
			if (high > 0 && low > 0) {
				transferChip(lowType, lowToGive, low);
				low = 0;
				transferChip(highType, highToGive, high);
				high = 0;
			}
			return this;
		}

		@Override
		public String toString() {
			return "Bot [id=" + id + ", low=" + low + ", high=" + high + ", lowToGive=" + lowToGive + ", lowType="
					+ lowType + ", highToGive=" + highToGive + ", highType=" + highType + "]";
		}
	}
	
	
    public static void main(String[] args) {
        file.FileReader reader = new FileReaderImpl();
        List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_10").getAbsolutePath());    
        long startTime = System.currentTimeMillis();
        
        Stream<String> lines = data.stream();
        bots = lines.filter(line -> line.contains("gives"))
        	.map(line -> line.split(" "))
        	.collect(Collectors.toMap(item -> Integer.parseInt(item[1]), 
        			item -> new Bot(Integer.parseInt(item[1]), Integer.parseInt(item[6]), 
        					Integer.parseInt(item[11]), item[5], item[10])));

        
       lines = data.stream();
     /*   Map<Integer, Bot> bots2 = lines.filter(line -> line.contains("value"))
        	.map(line -> line.split(" "))
        	.collect(Collectors.toMap(item -> Integer.parseInt(item[5]), 
        			item -> new Bot(Integer.parseInt(item[5]), Integer.parseInt(item[1])), 
        			(item1, item2) -> item1.addVal(item2.high)));*/
        List<Bot> directs = lines.filter(line -> line.contains("value"))
            	.map(line -> line.split(" "))
            	.map(item -> new Bot(Integer.parseInt(item[5]), Integer.parseInt(item[1])))
            	.collect(Collectors.toList());
        

        for (Bot bot : directs) {
			if (bots.containsKey(bot.id)) {
				bots.get(bot.id).addVal(bot.high);
			} else {
				bots.put(bot.id, new Bot(bot.id, bot.high));
			}
		}
        
        int count = 1;
        for (Integer number : outputs.get(0)) {           
            count *= number;
		}
        for (Integer number : outputs.get(1)) {           
            count *= number;
		}
        for (Integer number : outputs.get(2)) {           
            count *= number;
		}
        System.out.println("Multiply " + count);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }
    
    private static void transferChip(String type, int toId, int chip) {
		if (type.equals("output")) {
			if (outputs.containsKey(toId)) {
				List<Integer> chips = outputs.get(toId);
				chips.add(chip);
				outputs.put(toId, chips);
			} else {
				List<Integer> chips = new ArrayList<Integer>();
				chips.add(chip);
				outputs.put(toId, chips);
			}
		} else {				
			if (bots.containsKey(toId)) {
				bots.get(toId).addVal(chip);
			} else {
				bots.put(toId, new Bot(toId, chip));
			}
		}
    }
    
}
