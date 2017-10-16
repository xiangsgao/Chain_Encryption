package Application.ui;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Application.logic.model;
import Application.logic.Form_Controller.popUpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class mainWindow {

	private model model;
	private Stage stage;
	private Parent root;
	
	public mainWindow(Stage stage) throws Exception{
		/* This is how you do it without forms
		root = new StackPane();
		Button bt = new Button("Browse");
		// bt.setOnAction(e -> doSomething()); also works 
		bt.setOnAction(new browseHandler()); 
		root.getChildren().add(bt);
		this.stage.setScene(scene);
		*/
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Application/ui/Forms/main_screen.fxml"));
		root = loader.load();
		Scene scene = new Scene(root);
		this.model = new model(this, loader.getController());
		this.stage = stage;
		this.stage.setScene(scene);
		this.stage.show();
	}
		
	
		public void displayPopUp() {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Application/ui/Forms/pop_up_Screen.fxml"));
			try {
				Parent root = loader.load();
				popUpController controller = loader.getController(); 
				controller.initializeData(model);
				popUp.dispaly(root);
			} catch (IOException e) {
				Logger.getLogger(popUp.class.getName()).log(Level.SEVERE, null, e);
			}
			
		}
	
	
}
