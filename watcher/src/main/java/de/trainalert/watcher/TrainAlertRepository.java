package de.trainalert.watcher;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

public interface TrainAlertRepository extends CrudRepository<TrainAlert, Long> {

	TrainAlert findByTrainIdAndAlertMessageAndDate(String trainId, String alertMessage, Date date);

}
