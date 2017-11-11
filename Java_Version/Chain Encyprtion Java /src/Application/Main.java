package Application;
import javafx.application.Application;
// Remember to configure Forbidden Reference rule to ignore
// in configure security
import javafx.stage.Stage;
import Application.ui.mainWindow;
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
