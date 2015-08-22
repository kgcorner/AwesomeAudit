package com.aa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.aa.constants.Command;
import com.aa.constants.Locations;
import com.aa.constants.Views;
import com.aa.customcontrol.controller.PluginIcon;
import com.aa.model.Plugin;
import com.aa.model.Project;
import com.aa.pluginutil.PluginRunner;
import com.easyfx.exception.NoSessionAvailableException;
import com.easyfx.security.JWinSession;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class Run implements Initializable {
	@FXML private FlowPane pluginStatusHolder;
	private static final Logger log= Logger.getLogger(Run.class);
	private Project selectedProject;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.selectedProject=(Project) JWinSession.get("selectedProject");
			for(Plugin plugin:this.selectedProject.getPlugins())
			{
				FXMLLoader loader= new FXMLLoader();
				Parent root;
				try {
					root = loader.load(PluginHouse.class.getResourceAsStream(Views.PLUGIN_ICON));
					Text txtName=(Text) root.lookup("#txtPlugin");
					CheckBox chkSelected=(CheckBox)root.lookup("#chkSelected");
					chkSelected.setVisible(false);
					ImageView pluginIcon= (ImageView) root.lookup("#pluginIcon");
					File pluginImageFile= new File(Locations.PLUGIN_RUN_IMAGE);
					log.info("Load File path:"+Run.class.getResource(Locations.PLUGIN_RUN_IMAGE).getPath());
					//if(pluginImageFile.exists())
					//{
						Image img= new Image(Run.class.getResourceAsStream(Locations.PLUGIN_RUN_IMAGE));
						pluginIcon.setImage(img);
					//}
					txtName.setText(plugin.getName());
					PluginIcon pIcon=loader.getController();
					pIcon.setPlugin(plugin);
					pluginStatusHolder.getChildren().add((Node)root);
					PluginRunner runner= new PluginRunner();
					runner.setProject(selectedProject);
					runner.setPlugin(plugin);
					Thread pluginThread= new Thread(runner);
					pluginThread.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NoSessionAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public static void main(String[] args) {
	log.info("Load File path:"+Run.class.getResource(Locations.PLUGIN_RUN_IMAGE).getPath());	
}
}
