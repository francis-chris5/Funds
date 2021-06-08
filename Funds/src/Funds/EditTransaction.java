
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
    private Transaction revised = new Transaction();
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  CONSTRUCTORS  /////////
    
    /**
     * The three-arg constructor needs all the information to edit details about a transaction:
     * @param book what book it's recorded in
     * @param account what account's ledger the edit was called from
     * @param original what transaction is being edited
     */
    public EditTransaction(Book book, Account account, Transaction original){
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
        cmbTransfer.setValue(original.getTransfer() != null ? new ComboBoxItem(original.getTransfer(), false) : null);
        chkReconcile.setSelected(original.isReconcile());
        if(original.getDebit() == 0.0){
            txtAmount.setText(account.isNormalDebit() ? Double.toString(-original.getCredit()) : Double.toString(original.getCredit()));
            revised.setNormalDebit(false);
        }
        else{
            txtAmount.setText(account.isNormalDebit() ? Double.toString(original.getDebit()) : Double.toString(-original.getDebit()));
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
            if(!book.getAssets().get(i).toString().equals(account.toString())){
                cmbTransfer.getItems().add(new ComboBoxItem(book.getAssets().get(i), false));
            }
        }
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  LIABILITY  -----", false), true));
        for(int i = 0; i < book.getLiabilities().size(); i++){
            if(!book.getLiabilities().get(i).toString().equals(account.toString())){
                cmbTransfer.getItems().add(new ComboBoxItem(book.getLiabilities().get(i), false));
            }
        }
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  EQUITY  -----", false), true));
        for(int i = 0; i < book.getEquities().size(); i++){
            if(!book.getEquities().get(i).toString().equals(account.toString())){
                cmbTransfer.getItems().add(new ComboBoxItem(book.getEquities().get(i), false));
            }
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
     * A new Transaction object is created with the values input into the EditTransactionGUI.fxml form, and replaces an old transaction
     * @return <b>Transaction</b> the updated (edited) transaction details
     */
    public Transaction edit(){
        Transaction transaction = new Transaction();
        transaction.setLedgerID(revised.getLedgerID());
        transaction.setDate(dtDate.getValue());
        transaction.setTransactionID(txtTransactionID.getText());
        transaction.setDescription(txtDescription.getText());
        //revised.setTransfer((Account)cmbTransfer.getValue());
        transaction.setReconcile(chkReconcile.isSelected());
        if(account.isNormalDebit()){
            if(Double.parseDouble(txtAmount.getText()) < 0){
                transaction.setCredit(-Double.parseDouble(txtAmount.getText()));
            }
            else{
                transaction.setDebit(Double.parseDouble(txtAmount.getText()));
            }
        }
        else{
            if(Double.parseDouble(txtAmount.getText()) < 0){
                transaction.setDebit(-Double.parseDouble(txtAmount.getText()));
            }
            else{
                transaction.setCredit(Double.parseDouble(txtAmount.getText()));
            }
        }
        transaction.setTransfer(((ComboBoxItem)cmbTransfer.getValue()).toAccount());
        editTransfer(transaction.getTransfer(), account.isNormalDebit(), transaction.getLedgerID());
        return transaction;
    }//end edit()
    
    
    
    
    /**
     * Edits details into the other account used to balance the books, if necessary deletes the old balance transfer and creates a new one (i.e. the transfer column was edited) 
     * @param transfer The account used to balance the books with this transaction
     * @param notNormal Balance means the values are automatically the opposite of the original transaction's debit/credit status
     * @param ledgerID The background ID the computer uses to track the transactions --different than the human readable transactionID that shows on the ledger
     */
    public void editTransfer(Account transfer, boolean notNormal, int ledgerID){
        try{
            Transaction transaction = new Transaction();
            transaction.setLedgerID(revised.getLedgerID());
            transaction.setDate(dtDate.getValue());
            transaction.setTransactionID(txtTransactionID.getText());
            transaction.setDescription(txtDescription.getText());
            transaction.setTransfer(account);
            transaction.setReconcile(chkReconcile.isSelected());
            if(!notNormal){
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    transaction.setCredit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    transaction.setDebit(Double.parseDouble(txtAmount.getText()));
                }
            }
            else{
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    transaction.setDebit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    transaction.setCredit(Double.parseDouble(txtAmount.getText()));
                }
            }
            boolean sameAccount = false;
            for(int i = 0; i < ((ComboBoxItem)cmbTransfer.getValue()).toAccount().getTransactions().size(); i++){
                if(((ComboBoxItem)cmbTransfer.getValue()).toAccount().getTransactions().get(i).getLedgerID() == ledgerID){
                    sameAccount = true;
                }
            }
            if(sameAccount){
                for(int i = 0; i < transfer.getTransactions().size(); i++){
                    if(transfer.getTransactions().get(i).getLedgerID() == ledgerID){
                        transfer.getTransactions().set(i, transaction);
                    }
                }
            }
            else{
                ((ComboBoxItem)cmbTransfer.getValue()).toAccount().getTransactions().add(transaction);
                for(int i = 0; i < revised.getTransfer().getTransactions().size(); i++){
                    if(revised.getTransfer().getTransactions().get(i).getLedgerID() == ledgerID){
                        revised.getTransfer().getTransactions().remove(i);
                    }
                }
            }
        }
        catch(Exception e){
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
