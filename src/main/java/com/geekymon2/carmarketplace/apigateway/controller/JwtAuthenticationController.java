package com.geekymon2.carmarketplace.apigateway.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.geekymon2.carmarketplace.apigateway.models.ErrorResponseDto;
import com.geekymon2.carmarketplace.apigateway.models.JwtRequest;
import com.geekymon2.carmarketplace.apigateway.models.JwtResponse;
import com.geekymon2.carmarketplace.apigateway.serviceimpl.AuthenticationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.geekymon2.carmarketplace.apigateway.constants.Constants.API_GATEWAY_PREDICATE;

@RestController
public class JwtAuthenticationController {

	private final AuthenticationServiceImpl service;


	public JwtAuthenticationController(AuthenticationServiceImpl service) {
		this.service = service;
	}

	/**
	 * * *** NOTE: ***
	 * * Api Gateway should match predicate
	 * * path to be discoverable in swagger
	 */
	@RequestMapping(value = API_GATEWAY_PREDICATE + "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtRequest) {
		JwtResponse response = service.authenticate(jwtRequest);

		if (!response.getIsAuthenticated()) {
			List<String> details = new ArrayList<>();
			details.add(response.getMessage());
			ErrorResponseDto error = new ErrorResponseDto(new Date(), HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", details, "uri");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		} else {
			return ResponseEntity.ok(response);
		}
	}
}
