package Application;
import javafx.application.Application;
// Remember to configure Forbidden Reference rule to ignore
// in configure security
import javafx.stage.Stage;
import Application.UI.MainWindow;
public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle(Constants.TITLE.getValue());
		new MainWindow(stage);
	}



}
