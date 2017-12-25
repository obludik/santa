package santa.santa2017;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import santa.SantaIssue;

public class Santa2017_10 implements SantaIssue {

	public static final int SIZE = 256;

	public void solvePart1(String data, List<String> dataLines) {
		int[] lengths = Arrays.stream(data.split(",")).mapToInt(Integer::parseInt).toArray();
		int[] elements = IntStream.rangeClosed(0, SIZE - 1).toArray();
		int skipSize = 0;
		int startIndex = 0;
		for (int len : lengths) {
			reverseArrayPart(elements, startIndex, startIndex + len);
			startIndex = startIndex + len + skipSize++;
		}
		System.out.println("Result: " + elements[0] * elements[1]);
	}

	public void solvePart2(String data, List<String> dataLines) {
		System.out.println("Result: " + getKnotHash(data));
	}

	/**
	 * Reverses part of array - start index inclusive, end index exclusive.
	 * 
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 */
	private void reverseArrayPart(int[] array, int startIndex, int endIndex) {
		int half = startIndex + (endIndex - startIndex) / 2;
		int endCount = endIndex - 1;
		for (int startCount = startIndex; startCount < half; startCount++) {
			int store = array[startCount % SIZE];
			array[startCount % SIZE] = array[endCount % SIZE];
			array[endCount % SIZE] = store;
			endCount--;
		}
	}

	public String getKnotHash(String input) {
		byte[] lengthsPart = new String(input).getBytes();
		byte[] lengths = new byte[lengthsPart.length + 5];
		System.arraycopy(lengthsPart, 0, lengths, 0, lengthsPart.length);
		lengths[lengths.length - 5] = 17;
		lengths[lengths.length - 4] = 31;
		lengths[lengths.length - 3] = 73;
		lengths[lengths.length - 2] = 47;
		lengths[lengths.length - 1] = 23;

		int[] elements = IntStream.rangeClosed(0, SIZE - 1).toArray();
		int skipSize = 0;
		int startIndex = 0;

		for (int i = 0; i < 64; i++) {
			for (int len : lengths) {
				reverseArrayPart(elements, startIndex, startIndex + len);
				startIndex = startIndex + len + skipSize++;
			}
		}

		int[] result = new int[16];
		for (int i = 0; i < 16; i++) {
			int xor = 0;
			for (int j = 16 * i; j < 16 * i + 16; j++) {
				xor ^= elements[j];
			}
			result[i] = xor;
		}

		return Arrays.stream(result).mapToObj(i -> String.format("%02x", i)).collect(Collectors.joining(""));
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		solvePart2(data, dataLines);
	}

}
