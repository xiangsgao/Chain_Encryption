package Application.Controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import Application.Constants;
import Application.Libs.CryptoException;
import Application.Libs.CryptoUtils;
import Application.Model.Model;
import Application.UI.AlertPopUp;
import Application.UI.MainWindow;
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

public class OutputSceneController implements Initializable{
	
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
	private MainWindow mainWindow;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainWindow = MainWindow.getMainWindow();
		model = mainWindow.getModel();

		cancel.setOnAction(e -> mainWindow.closeOutputScene());
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
		this.popUpBrowsePath.setText(model.getInputFile().getParent());
		model.setOutPutFile(model.getInputFile().getParent());
	}



	public void convert()  throws CryptoException {
		if(model.isEncryptedMode()) {
			this.encrypt();
		}
		else {
			this.decrypt();
		}
	}

	private void encrypt() throws CryptoException {
		File encryptedFile = new File(model.getOutputFilePath() + File.separator + Constants.ENCRYPTED.getValue() + model.getInputFile().getName());
		if(encryptedFile.exists()) {
			throw new CryptoException(Constants.FILE_EXIST_ERROR_MSG.getValue());
		}
		try {
			CryptoUtils.encrypt(model.getKey(), model.getInputFile(), encryptedFile);
		}catch(OutOfMemoryError e) {
			throw new CryptoException(Constants.FILE_TOO_BIG.getValue());
		}


	}

	private void decrypt() throws CryptoException {
		File decryptedFile = new File(model.getOutputFilePath() + File.separator + Constants.DECRYPTED.getValue() + model.getInputFile().getName());
		if(decryptedFile.exists()) {
			throw new CryptoException(Constants.FILE_EXIST_ERROR_MSG.getValue());
		}
		try {
			CryptoUtils.decrypt(model.getKey(), model.getInputFile(), decryptedFile);
		}catch(OutOfMemoryError e) {
			throw new CryptoException(Constants.FILE_TOO_BIG.getValue());
		}
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
			mainWindow.closeOutputScene();
			mainWindow.convertButtonSetConvertingStatus(true);
			Thread convertThread = new Thread(new convertThread());
			convertThread.start();
		}
	}
	
	@FXML public void cancelClicked(ActionEvent e) {
		mainWindow.closeOutputScene();
	}
	
	@FXML public void popUpBrowseClicked(ActionEvent e){
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle(Constants.SAVE_LOCATION.getValue());
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
				OutputSceneController.this.confirmedSelected(new ActionEvent());
			}

		}
	}
	
	private class convertThread implements Runnable{
		@Override
		public void run(){
			try {
				OutputSceneController.this.convert();
				Platform.runLater(() -> mainWindow.convertButtonSetConvertingStatus(false));
				Platform.runLater(() -> AlertPopUp.display(Constants.SUCCESS.getValue()));
			} catch (CryptoException e) {
				Platform.runLater(() -> mainWindow.convertButtonSetConvertingStatus(false));
				Platform.runLater(() -> AlertPopUp.display(e.getMessage()));
			}
			
		}
		
	}



	
	
	
	
	
	
}
