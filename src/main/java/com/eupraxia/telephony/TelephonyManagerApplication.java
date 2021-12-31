package com.eupraxia.telephony;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eupraxia.telephony.Model.Role;
import com.eupraxia.telephony.repositories.RoleRepository;


@SpringBootApplication
@EnableCaching
public class TelephonyManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelephonyManagerApplication.class, args);		
		
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**").allowCredentials(true).allowedOrigins("*").allowedMethods("*");
	            }
	        };
	    }

	
}
