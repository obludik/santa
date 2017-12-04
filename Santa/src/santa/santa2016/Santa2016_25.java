package santa.santa2016;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import file.FileReaderImpl;

public class Santa2016_25 {

	private enum Type {
		CPY, INC, DEC, JNZ, TGL, OUT
	}

	static Map<String, Register> registers = new HashMap<>();

	private static class Register {
		String name;
		Long value;

		public Register(String name, Long value) {
			this.name = name;
			this.value = value;
		}

		public void increaseValue() {
			this.value++;
		}

		public void decreaseValue() {
			this.value--;
		}

		public void setValue(long value) {
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
		List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_25").getAbsolutePath());
		long startTime = System.currentTimeMillis();

		registers.put("a", new Register("a", 0L));
		registers.put("b", new Register("b", 0L));
		registers.put("c", new Register("c", 0L));
		registers.put("d", new Register("d", 0L));

		Stream<String> lines = data.stream();
		List<Instruction> instructions = lines.map(line -> line.split(" "))
				.map(item -> new Instruction(item[0], item[1], item.length > 2 ? item[2] : null))
				.collect(Collectors.toList());

		for (int i = 0; i < 5000; i++) {
			processA(i, instructions);
			System.out.println("End: " + i + " seq: " + seq);		
		}
		
		System.out.println(Arrays.toString(registers.values().toArray()));
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}

	private static void processA(long a, List<Instruction> instructions) {
		registers.put("a", new Register("a", a)); 
		registers.put("b", new Register("b", 0L));
		registers.put("c", new Register("c", 0L));
		registers.put("d", new Register("d", 0L));
		counter = 0;
		seq = "";
		lastOut = 1;
		long startIndex = 0;
		while (startIndex < instructions.size() && startIndex >= 0) {
			startIndex = continueWithInstructions(instructions, startIndex);
			if (startIndex == -1) {
				return;
			}
			if (startIndex == -20) {
				System.out.println(a);	
				System.out.println("***********************************************");	
				return;
			}		
		}
	}
	
	static int lastOut = 1;
	static int counter = 0;
	static String seq = "";
	
	private static long continueWithInstructions(List<Instruction> instructions, long startIndex) {
		for (long i = startIndex; i < instructions.size(); i++) {
			Instruction instruction = instructions.get((int)i);

			switch (instruction.instr) {
			case CPY:
				if (Character.isLetter(instruction.param2.charAt(0))) {						
					registers.get(instruction.param2).setValue(getValue(instruction.param1));
				}				
				break;
			case DEC:
				if (Character.isLetter(instruction.param1.charAt(0))) {	
					registers.get(instruction.param1).decreaseValue();
				}
				break;
			case INC:
				if (Character.isLetter(instruction.param1.charAt(0))) {	
					registers.get(instruction.param1).increaseValue();
				}
				break;
			case JNZ:
				if (getValue(instruction.param1) != 0) {
						return i + getValue(instruction.param2);
				}
				break;
			case TGL:
				long changedInstrIndex = i + getValue(instruction.param1);
				if (changedInstrIndex < 0 || changedInstrIndex >= instructions.size()) {
					break;
				}				
				Instruction changedInstr = instructions.get((int)changedInstrIndex);
				switch (changedInstr.instr) {
				case CPY:
					changedInstr.instr = Type.JNZ;
					break;
				case DEC:
				case TGL:
				case OUT:
					changedInstr.instr = Type.INC;
					break;
				case INC:
					changedInstr.instr = Type.DEC;
					break;
				case JNZ:
					changedInstr.instr = Type.CPY;
					break;				
				}
				break;
			case OUT:
				long out = getValue(instruction.param1);
				if (lastOut == 0 && out == 1) {
					lastOut = 1;
				} else if (lastOut == 1 && out == 0) {
					lastOut = 0;
				} else {
					return -1;
				}
				counter++;
				seq += out;
				if (counter == 10000) {
					return -20;
				}
				
				break;
			}

		}
		return instructions.size();
	}

	private static long getValue(String registerOrLong) {
		try {
			long i = Long.parseLong(registerOrLong);
			return i;
		} catch (NumberFormatException e) {
			return registers.get(registerOrLong).value;
		}
	}
}
