package com.example.thepirates.common.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
		info = @Info(title = "ThePirates 과제 API 명세서",
				description = "ThePirates 과제 API 명세서",
				version = "v1",
				contact = @Contact(name = "sujl95", email = "sujl95@naver.com"),
				license = @License(name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0.html")
		)
)
@Configuration
public class OpenApiConfig {

}
