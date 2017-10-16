package Application.logic.Form_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import Application.logic.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class mainSceneController implements Initializable{
	
	private model model;
	
	@FXML 
	private Button browseButton;
	
	@FXML
	private Button convertButton;
	
	@FXML 
	private TextField filePath;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*If reference to other controller is needed, use:
		 * FMXLLoader loader = new FXMLLloader(this.getClass.getReousce(""FXMLPathName"));
		 * try{
		 * loader.load
		 * } catch(IOException e){
		 * Logger.getLogger(mainSceneController.class.getName(), null).log(Level.SEVERE, null, e);
		 * }
		 * controller controller = loader.getController();
		 * */
		convertButton.setDisable(true);
		filePath.textProperty().addListener((obs, oldText, newText) -> {
		    if(!newText.equals("")){
		    	mainSceneController.this.convertButton.setDisable(false);
		    }
		    else {
		    	mainSceneController.this.convertButton.setDisable(true);
		    }
		});
	}
	
	public void inializeModel(model model) {
		this.model = model;
	}
	
	
	@FXML
	public void convertButtonClicked(ActionEvent e) {
		/*if you don't want to use @FXML, you can also
		 * get the reference to the control object by using (Button) e.getSourced()*/
		this.model.tellUItoDisplayPopUp();
	}
	
	@FXML 
	public void browseButtonClicked(ActionEvent e) {
		System.out.println("Debugging: Browse Button clicked");
	}

}
