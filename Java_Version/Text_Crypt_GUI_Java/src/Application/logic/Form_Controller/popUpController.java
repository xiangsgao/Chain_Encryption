package Application.logic.Form_Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Application.logic.model;
import Application.ui.popUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class popUpController implements Initializable{
	
	@FXML private Label popUpLabel;
	@FXML private RadioButton encryptRadio;
	@FXML private RadioButton decryptRadio;
	@FXML private Button cancel;
	@FXML private Button confirmed;
	@FXML private Label passwordField;
	
	private model model;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cancel.setOnAction(e -> popUp.closeWindow());
		this.encryptRadio.setSelected(true);
		this.decryptRadio.setSelected(false);
	}
	
	
	public void initializeData(model model) {
		this.model = model;
	}
	
	@FXML 
	public void confirmedSelected(ActionEvent e) {
		
	}
	
	
	
	
	
	
	
	
}
