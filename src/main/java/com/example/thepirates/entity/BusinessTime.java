package com.example.thepirates.entity;

import static javax.persistence.FetchType.*;

import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.thepirates.common.converter.DayOfWeekConverter;
import com.example.thepirates.controller.common.DayOfWeek;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "business_time")
public class BusinessTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BUSINESS_TIME_ID")
	private Long id;

	@Column(nullable = false)
	@Convert(converter = DayOfWeekConverter.class)
	private DayOfWeek day;

	@Column(nullable = false)
	private LocalTime open;

	@Column(nullable = false)
	private LocalTime close;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "SHOP_ID", updatable = false)
	private Shop shop;

	@Builder
	public BusinessTime(DayOfWeek day, LocalTime open, LocalTime close) {
		this.day = day;
		this.open = open;
		this.close = close;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BusinessTime that = (BusinessTime)o;
		return Objects.equals(getId(), that.getId()) && getDay() == that.getDay() && Objects.equals(
				getOpen(), that.getOpen()) && Objects.equals(getClose(), that.getClose())
				&& Objects.equals(getShop(), that.getShop());
	}

	public void changeShop(Shop shop) {
		this.shop = shop;
	}
}
