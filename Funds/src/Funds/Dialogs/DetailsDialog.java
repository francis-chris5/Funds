
package Funds.Dialogs;

import Funds.DataObjects.Book;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;




/**
 * The dialog for Book level interaction: book name (not filename to allow versioning), date opened, description, text editor for general note keeping...
 * @author Chris Francis
 */
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
    
    
    /**
     * The default constructor sets everything up with a new book, the is basically for the new file option in this software
     */
    public DetailsDialog(){
        book = new Book();
        this.setTitle("Funds: Book Details");
        Image icon = new Image(getClass().getResourceAsStream("../Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
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
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == btnConfirm){
            //should be finished at this point
        }
        else{
            //should be finished at this point
        }
    }//end string-arg constructor
    
    
    
    
    /**
     * The one-arg constructor takes in a reference to the book (working file for this software) currently open so that its details can be edited.
     * @param book The currently open Book object
     */
    public DetailsDialog(Book book){
        this.setTitle("Funds: Book Details");
        this.book = book;
        Image icon = new Image(getClass().getResourceAsStream("../Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsDialogGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            e.printStackTrace();
            //just move on then
        }
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
    
    /**
     * When the finished button is clicked this loads the values in the input fields into the Book object
     */
    public void editDetails(){
        book.setName(txtName.getText());
        book.setInitialized(dtInitialized.getValue());
        book.setDescription(txtDescription.getText());
        book.setNotes(txtNotes.getHtmlText());
        book.setSaved(false);
        book.displayDetails();
    }//end editDetails()
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////////  JAVA OBJECTS  ////////
    
    /**
     * I rarely use this but the interface requires it
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but the interface requires it
    }//end Initialize()
    
}//end DetailsDialog
