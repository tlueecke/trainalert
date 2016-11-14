package de.trainalert.watcher;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WatcherJob {
	private static final Logger LOG = Logger.getLogger(WatcherJob.class.getName());

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TrainAlertFetcher fetcher;

	@Autowired
	private TrainAlertRepository repository;

	@Autowired
	private ConfigurationService cfgService;

	@Scheduled(fixedRateString = "${watchRate}")
	public void watchTrains() throws MessagingException {
		List<Route> routes = cfgService.findRoutesToWatch();
		Set<String> destinations = routes.stream().filter(r -> r.getEndTerminal() != null).map(r -> r.getEndTerminal())
				.collect(Collectors.toSet());
		String[] origins = routes.stream().map(Route::getStartTerminal).toArray(s -> new String[s]);
		List<TrainAlert> alerts = fetcher.fetchTrainAlerts(origins);
		List<TrainAlert> realAlerts = alerts.stream()
				.filter(a -> destinations.isEmpty() || destinations.contains(a.getDestination()))
				.filter(this::saveIfNotExisting).collect(Collectors.toList());
		if (!realAlerts.isEmpty()) {
			sendMail(alerts);
		}
	}

	private boolean saveIfNotExisting(TrainAlert tA) {
		TrainAlert existing = repository.findByTrainIdAndAlertMessageAndDate(tA.getTrainId(), tA.getAlertMessage(),
				tA.getDate());
		boolean exists = existing != null;
		if (!exists) {
			repository.save(tA);
		}
		return !exists;
	}

	private void sendMail(List<TrainAlert> alerts) throws MessagingException {

		LOG.info("Sending mail with " + alerts.size() + " train alerts");

		MimeMessage message = mailSender.createMimeMessage();
		message.addRecipients(RecipientType.TO, "tlueecke@gmail.com");
		message.setFrom("tim.lueecke@capgemini.com");
		message.setSubject("Zugalarm!");
		String text = "Verspätete oder ausgefallene Züge:\n\n"
				+ alerts.stream().map(t -> t.toString()).collect(Collectors.joining("\n\n"));
		message.setText(text);

		mailSender.send(message);
		LOG.info("Mail successfully sent");
	}
}
