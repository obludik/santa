package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import file.FileReaderImpl;

public class Santa2016_22 {
	
	public static final Pattern PATTERN = Pattern.compile("(/dev/grid/node-x(\\d*)-y(\\d*)) *(\\d*)T *(\\d*)T *(\\d*)T *(\\d*)%");
	
	private static Row parseRow(String row) {
	    Matcher matcher = PATTERN.matcher(row);
        if (matcher.find()) {
        System.out.println(row);
        Row roww = 
             new Row(matcher.group(1), Integer.parseInt(matcher.group(4)),
                    Integer.parseInt(matcher.group(5)),
                            Integer.parseInt(matcher.group(6)));
        System.out.println(roww);
        return roww;
        } else {
            throw new RuntimeException("Parsing error:" + row);
        }
        
	}
	
	public static void main(String[] args) {
		file.FileReader reader = new FileReaderImpl();
		List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_22").getAbsolutePath());
		long startTime = System.currentTimeMillis();

		Stream<String> lines = data.stream();
		List<Row> instructions = lines
		        .map(item -> parseRow(item))
				.collect(Collectors.toList());
		
		int counter = 0;
		for (Row row : instructions) {
		  
		      for (Row row1 : instructions) {
		          if (row != row1) {
		            if (row.used > 0 && row.used <= row1.avail) {
		                counter++;
		                System.out.println(row + "->" + row1);
		            }
		          }
		      }
        }
		
		System.out.println(counter);
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}
	


	private static class Row {
	    String node;
	    int size;
	    int used;
	    int avail;
	    
        public Row(String node, int size, int used, int avail) {
            this.node = node;
            this.size = size;
            this.used = used;
            this.avail = avail;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + avail;
            result = prime * result + ((node == null) ? 0 : node.hashCode());
            result = prime * result + size;
            result = prime * result + used;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Row other = (Row) obj;
            if (avail != other.avail)
                return false;
            if (node == null) {
                if (other.node != null)
                    return false;
            } else if (!node.equals(other.node))
                return false;
            if (size != other.size)
                return false;
            if (used != other.used)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Row [node=" + node + ", size=" + size + ", used=" + used + ", avail=" + avail
                    + "]";
        }
        
	}
}
