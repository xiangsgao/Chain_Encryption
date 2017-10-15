package Application.logic.Form_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class mainSceneController implements Initializable{
	
	@FXML 
	private Button browseButton;
	
	@FXML
	private Button convertButton;
	
	@FXML 
	private TextField filePath;
	
	
	
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		convertButton.setDisable(true);
		filePath.setText("Debugging path set");
	}
	
	@FXML
	public void convertButtonClicked() {
		System.out.println("Debugging: Convert Button Clicked");
	}
	
	@FXML 
	public void browseButtonClicked() {
		System.out.println("Debugging: Browse Button clicked");
	}

}
