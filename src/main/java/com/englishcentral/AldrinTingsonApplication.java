package com.englishcentral;

import com.englishcentral.video.Video;
import com.englishcentral.video.VideoRepository;
import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@SpringBootApplication
@ComponentScan
@Configuration
@EnableAutoConfiguration
public class AldrinTingsonApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AldrinTingsonApplication.class, args);
		initializeSampleData(context.getBean(VideoRepository.class));
	}

	@Bean
	public Fongo fongo(){
		return new Fongo("InMemoryMongo");
	}

	@Bean
	public MongoClient mongo(Fongo fongo){
		return fongo.getMongo();
	}

	@Bean
	public Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	public static void initializeSampleData(VideoRepository videoRepository){
		Video video = new Video();
		video.setName("Sample Data 1");
		video.setDescription("Sample Data description 1");
		video.setLengthInSecs(180L);
		video.setUploadedBy("SampleUploader1");
		videoRepository.save(video);

		Video video2 = new Video();
		video2.setName("Sample Data 2");
		video2.setDescription("Sample Data description 2");
		video2.setLengthInSecs(240L);
		video2.setUploadedBy("SampleUploader2");
		videoRepository.save(video2);

		Video video3 = new Video();
		video3.setName("Sample Data 2");
		video3.setDescription("Sample Data description 2");
		video3.setLengthInSecs(210L);
		video3.setUploadedBy("SampleUploader3");
		videoRepository.save(video3);
	}
}
