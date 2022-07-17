package com.geekymon2.carmarketplace.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@Slf4j
public class AppConfig {
    private static final String API_URI = "/v3/api-docs";

    private final RouteDefinitionLocator locator;

    public AppConfig(RouteDefinitionLocator locator) {
        this.locator = locator;
    }

    @Bean
    public List<GroupedOpenApi> apis(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();

        locator.getRouteDefinitions().subscribe(routeDefinition -> {
            log.info("Discovered route definition: {}", routeDefinition.getId());
            String resourceName = routeDefinition.getId();
            String location = routeDefinition.getPredicates().get(0).getArgs().get("_genkey_0").replace("/**", API_URI);
            log.info("Adding swagger resource: {} with location {}", resourceName, location);

            GroupedOpenApi api = GroupedOpenApi.builder()
                    .group(resourceName)
                    .pathsToMatch("/" + location + "/**")
                    .build();

            urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl(resourceName, location, resourceName));
            groups.add(api);
        });

        swaggerUiConfigProperties.setUrls(urls);
        return groups;
    }
}
