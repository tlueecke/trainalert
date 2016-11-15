package de.lueecke.trainalert.watcher.config;

import static org.hamcrest.Matchers.containsInAnyOrder;
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

@RunWith(Parameterized.class)
public class WeekdayDecodeFromStringTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "0101001", new Weekday[] { Weekday.SUNDAY, Weekday.WEDNESDAY, Weekday.FRIDAY }, null },
				{ "1111111", Weekday.values(), null },
				{ "0000000", new Weekday[0], null },
				{ "010000", null, IllegalArgumentException.class },
				{ "00110011", null, IllegalArgumentException.class }
		});
	}

	@Parameter(0)
	public String bitmask;
	@Parameter(1)
	public Weekday[] expectedWeekdays;
	@Parameter(2)
	public Class<? extends Throwable> expectedException;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testFromString() {
		if (expectedException != null) {
			thrown.expect(expectedException);
		}
		assertThat(Weekday.fromString(bitmask), containsInAnyOrder(expectedWeekdays));
	}

}
