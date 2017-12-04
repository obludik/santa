package santa.santa2016;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import file.FileReaderImpl;

public class Santa2016_12 {

	private enum Type {
		CPY, INC, DEC, JNZ
	}

	static Map<String, Register> registers = new HashMap<>();

	private static class Register {
		String name;
		Integer value;

		public Register(String name, Integer value) {
			this.name = name;
			this.value = value;
		}

		public void increaseValue() {
			this.value++;
		}

		public void decreaseValue() {
			this.value--;
		}

		public void setValue(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Register [name=" + name + ", value=" + value + "]";
		}

	}

	private static class Instruction {
		Type instr;
		String param1;
		String param2;

		public Instruction(String instr, String param1, String param2) {
			this.instr = Type.valueOf(instr.toUpperCase());
			this.param1 = param1;
			this.param2 = param2;
		}

		@Override
		public String toString() {
			return "Instruction [instr=" + instr + ", param1=" + param1 + ", param2=" + param2 + "]";
		}
	}

	public static void main(String[] args) {
		file.FileReader reader = new FileReaderImpl();
		List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_12").getAbsolutePath());
		long startTime = System.currentTimeMillis();

		registers.put("a", new Register("a", 0));
		registers.put("b", new Register("b", 0));
		registers.put("c", new Register("c", 1));
		registers.put("d", new Register("d", 0));

		Stream<String> lines = data.stream();
		List<Instruction> instructions = lines.map(line -> line.split(" "))
				.map(item -> new Instruction(item[0], item[1], item.length > 2 ? item[2] : null))
				.collect(Collectors.toList());

		int startIndex = 0;
		while (startIndex < instructions.size() && startIndex >= 0) {
			startIndex = continueWithInstructions(instructions, startIndex);
		}
		System.out.println(Arrays.toString(registers.values().toArray()));
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}

	private static int continueWithInstructions(List<Instruction> instructions, int startIndex) {
		for (int i = startIndex; i < instructions.size(); i++) {
			Instruction instruction = instructions.get(i);

			switch (instruction.instr) {
			case CPY:
				registers.get(instruction.param2).setValue(getValue(instruction.param1));
				break;
			case DEC:
				registers.get(instruction.param1).decreaseValue();
				break;
			case INC:
				registers.get(instruction.param1).increaseValue();
				break;
			case JNZ:
				if (getValue(instruction.param1) != 0) {
					return i + Integer.parseInt(instruction.param2);
				}
				break;
			default:
				break;
			}

		}
		return instructions.size();
	}

	private static int getValue(String registerOrInt) {
		try {
			int i = Integer.parseInt(registerOrInt);
			return i;
		} catch (NumberFormatException e) {
			return registers.get(registerOrInt).value;
		}
	}
}
