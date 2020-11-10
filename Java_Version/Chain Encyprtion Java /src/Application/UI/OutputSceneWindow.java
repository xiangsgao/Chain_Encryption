package Application.UI;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OutputSceneWindow {
    private static Stage outputSceneWindow;
    public static void display(Parent FXMLtemplate){
        outputSceneWindow = new Stage();
        // This disables other windows until this windows is close
        outputSceneWindow.initModality(Modality.APPLICATION_MODAL);
        // this disables all three utility buttons like maximize, minimize, and close windows functions but also get rid of header
        outputSceneWindow.initStyle(StageStyle.UNDECORATED);
        // This removes the resizing ability like maximize and minimize
        outputSceneWindow.setResizable(false);
        Scene scene = new Scene(FXMLtemplate);
        outputSceneWindow.setScene(scene);
        outputSceneWindow.show();
    }
    public static void closeWindow() {
        outputSceneWindow.close();
    }
}
