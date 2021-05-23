package com.example.thepirates.persistence;

import java.util.List;

import com.example.thepirates.entity.Shop;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.example.thepirates.entity.QBusinessTime.*;
import static com.example.thepirates.entity.QHoliday.*;
import static com.example.thepirates.entity.QShop.shop;

@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom{


	private final JPAQueryFactory queryFactory;

	@Override
	public List<Shop> findAllList() {
		return queryFactory
				.selectFrom(shop)
				.leftJoin(shop.holidays, holiday).fetchJoin()
				.leftJoin(shop.businessTimes, businessTime).fetchJoin()
				.orderBy(shop.level.asc())
				.distinct()
				.fetch();
	}

	@Override
	public Shop findByIdDetail(Long id) {
		return queryFactory
				.selectFrom(shop)
				.leftJoin(shop.holidays, holiday).fetchJoin()
				.leftJoin(shop.businessTimes, businessTime).fetchJoin()
				.where(shop.id.eq(id))
				.fetchOne();
	}
}
