package com.aa.controller;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import com.aa.constants.Views;
import com.aa.util.ParamsFactory;
import com.easyfx.exception.NoControllorFoundException;
import com.easyfx.exception.NoResultFound;
import com.easyfx.util.BaseController;

public class Home extends BaseController {	
	@FXML protected void onHome(MouseEvent event)
	{
		System.out.println("On Home");
		try {
			load("home", "projectBrowser", ParamsFactory.masterLayout);
		} catch (IOException | NoControllorFoundException | NoResultFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML protected void onEdit(MouseEvent event)
	{
		try {
			load("home", "edit", ParamsFactory.masterLayout);
		} catch (IOException | NoControllorFoundException | NoResultFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("On Edit");
	}
	@FXML protected void onPlugin(MouseEvent event)
	{
		System.out.println("On Plugin");
		try {
			load("home", "plugin", ParamsFactory.masterLayout);
		} catch (IOException | NoControllorFoundException | NoResultFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
