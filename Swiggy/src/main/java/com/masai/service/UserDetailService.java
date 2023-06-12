package com.masai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masai.model.Users;
import com.masai.repository.UserRepo;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepo uRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> opt = uRepo.findByUsername(username);
		if(opt.isPresent()) {
			Users user = opt.get();
			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(user.getRole());
			authorities.add(sga);
			
			return new User(user.getUsername(), user.getPassword(), authorities);
		} else 
			throw new BadCredentialsException("User Details not found with this username: " + username);
	}

}
