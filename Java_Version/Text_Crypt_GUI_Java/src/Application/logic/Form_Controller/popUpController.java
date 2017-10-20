package Application.logic.Form_Controller;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import Application.logic.model;
import Application.ui.alertPopUp;
import Application.ui.popUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class popUpController implements Initializable{
	
	@FXML private RadioButton encryptRadio;
	@FXML private RadioButton decryptRadio;
	@FXML private Button cancel;
	@FXML private Button confirmed;
	@FXML private PasswordField passwordField;
	@FXML CheckBox showCheckBox;
	@FXML TextField unhiddenPasswordField;
	@FXML TextField popUpBrowsePath;
	@FXML Button popUpBrowse;
	
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
				this.passwordField.setText(this.unhiddenPasswordField.getText());
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
		Path path = Paths.get(this.popUpBrowsePath.getText());
		
		if(!Files.exists(path)||this.popUpBrowsePath.getText().equals("")) {
			alertPopUp.display("Please enter a valid save location.\nUse the browse button to help you.");
		}
		
		else if(this.passwordField.getText().equals("")){
			alertPopUp.display("key can not be empty\nEnter keys with length of 8 or more if you are serious about security");
		}
		
		else {
			this.model.setKey(this.passwordField.getText());
			this.model.setOutPutFile(this.popUpBrowsePath.getText());
			this.model.setEncrycted(this.encryptRadio.isSelected());
			popUp.closeWindow();
			this.model.convert();
			};
	}
	
	@FXML public void cancelClicked(ActionEvent e) {
		popUp.closeWindow();
	}
	
	@FXML public void popUpBrowseClicked(ActionEvent e){
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Save Location");
		// null pointer error here, don't care cuz code still works 
		this.popUpBrowsePath.setText(chooser.showDialog(null).getAbsolutePath());
	}
	
	
	
	
	
	
	
	
	
}
