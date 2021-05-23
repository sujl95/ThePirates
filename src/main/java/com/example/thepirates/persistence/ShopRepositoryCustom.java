package com.example.thepirates.persistence;

import java.util.List;

import com.example.thepirates.entity.Shop;

public interface ShopRepositoryCustom {

	List<Shop> findAllList();
	Shop findByIdDetail(Long id);
}
