package com.payroll.gateway_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServerApplication {


	// TODO :  add the routes for other microservices
	@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder){
		return  builder.routes()
				.route(p -> p
						.path("/payroll/leaves/**")
						.filters(f -> f.rewritePath("/payroll/leaves/(?<segment>.*)","/${segment}"))
						.uri("lb://LEAVES")
				)
				.route(p -> p.
						path("/payroll/employee/**")
						.filters(f -> f.rewritePath("/payroll/employee/(?<segment>.*)","/${segment}"))
						.uri("lb://EMPLOYEE")
				)
				.route(p -> p.
						path("/payroll/salary/**")
						.filters(f -> f.rewritePath("/payroll/salary/(?<segment>.*)","/${segment}"))
						.uri("lb://SALARY")
				)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

}
