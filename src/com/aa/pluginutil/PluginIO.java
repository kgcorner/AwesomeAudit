package com.aa.pluginutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.aa.constants.Locations;
import com.odoa.models.Plugin;


public class PluginIO {
	private static final Logger log= Logger.getLogger(PluginIO.class);
	@SuppressWarnings("unchecked")
	public static Set<Plugin> getAllPlugin()
	{
		FileInputStream is=null;
		Set<Plugin> plugins=null;
		ObjectInputStream os=null;
		if(!new File(Locations.PLUGIN_INFO_FILE).exists())
		{
			return null;
		}
		try {
			is=new FileInputStream(Locations.PLUGIN_INFO_FILE);
			os= new ObjectInputStream(is);
			plugins=(Set<Plugin>) os.readObject();
		} catch (IOException | ClassNotFoundException e) {
			log.error("Error retrieving plugins",e);			
		}
		finally
		{
			if(is!=null)
			{
				try {
					is.close();
					if(os!=null)
						os.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		return plugins;
	}
	public static void serializedPlugin(Plugin plugin)
	{
		FileOutputStream fos=null;
		ObjectOutputStream oos= null;
		Set<Plugin> plugins=getAllPlugin();
		if(plugins==null)
		{
			plugins= new HashSet<>();
			File file= new File(Locations.PLUGIN_INFO_FILE);
			if(!file.exists())
			{
				try {
					file.createNewFile();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}			
		}		
		plugins.add(plugin);
		try {
			fos= new FileOutputStream(Locations.PLUGIN_INFO_FILE);
			oos=new ObjectOutputStream(fos);
			oos.writeObject(plugins);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		finally
		{
			if(fos!=null)
			{
				try {
					fos.close();
					if(oos!=null)
						oos.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
	}
	/***
	 * This method is for importing plugin through plugin directory. 
	 * Assumption: Plugin Directory location is not validated. This method is to validate plugin structure
	 * Please validate location prior.
	 * Directory should have atleast three files
	 * xml file
	 * jar file
	 * icon image (gif,png,jpeg)  
	 * @param pluginDirectory
	 * @return true if valid false otherwise
	 */
	public static boolean validatePluginDirectory(String pluginDirectory)	
	{
		boolean result=false;
		File pluginDirectoryFile=new File(pluginDirectory);
		File[] pluginDirCon=pluginDirectoryFile.listFiles();
		boolean jar=false,xml=false,png=false;
		for(File f:pluginDirCon)
		{
			String ext=f.getName().substring(f.getName().lastIndexOf(".")+1);
			switch(ext.toLowerCase())
			{
				case "jar":
					jar=true;
					break;
				case "png":
					png=true;
					break;
				case "jpg":
					png=true;
					break;
				case "gif":
					png=true;
					break;
				case "jpeg":
					png=true;
					break;
				case "xml":
					xml=true;
					break;				
			}
		}
		 result=jar&xml&png;
		 return result;
	}
	public static String getPluginDirectory(Plugin plugin)
	{
		String pluginDirectory=Locations.PLUGIN_DIRECTORY_ABS;
		pluginDirectory+=plugin.getJarName()+"/";
		return pluginDirectory;
	}
	
}
