package de.lueecke.trainalert.watcher.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RestConfiguration.class)
public class TrainWatcherConfigurationService {

	public static void main(String[] args) {
		SpringApplication.run(TrainWatcherConfigurationService.class, args);
	}

}
