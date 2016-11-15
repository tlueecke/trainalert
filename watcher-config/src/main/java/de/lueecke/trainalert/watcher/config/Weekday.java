package de.lueecke.trainalert.watcher.config;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public enum Weekday {

	MONDAY(Calendar.MONDAY), TUESDAY(Calendar.TUESDAY), WEDNESDAY(Calendar.WEDNESDAY), THURSDAY(
			Calendar.THURSDAY), FRIDAY(Calendar.FRIDAY), SATURDAY(Calendar.SATURDAY), SUNDAY(Calendar.SUNDAY);

	private int flag;
	private int calendarId;

	private Weekday(int calendarId) {
		this.calendarId = calendarId;
		this.flag = Double.valueOf(Math.pow(2d, (calendarId - 1))).intValue();
	}

	public static Weekday fromCalendarId(int calendarId) {
		for (Weekday weekday : values()) {
			if (calendarId == weekday.calendarId) {
				return weekday;
			}
		}
		throw new IllegalStateException();
	}

	public String bitmask() {
		return StringUtils.leftPad(Integer.toString(flag, 2), 7, '0');
	}

	public static Set<Weekday> fromString(String code) {
		if (!code.matches("[01]{7,7}"))
			throw new IllegalArgumentException("Expecting a 7-char bit string consisting of 0,1");
		Integer bitmask = Integer.parseInt(code, 2);
		Set<Weekday> weekdays = new HashSet<>();
		for (Weekday weekday : values()) {
			if ((weekday.flag & bitmask) == weekday.flag) {
				weekdays.add(weekday);
			}
		}
		return weekdays;
	}

	public static String toString(Set<Weekday> weekdays) {
		int bitmask = 0;
		for (Weekday weekday : weekdays) {
			bitmask |= weekday.flag;
		}
		return StringUtils.leftPad(Integer.toString(bitmask, 2), 7, '0');
	}

}
