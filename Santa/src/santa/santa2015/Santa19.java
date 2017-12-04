package santa.santa2015;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Santa19 {
	
	private static String INPUT = "ORnPBPMgArCaCaCaSiThCaCaSiThCaCaPBSiRnFArRnFArCaCaSiThCaCaSiThCaCaCaCaCaCaSiRnFYFArSiRnMgArCaSiRnPTiTiBFYPBFArSiRnCaSiRnTiRnFArSiAlArPTiBPTiRnCaSiAlArCaPTiTiBPMgYFArPTiRnFArSiRnCaCaFArRnCaFArCaSiRnSiRnMgArFYCaSiRnMgArCaCaSiThPRnFArPBCaSiRnMgArCaCaSiThCaSiRnTiMgArFArSiThSiThCaCaSiRnMgArCaCaSiRnFArTiBPTiRnCaSiAlArCaPTiRnFArPBPBCaCaSiThCaPBSiThPRnFArSiThCaSiThCaSiThCaPTiBSiRnFYFArCaCaPRnFArPBCaCaPBSiRnTiRnFArCaPRnFArSiRnCaCaCaSiThCaRnCaFArYCaSiRnFArBCaCaCaSiThFArPBFArCaSiRnFArRnCaCaCaFArSiRnFArTiRnPMgArF";

	public static void main(String[] args) {
	    
	    List<String> data = FileReader.read("D:\\temp\\input19.txt");
	    Set<String> results = new HashSet<String>();
	    
	    for (String row : data) {
            String[] replacement = row.split(" ");
            int index = INPUT.indexOf(replacement[0]);
            while (index > -1) {
                String replaced = INPUT.substring(0, index) + 
                        INPUT.substring(index, INPUT.length()).replaceFirst(replacement[0], replacement[2]);
                results.add(replaced);
                index = INPUT.indexOf(replacement[0], index+1);                
            }
        }

	    for (String result : results) {
	        System.out.println(result);
        }
	    System.out.println(results.size());
	}

}
