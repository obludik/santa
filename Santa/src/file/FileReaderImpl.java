package file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderImpl implements FileReader {

	@Override
	public String readSmallTextFile(String nameWithPath) {
		StringBuilder data = new StringBuilder();
		Path file = Paths.get(nameWithPath);
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	data.append(line);
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data.toString();
	}

	@Override
	public List<String> readFileByLines(String nameWithPath) {
		Path file = Paths.get(nameWithPath);
		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	@Override
	public byte[] readFileBytes(String nameWithPath) {
		Path file = Paths.get(nameWithPath);
		byte[] fileArray = null;
		try {
			fileArray = Files.readAllBytes(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileArray;
	}

}
