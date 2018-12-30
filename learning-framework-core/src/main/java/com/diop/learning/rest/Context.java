package com.diop.learning.rest;

/**
 * Context connexion
 * @author djibi
 *
 */
public class Context {

	private String connectedId;
	private String canal;
	
	public Context() {
		
	}

	public Context(String customerId, String canal) {
		super();
		this.connectedId = customerId;
		this.canal = canal;
	}

	public String getCustomerId() {
		return connectedId;
	}

	public void setCustomerId(String customerId) {
		this.connectedId = customerId;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Context [customerId=");
		builder.append(connectedId);
		builder.append(", canal=");
		builder.append(canal);
		builder.append("]");
		return builder.toString();
	}
	
	
}
