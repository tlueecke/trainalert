package de.lueecke.trainalert.watcher.config;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class RouteJsonTest {

	@Autowired
	private JacksonTester<Route> json;

	@Test
	public void testSerialize() throws Exception {
		Route route = new Route("AH", "ATST");
		// Assert against a `.json` file in the same package as the test
		System.out.println(json.write(route).getJson());
		this.json.write(route).assertThat().isEqualToJson(
				"{\"startTerminal\":\"AH\",\"endTerminal\":\"ATST\"}");
	}

	@Test
	public void testDeserialize() throws Exception {
		String content = "{\"startTerminal\":\"HH\",\"endTerminal\":\"ESCH\"}";
		Route route = this.json.parse(content).getObject();
		assertThat(route.getStartTerminal(), equalTo("HH"));
		assertThat(route.getEndTerminal(), equalTo("ESCH"));
	}
}
