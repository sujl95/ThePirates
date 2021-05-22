package com.example.thepirates.controller.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.thepirates.common.converter.DayOfWeekConverter;
import com.example.thepirates.controller.common.DayOfWeek;
import com.example.thepirates.entity.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ShopRequest {

	@Getter
	@Setter
	public static class ShopCreate {

		@NotBlank
		private String name;

		@NotBlank
		private String owner;

		@NotBlank
		private String description;

		@NotNull
		private Integer level;

		@NotBlank
		private String address;

		@NotBlank
		@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "전화번호 형식이 일치하지 않습니다.")
		private String phone;

		@NotEmpty
		List<BusinessTime> businessTimes;

		@Builder
		public ShopCreate(String name, String owner, String description, Integer level, String address, String phone,
				List<BusinessTime> businessTimes) {
			this.name = name;
			this.owner = owner;
			this.description = description;
			this.level = level;
			this.address = address;
			this.phone = phone;
			this.businessTimes = businessTimes;
		}

		public Shop toEntity() {
			return Shop.builder()
					.name(name)
					.owner(owner)
					.description(description)
					.level(level)
					.address(address)
					.phone(phone)
					.businessTimes(businessTimes)
					.build();
		}



		@Getter
		@Setter
		public static class BusinessTime {

			@Convert(converter = DayOfWeekConverter.class)
			@JsonProperty("day")
			private DayOfWeek day;

			@NotNull
			@JsonFormat(pattern = "kk:mm")
			private LocalTime open;

			@NotNull
			@JsonFormat(pattern = "kk:mm")
			private LocalTime close;

			public boolean isOpenTimeEqualsCloseTime() {
				return open.equals(close);
			}

		}

	}

	@Getter
	@Setter
	public static class HolidayCreate {
		@NotNull
		private Long id;

		@NotEmpty
		private List<LocalDate> holidays;
	}

}