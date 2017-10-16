package Application.ui;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class popUp {
	

	
	public static void dispaly(){
		Stage window = new Stage();
		
		// This disables other windows until this windows is close 
		window.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(popUp.class.getResource("Forms/pop_up_Screen.fxml"));
		Parent layout;
		try {
			 layout = loader.load();
			 Scene scene = new Scene(layout);
			 window.setScene(scene);
			 window.show();
		} catch (IOException e) {
			Logger.getLogger(popUp.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
