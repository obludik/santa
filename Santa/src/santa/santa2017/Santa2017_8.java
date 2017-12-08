package santa.santa2017;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import santa.SantaIssue;

public class Santa2017_8 implements SantaIssue {

	Memory memory = new Memory();
	
	public void solve(String data, List<String> dataLines) {
		int max = 0;		
		List<Instruction> instructions = dataLines
				.stream().map(line -> line.split(" ")).map(item -> new Instruction(item[0], item[4],
						Integer.parseInt(item[2]), Integer.parseInt(item[6]), item[5], item[1]))
				.collect(Collectors.toList());

		for (Instruction instruction : instructions) {
			int result = instruction.processInstruction();
			
			if (result > max) {
				max = result;
			}
		}
		
		System.out.println("Part 1: " + memory.getMaxFromRegisters());
		System.out.println("Part 2: " + max);
	}


	public class Instruction {

		String registerName;
		String conditionRegisterName;
		int value;
		int conditionValue;
		String condition;
		String operation;

		public Instruction(String registerName, String conditionRegisterName, int value, int conditionValue,
				String condition, String operation) {
			this.registerName = registerName;
			this.conditionRegisterName = conditionRegisterName;
			this.value = value;
			this.conditionValue = conditionValue;
			this.condition = condition;
			this.operation = operation;
		}

		public int processInstruction() {
			Register register = memory.getRegister(registerName);
			Register conditionRegister = memory.getRegister(conditionRegisterName);
			if (checkCondition(conditionRegister.value)) {
				if (operation.equals("dec")) {
					register.value = register.value - value;
				} else {
					register.value = register.value + value;
				}
			}
			return register.value;
		}

		public boolean checkCondition(int registerValue) {
			switch (condition) {
			case "<":
				if (registerValue < conditionValue) {
					return true;
				}
				break;
			case ">":
				if (registerValue > conditionValue) {
					return true;
				}
				break;
			case ">=":
				if (registerValue >= conditionValue) {
					return true;
				}
				break;
			case "<=":
				if (registerValue <= conditionValue) {
					return true;
				}
				break;
			case "==":
				if (registerValue == conditionValue) {
					return true;
				}
				break;
			case "!=":
				if (registerValue != conditionValue) {
					return true;
				}
				break;
			default:
				break;
			}
			return false;
		}

		@Override
		public String toString() {
			return "Instruction [registerName=" + registerName + ", conditionRegisterName=" + conditionRegisterName
					+ ", value=" + value + ", conditionValue=" + conditionValue + ", condition=" + condition
					+ ", operation=" + operation + "]";
		}
	}

	public class Register {

		public String id;
		public int value;

		public Register(String id) {
			this.id = id;
		}

		@Override
		public int hashCode() {
			return id.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Register other = (Register) obj;
			if (id.equals(other.id))
				return true;
			return false;
		}

		@Override
		public String toString() {
			return "Register [id=" + id + ", value=" + value + "]";
		}
	}
	
	public class Memory {
		
		List<Register> registers = new ArrayList<>();
		
		public Memory() {
			
		}
		
		public Memory(List<Register> registers) {
			this.registers = registers;
		}

		public Register getRegister(String registerName) {
			Register reg = new Register(registerName);
			if (registers.contains(reg)) {
				return registers.get(registers.indexOf(reg));
			} else {
				registers.add(reg);
				return reg;
			}
		}	
		
		public int getMaxFromRegisters() {
			int max = 0;
			for (Register register : registers) {
				if (register.value > max) {
					max = register.value;
				}
			}
			return max;
		}
	}
	
	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solve(data, dataLines);
	}

}
