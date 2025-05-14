package com.trendist.activity_service.global.config.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;

@Configuration
public class FeignAuthConfig {
	@Bean
	public RequestInterceptor authInterceptor() {
		return requestTemplate -> {
			ServletRequestAttributes attrs =
				(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			if (attrs != null) {
				String auth = attrs.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
				if (auth != null) {
					requestTemplate.header(HttpHeaders.AUTHORIZATION, auth);
				}
			}
		};
	}
}
