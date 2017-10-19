package Application.logic.Form_Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Application.logic.model;
import Application.ui.alertPopUp;
import Application.ui.popUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class popUpController implements Initializable{
	
	@FXML private RadioButton encryptRadio;
	@FXML private RadioButton decryptRadio;
	@FXML private Button cancel;
	@FXML private Button confirmed;
	@FXML private PasswordField passwordField;
	@FXML CheckBox showCheckBox;
	@FXML TextField unhiddenPasswordField;
	@FXML TextField popUpBrowsePath;
	
	private model model;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cancel.setOnAction(e -> popUp.closeWindow());
		this.encryptRadio.setSelected(true);
		this.decryptRadio.setSelected(false);
		
		
		
		this.showCheckBox.setOnAction(e -> {
			if(this.showCheckBox.isSelected()) {
				this.passwordField.setVisible(false);
				this.unhiddenPasswordField.setVisible(true);
				this.unhiddenPasswordField.setText(this.passwordField.getText());
			}
			else {
				this.unhiddenPasswordField.setVisible(false);
				this.passwordField.setVisible(true);
			}
		});
		this.passwordField.setText(this.unhiddenPasswordField.getText());
		this.unhiddenPasswordField.setVisible(false);
		this.passwordField.setVisible(true);
		this.passwordField.setText(this.unhiddenPasswordField.getText());
		this.unhiddenPasswordField.setVisible(false);
		this.passwordField.setVisible(true);
		
	}
	
	
	public void initializeData(model model) {
		this.model = model;
	}
	
	@FXML 
	public void confirmedSelected(ActionEvent e) {
		alertPopUp.display("Not supported yet");
		popUp.closeWindow();
	}
	
	
	
	
	
	
	
	
}
