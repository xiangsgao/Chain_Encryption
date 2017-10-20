package Application.logic.Form_Controller;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import Application.logic.model;
import Application.ui.alertPopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
		filePath.setPromptText("Click on browse to select path. .txt file only");
	}
	
	public void inializeModel(model model) {
		this.model = model;
	}
	
	
	@FXML
	public void convertButtonClicked(ActionEvent e) {
		/*if you don't want to use @FXML, you can also
		 * get the reference to the control object by using (Button) e.getSourced()*/
		
		// This checks if the file path is valid
		Path path = Paths.get(this.filePath.getText());
		if(this.filePath.getText().length()<4) {
			alertPopUp.display("Please Enter a valid file path.\nUse the browse button to help you.");
		}
		else if(!Files.exists(path)) {
			alertPopUp.display("Please enter a valid file path.\nUse the browse button to help you.");
		}
		else if(!this.filePath.getText().substring(this.filePath.getText().length() - 4).equalsIgnoreCase(".txt")) {
			alertPopUp.display("Invalid path or this is not a .txt file.\nYou trying to manually enter a nonsupported file?\nHaha, I am not that noob");
		}
		else {
			this.model.setInputFile(this.filePath.getText());
			this.model.tellUItoDisplayPopUp();
			}
		}
	
	@FXML 
	public void browseButtonClicked(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		//this makes sure that only .text file is selected
		chooser.getExtensionFilters().addAll(new ExtensionFilter("*.txt", "*.txt"));
		try {
		this.filePath.setText(chooser.showOpenDialog(null).getAbsolutePath()); 
		}catch(NullPointerException f) {
			System.out.println("User did not enter input\nNo worries");
		}
	}

}
