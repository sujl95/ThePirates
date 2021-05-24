package com.example.thepirates.common.util;

import static com.example.thepirates.controller.common.BusinessStatus.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import com.example.thepirates.controller.common.BusinessStatus;
import com.example.thepirates.entity.BusinessTime;
import com.example.thepirates.entity.Holiday;

public class DateUtils {

	private static final LocalTime LOCAL_TIME_MIN = LocalTime.MIN;

	public static boolean isHoliday(Set<Holiday> holidays, LocalDate date) {
		return holidays.stream().anyMatch(holiday -> date.equals(holiday.getDay()));
	}

	public static boolean isDayEqualsWeek(LocalDate date, BusinessTime businessTime) {
		return date.getDayOfWeek() == DayOfWeek.valueOf(businessTime.getDay().getDay().toUpperCase());
	}

	public static boolean isShopStatusOpen(LocalTime time, LocalTime close, LocalTime open) {
		return time.isAfter(open) && time.isBefore(getLocalTime(close));
	}


	public static BusinessStatus getBusinessStatus(Set<BusinessTime> businessTimes, LocalDate date, LocalTime time) {
		for (BusinessTime businessTime : businessTimes) {
			if (isDayEqualsWeek(date, businessTime) && isShopStatusOpen(time, getLocalTime(businessTime.getClose()), businessTime.getOpen())) {
				return OPEN;
			}
		}
		return CLOSE;
	}

	public static String localTimeFormat(LocalTime time) {
		return time.format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	private static LocalTime getLocalTime(LocalTime time) {
		if (time.equals(LOCAL_TIME_MIN)) {
			return LocalTime.MAX;
		}
		return time;
	}

}
