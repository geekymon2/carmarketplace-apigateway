package com.geekymon2.carmarketplace.apigateway.controller;

import com.geekymon2.carmarketplace.apigateway.config.JwtConfig;
import com.geekymon2.carmarketplace.apigateway.models.JwtRequest;
import com.geekymon2.carmarketplace.apigateway.models.JwtResponse;
import com.geekymon2.carmarketplace.apigateway.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

	@Autowired
	private final JwtTokenUtil jwtTokenUtil;
	@Autowired
	private final JwtConfig config;

	public JwtAuthenticationController(JwtConfig config, JwtTokenUtil jwtTokenUtil) {
		this.config = config;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		if (!username.equals("foo") && !password.equals("foo")) {
			throw new Exception("AUTHENTICATION_FAILED");
		}
	}
}
