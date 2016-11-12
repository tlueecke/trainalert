package de.lueecke.trainalert.watcher.config;

public class Route {

	private String startTerminal;

	private String endTerminal;

	public Route() {
	}

	public Route(String startTerminal, String endTerminal) {
		this.startTerminal = startTerminal;
		this.endTerminal = endTerminal;
	}

	public String getStartTerminal() {
		return startTerminal;
	}

	public void setStartTerminal(String startTerminal) {
		this.startTerminal = startTerminal;
	}

	public String getEndTerminal() {
		return endTerminal;
	}

	public void setEndTerminal(String endTerminal) {
		this.endTerminal = endTerminal;
	}

}
