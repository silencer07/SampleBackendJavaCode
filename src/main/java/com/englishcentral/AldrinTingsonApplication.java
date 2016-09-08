package com.englishcentral;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan
@Configuration
@EnableAutoConfiguration
public class AldrinTingsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(AldrinTingsonApplication.class, args);
	}

	@Bean
	public Fongo fongo(){
		return new Fongo("InMemoryMongo");
	}

	@Bean
	public MongoClient mongo(Fongo fongo){
		return fongo.getMongo();
	}
}
