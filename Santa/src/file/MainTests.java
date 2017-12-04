package file;

public class MainTests {

	public static final String FILE = "H:/Temp/dd.txt";
	
	public static void main(String[] args) {
		FileReader reader = new FileReaderImpl();
		System.out.println(reader.readSmallTextFile(FILE));
		System.out.println(reader.readFileByLines(FILE));
		System.out.println(reader.readFileBytes(FILE));
	}

}
