package com.example.thepirates.entity;

import static javax.persistence.FetchType.*;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HOLIDAY_ID")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "SHOP_ID", updatable = false)
	private Shop shop;

	private LocalDate day;

	@Builder
	public Holiday(Shop shop, LocalDate day) {
		this.shop = shop;
		this.day = day;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Holiday holiday = (Holiday)o;
		return Objects.equals(getId(), holiday.getId()) && Objects.equals(getShop(), holiday.getShop())
				&& Objects.equals(getDay(), holiday.getDay());
	}

}
