package de.trainalert.watcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WatcherService {

	public static void main(String[] args) {
		SpringApplication.run(WatcherService.class, args);
	}
}
