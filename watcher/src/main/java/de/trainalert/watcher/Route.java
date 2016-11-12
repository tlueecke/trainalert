package de.trainalert.watcher;

public class Route {

	private String startTerminal;

	private String endTerminal;

	public String getStartTerminal() {
		return startTerminal;
	}

	public void setStartTerminal(String fromTerminal) {
		this.startTerminal = fromTerminal;
	}

	public String getEndTerminal() {
		return endTerminal;
	}

	public void setEndTerminal(String toTerminal) {
		this.endTerminal = toTerminal;
	}

}
