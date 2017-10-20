package Application.logic;
import Application.ui.mainWindow;
import java.io.File;
import Application.logic.Form_Controller.popUpController;
import Application.logic.Form_Controller.mainSceneController;

public class model {

	private mainSceneController mainSceneController;
	
	private mainWindow window;
	private String key = "";
	private File inputFile;
	private File outputFile;
	private popUpController contoller;
	private boolean encryptedMode = true;
	
	public model(mainWindow window, mainSceneController mainSceneController){
		this.window = window;
		this.mainSceneController = mainSceneController;
		this.mainSceneController.inializeModel(this);
	}
	
	public void convert() {

	}
	
	public void browsedClicked() {
		System.out.println("Success");
	}
	
	public void tellUItoDisplayPopUp() {
		window.displayPopUp();
	}
	
	public void setInputFile(String input) {
		inputFile = new File(input);
	}
	
	public void setOutPutFile(String outPutFilePath) {
		this.outputFile = new File(outPutFilePath);
	}
	
	public void setEncrycted(boolean mode) {
		this.encryptedMode = mode;
	}
	
	
	

}
