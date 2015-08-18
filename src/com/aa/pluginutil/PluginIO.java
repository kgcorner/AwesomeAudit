package com.aa.pluginutil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.tools.DocumentationTool.Location;

import com.aa.constants.Command;
import com.aa.constants.Locations;
import com.aa.model.Plugin;

public class PluginIO {
	public static List<Plugin> getAllPlugin()
	{
		FileInputStream is=null;
		List<Plugin> plugins=null;
		ObjectInputStream os=null;
		if(!new File(Locations.PLUGIN_INFO_FILE).exists())
		{
			return null;
		}
		try {
			is=new FileInputStream(Locations.PLUGIN_INFO_FILE);
			os= new ObjectInputStream(is);
			plugins=(List<Plugin>) os.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return plugins;
	}
	public static void serializedPlugin(Plugin plugin)
	{
		FileOutputStream fos=null;
		ObjectOutputStream oos= null;
		List<Plugin> plugins=getAllPlugin();
		if(plugins==null)
		{
			plugins= new ArrayList<>();
			File file= new File(Locations.PLUGIN_INFO_FILE);
			if(!file.exists())
			{
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			plugin.setPluginId(1);
		}
		else
		{
			
			Collections.sort(plugins);
			plugin.setPluginId(plugins.get(plugins.size()-1).getPluginId()+1);
		}
		plugins.add(plugin);
		try {
			fos= new FileOutputStream(Locations.PLUGIN_INFO_FILE);
			oos=new ObjectOutputStream(fos);
			oos.writeObject(plugins);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static boolean validatePlugin(String pluginLocation)
	{
		String command=Command.PLUGIN_TAR_LIST;
		System.out.println("Command:"+command);
		Runtime runtime= Runtime.getRuntime();
		boolean jar=false;
		boolean image=false;
		boolean xml=false;
		//int count=0;
		BufferedReader br=null;
		try {
			
			Process proc= runtime.exec(new String[]{Command.PLUGIN_TAR_LIST,Command.PLUGIN_TAR_LIST_OPT,pluginLocation});
			InputStream is= proc.getInputStream();
			InputStream error=proc.getErrorStream();
			br= new BufferedReader(new InputStreamReader(is));
			String a="";
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//if(proc.isAlive())
			//{
				while((a=br.readLine())!=null)
				{
					switch(a.split("[/.]")[1])
					{
					case "jar":
						jar=true;
						break;
					case "jpg":
						image=true;
						break;
					case "png":
						image=true;
						break;
					case "xml":
						xml=true;
						break;
					}
					System.out.println(a);
					//count++;
				}
				br= new BufferedReader(new InputStreamReader(error));
				while((a=br.readLine())!=null)
				{
					System.out.println("Error:"+a);
				}
				System.out.println(proc.exitValue());
				return image&&xml&&jar;
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(br!=null)
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println(validatePlugin("/rough/awesomeaudit/AwesomeAudit/plugins/violation/New Folder/violation.tar"));
	}
}
