package com.aa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.aa.constants.Locations;
import com.aa.model.Plugin;
import com.aa.model.Project;

public class ProjectIO {
	public static List<Project> getAllProject()
	{
		FileInputStream is=null;
		List<Project> projects=null;
		ObjectInputStream os=null;
		if(!new File(Locations.PROJECT_INFO_FILE).exists())
		{
			return null;
		}
		try {
			is=new FileInputStream(Locations.PROJECT_INFO_FILE);
			os= new ObjectInputStream(is);
			projects=(List<Project>) os.readObject();
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
		return projects;
	}
	public static void serializedProject(List<Project> projects)
	{
		FileOutputStream fos=null;
		ObjectOutputStream oos= null;
		//List<Project> projects=getAllProject();		
		
		try {
			fos= new FileOutputStream(Locations.PROJECT_INFO_FILE);
			oos=new ObjectOutputStream(fos);
			oos.writeObject(projects);
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
	public static void serializedProject(Project project)
	{
		FileOutputStream fos=null;
		ObjectOutputStream oos= null;
		List<Project> projects=getAllProject();		
		if(projects!=null)
		{
			Collections.sort(projects);
			project.setProjectId(projects.get(projects.size()-1).getProjectId()+1);
			projects= new ArrayList<>();
			File file= new File(Locations.PROJECT_INFO_FILE);
			if(!file.exists())
			{
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			projects = new ArrayList<Project>();
			projects.add(project);
		}
		try {
			fos= new FileOutputStream(Locations.PROJECT_INFO_FILE);
			oos=new ObjectOutputStream(fos);
			oos.writeObject(projects);
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
}
