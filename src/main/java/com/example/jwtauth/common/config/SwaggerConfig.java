package com.example.jwtauth.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "조예인",
		description = "바로인턴 과제",
		version = "1.0.0"
	),
	servers = {
		@Server(url = "http://3.35.225.38:8080", description = "개발 서버"),
		@Server(url = "http://localhost:8080", description = "로컬 서버")
	}
)
@RequiredArgsConstructor
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi publicApi() {
		String[] packages = {"com.example.jwtauth"};
		return GroupedOpenApi.builder()
			.group("default")
			.packagesToScan(packages)
			.build();
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("JWT", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.in(SecurityScheme.In.HEADER)
					.name("Authorization")
				)
			)
			.security(Collections.singletonList(new SecurityRequirement().addList("JWT")));
	}
}
