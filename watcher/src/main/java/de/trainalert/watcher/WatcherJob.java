package de.trainalert.watcher;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

@Component
public class WatcherJob {
	private static final Logger LOG = Logger.getLogger(WatcherJob.class.getName());

	@Value("${vcap.services.mail.credentials.username}")
	private String username;

	@Value("${vcap.services.mail.credentials.password}")
	private String password;

	@Autowired
	private TrainAlertFetcher fetcher;

	@Autowired
	private TrainAlertRepository repository;

	@Autowired
	private ConfigurationClient cfgClient;

	@Scheduled(fixedRate = 1000 * 60 * 10)
	public void watchTrains() throws SendGridException {
		List<Route> routes = cfgClient.findRoutesToWatch();
		Set<String> destinations = routes.stream().filter(r -> r.getEndTerminal() != null).map(r -> r.getEndTerminal())
				.collect(Collectors.toSet());
		String[] origins = routes.stream().map(Route::getStartTerminal).toArray(s -> new String[s]);
		List<TrainAlert> trainAlerts = fetcher.fetchTrainAlerts(origins);
		List<TrainAlert> realTrainAlerts = trainAlerts.stream().filter(a -> destinations.contains(a.getDestination()))
				.filter(this::saveIfNotExisting).collect(Collectors.toList());
		if (!realTrainAlerts.isEmpty()) {
			sendMail(trainAlerts);
		}
	}

	private boolean saveIfNotExisting(TrainAlert tA) {
		TrainAlert existing = repository.findByTrainIdAndAlertMessageAndDate(tA.getTrainId(), tA.getAlertMessage(),
				tA.getDate());
		boolean exists = existing == null;
		if (exists) {
			repository.save(tA);
		}
		return exists;
	}

	private void sendMail(List<TrainAlert> lateOrMissedTrains) throws SendGridException {
		SendGrid sendgrid = new SendGrid(username, password);
		SendGrid.Email mail = new SendGrid.Email();
		mail.addTo("tlueecke@gmail.com");
		mail.setFrom("tim.lueecke@capgemini.com");
		mail.addToName("Tim Lüecke");
		mail.setSubject("Zugalarm!");
		String text = "Verspätete oder ausgefallene Züge:\n\n"
				+ lateOrMissedTrains.stream().map(t -> t.toString()).collect(Collectors.joining("\n\n"));
		LOG.info("Sending mail content: " + text);
		mail.setText(text);

		SendGrid.Response response = sendgrid.send(mail);
		LOG.info(response.getMessage());
	}
}
