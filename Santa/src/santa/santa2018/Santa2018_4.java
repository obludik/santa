package santa.santa2018;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import santa.SantaIssue;

public class Santa2018_4 implements SantaIssue {

	private static final Pattern PATTERN = Pattern
			.compile("\\[([0-9]*-[0-9]*-[0-9]*[ ][0-9]*:[0-9]*)\\][ ]([a-zA-Z])[a-zA-Z]*[ ]*#*([0-9]*)[a-zA-Z ]*");

	public void solvePart12(List<String> dataLines) {
		List<Record> records = parseAndSortData(dataLines);
		Map<Integer, Guard> guards = new HashMap<>();
		int currentGuard = records.get(0).guardId;
		Date sleepStart = null;
		int maxSleepGuardId = 0;
		int maxSleepTime = 0;
		
		for (Record record : records) {
			switch (record.state) {
			case START:
				currentGuard = record.guardId;
				break;
			case SLEEP:
				sleepStart = record.date;
				break;
			case WAKE:
				int diffInMinutes = (int)((record.date.getTime() - sleepStart.getTime()) / 1000 / 60);
				Calendar calc = Calendar.getInstance();
				calc.setTime(sleepStart);
				int startMinute = calc.get(Calendar.MINUTE);
				calc.setTime(record.date);
				int endMinute = calc.get(Calendar.MINUTE);
				if (!guards.containsKey(currentGuard)) {
					guards.put(currentGuard, new Guard(currentGuard));

				}
				for (int i = startMinute; i < endMinute; i++) {
					guards.get(currentGuard).sleepMinutes[i]++;
				}
				guards.get(currentGuard).sleepTime = guards.get(currentGuard).sleepTime + diffInMinutes;
				if (guards.get(currentGuard).sleepTime >= maxSleepTime) {
					maxSleepGuardId = currentGuard;
					maxSleepTime = guards.get(currentGuard).sleepTime;
				}
				break;
			}
		}

		int maxIdx = 0;
		int maxSleep = 0;
		int maxSleepIndex = 0;
		Guard minuteSleeper = null;
		for (Guard guard : guards.values()) {
			List<Integer> slMinutes = Arrays.stream(guard.sleepMinutes).boxed().collect(Collectors.toList());
			maxIdx = IntStream.range(0, slMinutes.size()).reduce((i, j) -> slMinutes.get(i) > slMinutes.get(j) ? i : j)
					.getAsInt();
			if (guard.guardId == maxSleepGuardId) {
				System.out.println("Part 1: Guard ID " + maxSleepGuardId + " in minute " + maxIdx + " result: "
						+ maxSleepGuardId * maxIdx);
			}
			if (guard.sleepMinutes[maxIdx] > maxSleep) {
				maxSleep = guard.sleepMinutes[maxIdx];
				maxSleepIndex = maxIdx;
				minuteSleeper = guard;
			}
		}
		System.out.println("Part 2: Guard ID " + minuteSleeper.guardId + " in minute " + maxSleepIndex + " result: "
				+ minuteSleeper.guardId * maxSleepIndex);
	}

	private List<Record> parseAndSortData(List<String> dataLines) {
		List<Record> records = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		for (String line : dataLines) {
			Matcher m = PATTERN.matcher(line);
			m.find();
			String date = m.group(1);
			Date d = null;
			int guardId = 0;
			if (!m.group(3).isEmpty()) {
				guardId = Integer.parseInt(m.group(3));
			}
			try {
				d = sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			State state = null;
			switch (m.group(2)) {
			case "w":
				state = State.WAKE;
				break;
			case "f":
				state = State.SLEEP;
				break;
			case "G":
				state = State.START;
				break;
			}
			records.add(new Record(d, guardId, state));
		}
		Collections.sort(records, new DateComparator());
		return records;
	}

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart12(dataLines);
	}

	class DateComparator implements Comparator<Record> {
		@Override
		public int compare(Record a, Record b) {
			return a.date.compareTo(b.date);
		}
	}

	private enum State {
		SLEEP, WAKE, START
	}

	private class Record {
		Date date;
		int guardId;
		State state;

		public Record(Date date, int guardId, State state) {
			this.date = date;
			this.guardId = guardId;
			this.state = state;
		}
	}

	private class Guard {
		int guardId;
		int sleepTime;
		int[] sleepMinutes = new int[100];

		public Guard(int guardId) {
			this.guardId = guardId;
		}
	}
}
