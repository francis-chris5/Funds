
package Funds.Tools.GeneralLedger;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.Book;
import Funds.DataObjects.Transaction;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;



/**
 * The general ledger listing out all transactions with filters to sort through and track down the records of a transaction or group of transactions
 * @author Chris Francis
 */
public class LedgerPane extends Pane implements Initializable {
    
        ////////////////////////////////////////////  DATAFIELDS  ///////////
    
    @FXML
    RadioButton rdOn;
    @FXML
    RadioButton rdBefore;
    @FXML
    RadioButton rdAfter;
    @FXML
    RadioButton rdBetween;
    @FXML
    DatePicker dtDate1;
    @FXML
    DatePicker dtDate2;
    @FXML
    TextField txtDescription;
    @FXML
    TextField txtTransactionID;
    @FXML
    RadioButton rdChecked;
    @FXML
    RadioButton rdUnchecked;
    @FXML
    ComboBox cmbTransfer;
    @FXML
    TextField txtAmount;
    
    @FXML
    ListView lstTransactions;
    
    private Book book;
    private TableView<Transaction> tblLedger = new TableView<>();
    private LinkedList<Transaction> transactions = new LinkedList<>();
    private ObservableList<LedgerItemPane> observableTransactions = FXCollections.observableArrayList();
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  CONSTRUCTORS  //////////
    
