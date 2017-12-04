package santa.santa2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Santa14 {

	private static class Reindeer {
    	int run;
    	int wait;
    	int km;
    	String name;
    	int points;
	}
    public static void main(String[] args) {
        
        List<String> data = FileReader.read("F:\\temp\\santa_14.txt");
        
        int max = 0;
        
        List<Reindeer> animals = new ArrayList<Reindeer>();
        
        for (String string : data) {
        	Reindeer r = new Reindeer();
        	animals.add(r);
        	String[] reindeerData = string.split(" ");
        	r.run = Integer.parseInt(reindeerData[6]);
        	r.wait = Integer.parseInt(reindeerData[13]);
        	r.km = Integer.parseInt(reindeerData[3]);
        	r.name = reindeerData[0];
        	
        	int allKm = getRunKms(r, 2503);
        	
        	if (allKm > max) { 
        		max = allKm;
        		System.out.println(r.name + " bezel " + allKm);
        	}
        }
        
        for (int i = 1; i <= 2503; i++) {
        	max = 0;
        	Map<Reindeer, Integer> times = new HashMap<Reindeer, Integer>();
			for (Reindeer reindeer : animals) {
				int len = getRunKms(reindeer, i);
				if (len >= max) {
					max = len;
					times.put(reindeer, len);
				}
			}
			for (Reindeer reindeer : times.keySet()) {
				if (times.get(reindeer) == max) {
					reindeer.points++;
				}
			}
		}
        
        for (Reindeer reindeer : animals) {
        	System.out.println(reindeer.name + " " + reindeer.points);
		}
    }
    
    private static int getRunKms(Reindeer r, int time) {
    	int cycles = time / (r.run + r.wait);
    	int km = cycles * r.run * r.km;
    	int remainder = time % (r.run + r.wait);
    	km += (remainder > r.run ? r.run : remainder) * r.km; 
    	return km;
    }
    
}


