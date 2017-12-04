package santa.santa2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Santa9 {

    public static void main(String[] args) {
        
        List<String> data = FileReader.read("D:\\temp\\input9.txt");

        Map<String, Map<String, Integer>> cities = new HashMap<String, Map<String, Integer>>();
        for (String string : data) {
            string = string.replace("to ", "");
            string = string.replace("= ", "");
            String[] input = string.split(" ");
            if (!cities.containsKey(input[0])) {
                cities.put(input[0], new  HashMap<String, Integer>());
            }
            if (!cities.containsKey(input[1])) {
                cities.put(input[1], new  HashMap<String, Integer>());
            }
            cities.get(input[0]).put(input[1], Integer.parseInt(input[2]));
            cities.get(input[1]).put(input[0], Integer.parseInt(input[2]));
        }
        
        List<Object[]> routes = new ArrayList<Object[]>();        
        List<String> list = new ArrayList<String>();
        list.addAll(cities.keySet());
        
        permute(routes, list, 0);
        
        int min = Integer.MAX_VALUE;
        for (Object[] route : routes) {
            int count = 0;
            for (int i = 0; i < route.length - 1; i++) {
                count += cities.get(route[i]).get(route[i+1]);
            }
            if (min >= count) {
                min = count;
                System.out.println(Arrays.toString(route) + " " + count);
            }
        }
        System.out.println("----------");
        int max = 0;
        for (Object[] route : routes) {
            int count = 0;
            for (int i = 0; i < route.length - 1; i++) {
                count += cities.get(route[i]).get(route[i+1]);
            }
            if (max <= count) {
                max = count;
                System.out.println(Arrays.toString(route) + " " + count);
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
           // System.out.println(Arrays.toString(arr.toArray()));
        }
    }
}

