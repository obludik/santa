package santa.santa2016;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import file.FileReader;
import file.FileReaderImpl;

public class Santa2016_7_2 {

	public static void main(String[] args) {
		FileReader reader = new FileReaderImpl();
		List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_7").getAbsolutePath());
		long startTime = System.currentTimeMillis();

		int counter = 0;
		for (String string : data) {

			boolean found = false;
			for (int i = 0; i < string.length() - 2; i++) {
				if (found) {
					break;
				}
				Pattern p4 = Pattern.compile("((\\w)(\\w)(?!\\3)\\2)");
				String part = string.substring(i);
				Matcher m4 = p4.matcher(part);

				while (m4.find()) {
					String aba = m4.group(1);

					Pattern p1 = Pattern.compile(".*\\]\\w*" + aba + ".*");
					Pattern p2 = Pattern.compile("\\w*" + aba + ".*");

					Matcher m1 = p1.matcher(string);
					Matcher m2 = p2.matcher(string);

					if (m1.matches() || m2.matches()) {
						Pattern p3 = Pattern.compile(".*\\[\\w*" + aba.substring(1, 2) + aba.substring(0, 1)
								+ aba.substring(1, 2) + "\\w*\\].*");
						Matcher m3 = p3.matcher(string);
						if (m3.matches()) {
							System.out.println("ok" + string);
							counter++;
							found = true;
							break;
						}
					}
				}
			}
		}

		System.out.println("ok" + counter);
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}

}
