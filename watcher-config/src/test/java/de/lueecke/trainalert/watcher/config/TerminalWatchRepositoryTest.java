package de.lueecke.trainalert.watcher.config;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class TerminalWatchRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TerminalWatchJobRepository repository;

	@Test
	public void findMatchingLowerBound() {
		TerminalWatchJob job = entityManager.persist(new TerminalWatchJob("AH", 10, 12, "1111111", true));
		List<TerminalWatchJob> jobs = repository.findByFromHourLessThanEqualAndToHourGreaterThanAndActiveIsTrue(10, 10);
		assertThat(jobs, contains(job));
	}

	@Test
	public void findMatchingUpperBound() {
		TerminalWatchJob job = entityManager.persist(new TerminalWatchJob("AST", 9, 12, "0000000", true));
		List<TerminalWatchJob> jobs = repository.findByFromHourLessThanEqualAndToHourGreaterThanAndActiveIsTrue(11, 11);
		assertThat(jobs, contains(job));
	}

	@Test
	public void dontFindEqualToUpperBound() {
		entityManager.persist(new TerminalWatchJob("AST", 12, 13, "1100100", true));
		List<TerminalWatchJob> jobs = repository.findByFromHourLessThanEqualAndToHourGreaterThanAndActiveIsTrue(13, 13);
		assertThat(jobs, empty());
	}

	@Test
	public void dontFindLowerThanLowerBound() {
		entityManager.persist(new TerminalWatchJob("AST", 20, 22, "0001000", true));
		List<TerminalWatchJob> jobs = repository.findByFromHourLessThanEqualAndToHourGreaterThanAndActiveIsTrue(19, 21);
		assertThat(jobs, empty());
	}

	@Test
	public void dontFindInactive() {
		TerminalWatchJob job = entityManager.persist(new TerminalWatchJob("AH", 10, 12, "1111111", false));
		List<TerminalWatchJob> jobs = repository.findByFromHourLessThanEqualAndToHourGreaterThanAndActiveIsTrue(11, 11);
		assertThat(jobs, empty());
	}

}
