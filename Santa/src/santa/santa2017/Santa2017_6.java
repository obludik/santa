package santa.santa2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import santa.SantaIssue;

public class Santa2017_6 implements SantaIssue {

	private static final String DELIMITER = "\\t";
	
	int order = 0;
	
	@Override
	public void solvePart1(String data, List<String> dataLines) {
		solve(data, dataLines);
		
	}

	@Override
	public void solvePart2(String data, List<String> dataLines) {
		solve(data, dataLines);
	}
	
	public void solve(String data, List<String> dataLines) {
		List<Register> registers = Arrays.stream(data.split(DELIMITER))
				.map(i -> new Register(Integer.valueOf(i)))
                .collect(Collectors.toList());
		Memory memory = new Memory(registers);
		List<Memory> results = new ArrayList<Memory>();
		
		int steps = 1;
		int cycle = 0;
		while (true) {
			int biggest = memory.getBiggest().num;
			int start = memory.getBiggest().id + 1;
			memory.getBiggest().num = 0;
			for (int j = 0; j < biggest; j++) {
				start = start % registers.size();
				memory.increaseRegisterVal(start++);				
			}
			List<Register> current = new ArrayList<Register>();
			for (Register req : memory.registers) {
				current.add(new Register(req.num, req.id));
			}
			Memory result = new Memory(current);
			if (results.contains(result)) {
				int index = results.indexOf(result);
				cycle = results.size() - index;
				break;
			}
			steps++;
			results.add(result); // chybi prvni zadani
		}
		System.out.println("Part 1: " + steps);
		System.out.println("Part 2: " + cycle);
	}
	
	public class Register {
		int num;	
		int id;
		
		public Register(int num) {
			this.num = num;
			this.id = order++;
		}
		
		public Register(int num, int id) {
			this.num = num;
			this.id = id;
		}
		
		public void increase() {
			num++;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + num;
			result = prime * result + id;
			return result;
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
			if (num == other.num && id == other.id)
				return true;
			return false;
		}

		@Override
		public String toString() {
			return "Register [num=" + num + ", id=" + id + "]";
		}	
	}
	
	public class Memory {
		
		List<Register> registers;
		
		public Memory(List<Register> registers) {
			this.registers = registers;
		}

		public Register getBiggest() {
			Register biggest = registers.get(0);
			for (Register register : registers) {
				if (register.num > biggest.num) {
					biggest = register;
				}
			}
			return biggest;
		}
		
		public void increaseRegisterVal(int order) {
			registers.get(order).increase();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((registers == null) ? 0 : registers.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Memory other = (Memory) obj;
			for (int i = 0; i < other.registers.size(); i++) {				
				if (!registers.get(i).equals(other.registers.get(i))) {
					return false;
				}
			}
			return true;
		}	
	}
}
