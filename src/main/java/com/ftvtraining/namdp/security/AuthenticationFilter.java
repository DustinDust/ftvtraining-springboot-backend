package com.ftvtraining.namdp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String token = getTokenFromRequest(request);
			if (token != null && jwtService.validateToken(token, "access")) {
				String username = jwtService.getUsernameFromAccessToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				// create a new UsernamePasswordAuthenticationToken, which is used to populate the SecurityContext.
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				System.out.println(authenticationToken);
				
				// the context is now populated with Authentication object. We can access that Object any time we want.
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception ex) {
			logger.error("Failed to set up authentication {}", ex.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7, token.length());
		}

		return null;
	}
}
