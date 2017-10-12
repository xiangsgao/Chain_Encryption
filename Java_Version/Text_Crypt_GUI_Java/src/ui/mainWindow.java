package ui;
import logic.model;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
public class mainWindow {

	model model;
	Stage stage;

	
	public mainWindow(Stage stage){
		model model = new model(this);
		this.stage = stage;
		stage.show();
	}
	
}
