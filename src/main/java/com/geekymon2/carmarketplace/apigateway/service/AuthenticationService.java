package com.geekymon2.carmarketplace.apigateway.service;

import com.geekymon2.carmarketplace.apigateway.models.JwtRequest;
import com.geekymon2.carmarketplace.apigateway.models.JwtResponse;

public interface AuthenticationService {
    JwtResponse authenticate(JwtRequest request);
}
