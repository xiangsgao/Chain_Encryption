package Application.UI;

import Application.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertPopUp {
	
	private static Stage window;
	
	public static void display(String message) {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		VBox layout = new VBox(10);
		Label label = new Label(message);
		Button ok = new Button(Constants.OK.getValue());
		ok.setOnAction(e -> close());
		layout.getChildren().addAll(label, ok);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(10));
		//use this for just one component
		//layout.setMargin(child, value);
		Scene scene = new Scene(layout);
		ok.setMinSize(50, 30);
		window.setMinWidth(250);
		window.setScene(scene);
		window.setTitle(Constants.ALERT.getValue());
		window.show();
	}
	
	
	private static void close() {
		window.close();
	}

}
