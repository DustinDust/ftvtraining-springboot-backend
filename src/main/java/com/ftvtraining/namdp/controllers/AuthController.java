package com.ftvtraining.namdp.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftvtraining.namdp.dto.LoginRequest;
import com.ftvtraining.namdp.dto.LoginResponse;
import com.ftvtraining.namdp.dto.LogoutRequest;
import com.ftvtraining.namdp.dto.RefreshRequest;
import com.ftvtraining.namdp.dto.RefreshResponse;
import com.ftvtraining.namdp.dto.ResponseDTO;
import com.ftvtraining.namdp.exceptions.RefreshTokenException;
import com.ftvtraining.namdp.security.JWTService;
import com.ftvtraining.namdp.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JWTService jwtService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Map<String, Object> access = jwtService.generateToken((UserDetailsImpl) authentication.getPrincipal(),
				"access");
		Map<String, Object> refresh = jwtService.generateToken((UserDetailsImpl) authentication.getPrincipal(),
				"refresh");
		return ResponseEntity.ok().body(new LoginResponse((String) access.get("token"), (String) refresh.get("token"),
				(Long) access.get("exp")));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
		// TODO: remove refreshToken from an user
		this.jwtService.removeRefreshTokenFromUser(logoutRequest.getToken());
		return ResponseEntity.ok().body(new ResponseDTO("Successfully logged out", true, null));
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
		if (refreshRequest.getRefreshToken() != null
				&& this.jwtService.validateToken(refreshRequest.getRefreshToken(), "refresh")) {
			UserDetailsImpl user = this.jwtService.buildUserImplFromRefreshToken(refreshRequest.getRefreshToken());
			if (user == null) {
				throw new RefreshTokenException("Fail to authorize this token", "USER_NOT_FOUND");
			}
			Map<String, Object> newAccess = this.jwtService.generateToken(user, "access");
			return ResponseEntity.ok().body(new RefreshResponse((String) newAccess.get("token"),
					refreshRequest.getRefreshToken(), (Long) newAccess.get("exp")));
		} else {
			throw new RefreshTokenException("Refresh Token invalid", "INVALID_TOKEN");
		}
	}
}
