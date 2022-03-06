package com.geekymon2.carmarketplace.apigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;


@RefreshScope
@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	public AuthenticationFilter(RouterValidator routerValidator, JwtTokenUtil jwtTokenUtil) {
		super(Config.class);
		this.routerValidator = routerValidator;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Autowired
	private final RouterValidator routerValidator;
	private final JwtTokenUtil jwtTokenUtil;

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if (routerValidator.isSecured.test(exchange.getRequest())) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Missing Authorisation Header");
				}

				String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
				try {
					jwtTokenUtil.validateToken(authHeader);
				}
				catch (Exception ex) {
					log.error("Error Validating Authentication Header", ex);
					ServerHttpResponse response = exchange.getResponse();
					response.setStatusCode(HttpStatus.UNAUTHORIZED);
					return response.setComplete();
				}
			}

			return chain.filter(exchange);
		});
	}

	public static class Config {
	}
}