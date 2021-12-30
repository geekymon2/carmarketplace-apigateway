package com.geekymon2.carmarketplace.apigateway.swagger;

import java.util.ArrayList;
import java.util.List;

import com.geekymon2.carmarketplace.apigateway.config.AppConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {
    
    public static final String API_URI = "/v2/api-docs";
    public static final String EUREKA_SUB_PRIX = "ReactiveCompositeDiscoveryClient_";
    
    private final RouteDefinitionLocator routeLocator;
    private final AppConfig appConfig;
    
    public SwaggerProvider(RouteDefinitionLocator routeLocator, AppConfig appConfig) {
        this.routeLocator = routeLocator;
        this.appConfig = appConfig;
    }
    
    @Override
    
    public List<SwaggerResource> get() {
        final String GATEWAY_STRING = "/" + appConfig.getAppName();
        List<SwaggerResource> resources = new ArrayList<>();
        routeLocator.getRouteDefinitions().subscribe(routeDefinition -> {
            if (routeDefinition.getId().contains(EUREKA_SUB_PRIX)) {
                String resourceName = routeDefinition.getId().substring(EUREKA_SUB_PRIX.length());
                String location = routeDefinition.getPredicates().get(0).getArgs().get("pattern").replace("/**", API_URI);
                if (location.contains(GATEWAY_STRING)) {
                    location = location.replace(GATEWAY_STRING, StringUtils.EMPTY);
                }
                resources.add(swaggerResource(resourceName, location));
            }});
            
            return resources;
        }
        
        private SwaggerResource swaggerResource(String name, String location) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion("2.0");
            return swaggerResource;    
        }
    }
    