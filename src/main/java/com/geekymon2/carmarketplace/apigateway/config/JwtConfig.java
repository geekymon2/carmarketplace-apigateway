package com.geekymon2.carmarketplace.apigateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
public class JwtConfig {
    private String secret;
    private long validity;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setValidity(long validity) {
        this.validity = validity;
    }

    public String getSecret() {
        return secret;
    }

    public long getValidity() {
        return validity;
    }
}