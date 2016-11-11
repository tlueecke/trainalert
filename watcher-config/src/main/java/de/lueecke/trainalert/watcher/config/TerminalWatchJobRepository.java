package de.lueecke.trainalert.watcher.config;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "config", collectionResourceRel = "terminalWatchJob")
public interface TerminalWatchJobRepository extends PagingAndSortingRepository<TerminalWatchJob, Long> {

}
