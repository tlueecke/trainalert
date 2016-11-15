package de.lueecke.trainalert.watcher.config;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Sets;

@RunWith(Parameterized.class)
public class WeekdayEncodeToStringTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "1001010", new Weekday[] { Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.SATURDAY } },
				{ "1111111", Weekday.values() },
				{ "0000000", new Weekday[0] }
		});
	}

	@Parameter(0)
	public String expectedBitmask;
	@Parameter(1)
	public Weekday[] weekdays;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testFromString() {
		assertThat(Weekday.toString(Sets.newHashSet(weekdays)), equalTo(expectedBitmask));
	}

}
