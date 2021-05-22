package com.example.thepirates.common.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.util.ObjectUtils;

import com.example.thepirates.controller.common.DayOfWeek;

@Converter(autoApply = true)
public class DayOfWeekConverter implements AttributeConverter<DayOfWeek, String> {

	@Override
	public String convertToDatabaseColumn(DayOfWeek dayOfWeek) {
		if (ObjectUtils.isEmpty(dayOfWeek)) {
			return null;
		}
		return dayOfWeek.getDay();
	}

	@Override
	public DayOfWeek convertToEntityAttribute(String code) {
		if (ObjectUtils.isEmpty(code)) {
			return null;
		}
		return Stream.of(DayOfWeek.values())
				.filter(dayOfWeek -> dayOfWeek.getDay().equals(code))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

}
