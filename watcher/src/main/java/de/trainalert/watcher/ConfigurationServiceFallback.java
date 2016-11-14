package de.trainalert.watcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ConfigurationServiceFallback implements ConfigurationService {

	@Override
	public List<Route> findRoutesToWatch() {
		return new ArrayList<Route>();
	}

}
