package com.aa.customcontrol.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.aa.model.Project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ProjectLabel implements Initializable{
	@FXML private Text txtProject;
	@FXML private ImageView imgProject;
	private Project project;
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@FXML private void onSelect(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Loaded");
	}
	
}
