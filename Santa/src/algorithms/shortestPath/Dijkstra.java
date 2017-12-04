package algorithms.shortestPath;

import java.util.HashSet;
import java.util.Set;

public class Dijkstra {
	/**
	 * Dijkstruv algoritmus
	 * @param d matice delek (Integer.MAX_VALUE pokud hrana mezi uzly neexistuje)
	 * @param from uzel ze ktereho se hledaji nejkratsi cesty
	 * @return strom predchudcu (z ciloveho uzlu znaci cestu do uzlu from - !!! strom odkud kam se ma jit, postupne se tak dojde po krocich od startovniho do ciloveho policka)
	 */
	public static int[] doDijkstra(int[][] d, int from) {
	    Set<Integer> set = new HashSet<Integer>();
	    set.add(from);

	    boolean[] closed = new boolean[d.length];
	    int[] distances = new int[d.length];
	    for (int i = 0; i < d.length; i++) {
	        if (i != from) {
	            distances[i] = Integer.MAX_VALUE;
	        } else {
	            distances[i] = 0;
	        }
	    }


	    int[] predecessors = new int[d.length];
	    predecessors[from] = -1;

	    while (!set.isEmpty()) {
	        //najdi nejblizsi dosazitelny uzel
	        int minDistance = Integer.MAX_VALUE;
	        int node = -1;
	        for(Integer i : set){
	            if(distances[i] < minDistance){
	                minDistance = distances[i];
	                node = i;
	            }
	        }

	        set.remove(node);
	        closed[node] = true;
	        
	        //zkrat vzdalenosti
	        for (int i = 0; i < d.length; i++) {
	            //existuje tam hrana
	            if (d[node][i] != Integer.MAX_VALUE) {
	                if (!closed[i]) {
	                    //cesta se zkrati
	                    if (distances[node] + d[node][i] < distances[i]) {
	                        distances[i] = distances[node] + d[node][i];
	                        predecessors[i] = node;
	                        set.add(i); // prida uzel mezi kandidaty, pokud je jiz obsazen, nic se nestane
	                    }
	                }
	            }
	        }
	    }
	    return predecessors;
	}

}
