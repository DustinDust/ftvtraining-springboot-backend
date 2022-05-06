package com.ftvtraining.namdp.repositories;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ftvtraining.namdp.models.MockUser;

@Component
public class MockUserRepo {
	MockUser[] users = { new MockUser(
		"user", 
		"$2a$12$PPagmC0nMcNzn/xgQQE1I.vo2oFxT1emvyrDTzgZDKX2DkVCZzuOe",  
		Arrays.asList("ADMIN")
		) 
	};

	public MockUser findUserByUsername(String username) {
		Optional<MockUser> user = Arrays.stream(users).filter((mockUser) -> {
			return username.equals(mockUser.getUsername());
		}).findFirst();
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}
	
	public MockUser findUserByToken(String token) {
		Optional<MockUser> user = Arrays.stream(users).filter((mockUser) -> {
			return mockUser.getRefreshToken().equals(token);
		}).findFirst();
		return user.get();
	}
}
