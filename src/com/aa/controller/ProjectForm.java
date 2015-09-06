package com.aa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import com.aa.constants.StaticMessages;
import com.aa.constants.Views;
import com.aa.customcontrol.controller.PluginIcon;
import com.aa.customcontrol.controller.ProjectLabel;
import com.aa.model.Project;
import com.aa.pluginutil.PluginIO;
import com.aa.util.ParamsFactory;
import com.aa.util.ProjectIO;
import com.easyfx.util.BaseController;
import com.odoa.models.Plugin;

public class ProjectForm extends BaseController implements Initializable{
	@FXML private TextField txtProjectName;
	@FXML private TextField txtBuildFrequency;
	@FXML private TextArea txtDescription;
	@FXML private HBox hboxPluginContainer;
	@FXML private Label lblProjectPath; 
	@FXML private VBox vboxPrjectContainer;
	private boolean updating=false;
	private Project selectedProject=null;
	private Set<Plugin> selectedPlugins= new HashSet<>();
	@FXML private Label lblIndecator;
	private static final Logger log= Logger.getLogger(ProjectForm.class);
	@FXML protected void onBrowse(ActionEvent event)
	{
		DirectoryChooser fileChooser= new DirectoryChooser();
		File codeBase= fileChooser.showDialog(ParamsFactory.primaryStage);
		if(codeBase!=null)
		{
			lblProjectPath.setText(codeBase.getAbsolutePath());
		}
	}
	@FXML protected void onSave(ActionEvent event)
	{
		if(!updating)
		{
			Project project = new Project();
			project.setName(txtProjectName.getText());
			project.setDescription(txtDescription.getText());
			project.setBuildFrequency(txtBuildFrequency.getText());
			project.setPath(lblProjectPath.getText());
			project.setPlugins(selectedPlugins);
			ProjectIO.serializedProject(project);
			lblIndecator.setText(StaticMessages.PROJECT_FORM_PROJECT_UPDATE);
		}
		else
		{
			if(selectedProject!=null)
			{
				for(Project p:ProjectIO.getAllProject())
				{
					if(p.equals(selectedProject))
					{
						p.setPlugins(selectedPlugins);
						p.setName(txtProjectName.getText());
						p.setDescription(txtDescription.getText());
						p.setBuildFrequency(txtBuildFrequency.getText());
						p.setPath(lblProjectPath.getText());
						ProjectIO.serializedProject(selectedProject);
						lblIndecator.setText(StaticMessages.PROJECT_FORM_PROJECT_SAVE);
					}
				}
			}
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Project> projects=ProjectIO.getAllProject();
		//Loading Project Label
		if(projects!=null)
		{
			for(Project p:projects)
			{
				FXMLLoader loader= new FXMLLoader();
				try {
					Parent parent=loader.load(ProjectForm.class.getResourceAsStream(Views.PROJECT_LABEL));
					Text txtProject=(Text) parent.lookup("#txtProject");
					//ImageView imgProject=(ImageView) parent.lookup("#imgProject");
					txtProject.setText(p.getName());
					//imgProject.setImage(new Image(p.getImage()));
					ProjectLabel pLabel=loader.getController();
					pLabel.setProject(p);
					parent.setOnMouseClicked(new EventHandler<Event>() {

						@Override
						public void handle(Event event) {						
							//Parent p=(Parent) event.getSource();
							ProjectLabel pLabel=loader.getController();
							selectedProject=pLabel.getProject();
							//log.info(pLabel.getProject().getName());
							txtProjectName.setText(pLabel.getProject().getName());
							txtDescription.setText(pLabel.getProject().getDescription());
							lblProjectPath.setText(pLabel.getProject().getPath());
							txtBuildFrequency.setText(pLabel.getProject().getBuildFrequency());
							updating=true;
						}
					});
					vboxPrjectContainer.getChildren().add(parent);
				} catch (IOException e) {
					log.error(StaticMessages.PROJECT_FORM_PROJECT_LOAD_ERROR,e);
					lblIndecator.setText(StaticMessages.PROJECT_FORM_PROJECT_LOAD_ERROR);
				}
			}
		}
		Set<Plugin> plugins= PluginIO.getAllPlugin();
		if(plugins!=null)
		{
			for(Plugin plugin:plugins)
			{
				FXMLLoader loader= new FXMLLoader();
				Parent root;
				try {
					String pluginDirectory=PluginIO.getPluginDirectory(plugin);
					root = loader.load(PluginHouse.class.getResourceAsStream(Views.PLUGIN_ICON));
					Text txtName=(Text) root.lookup("#txtPlugin");
					ImageView pluginIcon= (ImageView) root.lookup("#pluginIcon");
					File pluginImageFile= new File(pluginDirectory+ plugin.getIconPath());
					if(pluginImageFile.exists())
					{
						Image img= new Image(new FileInputStream(pluginImageFile));
						pluginIcon.setImage(img);
					}
					txtName.setText(plugin.getName());
					PluginIcon pIcon=loader.getController();
					pIcon.setPlugin(plugin);
					selectedPlugins.add(plugin);
					CheckBox chkSelected= (CheckBox) root.lookup("#chkSelected");
					chkSelected.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							selectedPlugins.remove(plugin);							
						}
					});
					hboxPluginContainer.getChildren().add((Node)root);
				} catch (IOException e) {
					log.error(StaticMessages.PROJECT_FORM_PLUGIN_LOAD_ERROR,e);
					lblIndecator.setText(lblIndecator.getText()+" "+StaticMessages.PROJECT_FORM_PLUGIN_LOAD_ERROR);
				}
				
			}
		}
	}
	
}
