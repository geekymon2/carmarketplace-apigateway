package com.geekymon2.carmarketplace.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class AppConfig {
    private static final String API_URI = "/v3/api-docs";

    private final RouteDefinitionLocator locator;

    public AppConfig(RouteDefinitionLocator locator) {
        this.locator = locator;
    }

    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();


        locator.getRouteDefinitions().subscribe(routeDefinition -> {
            log.info("Discovered route definition: {}", routeDefinition.getId());
            String resourceName = routeDefinition.getId();
            String location = routeDefinition.getPredicates().get(0).getArgs().get("_genkey_0").replace("/**", API_URI);
            log.info("Adding swagger resource: {} with location {}", resourceName, location);

            GroupedOpenApi api = GroupedOpenApi.builder()
                    .group(resourceName)
                    .pathsToMatch("/" + location + "/**")
                    .build();

            groups.add(api);
        });

        return groups;
    }
}
