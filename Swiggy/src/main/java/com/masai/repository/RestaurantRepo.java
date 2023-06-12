package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.masai.model.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer>, PagingAndSortingRepository<Restaurant, Integer> {

	public Optional<Restaurant> findByName(String name);
	
}
