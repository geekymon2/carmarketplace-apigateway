package com.geekymon2.carmarketplace.apigateway.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.geekymon2.carmarketplace.apigateway.models.AuthenticationStatus;
import com.geekymon2.carmarketplace.apigateway.models.ErrorResponseDto;
import com.geekymon2.carmarketplace.apigateway.models.JwtRequest;
import com.geekymon2.carmarketplace.apigateway.models.JwtResponse;
import com.geekymon2.carmarketplace.apigateway.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

	@Autowired
	private final JwtTokenUtil jwtTokenUtil;

	public JwtAuthenticationController(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		AuthenticationStatus status = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		if (!status.getIsAuthenticated()) {
			List<String> details = new ArrayList<>();
			details.add(status.getMessage());
			ErrorResponseDto error = new ErrorResponseDto(new Date(), HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", details, "aa");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}

		final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private AuthenticationStatus authenticate(String username, String password) {
		AuthenticationStatus status;

		if (!username.equals("foo") && !password.equals("foo")) {
			status = new AuthenticationStatus(false, "Invalid Username/Password");
		}
		else {
			status = new AuthenticationStatus(true, "Authentication Successful");
		}

		return status;
	}
}
