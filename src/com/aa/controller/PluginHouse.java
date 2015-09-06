package com.aa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.aa.constants.Locations;
import com.aa.constants.Views;
import com.aa.customcontrol.controller.PluginIcon;
import com.aa.pluginutil.PluginIO;
import com.aa.util.ParamsFactory;
import com.odoa.exception.PluginParseException;
import com.odoa.models.Plugin;
import com.odoa.util.PluginParser;

public class PluginHouse implements Initializable {
	private static final Logger log= Logger.getLogger(PluginHouse.class);
	@FXML private FlowPane pluginHolder;
	@FXML private Label lblMessage;
	@FXML protected void onSet(MouseEvent event){}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Set<Plugin> plugins=PluginIO.getAllPlugin();
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
					CheckBox chkSelected=(CheckBox)root.lookup("#chkSelected");
					chkSelected.setVisible(false);
					ImageView pluginIcon= (ImageView) root.lookup("#pluginIcon");
					File pluginImageFile= new File(pluginDirectory+"/"+ plugin.getIconPath());
					if(pluginImageFile.exists())
					{
						Image img= new Image(new FileInputStream(pluginImageFile));
						pluginIcon.setImage(img);
					}
					txtName.setText(plugin.getName());
					PluginIcon pIcon=loader.getController();
					pIcon.setPlugin(plugin);
					pluginHolder.getChildren().add((Node)root);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	@FXML private void pluginAddMethod()
	{
		DirectoryChooser dialog= new DirectoryChooser();
		Plugin plugin=null;
		File pluginFileDirectory= dialog.showDialog(ParamsFactory.primaryStage);
		if(pluginFileDirectory!=null && pluginFileDirectory.isDirectory())
		{
			if(!PluginIO.validatePluginDirectory(pluginFileDirectory.getAbsolutePath()))
			{
				log.info("Not a valid Plugin");	
				lblMessage.setText("Not a valid plugin");
				lblMessage.setStyle("-fx-text-fill:red;");
				return;
			}
			else
			{
				lblMessage.setText("");
				try {
					File pluginFile= new File(pluginFileDirectory.getAbsolutePath()+"/"+Locations.PLUGINS_CONFIG);
					plugin=PluginParser.parsePlugin(pluginFile);
					String pluginDirectory=PluginIO.getPluginDirectory(plugin);
					File destPluginDirectory= new File(pluginDirectory);
					if(!destPluginDirectory.exists())
						destPluginDirectory.mkdir();
					FileUtils.copyDirectory(pluginFileDirectory, destPluginDirectory);
										
					FXMLLoader loader= new FXMLLoader();
					Parent root=loader.load(PluginHouse.class.getResourceAsStream(Views.PLUGIN_ICON));
					Text txtName=(Text) root.lookup("#txtPlugin");
					ImageView pluginIcon= (ImageView) root.lookup("#pluginIcon");					
					File pluginImageFile= new File(pluginDirectory+ plugin.getIconPath());
					if(pluginImageFile.exists())
					{
						Image img= new Image(new FileInputStream(pluginImageFile));
						pluginIcon.setImage(img);
					}
					txtName.setText(plugin.getName());
					pluginHolder.getChildren().add((Node)root);
					root.setCursor(Cursor.HAND);
					PluginIO.serializedPlugin(plugin);
				} catch (IOException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
					log.error("Error occured while copying plugin directory", e);	
					lblMessage.setText("Error copying plugin directory");
					lblMessage.setStyle("-fx-text-fill:red;");
				} catch (PluginParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
