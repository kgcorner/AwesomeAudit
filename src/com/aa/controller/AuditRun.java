package com.aa.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import com.aa.constants.Views;
import com.aa.customcontrol.controller.ProjectLabel;
import com.aa.model.Project;
import com.easyfx.exception.NoSessionAvailableException;
import com.easyfx.security.JWinSession;
import com.easyfx.util.BaseController;

public class AuditRun extends BaseController implements Initializable{
	@FXML private BorderPane auditRunBorderPane;
	@FXML private Label projectTitleLbl;
	@FXML private Label lblProjectName;
	@FXML private Label lblCodeBase;
	@FXML private Label lblBuildFrequency;
	@FXML private Label lblProjectDescription;
	private Project selectedProject;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			load(Views.PLUGIN_HOLDER,this.auditRunBorderPane,4);
			this.selectedProject=(Project) JWinSession.get("selectedProject");
			System.out.println(this.selectedProject);
			this.projectTitleLbl.setText(this.selectedProject.getName());
			FXMLLoader loader= new FXMLLoader();
			Parent project=loader.load(AuditRun.class.getResourceAsStream(Views.PROJECT_LABEL));
			Text txtProject=(Text) project.lookup("#txtProject");
			ImageView imgProject=(ImageView) project.lookup("#imgProject");
			txtProject.setText(this.selectedProject.getName());
			//imgProject.setImage(new Image(p.getImage()));
			ProjectLabel pLabel=loader.getController();
			pLabel.setProject(this.selectedProject);
			this.lblProjectName.setText(this.selectedProject.getName());
			this.lblProjectDescription.setText(this.selectedProject.getDescription());
			this.lblBuildFrequency.setText(this.selectedProject.getBuildFrequency());
			this.lblCodeBase.setText(this.selectedProject.getPath());
			this.auditRunBorderPane.setLeft(project);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(NoSessionAvailableException x)
		{
			x.printStackTrace();
		}
		
	}
	
}
