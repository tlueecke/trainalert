package de.trainalert.watcher;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "watcher-config-service", fallback = ConfigurationServiceFallback.class)
public interface ConfigurationService {

	@RequestMapping(method = RequestMethod.GET, value = "/routesToWatch")
	List<Route> findRoutesToWatch();
}