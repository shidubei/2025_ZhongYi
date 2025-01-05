package com.example.demo.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.CoinGameInterceptor;

@Configuration
public class WebConfigurer {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			// allow React 
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				        .allowedOrigins("http://18.138.192.230")
				        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				        .allowedHeaders("*")
				        .allowCredentials(true);
			}
			
			// registry the interceptor
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new CoinGameInterceptor())  
                        .addPathPatterns("/**")
                        .excludePathPatterns("/index.html","/","/manifest.json", "/assets/**", "/static/**");
            }

		};
	}
}