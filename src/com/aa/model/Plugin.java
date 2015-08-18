package com.aa.model;

import java.io.Serializable;

public class Plugin implements Serializable,Comparable<Plugin> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -52920592235704497L;
	@Override
	public String toString() {
		return "Plugin [name="+name+", runClass=" + runClass + ", config=" + config
				+ ", iconPath=" + iconPath + ", description=" + description
				+ ", authorName=" + authorName + ", version=" + version + "]";
	}
	private String runClass;
	private PluginRunConfig config;
	private String iconPath;
	private String description;
	private String authorName;
	private String version;
	private String name;
	private String pluginDirectory;
	private int pluginId;
	private String jarFileName;
	public String getRunClass() {
		return runClass;
	}
	public void setRunClass(String runClass) {
		this.runClass = runClass;
	}
	public PluginRunConfig getConfig() {
		return config;
	}
	public void setConfig(PluginRunConfig config) {
		this.config = config;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPluginDirectory() {
		return pluginDirectory;
	}
	public void setPluginDirectory(String pluginDirectory) {
		this.pluginDirectory = pluginDirectory;
	}
	public int getPluginId() {
		return pluginId;
	}
	public void setPluginId(int pluginId) {
		this.pluginId = pluginId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Plugin)
			return this.pluginId==((Plugin)obj).pluginId;
		return false;
	}
	@Override
	public int compareTo(Plugin o) {
		
		return this.pluginId-o.pluginId;
	}
	public String getJarFileName() {
		return jarFileName;
	}
	public void setJarFileName(String jarFileName) {
		this.jarFileName = jarFileName;
	}
}
