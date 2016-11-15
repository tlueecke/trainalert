package de.lueecke.trainalert.watcher.config;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "config", collectionResourceRel = "terminalWatchJob")
public interface TerminalWatchJobRepository extends PagingAndSortingRepository<TerminalWatchJob, Long> {

	List<TerminalWatchJob> findByFromHourLessThanEqualAndToHourGreaterThanAndActiveIsTrue(int fromHour, int toHour);
}
