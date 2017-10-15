package Application.logic;
import Application.ui.mainWindow;
import Application.logic.Form_Controller.mainSceneController;

public class model {

	private mainSceneController mainSceneController;
	
	private mainWindow window;
	
	public model(mainWindow window, mainSceneController mainSceneController){
		this.window = window;
		this.mainSceneController = mainSceneController;
		this.mainSceneController.inializeModel(this);
	}
	
	public void convert() {
		System.out.println("Debugging: Convert Success!");
	}
	
	public void browsedClicked() {
		System.out.println("Success");
	}
	
	
	

}
