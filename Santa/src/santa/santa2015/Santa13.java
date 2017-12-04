package santa.santa2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Santa13 {

    public static void main(String[] args) {
        
        List<String> data = FileReader.read("F:\\temp\\santa_13.txt");
        
        Map<String, Map<String, Integer>> names = new HashMap<String, Map<String, Integer>>();
        for (String string : data) {
        	string = string.substring(0, string.length() - 1);
        	boolean gain = false;
        	if (string.contains("gain")) {
        		gain = true;
        	}
            string = string.replace("happiness units by sitting next to ", "");
            string = string.replace("would ", "");
            String[] input = string.split(" ");
            if (!names.containsKey(input[0])) {
            	names.put(input[0], new  HashMap<String, Integer>());
            }
            if (!names.containsKey(input[3])) {
            	names.put(input[3], new  HashMap<String, Integer>());
            }
            names.get(input[0]).put(input[3], (gain ? 1 : -1) * Integer.parseInt(input[2]));
        }
        
        List<Object[]> combinations = new ArrayList<Object[]>();        
        List<String> list = new ArrayList<String>();
        list.addAll(names.keySet());
        
        permute(combinations, list, 0);
        
        int max = 0;
        for (Object[] comb : combinations) {
            int count = 0;
            for (int i = 0; i < comb.length - 1; i++) {
                count += names.get(comb[i]).get(comb[i+1]);
                count += names.get(comb[i+1]).get(comb[i]);
            }
            count += names.get(comb[0]).get(comb[comb.length-1]);
            count += names.get(comb[comb.length-1]).get(comb[0]);
            if (max <= count) {
            	max = count;
                System.out.println(Arrays.toString(comb) + " " + count);
            }
        }
        
    }
    
    static void permute(List<Object[]> routes, List<String> arr, int k){
        for(int i = k; i < arr.size(); i++){
            Collections.swap(arr, i, k);
            permute(routes, arr, k+1);
            Collections.swap(arr, k, i);
        }
        if (k == arr.size() -1){
            routes.add(arr.toArray());
        //   System.out.println(Arrays.toString(arr.toArray()));
        }
    }
}

