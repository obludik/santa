package santa.santa2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import santa.SantaIssue;

public class Santa2017_18_2 implements SantaIssue {

	private enum Type {
		snd, set, add, mul, mod, rcv, jgz
	}

	boolean program0Waits = false;
	boolean program1Waits = false;
	static long counterProgram1 = 0;

	public class ProgramThread extends Thread {

		BlockingQueue<Long> in;
		BlockingQueue<Long> out;
		List<Instruction> instructions;
		int id;
		int counter = 0;
		Map<String, Register> registers = new HashMap<>();

		public ProgramThread(int id, List<Instruction> instructions, BlockingQueue<Long> in, BlockingQueue<Long> out) {
			this.in = in;
			this.out = out;
			this.id = id;
			this.instructions = instructions;
		}

		private long processInstructions(List<Instruction> instructions, long startIndex) {

			for (long i = startIndex; i < instructions.size(); i++) {
				Instruction instruction = instructions.get((int) i);

				switch (instruction.instr) {
				case snd:
					out.add(getValue(instruction.param1));
					if (id == 1) {
						counterProgram1++;
					}
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
					if (id == 0) {
						try {
							program0Waits = true;
							getOrCreateRegister(instruction.param1).value = in.take();
							program0Waits = false;
						} catch (InterruptedException e) {
							System.out.println("Interrupted");
							return -1;
						}
					} else {
						try {
							program1Waits = true;
							getOrCreateRegister(instruction.param1).value = in.take();
							program1Waits = false;
						} catch (InterruptedException e) {
							System.out.println("Interrupted");
							return -1;
						}
					}
					break;
				case jgz:
					if (getValue(instruction.param1) > 0) {
						long val = getValue(instruction.param2);
						return i + val;
					}
					break;
				default:
					break;
				}
			}
			return instructions.size();
		}

		private Register getOrCreateRegister(String register) {

			if (!registers.containsKey(register)) {
				if (register.equals("p")) {
					registers.put(register, new Register(register, (long) id));
				} else {
					registers.put(register, new Register(register, 0L));
				}
			}
			return registers.get(register);

		}

		private long getValue(String registerOrInt) {
			try {
				return Long.parseLong(registerOrInt);
			} catch (NumberFormatException e) {
				return getOrCreateRegister(registerOrInt).value;
			}
		}

		public void run() {
			System.out.println("Hello from a thread " + id);
			long startIndex = 0;
			while (startIndex < instructions.size() && startIndex >= 0) {
				startIndex = processInstructions(instructions, startIndex);
			}
		}

	}

	public void solvePart2(String data, List<String> dataLines) {
		List<Instruction> instructions = dataLines.stream().map(line -> line.split(" "))
				.map(item -> new Instruction(item[0], item[1], item.length > 2 ? item[2] : null))
				.collect(Collectors.toList());

		BlockingQueue<Long> queue1 = new LinkedBlockingQueue<>();
		BlockingQueue<Long> queue2 = new LinkedBlockingQueue<>();

		Thread thread0 = new ProgramThread(0, instructions, queue1, queue2);
		Thread thread1 = new ProgramThread(1, instructions, queue2, queue1);
		thread0.start();
		thread1.start();

		while (true) {
			if (program1Waits && program0Waits && queue1.size() == 0 && queue2.isEmpty()) {
				thread0.interrupt();
				thread1.interrupt();
				System.out.println(counterProgram1);
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class Register {
		String name;
		long value;

		public Register(String name, long value) {
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
		solvePart2(data, dataLines);
	}

}
