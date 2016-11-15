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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTerminal == null) ? 0 : endTerminal.hashCode());
		result = prime * result + ((startTerminal == null) ? 0 : startTerminal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (endTerminal == null) {
			if (other.endTerminal != null)
				return false;
		} else if (!endTerminal.equals(other.endTerminal))
			return false;
		if (startTerminal == null) {
			if (other.startTerminal != null)
				return false;
		} else if (!startTerminal.equals(other.startTerminal))
			return false;
		return true;
	}

}
