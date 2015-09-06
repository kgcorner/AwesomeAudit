package com.aa.pluginutil;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.apache.log4j.Logger;

import com.aa.constants.Command;
import com.aa.constants.Locations;
import com.aa.model.Project;
import com.aa.util.CustomClassLoader;
import com.odoa.models.Plugin;
import com.odoa.models.ProcessType;
import com.odoa.models.RunConfig;

public class PluginRunner implements Runnable{
	private static final Logger log= Logger.getLogger(PluginRunner.class);
	private Project project;
	private Plugin plugin;
	private ImageView pluginRunImage;
	@Override
	public void run() {
		String pluginDirectory=PluginIO.getPluginDirectory(plugin);
		try {
			//String jarFileName=plugin.getPluginDirectory().split("/")[plugin.getPluginDirectory().split("/").length-1];
			//Class.forName(plugin.getPluginDirectory()+plugin.getJarFileName());
			try {
				CustomClassLoader.load(pluginDirectory+plugin.getJarName()+".jar");
			} catch (MalformedURLException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RunConfig config=plugin.getRunConfigs().get(0);
			
			
				if(config.getProcessType()==ProcessType.INDIVIDUAL)
				{
					Object runner=Thread.currentThread().getContextClassLoader().loadClass(config.getRunClassName()).newInstance();
					//Object runClass=runner.
					if(runner!=null)
					{
						Method[] methods=runner.getClass().getMethods();
								//Thread.currentThread().getContextClassLoader().loadClass(config.getClassName()).getMethods();
						for(Method m:methods)
						{
							if(m.getName().equals(Command.PLGUIN_RUN_METHOD_NAME_CODEBASE))
							{
								log.info("Method Found");
								m.invoke(runner, this.project.getPath(),Locations.OUTPUT_FILE_LOCATION);
							}
						}
					}
				}
				else
				{
					log.info("Not Supported yet");
				}
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {						
						pluginRunImage.setImage(new Image(Locations.PLUGIN_COMPLETE_ICON));
					}
				});
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(),e);
		} catch (InstantiationException e) {
			log.error(e.getMessage(),e);
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
	public ImageView getPluginRunImage() {
		return pluginRunImage;
	}
	public void setPluginRunImage(ImageView pluginRunImage) {
		this.pluginRunImage = pluginRunImage;
	}

}
