package com.jsp.workSpace.clone;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WorkSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkSpaceApplication.class, args);
	}
	@Configuration
	public class WebConfig implements WebMvcConfigurer{
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","POST","PUT","DELTE");
			
			
		}
	}
	@Bean
	public ModelMapper model(){
		return new ModelMapper(); 
	}

}
  