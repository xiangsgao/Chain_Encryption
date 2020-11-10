package Application.Controllers;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import Application.Constants;
import Application.Libs.CryptoException;
import Application.Model.Model;
import Application.UI.AlertPopUp;
import Application.UI.PopUp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;

public class PopUpController implements Initializable{
	
	@FXML private RadioButton encryptRadio;
	@FXML private RadioButton decryptRadio;
	@FXML private Button cancel;
	@FXML private Button confirmed;
	@FXML private PasswordField passwordField;
	@FXML CheckBox showCheckBox;
	@FXML TextField unhiddenPasswordField;
	@FXML TextField popUpBrowsePath;
	@FXML Button popUpBrowse;
	
	private Model model;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cancel.setOnAction(e -> PopUp.closeWindow());
		this.encryptRadio.setSelected(true);
		this.decryptRadio.setSelected(false);
		this.passwordField.textProperty().addListener((obs,oldText,newText)-> {
			this.unhiddenPasswordField.setText(this.passwordField.getText());
		});
		
		this.unhiddenPasswordField.textProperty().addListener((obs,oldText,newText)-> {
			this.passwordField.setText(this.unhiddenPasswordField.getText());
		});
		
		
		this.showCheckBox.setOnAction(e -> {
			if(this.showCheckBox.isSelected()) {
				this.passwordField.setVisible(false);
				this.unhiddenPasswordField.setVisible(true);
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
		this.passwordField.setOnKeyPressed(new enterHandler());
		this.unhiddenPasswordField.setOnKeyPressed(new enterHandler());
		
	}
	
	
	public void initializeData(Model model) {
		this.model = model;
	}
	
	@FXML 
	public void confirmedSelected(ActionEvent e) {
		Path path = Paths.get(this.popUpBrowsePath.getText());
		
		if(!Files.exists(path)||this.popUpBrowsePath.getText().equals("")) {
			AlertPopUp.display(Constants.ENCRYPTED.getValue());
		}
		
		else if(this.passwordField.getText().equals("")){
			AlertPopUp.display(Constants.EMPTY_KEY_ERROR_MSG.getValue());
		}
		
		else {
			this.model.setKey(this.passwordField.getText());
			this.model.setOutPutFile(this.popUpBrowsePath.getText());
			this.model.setEncrypted(this.encryptRadio.isSelected());
			PopUp.closeWindow();
			model.convertButtonSetConvertingStatus(true);
			Thread convertThread = new Thread(new convertThread());
			convertThread.start();
			
		}
	}
	
	@FXML public void cancelClicked(ActionEvent e) {
		PopUp.closeWindow();
	}
	
	@FXML public void popUpBrowseClicked(ActionEvent e){
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Save Location");
		// null pointer error here, don't care cuz code still works 
		try {
		this.popUpBrowsePath.setText(chooser.showDialog(null).getAbsolutePath());
		} catch(NullPointerException f) {
			System.out.println(Constants.NULL_ERROR_MSG.getValue());
		}
	}
	
	
	private class enterHandler implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent e) {
			if (e.getCode().equals(KeyCode.ENTER)) {
				PopUpController.this.confirmedSelected(new ActionEvent());
			}

		}
	}
	
	private class convertThread implements Runnable{

		@Override
		public void run(){
			try {
				PopUpController.this.model.convert();
				Platform.runLater(() -> model.convertButtonSetConvertingStatus(false));
				Platform.runLater(() -> AlertPopUp.display("Success!"));
				
			} catch (CryptoException e) {
				Platform.runLater(() -> model.convertButtonSetConvertingStatus(false));
				Platform.runLater(() -> AlertPopUp.display(e.getMessage()));
			}
			
		}
		
	}
	
	
	
	
	
	
}
