package com.geekymon2.carmarketplace.apigateway.serviceimpl;

import com.geekymon2.carmarketplace.apigateway.feign.UserServiceFeignClient;
import com.geekymon2.carmarketplace.apigateway.models.AuthenticationStatus;
import com.geekymon2.carmarketplace.apigateway.models.JwtRequest;
import com.geekymon2.carmarketplace.apigateway.models.JwtResponse;
import com.geekymon2.carmarketplace.apigateway.security.JwtTokenUtil;
import com.geekymon2.carmarketplace.apigateway.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenUtil tokenUtil;
    private final UserServiceFeignClient userService;


    public AuthenticationServiceImpl(JwtTokenUtil tokenUtil, UserServiceFeignClient userService) {
        this.tokenUtil = tokenUtil;
        this.userService = userService;
    }

    @Override
    public JwtResponse authenticate(JwtRequest request) {
        AuthenticationStatus status = validateUserLogin(request.getPassword(), request.getPassword());
        String token = null;

        if (status.getIsAuthenticated()) {
            token = tokenUtil.generateToken(request.getUsername());
        }

        return new JwtResponse(token, status.getIsAuthenticated(),status.getMessage());
    }


    private AuthenticationStatus validateUserLogin(String username, String password) {
        AuthenticationStatus status;

        userService.validateUserPassword();

        if (!username.equals("foo") && !password.equals("foo")) {
            status = new AuthenticationStatus(false, "Invalid Username/Password");
        }
        else {
            status = new AuthenticationStatus(true, "Authentication Successful");
        }

        return status;
    }
}
