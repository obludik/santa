package santa.santa2016;

import java.io.File;
import java.util.List;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_8 {

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        List<String> data = reader
                .readFileByLines(new File("src\\santa\\input\\santa_8").getAbsolutePath());
        long startTime = System.currentTimeMillis();

        int xaxis = 50;
        int yaxis = 6;
        int[][] playground = new int[xaxis][yaxis];
        for (String string : data) {
            String[] commands = string.split(" ");
            switch (commands[0].trim()) {
            case "rotate":
                String axis = commands[2].substring(0, 1);
                int line = Integer.parseInt(commands[2].substring(2));
                int len = Integer.parseInt(commands[4]);
                if (axis.equals("x")) {   
                    int[] newCol = new int[yaxis];               
                    for (int i = 0; i < yaxis; i++) {           
                        int newIndex = (i + len) % yaxis;                    
                        newCol[newIndex] = playground[line][i];
                    }
                    for (int i = 0; i < newCol.length; i++) {
                        playground[line][i] = newCol[i];
                                
                    }
                } else {
                    int[] newRow = new int[xaxis];               
                    for (int i = 0; i < xaxis; i++) {           
                        int newIndex = (i + len) % xaxis;                    
                        newRow[newIndex] = playground[i][line];
                    }
                    for (int i = 0; i < newRow.length; i++) {
                        playground[i][line] = newRow[i];
                                
                    }
                }
                break;
            case "rect":
                String[] size = commands[1].split("x");
                int x = Integer.parseInt(size[0]);
                int y = Integer.parseInt(size[1]);
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        playground[i][j]++;
                    }
                }
                break;
            default:
                break;
            }
        }        

        int counter = 0;
        for (int i = 0; i < yaxis; i++) {
            for (int j = 0; j < xaxis; j++) {                
                if (playground[j][i] > 0) {
                    counter++;
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println("Counter: " + counter);
        
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }

}
