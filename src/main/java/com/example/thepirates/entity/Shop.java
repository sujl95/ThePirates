package com.example.thepirates.entity;

import static javax.persistence.CascadeType.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.thepirates.controller.request.ShopRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SHOP_ID")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String owner;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Integer level;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String phone;

	@OneToMany(mappedBy = "shop", cascade = ALL)
	private Set<BusinessTime> businessTimes = new HashSet<>();

	@OneToMany(mappedBy = "shop", cascade = ALL)
	private Set<Holiday> holidays = new HashSet<>();


	@Builder
	public Shop(String name, String owner, String description, Integer level, String address, String phone,
			List<ShopRequest.ShopCreate.BusinessTime> businessTimes) {
		this.name			 = name;
		this.owner			 = owner;
		this.description	 = description;
		this.level			 = level;
		this.address		 = address;
		this.phone			 = phone;
		businessTimes.forEach(this::addBusinessTime);
	}

	public void addBusinessTime(ShopRequest.ShopCreate.BusinessTime createBusinessTime) {
		BusinessTime businessTime = BusinessTime.builder()
				.day(createBusinessTime.getDay())
				.open(createBusinessTime.getOpen())
				.close(createBusinessTime.getClose())
				.build();
		businessTimes.add(businessTime);
		businessTime.changeShop(this);
	}

	public void addHoliday(LocalDate day) {
		Holiday holiday = Holiday.builder()
				.day(day)
				.shop(this)
				.build();
		holidays.add(holiday);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Shop shop = (Shop)o;
		return Objects.equals(getId(), shop.getId()) && Objects.equals(getName(), shop.getName())
				&& Objects.equals(getOwner(), shop.getOwner()) && Objects.equals(getDescription(),
				shop.getDescription()) && Objects.equals(getLevel(), shop.getLevel()) && Objects.equals(
				getAddress(), shop.getAddress()) && Objects.equals(getPhone(), shop.getPhone())
				&& Objects.equals(getBusinessTimes(), shop.getBusinessTimes()) && Objects.equals(
				getHolidays(), shop.getHolidays());
	}

}
