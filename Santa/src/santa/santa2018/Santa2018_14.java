package santa.santa2018;

import java.util.List;

import santa.SantaIssue;

public class Santa2018_14 implements SantaIssue {
	
	private static final int STEPS = 825401;
	private static final String INPUT = "825401";
	
	Entry first = new Entry(3);
	Entry last = new Entry(7);
	
	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		
		first.setNext(last);
		last.setPrevious(first);
		Entry firstElfPosition = first;
		Entry secondElfPosition = last;
		
		while (true) {
			int newReceiptVal = firstElfPosition.getValue() + secondElfPosition.getValue();
			if (newReceiptVal < 10) {
				getNewEntryAfterLast(newReceiptVal);
			} else {
				getNewEntryAfterLast(newReceiptVal / 10);
				getNewEntryAfterLast(newReceiptVal % 10);
			}
			 
			firstElfPosition = getEntryAfterNumOfSteps(firstElfPosition.getValue() + 1, firstElfPosition);
			secondElfPosition = getEntryAfterNumOfSteps(secondElfPosition.getValue() + 1, secondElfPosition);
			//printEntries();
			if (getLastEntriesString().indexOf(INPUT) > -1) {
				System.out.println("Part 2: " + getEntriesString().indexOf(INPUT));
				break;
			}
		}

		StringBuilder result = new StringBuilder();
		Entry current = first;
		for (int i = 0; i < STEPS + 10; i++) {
			if (i >= STEPS) {
				result.append(current.getValue());
			}
			current = current.getNext();
		}
		System.out.println("Part 1: " + result.toString());
	}
	
	public void printEntries() {
		Entry current = first;
		while (current != null) {
			System.out.print(current.getValue());
			current = current.getNext();
		}
		System.out.println();
	}
	
	public String getEntriesString() {
		StringBuilder result = new StringBuilder();
		Entry current = first;
		while (current != null) {
			result.append(current.getValue());
			current = current.getNext();
		}
		return result.toString();
	}
	
	public String getLastEntriesString() {
		StringBuilder result = new StringBuilder();
		Entry current = last;
		for (int i = 0; i < 7; i++) {
			if (current == null) {
				break;
			}
			result.append(current.getValue());
			current = current.getPrevious();
		}
		return result.reverse().toString();
	}
	
	public void getNewEntryAfterLast(int value) {
		Entry newReceipt = new Entry(value);
		last.setNext(newReceipt);
		newReceipt.setPrevious(last);
		last = newReceipt;
	}
	
	public Entry getEntryAfterNumOfSteps(int steps, Entry start) {
		Entry result = start;
		for (int i = 0; i < steps; i++) {
			if (result.getNext() == null) {
				result = first;
			} else {
				result = result.getNext();
			}
		}
		return result;
	}
	
	private class Entry {
		int value;
		Entry next;
		Entry previous;

		public Entry(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}

		public Entry getNext() {
			return next;
		}

		public void setNext(Entry next) {
			this.next = next;
		}

		public Entry getPrevious() {
			return previous;
		}

		public void setPrevious(Entry previous) {
			this.previous = previous;
		}

		@Override
		public String toString() {
			return "Entry [value=" + value + "]";
		}
	}
}
