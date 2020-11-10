package Application.Model;
import Application.Constants;
import Application.Libs.CryptoException;
import Application.Libs.CryptoUtils;
import Application.UI.MainWindow;
import javafx.scene.control.Button;

import java.io.File;

import Application.Controllers.MainSceneController;

public class Model {

	private MainSceneController mainSceneController;
	
	private MainWindow window;
	private String key = "";
	private File inputFile;
	private String outputFilePath;
	private boolean encryptedMode = true;
	private Button convertButton;
	
	public Model(MainWindow window, MainSceneController mainSceneController, Button convertButton){
		this.window = window;
		this.mainSceneController = mainSceneController;
		this.mainSceneController.initializeModel(this);
		this.convertButton = convertButton;
	}
	
	public void convert()  throws CryptoException {
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
	
	public void setEncrypted(boolean mode) {
		this.encryptedMode = mode;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	private void encrypt() throws CryptoException {
		File encryptedFile = new File(this.outputFilePath + File.pathSeparator + Constants.ENCRYPTED.getValue() +this.inputFile.getName());
		if(encryptedFile.exists()) {
			throw new CryptoException(Constants.FILE_EXIST_ERROR_MSG.getValue());
		}
		try {
			CryptoUtils.encrypt(this.key, this.inputFile, encryptedFile);
			}catch(OutOfMemoryError e) {
				throw new CryptoException(Constants.FILE_TOO_BIG.getValue());
			}

			
		}
	
	private void decrypt() throws CryptoException {
		File decryptedFile = new File(this.outputFilePath + File.pathSeparator + Constants.DECRYPTED.getValue() + this.inputFile.getName());
		if(decryptedFile.exists()) {
			throw new CryptoException(Constants.FILE_EXIST_ERROR_MSG.getValue());
		}
		try {
		CryptoUtils.decrypt(this.key, this.inputFile, decryptedFile);
		}catch(OutOfMemoryError e) {
			throw new CryptoException(Constants.FILE_TOO_BIG.getValue());
		}
	}
	
	public void convertButtonSetConvertingStatus(Boolean isConverting) {
		if(isConverting) {
			this.convertButton.setText(Constants.CONVERTING.getValue());
			this.convertButton.setDisable(true);
		}
		else {
			this.convertButton.setText(Constants.CONVERT.getValue());
			this.convertButton.setDisable(false);
		}
	}
		
	
	
	
	

}
