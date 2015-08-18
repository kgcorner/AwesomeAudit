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
}
