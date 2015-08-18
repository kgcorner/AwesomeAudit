package application;
	
import java.io.File;
import java.io.IOException;

import com.aa.util.ParamsFactory;
import com.easyfx.security.JWinSession;
import com.easyfx.util.ControllerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root=  new FXMLLoader().load(Main.class.getResourceAsStream("/views/Home.fxml"));
			ParamsFactory.masterLayout=root;
			Scene s= new Scene(root);
			primaryStage.setScene(s);
			ControllerFactory.populateControllerList("views/javafxmap.xml");
			ParamsFactory.primaryStage=primaryStage;
			primaryStage.show();
			//Initializing EasyFX Session
			JWinSession.initializeJWinSession();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
