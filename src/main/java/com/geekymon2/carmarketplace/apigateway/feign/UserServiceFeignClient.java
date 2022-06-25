package com.geekymon2.carmarketplace.apigateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user-service")
public interface UserServiceFeignClient {
    @GetMapping("/user-service/validate")
    ResponseEntity<Boolean> validateUserPassword();
}
