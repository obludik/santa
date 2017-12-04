package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import file.FileReaderImpl;

public class Santa2016_21 {

	public static final String INPUT = "gahedfcb";
	
	public static void main(String[] args) {
		file.FileReader reader = new FileReaderImpl();
		List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_21_2").getAbsolutePath());
		long startTime = System.currentTimeMillis();

		Stream<String> lines = data.stream();
		List<Instruction> instructions = lines.map(item -> getInstruction(item))
				.collect(Collectors.toList());
		System.out.println(INPUT);
		StringBuilder builder = new StringBuilder(INPUT);
		for (Instruction instruction : instructions) {
			builder = instruction.convertString(builder);
			System.out.println(instruction.toString());
			System.out.println(builder.toString());
		}
		
		System.out.println(builder.toString());
		System.out.println("---------------------------------------------------------");
		String input = "fbgdceah";
		System.out.println(input);
		
		 builder = new StringBuilder(input);
		for (int i = 1; i <= instructions.size(); i++) {
			Instruction instruction = instructions.get(instructions.size() - i);
			builder = instruction.convertInvertedString(builder);
			System.out.println(instruction.toString());
			System.out.println(builder.toString());
		}
		
		System.out.println(builder.toString());
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}

	private static Instruction getInstruction(String instr) {
		String start = instr.substring(0, 8);
		switch (start) {
		case "swap pos":
			return new SwapPositionInstruction(instr);
		case "swap let":
			return new SwapLetterInstruction(instr);
		case "rotate l":
			return new RotateLeftInstruction(instr);
		case "rotate r":
			return new RotateRightInstruction(instr);
		case "rotate b":
			return new RotateBasedOnPositionInstruction(instr);
		case "reverse ":
			return new ReverseInstruction(instr);		
		case "move pos":
			return new MoveInstruction(instr);
		default:
			throw new RuntimeException("Unknown instruction: " + start);
		}
	}
	

	private static abstract class Instruction {
		String x;
		String y;
		String direction;
		
		public Instruction() {
		}
		
		public Instruction(String instr, Pattern pattern) {
			Matcher matcher = pattern.matcher(instr);
			if (matcher.find()) {
				setX(matcher);
				setY(matcher);
				setDirection(matcher);
			} else {
				throw new RuntimeException("Parsing error:" + instr);
			}
		}
		
		public abstract StringBuilder convertString(StringBuilder input);
		public abstract StringBuilder convertInvertedString(StringBuilder input);
		public abstract void setX(Matcher matcher);
		public abstract void setY(Matcher matcher);
		public abstract void setDirection(Matcher matcher);

		@Override
		public String toString() {
			return "Instruction [x=" + x + ", y=" + y + ", direction=" + direction + "]";
		}
	}
	
	private static class SwapPositionInstruction extends Instruction {
		
		public static final Pattern SWAP_POS = Pattern.compile("swap position (\\d) with position (\\d)");
		
		public SwapPositionInstruction(String instr) {
			super(instr, SWAP_POS);
		}
		
		@Override
		public StringBuilder convertString(StringBuilder input) {
			int indexX = Integer.parseInt(x);
			int indexY = Integer.parseInt(y);
			char[] arr = input.toString().toCharArray();
			char charX = arr[indexX];
			char charY = arr[indexY];
			arr[indexX] = charY;
			arr[indexY] = charX;
			return new StringBuilder(String.valueOf(arr));
		}

		@Override
		public void setX(Matcher matcher) {
			this.x = matcher.group(1);
		}

		@Override
		public void setY(Matcher matcher) {
			this.y = matcher.group(2);
		}

		@Override
		public void setDirection(Matcher matcher) {
			this.direction = null;
		}
		
		@Override
		public String toString() {
			return super.toString() + " " + this.getClass().getSimpleName();
		}

		@Override
		public StringBuilder convertInvertedString(StringBuilder input) {
			return convertString(input);
		}
	}
	
	private static class SwapLetterInstruction extends Instruction {
		
		public static final Pattern SWAP_LETTER = Pattern.compile("swap letter (\\w) with letter (\\w)");		
		
