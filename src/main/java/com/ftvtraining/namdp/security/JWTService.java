package com.ftvtraining.namdp.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftvtraining.namdp.models.MockUser;
import com.ftvtraining.namdp.repositories.MockUserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JWTService {

	@Value("${jwt.access-secret}")
	private String accessSecret;

	@Value("${jwt.access-duration}")
	private Long accessExpirationMs;

	@Value("${jwt.refresh-secret}")
	private String refreshSecret;

	@Value("${jwt.refresh-duration}")
	private Long refreshExpirationMs;

	private final Logger logger = LoggerFactory.getLogger(JWTService.class);

	@Autowired
	MockUserRepo userRepo;

	public Map<String, Object> generateToken(UserDetailsImpl userDetails, String type) {
		Date now = new Date();

		Date expiry = type.equals("access") ? new Date(now.getTime() + this.accessExpirationMs)
				: new Date(now.getTime() + this.refreshExpirationMs);
		String token = Jwts.builder().setSubject((userDetails.getUsername())).setIssuedAt(now).setExpiration(expiry)
				.signWith(SignatureAlgorithm.HS512, type.equals("access") ? this.accessSecret : this.refreshSecret)
				.compact();
		Map<String, Object> res = new HashMap<>();
		res.put("token", token);
		res.put("exp", expiry.getTime());
		if(type.equals("refresh")) {
			this.userRepo.findUserByUsername(userDetails.getUsername()).setRefreshToken(token);
		}
		return res;
	}

	public void removeRefreshTokenFromUser(String refreshToken) {
		this.userRepo.findUserByUsername(this.getUsernameFromRefreshToken(refreshToken)).setRefreshToken("");;
	}

	private Claims getClaimsFromAccessToken(String token) {
		return Jwts.parser().setSigningKey(this.accessSecret).parseClaimsJws(token).getBody();
	}

	private boolean isAccessTokenNotExpired(Claims claims) {
		return claims.getExpiration().after(new Date());
	}

	public String getUsernameFromAccessToken(String token) {
		Claims claims = getClaimsFromAccessToken(token);
		if (claims != null && isAccessTokenNotExpired(claims)) {
			return claims.getSubject();
		}
		return null;
	}

	public String getUsernameFromRefreshToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(token).getBody();
		if(claims != null && claims.getExpiration().after(new Date())) {
			return claims.getSubject();
		}
		return null;
	}

	public UserDetailsImpl buildUserImplFromRefreshToken(String refreshToken) {
		String username = this.getUsernameFromRefreshToken(refreshToken);
		MockUser user = this.userRepo.findUserByUsername(username);
		if(!user.getRefreshToken().equals(refreshToken)) {
			return null;
		}
		return new UserDetailsImpl(user.getUsername(), user.getPassword(), user.getRoles());
	}

	public boolean validateToken(String authToken, String type) {
		try {
			if (type.equals("access")) {
				Jwts.parser().setSigningKey(this.accessSecret).parseClaimsJws(authToken);
			} else {
				Jwts.parser().setSigningKey(this.refreshSecret).parseClaimsJws(authToken);
			}
			return true;
		} catch (SignatureException ex) {
			this.logger.error("Invalid JWT signature: {}", ex.getMessage());
		} catch (MalformedJwtException ex) {
			this.logger.error("Invalid token: {}", ex.getMessage());
		} catch (ExpiredJwtException ex) {
			this.logger.error("Token expired: {}", ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			this.logger.error("Unsupported token format: {}", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			this.logger.error("JWT token is empty: {}", ex.getMessage());
		}
		return false;
	}
}
