package de.rwth.swc.oosc.swcarchitect.webservice;

import de.rwth.swc.oosc.swcarchitect.webservice.storage.StorageProperties;
import de.rwth.swc.oosc.swcarchitect.webservice.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SwcArchitectWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwcArchitectWebserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
