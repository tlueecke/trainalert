package de.lueecke.trainalert.watcher.config;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class WeekdayBitmaskTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { Weekday.TUESDAY, "0000100" }, { Weekday.SUNDAY, "0000001" },
				{ Weekday.SATURDAY, "1000000" } });
	}

	@Parameter(value = 0)
	public Weekday weekday;
	@Parameter(value = 1)
	public String expectedBitmask;

	@Test
	public void testBitmask() {
		assertThat(weekday.bitmask(), equalTo(expectedBitmask));
	}
}