		public SwapLetterInstruction(String instr) {
			super(instr, SWAP_LETTER);
		}
		
		@Override
		public StringBuilder convertString(StringBuilder input) {
			int indexX = input.indexOf(x);
			int indexY = input.indexOf(y);
			char[] arr = input.toString().toCharArray();
			char charX = arr[indexX];
			char charY = arr[indexY];
			arr[indexX] = charY;
			arr[indexY] = charX;
			return new StringBuilder(String.valueOf(arr));
		}
		
		@Override
		public StringBuilder convertInvertedString(StringBuilder input) {
			return convertString(input);
		}
		
		@Override
		public void setX(Matcher matcher) {
			this.x = matcher.group(1);
		}

		@Override
		public void setY(Matcher matcher) {
			this.y = matcher.group(2);
		}

		@Override
		public void setDirection(Matcher matcher) {
			this.direction = null;
		}
		
		@Override
		public String toString() {
			return super.toString() + " " + this.getClass().getSimpleName();
		}
	}
	
	private static class RotateRightInstruction extends Instruction {
		
		public static final Pattern ROTATE_RIGHT = Pattern.compile("rotate right (\\d) steps");	
		
		public RotateRightInstruction(String instr) {
			super(instr, ROTATE_RIGHT);
		}
		
		public RotateRightInstruction(int step) {
			this.x = String.valueOf(step);
		}
		
		@Override
		public StringBuilder convertString(StringBuilder input) {
			int step = Integer.parseInt(x);
			char[] arr = input.toString().toCharArray();
			char[] arr2 = new char[arr.length];
			for (int i = 0; i < arr.length; i++) {
				int to = (i + step) < arr.length ? (i + step) : ((i + step) % arr.length);
				char charX = arr[i];
				arr2[to] = charX;
			}
			return new StringBuilder(String.valueOf(arr2));
		}

		@Override
		public StringBuilder convertInvertedString(StringBuilder input) {
			return (new RotateLeftInstruction(Integer.parseInt(x))).convertString(input);
		}
		
		@Override
		public void setX(Matcher matcher) {
			this.x = matcher.group(1);
		}

		@Override
		public void setY(Matcher matcher) {
			this.y = null;
		}

		@Override
		public void setDirection(Matcher matcher) {
			this.direction = null;
		}
		
		@Override
		public String toString() {
			return super.toString() + " " + this.getClass().getSimpleName();
		}
	}
	
	private static class RotateLeftInstruction extends Instruction {
		
		public static final Pattern ROTATE_LEFT = Pattern.compile("rotate (left) (\\d) step");	
		
		public RotateLeftInstruction(String instr) {
			super(instr, ROTATE_LEFT);
		}
		
		public RotateLeftInstruction(int step) {
			this.x = String.valueOf(step);
		}
		
		@Override
		public StringBuilder convertString(StringBuilder input) {
			int step = Integer.parseInt(x);
			char[] arr = input.toString().toCharArray();
			char[] arr2 = new char[arr.length];
			for (int i = 0; i < arr.length; i++) {
				int to = (i - (step % arr.length)) >= 0 ? 
							(i - (step % arr.length)) : (arr.length + (i - (step % arr.length)));
				char charX = arr[i];
				arr2[to] = charX;
			}
			return new StringBuilder(String.valueOf(arr2));
		}
		
		@Override
		public StringBuilder convertInvertedString(StringBuilder input) {
			return (new RotateRightInstruction(Integer.parseInt(x))).convertString(input);
		}
		
		@Override
		public void setX(Matcher matcher) {
			this.x = matcher.group(2);
		}

		@Override
		public void setY(Matcher matcher) {
			this.y = null;
		}

		@Override
		public void setDirection(Matcher matcher) {
			this.direction = matcher.group(1);
		}
		
		@Override
		public String toString() {
			return super.toString() + " " + this.getClass().getSimpleName();
		}
	}
	
	private static class ReverseInstruction extends Instruction {
		
		public static final Pattern REVERSE = Pattern.compile("reverse positions (\\d) through (\\d)");		
		
