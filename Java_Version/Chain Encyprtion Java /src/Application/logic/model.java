package Application.logic;
import Application.ui.mainWindow;
import javafx.scene.control.Button;

import java.io.File;
import Application.logic.cryptoException;
import Application.logic.Form_Controller.mainSceneController;

public class model {

	private mainSceneController mainSceneController;
	
	private mainWindow window;
	private String key = "";
	private File inputFile;
	private String outputFilePath;
	private boolean encryptedMode = true;
	private Button convertButton;
	
	public model(mainWindow window, mainSceneController mainSceneController, Button convertButton){
		this.window = window;
		this.mainSceneController = mainSceneController;
		this.mainSceneController.inializeModel(this);
		this.convertButton = convertButton;
	}
	
	public void convert()  throws cryptoException{
		if(this.encryptedMode) {
				this.encrypt();
		}
		else {
				this.decrypt();
		}
	}
	
	
	public void tellUItoDisplayPopUp() {
		window.displayPopUp();
	}
	
	public void setInputFile(String input) {
		this.inputFile = new File(input);
	}
	
	public void setOutPutFile(String outPutFilePath) {
		this.outputFilePath = outPutFilePath;
	}
	
	public void setEncrycted(boolean mode) {
		this.encryptedMode = mode;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	private void encrypt() throws cryptoException {
		File encryptedFile = new File(this.outputFilePath+"/" +"Encrypted"+this.inputFile.getName());
		if(encryptedFile.exists()) {
			throw new cryptoException("File with same name already exists, delete the old one first");
		}
		try {
			cryptoUtils.encrypt(this.key, this.inputFile, encryptedFile);
			}catch(OutOfMemoryError e) {
				throw new  cryptoException("File is too big dude, your computer does not have enough ram to convert the whole thing\nDon't worry though, I'll fix it in the next patch");
			}

			
		}
	
	private void decrypt() throws cryptoException {
		File decryptedFile = new File(this.outputFilePath+"/" +"decrypted"+this.inputFile.getName());
		if(decryptedFile.exists()) {
			throw new cryptoException("File with same name already exists, delete the old one first");
		}
		try {
		cryptoUtils.decrypt(this.key, this.inputFile, decryptedFile);
		}catch(OutOfMemoryError e) {
			throw new  cryptoException("File is too big dude, your computer does not have enough ram to convert the whole thing\nDon't worry though, I'll fix it in the next patch");
		}
	}
	
	public void convertButtonSetConvertingStatus(Boolean isConverting) {
		if(isConverting) {
			this.convertButton.setText("Converting...");
			this.convertButton.setDisable(true);
		}
		else {
			this.convertButton.setText("Convert");
			this.convertButton.setDisable(false);
		}
	}
		
	
	
	
	

}
