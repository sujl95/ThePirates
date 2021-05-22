package com.example.thepirates.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.thepirates.controller.request.ShopRequest;
import com.example.thepirates.controller.response.ShopResponse;
import com.example.thepirates.service.ShopService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
public class ShopController {

	private final ShopService shopService;

	@PostMapping
	@Operation(summary = "점포 추가 API")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerShop(@RequestBody @Valid ShopRequest.ShopCreate shopCreate) {
		shopService.registerShop(shopCreate);
	}

	@PostMapping("/holiday")
	@Operation(summary = "점포 휴무일 추가 API")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerHoliday(@RequestBody @Valid ShopRequest.HolidayCreate holidayCreate) {
		shopService.registerHoliday(holidayCreate);
	}

	@GetMapping
	@Operation(summary = "점포 리스트 조회 API")
	public List<ShopResponse.ShopFind> getShops() {
		return shopService.getShops();
	}

	@GetMapping("/{shopId}")
	@Operation(summary = "점포 상세 조회 API")
	public ShopResponse.ShopDetail getShop(@PathVariable Long shopId) {
		return shopService.getShop(shopId);
	}

	@DeleteMapping("/{shopId}")
	@Operation(summary = "점포 삭제 API")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteShop (@PathVariable Long shopId) {
		shopService.deleteShop(shopId);
	}
}
