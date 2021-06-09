
package Funds.Dialogs;

import Funds.DataEnums.NewBookType;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The dialog to start a new Book, which is the working file: what the user opens/edits/saves...
 * @author Chris Francis
 */
public class NewBookDialog extends Dialog implements Initializable {

        //////////////////////////////////////////  DATAFIELDS  /////////////
    
    @FXML
    RadioButton rdEmpty;
    @FXML
    RadioButton rdBasicPersonal;
    @FXML
    RadioButton rdDetailed;
    
    
    
    
    
    
    
    
        /////////////////////////////////////////  CONSTRUCTORS  //////////
    
    /**
     * The constructor doesn't do anything exceptional here, load in the fxml file and wait
     */
    public NewBookDialog(){
        this.setTitle("Funds: New Book");
        Image icon = new Image(getClass().getResourceAsStream("../Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewBookDialogGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> clicker = this.showAndWait();
        if(clicker.get() == ButtonType.OK){
            //should be finished by this point
        }
        else{
            //should be finished by this point
        }
    }//end constructor
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  CLASS METHODS  /////////
    
    /**
     * This method does the work for this class, determining which types of accounts will be included in the new book
     * @return <b>NewBookType</b> enumerated list defining the chosen starting accounts for the new book
     */
    public NewBookType getBookStart(){
        if(rdEmpty.isSelected()){
            return NewBookType.EMPTY;
        }
        else if(rdBasicPersonal.isSelected()){
            return NewBookType.BASIC_PERSONAL;
        }
        else if(rdDetailed.isSelected()){
            return NewBookType.DETAILED;
        }
        else{
            return NewBookType.EMPTY;
        }
    }//end getBookStart()
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  JAVA OBJECT  //////////
    
    /**
     * I rarely use this but it's in the interfacing requirements
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements
    }//end initialize()
    
}//end NewBookDialog
