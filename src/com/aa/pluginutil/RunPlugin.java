package com.aa.pluginutil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.aa.model.Plugin;
import com.aa.model.ProcessType;

public class RunPlugin {
	public static void run()
	{
		try {
			Plugin plugin= PluginParse.parse("/home/kumar/aaPlugin.xml");
			URLClassLoader child = new URLClassLoader (new URL[] {new File("/home/kumar/vp.jar").toURI().toURL()}, RunPlugin.class.getClassLoader());
			Class pluginClass=null;
			try {
				pluginClass=Class.forName(plugin.getConfig().getClassName(),true,child);
				//log.info(pluginClass.getMethods()[0].getName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(com.aa.model.Process p:plugin.getConfig().getProcesses())
			{
				if(p.getType()==ProcessType.CODEBASE)
				{
					try {
						Method method=pluginClass.getDeclaredMethods()[0];
						
						try {
							Object instance=pluginClass.newInstance();
							try {
								method.invoke(instance, "Hello");
							} catch (IllegalArgumentException
									| InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (InstantiationException
								| IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		run();
	}
}
