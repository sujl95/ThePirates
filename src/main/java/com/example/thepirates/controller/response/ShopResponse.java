package com.example.thepirates.controller.response;

import java.util.List;
import java.util.Objects;

import com.example.thepirates.controller.common.BusinessStatus;
import com.example.thepirates.entity.Shop;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ShopResponse {



	@Getter
	@AllArgsConstructor
	public static class ShopFind {

		private final String name;
		private final String description;
		private final Integer level;
		private final BusinessStatus status;

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ShopFind shopFind = (ShopFind)o;
			return Objects.equals(getName(), shopFind.getName()) && Objects.equals(getDescription(),
					shopFind.getDescription()) && Objects.equals(getLevel(), shopFind.getLevel())
					&& getStatus() == shopFind.getStatus();
		}

	}

	@Getter
	public static class ShopDetail {
		private final Long id;
		private final String name;
		private final String description;
		private final Integer level;
		private final String address;
		private final String phone;
		private final List<BusinessDay> businessDays;

		public ShopDetail(Shop shop, List<BusinessDay> businessDays) {
			this.id = shop.getId();
			this.name = shop.getName();
			this.description = shop.getDescription();
			this.level = shop.getLevel();
			this.address = shop.getAddress();
			this.phone = shop.getPhone();
			this.businessDays = businessDays;
		}

		@Getter
		public static class BusinessDay {

			private final String day;
			private final String open;
			private final String close;
			private final BusinessStatus status;

			public BusinessDay(String day, String open, String close, BusinessStatus status) {
				this.day = day;
				this.open = open;
				this.close = getClose(close);
				this.status = status;
			}

			private String getClose(String close) {
				String localTimeMin = "00:00";
				String localTimeMax = "24:00";
				if (close.equals(localTimeMin)) {
					return localTimeMax;
				}
				return close;
			}

		}
	}
}
