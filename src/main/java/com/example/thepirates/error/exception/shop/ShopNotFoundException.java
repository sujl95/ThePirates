package com.example.thepirates.error.exception.shop;

import com.example.thepirates.error.exception.common.ExceptionStatus;
import com.example.thepirates.error.exception.common.ThePiratesException;

public class ShopNotFoundException extends ThePiratesException {

	public ShopNotFoundException() {
		super(ExceptionStatus.SHOP_NOT_FOUND);
	}
}
