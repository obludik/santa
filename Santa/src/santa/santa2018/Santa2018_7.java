package santa.santa2018;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import santa.SantaIssue;

public class Santa2018_7 implements SantaIssue {

	private static final Pattern PATTERN = Pattern
			.compile("Step ([A-Z]) must be finished before step ([A-Z]) can begin.");

	private static final int LETTER_COUNT = 26;
	private static final int ROBOT_COUNT = 5;
	private static final int DELAY = 60;
	List<Robot> robots = new ArrayList<>();
	LetterList letterList = new LetterList();
	
	private void parseData(List<String> dataLines) {
		letterList = new LetterList();
		for (String line : dataLines) {
			Matcher m = PATTERN.matcher(line);
			m.find();
			char condition = m.group(1).charAt(0);
			char letter = m.group(2).charAt(0);
			letterList.addCondition(letter, condition);
		}
	}
	
	private void solvePart1(List<String> dataLines) {
		parseData(dataLines);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < LETTER_COUNT; i++) {
			Letter next = letterList.getNoConditionLetter();
			result.append(next.letter);
			letterList.removeFromConditions(next);
		}
		System.out.println(result.toString());
	}

	private void solvePart2(List<String> dataLines) {
		parseData(dataLines);
		
		for (int i = 0; i < ROBOT_COUNT; i++) {
			robots.add(new Robot());
		}

		int second = 0;
		while (!noJob()) {

			for (Robot robot : robots) {
				if (robot.state == State.RUNNING) {
					if (robot.runTime == (DELAY + (robot.jobChar - 65))) {
						robot.state = State.NO_JOB;
						letterList.removeFromConditions(new Letter(robot.jobChar));
					} else {
						robot.runTime++;
					}
				}

			}
			for (Robot robot : robots) {
				if (robot.state == State.NO_JOB || robot.state == State.START) {
					Letter letter = letterList.getNoConditionLetter();
					if (letter != null) {
						robot.runTime = 0;
						robot.jobChar = letter.letter;
						robot.state = State.RUNNING;
					}
				}
			}

			second++;
		}

		System.out.println(second - 1);
	}

	private boolean noJob() {
		for (Robot robot : robots) {
			if (robot.state == State.RUNNING || robot.state == State.START) {
				return false;
			}
		}
		return true;
	}

	private enum State {
		RUNNING, NO_JOB, START
	}

	private class Robot {
		char jobChar;
		int runTime;
		State state = State.START;
	}

	private class LetterList {
		List<Letter> letters = new ArrayList<>();

		public void add(char letter) {
			if (!letters.contains(new Letter(letter))) {
				letters.add(new Letter(letter));
			}
		}

		public void addCondition(char letter, char condition) {
			add(letter);
			add(condition);
			letters.get(letters.indexOf(new Letter(letter))).conditions.add(new Letter(condition));
		}

		public Letter getNoConditionLetter() {
			Letter result = null;
			for (Letter letter : letters) {
				if (letter.conditions.size() == 0 && !letter.used) {
					if (result == null) {
						result = letter;
					} else {
						if (result.letter > letter.letter) {
							result = letter;
						}
					}
				}
			}
			if (result != null) {
				result.used = true;
			}
			return result;
		}

		public void removeFromConditions(Letter remove) {
			for (Letter letter : letters) {
				letter.conditions.remove(remove);
			}
		}

	}

	private class Letter implements Comparable<Letter> {
		char letter;
		List<Letter> conditions = new ArrayList<>();
		boolean used;

		public Letter(char letter) {
			this.letter = letter;
		}

		@Override
		public int hashCode() {
			return letter;
		}

		@Override
		public boolean equals(Object obj) {
			Letter other = (Letter) obj;
			if (letter != other.letter)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return letter + "";
		}

		@Override
		public int compareTo(Letter o) {
			return o.letter < letter ? 1 : -1;
		}

	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(dataLines);
		solvePart2(dataLines);
	}
}
