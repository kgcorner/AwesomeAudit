package com.aa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader {
	 private static final Class<?>[] parameters = new Class[]{URL.class};
	public static void load(URL jarUrl)
	{
		URLClassLoader loader=(URLClassLoader)ClassLoader.getSystemClassLoader();
		Class<?> sysClass=URLClassLoader.class;
		
			Method m;
			try {
				m = sysClass.getDeclaredMethod("addURL", parameters);
				m.setAccessible(true);
				m.invoke(loader, jarUrl);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
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
			}
			
	
	}
	public static void load(String url) throws MalformedURLException, FileNotFoundException
	{
		File f= new File(url);
		if(f.exists())
		{
			load(f.toURI().toURL());
		}
		else
		{
			throw new FileNotFoundException("File at "+url+" not found");
		}
	}
	
}
