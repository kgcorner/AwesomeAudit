package com.aa.model;

import java.io.Serializable;
import java.util.List;

public class PluginRunConfig implements Serializable{
	private String runName;
	private List<Process> processes;
	private String className;
	public String getRunName() {
		return runName;
	}
	@Override
	public String toString() {
		return "PluginRunConfig [runName=" + runName + ", processes="
				+ processes + ", className=" + className + "]";
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public List<Process> getProcesses() {
		return processes;
	}
	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}
	public void addProcesses(Process p)
	{
		this.processes.add(p);
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}
