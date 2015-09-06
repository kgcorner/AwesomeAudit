package com.aa.dialogueutil;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import com.aa.constants.Locations;
import com.aa.constants.Views;

public class Dialogue {
	private DialogueType dialogueType;
	private String title;
	private String message;
	private Label lblTitle;
	private Label lblMessage;
	private Button btnOk;
	private Button btnCancel;
	private ImageView imgSignal;
	private Parent owner;
	public Dialogue(Parent owner)
	{
		this.dialogueType=DialogueType.INFO;
		this.title="Information";
		this.message="Application wanna say something";
		this.owner=owner;
	}
	public Dialogue(String title,String message,DialogueType dialogueType,Parent owner)
	{
		this.title=title;
		this.message=message;
		this.dialogueType=dialogueType;
		this.owner=owner;
	}

	
	public void show()
	{
		FXMLLoader loader= new FXMLLoader();
		owner.setDisable(true);
		try {
			Parent parent=loader.load(Dialogue.class.getResourceAsStream(Views.DIALOGUE));
			lblTitle=(Label) parent.lookup("#lblTitle");
			lblMessage=(Label) parent.lookup("#lblMessage");
			imgSignal=(ImageView) parent.lookup("#imgSignal");
			btnCancel=(Button)parent.lookup("#btnCancel");
			btnOk=(Button)parent.lookup("#btnOk");
			lblTitle.setText(this.title);
			lblMessage.setText(this.message);
			Image img=null;
			switch (dialogueType) {
			case CONFIRM:
				img= new Image(Locations.CONFIRM_ICON);				
				break;
			case ERROR:
				img= new Image(Locations.ERROR_ICON);
				break;
			case INFO:
				img= new Image(Locations.INFO_ICON);
				btnCancel.setVisible(false);
				break;				
			default:
				img= new Image(Locations.INFO_ICON);
				btnCancel.setVisible(false);
				break;
			}
			
			imgSignal.setImage(img);
			Stage dialogueStage= new Stage();
			Scene dialogueScene= new Scene(parent);
			dialogueStage.setScene(dialogueScene);			
			dialogueStage.show();
			btnOk.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					owner.setDisable(false);	
					dialogueStage.close();
				}
			});
			btnCancel.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					owner.setDisable(false);	
					dialogueStage.close();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
