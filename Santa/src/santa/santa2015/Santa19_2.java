package santa.santa2015;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Santa19_2 {


     private static String RESULT =
     "ORnPBPMgArCaCaCaSiThCaCaSiThCaCaPBSiRnFArRnFArCaCaSiThCaCaSiThCaCaCaCaCaCaSiRnFYFArSiRnMgArCaSiRnPTiTiBFYPBFArSiRnCaSiRnTiRnFArSiAlArPTiBPTiRnCaSiAlArCaPTiTiBPMgYFArPTiRnFArSiRnCaCaFArRnCaFArCaSiRnSiRnMgArFYCaSiRnMgArCaCaSiThPRnFArPBCaSiRnMgArCaCaSiThCaSiRnTiMgArFArSiThSiThCaCaSiRnMgArCaCaSiRnFArTiBPTiRnCaSiAlArCaPTiRnFArPBPBCaCaSiThCaPBSiThPRnFArSiThCaSiThCaSiThCaPTiBSiRnFYFArCaCaPRnFArPBCaCaPBSiRnTiRnFArCaPRnFArSiRnCaCaCaSiThCaRnCaFArYCaSiRnFArBCaCaCaSiThFArPBFArCaSiRnFArRnCaCaCaFArSiRnFArTiRnPMgArF";

    public static void main(String[] args) {

        List<String> data = FileReader.read("D:\\temp\\input19.txt");
        Set<String> results = new HashSet<String>();
        
      String a = "ORnPBPMgArCaCaCaSiThCaCaSiThCaCaPBSiRnFArRnFArCaCaSiThCaCaSiThCaCaCaCaCaCaSiRnFYFArSiRnMgArCaSiRnPTiTiBFYPBFArSiRnCaSiRnTiRnFArSiAlArPTiBPTiRnCaSiAlArCaPTiTiBPMgYFArPTiRnFArSiRnCaCaFArRnCaFArCaSiRnSiRnMgArFYCaSiRnMgArCaCaSiThPRnFArPBCaSiRnMgArCaCaSiThCaSiRnTiMgArFArSiThSiThCaCaSiRnMgArCaCaSiRnFArTiBPTiRnCaSiAlArCaPTiRnFArPBPBCaCaSiThCaPBSiThPRnFArSiThCaSiThCaSiThCaPTiBSiRnFYFArCaCaPRnFArPBCaCaPBSiRnTiRnFArCaPRnFArSiRnCaCaCaSiThCaRnCaFArYCaSiRnFArBCaCaCaSiThFArPBFArCaSiRnFArRnCaCaCaFArSiRnFArTiRnPMgArF";        
  /*    a = a.replace("TiTi", "Ti");
      a = a.replace("CaCa", "Ca");
      a = a.replace("CRnFYFYFAr", "H");
      
      a = a.replace("CaSi", "Si");
      a = a.replace("TiMg", "Mg");*/
      
      a = a.replace("CaCa", "Ca");
      a = a.replace("TiTi", "Ti");
        a = replaceLong(a);
        a = a.replace("CaCa", "Ca");
        a = a.replace("TiTi", "Ti");
    //    a = a.replace("CaCa", "Ca");
        a = replaceLong(a);
        a = replaceLong(a);
        a = a.replace("CaCa", "Ca");
        a = a.replace("TiTi", "Ti");
        a = replaceLong(a);
        a = replaceFew(a);
        a = replaceLong(a);
        
        a = replaceLong(a);
        a = replaceFew(a);
        a = replaceLong(a);
        a = replaceFew(a);
        a = replaceLong(a);
        a = replaceFew(a);
        a = replaceLong(a);
  //      a = replace(a) ;
    //    a = a.replace("CaCa", "Ca");
   /*     a = replace(a) ;
        a = replaceLong(a);
        a = replace(a) ;
        a = replaceLong(a);
        a = replace(a) ;
        a = replaceLong(a);*/
        
      System.out.println(a);
      results.add(a);
        // results.add("HCaCaSiRnBSiRnCaRnFArYFArFArF");
        // results.add("e");
        
      runBack(0, results, data);

    }
    
    private static String replaceLong(String a) {
        a = a.replace("CRnFYFYFAr", "H");
        a = a.replace("SiRnFYFAr", "Ca");
        a = a.replace("CRnFYFAr", "O");
        a = a.replace("CRnAlAr", "H");
        a = a.replace("PRnFAr", "Ca");
        a = a.replace("CRnFYMgAr", "H");
        a = a.replace("CRnMgYFAr", "H");
        a = a.replace("NRnFYFAr", "H");
        a = a.replace("NRnMgAr", "H");
        a = a.replace("SiRnMgAr", "Ca");
        a = a.replace("ThRnFAr", "Al");
        a = a.replace("CRnMgAr", "O");
        a = a.replace("CRnFAr", "N");
        a = a.replace("ORnFAr", "H");
        a = a.replace("NRnFAr", "O");
        a = a.replace("SiRnFAr", "P");
        a = a.replace("TiRnFAr", "B");
        return a;
    }
    
    private static String replaceFew(String a) {
        a = a.replace("CaCa", "Ca");
        a = a.replace("TiTi", "Ti");
        a = a.replace("SiTh", "Ca");
        a = a.replace("ThCa", "Th");
        a = a.replace("TiMg", "Mg");
        a = a.replace("CaSi", "S");
        a = a.replace("SiAl", "F");
        a = a.replace("TiB", "B");
        a = a.replace("ThF", "Al"); 
        a = a.replace("PMg", "F");
        a = a.replace("SiAl", "F");
        a = a.replace("PTi", "P");
        a = a.replace("HSi", "N");
        a = a.replace("HCa", "H");
        a = a.replace("NTh", "H");
        a = a.replace("BP", "Ti");   
        return a;
    }
    
    private static String replace(String a) {
        a = a.replace("CaCa", "Ca");
        a = a.replace("TiTi", "Ti");
        a = a.replace("SiTh", "Ca");
        a = a.replace("ThCa", "Th");
        a = a.replace("TiMg", "Mg");
        a = a.replace("CaSi", "S");
        a = a.replace("BCa", "B");      
        a = a.replace("PB", "Ca");
        a = a.replace("CaF", "F");
        a = a.replace("OB", "H");
        a = a.replace("BF", "Mg");
        a = a.replace("HP", "O");
        a = a.replace("OTi", "O");
        a = a.replace("CaP", "P");
        a = a.replace("BP", "Ti");       
    /*    a = a.replace("HF", "e");
        a = a.replace("NAl", "e");
        a = a.replace("OMg", "e"); */
        return a;
    }

    private static void runBack(int iterations, Set<String> results, List<String> data) {
        if (iterations > 100) {
            return;
        }
        int level = iterations;
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            String result = (String) iterator.next();
            if (result.equals(RESULT)) {
                System.out.print(iterations + " ");
                System.out.println(result);

                System.exit(0);
            }
            /*
             * if (result.length() > RESULT.length()) { iterator.remove(); }
             */
        }
        for (String result : results) {
            System.out.println(level);
            Set<String> combinations = new HashSet<String>();
            for (String row : data) {
                String[] replacement = row.split(" ");
                int index = result.indexOf(replacement[2]);
                while (index > -1) {
                    String replaced = result.substring(0, index)
                            + result.substring(index, result.length()).replaceFirst(replacement[2],
                                    replacement[0]);
                    combinations.add(replaced);
                    index = result.indexOf(replacement[2], index + 1);

                    // System.out.println("--" + replaced);
                }
            }
            runBack(level + 1, combinations, data);
        }
    }

    private static void run(int iterations, Set<String> results, List<String> data) {
        if (iterations > 20) {
            return;
        }

        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            String result = (String) iterator.next();
            if (result.equals(RESULT)) {
                System.out.print(iterations + " ");
                System.out.println(result);
                System.exit(0);
            }
            if (result.length() > RESULT.length()) {
                iterator.remove();
            }
        }
        for (String result : results) {
            System.out.println(iterations);
            Set<String> combinations = new HashSet<String>();
            for (String row : data) {
                String[] replacement = row.split(" ");
                int index = result.indexOf(replacement[0]);
                while (index > -1) {
                    String replaced = result.substring(0, index)
                            + result.substring(index, result.length()).replaceFirst(replacement[0],
                                    replacement[2]);
                    combinations.add(replaced);
                    index = result.indexOf(replacement[0], index + 1);
                    System.out.println("--" + replaced);
                }
            }
            run(iterations + 1, combinations, data);
            // iterations--;
        }
    }

}
