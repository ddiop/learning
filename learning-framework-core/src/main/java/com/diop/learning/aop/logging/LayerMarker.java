package com.diop.learning.aop.logging;

public enum LayerMarker {

	DOMAIN("DOMAIN"), APPLICATION("APPLICATION"), INFRASTRUCTURE("INFRASTRUCTURE"), RESOURCE("RESOURCE");

	private final String label;

	LayerMarker(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