    /**
     * In order to list out all recorded transactions the only thing necessary is the book where those transactions are recorded
     * @param book the current working book
     */
    public LedgerPane(Book book){
        this.book = book;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LedgerPaneGUI.fxml"));
            loader.setController(this);
            this.getChildren().add(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        fillList();
        loadAccountList();
        lstTransactions.getItems().addAll(observableTransactions);
    }//end one-arg constructor
    
    
    
    
    
    
    
    
        ///////////////////////////////////////  CLASS METHODS  /////////////
    
    /**
     * makes a single linked list of all transactions in the order they were recorded
     */
    public void fillList(){
        for(int i = 0; i < book.getAssets().size(); i++){
            transactions.addAll(book.getAssets().get(i).getTransactions());
        }
        for(int i = 0; i < book.getSubcategoryAccounts(AccountType.ASSET).size(); i++){
            transactions.addAll(book.getSubcategoryAccounts(AccountType.ASSET).get(i).getTransactions());
        }
        for(int i = 0; i < book.getLiabilities().size(); i++){
            transactions.addAll(book.getLiabilities().get(i).getTransactions());
        }
        for(int i = 0; i < book.getSubcategoryAccounts(AccountType.LIABILITY).size(); i++){
            transactions.addAll(book.getSubcategoryAccounts(AccountType.LIABILITY).get(i).getTransactions());
        }
        for(int i = 0; i < book.getEquities().size(); i++){
            transactions.addAll(book.getEquities().get(i).getTransactions());
        }
        for(int i = 0; i < book.getSubcategoryAccounts(AccountType.EQUITY).size(); i++){
            transactions.addAll(book.getSubcategoryAccounts(AccountType.EQUITY).get(i).getTransactions());
        }
        for(int i = 0; i < book.getLedgerID(); i++){
            Transaction t1 = null;
            Transaction t2 = null;
            for(int j = 0; j < transactions.size(); j++){
                if(transactions.get(j).getLedgerID() == i){
                    if(t1 == null){
                        t1 = transactions.get(j);
                    }
                    else{
                        t2 = transactions.get(j);
                    }
                }
            }
            if(t1 != null && t2 != null){
                observableTransactions.add(new LedgerItemPane(t1, t2));
            }
        }
    }//end fillLists()
    
    
    
    /**
     * internal method to disable/enable the second date picker when it's needed
     */
    @FXML
    public void activateDate2(){
        if(rdBetween.isSelected()){
            dtDate2.setDisable(false);
        }
        else{
            dtDate2.setDisable(true);
        }
    }//end activateDate2()
    
    
    
    
    /**
     * fills the options for the account filter
     */
    public void loadAccountList(){
        cmbTransfer.getItems().clear();
        cmbTransfer.setValue(null);
        cmbTransfer.getItems().addAll(book.getAssets());
        cmbTransfer.getItems().addAll(book.getSubcategoryAccounts(AccountType.ASSET));
        cmbTransfer.getItems().addAll(book.getLiabilities());
        cmbTransfer.getItems().addAll(book.getSubcategoryAccounts(AccountType.LIABILITY));
        cmbTransfer.getItems().addAll(book.getEquities());
        cmbTransfer.getItems().addAll(book.getSubcategoryAccounts(AccountType.EQUITY));
    }//end loadAccountList()
    
    
    
    
    /**
     * uses the selected filters to remove items from the list of transactions being shown
     */
    @FXML
    public void applyFilters(){
        
            // date filters
        if(rdOn.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(dtDate1.getValue() != null && !transactions.get(i).getDate().equals(dtDate1.getValue())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        else if(rdBefore.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(dtDate1.getValue() != null && !transactions.get(i).getDate().isBefore(dtDate1.getValue())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        else if(rdAfter.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(dtDate1.getValue() != null && !transactions.get(i).getDate().isAfter(dtDate1.getValue())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        else if(rdBetween.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(dtDate1.getValue() != null && (transactions.get(i).getDate().isBefore(dtDate1.getValue()) || transactions.get(i).getDate().isAfter(dtDate2.getValue()))){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
            //description filter
        if(txtDescription.getText().length() > 0){
            for(int i = 0; i < transactions.size(); i++){
                if(!transactions.get(i).getDescription().contains(txtDescription.getText())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
        
            //filter by human readable transaction id
        if(txtTransactionID.getText().length() > 0){
            for(int i = 0; i < transactions.size(); i++){
                if(!transactions.get(i).getTransactionID().contains(txtTransactionID.getText())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
            //reconciled filter
        if(rdChecked.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(!transactions.get(i).isReconcile()){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
        
            //unreconciled filter
        if(rdUnchecked.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(transactions.get(i).isReconcile()){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
        
            //account filter
        if(cmbTransfer.getValue() != null){
            LinkedList<Integer> keepIDs = new LinkedList<>();
            for(int i = 0; i < transactions.size(); i++){
                if(transactions.get(i).getTransfer() == cmbTransfer.getValue()){
                    keepIDs.add(transactions.get(i).getLedgerID());
                }
            }
            for(int i = 0; i < transactions.size(); i++){
                boolean keep = false;
                for(int j = 0; j < keepIDs.size(); j++){
                    if(transactions.get(i).getLedgerID() == keepIDs.get(j)){
                        keep = true;
                        break;
                    }
                }
                if(!keep){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
        
        
        if(txtAmount.getText().length() > 0){
            double amount = 0.0;
            try{
                amount = Double.parseDouble(txtAmount.getText());
            }
            catch(Exception e){
                try{
                    amount = Double.parseDouble(txtAmount.getText().substring(1));
                }
                catch(NumberFormatException nf){
                    //not a number or number with dollar sign, just ignoring then
                }
            }
            if(amount != 0.0){
                for(int i = 0; i < transactions.size(); i++){
                    if(transactions.get(i).getDebit() != amount && transactions.get(i).getCredit() != amount){
                        transactions.remove(i);
                        i--;
                    }
                }
            }
        }
        
        
        
        
        
            //reset the list with selected filters applied
        observableTransactions.clear();
        for(int i = 0; i < book.getLedgerID(); i++){
            Transaction t1 = null;
            Transaction t2 = null;
            for(int j = 0; j < transactions.size(); j++){
                if(transactions.get(j).getLedgerID() == i){
                    if(t1 == null){
                        t1 = transactions.get(j);
                    }
                    else{
                        t2 = transactions.get(j);
                    }
                }
            }
            if(t1 != null && t2 != null){
                observableTransactions.add(new LedgerItemPane(t1, t2));
            }
        }
        lstTransactions.getItems().clear();
        lstTransactions.getItems().addAll(observableTransactions);
    }//end applyFilters()

    
    
    
    
    /**
     * resets the filters and reloads the full list of transactions
     */
    @FXML
    public void clearFilters(){
        rdOn.setSelected(true);
        dtDate1.setValue(null);
        dtDate2.setValue(null);
        dtDate2.setDisable(true);
        txtDescription.clear();
        txtTransactionID.clear();
        rdChecked.setSelected(false);
        rdUnchecked.setSelected(false);
        loadAccountList();
        txtAmount.clear();
        transactions.clear();
        observableTransactions.clear();
        fillList();
        lstTransactions.getItems().clear();
        lstTransactions.getItems().addAll(observableTransactions);
    }//end clearFilters()
    
    
    
    
    
    /**
     * Method to listen for and react to keyboard events, currently the only one is to try apply filters when the enter key is pressed
     * @param event The keyboard event detected
     */
    @FXML
    public void checkKeyboardEvents(KeyEvent event){
        switch(event.getCode()){
            case ENTER:
                applyFilters();
                break;
            default:
                break;
        }
    }//end keyboardEvents
    
    
    
    
    
    
        /////////////////////////////////////////  JAVA OBJECT  /////////////
    
    /**
     * I rarely use this but it's in the interfacing requirements for fxml
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements for fxml
    }

}//end LedgerPane
