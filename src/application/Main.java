package application;
	
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.aa.util.ParamsFactory;
import com.easyfx.security.JWinSession;
import com.easyfx.util.ControllerFactory;


public class Main extends Application {
	private static final Logger log= Logger.getLogger(Main.class);
	@Override
	public void start(Stage primaryStage) {
		try {
			log.info("Initializinf Application at :"+new Date());
			log.info("Loading Controllers");
			ControllerFactory.populateControllerList("views/javafxmap.xml");
			log.info("Loading placeholder");
			BorderPane root=  new FXMLLoader().load(Main.class.getResourceAsStream("/views/Home.fxml"));
			log.info("Loading project browser");
			Node node=new FXMLLoader().load(Main.class.getResourceAsStream("/views/ProjectBrowser.fxml"));
			ParamsFactory.masterLayout=root;
			root.setCenter(node);
			Scene s= new Scene(root);
			primaryStage.setScene(s);
			
			ParamsFactory.primaryStage=primaryStage;
			primaryStage.show();
			log.info("Initializing JwinSession");
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
