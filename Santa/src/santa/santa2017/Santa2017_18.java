package santa.santa2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import santa.SantaIssue;

public class Santa2017_18 implements SantaIssue {

	private enum Type {
		snd, set, add, mul, mod, rcv, jgz
	}

	static Map<String, Register> registers = new HashMap<>();
	static long playedFrequency = 0;
			
	public void solvePart1(String data, List<String> dataLines) {
		List<Instruction> instructions = dataLines.stream().map(line -> line.split(" "))
				.map(item -> new Instruction(item[0], item[1], item.length > 2 ? item[2] : null))
				.collect(Collectors.toList());
		
		long startIndex = 0;
		while (startIndex < instructions.size() && startIndex >= 0) {
			startIndex = processInstructions(instructions, startIndex);
		}
	}
	
	private static long processInstructions(List<Instruction> instructions, long startIndex) {
		for (long i = startIndex; i < instructions.size(); i++) {
			Instruction instruction = instructions.get((int)i);
				switch (instruction.instr) {
				case snd:
					playedFrequency = getOrCreateRegister(instruction.param1).value;
					break;
				case set:				
					getOrCreateRegister(instruction.param1).value = getValue(instruction.param2);
					break;
				case add:
					getOrCreateRegister(instruction.param1).value += getValue(instruction.param2);
					break;
				case mul:
					getOrCreateRegister(instruction.param1).value *= getValue(instruction.param2);
					break;
				case mod:
					getOrCreateRegister(instruction.param1).value %= getValue(instruction.param2);
					break;	
				case rcv:
					if (getOrCreateRegister(instruction.param1).value > 0) {
						System.out.println("Frequency: "  + playedFrequency);
						return -1;
					}
					break;		
				case jgz:
					if (getOrCreateRegister(instruction.param1).value > 0) {
						return i + getValue(instruction.param2);
					}
					break;				
				default:
					break;
				}
			}
		return instructions.size();
	}

	private static Register getOrCreateRegister(String register) {
		if (!registers.containsKey(register)) {
			registers.put(register, new Register(register, 0L));
		}
		return registers.get(register);
	}
	
	private static long getValue(String registerOrInt) {
		try {
			long i = Long.parseLong(registerOrInt);
			return i;
		} catch (NumberFormatException e) {
			return getOrCreateRegister(registerOrInt).value;
		}
	}
	
	private static class Register {
		String name;
		Long value;

		public Register(String name, Long value) {
			this.name = name;
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
			this.instr = Type.valueOf(instr);
			this.param1 = param1;
			this.param2 = param2;
		}

		@Override
		public String toString() {
			return "Instruction [instr=" + instr + ", param1=" + param1 + ", param2=" + param2 + "]";
		}
	}
	
	
	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(data, dataLines);
		//solvePart2(data, dataLines);
	}

}
