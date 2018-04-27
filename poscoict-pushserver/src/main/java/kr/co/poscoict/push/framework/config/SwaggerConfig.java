package kr.co.poscoict.push.framework.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("kr.co.poscoict.push"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(Arrays.asList(new ApiKey("jwt", "Authorization", "header")))
				.apiInfo(new ApiInfo(
						"Message-Server API Documentation",
						"Push & Email API Documentation",
						"1.0",
						"urn:tos",
						ApiInfo.DEFAULT_CONTACT,
						"Apache 2.0",
						"http://www.apache.org/licenses/LICENSE-2.0",
						Collections.emptyList()
						));
	}
}
