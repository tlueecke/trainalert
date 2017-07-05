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
	private String fromTerminal;

	private String toTerminal;

	@NotNull
	private Integer fromHour;

	@NotNull
	private Integer toHour;

	private String weekdays;

	private boolean active;

	public TerminalWatchJob(String fromTerminal, String toTerminal, Integer fromHour, Integer toHour, String weekdays,
			boolean active) {
		this.fromTerminal = fromTerminal;
		this.toTerminal = toTerminal;
		this.fromHour = fromHour;
		this.toHour = toHour;
		this.weekdays = weekdays;
		this.setActive(active);
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

	public String getFromTerminal() {
		return fromTerminal;
	}

	public void setFromTerminal(String terminalId) {
		this.fromTerminal = terminalId;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setToTerminal(String toTerminal) {
		this.toTerminal = toTerminal;
	}

	public String getToTerminal() {
		return toTerminal;
	}
}
