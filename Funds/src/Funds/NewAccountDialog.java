
package Funds;

import java.net.URL;
import java.util.LinkedList;
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
    ComboBox cmbType;
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
    private AccountType type;
    
    
    
    
    
    
    
    
        //////////////////////////////////////////////////  CONSTRUCTORS  ////////
    
    /**
     * The two-arg constructor needs a reference to the current Book and type of account as a string (not the enum value yet) which it gets from context menu on balance sheet tree
     * @param book The current book this account is being added to
     * @param type String (NOT ENUM VALUE this class will set that) representing the type of account ("Assets", "Liabilities", "Equity")
     */
    public NewAccountDialog(Book book, AccountType type){
        this.book = book;
        this.type = type;
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
        cmbType.setValue(type);
        setChoices();
        setDefaultNormal();
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == ButtonType.OK){
            addAccount();
        }
        else{
            //probably don't do anything then
        }
    }//end one-arg constructor
    
    
    
    
    /**
     * The three-arg constructor needs a reference to the current Book and type of account as a string (not the enum value yet) which it gets from context menu on balance sheet tree, and the subcategory which this account goes into
     * @param book The current book this account is being added to
     * @param type String (NOT ENUM VALUE this class will set that) representing the type of account ("Assets", "Liabilities", "Equity")
     * @param parent The subcategory of accounts this new account will go into
     */
    public NewAccountDialog(Book book, AccountType type, AccountCategory parent){
        this.book = book;
        this.type = type;
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
        cmbType.setValue(this.type);
        cmbParent.setValue(parent);
        setChoices();
        setDefaultNormal();
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
    
    
    @FXML
    public void onTypeChange(){
        setChoices();
        setDefaultNormal();
    }//end onTypeChange()
    
    
    
    
    
    /**
     * Internal method to load choices for type of accounts into comboboxes
     */
    public void setChoices(){
        if(cmbType.getItems().isEmpty()){
            cmbType.getItems().addAll(AccountType.ASSET, AccountType.LIABILITY, AccountType.EQUITY);
        }
        if(book.getAccountCategories().isEmpty()){
            cmbParent.getItems().clear();
            cmbParent.setDisable(true);
        }
        else{
            cmbParent.getItems().clear();
            cmbParent.setDisable(false);
            LinkedList<AccountCategory> subcategory = book.getSubcategory((AccountType)cmbType.getValue());
            for(int i = 0; i < subcategory.size(); i++){
                cmbParent.getItems().add(subcategory.get(i));
            }
        }
    }//end setParentChoices()
    
    
    
    
    /**
     * internal method to set the default radio button selection: asset -debit, liability or equity -credit
     */
    @FXML
    public void setDefaultNormal(){
        if(cmbType.getValue() == AccountType.ASSET){
            rdDebit.setSelected(true);
        }
        else{
            rdCredit.setSelected(true);
        }
    }//end setDefaultNormal()
    
    
    
    
    
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
        if(cmbType.getValue() == AccountType.ASSET){
            if(cmbParent.getValue() == null){
                acc.setType(AccountType.ASSET);
                book.getAssets().add(acc);
            }
            else{
                acc.setType(AccountType.ASSET);
                for(int i = 0; i < book.getAccountCategories().size(); i++){
                    if(book.getAccountCategories().get(i) == cmbParent.getValue()){
                        book.getAccountCategories().get(i).getAccounts().add(acc);
                    }
                }
            }
        }
        else if(cmbType.getValue() == AccountType.LIABILITY){
            if(cmbParent.getValue() == null){
                acc.setType(AccountType.LIABILITY);
                book.getLiabilities().add(acc);
            }
            else{
                acc.setType(AccountType.LIABILITY);
                for(int i = 0; i < book.getAccountCategories().size(); i++){
                    if(book.getAccountCategories().get(i) == cmbParent.getValue()){
                        book.getAccountCategories().get(i).getAccounts().add(acc);
                    }
                }
            }
        }
        else if(cmbType.getValue() == AccountType.EQUITY){
            if(cmbParent.getValue() == null){
                acc.setType(AccountType.EQUITY);
                book.getEquities().add(acc);
            }
            else{
                acc.setType(AccountType.EQUITY);
                for(int i = 0; i < book.getAccountCategories().size(); i++){
                    if(book.getAccountCategories().get(i) == cmbParent.getValue()){
                        book.getAccountCategories().get(i).getAccounts().add(acc);
                    }
                }
            }
        }
        
        else{
            System.out.println("could not create account");
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
