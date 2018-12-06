package util;

public class Utils {

	final static char HEX_CHARS[] = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	public static String byte2hexString(byte[] array) {
		StringBuilder sb = new StringBuilder(array.length * 2);
		for (int i = 0; i < array.length; i++) {
			int part1 = (array[i] & 0xF0) / 16;
			sb.append(HEX_CHARS[part1]);
			int part2 = (array[i] & 0x0F);
			sb.append(HEX_CHARS[part2]);
		}
		return sb.toString();
	}
}
