package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.masai.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>, PagingAndSortingRepository<Customer, Integer> {
	
	public Optional<Customer> findByName(String name);

}
