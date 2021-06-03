
package Funds;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The controller class for the similarly named FXML dialog.
 * @author Chris Francis
 */
public class EditTransaction extends Dialog implements Initializable {

    //////////////////////////////////////////////////  DATAFIELDS  ///////////
    
    @FXML
    DatePicker dtDate;
    @FXML
    TextField txtTransactionID;
    @FXML
    TextField txtDescription;
    @FXML
    ComboBox cmbTransfer;
    @FXML
    CheckBox chkReconcile;
    @FXML
    TextField txtAmount;
    
    private Book book = new Book();
    private Account account = new Account();
    private Entry revised = new Entry();
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  CONSTRUCTORS  /////////
    
    /**
     * The three-arg constructor needs all the information to edit details about a transaction:
     * @param book what book it's recorded in
     * @param account what account's ledger the edit was called from
     * @param original what entry is being edited
     */
    public EditTransaction(Book book, Account account, Entry original){
        this.book = book;
        this.account = account;
        this.revised = original;
        this.setTitle("Funds: " + original.getDescription());
        Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTransactionGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        //cmbTransfer.setDisable(true);
        setAccountChoices();
        dtDate.setValue(original.getDate());
        txtTransactionID.setText(original.getTransactionID());
        txtDescription.setText(original.getDescription());
        cmbTransfer.setValue(new ComboBoxItem(original.getTransfer(), false));
        chkReconcile.setSelected(original.isReconcile());
        if(original.getDebit() == 0.0){
            txtAmount.setText(Double.toString(original.getCredit()));
            revised.setNormalDebit(false);
        }
        else{
            txtAmount.setText(Double.toString(original.getDebit()));
            revised.setNormalDebit(true);
        }
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == ButtonType.OK){
            //should be finished here
        }
        else{
            //just cancel it then
        }
    }//end constructor
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////////  CLASS METHODS  /////////
    
    /**
     * internal method to set up the options in the ComboBox on the GUI
     */
    public void setAccountChoices(){
        cmbTransfer.getItems().clear();
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  ASSET  -----", true), true));
        for(int i = 0; i < book.getAssets().size(); i++){
            cmbTransfer.getItems().add(new ComboBoxItem(book.getAssets().get(i), false));
        }
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  LIABILITY  -----", false), true));
        for(int i = 0; i < book.getLiabilities().size(); i++){
            cmbTransfer.getItems().add(new ComboBoxItem(book.getLiabilities().get(i), false));
        }
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  EQUITY  -----", false), true));
        for(int i = 0; i < book.getEquities().size(); i++){
            cmbTransfer.getItems().add(new ComboBoxItem(book.getEquities().get(i), false));
        }
        cmbTransfer.setCellFactory(cell -> new ListCell<ComboBoxItem>(){
            @Override
            public void updateItem(ComboBoxItem account, boolean empty){
                super.updateItem(account, empty);
                if(!empty){
                    setText(account.getName());
                    setDisable(account.isLabel());
                    setStyle(account.isLabel ? "-fx-text-fill: #FF0000a2;" : "-fx-text-fill: #b5a264;");
                }
            }
        });
    }//end setAccountChoices()
    
    
    
    
    /**
     * A new Entry object is created with the values input into the EditTransactionGUI.fxml form, and replaces an old entry
     * @return <b>Entry</b> the updated (edited) transaction details
     */
    public Entry edit(){
        Entry entry = new Entry();
        entry.setDate(dtDate.getValue());
        entry.setTransactionID(txtTransactionID.getText());
        entry.setDescription(txtDescription.getText());
        //revised.setTransfer((Account)cmbTransfer.getValue());
        entry.setReconcile(chkReconcile.isSelected());
        if(account.isNormalDebit()){
            if(Double.parseDouble(txtAmount.getText()) < 0){
                entry.setCredit(-Double.parseDouble(txtAmount.getText()));
            }
            else{
                entry.setDebit(Double.parseDouble(txtAmount.getText()));
            }
        }
        else{
            if(Double.parseDouble(txtAmount.getText()) < 0){
                entry.setDebit(-Double.parseDouble(txtAmount.getText()));
            }
            else{
                entry.setCredit(Double.parseDouble(txtAmount.getText()));
            }
        }
        entry.setTransfer(((ComboBoxItem)cmbTransfer.getValue()).toAccount());
        editTransfer(entry.getTransfer(), !entry.getTransfer().isNormalDebit(), entry.getLedgerID());
        return entry;
    }//end edit()
    
    
    
    
    /**
     * Edits details into the other account used to balance the books, if necessary deletes the old balance transfer and creates a new one (i.e. the transfer column was edited) 
     * @param transfer The account used to balance the books with this transaction
     * @param notNormal Balance means the values are automatically the opposite of the orginal entry's debit/credit status
     * @param ledgerID The background ID the computer uses to track the transactions --different than the human readable transactionID that shows on the ledger
     */
    public void editTransfer(Account transfer, boolean notNormal, int ledgerID){
        try{
            Entry entry = new Entry();
            entry.setDate(dtDate.getValue());
            entry.setTransactionID(txtTransactionID.getText());
            entry.setDescription(txtDescription.getText());
            entry.setTransfer(account);
            entry.setReconcile(chkReconcile.isSelected());
            if(!notNormal){
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    entry.setCredit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    entry.setDebit(Double.parseDouble(txtAmount.getText()));
                }
            }
            else{
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    entry.setDebit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    entry.setCredit(Double.parseDouble(txtAmount.getText()));
                }
            }
            boolean sameAccount = false;
            for(int i = 0; i < ((ComboBoxItem)cmbTransfer.getValue()).toAccount().getEntries().size(); i++){
                if(((ComboBoxItem)cmbTransfer.getValue()).toAccount().getEntries().get(i).getLedgerID() == ledgerID){
                    sameAccount = true;
                }
            }
            if(sameAccount){
                for(int i = 0; i < transfer.getEntries().size(); i++){
                    if(transfer.getEntries().get(i).getLedgerID() == ledgerID){
                        transfer.getEntries().set(i, entry);
                    }
                }
            }
            else{
                ((ComboBoxItem)cmbTransfer.getValue()).toAccount().getEntries().add(entry);
                for(int i = 0; i < revised.getTransfer().getEntries().size(); i++){
                    if(revised.getTransfer().getEntries().get(i).getLedgerID() == ledgerID){
                        revised.getTransfer().getEntries().remove(i);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            //probbaly nothing to add: blank amount, rest are fine
        }
    }//end addTransfer()
    
    
    
    
    
    
    
    
        //////////////////////////////////////////////  JAVA OBJECT  /////////
    
    /**
     * I rarely use this, but required in interfacing requirements
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, but required in interfacing requirements
    }
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////  INNER CLASSES  //////////
    
    /**
     * list item for combo box so that category labels can be included
     */
    public class ComboBoxItem{
        
            /////////////////////////////  DATAFIELDS  /////////
        
        private Account account;
        private boolean isLabel;
        
        
            ///////////////////////////  CONSTRUCTORS  /////////
        public ComboBoxItem(Account account, boolean isLabel){
            this.account = account;
            this.isLabel = isLabel;
        }//end constructor
        
        
            ///////////////////////  CLASS METHODS  //////////
        public String getName(){
            if(isLabel){
                return account.getName();
            }
            else{
                return account.toString();
            }
        }//end getName()
        
        
        public boolean isLabel(){
            return isLabel;
        }//end isLabel()
        
        
        public Account toAccount(){
            if(isLabel){
                return null;
            }
            else{
                return account;
            }
        }//end toAccount()
        
        
        
            ///////////////////////////  JAVA OBJECT  //////////////
        @Override
        public String toString(){
            return account.toString();
        }//end toString()
        
    }//end ComboBoxItem
    
}//end EditTransaction
