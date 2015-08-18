package com.aa.model;

import java.io.Serializable;

public class Process implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -791923452105074282L;
	private ProcessType type;
	public Process()
	{
		
	}
	public Process(ProcessType type)
	{
		this.setType(type);
	}
	public ProcessType getType() {
		return type;
	}
	public void setType(ProcessType type) {
		this.type = type;
	}
	
}
