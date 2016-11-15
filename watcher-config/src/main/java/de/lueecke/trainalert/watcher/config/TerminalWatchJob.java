package de.lueecke.trainalert.watcher.config;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class TerminalWatchJob {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String terminalId;

	@NotNull
	private Integer fromHour;

	@NotNull
	private Integer toHour;

	private String weekdays;

	public TerminalWatchJob(String terminalId, Integer fromHour, Integer toHour, String weekdays) {
		this.terminalId = terminalId;
		this.fromHour = fromHour;
		this.toHour = toHour;
		this.weekdays = weekdays;
	}

	public TerminalWatchJob() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public Integer getFromHour() {
		return fromHour;
	}

	public void setFromHour(Integer fromHour) {
		this.fromHour = fromHour;
	}

	public Integer getToHour() {
		return toHour;
	}

	public void setToHour(Integer toHour) {
		this.toHour = toHour;
	}

	public Set<Weekday> getActiveOnWeekdays() {
		return Weekday.fromString(weekdays);
	}

	public void setActiveOnWeekdays(Set<Weekday> weekdays) {
		this.weekdays = Weekday.toString(weekdays);
	}
}
