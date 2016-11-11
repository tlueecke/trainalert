package de.trainalert.watcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

@Component
public class HtmlScrapingTrainAlertFetcher implements TrainAlertFetcher {

	private static final Map<String, String> stationMap = new HashMap<>();
	{
		stationMap.put("ATST", "Tostedt");
		stationMap.put("AH", "Hamburg Hbf");
	}

	@Value(value = "${htmlscraping.timeout}")
	private Integer scrapingTimeout;

	private WebClient webClient;
	private HtmlPage page;

	@PostConstruct
	public void initialize() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		page = webClient.getPage("http://www.der-metronom.de/#live-fahrplan");
		webClient.waitForBackgroundJavaScript(scrapingTimeout);
	}

	@Override
	public List<TrainAlert> fetchTrainAlerts(String... stations) {
		List<TrainAlert> trainAlerts = new ArrayList<>();
		for (String station : stations) {
			HtmlSelect select = page.getHtmlElementById("stationen");
			HtmlOption option = select.getOptionByValue(station);
			select.setSelectedAttribute(option, true);
			webClient.waitForBackgroundJavaScript(scrapingTimeout);
			@SuppressWarnings("unchecked")
			List<HtmlTableCell> nodes = (List<HtmlTableCell>) page.getByXPath("//td[@class=\"status\"]");
			nodes.stream().filter(c -> !c.getTextContent().contains("pünktlich")).map(c -> c.getEnclosingRow())
					.map(r -> mapToTrainAlert(r, stationMap.get(station))).forEach(a -> trainAlerts.add(a));
		}
		return trainAlerts;
	}

	private TrainAlert mapToTrainAlert(HtmlTableRow row, String terminal) {
		TrainAlert alert = new TrainAlert();
		alert.setTerminal(terminal);
		alert.setTime(LocalTime.parse(row.getCell(0).asText().trim()));
		alert.setTrainId(row.getCell(1).asText().trim());
		alert.setDestination(row.getCell(2).asText().trim());
		alert.setAlertMessage(row.getCell(3).asText().trim());
		return alert;
	}

}
