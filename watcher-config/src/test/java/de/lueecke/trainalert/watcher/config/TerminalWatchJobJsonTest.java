package de.lueecke.trainalert.watcher.config;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class TerminalWatchJobJsonTest {

	@Autowired
	private JacksonTester<TerminalWatchJob> json;

	@Test
	public void testSerialize() throws Exception {
		TerminalWatchJob job = new TerminalWatchJob("AH", 10, 20, "0000110", true);
		// Assert against a `.json` file in the same package as the test
		System.out.println(json.write(job).getJson());
		this.json.write(job).assertThat().isEqualToJson(
				"{\"id\":null,\"terminalId\":\"AH\",\"fromHour\":10,\"toHour\":20,\"activeOnWeekdays\":[\"MONDAY\",\"TUESDAY\"],\"active\":true}");
	}

	@Test
	public void testDeserialize() throws Exception {
		String content = "{\"terminalId\":\"ATST\",\"fromHour\":20,\"toHour\":22,\"activeOnWeekdays\":[\"MONDAY\",\"WEDNESDAY\",\"FRIDAY\"],\"active\":false}";
		TerminalWatchJob job = this.json.parse(content).getObject();
		assertThat(job.getId(), nullValue());
		assertThat(job.getTerminalId(), equalTo("ATST"));
		assertThat(job.getFromHour(), equalTo(20));
		assertThat(job.getToHour(), equalTo(22));
		assertThat(job.getActiveOnWeekdays(), containsInAnyOrder(Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.FRIDAY));
		assertThat(job.isActive(), equalTo(false));
	}
}
