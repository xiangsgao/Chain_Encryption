package Application.ui;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class popUp {
	private static  Stage window;
	
	public static void dispaly(Parent root){
		 window = new Stage();
		// This disables other windows until this windows is close 
		window.initModality(Modality.APPLICATION_MODAL);
		// this disables all three utility buttons like maximize, minimize, and close windows functions but also get rid of header
		// window.initStyle(StageStyle.UNDECORATED);
		// This removes the resizing ability like maximize and minimize
		window.setResizable(false);
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.show();
		
	}
	
	public static void closeWindow() {
		window.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
