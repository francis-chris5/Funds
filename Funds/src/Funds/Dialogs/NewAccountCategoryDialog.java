
package Funds.Dialogs;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.AccountCategory;
import Funds.DataObjects.Book;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The controller class for the similarly named FXML dialog.
 * @author Chris Francis
 */
public class NewAccountCategoryDialog extends Dialog implements Initializable {

        ///////////////////////////////////////////  DATAFIELDS  ///////////
    
    @FXML
    ComboBox cmbType;
    @FXML
    TextField txtName;
    @FXML
    ComboBox cmbAddAccount;
    @FXML
    ListView lstAccounts;
    
    private Book book;
    private AccountType type;
    
    
    
    
    
    
    
    
        /////////////////////////////////////////  CONSTRUCTORS  ///////////
    
    public NewAccountCategoryDialog(Book book, AccountType type){
        this.book = book;
        this.type = type;
        this.setTitle("Funds: New Account");
        Image icon = new Image(getClass().getResourceAsStream("../Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewAccountCategoryDialogGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        setChoices();
        cmbType.setValue(this.type);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == ButtonType.OK){
            addAccountCategory();
        }
        else{
            //probably don't do anything then
        }
    }//end two-arg constructor
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////////  CLASS METHODS  /////////
    
    /**
     * internal method to load options in combo box
     */
    public void setChoices(){
        cmbType.getItems().addAll(AccountType.ASSET, AccountType.LIABILITY, AccountType.EQUITY);
    }//end setChoices()
    
    
    
    /**
     * method to actually create the category when the ok button is clicked
     */
    public void addAccountCategory(){
        book.getAccountCategories().add(new AccountCategory(txtName.getText(), (AccountType)cmbType.getValue()));
    }//end addAccountCategory()
    
    
    
        ///////////////////////////////////////////  JAVA OBJECT  ////////////
    
    /**
     * I rarely use this but it's in the interfacing requirements
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements
    }//end initialize()
    
}//end NewAccountCategoryDialog
