package com.aa.customcontrol.controller;

import java.net.URL;
import java.util.ResourceBundle;




import com.odoa.models.Plugin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class PluginIcon implements Initializable{

	@FXML private ImageView pluginIcon;
	@FXML private Text txtPlugin;
	@FXML private CheckBox chkSelected;
	private Plugin plugin;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public Plugin getPlugin() {
		return plugin;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

}
