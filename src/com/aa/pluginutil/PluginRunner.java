package com.aa.pluginutil;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import com.aa.constants.Command;
import com.aa.model.Plugin;
import com.aa.model.PluginRunConfig;
import com.aa.model.Process;
import com.aa.model.ProcessType;
import com.aa.model.Project;
import com.aa.util.CustomClassLoader;

public class PluginRunner implements Runnable{
	private Project project;
	private Plugin plugin;
	@Override
	public void run() {
		try {
			//String jarFileName=plugin.getPluginDirectory().split("/")[plugin.getPluginDirectory().split("/").length-1];
			//Class.forName(plugin.getPluginDirectory()+plugin.getJarFileName());
			try {
				CustomClassLoader.load(plugin.getPluginDirectory()+plugin.getJarFileName());
			} catch (MalformedURLException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PluginRunConfig config=plugin.getConfig();
			for(Process process:config.getProcesses())
			{
				if(process.getType()==ProcessType.CODEBASE)
				{
					Object runner=Thread.currentThread().getContextClassLoader().loadClass(config.getClassName()).newInstance();
					//Object runClass=runner.
					if(runner!=null)
					{
						Method[] methods=runner.getClass().getMethods();
								//Thread.currentThread().getContextClassLoader().loadClass(config.getClassName()).getMethods();
						for(Method m:methods)
						{
							if(m.getName().equals(Command.PLGUIN_RUN_METHOD_NAME_CODEBASE))
							{
								System.out.println("Method Found");
								m.invoke(runner, this.project.getPath());
							}
						}
					}
				}
				else
				{
					System.out.println("Not Supported yet");
				}
			}
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Plugin getPlugin() {
		return plugin;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

}
