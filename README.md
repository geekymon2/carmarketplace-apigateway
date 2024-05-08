# Car Marketplace

Microservices App for Car Marketplace

## Status

[![build](https://github.com/geekymon2/carmarketplace-apigateway/actions/workflows/build.yml/badge.svg)](https://github.com/geekymon2/carmarketplace-apigateway/actions/workflows/build.yml) &nbsp;&nbsp; [![codecov](https://codecov.io/gh/geekymon2/carmarketplace-apigateway/branch/main/graph/badge.svg?token=XTL0XCZ4JI)](https://codecov.io/gh/geekymon2/carmarketplace-apigateway) &nbsp;&nbsp; ![Docker Image Version (latest by date)](https://img.shields.io/docker/v/geekymon2/cm-apigateway) &nbsp;&nbsp; ![Docker Image Size (latest by date)](https://img.shields.io/docker/image-size/geekymon2/cm-apigateway)

## About this Service

**Car Marketplace Api Gateway**

* This service provides an entry point for all API interfaces for the UI client.
* Request Validation through JWT token authentication
* Swagger API documentation Aggregation for various services

## Local Environment Setup
* To run locally set the SPRING profile to "local".
    * You can do this by setting environment variable SPRING_PROFILES_ACTIVE=local
    * you can also set profile within the Intellij IDE
* Swagger URL: http://localhost:8008/api/swagger-ui/index.html
* Configuration is loaded from config-server

## CI environment Setup
* CI is configured to use profile "ci"
* Swagger URL: TBD

## Documentation

For more details refer to the swagger documentation.