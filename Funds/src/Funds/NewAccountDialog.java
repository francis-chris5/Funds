
package Funds;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The controller class for the similarly named FXML dialog.
 * @author Chris Francis
 */
public class NewAccountDialog extends Dialog implements Initializable {

    @FXML
    ComboBox cmbParent;
    @FXML
    TextField txtName;
    @FXML
    RadioButton rdDebit;
    @FXML
    RadioButton rdCredit;
    @FXML
    TextField txtNumber;
    @FXML
    TextField txtRouting;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtDescription;
    
    
    private Book book;
    
    
    
    
    
    
    
    
        //////////////////////////////////////////////////  CONSTRUCTORS  ////////
    
    /**
     * The two-arg constructor needs a reference to the current Book and type of account as a string (not the enum value yet) which it gets from context menu on balance sheet tree
     * @param book The current book this account is being added to
     * @param type String (NOT ENUM VALUE this class will set that) representing the type of account ("Assets", "Liabilities", "Equity")
     */
    public NewAccountDialog(Book book, String type){
        this.book = book;
        this.setTitle("Funds: New Account");
        Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewAccountDialogGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        setParentChoices();
        cmbParent.setValue(type);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == ButtonType.OK){
            addAccount();
        }
        else{
            //probably don't do anything then
        }
    }//end one-arg constructor
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////// CLASS METHODS  /////////
    
    /**
     * Internal method to load choices for type of accounts into combobox
     */
    public void setParentChoices(){
            //eventually the book will have a list of subcategories to put accounts in rather than simply the big three
        cmbParent.getItems().addAll("Assets", "Liabilities", "Equity");
    }//end setParentChoices()
    
    
    
    /**
     * Method to actually create the account from the input fields on the dialog, called when ok button is clicked
     */
    public void addAccount(){
        Account acc = new Account();
        acc.setName(txtName.getText().length() == 0 ? "untitled" : txtName.getText());
        acc.setNormalDebit(rdDebit.isSelected() ? true : false);
        acc.setNumber(txtNumber.getText());
        acc.setRouting(txtRouting.getText());
        acc.setCode(txtCode.getText());
        acc.setDescription(txtDescription.getText());
        if(cmbParent.getValue().equals("Assets")){
            acc.setType(AccountType.ASSET);
            book.getAssets().add(acc);
        }
        else if(cmbParent.getValue().equals("Liabilities")){
            acc.setType(AccountType.LIABILITY);
            book.getLiabilities().add(acc);
        }
        else if(cmbParent.getValue().equals("Equity")){
            acc.setType(AccountType.EQUITY);
            book.getEquities().add(acc);
        }
        else{
            book.getAssets().add(acc);
        }
    }//end addAccount()
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////////  JAVA OBJECT  ///////
    
    /**
     * I rarely use this but it's in the interfacing requirements
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements
    }
    
}//end NewAccountDialog
