package Application.logic;
import Application.ui.alertPopUp;
import Application.ui.mainWindow;
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
	
	public model(mainWindow window, mainSceneController mainSceneController){
		this.window = window;
		this.mainSceneController = mainSceneController;
		this.mainSceneController.inializeModel(this);
	}
	
	public void convert()  {
		if(this.encryptedMode) {
			try {
				this.encrypt();
				alertPopUp.display("Success!");
			} catch (cryptoException e) {
				alertPopUp.display(e.getMessage());
			}
		}
		else {
			try {
				this.decrypt();
				alertPopUp.display("Success!");
			} catch (cryptoException e) {
				alertPopUp.display(e.getMessage());
			}
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
		cryptoUtils.encrypt(this.key, this.inputFile, encryptedFile);
			
		}
	
	private void decrypt() throws cryptoException {
		File decryptedFile = new File(this.outputFilePath+"/" +"decrypted"+this.inputFile.getName());
		if(decryptedFile.exists()) {
			throw new cryptoException("File with same name already exists, delete the old one first");
		}
		cryptoUtils.decrypt(this.key, this.inputFile, decryptedFile);
			
		}
		
	
	
	
	

}
