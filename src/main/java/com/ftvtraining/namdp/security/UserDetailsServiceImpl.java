package com.ftvtraining.namdp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftvtraining.namdp.models.MockUser;
import com.ftvtraining.namdp.repositories.MockUserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	MockUserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MockUser user = userRepo.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		System.out.println(username + " - UserDetailsService reached");
		System.out.println(user.getRoles());
		return new UserDetailsImpl(user.getUsername(), user.getPassword(), user.getRoles());
	}

}
