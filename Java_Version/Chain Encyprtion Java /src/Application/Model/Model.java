package Application.Model;
import Application.Constants;
import Application.Libs.CryptoException;
import Application.Libs.CryptoUtils;
import Application.UI.MainWindow;
import javafx.scene.control.Button;

import java.io.File;

public class Model {

	
	private MainWindow window;
	private String key = "";
	private File inputFile;
	private String outputFilePath;
	private boolean encryptedMode = true;
	
	public Model(MainWindow window){
		this.window = window;
	}
	
	public void tellUItoDisplayPopUp() {
		window.displayOutputScene();
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


	public String getKey() {
		return key;
	}

	public File getInputFile() {
		return inputFile;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public boolean isEncryptedMode() {
		return encryptedMode;
	}
}
