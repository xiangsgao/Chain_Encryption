package Application.Controllers;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import Application.Constants;
import Application.Model.Model;
import Application.UI.AlertPopUp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class MainSceneController implements Initializable{
	
	private Model model;
	
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
		    	MainSceneController.this.convertButton.setDisable(false);
		    }
		    else {
		    	MainSceneController.this.convertButton.setDisable(true);
		    }
		});
		
		// This adds the enter key function to text field
		this.filePath.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                MainSceneController.this.convertButtonClicked(new ActionEvent());
	            }
	        }
	    });
		filePath.setPromptText(Constants.BROWSE_TEXT.getValue());
	}
	
	public void initializeModel(Model model) {
		this.model = model;
	}
	
	
	@FXML
	public void convertButtonClicked(ActionEvent e) {
		/*if you don't want to use @FXML, you can also
		 * get the reference to the control object by using (Button) e.getSourced()*/
		
		// This checks if the file path is valid
		Path path = Paths.get(this.filePath.getText());
		if(!Files.exists(path)) {
			AlertPopUp.display(Constants.ENTER_VALID_PATH_TEXT.getValue());
		}
		else {
			this.model.setInputFile(this.filePath.getText());
			this.model.tellUItoDisplayPopUp();
			}
		}
	
	@FXML 
	public void browseButtonClicked(ActionEvent e) {
		FileChooser chooser = new FileChooser();
		//this makes sure that only .text file is selected, no need cuz AES encrypts all files
		//chooser.getExtensionFilters().addAll(new ExtensionFilter("*.txt", "*.txt"));
		try {
		this.filePath.setText(chooser.showOpenDialog(null).getAbsolutePath()); 
		}catch(NullPointerException f) {
			System.out.println(Constants.NO_INPUT_ERROR_MSG.getValue());
		}
	}

}
