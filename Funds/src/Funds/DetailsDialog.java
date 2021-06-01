
package Funds;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class DetailsDialog extends Dialog implements Initializable {

    
        ///////////////////////////////////////////////  DATA FIELDS  ////////
    
    @FXML
    TextField txtName;
    @FXML
    DatePicker dtInitialized;
    @FXML
    TextField txtDescription;
    @FXML
    HTMLEditor txtNotes;
    
    
    private Book book;
    
    
    
    
    
    
    
    
        //////////////////////////////////////////////  CONSTRUCTORS  /////////
    
    public DetailsDialog(){
    }//end default constructor
    
    
    
    
    public DetailsDialog(String purpose){
        book = new Book();
        this.setTitle("Funds: Book Details");
        if(purpose.equals("new")){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsDialogGUI.fxml"));
                loader.setController(this);
                this.setDialogPane(loader.load());
            }
            catch(Exception e){
                //just move on then
            }
            ButtonType btnConfirm = new ButtonType("Create Book", ButtonData.OTHER);
            this.getDialogPane().getButtonTypes().addAll(btnConfirm, ButtonType.CANCEL);
            Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
            Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
            stage.getIcons().add(icon);
            Optional<ButtonType> clicked = this.showAndWait();
            if(clicked.get() == btnConfirm){
                //should be finished at this point
            }
            else{
                this.book = null;
            }
        }
    }//end string-arg constructor
    
    
    
    
    public DetailsDialog(Book book){
        this.setTitle("Funds: Book Details");
        this.book = book;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsDialogGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            e.printStackTrace();
            //just move on then
        }
        Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        ButtonType btnConfirm = new ButtonType("Update Book", ButtonData.OTHER);
        this.getDialogPane().getButtonTypes().addAll(btnConfirm, ButtonType.CANCEL);
        txtName.setText(this.book.getName());
        dtInitialized.setValue(this.book.getInitialized());
        txtDescription.setText(this.book.getDescription());
        txtNotes.setHtmlText(this.book.getNotes());
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == btnConfirm){
            //should be finished at this point
        }
        else{
            //should be finished when button is clicked
        }
    }//end routine-arg consturctor
    
    
    
    
    
    
        ///////////////////////////////////////////////  DIALOG METHODS  //////
    
    public void editDetails(){
        book.setName(txtName.getText());
        book.setInitialized(dtInitialized.getValue());
        book.setDescription(txtDescription.getText());
        book.setNotes(txtNotes.getHtmlText());
    }//end editDetails()
    
    
        ///////////////////////////////////////////////  JAVA OBJECTS  ////////
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but the interface requires it
    }//end Initialize()
    
}//end DetailsDialog
