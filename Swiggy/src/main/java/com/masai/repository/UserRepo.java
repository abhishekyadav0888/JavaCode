package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>, PagingAndSortingRepository<Users, Integer> {

	public Optional<Users> findByUsername(String username);
	
}
