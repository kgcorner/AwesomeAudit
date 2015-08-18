package com.aa.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.ConstantCallSite;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.aa.constants.Command;
import com.aa.constants.Locations;
import com.aa.constants.Views;
import com.aa.customcontrol.controller.PluginIcon;
import com.aa.model.Plugin;
import com.aa.pluginutil.PluginIO;
import com.aa.pluginutil.PluginParse;
import com.aa.util.ParamsFactory;
import com.aa.util.UtilityFuncs;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class PluginHouse implements Initializable {
	
	@FXML private FlowPane pluginHolder;
	@FXML protected void onAdd(MouseEvent event)
	{
		FileChooser dialog= new FileChooser();
		Plugin plugin=null;
		File pluginFile= dialog.showOpenDialog(ParamsFactory.primaryStage);
		if(pluginFile!=null)
		{
			if(!PluginIO.validatePlugin(pluginFile.getAbsolutePath()))
			{
				System.out.println("Not a valid Plugin");
				return;
			}
			StringTokenizer tokenizer= new StringTokenizer(pluginFile.getName());
			String fileName=tokenizer.nextToken(".");
			//String pwd=UtilityFuncs.getPWD();
			
			//System.out.println("File name:"+pluginFile.getName()+"SIze:"+ pluginFile.getName().split(".").length);
			//String command=Command.UNTAR_PLUGIN+pluginFile.getAbsolutePath()+Command.UNTAR_PLUGIN_LOCATION_PARAM+"/"+fileName;
			String[] command=
							{
							Command.UNTAR_PLUGIN, //TAR
							Command.UNTAR_PLUGIN_OPT, //-xvf							
							pluginFile.getAbsolutePath(), //Plgin File path
							Command.UNTAR_PLUGIN_LOCATION_OPT, //Constant Location to untar file
							Command.UNTAR_LOCATION+fileName //name for the dir
							};
			Runtime r= Runtime.getRuntime();
			try {
				System.out.println(Command.MKDIR_PLUGIN_DIR+fileName+";"+ command);
				if(!UtilityFuncs.mkDir(UtilityFuncs.getPWD()+"/plugins/"+fileName))
				{
					System.out.println("Failed to create plugin directory");
					System.out.println("Exiting");
					
				}
				Process p=r.exec(command);
				try {
					int ent=p.waitFor();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(p.exitValue()==0)
				{
					String pwd=UtilityFuncs.getPWD();
					pwd=pwd+"/plugins/"+fileName+"/"; //Plugin Directory
					try {
						plugin=PluginParse.parse(pwd+Locations.PLUGINS_CONFIG);
						plugin.setPluginDirectory(pwd);
						FXMLLoader loader= new FXMLLoader();
						Parent root=loader.load(PluginHouse.class.getResourceAsStream(Views.PLUGIN_ICON));
						Text txtName=(Text) root.lookup("#txtPlugin");
						ImageView pluginIcon= (ImageView) root.lookup("#pluginIcon");
						CheckBox chkSelected=(CheckBox) root.lookup("#chkSeleted");
						File pluginImageFile= new File(plugin.getPluginDirectory()+"/"+ plugin.getIconPath());
						if(pluginImageFile.exists())
						{
							Image img= new Image(new FileInputStream(pluginImageFile));
							pluginIcon.setImage(img);
						}
						txtName.setText(plugin.getName());
						pluginHolder.getChildren().add((Node)root);
						root.setCursor(Cursor.HAND);
						PluginIO.serializedPlugin(plugin);
					} catch (ParserConfigurationException | SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					InputStream error=p.getErrorStream();
					BufferedReader br= new BufferedReader(new InputStreamReader(error));
					String err="";
					while((err=br.readLine())!=null)
						System.out.println(err);
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@FXML protected void onSet(MouseEvent event){}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Plugin> plugins=PluginIO.getAllPlugin();
		if(plugins!=null)
		{
			for(Plugin plugin:plugins)
			{
				FXMLLoader loader= new FXMLLoader();
				Parent root;
				try {
					root = loader.load(PluginHouse.class.getResourceAsStream(Views.PLUGIN_ICON));
					Text txtName=(Text) root.lookup("#txtPlugin");
					CheckBox chkSelected=(CheckBox)root.lookup("#chkSelected");
					chkSelected.setVisible(false);
					ImageView pluginIcon= (ImageView) root.lookup("#pluginIcon");
					File pluginImageFile= new File(plugin.getPluginDirectory()+"/"+ plugin.getIconPath());
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
}
