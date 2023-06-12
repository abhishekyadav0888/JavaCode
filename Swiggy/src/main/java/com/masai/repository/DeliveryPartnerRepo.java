package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.masai.model.DeliveryPartner;

public interface DeliveryPartnerRepo extends JpaRepository<DeliveryPartner, Integer>, PagingAndSortingRepository<DeliveryPartner, Integer> {

	public Optional<DeliveryPartner> findByPhoneNumber(String phoneNumber);
	
}
