package de.lueecke.trainalert.watcher.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RestConfiguration.class)
@EnableDiscoveryClient
public class TrainWatcherConfigurationService {

	public static void main(String[] args) {
		SpringApplication.run(TrainWatcherConfigurationService.class, args);
	}

}
