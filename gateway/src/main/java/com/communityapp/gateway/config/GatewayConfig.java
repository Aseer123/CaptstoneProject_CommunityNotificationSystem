package com.communityapp.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("auth-service", r-> r.path("/auth/**")
						.uri("lb://AUTH-SERVICE"))
				.route("notification-service", r->r.path("/notifications/**")
				.uri("lb://NOTIFICATION-SERVICE"))
				.route("feedback-service", r->r.path("/feedback/**")
						.uri("lb://FEEDBACK-SERVICE"))
				.route("maintenance-task-service", r->r.path("/tasks/**")
						.uri("lb://MAINTENANCE_TASK-SERVICE"))
				.route("payment-service", r->r.path("/payments/**")
						.uri("lb://PAYMENT-SERVICE")).build();
				
	}
}
