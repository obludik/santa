package santa;

import java.io.File;
import java.util.List;

import file.FileReader;
import file.FileReaderImpl;
import santa.santa2017.Santa2017_5;

public class SantaMain {

	private static FileReader reader = new FileReaderImpl();
	
	public static void main(String[] args) {
	//	solveOneLineFile("src\\santa\\input\\santa_2017_1", new Santa2017_1());
	//	solveMoreLinesFile("src\\santa\\input\\santa_2017_2", new Santa2017_2());
	//	solveProblem(new Santa2017_3());
	//	solveMoreLinesFile("src\\santa\\input\\santa_2017_4", new Santa2017_4());
		solveMoreLinesFile("src\\santa\\input\\santa_2017_5", new Santa2017_5());		
	}
	
	private static void solveProblem(SantaIssue issue) {
		long startTime = System.currentTimeMillis();
		issue.solvePart1(null, null);
		System.out.println("Part 1 time: " + (System.currentTimeMillis() - startTime));
		
		startTime = System.currentTimeMillis();
		issue.solvePart2(null, null);
		System.out.println("Part 2 time: " + (System.currentTimeMillis() - startTime));
	}
	
	private static void solveOneLineFile(String file, SantaIssue issue) {
		
		String data = reader.readSmallTextFile(new File(file).getAbsolutePath());
		long startTime = System.currentTimeMillis();
		issue.solvePart1(data, null);
		System.out.println("Part 1 time: " + (System.currentTimeMillis() - startTime));
		
		startTime = System.currentTimeMillis();
		issue.solvePart2(data, null);
		System.out.println("Part 2 time: " + (System.currentTimeMillis() - startTime));
	}
	
	private static void solveMoreLinesFile(String file, SantaIssue issue) {
		
		List<String> dataLines = reader.readFileByLines(new File(file).getAbsolutePath());
		long startTime = System.currentTimeMillis();
		issue.solvePart1(null, dataLines);
		System.out.println("Part 1 time: " + (System.currentTimeMillis() - startTime));
		
		startTime = System.currentTimeMillis();
		issue.solvePart2(null, dataLines);
		System.out.println("Part 2 time: " + (System.currentTimeMillis() - startTime));
	}
}
