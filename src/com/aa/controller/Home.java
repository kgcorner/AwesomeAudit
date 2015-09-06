package com.aa.controller;


import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import com.aa.dialogueutil.Dialogue;
import com.aa.dialogueutil.DialogueType;
import com.aa.util.ParamsFactory;
import com.easyfx.exception.NoControllorFoundException;
import com.easyfx.exception.NoResultFound;
import com.easyfx.util.BaseController;

public class Home extends BaseController {	
	private static final Logger log= Logger.getLogger(Home.class);
	
	@FXML protected void onHome(MouseEvent event)
	{
		log.info("On Home");
		/*try {
			load("home", "projectBrowser", ParamsFactory.masterLayout);
		} catch (IOException | NoControllorFoundException | NoResultFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Dialogue dialogue= new Dialogue("title", "abc", DialogueType.CONFIRM, ParamsFactory.masterLayout);
		dialogue.show();
	}
	@FXML protected void onEdit(MouseEvent event)
	{
		try {
			load("home", "edit", ParamsFactory.masterLayout);
		} catch (IOException | NoControllorFoundException | NoResultFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("On Edit");
	}
	@FXML protected void onPlugin(MouseEvent event)
	{
		log.info("On Plugin");
		try {
			load("home", "plugin", ParamsFactory.masterLayout);
		} catch (IOException | NoControllorFoundException | NoResultFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
