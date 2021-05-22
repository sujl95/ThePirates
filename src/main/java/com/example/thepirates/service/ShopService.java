package com.example.thepirates.service;

import static com.example.thepirates.common.util.DateUtils.*;
import static com.example.thepirates.controller.common.BusinessStatus.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.thepirates.common.util.DateUtils;
import com.example.thepirates.controller.request.ShopRequest;
import com.example.thepirates.controller.response.ShopResponse;
import com.example.thepirates.controller.common.BusinessStatus;
import com.example.thepirates.entity.BusinessTime;
import com.example.thepirates.entity.Holiday;
import com.example.thepirates.entity.Shop;
import com.example.thepirates.error.exception.shop.OpenTimeEqualsCloseTimeException;
import com.example.thepirates.error.exception.shop.ShopNotFoundException;
import com.example.thepirates.persistence.ShopRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ShopService {

	private final ShopRepository shopRepository;

	private void registerAble(ShopRequest.ShopCreate shopCreate) {
		shopCreate.getBusinessTimes().forEach(businessTime -> {
			if (businessTime.isOpenTimeEqualsCloseTime()) {
				throw new OpenTimeEqualsCloseTimeException();
			}
		});
	}

	@Transactional
	public void registerShop(ShopRequest.ShopCreate shopCreate) {
		registerAble(shopCreate);
		Shop shop = shopCreate.toEntity();
		shopRepository.saveAndFlush(shop);
		log.info("점포 생성 id = {}", shop.getId());
	}

	@Transactional
	public void registerHoliday(ShopRequest.HolidayCreate holidayCreate) {
		Shop shop = shopRepository.findById(holidayCreate.getId()).orElseThrow(ShopNotFoundException::new);
		holidayCreate.getHolidays().forEach(shop::addHoliday);
		log.info("점포 휴무일 생성 점포 id = {}", shop.getId());
	}

	@Transactional(readOnly = true)
	public List<ShopResponse.ShopFind> getShops() {
		List<Shop> shops = shopRepository.findAllByOrderByLevelAsc();
		List<ShopResponse.ShopFind> shopFinds = new ArrayList<>();
		for (Shop shop : shops) {
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			Set<Holiday> holidays = shop.getHolidays();
			BusinessStatus status;
			if (DateUtils.isHoliday(holidays, date)) {
				status = HOLIDAY;
			} else {
				status = DateUtils.getBusinessStatus(shop.getBusinessTimes(), date, time);
			}
			shopFinds.add(new ShopResponse.ShopFind(shop.getName(), shop.getDescription(), shop.getLevel(), status));
		}
		return shopFinds;
	}

	@Transactional(readOnly = true)
	public ShopResponse.ShopDetail getShop(Long shopId) {
		Shop shop = shopRepository.findById(shopId).orElseThrow(ShopNotFoundException::new);
		Set<Holiday> holidays = shop.getHolidays();
		Set<BusinessTime> businessTimes = shop.getBusinessTimes();
		LocalDate date = LocalDate.now();
		return new ShopResponse.ShopDetail(shop, getBusinessDay(holidays, businessTimes, date));
	}

	private List<ShopResponse.ShopDetail.BusinessDay> getBusinessDay(Set<Holiday> holidays,
			Set<BusinessTime> businessTimes, LocalDate date) {
		List<ShopResponse.ShopDetail.BusinessDay> businessDays = new ArrayList<>();
		IntStream.range(0, 3)
				.mapToObj(date::plusDays)
				.forEach(localDate -> addBusinessDay(holidays, businessTimes, localDate, businessDays));
		return businessDays;
	}

	private void addBusinessDay(Set<Holiday> holidays, Set<BusinessTime> businessTimes, LocalDate date,
			List<ShopResponse.ShopDetail.BusinessDay> businessDays) {
		for (BusinessTime businessTime : businessTimes) {
			String day = businessTime.getDay().getDay();
			LocalTime openTime = businessTime.getOpen();
			LocalTime closeTime = businessTime.getClose();
			LocalDate localdate = LocalDate.now();
			String open = localTimeFormat(openTime);
			String close = localTimeFormat(closeTime);
			BusinessStatus status;
			if (isDayEqualsWeek(date, businessTime)) {
				LocalTime time = LocalTime.now();
				if (isHoliday(holidays, date)) {
					status = HOLIDAY;
				} else if (localdate.equals(date) && isShopStatusOpen(time, closeTime, openTime)) {
					status = OPEN;
				} else {
					status = CLOSE;
				}
				businessDays.add(new ShopResponse.ShopDetail.BusinessDay(day, open, close, status));
				return;
			}
		}
	}

	@Transactional
	public void deleteShop(Long shopId) {
		shopRepository.deleteById(shopId);
		log.info("점포 삭제 id = {}", shopId);
	}
}
