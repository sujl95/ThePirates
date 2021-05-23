package com.example.thepirates.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thepirates.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom{
}
