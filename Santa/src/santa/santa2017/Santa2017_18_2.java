package santa.santa2017;

import java.math.BigDecimal;
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

	static BlockingQueue<BigDecimal> queue1 = new LinkedBlockingQueue<>();
	static BlockingQueue<BigDecimal> queue2 = new LinkedBlockingQueue<>();
	boolean program0Waits = false;
	boolean program1Waits = false;
	static long counterProgram1 = 0;

	public class ProgramThread extends Thread {

		BlockingQueue<BigDecimal> in;
		BlockingQueue<BigDecimal> out;
		List<Instruction> instructions;
		int id;
		int counter = 0;
		Map<String, Register> registers = new HashMap<>();

		public ProgramThread(int id, List<Instruction> instructions, BlockingQueue<BigDecimal> in,
				BlockingQueue<BigDecimal> out) {
			this.in = in;
			this.out = out;
			this.id = id;
			this.instructions = instructions;
		}

		long max = 0;

		private int processInstructions(List<Instruction> instructions, int startIndex) {

			for (int i = startIndex; i < instructions.size(); i++) {
				Instruction instruction = instructions.get(i);

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
					getOrCreateRegister(instruction.param1).value = getOrCreateRegister(instruction.param1).value
							.add(getValue(instruction.param2));
					break;
				case mul:
					getOrCreateRegister(instruction.param1).value = getOrCreateRegister(instruction.param1).value
							.multiply(getValue(instruction.param2));
					break;
				case mod:
					getOrCreateRegister(instruction.param1).value = getOrCreateRegister(instruction.param1).value
							.remainder(getValue(instruction.param2));
					break;
				case rcv:
					if (id == 0) {
						try {
							program0Waits = true;
							getOrCreateRegister(instruction.param1).value = in.take();
							program0Waits = false;
						} catch (InterruptedException e) {
							System.out.println("Interrupted");
						}

					} else {
						try {
							program1Waits = true;
							getOrCreateRegister(instruction.param1).value = in.take();
							program1Waits = false;
						} catch (InterruptedException e) {
							System.out.println("Interrupted");
						}

					}
					break;
				case jgz:
					if (getOrCreateRegister(instruction.param1).value.compareTo(new BigDecimal(0)) == 1) {
						System.out.println(id + "  " + instruction + " " + registers);
						BigDecimal val = getValue(instruction.param2);
						BigDecimal next = val.add(new BigDecimal(i));
						if (next.compareTo(new BigDecimal(instructions.size())) == 1) {
							return -1;
						}
						if (next.compareTo(new BigDecimal(0)) == -1) {
							return -1;
						}
						return next.intValue();
					}
					break;
				default:
					break;
				}

				System.out.println(id + "  " + instruction + " " + registers);
			}
			return instructions.size();
		}

		private Register getOrCreateRegister(String register) {
			if (!registers.containsKey(register)) {
				if (register.equals("p")) {
					registers.put(register, new Register(register, new BigDecimal(id)));
				} else {
					registers.put(register, new Register(register, new BigDecimal(0)));
				}
			}
			return registers.get(register);
		}

		private BigDecimal getValue(String registerOrInt) {
			try {
				BigDecimal i = new BigDecimal(Long.parseLong(registerOrInt));
				return i;
			} catch (NumberFormatException e) {
				return getOrCreateRegister(registerOrInt).value;
			}
		}

		public void run() {
			System.out.println("Hello from a thread " + id);
			int startIndex = 0;
			while (startIndex < instructions.size() && startIndex >= 0) {
				startIndex = processInstructions(instructions, startIndex);
			}
		}

	}

	public void solvePart1(String data, List<String> dataLines) {
		List<Instruction> instructions = dataLines.stream().map(line -> line.split(" "))
				.map(item -> new Instruction(item[0], item[1], item.length > 2 ? item[2] : null))
				.collect(Collectors.toList());

		Thread thread0 = new ProgramThread(0, instructions, queue1, queue2);
		Thread thread1 = new ProgramThread(1, instructions, queue2, queue1);
		thread0.start();
		thread1.start();

		while (true) {
			if (program1Waits && program0Waits && queue1.size() == 0 && queue2.size() == 0) {
				thread0.interrupt();
				thread1.interrupt();
				System.out.println("Deadlock counter " + counterProgram1);
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class Register {
		String name;
		BigDecimal value;

		public Register(String name, BigDecimal value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString() {
			return "Register [name=" + name + ", value=" + value.toString() + "]";
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
		// solvePart2(data, dataLines);
	}

}
