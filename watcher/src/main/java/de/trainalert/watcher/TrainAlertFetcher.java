package de.trainalert.watcher;

import java.util.List;

public interface TrainAlertFetcher {

	List<TrainAlert> fetchTrainAlerts(String... stations);

}
