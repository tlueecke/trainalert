package de.lueecke.trainalert.watcher.config;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigQueryControllerTest {

	@Autowired
	private ConfigQueryController service;

	@MockBean
	private TerminalWatchJobRepository repositoryMock;

	@Test
	public void testFindRoutesToWatch() {
		when(repositoryMock.findByFromHourLessThanEqualAndToHourGreaterThan(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Lists.newArrayList(new TerminalWatchJob("HH", 10, 20, "1111111")));

		List<Route> routes = this.service.findRoutesToWatch(null);
		assertThat(routes, contains(new Route("HH", null)));
	}

	@Test
	public void testFindNoRouteMatchingToSingleWeekday() {
		when(repositoryMock.findByFromHourLessThanEqualAndToHourGreaterThan(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Lists.newArrayList(new TerminalWatchJob("H2", 10, 20, "0001000")));

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		List<Route> routes = this.service.findRoutesToWatch(c.getTime());
		assertThat(routes, contains(new Route("H2", null)));
	}

	@Test
	public void testFindNoRouteMatchingToWeekday() {
		when(repositoryMock.findByFromHourLessThanEqualAndToHourGreaterThan(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Lists.newArrayList(new TerminalWatchJob("H3", 10, 20, "1111101")));

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		List<Route> routes = this.service.findRoutesToWatch(c.getTime());
		assertThat(routes, empty());
	}

	@Test
	public void testFindNone() {
		when(repositoryMock.findByFromHourLessThanEqualAndToHourGreaterThan(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(Lists.newArrayList());

		List<Route> routes = this.service.findRoutesToWatch(null);
		assertThat(routes, empty());
	}

}
