package Application.ui.Forms;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class alertPopUp {
	
	private static Stage window;
	
	public static void display(String message) {
		window = new Stage();
		window.setResizable(false);
		window.initModality(Modality.APPLICATION_MODAL);
		
		VBox layout = new VBox();
		Label label = new Label("message");
		Button ok = new Button("OK");
		ok.setOnAction(e -> close());
		layout.getChildren().addAll(label, ok);
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.setTitle("Alert");
	}
	
	
	private static void close() {
		window.close();
	}

}
