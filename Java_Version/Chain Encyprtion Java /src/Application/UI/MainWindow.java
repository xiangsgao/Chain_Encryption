package Application.UI;
import Application.Constants;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Application.Model.Model;
import Application.Controllers.PopUpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
public class MainWindow {

	private Model model;
	private Stage stage;
	private Parent root;
	
	public MainWindow(Stage stage) throws Exception{
		/* This is how you do it without forms
		root = new StackPane();
		Button bt = new Button("Browse");
		// bt.setOnAction(e -> doSomething()); also works 
		bt.setOnAction(new browseHandler()); 
		root.getChildren().add(bt);
		this.stage.setScene(scene);
		*/
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(Constants.MAIN_SCREEN_FORM.getValue()));
		root = loader.load();
		Scene scene = new Scene(root);
		this.stage = stage;
		this.stage.setScene(scene);
		this.stage.show();
		// JavaFX version of getViewById, gotta do this after the scene has been render or it returns null. Use @FXML if all fails
		Button convertButton = (Button)loader.getNamespace().get(Constants.CONVERT_BUTTON.getValue());
		this.model = new Model(this, loader.getController(), convertButton);
	}
		
	
		public void displayPopUp() {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource(Constants.POP_UP_FORM.getValue()));
			try {
				Parent root = loader.load();
				PopUpController controller = loader.getController();
				controller.initializeData(model);
				PopUp.dispaly(root);
			} catch (IOException e) {
				Logger.getLogger(PopUp.class.getName()).log(Level.SEVERE, null, e);
			}
			
		}
	
	
}
