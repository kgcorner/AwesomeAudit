package com.aa.model;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable,Comparable<Project> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2212717401870761532L;
	private String name;
	private String description;
	private String path;
	private String buildFrequency;
	private int projectId;
	private String image;
	private List<Plugin> plugins;
	public String getName() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Project)
		{
			Project p=(Project)obj;
			if(p.projectId==this.projectId)
				return true;
			else
				return false;
		}
		return false;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getBuildFrequency() {
		return buildFrequency;
	}
	public void setBuildFrequency(String buildFrequency) {
		this.buildFrequency = buildFrequency;
	}
	@Override
	public int compareTo(Project o) {
		return this.projectId-o.projectId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Plugin> getPlugins() {
		return plugins;
	}
	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}
	@Override
	public String toString()
	{
		return this.name;
	}
}
