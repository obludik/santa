package santa.santa2018;

import java.util.Arrays;
import java.util.List;

import santa.SantaIssue;

public class Santa2018_9 implements SantaIssue {

	private static final int MARBLE_COUNT = 7162800;
	private static final int PLAYER_COUNT = 448;

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		long[] scores = new long[PLAYER_COUNT];
		int playerIndex = 1;
		int currentMarble = 2;

		Marble first = new Marble(0);
		Marble second = new Marble(1);

		first.nextMarble = second;
		second.previousMarble = first;
		Marble current = second;
		Marble one = first;
		Marble last = second;

		for (int i = 0; i < MARBLE_COUNT - 1; i++) {

			if (currentMarble % 23 == 0) {
				Marble toRemove = current;
				for (int j = 0; j < 7; j++) {
					if (toRemove == one) {
						toRemove = last;
					} else {
						toRemove = toRemove.previousMarble;
					}
				}

				scores[playerIndex] += currentMarble;
				scores[playerIndex] += toRemove.value;
				toRemove.previousMarble.nextMarble = toRemove.nextMarble;
				if (toRemove.nextMarble != null) {
					toRemove.nextMarble.previousMarble = toRemove.previousMarble;
				}
				current = toRemove.nextMarble;
				currentMarble++;
			} else {
				if (current.nextMarble != null) {
					Marble newMarble = new Marble(currentMarble++);
					newMarble.nextMarble = current.nextMarble.nextMarble;
					if (current.nextMarble.nextMarble != null) {
						current.nextMarble.nextMarble.previousMarble = newMarble;
					}
					newMarble.previousMarble = current.nextMarble;
					current.nextMarble.nextMarble = newMarble;
					current = newMarble;
					if (current.previousMarble == last) {
						last = current;
					}

				} else {
					Marble newMarble = new Marble(currentMarble++);
					newMarble.nextMarble = one.nextMarble;
					one.nextMarble.previousMarble = newMarble;
					one.nextMarble = newMarble;
					newMarble.previousMarble = one;
					current = newMarble;
				}
			}
		}
		Arrays.sort(scores);
		System.out.println(scores[scores.length - 1]);
	}

	private class Marble {
		int value;
		Marble nextMarble;
		Marble previousMarble;

		public Marble(int value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + value;
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
			Marble other = (Marble) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (value != other.value)
				return false;
			return true;
		}

		private Santa2018_9 getOuterType() {
			return Santa2018_9.this;
		}

		@Override
		public String toString() {
			return "Marble [value=" + value + "]";
		}
	}

}
