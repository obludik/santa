package file;

import java.util.List;

public interface FileReader {

	public String readSmallTextFile(String nameWithPath);
	public List<String> readFileByLines(String nameWithPath);
	public byte[] readFileBytes(String nameWithPath);
	
}
