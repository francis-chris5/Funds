package Funds;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The main class for this application
 * @author Chris Francis
 */
public class FundMain extends Application{

    /**
     * default start method for a JavaFX application
     * @param stgMain the primary window, comes from operating system
     * @throws Exception --reading from a file to get GUI information, so I'm not sure what's supposed to catch this: it is the top level
     */
    @Override
    public void start(Stage stgMain) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FundGUI.fxml"));
        Scene scnMain = new Scene(root);
        stgMain.setScene(scnMain);
        stgMain.setTitle("Funds");
        Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
        stgMain.getIcons().add(icon);
        stgMain.show();
    }//end start()
    
    
    
    /**
     * default main for a JavaFX application
     * @param args 
     */
    public static void main(String[] args){
        Application.launch(args);
    }
    
}
