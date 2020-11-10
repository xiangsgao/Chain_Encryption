package Application.UI;
import Application.Constants;
import Application.Controllers.InputSceneController;
import Application.Controllers.OutputSceneController;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Application.Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainWindow {

	private static MainWindow mainWindow;

	private Model model;
	private Stage mainWindowStage;
	InputSceneController inputSceneController;
	OutputSceneController outputSceneController;


	public static MainWindow newInstance(Stage stage){
		if(mainWindow == null) mainWindow = new MainWindow(stage);
		return mainWindow;
	}

	public static MainWindow getMainWindow() {
		return mainWindow;
	}

	private MainWindow(Stage stage){
		/* This is how you do it without forms
		root = new StackPane();
		Button bt = new Button("Browse");
		// bt.setOnAction(e -> doSomething()); also works 
		bt.setOnAction(new browseHandler()); 
		root.getChildren().add(bt);
		this.stage.setScene(scene);
		*/
		this.mainWindowStage = stage;
		this.model = new Model(this);
	}


	public Model getModel() {
		return model;
	}

	public void displayOutputScene() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(Constants.POP_UP_FORM.getValue()));
		try {
			Parent root = loader.load();
			outputSceneController = loader.getController();
			OutputSceneWindow.display(root);
		} catch (IOException e) {
			Logger.getLogger(String.format("Error: %s", OutputSceneWindow.class.getName())).log(Level.SEVERE, null, e);
		}
	}

	public void closeOutputScene(){
		OutputSceneWindow.closeWindow();
	}

	public void displayInputScene(){
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(Constants.MAIN_SCREEN_FORM.getValue()));
		try{
			Parent root = loader.load();
			inputSceneController = loader.getController();
			Scene scene = new Scene(root);
			this.mainWindowStage.setScene(scene);
			this.mainWindowStage.show();
		}catch (IOException e){
			Logger.getLogger(String.format("Error: can't display input scene")).log(Level.SEVERE, null, e);
		}
	}

	public void convertButtonSetConvertingStatus(Boolean isConverting) {
		inputSceneController.convertButtonSetConvertingStatus(isConverting);
	}
}
