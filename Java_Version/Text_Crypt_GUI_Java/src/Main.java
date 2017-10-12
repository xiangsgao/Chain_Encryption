import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// Remember to configure Forbidden Reference rule to ignore
// in configure security
import javafx.stage.Stage;
import ui.mainWindow;
public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Text_Crypt");
		new mainWindow(stage);
		
	}



}
