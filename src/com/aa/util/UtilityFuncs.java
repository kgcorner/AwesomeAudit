package com.aa.util;

import java.io.File;

public class UtilityFuncs {
	public static String getPWD()
	{
		File f= new File("");
		return f.getAbsolutePath();
		
	}
	public static void main(String[] args) {
		getPWD();
	}
	public static boolean mkDir(String path)
	{
		File f= new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		return f.exists();
	}
	public static String getPluginName(File pluginDirectory)
	{
		File[] files=pluginDirectory.listFiles();
		String name="";
		for(File f:files)
		{			
			String ext=f.getName();
			ext=ext.contains(".")?ext.substring(ext.lastIndexOf(".")+1):"plugin";
			if(ext.toLowerCase().equals("jar"))
				name= f.getName().substring(0,f.getName().lastIndexOf((".")));
		}
		return name;
	}
}
