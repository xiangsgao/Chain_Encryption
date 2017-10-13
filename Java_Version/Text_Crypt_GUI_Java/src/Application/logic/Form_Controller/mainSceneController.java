package Application.logic.Form_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class mainSceneController implements Initializable{
	
	@FXML 
	private Button browseButton;
	
	@FXML Label filePathLabel;

	
	
	
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Debugging: Initialized");
		
	}
	
	
	@FXML 
	public void browseButtonClicked() {
		System.out.println("Debugging: Button clicked");
	}

}
