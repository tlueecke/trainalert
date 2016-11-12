package de.lueecke.trainalert.watcher.config;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigQueryController {

	@Autowired
	private TerminalWatchJobRepository repository;

	@RequestMapping(path = "routesToWatch")
	public List<Route> findRoutesToWatch(
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd_HH:mm") Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		int hour = c.get(Calendar.HOUR_OF_DAY);
		List<TerminalWatchJob> jobs = repository.findByFromHourLessThanEqualAndToHourGreaterThan(hour, hour);
		return jobs.stream().map(j -> new Route(j.getTerminalId(), null)).collect(Collectors.toList());
	}

}
