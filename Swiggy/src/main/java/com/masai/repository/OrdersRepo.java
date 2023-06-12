package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.masai.model.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Integer>, PagingAndSortingRepository<Orders, Integer> {

}
