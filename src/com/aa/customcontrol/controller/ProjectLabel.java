package com.aa.customcontrol.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import org.apache.log4j.Logger;

import com.aa.model.Project;

public class ProjectLabel implements Initializable{
	private static final Logger log= Logger.getLogger(ProjectLabel.class);
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
		log.info("Loaded");
	}
	
}
