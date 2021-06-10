
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;



/**
 * The general ledger listing out all transactions and eventually including filters and search features to sort through and find records of a certain transaction
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
    ListView lstTransactions;
    
    private Book book;
    private TableView<Transaction> tblLedger = new TableView<>();
    private LinkedList<Transaction> transactions = new LinkedList<>();
    private ObservableList<LedgerItemPane> observableTransactions = FXCollections.observableArrayList();
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  CONSTRUCTORS  //////////
    
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
        for(int i = 0; i < book.getSubcategoryAccounts(AccountType.LIABILITY).size(); i++){
            transactions.addAll(book.getSubcategoryAccounts(AccountType.LIABILITY).get(i).getTransactions());
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
     * uses the selected filters to remove items from the list of transactions being shown
     */
    @FXML
    public void applyFilters(){
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
        if(txtDescription.getText().length() > 0){
            for(int i = 0; i < transactions.size(); i++){
                if(!transactions.get(i).getDescription().contains(txtDescription.getText())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
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
        transactions.clear();
        observableTransactions.clear();
        fillList();
        lstTransactions.getItems().clear();
        lstTransactions.getItems().addAll(observableTransactions);
    }//end clearFilters()
    
    
    
    
    
    
    
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
