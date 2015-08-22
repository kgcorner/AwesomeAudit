package com.aa.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.aa.model.Project;
import com.aa.util.ParamsFactory;
import com.aa.util.ProjectIO;
import com.easyfx.exception.NoControllorFoundException;
import com.easyfx.exception.NoResultFound;
import com.easyfx.security.JWinSession;
import com.easyfx.util.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class ProjectBrowser extends BaseController implements Initializable{
	@FXML private ComboBox<Project> cmbProjects;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Project> projects=ProjectIO.getAllProject();
		if(projects!=null)
		{
			ObservableList<Project> projectCollection=FXCollections.observableArrayList();
			for(Project p:projects)
			{
				projectCollection.add(p);
			}
			cmbProjects.setItems(projectCollection);
		}
	}
	@FXML protected void onSelect(ActionEvent event)
	{	
		JWinSession.add("selectedProject", this.cmbProjects.getSelectionModel().getSelectedItem());
		try {
			load("projectBrowser", "auditRun", ParamsFactory.masterLayout);
		} catch (IOException | NoControllorFoundException | NoResultFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