		public ReverseInstruction(String instr) {
			super(instr, REVERSE);
		}
		
		@Override
		public StringBuilder convertString(StringBuilder input) {
			int indexX = Integer.parseInt(x);
			int indexY = Integer.parseInt(y);
			char[] arr = input.toString().toCharArray();
			for (int i = indexX; i < indexX + ((indexY - indexX) + 1)/2; i++) {
				char charX = arr[i];
				char charY = arr[indexY - (i - indexX)];
				arr[i] = charY;
				arr[indexY - (i - indexX)] = charX;
			}
			return new StringBuilder(String.valueOf(arr));
		}
		
		@Override
		public StringBuilder convertInvertedString(StringBuilder input) {
			return convertString(input);
		}
		
		@Override
		public void setX(Matcher matcher) {
			this.x = matcher.group(1);
		}

		@Override
		public void setY(Matcher matcher) {
			this.y = matcher.group(2);
		}

		@Override
		public void setDirection(Matcher matcher) {
			this.direction = null;
		}
		
		@Override
		public String toString() {
			return super.toString() + " " + this.getClass().getSimpleName();
		}
	}
	
	private static class MoveInstruction extends Instruction {
		
		public static final Pattern MOVE = Pattern.compile("move position (\\d) to position (\\d)");		
		
		public MoveInstruction(String instr) {
			super(instr, MOVE);
		}
		
		@Override
		public StringBuilder convertString(StringBuilder input) {
			int indexX = Integer.parseInt(x);
			int indexY = Integer.parseInt(y);
			if (indexX > indexY) {
				return new StringBuilder(input.substring(0, indexY) + input.charAt(indexX)
					+ input.substring(indexY, indexX)
					+ input.substring(indexX + 1, input.length()));
			}
			return new StringBuilder(input.substring(0, indexX) + input.substring(indexX + 1, indexY + 1)
					+ input.charAt(indexX) + input.substring(indexY + 1, input.length()));
		}
		
		@Override
		public StringBuilder convertInvertedString(StringBuilder input) {
			String temp = y;
			y = x;
			x = temp;
			return convertString(input);
		}
		
		@Override
		public void setX(Matcher matcher) {
			this.x = matcher.group(1);
		}

		@Override
		public void setY(Matcher matcher) {
			this.y = matcher.group(2);
		}

		@Override
		public void setDirection(Matcher matcher) {
			this.direction = null;
		}
		
		@Override
		public String toString() {
			return super.toString() + " " + this.getClass().getSimpleName();
		}
	}
	
	private static class RotateBasedOnPositionInstruction extends Instruction {
		
		public static final Pattern ROTATE = Pattern.compile("rotate based on position of letter (\\w)");		
		
		public RotateBasedOnPositionInstruction(String instr) {
			super(instr, ROTATE);
		}

		@Override
		public StringBuilder convertString(StringBuilder input) {
			int step = input.indexOf(x);		
			if (step >= 4) {
				step++;
			}
			return (new RotateRightInstruction(step + 1)).convertString(input);
		}

		@Override
		public StringBuilder convertInvertedString(StringBuilder input) {
			int currentX = input.indexOf(x);
			int previousX = 0;
			List<Integer> results = new ArrayList<>();
			for (int i = 0; i < input.length(); i++) {
				if (currentX == (2*i+1+ (i >= 4 ? 1:0)) % input.length()) {
					previousX = i;
					results.add(i);
				}
			}
			System.out.println("Results->" + results.toString());
			int step = previousX + 1;
			if (previousX >= 4) {
				step++;
			}
			return (new RotateLeftInstruction(step)).convertString(input);
		}
		
		@Override
		public void setX(Matcher matcher) {
			this.x = matcher.group(1);
		}

		@Override
		public void setY(Matcher matcher) {
			this.y = null;
		}

		@Override
		public void setDirection(Matcher matcher) {
			this.direction = null;
		}
		
		@Override
		public String toString() {
			return super.toString() + " " + this.getClass().getSimpleName();
		}
	}
}
